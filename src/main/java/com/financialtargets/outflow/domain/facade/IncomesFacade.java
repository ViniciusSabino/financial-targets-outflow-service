package com.financialtargets.outflow.domain.facade;

import com.financialtargets.outflow.domain.model.IncomesSummary;

public interface IncomesFacade {
    IncomesSummary getIncomesSummary(String month, String year);
}