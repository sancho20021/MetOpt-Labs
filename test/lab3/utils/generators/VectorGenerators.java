package lab3.utils.generators;

import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

import static lab3.utils.generators.MainGenerator.random;

public class VectorGenerators {
    public static double[] generateVector(final int n, final DoubleSupplier getX) {
        return DoubleStream.generate(getX).limit(n).toArray();
    }

    public static double[] generateIntegerVector(final int n, final int lo, final int hi) {
        return generateVector(n, () -> lo + random.nextInt(hi - lo));
    }
}
