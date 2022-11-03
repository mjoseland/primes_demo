package name.mjoseland.demo.primes.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUnsafeBitsetTest {

    @Test
    public void givenBitsetOf64_thenNextSetIs0() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(0);
    }

    @Test
    public void givenBitsetOf64_whenUnset0_thenNextSetIs1() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

        bitset.set(0);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(1);
    }

    @Test
    public void givenBitsetOf64_whenUnset0to31_thenNextSetIs32() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

        for (int i = 0; i < 32; i++)
            bitset.set(i);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(32);
    }

    @Test
    public void givenBitsetOf64_whenUnset0to32_thenNextSetIs33() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

        for (int i = 0; i < 33; i++)
            bitset.set(i);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(33);
    }

    @Test
    public void givenBitsetOf65_whenUnset0to64_thenNextSetIs65() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(65);

        for (int i = 0; i < 65; i++)
            bitset.set(i);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(65);
    }

    @Test
    public void givenBitsetOf64_whenUnset0to64_thenNextSetIsMinus1() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

        for (int i = 0; i < 65; i++)
            bitset.set(i);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(-1);
    }

    @Test
    public void givenBitsetOf65_whenUnset0to65_thenNextSetIsMinus1() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(65);

        for (int i = 0; i < 66; i++)
            bitset.set(i);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(-1);
    }

    @Test
    public void givenBitsetOf100_whenUnset45_thenNextSetFrom45Is46() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(100);

        bitset.set(45);

        assertThat(bitset.nextUnsetBit(45)).isEqualTo(46);
    }

    @Test
    public void givenBitsetOf10_whenUnset0Twice_thenNextSetIs1() {
        SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

        bitset.set(0);
        bitset.set(0);

        assertThat(bitset.nextUnsetBit(0)).isEqualTo(1);
    }
}
