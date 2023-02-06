package json.generator.model;

import com.fasterxml.jackson.databind.JsonNode;

public record RandomizerInput(GeneratorConfiguration generatorSpec, JsonNode objectToRandomize) {
}
