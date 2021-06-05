package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.tasks.Task2;
import org.junit.Test;

public class MarquardtTest {
    @Test
    public void test() {
        final MinimizationTester tester = new MinimizationTester((f, x0, eps) -> new Marquardt(f, x0, 4, 3, eps));
        tester.test(Task2.f1);
    }

}