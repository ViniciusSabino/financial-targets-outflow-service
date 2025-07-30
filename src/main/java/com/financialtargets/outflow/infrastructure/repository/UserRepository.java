package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> { }