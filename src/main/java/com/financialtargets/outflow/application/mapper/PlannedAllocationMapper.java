package com.financialtargets.outflow.application.mapper;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationResponseDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.domain.utils.AmountUtil;
import com.financialtargets.outflow.domain.utils.DateUtil;
import com.financialtargets.outflow.domain.utils.MathUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component("ApplicationPlannedAllocationMapper")
public class PlannedAllocationMapper {

    public PlannedAllocation toModel(PlannedAllocationCreateDTO plannedAllocationCreateDTO) {
        PlannedAllocation plannedAllocation = new PlannedAllocation();

        plannedAllocation.setName(plannedAllocationCreateDTO.name());
        plannedAllocation.setUserId(plannedAllocationCreateDTO.userId());
        plannedAllocation.setAccountId(plannedAllocationCreateDTO.accountId());
        plannedAllocation.setDefinedPercentage(plannedAllocationCreateDTO.definedPercentage());
        plannedAllocation.setValue(plannedAllocationCreateDTO.value());
        plannedAllocation.setAppliedValue(plannedAllocationCreateDTO.appliedValue());
        plannedAllocation.setRecurrence(OutflowRecurrence.getRecurrenceByText(plannedAllocationCreateDTO.recurrence()));
        plannedAllocation.setNotes(plannedAllocationCreateDTO.notes());
        plannedAllocation.setCreatedAt(DateUtil.getNowGlobalDate());
        plannedAllocation.setUpdatedAt(DateUtil.getNowGlobalDate());
        plannedAllocation.setAllocationDate(DateUtil.getStartOfDayByDate(plannedAllocationCreateDTO.allocationDate()));

        return plannedAllocation;
    }

    public PlannedAllocation toModel(String id, PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) {
        PlannedAllocation plannedAllocation = new PlannedAllocation();

        plannedAllocation.setId(Long.valueOf(id));
        plannedAllocation.setName(plannedAllocationUpdateDTO.name());
        plannedAllocation.setDefinedPercentage(plannedAllocationUpdateDTO.definedPercentage());
        plannedAllocation.setValue(plannedAllocationUpdateDTO.value());
        plannedAllocation.setAppliedValue(plannedAllocationUpdateDTO.appliedValue());
        plannedAllocation.setRecurrence(OutflowRecurrence.getRecurrenceByText(plannedAllocationUpdateDTO.recurrence()));
        plannedAllocation.setNotes(plannedAllocationUpdateDTO.notes());
        plannedAllocation.setUpdatedAt(DateUtil.getNowGlobalDate());

        return plannedAllocation;
    }

    public PlannedAllocationResponseDTO toResponse(PlannedAllocation plannedAllocation) {
        return PlannedAllocationResponseDTO.builder()
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
}