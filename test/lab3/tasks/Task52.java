package lab3.tasks;

import lab3.methods.ConjugateLSSolver;
import lab3.models.SparseMatrix;
import lab3.models.Vector;
import lab3.utils.generators.MainGenerator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;


public class Task52 {
    final public static String TEST_FOLDER = "Task52";

    private static void write52Matrix(final int n, final PrintWriter writer) {
        write52Matrix(n, writer, -1);
    }

    public static void write52Matrix(final int n, final PrintWriter writer, final double sign) {
        writer.println(n);
        final double[] sums = new double[n];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                final double x = MainGenerator.getInt(-4, 1, 1.0 / Math.sqrt(i));
                sums[j] += x;
                writer.print(x + " ");
            }
            writer.println();
        }
        for (int i = 0; i < n; i++) {
            writer.print((sign * sums[i] + (i == 0 ? 1 : 0)) + " ");
        }
        writer.println();
    }

    @Test
    public void generateSmallMatrices() {
        generate52Matrices(2, new int[]{2, 4, 8, 16, 32});
    }

    @Test
    public void generateMediumMatrices() {
        generate52Matrices(3, new int[]{64, 128, 256, 512});
    }

    @Test
    public void generateBigMatrices() {
        generate52Matrices(2, new int[]{1024, 2048, 4096});
    }

    @Test
    public void solveAll() {
        solveAll(TEST_FOLDER);
    }

    public static List<String> getData(final SparseMatrix a) {
        final Vector x = getNthAscending(a.size());
        final Vector f = a.multiply(x);
        final ConjugateLSSolver solver = new ConjugateLSSolver(a, f, getZeros(a.size()));
        final Vector got = solver.solve().orElseThrow();
        final List<String> data = new ArrayList<>();
        data.add(Integer.toString(a.size()));  // n
        data.add(Integer.toString(solver.getIteration()));  // iterations
        final double diff = got.subtract(x).getEuclideanNorm();
        data.add(Double.toString(diff));  // ||x* - x||
        final double relativeDiff = diff / x.getEuclideanNorm();
        data.add(Double.toString(relativeDiff)); // ||x* - x||/||x*||
        data.add(Double.toString(relativeDiff / (f.subtract(a.multiply(got)).getEuclideanNorm() / f.getEuclideanNorm()))); // cond >=
        return data;
    }

    public static void solveAll(final String testFolder) {
        FileTesting.solveTests(testFolder, (input, output) -> {
            try {
                final Scanner in = new Scanner(new FileInputStream(input));
                final PrintWriter out = new PrintWriter(output);
                final SparseMatrix a = new SparseMatrix(in);
                try {
                    final List<String> data = getData(a);
                    out.println("OK");
                    data.forEach(out::println);
                } catch (final RuntimeException e) {
                    out.println("ERROR " + e.getMessage());
                } finally {
                    out.close();
                }
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    private static void generate52Matrices(final int samples, final int[] ns) {
        generate52Matrices(samples, ns, Task52::write52Matrix, TEST_FOLDER);
    }

    public static void generate52Matrices(final int samples, final int[] ns, final BiConsumer<Integer, PrintWriter> write, final String testFolder) {
        try {
            for (int k = 0; k < samples; k++) {
                for (int n : ns) {
                    FileTesting.writeFile("Test n = " + n + ", sample = " + k, testFolder, writer -> write.accept(n, writer));
                }
            }
        } catch (final IOException e) {
            System.err.println("Error while writing tests");
        }
    }

    public static void read52Matrices(final String testFolder) {
        FileTesting.solveTests(testFolder, (input, output) -> {
            try {
                final Scanner in = new Scanner(new FileInputStream(input));
                final PrintWriter out = new PrintWriter(output);
                final SparseMatrix matrix = new SparseMatrix(in);
                out.println("OK");
                out.close();
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    @Test
    public void read52Matrices() {
        read52Matrices(TEST_FOLDER);
    }

    public static Vector getNthAscending(final int n) {
        return new Vector(IntStream.range(0, n).mapToDouble(x -> x + 1).toArray());
    }

    public static Vector getZeros(final int n) {
        return new Vector(new double[n]);
    }
}
