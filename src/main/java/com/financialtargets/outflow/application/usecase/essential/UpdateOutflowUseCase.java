package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;

import com.financialtargets.outflow.domain.policy.essential.update.EssentialOutflowUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOutflowUseCase {
    private final EssentialOutflowUpdater updater;
    private final EssentialOutflowMapper mapper;

    public EssentialOutflowResponseDTO update(String id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws Exception {
        EssentialOutflow outflow = mapper.toModel(id, essentialOutflowUpdateDTO);

        EssentialOutflow outflowUpdated = updater.processUpdate(outflow);

        log.info("Essential Outflow updated successfully, Id: {}", outflowUpdated.getId());

        return mapper.toResponse(outflowUpdated);
    }
}