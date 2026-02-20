package com.financialtargets.outflow.domain.policy.essential.update;

import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.policy.essential.calculation.PaidValueCalculationPolicy;
import com.financialtargets.outflow.domain.policy.essential.calculation.PaidValueCalculationResolver;
import com.financialtargets.outflow.domain.policy.templates.UpdateOutflowTemplate;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;
import com.financialtargets.outflow.domain.utils.DateUtil;

import java.util.Objects;

public class EssentialOutflowUpdater extends UpdateOutflowTemplate<EssentialOutflow, EssentialOutflowException> {
    private final EssentialOutflowService service;
    private final PaidValueCalculationResolver paidValueCalculationResolver;

    public EssentialOutflowUpdater(EssentialOutflowService service, PaidValueCalculationResolver paidValueCalculationResolver) {
        this.service = service;
        this.paidValueCalculationResolver = paidValueCalculationResolver;
    }

    @Override
    protected void validate(EssentialOutflow update) {
    }

    @Override
    protected EssentialOutflow checkExistence(EssentialOutflow update) throws ResourceNotFoundException {
        return service.findById(update.getId());
    }

    @Override
    protected void checkForDuplicates(EssentialOutflow update, EssentialOutflow current) throws EssentialOutflowException {
        if (Objects.equals(current.getName(), update.getName()) && !Objects.equals(current.getId(), update.getId())) {
            throw new EssentialOutflowException("There is already an essential outflow with that name");
        }
    }

    @Override
    protected void prepareUpdate(EssentialOutflow update, EssentialOutflow current) {
        if (!Objects.isNull(update.getAccountId()))  current.setAccountId(update.getAccountId());

        PaidValueCalculationPolicy policy = paidValueCalculationResolver.resolve(update);

        policy.calculate(current, update);

        if (!Objects.isNull(update.getName())) current.setName(update.getName());
        if (!Objects.isNull(update.getValue())) current.setValue(update.getValue());
        if (!Objects.isNull(update.getDueDate())) current.setDueDate(update.getDueDate());
        if (!Objects.isNull(update.getNotes())) current.setNotes(update.getNotes());
        if (!Objects.isNull(update.getRecurrence())) current.setRecurrence(update.getRecurrence());

        current.setFullyPaid(current.getPaidValue().compareTo(current.getValue()) >= 0);

        current.setUpdatedAt(DateUtil.getNowGlobalDate());
    }

    @Override
    protected EssentialOutflow update(EssentialOutflow current) throws Exception {
        return service.update(current);
    }
}
