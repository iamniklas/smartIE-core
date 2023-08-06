import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.devices.DeviceAddress;
import com.github.iamniklas.smartIEcore.hub.devices.InputDevice;
import com.github.iamniklas.smartIEcore.hub.devices.InputDeviceType;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.implementations.ProxyRule;
import com.github.iamniklas.smartIEcore.hub.rules.models.NumberComparator;
import com.github.iamniklas.smartIEcore.hub.rules.models.RecurringMode;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SerializationTests {
    Gson gson;

    @Test
    public void testInputDeviceSerialization() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Device.class, new DeviceTypeAdapter());
        gson = builder.create();

        InputDevice inputDevice = new InputDevice(UUID.randomUUID().toString(), "input_device_1", InputDeviceType.Sensor, new DeviceAddress("0.0.0.0", "hostname", 5700));
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
        Assertions.assertEquals(inputDevice.getInputDeviceType(), castedDevice.getInputDeviceType());
        Assertions.assertEquals(
                Integer.parseInt(inputDevice.getInputDeviceSpecification().getSpecification("sensor#1").toString()),
                Math.round(Double.parseDouble(castedDevice.getInputDeviceSpecification().getSpecification("sensor#1").toString()))
        );
    }

    @Test
    public void testOutputDeviceSerialization() {

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
