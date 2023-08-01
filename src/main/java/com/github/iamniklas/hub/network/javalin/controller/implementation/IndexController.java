package com.github.iamniklas.hub.network.javalin.controller.implementation;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.network.javalin.controller.Controller;
import io.javalin.Javalin;

import java.util.ArrayList;

public class IndexController extends Controller {
    public IndexController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);

        app.get("/", ctx -> {
            String apiInfo =
                    "\n"+
                            "Device:\n"+
                            "GET        /device/info/{id}/:             Get device information by device ID\n"+
                            "GET        /device/info/all/:              Get information for all registered devices\n"+
                            "GET        /device/echo/{id}/:             Receive an echo message for a device identified by device ID to check availability\n"+
                            "POST       /device/{id}/:                  Register a new device\n"+
                            "PUT        /device/{id}/:                  Update a device's information by device ID\n"+
                            "DELETE     /device/{id}/:                  Unregister a known device by device ID\n"+
                            "\n"+
                            "Action:\n"+
                            "GET        /action/{id}/:                  Get the action and its affected devices by action ID\n"+
                            "GET        /action/all/:                   Get all actions and its affected devices\n"+
                            "POST       /action/{id}/:                  Create a new action\n"+
                            "PUT        /action/{id}/:                  Update an existing action by its id\n"+
                            "DELETE     /action/{id}/:                  Remove an action by its id\n"+
                            "\n"+
                            "Action:\n"+
                            "GET        /history/rule/{id}/:            Get every passed execution for a rule identified by its ID\n"+
                            "GET        /history/rule/all/:             Get every passed execution for all rules\n"+
                            "GET        /history/device/{id}/:          Get every passed execution for a output device identified by its ID\n"+
                            "GET        /history/device/all/:           Get every passed execution for a output device\n";

            ArrayList<String> s = new ArrayList<>();
            s.add("Index Page. Please refer to the API documentation:\n"+apiInfo);
            ctx.json(s);
        });
    }
}
