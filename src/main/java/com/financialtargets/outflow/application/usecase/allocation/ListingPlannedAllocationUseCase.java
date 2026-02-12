package com.financialtargets.outflow.application.usecase.allocation;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationResponseDTO;
import com.financialtargets.outflow.application.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.service.PlannedAllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingPlannedAllocationUseCase {
    private final PlannedAllocationService service;
    private final PlannedAllocationMapper mapper;

    public List<PlannedAllocationResponseDTO> byPeriod(String month, String year) throws Exception {
        log.trace("Listing planned allocations for the period {} to {}", month, year);

        List<PlannedAllocation> allocations = service.listByDate(month, year);

        log.info("Listed {} planned allocations successfully", allocations.size());

        return allocations.stream().map(mapper::toResponse).toList();
    }
}