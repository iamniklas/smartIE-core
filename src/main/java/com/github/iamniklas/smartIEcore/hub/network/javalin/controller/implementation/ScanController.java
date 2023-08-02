package com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import com.github.iamniklas.nettools.models.DeviceResult;
import com.github.iamniklas.nettools.models.RequestMethod;
import com.github.iamniklas.nettools.models.TestResult;
import com.github.iamniklas.nettools.scanner.AcceleratedNetworkScanner;
import com.github.iamniklas.nettools.scanner.ScanResultCallback;
import com.github.iamniklas.nettools.scanner.Scanner;
import io.javalin.Javalin;

import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;

public class ScanController extends Controller {
    public ScanController(Javalin app, SmartIEHub smartIEInstance) {
        super(app, smartIEInstance);

        app.get("/scan", ctx -> {
            TestResult result = scanForDevices(new ScanResultCallback() {
                @Override
                public void onSuccessResult(DeviceResult result) {

                }

                @Override
                public void onScanComplete(int progress, int maxValue) {

                }

                @Override
                public void onErrorResult(Exception exception) {

                }
            });

            
        });
    }

    public TestResult scanForDevices(ScanResultCallback _callback) {
        try(final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String scanClientIp = socket.getLocalAddress().getHostAddress();

            int lastIndexOf = scanClientIp.lastIndexOf('.');
            String networkScanRange = scanClientIp.substring(0, lastIndexOf);

            AcceleratedNetworkScanner scanner = new AcceleratedNetworkScanner(
                    networkScanRange,
                    SmartIEHub.JAVALIN_PORT,
                    "device/name",
                    RequestMethod.GET,
                    Scanner.DEFAULT_TIMEOUT,
                    _callback
            );

            return scanner.scanFor(t ->
                    t.resultCode == HttpURLConnection.HTTP_OK &&
                            t.body != null
            );
        }
        catch (Exception e) {
            return null;
        }
    }
}
