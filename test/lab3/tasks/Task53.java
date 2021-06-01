package lab3.tasks;

import lab3.models.SparseMatrix;
import org.junit.Test;

import java.io.PrintWriter;

import static lab3.tasks.Task52.*;

public class Task53 {
    final public static String TEST_FOLDER = "Task53";

    private static void write53Matrix(final int n, final PrintWriter writer) {
        Task52.write52Matrix(n, writer, 1);
    }

    @Test
    public void generateMatrices() {
        final var t = new Task53();
        t.generateSmallMatrices();
        t.generateMediumMatrices();
        t.generateBigMatrices();
    }

    @Test
    public void generateSmallMatrices() {
        generate53Matrices(1, smallDimensions);
    }

    @Test
    public void generateMediumMatrices() {
        generate53Matrices(1, mediumDimensions);
    }

    @Test
    public void generateBigMatrices() {
        generate53Matrices(3, largeDimensions);
    }

    @Test
    public void solveAll() {
        Task52.solveAll(TEST_FOLDER, in -> new SparseMatrix(SparseMatrix.DisSymMatrix.read(in)));
    }

    @Test
    public void capd() {
        collectAndPrintData(TEST_FOLDER, "Матрицы с диагональным преобладанием, другой знак");
    }

    private static void generate53Matrices(final int samples, final int[] ns) {
        Task52.generate52Matrices(samples, ns, Task53::write53Matrix, TEST_FOLDER);
    }
}
