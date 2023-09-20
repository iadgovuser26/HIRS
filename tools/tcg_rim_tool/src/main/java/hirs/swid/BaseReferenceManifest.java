package hirs.swid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hirs.swid.ReferenceManifest;
import hirs.swid.SwidResource;
import hirs.swid.SwidTagConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 */
@Log4j2
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BaseReferenceManifest extends ReferenceManifest {
    /**
     * Holds the name of the 'base64Hash' field.
     */
    public static final String BASE_64_HASH_FIELD = "base64Hash";

    private static JAXBContext jaxbContext;

    @Column
    @JsonIgnore
    private String base64Hash = "";
    @Column
    private String swidName = null;
    @Column
    private int swidCorpus = 0;
    @Column
    private String colloquialVersion = null;
    @Column
    private String product = null;
    @Column
    private String revision = null;
    @Column
    private String edition = null;
    @Column
    private String rimLinkHash = null;
    @Column
    private String bindingSpec = null;
    @Column
    private String bindingSpecVersion = null;
    @Column
    private String platformVersion = null;
    @Column
    private String payloadType = null;
    @Column
    private String pcURIGlobal = null;
    @Column
    private String pcURILocal = null;

    private String entityName = null;
    private String entityRegId = null;
    private String entityRole = null;
    private String entityThumbprint = null;
    private String linkHref = null;
    private String linkRel = null;

    /**
     * Support constructor for the RIM object.
     *
     * @param rimBytes - the file content of the uploaded file.
     * @throws IOException - thrown if the file is invalid.
     */
    public BaseReferenceManifest(final byte[] rimBytes) throws IOException {
        this("", rimBytes);
    }

    /**
     * Main constructor for the RIM object. This takes in a byte array of a
     * valid swidtag file and parses the information.
     *
     * @param fileName - string representation of the uploaded file.
     * @param rimBytes byte array representation of the RIM
     * @throws IOException if unable to unmarshal the string
     */
    @SuppressWarnings("checkstyle:AvoidInlineConditionals")
    public BaseReferenceManifest(final String fileName, final byte[] rimBytes) throws IOException {
        super(rimBytes);
        this.setRimType(BASE_RIM);
        this.setFileName(fileName);
        Document document = unmarshallSwidTag(new ByteArrayInputStream(rimBytes));
        Element softwareIdentity;
        Element meta;
        Element entity;
        Element link;

        MessageDigest digest = null;
        this.base64Hash = "";
        try {
            digest = MessageDigest.getInstance("SHA-256");
            this.base64Hash = Base64.getEncoder().encodeToString(
                    digest.digest(rimBytes));
        } catch (NoSuchAlgorithmException noSaEx) {
            log.error(noSaEx);
        }

        // begin parsing valid swid tag
        if (document != null) {
            softwareIdentity = (Element) document.getElementsByTagName(SwidTagConstants.SOFTWARE_IDENTITY).item(0);
            entity = (Element) document.getElementsByTagName(SwidTagConstants.ENTITY).item(0);
            link = (Element) document.getElementsByTagName(SwidTagConstants.LINK).item(0);
            meta = (Element) document.getElementsByTagName(SwidTagConstants.META).item(0);
            setTagId(softwareIdentity.getAttribute(SwidTagConstants.TAGID));
            this.swidName = softwareIdentity.getAttribute(SwidTagConstants.NAME);
            this.swidCorpus = Boolean.parseBoolean(softwareIdentity.getAttribute(SwidTagConstants.CORPUS)) ? 1 : 0;
            this.setSwidPatch(Boolean.parseBoolean(softwareIdentity.getAttribute(SwidTagConstants.PATCH)));
            this.setSwidSupplemental(Boolean.parseBoolean(softwareIdentity.getAttribute(SwidTagConstants.SUPPLEMENTAL)));
            this.setSwidVersion(softwareIdentity.getAttribute(SwidTagConstants.VERSION));
            this.setSwidTagVersion(softwareIdentity.getAttribute(SwidTagConstants.TAGVERSION));

            parseSoftwareMeta(meta);
            parseEntity(entity);
            parseLink(link);
        }
    }

    /**
     * This is a helper method that parses the SoftwareMeta tag and stores the
     * information in the class fields.
     *
     * @param softwareMeta The object to parse.
     */
    private void parseSoftwareMeta(final Element softwareMeta) {
        if (softwareMeta != null) {
            this.colloquialVersion = softwareMeta.getAttribute(SwidTagConstants.COLLOQUIAL_VERSION);
            this.product = softwareMeta.getAttribute(SwidTagConstants.PRODUCT);
            this.revision = softwareMeta.getAttribute(SwidTagConstants.REVISION);
            this.edition = softwareMeta.getAttribute(SwidTagConstants.EDITION);
            this.rimLinkHash = softwareMeta.getAttribute(SwidTagConstants.RIM_LINK_HASH);
            this.bindingSpec = softwareMeta.getAttribute(SwidTagConstants.BINDING_SPEC);
            this.bindingSpecVersion = softwareMeta.getAttribute(SwidTagConstants.BINDING_SPEC_VERSION);
            this.setPlatformManufacturerId(softwareMeta.getAttribute(SwidTagConstants.PLATFORM_MANUFACTURER_ID));
            this.setPlatformManufacturer(softwareMeta.getAttribute(SwidTagConstants.PLATFORM_MANUFACTURER_STR));
            this.setPlatformModel(softwareMeta.getAttribute(SwidTagConstants.PLATFORM_MODEL));
            this.platformVersion = softwareMeta.getAttribute(SwidTagConstants.PLATFORM_VERSION);
            this.payloadType = softwareMeta.getAttribute(SwidTagConstants.PAYLOAD_TYPE);
            this.pcURIGlobal = softwareMeta.getAttribute(SwidTagConstants.PC_URI_GLOBAL);
            this.pcURILocal = softwareMeta.getAttribute(SwidTagConstants.PC_URI_LOCAL);
        } else {
            log.warn("SoftwareMeta Tag not found.");
        }
    }

    /**
     * This is a helper method that parses the Entity tag and stores the
     * information in the class fields.
     *
     * @param entity The object to parse.
     */
    private void parseEntity(final Element entity) {
        if (entity != null) {
            this.entityName = entity.getAttribute(SwidTagConstants.NAME);
            this.entityRegId = entity.getAttribute(SwidTagConstants.REGID);
            this.entityRole = entity.getAttribute(SwidTagConstants.ROLE);
            this.entityThumbprint = entity.getAttribute(SwidTagConstants.THUMBPRINT);
        } else {
            log.warn("Entity Tag not found.");
        }
    }

    /**
     * This is a helper method that parses the Link tag and stores the
     * information in the class fields.
     *
     * @param link The object to parse.
     */
    private void parseLink(final Element link) {
        if (link != null) {
            this.linkHref = link.getAttribute(SwidTagConstants.HREF);
            this.linkRel = link.getAttribute(SwidTagConstants.REL);
        } else {
            log.warn("Link Tag not found.");
        }
    }

    /**
     * This method validates the .swidtag file at the given filepath against the
     * schema. A successful validation results in the output of the tag's name
     * and tagId attributes, otherwise a generic error message is printed.
     *
     */
    private Element getDirectoryTag() {
        return getDirectoryTag(new ByteArrayInputStream(getRimBytes()));
    }

    /**
     * This method validates the .swidtag file at the given filepath against the
     * schema. A successful validation results in the output of the tag's name
     * and tagId attributes, otherwise a generic error message is printed.
     *
     * @param byteArrayInputStream the location of the file to be validated
     */
    private Element getDirectoryTag(final ByteArrayInputStream byteArrayInputStream) {
        Document document = unmarshallSwidTag(byteArrayInputStream);
        Element softwareIdentity =
                (Element) document.getElementsByTagName("SoftwareIdentity").item(0);
        if (softwareIdentity != null) {
            Element directory = (Element) document.getElementsByTagName("Directory").item(0);

            return directory;
        } else {
            log.error("Invalid xml for validation, please verify ");
        }

        return null;
    }

    /**
     * This method iterates over the list of File elements under the directory.     *
     */
    public List<SwidResource> getFileResources() {
        return getFileResources(getRimBytes());
    }

    /**
     * This method iterates over the list of File elements under the directory.
     *
     * @param rimBytes the bytes to find the files
     *
     */
    public List<SwidResource> getFileResources(final byte[] rimBytes) {
        Element directoryTag = getDirectoryTag(new ByteArrayInputStream(rimBytes));
        List<SwidResource> validHashes = new ArrayList<>();
        NodeList fileNodeList = directoryTag.getChildNodes();
        Element file = null;
        SwidResource swidResource = null;
        for (int i = 0; i < fileNodeList.getLength(); i++) {
            file = (Element) fileNodeList.item(i);
            swidResource = new SwidResource();
            swidResource.setName(file.getAttribute(SwidTagConstants.NAME));
            swidResource.setSize(file.getAttribute(SwidTagConstants.SIZE));
            swidResource.setHashValue(file.getAttribute(SwidTagConstants._SHA256_HASH.getPrefix() + ":"
                    + SwidTagConstants._SHA256_HASH.getLocalPart()));
            validHashes.add(swidResource);
        }

        return validHashes;
    }

    /**
     * This method unmarshalls the swidtag found at [path] into a Document object
     * and validates it according to the schema.
     *
     * @param byteArrayInputStream to the input swidtag
     * @return the Document element at the root of the swidtag
     */
    private Document unmarshallSwidTag(final ByteArrayInputStream byteArrayInputStream) {
        InputStream is = null;
        Document document = null;
        Unmarshaller unmarshaller = null;
        try {
            document = removeXMLWhitespace(byteArrayInputStream);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(SCHEMA_LANGUAGE);
            is = getClass().getClassLoader().getResourceAsStream(SwidTagConstants.SCHEMA_URL);
            Schema schema = schemaFactory.newSchema(new StreamSource(is));
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(SCHEMA_PACKAGE);
            }
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.unmarshal(document);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SAXException e) {
            log.error("Error setting schema for validation!");
        } catch (UnmarshalException e) {
            log.error("Error validating swidtag file!");
        } catch (IllegalArgumentException e) {
            log.error("Input file empty.");
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Error closing input stream");
                }
            }
        }

        return document;
    }

    /**
     * This method strips all whitespace from an xml file, including indents and spaces
     * added for human-readability.
     *
     * @param byteArrayInputStream to the xml file
     * @return Document object without whitespace
     */
    private Document removeXMLWhitespace(final ByteArrayInputStream byteArrayInputStream) throws IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Source source = new StreamSource(
                getClass().getClassLoader().getResourceAsStream("identity_transform.xslt"));
        Document document = null;
        if (byteArrayInputStream.available() > 0) {
            try {
                Transformer transformer = tf.newTransformer(source);
                DOMResult result = new DOMResult();
                transformer.transform(new StreamSource(byteArrayInputStream), result);
                document = (Document) result.getNode();
            } catch (TransformerConfigurationException tcEx) {
                log.error("Error configuring transformer!");
            } catch (TransformerException tEx) {
                log.error("Error transforming input!");
            }
        } else {
            throw new IOException("Input file is empty!");
        }

        return document;
    }

    @Override
    public String toString() {
        return String.format("ReferenceManifest{swidName=%s,"
                        + "platformManufacturer=%s,"
                        + " platformModel=%s,"
                        + "tagId=%s, base64Hash=%s}",
                swidName, this.getPlatformManufacturer(),
                this.getPlatformModel(), getTagId(), this.getBase64Hash());
    }
}