package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.application.dto.PlannedAllocationCreateDTO;
import com.financialtargets.outflow.application.dto.PlannedAllocationUpdateDTO;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.model.PlannedAllocation;

import java.util.List;

public interface PlannedAllocationService {
    List<PlannedAllocation> listByMonth(Integer month, Integer year) throws Exception;

    PlannedAllocation create(PlannedAllocationCreateDTO plannedAllocationCreateDTO) throws Exception;

    PlannedAllocation update(Long id, PlannedAllocationUpdateDTO plannedAllocationUpdateDTO) throws Exception;

    PlannedAllocation fullyApplied(Long id) throws BusinessException;
}
