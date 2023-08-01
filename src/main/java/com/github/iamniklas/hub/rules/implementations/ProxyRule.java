package com.github.iamniklas.hub.rules.implementations;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.RuleCallback;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.github.iamniklas.hub.rules.models.RuleType;

import java.time.LocalDateTime;

//TODO Implement
public class ProxyRule extends Rule {
    public ProxyRule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        super(callback, ruleName, ruleData, programInstance, RuleType.Proxy);
    }

    @Override
    public void onRuleStart(LocalDateTime dateTimeOfExecution) {

    }
}
