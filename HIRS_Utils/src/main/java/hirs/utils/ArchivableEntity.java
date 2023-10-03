package hirs.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

/**
 * An abstract archivable entity that can be deleted.
 */
@ToString
@Getter
@MappedSuperclass
public abstract class ArchivableEntity extends AbstractEntity {

    /**
     * Defining the size of a message field for error display.
     */
    public static final int MAX_MESSAGE_LENGTH = 2400;

    @Column(name = "archived_time")
    private Date archivedTime;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "archived_description")
    private String archivedDescription;

    /**
     * Default empty constructor is required for Hibernate. It is protected to
     * prevent code from calling it directly.
     */
    protected ArchivableEntity() {
        super();
    }

    /**
     * Return the boolean representing whether or not this entity has been soft-deleted.
     *
     * @return true if this entity has been soft-deleted, false otherwise
     */
    public final boolean isArchived() {
        return archivedTime != null;
    }

    /**
     * Signals that this entity has been archived, by setting the archivedTime to the current date
     * and time.
     *
     * @return
     *      true if time was null and date was set.
     *      false is archived time is already set, signifying the entity has been archived.
     */
    public final boolean archive() {
        if (this.archivedTime == null) {
            this.archivedTime = new Date();
            return true;
        }
        return false;
    }

    /**
     * Sets a description for the resolution if one is provided.  This is done for accounting
     * purposes so the reason for action taken can be referenced.
     *
     * @param description - description of the action taken for resolution
     * @return
     *      boolean result is dependent on the return value of the archive() method
     */
    public final boolean archive(final String description) {
        if (archive()) {
            this.archivedDescription = description;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the archivedTime to null.  The archivedTime being null signifies that the entity has
     * not been archived.  If the time is already null then this call was unnecessary.
     *
     * @return
     *      true if the time is changed to null.
     *      false if time was already set to null.
     */
    public final boolean restore() {
        if (this.archivedTime != null) {
            this.archivedTime = null;
            this.archivedDescription = null;
            return true;
        }
        return false;
    }
}
