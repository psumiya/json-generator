package json.generator.api;

/**
 * Given Input I, generate Output O
 *
 * @param <I> the input type
 * @param <O> the output type
 */
public interface Generator<I, O> {

    /**
     * A high level abstraction to generate random values.
     *
     * @param input the input
     * @return generated output
     */
    O generate(I input);

}
