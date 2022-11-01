package name.mjoseland.demo.virtualthreads.primes;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasicPrimeFinder implements PrimeFinder {

    // TODO review/fix overflows
    public List<Integer> getPrimes(int maxNumber) {
        List<Integer> result = new ArrayList<>();

        if (maxNumber >= 2)
            result.add(2);
        if (maxNumber >= 3)
            result.add(3);
        if (maxNumber >= 5)
            result.add(5);
        if (maxNumber >= 7)
            result.add(7);

        int oneLower;
        int oneHigher;

        for (int i = 12; i <= maxNumber; i += 6) {
            oneLower = i - 1;
            oneHigher = i + 1;

            if (isPrime(oneLower, result)) {
                result.add(oneLower);
            }
            if (oneHigher <= maxNumber && isPrime(oneHigher, result)) {
                result.add(oneHigher);
            }
        }

        return result;
    }

    private static boolean isPrime(int number, List<Integer> knownPrimes) {
        int maxFactorToCheck = ((int) Math.sqrt(number)) + 1;

        for (int i = 2; knownPrimes.get(i) <= maxFactorToCheck; i++) {
            if (number % knownPrimes.get(i) == 0) {
                return false;
            }
        }

        return true;
    }
}
