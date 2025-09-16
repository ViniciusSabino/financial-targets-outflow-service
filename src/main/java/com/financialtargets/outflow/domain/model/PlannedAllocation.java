package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.application.dto.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Data
public class PlannedAllocation {
    private Long id;

    private String accountName;

    private String name;

    private BigDecimal definedPercentage;

    private BigDecimal value;

    private BigDecimal appliedValue;

    private Boolean isFullyApplied;

    private Instant allocationDate;

    private OutflowRecurrence recurrence;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

    public PlannedAllocation() {}

    public PlannedAllocation(PlannedAllocationCreateDTO plannedAllocationCreateDTO) {
        this.name = plannedAllocationCreateDTO.name();
        this.definedPercentage = plannedAllocationCreateDTO.definedPercentage();
        this.value = plannedAllocationCreateDTO.value();
        this.appliedValue = plannedAllocationCreateDTO.appliedValue();
        this.recurrence = OutflowRecurrence.getRecurrenceByText(plannedAllocationCreateDTO.recurrence());
        this.notes = plannedAllocationCreateDTO.notes();
        this.createdAt = DateUtil.getNowGlobalDate();
        this.updatedAt = DateUtil.getNowGlobalDate();

        this.setAllocationDate(plannedAllocationCreateDTO.allocationDate());
    }

    public PlannedAllocation(PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) {
        this.name = plannedAllocationUpdateDTO.name();
        this.definedPercentage = plannedAllocationUpdateDTO.definedPercentage();
        this.value = plannedAllocationUpdateDTO.value();
        this.appliedValue = plannedAllocationUpdateDTO.appliedValue();
        this.recurrence = OutflowRecurrence.getRecurrenceByText(plannedAllocationUpdateDTO.recurrence());
        this.notes = plannedAllocationUpdateDTO.notes();
        this.updatedAt = DateUtil.getNowGlobalDate();

        if(!Objects.isNull(plannedAllocationUpdateDTO.allocationDate())) {
            this.setAllocationDate(plannedAllocationUpdateDTO.allocationDate());
        }
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = DateUtil.getStartOfDayByDate(allocationDate);
    }

    public void setAllocationDate(Instant allocationDate) {
        this.allocationDate = allocationDate;
    }
}
