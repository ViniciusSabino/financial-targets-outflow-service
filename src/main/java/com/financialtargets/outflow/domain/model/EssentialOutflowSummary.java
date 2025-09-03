package com.financialtargets.outflow.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EssentialOutflowSummary {

    private BigDecimal totalAmount;

    private BigDecimal totalAmountProcessed;

    private BigDecimal totalAmountRemaining;

    private BigDecimal percentageOfIncomes;
}