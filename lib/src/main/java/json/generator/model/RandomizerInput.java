package json.generator.model;

import com.fasterxml.jackson.databind.JsonNode;

public record RandomizerInput(GeneratorSpec generatorSpec, JsonNode objectToRandomize) {
}
