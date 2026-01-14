package com.financialtargets.outflow.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlannedAllocationResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("definedPercentage")
    private String definedPercentage;

    @JsonProperty("value")
    private String value;

    @JsonProperty("appliedValue")
    private String appliedValue;

    @JsonProperty("isFullyApplied")
    private boolean isFullyApplied;

    @JsonProperty("allocationDate")
    private String allocationDate;

    @JsonProperty("recurrence")
    private String recurrence;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
