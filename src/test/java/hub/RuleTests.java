package hub;

import com.github.iamniklas.smartIEcore.device.input.InputDevice;
import com.github.iamniklas.smartIEcore.device.output.OutputDevice;
import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.network.IInputDeviceAPI;
import com.github.iamniklas.smartIEcore.hub.network.IOutputDeviceAPI;
import com.github.iamniklas.smartIEcore.hub.network.ISmartIEAPI;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.TestsController;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.implementations.ProxyRule;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.RuleTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.UUID;

public class RuleTests {
    static SmartIEHub hub;
    static InputDevice inputDevice;
    static OutputDevice outputDevice;

    static ISmartIEAPI hubAPI;
    static IInputDeviceAPI inputDeviceAPI;
    static IOutputDeviceAPI outputDeviceAPI;

    @BeforeAll
    public static void initialize() {
        hub = new SmartIEHub(SmartIEHub.DeviceMode.DEFAULT);
        hub.start();
        inputDevice = new InputDevice(SmartIEHub.DeviceMode.DEBUG); //5701
        outputDevice = new OutputDevice(SmartIEHub.DeviceMode.DEBUG); //5702

        GsonConverterFactory factory = GsonConverterFactory.create(
                new GsonBuilder()
                        .registerTypeAdapter(Rule.class, new RuleTypeAdapter())
                        .create()
        );

        hubAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+SmartIEHub.JAVALIN_PORT)
                .addConverterFactory(factory)
                .build()
                .create(ISmartIEAPI.class);

        inputDeviceAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+InputDevice.DEBUG_PORT)
                .addConverterFactory(factory)
                .build()
                .create(IInputDeviceAPI.class);

        outputDeviceAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+OutputDevice.DEBUG_PORT)
                .addConverterFactory(factory)
                .build()
                .create(IOutputDeviceAPI.class);
    }

    @Test
    public void testRuleRegister() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Rule.class, new RuleTypeAdapter());
        Gson gson = builder.create();

        //Hub: Register Rule
        ProxyRule rule = new ProxyRule(UUID.randomUUID().toString(), null, "Proxy Rule", null, null);

        String s = (gson.toJson(new ProxyRule(UUID.randomUUID().toString(), null, "Proxy Rule", null, null)));

        Rule status;
        try {
            status = hubAPI.newRule(rule).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(HttpStatus.OK, status);

        //Hub: Check Rule Count
        TestsController.CountObject ruleCount;
        try {
            ruleCount = hubAPI.getTotalRuleCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, ruleCount);
    }

    @Test
    public void testRuleDeregister() {
        //Hub: Deregister Rule

        //Hub: Check Rule Count
    }
}
