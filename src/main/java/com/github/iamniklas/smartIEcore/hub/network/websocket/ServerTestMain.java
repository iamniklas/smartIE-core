package com.github.iamniklas.smartIEcore.hub.network.websocket;


import com.github.iamniklas.smartIEcore.hub.SmartIEHub;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerTestMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        SmartIEHub smartIEInstance = new SmartIEHub(SmartIEHub.DeviceMode.DEFAULT);

        DashUpdateEndpoint dashUpdateEndpoint = new DashUpdateEndpoint(new InetSocketAddress("localhost", 8250), smartIEInstance);
        dashUpdateEndpoint.start();

        while (true) {

        }
    }
}