package json.generator.model;

import java.util.List;

/**
 * The Generator Configuration.
 *
 * @param localizationSpec the localizationSpec
 * @param fieldConfigurations the fieldConfigurations
 */
public record GeneratorConfiguration(Localization localizationSpec, List<FieldConfiguration> fieldConfigurations) {
}
