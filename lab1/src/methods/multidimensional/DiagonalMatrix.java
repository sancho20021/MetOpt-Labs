package methods.multidimensional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiagonalMatrix implements SquareMatrix {
    final List<Double> elements;

    public DiagonalMatrix(double... elements) {
        this.elements = Arrays.stream(elements).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    public DiagonalMatrix(final List<Double> elements) {
        this.elements = elements;
    }

    @Override
    public SquareMatrix multiply(SquareMatrix other) {
        return other instanceof DiagonalMatrix ? multiply((DiagonalMatrix) other) : null;
    }

    public DiagonalMatrix multiply(DiagonalMatrix other) {
        checkSizeMatch(other);
        return new DiagonalMatrix(
                IntStream.range(0, size())
                        .mapToObj(i -> elements.get(i) * other.elements.get(i))
                        .collect(Collectors.toList()));
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
        return elements.size();
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
