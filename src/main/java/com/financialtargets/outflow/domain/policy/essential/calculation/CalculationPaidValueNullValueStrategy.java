package com.financialtargets.outflow.domain.policy.essential.calculation;

import com.financialtargets.outflow.domain.model.EssentialOutflow;

public class CalculationPaidValueNullValueStrategy implements PaidValueCalculationPolicy {
    @Override
    public void calculate(EssentialOutflow current, EssentialOutflow update) {
        current.setPaidValue(current.getPaidValue().compareTo(update.getValue()) >= 0 ? update.getValue() : current.getPaidValue());
    }
}