package com.financialtargets.outflow.application.mapper;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowSummaryResponseDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationSummaryResponseDTO;
import com.financialtargets.outflow.domain.utils.AmountUtil;
import com.financialtargets.outflow.domain.utils.MathUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import org.springframework.stereotype.Component;

@Component
public class SummaryMapper {
    public EssentialOutflowSummaryResponseDTO toEssentialOutflowSummaryResponse(EssentialOutflowSummary essentialOutflowSummary) {
        return EssentialOutflowSummaryResponseDTO.builder()
                .totalAmount(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmount()))
                .totalAmountProcessed(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountProcessed()))
                .totalAmountRemaining(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountRemaining()))
                .percentageOfIncomes(MathUtil.toSimplePercentageFormat(essentialOutflowSummary.getPercentageOfIncomes()))
                .build();
    }

    public PlannedAllocationSummaryResponseDTO toPlannedAllocationSummaryResponse(PlannedAllocationSummary plannedAllocationSummary) {
        return PlannedAllocationSummaryResponseDTO.builder()
                .totalAmount(AmountUtil.formatAmount(plannedAllocationSummary.getTotalAmount()))
                .totalAmountProcessed(AmountUtil.formatAmount(plannedAllocationSummary.getTotalAmountProcessed()))
                .totalAmountRemaining(AmountUtil.formatAmount(plannedAllocationSummary.getTotalAmountRemaining()))
                .percentageOfIncomes(MathUtil.toSimplePercentageFormat(plannedAllocationSummary.getPercentageOfIncomes()))
                .numberOfAllocations(plannedAllocationSummary.getNumberOfAllocations())
                .percentageCurrentlyReserved(MathUtil.toSimplePercentageFormat(plannedAllocationSummary.getPercentageCurrentlyReserved()))
                .remainingPercentage(MathUtil.toSimplePercentageFormat(plannedAllocationSummary.getRemainingPercentage()))
                .build();
    }
}
