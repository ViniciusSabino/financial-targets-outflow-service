package com.financialtargets.outflow.presentation.controller;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EssentialOutflowController {
    ResponseEntity<List<EssentialOutflowDTO>> listByMonth(String month, String year);

    ResponseEntity<EssentialOutflowDTO> create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException;

    ResponseEntity<EssentialOutflowDTO> update(String id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException, NotFoundException;
}