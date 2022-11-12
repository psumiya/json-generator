package json.generator;

import com.fasterxml.jackson.databind.ObjectMapper;

public record JsonGeneratorModel(ObjectMapper objectMapper, int maxRandomArraySize) {

    private static final int MAX_RANDOM_ARRAY_SIZE = 10;

    public JsonGeneratorModel() {
        this(new ObjectMapper(), MAX_RANDOM_ARRAY_SIZE);
    }

    public JsonGeneratorModel(ObjectMapper objectMapper) {
        this(objectMapper, MAX_RANDOM_ARRAY_SIZE);
    }

    public JsonGeneratorModel(int maxRandomArraySize) {
        this(new ObjectMapper(), maxRandomArraySize);
    }

}
