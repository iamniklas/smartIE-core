package com.github.iamniklas.hub.devices;

public class InputDevice extends Device {
    private Integer sensorValue = 0;
    private final InputDeviceType inputDeviceType;

    public InputDevice(String deviceUUID, String name, InputDeviceType inputDeviceType, DeviceAddress deviceAddress) {
        super(deviceUUID, name, deviceAddress);
        this.deviceType = DeviceType.INPUT;
        this.inputDeviceType = inputDeviceType;
    }

    public InputDeviceType getInputDeviceType() { return inputDeviceType; }
    public Integer getSensorValue() { return sensorValue; }

    public void setSensorValue(Integer newSensorValue) { sensorValue = newSensorValue; }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }
}