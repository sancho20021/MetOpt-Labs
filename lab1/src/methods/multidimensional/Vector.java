package methods.multidimensional;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class Vector {
    private final double[] coordinates;

    public Vector(final double... coordinates) {
        this.coordinates = coordinates;
    }

    public int getDim() {
        return coordinates.length;
    }

    public double getIth(int i) throws IllegalArgumentException {
        checkIndex(i);
        return coordinates[i];
    }

    public double getEuclideanNorm() {
        return Math.sqrt(reduce(x -> x * x, DoubleSummaryStatistics::getSum));
    }

    public double getUniformNorm() {
        return reduce(Math::abs, DoubleSummaryStatistics::getMax);
    }

    private double reduce(DoubleUnaryOperator mapF, Function<DoubleSummaryStatistics, Double> stat) {
        return stat.apply(Arrays.stream(coordinates).map(mapF).summaryStatistics());
    }

    private void checkIndex(int i) {
        if (!(0 <= i && i < coordinates.length)) {
            throw new IllegalArgumentException(
                    "trying to get " + i + "-th coordinate in a vector of dimension " + getDim());
        }
    }
}
