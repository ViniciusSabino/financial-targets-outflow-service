package com.financialtargets.outflow.domain.repository;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.EssentialOutflow;

import java.util.List;

public interface EssentialOutflowRepository {
    EssentialOutflow save(EssentialOutflow outflow);

    EssentialOutflow findByName(String name);

    EssentialOutflow findById(Long id) throws ResourceNotFoundException;

    List<EssentialOutflow> listByDate(DateFilter dateFilter);

    EssentialOutflow update(EssentialOutflow outflow) throws ResourceNotFoundException;
}