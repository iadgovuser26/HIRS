package hirs.attestationca.portal.page;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationIntegrationTest {

    @Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }
}
