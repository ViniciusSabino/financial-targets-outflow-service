package com.financialtargets.outflow.domain.policy.templates;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;

public abstract class UpdateOutflowTemplate<O, EX extends Throwable> {

    public final O processUpdate(O outflow) throws EX, Exception {
        validate(outflow);
        O current = checkExistence(outflow);
        checkForDuplicates(current, outflow);
        prepareUpdate(current, outflow);
        return update(current);
    }

    protected abstract void validate(O outflow) throws EX;
    protected abstract O checkExistence(O outflow) throws ResourceNotFoundException;
    protected abstract void checkForDuplicates(O current, O outflow) throws EX;
    protected abstract void prepareUpdate(O current, O outflow) throws Exception;
    protected abstract O update(O current) throws Exception;
}
