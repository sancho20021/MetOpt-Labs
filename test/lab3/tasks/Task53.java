package lab3.tasks;

import org.junit.Test;

import java.io.PrintWriter;

import static lab3.tasks.Task52.collectAndPrintData;

public class Task53 {
    final public static String TEST_FOLDER = "Task53";

    private static void write53Matrix(final int n, final PrintWriter writer) {
        Task52.write52Matrix(n, writer, 1);
    }

    @Test
    public void generateSmallMatrices() {
        generate53Matrices(2, new int[]{2, 4, 8, 16, 32});
    }

    @Test
    public void generateMediumMatrices() {
        generate53Matrices(3, new int[]{64, 128, 256, 512});
    }

    @Test
    public void generateBigMatrices() {
        generate53Matrices(2, new int[]{1024, 2048, 4096});
    }

    @Test
    public void solveAll() {
        Task52.solveAll(TEST_FOLDER);
    }

    @Test
    public void capd() {
        collectAndPrintData(TEST_FOLDER, "Матрицы с диагональным преобладанием, другой знак");
    }

    private static void generate53Matrices(final int samples, final int[] ns) {
        Task52.generate52Matrices(samples, ns, Task53::write53Matrix, TEST_FOLDER);
    }
}
