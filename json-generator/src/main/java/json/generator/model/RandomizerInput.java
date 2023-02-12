package json.generator.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The randomizer input.
 *
 * @param generatorSpec the generator spec
 * @param objectToRandomize the object to randomize
 */
public record RandomizerInput(GeneratorConfiguration generatorSpec, JsonNode objectToRandomize) {
}
