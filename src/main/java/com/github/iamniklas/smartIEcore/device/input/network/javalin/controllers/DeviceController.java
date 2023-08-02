package com.github.iamniklas.smartIEcore.device.input.network.javalin.controllers;

import com.github.iamniklas.smartIEcore.javalin.JavalinController;
import io.javalin.Javalin;

public class DeviceController extends JavalinController {

    public DeviceController(Javalin app) {
        super(app);

        app.get("/scan", ctx -> {

        });

        app.post("/configure", ctx -> {

        });
    }
}