package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import json.generator.model.BaseSpec;

@FunctionalInterface
public interface Randomizer<R> {

    R generate(BaseSpec baseSpec, JsonNode sampleValue);

}
