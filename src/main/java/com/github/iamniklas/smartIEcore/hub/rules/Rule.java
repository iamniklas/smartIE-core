package com.github.iamniklas.smartIEcore.hub.rules;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Rule {

    private final String ruleUUID;
    private final String ruleName;
    private final RuleType ruleType;

    public Rule(String uuid, RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance, RuleType ruleType) {
        ruleUUID = uuid;
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
    public RuleType getRuleType() { return ruleType; }
    public RuleData getRuleData() { return ruleData; }

    public void setSmartIEHub(SmartIEHub hub) { this.programInstance = hub; }
    public void setCallback(RuleCallback callback) { this.callback = callback; }

    public void startRule() {
        onRuleStart(LocalDateTime.now());
    }

    protected abstract void onRuleStart(LocalDateTime dateTimeOfExecution);
}