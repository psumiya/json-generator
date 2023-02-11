package json.generator.faker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import net.datafaker.Faker;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provide a text JsonNode from a string supplier.
 *
 * @param faker the faker instance
 */
public record FakerStringSupplier(Faker faker) implements Function<Supplier<String>, JsonNode> {

    private static final JsonNodeFactory JSON_NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public JsonNode apply(Supplier<String> supplier) {
        return Optional.ofNullable(supplier.get())
                .map(fakeValue -> (JsonNode) JSON_NODE_FACTORY.textNode(fakeValue))
                .orElse(JSON_NODE_FACTORY.missingNode());
    }
}
