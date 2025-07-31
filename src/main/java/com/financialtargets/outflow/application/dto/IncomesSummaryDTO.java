package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record IncomesSummaryDTO(
        @JsonProperty("totalExpected")
        String totalExpected,

        @JsonProperty("totalExpectedValue")
        Float totalExpectedValue,

        @JsonProperty("totalReceived")
        String totalReceived,

        @JsonProperty("totalReceivedValue")
        Float totalReceivedValue,

        @JsonProperty("countExpected")
        Integer countExpected,

        @JsonProperty("countReceived")
        Integer countReceived
) {
}
