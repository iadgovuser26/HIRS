package hirs.attestationca.persist.service;

import hirs.attestationca.persist.CriteriaModifier;
import hirs.attestationca.persist.DBManagerException;
import hirs.attestationca.persist.FilteredRecordsList;
import hirs.attestationca.persist.entity.manager.ReferenceManifestRepository;
import hirs.attestationca.persist.entity.userdefined.ReferenceManifest;
import hirs.attestationca.persist.service.selector.ReferenceManifestSelector;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Log4j2
@Service
public class ReferenceManifestServiceImpl<T extends ReferenceManifest> extends DefaultDbService<ReferenceManifest> implements ReferenceManifestService<ReferenceManifest> {

    /**
     * The variable that establishes a schema factory for xml processing.
     */
    public static final SchemaFactory SCHEMA_FACTORY
            = SchemaFactory.newInstance(ReferenceManifest.SCHEMA_LANGUAGE);

    @Autowired(required = false)
    private EntityManager entityManager;

    @Autowired
    private ReferenceManifestRepository repository;

    private static Schema schema;

    public ReferenceManifestServiceImpl() {
        getSchemaObject();
    }

    /**
     * This method sets the xml schema for processing RIMs.
     *
     * @return the schema
     */
    public static final Schema getSchemaObject() {
        if (schema == null) {
            InputStream is = null;
            try {
                is = ReferenceManifest.class
                        .getClassLoader()
                        .getResourceAsStream(ReferenceManifest.SCHEMA_URL);
                schema = SCHEMA_FACTORY.newSchema(new StreamSource(is));
            } catch (SAXException saxEx) {
                log.error(String.format("Error setting schema for validation!%n%s",
                        saxEx.getMessage()));
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ioEx) {
                        log.error(String.format("Error closing input stream%n%s",
                                ioEx.getMessage()));
                    }
                } else {
                    log.error("Input stream variable is null");
                }
            }
        }
        return schema;
    }

    @Override
    public ReferenceManifest saveReferenceManifest(ReferenceManifest referenceManifest) {
        return repository.save(referenceManifest);
    }

    @Override
    public List<ReferenceManifest> fetchReferenceManifests() {
        return repository.findAll();
    }

    /**
     * This method does not need to be used directly as it is used by
     * {@link ReferenceManifestSelector}'s get* methods. Regardless, it may be
     * used to retrieve ReferenceManifest by other code in this package, given a
     * configured ReferenceManifestSelector.
     *
     * @param referenceManifestSelector a configured
     * {@link ReferenceManifestSelector} to use for querying
     * @return the resulting set of ReferenceManifest, possibly empty
     */
    @SuppressWarnings("unchecked")
    public <T extends  ReferenceManifest> List<T> get(
            Class<T> classType) {
        log.info("Getting the full set of Reference Manifest files.");
//        return new HashSet<>(
//                (List<T>) getWithCriteria(
//                        referenceManifestSelector.getReferenceManifestClass(),
//                        Collections.singleton(referenceManifestSelector.getCriterion())
//                )
//        );
        return (List<T>) repository.findAll(Sort.sort(classType));
    }

    @Override
    public ReferenceManifest updateReferenceManifest(ReferenceManifest referenceManifest, UUID rimId) {
        return null;
    }

    @Override
    public void deleteReferenceManifestById(UUID rimId) {
        repository.deleteById(rimId);
    }

    @Override
    public <T extends ReferenceManifest> Set<T> get(ReferenceManifestSelector referenceManifestSelector) {
        return null;
    }

    @Override
    public FilteredRecordsList getOrderedList(Class<? extends ReferenceManifest> clazz,
                                              String columnToOrder, boolean ascending, int firstResult,
                                              int maxResults, String search,
                                              Map<String, Boolean> searchableColumns) throws DBManagerException {
        return null;
    }

    @Override
    public FilteredRecordsList<ReferenceManifest> getOrderedList(Class<? extends ReferenceManifest> clazz,
                                                                 String columnToOrder, boolean ascending,
                                                                 int firstResult, int maxResults, String search,
                                                                 Map<String, Boolean> searchableColumns,
                                                                 CriteriaModifier<ReferenceManifest> criteriaModifier) throws DBManagerException {
        return null;
    }
}