package com.github.iamniklas.device.output.network.javalin.controllers;

import com.github.iamniklas.shared.network.javalin.JavalinController;
import io.javalin.Javalin;

public class OutputMasterController extends JavalinController {
    public OutputMasterController(Javalin app) {
        super(app);

        app.get("/act", ctx -> {

        });

        app.post("/act", ctx -> {

        });
    }
}
