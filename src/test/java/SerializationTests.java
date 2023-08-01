import com.github.iamniklas.hub.devices.DeviceAddress;
import com.github.iamniklas.hub.devices.InputDevice;
import com.github.iamniklas.hub.devices.InputDeviceType;
import com.github.iamniklas.hub.rules.Rule;
import com.github.iamniklas.hub.rules.implementations.ProxyRule;
import com.github.iamniklas.hub.rules.models.NumberComparator;
import com.github.iamniklas.hub.rules.models.RecurringMode;
import com.github.iamniklas.hub.rules.models.RuleData;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

public class SerializationTests {

    @Test
    public void testDeviceSerialization() {
        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700));
        System.out.println(new Gson().toJson(inputDevice));
    }

    @Test
    public void testRuleSerialization() {
        RuleData ruleData = new RuleData();
        ruleData.numberComparator = NumberComparator.GREATER_EQUALS;
        ruleData.recurringMode = RecurringMode.YEARLY;
        ruleData.recurringModifier = 45;
        ruleData.startValue = 444;
        ruleData.targetValue = 888;
        ruleData.timeInMs = 1234;

        Rule rule = new ProxyRule(null, "Proxy Rule", ruleData, null);

        System.out.println(new Gson().toJson(rule));
    }
}