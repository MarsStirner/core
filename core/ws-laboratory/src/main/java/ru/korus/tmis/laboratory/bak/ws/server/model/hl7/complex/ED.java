
package ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Data that is primarily intended for human interpretation
 * or for further machine processing is outside the scope of
 * HL7. This includes unformatted or formatted written language,
 * multimedia data, or structured information as defined by a
 * different standard (e.g., XML-signatures.)  Instead of the
 * data itself, an ED may contain
 * only a reference (see TEL.) Note
 * that the ST data type is a
 * specialization of
 * when the  is text/plain.
 * <p/>
 * <p/>
 * <p>Java class for ED complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ED">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}BIN">
 *       &lt;sequence>
 *         &lt;element name="reference" type="{urn:hl7-org:v3}TEL" minOccurs="0"/>
 *         &lt;element name="thumbnail" type="{urn:hl7-org:v3}thumbnail" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mediaType" type="{urn:hl7-org:v3}cs" default="text/plain" />
 *       &lt;attribute name="language" type="{urn:hl7-org:v3}cs" />
 *       &lt;attribute name="compression" type="{urn:hl7-org:v3}CompressionAlgorithm" />
 *       &lt;attribute name="integrityCheck" type="{urn:hl7-org:v3}bin" />
 *       &lt;attribute name="integrityCheckAlgorithm" type="{urn:hl7-org:v3}IntegrityCheckAlgorithm" default="SHA-1" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement
@XmlType(name = "ED", propOrder = {
        "reference",
        "thumbnail",
        "content"
})
@XmlSeeAlso({
        Thumbnail.class,
        ST.class
})
public class ED
        extends BIN {


    protected TEL reference;
    protected Thumbnail thumbnail;
//    @XmlAnyElement
    @XmlElement(nillable = true)
    protected List<Object> content;

    @XmlAttribute(name = "mediaType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String mediaType;
    @XmlAttribute(name = "language")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String language;
    @XmlAttribute(name = "compression")
    protected CompressionAlgorithm compression;
    @XmlAttribute(name = "integrityCheck")
    protected byte[] integrityCheck;
    @XmlAttribute(name = "integrityCheckAlgorithm")
    protected IntegrityCheckAlgorithm integrityCheckAlgorithm;

    /**
     * Gets the value of the content property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     *
     *
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }
    /**
     * Gets the value of the reference property.
     *
     * @return possible object is
     *         {@link TEL }
     */
    public TEL getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     *
     * @param value allowed object is
     *              {@link TEL }
     */
    public void setReference(TEL value) {
        this.reference = value;
    }

    /**
     * Gets the value of the thumbnail property.
     *
     * @return possible object is
     *         {@link Thumbnail }
     */
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the value of the thumbnail property.
     *
     * @param value allowed object is
     *              {@link Thumbnail }
     */
    public void setThumbnail(Thumbnail value) {
        this.thumbnail = value;
    }

    /**
     * Gets the value of the mediaType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMediaType() {
        if (mediaType == null) {
            return "text/plain";
        } else {
            return mediaType;
        }
    }

    /**
     * Sets the value of the mediaType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the language property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the compression property.
     *
     * @return possible object is
     *         {@link CompressionAlgorithm }
     */
    public CompressionAlgorithm getCompression() {
        return compression;
    }

    /**
     * Sets the value of the compression property.
     *
     * @param value allowed object is
     *              {@link CompressionAlgorithm }
     */
    public void setCompression(CompressionAlgorithm value) {
        this.compression = value;
    }

    /**
     * Gets the value of the integrityCheck property.
     *
     * @return possible object is
     *         byte[]
     */
    public byte[] getIntegrityCheck() {
        return integrityCheck;
    }

    /**
     * Sets the value of the integrityCheck property.
     *
     * @param value allowed object is
     *              byte[]
     */
    public void setIntegrityCheck(byte[] value) {
        this.integrityCheck = value;
    }

    /**
     * Gets the value of the integrityCheckAlgorithm property.
     *
     * @return possible object is
     *         {@link IntegrityCheckAlgorithm }
     */
    public IntegrityCheckAlgorithm getIntegrityCheckAlgorithm() {
        if (integrityCheckAlgorithm == null) {
            return IntegrityCheckAlgorithm.SHA_1;
        } else {
            return integrityCheckAlgorithm;
        }
    }

    /**
     * Sets the value of the integrityCheckAlgorithm property.
     *
     * @param value allowed object is
     *              {@link IntegrityCheckAlgorithm }
     */
    public void setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm value) {
        this.integrityCheckAlgorithm = value;
    }

}