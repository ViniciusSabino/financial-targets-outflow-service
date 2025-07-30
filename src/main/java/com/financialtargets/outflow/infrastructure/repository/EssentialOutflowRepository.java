package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EssentialOutflowRepository extends JpaRepository<EssentialOutflowEntity, Long> {
    List<EssentialOutflowEntity> findByDueDateBetween(Instant dueDateStart, Instant dueDateEnd);

    EssentialOutflowEntity findByName(String name);
}