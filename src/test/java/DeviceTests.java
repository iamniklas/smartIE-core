import com.github.iamniklas.smartIEcore.hub.SmartIEHub;
import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.DeviceAddress;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.InputDeviceType;
import com.github.iamniklas.smartIEcore.hub.network.ISmartIEAPI;
import com.github.iamniklas.smartIEcore.hub.network.javalin.controller.implementation.TestsController;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DeviceTests {
    static SmartIEHub hub;
    static ISmartIEAPI hubAPI;

    @BeforeAll
    public static void initialize() {
        hub = new SmartIEHub(SmartIEHub.DeviceMode.DEFAULT); //5700
        hub.start();

        GsonConverterFactory factory = GsonConverterFactory.create(
                new GsonBuilder()
                        .registerTypeAdapter(Device.class, new DeviceTypeAdapter())
                        .create()
        );

        hubAPI = new Retrofit.Builder()
                .baseUrl("http://localhost:"+SmartIEHub.JAVALIN_PORT)
                .addConverterFactory(factory)
                .build()
                .create(ISmartIEAPI.class);
    }

    @Test
    public void testRegisterDevice() {

        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "Device A", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "localhost", 3742));
        InputDevice inputDevice2 = new InputDevice(UUID.randomUUID().toString(), "Device B", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "localhost", 3742));

        //HUB: Register new unknown device
        Device device;
        try {
            device = hubAPI.newDevice(inputDevice).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(device);
        Assertions.assertEquals("Device A", device.getName());
        Assertions.assertEquals("INPUT", device.getDeviceType().toString());
        Assertions.assertEquals(1, hub.getDeviceCount());
        try {
            device = hubAPI.newDevice(inputDevice2).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(device);
        Assertions.assertEquals("Device B", device.getName());
        Assertions.assertEquals("INPUT", device.getDeviceType().toString());
        Assertions.assertEquals(2, hub.getDeviceCount());
    }

    @Test
    public void testDeregisterDevice() {
        testRegisterDevice();
        Assertions.assertEquals(2, hub.getDeviceCount());

        String uuidOfDeviceA = hub.getAllRegisteredDevices().get(0).getDeviceUUID();
        String uuidOfDeviceB = hub.getAllRegisteredDevices().get(1).getDeviceUUID();

        //HUB: Deregister known device

        Device device;
        try {
            device = hubAPI.deleteDeviceByID(uuidOfDeviceA).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, hub.getDeviceCount());
        try {
            device = hubAPI.deleteDeviceByID(uuidOfDeviceB).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //HUB: Check registered device count equals 0
        Assertions.assertEquals(0, hub.getDeviceCount());
    }

    @Test
    public void testUpdateDevice() {
        testRegisterDevice();

        String newDeviceName = "new_super_epic_device_name";
        Device dev = hub.getAllRegisteredDevices().get(0);
        Device modifiedDevice = new InputDevice(dev.getDeviceUUID(), newDeviceName, InputDeviceType.Sensor, dev.getDeviceAddress());

        String deviceUUID = hub.getAllRegisteredDevices().get(0).getDeviceUUID();

        Device deviceFromApi;
        try {
            deviceFromApi = hubAPI.updateDeviceByID(deviceUUID, modifiedDevice).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(deviceFromApi);
        Assertions.assertEquals(dev.getDeviceUUID(), deviceFromApi.getDeviceUUID());
        Assertions.assertEquals(newDeviceName, deviceFromApi.getName());
    }

    @Test
    public void testSpecificDeviceGet() {

    }

    @Test
    public void testDeviceExecutionHistoryUpdate() {
        testRegisterDevice();

        InputDevice inputDevice = hub.getRegisteredInputDevices().get(0);

        Device device;
        try {
            device = hubAPI.updateDeviceSensorByID(inputDevice.getDeviceUUID(), "sensor#1", 123).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO HUB: Add to Device Update Execution History
        //TODO Update Execution History Size Assertion

        //InputDeviceAssertions.assertEquals(1, inputDevice.getSensorCount());
        Assertions.assertEquals("123", inputDevice.getSensorValue("sensor#1").toString());
    }
}
