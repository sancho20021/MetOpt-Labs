import methods.*;
import utils.Utility;

import java.util.function.Function;

public class MultimodalTest {
    private static void printTestData(String minName, Minimizer min, int precision) {
        System.out.println(Utility.getMinimizerTestData(minName, min, precision).toTex());
    }

    public static void main(String[] args) {
        Function<Double, Double> multimodal = x -> Math.pow(x, 5) - Math.pow(x, 3) + x * x;
        double a = -2, b = 2, eps = 0.01;
        int precision = 3;
        printTestData("Дихотомия", new DichotomyMinimizer(multimodal, a, b, eps), precision);
        printTestData("Золотое сечение", new GoldenMinimizer(multimodal, a, b, eps), precision);
        printTestData("Фибоначчи", new FibonacciMinimizer(multimodal, a, b, eps), precision);
    }
}
