package com.github.iamniklas.smartIEcore.hub.devices;

public class InputDevice extends Device {
    private final InputDeviceType inputDeviceType;
    private int sensorValue;


    public InputDevice(String deviceUUID, String name, InputDeviceType inputDeviceType, DeviceAddress deviceAddress) {
        super(deviceUUID, name, deviceAddress);
        this.deviceType = DeviceType.INPUT;
        this.inputDeviceType = inputDeviceType;
    }

    public InputDeviceType getInputDeviceType() { return inputDeviceType; }

    public Integer getSensorValue() { return sensorValue; }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }
}