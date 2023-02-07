package name.mjoseland.demo.primes.find;

import java.util.List;

public interface PrimeFinder {

    /**
     * @param maxNumber the maximum number to check for primality
     * @return an ordered list containing all primes <= maxNumber
     */
    List<Integer> getPrimes(int maxNumber);
}
