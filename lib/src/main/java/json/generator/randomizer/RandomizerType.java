package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import json.generator.model.BaseSpec;
import net.datafaker.Faker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public enum RandomizerType {

    IDENTITY("identity"),
    RANDOM_UUID("id"),
    ONE_OF("oneOf"),
    FIRST_NAME("firstName", faker -> faker.name().firstName()),
    LAST_NAME("lastName", faker -> faker.name().lastName()),
    GENDER("gender", faker -> faker.gender().types()),
    ADDRESS("address", faker -> faker.address().fullAddress()),
    STREET_ADDRESS("streetAddress", faker -> faker.address().streetAddress()),
    CITY("city", faker -> faker.address().city());

    public static final Map<RandomizerType, Randomizer<JsonNode>> BASE_SPEC_PROVIDER_MAP = new HashMap<>();

    public static final Map<String, BaseSpec> DEFAULT_FIELD_SPEC_MAP = new HashMap<>();

    public static final Map<RandomizerType, Function<Faker, String>> FAKER_FUNCTION_MAP = new HashMap<>();

    private final String randomizerKey;

    private final Function<Faker, String> supplier;

    RandomizerType(String randomizerKey) {
        this.randomizerKey = randomizerKey;
        this.supplier = faker -> "";
    }

    RandomizerType(String randomizerKey, Function<Faker, String> supplier) {
        this.randomizerKey = randomizerKey;
        this.supplier = supplier;
    }

    static {
        for (RandomizerType randomizerType : RandomizerType.values()) {
            DEFAULT_FIELD_SPEC_MAP.put(randomizerType.randomizerKey, new BaseSpec(randomizerType));
            FAKER_FUNCTION_MAP.put(randomizerType, randomizerType.supplier);
        }
    }

    public static void buildProviders(Faker faker) {
        Randomizer<JsonNode> randomUUIDProvider = (baseSpec, jsonNode) -> JsonNodeFactory.instance.textNode(UUID.randomUUID().toString());
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
