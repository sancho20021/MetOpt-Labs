package tasks.lab2;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import methods.multidimensional.FastestDescent;
import methods.unidimensional.*;
import org.junit.Test;
import tasks.lab2.models.Task;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static methods.multidimensional.Constants.STANDARD_EPS;
import static methods.multidimensional.Constants.STANDARD_MAXA;
import static utils.JavaPlotExample.getPlot;
import static utils.JavaPlotExample.getPointsGraph;

public class Task1 {
    final static int DIMENSION = 2;

    private static int getItersNumber(final Task task, final Class<? extends Minimizer> m) {
        var descent = new FastestDescent(task.f, task.initialPoint, STANDARD_EPS, STANDARD_MAXA, m);
        descent.resetAllIterationsCount();
        try {
            descent.findMinimum();
        } catch (TimeoutException e) {
            System.err.println("Too much iterations of "
                    + m.getSimpleName() + ", Task:" + System.lineSeparator() + task);
            System.exit(0);
        }
        return descent.getAllIterationsCount();
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

    @Test
    public void differentOneDimMinimizers() {
        var tasks = IntStream.range(1, 10).mapToObj(k -> getRandomTasks(100, DIMENSION, k))
                .collect(Collectors.toList());
        double[] dichotomy = getAverageItersNumbers(tasks, DichotomyMinimizer.class);
        double[] golden = getAverageItersNumbers(tasks, GoldenMinimizer.class);
        double[] fibonacci = getAverageItersNumbers(tasks, FibonacciMinimizer.class);
        double[] parabolic = getAverageItersNumbers(tasks, ParabolicMinimizer.class);
        var map = Map.of(
                "Дихотомия", dichotomy,
                "Золотое сечение", golden,
                "Фибоначчи", fibonacci,
                "Парабол", parabolic
        );
        var plot = conditionNumberToIterations(map);
        plot.setTitle("Влияние выбора одном. метода опт. на скорость сходимости метода наиск. спуска. " +
                "Размерность пространства: " + DIMENSION);
        plot.plot();
    }
}
