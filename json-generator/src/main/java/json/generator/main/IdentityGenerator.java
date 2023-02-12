package json.generator.main;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;

/**
 * Mimics Identity function
 */
class IdentityGenerator implements FieldGenerator {

    @Override
    public String getFieldName() {
        return "IDENTITY";
    }

    @Override
    public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
        return seedValue;
    }

}
