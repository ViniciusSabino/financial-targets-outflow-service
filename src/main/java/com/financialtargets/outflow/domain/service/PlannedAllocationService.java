package com.financialtargets.outflow.domain.service;

import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.repository.PlannedAllocationRepository;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.PlannedAllocation;

import java.util.List;

public class PlannedAllocationService {
    private final PlannedAllocationRepository repository;

    public PlannedAllocationService(PlannedAllocationRepository repository) {
        this.repository = repository;
    }

    public PlannedAllocation findByName(String name) {
        return repository.findByName(name);
    }

    public PlannedAllocation findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id);
    }

    public PlannedAllocation save(PlannedAllocation allocation) {
        return repository.save(allocation);
    }

    public List<PlannedAllocation> listByDate(String month, String year) throws Exception {
        DateFilter dateFilter = new DateFilter(month, year);

        return repository.listByDate(dateFilter);
    }

    public PlannedAllocation update(PlannedAllocation allocation) throws Exception {
        return repository.update(allocation);
    }
}