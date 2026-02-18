package com.financialtargets.outflow.application.dto.incomes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record IncomesSummaryDTO(
        @JsonProperty("totalExpected")
        BigDecimal totalExpected,

        @JsonProperty("totalReceived")
        BigDecimal totalReceived,

        @JsonProperty("countExpected")
        Integer countExpected,

        @JsonProperty("countReceived")
        Integer countReceived
) { }