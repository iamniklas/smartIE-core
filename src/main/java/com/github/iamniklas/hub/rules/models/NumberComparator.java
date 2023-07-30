package com.github.iamniklas.hub.rules.models;

public enum NumberComparator {
    GREATER, GREATER_EQUALS, EQUALS, LESS_EQUALS, LESS;

    public static boolean compare(NumberComparator comparator, double a, double b) {
        switch (comparator) {
            case GREATER: return a > b;
            case GREATER_EQUALS: return a >= b;
            case EQUALS: return a == b;
            case LESS_EQUALS: return a <= b;
            case LESS: return a < b;
            default: return false;
        }
    }
}
