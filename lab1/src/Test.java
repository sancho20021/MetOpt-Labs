import methods.DichotomyMinimizer;
import methods.Function;
import methods.Minimizer;

public class Test {
    public static void main(String[] args) {
        Function f9 = x -> (Math.exp(3 * x) + 5 * Math.exp(-2 * x));
        Minimizer dm = new DichotomyMinimizer(f9, 0, 1, 0.00001, 0.00001);
        while (dm.hasNext()) {
            System.out.println(dm.next());
        }
        double min = dm.findMinimum();
        System.out.println("min = (" + min + ", " + f9.evaluate(min) + ")");
    }
}
