package models.matrices;

public interface MutableSquareMatrix extends SimpleSquareMatrix{
    /**
     * Sets a_ij = x
     * @param i i
     * @param j j
     */
    void set(int i, int j, double x);
}