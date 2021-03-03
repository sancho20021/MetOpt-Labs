package utils;

import methods.Minimizer;
import methods.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static final int PRECISION = 6;

    public static Table getGoalData(Minimizer min) {
        List<List<String>> res = new ArrayList<>();
        min.restart();
        double prev = 1;
        while (min.hasNext()) {
            Section s = min.next();
            res.add(List.of(
                    String.valueOf(res.size()),
                    s.toString(PRECISION),
                    formatDouble(PRECISION, (s.getB() - s.getA()) / prev),
                    formatDouble(PRECISION, min.getCurrentXMin()),
                    formatDouble(PRECISION, min.getFun().evaluate(min.getCurrentXMin()))
            ));
            prev = s.getB() - s.getA();
        }
        return new Table("Table with required values",
                List.of("i", "i-th segment", "d_i/d_{i-1}", "x_{min}", "f(x_{min})"),
                res);
    }

    public static String formatDouble(int precision, double x) {
        return String.format(Locale.US, "%." + precision + "f", x);
    }

    public static Table getMinimizerTestData(String minName, Minimizer min, int precision) {
        Table res = new Table(minName, List.of("интервалы", "длина инт.", "точки", "знач. ф-ции"), new ArrayList<>());
        min.restart();
        Section s = new Section(min.getA(), min.getB());
        do {
            res.table.add(List.of(
                    "{" + s.toString(precision) + "}",
                    formatDouble(precision, s.getB() - s.getA()),
                    formatDouble(precision, min.getCurrentXMin()),
                    formatDouble(precision, min.getFun().evaluate(min.getCurrentXMin()))
            ));
            s = min.next();
        } while (s != null);
        return res;
    }
}
