package tasks.lab2.models;

import models.DiagonalMatrix;
import models.SquareMatrix;
import models.Vector;
import models.functions.QuadraticFunction;

import java.util.Random;
import java.util.stream.DoubleStream;

public class Task {
    public final static Random random = new Random(228);
    final public Vector initialPoint;
    final public QuadraticFunction f;

    public Task(final Vector initialPoint, final QuadraticFunction f) {
        this.initialPoint = initialPoint;
        this.f = f;
    }

    private static double rand(double from, double to) {
        int fromInt = (int) Math.ceil(from);
        int toInt = (int) Math.ceil(to);
        return fromInt + random.nextInt(Math.max(1, toInt - fromInt));
    }

    private static double[] getRandomDoubles(int number, double lowerBound, double higherBound) {
        if (number <= 0) {
            return new double[0];
        }
        return DoubleStream.generate(() -> rand(lowerBound, higherBound))
                .limit(number)
                .toArray();
    }

    public static Task getRandomTask(int n, double k) {
        double eigenvalueMin = 1;
        double[] diag = new double[n];
        diag[0] = 1;
        diag[n - 1] = k;
        var middle = getRandomDoubles(n - 2, eigenvalueMin, k);
        if (n - 1 - 1 >= 0) System.arraycopy(middle, 0, diag, 1, n - 1 - 1);
        SquareMatrix a = new DiagonalMatrix(diag);
        Vector b = new Vector(new double[n]);
        double c = 0;
        Vector startX = new Vector(getRandomDoubles(n, -1000, 1000));
        return new Task(startX, new QuadraticFunction(a, b, c));
    }

    private static double[] getRandomConditionArray(int n, double k) {
        double[] diag = new double[n];
        diag[0] = 1;
        diag[n - 1] = k;
        var middle = getRandomDoubles(n - 2, 1, k);
        if (n - 1 - 1 >= 0) System.arraycopy(middle, 0, diag, 1, n - 1 - 1);
        return diag;
    }

    public static Task getRandomTask(int n, double k, int min, int max) {
        final SquareMatrix a = new DiagonalMatrix(getRandomConditionArray(n, k));
        final Vector b = new Vector(new double[n]);
        final double c = 0;
        final Vector startX = new Vector(getRandomDoubles(n, min, max));
        return new Task(startX, new QuadraticFunction(a, b, c));
    }

    @Override
    public String toString() {
        return String.format("Quadratic function:%n%s%nInitial point:%n%s%n", f, initialPoint);
    }
}
