package utils;

import methods.Minimizer;
import methods.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static final int PRECISION = 6;

    public static List<List<String>> getBasicData(Minimizer min) {
        return getBasicData(min, 6);
    }

    public static List<List<String>> getBasicData(Minimizer min, int precision) {
        List<List<String>> res = new ArrayList<>();
        min.restart();
        while (min.hasNext()) {
            Section s = min.next();
            res.add(List.of(
                    s.toString(precision),
                    formatDouble(precision, s.getB() - s.getA()),
                    formatDouble(precision, min.getCurrentXMin()),
                    formatDouble(precision, min.getFun().evaluate(min.getCurrentXMin()))
            ));
        }
        return res;
    }

    public static List<List<String>> getGoalData(Minimizer min) {
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
        return res;
    }

    // tex table entries format
    public static String dataToTex(List<List<String>> data) {
        StringBuilder res = new StringBuilder();
        // contents
        for (int i = 0; i < data.size(); i++) {
            res.append("\\hline").append(System.lineSeparator());
            res.append(String.join(" & ", data.get(i)));
            res.append("\\\\").append(System.lineSeparator());
        }

        res.append("\\hline").append(System.lineSeparator());

        return res.toString();
    }

    // comma-separated values
    public static String dataToCSV(List<List<String>> data) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            res.append(String.join(",", data.get(i)));
            res.append(System.lineSeparator());
        }

        return res.toString();
    }

    public static String formatDouble(int precision, double x) {
        return String.format(Locale.US, "%." + precision + "f", x);
    }

    public static String getTexTable(final String tableName, final List<String> cols, final List<List<String>> table) {
        String br = System.lineSeparator();
        StringBuilder s = new StringBuilder();
        s.append("\\begin{centering}").append(br);
        s.append("\\begin{tabular}{||").append("c ".repeat(cols.size())).append("||}").append(br);
        s.append("\\hline").append(br);

        s.append("\\multicolumn{").append(cols.size()).append("}{||c||}{").append(tableName).append("}");
        s.append("\\\\").append(br);
        s.append("\\hline\\hline").append(br);

        s.append(String.join(" & ", cols)).append("\\\\").append(br);
        s.append("\\hline").append(br);

        for (final List<String> row : table) {
            s.append(String.join(" & ", row)).append("\\\\").append(br);
        }

        s.append("\\hline").append(br).append("\\end{tabular}").append(br);

        s.append("\\end{centering}").append(br);
        return s.toString();
    }

    public static String getTexTable(Table table) {
        return getTexTable(table.name, table.cols, table.table);
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
