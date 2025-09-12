package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OutflowAllocationSummaryDTO(
        @JsonProperty("totalAmount")
        String totalAmount,

        @JsonProperty("totalAmountProcessed")
        String totalAmountProcessed,

        @JsonProperty("totalAmountRemaining")
        String totalAmountRemaining,

        @JsonProperty("percentageOfIncomes")
        String percentageOfIncomes,

        @JsonProperty("numberOfAllocations")
        Integer numberOfAllocations,

        @JsonProperty("percentageCurrentlyReserved")
        String percentageCurrentlyReserved,

        @JsonProperty("remainingPercentage")
        String remainingPercentage
) { }
