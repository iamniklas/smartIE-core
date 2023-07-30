package com.github.iamniklas.hub.devices;

public class DeviceAddress {
    public String ip;
    public String hostname;
    public int port;

    public DeviceAddress(String ip, String hostname, int port) {
        this.ip = ip;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public String toString() {
        return "DeviceAddress{" +
                "ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
