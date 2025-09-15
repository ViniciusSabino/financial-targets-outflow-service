package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.OutflowAllocationUpdateDTO;
import com.financialtargets.outflow.application.service.OutflowAllocationService;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.mapper.OutflowAllocationMapper;
import com.financialtargets.outflow.domain.model.OutflowAllocation;
import com.financialtargets.outflow.domain.model.OutflowAllocationSummary;
import com.financialtargets.outflow.infrastructure.entity.OutflowAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountRepository;
import com.financialtargets.outflow.infrastructure.repository.OutflowAllocationRepository;
import com.financialtargets.outflow.infrastructure.repository.UserRepository;
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
public class OutflowAllocationServiceImpl implements OutflowAllocationService {

    private final OutflowAllocationRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private final SummaryService summaryService;

    @Override
    public List<OutflowAllocation> listByMonth(Integer month, Integer year) throws Exception {
        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        log.info("Listing outflow allocations for the period {} to {}", start, end);

        List<OutflowAllocationEntity> outflows = repository.findByAllocationDateBetween(start, end).stream().toList();

        log.info("Listed {} outflow allocations successfully", outflows.stream().toList().size());

        return OutflowAllocationMapper.toModelList(outflows);
    }

    @Override
    public OutflowAllocation create(OutflowAllocationCreateDTO outflowAllocationCreateDTO) throws Exception {
        if (!OutflowRecurrence.isValidRecurrence(outflowAllocationCreateDTO.recurrence()))
            throw new BusinessException("Invalid recurrence for create a new outflow allocation");

        if (Objects.isNull(outflowAllocationCreateDTO.definedPercentage()) && Objects.isNull(outflowAllocationCreateDTO.value()))
            throw new BusinessException("Choose between setting \"value\" or \"definedPercentage\"");

        if (!Objects.isNull(outflowAllocationCreateDTO.definedPercentage()) && !Objects.isNull(outflowAllocationCreateDTO.value()))
            throw new BusinessException("Choose between setting \"value\" or \"definedPercentage\"");

        OutflowAllocationEntity existingEntity = repository.findByName(outflowAllocationCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        OutflowAllocation outflowAllocation = new OutflowAllocation(outflowAllocationCreateDTO);

        Integer currentMonth = DateUtil.getNowLocalDate().getMonth().getValue();
        Integer currentYear = DateUtil.getNowLocalDate().getYear();

        OutflowAllocationSummary outflowAllocationSummary = summaryService.getOutflowAllocationSummary(currentMonth, currentYear);

        if ((Objects.isNull(outflowAllocation.getDefinedPercentage()))) {
            outflowAllocation.setDefinedPercentage(outflowAllocationSummary.getTotalAmount().divide(outflowAllocation.getValue(), 2, RoundingMode.HALF_UP));
        } else {
            BigDecimal percentage = outflowAllocation.getDefinedPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            outflowAllocation.setValue(percentage.multiply(outflowAllocationSummary.getTotalAmount()));
        }

        if (outflowAllocationCreateDTO.appliedValue().compareTo(outflowAllocation.getValue()) >= 0)
            outflowAllocation.setAppliedValue(outflowAllocation.getValue());

        OutflowAllocationEntity entity = new OutflowAllocationEntity();

        entity.setUser(userRepository.getReferenceById(outflowAllocationCreateDTO.userId()));
        entity.setAccount(accountRepository.getReferenceById(outflowAllocationCreateDTO.accountId()));

        entity.setName(outflowAllocation.getName());
        entity.setDefinedPercentage(outflowAllocation.getDefinedPercentage());
        entity.setValue(outflowAllocation.getValue());
        entity.setAppliedValue(outflowAllocation.getAppliedValue());
        entity.setAllocationDate(outflowAllocation.getAllocationDate());
        entity.setRecurrence(outflowAllocation.getRecurrence().name());
        entity.setNotes(outflowAllocation.getNotes());
        entity.setCreatedAt(outflowAllocation.getCreatedAt());
        entity.setUpdatedAt(outflowAllocation.getUpdatedAt());

        OutflowAllocation savedAllocation = repository.save(entity).toModel();

        log.info("Allocation created successfully, id: {}", savedAllocation.getId());

        return savedAllocation;
    }

    @Override
    public OutflowAllocation update(Long id, OutflowAllocationUpdateDTO outflowAllocationUpdateDTO) throws Exception {
        if (!Objects.isNull(outflowAllocationUpdateDTO.recurrence()) && !OutflowRecurrence.isValidRecurrence(outflowAllocationUpdateDTO.recurrence()))
            throw new BusinessException("Invalid recurrence for create a new outflow allocation");

        OutflowAllocationEntity currentOutflow = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Outflow allocation not found"));

        if (Objects.equals(currentOutflow.getName(), outflowAllocationUpdateDTO.name()) && !Objects.equals(currentOutflow.getId(), id)) {
            throw new BusinessException("There is already an allocation exit with that name");
        }

        OutflowAllocation allocationUpdate = new OutflowAllocation(outflowAllocationUpdateDTO);

        if(!Objects.isNull(outflowAllocationUpdateDTO.accountId())) {
            currentOutflow.setAccount(accountRepository.getReferenceById(outflowAllocationUpdateDTO.accountId()));
        }

        if (!Objects.isNull(allocationUpdate.getName())) currentOutflow.setName(allocationUpdate.getName());
        if (!Objects.isNull(allocationUpdate.getAppliedValue())) currentOutflow.setAppliedValue(allocationUpdate.getAppliedValue());
        if (!Objects.isNull(allocationUpdate.getAllocationDate())) currentOutflow.setAllocationDate(allocationUpdate.getAllocationDate());
        if (!Objects.isNull(allocationUpdate.getNotes())) currentOutflow.setNotes(allocationUpdate.getNotes());
        if (!Objects.isNull(allocationUpdate.getRecurrence())) currentOutflow.setRecurrence(allocationUpdate.getRecurrence().name());

        OutflowAllocation updatedAllocation = repository.save(currentOutflow).toModel();

        log.info("Allocation updated successfully, id: {}", updatedAllocation.getId());

        return updatedAllocation;
    }

    @Override
    public OutflowAllocation fullyApplied(Long id) throws BusinessException {
        Optional<OutflowAllocationEntity> optionalOutflowAllocation = repository.findById(id);

        if (optionalOutflowAllocation.isEmpty()) {
            throw new BusinessException("Outflow allocation not found");
        }

        OutflowAllocationEntity entity = optionalOutflowAllocation.get();

        entity.setIsFullyApplied(true);
        entity.setAppliedValue(entity.getValue());

        return repository.save(entity).toModel();
    }
}
