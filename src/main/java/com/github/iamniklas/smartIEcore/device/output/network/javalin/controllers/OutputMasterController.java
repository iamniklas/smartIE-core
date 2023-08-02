package com.github.iamniklas.smartIEcore.device.output.network.javalin.controllers;

import com.github.iamniklas.smartIEcore.shared.network.javalin.JavalinController;
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
