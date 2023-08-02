package com.github.iamniklas.smartIEcore.hub.rules.models;

import java.time.LocalDateTime;

public enum RecurringMode {
    NON_BLOCKING, MILLISECONDLY, SECONDLY, MINUTELY, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY;

    public static LocalDateTime getRecurringDateTime(LocalDateTime localDateTime, RecurringMode recurringMode, int recurringModifier) {
        switch (recurringMode) {
            case MILLISECONDLY: return localDateTime.plusNanos(recurringModifier * 1000000L);
            case SECONDLY: return localDateTime.plusSeconds(recurringModifier);
            case MINUTELY: return localDateTime.plusMinutes(recurringModifier);
            case HOURLY: return localDateTime.plusHours(recurringModifier);
            case DAILY: return localDateTime.plusDays(recurringModifier);
            case WEEKLY: return localDateTime.plusWeeks(recurringModifier);
            case MONTHLY: return localDateTime.plusMonths(recurringModifier);
            case YEARLY: return localDateTime.plusYears(recurringModifier);
            case NON_BLOCKING: return localDateTime;
        }

        return localDateTime;
    }

    public static long getRecurringTimeInMillis(RecurringMode recurringMode, long recurringModifier) {
        switch (recurringMode) {
            case MILLISECONDLY: return recurringModifier;
            case SECONDLY: return getRecurringTimeInMillis(RecurringMode.MILLISECONDLY, recurringModifier * 1000);
            case MINUTELY: return getRecurringTimeInMillis(RecurringMode.SECONDLY, recurringModifier * 60);
            case HOURLY: return getRecurringTimeInMillis(RecurringMode.MINUTELY, recurringModifier * 60);
            case DAILY: return getRecurringTimeInMillis(RecurringMode.HOURLY, recurringModifier * 24);
            case WEEKLY: return getRecurringTimeInMillis(RecurringMode.DAILY, recurringModifier * 7);
            case MONTHLY: return getRecurringTimeInMillis(RecurringMode.DAILY, recurringModifier * 30);
            case YEARLY: return getRecurringTimeInMillis(RecurringMode.DAILY, recurringModifier * 365);
            default: return 0;
        }
    }
}