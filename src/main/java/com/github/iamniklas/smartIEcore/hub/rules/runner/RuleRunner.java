package com.github.iamniklas.smartIEcore.hub.rules.runner;

import com.github.iamniklas.smartIEcore.hub.rules.Rule;

public class RuleRunner {

    private final Rule rule;
    private final Runner runner;

    public RuleRunner(Rule rule) {
        this.rule = rule;
        runner = new Runner();
    }

    public Rule getRule() {
        return rule;
    }

    public void start() { runner.start(); }
    public void stop() { runner.interrupt(); }

    private class Runner extends Thread {
        @Override
        public void run() {
            super.run();
            rule.startRule();
        }
    }
}
