package com.financialtargets.outflow.infrastructure.mapper;

import com.financialtargets.outflow.application.dto.incomes.IncomesSummaryDTO;
import com.financialtargets.outflow.domain.model.IncomesSummary;
import org.springframework.stereotype.Component;

@Component("InfrastructureIncomesMapper")
public class IncomesMapper {
    public IncomesSummary toModel(IncomesSummaryDTO incomesSummaryDTO) {
        IncomesSummary incomesSummary = new IncomesSummary();

        incomesSummary.setCountExpected(incomesSummaryDTO.countExpected());
        incomesSummary.setCountReceived(incomesSummaryDTO.countReceived());
        incomesSummary.setTotalExpected(incomesSummaryDTO.totalExpected());
        incomesSummary.setTotalReceived(incomesSummaryDTO.totalReceived());

        return incomesSummary;
    }
}