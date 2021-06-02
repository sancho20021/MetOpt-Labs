package methods.multidimensional;

import methods.multidimensional.quadratic.FastestDescent;
import methods.unidimensional.*;
import models.DiagonalMatrix;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;
import tasks.lab2.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskGenerationTest {
    final static private String BR = System.lineSeparator();

    private String getIterationsInfo(final Stream<Vector> iterations) {
        StringBuilder s = new StringBuilder();
        var points = iterations.collect(Collectors.toList());
        s.append("Number of iterations: ").append(points.size()).append(BR);
        for (int i = 0; i < points.size(); i++) {
            s.append("Iteration ").append(i).append(": ").append(points.get(i)).append(BR);
        }
        s.append(BR);
        return s.toString();
    }

    private void log(final String message, final boolean print) {
        if (print) {
            System.out.println(message);
        }
    }

    @Test
    public void testDifferentUniminimizers() {
        boolean print = false;

        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i < 2; i++) {
            tasks.add(Task.getRandomTask(2000, i));
        }
        var eps = 0.01;
        var mins = tasks.stream()
                .map(t -> new FastestDescent(t.f, t.initialPoint, eps, 1000000, ParabolicMinimizer.class))
                .collect(Collectors.toList());
        for (int i = 0; i < tasks.size(); i++) {
            log("Число обусловленности: " + (i + 1), print);
            log(tasks.get(i).toString(), print);
            log(getIterationsInfo(mins.get(i).points()), print);
        }
    }

    @Test
    public void testParabolic() {
        var startX = new Vector(1);
        QuadraticFunction f = new QuadraticFunction(
                new DiagonalMatrix(1),
                new Vector(0),
                0
        );
        var ps = new FastestDescent(f, startX, 0.01, 1000, ParabolicMinimizer.class).points();
        log(getIterationsInfo(ps), true);
    }
}
