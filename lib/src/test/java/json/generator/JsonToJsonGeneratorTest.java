package json.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.generator.model.JsonGeneratorModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static json.generator.FileReader.getFileContentAsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JsonToJsonGeneratorTest {

    private static final String DEFAULT_ROOT = "src/test/resources/samples/";

    private static final String GENERATOR_SPEC_NODE_NAME = "___GENERATOR_SPEC";

    static Stream<Arguments> sampleFilesProvider() {
        return Stream.of(
                arguments(DEFAULT_ROOT + "root_is_object.json", new JsonGeneratorModel()),
                arguments(DEFAULT_ROOT + "root_is_array.json", new JsonGeneratorModel()),
                arguments(DEFAULT_ROOT + "root_is_array.json", new JsonGeneratorModel(20)),
                arguments(DEFAULT_ROOT + "root_is_object.json", new JsonGeneratorModel(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT))),
                arguments(DEFAULT_ROOT + "with_spec_root_is_object.json", new JsonGeneratorModel())
        );
    }

    @ParameterizedTest
    @MethodSource("sampleFilesProvider")
    public void test(String fileName, JsonGeneratorModel jsonGeneratorModel) throws JsonProcessingException {
        JsonGenerator<String, String> jsonGenerator = new JsonToJsonGenerator(jsonGeneratorModel);
        String sample = getFileContentAsString(fileName);
        JsonNode original = jsonGeneratorModel.objectMapper().readTree(sample);
        if (original instanceof ObjectNode) {
            // Remove spec before comparison
            ((ObjectNode) original).remove(GENERATOR_SPEC_NODE_NAME);
        }
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        JsonKeyComparator jsonKeyComparator = new JsonKeyComparator();
        integers.forEach(item -> {
            String generated = jsonGenerator.generate(sample);
            try {
                JsonNode randomized = jsonGeneratorModel.objectMapper().readTree(generated);
                assertEquals(0, jsonKeyComparator.compare(original, randomized));
            } catch (JsonProcessingException e) {
                fail("JsonProcessingException occurred." + e);
            }
        });
    }

}
