package com.financialtargets.outflow.infrastructure.config.beans;

import com.financialtargets.outflow.domain.facade.IncomesFacade;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;
import com.financialtargets.outflow.domain.service.PlannedAllocationService;
import com.financialtargets.outflow.domain.service.SummaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SummaryDomainConfig {
    @Bean
    public SummaryService summaryService(EssentialOutflowService essentialOutflowService, PlannedAllocationService plannedAllocationService, IncomesFacade incomesFacade) {
        return new SummaryService(essentialOutflowService, plannedAllocationService, incomesFacade);
    }
}