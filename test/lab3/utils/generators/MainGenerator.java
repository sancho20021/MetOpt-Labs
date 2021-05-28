package lab3.utils.generators;

import java.util.Random;

public class MainGenerator {
    public static final Random RANDOM = new Random(229);

    public static int getInt(final int lo, final int hi, final double prOfNonZero) {
        double x = RANDOM.nextDouble();
        return x <= prOfNonZero ? lo + RANDOM.nextInt(hi - lo) : 0;
    }
}
