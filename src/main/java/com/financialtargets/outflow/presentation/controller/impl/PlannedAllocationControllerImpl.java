package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.service.PlannedAllocationService;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.presentation.controller.PlannedAllocationController;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/planned-allocation", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PlannedAllocationControllerImpl implements PlannedAllocationController {
    private final PlannedAllocationService service;

    @GetMapping
    @Override
    public ResponseEntity<List<PlannedAllocationDTO>> listByMonth(String month, String year) throws Exception {
        log.trace("GET /planned-allocation - List planned allocations by month: {} and year: {}", month, year);

        List<PlannedAllocation> plannedAllocations = service.listByMonth(Integer.parseInt(month), Integer.parseInt(year));

        return ResponseEntity.status(HttpStatus.OK).body(PlannedAllocationMapper.toDTOList(plannedAllocations));
    }

    @PostMapping
    @Override
    public ResponseEntity<PlannedAllocationDTO> create(@RequestBody PlannedAllocationCreateDTO plannedAllocationCreateDTO) throws Exception {
        log.trace("POST /planned-allocation - Creating a new planned allocation for user {}", plannedAllocationCreateDTO.userId());
        log.debug("Request body: {}", plannedAllocationCreateDTO);

        PlannedAllocation plannedAllocation = service.create(plannedAllocationCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(PlannedAllocationMapper.toDTO(plannedAllocation));
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<PlannedAllocationDTO> update(@PathVariable("id") String id, @Valid @RequestBody PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) throws Exception {
        log.trace("PATCH /planned-allocation - Update a planned allocation for id: {}", id);
        log.debug("Request body: {}", plannedAllocationUpdateDTO);

        PlannedAllocation plannedAllocation = service.update(Long.valueOf(id), plannedAllocationUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(PlannedAllocationMapper.toDTO(plannedAllocation));
    }

    @PatchMapping("{id}/fully-applied")
    @Override
    public ResponseEntity<PlannedAllocationDTO> fullyApplied(@PathVariable("id") String id) throws BusinessException {
        log.trace("PATCH /planned-allocation/{}/fully-applied - making full payment of an allocation", id);

        PlannedAllocation plannedAllocation = service.fullyApplied(Long.parseLong(id));

        return ResponseEntity.status(HttpStatus.OK).body(PlannedAllocationMapper.toDTO(plannedAllocation));
    }
}