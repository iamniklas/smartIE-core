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
        if(device.getDeviceSpecification() != null) {
            jsonObject.add("deviceSpecification", context.serialize((device).getDeviceSpecification(), DeviceSpecification.class));
            /*JsonObject spec = new JsonObject();
            for (String key : device.getDeviceSpecification().getSpecificationFields()) {
                Object value = device.getDeviceSpecification().getSpecification(key);
                spec.add(key, context.serialize(value));
            }
            jsonObject.add("deviceSpecification", spec);*/
        }
        // Add other properties specific to the Device class if needed
        switch (device.getDeviceType()) {
            case INPUT:
                jsonObject.add("inputDeviceType", context.serialize(((InputDevice)device).getInputDeviceType(), InputDeviceType.class));
                break;
            case OUTPUT:
                jsonObject.add("outputDeviceType", context.serialize(((OutputDevice)device).getType(), OutputDeviceType.class));

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
        DeviceSpecification spec = null;
        if(jsonObject.get("deviceSpecification") != null) {
            spec = context.deserialize(jsonObject.get("deviceSpecification"), DeviceSpecification.class);
        }

        switch (deviceType) {
            case INPUT:
                InputDeviceType iDevType = context.deserialize(jsonObject.get("inputDeviceType"), InputDeviceType.class);
                InputDevice iDev = new InputDevice(deviceUUID, name, iDevType, deviceAddress);
                iDev.setSpecification(spec);
                return iDev;
            case OUTPUT:
                OutputDeviceType oDevType = context.deserialize(jsonObject.get("outputDeviceType"), OutputDeviceType.class);
                OutputDevice oDev = new OutputDevice(deviceUUID, name, oDevType, deviceAddress);
                oDev.setSpecification(spec);
                return oDev;
            default:
                throw new JsonParseException("Unsupported device type: " + deviceType);
        }
    }
}
