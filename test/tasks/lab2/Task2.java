package tasks.lab2;

import methods.multidimensional.ConjugateGradientsMinimizer;
import methods.multidimensional.FastestDescent;
import methods.multidimensional.GradientDescentMinimizer;
import models.FullMatrix;
import models.SquareMatrix;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;
import tasks.lab2.models.Task;

import java.util.stream.Collectors;

import static methods.multidimensional.Constants.SMALL_EPS;

public class Task2 {
    private static void testCondition(final SquareMatrix a, final double l, final double L, final Vector startX) {
        final Vector b = new Vector(0, 0);
        final double c = 0;
        final QuadraticFunction f = new QuadraticFunction(a, b, c);
        final Task task = new Task(startX, f);

        System.out.println("Задача минимизации:");
        System.out.println("Квадратичная функция:");
        System.out.println(task.f);
        System.out.println("Минимальное с.ч.: " + l + ", макс. с.ч.: " + L);
        System.out.println("Начальная точка:");
        System.out.println(task.initialPoint);
        System.out.println("Точность: " + SMALL_EPS);
        System.out.println("Результаты работы алгоритмов:");
        System.out.println();

        testGradient(task, SMALL_EPS, 2 / (l + L));
        System.out.println();

        testFastest(task, SMALL_EPS, 10000);
        System.out.println();

        testConjugate(task, SMALL_EPS);
        System.out.println();
    }

    @Test
    public void testBadCondition() {
        // f(x, y) = 1 / 2 * (5x^2 + 3y^2 + 6xy)
        testCondition(
                new FullMatrix(5, 3, 3, 3),
                0.8378,
                7.1623,
                new Vector(100, -20)
        );
    }

    @Test
    public void testGoodCondition() {
        // f(x, y) = 30x^2 + 25y^2
        testCondition(
                new FullMatrix(60, 0, 0, 50),
                50,
                60,
                new Vector(20, 30)
        );
    }
    @Test
    public void testBestCondition() {
        // f(x, y) = 30x^2 + 25y^2
        testCondition(
                new FullMatrix(60, 0, 0, 50),
                50,
                60,
                new Vector(0, 30)
        );
    }

    private static void testGradient(final Task task, final double eps, final double startAlpha) {
        System.out.println("Градиентный спуск:");
        final var points = new GradientDescentMinimizer(task.f, startAlpha, task.initialPoint, eps).points().collect(Collectors.toList());
        System.out.println("Число итераций: " + (points.size() - 1));
        System.out.println("Последовательность точек: ");
        System.out.println(points);
    }

    private static void testFastest(final Task task, final double eps, final double startA) {
        System.out.println("Наискорейший спуск:");
        final var points = new FastestDescent(task.f, task.initialPoint, eps, startA).points().collect(Collectors.toList());
        System.out.println("Число итераций: " + (points.size() - 1));
        System.out.println("Последовательность точек: ");
        System.out.println(points);
    }

    private static void testConjugate(final Task task, final double eps) {
        System.out.println("Сопряженные градиенты:");
        final var points = new ConjugateGradientsMinimizer(task.f, task.initialPoint, eps).points().collect(Collectors.toList());
        System.out.println("Число итераций: " + (points.size() - 1));
        System.out.println("Последовательность точек: ");
        System.out.println(points);
    }
}
