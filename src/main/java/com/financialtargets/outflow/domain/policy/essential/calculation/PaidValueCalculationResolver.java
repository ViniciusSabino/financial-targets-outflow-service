package com.financialtargets.outflow.domain.policy.essential.calculation;

import com.financialtargets.outflow.domain.model.EssentialOutflow;

import java.util.Objects;

public class PaidValueCalculationResolver {
    public PaidValueCalculationPolicy resolve(EssentialOutflow update) {
        return Objects.isNull(update.getValue()) ? new CalculationPaidValueNullValueStrategy() :
                new CalculationNewPaidValueStrategy();
    }
}