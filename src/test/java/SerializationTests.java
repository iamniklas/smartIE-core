import com.github.iamniklas.hub.devices.DeviceAddress;
import com.github.iamniklas.hub.devices.InputDevice;
import com.github.iamniklas.hub.devices.InputDeviceType;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.implementations.ProxyRule;
import com.github.iamniklas.hub.rules.models.NumberComparator;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SerializationTests {
    @Test
    public void testRuleSerialization() {
        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700));
        System.out.println(new Gson().toJson(inputDevice));

        RuleData ruleData = new RuleData();
        ruleData.numberComparator = NumberComparator.GREATER_EQUALS;
        Rule rule = new ProxyRule(null, "Proxy Rule", ruleData, null);
        System.out.println(new Gson().toJson(rule));
    }
}