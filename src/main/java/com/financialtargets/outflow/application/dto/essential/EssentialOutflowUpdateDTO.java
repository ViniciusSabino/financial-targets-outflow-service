package com.financialtargets.outflow.application.dto.essential;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record EssentialOutflowUpdateDTO(
        @JsonProperty("name")
        String name,

        @Positive
        @JsonProperty("value")
        BigDecimal value,

        @Min(0)
        @JsonProperty("paidValue")
        BigDecimal paidValue,

        @Length(max = 100)
        @JsonProperty("notes")
        String notes,

        @Positive
        @JsonProperty("accountId")
        Long accountId,

        @Length(max = 20)
        @JsonProperty("recurrence")
        String recurrence
) { }