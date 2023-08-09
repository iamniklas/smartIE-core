package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.List;


//This controller shall be only available in development mode.
public class TestsController extends Controller {
    public TestsController(Javalin app, SmartIEHub smartIEInstance, Gson gson) {
        super(app, smartIEInstance, gson);

        //Get the total count of existing rules: {"toCount": "rule", "count": x}
        app.get("/tests/rule/count", ctx -> {
            CountObject countObject = new CountObject("rule", smartIEInstance.getRuleCount());
            ctx.result(new Gson().toJson(countObject));
        });

        //Get the total count of existing devices: {"toCount": "device", "count": x}
        app.get("/tests/device/count", ctx -> {
            CountObject countObject = new CountObject("device", smartIEInstance.getDeviceCount());
            ctx.result(new Gson().toJson(countObject));
        });

        //Delete all rules
        app.delete("/tests/rule/all", ctx -> {
            smartIEInstance.getRegisteredRules().clear();
            ctx.json(new CountObject("rule", smartIEInstance.getRuleCount()));
        });

        //Delete all devices
        app.delete("/tests/device/all", ctx -> {
            smartIEInstance.getRegisteredInputDevices().clear();
            smartIEInstance.getRegisteredOutputDevices().clear();
            ctx.json(new CountObject("device", smartIEInstance.getDeviceCount()));
        });
    }

    public static class CountObject {
        private final String toCount;
        private final int count;

        public CountObject(String toCount, int count) {
            this.toCount = toCount;
            this.count = count;
        }

        public String getToCount() {
            return toCount;
        }

        public int getCount() {
            return count;
        }
    }
}