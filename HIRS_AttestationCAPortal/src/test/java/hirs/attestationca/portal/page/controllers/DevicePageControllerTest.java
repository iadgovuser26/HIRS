package hirs.attestationca.portal.page.controllers;

import hirs.attestationca.persist.entity.manager.CertificateRepository;
import hirs.attestationca.persist.entity.manager.DeviceRepository;
import hirs.attestationca.portal.page.PageControllerTest;

import static hirs.attestationca.portal.page.Page.DEVICES;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hirs.attestationca.persist.entity.userdefined.Device;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests that test the URL End Points of DevicePageController.
 */
@WebAppConfiguration
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DevicePageControllerTest extends PageControllerTest {


    private static final String DEVICE_NAME = "Test Device";
    private static final String DEVICE_GROUP_NAME = "Test Device Group";
    //private static final String TEST_ENDORSEMENT_CREDENTIAL
    //        = "/endorsement_credentials/tpmcert.pem";
    //private static final String TEST_ENDORSEMENT_CREDENTIAL_2
    //        = "/endorsement_credentials/ab21ccf2-tpmcert.pem";
    //private static final String TEST_PLATFORM_CREDENTIAL
    //        = "/platform_credentials/Intel_pc.cer";

    private Device device;

    //@Autowired
    @MockBean
    private DeviceRepository deviceRepository;
    //private final DeviceRepository deviceRepository;

    //@Autowired
    @MockBean
    private CertificateRepository certificateRepository;
    //private final CertificateRepository certificateRepository;


    /**
     * Constructor providing the Page's display and routing specification.
     */
    public DevicePageControllerTest() {
        super(DEVICES);
    }


    /**
     * Tests retrieving the device list using a mocked device manager.
     *
     * @throws Exception if test fails
     */
    @Test
    @Rollback
    public void getDeviceList() throws Exception {
        // perform test
        //    getMockMvc().perform(MockMvcRequestBuilders
        //                    .get("/devices/list"))
        //            .andExpect(status().isOk())
        //            .andExpect(jsonPath("$.data", hasSize(1)))
        //            .andReturn();
    }


}


