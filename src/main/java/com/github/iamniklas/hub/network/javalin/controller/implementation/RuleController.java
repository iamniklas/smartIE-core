package com.github.iamniklas.hub.network.javalin.controller.implementation;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.network.javalin.controller.Controller;
import com.github.iamniklas.hub.rules.Rule;
import com.google.gson.Gson;
import io.javalin.Javalin;

public class RuleController extends Controller {

    public RuleController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);


        app.get("/rule/{id}", ctx -> {

        });

        app.get("/rule/all", ctx -> {

        });

        app.post("/rule", ctx -> {
            Rule r = new Gson().fromJson(ctx.body(), Rule.class);
            r.setSmartIEHub(smartIEInstance);

            smartIEInstance.registerRule(r);
        });

        app.put("/rule/{id}", ctx -> {

        });

        app.delete("/rule/{id}", ctx -> {

        });
    }
}
