package com.financialtargets.outflow.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncomesSummary {
    private BigDecimal totalExpected;
    private BigDecimal totalReceived;
    private Integer countExpected;
    private Integer countReceived;
}