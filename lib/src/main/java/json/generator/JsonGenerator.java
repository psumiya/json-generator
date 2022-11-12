package json.generator;

public interface JsonGenerator<I, O> {

    O generate(I input);

}
