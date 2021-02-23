package methods;

public class Section {
    public double a, b;

    public Section(final double a, final double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "[" + a + ", " + b + "]";
    }
}
