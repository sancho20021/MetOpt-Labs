package methods.multidimensional;

import java.util.List;

public class FullMatrix implements SquareMatrix {
    List<List<Double>> elements;

    @Override
    public SquareMatrix multiply(SquareMatrix other) {
        return null;
    }

    @Override
    public SquareMatrix add(SquareMatrix other) {
        return null;
    }

    @Override
    public SquareMatrix subtract(SquareMatrix other) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Vector multiply(Vector vector) {
        return null;
    }

    @Override
    public double element(int i, int j) {
        return 0;
    }

    @Override
    public Vector row(int row) {
        return null;
    }

    @Override
    public Vector column(int column) {
        return null;
    }
}
