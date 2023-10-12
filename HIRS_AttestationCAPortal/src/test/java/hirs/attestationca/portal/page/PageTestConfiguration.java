package hirs.attestationca.portal.page;

import hirs.attestationca.portal.PageConfiguration;
import hirs.attestationca.persist.entity.userdefined.certificate.CertificateAuthorityCredential;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.Properties;

/**
 * A configuration class for testing Attestation CA Portal classes that require a database.
 * This apparently is needed to appease spring tests in the TestNG runner.
 */
//@Import({ PageConfiguration.class })
@Configuration
@EnableJpaRepositories(basePackages = "hirs.attestationca.persist.entity.manager")
//@EnableTransactionManagement
public class PageTestConfiguration {

    /**
     * Test ACA cert.
     */
    public static final String FAKE_ROOT_CA = "/certificates/fakeCA.pem";

    /**
     * Gets a test x509 cert as the ACA cert for ACA portal tests.
     *
     * @return the {@link X509Certificate} of the ACA
     * @throws URISyntaxException if there's a syntax error on the path to the cert
     * @throws IOException exception reading the file
     */
    //@Bean
    @Bean("test_acaCertificate")
    @Primary
    public X509Certificate acaCertificate() throws URISyntaxException, IOException {

        CertificateAuthorityCredential credential = new CertificateAuthorityCredential(
                Files.readAllBytes(Paths.get(getClass().getResource(FAKE_ROOT_CA).toURI()))
        );
        return credential.getX509Certificate();
    }


    /**
     * Overrides the {@link DataSource} with one that is configured against an in-memory HSQL DB.
     *
     * @return test data source
     */
    //@Bean
    @Bean("test_dataSource")
    //@Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL).build();
    }

    /**
     * Configures a session factory bean that in turn configures the hibernate session factory.
     * Enables auto scanning of annotations such that entities do not need to be registered in a
     * hibernate configuration file.
     *
     * @return session factory
     */
    //@Bean("test_entityMangerFactory")
    //@Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        final LocalContainerEntityManagerFactoryBean entityManagerBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerBean.setDataSource(dataSource());
        entityManagerBean.setPackagesToScan("hirs.attestationca.persist.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerBean.setJpaProperties(hibernateProperties());

        return entityManagerBean;
    }

    /**
     * Generates properties using configuration file that will be used to configure the session
     * factory.
     *
     * @return properties for hibernate session factory
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.current_session_context_class", "thread");
        return properties;
    }

    /**
     * Generates JPA transaction manager.
     *
     * @return transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
