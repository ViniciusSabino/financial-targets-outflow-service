package com.financialtargets.outflow.domain.policy.essential.calculation;

import com.financialtargets.outflow.domain.model.EssentialOutflow;

public class CalculationPaidValueNullValueStrategy implements PaidValueCalculationPolicy {
    @Override
    public void calculate(EssentialOutflow current, EssentialOutflow update) {
        current.setPaidValue(update.getPaidValue().compareTo(current.getValue()) >= 0 ? current.getValue() : update.getPaidValue());
    }
}