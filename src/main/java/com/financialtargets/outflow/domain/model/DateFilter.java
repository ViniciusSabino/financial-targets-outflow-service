package com.financialtargets.outflow.domain.model;

import com.financialtargets.outflow.domain.utils.DateUtil;
import lombok.Data;

import java.time.Instant;

@Data
public class DateFilter {
    Instant startDate;
    Instant endDate;

    public DateFilter(String month, String year) throws Exception {
        this.startDate = DateUtil.getStartDateByFilter(month, year);
        this.endDate = DateUtil.getEndDateByFilter(month, year);
    }
}