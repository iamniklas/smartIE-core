package com.github.iamniklas.hub.rules.implementations;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.RuleCallback;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.github.iamniklas.hub.rules.models.RuleType;

import java.time.LocalDateTime;

/*
This rule executes the callback with a delay x for x times which is set in the
 */
public final class RecurringRule extends Rule {

    private int timesTriggered = 0;
    private final int maxTimes;
    long timeSinceLastTriggerInMs = 0L;
    LocalDateTime lastCycle;

    public RecurringRule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(callback, ruleName, ruleData, programInstance, RuleType.Recurring);
        maxTimes = ruleData.targetValue;
        lastCycle = LocalDateTime.now();
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {
        System.out.println("Rule Start");
        while (timesTriggered < maxTimes) {
            try {
                timesTriggered++;
                callback.onRuleTrigger();
                Thread.sleep(ruleData.timeInMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        programInstance.removeRule(this);
    }
}
