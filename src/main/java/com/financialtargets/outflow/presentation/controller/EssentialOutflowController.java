package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import org.springframework.http.ResponseEntity;

public interface EssentialOutflowController {
    ResponseEntity<EssentialOutflowDTO> create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException;

    ResponseEntity<EssentialOutflowDTO> update(String id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException;
}