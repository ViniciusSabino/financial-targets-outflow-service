package com.financialtargets.outflow.domain.policy.allocation.calculation;

import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValueCalculationByPercentageStrategy  implements PlannedAllocationCalculationValuePolicy{
    @Override
    public void calculate(PlannedAllocation plannedAllocation, PlannedAllocationSummary plannedAllocationSummary) {
        BigDecimal percentage = plannedAllocation.getDefinedPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        plannedAllocation.setValue(percentage.multiply(plannedAllocationSummary.getTotalAmount()));
    }
}
