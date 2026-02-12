package com.financialtargets.outflow.domain.policy.templates;

public abstract class CreateOutflowTemplate<O, EX extends Throwable> {

    public final O processUpdate(O outflow) throws EX, Exception {
        validate(outflow);
        checkExistence(outflow);
        prepareSave(outflow);
        return save(outflow);
    }

    protected abstract void validate(O outflow) throws EX;
    protected abstract void checkExistence(O outflow) throws EX;
    protected abstract void prepareSave(O outflow) throws Exception;
    protected abstract O save(O outflow);
}