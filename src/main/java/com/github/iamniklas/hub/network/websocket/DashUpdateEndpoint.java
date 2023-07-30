package com.github.iamniklas.hub.network.websocket;

import com.github.iamniklas.hub.SmartIEHub;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class DashUpdateEndpoint extends WebSocketServer {

    SmartIEHub smartIEInstance;
    Thread cycler;

    public DashUpdateEndpoint(SmartIEHub smartIEInstance) {
        this.smartIEInstance = smartIEInstance;
        cycler = new Thread(new CycleRunner());
        cycler.start();
    }

    public DashUpdateEndpoint(InetSocketAddress address, SmartIEHub smartIEInstance) {
        super(address);
        this.smartIEInstance = smartIEInstance;
        cycler = new Thread(new CycleRunner());
        cycler.start();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }



    class CycleRunner implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    broadcast("cycle");
                    System.out.println("cycler");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
