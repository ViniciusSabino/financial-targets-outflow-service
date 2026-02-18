package com.financialtargets.outflow.infrastructure.repository.specification;

import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import org.springframework.data.jpa.domain.Specification;

public class EssentialOutflowSpecification {
    public static Specification<EssentialOutflowEntity> byFilter(DateFilter filter) {
        return (root, query, cb) ->
                cb.between(root.get("date"), filter.getStartDate(), filter.getEndDate());
    }
}
