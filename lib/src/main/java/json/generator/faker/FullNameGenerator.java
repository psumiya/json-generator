package json.generator.faker;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;

public record FullNameGenerator(FakerStringSupplier fakerStringSupplier) implements FieldGenerator {

    @Override
    public String getFieldName() {
        return "fullName";
    }

    @Override
    public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
        return fakerStringSupplier.apply(() -> fakerStringSupplier.faker().name().fullName());
    }

}
