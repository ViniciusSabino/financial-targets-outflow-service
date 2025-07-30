package com.financialtargets.outflow.domain.mapper;

import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.utils.AmountUtil;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EssentialOutflowMapper {

    public EssentialOutflow toModel(EssentialOutflowEntity entity) {
        EssentialOutflow essentialOutflow = new EssentialOutflow();

        essentialOutflow.setId(entity.getId());
        essentialOutflow.setName(entity.getName());
        essentialOutflow.setAccountName(entity.getAccount().getName());
        essentialOutflow.setDueDate(entity.getDueDate());
        essentialOutflow.setValue(entity.getValue());
        essentialOutflow.setPaidValue(entity.getPaidValue());
        essentialOutflow.setFullyPaid(entity.getPaidValue() >= entity.getValue());
        essentialOutflow.setNotes(entity.getNotes());
        essentialOutflow.setCreatedAt(entity.getCreatedAt());
        essentialOutflow.setUpdatedAt(entity.getUpdatedAt());
        essentialOutflow.setRecurrence(OutflowRecurrence.getRecurrenceById(entity.getId()));

        return essentialOutflow;
    }

    public EssentialOutflowDTO toDTO(EssentialOutflow essentialOutflow) {
        return EssentialOutflowDTO.builder()
                .id(essentialOutflow.getId())
                .name(essentialOutflow.getName())
                .value(AmountUtil.formatAmount(essentialOutflow.getValue()))
                .paidValue(AmountUtil.formatAmount(essentialOutflow.getPaidValue()))
                .isFullyPaid(essentialOutflow.isFullyPaid())
                .dueDate(DateUtil.formatDate(essentialOutflow.getDueDate()))
                .notes(essentialOutflow.getNotes())
                .accountName(essentialOutflow.getAccountName())
                .recurrence(essentialOutflow.getRecurrence().getLabel())
                .createdAt(DateUtil.formatDateTime(essentialOutflow.getCreatedAt()))
                .updatedAt(DateUtil.formatDateTime(essentialOutflow.getUpdatedAt()))
                .build();
    }
}
