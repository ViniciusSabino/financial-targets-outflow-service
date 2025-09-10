package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.application.dto.OutflowAllocationCreateDTO;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.model.OutflowAllocation;

import java.util.List;

public interface OutflowAllocationService {
    List<OutflowAllocation> listByMonth(Integer month, Integer year) throws Exception;

    OutflowAllocation create(OutflowAllocationCreateDTO outflowAllocationCreateDTO) throws BusinessException;
}
