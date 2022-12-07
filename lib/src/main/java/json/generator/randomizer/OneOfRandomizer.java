package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.model.BaseSpec;

import java.util.List;
import java.util.Map;
import java.util.Random;

public record OneOfRandomizer() implements BaseSpecRandomizer<JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;
    private static final String VALUES = "values";

    @Override
    public JsonNode generate(BaseSpec baseSpec, JsonNode sampleValue) {
        Map<String, Object> parameters = baseSpec.parameters();
        Object values = parameters.get(VALUES);
        if (values instanceof List<?>) {
            List<?> possibleItems = (List<?>) values;
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
