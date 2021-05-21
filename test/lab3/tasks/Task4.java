package lab3.tasks;

import lab3.methods.Gauss;
import lab3.models.FullMatrix;
import lab3.models.LinearSystem;
import lab3.models.MutableSquareMatrix;
import lab3.models.Vector;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static lab3.tasks.Task2.parseLS;

public class Task4 {
    public static final String TEST_FOLDER = "Task4";

    @Test
    public void solveAllProblems() {
        System.out.println("Testing Gauss matrices");
        FileTesting.solveTests(TEST_FOLDER, new ProfileFormatGaussSolver());
        System.out.println("OK!");
    }

    static class ProfileFormatGaussSolver implements FileTesting.Solver {
        @Override
        public void solve(File problem, File solution) {
            try (final PrintWriter writer = new PrintWriter(new FileWriter(solution))) {
                final Scanner in = new Scanner(problem);
                final LinearSystem ls = parseLS(in);
                final double eps = 0.0000001;
                final MutableSquareMatrix denseMatrix = new FullMatrix(ls.getA());
                final Vector ans = new Vector(Gauss.solveOptimized(denseMatrix, ls.getB().getElementsArrayCopy(), eps).orElseThrow());
                writer.println("Expected: " + ls.getCorrectAnswer().toRawString());
                writer.println("Got: " + ans.toRawString());
                writer.println("Error: " + ls.getEuclideanError(ans));
            } catch (final IOException e) {
                System.err.println("Error while I/O with " + problem + ", " + solution);
            }
        }
    }

    @Test
    public void printAkStatistics() {
        Task2.printAkStatistics("Метод Гаусса", Gauss::solveOptimized, FullMatrix::new);
    }

    @Test
    public void printHilbertStatistics() {
        Task3.printHilbertStatistics("Метод Гаусса", Gauss::solveOptimized, FullMatrix::new);
    }
}
