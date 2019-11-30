package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class MieszkanieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mieszkanie.class);
        Mieszkanie mieszkanie1 = new Mieszkanie();
        mieszkanie1.setId(1L);
        Mieszkanie mieszkanie2 = new Mieszkanie();
        mieszkanie2.setId(mieszkanie1.getId());
        assertThat(mieszkanie1).isEqualTo(mieszkanie2);
        mieszkanie2.setId(2L);
        assertThat(mieszkanie1).isNotEqualTo(mieszkanie2);
        mieszkanie1.setId(null);
        assertThat(mieszkanie1).isNotEqualTo(mieszkanie2);
    }
}
