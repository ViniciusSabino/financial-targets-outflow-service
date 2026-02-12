package com.financialtargets.outflow.domain.policy.allocation.calculation;

import com.financialtargets.outflow.domain.model.PlannedAllocation;

import java.util.Objects;

public class PlannedAllocationCalculationResolver {
    public PlannedAllocationCalculationValuePolicy resolve(PlannedAllocation plannedAllocation) {
        if(Objects.isNull(plannedAllocation.getValue())) {
            return new ValueCalculationByPercentageStrategy();
        }

        return new PercentageCalculationByValueStrategy();
    }
}