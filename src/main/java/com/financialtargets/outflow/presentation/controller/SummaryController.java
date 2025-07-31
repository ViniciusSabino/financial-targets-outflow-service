package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.EssentialOutflowSummaryDTO;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface SummaryController {
    ResponseEntity<EssentialOutflowSummaryDTO> getEssentialOutflowSummary(@RequestParam @Valid @NonNull String month, @RequestParam @NonNull @Valid String year);
}
