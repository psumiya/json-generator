package json.generator.factory;

import json.generator.FieldGenerator;
import json.generator.faker.FakerStringSupplier;
import json.generator.faker.FirstNameGenerator;
import json.generator.faker.FullNameGenerator;
import json.generator.faker.LastNameGenerator;
import json.generator.main.IdentityGenerator;
import json.generator.main.OneOfGenerator;
import json.generator.main.RandomUUIDGenerator;
import net.datafaker.Faker;

import java.util.List;

public class FieldGeneratorFactory {

    public static List<FieldGenerator> initializeGenerators(Faker faker) {
        FakerStringSupplier fakerStringSupplier = new FakerStringSupplier(faker);
        return List.of(
                new IdentityGenerator(),
                new OneOfGenerator(),
                new RandomUUIDGenerator(),
                new FirstNameGenerator(fakerStringSupplier),
                new LastNameGenerator(fakerStringSupplier),
                new FullNameGenerator(fakerStringSupplier)
        );
    }

}
