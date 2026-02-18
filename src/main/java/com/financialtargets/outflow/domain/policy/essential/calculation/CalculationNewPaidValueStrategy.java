package com.financialtargets.outflow.domain.policy.essential.calculation;

import com.financialtargets.outflow.domain.model.EssentialOutflow;

import java.util.Objects;

public class CalculationNewPaidValueStrategy implements PaidValueCalculationPolicy{
    @Override
    public void calculate(EssentialOutflow current, EssentialOutflow update) {
        if (Objects.isNull(update.getValue())) {
            current.setPaidValue(update.getPaidValue().compareTo(current.getValue()) >= 0 ? current.getValue() : update.getPaidValue());

        } else {
            current.setPaidValue(update.getPaidValue().compareTo(update.getValue()) >= 0 ? update.getValue() : update.getPaidValue());
        }
    }
}
