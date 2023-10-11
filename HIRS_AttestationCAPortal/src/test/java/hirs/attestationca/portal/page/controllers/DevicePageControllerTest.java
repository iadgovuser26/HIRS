package hirs.attestationca.portal.page.controllers;

import hirs.attestationca.persist.entity.manager.CertificateRepository;
import hirs.attestationca.persist.entity.manager.DeviceRepository;
import hirs.attestationca.persist.entity.userdefined.Certificate;
import hirs.attestationca.persist.entity.userdefined.certificate.EndorsementCredential;
import hirs.attestationca.persist.entity.userdefined.certificate.PlatformCredential;
import hirs.attestationca.persist.entity.userdefined.info.*;
import hirs.attestationca.persist.entity.userdefined.report.DeviceInfoReport;
import hirs.attestationca.persist.enums.AppraisalStatus;
import hirs.attestationca.persist.enums.HealthStatus;
import hirs.attestationca.portal.page.PageControllerTest;

import static hirs.attestationca.portal.page.Page.DEVICES;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hirs.attestationca.persist.entity.userdefined.Device;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.Document;
//import org.testng.collections.Lists;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests that test the URL End Points of DevicePageController.
 */
//@WebMvcTest(DevicePageController.class)
//@WebAppConfiguration
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DevicePageControllerTest extends PageControllerTest {

    private static final String DEVICE_NAME = "Test Device - dell-lat-l-02";
    //private static final String DEVICE_GROUP_NAME = "Test Device Group";
    private static final String TEST_ENDORSEMENT_CREDENTIAL
            = "/endorsement_credentials/tpmcert.pem";
    private static final String TEST_ENDORSEMENT_CREDENTIAL_2
            = "/endorsement_credentials/ab21ccf2-tpmcert.pem";
    private static final String TEST_PLATFORM_CREDENTIAL
            = "/platform_credentials/Intel_pc.cer";

    private Device device;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CertificateRepository certificateRepository;



    /**
     * Constructor providing the Page's display and routing specification.
     */
    public DevicePageControllerTest() {
        super(DEVICES);

        if (device == null) {
            System.out.println("\nCONSTRUCTOR: Device is null");
        } else {
            System.out.println("\nCONSTRUCTOR: Device is NOT null");
        }
        if (deviceRepository == null) {
            System.out.println("\nCONSTRUCTOR: deviceRepository is null");
        } else {
            System.out.println("\nCONSTRUCTOR: deviceRepository is NOT null");
        }

        if (certificateRepository == null) {
            System.out.println("\nCONSTRUCTOR: certificateRepository is null");
        } else {
            System.out.println("\nCONSTRUCTOR: certificateRepository is NOT null");
        }


    }

    /**
     * Prepares a testing environment.
     * @throws IOException if there is a problem constructing the test certificate
     */
    @BeforeAll
    public void beforeMethod() throws IOException {


        if (device == null) {
            System.out.println("\nBEFORE METHOD: Device is null");
        } else {
            System.out.println("\nBEFORE METHOD: Device is NOT null");
        }
        if (deviceRepository == null) {
            System.out.println("\nBEFORE METHOD: deviceRepository is null");
        } else {
            System.out.println("\nBEFORE METHOD: deviceRepository is NOT null");
        }

        if (certificateRepository == null) {
            System.out.println("\nBEFORE METHOD: certificateRepository is null");
        } else {
            System.out.println("\nBEFORE METHOD: certificateRepository is NOT null");
        }


        //Create new device and save it
        //InetAddress ia = new InetAddress();
        //NetworkInfo ni = new NetworkInfo("tmp_hostname",ia,"AA.BB.CC.DD.EE.FF".getBytes());
        /*
        NetworkInfo ni = new NetworkInfo(null,null,null);
        OSInfo oi = new OSInfo("Linux","Rocky","x86_64","8","7");
        FirmwareInfo fi = new FirmwareInfo("Dell","A11","03/12/2013");
        HardwareInfo hi = new HardwareInfo("1","2","3","4","5","6");
        TPMInfo ti = new TPMInfo();
        DeviceInfoReport dir = new DeviceInfoReport(ni,oi,fi,hi,ti);
        Timestamp ts = new Timestamp(2000,01,01,12,30,45,10);
        */

        //device = new Device("device1",dir, HealthStatus.TRUSTED, AppraisalStatus.Status.PASS,ts,false,"tmp_overrideReason", "tmp_summId");
        device = new Device(DEVICE_NAME,null, HealthStatus.TRUSTED, AppraisalStatus.Status.PASS,null,false,"tmp_overrideReason", "tmp_summId");
        device = deviceRepository.save(device);

        System.out.println("\nUUID:" + device.getId());
        System.out.println("Number of devices:" + deviceRepository.count());
        System.out.println("List of info for each device:");
        System.out.println(deviceRepository.findAll());


        //Upload and save EK Cert
        EndorsementCredential ec = (EndorsementCredential)
                    getTestCertificate(EndorsementCredential.class,
                    TEST_ENDORSEMENT_CREDENTIAL);
        ec.setDeviceId(device.getId());
        certificateRepository.save(ec);

        //Add second EK Cert without a device
        ec = (EndorsementCredential)
                getTestCertificate(EndorsementCredential.class, TEST_ENDORSEMENT_CREDENTIAL_2);
        certificateRepository.save(ec);

        //Upload and save Platform Cert
        PlatformCredential pc = (PlatformCredential)
                getTestCertificate(PlatformCredential.class, TEST_PLATFORM_CREDENTIAL);
        pc.setDeviceId(device.getId());
        certificateRepository.save(pc);

    }

    /**
     * Construct a test certificate from the given parameters.
     * @param <T> the type of Certificate that will be created
     * @param certificateClass the class of certificate to generate
     * @param filename the location of the certificate to be used
     * @return the newly-constructed Certificate
     * @throws IOException if there is a problem constructing the test certificate
     */
    public <T extends Certificate> Certificate getTestCertificate(
            final Class<T> certificateClass,
            final String filename)
            throws IOException {

        Path fPath;
        try {
            fPath = Paths.get(this.getClass().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            throw new IOException("Could not resolve path URI", e);
        }

        switch (certificateClass.getSimpleName()) {
            case "EndorsementCredential":
                return new EndorsementCredential(fPath);
            case "PlatformCredential":
                return new PlatformCredential(fPath);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown certificate class %s", certificateClass.getName())
                );
        }
    }

    /**
     * Cleans up a testing environment.
    */
    @AfterAll
    public void afterMethod() {

        deviceRepository.deleteAll();
        certificateRepository.deleteAll();
        System.out.println("Number of devices after deleting:" + deviceRepository.count() + "\n");

    }


    /**
     * Tests retrieving the device list using a mocked device manager.
     *
     * @throws Exception if test fails
     */
    @Test
    //@Rollback
    //@Transactional
    public void getDeviceList() throws Exception {

        // Add prefix path for page verification
        String pagePath = "/" + getPage().getPrefixPath() + getPage().getViewName();
        if (getPage().getPrefixPath() == null) {
            pagePath = "/" + getPage().getViewName();
        }
        pagePath = pagePath + "/list";
        System.out.println("Device List Page Path: " + pagePath);

        // perform test
        getMockMvc()
                .perform(MockMvcRequestBuilders.get(pagePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andReturn()
        ;
    }


}


