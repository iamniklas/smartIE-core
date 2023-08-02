package com.github.iamniklas.smartIEcore.device.input;

import com.github.iamniklas.smartIEcore.device.input.network.javalin.controllers.DeviceController;
import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.shared.network.javalin.JavalinController;
import io.javalin.Javalin;

import java.util.ArrayList;

public class InputDevice {
    Javalin javalinApp;
    public static int JAVALIN_PORT = 3742;
    public static int DEBUG_PORT = 5701;

    ArrayList<JavalinController> javalinControllers = new ArrayList<>();

    public InputDevice(SmartIEHub.DeviceMode deviceMode) {
        javalinApp = Javalin.create().start(deviceMode == SmartIEHub.DeviceMode.DEFAULT ? JAVALIN_PORT : DEBUG_PORT);

        javalinControllers.add(new DeviceController(javalinApp));
    }
}
