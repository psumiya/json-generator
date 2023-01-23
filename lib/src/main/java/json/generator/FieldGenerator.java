package json.generator;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.model.FieldConfiguration;

public interface FieldGenerator {

    String getFieldName();
    JsonNode generate(FieldConfiguration configuration, JsonNode seedValue);

}
