package com.financialtargets.outflow.infrastructure.repository.specification;

import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.infrastructure.entity.PlannedAllocationEntity;
import org.springframework.data.jpa.domain.Specification;

public class PlannedAllocationSpecification {
    public static Specification<PlannedAllocationEntity> byFilter(DateFilter filter) {
        return (root, query, cb) ->
                cb.between(root.get("allocationDate"), filter.getStartDate(), filter.getEndDate());
    }
}