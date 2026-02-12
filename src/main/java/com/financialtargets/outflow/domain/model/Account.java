package com.financialtargets.outflow.domain.model;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private String name;
    private boolean isActive;

    public Account() {}
}