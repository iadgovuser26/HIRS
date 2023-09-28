package hirs.attestationca.portal.page;

import hirs.attestationca.portal.HIRSApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;


/**
 * Base class for PageController tests.
 */

//@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest
/*
@TestPropertySources({
        @TestPropertySource(value = "classpath:hibernate.properties"),

        // detects if file exists, if not, ignore errors
        @TestPropertySource(value = "file:/etc/hirs/aca/application.properties")
})
*/
//@TestPropertySource(locations = "file:/etc/hirs/aca/application.properties")
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes={ HIRSApplication.class})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // needed to use non-static BeforeAll
@WebAppConfiguration
@ContextConfiguration(classes = PageTestConfiguration.class)
public abstract class PageControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final Page page;

    /**
     * Constructor requiring the Page's display and routing specification.
     *
     * @param page The page specification for this controller.
     */
    public PageControllerTest(final Page page) {
        this.page = page;
    }

    /**
     * Returns the Page's display and routing specification.
     *
     * @return the Page's display and routing specification.
     */
    protected Page getPage() {
        return page;
    }

    /**
     * Returns Spring MVC Test object.
     *
     * @return Spring MVC Test object
     */
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Executes a test to check that the page exists, has the correct name, and basic page data in
     * its model. Methods annotated with @Test in abstract classes are ignored by Spring.
     *
     * @throws Exception if test fails
     */
    @Test
    public final void doTestPageExists() throws Exception {
        // Add prefix path for page verification
        String pagePath = "/" + page.getPrefixPath() + page.getViewName();
        if (page.getPrefixPath() == null) {
            pagePath = "/" + page.getViewName();
        }
        System.out.println("Page Path: " + pagePath);

        getMockMvc()
                .perform(MockMvcRequestBuilders.get(pagePath))
                .andExpect(status().isOk())
        //        .andExpect(view().name(page.getViewName()))
        //        .andExpect(forwardedUrl("/WEB-INF/jsp/" + page.getViewName() + ".jsp"))
        //        .andExpect(model().attribute(PageController.PAGE_ATTRIBUTE, equalTo(page)))
        //        .andExpect(model().attribute(
        //                PageController.PAGES_ATTRIBUTE, equalTo(Page.values()))
        //        )
        ;
    }
}

