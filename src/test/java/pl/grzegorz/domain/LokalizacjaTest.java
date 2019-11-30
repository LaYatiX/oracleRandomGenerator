package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class LokalizacjaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lokalizacja.class);
        Lokalizacja lokalizacja1 = new Lokalizacja();
        lokalizacja1.setId(1L);
        Lokalizacja lokalizacja2 = new Lokalizacja();
        lokalizacja2.setId(lokalizacja1.getId());
        assertThat(lokalizacja1).isEqualTo(lokalizacja2);
        lokalizacja2.setId(2L);
        assertThat(lokalizacja1).isNotEqualTo(lokalizacja2);
        lokalizacja1.setId(null);
        assertThat(lokalizacja1).isNotEqualTo(lokalizacja2);
    }
}
