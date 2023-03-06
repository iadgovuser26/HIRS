package hirs.persist;

/**
 * This class represents an <code>Exception</code> generated by a
 * <code>DeviceManageer</code>.
 */
public class DeviceManagerException extends RuntimeException {

    private static final long serialVersionUID = 6302897164826200167L;

    /**
     * Creates a new <code>DeviceManagerException</code> that has the message
     * <code>msg</code>.
     *
     * @param msg
     *            exception message
     */
    public DeviceManagerException(final String msg) {
        super(msg);
    }

    /**
     * Creates a new <code>DeviceManagerException</code> that wraps the given
     * <code>Throwable</code>.
     *
     * @param t
     *            root cause
     */
    public DeviceManagerException(final Throwable t) {
        super(t);
    }

    /**
     * Creates a new <code>DeviceManagerException</code> that has the message
     * <code>msg</code> and wraps the root cause.
     *
     * @param msg
     *            exception message
     * @param t
     *            root cause
     */
    public DeviceManagerException(final String msg, final Throwable t) {
        super(msg, t);
    }

}