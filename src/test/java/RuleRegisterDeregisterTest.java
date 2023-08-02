/*
import com.github.iamniklas.smartIEcore.device.input.InputDevice;
import com.github.iamniklas.smartIEcore.device.output.OutputDevice;
import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.network.IInputDeviceAPI;
import com.github.iamniklas.smartIEcore.hub.network.IOutputDeviceAPI;
import com.github.iamniklas.smartIEcore.hub.network.ISmartIEAPI;
import com.github.iamniklas.smartIEcore.hub.rules.implementations.ProxyRule;
import com.google.gson.Gson;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RuleRegisterDeregisterTest {
    static SmartIEHub hub;
    static InputDevice inputDevice;
    static OutputDevice outputDevice;

    static ISmartIEAPI hubAPI;
    static IInputDeviceAPI inputDeviceAPI;
    static IOutputDeviceAPI outputDeviceAPI;

    @BeforeAll
    public static void initialize() {
        hub = new SmartIEHub(SmartIEHub.DeviceMode.DEBUG); //5700
        inputDevice = new InputDevice(SmartIEHub.DeviceMode.DEBUG); //5701
        outputDevice = new OutputDevice(SmartIEHub.DeviceMode.DEBUG); //5702

        hubAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+SmartIEHub.JAVALIN_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ISmartIEAPI.class);

        inputDeviceAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+InputDevice.DEBUG_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IInputDeviceAPI.class);

        outputDeviceAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+OutputDevice.DEBUG_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IOutputDeviceAPI.class);
    }

    @Test
    public void testRuleRegister() {
        //Hub: Register Rule
        ProxyRule rule = new ProxyRule(null, "Proxy Rule", null, null);

        String s = (new Gson().toJson(new ProxyRule(null, "Proxy Rule", null, null)));

        HttpStatus status;
        try {
            status = hubAPI.newRule(rule).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(HttpStatus.OK, status);

        //Hub: Check Rule Count
        Integer ruleCount;
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
*/
