package lab3.models;

import java.util.Optional;

public class LinearSystem {
    private final FullMatrix matrix;
    private final Vector b;
    private final Optional<Vector> correctAnswer;

    public LinearSystem(final FullMatrix matrix, final Vector b, final Vector correctAnswer) {
        this.matrix = matrix;
        this.b = b;
        this.correctAnswer = Optional.of(correctAnswer);
        assert matrix.size() == b.getDim();
        assert matrix.size() == correctAnswer.getDim();
    }

    public LinearSystem(final FullMatrix matrix, final Vector b) {
        this.matrix = matrix;
        this.b = b;
        this.correctAnswer = Optional.empty();
        assert matrix.size() == b.getDim();
    }

    public FullMatrix getMatrix() {
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
}
