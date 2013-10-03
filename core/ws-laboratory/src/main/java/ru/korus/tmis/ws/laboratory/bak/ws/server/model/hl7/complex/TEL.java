
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TEL complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TEL">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}ANY">
 *       &lt;sequence>
 *         &lt;element name="useablePeriod" type="{urn:hl7-org:v3}QSET_TS" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="use" type="{urn:hl7-org:v3}set_TelecommunicationAddressUse" />
 *       &lt;attribute name="capabilities" type="{urn:hl7-org:v3}set_TelecommunicationCapability" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEL", propOrder = {
    "useablePeriod"
})
public class TEL
    extends ANY
{

    protected QSETTS useablePeriod;
    @XmlAttribute(name = "value")
    @XmlSchemaType(name = "anyURI")
    protected String value;
    @XmlAttribute(name = "use")
    protected List<TelecommunicationAddressUse> use;
    @XmlAttribute(name = "capabilities")
    protected List<TelecommunicationCapability> capabilities;

    /**
     * Gets the value of the useablePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTS }
     *     
     */
    public QSETTS getUseablePeriod() {
        return useablePeriod;
    }

    /**
     * Sets the value of the useablePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTS }
     *     
     */
    public void setUseablePeriod(QSETTS value) {
        this.useablePeriod = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the use property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the use property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelecommunicationAddressUse }
     * 
     * 
     */
    public List<TelecommunicationAddressUse> getUse() {
        if (use == null) {
            use = new ArrayList<TelecommunicationAddressUse>();
        }
        return this.use;
    }

    /**
     * Gets the value of the capabilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capabilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapabilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelecommunicationCapability }
     * 
     * 
     */
    public List<TelecommunicationCapability> getCapabilities() {
        if (capabilities == null) {
            capabilities = new ArrayList<TelecommunicationCapability>();
        }
        return this.capabilities;
    }

}
