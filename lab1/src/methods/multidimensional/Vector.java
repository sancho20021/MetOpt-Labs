package methods.multidimensional;

import utils.Utility;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Vector {
    private final double[] coordinates;

    public Vector(final double... coordinates) {
        this.coordinates = coordinates;
    }

    public Vector(final List<Double> coordinates) {
        this.coordinates = new double[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            this.coordinates[i] = coordinates.get(i);
        }
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

    public Vector normalize() {
        double norm = getEuclideanNorm();
        return this.multiply(norm == 0 ? 1 : 1 / norm);
    }

    public Vector add(final Vector other) {
        double[] result = new double[getDim()];
        for (int i = 0; i < getDim(); i++) {
            result[i] = coordinates[i] + other.coordinates[i];
        }
        return new Vector(result);
    }

    public Vector subtract(final Vector other) {
        return add(other.multiply(-1));
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

    public double[] coordinatesCopy() {
        return coordinates.clone();
    }

    public static Vector oneElementVector(int size, int index, double value) {
        double[] elements = new double[size];
        elements[index] = value;
        return new Vector(elements);
    }

    @Override
    public String toString() {

        return Arrays.stream(coordinates)
                .mapToObj(x -> Utility.formatDouble(8, x))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
