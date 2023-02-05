package name.mjoseland.demo.primes.primes;

import name.mjoseland.demo.primes.util.SimpleUnsafeBitset;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * An implementation of the <a href="https://en.wikipedia.org/wiki/Sieve_of_Atkin#Pseudocode">Sieve of Atkin<a>.
 */
@Component
public final class SieveOfAtkinPrimeFinder implements PrimeFinder {

    // if a sieve bitset is initialised for maxNumber >= 7, this value will be the index representing n=7
    private static final int INDEX_OF_7_IN_SIEVE_BITSET = 1;

    private static final List<Integer> FIRST_THREE_PRIMES = List.of(2, 3, 5);

    private static final boolean[] SET_FOR_STEP_3_1 = new boolean[60];
    private static final boolean[] SET_FOR_STEP_3_2 = new boolean[60];
    private static final boolean[] SET_FOR_STEP_3_3 = new boolean[60];

    // numbers less than 60 that aren't divisible by 2, 3, or 5
    private static final int[] WHEEL_HIT_POSITIONS =
        { 1, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 49, 53, 59 };

    private static final int[] WHEEL_HIT_POSITION_INDICES = new int[60];

    static {
        // initialise arrays used by in steps 3.1, 3.2, and 3.3
        List.of(1, 13, 17, 29, 37, 41, 49, 53).forEach(
            i -> SET_FOR_STEP_3_1[i] = true
        );

        List.of(7, 19, 31, 43).forEach(
            i -> SET_FOR_STEP_3_2[i] = true
        );

        List.of(11, 23, 47, 59).forEach(
            i -> SET_FOR_STEP_3_3[i] = true
        );

        // for all values in WHEEL_HIT_POSITIONS, set value at corresponding index in WHEEL_HIT_POSITION_INDICES to the
        // index of the value in WHEEL_HIT_POSITIONS
        IntStream.range(0, WHEEL_HIT_POSITIONS.length)
            .forEach(i -> WHEEL_HIT_POSITION_INDICES[WHEEL_HIT_POSITIONS[i]] = i);
    }


    @Override
    public List<Integer> getPrimes(final int maxNumber) {
        List<Integer> result = initialiseResult(maxNumber);

        if (maxNumber > 5) {
            addSievedPrimesGtFive(result, maxNumber);
        }

        return result;
    }

    private static void addSievedPrimesGtFive(final List<Integer> result, final int maxNumber) {
        // build a bitset with enough bits to represent the primality of all numbers between 0 and maxNumber (inclusive)
        // that aren't divisible by 2, 3, or 5
        // all values are initialised to 0, indicating that they haven't been found to be prime yet
        SimpleUnsafeBitset sieveBitset = buildSieveBitset(maxNumber);

        toggleForStep3_1(sieveBitset, maxNumber);
        toggleForStep3_2(sieveBitset, maxNumber);
        toggleForStep3_3(sieveBitset, maxNumber);
        eliminateComposites(sieveBitset, maxNumber);

        transferBitsetPrimesToResult(sieveBitset, result);
    }

    /**
     * Built a bitset where entries represent all integers n where:
     *  . 0 <= n <= maxNumber, and
     *  . n % 60 is an element of WHEEL_HIT_POSITIONS
     *
     * @param maxNumber the highest number that should be represented by the bitset
     * @return the new bitset
     */
    private static SimpleUnsafeBitset buildSieveBitset(final int maxNumber) {
        // count of all n <= (maxNumber - (maxNumber % 60))
        int ltEqFactorOf60 = (maxNumber / 60) * WHEEL_HIT_POSITIONS.length;

        // count of all n > (maxNumber - (maxNumber % 60))
        int gtFactorOf60 =
            (int) Arrays.stream(WHEEL_HIT_POSITIONS)
                .filter(position -> position <= (maxNumber % 60))
                .count();

        return new SimpleUnsafeBitset(ltEqFactorOf60 + gtFactorOf60);
    }

    private static List<Integer> initialiseResult(final int maxNumber) {
        if (maxNumber < 2)
            return List.of();
        if (maxNumber == 2)
            // return [ 2 ]
            return FIRST_THREE_PRIMES.subList(0, 1);
        if (maxNumber < 5)
            // maxNumber in [ 3, 4 ]: return [ 2, 3 ]
            return FIRST_THREE_PRIMES.subList(0, 2);

        return new ArrayList<>(FIRST_THREE_PRIMES);
    }

