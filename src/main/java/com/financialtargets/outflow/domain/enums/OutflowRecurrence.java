package com.financialtargets.outflow.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum OutflowRecurrence {
    MONTHLY(1L, "Mensal"),
    ANNUAL(2L, "Anual");

    private final Long id;

    @Getter
    private final String label;

    OutflowRecurrence(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public static OutflowRecurrence getRecurrenceById(Long id) {
        Optional<OutflowRecurrence> filtered = Arrays.stream(OutflowRecurrence.values()).filter(i -> Objects.equals(i.getId(), id)).findFirst();

        return filtered.orElse(OutflowRecurrence.MONTHLY);
    }

    public static OutflowRecurrence getRecurrenceByText(String recurrence) {
        Optional<OutflowRecurrence> filtered = Arrays.stream(OutflowRecurrence.values()).filter(r -> Objects.equals(r.name(), recurrence)).findFirst();

        return filtered.orElse(OutflowRecurrence.MONTHLY);

    }

    public static boolean isInvalidRecurrence(String recurrence) {
        Optional<OutflowRecurrence> filtered = Arrays.stream(OutflowRecurrence.values()).filter(r -> Objects.equals(r.name(), recurrence)).findFirst();

        return filtered.isEmpty();
    }
}