package json.generator.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;

import java.util.UUID;

public class RandomUUIDGenerator implements FieldGenerator {

    @Override
    public String getFieldName() {
        return "RANDOM_UUID";
    }

    @Override
    public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
        return JsonNodeFactory.instance.textNode(UUID.randomUUID().toString());
    }

}
