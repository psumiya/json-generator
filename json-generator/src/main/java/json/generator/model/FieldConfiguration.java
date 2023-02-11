package json.generator.model;

import java.util.Map;

/**
 * The field configuration.
 *
 * @param fieldName the field name
 * @param generatorName the generator name
 * @param parameters any parameters the generator may need
 */
public record FieldConfiguration(String fieldName, String generatorName, Map<String, Object> parameters) {

    /**
     * Use when no parameters are needed
     *
     * @param fieldName the field name
     * @param generatorName the generator name
     */
    public FieldConfiguration(String fieldName, String generatorName) {
        this(fieldName, generatorName, Map.of());
    }

}
