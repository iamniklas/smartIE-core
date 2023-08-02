package com.github.iamniklas.smartIEcore.hub.rules.models;

import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;

import java.time.LocalDateTime;

/*
    TODO: Single DTO for each rule
    https://www.baeldung.com/java-dto-pattern
*/
public class RuleData {
    public LocalDateTime startTime, targetTime;
    public int startValue, targetValue;
    public long timeInMs;
    public RecurringMode recurringMode;
    public int recurringModifier;
    public NumberComparator numberComparator;
    public InputDevice inputDevice;
}