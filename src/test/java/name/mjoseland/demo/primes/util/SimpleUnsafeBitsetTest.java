package name.mjoseland.demo.primes.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUnsafeBitsetTest {

    @Nested
    class SetGetTest {

        @Test
        public void givenSize4_whenGetAll_thenResultIsFalse() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(4);

            assertThat(bitset.get(0)).isFalse();
            assertThat(bitset.get(1)).isFalse();
            assertThat(bitset.get(2)).isFalse();
            assertThat(bitset.get(3)).isFalse();
        }

        @Test
        public void givenSize4_whenSet0GetAll_thenResultFor0IsTrue() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(4);

            bitset.set(0);

            assertThat(bitset.get(0)).isTrue();
            assertThat(bitset.get(1)).isFalse();
            assertThat(bitset.get(2)).isFalse();
            assertThat(bitset.get(3)).isFalse();
        }

        @Test
        public void givenSize4_whenSetAllGetAll_thenResultIsTrue() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(4);

            bitset.set(0);
            bitset.set(1);
            bitset.set(2);
            bitset.set(3);

            assertThat(bitset.get(0)).isTrue();
            assertThat(bitset.get(1)).isTrue();
            assertThat(bitset.get(2)).isTrue();
            assertThat(bitset.get(3)).isTrue();
        }

        @Test
        public void givenSize65_whenSet64Get64_thenResultIsTrue() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(65);

            bitset.set(64);

            assertThat(bitset.get(64)).isTrue();
        }
    }

    @Nested
    class ToggleGetTest {

        @Test
        public void givenSize2_whenToggle0GetAll_thenResultFor0IsTrue() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(4);

            bitset.toggle(0);

            assertThat(bitset.get(0)).isTrue();
            assertThat(bitset.get(1)).isFalse();
        }

        @Test
        public void givenSize2_whenToggle0TwiceGetAll_thenResultIsFalse() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(4);

            bitset.toggle(0);
            bitset.toggle(0);

            assertThat(bitset.get(0)).isFalse();
            assertThat(bitset.get(1)).isFalse();
        }

        @Test
        public void givenSize65_whenToggle64Get64_thenResultIsTrue() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(65);

            bitset.toggle(64);

            assertThat(bitset.get(64)).isTrue();
        }
    }

    @Nested
    class NextSetBitTest {

        @Test
        public void givenSize10_thenResultIsMinus1() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            assertThat(bitset.nextSetBit(0)).isEqualTo(-1);
        }

        @Test
        public void givenSize10_whenSet8_thenResultIs8() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            bitset.set(8);

            assertThat(bitset.nextSetBit(0)).isEqualTo(8);
        }

        @Test
        public void givenSize10_whenSet2And5_thenResultFrom2Is2() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            bitset.set(2);
            bitset.set(5);

            assertThat(bitset.nextSetBit(2)).isEqualTo(2);
        }

        @Test
        public void givenSize10_whenSet2And5_thenResultFrom3Is5() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            bitset.set(2);
            bitset.set(5);

            assertThat(bitset.nextSetBit(3)).isEqualTo(5);
        }

        @Test
        public void givenSize100_whenSet70And71_thenResultFrom10Is70() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(100);

            bitset.set(70);
            bitset.set(71);

            assertThat(bitset.nextSetBit(10)).isEqualTo(70);
        }

        @Test
        public void givenSize100_whenSet99_thenResultIs99() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(SimpleUnsafeBitset.MAX_SIZE);

            bitset.set(99);

            assertThat(bitset.nextSetBit(0)).isEqualTo(99);
        }
    }

    @Nested
    class NextUnsetBitTest {

        @Test
        public void givenSize10_thenResultIs0() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(0);
        }

        @Test
        public void givenSize10_whenSet0_thenResultIs1() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            bitset.set(0);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(1);
        }

        @Test
        public void givenSize10_whenSet2To5_thenResultFrom2Is6() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            for (int i = 2; i <= 5; i++)
                bitset.set(i);

            assertThat(bitset.nextUnsetBit(2)).isEqualTo(6);
        }

        @Test
        public void givenSize10_whenSetAll_thenResultIsMinus1() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(10);

            for (int i = 0; i <= 9; i++)
                bitset.set(i);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(-1);
        }

        @Test
        public void givenSize64_whenSet0to62_thenResultIs63() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

            for (int i = 0; i <= 62; i++)
                bitset.set(i);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(63);
        }

        @Test
        public void givenSize64_whenSetAll_thenResultIsMinus1() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(64);

            for (int i = 0; i <= 63; i++)
                bitset.set(i);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(-1);
        }

        @Test
        public void givenSize100_whenSet0to70_thenResultIs71() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(100);

            for (int i = 0; i <= 70; i++)
                bitset.set(i);

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(71);
        }

        @Test
        public void givenSizeMax_whenSet0To100_thenResultIs101() {
            SimpleUnsafeBitset bitset = new SimpleUnsafeBitset(SimpleUnsafeBitset.MAX_SIZE);

            for (int i = 0; i <= 100; i++) {
                bitset.set(i);
            }

            assertThat(bitset.nextUnsetBit(0)).isEqualTo(101);
        }
    }
}
