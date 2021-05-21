package lab3.tasks;

import lab3.methods.LU;
import lab3.models.*;
import lab3.utils.generators.LSGenerators;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;
import utils.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static lab3.tasks.Task2.toPrettyStr;

public class Task3 {
    public static final String TEST_FOLDER = "Task3";

    private static LinearSystem generateProfileHilbertSystem(final int n) {
        return generateHilbertSystem(n, ProfileFormatMatrix::new);
    }

    private static LinearSystem generateHilbertSystem(final int n, final Function<double[][], MutableSquareMatrix> matrixCons) {
        return LSGenerators.getLS(MatrixGenerators.generateHilbertMatrix(n), matrixCons);
    }

    @Test
    public void createTests() {
        final Map<Integer, String> testGroups = Map.of(10, "n=10", 100, "n=100", 1000, "n=1000");
        testGroups.forEach((n, name) -> {
            final LinearSystem system = generateProfileHilbertSystem(n);
            final String testData = Task2.getLinearSystemProfileString(system);
            try {
                FileTesting.writeFile(name, TEST_FOLDER, testData);
            } catch (final IOException e) {
                System.err.println("Couldn't create test: ");
                System.err.println(testData);
            }
        });
    }

    @Test
    public void solveAllProblems() {
        System.out.println("Testing Hilbert matrices");
        FileTesting.solveTests(TEST_FOLDER, new Task2.ProfileFormatLUSolver());
        System.out.println("OK!");
    }

    public static void printHilbertStatistics(final String methodName, final BiFunction<MutableSquareMatrix, double[], double[]> solver,
                                              final Function<double[][], MutableSquareMatrix> matrixCons) {
        List<List<String>> entries = new ArrayList<>();
        for (int n = 2; n <= 128; n = n * 3 / 2) {
            LinearSystem ls = generateHilbertSystem(n, matrixCons);
            Vector numericalSolution = new Vector(solver.apply(ls.getA(), ls.getB().getElementsArrayCopy()));
            double euclideanError = ls.getEuclideanError(numericalSolution);
            entries.add(List.of(
                    Integer.toString(n),
                    toPrettyStr(euclideanError),
                    toPrettyStr(euclideanError / ls.getCorrectAnswer().getEuclideanNorm())));
        }
        System.out.println(new Table(
                methodName + ", матрицы Гильберта",
                List.of("$n$", "$||x^{*}-x||$", "$\\frac{||x^{*}-x||}{||x^{*}||}$"),
                entries).toTex());
    }

    public static void printHilbertMatrix(final int n) {
        System.out.println(generateHilbertSystem(n, FullMatrix::new).getA().convertToString());
    }

    @Test
    public void testPrintHilbert() {
        printHilbertMatrix(8);
    }

    @Test
    public void printHilbertStatistics() {
        printHilbertStatistics("LU разложение", LU::solveInPlace, ProfileFormatMatrix::new);
    }
}
