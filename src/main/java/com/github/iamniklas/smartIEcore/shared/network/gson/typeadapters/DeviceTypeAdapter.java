package com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters;

import com.github.iamniklas.smartIEcore.hub.devices.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DeviceTypeAdapter implements JsonSerializer<Device>, JsonDeserializer<Device> {

    private static final String CLASS_META_KEY = "CLASS_META_KEY";

    @Override
    public JsonElement serialize(Device device, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("deviceUUID", device.getDeviceUUID());
        jsonObject.addProperty("name", device.getName());
        jsonObject.add("deviceAddress", context.serialize(device.getDeviceAddress(), DeviceAddress.class));
        jsonObject.addProperty("deviceType", device.getDeviceType().name());
        // Add other properties specific to the Device class if needed
        switch (device.getDeviceType()) {
            case INPUT:
                jsonObject.add("inputDeviceSpecification", context.serialize(((InputDevice)device).getInputDeviceSpecification()));
                jsonObject.add("inputDeviceType", context.serialize(((InputDevice)device).getInputDeviceType(), InputDeviceType.class));
                break;
            case OUTPUT:

                break;
        }
        // Make sure to handle null values properly if any field can be null
        return jsonObject;
    }

    @Override
    public Device deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String deviceUUID = jsonObject.get("deviceUUID").getAsString();
        String name = jsonObject.get("name").getAsString();
        DeviceAddress deviceAddress = context.deserialize(jsonObject.get("deviceAddress"), DeviceAddress.class);
        Device.DeviceType deviceType = Device.DeviceType.valueOf(jsonObject.get("deviceType").getAsString());

        if (deviceType == null) {
            throw new JsonParseException("DeviceType is not provided in the JSON.");
        }

        switch (deviceType) {
            case INPUT:
                InputDeviceType iDevType = context.deserialize(jsonObject.get("inputDeviceType"), InputDeviceType.class);
                InputDeviceSpecification spec = context.deserialize(jsonObject.get("inputDeviceSpecification"), InputDeviceSpecification.class);
                InputDevice iDev = new InputDevice(deviceUUID, name, iDevType, deviceAddress);
                iDev.setSpecification(spec);
                return iDev;
            case OUTPUT:
                return new OutputDevice(deviceUUID, name, OutputDeviceType.Other, deviceAddress);
            default:
                throw new JsonParseException("Unsupported device type: " + deviceType);
        }
    }
}
