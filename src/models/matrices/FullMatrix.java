package models.matrices;

import models.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix extends AdvancedMatrix {
    final double[][] data;

    public FullMatrix(final double[][] data) {
        this.data = data;
    }

    public FullMatrix(final SimpleSquareMatrix other) {
        this(
                IntStream.range(0, other.size())
                        .mapToObj(i -> IntStream.range(0, other.size()).mapToDouble(j -> other.get(i, j)).toArray())
                        .toArray(double[][]::new)
        );
    }

    /**
     * Copies other matrix elements to this
     *
     * @param other other matrix
     */
    public FullMatrix(final AdvancedMatrix other) {
        this.data = other.getDataCopy();
    }

    public FullMatrix(double... values) {
        int dim = (int) Math.sqrt(values.length);
        if (dim * dim != values.length) {
            throw new IllegalArgumentException("can't construct a square matrix of " + values.length + " elements");
        }
        data = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            data[i] = Arrays.copyOfRange(values, i * dim, (i + 1) * dim);
        }
    }

    public FullMatrix(final List<Vector> rows) {
        this(rows.stream().map(Vector::getElementsArrayCopy).toArray(double[][]::new));
    }

    @Override
    public AdvancedMatrix multiply(AdvancedMatrix other) {
        return new FullMatrix(getRows().stream().map(other::multiplyLeft).collect(Collectors.toList()));
    }

    @Override
    public AdvancedMatrix add(AdvancedMatrix other) {
        return new FullMatrix(
                IntStream.range(0, size())
                        .mapToObj(r -> this.getRow(r).add(other.getRow(r)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public AdvancedMatrix subtract(AdvancedMatrix other) {
        return add(other.multiply(-1));
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public Vector multiply(Vector vector) {
        return new Vector(getRows().stream().mapToDouble(row -> row.scalarProduct(vector)).toArray());
    }

    @Override
    public Vector multiplyLeft(Vector vector) {
        return new Vector(DiagonalMatrix.doubleArray(size(), i -> vector.scalarProduct(getColumns().get(i))));
    }


    @Override
    public double get(int i, int j) {
        return data[i][j];
    }

    @Override
    public Vector getRow(int row) {
        return new Vector(data[row]);
    }

    @Override
    public Vector getColumn(int column) {
        return new Vector(getRows().stream().mapToDouble(row -> row.get(column)).toArray());
    }

    @Override
    public List<Vector> getRows() {
        return Arrays.stream(data).map(Vector::new).collect(Collectors.toList());
    }

    @Override
    public List<Vector> getColumns() {
        return IntStream.range(0, size())
                .mapToObj(i ->
                        new Vector(
                                IntStream.range(0, size()).mapToDouble(row -> get(row, i)).toArray()
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public AdvancedMatrix multiply(double x) {
        return new FullMatrix(getRows().stream().map(row -> row.multiply(x)).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return getRows().stream().map(Vector::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void set(int i, int j, double x) {
        data[i][j] = x;
    }
}
