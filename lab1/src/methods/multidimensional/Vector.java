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

    public Vector add(final Vector other) {
        double[] result = new double[getDim()];
        for (int i = 0; i < getDim(); i++) {
            result[i] = coordinates[i] + other.coordinates[i];
        }
        return new Vector(result);
    }

    public Vector multiply(final double scalingFactor) {
        double[] result = new double[getDim()];
        for (int i = 0; i < getDim(); i++) {
            result[i] = coordinates[i] * scalingFactor;
        }
        return new Vector(result);
    }

    public double scalarProduct(final Vector other) {
        double result = 0;
        for (int i = 0; i < getDim(); i++) {
            result += coordinates[i] * other.coordinates[i];
        }
        return result;
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
