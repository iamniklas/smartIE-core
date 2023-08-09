package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.runner.RuleRunner;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.RuleTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;

public class RuleController extends Controller {

    public RuleController(Javalin app, SmartIEHub smartIEInstance, Gson gson) {
        super(app, smartIEInstance, gson);


        app.get("/rule/{id}", ctx -> {

        });

        app.get("/rule/all", ctx -> {

        });

        app.post("/rule", ctx -> {
            Rule r = gson.fromJson(ctx.body(), Rule.class);

            r.setSmartIEHub(smartIEInstance);

            smartIEInstance.registerRule(new RuleRunner(r));

            ctx.result(gson.toJson(r));
        });

        app.put("/rule/{id}", ctx -> {

        });

        app.delete("/rule/{id}", ctx -> {

        });
    }
}
