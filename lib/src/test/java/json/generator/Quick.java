package json.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import json.generator.model.JsonGeneratorModel;
import org.junit.jupiter.api.Test;

import static json.generator.FileReader.getFileContentAsString;

public class Quick {

    private static String FILE_NAME = "src/test/resources/samples/fullSpecWithAllTypes.json";

    private static JsonGeneratorModel JSON_GENERATOR_MODEL = new JsonGeneratorModel(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));

    private static String FULL_SPEC = getFileContentAsString(FILE_NAME);

    @Test
    public void test() {
        JsonToJsonGenerator jsonToJsonGenerator = new JsonToJsonGenerator(JSON_GENERATOR_MODEL);
        String generate = jsonToJsonGenerator.generate(FULL_SPEC);
        System.out.println(generate);
    }

}
