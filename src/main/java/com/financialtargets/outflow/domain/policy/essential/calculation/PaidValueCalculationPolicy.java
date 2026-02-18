package com.financialtargets.outflow.domain.policy.essential.calculation;

import com.financialtargets.outflow.domain.model.EssentialOutflow;

public interface PaidValueCalculationPolicy {
    void calculate(EssentialOutflow current, EssentialOutflow update);
}
