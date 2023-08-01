package com.github.iamniklas.hub;

import com.github.iamniklas.hub.devices.Device;
import com.github.iamniklas.hub.devices.InputDevice;
import com.github.iamniklas.hub.devices.OutputDevice;
import com.github.iamniklas.hub.network.javalin.ExecutionEvent;
import com.github.iamniklas.hub.network.javalin.controller.Controller;
import com.github.iamniklas.hub.network.javalin.controller.implementation.DeviceController;
import com.github.iamniklas.hub.network.javalin.controller.implementation.ExecutionHistoryController;
import com.github.iamniklas.hub.network.javalin.controller.implementation.RuleController;
import com.github.iamniklas.hub.network.javalin.controller.implementation.TestsController;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.runner.RuleRunner;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SmartIEHub {
    public static int JAVALIN_PORT = 3742;
    public static int DEBUG_PORT = 5700;

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
        }).start(deviceMode == DeviceMode.DEFAULT ? JAVALIN_PORT : DEBUG_PORT);
        app.get("/", ctx -> {
            String apiInfo =
                    "\n"+
                            "Device:\n"+
                            "GET        /device/info/{id}/:             Get device information by device ID\n"+
                            "GET        /device/info/all/:              Get information for all registered devices\n"+
                            "GET        /device/echo/{id}/:             Receive an echo message for a device identified by device ID to check availability\n"+
                            "POST       /device/{id}/:                  Register a new device\n"+
                            "PUT        /device/{id}/:                  Update a device's information by device ID\n"+
                            "DELETE     /device/{id}/:                  Unregister a known device by device ID\n"+
                            "\n"+
                            "Action:\n"+
                            "GET        /action/{id}/:                  Get the action and its affected devices by action ID\n"+
                            "GET        /action/all/:                   Get all actions and its affected devices\n"+
                            "POST       /action/{id}/:                  Create a new action\n"+
                            "PUT        /action/{id}/:                  Update an existing action by its id\n"+
                            "DELETE     /action/{id}/:                  Remove an action by its id\n"+
                            "\n"+
                            "Action:\n"+
                            "GET        /history/rule/{id}/:            Get every passed execution for a rule identified by its ID\n"+
                            "GET        /history/rule/all/:             Get every passed execution for all rules\n"+
                            "GET        /history/device/{id}/:          Get every passed execution for a output device identified by its ID\n"+
                            "GET        /history/device/all/:           Get every passed execution for a output device\n";

            ArrayList<String> s = new ArrayList<>();
            s.add("Index Page. Please refer to the API documentation:\n"+apiInfo);
            ctx.json(s);
        });

        javalinControllers.add(new RuleController(app, this));
        javalinControllers.add(new DeviceController(app, this));
        javalinControllers.add(new ExecutionHistoryController(app, this));
        if(deviceMode == DeviceMode.DEBUG) {
            javalinControllers.add(new TestsController(app, this));
        }
    }

    public void registerRule(RuleRunner ruleRunner) {
        registeredRules.add(ruleRunner);
        new Thread(ruleRunner);
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
