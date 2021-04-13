import methods.*;
import utils.Table;
import utils.Utility;

import java.util.function.Function;

public class Task2 {
    public static void main(String[] args) {
        double a5 = 0.1, b5 = 2.5, eps5 = 0.001;
        Function<Double, Double> f5 = x -> (10 * x * Math.log(x) - x * x / 2);
        printData("Dichotomy", new DichotomyMinimizer(f5, a5, b5, eps5));
        printData("Golden ratio", new GoldenMinimizer(f5, a5, b5, eps5));
        printData("Fibonacci", new FibonacciMinimizer(f5, a5, b5, eps5));
        printData("Parabolic", new ParabolicMinimizer(f5, a5, b5, eps5));
        printData("Brent's combined", new CombinationMinimizer(f5, a5, b5, eps5));
    }

    private static void printData(String methodName, Minimizer m2) {
        Table goalData = Utility.getGoalData(methodName, m2);
        System.out.print(goalData.toTex());
        System.out.println("\\pagebreak");
    }
}
