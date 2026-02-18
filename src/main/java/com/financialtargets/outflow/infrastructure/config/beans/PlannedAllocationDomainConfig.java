package com.financialtargets.outflow.infrastructure.config.beans;

import com.financialtargets.outflow.domain.policy.allocation.calculation.ValueCalculationResolver;
import com.financialtargets.outflow.domain.policy.allocation.create.PlannedAllocationCreator;
import com.financialtargets.outflow.domain.policy.allocation.update.PlannedAllocationUpdater;
import com.financialtargets.outflow.domain.repository.PlannedAllocationRepository;
import com.financialtargets.outflow.domain.service.AccountService;
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
    public ValueCalculationResolver valueCalculationResolver() {
        return new ValueCalculationResolver();
    }

    @Bean
    public PlannedAllocationCreator plannedAllocationCreator(PlannedAllocationService service, SummaryService summaryService, ValueCalculationResolver valueCalculationResolver) {
        return new PlannedAllocationCreator(service, summaryService, valueCalculationResolver);
    }

    @Bean
    public PlannedAllocationUpdater plannedAllocationUpdater(PlannedAllocationService service, AccountService accountService) {
        return new PlannedAllocationUpdater(service, accountService);
    }
}