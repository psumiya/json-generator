package json.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record JsonToJsonGenerator(JsonGeneratorModel jsonGeneratorModel) implements JsonGenerator<String, String> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public String generate(String input) {
        try {
            ObjectMapper objectMapper = jsonGeneratorModel.objectMapper();
            JsonNode randomized = randomize(objectMapper.readTree(input));
            return objectMapper.writeValueAsString(randomized);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode randomize(JsonNode jsonNode) {
        JsonNodeType nodeType = jsonNode.getNodeType();
        Random random = new Random();
        switch (nodeType) {
            case NUMBER -> {
                return JSON_NODE_FACTORY.numberNode(random.nextInt(Integer.MAX_VALUE));
            }
            case STRING -> {
                return JSON_NODE_FACTORY.textNode(RandomStringUtils.randomAlphabetic(random.nextInt(10)));
            }
            case BOOLEAN -> {
                return JSON_NODE_FACTORY.booleanNode(random.nextBoolean());
            }
            case OBJECT -> jsonNode.fields().forEachRemaining(field -> {
                if (field.getValue().isValueNode()) {
                    JsonNodeType fieldNodeType = field.getValue().getNodeType();
                    switch (fieldNodeType) {
                        case NUMBER -> field.setValue(JSON_NODE_FACTORY.numberNode(random.nextInt(Integer.MAX_VALUE)));
                        case STRING -> field.setValue(JSON_NODE_FACTORY.textNode(RandomStringUtils.randomAlphabetic(random.nextInt(10))));
                        case BOOLEAN -> field.setValue(JSON_NODE_FACTORY.booleanNode(random.nextBoolean()));
                    }
                } else {
                    field.setValue(randomize(field.getValue()));
                }
            });
            case ARRAY -> {
                int size = jsonNode.size();
                if (size == 0) {
                    throw new IllegalArgumentException("Empty array provided as input. Cannot use empty array to deduce array items.");
                }
                JsonNode sample = jsonNode.get(0);
                int newSize = random.nextInt(jsonGeneratorModel.maxRandomArraySize());
                List<JsonNode> nodeList = new ArrayList<>(newSize);
                for (int i = 0; i < newSize; i++) {
                    nodeList.add(randomize(sample.deepCopy()));
                }
                ((ArrayNode) jsonNode).addAll(nodeList);
            }
        }
        return jsonNode;
    }

}
