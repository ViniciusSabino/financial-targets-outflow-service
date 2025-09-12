package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;
import com.financialtargets.outflow.domain.model.OutflowAllocationSummary;

public interface SummaryService {
    EssentialOutflowSummary getEssentialOutflowSummary(Integer month, Integer year) throws Exception;

    OutflowAllocationSummary getOutflowAllocationSummary(Integer month, Integer year) throws Exception;
}
