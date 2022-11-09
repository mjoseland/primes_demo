package name.mjoseland.demo.primes.primes;

import name.mjoseland.demo.primes.util.SimpleUnsafeBitset;
import org.springframework.stereotype.Component;

import java.util.*;

// TODO rename parent package so it isn't primes.primes
/**
 * An implementation of the <a href="https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes">Sieve of Eratosthenes</a>.
 */
@Component
public final class SieveOfEratosthenesPrimeFinder implements PrimeFinder {

    @Override
    public List<Integer> getPrimes(final int maxNumber) {
        List<Integer> result = new ArrayList<>();
        if (maxNumber >= 2)
            result.add(2);

        // if an index representing an integer is true, then at least one factor has been identified
        // index -> integer mapping (2*i+1), examples:
        //  0  ->  1
        //  1  ->  3
        //  2  ->  5
        //  10 -> 21
        int bitSetSize = maxNumber / 2 + maxNumber % 2;
        SimpleUnsafeBitset oddNumbersBitSet = new SimpleUnsafeBitset(bitSetSize);

        int maxNumberSqrt = (int) Math.sqrt(maxNumber);

        // iterate from 1..(bitSetSize - 1) inclusive
        //  (ie. array elements representing 3..maxOddNumber)
        for (int i = 1; i > 0; i = oddNumbersBitSet.nextUnsetBit(i + 1)) {
            int primeNumber = (2 * i) + 1;
            result.add(primeNumber);

            if (primeNumber <= maxNumberSqrt)
                markFactors(primeNumber, maxNumber, oddNumbersBitSet);
        }

        if (result.get(result.size() - 1) > maxNumber)
            result.remove(result.size() - 1);

        return result;
    }

    private static void markFactors(
        final int primeNumber,
        final int maxNumber,
        final SimpleUnsafeBitset oddNumbersBitSet
    ) {
        int doublePrimeNumber = primeNumber * 2;
        for (int i = primeNumber * primeNumber; i <= maxNumber; i += doublePrimeNumber) {
            oddNumbersBitSet.set(i / 2);
        }
    }
}
