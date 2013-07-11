
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Administration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Administration">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="AdministrationNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdministeringProvider" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="AdministeredAtLocation" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="AdministrationStatus" type="{}CodeTableDetail" minOccurs="0"/>
 *         &lt;element name="AdministeredAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdministeredUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LotNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExpiryDate" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ManufacturerName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Administration", propOrder = {
    "administrationNotes",
    "administeringProvider",
    "administeredAtLocation",
    "administrationStatus",
    "administeredAmount",
    "administeredUnits",
    "lotNumber",
    "expiryDate",
    "manufacturerName"
})
public class Administration
    extends SuperClass
{

    @XmlElement(name = "AdministrationNotes")
    protected String administrationNotes;
    @XmlElement(name = "AdministeringProvider")
    protected CareProvider administeringProvider;
    @XmlElement(name = "AdministeredAtLocation")
    protected HealthCareFacility administeredAtLocation;
    @XmlElement(name = "AdministrationStatus")
    protected CodeTableDetail administrationStatus;
    @XmlElement(name = "AdministeredAmount")
    protected String administeredAmount;
    @XmlElement(name = "AdministeredUnits")
    protected String administeredUnits;
    @XmlElement(name = "LotNumber")
    protected String lotNumber;
    @XmlElement(name = "ExpiryDate")
    protected XMLGregorianCalendar expiryDate;
    @XmlElement(name = "ManufacturerName")
    protected String manufacturerName;

    /**
     * Gets the value of the administrationNotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrationNotes() {
        return administrationNotes;
    }

    /**
     * Sets the value of the administrationNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrationNotes(String value) {
        this.administrationNotes = value;
    }

    /**
     * Gets the value of the administeringProvider property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getAdministeringProvider() {
        return administeringProvider;
    }

    /**
     * Sets the value of the administeringProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setAdministeringProvider(CareProvider value) {
        this.administeringProvider = value;
    }

    /**
     * Gets the value of the administeredAtLocation property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getAdministeredAtLocation() {
        return administeredAtLocation;
    }

    /**
     * Sets the value of the administeredAtLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setAdministeredAtLocation(HealthCareFacility value) {
        this.administeredAtLocation = value;
    }

    /**
     * Gets the value of the administrationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CodeTableDetail }
     *     
     */
    public CodeTableDetail getAdministrationStatus() {
        return administrationStatus;
    }

    /**
     * Sets the value of the administrationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeTableDetail }
     *     
     */
    public void setAdministrationStatus(CodeTableDetail value) {
        this.administrationStatus = value;
    }

    /**
     * Gets the value of the administeredAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministeredAmount() {
        return administeredAmount;
    }

    /**
     * Sets the value of the administeredAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministeredAmount(String value) {
        this.administeredAmount = value;
    }

    /**
     * Gets the value of the administeredUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministeredUnits() {
        return administeredUnits;
    }

    /**
     * Sets the value of the administeredUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministeredUnits(String value) {
        this.administeredUnits = value;
    }

    /**
     * Gets the value of the lotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets the value of the lotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotNumber(String value) {
        this.lotNumber = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiryDate(XMLGregorianCalendar value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the manufacturerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Sets the value of the manufacturerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerName(String value) {
        this.manufacturerName = value;
    }

}
