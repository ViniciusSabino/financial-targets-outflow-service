package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryResponseDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationSummaryResponseDTO;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.domain.mapper.SummaryMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import com.financialtargets.outflow.presentation.controller.SummaryController;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class SummaryControllerImpl implements SummaryController {
    private final SummaryService service;

    @GetMapping("/essential-outflow")
    @Override
    public ResponseEntity<EssentialOutflowSummaryResponseDTO> getEssentialOutflowSummary(@NonNull String month, @NonNull String year) throws Exception {
        log.trace("GET /summary/essential-outflow - Get essential outflows summary by month: {} and year: {}", month, year);

        EssentialOutflowSummary essentialOutflowSummary = service.getEssentialOutflowSummary(Integer.parseInt(month), Integer.parseInt(year));

        return ResponseEntity.status(HttpStatus.OK).body(SummaryMapper.mapOutflowSummary(essentialOutflowSummary));
    }

    @GetMapping("/outflow-allocation")
    @Override
    public ResponseEntity<PlannedAllocationSummaryResponseDTO> getOutflowAllocationSummary(@NonNull String month, @NonNull String year) throws Exception {
        log.trace("GET /summary/outflow-allocation - Get outflow allocations summary by month: {} and year: {}", month, year);

        PlannedAllocationSummary plannedAllocationSummary = service.getPlannedAllocationSummary(Integer.parseInt(month), Integer.parseInt(year));

        return ResponseEntity.status(HttpStatus.OK).body(SummaryMapper.mapAllocationsSummary(plannedAllocationSummary));
    }
}
