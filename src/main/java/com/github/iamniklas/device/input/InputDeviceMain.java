package com.github.iamniklas.device.input;

import com.github.iamniklas.hub.SmartIEHub;

public class InputDeviceMain {

    private static boolean programRunning = true;

    public static void main(String[] args) {
        InputDevice inputDevice = new InputDevice(SmartIEHub.DeviceMode.DEFAULT);

        while (programRunning) { }
    }
}
