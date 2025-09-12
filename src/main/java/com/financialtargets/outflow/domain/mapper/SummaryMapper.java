package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryDTO;
import com.financialtargets.outflow.application.dto.OutflowAllocationSummaryDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.MathUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.OutflowAllocationSummary;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SummaryMapper {
    public EssentialOutflowSummaryDTO mapOutflowSummary(EssentialOutflowSummary essentialOutflowSummary) {
        return EssentialOutflowSummaryDTO.builder()
                .totalAmount(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmount()))
                .totalAmountProcessed(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountProcessed()))
                .totalAmountRemaining(AmountUtil.formatAmount(essentialOutflowSummary.getTotalAmountRemaining()))
                .percentageOfIncomes(MathUtil.toSimplePercentageFormat(essentialOutflowSummary.getPercentageOfIncomes()))
                .build();
    }

    public OutflowAllocationSummaryDTO mapAllocationsSummary(OutflowAllocationSummary outflowAllocationSummary) {
        return OutflowAllocationSummaryDTO.builder()
                .totalAmount(AmountUtil.formatAmount(outflowAllocationSummary.getTotalAmount()))
                .totalAmountProcessed(AmountUtil.formatAmount(outflowAllocationSummary.getTotalAmountProcessed()))
                .totalAmountRemaining(AmountUtil.formatAmount(outflowAllocationSummary.getTotalAmountRemaining()))
                .percentageOfIncomes(MathUtil.toSimplePercentageFormat(outflowAllocationSummary.getPercentageOfIncomes()))
                .numberOfAllocations(outflowAllocationSummary.getNumberOfAllocations())
                .percentageCurrentlyReserved(MathUtil.toSimplePercentageFormat(outflowAllocationSummary.getPercentageCurrentlyReserved()))
                .remainingPercentage(MathUtil.toSimplePercentageFormat(outflowAllocationSummary.getRemainingPercentage()))
                .build();
    }
}
