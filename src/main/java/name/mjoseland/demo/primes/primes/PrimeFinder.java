package name.mjoseland.demo.primes.primes;

import java.util.List;

public interface PrimeFinder {

    // TODO change to Stream?
    List<Integer> getPrimes(int maxNumber);
}
