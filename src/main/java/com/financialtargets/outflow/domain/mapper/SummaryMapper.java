package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.MathUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
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
}
