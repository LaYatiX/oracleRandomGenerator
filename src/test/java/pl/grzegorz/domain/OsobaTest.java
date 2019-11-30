package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class OsobaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Osoba.class);
        Osoba osoba1 = new Osoba();
        osoba1.setId(1L);
        Osoba osoba2 = new Osoba();
        osoba2.setId(osoba1.getId());
        assertThat(osoba1).isEqualTo(osoba2);
        osoba2.setId(2L);
        assertThat(osoba1).isNotEqualTo(osoba2);
        osoba1.setId(null);
        assertThat(osoba1).isNotEqualTo(osoba2);
    }
}
