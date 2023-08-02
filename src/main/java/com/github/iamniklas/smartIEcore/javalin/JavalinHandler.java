package com.github.iamniklas.smartIEcore.javalin;

import com.github.iamniklas.smartIEcore.device.input.network.javalin.controllers.DeviceController;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.ArrayList;

public class JavalinHandler {
    Javalin javalinApp;
    private static int JAVALIN_PORT = 3742;

    private ArrayList<JavalinController> controllers = new ArrayList<>();

    public JavalinHandler() {
        javalinApp = Javalin.create(conf -> {
            conf.plugins.enableCors(cors -> {
                cors.add(CorsPluginConfig::anyHost);
            });
        }).start(JAVALIN_PORT);

        //controllers.add(new RuleController(javalinApp));
        controllers.add(new DeviceController(javalinApp));
    }
}
