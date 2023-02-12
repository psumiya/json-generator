package json.generator.model;

import java.util.List;
import java.util.Map;

/**
 * Define localization values
 *
 * @param language the language
 * @param country the country
 * @param state the state
 * @param equivalencies any equivalent field keys
 */
public record Localization(String language, String country, String state, Map<String, List<String>> equivalencies) {

    private static final String DEFAULT_LANGUAGE = "en";
    private static final String DEFAULT_COUNTRY = "US";
    private static final String DEFAULT_STATE = "CA";

    private static final Map<String, List<String>> DEFAULT_EQUIVALENCIES = Map.of(
            "language", List.of("languageCode", "langCode", "lang"),
            "country", List.of("countryCode", "cntry", "cntryCd")
    );

    /**
     * Use when all params are knowm
     */
    public Localization() {
        this(DEFAULT_LANGUAGE, DEFAULT_COUNTRY, DEFAULT_STATE, DEFAULT_EQUIVALENCIES);
    }

    /**
     * Use when only lang and country is available
     *
     * @param language the language
     * @param country the country
     */
    public Localization(String language, String country) {
        this(language, country, null, DEFAULT_EQUIVALENCIES);
    }

}
