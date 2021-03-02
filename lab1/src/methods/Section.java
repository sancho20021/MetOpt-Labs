package methods;

import utils.Utility;

public class Section {
    private final double a, b;

    public Section(final double a, final double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public String toString() {
        return toString(6);
    }

    public String toString(int precision) {
        return "[" + Utility.formatDouble(precision, a) + ", " + Utility.formatDouble(precision, b) + "]";
    }
}
