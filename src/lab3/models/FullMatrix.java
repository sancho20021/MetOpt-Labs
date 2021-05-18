package lab3.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix extends SquareMatrix {
    List<Vector> rows;

    public FullMatrix(final double[][] data) {
        this(Arrays.stream(data).map(Vector::new).collect(Collectors.toList()));
    }

    public FullMatrix(final SimpleSquareMatrix other) {
        this(
                IntStream.range(0, other.size())
                        .mapToObj(i -> IntStream.range(0, other.size()).mapToDouble(j -> other.get(i, j)).toArray())
                        .toArray(double[][]::new)
        );
    }

    public FullMatrix(List<Vector> rows) {
        this.rows = rows;
    }

    public FullMatrix(final SquareMatrix other) {
        this.rows = other.getRows();
    }

    public FullMatrix(double... values) {
        int dim = (int) Math.sqrt(values.length);
        if (dim * dim != values.length) {
            throw new IllegalArgumentException("can't construct a square matrix of " + values.length + " elements");
        }
        rows = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            rows.add(new Vector(Arrays.copyOfRange(values, i * dim, (i + 1) * dim)));
        }
    }

    @Override
    public SquareMatrix multiply(SquareMatrix other) {
        return new FullMatrix(getRows().stream().map(other::multiplyLeft).collect(Collectors.toList()));
    }

    @Override
    public SquareMatrix add(SquareMatrix other) {
        return new FullMatrix(
                IntStream.range(0, size())
                        .mapToObj(r -> this.getRow(r).add(other.getRow(r)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public SquareMatrix subtract(SquareMatrix other) {
        return add(other.multiply(-1));
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public Vector multiply(Vector vector) {
        return new Vector(rows.stream().mapToDouble(row -> row.scalarProduct(vector)).toArray());
    }

    @Override
    public Vector multiplyLeft(Vector vector) {
        return new Vector(DiagonalMatrix.doubleArray(size(), i -> vector.scalarProduct(getColumns().get(i))));
    }


    @Override
    public double get(int i, int j) {
        return rows.get(i).getIth(j);
    }

    @Override
    public Vector getRow(int row) {
        return rows.get(row);
    }

    @Override
    public Vector getColumn(int column) {
        return new Vector(rows.stream().mapToDouble(row -> row.getIth(column)).toArray());
    }

    @Override
    public List<Vector> getRows() {
        return rows;
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
    public SquareMatrix multiply(double x) {
        return new FullMatrix(rows.stream().map(row -> row.multiply(x)).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return rows.stream().map(Vector::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
