package json.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import json.generator.api.Generator;
import json.generator.model.JsonGeneratorModel;
import json.generator.model.Localization;
import json.generator.factory.FieldGeneratorFactory;
import json.generator.model.FieldConfiguration;
import json.generator.model.RandomizerInput;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public record JsonNodeGenerator(JsonGeneratorModel jsonGeneratorModel) implements Generator<RandomizerInput, JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public JsonNode generate(RandomizerInput input) {

        Localization localizationSpec = input.generatorSpec().localizationSpec();
        Locale locale = new Locale.Builder()
                .setLanguage(localizationSpec.language())
                .setRegion(localizationSpec.country())
                .build();
        Faker faker = new Faker(locale);

        List<FieldGenerator> fieldGenerators = FieldGeneratorFactory.initializeGenerators(faker);
        Map<String, FieldGenerator> fieldGeneratorMap = getFieldGeneratorMap(fieldGenerators);

        Map<String, FieldConfiguration> fieldConfigurationMap = getFieldConfigurationMap(input.generatorSpec().fieldConfigurations());

        Random random = new Random();
        return randomize(input.objectToRandomize(), input, random, fieldConfigurationMap, fieldGeneratorMap);
    }

    private Map<String, FieldConfiguration> getFieldConfigurationMap(List<FieldConfiguration> fieldConfigurations) {
        Map<String, FieldConfiguration> map = new HashMap<>();
        for (FieldConfiguration fieldConfiguration : fieldConfigurations) {
            map.put(fieldConfiguration.fieldName(), fieldConfiguration);
        }
        return map;
    }

    private Map<String, FieldGenerator> getFieldGeneratorMap(List<FieldGenerator> fieldGenerators) {
        Map<String, FieldGenerator> map = new HashMap<>();
        for (FieldGenerator fieldGenerator : fieldGenerators) {
            map.put(fieldGenerator.getFieldName(), fieldGenerator);
        }
        return map;
    }

    private JsonNode randomize(JsonNode jsonNode, RandomizerInput input, Random random, Map<String, FieldConfiguration> fieldConfigurationMap, Map<String, FieldGenerator> fieldGeneratorMap) {
        JsonNodeType nodeType = jsonNode.getNodeType();
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
            case OBJECT -> jsonNode.fields().forEachRemaining(field -> randomizeField(random, field, input, fieldConfigurationMap, fieldGeneratorMap));
            case ARRAY -> {
                int size = jsonNode.size();
                if (size == 0) {
                    throw new IllegalArgumentException("Empty array provided as input. Cannot use empty array to deduce array items.");
                }
                JsonNode sample = jsonNode.get(random.nextInt(size));
                int newSize = random.nextInt(jsonGeneratorModel.maxRandomArraySize());
                List<JsonNode> nodeList = new ArrayList<>(newSize);
                for (int i = 0; i < newSize; i++) {
                    nodeList.add(randomize(sample.deepCopy(), input, random, fieldConfigurationMap, fieldGeneratorMap));
                }
                ((ArrayNode) jsonNode).addAll(nodeList);
            }
        }
        return jsonNode;
    }

    private void randomizeField(Random random, Map.Entry<String, JsonNode> field, RandomizerInput input, Map<String, FieldConfiguration> fieldConfigurationMap, Map<String, FieldGenerator> fieldGeneratorMap) {
        if (fieldConfigurationMap.containsKey(field.getKey())) {
            FieldConfiguration fieldConfiguration = fieldConfigurationMap.get(field.getKey());
            FieldGenerator fieldGenerator = fieldGeneratorMap.get(fieldConfiguration.generatorName());
            JsonNode fieldValue = Optional.ofNullable(fieldGenerator.generate(fieldConfiguration, field.getValue()))
                    .orElse(JSON_NODE_FACTORY.missingNode());
            field.setValue(fieldValue);
        } else if (fieldGeneratorMap.containsKey(field.getKey())) {
            FieldConfiguration fieldConfiguration = fieldConfigurationMap.get(field.getKey());
            FieldGenerator fieldGenerator = fieldGeneratorMap.get(field.getKey());
            JsonNode fieldValue = Optional.ofNullable(fieldGenerator.generate(fieldConfiguration, field.getValue()))
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
            field.setValue(randomize(field.getValue(), input, random, fieldConfigurationMap, fieldGeneratorMap));
        }
    }

}
