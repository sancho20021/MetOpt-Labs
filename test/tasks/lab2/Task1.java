package tasks.lab2;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import methods.multidimensional.FastestDescent;
import methods.unidimensional.*;
import models.DiagonalMatrix;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;
import tasks.lab2.models.Task;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static methods.multidimensional.Constants.STANDARD_EPS;
import static utils.JavaPlotExample.getPlot;
import static utils.JavaPlotExample.getPointsGraph;

public class Task1 {
    final static int DIMENSION = 2;

    private static int getItersNumber(final Task task, final Class<? extends Minimizer> m) {
        var descent = new FastestDescent(task.f, task.initialPoint, STANDARD_EPS, m);
        return (int) descent.points().count() - 1;
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
        return IntStream.range(0, number).mapToObj(i -> Task.getRandomTask(dimension, k)).collect(Collectors.toList());
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

    private Map<String, double[]> getTestMethodsResults(
            final List<List<Task>> tasks,
            final Map<String, Class<? extends Minimizer>> methodVariations
    ) {
        final Map<String, double[]> results = new HashMap<>();
        methodVariations.forEach((key, value) -> {
            System.out.println("Testing " + key);
            results.put(key, getAverageItersNumbers(tasks, value));
        });
        return results;
    }

    @Test
    public void differentOneDimMinimizers() {
        System.out.println("EPS = " + STANDARD_EPS);
        var tasks = IntStream.range(1, 11).mapToObj(k -> getRandomTasks(10, DIMENSION, k))
                .collect(Collectors.toList());
        final Map<String, Class<? extends Minimizer>> methodVariations = Map.of(
                "Дихотомия", GoldenMinimizer.class,
                "Золотое сечение", GoldenMinimizer.class,
                "Фибоначчи", FibonacciMinimizer.class,
                "Парабол", ParabolicMinimizer.class,
                "Брента", CombinationMinimizer.class
        );
        var results = getTestMethodsResults(tasks, methodVariations);
        var plot = conditionNumberToIterations(results);
        plot.setTitle("Влияние выбора одном. метода опт. на скорость сходимости наиск. спуска.");
        plot.plot();
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
