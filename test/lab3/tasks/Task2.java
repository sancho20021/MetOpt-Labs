package lab3.tasks;

import lab3.models.ProfileFormatMatrix;
import lab3.utils.generators.MatrixGenerators;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task2 {
    private static final Scanner scn = new Scanner(System.in);

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

    public static void main(String[] args) {
        print(5);
        final var kek = read(5);
        kek.forEach(m -> System.out.print(getProfileFormatString(m)));
    }
}
