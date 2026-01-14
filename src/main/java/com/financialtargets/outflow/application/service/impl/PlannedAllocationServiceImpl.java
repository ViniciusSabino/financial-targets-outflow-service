package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.service.PlannedAllocationService;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountsRepository;
import com.financialtargets.outflow.infrastructure.repository.PlannedAllocationRepository;
import com.financialtargets.outflow.infrastructure.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlannedAllocationServiceImpl implements PlannedAllocationService {

    private final PlannedAllocationRepository repository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;

    private final SummaryService summaryService;

    @Override
    public List<PlannedAllocation> listByMonth(Integer month, Integer year) throws Exception {
        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        log.info("Listing planned allocations for the period {} to {}", start, end);

        List<PlannedAllocationEntity> outflows = repository.findByAllocationDateBetween(start, end);

        log.info("Listed {} planned allocations successfully", outflows.size());

        return PlannedAllocationMapper.toModelList(outflows);
    }

    @Override
    public PlannedAllocation create(PlannedAllocationCreateDTO plannedAllocationCreateDTO) throws Exception {
        if (!OutflowRecurrence.isValidRecurrence(plannedAllocationCreateDTO.recurrence()))
            throw new BusinessException("Invalid recurrence for create a new planned allocation");

        if (Objects.isNull(plannedAllocationCreateDTO.definedPercentage()) && Objects.isNull(plannedAllocationCreateDTO.value()))
            throw new BusinessException("Choose between setting \"value\" or \"definedPercentage\"");

        if (!Objects.isNull(plannedAllocationCreateDTO.definedPercentage()) && !Objects.isNull(plannedAllocationCreateDTO.value()))
            throw new BusinessException("Choose between setting \"value\" or \"definedPercentage\"");

        PlannedAllocationEntity existingEntity = repository.findByName(plannedAllocationCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        PlannedAllocation plannedAllocation = new PlannedAllocation(plannedAllocationCreateDTO);

        Integer currentMonth = DateUtil.getNowLocalDate().getMonth().getValue();
        Integer currentYear = DateUtil.getNowLocalDate().getYear();

        PlannedAllocationSummary plannedAllocationSummary = summaryService.getPlannedAllocationSummary(currentMonth, currentYear);

        if ((Objects.isNull(plannedAllocation.getDefinedPercentage()))) {
            plannedAllocation.setDefinedPercentage(plannedAllocationSummary.getTotalAmount().divide(plannedAllocation.getValue(), 2, RoundingMode.HALF_UP));
        } else {
            BigDecimal percentage = plannedAllocation.getDefinedPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            plannedAllocation.setValue(percentage.multiply(plannedAllocationSummary.getTotalAmount()));
        }

        if (plannedAllocationCreateDTO.appliedValue().compareTo(plannedAllocation.getValue()) >= 0)
            plannedAllocation.setAppliedValue(plannedAllocation.getValue());

        PlannedAllocationEntity entity = new PlannedAllocationEntity();

        entity.setUser(usersRepository.getReferenceById(plannedAllocationCreateDTO.userId()));
        entity.setAccount(accountsRepository.getReferenceById(plannedAllocationCreateDTO.accountId()));

        entity.setName(plannedAllocation.getName());
        entity.setDefinedPercentage(plannedAllocation.getDefinedPercentage());
        entity.setValue(plannedAllocation.getValue());
        entity.setAppliedValue(plannedAllocation.getAppliedValue());
        entity.setAllocationDate(plannedAllocation.getAllocationDate());
        entity.setRecurrence(plannedAllocation.getRecurrence().name());
        entity.setNotes(plannedAllocation.getNotes());
        entity.setCreatedAt(plannedAllocation.getCreatedAt());
        entity.setUpdatedAt(plannedAllocation.getUpdatedAt());

        PlannedAllocation savedAllocation = repository.save(entity).toModel();

        log.info("Allocation created successfully, id: {}", savedAllocation.getId());

        return savedAllocation;
    }

    @Override
    public PlannedAllocation update(Long id, PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) throws Exception {
        if (!Objects.isNull(plannedAllocationUpdateDTO.recurrence()) && !OutflowRecurrence.isValidRecurrence(plannedAllocationUpdateDTO.recurrence()))
            throw new BusinessException("Invalid recurrence for create a new planned allocation");

        PlannedAllocationEntity currentOutflow = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Planned allocation not found"));

        if (Objects.equals(currentOutflow.getName(), plannedAllocationUpdateDTO.name()) && !Objects.equals(currentOutflow.getId(), id)) {
            throw new BusinessException("There is already an allocation exit with that name");
        }

        PlannedAllocation allocationUpdate = new PlannedAllocation(plannedAllocationUpdateDTO);

        if(!Objects.isNull(plannedAllocationUpdateDTO.accountId())) {
            currentOutflow.setAccount(accountsRepository.getReferenceById(plannedAllocationUpdateDTO.accountId()));
        }

        if (!Objects.isNull(allocationUpdate.getName())) currentOutflow.setName(allocationUpdate.getName());
        if (!Objects.isNull(allocationUpdate.getAppliedValue())) currentOutflow.setAppliedValue(allocationUpdate.getAppliedValue());
        if (!Objects.isNull(allocationUpdate.getAllocationDate())) currentOutflow.setAllocationDate(allocationUpdate.getAllocationDate());
        if (!Objects.isNull(allocationUpdate.getNotes())) currentOutflow.setNotes(allocationUpdate.getNotes());
        if (!Objects.isNull(allocationUpdate.getRecurrence())) currentOutflow.setRecurrence(allocationUpdate.getRecurrence().name());

        PlannedAllocation updatedAllocation = repository.save(currentOutflow).toModel();

        log.info("Allocation updated successfully, id: {}", updatedAllocation.getId());

        return updatedAllocation;
    }

    @Override
    public PlannedAllocation fullyApplied(Long id) throws BusinessException {
        Optional<PlannedAllocationEntity> optionalPlannedAllocation = repository.findById(id);

        if (optionalPlannedAllocation.isEmpty()) {
            throw new BusinessException("Planned allocation not found");
        }

        PlannedAllocationEntity entity = optionalPlannedAllocation.get();

        entity.setIsFullyApplied(true);
        entity.setAppliedValue(entity.getValue());

        return repository.save(entity).toModel();
    }
}
