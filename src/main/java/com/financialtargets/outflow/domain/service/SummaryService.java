package com.financialtargets.outflow.domain.service;

import com.financialtargets.outflow.domain.facade.IncomesFacade;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.IncomesSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SummaryService {
    private final EssentialOutflowService essentialOutflowService;
    private final PlannedAllocationService plannedAllocationService;
    private final IncomesFacade incomesFacade;

    public SummaryService(EssentialOutflowService essentialOutflowService, PlannedAllocationService plannedAllocationService, IncomesFacade incomesFacade) {
        this.essentialOutflowService = essentialOutflowService;
        this.plannedAllocationService = plannedAllocationService;
        this.incomesFacade = incomesFacade;
    }

    public EssentialOutflowSummary getEssentialOutflowSummary(String month, String year) throws Exception {
        List<EssentialOutflow> essentialsOutflows = essentialOutflowService.listByDate(month, year);

        IncomesSummary incomesSummary = incomesFacade.getIncomesSummary(month, year);

        EssentialOutflowSummary summary = new EssentialOutflowSummary();

        BigDecimal essentialOutflowsTotal = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getValue().add(total), BigDecimal::add);
        BigDecimal essentialOutflowsTotalProcessed = essentialsOutflows.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getPaidValue().add(total), BigDecimal::add);

        summary.setTotalIncomesReceived(incomesSummary.getTotalReceived());
        summary.setTotalAmount(essentialOutflowsTotal);
        summary.setTotalAmountProcessed(essentialOutflowsTotalProcessed);
        summary.setTotalAmountRemaining(essentialOutflowsTotal.subtract(essentialOutflowsTotalProcessed));

        if (incomesSummary.getTotalReceived().compareTo(new BigDecimal(0)) > 0) {
            summary.setPercentageOfIncomes(essentialOutflowsTotal.divide(incomesSummary.getTotalReceived(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }

        return summary;
    }

    public PlannedAllocationSummary getPlannedAllocationSummary(String month, String year) throws Exception {
        EssentialOutflowSummary essentialOutflowSummary = this.getEssentialOutflowSummary(month, year);

        List<PlannedAllocation> allocations = plannedAllocationService.listByDate(month, year);

        PlannedAllocationSummary plannedAllocationSummary = new PlannedAllocationSummary();

        BigDecimal allocationsTotalProcessed = allocations.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getAppliedValue().add(total), BigDecimal::add);
        BigDecimal allocationsPercentageReserved = allocations.stream().reduce(new BigDecimal(0), (total, outflow) -> outflow.getDefinedPercentage().add(total), BigDecimal::add);

        plannedAllocationSummary.setTotalIncomesReceived(essentialOutflowSummary.getTotalIncomesReceived());
        plannedAllocationSummary.setTotalAmount(essentialOutflowSummary.getTotalIncomesReceived().subtract(essentialOutflowSummary.getTotalAmount()));
        plannedAllocationSummary.setTotalAmountProcessed(allocationsTotalProcessed);
        plannedAllocationSummary.setTotalAmountRemaining(plannedAllocationSummary.getTotalAmount().subtract(allocationsTotalProcessed));
        plannedAllocationSummary.setPercentageOfIncomes(new BigDecimal(100).subtract(essentialOutflowSummary.getPercentageOfIncomes()));
        plannedAllocationSummary.setNumberOfAllocations(allocations.size());
        plannedAllocationSummary.setPercentageCurrentlyReserved(allocationsPercentageReserved);
        plannedAllocationSummary.setRemainingPercentage(new BigDecimal(100).subtract(allocationsPercentageReserved));

        return plannedAllocationSummary;
    }
}
