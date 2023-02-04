package name.mjoseland.demo.primes.primes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AtkinWheelIntSupplierTest {

    @Test
    void whenGetFirst32_thenResultEqualToWheelHitPositionsPlus60TimesI() {
        AtkinWheelIntSupplier supplier = new AtkinWheelIntSupplier();

        assertThat(supplier.getAsInt()).isEqualTo(1);
        assertThat(supplier.getAsInt()).isEqualTo(7);
        assertThat(supplier.getAsInt()).isEqualTo(11);
        assertThat(supplier.getAsInt()).isEqualTo(13);
        assertThat(supplier.getAsInt()).isEqualTo(17);
        assertThat(supplier.getAsInt()).isEqualTo(19);
        assertThat(supplier.getAsInt()).isEqualTo(23);
        assertThat(supplier.getAsInt()).isEqualTo(29);
        assertThat(supplier.getAsInt()).isEqualTo(31);
        assertThat(supplier.getAsInt()).isEqualTo(37);
        assertThat(supplier.getAsInt()).isEqualTo(41);
        assertThat(supplier.getAsInt()).isEqualTo(43);
        assertThat(supplier.getAsInt()).isEqualTo(47);
        assertThat(supplier.getAsInt()).isEqualTo(49);
        assertThat(supplier.getAsInt()).isEqualTo(53);
        assertThat(supplier.getAsInt()).isEqualTo(59);

        assertThat(supplier.getAsInt()).isEqualTo(60 + 1);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 7);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 11);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 13);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 17);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 19);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 23);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 29);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 31);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 37);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 41);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 43);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 47);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 49);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 53);
        assertThat(supplier.getAsInt()).isEqualTo(60 + 59);
    }
}
