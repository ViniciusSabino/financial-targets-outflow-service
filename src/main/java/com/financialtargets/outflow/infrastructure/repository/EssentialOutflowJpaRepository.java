package com.financialtargets.outflow.infrastructure.repository;

import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EssentialOutflowJpaRepository extends JpaRepository<EssentialOutflowEntity, Long>, JpaSpecificationExecutor<EssentialOutflowEntity> {
    EssentialOutflowEntity findByName(String name);
}