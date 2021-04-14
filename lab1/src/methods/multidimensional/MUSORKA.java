package methods.multidimensional;

import java.util.List;

public class MUSORKA {
    public static void main(String[] args) {
//        var m1 = new FullMatrix(
//                List.of(
//                        new Vector(1, 0, 0),
//                        new Vector(0, 0, 0),
//                        new Vector(0, 0, 5)
//                )
//        );
        //SquareMatrix m1 = new DiagonalMatrix(1, 2, 3);
        var m2 = new FullMatrix(
                List.of(
                        new Vector(1, 2, 3, 4),
                        new Vector(5, 6, 7, 8),
                        new Vector(9, 10, 11, 12),
                        new Vector(13, 14, 15, 15)
                )
        );
        //SquareMatrix m2 = new DiagonalMatrix(3, 2, 2);
//        var product = m1.multiply(m2);
//        System.out.println(m1);
//        System.out.println();
//        System.out.println(m2);
//        System.out.println();
//        System.out.println(product);
//        System.out.println(product.rows());
        System.out.println(m2.columns());
    }
}
