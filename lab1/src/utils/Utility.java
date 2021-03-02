package utils;

import methods.Minimizer;
import methods.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {
    public static List<List<String>> getBasicData(Minimizer min) {
        List<List<String>> res = new ArrayList();
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
}
