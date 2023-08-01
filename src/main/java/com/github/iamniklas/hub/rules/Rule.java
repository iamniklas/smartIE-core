package com.github.iamniklas.hub.rules;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.github.iamniklas.hub.rules.models.RuleType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Rule {

    private final String ruleUUID;
    private final String ruleName;
    private final RuleType ruleType;

    public Rule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance, RuleType ruleType) {
        ruleUUID = UUID.randomUUID().toString();
        this.callback = callback;
        this.ruleName = ruleName;
        this.ruleData = ruleData;
        this.programInstance = programInstance;
        this.ruleType = ruleType;
    }

    protected RuleCallback callback;
    protected RuleData ruleData;
    protected transient SmartIEHub programInstance;

    public String getRuleUUID() { return ruleUUID; }
    public String getRuleName() { return ruleName; }

    public void setSmartIEHub(SmartIEHub hub) { this.programInstance = hub; }

    //@Override
    public void run() {
        //super.run();
        onRuleStart(LocalDateTime.now());
    }

    public abstract void onRuleStart(LocalDateTime dateTimeOfExecution);
}