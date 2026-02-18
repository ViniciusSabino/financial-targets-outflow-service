package com.financialtargets.outflow.domain.service;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.repository.EssentialOutflowRepository;

import java.util.List;

public class EssentialOutflowService {
    private final EssentialOutflowRepository repository;

    public EssentialOutflowService(EssentialOutflowRepository repository) {
        this.repository = repository;
    }

    public EssentialOutflow save(EssentialOutflow outflow) {
        return repository.save(outflow);
    }

    public List<EssentialOutflow> listByDate(String month, String year) throws Exception {
        DateFilter dateFilter = new DateFilter(month, year);

        return repository.listByDate(dateFilter);
    }

    public EssentialOutflow findByName(String name) {
        return repository.findByName(name);
    }

    public EssentialOutflow findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id);
    }

    public EssentialOutflow update(EssentialOutflow outflow) throws ResourceNotFoundException {
        return repository.update(outflow);
    }
}