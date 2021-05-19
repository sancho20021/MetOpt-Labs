package lab3.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

public class FileTesting {
    private static final Path TEST_ROOT = Path.of("tests");
    private static final Map<String, Path> TEST_FOLDERS = Map.of(Task2.TEST_GROUP, TEST_ROOT.resolve(Task2.TEST_GROUP));

    public static void createTest(final String testName, final String testFolder, final String testData) throws IOException {
        final Path testDir = TEST_FOLDERS.computeIfAbsent(testFolder, TEST_ROOT::resolve).resolve(Path.of(testName));
        final Path testFile = testDir.resolve(Path.of(testName + ".txt"));
        Files.createDirectories(testDir);
        final PrintWriter writer = new PrintWriter(new FileWriter(testFile.toFile()));
        writer.print(testData);
        writer.close();
    }

    public static void createTest(final String testName, final String testData) throws IOException {
        createTest(testName, "", testData);
    }

    public static void deleteTests() {
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
        createTest("TEST NA PIDORA", "Ты пидор?");
        createTest("Тест на труса", "Ты трус?");
//        deleteTests();
    }
}
