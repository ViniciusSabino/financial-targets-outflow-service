package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import com.financialtargets.outflow.infrastructure.repository.specification.PlannedAllocationSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PlannedAllocationJpaRepository extends JpaRepository<PlannedAllocationEntity, Long>, JpaSpecificationExecutor<PlannedAllocationEntity> {
    List<PlannedAllocationEntity> findByAllocationDateBetween(Instant allocationDateStart, Instant allocationDateEnd);
    PlannedAllocationEntity findByName(String name);
}