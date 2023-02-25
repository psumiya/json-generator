package json.generator.main;

import json.generator.FieldGenerator;

import java.util.List;

/**
 * Core generators
 */
public final class MainFactory {

    private MainFactory() {
    }

    /**
     * Build main generators
     *
     * @return list of generators provided by this library
     */
    public static List<FieldGenerator> buildMainGenerators() {
        return List.of(
                new IdentityGenerator(),
                new OneOfGenerator(),
                new RandomUUIDGenerator() {
                    @Override
                    public String getFieldName() {
                        return "id";
                    }
                },
                new RandomUUIDGenerator() {
                    @Override
                    public String getFieldName() {
                        return "uuid";
                    }
                },
                new RandomUUIDGenerator()
        );
    }

}
