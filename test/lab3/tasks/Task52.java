package lab3.tasks;

import lab3.methods.ConjugateLSSolver;
import lab3.models.SparseMatrix;
import lab3.models.Vector;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;
import utils.Table;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;


public class Task52 {
    final public static String TEST_FOLDER = "Task52";

    private static void write52Matrix(final int n, final PrintWriter writer) {
        write52Matrix(n, writer, -1);
    }

    public static void write52Matrix(final int n, final PrintWriter writer, final double sign) {
        MatrixGenerators.generateRandomDisSymMatrix(n, sign > 0 ? 1 : -4, sign > 0 ? 5 : 0).print(writer);
    }

    @Test
    public void generateMatrices() {
        final var t = new Task52();
        t.generateSmallMatrices();
        t.generateMediumMatrices();
        t.generateBigMatrices();
    }

    @Test
    public void generateSmallMatrices() {
        generate52Matrices(1, smallDimensions);
    }

    @Test
    public void generateMediumMatrices() {
        generate52Matrices(1, mediumDimensions);
    }

    @Test
    public void generateBigMatrices() {
        generate52Matrices(3, largeDimensions);
    }

    @Test
    public void solveAll() {
        solveAll(TEST_FOLDER, in -> new SparseMatrix(SparseMatrix.DisSymMatrix.read(in)));
    }

    @Test
    public void capd() {
        collectAndPrintData(TEST_FOLDER, "Матрицы с диагональным преобладанием");
    }

    public final static int[] smallDimensions = {2, 4, 16, 64};
    public final static int[] mediumDimensions = {300, 1000, 4000};
    public final static int[] largeDimensions = {10000, 100000};

    public static List<String> getData(final SparseMatrix a) {
        final Vector x = getNthAscending(a.size());
        final Vector f = a.multiply(x);
        final ConjugateLSSolver solver = new ConjugateLSSolver(a, f, getZeros(a.size()));
        final Vector got = solver.solve().orElseThrow();
        final List<String> data = new ArrayList<>();
        data.add(Integer.toString(a.size()));  // n
        data.add(Integer.toString(solver.getIteration()));  // iterations
        final double diff = got.subtract(x).getEuclideanNorm();
        data.add(toString(diff));  // ||x* - x||
        final double relativeDiff = diff / x.getEuclideanNorm();
        data.add(toString(relativeDiff)); // ||x* - x||/||x*||
        data.add(toString(relativeDiff / (f.subtract(a.multiply(got)).getEuclideanNorm() / f.getEuclideanNorm()))); // cond >=
        return data;
    }

    private static String toString(double x) {
        return String.format("%.7f", x);
    }

    public static void solveAll(final String testFolder, final Function<Scanner, SparseMatrix> matrixReader) {
        FileTesting.solveTests(testFolder, (input, output) -> {
            try {
                final Scanner in = new Scanner(new FileInputStream(input));
                final PrintWriter out = new PrintWriter(output);
                final SparseMatrix a = matrixReader.apply(in);
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

    private static List<String> parseFile(final File file) {
        try {
            final Scanner scn = new Scanner(new FileInputStream(file));
            final String firstLine = scn.nextLine();
            if (!firstLine.startsWith("OK")) {
                return null;
            }
            return List.of(scn.next(), scn.next(), scn.next(), scn.next(), scn.next());
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void sort(final List<List<String>> lists) {
        lists.sort(Comparator.comparing(list -> Integer.parseInt(list.get(0))));
    }

    public static void collectAndPrintData(final String testFolder, final String name) {
        final Path testDir = FileTesting.TEST_FOLDERS.get(testFolder);
        if (testDir == null) {
            System.err.println("TestFolder " + testFolder + " not found");
            return;
        }
        try {
            final List<List<String>> table = new ArrayList<>();
            Files.walk(testDir, 3)
                    .map(Path::toFile)
                    .filter(file -> file.isFile() && file.getName().startsWith("ANS_"))
                    .forEach(ans -> table.add(parseFile(ans)));
            sort(table);
            System.out.println(new Table(
                    name,
                    List.of("n", "Количество итераций", "$\\| x^* - x \\|$", "$\\frac{\\|x^* - x\\|}{\\|x^*\\|}$", "cond($A$)"),
                    table
            ).toTex());
        } catch (final IOException e) {
            System.err.println("Error while walking the tests folders");
        }
    }
}
