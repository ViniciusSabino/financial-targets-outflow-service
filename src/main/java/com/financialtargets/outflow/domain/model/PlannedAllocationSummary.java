package com.financialtargets.outflow.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlannedAllocationSummary {
    private BigDecimal totalIncomesReceived;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountProcessed;
    private BigDecimal totalAmountRemaining;
    private BigDecimal percentageOfIncomes;
    private Integer numberOfAllocations;
    private BigDecimal percentageCurrentlyReserved;
    private BigDecimal remainingPercentage;
}