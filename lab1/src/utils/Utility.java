package utils;

import methods.Minimizer;
import methods.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {
    public static List<List<String>> getBasicData(Minimizer min) {
        List<List<String>> res = new ArrayList<>();
        res.add(List.of("intervals", "lengths", "xs", "fs"));
        min.restart();
        while (min.hasNext()) {
            Section s = min.next();
            res.add(List.of(
                    s.toString(6),
                    formatDouble(6, s.getB() - s.getA()),
                    formatDouble(6, min.getCurrentXMin()),
                    formatDouble(6, min.getFun().evaluate(min.getCurrentXMin()))
            ));
        }
        return res;
    }

    // converts any valid data to tex table format
    public static String dataToTex(List<List<String>> data) {
        // header
        StringBuilder res = new StringBuilder();
        // contents
        for (int i = 0; i < data.size(); i++) {
            res.append("\\hline" + System.lineSeparator());
            res.append(String.join(" & ", data.get(i)));
            res.append("\\\\" + System.lineSeparator());
        }

        res.append("\\hline" + System.lineSeparator());

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
}
