package com.financialtargets.outflow.domain.utils;

import java.math.BigDecimal;

public class MathUtil {
    public static String toSimplePercentageFormat(BigDecimal value) {
        return value + "%";
    }
}
