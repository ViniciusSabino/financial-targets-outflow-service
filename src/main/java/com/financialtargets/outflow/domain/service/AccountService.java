package com.financialtargets.outflow.domain.service;

import com.financialtargets.outflow.domain.model.Account;
import com.financialtargets.outflow.domain.repository.AccountsRepository;

public class AccountService {
    private final AccountsRepository repository;

    public AccountService(AccountsRepository repository) {
        this.repository = repository;
    }

    public Account getAccountById(long id) {
        return repository.getAccountById(id);
    }
}