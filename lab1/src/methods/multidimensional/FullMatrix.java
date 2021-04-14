package methods.multidimensional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FullMatrix implements SquareMatrix {
    List<Vector> rows;

    public FullMatrix(List<Vector> rows) {
        this.rows = rows;
    }

    public FullMatrix(final SquareMatrix other) {
        this.rows = other.rows();
    }

    @Override
    public SquareMatrix multiply(SquareMatrix other) {
        return new FullMatrix(rows().stream().map(other::multiplyLeft).collect(Collectors.toList()));
    }

    @Override
    public SquareMatrix add(SquareMatrix other) {
        return new FullMatrix(
                IntStream.range(0, size())
                        .mapToObj(r -> this.row(r).add(other.row(r)))
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
        return new Vector(DiagonalMatrix.doubleArray(size(), i -> vector.scalarProduct(columns().get(i))));
    }


    @Override
    public double element(int i, int j) {
        return rows.get(i).getIth(j);
    }

    @Override
    public Vector row(int row) {
        return rows.get(row);
    }

    @Override
    public Vector column(int column) {
        return new Vector(rows.stream().mapToDouble(row -> row.getIth(column)).toArray());
    }

    @Override
    public List<Vector> rows() {
        return rows;
    }

    @Override
    public List<Vector> columns() {
        return IntStream.range(0, size())
                .mapToObj(i ->
                        new Vector(
                                IntStream.range(0, size()).mapToDouble(row -> element(row, i)).toArray()
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
