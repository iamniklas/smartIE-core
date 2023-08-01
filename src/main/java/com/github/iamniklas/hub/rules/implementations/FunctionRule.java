package com.github.iamniklas.hub.rules.implementations;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.RuleCallback;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.github.iamniklas.hub.rules.models.RuleType;

import java.time.LocalDateTime;

//TODO Implement
public class FunctionRule extends Rule {
    public FunctionRule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(callback, ruleName, ruleData, programInstance, RuleType.Function);
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {

    }
}
