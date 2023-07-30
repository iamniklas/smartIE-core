package com.github.iamniklas.hub.network.javalin.controller;

import com.github.iamniklas.hub.SmartIEHub;
import io.javalin.Javalin;

public abstract class Controller {
    Javalin app;
    SmartIEHub smartIEInstance;

    public Controller(Javalin app, SmartIEHub smartIEInstance) {
        this.app = app;
        this.smartIEInstance = smartIEInstance;
    }
}
