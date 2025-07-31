package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.IncomesSummaryDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.infrastructure.client.IncomesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {
    private final EssentialOutflowService essentialOutflowService;

    private final IncomesClient incomesClient;

    @Override
    public EssentialOutflowSummary getEssentialOutflowSummary(String month, String year) {
        List<EssentialOutflow> essentialsOutflows = essentialOutflowService.listByMonth(month, year);

        IncomesSummaryDTO incomesSummary = incomesClient.getIncomesSummary(month, year);

        EssentialOutflowSummary summary = new EssentialOutflowSummary();

        Float totalAmount = essentialsOutflows.stream().reduce(0.0F, (total, outflow) -> outflow.getValue() + total, Float::sum);
        Float totalAmountProcessed = essentialsOutflows.stream().reduce(0.0F, (total, outflow) -> outflow.getPaidValue() + total, Float::sum);

        summary.setTotalAmount(totalAmount);
        summary.setTotalAmountProcessed(totalAmountProcessed);
        summary.setTotalAmountRemaining(totalAmount - totalAmountProcessed);
        summary.setPercentageOfIncomes((totalAmount / incomesSummary.totalExpectedValue()) * 100);

        return summary;
    }
}
