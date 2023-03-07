package hirs.attestationca.portal;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

@SpringBootApplication
@EnableAutoConfiguration
@Log4j2
@ComponentScan({"hirs.attestationca.portal", "hirs.attestationca.portal.page.controllers", "hirs.attestationca.persist.entity", "hirs.attestationca.persist.service"})
public class HIRSApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HIRSApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(HIRSApplication.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/portal"));
        springApplication.run(args);

        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
        log.warn("Warn log message");
        log.fatal("Fatal log message");
        log.trace("Trace log message");
    }
}