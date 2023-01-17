package json.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import json.generator.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public record JsonToJsonGenerator(JsonGeneratorModel jsonGeneratorModel) implements JsonGenerator<String, String> {

    private static final String GENERATOR_SPEC_NODE_NAME = "___GENERATOR_SPEC";
    private static final String LOCALIZATION_SPEC_NODE_NAME = "___LOCALIZATION";
    private static final String FIELDS_SPEC_NODE_NAME = "___FIELDS";

    @Override
    public String generate(String input) {
        RandomizerInput randomizerInput = getRandomizerInput(input);
        JsonGenerator<RandomizerInput, JsonNode> jsonNodeGenerator = getJsonNodeGenerator();
        JsonNode randomized = jsonNodeGenerator.generate(randomizerInput);
        try {
            return jsonGeneratorModel.objectMapper().writeValueAsString(randomized);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonGenerator<RandomizerInput, JsonNode> getJsonNodeGenerator() {
        return new JsonNodeGenerator(jsonGeneratorModel);
    }

    private RandomizerInput getRandomizerInput(String input) {
        try {
            JsonNode jsonNode = jsonGeneratorModel.objectMapper().readTree(input);
            GeneratorSpec generatorSpec = getGeneratorSpec(jsonNode);
            if (jsonNode instanceof ObjectNode) {
                ((ObjectNode) jsonNode).remove(GENERATOR_SPEC_NODE_NAME);
            }
            return new RandomizerInput(generatorSpec, jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while reading RandomizerInput", e);
        }
    }

    private GeneratorSpec getGeneratorSpec(JsonNode jsonNode) {
        return getSpec(jsonNode, GENERATOR_SPEC_NODE_NAME, GeneratorSpec::new, (generatorSpecNode -> {
                    Localization l18NSpec = getLocalizationSpec(generatorSpecNode);
                    Fields fieldSpec = getFieldSpec(generatorSpecNode);
                    return new GeneratorSpec(l18NSpec, fieldSpec);
                })
        );
    }

    private Fields getFieldSpec(JsonNode generatorSpecNode) {
        return getSpec(generatorSpecNode, FIELDS_SPEC_NODE_NAME, Fields::new, (jsonNode -> {
            TypeFactory typeFactory = jsonGeneratorModel.objectMapper().getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, BaseSpec.class);
            try {
                Fields fieldSpec = new Fields(jsonGeneratorModel.objectMapper().readValue(jsonNode.toString(), mapType));
                Fields defaultFieldSpec = new Fields();
                Map<String, BaseSpec> fieldSpecMap = new HashMap<>(defaultFieldSpec.fieldSpecMap());
                fieldSpecMap.putAll(fieldSpec.fieldSpecMap());
                return new Fields(fieldSpecMap);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error while reading FieldSpec", e);
            }
        }));

    }

    private Localization getLocalizationSpec(JsonNode generatorSpecNode) {
        return getSpec(generatorSpecNode, LOCALIZATION_SPEC_NODE_NAME, Localization::new, (jsonNode -> {
            try {
                return jsonGeneratorModel.objectMapper().readValue(jsonNode.toString(), Localization.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error while reading LocalizationSpec", e);
            }
        }));
    }

    private static <R> R getSpec(JsonNode jsonNode, String fieldName, Supplier<R> defaultSupplier, Function<JsonNode, R> customSupplier) {
        JsonNode targetJsonNode = jsonNode.path(fieldName);
        if (targetJsonNode.isMissingNode()) {
            return defaultSupplier.get();
        } else {
            return customSupplier.apply(targetJsonNode);
        }
    }

}
