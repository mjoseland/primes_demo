package name.mjoseland.demo.primes.find;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PrimeFinderTest {

    public static final String FIRST_1000_PRIMES_FILENAME = "src/test/resources/first_1000_primes.txt";

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    void assertForNegative1(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(-1);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    void assertFor0(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(0);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    void assertForFirst1000Primes(PrimeFinder primeFinder) throws IOException {
        Path first1000PrimesPath = Path.of(FIRST_1000_PRIMES_FILENAME);
        String first1000PrimesString = Files.readString(first1000PrimesPath);
        List<Integer> expected = Arrays.stream(first1000PrimesString.split(","))
            .map(Integer::parseInt)
            .toList();

        List<Integer> result = primeFinder.getPrimes(7920);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    void assertCountForMaxNumber1000000(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(1000000);

        assertThat(result).hasSize(78498);
    }

    @Disabled("This test passes (at the time of being disabled) but it takes a long time to run")
    @ParameterizedTest
    @MethodSource("getPrimeFinders")
    void assertCountFor1Trillion(PrimeFinder primeFinder) {
        List<Integer> result = primeFinder.getPrimes(1_000_000_000);

        assertThat(result).hasSize(50847534);
    }

    @Disabled("This test passes (at the time of being disabled) but it takes a long time to run")
    @Test
    void assertCountForMaxInteger() {
        PrimeFinder primeFinder = new SieveOfAtkinPrimeFinder();

        List<Integer> result = primeFinder.getPrimes(Integer.MAX_VALUE);

        assertThat(result).hasSize(105097565);
    }

    private static Stream<Arguments> getPrimeFinders() {
        return Stream.of(
            Arguments.of(new SieveOfEratosthenesPrimeFinder()),
            Arguments.of(new SieveOfAtkinPrimeFinder())
        );
    }
}
