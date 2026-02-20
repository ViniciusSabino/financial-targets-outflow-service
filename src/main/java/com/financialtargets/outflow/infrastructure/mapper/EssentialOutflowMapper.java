package com.financialtargets.outflow.infrastructure.mapper;

import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("InfrastructureEssentialOutflowMapper")
public class EssentialOutflowMapper {
    public EssentialOutflowEntity toEntity(EssentialOutflow outflow) {
        EssentialOutflowEntity entity = new EssentialOutflowEntity();

        entity.setName(outflow.getName());
        entity.setValue(outflow.getValue());
        entity.setPaidValue(outflow.getPaidValue());
        entity.setDueDate(outflow.getDueDate());
        entity.setRecurrence(outflow.getRecurrence().name());
        entity.setNotes(outflow.getNotes());
        entity.setCreatedAt(outflow.getCreatedAt());
        entity.setUpdatedAt(outflow.getUpdatedAt());
        entity.setIsFullyPaid(outflow.isFullyPaid());

        return entity;
    }

    public EssentialOutflow toModel(EssentialOutflowEntity entity) {

        EssentialOutflow outflow = new EssentialOutflow();

        if (Objects.isNull(entity)) return outflow;

        outflow.setId(entity.getId());
        outflow.setName(entity.getName());
        outflow.setAccountName(entity.getAccount().getName());
        outflow.setValue(entity.getValue());
        outflow.setPaidValue(entity.getPaidValue());
        outflow.setFullyPaid(entity.getIsFullyPaid());
        outflow.setDueDate(entity.getDueDate());
        outflow.setRecurrence(OutflowRecurrence.getRecurrenceById(entity.getId()));
        outflow.setNotes(entity.getNotes());
        outflow.setCreatedAt(entity.getCreatedAt());
        outflow.setUpdatedAt(entity.getUpdatedAt());

        return outflow;
    }
}
