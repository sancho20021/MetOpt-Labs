package lab3.models;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AdvancedMatrix implements MutableSquareMatrix {
    /**
     * Multiplies this matrix with other matrix
     *
     * @param other matrix to multiply
     * @return product of matrices
     */
    public abstract AdvancedMatrix multiply(AdvancedMatrix other);

    /**
     * Sums this matrix with other matrix
     *
     * @param other matrix to sum with
     * @return sum of matrices
     */
    public abstract AdvancedMatrix add(AdvancedMatrix other);

    /**
     * Subtracts other matrix from this matrix
     *
     * @param other matrix to subtract
     * @return this matrix minus other matrix
     */
    public abstract AdvancedMatrix subtract(AdvancedMatrix other);

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
     * Returns row's row
     *
     * @param row row
     * @return row's row
     */
    public abstract Vector getRow(int row);

    /**
     * Returns column's column
     *
     * @param column column
     * @return column's column
     */
    public abstract Vector getColumn(int column);

    /**
     * Returns list of rows
     *
     * @return list of rows
     */
    public abstract List<Vector> getRows();

    /**
     * Returns list of columns
     *
     * @return list of columns
     */
    public abstract List<Vector> getColumns();

    /**
     * Returns matrix multiplied by x
     *
     * @param x x
     * @return matrix multiplied by x
     */
    public abstract AdvancedMatrix multiply(double x);

    @Override
    public String toString() {
        return getRows().stream()
                .map(Vector::toString)
                .collect(Collectors.joining(System.lineSeparator(), "{", "}"));
    }

    public String toRawString() {
        return getRows().stream().map(Vector::toRawString).collect(Collectors.joining(System.lineSeparator()));
    }

}
