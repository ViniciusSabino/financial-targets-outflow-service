package com.financialtargets.outflow.domain.policy.allocation.calculation;

import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;

public interface ValueCalculationPolicy {
    void calculate(PlannedAllocation plannedAllocation, PlannedAllocationSummary plannedAllocationSummary);
}