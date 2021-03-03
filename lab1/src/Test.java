import methods.Minimizer;
import methods.ParabolicMinimizer;
import utils.Utility;

import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        double a5 = 0.1, b5 = 2.5, eps5 = 0.000001;
        Function<Double, Double> f5 = x -> (10 * x * Math.log(x) - x * x / 2);
        Minimizer m2 = new ParabolicMinimizer(f5, a5, b5, eps5);

        System.out.println(
                Utility.getGoalData("Parabolic minimizer", m2).toTex());
    }

}
