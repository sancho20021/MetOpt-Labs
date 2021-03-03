package utils;

import methods.Minimizer;
import methods.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static final int PRECISION = 6;

    public static Table getGoalData(String minName, Minimizer min) {
        Table res = new Table(
                minName,
                List.of("i", "$\\Delta_i$", "$|\\Delta_i|/|\\Delta_{i-1}|$", "$x_{\\min}$", "$f(x_{\\min})$"),
                new ArrayList<>());
        min.restart();
        Section s = new Section(min.getA(), min.getB());
        Section prev = new Section(min.getA(), min.getB());
        do {
            res.table.add(List.of(
                    String.valueOf(res.table.size()),
                    "{" + s.toString(PRECISION) + "}",
                    formatDouble(PRECISION, (s.getB() - s.getA()) / (prev.getB() - prev.getA())),
                    formatDouble(PRECISION, min.getCurrentXMin()),
                    formatDouble(PRECISION, min.getFun().apply(min.getCurrentXMin()))
            ));
            prev = s;
            s = min.next();
        } while (s != null);
        return res;
    }

    public static String formatDouble(int precision, double x) {
        return String.format(Locale.US, "%." + precision + "f", x);
    }
}
