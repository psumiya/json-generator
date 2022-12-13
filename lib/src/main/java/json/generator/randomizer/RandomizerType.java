package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.model.BaseSpec;
import net.datafaker.Faker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum RandomizerType {

    IDENTITY("identity"),
    RANDOM_UUID("id"),
    ONE_OF("oneOf"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    GENDER("gender"),
    ADDRESS("address"),
    STREET_ADDRESS("streetAddress");

    public static final Map<RandomizerType, BaseSpecRandomizer<JsonNode>> BASE_SPEC_PROVIDER_MAP = new HashMap<>();

    public static final Map<String, BaseSpec> DEFAULT_FIELD_SPEC_MAP = new HashMap<>();

    private final String randomizerKey;

    RandomizerType(String randomizerKey) {
        this.randomizerKey = randomizerKey;
    }

    static {
        for (RandomizerType randomizerType : RandomizerType.values()) {
            DEFAULT_FIELD_SPEC_MAP.put(randomizerType.randomizerKey, new BaseSpec(randomizerType));
        }
    }

    public static void buildProviders(Faker faker) {
        BaseSpecRandomizer<JsonNode> randomUUIDProvider = (baseSpec, jsonNode) -> JsonNodeFactory.instance.textNode(UUID.randomUUID().toString());
        OneOfRandomizer oneOfProvider = new OneOfRandomizer();
        FakerRandomizer fakerProvider = new FakerRandomizer(faker);
        for (RandomizerType randomizerType : RandomizerType.values()) {
            switch (randomizerType) {
                case IDENTITY -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, (baseSpec, jsonNode) -> jsonNode);
                case RANDOM_UUID -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, randomUUIDProvider);
                case ONE_OF -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, oneOfProvider);
                default -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, fakerProvider);
            }
        }
    }

}
