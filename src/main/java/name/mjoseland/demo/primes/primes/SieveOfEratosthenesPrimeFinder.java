package name.mjoseland.demo.primes.primes;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <a href="https://web.archive.org/web/20140724050940/http://fpx.de/fp/Software/Sieve.html">...</a>
 */
@Component
public class SieveOfEratosthenesPrimeFinder implements PrimeFinder {

    @Override
    public List<Integer> getPrimes(int maxNumber) {
        List<Integer> result = new ArrayList<>();
        if (maxNumber >= 2)
            result.add(2);

        // if an index representing an integer is true, then it has known factors
        // index -> integer mapping (2*i+1), examples:
        //  0  ->  1
        //  1  ->  3
        //  2  ->  5
        //  10 -> 21
        int bitSetSize = maxNumber / 2 + maxNumber % 2;
        BitSet oddNumbersBitSet = new BitSet(bitSetSize);

        int maxNumberSqrt = (int) Math.sqrt(maxNumber);

        // iterate from 1..(bitSetSize - 1) inclusive
        //  (ie. array elements representing 3..maxOddNumber)
        for (int i = 1; i > 0 && i < bitSetSize; i = oddNumbersBitSet.nextClearBit(i + 1)) {
            int primeNumber = (2 * i) + 1;
            result.add(primeNumber);

            if (primeNumber <= maxNumberSqrt)
                markFactors(primeNumber, maxNumber, oddNumbersBitSet);
        }

        return result;
    }

    private static void markFactors(int primeNumber, int maxNumber, BitSet oddNumbersBitSet) {
        int doublePrimeNumber = primeNumber * 2;
        for (int i = primeNumber * primeNumber; i > 0 && i <= maxNumber; i += doublePrimeNumber) {
            oddNumbersBitSet.set(i / 2);
        }
    }
}
