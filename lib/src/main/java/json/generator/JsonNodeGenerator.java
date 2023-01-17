package json.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import json.generator.model.BaseSpec;
import json.generator.model.JsonGeneratorModel;
import json.generator.model.Localization;
import json.generator.model.RandomizerInput;
import json.generator.randomizer.RandomizerType;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public record JsonNodeGenerator(JsonGeneratorModel jsonGeneratorModel) implements JsonGenerator<RandomizerInput, JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public JsonNode generate(RandomizerInput input) {
        initialize(input);
        Random random = new Random();
        return randomize(input.objectToRandomize(), input, random);
    }

    private void initialize(RandomizerInput input) {
        Localization localizationSpec = input.generatorSpec().localizationSpec();
        Locale locale = new Locale.Builder()
                .setLanguage(localizationSpec.language())
                .setRegion(localizationSpec.country())
                .build();
        Faker faker = new Faker(locale);
        RandomizerType.initializeProviders(faker);
    }

    private JsonNode randomize(JsonNode jsonNode, RandomizerInput input, Random random) {
        JsonNodeType nodeType = jsonNode.getNodeType();
        Map<String, BaseSpec> fieldSpecMap = input.generatorSpec().fieldSpec().fieldSpecMap();
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
            case OBJECT -> jsonNode.fields().forEachRemaining(field -> randomizeField(random, field, input, fieldSpecMap));
            case ARRAY -> {
                int size = jsonNode.size();
                if (size == 0) {
                    throw new IllegalArgumentException("Empty array provided as input. Cannot use empty array to deduce array items.");
                }
                JsonNode sample = jsonNode.get(random.nextInt(size));
                int newSize = random.nextInt(jsonGeneratorModel.maxRandomArraySize());
                List<JsonNode> nodeList = new ArrayList<>(newSize);
                for (int i = 0; i < newSize; i++) {
                    nodeList.add(randomize(sample.deepCopy(), input, random));
                }
                ((ArrayNode) jsonNode).addAll(nodeList);
            }
        }
        return jsonNode;
    }

    private void randomizeField(Random random, Map.Entry<String, JsonNode> field, RandomizerInput input, Map<String, BaseSpec> fieldSpecMap) {
        if (fieldSpecMap.containsKey(field.getKey())) {
            BaseSpec baseSpec = fieldSpecMap.get(field.getKey());
            RandomizerType type = baseSpec.type();
            JsonNode fieldValue = Optional.ofNullable(RandomizerType.BASE_SPEC_PROVIDER_MAP.get(type))
                    .map(randomizerType -> randomizerType.generate(baseSpec, field.getValue()))
                    .orElse(JSON_NODE_FACTORY.missingNode());
            field.setValue(fieldValue);
        } else if (field.getValue().isValueNode()) {
            JsonNodeType fieldNodeType = field.getValue().getNodeType();
            switch (fieldNodeType) {
                case NUMBER -> field.setValue(JSON_NODE_FACTORY.numberNode(random.nextInt(Integer.MAX_VALUE)));
                case STRING -> field.setValue(JSON_NODE_FACTORY.textNode(RandomStringUtils.randomAlphabetic(random.nextInt(10))));
                case BOOLEAN -> field.setValue(JSON_NODE_FACTORY.booleanNode(random.nextBoolean()));
            }
        } else {
            field.setValue(randomize(field.getValue(), input, random));
        }
    }

}
