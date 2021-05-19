package lab3.tasks;

import lab3.models.LinearSystem;
import lab3.utils.generators.LSGenerators;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class Task3 {
    public static final String TEST_FOLDER = "Task3";

    private static LinearSystem generateHilbertSystem(final int n) {
        return LSGenerators.getProfilFormatLS(MatrixGenerators.generateHilbertMatrix(n));
    }

    @Test
    public void createTests() {
        final Map<Integer, String> testGroups = Map.of(10, "n=10", 100, "n=100", 1000, "n=1000");
        testGroups.forEach((n, name) -> {
            final LinearSystem system = generateHilbertSystem(n);
            final String testData = Task2.getLinearSystemProfileString(system);
            try {
                FileTesting.writeFile(name, TEST_FOLDER, testData);
            } catch (final IOException e) {
                System.err.println("Couldn't create test: ");
                System.err.println(testData);
            }
        });
    }

    @Test
    public void solveAllProblems() {
        System.out.println("Testing Hilbert matrices");
        FileTesting.solveTests(TEST_FOLDER, new Task2.ProfileFormatLUSolver());
        System.out.println("OK!");
    }
}
