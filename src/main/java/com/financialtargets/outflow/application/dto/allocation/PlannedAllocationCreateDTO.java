package com.financialtargets.outflow.application.dto.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record PlannedAllocationCreateDTO(
        @NotNull
        @JsonProperty("name")
        String name,

        @Positive
        @JsonProperty("definedPercentage")
        BigDecimal definedPercentage,

        @Positive
        @JsonProperty("value")
        BigDecimal value,

        @NotNull
        @Min(0)
        @JsonProperty("appliedValue")
        BigDecimal appliedValue,

        @NotNull
        @JsonProperty("allocationDate")
        String allocationDate,

        @NotNull
        @Length(max = 20)
        @JsonProperty("recurrence")
        String recurrence,

        @Length(max = 100)
        @JsonProperty("notes")
        String notes,

        @NotNull
        @Positive
        @JsonProperty("accountId")
        Long accountId,

        @NotNull
        @Positive
        @JsonProperty("userId")
        Long userId
) { }