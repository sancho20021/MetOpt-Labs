package methods.multidimensional;

import java.util.List;

public interface SquareMatrix {
    /**
     * Multiplies this matrix with other matrix
     *
     * @param other matrix to multiply
     * @return product of matrices
     */
    SquareMatrix multiply(SquareMatrix other);

    /**
     * Sums this matrix with other matrix
     *
     * @param other matrix to sum with
     * @return sum of matrices
     */
    SquareMatrix add(SquareMatrix other);

    /**
     * Subtracts other matrix from this matrix
     *
     * @param other matrix to subtract
     * @return this matrix minus other matrix
     */
    SquareMatrix subtract(SquareMatrix other);

    /**
     * Returns dimension of the matrix
     *
     * @return dimension of the matrix
     */
    int size();

    /**
     * Multiplies this matrix with vector
     *
     * @param vector vector to multiply
     * @return vector, product of multiplication
     */
    Vector multiply(Vector vector);

    /**
     * Multiplies vector with this matrix
     * @param vector vector to multiply
     * @return matrix, product of multiplication
     */
    Vector multiplyLeft(Vector vector);

    /**
     * Checks if sizes of matrices are same
     *
     * @param other other matrix
     * @throws IllegalArgumentException if sizes don't match
     */
    default void checkSizeMatch(SquareMatrix other) throws IllegalArgumentException {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException("Sizes of matrices are not same");
        }
    }

    /**
     * Returns element (i, j)
     *
     * @param i i
     * @param j j
     * @return element (i, j)
     */
    double element(int i, int j);

    /**
     * Returns row's row
     *
     * @param row row
     * @return row's row
     */
    Vector row(int row);

    /**
     * Returns column's column
     *
     * @param column column
     * @return column's column
     */
    Vector column(int column);

    /**
     * Returns list of rows
     *
     * @return list of rows
     */
    List<Vector> rows();

    /**
     * Returns list of columns
     *
     * @return list of columns
     */
    List<Vector> columns();

    /**
     * Returns matrix multiplied by x
     *
     * @param x x
     * @return matrix multiplied by x
     */
    SquareMatrix multiply(double x);
}
