package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.IncomesSummaryDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.infrastructure.client.IncomesClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryServiceImpl implements SummaryService {
    private final EssentialOutflowService essentialOutflowService;

    private final IncomesClient incomesClient;

    @Override
    public EssentialOutflowSummary getEssentialOutflowSummary(String month, String year) throws Exception {
        List<EssentialOutflow> essentialsOutflows = essentialOutflowService.listByMonth(Integer.parseInt(month), Integer.parseInt(year));

        log.info("Listed {} essential outflows successfully from database", essentialsOutflows.stream().toList().size());

        IncomesSummaryDTO incomesSummary = incomesClient.getIncomesSummary(month, year);

        log.info("Get Incomes summary successfully from incomes service");

        EssentialOutflowSummary summary = new EssentialOutflowSummary();

        BigDecimal totalAmount = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getValue().add(total), BigDecimal::add);
        BigDecimal totalAmountProcessed = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getPaidValue().add(total), BigDecimal::add);

        summary.setTotalAmount(totalAmount);
        summary.setTotalAmountProcessed(totalAmountProcessed);
        summary.setTotalAmountRemaining(totalAmount.subtract(totalAmountProcessed));

        if(incomesSummary.totalReceivedValue().compareTo(new BigDecimal(0)) >= 0) {
            summary.setPercentageOfIncomes(totalAmount.divide(incomesSummary.totalReceivedValue(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }

        return summary;
    }
}
