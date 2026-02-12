package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateEssentialOutflowUseCase {

    private final EssentialOutflowService service;

    public EssentialOutflowResponseDTO create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException {
        service.validOutflowRecurrence(essentialOutflowCreateDTO.recurrence());

        if(!Objects.isNull(service.findByName(essentialOutflowCreateDTO.name()))) {
            throw new EssentialOutflowException("There is already an essential outflow with that name");
        }

        EssentialOutflow essentialOutflow = new EssentialOutflow(essentialOutflowCreateDTO);

        EssentialOutflow savedEssentialOutflow = service.save(service.buildEssentialOutflowEntity(essentialOutflow));

        return EssentialOutflowMapper.toResponse(savedEssentialOutflow);
    }
}