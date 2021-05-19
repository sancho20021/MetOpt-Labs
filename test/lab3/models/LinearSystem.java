package lab3.models;

import java.util.Optional;
import java.util.function.Supplier;

public class LinearSystem {
    private final AdvancedMatrix matrix;
    private final Vector b;
    private final Optional<Vector> correctAnswer;

    public LinearSystem(final AdvancedMatrix matrix, final Vector b, final Vector correctAnswer) {
        this.matrix = matrix;
        this.b = b;
        this.correctAnswer = Optional.of(correctAnswer);
        assert matrix.size() == b.getDim();
        assert matrix.size() == correctAnswer.getDim();
    }
    public LinearSystem(final double[][] a, final double[] b) {
        this(new FullMatrix(a), new Vector(b));
    }

    public LinearSystem(final AdvancedMatrix matrix, final Vector b) {
        this.matrix = matrix;
        this.b = b;
        this.correctAnswer = Optional.empty();
        assert matrix.size() == b.getDim();
    }

    public AdvancedMatrix getA() {
        return matrix;
    }

    public Vector getB() {
        return b;
    }

    public Vector getCorrectAnswer() {
        return correctAnswer.orElse(null);
    }

    public Vector getError(final Vector other) {
        return correctAnswer.orElse(null).subtract(other);
    }

    public double getEuclideanError(final Vector other) {
        return getError(other).getEuclideanNorm();
    }

    public Vector getResidual(final Vector other) {
        return b.subtract(matrix.multiply(other));
    }

    public static Supplier<LinearSystem> newLSGenerator(final Supplier<double[][]> getA, final Supplier<double[]> getB) {
        return () -> new LinearSystem(getA.get(), getB.get());
    }

    public String toRawString() {
        StringBuilder result = new StringBuilder();
        final String lf = System.lineSeparator();
        result.append(matrix.size()).append(lf);
        result.append(matrix.toRawString()).append(lf);
        result.append(b.toRawString()).append(lf);
        return result.toString();
    }
}
