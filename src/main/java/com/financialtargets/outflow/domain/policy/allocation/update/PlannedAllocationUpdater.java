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
    protected void validate(PlannedAllocation allocation) throws PlannedAllocationException {
        if (!Objects.isNull(allocation.getRecurrence()) && OutflowRecurrence.isInvalidRecurrence(allocation.getRecurrence().getLabel()))
            throw new PlannedAllocationException("Invalid recurrence for create a new planned allocation");

        Account account = accountService.getAccountById(allocation.getAccountId());

        if(Objects.isNull(account) || !account.isActive()) {
            throw new PlannedAllocationException("The account provided does not exist or is inactive");
        }
    }

    @Override
    protected PlannedAllocation checkExistence(PlannedAllocation allocation) throws ResourceNotFoundException {
        return service.findById(allocation.getId());
    }

    @Override
    protected void checkForDuplicates(PlannedAllocation allocation, PlannedAllocation current) throws PlannedAllocationException {
        if (Objects.equals(current.getName(), allocation.getName()) && !Objects.equals(current.getId(), allocation.getId())) {
            throw new PlannedAllocationException("There is already an allocation exit with that name");
        }
    }

    @Override
    protected void prepareUpdate(PlannedAllocation allocation, PlannedAllocation current) {
        if(!Objects.isNull(allocation.getAccountId()) && !allocation.getAccountId().equals(current.getAccountId())) {
            current.setAccountId(allocation.getAccountId());
        }

        if (!Objects.isNull(allocation.getName())) current.setName(allocation.getName());
        if (!Objects.isNull(allocation.getAppliedValue())) current.setAppliedValue(allocation.getAppliedValue());
        if (!Objects.isNull(allocation.getAllocationDate())) current.setAllocationDate(allocation.getAllocationDate());
        if (!Objects.isNull(allocation.getNotes())) current.setNotes(allocation.getNotes());
        if (!Objects.isNull(allocation.getRecurrence())) current.setRecurrence(allocation.getRecurrence());

        current.setUpdatedAt(DateUtil.getNowGlobalDate());
    }

    @Override
    protected PlannedAllocation update(PlannedAllocation current) throws Exception {
        return service.update(current);
    }
}