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

    FULL_NAME("fullName", faker -> faker.name().fullName()),
    FIRST_NAME("firstName", faker -> faker.name().firstName()),
    LAST_NAME("lastName", faker -> faker.name().lastName()),

    GENDER("gender", faker -> faker.gender().types()),

    ADDRESS("address", faker -> faker.address().fullAddress()),
    STREET_ADDRESS("streetAddress", faker -> faker.address().streetAddress()),
    STATE("state", faker -> faker.address().state()),
    TIME_ZONE("timeZone", faker -> faker.address().timeZone()),
    CITY("city", faker -> faker.address().city()),

    CREDIT_CARD_TYPE("creditCardType", faker -> faker.business().creditCardType()),
    CREDIT_CARD_NUMBER("creditCardNumber", faker -> faker.business().creditCardNumber()),
    CREDIT_CARD_EXPIRY("creditCardExpiry", faker -> faker.business().creditCardExpiry()),

    BOOK_TITLE("title", faker -> faker.book().title()),
    BOOK_AUTHOR("author", faker -> faker.book().author()),
    BOOK_GENRE("genre", faker -> faker.book().genre()),
    BOOK_PUBLISHER("publisher", faker -> faker.book().publisher()),

    COFFEE_BLEND("blend", faker -> faker.coffee().blendName()),

    COLOR("color", faker -> faker.color().name()),
    COLOR_HEX("hexColor", faker -> faker.color().hex(true)),

    CURRENCY("currency", faker -> faker.currency().code()),
    CURRENCY_NAME("currencyName", faker -> faker.currency().name()),

    BLOOD_GROUP("bloodGroup", faker -> faker.bloodtype().bloodGroup());

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

    public static Map<RandomizerType, Randomizer<JsonNode>> initializeProviders(Faker faker) {
        Map<RandomizerType, Randomizer<JsonNode>> baseSpecProviderMap = new HashMap<>();
        Randomizer<JsonNode> randomUUIDProvider = (baseSpec, jsonNode) -> JsonNodeFactory.instance.textNode(UUID.randomUUID().toString());
        OneOfRandomizer oneOfProvider = new OneOfRandomizer();
        FakerRandomizer fakerProvider = new FakerRandomizer(faker);
        for (RandomizerType randomizerType : RandomizerType.values()) {
            switch (randomizerType) {
                case IDENTITY -> baseSpecProviderMap.put(randomizerType, (baseSpec, jsonNode) -> jsonNode);
                case RANDOM_UUID -> baseSpecProviderMap.put(randomizerType, randomUUIDProvider);
                case ONE_OF -> baseSpecProviderMap.put(randomizerType, oneOfProvider);
                default -> baseSpecProviderMap.put(randomizerType, fakerProvider);
            }
        }
        return baseSpecProviderMap;
    }

}
