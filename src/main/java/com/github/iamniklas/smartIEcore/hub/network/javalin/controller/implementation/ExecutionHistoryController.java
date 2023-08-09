package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import com.google.gson.Gson;
import io.javalin.Javalin;

public class ExecutionHistoryController extends Controller {
    public ExecutionHistoryController(Javalin app, SmartIEHub smartIEInstance, Gson gson) {
        super(app, smartIEInstance, gson);

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
