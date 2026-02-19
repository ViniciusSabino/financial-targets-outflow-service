package com.financialtargets.outflow.domain.policy.essential.create;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.policy.templates.CreateOutflowTemplate;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;

import java.util.Objects;

public class EssentialOutflowCreator extends CreateOutflowTemplate<EssentialOutflow, EssentialOutflowException> {
    private final EssentialOutflowService service;

    public EssentialOutflowCreator(EssentialOutflowService essentialOutflowService) {
        this.service = essentialOutflowService;
    }

    @Override
    protected void validate(EssentialOutflow outflow) throws EssentialOutflowException {
        if (OutflowRecurrence.isInvalidRecurrence(outflow.getRecurrence().name())) {
            throw new EssentialOutflowException("Invalid recurrence for create or update a essential outflow");
        }
    }

    @Override
    protected void checkExistence(EssentialOutflow outflow) throws EssentialOutflowException {
        EssentialOutflow existingOutflow = service.findByName(outflow.getName());

        if (existingOutflow.getId() != null) {
            throw new EssentialOutflowException("There is already an essential outflow with that name");
        }
    }

    @Override
    protected void prepareSave(EssentialOutflow outflow) {
    }

    @Override
    protected EssentialOutflow save(EssentialOutflow outflow) {
        return service.save(outflow);
    }
}