    private static void toggleForStep3_1(final SimpleUnsafeBitset sieveBitset, final int maxNumber) {
        // max possible value of x in the range [ 1, 2, 3, ... ]
        int maxX = (int) Math.sqrt((maxNumber - 1) >> 2);

        for (int x = 1; x <= maxX; x++) {
            // max possible value of y in the range [ 1, 3, 5, ... ]
            int maxY = (int) Math.sqrt(maxNumber - (4 * x * x));

            for (int y = 1; y <= maxY; y += 2) {
                int n = 4 * x * x + y * y;

                // if n % 60 is in SET_FOR_STEP_3_1, then toggle the bit at index n
                if (SET_FOR_STEP_3_1[n % 60]) {
                    sieveBitset.toggle(toSieveBitsetIndex(n));
                }
            }
        }
    }

    private static void toggleForStep3_2(final SimpleUnsafeBitset sieveBitset, final int maxNumber) {
        // max possible value of x in the range [ 1, 3, 5, ... ]
        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        int maxX = (int) Math.sqrt(((maxNumber - 4) / 3));

        for (int x = 1; x <= maxX; x += 2) {
            // max possible value of y in the range [ 2, 4, 6, ... ]
            int maxY = (int) Math.sqrt(maxNumber - (3 * x * x));

            for (int y = 2; y <= maxY; y += 2) {
                int n = 3 * x * x + y * y;

                // if n % 60 is in SET_FOR_STEP_3_2, then toggle the bit at index n
                if (SET_FOR_STEP_3_2[n % 60]) {
                    sieveBitset.toggle(toSieveBitsetIndex(n));
                }
            }
        }
    }

    private static void toggleForStep3_3(final SimpleUnsafeBitset sieveBitset, final int maxNumber) {
        // max possible value of x in the range [ 2, 3, 4, ... ]
        int maxX = (int) (Math.sqrt((double) 2 * maxNumber + 3) / 2 - 0.5);

        for (int x = 2; x <= maxX; x++) {
            int minY = Math.max((int) Math.sqrt((double) 3 * x * x - maxNumber) + 1, 1);

            for (int y = x - 1; y >= minY; y -= 2) {
                int n = 3 * x * x - y * y;

                // if n % 60 is in SET_FOR_STEP_3_3, then toggle the bit at index n
                if (SET_FOR_STEP_3_3[n % 60]) {
                    sieveBitset.toggle(toSieveBitsetIndex(n));
                }
            }
        }
    }

    private static void eliminateComposites(
        final SimpleUnsafeBitset sieveBitset,
        final int maxNumber
    ) {
        final int maxN = (int) Math.sqrt(maxNumber);

        int i = 0;
        int j = 0;

        for (int n = 1; n <= maxN; n = (i * 60) + WHEEL_HIT_POSITIONS[j++]) {
            if (sieveBitset.get(toSieveBitsetIndex(n))) {
                eliminateMultiplesOfSquaredPrime(sieveBitset, maxNumber, n * n);
            }

            if (j >= WHEEL_HIT_POSITIONS.length) {
                i++;
                j = 0;
            }
        }
    }

    private static void eliminateMultiplesOfSquaredPrime(
        final SimpleUnsafeBitset sieveBitset,
        final int maxNumber,
        final int squaredPrime
    ) {
        int i = 0;
        int j = 0;

        for (int n = squaredPrime; n <= maxNumber && n > 0; n = squaredPrime * ((i * 60) + WHEEL_HIT_POSITIONS[j++])) {
            sieveBitset.unset(toSieveBitsetIndex(n));

            if (j >= WHEEL_HIT_POSITIONS.length) {
                i++;
                j = 0;
            }
        }
    }

    private static void transferBitsetPrimesToResult(
        final SimpleUnsafeBitset sieveBitset,
        final List<Integer> result
    ) {
        // iterate through all bits set to 1. if the list is traversed in-order and the composite elimination is
        // performed, then these bits correspond to primes.
        for (
            int i = sieveBitset.nextSetBit(INDEX_OF_7_IN_SIEVE_BITSET);
            i < sieveBitset.size() && i > 0;
            i = sieveBitset.nextSetBit(i + 1)
        ) {
            result.add(toNumberRepresentedByBitsetIndex(i));
        }
    }

    /**
     * Convert integer n to a position in a sieve bitset constructed in getPrimes.
     *
     * @param n any integer
     * @return the index of the bit in bitset
     */
    private static int toSieveBitsetIndex(final int n) {
        return ((n / 60) * WHEEL_HIT_POSITIONS.length) + WHEEL_HIT_POSITION_INDICES[n % 60];
    }

    /**
     * Convert position in a sieve bitset to its corresponding integer.
     *
     * @param i index in a sieve bitset
     * @return the integer corresponding to the index
     */
    private static int toNumberRepresentedByBitsetIndex(final int i) {
        return (i / WHEEL_HIT_POSITIONS.length) * 60 + WHEEL_HIT_POSITIONS[i % WHEEL_HIT_POSITIONS.length];
    }
}
