package json.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.generator.api.Generator;
import json.generator.model.JsonGeneratorModel;
import json.generator.model.Localization;
import json.generator.model.FieldConfiguration;
import json.generator.model.GeneratorConfiguration;
import json.generator.model.RandomizerInput;

import java.util.List;

/**
 * The json generation model.
 *
 * @param jsonGeneratorModel the json generator model
 */
public record JsonToJsonGenerator(JsonGeneratorModel jsonGeneratorModel) implements Generator<String, String> {

    private static final String GENERATOR_SPEC_NODE_NAME = "___GENERATOR_SPEC";
    private static final String LOCALIZATION_SPEC_NODE_NAME = "___LOCALIZATION";
    private static final String FIELDS_SPEC_NODE_NAME = "___FIELDS";

    @Override
    public String generate(String input) {

        try {

            JsonNode jsonNode = jsonGeneratorModel.objectMapper().readTree(input);

            JsonNode generatorSpecJsonNode = jsonNode.path(GENERATOR_SPEC_NODE_NAME);

            GeneratorConfiguration generatorConfiguration;

            if (!generatorSpecJsonNode.isEmpty()) {
                JsonNode localizationSpecJsonNode = generatorSpecJsonNode.path(LOCALIZATION_SPEC_NODE_NAME);
                Localization localization = getLocalizationSpec(localizationSpecJsonNode);

                JsonNode fieldsSpecJsonNode = generatorSpecJsonNode.path(FIELDS_SPEC_NODE_NAME);
                List<FieldConfiguration> fieldConfigurations = getFieldsSpec(fieldsSpecJsonNode);

                generatorConfiguration = new GeneratorConfiguration(localization, fieldConfigurations);

            } else {
                generatorConfiguration = new GeneratorConfiguration(new Localization(), List.of());
            }

            // Changing original input, i.e., mutable operation
            if (jsonNode instanceof ObjectNode) {
                ((ObjectNode) jsonNode).remove(GENERATOR_SPEC_NODE_NAME);
            }

            RandomizerInput randomizerInput = new RandomizerInput(generatorConfiguration, jsonNode);

            JsonNodeGenerator jsonNodeGenerator = new JsonNodeGenerator(jsonGeneratorModel);

            JsonNode randomized = jsonNodeGenerator.generate(randomizerInput);

            return jsonGeneratorModel.objectMapper().writeValueAsString(randomized);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private Localization getLocalizationSpec(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode.isEmpty()) {
            return new Localization();
        }
        return jsonGeneratorModel.objectMapper().readValue(jsonNode.toString(), Localization.class);
    }

    private List<FieldConfiguration> getFieldsSpec(JsonNode jsonNode) throws JsonProcessingException {
        return jsonGeneratorModel.objectMapper().readValue(jsonNode.toString(), new TypeReference<>() {});
    }

}
