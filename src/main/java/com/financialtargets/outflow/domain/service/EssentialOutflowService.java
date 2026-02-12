package com.financialtargets.outflow.domain.service;

import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountsJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.EssentialOutflowJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.UsersJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.specification.PlannedAllocationSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class EssentialOutflowService {

    private final EssentialOutflowJpaRepository repository;
    private final UsersJpaRepository usersJpaRepository;
    private final AccountsJpaRepository accountsJpaRepository;

    public void validOutflowRecurrence(String recurrence) throws EssentialOutflowException {
        if (OutflowRecurrence.isInvalidRecurrence(recurrence)) {
            throw new EssentialOutflowException("Invalid recurrence for create or update a essential outflow");
        }
    }


    public EssentialOutflowEntity buildEssentialOutflowEntity(EssentialOutflow essentialOutflow) {
        EssentialOutflowEntity entity = new EssentialOutflowEntity();

        entity.setName(essentialOutflow.getName());
        entity.setDueDate(essentialOutflow.getDueDate());
        entity.setValue(essentialOutflow.getValue());
        entity.setPaidValue(essentialOutflow.getPaidValue());
        entity.setNotes(essentialOutflow.getNotes());
        entity.setRecurrence(essentialOutflow.getRecurrence().name());
        entity.setCreatedAt(essentialOutflow.getCreatedAt());
        entity.setUpdatedAt(essentialOutflow.getUpdatedAt());

        entity.setUser(usersJpaRepository.getReferenceById(essentialOutflow.getUserId()));
        entity.setAccount(accountsJpaRepository.getReferenceById(essentialOutflow.getAccountId()));

        return entity;
    }

    public EssentialOutflow save(EssentialOutflowEntity entity) {
        EssentialOutflow savedOutflow = repository.save(entity).toModel();

        log.info("Outflow created successfully, id: {}", savedOutflow.getId());

        return savedOutflow;
    }

    public List<EssentialOutflow> listByDate(DateFilter filter) throws Exception {
        log.info("Listing essential outflows for the period {} to {}", filter.getStartDate(), filter.getEndDate());

        List<EssentialOutflowEntity> outflows = repository.findAll(PlannedAllocationSpecification.byFilter(filter)).stream().toList();

        log.info("Listed {} essential outflows successfully", outflows.size());

        return EssentialOutflowMapper.toModelList(outflows);
    }

    public EssentialOutflow findByName(String name) {
        return repository.findByName(name).toModel();
    }

    public EssentialOutflow update(Long id, EssentialOutflow essentialOutflowUpdate) throws EssentialOutflowException, ResourceNotFoundException {
        EssentialOutflowEntity currentOutflow = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Essential outflow not found"));

        if (Objects.equals(currentOutflow.getName(), essentialOutflowUpdate.getName()) && !Objects.equals(currentOutflow.getId(), id)) {
            throw new EssentialOutflowException("There is already an essential outflow with that name");
        }

        if (Objects.isNull(essentialOutflowUpdate.getPaidValue())) {
            if (!Objects.isNull(essentialOutflowUpdate.getValue())) {
                BigDecimal paidValue = currentOutflow.getPaidValue().compareTo(essentialOutflowUpdate.getValue()) >= 0  ? essentialOutflowUpdate.getValue() : currentOutflow.getPaidValue();

                currentOutflow.setPaidValue(paidValue);
            }
        } else {
            BigDecimal paidValue;

            if (Objects.isNull(essentialOutflowUpdate.getValue())) {
                paidValue = essentialOutflowUpdate.getPaidValue().compareTo(currentOutflow.getValue()) >= 0 ? currentOutflow.getValue() : essentialOutflowUpdate.getPaidValue();

            } else {
                paidValue = essentialOutflowUpdate.getPaidValue().compareTo(essentialOutflowUpdate.getValue()) >= 0 ? essentialOutflowUpdate.getValue() : essentialOutflowUpdate.getPaidValue();
            }

            currentOutflow.setPaidValue(paidValue);
        }

        if (!Objects.isNull(essentialOutflowUpdate.getAccountId()))
            currentOutflow.setAccount(accountsJpaRepository.getReferenceById(essentialOutflowUpdate.getAccountId()));

        if (!Objects.isNull(essentialOutflowUpdate.getName())) currentOutflow.setName(essentialOutflowUpdate.getName());
        if (!Objects.isNull(essentialOutflowUpdate.getValue())) currentOutflow.setValue(essentialOutflowUpdate.getValue());
        if (!Objects.isNull(essentialOutflowUpdate.getDueDate())) currentOutflow.setDueDate(essentialOutflowUpdate.getDueDate());
        if (!Objects.isNull(essentialOutflowUpdate.getNotes())) currentOutflow.setNotes(essentialOutflowUpdate.getNotes());
        if (!Objects.isNull(essentialOutflowUpdate.getRecurrence())) currentOutflow.setRecurrence(essentialOutflowUpdate.getRecurrence().name());

        EssentialOutflow updatedOutflow = repository.save(currentOutflow).toModel();

        log.info("Outflow updated successfully, id: {}", updatedOutflow.getId());

        return updatedOutflow;
    }
}
