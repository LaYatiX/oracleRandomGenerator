package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class SklepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sklep.class);
        Sklep sklep1 = new Sklep();
        sklep1.setId(1L);
        Sklep sklep2 = new Sklep();
        sklep2.setId(sklep1.getId());
        assertThat(sklep1).isEqualTo(sklep2);
        sklep2.setId(2L);
        assertThat(sklep1).isNotEqualTo(sklep2);
        sklep1.setId(null);
        assertThat(sklep1).isNotEqualTo(sklep2);
    }
}
