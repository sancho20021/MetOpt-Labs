package ru.irmo.metopt.metopt.domain.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FibonacciMinimizer extends Minimizer {
    private static final List<Double> fibonacciNums = new ArrayList<>(List.of(0.0, 1.0, 1.0));
    private double curA, curB;
    private double f1, f2;
    private double x1, x2;
    private boolean isX1Set, isX2Set;
    private final int n;
    private int curIteration;

    public FibonacciMinimizer(Function<Double, Double> fun, double a, double b, double eps) {
        super(fun, a, b, eps);
        int t = 2;
        while (fib(t) <= (b - a) / eps) {
            t++;
        }
        n = t;
        restart();
    }

    @Override
    public boolean hasNext() {
        return curIteration < n - 2;
    }

    @Override
    protected Section nextIteration() {
        x1 = isX1Set ? x1 : curA + fib(n - curIteration - 2) / fib(n) * (b - a);
        x2 = isX2Set ? x2 : curA + fib(n - curIteration - 1) / fib(n) * (b - a);
        f1 = isX1Set ? f1 : fun.apply(x1);
        f2 = isX2Set ? f2 : fun.apply(x2);

        curIteration++;
        if (f1 <= f2) {
            curB = x2;
            x2 = x1;
            f2 = f1;
            isX2Set = true;
            isX1Set = false;
        } else {
            curA = x1;
            x1 = x2;
            f1 = f2;
            isX1Set = true;
            isX2Set = false;
        }
        return new Section(curA, curB);
    }

    @Override
    public void restart() {
        curA = a;
        curB = b;
        curIteration = 0;
        f1 = f2 = 0;
        x1 = x2 = 0;
        isX1Set = isX2Set = false;
    }

    private double fib(int n) {
        while (fibonacciNums.size() <= n) {
            int size = fibonacciNums.size();
            fibonacciNums.add(fibonacciNums.get(size - 1) + fibonacciNums.get(size - 2));
        }
        return fibonacciNums.get(n);
    }

    @Override
    public double getCurrentXMin() {
        return (curA + curB) / 2;
    }
}
