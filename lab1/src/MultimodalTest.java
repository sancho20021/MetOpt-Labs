import methods.*;
import utils.Utility;

public class MultimodalTest {
    private static void printTestData(String minName, Minimizer min) {
        System.out.println(Utility.getGoalData(minName, min).toTex());
    }

    public static void main(String[] args) {
        Function multimodal = x -> Math.pow(x, 5) - Math.pow(x, 3) + x * x;
        double a = -2, b = 2, eps = 0.01;
        printTestData("Дихотомия", new DichotomyMinimizer(multimodal, a, b, eps));
        printTestData("Золотое сечение", new GoldenMinimizer(multimodal, a, b, eps));
        printTestData("Фибоначчи", new FibonacciMinimizer(multimodal, a, b, eps));
    }
}
