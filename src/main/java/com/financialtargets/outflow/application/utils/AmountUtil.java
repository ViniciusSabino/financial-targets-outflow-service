package com.financialtargets.outflow.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class AmountUtil {
    public String formatAmount(BigDecimal amount) {
        if (amount == null) return "R$ 0,00";

        BigDecimal adjustedAmount = amount.setScale(2, RoundingMode.HALF_UP);

        return "R$ " + adjustedAmount.toString().replace(".", ",");
    }
}
