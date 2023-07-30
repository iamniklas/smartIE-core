import com.github.iamniklas.hub.devices.DeviceAddress;
import com.github.iamniklas.hub.devices.InputDevice;
import com.github.iamniklas.hub.devices.InputDeviceType;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.implementations.ProxyRule;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SerializationTests {
    @Test
    public void serialize() {
        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700));

        String output = new Gson().toJson(inputDevice);

        ProxyRule rule = new ProxyRule(null, "Proxy Rule", null, null);

        output = new Gson().toJson(rule);
    }
}
