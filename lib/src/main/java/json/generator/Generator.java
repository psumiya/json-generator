package json.generator;

public interface Generator<I, O> {

    O generate(I input);

}
