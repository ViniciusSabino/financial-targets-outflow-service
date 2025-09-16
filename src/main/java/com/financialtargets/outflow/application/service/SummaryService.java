package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.PlannedAllocationSummary;

public interface SummaryService {
    EssentialOutflowSummary getEssentialOutflowSummary(Integer month, Integer year) throws Exception;

    PlannedAllocationSummary getPlannedAllocationSummary(Integer month, Integer year) throws Exception;
}