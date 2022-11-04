package name.mjoseland.demo.primes.util;

/**
 * A fixed-size bitset that:
 *  . only implements methods required by this project
 *  . doesn't always perform bounds checks
 */
public class SimpleUnsafeBitset {

    private static final int RIGHT_SHIFT_FOR_DIVIDE_BY_BITS_PER_LONG = 6;
    private static final int BITS_PER_LONG = 64;

    private final long highestInteger;
    private final long[] longArray;

    public SimpleUnsafeBitset(int highestInteger) {
        int intArraySize = highestInteger / BITS_PER_LONG + 1;

        this.highestInteger = highestInteger;
        this.longArray = new long[intArraySize];
    }

    /**
     * Sets a bit at a single index to 1.
     *
     * @param index the index of the bit to set to 1
     */
    public void set(int index) {
        longArray[index >> RIGHT_SHIFT_FOR_DIVIDE_BY_BITS_PER_LONG] |= 1L << index;
    }

    /**
     * Finds the index of the next 0 bit that is >= the provided start index.
     *
     * @param startIndex the start index
     * @return the index of the next bit set to 0 or -1 if out-of-bounds
     */
    public int nextUnsetBit(int startIndex) {
        int jStartIndex = startIndex % BITS_PER_LONG;

        for (int i = startIndex / BITS_PER_LONG; i < longArray.length; i++) {
            for (int j = jStartIndex; j < BITS_PER_LONG; j++) {
                if ((longArray[i] & (1L << j)) == 0) {
                    int nextUnsetValue = i * BITS_PER_LONG + j;

                    if (nextUnsetValue > highestInteger)
                        nextUnsetValue = -1;

                    return nextUnsetValue;
                }
            }
            jStartIndex = 0;
        }

        return -1;
    }
}
