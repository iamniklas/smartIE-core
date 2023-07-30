package com.github.iamniklas.device.input.network.javalin.controllers;

import com.github.iamniklas.shared.network.javalin.JavalinController;
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