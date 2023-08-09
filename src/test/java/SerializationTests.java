import com.github.iamniklas.smartIEcore.hub.devices.*;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.implementations.FunctionRule;
import com.github.iamniklas.smartIEcore.hub.rules.models.NumberComparator;
import com.github.iamniklas.smartIEcore.hub.rules.models.RecurringMode;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.shared.network.gson.creator.GsonSerializer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SerializationTests {
    static Gson gson;

    @BeforeAll
    public static void initialize() {
        gson = GsonSerializer.newGsonConverter();
    }

    @Test
    public void testInputDeviceSerialization() {
        Device inputDevice = new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700));
        inputDevice.setSpecificationValue("sensor#1", 123);

        String serializedDevice = gson.toJson(inputDevice);
        System.out.println(serializedDevice);

        Device deserializedInputDevice = gson.fromJson(serializedDevice, Device.class);

        InputDevice castedDevice = (InputDevice) deserializedInputDevice;

        Assertions.assertEquals(inputDevice.getDeviceUUID(), deserializedInputDevice.getDeviceUUID());
        Assertions.assertEquals(inputDevice.getName(), deserializedInputDevice.getName());
        Assertions.assertEquals(inputDevice.getDeviceAddress().ip, deserializedInputDevice.getDeviceAddress().ip);
        Assertions.assertEquals(inputDevice.getDeviceAddress().hostname, deserializedInputDevice.getDeviceAddress().hostname);
        Assertions.assertEquals(inputDevice.getDeviceAddress().port, deserializedInputDevice.getDeviceAddress().port);
        Assertions.assertEquals(inputDevice.getDeviceType(), deserializedInputDevice.getDeviceType());
        Assertions.assertEquals(
                Integer.parseInt(inputDevice.getDeviceSpecification().getSpecification("sensor#1").toString()),
                Math.round(Double.parseDouble(castedDevice.getDeviceSpecification().getSpecification("sensor#1").toString()))
        );
    }

    @Test
    public void testOutputDeviceSerialization() {
        OutputDevice outputDevice = new OutputDevice(UUID.randomUUID().toString(), "output_device_1", OutputDeviceType.Light, new DeviceAddress("0.0.0.0", "hostname", 5700));

        String serializedDevice = gson.toJson(outputDevice);
        System.out.println(serializedDevice);

        Device deserializedInputDevice = gson.fromJson(serializedDevice, Device.class);

        OutputDevice castedDevice = (OutputDevice) deserializedInputDevice;

        Assertions.assertEquals(outputDevice.getDeviceUUID(), deserializedInputDevice.getDeviceUUID());
        Assertions.assertEquals(outputDevice.getName(), deserializedInputDevice.getName());
        Assertions.assertEquals(outputDevice.getDeviceAddress().ip, deserializedInputDevice.getDeviceAddress().ip);
        Assertions.assertEquals(outputDevice.getDeviceAddress().hostname, deserializedInputDevice.getDeviceAddress().hostname);
        Assertions.assertEquals(outputDevice.getDeviceAddress().port, deserializedInputDevice.getDeviceAddress().port);
        Assertions.assertEquals(outputDevice.getDeviceType(), deserializedInputDevice.getDeviceType());
        Assertions.assertEquals(outputDevice.getType(), castedDevice.getType());
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

        Rule rule = new FunctionRule(UUID.randomUUID().toString(), null, "FunctionRule", ruleData, null);

        String serializedRule = gson.toJson(rule);
        System.out.println(serializedRule);

        Rule deserializedRule = gson.fromJson(serializedRule, Rule.class);

        System.out.println(deserializedRule);
    }
}
