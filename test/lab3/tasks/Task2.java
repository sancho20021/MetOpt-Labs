package lab3.tasks;

import lab3.methods.LU;
import lab3.models.LinearSystem;
import lab3.models.ProfileFormatMatrix;
import lab3.models.Vector;
import lab3.utils.generators.LSGenerators;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;
import utils.Table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task2 {
    private static final Scanner scn = new Scanner(System.in);
    public static final String TEST_FOLDER = "Task2";

    public static <T> String property(final String name, final T property, final Function<T, String> toString) {
        return name + " " + toString.apply(property);
    }

    public static void addLine(final StringBuilder s, final String line) {
        s.append(line).append(System.lineSeparator());
    }

    public static <T> String getArrayWithSpaces(final T[] a) {
        return Arrays.stream(a).map(T::toString).collect(Collectors.joining(" "));
    }

    public static String getArrayWithSpaces(final double[] a) {
        return Arrays.stream(a).mapToObj(Double::toString).collect(Collectors.joining(" "));
    }

    public static String getArrayWithSpaces(final int[] a) {
        return Arrays.stream(a).mapToObj(Integer::toString).collect(Collectors.joining(" "));
    }

    public static String getProfileFormatString(final ProfileFormatMatrix a) {
        final StringBuilder s = new StringBuilder();
        addLine(s, property("n", a.size(), i -> Integer.toString(i)));
        addLine(s, property("d", a.getDiCopy(), Task2::getArrayWithSpaces));
        addLine(s, property("ia", a.getIaCopy(), Task2::getArrayWithSpaces));
        addLine(s, property("au", a.getAuCopy(), Task2::getArrayWithSpaces));
        addLine(s, property("al", a.getAlCopy(), Task2::getArrayWithSpaces));
        return s.toString();
    }

    public static <T> T parseLineProperty(final Scanner in, final Function<String, T> parse) {
        final String line = in.nextLine();
        return parse.apply(line.substring(line.indexOf(' ') + 1));
    }

    public static double[] parseDoubleArray(final String s) {
        return Arrays.stream(s.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }

    public static int[] parseIntArray(final String s) {
        return Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static ProfileFormatMatrix parseProfileFormatMatrix(final Scanner in) {
        final int n = parseLineProperty(in, Integer::parseInt);
        final double[] d = parseLineProperty(in, Task2::parseDoubleArray);
        final int[] ia = parseLineProperty(in, Task2::parseIntArray);
        final double[] au = parseLineProperty(in, Task2::parseDoubleArray);
        final double[] al = parseLineProperty(in, Task2::parseDoubleArray);
        return new ProfileFormatMatrix(n, d, ia, au, al);
    }

    private static void print(final int count) {
        final int n = 10;
        Stream.generate(() -> new ProfileFormatMatrix(MatrixGenerators.generateIntegerMatrix(n, -3, 3)))
                .limit(count)
                .forEach(m -> System.out.print(getProfileFormatString(m)));
    }

    private static List<ProfileFormatMatrix> read(final int count) {
        return Stream.generate(() -> parseProfileFormatMatrix(scn)).limit(count).collect(Collectors.toList());
    }

    public static LinearSystem parseLS(final Scanner in) {
        final var a = parseProfileFormatMatrix(in);
        final var b = parseLineProperty(in, Task2::parseDoubleArray);
        final var xStar = parseLineProperty(in, Task2::parseDoubleArray);
        return new LinearSystem(a, new Vector(b), new Vector(xStar));
    }

    public static String getLinearSystemProfileString(final LinearSystem s) {
        final StringBuilder str = new StringBuilder();
        str.append(getProfileFormatString((ProfileFormatMatrix) s.getA()));
        addLine(str, property("b", s.getB(), Vector::toRawString));
        addLine(str, property("x", s.getCorrectAnswer(), Vector::toRawString));
        return str.toString();
    }

    private static LinearSystem generateAkSystem(final int n, final int k) {
        return LSGenerators.getProfilFormatLS(MatrixGenerators.generateAkMatrix(n, k));
    }

    @Test
    public void createTests() {
        final Map<Integer, String> testGroups = Map.of(10, "n=10", 100, "n=100", 1000, "n=1000");
        final int maxK = 5;
        testGroups.forEach((n, name) -> {
            final AtomicInteger k = new AtomicInteger(0);
            LSGenerators.generateSystems(n, Task2::generateAkSystem).limit(maxK).forEach(system -> {
                final String testData = getLinearSystemProfileString(system);
                try {
                    FileTesting.writeFile(name + "_k=" + k.getAndIncrement(), TEST_FOLDER, testData);
                } catch (final IOException e) {
                    System.err.println("Couldn't create test: ");
                    System.err.println(testData);
                }
            });
        });
    }

    @Test
    public void solveAllProblems() {
        FileTesting.solveTests(TEST_FOLDER, new ProfileFormatLUSolver());
        System.out.println("OK!");
    }

    static class ProfileFormatLUSolver implements FileTesting.Solver {
        @Override
        public void solve(File problem, File solution) {
            try (final PrintWriter writer = new PrintWriter(new FileWriter(solution))) {
                final Scanner in = new Scanner(problem);
                final LinearSystem ls = parseLS(in);
                final Vector ans = new Vector(LU.solveInPlace(ls.getA(), ls.getB().getElementsArrayCopy()));
                writer.println("Expected: " + ls.getCorrectAnswer().toRawString());
                writer.println("Got: " + ans.toRawString());
                writer.println("Error: " + ls.getEuclideanError(ans));
            } catch (final IOException e) {
                System.err.println("Error while I/O with " + problem + ", " + solution);
            }
        }
    }

    public static void main(String[] args) {
        print(5);
        final var kek = read(5);
        kek.forEach(m -> System.out.print(getProfileFormatString(m)));
    }
}
