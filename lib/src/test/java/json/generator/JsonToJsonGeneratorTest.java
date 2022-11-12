package json.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static json.generator.FileReader.getFileContentAsString;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JsonToJsonGeneratorTest {

    private static final String DEFAULT_ROOT = "src/test/resources/samples/";

    static Stream<Arguments> sampleFilesProvider() {
        return Stream.of(
                arguments(DEFAULT_ROOT + "root_is_object.json", new JsonGeneratorModel()),
                arguments(DEFAULT_ROOT + "root_is_array.json", new JsonGeneratorModel()),
                arguments(DEFAULT_ROOT + "root_is_array.json", new JsonGeneratorModel(20)),
                arguments(DEFAULT_ROOT + "root_is_object.json", new JsonGeneratorModel(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)))
        );
    }

    @ParameterizedTest
    @MethodSource("sampleFilesProvider")
    public void test(String fileName, JsonGeneratorModel jsonGeneratorModel) {
        JsonGenerator<String, String> jsonGenerator = new JsonToJsonGenerator(jsonGeneratorModel);
        String sample = getFileContentAsString(fileName);
        String generated = jsonGenerator.generate(sample);
        System.out.println(generated);
    }

}
