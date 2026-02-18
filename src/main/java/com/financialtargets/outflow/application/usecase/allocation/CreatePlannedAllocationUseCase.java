package com.financialtargets.outflow.application.usecase.allocation;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationResponseDTO;
import com.financialtargets.outflow.application.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.policy.allocation.create.PlannedAllocationCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePlannedAllocationUseCase {
    private final PlannedAllocationCreator creator;
    private final PlannedAllocationMapper mapper;

    public PlannedAllocationResponseDTO create(PlannedAllocationCreateDTO plannedAllocationCreateDTO) throws Throwable {
        PlannedAllocation allocation = mapper.toModel(plannedAllocationCreateDTO);

        PlannedAllocation allocationCreated = creator.processCreate(allocation);

        log.info("Planned Allocation saved successfully, Id: {}", allocationCreated.getId());

        return mapper.toResponse(allocationCreated);
    }
}