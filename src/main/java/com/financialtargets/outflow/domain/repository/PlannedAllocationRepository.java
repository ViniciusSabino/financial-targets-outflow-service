package com.financialtargets.outflow.domain.repository;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.PlannedAllocation;

import java.util.List;

public interface PlannedAllocationRepository {
    PlannedAllocation save(PlannedAllocation allocation);

    PlannedAllocation findByName(String name);

    PlannedAllocation findById(Long id) throws ResourceNotFoundException;

    List<PlannedAllocation> listByDate(DateFilter dateFilter);

    PlannedAllocation update(PlannedAllocation allocation) throws ResourceNotFoundException;
}