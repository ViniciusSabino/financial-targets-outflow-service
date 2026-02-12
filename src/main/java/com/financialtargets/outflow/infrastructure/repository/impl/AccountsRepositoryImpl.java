package com.financialtargets.outflow.infrastructure.repository.impl;

import com.financialtargets.outflow.domain.model.Account;
import com.financialtargets.outflow.domain.repository.AccountsRepository;
import com.financialtargets.outflow.infrastructure.entity.AccountsEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountsRepositoryImpl implements AccountsRepository {
    private final AccountsJpaRepository repository;

    @Override
    public Account getAccountById(long id) {
        AccountsEntity entity = repository.getReferenceById(id);

        Account account = new Account();

        account.setId(entity.getId());
        account.setName(entity.getName());
        account.setActive(entity.isActive());

        return account;
    }
}