import methods.*;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

import static utils.Utility.formatDouble;

public class Task3 {
    static int count;

    public static void main(String[] args) {
        Function fCounting = x -> {
            count++;
            return 10 * x * Math.log(x) - x * x / 2;
        };
        List<List<String>> table = new ArrayList<>();
        double a = 0.1, b = 2.5;
        double eps = 0.5;
        while (eps > 0.0000001) {
            List<String> row = new ArrayList<>(List.of(formatDouble(7, eps)));
            count = 0;
            new DichotomyMinimizer(fCounting, a, b, eps).findMinimum();
            row.add(Integer.toString(count));

            count = 0;
            new GoldenMinimizer(fCounting, a, b, eps).findMinimum();
            row.add(Integer.toString(count));

            count = 0;
            new FibonacciMinimizer(fCounting, a, b, eps).findMinimum();
            row.add(Integer.toString(count));

            count = 0;
            new ParabolicMinimizer(fCounting, a, b, eps).findMinimum();
            row.add(Integer.toString(count));

            count = 0;
            new CombinationMinimizer(fCounting, a, b, eps).findMinimum();
            row.add(Integer.toString(count));
            eps /= 10;
            table.add(row);
        }
        List<String> cols = List.of("$\\epsilon$", "Дихотомия", "З. сечения", "Фибоначчи", "Парабол", "Брента");
        System.out.println(Utility.getTexTable("Зависимость числа вычислений от точности", cols, table));
    }
}
