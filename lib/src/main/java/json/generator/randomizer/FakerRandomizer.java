package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.model.BaseSpec;
import net.datafaker.Faker;

public record FakerRandomizer(Faker faker) implements BaseSpecRandomizer<JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public JsonNode generate(BaseSpec baseSpec, JsonNode sampleValue) {
        JsonNode jsonNode = switch (baseSpec.type()) {
            case FIRST_NAME -> JSON_NODE_FACTORY.textNode(faker.name().firstName());
            case LAST_NAME -> JSON_NODE_FACTORY.textNode(faker.name().lastName());
            case GENDER -> JSON_NODE_FACTORY.textNode(faker.gender().types());
            default -> JSON_NODE_FACTORY.missingNode();
        };
        return jsonNode;
    }

}
