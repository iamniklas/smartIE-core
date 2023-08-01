package com.github.iamniklas.hub.network.javalin.controller.implementation;

import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.devices.*;
import com.github.iamniklas.hub.network.javalin.controller.Controller;
import com.github.iamniklas.shared.network.javalin.JavalinController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeviceController extends Controller {

    public DeviceController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);

        app.get("/device/{device_type}/all", ctx -> {
            Device.DeviceType deviceType = Device.DeviceType.valueOf(ctx.pathParam("device_type"));
            switch (deviceType) {
                case INPUT:
                    List<Device> inputDevices = smartIEInstance.getRegisteredInputDevices()
                            .stream()
                            .map(device -> (Device) device) // Perform the type conversion for each element
                            .collect(Collectors.toList());
                    ctx.json(inputDevices);
                    break;
                case OUTPUT:
                    List<Device> outputDevices = smartIEInstance.getRegisteredOutputDevices()
                            .stream()
                            .map(device -> (Device) device) // Perform the type conversion for each element
                            .collect(Collectors.toList());
                    ctx.json(outputDevices);
                    break;
            }
        });

        app.get("/device/{id}", ctx -> {
            String id = ctx.pathParam("id");

            InputDevice iDev = smartIEInstance.getRegisteredInputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);
            OutputDevice oDev = smartIEInstance.getRegisteredOutputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);

            if(iDev != null) {
                ctx.json(iDev);
            } else if(oDev != null) {
                ctx.json(oDev);
            } else {
                ctx.json(new Exception("No Device Found"));
            }
        });

        app.get("/device/{device_type}/scan", ctx -> {
            Device.DeviceType deviceType = Device.DeviceType.valueOf(ctx.pathParam("device_type"));

            ArrayList<Device> result = new ArrayList<>();
            switch (deviceType) {
                case INPUT:
                    result.add(new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700)));
                    break;
                case OUTPUT:
                    result.add(new OutputDevice(UUID.randomUUID().toString(), "output_device_1", OutputDeviceType.Other, new DeviceAddress("0.0.0.0", "hostname", 5700)));
                    break;
            }
            ctx.json(result);
        });

        app.post("/device", ctx -> {
            Device.DeviceType type = Device.DeviceType.valueOf(new Gson().fromJson(ctx.body(), JsonObject.class).get("deviceType").getAsString());

            if (type == Device.DeviceType.INPUT) {
                InputDevice iDev = new Gson().fromJson(ctx.body(), InputDevice.class);
                smartIEInstance.addRegisteredInputDevices(iDev);
                ctx.json(iDev);
            } else if (type == Device.DeviceType.OUTPUT) {
                OutputDevice oDev = new Gson().fromJson(ctx.body(), OutputDevice.class);
                smartIEInstance.addRegisteredOutputDevices(oDev);
                ctx.json(oDev);
            }
        });

        app.put("/device/{id}", ctx -> {
            Device.DeviceType type = Device.DeviceType.valueOf(new Gson().fromJson(ctx.body(), JsonObject.class).get("deviceType").getAsString());

            if (type == Device.DeviceType.INPUT) {
                InputDevice iDev = new Gson().fromJson(ctx.body(), InputDevice.class);
                smartIEInstance.setRegisteredInputDevices(iDev, ctx.pathParam("id"));
                ctx.json(iDev);
            } else if (type == Device.DeviceType.OUTPUT) {
                OutputDevice oDev = new Gson().fromJson(ctx.body(), OutputDevice.class);
                smartIEInstance.setRegisteredOutputDevices(oDev, ctx.pathParam("id"));
                ctx.json(oDev);
            }
        });

        app.delete("/device/{id}", ctx -> {
            String id = ctx.pathParam("id");
            InputDevice iDev = smartIEInstance.getRegisteredInputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);
            OutputDevice oDev = smartIEInstance.getRegisteredOutputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);

            if(iDev != null) {
                smartIEInstance.removeRegisteredInputDevice(id);
            } else if(oDev != null) {
                smartIEInstance.removeRegisteredOutputDevice(id);
            } else {
                ctx.status(500);
                ctx.result("Specified device id does not match any registered device");
            }
        });
    }
}