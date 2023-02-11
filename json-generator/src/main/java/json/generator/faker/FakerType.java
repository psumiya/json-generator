package json.generator.faker;

import net.datafaker.Faker;

import java.util.function.Function;

/**
 * All Faker Types
 */
public enum FakerType {

    /**
     * Example: `Prof Evan Julian`
     */
    FULL_NAME("fullName", faker -> faker.name().fullName()),

    /**
     * Example: Victor
     */
    FIRST_NAME("firstName", faker -> faker.name().firstName()),

    /**
     * Example: Lacroix
     */
    LAST_NAME("lastName", faker -> faker.name().lastName()),

    /**
     * Example: Male
     */
    GENDER("gender", faker -> faker.gender().types()),

    /**
     * Example: 5010 Wiegand Hollow, Deanemouth, NM 20893
     */
    ADDRESS("address", faker -> faker.address().fullAddress()),

    /**
     * Example: 406 Collier Bypass
     */
    STREET_ADDRESS("streetAddress", faker -> faker.address().streetAddress()),

    /**
     * Example: Arkansas
     */
    STATE("state", faker -> faker.address().state()),

    /**
     * Example: Europe/Dublin
     */
    TIME_ZONE("timeZone", faker -> faker.address().timeZone()),

    /**
     * Example: Davidhaven
     */
    CITY("city", faker -> faker.address().city()),

    /**
     * Example: diners_club
     */
    CREDIT_CARD_TYPE("creditCardType", faker -> faker.business().creditCardType()),

    /**
     * Example: 1228-1221-1221-1431
     */
    CREDIT_CARD_NUMBER("creditCardNumber", faker -> faker.business().creditCardNumber()),

    /**
     * Example: 2011-10-12
     */
    CREDIT_CARD_EXPIRY("creditCardExpiry", faker -> faker.business().creditCardExpiry()),

    /**
     * Example: The Needle's Eye
     */
    BOOK_TITLE("title", faker -> faker.book().title()),

    /**
     * Example: Coy Schmeler
     */
    BOOK_AUTHOR("author", faker -> faker.book().author()),

    /**
     * Example: Comic/Graphic Novel
     */
    BOOK_GENRE("genre", faker -> faker.book().genre()),

    /**
     * Example: Academic Press
     */
    BOOK_PUBLISHER("publisher", faker -> faker.book().publisher()),

    /**
     * Example: Goodbye Blend
     */
    COFFEE_BLEND("blend", faker -> faker.coffee().blendName()),

    /**
     * Example: sky blue
     */
    COLOR("color", faker -> faker.color().name()),

    /**
     * Example: #E2AA1B
     */
    COLOR_HEX("hexColor", faker -> faker.color().hex(true)),

    /**
     * Example: USD
     */
    CURRENCY("currency", faker -> faker.currency().code()),

    /**
     * Example: US Dollar
     */
    CURRENCY_NAME("currencyName", faker -> faker.currency().name()),

    /**
     * Example: O+
     */
    BLOOD_GROUP("bloodGroup", faker -> faker.bloodtype().bloodGroup());

    /**
     * the field key
     */
    protected final String fieldKey;

    /**
     * a supplier to generate a value for the field key
     */
    protected final Function<Faker, String> supplier;

    /**
     * Base FakerType
     *
     * @param fieldKey the field key
     * @param supplier the faker supplier
     */
    FakerType(String fieldKey, Function<Faker, String> supplier) {
        this.fieldKey = fieldKey;
        this.supplier = supplier;
    }

}
