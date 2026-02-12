package com.financialtargets.outflow.infrastructure.repository.impl;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.repository.PlannedAllocationRepository;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import com.financialtargets.outflow.infrastructure.mapper.PlannedAllocationMapper;
import com.financialtargets.outflow.infrastructure.repository.PlannedAllocationJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.specification.PlannedAllocationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlannedAllocationImplRepository implements PlannedAllocationRepository {

    private final PlannedAllocationJpaRepository repository;
    private final PlannedAllocationMapper mapper;

    @Override
    public PlannedAllocation save(PlannedAllocation plannedAllocation) {
        PlannedAllocationEntity entity = mapper.toEntity(plannedAllocation);

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public PlannedAllocation findByName(String name) {
        PlannedAllocationEntity entity = repository.findByName(name);

        return mapper.toModel(entity);
    }

    @Override
    public PlannedAllocation findById(Long id) throws ResourceNotFoundException {
        PlannedAllocationEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Planned allocation not found"));

        return mapper.toModel(entity);
    }

    @Override
    public List<PlannedAllocation> findByDate(DateFilter dateFilter) {
        List<PlannedAllocationEntity> allocations = repository.findAll(PlannedAllocationSpecification.byFilter(dateFilter)).stream().toList();

        return allocations.stream().map(mapper::toModel).toList();
    }

    @Override
    public PlannedAllocation update(PlannedAllocation allocation) throws ResourceNotFoundException {
        PlannedAllocationEntity entity = mapper.toEntity(allocation);

        return mapper.toModel(repository.save(entity));
    }
}