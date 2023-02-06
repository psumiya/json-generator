package json.generator;

import json.generator.api.Generator;
import json.generator.model.JsonGeneratorModel;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Benchmark {

    private static final String FILE_NAME = "src/jmh/resources/samples/root_is_object.json";

    private static final Generator<String, String> GENERATOR = new JsonToJsonGenerator(new JsonGeneratorModel());

    private static final String SAMPLE;

    static {
        Path path = Paths.get(FILE_NAME);
        try {
            SAMPLE = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    @Fork(value = 3, warmups = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @OperationsPerInvocation(10)
    public String executeMethodToBenchmark() {
        return GENERATOR.generate(SAMPLE);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

}
