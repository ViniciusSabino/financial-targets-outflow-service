package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EssentialOutflowSummaryDTO(
        @JsonProperty("totalAmount")
        String totalAmount,

        @JsonProperty("totalAmountProcessed")
        String totalAmountProcessed,

        @JsonProperty("totalAmountRemaining")
        String totalAmountRemaining,

        @JsonProperty("percentageOfIncomes")
        String percentageOfIncomes
) { }
