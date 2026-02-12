package com.financialtargets.outflow.domain.utils;

import com.financialtargets.outflow.domain.contants.DateConstants;
import com.financialtargets.outflow.domain.exception.BadRequestException;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static Instant getStartOfDayByDate(String localDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return LocalDate.parse(localDate, fmt).atStartOfDay(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public static Instant getNowGlobalDate() {
        return Instant.now().atZone(DateConstants.DEFAULT_TIME_ZONE).toInstant();
    }

    public static LocalDate getNowLocalDate() {
        return LocalDate.now();
    }

    public static String formatDate(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }

    public static String formatDateTime(Instant date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_TIME_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

        return fmt.format(date);
    }

    public static Instant getStartDateByFilter(String month, String year) throws Exception {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

            String adjustedMonth = month.length() < 2 ? "0" + month : month;

            LocalDate firstDateOfMonth = LocalDate.parse(String.format("01/%s/%s", adjustedMonth, year), fmt);

            return firstDateOfMonth.atStartOfDay(DateConstants.DEFAULT_TIME_ZONE).toInstant();
        } catch (DateTimeException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static Instant getEndDateByFilter(String month, String year) throws Exception {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateConstants.DEFAULT_DATE_FORMAT).withZone(DateConstants.DEFAULT_TIME_ZONE);

            String adjustedMonth = month.length() < 2 ? "0" + month : month;

            LocalDate firstDateOfMonth = LocalDate.parse(String.format("01/%s/%s", adjustedMonth, year), fmt);
            LocalDate lastDateOfMonth = firstDateOfMonth.withDayOfMonth(firstDateOfMonth.lengthOfMonth());

            return lastDateOfMonth.atTime(23, 59, 59, 999).atZone(DateConstants.DEFAULT_TIME_ZONE).toInstant();
        } catch (DateTimeException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }
}
