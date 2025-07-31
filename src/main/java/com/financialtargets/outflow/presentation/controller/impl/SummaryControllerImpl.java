package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryDTO;
import com.financialtargets.outflow.application.service.SummaryService;
import com.financialtargets.outflow.domain.mapper.SummaryMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.presentation.controller.SummaryController;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SummaryControllerImpl implements SummaryController {
    private final SummaryService service;

    @GetMapping("/essential-outflow")
    @Override
    public ResponseEntity<EssentialOutflowSummaryDTO> getEssentialOutflowSummary(@NonNull String month, @NonNull String year) {
        EssentialOutflowSummary essentialOutflowSummary = service.getEssentialOutflowSummary(month, year);

        return ResponseEntity.status(HttpStatus.OK).body(SummaryMapper.mapOutflowSummary(essentialOutflowSummary));
    }
}
