package com.financialtargets.outflow.application.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class MathUtil {
    public String toSimplePercentageFormat(BigDecimal value) {
        return value + "%";
    }
}
