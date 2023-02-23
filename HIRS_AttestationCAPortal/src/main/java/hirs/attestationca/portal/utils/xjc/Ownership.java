//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.02.16 at 04:29:40 PM UTC 
//


package hirs.attestationca.portal.utils.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Ownership.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Ownership">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="abandon"/>
 *     &lt;enumeration value="private"/>
 *     &lt;enumeration value="shared"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Ownership", namespace = "http://standards.iso.org/iso/19770/-2/2015/schema.xsd")
@XmlEnum
public enum Ownership {


    /**
     * 
     *             Determines the relative strength of ownership of the target
     *             piece of software.
     *           
     * 
     */
    @XmlEnumValue("abandon")
    ABANDON("abandon"),

    /**
     * 
     *             If this is uninstalled, then the [Link]'d software should be removed
     *             too.
     *           
     * 
     */
    @XmlEnumValue("private")
    PRIVATE("private"),

    /**
     * 
     *             If this is uninstalled, then the [Link]'d software should be removed
     *             if nobody else is sharing it
     *           
     * 
     */
    @XmlEnumValue("shared")
    SHARED("shared");
    private final String value;

    Ownership(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Ownership fromValue(String v) {
        for (Ownership c: Ownership.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}