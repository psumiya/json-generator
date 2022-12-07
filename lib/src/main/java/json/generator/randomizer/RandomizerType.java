package json.generator.randomizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import net.datafaker.Faker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum RandomizerType {

    IDENTITY,
    RANDOM_UUID,
    ONE_OF,
    FIRST_NAME,
    LAST_NAME,
    GENDER;

    public static Map<RandomizerType, BaseSpecRandomizer<JsonNode>> BASE_SPEC_PROVIDER_MAP;

    public static void buildProviders(Faker faker) {
        BaseSpecRandomizer<JsonNode> randomUUIDProvider = (baseSpec, jsonNode) -> JsonNodeFactory.instance.textNode(UUID.randomUUID().toString());
        OneOfRandomizer oneOfProvider = new OneOfRandomizer();
        FakerRandomizer fakerProvider = new FakerRandomizer(faker);
        BASE_SPEC_PROVIDER_MAP = new HashMap<>();
        Arrays.stream(RandomizerType.values()).forEach(
                randomizerType -> {
                    switch (randomizerType) {
                        case IDENTITY -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, (baseSpec, jsonNode) -> jsonNode);
                        case RANDOM_UUID -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, randomUUIDProvider);
                        case ONE_OF -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, oneOfProvider);
                        default -> BASE_SPEC_PROVIDER_MAP.put(randomizerType, fakerProvider);
                    }
                }
        );
    }

}
