package com.financialtargets.outflow.application.dto.allocation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record PlannedAllocationUpdateDTO(
        @JsonProperty("name")
        String name,

        @Positive
        @JsonProperty("accountId")
        Long accountId,

        @JsonIgnore
        @Positive
        @JsonProperty("definedPercentage")
        BigDecimal definedPercentage,

        @JsonIgnore
        @Positive
        @JsonProperty("value")
        BigDecimal value,

        @Min(0)
        @JsonProperty("appliedValue")
        BigDecimal appliedValue,

        @Length(max = 20)
        @JsonProperty("recurrence")
        String recurrence,

        @Length(max = 100)
        @JsonProperty("notes")
        String notes
) { }
