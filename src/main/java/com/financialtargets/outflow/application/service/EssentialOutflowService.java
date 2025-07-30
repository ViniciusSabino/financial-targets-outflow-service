package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;

import java.util.List;

public interface EssentialOutflowService {
    List<EssentialOutflow> listByMonth(String month, String year);

    EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException;

    EssentialOutflow update(Long id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException;
}