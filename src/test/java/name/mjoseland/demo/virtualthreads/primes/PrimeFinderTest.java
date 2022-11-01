package name.mjoseland.demo.virtualthreads.primes;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PrimeFinderTest {

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    public void assertForMaxNumber100(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(100);

        assertThat(result).hasSize(25);
        assertThat(result).containsAll(
            List.of(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
            )
        );
    }

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    public void assertCountForMaxNumber1000000(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(1000000);

        assertThat(result).hasSize(78498);
    }

    // TODO test overflows

    private static Stream<Arguments> getPrimeFinders() {
        return Stream.of(
            Arguments.of(new BasicPrimeFinder()),
            Arguments.of(new SieveOfEratosthenesPrimeFinder())
        );
    }
}
