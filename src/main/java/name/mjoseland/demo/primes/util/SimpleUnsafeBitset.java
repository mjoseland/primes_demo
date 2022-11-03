package name.mjoseland.demo.primes.util;

/**
 * A fixed-size bitset that:
 *  . only implements methods required by this project
 *  . doesn't perform bounds checks
 */
public class SimpleUnsafeBitset {

    private static final int ADDRESS_BITS_PER_WORD = 5;

    private final int highestInteger;
    private final int[] intArray;

    public SimpleUnsafeBitset(int highestInteger) {
        int intArraySize = highestInteger / 32 + 1;

        this.highestInteger = highestInteger;
        this.intArray = new int[intArraySize];
    }

    public void set(int number) {
        intArray[number >> ADDRESS_BITS_PER_WORD] |= 1 << number;
    }

    public int nextUnsetBit(int fromValue) {
        int jStartValue = fromValue % 32;

        for (int i = fromValue / 32; i < intArray.length; i++) {
            for (int j = jStartValue; j < 32; j++) {
                if ((intArray[i] & (1 << j)) == 0) {
                    int nextSetBit = i * 32 + j;
                    if (nextSetBit > this.highestInteger) {
                        return -1;
                    } else {
                        return nextSetBit;
                    }
                }
            }
            jStartValue = 0;
        }

        return -1;
    }
}