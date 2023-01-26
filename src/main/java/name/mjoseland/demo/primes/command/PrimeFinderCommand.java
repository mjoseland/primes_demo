package name.mjoseland.demo.primes.command;

import name.mjoseland.demo.primes.primes.PrimeFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class PrimeFinderCommand {

    private final PrimeFinder basicPrimeFinder;

    PrimeFinderCommand(@Autowired @Qualifier("sieveOfEratosthenesPrimeFinder") PrimeFinder basicPrimeFinder) {
        this.basicPrimeFinder = basicPrimeFinder;
    }

    @ShellMethod("Returns count and ten highest primes <= a number")
    public String primes(final int number) {
        if (number <= 0) {
            return "Please enter a positive integer";
        }

        long start = System.currentTimeMillis();

        List<Integer> primesList = basicPrimeFinder.getPrimes(number);

        long runTime = System.currentTimeMillis() - start;

        String primesStringList =
                primesList.subList(Math.max(0, primesList.size() - 10), primesList.size())
                        .stream()
                        .map(prime -> Integer.toString(prime))
                        .collect(Collectors.joining(", "));

        return "highest ten primes: " + primesStringList +
                "\ntotal primes: " + primesList.size() +
                "\nruntime: " + runTime + "ms";
    }
}
