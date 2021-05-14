package lab3;

import lab3.models.ProfileFormatMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Yaroslav Ilin
 * So I am writing this code, so I am writing input rules.
 */
public class Utils {
    static ProfileFormatMatrix readMatrix(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        if (!in.hasNextInt()) {
            throw new IllegalArgumentException("expected dimension");
        }
        int n = in.nextInt();
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!in.hasNextInt()) {
                    throw new IllegalArgumentException("expected matrix values on position (" + i + " " + j + ")");
                }
                a[i][j] = in.nextInt();
            }
        }
        return new ProfileFormatMatrix(n, a);
    }
}
