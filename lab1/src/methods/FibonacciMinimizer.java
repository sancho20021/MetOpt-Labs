package methods;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer extends Minimizer {
    private static final List<Double> fibonacciNums = new ArrayList<>(List.of(0.0, 1.0, 1.0));
    private double curA, curB;
    private final int n;
    private int curIteration;

    public FibonacciMinimizer(Function fun, double a, double b, double eps) {
        super(fun, a, b, eps);
        int t = 0;
        while (fib(t) <= (b - a) / eps) {
            t++;
        }
        n = t;
        restart();
    }

    @Override
    public boolean hasNext() {
        return curIteration <= n;
    }

    @Override
    protected Section nextIteration() {
        double x1 = curA + fib(n - curIteration + 1) / fib(n + 2) * (b - a);
        double x2 = curA + fib(n - curIteration + 2) / fib(n + 2) * (b - a);
        curIteration++;
        if (fun.evaluate(x1) <= fun.evaluate(x2)) {
            curB = x2;
        } else {
            curA = x1;
        }
        return new Section(curA, curB);
    }

    @Override
    public void restart() {
        curA = a;
        curB = b;
        curIteration = 0;
    }

    private double fib(int n) {
        while (fibonacciNums.size() <= n) {
            int size = fibonacciNums.size();
            fibonacciNums.add(fibonacciNums.get(size - 1) + fibonacciNums.get(size - 2));
        }
        return fibonacciNums.get(n);
    }
    @Override
    protected double getCurrentXMin() {
        return (curA + curB) / 2;
    }
}
