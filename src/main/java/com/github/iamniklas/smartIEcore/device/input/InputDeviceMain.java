package com.github.iamniklas.smartIEcore.device.input;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;

public class InputDeviceMain {

    private static boolean programRunning = true;

    public static void main(String[] args) {
        InputDevice inputDevice = new InputDevice(SmartIEHub.DeviceMode.DEFAULT);

        while (programRunning) { }
    }
}
