package lab3.tasks;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FileTesting {
    private static final Path TEST_ROOT = Path.of("tests");
    private static final Map<String, Path> TEST_FOLDERS = new HashMap<>(Map.of(
            Task2.TEST_FOLDER, TEST_ROOT.resolve(Task2.TEST_FOLDER),
            Task3.TEST_FOLDER, TEST_ROOT.resolve(Task3.TEST_FOLDER),
            Task4.TEST_FOLDER, TEST_ROOT.resolve(Task4.TEST_FOLDER)
    ));

    public static void writeFile(final String testName, final String testFolder, final String testData) throws IOException {
        final Path testDir = TEST_FOLDERS.computeIfAbsent(testFolder, TEST_ROOT::resolve).resolve(Path.of(testName));
        final Path testFile = testDir.resolve(Path.of(testName + ".txt"));
        Files.createDirectories(testDir);
        final PrintWriter writer = new PrintWriter(new FileWriter(testFile.toFile()));
        writer.print(testData);
        writer.close();
    }

    public static void writeFile(final String testName, final String testData) throws IOException {
        writeFile(testName, "", testData);
    }

    private static File getSolutionFile(final File test) {
        final String name = test.getName();
        return test.getParentFile().toPath().resolve(Path.of("ANS_" + name)).toFile();
    }

    public static void solveTests(final String testFolder, final Solver solver) {
        final Path testDir = TEST_FOLDERS.get(testFolder);
        if (testDir == null) {
            System.err.println("TestFolder " + testFolder + " not found");
            return;
        }
        try {
            Files.createDirectories(testDir);
            Files.walk(testDir, 3)
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .forEach(test -> solver.solve(test, getSolutionFile(test)));
        } catch (final IOException e) {
            System.err.println("Error while walking the tests folders");
        }
    }

    @Test
    public void deleteSolutions() {
        try {
            Files.walk(TEST_ROOT)
                    .sorted()
                    .map(Path::toFile)
                    .filter(file -> file.isFile() && file.getName().startsWith("ANS_"))
                    .forEach(File::delete);
        } catch (final IOException e) {
            System.err.println("Error while deleting solutions");
        }
    }

    @Test
    public void deleteTests() {
        try {
            Files.walk(TEST_ROOT)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (final IOException e) {
            System.err.println("Couldn't delete directories");
        }
    }

    public static void main(String[] args) throws IOException {
//        deleteTests();
    }

    interface Solver {
        void solve(final File problem, final File solution);
    }
}
