package methods.multidimensional;

public class MUSORKA {
    public static void main(String[] args) {
        SquareMatrix m = new DiagonalMatrix(1.0, 2.0, 3.0);
        SquareMatrix b = new DiagonalMatrix(2.0, 3.0, 3.0);
        var c = m.multiply(b);
    }
}
