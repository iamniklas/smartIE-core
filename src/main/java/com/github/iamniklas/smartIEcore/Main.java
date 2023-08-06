package com.github.iamniklas.smartIEcore;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.DeviceAddress;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.InputDeviceType;
import com.github.iamniklas.smartIEcore.hub.network.ISmartIEAPI;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        SmartIEHub.DeviceMode mode = SmartIEHub.DeviceMode.DEFAULT;
        if(Arrays.asList(args).contains("-debug")) {
            mode = SmartIEHub.DeviceMode.DEBUG;

            System.err.println("********************************");
            System.err.println("** smartIE DEBUG MODE ENABLED **");
            System.err.println("********************************");
            System.err.println();
        }

        SmartIEHub smartIEHub = new SmartIEHub(mode);
        smartIEHub.start();

        System.out.println("smartIE ready");

        /*
        ISmartIEAPI hubAPI;
        GsonConverterFactory factory = GsonConverterFactory.create(
                new GsonBuilder()
                        .registerTypeAdapter(Device.class, new DeviceTypeAdapter())
                        .create()
        );

        hubAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+SmartIEHub.JAVALIN_PORT)
                .addConverterFactory(factory)
                .build()
                .create(ISmartIEAPI.class);

        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "Device 1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "localhost", 5700));
        inputDevice.setSpecificationValue("sensor#1", 12);

        //HUB: Register new unknown device
        Device device;
        try {
            device = hubAPI.newDevice(inputDevice).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        while (smartIEHub.getHubRunning()) { }
    }
}