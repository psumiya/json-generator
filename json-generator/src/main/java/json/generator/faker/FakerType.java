package json.generator.faker;

import net.datafaker.*;

import java.util.function.Function;

/**
 * All Faker Types
 */
public enum FakerType {

    /**
     * Example: `Prof Evan Julian`
     */
    FULL_NAME("fullName", "name", name -> ((Name) name).fullName()),

    /**
     * Example: Victor
     */
    FIRST_NAME("firstName", "name", name -> ((Name) name).firstName()),

    /**
     * Example: Lacroix
     */
    LAST_NAME("lastName", "name", name -> ((Name) name).lastName()),

    /**
     * Example: Male
     */
    GENDER("gender", faker -> faker.gender().types()),

    /**
     * Example: 5010 Wiegand Hollow, Deanemouth, NM 20893
     */
    ADDRESS("address", "address", address -> ((Address) address).fullAddress()),

    /**
     * Example: 406 Collier Bypass
     */
    STREET_ADDRESS("streetAddress", "address", address -> ((Address) address).streetAddress()),

    /**
     * Example: Arkansas
     */
    STATE("state", "address", address -> ((Address) address).state()),

    /**
     * Example: Europe/Dublin
     */
    TIME_ZONE("timeZone", "address", address -> ((Address) address).timeZone()),

    /**
     * Example: Davidhaven
     */
    CITY("city", "address", address -> ((Address) address).city()),

    /**
     * Example: diners_club
     */
    CREDIT_CARD_TYPE("creditCardType", "business", business -> ((Business) business).creditCardType()),

    /**
     * Example: 1228-1221-1221-1431
     */
    CREDIT_CARD_NUMBER("creditCardNumber", "business", business -> ((Business) business).creditCardNumber()),

    /**
     * Example: 2011-10-12
     */
    CREDIT_CARD_EXPIRY("creditCardExpiry", "business", business -> ((Business) business).creditCardExpiry()),

    /**
     * Example: The Needle's Eye
     */
    BOOK_TITLE("title", "book", book -> ((Book) book).title()),

    /**
     * Example: Coy Schmeler
     */
    BOOK_AUTHOR("author", "book", book -> ((Book) book).author()),

    /**
     * Example: Comic/Graphic Novel
     */
    BOOK_GENRE("genre", "book", book -> ((Book) book).genre()),

    /**
     * Example: Academic Press
     */
    BOOK_PUBLISHER("publisher", "book", book -> ((Book) book).publisher()),

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
     * a group name for the field
     */
    protected final String groupName;

    /**
     * the abstract provider for the group, such as Name, Address, etc.
     */
    protected final Function<AbstractProvider, String> valueSupplier;

    /**
     * Base FakerType
     *
     * @param fieldKey the field key
     * @param supplier the faker supplier
     */
    /**
     * Use for ungrouped types
     *
     * @param fieldKey the fieldKey
     * @param supplier the supplier function
     */
    FakerType(String fieldKey, Function<Faker, String> supplier) {
        this.fieldKey = fieldKey;
        this.supplier = supplier;
        this.groupName = null;
        this.valueSupplier = null;
    }

    /**
     * Use for grouped types
     *
     * @param fieldKey the fieldKey
     * @param groupName the groupName
     * @param valueSupplier the valueSupplier
     */
    FakerType(String fieldKey, String groupName, Function<AbstractProvider, String> valueSupplier) {
        this.fieldKey = fieldKey;
        this.supplier = null;
        this.groupName = groupName;
        this.valueSupplier = valueSupplier;
    }

}
