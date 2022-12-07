package json.generator.model;

public record GeneratorSpec(Localization localizationSpec, Fields fieldSpec) {

    public GeneratorSpec() {
        this(new Localization(), new Fields());
    }

}
