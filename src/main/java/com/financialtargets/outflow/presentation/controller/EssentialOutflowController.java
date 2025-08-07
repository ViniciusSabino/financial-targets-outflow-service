package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EssentialOutflowController {
    ResponseEntity<List<EssentialOutflowDTO>> listByMonth(String month, String year);

    ResponseEntity<EssentialOutflowDTO> create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws BusinessException;

    ResponseEntity<EssentialOutflowDTO> update(String id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws BusinessException, NotFoundException;
}