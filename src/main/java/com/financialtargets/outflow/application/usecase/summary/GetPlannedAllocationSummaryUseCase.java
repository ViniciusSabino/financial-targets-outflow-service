package com.financialtargets.outflow.application.usecase.summary;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationSummaryResponseDTO;
import com.financialtargets.outflow.application.mapper.SummaryMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import com.financialtargets.outflow.domain.service.SummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetPlannedAllocationSummaryUseCase {
    private final SummaryService service;
    private final SummaryMapper mapper;

    public PlannedAllocationSummaryResponseDTO byPeriod(String month, String year) throws Exception {
        log.trace("Get planned allocation summary for the period {} to {}", month, year);

        PlannedAllocationSummary summary = service.getPlannedAllocationSummary(month, year);

        return mapper.toPlannedAllocationSummaryResponse(summary);
    }
}