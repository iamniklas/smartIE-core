package com.github.iamniklas.hub.rules;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.rules.models.RuleData;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Rule{//} extends Thread {

    private final String ruleUUID;
    private final String ruleName;

    public Rule(RuleCallback callback, String ruleName, RuleData ruleData, SmartIEHub programInstance) {
        ruleUUID = UUID.randomUUID().toString();
        this.callback = callback;
        this.ruleName = ruleName;
        this.ruleData = ruleData;
        this.programInstance = programInstance;
    }

    protected RuleCallback callback;
    protected RuleData ruleData;
    protected transient SmartIEHub programInstance;

    public String getRuleUUID() { return ruleUUID; }
    public String getRuleName() { return ruleName; }

    public void setSmartIEHub(SmartIEHub hub) { this.programInstance = hub; }

    //@Override
    public void run() {
        //TODO super.run();
        onRuleStart(LocalDateTime.now());
    }

    public abstract void onRuleStart(LocalDateTime dateTimeOfExecution);
}