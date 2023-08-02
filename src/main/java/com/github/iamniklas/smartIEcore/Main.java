package com.github.iamniklas.smartIEcore;

import com.github.iamniklas.smartIEcore.hub.SmartIEHub;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SmartIEHub.DeviceMode mode = SmartIEHub.DeviceMode.DEFAULT;
        if(Arrays.asList(args).contains("-debug")) {
            mode = SmartIEHub.DeviceMode.DEBUG;

            System.err.println("********************************");
            System.err.println("** smartIE DEBUG MODE ENABLED **");
            System.err.println("********************************");
            System.err.println();
        }
        SmartIEHub smartIEInstance = new SmartIEHub(mode);

        System.out.println("smartIE ready");

        boolean programIsRunning = true;
        while (programIsRunning) { }
    }
}