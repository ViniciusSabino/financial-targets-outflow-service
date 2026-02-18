package com.financialtargets.outflow.infrastructure.config.beans;

import com.financialtargets.outflow.domain.repository.AccountsRepository;
import com.financialtargets.outflow.domain.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountDomainConfig {
    @Bean
    public AccountService accountService(AccountsRepository repository) {
        return new AccountService(repository);
    }
}
