package tasks.lab2;

import com.panayotis.gnuplot.plot.DataSetPlot;
import methods.multidimensional.*;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;
import tasks.lab2.models.Task;
import utils.JavaPlotExample;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static methods.multidimensional.Constants.SMALL_EPS;
import static methods.multidimensional.Constants.STANDARD_EPS;

public class Task3 {
    public static MultiMinimizer getMinimizerFromTask(final Task task, final Class<? extends MultiMinimizer> mToken) {
        try {
            var constructor = mToken
                    .getConstructor(QuadraticFunction.class, Vector.class, double.class);
            return constructor.newInstance(task.f, task.initialPoint, STANDARD_EPS);
        } catch (Exception e) {
            System.err.println("Error occurred while trying to create an instance of " + mToken.getSimpleName());
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }

    public static int[] getItersNumbers(final List<? extends MultiMinimizer> minimizers) {
        int[] iterationsNumbers = new int[minimizers.size()];
        for (int i = 0; i < minimizers.size(); i++) {
            System.out.println(i);
            iterationsNumbers[i] = (int) minimizers.get(i).points().count() - 1;
        }
        return iterationsNumbers;
    }

    public static double getAverage(final int[] array) {
        return Arrays.stream(array).average().getAsDouble();
    }

    public static List<? extends MultiMinimizer> minimizersFromTasks(
            final List<Task> tasks, final Class<? extends MultiMinimizer> mToken
    ) {
        return tasks.stream().map(t -> getMinimizerFromTask(t, mToken)).collect(Collectors.toList());
    }

    public static List<Point2D.Double> getPointsFromArrays(double[] xs, double[] ys) {
        return IntStream.range(0, xs.length).mapToObj(i -> new Point2D.Double(xs[i], ys[i]))
                .collect(Collectors.toList());
    }

    private DataSetPlot getOneGraph(final Class<? extends MultiMinimizer> mToken, final int dim) {
        int[] conditionNumbers = {1, 10, 30, 90, 270, 810};
        double[] conditionNumbersDouble = Arrays.stream(conditionNumbers).mapToDouble(x -> x).toArray();
        var minimizerSets = new ArrayList<List<? extends MultiMinimizer>>();
        for (int conditionNumber : conditionNumbers) {
            minimizerSets.add(minimizersFromTasks(Task1.getRandomTasks(4, dim, conditionNumber), mToken));
        }
        double[] averageIterations = new double[minimizerSets.size()];
        for (int i = 0; i < minimizerSets.size(); i++) {
            System.out.println("Testing condition number = " + conditionNumbers[i]);
            averageIterations[i] = getAverage(getItersNumbers(minimizerSets.get(i)));
        }
        return JavaPlotExample.getPointsGraph(
                getPointsFromArrays(conditionNumbersDouble, averageIterations),
                "n= " + dim
        );
    }

    private List<DataSetPlot> getAllGraphs(final Class<? extends MultiMinimizer> mToken) {
        int[] dims = {11, 100, 300, 1000, 10000};
        var dimGraphs = new ArrayList<DataSetPlot>();
        for (var dim : dims) {
            System.out.println("Testing n = " + dim);
            dimGraphs.add(getOneGraph(mToken, dim));
        }
        return dimGraphs;
    }

    private void plot(Class<? extends MultiMinimizer> mToken, final String methodName) {
        var graphs = getAllGraphs(mToken);
        var plot = JavaPlotExample.getPlot(
                "Зависимость числа итераций от числа обусл. " + methodName,
                "Число обусловленности",
                "Число итераций",
                graphs
        );
        plot.getAxis("x").setLogScale(false);
        plot.plot();
    }

    @Test
    public void gradientTest() {
        plot(GradientDescentMinimizer.class, "Градиентный спуск");
    }

    @Test
    public void fastestTest() {
        plot(FastestDescent.class, "Наискорейший спуск");
    }

    @Test
    public void conjugateTest() {
        plot(ConjugateGradientsMinimizer.class, "Сопряженные градиенты");
    }

    @Test
    public void compareMethodsForRecord() {
        compreMethodsForRecord(10, 2000);
        compreMethodsForRecord(100, 1000);
        compreMethodsForRecord(1000, 100);
        compreMethodsForRecord(10000, 50);
    }
    private static void compreMethodsForRecord(final int n, final int k) {
        var taskForRecord = Task1.getRandomTasks(1, n, k, -50000, 50000).get(0);
        System.out.println("Задача минимизации:");
        System.out.println("Размерность: " + taskForRecord.f.getA().size());
        System.out.println("Минимальное с.ч.: " + taskForRecord.f.getMinEigenValueAbs() + ", макс. с.ч.: " + taskForRecord.f.getMaxEigenValueAbs());
        System.out.println("Точность: " + SMALL_EPS);
        System.out.println("Результаты работы алгоритмов:");
        printItersNumberGradient(taskForRecord);
        printItersNumberFastest(taskForRecord);
        printItersNumberConjugate(taskForRecord);
        System.out.println();
    }

    private static void printItersNumberGradient(final Task task) {
        System.out.println("Градиентный спуск:");
        System.out.println(new GradientDescentMinimizer(task.f, task.initialPoint, SMALL_EPS).points().count() - 1);
    }

    private static void printItersNumberFastest(final Task task) {
        System.out.println("Наискорейший спуск:");
        System.out.println(new FastestDescent(task.f, task.initialPoint, SMALL_EPS).points().count() - 1);
    }

    private static void printItersNumberConjugate(final Task task) {
        System.out.println("Сопряженные градиенты:");
        System.out.println(new ConjugateGradientsMinimizer(task.f, task.initialPoint, SMALL_EPS).points().count() - 1);
    }
}
