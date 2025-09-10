package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.OutflowAllocationDTO;
import com.financialtargets.outflow.application.service.OutflowAllocationService;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.mapper.OutflowAllocationMapper;
import com.financialtargets.outflow.domain.model.OutflowAllocation;
import com.financialtargets.outflow.presentation.controller.OutflowAllocationController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/outflow-allocation", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class OutflowAllocationControllerImpl implements OutflowAllocationController {
    private final OutflowAllocationService service;

    @Override
    public ResponseEntity<List<OutflowAllocationDTO>> listByMonth(String month, String year) throws Exception {
        log.trace("GET /outflow-allocation - List outflow allocations by month: {} and year: {}", month, year);

        List<OutflowAllocation> outflowAllocations = service.listByMonth(Integer.parseInt(month), Integer.parseInt(year));

        return ResponseEntity.status(HttpStatus.OK).body(OutflowAllocationMapper.toDTOList(outflowAllocations));
    }

    @Override
    public ResponseEntity<OutflowAllocationDTO> create(OutflowAllocationCreateDTO outflowAllocationCreateDTO) throws BusinessException {
        log.trace("POST /outflow-allocation - Creating a new outflow allocation for user {}", outflowAllocationCreateDTO.userId());
        log.debug("Request body: {}", outflowAllocationCreateDTO);

        OutflowAllocation outflowAllocation = service.create(outflowAllocationCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(OutflowAllocationMapper.toDTO(outflowAllocation));
    }
}