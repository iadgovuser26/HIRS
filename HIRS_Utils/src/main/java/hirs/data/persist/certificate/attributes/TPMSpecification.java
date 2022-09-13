package hirs.data.persist.certificate.attributes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigInteger;

/**
 * A class to represent the TPM Specification in an Endorsement Credential as
 * defined by the TCG spec for TPM 1.2.
 *
 * https://www.trustedcomputinggroup.org/wp-content/uploads/IWG-Credential_Profiles_V1_R0.pdf
 *
 * Future iterations of this code may want to reference
 * www.trustedcomputinggroup.org/wp-content/uploads/Credential_Profile_EK_V2.0_R14_published.pdf
 * for specifications for TPM 2.0.
 */
@Embeddable
public class TPMSpecification {

    @Column
    private String family;

    @Column
    private BigInteger level;

    @Column
    private BigInteger revision;

    /**
     * Standard constructor.
     * @param family the specification family.
     * @param level the specification level.
     * @param revision the specification revision.
     */
    public TPMSpecification(final String family, final BigInteger level,
                            final BigInteger revision) {
        this.family = family;
        this.level = level;
        this.revision = revision;
    }

    /**
     * Default constructor for Hibernate.
     */
    protected TPMSpecification() {

    }

    /**
     * Get the specification family of the TPM Specification.
     * @return the specification family of the TPM Specification.
     */
    public String getFamily() {
        return family;
    }

    /**
     * Get the specification level of the TPM Specification.
     * @return the specification level of the TPM Specification
     */
    public BigInteger getLevel() {
        return level;
    }

    /**
     * Get the revision of the TPM Specification.
     * @return the revision of the TPM Specification
     */
    public BigInteger getRevision() {
        return revision;
    }

    // this method was autogenerated
    @Override
    @SuppressWarnings("checkstyle:avoidinlineconditionals")
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TPMSpecification that = (TPMSpecification) o;

        if (family != null ? !family.equals(that.family) : that.family != null) {
            return false;
        }
        if (level != null ? !level.equals(that.level) : that.level != null) {
            return false;
        }
        return revision != null ? revision.equals(that.revision) : that.revision == null;

    }

    // this method was autogenerated
    @Override
    @SuppressWarnings({"checkstyle:avoidinlineconditionals", "checkstyle:magicnumber" })
    public int hashCode() {
        int result = family != null ? family.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (revision != null ? revision.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TPMSpecification{"
                + "family='" + family + '\''
                + ", level=" + level
                + ", revision=" + revision
                + '}';
    }
}