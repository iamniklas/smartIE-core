package com.github.iamniklas.smartIEcore.hub.devices;

public class OutputDevice extends Device {
    private final OutputDeviceType outputDeviceType;

    public OutputDevice(String deviceUUID, String name, OutputDeviceType outputDeviceType, DeviceAddress deviceAddress) {
        super(deviceUUID, name, deviceAddress);
        this.outputDeviceType = outputDeviceType;
        this.deviceType = DeviceType.OUTPUT;
    }

    public OutputDeviceType getType() { return outputDeviceType; }

    @Override
    public DeviceType getDeviceType() {
        return deviceType;
    }
}
