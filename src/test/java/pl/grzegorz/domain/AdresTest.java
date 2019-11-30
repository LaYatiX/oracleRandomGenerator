package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class AdresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adres.class);
        Adres adres1 = new Adres();
        adres1.setId(1L);
        Adres adres2 = new Adres();
        adres2.setId(adres1.getId());
        assertThat(adres1).isEqualTo(adres2);
        adres2.setId(2L);
        assertThat(adres1).isNotEqualTo(adres2);
        adres1.setId(null);
        assertThat(adres1).isNotEqualTo(adres2);
    }
}
