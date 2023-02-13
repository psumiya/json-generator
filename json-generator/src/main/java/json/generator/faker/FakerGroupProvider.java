package json.generator.faker;

import net.datafaker.AbstractProvider;
import net.datafaker.Faker;

import java.util.Map;

/**
 * Provides faker groups
 */
public final class FakerGroupProvider {

    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String BUSINESS = "business";
    private static final String BOOK = "book";

    private FakerGroupProvider() {
    }

    /**
     * Build a Map of faker group and its providers.
     *
     * @param faker the Faker instance
     * @return map of a faker group to its provider
     */
    public static Map<String, AbstractProvider> getFakerGroupProviders(Faker faker) {
        return Map.of(
                NAME, faker.name(),
                ADDRESS, faker.address(),
                BUSINESS, faker.business(),
                BOOK, faker.book()
        );
    }

}
