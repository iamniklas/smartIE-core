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

        // Create and return the appropriate Device subclass based on deviceType
        if (deviceType == null) {
            throw new JsonParseException("DeviceType is not provided in the JSON.");
        }

        switch (deviceType) {
            // Here, you should have logic to determine which subclass to instantiate
            // based on the deviceType read from JSON. For now, I'll assume you have
            // two subclasses named MobileDevice and LaptopDevice.
            case INPUT:
                InputDevice iDev = new InputDevice(deviceUUID, name, InputDeviceType.OtherInput, deviceAddress);
                return iDev;
            case OUTPUT:
                return new OutputDevice(deviceUUID, name, OutputDeviceType.Other, deviceAddress);
            default:
                throw new JsonParseException("Unsupported device type: " + deviceType);
        }
    }
}
