package com.github.iamniklas.hub.network.websocket;


import com.github.iamniklas.hub.SmartIEHub;

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