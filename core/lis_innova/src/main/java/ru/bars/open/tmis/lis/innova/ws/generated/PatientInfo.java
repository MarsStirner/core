package ru.bars.open.tmis.lis.innova.ws.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PatientInfo complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PatientInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="patientMisId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="patientFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="patientPatronum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="patientBirthDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="patientSex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientInfo", propOrder = {"patientMisId", "patientFamily", "patientName", "patientPatronum", "patientBirthDate", "patientSex"})
public class PatientInfo {

    @XmlElement(name = "patientMisId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer patientMisId;
    @XmlElementRef(name = "patientFamily", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> patientFamily;
    @XmlElementRef(name = "patientName", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> patientName;
    @XmlElementRef(name = "patientPatronum", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> patientPatronum;
    @XmlElementRef(name = "patientBirthDate", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> patientBirthDate;
    @XmlElement(name = "patientSex", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer patientSex;

    /**
     * Gets the value of the patientMisId property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getPatientMisId() {
        return patientMisId;
    }

    /**
     * Sets the value of the patientMisId property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setPatientMisId(Integer value) {
        this.patientMisId = value;
    }

    /**
     * Gets the value of the patientFamily property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getPatientFamily() {
        return patientFamily;
    }

    /**
     * Sets the value of the patientFamily property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setPatientFamily(JAXBElement<String> value) {
        this.patientFamily = value;
    }

    /**
     * Gets the value of the patientName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setPatientName(JAXBElement<String> value) {
        this.patientName = value;
    }

    /**
     * Gets the value of the patientPatronum property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getPatientPatronum() {
        return patientPatronum;
    }

    /**
     * Sets the value of the patientPatronum property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setPatientPatronum(JAXBElement<String> value) {
        this.patientPatronum = value;
    }

    /**
     * Gets the value of the patientBirthDate property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getPatientBirthDate() {
        return patientBirthDate;
    }

    /**
     * Sets the value of the patientBirthDate property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setPatientBirthDate(JAXBElement<String> value) {
        this.patientBirthDate = value;
    }

    /**
     * Gets the value of the patientSex property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getPatientSex() {
        return patientSex;
    }

    /**
     * Sets the value of the patientSex property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setPatientSex(Integer value) {
        this.patientSex = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientInfo{");
        sb.append("patientMisId=").append(patientMisId);
        sb.append(", patientFamily=").append(patientFamily != null ? patientFamily.getValue() : null);
        sb.append(", patientName=").append(patientName !=null ? patientName.getValue() : null);
        sb.append(", patientPatronum=").append(patientPatronum !=null ? patientPatronum.getValue() : null);
        sb.append(", patientBirthDate=").append(patientBirthDate !=null ? patientBirthDate.getValue() : null);
        sb.append(", patientSex=").append(patientSex);
        sb.append('}');
        return sb.toString();
    }
}
