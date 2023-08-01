package com.github.iamniklas.hub.rules.runner;

import com.github.iamniklas.hub.rules.Rule;

import java.time.LocalDateTime;

public class RuleRunner implements Runnable {

    private final Rule rule;

    public RuleRunner(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

    @Override
    public void run() {
        rule.onRuleStart(LocalDateTime.now());
    }
}
