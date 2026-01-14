package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryResponseDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationSummaryResponseDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.MathUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SummaryMapper {
    public EssentialOutflowSummaryResponseDTO mapOutflowSummary(EssentialOutflowSummary essentialOutflowSummary) {
        return EssentialOutflowSummaryResponseDTO.builder()
                .totalAmount(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmount()))
                .totalAmountProcessed(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountProcessed()))
                .totalAmountRemaining(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountRemaining()))
                .percentageOfIncomes(MathUtil.toSimplePercentageFormat(essentialOutflowSummary.getPercentageOfIncomes()))
                .build();
    }

    public PlannedAllocationSummaryResponseDTO mapAllocationsSummary(PlannedAllocationSummary plannedAllocationSummary) {
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
