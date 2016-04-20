
package ru.korus.tmis.laboratory.across.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.ru_novolabs_misexchange_exchangehelpers package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PatientInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "PatientInfo");
    private final static QName _DiagnosticRequestInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "DiagnosticRequestInfo");
    private final static QName _BiomaterialInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "BiomaterialInfo");
    private final static QName _OrderInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "OrderInfo");
    private final static QName _ArrayOfTindicator_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "ArrayOfTindicator");
    private final static QName _Tindicator_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "Tindicator");
    private final static QName _TindicatorIndicatorName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "indicatorName");
    private final static QName _TindicatorIndicatorCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "indicatorCode");
    private final static QName _OrderInfoDiagnosticCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "diagnosticCode");
    private final static QName _OrderInfoDiagnosticName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "diagnosticName");
    private final static QName _OrderInfoIndicators_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "indicators");
    private final static QName _BiomaterialInfoOrderBiomaterialCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderBiomaterialCode");
    private final static QName _BiomaterialInfoOrderBiomaterialName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderBiomaterialName");
    private final static QName _BiomaterialInfoOrderBarCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderBarCode");
    private final static QName _BiomaterialInfoOrderBiomaterialComment_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderBiomaterialComment");
    private final static QName _DiagnosticRequestInfoOrderCaseId_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderCaseId");
    private final static QName _DiagnosticRequestInfoOrderDiagCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDiagCode");
    private final static QName _DiagnosticRequestInfoOrderDiagText_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDiagText");
    private final static QName _DiagnosticRequestInfoOrderComment_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderComment");
    private final static QName _DiagnosticRequestInfoOrderDepartmentName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDepartmentName");
    private final static QName _DiagnosticRequestInfoOrderDepartmentMisId_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDepartmentMisId");
    private final static QName _DiagnosticRequestInfoOrderDoctorFamily_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDoctorFamily");
    private final static QName _DiagnosticRequestInfoOrderDoctorName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDoctorName");
    private final static QName _DiagnosticRequestInfoOrderDoctorPatronum_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDoctorPatronum");
    private final static QName _DiagnosticRequestInfoOrderDoctorMisId_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "orderDoctorMisId");
    private final static QName _PatientInfoPatientFamily_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientFamily");
    private final static QName _PatientInfoPatientName_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientName");
    private final static QName _PatientInfoPatientPatronum_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientPatronum");
    private final static QName _PatientInfoPatientBirthDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientBirthDate");
    private final static QName _PatientInfoPatientSex_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientSex");
    private final static QName _PatientInfoPatientMisId_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "patientMisId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.ru_novolabs_misexchange_exchangehelpers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatientInfo }
     * 
     */
    public PatientInfo createPatientInfo() {
        return new PatientInfo();
    }

    /**
     * Create an instance of {@link DiagnosticRequestInfo }
     * 
     */
    public DiagnosticRequestInfo createDiagnosticRequestInfo() {
        return new DiagnosticRequestInfo();
    }

    /**
     * Create an instance of {@link BiomaterialInfo }
     * 
     */
    public BiomaterialInfo createBiomaterialInfo() {
        return new BiomaterialInfo();
    }

    /**
     * Create an instance of {@link OrderInfo }
     * 
     */
    public OrderInfo createOrderInfo() {
        return new OrderInfo();
    }

    /**
     * Create an instance of {@link ArrayOfTindicator }
     * 
     */
    public ArrayOfTindicator createArrayOfTindicator() {
        return new ArrayOfTindicator();
    }

    /**
     * Create an instance of {@link Tindicator }
     * 
     */
    public Tindicator createTindicator() {
        return new Tindicator();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "PatientInfo")
    public JAXBElement<PatientInfo> createPatientInfo(PatientInfo value) {
        return new JAXBElement<PatientInfo>(_PatientInfo_QNAME, PatientInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiagnosticRequestInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "DiagnosticRequestInfo")
    public JAXBElement<DiagnosticRequestInfo> createDiagnosticRequestInfo(DiagnosticRequestInfo value) {
        return new JAXBElement<DiagnosticRequestInfo>(_DiagnosticRequestInfo_QNAME, DiagnosticRequestInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BiomaterialInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "BiomaterialInfo")
    public JAXBElement<BiomaterialInfo> createBiomaterialInfo(BiomaterialInfo value) {
        return new JAXBElement<BiomaterialInfo>(_BiomaterialInfo_QNAME, BiomaterialInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "OrderInfo")
    public JAXBElement<OrderInfo> createOrderInfo(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_OrderInfo_QNAME, OrderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTindicator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "indicators")
    public JAXBElement<ArrayOfTindicator> createArrayOfTindicator(ArrayOfTindicator value) {
        return new JAXBElement<ArrayOfTindicator>(_ArrayOfTindicator_QNAME, ArrayOfTindicator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Tindicator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "Tindicator")
    public JAXBElement<Tindicator> createTindicator(Tindicator value) {
        return new JAXBElement<Tindicator>(_Tindicator_QNAME, Tindicator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "indicatorName", scope = Tindicator.class)
    public JAXBElement<String> createTindicatorIndicatorName(String value) {
        return new JAXBElement<String>(_TindicatorIndicatorName_QNAME, String.class, Tindicator.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "indicatorCode", scope = Tindicator.class)
    public JAXBElement<String> createTindicatorIndicatorCode(String value) {
        return new JAXBElement<String>(_TindicatorIndicatorCode_QNAME, String.class, Tindicator.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "diagnosticCode", scope = OrderInfo.class)
    public JAXBElement<String> createOrderInfoDiagnosticCode(String value) {
        return new JAXBElement<String>(_OrderInfoDiagnosticCode_QNAME, String.class, OrderInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "diagnosticName", scope = OrderInfo.class)
    public JAXBElement<String> createOrderInfoDiagnosticName(String value) {
        return new JAXBElement<String>(_OrderInfoDiagnosticName_QNAME, String.class, OrderInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTindicator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "indicators", scope = OrderInfo.class)
    public JAXBElement<ArrayOfTindicator> createOrderInfoIndicators(ArrayOfTindicator value) {
        return new JAXBElement<ArrayOfTindicator>(_OrderInfoIndicators_QNAME, ArrayOfTindicator.class, OrderInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderBiomaterialCode", scope = BiomaterialInfo.class)
    public JAXBElement<String> createBiomaterialInfoOrderBiomaterialCode(String value) {
        return new JAXBElement<String>(_BiomaterialInfoOrderBiomaterialCode_QNAME, String.class, BiomaterialInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderBiomaterialName", scope = BiomaterialInfo.class)
    public JAXBElement<String> createBiomaterialInfoOrderBiomaterialName(String value) {
        return new JAXBElement<String>(_BiomaterialInfoOrderBiomaterialName_QNAME, String.class, BiomaterialInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderBarCode", scope = BiomaterialInfo.class)
    public JAXBElement<String> createBiomaterialInfoOrderBarCode(String value) {
        return new JAXBElement<String>(_BiomaterialInfoOrderBarCode_QNAME, String.class, BiomaterialInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderBiomaterialComment", scope = BiomaterialInfo.class)
    public JAXBElement<String> createBiomaterialInfoOrderBiomaterialComment(String value) {
        return new JAXBElement<String>(_BiomaterialInfoOrderBiomaterialComment_QNAME, String.class, BiomaterialInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderCaseId", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderCaseId(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderCaseId_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDiagCode", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDiagCode(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDiagCode_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDiagText", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDiagText(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDiagText_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderComment", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderComment(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderComment_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDepartmentName", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDepartmentName(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDepartmentName_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDepartmentMisId", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDepartmentMisId(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDepartmentMisId_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDoctorFamily", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDoctorFamily(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDoctorFamily_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDoctorName", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDoctorName(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDoctorName_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDoctorPatronum", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDoctorPatronum(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDoctorPatronum_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "orderDoctorMisId", scope = DiagnosticRequestInfo.class)
    public JAXBElement<String> createDiagnosticRequestInfoOrderDoctorMisId(String value) {
        return new JAXBElement<String>(_DiagnosticRequestInfoOrderDoctorMisId_QNAME, String.class, DiagnosticRequestInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientMisId", scope = PatientInfo.class)
    public JAXBElement<Integer> createPatientInfoPatientMisId(Integer value) {
        return new JAXBElement<Integer>(_PatientInfoPatientMisId_QNAME, Integer.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientSex", scope = PatientInfo.class)
    public JAXBElement<Integer> createPatientInfoPatientSex(Integer value) {
        return new JAXBElement<Integer>(_PatientInfoPatientSex_QNAME, Integer.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientFamily", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoPatientFamily(String value) {
        return new JAXBElement<String>(_PatientInfoPatientFamily_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientName", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoPatientName(String value) {
        return new JAXBElement<String>(_PatientInfoPatientName_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientPatronum", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoPatientPatronum(String value) {
        return new JAXBElement<String>(_PatientInfoPatientPatronum_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "patientBirthDate", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoPatientBirthDate(String value) {
        return new JAXBElement<String>(_PatientInfoPatientBirthDate_QNAME, String.class, PatientInfo.class, value);
    }

}
