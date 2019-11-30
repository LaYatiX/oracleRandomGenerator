package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class TransakcjaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transakcja.class);
        Transakcja transakcja1 = new Transakcja();
        transakcja1.setId(1L);
        Transakcja transakcja2 = new Transakcja();
        transakcja2.setId(transakcja1.getId());
        assertThat(transakcja1).isEqualTo(transakcja2);
        transakcja2.setId(2L);
        assertThat(transakcja1).isNotEqualTo(transakcja2);
        transakcja1.setId(null);
        assertThat(transakcja1).isNotEqualTo(transakcja2);
    }
}
