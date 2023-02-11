package json.generator.api;

import json.generator.JsonToJsonGenerator;
import json.generator.model.JsonGeneratorModel;

import java.util.Optional;

/**
 * Provide default generators. Only meant for library consumers. Avoid adding internal generators.
 */
public final class GeneratorFactory {

    /**
     * Prevent external instantiation of this factory
     */
    private GeneratorFactory() {
    }

    /**
     * Get a default generator
     *
     * @return a generator with default JsonGeneratorModel
     */
    public static Generator<String, String> getDefaultGenerator() {
        return new JsonToJsonGenerator(new JsonGeneratorModel());
    }

    /**
     * Setup a generator.
     *
     * @param jsonGeneratorModel a model with customized input
     * @return a generator with given JsonGeneratorModel
     */
    public static Generator<String, String> getGeneratorWithModel(JsonGeneratorModel jsonGeneratorModel) {
        return Optional.ofNullable(jsonGeneratorModel)
                .<Generator<String, String>>map(JsonToJsonGenerator::new)
                .orElse(getDefaultGenerator());
    }

}
