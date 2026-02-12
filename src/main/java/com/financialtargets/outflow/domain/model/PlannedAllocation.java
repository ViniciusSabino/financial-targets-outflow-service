package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PlannedAllocation {
    private Long id;
    private Long userId;
    private String accountName;
    private Long accountId;
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
}
