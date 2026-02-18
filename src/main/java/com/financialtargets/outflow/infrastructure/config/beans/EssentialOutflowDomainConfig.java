package com.financialtargets.outflow.infrastructure.config.beans;

import com.financialtargets.outflow.domain.policy.essential.calculation.PaidValueCalculationResolver;
import com.financialtargets.outflow.domain.policy.essential.create.EssentialOutflowCreator;
import com.financialtargets.outflow.domain.policy.essential.update.EssentialOutflowUpdater;
import com.financialtargets.outflow.domain.repository.EssentialOutflowRepository;
import com.financialtargets.outflow.domain.service.EssentialOutflowService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EssentialOutflowDomainConfig {
    @Bean
    public EssentialOutflowService essentialOutflowService(EssentialOutflowRepository repository) {
        return new EssentialOutflowService(repository);
    }

    @Bean
    public PaidValueCalculationResolver paidValueCalculationResolver() {
        return new PaidValueCalculationResolver();
    }

    @Bean
    public EssentialOutflowCreator essentialOutflowCreator(EssentialOutflowService service) {
        return new EssentialOutflowCreator(service);
    }

    @Bean
    public EssentialOutflowUpdater essentialOutflowUpdater(EssentialOutflowService service, PaidValueCalculationResolver paidValueCalculationResolver) {
        return new EssentialOutflowUpdater(service, paidValueCalculationResolver);
    }
}