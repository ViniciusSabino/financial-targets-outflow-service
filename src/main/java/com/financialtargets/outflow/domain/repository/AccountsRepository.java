package com.financialtargets.outflow.domain.repository;

import com.financialtargets.outflow.domain.model.Account;

public interface AccountsRepository {
    public Account getAccountById(long id);
}