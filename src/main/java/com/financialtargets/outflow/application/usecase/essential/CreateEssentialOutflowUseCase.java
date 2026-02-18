package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.policy.essential.create.EssentialOutflowCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateEssentialOutflowUseCase {
    private final EssentialOutflowCreator creator;
    private final EssentialOutflowMapper mapper;

    public EssentialOutflowResponseDTO create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws Exception {
        EssentialOutflow outflow = mapper.toModel(essentialOutflowCreateDTO);

        EssentialOutflow outflowCreated = creator.processCreate(outflow);

        log.info("Essential outflow saved successfully, Id: {}", outflowCreated.getId());

        return mapper.toResponse(outflowCreated);
    }
}