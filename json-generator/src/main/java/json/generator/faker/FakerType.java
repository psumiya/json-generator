package json.generator.faker;

import net.datafaker.*;

import java.util.function.Function;

public enum FakerType {

    FULL_NAME("fullName", "name", name -> ((Name) name).fullName()),
    FIRST_NAME("firstName", "name", name -> ((Name) name).firstName()),
    LAST_NAME("lastName", "name", name -> ((Name) name).lastName()),

    GENDER("gender", faker -> faker.gender().types()),

    ADDRESS("address", "address", address -> ((Address) address).fullAddress()),
    STREET_ADDRESS("streetAddress", "address", address -> ((Address) address).streetAddress()),
    STATE("state", "address", address -> ((Address) address).state()),
    TIME_ZONE("timeZone", "address", address -> ((Address) address).timeZone()),
    CITY("city", "address", address -> ((Address) address).city()),

    CREDIT_CARD_TYPE("creditCardType", "business", business -> ((Business) business).creditCardType()),
    CREDIT_CARD_NUMBER("creditCardNumber", "business", business -> ((Business) business).creditCardNumber()),
    CREDIT_CARD_EXPIRY("creditCardExpiry", "business", business -> ((Business) business).creditCardExpiry()),

    BOOK_TITLE("title", "book", book -> ((Book) book).title()),
    BOOK_AUTHOR("author", "book", book -> ((Book) book).author()),
    BOOK_GENRE("genre", "book", book -> ((Book) book).genre()),
    BOOK_PUBLISHER("publisher", "book", book -> ((Book) book).publisher()),

    COFFEE_BLEND("blend", faker -> faker.coffee().blendName()),

    COLOR("color", faker -> faker.color().name()),
    COLOR_HEX("hexColor", faker -> faker.color().hex(true)),

    CURRENCY("currency", faker -> faker.currency().code()),
    CURRENCY_NAME("currencyName", faker -> faker.currency().name()),

    BLOOD_GROUP("bloodGroup", faker -> faker.bloodtype().bloodGroup());

    protected final String fieldKey;
    protected final Function<Faker, String> supplier;
    protected final String groupName;
    protected final Function<AbstractProvider, String> valueSupplier;

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
