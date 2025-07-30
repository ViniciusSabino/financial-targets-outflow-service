package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;

public interface EssentialOutflowService {
    EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException;
}