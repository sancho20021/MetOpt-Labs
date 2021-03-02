import methods.*;
import utils.Utility;

import java.io.IOException;

public class MultimodalTest {
    private static void printTestData(String minName, Minimizer min, int precision) {
        System.out.println(Utility.getTexTable(Utility.getMinimizerTestData(minName, min, precision)));
    }

    public static void main(String[] args) {
        Function multimodal = x -> Math.pow(x, 5) - Math.pow(x, 3) + x * x;
        double a = -2, b = 2, eps = 0.01;
        int precision = 3;
        printTestData("Дихотомия", new DichotomyMinimizer(multimodal, a, b, eps), precision);
        printTestData("Золотое сечение", new GoldenMinimizer(multimodal, a, b, eps), precision);
        printTestData("Фибоначчи", new FibonacciMinimizer(multimodal, a, b, eps), precision);
    }
}
