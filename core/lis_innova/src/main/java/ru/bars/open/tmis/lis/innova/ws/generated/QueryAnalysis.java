package ru.bars.open.tmis.lis.innova.ws.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PatientInfo" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}PatientInfo" minOccurs="0"/&gt;
 *         &lt;element name="DiagnosticRequestInfo" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}DiagnosticRequestInfo" minOccurs="0"/&gt;
 *         &lt;element name="BiomaterialInfo" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}BiomaterialInfo" minOccurs="0"/&gt;
 *         &lt;element name="OrderInfoList" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}ArrayOfOrderInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "patientInfo",
    "diagnosticRequestInfo",
    "biomaterialInfo",
    "orderInfoList"
})
@XmlRootElement(name = "queryAnalysis", namespace = "ru.novolabs.Integration.FTMIS")
public class QueryAnalysis {

    @XmlElementRef(name = "PatientInfo", namespace = "ru.novolabs.Integration.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<PatientInfo> patientInfo;
    @XmlElementRef(name = "DiagnosticRequestInfo", namespace = "ru.novolabs.Integration.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<DiagnosticRequestInfo> diagnosticRequestInfo;
    @XmlElementRef(name = "BiomaterialInfo", namespace = "ru.novolabs.Integration.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<BiomaterialInfo> biomaterialInfo;
    @XmlElementRef(name = "OrderInfoList", namespace = "ru.novolabs.Integration.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfOrderInfo> orderInfoList;

    /**
     * Gets the value of the patientInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PatientInfo }{@code >}
     *     
     */
    public JAXBElement<PatientInfo> getPatientInfo() {
        return patientInfo;
    }

    /**
     * Sets the value of the patientInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PatientInfo }{@code >}
     *     
     */
    public void setPatientInfo(JAXBElement<PatientInfo> value) {
        this.patientInfo = value;
    }

    /**
     * Gets the value of the diagnosticRequestInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DiagnosticRequestInfo }{@code >}
     *     
     */
    public JAXBElement<DiagnosticRequestInfo> getDiagnosticRequestInfo() {
        return diagnosticRequestInfo;
    }

    /**
     * Sets the value of the diagnosticRequestInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DiagnosticRequestInfo }{@code >}
     *     
     */
    public void setDiagnosticRequestInfo(JAXBElement<DiagnosticRequestInfo> value) {
        this.diagnosticRequestInfo = value;
    }

    /**
     * Gets the value of the biomaterialInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BiomaterialInfo }{@code >}
     *     
     */
    public JAXBElement<BiomaterialInfo> getBiomaterialInfo() {
        return biomaterialInfo;
    }

    /**
     * Sets the value of the biomaterialInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BiomaterialInfo }{@code >}
     *     
     */
    public void setBiomaterialInfo(JAXBElement<BiomaterialInfo> value) {
        this.biomaterialInfo = value;
    }

    /**
     * Gets the value of the orderInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}
     *     
     */
    public JAXBElement<ArrayOfOrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    /**
     * Sets the value of the orderInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}
     *     
     */
    public void setOrderInfoList(JAXBElement<ArrayOfOrderInfo> value) {
        this.orderInfoList = value;
    }

}
