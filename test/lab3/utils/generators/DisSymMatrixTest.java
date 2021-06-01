package lab3.utils.generators;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Scanner;

public class DisSymMatrixTest {
    private final static Scanner scn = new Scanner(System.in);
    private final static PrintWriter out = new PrintWriter(System.out);

    @AfterClass
    public static void finish() {
        out.close();
    }

    @Test
    public void writeTest() {
        final int samples = 1;
        final int maxN = 5;
        for (int n = 1; n <= maxN; n++) {
            for (int k = 0; k < samples; k++) {
                printRandom(n, -4, 0);
                out.println();
            }
        }
    }

    private void printRandom(final int n, final int lo, final int hi) {
        MatrixGenerators.generateRandomDisSymMatrix(n, lo, hi).print(out);
    }
}
