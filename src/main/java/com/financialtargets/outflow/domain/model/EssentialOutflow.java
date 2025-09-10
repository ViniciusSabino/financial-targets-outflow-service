package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Data
public class EssentialOutflow {
    private Long id;

    private String name;

    private BigDecimal value;

    private Instant dueDate;

    private BigDecimal paidValue;

    private boolean isFullyPaid;

    private String notes;

    private String accountName;

    private OutflowRecurrence recurrence;

    private Instant createdAt;

    private Instant updatedAt;

    public EssentialOutflow() {}

    public EssentialOutflow(EssentialOutflowCreateDTO essentialOutflowCreateDTO) {
        this.name = essentialOutflowCreateDTO.name();
        this.value = essentialOutflowCreateDTO.value();
        this.paidValue = essentialOutflowCreateDTO.paidValue();
        this.notes = essentialOutflowCreateDTO.notes();
        this.recurrence = OutflowRecurrence.getRecurrenceByText(essentialOutflowCreateDTO.recurrence());
        this.createdAt = DateUtil.getNowGlobalDate();
        this.updatedAt = DateUtil.getNowGlobalDate();

        this.setDueDate(essentialOutflowCreateDTO.dueDate());
    }

    public EssentialOutflow(EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) {
        this.name = essentialOutflowUpdateDTO.name();
        this.value = essentialOutflowUpdateDTO.value();
        this.paidValue = essentialOutflowUpdateDTO.paidValue();
        this.notes = essentialOutflowUpdateDTO.notes();
        this.recurrence = OutflowRecurrence.getRecurrenceByText(essentialOutflowUpdateDTO.recurrence());
        this.updatedAt = DateUtil.getNowGlobalDate();

        if(!Objects.isNull(essentialOutflowUpdateDTO.dueDate()))
            this.setDueDate(essentialOutflowUpdateDTO.dueDate());
    }

    public void setDueDate(String dueDate) {
        this.dueDate = DateUtil.getStartOfDayByDate(dueDate);
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }
}
