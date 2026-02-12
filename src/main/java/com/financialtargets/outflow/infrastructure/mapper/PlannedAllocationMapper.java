package com.financialtargets.outflow.infrastructure.mapper;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountsJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.UsersJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlannedAllocationMapper {
    private final UsersJpaRepository usersJpaRepository;
    private final AccountsJpaRepository accountsJpaRepository;

    public PlannedAllocationEntity toEntity(PlannedAllocation allocation) {
        PlannedAllocationEntity entity = new PlannedAllocationEntity();

        entity.setUser(usersJpaRepository.getReferenceById(allocation.getUserId()));
        entity.setAccount(accountsJpaRepository.getReferenceById(allocation.getAccountId()));

        entity.setName(allocation.getName());
        entity.setDefinedPercentage(allocation.getDefinedPercentage());
        entity.setValue(allocation.getValue());
        entity.setAppliedValue(allocation.getAppliedValue());
        entity.setAllocationDate(allocation.getAllocationDate());
        entity.setRecurrence(allocation.getRecurrence().name());
        entity.setNotes(allocation.getNotes());
        entity.setCreatedAt(allocation.getCreatedAt());
        entity.setUpdatedAt(allocation.getUpdatedAt());

        return entity;
    }
    
    public PlannedAllocation toModel(PlannedAllocationEntity entity) {
        PlannedAllocation allocation = new PlannedAllocation();

        allocation.setId(entity.getId());
        allocation.setName(entity.getName());
        allocation.setAccountName(entity.getAccount().getName());
        allocation.setDefinedPercentage(entity.getDefinedPercentage());
        allocation.setValue(entity.getValue());
        allocation.setAppliedValue(entity.getAppliedValue());
        allocation.setIsFullyApplied(entity.getAppliedValue().compareTo(entity.getValue()) >= 0);
        allocation.setAllocationDate(entity.getAllocationDate());
        allocation.setRecurrence(OutflowRecurrence.getRecurrenceById(entity.getId()));
        allocation.setNotes(entity.getNotes());
        allocation.setCreatedAt(entity.getCreatedAt());
        allocation.setUpdatedAt(entity.getUpdatedAt());

        return allocation;
    }
}
