package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryResponseDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationSummaryResponseDTO;
import com.financialtargets.outflow.presentation.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Summary", description = "Endpoints from Summary Outflows")
public interface SummaryController {

    @Operation(summary = "Get Essential Outflow Summary",
            description = "Get Essential Outflow Summary",
            tags = {"Summary"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = EssentialOutflowSummaryResponseDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
            }
    )
    ResponseEntity<EssentialOutflowSummaryResponseDTO> getEssentialOutflowSummary(@RequestParam @Valid @NonNull String month, @RequestParam @NonNull @Valid String year) throws Exception;

    @Operation(summary = "Get Outflow Allocation Summary",
            description = "Get Outflow Allocation Summary",
            tags = {"Summary"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PlannedAllocationSummaryResponseDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    })
            }
    )
    ResponseEntity<PlannedAllocationSummaryResponseDTO> getOutflowAllocationSummary(@RequestParam @Valid @NonNull String month, @RequestParam @NonNull @Valid String year) throws Exception;
}
