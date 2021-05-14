package lab3.models;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface SimpleSquareMatrix {

    /**
     * Returns dimension of the matrix
     *
     * @return dimension of the matrix
     */
    int size();

    /**
     * Returns element (i, j)
     *
     * @param i i
     * @param j j
     * @return element (i, j)
     */
    double get(int i, int j);

    /**
     * Returns string representation of matrix
     * @return string
     */
    default String convertToString() {
        return IntStream.range(0, size())
                .mapToObj(i ->
                        IntStream.range(0, size())
                                .mapToObj(j -> Double.toString(get(i, j)))
                                .collect(Collectors.joining(" "))
                ).collect(Collectors.joining(System.lineSeparator()));
    }
}
