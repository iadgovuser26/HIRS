package hirs;

import hirs.data.persist.Report;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 * A <code>ReportRequest</code> is generated by the <code>Appraiser</code> and used to instruct the
 * client to produce a particular report or set of reports. Reporting configuration parameters, such
 * as the URL of the appraiser, are also specified in the <code>ReportRequest</code>.
 * <p>
 * This <code>ReportRequest</code> class contains minimal information because each report request is
 * vastly different. The report type is used to specify the type of report to be generated by the
 * client and submitted to the <code>Appraiser</code> for processing.
 */
@XmlTransient
@XmlSeeAlso(value = { IntegrityReportRequest.class, TPMReportRequest.class,
        DeviceInfoReportRequest.class })
public interface ReportRequest {

    /**
     * Returns a <code>Report</code> class that indicates the type of report requested.  The report
     * type is used to specify what report(s) the client is expected to generate and submit to the
     * <code>Appraiser</code> for evaluation.
     *
     * @return report type
     */
    Class<? extends Report> getReportType();
}