package methods.multidimensional;

import models.DiagonalMatrix;
import models.SquareMatrix;
import models.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class FullMatrixTest {

    @Test
    public void testMultiply() {
        SquareMatrix matrix1 = new DiagonalMatrix(1.0, 2.0, 3.0);
        SquareMatrix matrix2 = new DiagonalMatrix(1.0, 2.0, 3.0);
        SquareMatrix result = matrix1.multiply(matrix2);
        IntStream.range(0, result.size()).forEach(i -> {
            Vector row = result.row(i);
            for (int j = 0; j < row.getDim(); j++) {
                if (i == j) {
                    Assert.assertNotEquals(0, row.getIth(j), 0.0);
                } else {
                    Assert.assertEquals(0, row.getIth(j), 0.0);
                }
            }
        });

    }
}
