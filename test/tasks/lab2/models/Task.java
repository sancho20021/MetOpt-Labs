package tasks.lab2.models;

import models.DiagonalMatrix;
import models.SquareMatrix;
import models.Vector;
import models.functions.QuadraticFunction;

import java.util.stream.DoubleStream;

public class Task {
    final public Vector initialPoint;
    final public QuadraticFunction f;

    public Task(final Vector initialPoint, final QuadraticFunction f) {
        this.initialPoint = initialPoint;
        this.f = f;
    }

    private static double rand(double from, double to) {
        return from + Math.random() * (to - from);
    }

    private static double[] getRandomDoubles(int number, double lowerBound, double higherBound) {
        return DoubleStream.generate(() -> rand(lowerBound, higherBound))
                .limit(number)
                .toArray();
    }

    public static Task getRandomTask(int n, double k) {
        double eigenvalueMin = 1;
        SquareMatrix a = new DiagonalMatrix(getRandomDoubles(n, eigenvalueMin, k));
        Vector b = new Vector(getRandomDoubles(n, -1000, 1000));
        double c = rand(eigenvalueMin, k);
        Vector startX = new Vector(getRandomDoubles(n, -1000, 1000));
        return new Task(startX, new QuadraticFunction(a, b, c));
    }

    @Override
    public String toString() {
        return String.format("Quadratic function:%n%s%nInitial point:%n%s%n", f, initialPoint);
    }
}
