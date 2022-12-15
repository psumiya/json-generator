package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.model.BaseSpec;
import net.datafaker.Faker;

import java.util.function.Function;

public record FakerRandomizer(Faker faker) implements Randomizer<JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public JsonNode generate(BaseSpec baseSpec, JsonNode sampleValue) {
        String fakeValue = getFakeValue(baseSpec);
        if (fakeValue.isEmpty()) {
            return JSON_NODE_FACTORY.missingNode();
        } else {
            return JSON_NODE_FACTORY.textNode(fakeValue);
        }
    }

    private String getFakeValue(BaseSpec baseSpec) {
        Function<Faker, String> function = RandomizerType.FAKER_FUNCTION_MAP.get(baseSpec.type());
        return function == null ? "" : function.apply(faker);
    }

}
