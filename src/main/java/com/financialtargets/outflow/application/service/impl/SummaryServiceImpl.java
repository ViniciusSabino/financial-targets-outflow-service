package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.IncomesSummaryResponseDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import com.financialtargets.outflow.infrastructure.client.IncomesClient;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.PlannedAllocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryServiceImpl implements SummaryService {
    private final EssentialOutflowService essentialOutflowService;

    private final PlannedAllocationRepository plannedAllocationRepository;

    private final IncomesClient incomesClient;

    @Override
    public EssentialOutflowSummary getEssentialOutflowSummary(Integer month, Integer year) throws Exception {
        List<EssentialOutflow> essentialsOutflows = essentialOutflowService.listByMonth(month, year);

        log.info("Listed {} essential outflows successfully from database", essentialsOutflows.size());

        IncomesSummaryResponseDTO incomesSummary = incomesClient.getIncomesSummary(month.toString(), year.toString());

        log.info("Get Incomes summary successfully from incomes service");

        EssentialOutflowSummary summary = new EssentialOutflowSummary();

        BigDecimal totalAmount = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getValue().add(total), BigDecimal::add);
        BigDecimal totalAmountProcessed = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getPaidValue().add(total), BigDecimal::add);

        summary.setTotalIncomesReceived(incomesSummary.totalReceivedValue());
        summary.setTotalAmount(totalAmount);
        summary.setTotalAmountProcessed(totalAmountProcessed);
        summary.setTotalAmountRemaining(totalAmount.subtract(totalAmountProcessed));

        if(incomesSummary.totalReceivedValue().compareTo(new BigDecimal(0)) >= 0) {
            summary.setPercentageOfIncomes(totalAmount.divide(incomesSummary.totalReceivedValue(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }

        return summary;
    }

    @Override
    public PlannedAllocationSummary getPlannedAllocationSummary(Integer month, Integer year) throws Exception {
        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        EssentialOutflowSummary essentialOutflowSummary = this.getEssentialOutflowSummary(month, year);

        List<PlannedAllocationEntity> allocations = plannedAllocationRepository.findByAllocationDateBetween(start, end);

        PlannedAllocationSummary plannedAllocationSummary = new PlannedAllocationSummary();

        BigDecimal totalAmountProcessed = allocations.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getAppliedValue().add(total), BigDecimal::add);
        BigDecimal percentageCurrentlyReserved = allocations.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getDefinedPercentage().add(total), BigDecimal::add);

        plannedAllocationSummary.setTotalIncomesReceived(essentialOutflowSummary.getTotalIncomesReceived());
        plannedAllocationSummary.setTotalAmount(essentialOutflowSummary.getTotalIncomesReceived().subtract(essentialOutflowSummary.getTotalAmount()));
        plannedAllocationSummary.setTotalAmountProcessed(totalAmountProcessed);
        plannedAllocationSummary.setTotalAmountRemaining(plannedAllocationSummary.getTotalAmount().subtract(totalAmountProcessed));
        plannedAllocationSummary.setPercentageOfIncomes(new BigDecimal(100).subtract(essentialOutflowSummary.getPercentageOfIncomes()));
        plannedAllocationSummary.setNumberOfAllocations(allocations.size());
        plannedAllocationSummary.setPercentageCurrentlyReserved(percentageCurrentlyReserved);
        plannedAllocationSummary.setRemainingPercentage(new BigDecimal(100).subtract(percentageCurrentlyReserved));

        return plannedAllocationSummary;
    }
}
