package com.financialtargets.outflow.domain.model;

import lombok.Data;

@Data
public class EssentialOutflowSummary {

    private Float totalAmount;

    private Float totalAmountProcessed;

    private Float totalAmountRemaining;

    private Float percentageOfIncomes;
}