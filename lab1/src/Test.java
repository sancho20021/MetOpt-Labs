import methods.DichotomyMinimizer;
import methods.Function;
import methods.Minimizer;
import methods.ParabolicMinimizer;

import utils.Utility;

public class Test {
    public static void main(String[] args) {
        double a5 = 0.1, b5 = 2.5;
        Function f9 = x -> (Math.exp(3 * x) + 5 * Math.exp(-2 * x));
        Function f5 = x -> (10 * x * Math.log(x) - x * x / 2);
        Minimizer m2 = new ParabolicMinimizer(f5, a5, b5, 0.000001);
        testMinimizer(f5, 0.1, 2.5, m2);

        System.out.println(Utility.dataToTex(Utility.getBasicData(m2)));
    }

    private static void testMinimizer(Function f, double a, double b, Minimizer m) {
        Minimizer dm = new DichotomyMinimizer(f, a, b, 0.000001, 0.000001);
        while (dm.hasNext()) {
            System.out.println(dm.next());
        }
        double min = dm.findMinimum();
        System.out.println("min = (" + min + ", " + f.evaluate(min) + ")");

        System.out.println();

        System.out.println("Testing " + dm.getClass().toString() + " on function" + f.toString());
        while (m.hasNext()) {
            System.out.println(m.next());
        }
        min = m.findMinimum();
        System.out.println("min = (" + min + ", " + f.evaluate(min) + ")");
    }


}
