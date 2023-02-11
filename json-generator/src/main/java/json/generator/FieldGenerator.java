package json.generator;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.model.FieldConfiguration;

/**
 * Field generator
 */
public interface FieldGenerator {

    /**
     * Provide the field name this generator generates values for
     *
     * @return the field name this generator generates values for
     */
    String getFieldName();

    /**
     * Generate a JsonNode for the field
     *
     * @param configuration the field configuration to use for generation
     * @param seedValue a seed value to use for generation
     * @return generated JsonNode
     */
    JsonNode generate(FieldConfiguration configuration, JsonNode seedValue);

}
