package json.generator.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public record JsonGeneratorModel(ObjectMapper objectMapper, int maxRandomArraySize) {

    private static final int DEFAULT_MAX_ARRAY_SIZE = 10;
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public JsonGeneratorModel() {
        this(DEFAULT_MAPPER, DEFAULT_MAX_ARRAY_SIZE);
    }

    public JsonGeneratorModel(ObjectMapper objectMapper) {
        this(objectMapper, DEFAULT_MAX_ARRAY_SIZE);
    }

    public JsonGeneratorModel(int maxRandomArraySize) {
        this(DEFAULT_MAPPER, maxRandomArraySize);
    }

}
