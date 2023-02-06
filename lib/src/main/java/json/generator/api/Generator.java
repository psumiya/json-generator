package json.generator.api;

public interface Generator<I, O> {

    O generate(I input);

}
