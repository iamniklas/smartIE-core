package com.github.iamniklas.hub.rules.implementations;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.RuleCallback;
import com.github.iamniklas.hub.rules.models.NumberComparator;
import com.github.iamniklas.hub.rules.models.RecurringMode;
import com.github.iamniklas.hub.rules.models.RuleData;

import java.time.LocalDateTime;

public class ValueComparisonRule extends Rule {


    public ValueComparisonRule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(callback, ruleName, ruleData, programInstance);
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {
        while (true) {
            synchronized (ruleData.inputDevice.getSensorValue()) {
                while (NumberComparator.compare(ruleData.numberComparator,
                        ruleData.inputDevice.getSensorValue(),
                        ruleData.targetValue)) {
                    try {
                        callback.onRuleTrigger();
                        Thread.sleep(RecurringMode.getRecurringTimeInMillis(ruleData.recurringMode, ruleData.recurringModifier));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
