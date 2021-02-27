import methods.*;

public class Test {
    public static void main(String[] args) {
        Function f9 = x -> (Math.exp(3 * x) + 5 * Math.exp(-2 * x));
        Minimizer dm = new DichotomyMinimizer(f9, 0, 1, 0.000001, 0.000001);
        while (dm.hasNext()) {
            System.out.println(dm.next());
        }
        double min = dm.findMinimum();
        System.out.println("min = (" + min + ", " + f9.evaluate(min) + ")");

        System.out.println();

        Minimizer m2 = new ParabolicMinimizer(f9, 0, 1, 0.000001);
        while (m2.hasNext()) {
            System.out.println(m2.next());
        }
        min = m2.findMinimum();
        System.out.println("min = (" + min + ", " + f9.evaluate(min) + ")");
    }
}
