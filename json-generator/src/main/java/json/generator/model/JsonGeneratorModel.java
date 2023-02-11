package json.generator.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The generator model.
 *
 * @param objectMapper the Jackson object mapper to use
 * @param maxRandomArraySize max random array size to generate
 */
public record JsonGeneratorModel(ObjectMapper objectMapper, int maxRandomArraySize) {

    private static final int DEFAULT_MAX_ARRAY_SIZE = 10;
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Use when all params are known
     */
    public JsonGeneratorModel() {
        this(DEFAULT_MAPPER, DEFAULT_MAX_ARRAY_SIZE);
    }

    /**
     * Use when a custom object mapper is needed
     *
     * @param objectMapper the custom object mapper
     */
    public JsonGeneratorModel(ObjectMapper objectMapper) {
        this(objectMapper, DEFAULT_MAX_ARRAY_SIZE);
    }

    /**
     * Use to define the maxRandomArraySize
     * @param maxRandomArraySize defines the max number of items that will be randomly generated for array types
     */
    public JsonGeneratorModel(int maxRandomArraySize) {
        this(DEFAULT_MAPPER, maxRandomArraySize);
    }

}
