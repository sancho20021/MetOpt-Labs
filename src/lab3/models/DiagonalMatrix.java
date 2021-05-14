package lab3.models;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class DiagonalMatrix extends SquareMatrix {
    final double[] elements;

    public DiagonalMatrix(double... elements) {
        this.elements = elements;
    }

    public DiagonalMatrix(final List<Double> elements) {
        this.elements = new double[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            this.elements[i] = elements.get(i);
        }
    }

    @Override
    public SquareMatrix multiply(SquareMatrix other) {
        return other instanceof DiagonalMatrix
                ? multiply((DiagonalMatrix) other)
                : new FullMatrix(this).multiply(other);
    }

    public DiagonalMatrix multiply(DiagonalMatrix other) {
        checkSizeMatch(other);
        return new DiagonalMatrix(doubleArray(size(), i -> elements[i] * other.elements[i]));
    }

    @Override
    public SquareMatrix add(SquareMatrix other) {
        return other instanceof DiagonalMatrix
                ? add((DiagonalMatrix) other)
                : new FullMatrix(this).add(other);
    }

    public DiagonalMatrix add(DiagonalMatrix other) {
        checkSizeMatch(other);
        return new DiagonalMatrix(doubleArray(size(), i -> elements[i] + other.elements[i]));
    }

    @Override
    public SquareMatrix subtract(SquareMatrix other) {
        return add(other.multiply(-1));
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public Vector multiply(Vector vector) {
        return new Vector(doubleArray(size(), i -> elements[i] * vector.getIth(i)));
    }

    @Override
    public Vector multiplyLeft(Vector vector) {
        return multiply(vector);
    }

    @Override
    public double get(int i, int j) {
        return i == j ? elements[i] : 0;
    }

    @Override
    public Vector getRow(int row) {
        return Vector.oneElementVector(size(), row, get(row, row));
    }

    @Override
    public Vector getColumn(int column) {
        return getRow(column);
    }

    @Override
    public List<Vector> getRows() {
        return IntStream.range(0, size())
                .mapToObj(r -> Vector.oneElementVector(size(), r, get(r, r)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vector> getColumns() {
        return getRows();
    }

    @Override
    public SquareMatrix multiply(double x) {
        return new DiagonalMatrix(DoubleStream.of(elements).map(a -> a * x).toArray());
    }

    public static double[] doubleArray(int length, IntToDoubleFunction f) {
        return IntStream.range(0, length).mapToDouble(f).toArray();
    }

    @Override
    public String toString() {
        return getRows().stream().map(Vector::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    public double getMinEigenValueAbs() {
        return Arrays.stream(elements).map(Math::abs).min().getAsDouble();
    }

    public double getMaxEigenValueAbs() {
        return Arrays.stream(elements).map(Math::abs).max().getAsDouble();
    }
}
