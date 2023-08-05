import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.OutputDevice;
import com.github.iamniklas.smartIEcore.hub.network.ISmartIEAPI;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.TestsController;
import com.github.iamniklas.smartIEcore.hub.rules.runner.RuleRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class TestTests {

    static SmartIEHub hub;
    static ISmartIEAPI hubAPI;

    @BeforeAll
    public static void initialize() {
        hub = new SmartIEHub(SmartIEHub.DeviceMode.DEBUG);
        hub.start();

        hubAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+SmartIEHub.JAVALIN_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ISmartIEAPI.class);

        hub.addRegisteredInputDevices(new InputDevice(null, null, null, null));
        hub.addRegisteredInputDevices(new InputDevice(null, null, null, null));
        hub.addRegisteredOutputDevices(new OutputDevice(null, null, null, null));
        hub.addRegisteredOutputDevices(new OutputDevice(null, null, null, null));

        hub.addRegisteredRules(new RuleRunner(null));
        hub.addRegisteredRules(new RuleRunner(null));
        hub.addRegisteredRules(new RuleRunner(null));
        hub.addRegisteredRules(new RuleRunner(null));
    }

    // GET /tests/device/count
    @Test
    public void testDeviceCountEndpoint() {
        TestsController.CountObject countFromApi;

        try {
            countFromApi = hubAPI.getTotalDeviceCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(countFromApi);
        Assertions.assertEquals(hub.getDeviceCount(), countFromApi.getCount());
    }

    // GET /tests/rule/count
    @Test
    public void testRuleCountEndpoint() {
        TestsController.CountObject countFromApi;

        try {
            countFromApi = hubAPI.getTotalRuleCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(countFromApi);
        Assertions.assertEquals(hub.getRuleCount(), countFromApi.getCount());
    }

    // DELETE /tests/device/all
    @Test
    public void testAllDeviceRemovalEndpoint() {
        TestsController.CountObject countObject;

        try {
            countObject = hubAPI.removeAllDevices().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestsController.CountObject countFromApi;

        try {
            countFromApi = hubAPI.getTotalDeviceCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(countObject);
        Assertions.assertNotNull(countFromApi);
        Assertions.assertEquals(0, countFromApi.getCount());
    }

    // DELETE /tests/rule/all
    @Test
    public void testAllRuleRemovalEndpoint() {
        TestsController.CountObject countObject;

        try {
            countObject = hubAPI.removeAllRules().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestsController.CountObject countFromApi;

        try {
            countFromApi = hubAPI.getTotalRuleCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(countObject);
        Assertions.assertNotNull(countFromApi);
        Assertions.assertEquals(0, countFromApi.getCount());
    }
}
