package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.application.service.OutflowAllocationService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.mapper.OutflowAllocationMapper;
import com.financialtargets.outflow.domain.model.OutflowAllocation;
import com.financialtargets.outflow.infrastructure.entity.OutflowAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountRepository;
import com.financialtargets.outflow.infrastructure.repository.OutflowAllocationRepository;
import com.financialtargets.outflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutflowAllocationServiceImpl implements OutflowAllocationService {

    private final OutflowAllocationRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

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
    public OutflowAllocation create(OutflowAllocationCreateDTO outflowAllocationCreateDTO) throws BusinessException {
        if (!OutflowRecurrence.isValidRecurrence(outflowAllocationCreateDTO.recurrence())) {
            throw new BusinessException("Invalid recurrence for create a new outflow allocation");
        }

        OutflowAllocationEntity existingEntity = repository.findByName(outflowAllocationCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        OutflowAllocation outflowAllocation = new OutflowAllocation(outflowAllocationCreateDTO);

        if (outflowAllocationCreateDTO.appliedValue().compareTo(outflowAllocationCreateDTO.value()) >= 0) {
            outflowAllocation.setAppliedValue(outflowAllocationCreateDTO.value());
        }

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
}
