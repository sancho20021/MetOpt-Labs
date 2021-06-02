package tasks.lab2;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import methods.multidimensional.quadratic.FastestDescent;
import methods.unidimensional.*;
import models.matrices.DiagonalMatrix;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;
import tasks.lab2.models.Task;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static methods.multidimensional.quadratic.Constants.*;
import static utils.JavaPlotExample.getPlot;
import static utils.JavaPlotExample.getPointsGraph;

public class Task1 {
    final static int DIMENSION = 2;
    private final static Map<String, Class<? extends Minimizer>> methodVariations = Map.of(
            "Дихотомия", GoldenMinimizer.class,
            "Золотое сечение", GoldenMinimizer.class,
            "Фибоначчи", FibonacciMinimizer.class,
            "Парабол", ParabolicMinimizer.class/*,
                "Брента", CombinationMinimizer.class*/
    );

    private static Stream<Vector> getPoints(final Task task, final Class<? extends Minimizer> m) {
        return new FastestDescent(task.f, task.initialPoint, SMALL_EPS, m).points();
    }

    private static int getItersNumber(final Task task, final Class<? extends Minimizer> m) {
        return (int) (getPoints(task, m).count() - 1);
//        descent.resetAllIterationsCount();
//        try {
//            descent.findMinimum();
//        } catch (TimeoutException e) {
//            System.err.println("Too much iterations of "
//                    + m.getSimpleName() + ", Task:" + System.lineSeparator() + task);
//            System.exit(0);
//        }
//        return descent.getAllIterationsCount();
    }

    private static IntStream getItersNumbers(final List<Task> tasks, Class<? extends Minimizer> m) {
        return tasks.stream().mapToInt(t -> getItersNumber(t, m));
    }

    private static String getArrayInfo(final String methodName, final double[] array) {
        return String.format(
                "Метод наискорейшего спуска с использованием метода '%s', "
                        + "Размерность: %d, "
                        + "число обусловленности от 1 до %d: %s",
                methodName,
                DIMENSION,
                array.length, Arrays.toString(array)
        );
    }

    public static double[] getAverageItersNumbers(final List<List<Task>> tasks, Class<? extends Minimizer> m) {
        return tasks.stream()
                .mapToDouble(ts -> getItersNumbers(ts, m).average().getAsDouble())
                .toArray();
    }

    public static List<Task> getRandomTasks(final int number, final int dimension, final int k) {
        return getRandomTasks(number, dimension, k, -1000, 1000);
    }

    public static List<Task> getRandomTasks(final int number, final int dim, final int k, final int lo, final int hi) {
        return IntStream.range(0, number).mapToObj(i -> Task.getRandomTask(dim, k, lo, hi)).collect(Collectors.toList());
    }

    public static List<Point2D.Double> getConditionArrayPoints(final double[] iterations) {
        var points = new ArrayList<Point2D.Double>();
        for (int i = 0; i < iterations.length; i++) {
            points.add(new Point2D.Double(i + 1, iterations[i]));
        }
        return points;
    }

    public static DataSetPlot getGraphFromConditionArray(final double[] iterationNumbers, final String name) {
        return getPointsGraph(getConditionArrayPoints(iterationNumbers), name);
    }

    public static JavaPlot conditionNumberToIterations(final Map<String, double[]> methodNameToIterations) {
        var graphs = methodNameToIterations.entrySet().stream()
                .map(entry -> getGraphFromConditionArray(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
        return getPlot(
                "Зависимость скорости сходимости от числа обусловленности, размерность пр-ва: " + DIMENSION,
                "Число обусловленности",
                "Число итераций",
                graphs
        );
    }

    private Map<String, double[]> getTestMethodsResults(final List<List<Task>> tasks) {
        final Map<String, double[]> results = new HashMap<>();
        methodVariations.forEach((key, value) -> {
            System.out.println("Testing " + key);
            results.put(key, getAverageItersNumbers(tasks, value));
        });
        return results;
    }

    @Test
    public void differentOneDimMinimizers() {
        System.out.println("EPS = " + SMALL_EPS);
        var tasks = IntStream.range(1, 11).mapToObj(k -> getRandomTasks(40, DIMENSION, k, -10, 10))
                .collect(Collectors.toList());
        var results = getTestMethodsResults(tasks);
        var plot = conditionNumberToIterations(results);
        plot.setTitle("Влияние выбора одном. метода опт. на скорость сходимости наиск. спуска.");
        plot.plot();
        printRecordStatistics();
    }

    private static void printRecordStatistics() {
        var taskForRecord = getRandomTasks(1, DIMENSION, 5, -10, 10).get(0);
        System.out.println("Задача минимизации:");
        System.out.println("Квадратичная функция:");
        System.out.println(taskForRecord.f);
        System.out.println("Начальная точка:");
        System.out.println(taskForRecord.initialPoint);
        System.out.println("Точность: " + SMALL_EPS);
        System.out.println("Результаты работы алгоритмов:");
        for (var m : methodVariations.entrySet()) {
            System.out.println(m.getKey());
            System.out.println(getItersNumber(taskForRecord, m.getValue()) + " Итераций");
            System.out.println("Точки приближения:");
            System.out.println(
                    getPoints(taskForRecord, m.getValue())
                            .map(v ->
                                    v.getElements().stream()
                                            .map(x -> String.format("%.7f", x))
                                            .collect(Collectors.toList())
                            ).collect(Collectors.toList())
            );
            System.out.println();
        }
    }

    @Test
    public void testBrent() {
        var task1 = new Task(
                new Vector(10, 10),
                new QuadraticFunction(
                        new DiagonalMatrix(3, 1),
                        new Vector(0, 0),
                        3
                )
        );
        System.out.println("-----------brent----------------");
        getItersNumber(task1, CombinationMinimizer.class);
        System.out.println("-----------parabolic----------------");
        getItersNumber(task1, ParabolicMinimizer.class);
    }
}
