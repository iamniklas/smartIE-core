package com.github.iamniklas.hub.network.javalin.controller.implementation;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.devices.Device;
import com.github.iamniklas.hub.network.javalin.controller.Controller;
import com.github.iamniklas.hub.rules.Rule;
import com.google.gson.JsonObject;
import io.javalin.Javalin;

import java.util.List;


//This controller shall be only available in development mode.
public class TestsController extends Controller {
    public TestsController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);

        //Get the total count of existing rules: {"to_count": "rule", "count": x}
        app.get("/tests/rule/count", ctx -> {
            JsonObject json = new JsonObject();
            json.addProperty("to_count", "rule");
            json.addProperty("count", smartIEInstance.getRuleCount());
            //ctx.json(json);
            int i = smartIEInstance.getRuleCount();
            ctx.result(String.valueOf(i));
        });

        //Get the total count of existing devices: {"to_count": "device", "count": x}
        app.get("/tests/device/count", ctx -> {
            JsonObject json = new JsonObject();
            json.addProperty("to_count", "device");
            json.addProperty("count", smartIEInstance.getRegisteredInputDevices().size() + smartIEInstance.getRegisteredOutputDevices().size());
            int i = smartIEInstance.getRegisteredInputDevices().size() + smartIEInstance.getRegisteredOutputDevices().size();
            ctx.result(String.valueOf(i));
        });

        //Delete all rules
        app.delete("/tests/rule/all", ctx -> {
            List<Rule> rules = smartIEInstance.getRegisteredRules();
            smartIEInstance.getRegisteredRules().clear();
            ctx.json(rules.toArray());
        });

        //Delete all devices
        app.delete("/tests/device/all", ctx -> {
            List<Device> allDevices = smartIEInstance.getAllRegisteredDevices();
            smartIEInstance.getRegisteredInputDevices().clear();
            smartIEInstance.getRegisteredOutputDevices().clear();
            ctx.json(allDevices);
        });
    }
}