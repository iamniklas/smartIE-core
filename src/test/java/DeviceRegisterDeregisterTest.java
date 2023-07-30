import com.github.iamniklas.device.input.InputDevice;
import com.github.iamniklas.device.output.OutputDevice;
import com.github.iamniklas.hub.SmartIEHub;
import com.github.iamniklas.hub.devices.Device;
import com.github.iamniklas.hub.network.IInputDeviceAPI;
import com.github.iamniklas.hub.network.IOutputDeviceAPI;
import com.github.iamniklas.hub.network.ISmartIEAPI;
import com.github.iamniklas.nettools.scanner.AcceleratedNetworkScanner;
import com.google.gson.Gson;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class DeviceRegisterDeregisterTest {

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
                .baseUrl("http://localhost:"+SmartIEHub.DEBUG_PORT)
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
    public void testRegister() {

        //HUB: Scan for available devices
        List<com.github.iamniklas.hub.devices.InputDevice> foundInputDevices;
        try {
            foundInputDevices = hubAPI.scanForInputDevices().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(foundInputDevices);
        Assertions.assertEquals(1, foundInputDevices.size());
        List<com.github.iamniklas.hub.devices.OutputDevice> foundOutputDevices;
        try {
            foundOutputDevices = hubAPI.scanForOutputDevices().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(foundOutputDevices);
        Assertions.assertEquals(1, foundOutputDevices.size());


        //HUB: Register new unknown device
        HttpStatus status;
        try {
            status = hubAPI.newDevice(foundInputDevices.get(0)).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(status, HttpStatus.OK);
        try {
            status = hubAPI.newDevice(foundOutputDevices.get(0)).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(status, HttpStatus.OK);


        //HUB: Check registered device count equals 1
        Integer deviceCount = -1;
        try {
            deviceCount = hubAPI.getTotalDeviceCount().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(2, deviceCount);
    }

    @Test
    public void testDeregister() {
        //HUB: Deregister known device

        //HUB: Check registered device count equals 0
    }
}
