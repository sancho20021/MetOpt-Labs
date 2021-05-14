package lab3.models;

public abstract class SimpleSquareMatrix {

    /**
     * Returns dimension of the matrix
     *
     * @return dimension of the matrix
     */
    public abstract int size();

    /**
     * Checks if sizes of matrices are same
     *
     * @param other other matrix
     * @throws IllegalArgumentException if sizes don't match
     */
    public void checkSizeMatch(final SimpleSquareMatrix other) throws IllegalArgumentException {
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
    public abstract double get(int i, int j);
}
