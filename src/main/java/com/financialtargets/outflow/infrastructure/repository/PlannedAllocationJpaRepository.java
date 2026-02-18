package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedAllocationJpaRepository extends JpaRepository<PlannedAllocationEntity, Long>, JpaSpecificationExecutor<PlannedAllocationEntity> {
    PlannedAllocationEntity findByName(String name);
}