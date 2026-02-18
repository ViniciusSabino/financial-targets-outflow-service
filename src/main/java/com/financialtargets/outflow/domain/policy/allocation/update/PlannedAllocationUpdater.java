package com.financialtargets.outflow.domain.policy.allocation.update;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.PlannedAllocationException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.Account;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.policy.templates.UpdateOutflowTemplate;
import com.financialtargets.outflow.domain.service.AccountService;
import com.financialtargets.outflow.domain.service.PlannedAllocationService;
import com.financialtargets.outflow.domain.utils.DateUtil;

import java.util.Objects;

public class PlannedAllocationUpdater extends UpdateOutflowTemplate<PlannedAllocation, PlannedAllocationException> {
    private final PlannedAllocationService service;
    private final AccountService accountService;

    public PlannedAllocationUpdater(PlannedAllocationService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @Override
    protected void validate(PlannedAllocation update) throws PlannedAllocationException {
        if (!Objects.isNull(update.getRecurrence()) && OutflowRecurrence.isInvalidRecurrence(update.getRecurrence().getLabel()))
            throw new PlannedAllocationException("Invalid recurrence for create a new planned allocation");

        Account account = accountService.getAccountById(update.getAccountId());

        if (Objects.isNull(account) || !account.isActive()) {
            throw new PlannedAllocationException("The account provided does not exist or is inactive");
        }
    }

    @Override
    protected PlannedAllocation checkExistence(PlannedAllocation update) throws ResourceNotFoundException {
        return service.findById(update.getId());
    }

    @Override
    protected void checkForDuplicates(PlannedAllocation update, PlannedAllocation current) throws PlannedAllocationException {
        if (Objects.equals(current.getName(), update.getName()) && !Objects.equals(current.getId(), update.getId())) {
            throw new PlannedAllocationException("There is already an allocation exit with that name");
        }
    }

    @Override
    protected void prepareUpdate(PlannedAllocation update, PlannedAllocation current) {
        if (!Objects.isNull(update.getAccountId()) && !update.getAccountId().equals(current.getAccountId())) {
            current.setAccountId(update.getAccountId());
        }

        if (!Objects.isNull(update.getName())) current.setName(update.getName());
        if (!Objects.isNull(update.getAppliedValue())) current.setAppliedValue(update.getAppliedValue());
        if (!Objects.isNull(update.getAllocationDate())) current.setAllocationDate(update.getAllocationDate());
        if (!Objects.isNull(update.getNotes())) current.setNotes(update.getNotes());
        if (!Objects.isNull(update.getRecurrence())) current.setRecurrence(update.getRecurrence());

        current.setUpdatedAt(DateUtil.getNowGlobalDate());
    }

    @Override
    protected PlannedAllocation update(PlannedAllocation current) throws Exception {
        return service.update(current);
    }
}