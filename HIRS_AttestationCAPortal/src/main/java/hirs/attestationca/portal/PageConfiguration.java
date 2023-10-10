package hirs.attestationca.portal;

import hirs.attestationca.portal.datatables.DataTableView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


/**
 * Specifies the location to scan for page controllers, view resolver for JSON data, and view
 * resolver to map view names to jsp files.
 */
@Configuration
@EnableWebMvc
@ComponentScan("hirs.attestationca.portal.page.controllers")
public class PageConfiguration {

    /**
     * @return bean to resolve injected annotation.Value
     * property expressions for beans.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Makes all URLs that end in "dataTable" use DataTableView to serialize DataTableResponse.
     *
     * @return ViewResolver that uses DataTableView.
     */
    @Bean
    public ViewResolver dataTableViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(DataTableView.class);
        resolver.setViewNames("*dataTable");
        resolver.setOrder(0);
        return resolver;
    }

    /**
     * Maps view names to the appropriate jsp file.
     * <p>
     * Only seems to apply to GET requests.
     *
     * @return a ViewResolver bean containing the mapping.
     */
    @Bean
    public ViewResolver pageViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
