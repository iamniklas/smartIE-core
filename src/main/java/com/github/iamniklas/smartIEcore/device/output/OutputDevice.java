package com.github.iamniklas.smartIEcore.device.output;

import com.github.iamniklas.smartIEcore.device.output.network.javalin.controllers.OutputMasterController;
import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.javalin.JavalinController;
import io.javalin.Javalin;

import java.util.ArrayList;

public class OutputDevice {
    Javalin javalinApp;
    public static int JAVALIN_PORT = 3742;
    public static int DEBUG_PORT = 5702;
    ArrayList<JavalinController> javalinControllers = new ArrayList<>();
    public OutputDevice(SmartIEHub.DeviceMode deviceMode) {
        javalinApp = Javalin.create().start(deviceMode == SmartIEHub.DeviceMode.DEFAULT ? JAVALIN_PORT : DEBUG_PORT);

        javalinControllers.add(new OutputMasterController(javalinApp));
    }
}
