package json.generator.model;

import java.util.Map;

public record FieldConfiguration(String fieldName, String generatorName, Map<String, Object> parameters) {

    public FieldConfiguration(String fieldName, String generatorName) {
        this(fieldName, generatorName, Map.of());
    }

}
