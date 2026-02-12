package com.financialtargets.outflow.application.usecase.allocation;

import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationResponseDTO;
import com.financialtargets.outflow.application.dto.allocation.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.application.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.policy.allocation.update.PlannedAllocationUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdatePlannedAllocationUseCase {
    private final PlannedAllocationUpdater updater;
    private final PlannedAllocationMapper mapper;

    public PlannedAllocationResponseDTO update(String id, PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) throws Exception {
        PlannedAllocation allocation = mapper.toModel(id, plannedAllocationUpdateDTO);

        PlannedAllocation allocationUpdated = updater.processUpdate(allocation);

        log.info("Planned Allocation updated successfully, Id: {}", allocationUpdated.getId());

        return mapper.toResponse(allocationUpdated);
    }
}