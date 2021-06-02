package methods.multidimensional;

import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;
import models.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class FullMatrixTest {

    @Test
    public void testMultiply() {
        AdvancedMatrix matrix1 = new DiagonalMatrix(1.0, 2.0, 3.0);
        AdvancedMatrix matrix2 = new DiagonalMatrix(1.0, 2.0, 3.0);
        AdvancedMatrix result = matrix1.multiply(matrix2);
        IntStream.range(0, result.size()).forEach(i -> {
            Vector row = result.getRow(i);
            for (int j = 0; j < row.size(); j++) {
                if (i == j) {
                    Assert.assertNotEquals(0, row.get(j), 0.0);
                } else {
                    Assert.assertEquals(0, row.get(j), 0.0);
                }
            }
        });

    }
}
