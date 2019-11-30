package pl.grzegorz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.grzegorz.web.rest.TestUtil;

public class GodzinyOtwarciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GodzinyOtwarcia.class);
        GodzinyOtwarcia godzinyOtwarcia1 = new GodzinyOtwarcia();
        godzinyOtwarcia1.setId(1L);
        GodzinyOtwarcia godzinyOtwarcia2 = new GodzinyOtwarcia();
        godzinyOtwarcia2.setId(godzinyOtwarcia1.getId());
        assertThat(godzinyOtwarcia1).isEqualTo(godzinyOtwarcia2);
        godzinyOtwarcia2.setId(2L);
        assertThat(godzinyOtwarcia1).isNotEqualTo(godzinyOtwarcia2);
        godzinyOtwarcia1.setId(null);
        assertThat(godzinyOtwarcia1).isNotEqualTo(godzinyOtwarcia2);
    }
}
