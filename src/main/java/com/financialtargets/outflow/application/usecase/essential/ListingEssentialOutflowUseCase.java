package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingEssentialOutflowUseCase {
    private final EssentialOutflowService service;
    private final EssentialOutflowMapper mapper;

    public List<EssentialOutflowResponseDTO> byPeriod(String month, String year) throws Exception {
        log.trace("Listing essential outflows for the period {} to {}", month, year);

        List<EssentialOutflow> outflows = service.listByDate(month, year);

        log.info("Listed {} essential outflows successfully", outflows.size());

        return outflows.stream().map(mapper::toResponse).toList();
    }
}