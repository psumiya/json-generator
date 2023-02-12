package json.generator.faker;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.FieldGenerator;
import json.generator.model.FieldConfiguration;
import net.datafaker.AbstractProvider;
import net.datafaker.Address;
import net.datafaker.Faker;
import net.datafaker.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Factory to generate faker field generators
 */
public final class FakerFactory {

    private FakerFactory() {
    }

    /**
     * Build faker generators.
     *
     * @param faker the faker instance
     * @return list of faker field generators
     */
    public static List<FieldGenerator> buildFakerGenerators(Faker faker) {
        FakerStringSupplier fakerStringSupplier = new FakerStringSupplier(faker);
        Map<String, AbstractProvider> fakerGroupProviders = Map.of(
                "name", faker.name(),
                "address", faker.address(),
                "business", faker.business(),
                "book", faker.book()
        );
        List<FieldGenerator> fakerGenerators = new ArrayList<>();
        for (FakerType fakerType : FakerType.values()) {

            FieldGenerator fieldGenerator = Optional.ofNullable(fakerType.groupName)
                    .map(groupName -> fakerGroupProviders.get(groupName))
                    .map(abstractProvider -> getFieldGenerator(fakerStringSupplier, fakerType, abstractProvider))
                    .orElse(getDefaultFieldGenerator(faker, fakerStringSupplier, fakerType));
            fakerGenerators.add(fieldGenerator);

        }
        return fakerGenerators;
    }

    private static FieldGenerator getFieldGenerator(FakerStringSupplier fakerStringSupplier, FakerType fakerType, AbstractProvider abstractProvider) {
        return new FieldGenerator() {

            @Override
            public String getFieldName() {
                return fakerType.fieldKey;
            }

            @Override
            public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
                return fakerStringSupplier.apply(() -> fakerType.valueSupplier.apply(abstractProvider));
            }

        };
    }

    private static FieldGenerator getDefaultFieldGenerator(Faker faker, FakerStringSupplier fakerStringSupplier, FakerType fakerType) {
        return new FieldGenerator() {

            @Override
            public String getFieldName() {
                return fakerType.fieldKey;
            }

            @Override
            public JsonNode generate(FieldConfiguration configuration, JsonNode seedValue) {
                return fakerStringSupplier.apply(() -> fakerType.supplier.apply(faker));
            }

        };
    }

}
