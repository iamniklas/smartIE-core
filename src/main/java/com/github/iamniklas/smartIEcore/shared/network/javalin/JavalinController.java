package com.github.iamniklas.smartIEcore.shared.network.javalin;

import io.javalin.Javalin;

public class JavalinController {
    protected Javalin app;

    public JavalinController(Javalin app) {
        this.app = app;
    }
}