package com.financialtargets.outflow.infrastructure.config.beans;

import com.financialtargets.outflow.domain.policy.allocation.calculation.PlannedAllocationCalculationResolver;
import com.financialtargets.outflow.domain.policy.allocation.create.PlannedAllocationCreator;
import com.financialtargets.outflow.domain.repository.PlannedAllocationRepository;
import com.financialtargets.outflow.domain.service.PlannedAllocationService;
import com.financialtargets.outflow.domain.service.SummaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlannedAllocationDomainConfig {
    @Bean
    public PlannedAllocationService plannedAllocationService(PlannedAllocationRepository repository) {
        return new PlannedAllocationService(repository);
    }

    @Bean
    public PlannedAllocationCalculationResolver plannedAllocationCalculationResolver() {
        return new PlannedAllocationCalculationResolver();
    }

    @Bean
    public PlannedAllocationCreator plannedAllocationCreator(PlannedAllocationService service, SummaryService summaryService, PlannedAllocationCalculationResolver plannedAllocationCalculationResolver) {
        return new PlannedAllocationCreator(service, summaryService, plannedAllocationCalculationResolver);
    }
}
