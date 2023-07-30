package com.github.iamniklas.device.output;


import com.github.iamniklas.hub.SmartIEHub;

public class OutputDeviceMain {

    private static boolean programRunning = true;

    public static void main(String[] args) {
        OutputDevice outputDevice = new OutputDevice(SmartIEHub.DeviceMode.DEBUG);

        while (programRunning) { }
    }
}
