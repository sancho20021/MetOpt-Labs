package lab3.methods;

import lab3.models.SimpleSquareMatrix;
import lab3.models.SparseMatrix;
import lab3.models.Vector;

import java.util.Optional;

public class ConjugateLSSolver {
    public final static double STANDARD_EPS = 1e-7;
    private final SparseMatrix a;
    private final Vector f;
    private final double eps;
    private Vector x;
    private Vector r;
    private Vector z;
    private double rr;
    private int iteration;
    public final static int MAX_ITERATIONS = 10000;

    public ConjugateLSSolver(final SimpleSquareMatrix a, final Vector f, final Vector x0, double eps) {
        this(new SparseMatrix(a), f, x0, eps);
    }

    public ConjugateLSSolver(final SimpleSquareMatrix a, final Vector f, final Vector x0) {
        this(a, f, x0, STANDARD_EPS);
    }

    public ConjugateLSSolver(final SparseMatrix a, final Vector f, final Vector x0, double eps) {
        this.a = a;
        this.f = f;
        this.x = x0;
        this.eps = eps;
        this.r = f.subtract(a.multiply(x0));
        z = r;
        rr = r.scalarProduct(r);
        iteration = 0;
    }

    public ConjugateLSSolver(final SparseMatrix a, final Vector f, final Vector x0) {
        this(a, f, x0, STANDARD_EPS);
    }

    public Optional<Vector> solve() {
        while (hasNext()) {
            next();
        }
        System.out.println("-----------solved---------------");
        return iteration >= MAX_ITERATIONS ? Optional.empty() : Optional.of(x);
    }

    public int getIteration() {
        return iteration;
    }

    private boolean hasNext() {
        if (iteration >= MAX_ITERATIONS) {
            return false;
        }
        final double rNorm = r.getEuclideanNorm();
        final double fNorm = f.getEuclideanNorm();
        final double relativeDiff = fNorm == 0 ? rNorm : rNorm / fNorm;
        return relativeDiff > eps;
    }

    private void next() {
        final Vector az = a.multiply(z);
        final double alpha = rr / az.scalarProduct(z);
        x = x.add(z.multiply(alpha));
        final double rrPrev = rr;
        r = r.subtract(az.multiply(alpha));
        rr = r.scalarProduct(r);
        final double beta = rr / rrPrev;
        z = r.add(z.multiply(beta));
        iteration++;

        System.out.println("||Ax - f|| = " + a.multiply(x).subtract(f).getEuclideanNorm());
    }
}
