package name.mjoseland.demo.primes.primes;

import java.util.List;
import java.util.function.IntSupplier;

class AtkinWheelIntSupplier implements IntSupplier {

    // numbers less than 60 that aren't divisible by 2, 3, or 5
    private static final List<Integer> WHEEL_HIT_POSITIONS =
        List.of(1, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 49, 53, 59);

    private int i = 0;
    private int j = 0;

    @Override
    public int getAsInt() {
        if (j >= WHEEL_HIT_POSITIONS.size()) {
            i++;
            j = 0;
        }

        return (i * 60) + WHEEL_HIT_POSITIONS.get(j++);
    }
}
