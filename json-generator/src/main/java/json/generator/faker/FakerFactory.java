package json.generator.faker;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class FakerFactory {

    public static List<FieldGenerator> buildFakerGenerators(Faker faker) {
        FakerStringSupplier fakerStringSupplier = new FakerStringSupplier(faker);
        List<FieldGenerator> fakerGenerators = new ArrayList<>();
        for (FakerType fakerType : FakerType.values()) {

            fakerGenerators.add(new FieldGenerator() {

                @Override
                public String getFieldName() {
                    return fakerType.fieldKey;
                }

                @Override
                public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
                    return fakerStringSupplier.apply(() -> fakerType.supplier.apply(faker));
                }

            });

        }
        return fakerGenerators;
    }

}
