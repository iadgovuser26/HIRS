package hirs.attestationca.portal.page;

import static org.assertj.core.api.Assertions.assertThat;

import hirs.attestationca.portal.HIRSApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@WebMvcTest
//@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes={ HIRSApplication.class})
public class ApplicationIntegrationTest {

    @Test
    void TestNothing() {
    //void contextLoads(ApplicationContext context) {
        //assertThat(context).isNotNull();
    }
}
