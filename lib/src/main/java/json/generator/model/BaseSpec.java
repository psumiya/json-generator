package json.generator.model;

import json.generator.randomizer.RandomizerType;

import java.util.Map;

public record BaseSpec(RandomizerType type, Map<String, Object> parameters) {

    public BaseSpec(RandomizerType type) {
        this(type, Map.of());
    }

}
