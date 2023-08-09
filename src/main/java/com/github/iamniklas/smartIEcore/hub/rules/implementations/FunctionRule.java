package com.github.iamniklas.smartIEcore.hub.rules.implementations;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.RuleCallback;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleType;

import java.time.LocalDateTime;

//TODO Implement
public class FunctionRule extends Rule {
    public FunctionRule(String uuid, RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(uuid, callback, ruleName, ruleData, programInstance, RuleType.Function);
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {

    }
}
