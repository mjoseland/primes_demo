package name.mjoseland.demo.primes.command;

import name.mjoseland.demo.primes.find.PrimeFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class PrimeFinderCommand {

    private final PrimeFinder sieveOfAtkinPrimeFinder;
    private final PrimeFinder sieveOfEratosthenesPrimeFinder;

    PrimeFinderCommand(
        @Autowired @Qualifier("sieveOfAtkinPrimeFinder") PrimeFinder sieveOfAtkinPrimeFinder,
        @Autowired @Qualifier("sieveOfEratosthenesPrimeFinder") PrimeFinder sieveOfEratosthenesPrimeFinder
    ) {
        this.sieveOfAtkinPrimeFinder = sieveOfAtkinPrimeFinder;
        this.sieveOfEratosthenesPrimeFinder = sieveOfEratosthenesPrimeFinder;
    }

    @ShellMethod("Returns count and ten highest primes <= a number")
    public String a(final int number) {
        return primes(number, sieveOfAtkinPrimeFinder);
    }

    @ShellMethod("Returns count and ten highest primes <= a number")
    public String e(final int number) {
        return primes(number, sieveOfEratosthenesPrimeFinder);
    }

    private String primes(final int number, PrimeFinder primeFinder) {
        long start = System.currentTimeMillis();

        List<Integer> primesList = primeFinder.getPrimes(number);

        long runTime = System.currentTimeMillis() - start;

        String primesStringList =
                primesList.subList(Math.max(0, primesList.size() - 10), primesList.size())
                        .stream()
                        .map(prime -> Integer.toString(prime))
                        .collect(Collectors.joining(", "));

        return String.format(
            """
                highest ten primes: %s
                total primes: %d
                runtime: %dms""",
            primesStringList,
            primesList.size(),
            runTime
        );
    }
}
