package com.github.iamniklas.smartIEcore.hub.network.javalin;

import java.time.LocalDateTime;

public class ExecutionEvent<T> {
    private final LocalDateTime timeOfExecution;
    private final T affectedObject;

    public ExecutionEvent(LocalDateTime timeOfExecution, T affectedObject) {
        this.timeOfExecution = timeOfExecution;
        this.affectedObject = affectedObject;
    }

    public LocalDateTime getTimeOfExecution() { return timeOfExecution; }
    public T getAffectedObject() { return affectedObject; }
}
