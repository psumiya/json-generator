package json.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import json.generator.model.JsonGeneratorModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static json.generator.FileReader.getFileContentAsString;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FullSpecTest {

    private static String FILE_NAME = "src/test/resources/samples/fullSpecWithAllTypes.json";

    private static JsonGeneratorModel JSON_GENERATOR_MODEL = new JsonGeneratorModel(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));
    private static JsonGenerator<String, String> JSON_GENERATOR = new JsonToJsonGenerator(JSON_GENERATOR_MODEL);

    private static String FULL_SPEC_fr_FR = getFileContentAsString(FILE_NAME);

    static Stream<Arguments> fullSpecProviders() {
        return Stream.of(
                arguments("french-France", FULL_SPEC_fr_FR),
                arguments("french-Canada", FULL_SPEC_fr_FR.replace("\"country\": \"FR\"", "\"country\": \"CA\"")),
                arguments("english-US", FULL_SPEC_fr_FR
                        .replace("\"language\": \"fr\"", "\"language\": \"en\"")
                        .replace("\"country\": \"FR\"", "\"country\": \"US\""))
        );
    }

    @ParameterizedTest
    @MethodSource("fullSpecProviders")
    public void test(String locale, String spec) {
        String result = JSON_GENERATOR.generate(spec);
        System.out.println("Localization language-region :: " + locale);
        System.out.println(result);
    }

}
