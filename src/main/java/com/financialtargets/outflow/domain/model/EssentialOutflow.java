package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class EssentialOutflow {
    private Long id;
    private String name;
    private Long userId;
    private BigDecimal value;
    private Instant dueDate;
    private BigDecimal paidValue;
    private boolean isFullyPaid;
    private String notes;
    private Long accountId;
    private String accountName;
    private OutflowRecurrence recurrence;
    private Instant createdAt;
    private Instant updatedAt;

    public EssentialOutflow() {}
}
