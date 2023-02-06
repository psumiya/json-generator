package json.generator.main;

import json.generator.FieldGenerator;

import java.util.List;

public final class MainFactory {

    public static List<FieldGenerator> buildMainGenerators() {
        return List.of(
                new IdentityGenerator(),
                new OneOfGenerator(),
                new RandomUUIDGenerator()
        );
    }

}
