package com.financialtargets.outflow.application.usecase.summary;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowSummaryResponseDTO;
import com.financialtargets.outflow.application.mapper.SummaryMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.service.SummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetEssentialOutflowSummaryUseCase {
    private final SummaryService service;
    private final SummaryMapper mapper;

    public EssentialOutflowSummaryResponseDTO byPeriod(String month, String year) throws Exception {
        log.trace("Get essential outflow summary for the period {} to {}", month, year);

        EssentialOutflowSummary summary = service.getEssentialOutflowSummary(month, year);

        return mapper.toEssentialOutflowSummaryResponse(summary);
    }
}