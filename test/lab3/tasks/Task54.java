package lab3.tasks;

import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;

import java.io.PrintWriter;

import static lab3.tasks.Task52.collectAndPrintData;

public class Task54 {
    public final static String TEST_FOLDER = "Task54";

    @Test
    public void generateSmallMatrices() {
        generateHilbertMatrices(new int[]{2, 4, 8, 16});
    }

    @Test
    public void generateMediumMatrices() {
        generateHilbertMatrices(new int[]{64, 128});
    }

    @Test
    public void generateBigMatrices() {
        generateHilbertMatrices(new int[]{256, 512, 1000});
    }

    @Test
    public void solveAll() {
        Task52.solveAll(TEST_FOLDER);
    }

    @Test
    public void capd() {
        collectAndPrintData(TEST_FOLDER, "Матрицы Гильберта");
    }

    public static void writeHilbertMatrix(final int n, final PrintWriter writer) {
        final double[][] hilbert = MatrixGenerators.generateHilbertMatrix(n);
        writer.println(n);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                writer.print(hilbert[i][j] + " ");
            }
            writer.println();
        }
        for (int i = 0; i < n; i++) {
            writer.print(hilbert[i][i] + " ");
        }
        writer.println();
    }

    private static void generateHilbertMatrices(final int[] ns) {
        Task52.generate52Matrices(1, ns, Task54::writeHilbertMatrix, TEST_FOLDER);
    }
}
