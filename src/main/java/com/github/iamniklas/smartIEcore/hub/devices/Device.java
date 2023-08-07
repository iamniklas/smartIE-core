package com.github.iamniklas.smartIEcore.hub.devices;

public abstract class Device {
    protected final String deviceUUID;
    protected final String name;
    protected final DeviceAddress deviceAddress;
    private DeviceSpecification deviceSpecification;

    protected DeviceType deviceType;

    public enum DeviceType {INPUT, OUTPUT}

    public Device(String deviceUUID, String name, DeviceAddress deviceAddress) {
        this.deviceUUID = deviceUUID;
        this.name = name;
        this.deviceAddress = deviceAddress;
        deviceSpecification = new DeviceSpecification(new String[]{});
    }

    public String getDeviceUUID() { return deviceUUID; }
    public String getName() { return name; }
    public DeviceAddress getDeviceAddress() { return deviceAddress; }
    public abstract DeviceType getDeviceType();

    public DeviceSpecification getDeviceSpecification() {
        return deviceSpecification;
    }
    public void setSpecificationValue(String key, Object val) { deviceSpecification.setSpecification(key, val); }
    public void setSpecification(DeviceSpecification spec) { deviceSpecification = spec; }
}
