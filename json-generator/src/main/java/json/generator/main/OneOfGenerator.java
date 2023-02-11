package json.generator.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Generate one of the given values
 */
class OneOfGenerator implements FieldGenerator {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;
    private static final String VALUES = "values";

    @Override
    public String getFieldName() {
        return "ONE_OF";
    }

    @Override
    public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
        Map<String, Object> parameters = configuration.parameters();
        Object values = parameters.get(VALUES);
        if (values instanceof List<?> possibleItems) {
            int size = possibleItems.size();
            int nextInt = new Random().nextInt(size);
            Object o = possibleItems.get(nextInt);
            if (o instanceof String) {
                return JSON_NODE_FACTORY.textNode((String) o);
            } else if (o instanceof Integer) {
                return JSON_NODE_FACTORY.numberNode((Integer) o);
            }
        }
        return JSON_NODE_FACTORY.missingNode();
    }

}
