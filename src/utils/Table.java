package utils;

import java.util.List;

public class Table {
    public String name;
    public List<String> cols;
    public List<List<String>> table;

    public Table(final String name, final List<String> cols, final List<List<String>> table) {
        this.name = name;
        this.cols = cols;
        this.table = table;
    }

    public String toTex() {
        final String br = System.lineSeparator();
        final StringBuilder s = new StringBuilder();
        s.append("\\begin{centering}").append(br);
        s.append("\\begin{tabular}{||").append("c ".repeat(cols.size())).append("||}").append(br);
        s.append("\\hline").append(br);

        s.append("\\multicolumn{").append(cols.size()).append("}{||c||}{").append(name).append("}");
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

    public String toCSV() {
        // comma-separated values
        final StringBuilder res = new StringBuilder();
        for (final List<String> strings : table) {
            res.append(String.join(",", strings));
            res.append(System.lineSeparator());
        }
        return res.toString();
    }
}
