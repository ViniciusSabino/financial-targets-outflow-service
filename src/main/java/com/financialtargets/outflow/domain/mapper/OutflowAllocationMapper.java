package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.OutflowAllocationDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.application.utils.MathUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.OutflowAllocation;
import com.financialtargets.outflow.infrastructure.entity.OutflowAllocationEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OutflowAllocationMapper {
    public OutflowAllocation toModel(OutflowAllocationEntity entity) {
        OutflowAllocation outflowAllocation = new OutflowAllocation();

        outflowAllocation.setId(entity.getId());
        outflowAllocation.setName(entity.getName());
        outflowAllocation.setAccountName(entity.getAccount().getName());
        outflowAllocation.setDefinedPercentage(entity.getDefinedPercentage());
        outflowAllocation.setValue(entity.getValue());
        outflowAllocation.setAppliedValue(entity.getAppliedValue());
        outflowAllocation.setIsFullyApplied(entity.getAppliedValue().compareTo(entity.getValue()) >= 0);
        outflowAllocation.setAllocationDate(entity.getAllocationDate());
        outflowAllocation.setRecurrence(OutflowRecurrence.getRecurrenceById(entity.getId()));
        outflowAllocation.setNotes(entity.getNotes());
        outflowAllocation.setCreatedAt(entity.getCreatedAt());
        outflowAllocation.setUpdatedAt(entity.getUpdatedAt());

        return outflowAllocation;
    }

    public List<OutflowAllocation> toModelList(List<OutflowAllocationEntity> entities) {
        return entities.stream().map(OutflowAllocationMapper::toModel).toList();
    }

    public OutflowAllocationDTO toDTO(OutflowAllocation outflowAllocation) {
        return OutflowAllocationDTO.builder()
                .id(outflowAllocation.getId())
                .name(outflowAllocation.getName())
                .accountName(outflowAllocation.getAccountName())
                .definedPercentage(MathUtil.toSimplePercentageFormat(outflowAllocation.getDefinedPercentage()))
                .value(AmountUtil.formatAmount(outflowAllocation.getValue()))
                .appliedValue(AmountUtil.formatAmount(outflowAllocation.getAppliedValue()))
                .isFullyApplied(outflowAllocation.getIsFullyApplied())
                .allocationDate(DateUtil.formatDate(outflowAllocation.getAllocationDate()))
                .recurrence(outflowAllocation.getRecurrence().getLabel())
                .notes(outflowAllocation.getNotes())
                .createdAt(DateUtil.formatDateTime(outflowAllocation.getCreatedAt()))
                .updatedAt(DateUtil.formatDateTime(outflowAllocation.getUpdatedAt()))
                .build();
    }

    public List<OutflowAllocationDTO> toDTOList(List<OutflowAllocation> outflowAllocations) {
        return outflowAllocations.stream().map(OutflowAllocationMapper::toDTO).toList();
    }
}