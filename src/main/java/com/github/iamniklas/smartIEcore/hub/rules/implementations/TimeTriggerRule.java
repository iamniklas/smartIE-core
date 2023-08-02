package com.github.iamniklas.smartIEcore.hub.rules.implementations;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.RuleCallback;
import com.github.iamniklas.smartIEcore.hub.rules.models.RecurringMode;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleType;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeTriggerRule extends Rule {
    private final RecurringMode recurring;

    public TimeTriggerRule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(callback, ruleName, ruleData, programInstance, RuleType.Trigger);
        recurring = ruleData.recurringMode;
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {
        LocalDateTime currentTime;
        LocalDateTime nextTriggerTime = ruleData.targetTime;

        do {
            currentTime = LocalDateTime.now();

            if (currentTime.isAfter(nextTriggerTime) && recurring != null) {
                nextTriggerTime = nextTriggerTime.plusDays(1);
            }

            Duration durationUntilNextTrigger = Duration.between(currentTime, nextTriggerTime);

            if (durationUntilNextTrigger.isNegative()) {
                nextTriggerTime = nextTriggerTime.plusDays(1);
                continue;
            }

            long delayMillis = durationUntilNextTrigger.toMillis();
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            callback.onRuleTrigger();

            if (recurring != null) {
                nextTriggerTime = RecurringMode.getRecurringDateTime(nextTriggerTime, ruleData.recurringMode, ruleData.recurringModifier);
            }
        } while (recurring != null);

        programInstance.removeRule(this);
    }
}
