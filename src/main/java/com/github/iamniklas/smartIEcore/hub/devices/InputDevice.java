package com.github.iamniklas.smartIEcore.hub.devices;

import java.util.HashMap;
import java.util.Map;

public class InputDevice extends Device {
    private final InputDeviceType inputDeviceType;
    private int sensorValue;
    private InputDeviceSpecification inputDeviceSpecification;

    public InputDevice(String deviceUUID, String name, InputDeviceType inputDeviceType, DeviceAddress deviceAddress) {
        super(deviceUUID, name, deviceAddress);
        this.deviceType = DeviceType.INPUT;
        this.inputDeviceType = inputDeviceType;
        inputDeviceSpecification = new InputDeviceSpecification(new String[]{});
    }

    public InputDeviceType getInputDeviceType() { return inputDeviceType; }

    public InputDeviceSpecification getInputDeviceSpecification() {
        if(inputDeviceSpecification == null) {
            inputDeviceSpecification = new InputDeviceSpecification(new String[]{});
        }
        return inputDeviceSpecification;
    }
    public void setSpecificationValue(String key, Object val) { inputDeviceSpecification.setSpecification(key, val); }
    public void setSpecification(InputDeviceSpecification spec) { inputDeviceSpecification = spec; }

    public Integer getSensorValue() { return sensorValue; }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }
}