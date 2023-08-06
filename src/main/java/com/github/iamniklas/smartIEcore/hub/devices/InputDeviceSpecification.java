package com.github.iamniklas.smartIEcore.hub.devices;

import java.util.HashMap;
import java.util.Map;

public class InputDeviceSpecification {
    private Map<String, Object> specification = new HashMap<>();

    public InputDeviceSpecification(String[] specification) {
        for (String s : specification) {
            this.specification.put(s, new Object());
        }
    }

    public int getSpecificationSize() {
        return specification.size();
    }
    public String[] getSpecificationFields() { return specification.keySet().toArray(new String[0]); }

    public Object getSpecification(String key) { return specification.get(key); }
    public void setSpecification(String key, Object val) { specification.put(key, val); }
}
