package com.github.iamniklas.smartIEcore.hub;

import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.OutputDevice;
import com.github.iamniklas.smartIEcore.hub.network.javalin.ExecutionEvent;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.Controller;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.*;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.runner.RuleRunner;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.*;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SmartIEHub {
    public static int JAVALIN_PORT = 3742;

    private final ArrayList<Controller> javalinControllers = new ArrayList<>();

    private final ArrayList<InputDevice> registeredInputDevices = new ArrayList<>();
    private final ArrayList<OutputDevice> registeredOutputDevices = new ArrayList<>();
    private final ArrayList<RuleRunner> registeredRules = new ArrayList<>();

    private final ArrayList<ExecutionEvent<Rule>> ruleExecutions = new ArrayList<>();
    private final ArrayList<ExecutionEvent<OutputDevice>> outputDeviceExecutions = new ArrayList<>();

    public enum DeviceMode { DEFAULT, DEBUG }

    public SmartIEHub(DeviceMode deviceMode) {
        Javalin app = Javalin.create(conf -> {
            conf.plugins.enableCors(cors -> {
                cors.add(CorsPluginConfig::anyHost);
            });
        }).start(JAVALIN_PORT);

        javalinControllers.add(new IndexController(app, this));
        javalinControllers.add(new RuleController(app, this));
        javalinControllers.add(new DeviceController(app, this));
        javalinControllers.add(new ExecutionHistoryController(app, this));
        if(deviceMode == DeviceMode.DEBUG) {
            javalinControllers.add(new TestsController(app, this));
        }
    }

    public void registerRule(RuleRunner ruleRunner) {
        registeredRules.add(ruleRunner);
        ruleRunner.start();
    }

    public int getRuleCount() {
        return registeredRules.size();
    }

    public void removeRule(Rule rule) {
        registeredRules.remove(rule);
        //TODO rule.stop();
    }

    public ArrayList<InputDevice> getRegisteredInputDevices() { return registeredInputDevices; }
    public ArrayList<OutputDevice> getRegisteredOutputDevices() { return registeredOutputDevices; }
    public ArrayList<Device> getAllRegisteredDevices() {
        ArrayList<Device> result = new ArrayList<>(registeredInputDevices);
        result.addAll(registeredOutputDevices);
        return result;
    }
    public List<Rule> getRegisteredRules() { return registeredRules.stream().map(r -> r.getRule()).collect(Collectors.toList()); }
    public ArrayList<ExecutionEvent<OutputDevice>> getOutputDeviceExecutions() { return outputDeviceExecutions; }
    public ArrayList<ExecutionEvent<Rule>> getRuleExecutions() { return ruleExecutions; }

    public void addRegisteredInputDevices(InputDevice dev) { registeredInputDevices.add(dev); }
    public void setRegisteredInputDevices(InputDevice dev, String uuid) { registeredInputDevices.replaceAll(e -> Objects.equals(e.getDeviceUUID(), uuid) ? dev : e); }
    public void removeRegisteredInputDevice(String uuid) { registeredInputDevices.removeIf(e -> e.getDeviceUUID().equals(uuid));}

    public void addRegisteredOutputDevices(OutputDevice dev) { registeredOutputDevices.add(dev); }
    public void setRegisteredOutputDevices(OutputDevice dev, String uuid) { registeredOutputDevices.replaceAll(e -> Objects.equals(e.getDeviceUUID(), uuid) ? dev : e); }
    public void removeRegisteredOutputDevice(String uuid) { registeredOutputDevices.removeIf(e -> e.getDeviceUUID().equals(uuid));}

    public void addRegisteredRules(RuleRunner rule) { registeredRules.add(rule); }
    public void setRegisteredRules(Rule rule, String uuid) { registeredRules.replaceAll(e -> Objects.equals(e.getRule().getRuleUUID(), uuid) ? new RuleRunner(rule) : e); }
    public void removeRegisteredRule(String uuid) { registeredRules.removeIf(e -> e.getRule().getRuleUUID().equals(uuid)); }
}
