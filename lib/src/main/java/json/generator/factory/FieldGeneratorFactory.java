package json.generator.factory;

import json.generator.FieldGenerator;
import json.generator.faker.*;
import json.generator.main.IdentityGenerator;
import json.generator.main.MainFactory;
import json.generator.main.OneOfGenerator;
import json.generator.main.RandomUUIDGenerator;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class FieldGeneratorFactory {

    public static List<FieldGenerator> initializeGenerators(Faker faker) {
        List<FieldGenerator> allGenerators = new ArrayList<>();
        allGenerators.addAll(FakerFactory.buildFakerGenerators(faker));
        allGenerators.addAll(MainFactory.buildMainGenerators());
        return allGenerators;
    }

}
