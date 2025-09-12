package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.OutflowAllocationDTO;
import com.financialtargets.outflow.domain.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "OutflowAllocation", description = "Endpoints from Managing Outflow Allocations")
public interface OutflowAllocationController {

    @Operation(summary = "List Outflow allocations by month and year parameter",
            description = "List Outflow allocations by month and year parameter",
            tags = {"OutflowAllocation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = OutflowAllocationDTO.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<OutflowAllocationDTO>> listByMonth(String month, String year) throws Exception;

    @Operation(summary = "Create a Outflow Allocation",
            description = "Create a Outflow Allocation",
            tags = {"OutflowAllocation"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OutflowAllocationDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<OutflowAllocationDTO> create(OutflowAllocationCreateDTO outflowAllocationCreateDTO) throws Exception;

    @Operation(summary = "Making full applied of an allocation",
            description = "Making full applied of an allocation",
            tags = {"OutflowAllocation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OutflowAllocationDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<OutflowAllocationDTO> fullyApplied(String id) throws BusinessException;
}
