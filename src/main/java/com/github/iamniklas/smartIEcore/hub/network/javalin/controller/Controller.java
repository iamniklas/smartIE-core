package com.github.iamniklas.smartIEcore.hub.network.javalin.controller;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.google.gson.Gson;
import io.javalin.Javalin;

public abstract class Controller {
    Javalin app;
    SmartIEHub smartIEInstance;
    Gson gson;

    public Controller(Javalin app, SmartIEHub smartIEInstance, Gson gson) {
        this.app = app;
        this.smartIEInstance = smartIEInstance;
        this.gson = gson;
    }
}
