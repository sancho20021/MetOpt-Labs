package methods.multidimensional;

public class GradientDescentMinimizer extends MultiMinimizer {
    private final int MAX_ITERATIONS = 10000;

    private double alpha;
    private Vector x;

    // these iterations
    private int iterationCounter;

    public GradientDescentMinimizer(final QuadraticFunction fun, double startAlpha, Vector startX, double eps) {
        super(fun, startAlpha, startX, eps);
        restart();
    }

    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps && iterationCounter < MAX_ITERATIONS;
    }

    public Vector nextIteration() {
        iterationCounter++;
        Vector next = x.add(fun.getGradient(x).normalize().multiply(-alpha));
        if (fun.get(next) < fun.get(x)) {
            return x = next;
        }
        alpha /= 2;
        return x;
    }

    @Override
    public void restart() {
        iterationCounter = 0;
        alpha = startAlpha;
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

}
