package com.github.iamniklas.smartIEcore.shared.network.gson.creator;

import com.github.iamniklas.smartIEcore.hub.devices.Device;
import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.DeviceTypeAdapter;
import com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters.RuleTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSerializer {
    public static Gson newGsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Rule.class, new RuleTypeAdapter());
        builder.registerTypeAdapter(Device.class, new DeviceTypeAdapter());
        return builder.create();
    }
}
