package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.*;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;

import com.github.iamniklas.smartIEcore.hub.devices.*;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import retrofit2.converter.gson.GsonConverterFactory;

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

        app.get("/device/{device_type}/{id}", ctx -> {
            String id = ctx.pathParam("id");

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Device.class, new DeviceTypeAdapter());
            Gson gson = builder.create();

            Device.DeviceType deviceType = Device.DeviceType.valueOf(ctx.pathParam("device_type").toUpperCase());

            if(deviceType == Device.DeviceType.INPUT) {
                InputDevice iDev = smartIEInstance.getRegisteredInputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);
                String result = gson.toJson(iDev);
                ctx.result(result);
            } else if(deviceType == Device.DeviceType.OUTPUT) {
                OutputDevice oDev = smartIEInstance.getRegisteredOutputDevices().stream().filter(i -> i.getDeviceUUID().equals(id)).findFirst().orElse(null);
                String result = gson.toJson(oDev);
                ctx.result(result);
            }
        });

        app.get("/device/{device_type}/scan", ctx -> {
            Device.DeviceType deviceType = Device.DeviceType.valueOf(ctx.pathParam("device_type").toUpperCase());

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
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Device.class, new DeviceTypeAdapter());
            Gson gson = builder.create();

            Device.DeviceType type = Device.DeviceType.valueOf(gson.fromJson(ctx.body(), JsonObject.class).get("deviceType").getAsString());

            if (type == Device.DeviceType.INPUT) {
                Device iDev = gson.fromJson(ctx.body(), Device.class);
                smartIEInstance.addRegisteredInputDevices((InputDevice) iDev);
                ctx.json(iDev);
            } else if (type == Device.DeviceType.OUTPUT) {
                OutputDevice oDev = gson.fromJson(ctx.body(), OutputDevice.class);
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

        app.put("/device/{device_id}/{sensor_id}", ctx -> {
            smartIEInstance.updateSensorValue(ctx.pathParam("device_id"), ctx.pathParam("sensor_id"), ctx.body());

            ctx.json(smartIEInstance.getAllRegisteredDevices().stream().filter(i -> i.getDeviceUUID().equals(ctx.pathParam("device_id"))).findFirst().get());
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