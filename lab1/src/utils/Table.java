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
}
