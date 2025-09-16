package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.PlannedAllocationDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.application.utils.MathUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PlannedAllocationMapper {
    public PlannedAllocation toModel(PlannedAllocationEntity entity) {
        PlannedAllocation plannedAllocation = new PlannedAllocation();

        plannedAllocation.setId(entity.getId());
        plannedAllocation.setName(entity.getName());
        plannedAllocation.setAccountName(entity.getAccount().getName());
        plannedAllocation.setDefinedPercentage(entity.getDefinedPercentage());
        plannedAllocation.setValue(entity.getValue());
        plannedAllocation.setAppliedValue(entity.getAppliedValue());
        plannedAllocation.setIsFullyApplied(entity.getAppliedValue().compareTo(entity.getValue()) >= 0);
        plannedAllocation.setAllocationDate(entity.getAllocationDate());
        plannedAllocation.setRecurrence(OutflowRecurrence.getRecurrenceById(entity.getId()));
        plannedAllocation.setNotes(entity.getNotes());
        plannedAllocation.setCreatedAt(entity.getCreatedAt());
        plannedAllocation.setUpdatedAt(entity.getUpdatedAt());

        return plannedAllocation;
    }

    public List<PlannedAllocation> toModelList(List<PlannedAllocationEntity> entities) {
        return entities.stream().map(PlannedAllocationMapper::toModel).toList();
    }

    public PlannedAllocationDTO toDTO(PlannedAllocation plannedAllocation) {
        return PlannedAllocationDTO.builder()
                .id(plannedAllocation.getId())
                .name(plannedAllocation.getName())
                .accountName(plannedAllocation.getAccountName())
                .definedPercentage(MathUtil.toSimplePercentageFormat(plannedAllocation.getDefinedPercentage()))
                .value(AmountUtil.formatAmount(plannedAllocation.getValue()))
                .appliedValue(AmountUtil.formatAmount(plannedAllocation.getAppliedValue()))
                .isFullyApplied(plannedAllocation.getIsFullyApplied())
                .allocationDate(DateUtil.formatDate(plannedAllocation.getAllocationDate()))
                .recurrence(plannedAllocation.getRecurrence().getLabel())
                .notes(plannedAllocation.getNotes())
                .createdAt(DateUtil.formatDateTime(plannedAllocation.getCreatedAt()))
                .updatedAt(DateUtil.formatDateTime(plannedAllocation.getUpdatedAt()))
                .build();
    }

    public List<PlannedAllocationDTO> toDTOList(List<PlannedAllocation> plannedAllocations) {
        return plannedAllocations.stream().map(PlannedAllocationMapper::toDTO).toList();
    }
}