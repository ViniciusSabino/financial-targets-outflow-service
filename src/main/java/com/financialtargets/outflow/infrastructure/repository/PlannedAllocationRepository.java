package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PlannedAllocationRepository extends JpaRepository<PlannedAllocationEntity, Long> {
    List<PlannedAllocationEntity> findByAllocationDateBetween(Instant allocationDateStart, Instant allocationDateEnd);

    PlannedAllocationEntity findByName(String name);
}