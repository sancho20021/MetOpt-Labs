package utils;

import java.util.List;

public class Table {
    public String name;
    public List<String> cols;
    public List<List<String>> table;

    public Table(String name, List<String> cols, List<List<String>> table) {
        this.name = name;
        this.cols = cols;
        this.table = table;
    }

    public String toTex() {
        String br = System.lineSeparator();
        StringBuilder s = new StringBuilder();
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

    /**
     * @return the table in csv format
    * */
    public String toCSV() {
        return toDSV(",");
    }

    /**
     * @return the table as whitespace-separated values
    * */
    public String toTSV() {
        return toDSV(" ");
    }

    public String toDSV(String delimeter) {
        // delimiter-separated values
        StringBuilder res = new StringBuilder();
        for (List<String> strings : table) {
            res.append(String.join(delimeter, strings));
            res.append(System.lineSeparator());
        }
        return res.toString();
    }
}
