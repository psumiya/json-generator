package json.generator.faker;

import net.datafaker.Faker;

import java.util.function.Function;

public enum FakerType {

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

    protected final String fieldKey;
    protected final Function<Faker, String> supplier;

    FakerType(String fieldKey, Function<Faker, String> supplier) {
        this.fieldKey = fieldKey;
        this.supplier = supplier;
    }

}
