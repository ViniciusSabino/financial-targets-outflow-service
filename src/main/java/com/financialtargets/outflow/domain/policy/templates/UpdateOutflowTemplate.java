package com.financialtargets.outflow.domain.policy.templates;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;

public abstract class UpdateOutflowTemplate<O, EX extends Throwable> {

    public final O processUpdate(O update) throws EX, Exception {
        validate(update);
        O current = checkExistence(update);
        checkForDuplicates(update, current);
        prepareUpdate(update, current);
        return update(current);
    }

    protected abstract void validate(O update) throws EX;
    protected abstract O checkExistence(O update) throws ResourceNotFoundException;
    protected abstract void checkForDuplicates(O update, O current) throws EX;
    protected abstract void prepareUpdate(O update, O current) throws Exception;
    protected abstract O update(O current) throws Exception;
}
