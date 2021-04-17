package methods.multidimensional;

import methods.unidimensional.DichotomyMinimizer;
import methods.unidimensional.FibonacciMinimizer;
import methods.unidimensional.GoldenMinimizer;
import methods.unidimensional.Minimizer;
import utils.Utility;

import java.util.function.Function;

public class MultimodalTest {
    private static void printTestData(String minName, Minimizer min) {
        System.out.println(Utility.getGoalData(minName, min).toTex());
    }

    public static void main(String[] args) {
        Function<Double, Double> multimodal = x -> Math.pow(x, 5) - Math.pow(x, 3) + x * x;
        double a = -2, b = 2, eps = 0.000001;
        printTestData("Дихотомия", new DichotomyMinimizer(multimodal, a, b, eps));
        printTestData("Золотое сечение", new GoldenMinimizer(multimodal, a, b, eps));
        printTestData("Фибоначчи", new FibonacciMinimizer(multimodal, a, b, eps));
    }
}
