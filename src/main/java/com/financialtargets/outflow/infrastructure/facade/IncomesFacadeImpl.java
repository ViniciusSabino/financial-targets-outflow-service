package com.financialtargets.outflow.infrastructure.facade;

import com.financialtargets.outflow.application.dto.incomes.IncomesSummaryDTO;
import com.financialtargets.outflow.domain.facade.IncomesFacade;
import com.financialtargets.outflow.domain.model.IncomesSummary;
import com.financialtargets.outflow.infrastructure.client.IncomesClient;
import com.financialtargets.outflow.infrastructure.mapper.IncomesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomesFacadeImpl implements IncomesFacade {
    private final IncomesClient client;
    private final IncomesMapper mapper;

    @Override
    public IncomesSummary getIncomesSummary(String month, String year) {
        IncomesSummaryDTO incomesSummaryResponse = client.getIncomesSummary(month, year);

        return mapper.toModel(incomesSummaryResponse);
    }
}
