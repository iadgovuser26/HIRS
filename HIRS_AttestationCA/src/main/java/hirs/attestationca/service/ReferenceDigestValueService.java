package hirs.attestationca.service;

import hirs.attestationca.entity.ReferenceDigestValue;
import hirs.persist.OrderedQuery;

import java.util.List;
import java.util.UUID;

/**
 * A <code>ReferenceDigestValue</code> manages <code>ReferenceDigestValue</code>s. A
 * <code>ReferenceDigestValue</code> is used to store and manage digest events. It has
 * support for the basic create, read, update, and delete methods.
 */
public interface ReferenceDigestValueService extends OrderedQuery<ReferenceDigestValue> {

    /**
     * Saves the <code>ReferenceDigestValue</code> in the database. This creates a new
     * database session and saves the device.
     *
     * @param digestValue Certificate to save
     * @return reference to saved reference digest value
     */
    ReferenceDigestValue saveDigestValue(ReferenceDigestValue digestValue);

    /**
     * Updates a <code>ReferenceDigestValue</code>. This updates the database entries to
     * reflect the new values that should be set.
     *
     * @param digestValue Certificate object to save
     * @param uuid UUID for the database object
     * @return a ReferenceDigestValue object
     */
    ReferenceDigestValue updateDigestValue(ReferenceDigestValue digestValue, UUID uuid);

    /**
     * Persists an object by the manufacturer and model.
     * @param manufacturer string parameter for the manufacturer
     * @param model string parameter for the model
     * @return a list of RDVs associated with the parameters
     */
    List<ReferenceDigestValue> getValueByManufacturerModel(String manufacturer, String model);

    /**
     * Persists a new Reference Digest value.
     *
     * @param uuid associated with the base rim .
     * @return the persisted list of ReferenceDigestValue
     */
    List<ReferenceDigestValue> getValuesByBaseRimId(UUID uuid);

    /**
     * Persists a new Reference Digest value.
     *
     * @param uuid associated with the support rim.
     * @return the persisted list of ReferenceDigestValue
     */
    List<ReferenceDigestValue> getValuesBySupportRimId(UUID uuid);
}