package com.financialtargets.outflow.application.service;

import com.financialtargets.outflow.domain.model.EssentialOutflowSummary;

public interface SummaryService {
    EssentialOutflowSummary getEssentialOutflowSummary(String month, String year);
}
