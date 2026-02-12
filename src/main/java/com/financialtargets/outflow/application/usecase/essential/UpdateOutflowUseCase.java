package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOutflowUseCase implements UpdateOutflowUseCase {
    private final EssentialOutflowService service;
    @Override
    public EssentialOutflowResponseDTO update(Long id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException, ResourceNotFoundException {
        service.validOutflowRecurrence(essentialOutflowUpdateDTO.recurrence());

        EssentialOutflow essentialOutflowUpdate = new EssentialOutflow(essentialOutflowUpdateDTO);

        EssentialOutflow essentialOutflow = service.update(id, essentialOutflowUpdate);

        return EssentialOutflowMapper.toResponse(essentialOutflow);
    }
}