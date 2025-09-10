package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class OutflowAllocation {
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

    public OutflowAllocation() {}

    public OutflowAllocation(OutflowAllocationCreateDTO outflowAllocationCreateDTO) {
        this.name = outflowAllocationCreateDTO.name();
        this.definedPercentage = outflowAllocationCreateDTO.definedPercentage();
        this.value = outflowAllocationCreateDTO.value();
        this.appliedValue = outflowAllocationCreateDTO.appliedValue();
        this.recurrence = OutflowRecurrence.getRecurrenceByText(outflowAllocationCreateDTO.recurrence());
        this.notes = outflowAllocationCreateDTO.notes();
        this.createdAt = DateUtil.getNowGlobalDate();
        this.updatedAt = DateUtil.getNowGlobalDate();

        this.setAllocationDate(outflowAllocationCreateDTO.allocationDate());
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = DateUtil.getStartOfDayByDate(allocationDate);
    }

    public void setAllocationDate(Instant allocationDate) {
        this.allocationDate = allocationDate;
    }
}
