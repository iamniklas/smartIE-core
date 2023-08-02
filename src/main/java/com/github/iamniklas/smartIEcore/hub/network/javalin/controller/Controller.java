package com.github.iamniklas.smartIEcore.hub.network.javalin.controller;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import io.javalin.Javalin;

public abstract class Controller {
    Javalin app;
    SmartIEHub smartIEInstance;

    public Controller(Javalin app, SmartIEHub smartIEInstance) {
        this.app = app;
        this.smartIEInstance = smartIEInstance;
    }
}
