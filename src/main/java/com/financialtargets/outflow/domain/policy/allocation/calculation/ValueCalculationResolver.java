package com.financialtargets.outflow.domain.policy.allocation.calculation;

import com.financialtargets.outflow.domain.model.PlannedAllocation;

import java.util.Objects;

public class ValueCalculationResolver {
    public ValueCalculationPolicy resolve(PlannedAllocation allocation) {
        return Objects.isNull(allocation.getValue()) ? new ValueCalculationByPercentageStrategy() :
                new PercentageByValueCalculationStrategy();
    }
}