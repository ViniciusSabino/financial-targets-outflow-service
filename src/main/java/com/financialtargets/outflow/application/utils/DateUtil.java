package com.financialtargets.outflow.application.utils;

import com.financialtargets.outflow.domain.contants.DateConstants;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {
    public Instant getStartOfDayByDate(String localDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return LocalDate.parse(localDate, fmt).atStartOfDay(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public Instant getNowGlobalDate() {
        return Instant.now().atZone(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public String formatDate(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }

    public String formatDateTime(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_TIME_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }

    public Instant getStartDateByFilter(String month, String year) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        LocalDate firstDateOfMonth = LocalDate.parse(String.format("01/%s/%s", month, year), fmt);

        return firstDateOfMonth.atStartOfDay(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public Instant getEndDateByFilter(String month, String year) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        LocalDate firstDateOfMonth = LocalDate.parse(String.format("01/%s/%s", month, year), fmt);
        LocalDate lastDateOfMonth = firstDateOfMonth.withDayOfMonth(firstDateOfMonth.lengthOfMonth());

        return lastDateOfMonth.atTime(23, 59, 59, 999).atZone(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }
}
