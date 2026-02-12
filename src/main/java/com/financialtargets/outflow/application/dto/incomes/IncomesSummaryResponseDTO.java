package com.financialtargets.outflow.application.dto.incomes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record IncomesSummaryResponseDTO(
        @JsonProperty("totalExpected")
        String totalExpected,

        @JsonProperty("totalExpectedValue")
        BigDecimal totalExpectedValue,

        @JsonProperty("totalReceived")
        String totalReceived,

        @JsonProperty("totalReceivedValue")
        BigDecimal totalReceivedValue,

        @JsonProperty("countExpected")
        Integer countExpected,

        @JsonProperty("countReceived")
        Integer countReceived
) {
}
