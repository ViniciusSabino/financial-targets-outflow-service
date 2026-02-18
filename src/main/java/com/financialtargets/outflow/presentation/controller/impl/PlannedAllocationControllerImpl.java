package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationResponseDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.usecase.allocation.CreatePlannedAllocationUseCase;
import com.financialtargets.outflow.application.usecase.allocation.ListingPlannedAllocationUseCase;
import com.financialtargets.outflow.application.usecase.allocation.UpdatePlannedAllocationUseCase;
import com.financialtargets.outflow.presentation.controller.PlannedAllocationController;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/planned-allocation", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PlannedAllocationControllerImpl implements PlannedAllocationController {
    private final CreatePlannedAllocationUseCase createCase;
    private final ListingPlannedAllocationUseCase listingCase;
    private final UpdatePlannedAllocationUseCase updateCase;

    @GetMapping
    @Override
    public ResponseEntity<List<PlannedAllocationResponseDTO>> listByMonth(@RequestParam @Valid @NonNull String month, @RequestParam @Valid @NonNull String year) throws Exception {
        log.trace("GET /planned-allocation - List planned allocations by month: {} and year: {}", month, year);

        List<PlannedAllocationResponseDTO> allocations = listingCase.byPeriod(month, year);

        return ResponseEntity.status(HttpStatus.OK).body(allocations);
    }

    @PostMapping
    @Override
    public ResponseEntity<PlannedAllocationResponseDTO> create(@RequestBody PlannedAllocationCreateDTO plannedAllocationCreateDTO) throws Throwable {
        log.trace("POST /planned-allocation - Creating a new planned allocation for user {}", plannedAllocationCreateDTO.userId());
        log.debug("Request body: {}", plannedAllocationCreateDTO);

        PlannedAllocationResponseDTO allocation = createCase.create(plannedAllocationCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(allocation);
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<PlannedAllocationResponseDTO> update(@PathVariable("id") String id, @Valid @RequestBody PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) throws Exception {
        log.trace("PATCH /planned-allocation - Update a planned allocation for id: {}", id);
        log.debug("Request body: {}", plannedAllocationUpdateDTO);

        PlannedAllocationResponseDTO allocation = updateCase.update(id, plannedAllocationUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(allocation);
    }
}