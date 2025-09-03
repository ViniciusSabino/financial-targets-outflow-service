package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record EssentialOutflowCreateDTO (
        @NotNull
        @JsonProperty("name")
        String name,

        @NotNull
        @Positive
        @JsonProperty("value")
        BigDecimal value,

        @NotNull
        @JsonProperty("dueDate")
        String dueDate,

        @NotNull
        @Min(0)
        @JsonProperty("paidValue")
        BigDecimal paidValue,

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
        Long userId,

        @NotNull
        @Length(max = 20)
        @JsonProperty("recurrence")
        String recurrence
) { }