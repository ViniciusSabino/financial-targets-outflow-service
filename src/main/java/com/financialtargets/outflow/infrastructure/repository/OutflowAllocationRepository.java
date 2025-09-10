package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.OutflowAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OutflowAllocationRepository extends JpaRepository<OutflowAllocationEntity, Long> {
    List<OutflowAllocationEntity> findByAllocationDateBetween(Instant allocationDateStart, Instant allocationDateEnd);

    OutflowAllocationEntity findByName(String name);
}