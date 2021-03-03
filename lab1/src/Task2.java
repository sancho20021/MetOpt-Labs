import methods.*;
import utils.Table;
import utils.Utility;

import java.util.List;
import java.util.function.Function;

public class Task2 {
    public static void main(String[] args) {
        double a5 = 0.1, b5 = 2.5, eps5 = 0.000001;
        Function<Double, Double> f5 = x -> (10 * x * Math.log(x) - x * x / 2);
        Minimizer m2 = new ParabolicMinimizer(f5, a5, b5, eps5);
        printData("Dichotomy", new DichotomyMinimizer(f5, a5, b5, eps5));
        printData("Golden ratio", new GoldenMinimizer(f5, a5, b5, eps5));
        printData("Fibonacci", new FibonacciMinimizer(f5, a5, b5, eps5));
        printData("Parabolic", new ParabolicMinimizer(f5, a5, b5, eps5));
        printData("Brent's combined", new CombinationMinimizer(f5, a5, b5, eps5));
    }

    private static void printData(String metaData, Minimizer m2) {
        Table goalData = Utility.getGoalData(m2);
        System.out.println(metaData);
        System.out.println(new Table(
                metaData,
                List.of("i", "\\Delta_i", "|\\Delta_i|/|\\Delta_{i-1}|", "x_{min}", "f(x_{min})"),
                goalData.table).toTex());
        System.out.println("=".repeat(42));
    }
}
