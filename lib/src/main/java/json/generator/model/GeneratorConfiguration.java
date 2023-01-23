package json.generator.model;

import java.util.List;

public record GeneratorConfiguration(Localization localizationSpec, List<FieldConfiguration> fieldConfigurations) {
}
