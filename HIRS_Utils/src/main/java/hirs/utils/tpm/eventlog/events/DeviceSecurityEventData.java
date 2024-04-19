package hirs.utils.tpm.eventlog.events;


import lombok.Getter;

public class DeviceSecurityEventData extends DeviceSecurityEventDataBase {

    /**
     * DeviceSecurityEventDataHeader Object.
     */
    @Getter
    private DeviceSecurityEventDataHeader dsedHeader = null;

    /**
     * DeviceSecurityEventData Constructor.
     *
     * @param dSEDbytes byte array holding the DeviceSecurityEventData.
     */
    public DeviceSecurityEventData(final byte[] dSEDbytes) {
        super(dSEDbytes);
        dsedHeader = new DeviceSecurityEventDataHeader(dSEDbytes);
    }

    public String toString() {
        String dsedInfo = "";
        dsedInfo += dsedHeader.toString();
//      dsedInfo += dsedDeviceContext.toString();
        return dsedInfo;
    }
}
