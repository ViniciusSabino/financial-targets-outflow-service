package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationDTO;
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

@Tag(name = "PlannedAllocation", description = "Endpoints from Managing planned allocations")
public interface PlannedAllocationController {

    @Operation(summary = "List planned allocations by month and year parameter",
            description = "List planned allocations by month and year parameter",
            tags = {"PlannedAllocation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PlannedAllocationDTO.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<PlannedAllocationDTO>> listByMonth(String month, String year) throws Exception;

    @Operation(summary = "Create a planned allocation",
            description = "Create a planned allocation",
            tags = {"PlannedAllocation"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PlannedAllocationDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PlannedAllocationDTO> create(PlannedAllocationCreateDTO PlannedAllocationCreateDTO) throws Exception;

    @Operation(summary = "Update a planned allocation",
            description = "Update a planned allocation",
            tags = {"PlannedAllocation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PlannedAllocationUpdateDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PlannedAllocationDTO> update(String id, PlannedAllocationUpdateDTO PlannedAllocationUpdateDTO) throws Exception;

    @Operation(summary = "Making full applied of an allocation",
            description = "Making full applied of an allocation",
            tags = {"PlannedAllocation"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PlannedAllocationDTO.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PlannedAllocationDTO> fullyApplied(String id) throws BusinessException;
}
