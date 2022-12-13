package json.generator.model;

import json.generator.randomizer.RandomizerType;

import java.util.Map;

public record Fields(Map<String, BaseSpec> fieldSpecMap) {

    public Fields() {
        this(RandomizerType.DEFAULT_FIELD_SPEC_MAP);
    }

}
