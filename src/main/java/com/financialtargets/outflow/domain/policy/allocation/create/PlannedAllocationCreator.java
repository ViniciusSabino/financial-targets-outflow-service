package com.financialtargets.outflow.domain.policy.allocation.create;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.PlannedAllocationException;
import com.financialtargets.outflow.domain.model.PlannedAllocation;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;
import com.financialtargets.outflow.domain.policy.allocation.calculation.PlannedAllocationCalculationResolver;
import com.financialtargets.outflow.domain.policy.allocation.calculation.PlannedAllocationCalculationValuePolicy;
import com.financialtargets.outflow.domain.policy.templates.CreateOutflowTemplate;
import com.financialtargets.outflow.domain.service.PlannedAllocationService;
import com.financialtargets.outflow.domain.service.SummaryService;

import java.util.Objects;

public class PlannedAllocationCreator extends CreateOutflowTemplate<PlannedAllocation, PlannedAllocationException> {
    private final PlannedAllocationService service;
    private final SummaryService summaryService;
    private final PlannedAllocationCalculationResolver plannedAllocationCalculationResolver;

    public PlannedAllocationCreator(PlannedAllocationService service, SummaryService summaryService, PlannedAllocationCalculationResolver plannedAllocationCalculationResolver) {
        this.service = service;
        this.summaryService = summaryService;
        this.plannedAllocationCalculationResolver = plannedAllocationCalculationResolver;
    }

    @Override
    protected void validate(PlannedAllocation allocation) throws PlannedAllocationException {
        if (OutflowRecurrence.isInvalidRecurrence(allocation.getRecurrence().getLabel()))
            throw new PlannedAllocationException("Invalid recurrence for create or update a planned allocation");

        if (Objects.isNull(allocation.getDefinedPercentage()) && Objects.isNull(allocation.getValue()))
            throw new PlannedAllocationException("Choose between setting \"value\" or \"definedPercentage\"");

        if (!Objects.isNull(allocation.getDefinedPercentage()) && !Objects.isNull(allocation.getValue()))
            throw new PlannedAllocationException("Choose between setting \"value\" or \"definedPercentage\"");
    }

    @Override
    protected void checkExistence(PlannedAllocation allocation) throws PlannedAllocationException {
        if(!Objects.isNull(service.findByName(allocation.getName()))) {
            throw new PlannedAllocationException("There is already an planned allocation with that name");
        }
    }

    @Override
    protected void prepareSave(PlannedAllocation allocation) throws Exception {
        PlannedAllocationSummary plannedAllocationSummary = summaryService.getCurrentPlannedAllocationSummary();

        PlannedAllocationCalculationValuePolicy policyCalculate = plannedAllocationCalculationResolver.resolve(allocation);

        policyCalculate.calculate(allocation, plannedAllocationSummary);
    }

    @Override
    protected PlannedAllocation save(PlannedAllocation allocation) {
        return service.save(allocation);
    }
}