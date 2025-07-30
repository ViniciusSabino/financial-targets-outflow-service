package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record EssentialOutflowUpdateDTO(
        @JsonProperty("name")
        String name,

        @Positive
        @JsonProperty("value")
        Float value,

        @JsonProperty("dueDate")
        String dueDate,

        @Min(0)
        @JsonProperty("paidValue")
        Float paidValue,

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