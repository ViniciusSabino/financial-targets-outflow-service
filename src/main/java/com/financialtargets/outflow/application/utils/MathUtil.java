package com.financialtargets.outflow.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class MathUtil {

    public String toSimplePercentageFormat(Float value) {
        BigDecimal decimalValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);

        return decimalValue + "%";
    }
}
