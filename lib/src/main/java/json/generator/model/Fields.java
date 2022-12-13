package json.generator.model;

import json.generator.randomizer.RandomizerType;

import java.util.Map;

public record Fields(Map<String, BaseSpec> fieldSpecMap) {

    private static final Map<String, BaseSpec> DEFAULT_FIELD_SPEC_MAP = Map.of(
            "id", new BaseSpec(RandomizerType.RANDOM_UUID),
            "firstName", new BaseSpec(RandomizerType.FIRST_NAME),
            "lastName", new BaseSpec(RandomizerType.LAST_NAME),
            "gender", new BaseSpec(RandomizerType.GENDER),
            "address", new BaseSpec(RandomizerType.ADDRESS)
    );

    public Fields() {
        this(DEFAULT_FIELD_SPEC_MAP);
    }

}
