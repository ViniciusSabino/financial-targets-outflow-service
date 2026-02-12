package com.financialtargets.outflow.application.dto.essential;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EssentialOutflowResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    @JsonProperty("dueDate")
    private String dueDate;

    @JsonProperty("paidValue")
    private String paidValue;

    @JsonProperty("isFullyPaid")
    private boolean isFullyPaid;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("recurrence")
    private String recurrence;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
