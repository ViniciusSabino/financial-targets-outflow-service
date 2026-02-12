package com.financialtargets.outflow.domain.policy.allocation.calculation;

import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageCalculationByValueStrategy implements PlannedAllocationCalculationValuePolicy {
    @Override
    public void calculate(PlannedAllocation plannedAllocation, PlannedAllocationSummary plannedAllocationSummary) {
        BigDecimal percentage = plannedAllocationSummary.getTotalAmount().divide(plannedAllocation.getValue(), 2, RoundingMode.HALF_UP);

        plannedAllocation.setDefinedPercentage(percentage);
    }
}