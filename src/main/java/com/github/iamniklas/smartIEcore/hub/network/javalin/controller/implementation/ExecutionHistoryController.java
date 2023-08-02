package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import io.javalin.Javalin;

public class ExecutionHistoryController extends Controller {
    public ExecutionHistoryController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);

        app.get("/history/rule/all", ctx -> {

        });

        app.get("/history/device/all", ctx -> {

        });

        app.get("/history/rule/{id}", ctx -> {

        });

        app.get("/history/device/{id}", ctx -> {

        });
    }
}
