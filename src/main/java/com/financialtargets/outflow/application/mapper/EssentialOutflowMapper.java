package com.financialtargets.outflow.application.mapper;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.utils.AmountUtil;
import com.financialtargets.outflow.domain.utils.DateUtil;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import org.springframework.stereotype.Component;

@Component("ApplicationEssentialOutflowMapper")
public class EssentialOutflowMapper {

    public EssentialOutflow toModel(EssentialOutflowCreateDTO essentialOutflowCreateDTO) {
        EssentialOutflow essentialOutflow = new EssentialOutflow();

        essentialOutflow.setName(essentialOutflowCreateDTO.name());
        essentialOutflow.setUserId(essentialOutflowCreateDTO.userId());
        essentialOutflow.setValue(essentialOutflowCreateDTO.value());
        essentialOutflow.setPaidValue(essentialOutflowCreateDTO.paidValue());
        essentialOutflow.setNotes(essentialOutflowCreateDTO.notes());
        essentialOutflow.setRecurrence(OutflowRecurrence.getRecurrenceByText(essentialOutflowCreateDTO.recurrence()));
        essentialOutflow.setDueDate(DateUtil.getStartOfDayByDate(essentialOutflowCreateDTO.dueDate()));
        essentialOutflow.setAccountId(essentialOutflowCreateDTO.accountId());
        essentialOutflow.setCreatedAt(DateUtil.getNowGlobalDate());
        essentialOutflow.setUpdatedAt(DateUtil.getNowGlobalDate());

        if (essentialOutflowCreateDTO.paidValue().compareTo(essentialOutflowCreateDTO.value()) >= 0) {
            essentialOutflow.setPaidValue(essentialOutflowCreateDTO.value());
        }

        return essentialOutflow;
    }

    public EssentialOutflow toModel(EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) {
        EssentialOutflow essentialOutflow = new EssentialOutflow();

        essentialOutflow.setName(essentialOutflowUpdateDTO.name());
        essentialOutflow.setValue(essentialOutflowUpdateDTO.value());
        essentialOutflow.setPaidValue(essentialOutflowUpdateDTO.paidValue());
        essentialOutflow.setNotes(essentialOutflowUpdateDTO.notes());
        essentialOutflow.setUpdatedAt(DateUtil.getNowGlobalDate());

        if (essentialOutflowUpdateDTO.paidValue().compareTo(essentialOutflowUpdateDTO.value()) >= 0) {
            essentialOutflow.setPaidValue(essentialOutflowUpdateDTO.value());
        }

        return essentialOutflow;
    }

    public EssentialOutflowResponseDTO toResponse(EssentialOutflow essentialOutflow) {
        return EssentialOutflowResponseDTO.builder().id(essentialOutflow.getId()).name(essentialOutflow.getName()).value(AmountUtil.formatAmount(essentialOutflow.getValue())).paidValue(AmountUtil.formatAmount(essentialOutflow.getPaidValue())).isFullyPaid(essentialOutflow.isFullyPaid()).dueDate(DateUtil.formatDate(essentialOutflow.getDueDate())).notes(essentialOutflow.getNotes()).accountName(essentialOutflow.getAccountName()).recurrence(essentialOutflow.getRecurrence().getLabel()).createdAt(DateUtil.formatDateTime(essentialOutflow.getCreatedAt())).updatedAt(DateUtil.formatDateTime(essentialOutflow.getUpdatedAt())).build();
    }
}
