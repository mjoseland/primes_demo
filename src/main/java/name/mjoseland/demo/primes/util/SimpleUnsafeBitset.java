package name.mjoseland.demo.primes.util;

/**
 * A fixed-size bitset that:
 *  . only implements methods required by this project
 *  . doesn't always perform bounds checks
 */
public class SimpleUnsafeBitset {

    private static final int RIGHT_SHIFT_FOR_LONG_INDEX = 6;
    private static final int BITS_PER_LONG = 64;

    // Maximum number of bits that can be stored. This number of bits will be stored in a long array with 2^25
    // (33,554,432) elements. a larger max size of (2^31 - 1) * 64 is theoretically possible, but it would require
    // long -> int casting to access the array and require approx. 17.2 GB of memory.
    public static final int MAX_SIZE = Integer.MAX_VALUE;

    private final int size;
    private final long[] longArray;

    public SimpleUnsafeBitset(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be between 1 and " + MAX_SIZE + " (inclusive)");
        }

        int longArraySize;
        if (size % 64 == 0)
            longArraySize = size >> RIGHT_SHIFT_FOR_LONG_INDEX;
        else
            longArraySize = (size >> RIGHT_SHIFT_FOR_LONG_INDEX) + 1;

        this.size = size;
        this.longArray = new long[longArraySize];
    }

    public int size() {
        return size;
    }

    public boolean get(final int index) {
        return (longArray[index >> RIGHT_SHIFT_FOR_LONG_INDEX] & (1L << index)) != 0;
    }

    /**
     * Sets a bit at a single index to 1.
     *
     * @param index the index of the bit to set to 1
     */
    public void set(final int index) {
        longArray[index >> RIGHT_SHIFT_FOR_LONG_INDEX] |= 1L << index;
    }

    /**
     * Sets a bit at a single index to 0.
     *
     * @param index the index of the bit to set to 1
     */
    public void unset(final int index) {
        longArray[index >> RIGHT_SHIFT_FOR_LONG_INDEX] &= ~(1L << index);
    }

    /**
     * Toggles a bit at a single index.
     *
     * @param index the index of the bit to set to 1
     */
    public void toggle(final int index) {
        longArray[index >> RIGHT_SHIFT_FOR_LONG_INDEX] ^= 1L << index;
    }

    /**
     * Finds the index of the next 1 bit that is >= the provided start index.
     *
     * @param startIndex the start index
     * @return the index of the next bit set to 1 or -1 if out-of-bounds
     */
    public int nextSetBit(final int startIndex) {
        if (startIndex >= this.size)
            return -1;

        int i = startIndex >> RIGHT_SHIFT_FOR_LONG_INDEX;

        long firstBitToCheckInFirstLong = 1L << (startIndex % BITS_PER_LONG);

        long longWithUnnecessaryBitsCleared = longArray[i] & (firstBitToCheckInFirstLong * -1);

        if (longWithUnnecessaryBitsCleared != 0) {
            return i * BITS_PER_LONG + Long.numberOfTrailingZeros(longWithUnnecessaryBitsCleared);
        }

        for (i++; i < longArray.length; i++) {
            if (longArray[i] != 0) {
                return i * BITS_PER_LONG + Long.numberOfTrailingZeros(longArray[i]);
            }
        }

        return -1;
    }

    /**
     * Finds the index of the next 0 bit that is >= the provided start index.
     *
     * @param startIndex the start index
     * @return the index of the next bit set to 0 or -1 if out-of-bounds
     */
    public int nextUnsetBit(final int startIndex) {
        if (startIndex >= this.size)
            return -1;

        int i = startIndex >> RIGHT_SHIFT_FOR_LONG_INDEX;

        long firstBitToCheckInFirstLong = 1L << (startIndex % BITS_PER_LONG);

        long longWithUnnecessaryBitsSetTo1 = longArray[i] | (firstBitToCheckInFirstLong - 1);

        if (longWithUnnecessaryBitsSetTo1 != -1) {
            int result = i * BITS_PER_LONG + Long.numberOfTrailingZeros(~longWithUnnecessaryBitsSetTo1);

            if (result >= this.size)
                return -1;

            return result;
        }

        for (i++; i < longArray.length; i++) {
            if (longArray[i] != -1) {
                int result = i * BITS_PER_LONG + Long.numberOfTrailingZeros(~longArray[i]);

                if (result >= this.size)
                    return -1;

                return result;
            }
        }

        return -1;
    }
}
