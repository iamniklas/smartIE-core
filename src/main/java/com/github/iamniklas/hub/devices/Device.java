package com.github.iamniklas.hub.devices;

import java.util.UUID;

public abstract class Device {
    protected String deviceUUID;
    protected final String name;
    protected final DeviceAddress deviceAddress;

    protected DeviceType deviceType;

    public enum DeviceType {INPUT, OUTPUT}

    public Device(String deviceUUID, String name, DeviceAddress deviceAddress) {
        this.deviceUUID = deviceUUID;
        this.name = name;
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceUUID() { return deviceUUID; }
    public String getName() { return name; }
    public DeviceAddress getDeviceAddress() { return deviceAddress; }
    public abstract DeviceType getDeviceType();

    public void regenerateUUID() {
        deviceUUID = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceUUID='" + deviceUUID + '\'' +
                ", name='" + name + '\'' +
                ", deviceAddress=" + deviceAddress +
                ", deviceType=" + getDeviceType() +
                '}';
    }
}
