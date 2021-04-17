package methods.multidimensional;

import org.junit.Test;

public class TaskGenerationTest {
    @Test
    public void testThreeDims() {
        for (int i = 1; i < 10; i++) {
            System.out.println("k = " + (double) i);
            System.out.println(MultidimensionalTester.Task.getRandomTask(3, i));
        }
    }
}
