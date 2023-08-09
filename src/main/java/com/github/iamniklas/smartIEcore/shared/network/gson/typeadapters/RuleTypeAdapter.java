package com.github.iamniklas.smartIEcore.shared.network.gson.typeadapters;

import com.github.iamniklas.smartIEcore.hub.rules.Rule;
import com.github.iamniklas.smartIEcore.hub.rules.implementations.*;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleData;
import com.github.iamniklas.smartIEcore.hub.rules.models.RuleType;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RuleTypeAdapter implements JsonSerializer<Rule>, JsonDeserializer<Rule> {

    @Override
    public JsonElement serialize(Rule src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ruleUUID", src.getRuleUUID());
        jsonObject.addProperty("ruleName", src.getRuleName());
        jsonObject.addProperty("ruleType", src.getRuleType().toString());
        jsonObject.add("ruleData", context.serialize(src.getRuleData()));
        // You might want to include more properties here

        return jsonObject;
    }

    @Override
    public Rule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String ruleUUID = jsonObject.get("ruleUUID").getAsString();
        String ruleName = jsonObject.get("ruleName").getAsString();
        RuleType ruleType = RuleType.valueOf(jsonObject.get("ruleType").getAsString());
        RuleData ruleData = context.deserialize(jsonObject.get("ruleData"), RuleData.class);
        // You might need to deserialize more properties here

        // Instantiate and return the appropriate subclass of Rule based on ruleType
        switch (ruleType) {

            case Recurring: return new RecurringRule(ruleUUID, null, ruleName, ruleData, null);
            case Trigger: return new TimeTriggerRule(ruleUUID, null, ruleName, ruleData, null);
            case ValueComparison: return new ValueComparisonRule(ruleUUID, null, ruleName, ruleData, null);
            case Function: return new FunctionRule(ruleUUID, null, ruleName, ruleData, null);
            case Proxy: return new ProxyRule(ruleUUID, null, ruleName, ruleData, null);
        }
        // Add more cases for different Rule types
        return null;
    }
}
