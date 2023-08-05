package com.github.iamniklas.smartIEcore.hub.devices;

import java.util.HashMap;
import java.util.Map;

public class InputDevice extends Device {
    private final InputDeviceType inputDeviceType;
    private int sensorValue;
    private Map<String, Object> sensorData;

    public InputDevice(String deviceUUID, String name, InputDeviceType inputDeviceType, DeviceAddress deviceAddress) {
        super(deviceUUID, name, deviceAddress);
        this.deviceType = DeviceType.INPUT;
        this.inputDeviceType = inputDeviceType;
        this.sensorData = new HashMap<>();
    }

    public InputDeviceType getInputDeviceType() { return inputDeviceType; }

    public void setSensorValue(String sensorId, Object newSensorValue) { sensorData.put(sensorId, newSensorValue); }
    public Object getSensorValue(String sensorId) {
        return sensorData.get(sensorId);
    }
    public int getSensorCount() {return sensorData.size(); }
    public void newSensorValues() { if(sensorData == null) sensorData = new HashMap<>(); }

    public Integer getSensorValue() { return sensorValue; }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }
}