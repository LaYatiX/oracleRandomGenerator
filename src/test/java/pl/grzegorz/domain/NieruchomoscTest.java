package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class NieruchomoscTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nieruchomosc.class);
        Nieruchomosc nieruchomosc1 = new Nieruchomosc();
        nieruchomosc1.setId(1L);
        Nieruchomosc nieruchomosc2 = new Nieruchomosc();
        nieruchomosc2.setId(nieruchomosc1.getId());
        assertThat(nieruchomosc1).isEqualTo(nieruchomosc2);
        nieruchomosc2.setId(2L);
        assertThat(nieruchomosc1).isNotEqualTo(nieruchomosc2);
        nieruchomosc1.setId(null);
        assertThat(nieruchomosc1).isNotEqualTo(nieruchomosc2);
    }
}
