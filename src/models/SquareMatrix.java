package models;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SquareMatrix {
    /**
     * Multiplies this matrix with other matrix
     *
     * @param other matrix to multiply
     * @return product of matrices
     */
    public abstract SquareMatrix multiply(SquareMatrix other);

    /**
     * Sums this matrix with other matrix
     *
     * @param other matrix to sum with
     * @return sum of matrices
     */
    public abstract SquareMatrix add(SquareMatrix other);

    /**
     * Subtracts other matrix from this matrix
     *
     * @param other matrix to subtract
     * @return this matrix minus other matrix
     */
    public abstract SquareMatrix subtract(SquareMatrix other);

    /**
     * Returns dimension of the matrix
     *
     * @return dimension of the matrix
     */
    public abstract int size();

    /**
     * Multiplies this matrix with vector
     *
     * @param vector vector to multiply
     * @return vector, product of multiplication
     */
    public abstract Vector multiply(Vector vector);

    /**
     * Multiplies vector with this matrix
     *
     * @param vector vector to multiply
     * @return matrix, product of multiplication
     */
    public abstract Vector multiplyLeft(Vector vector);

    /**
     * Checks if sizes of matrices are same
     *
     * @param other other matrix
     * @throws IllegalArgumentException if sizes don't match
     */
    void checkSizeMatch(SquareMatrix other) throws IllegalArgumentException {
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
    public abstract double element(int i, int j);

    /**
     * Returns row's row
     *
     * @param row row
     * @return row's row
     */
    public abstract Vector row(int row);

    /**
     * Returns column's column
     *
     * @param column column
     * @return column's column
     */
    public abstract Vector column(int column);

    /**
     * Returns list of rows
     *
     * @return list of rows
     */
    public abstract List<Vector> rows();

    /**
     * Returns list of columns
     *
     * @return list of columns
     */
    public abstract List<Vector> columns();

    /**
     * Returns matrix multiplied by x
     *
     * @param x x
     * @return matrix multiplied by x
     */
    public abstract SquareMatrix multiply(double x);

    @Override
    public String toString() {
        return rows().stream()
                .map(Vector::toString)
                .collect(Collectors.joining(System.lineSeparator(), "{", "}"));
    }
}
