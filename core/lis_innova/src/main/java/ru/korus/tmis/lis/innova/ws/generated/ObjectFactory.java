
package ru.korus.tmis.lis.innova.ws.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.tmis.lis.innova.ws.generated package. 
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

    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _PatientInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "PatientInfo");
    private final static QName _DiagnosticRequestInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "DiagnosticRequestInfo");
    private final static QName _BiomaterialInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "BiomaterialInfo");
    private final static QName _ArrayOfOrderInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "ArrayOfOrderInfo");
    private final static QName _OrderInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "OrderInfo");
    private final static QName _ArrayOfTindicator_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "ArrayOfTindicator");
    private final static QName _Tindicator_QNAME = new QName("http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", "Tindicator");
    private final static QName _QueryAnalysisPatientInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "PatientInfo");
    private final static QName _QueryAnalysisDiagnosticRequestInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "DiagnosticRequestInfo");
    private final static QName _QueryAnalysisBiomaterialInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "BiomaterialInfo");
    private final static QName _QueryAnalysisOrderInfoList_QNAME = new QName("ru.novolabs.Integration.FTMIS", "OrderInfoList");
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

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.lis.innova.ws.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryAnalysis }
     * 
     */
    public QueryAnalysis createQueryAnalysis() {
        return new QueryAnalysis();
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
     * Create an instance of {@link ArrayOfOrderInfo }
     * 
     */
    public ArrayOfOrderInfo createArrayOfOrderInfo() {
        return new ArrayOfOrderInfo();
    }

    /**
     * Create an instance of {@link QueryAnalysisResponse }
     * 
     */
    public QueryAnalysisResponse createQueryAnalysisResponse() {
        return new QueryAnalysisResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "ArrayOfOrderInfo")
    public JAXBElement<ArrayOfOrderInfo> createArrayOfOrderInfo(ArrayOfOrderInfo value) {
        return new JAXBElement<ArrayOfOrderInfo>(_ArrayOfOrderInfo_QNAME, ArrayOfOrderInfo.class, null, value);
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
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", name = "ArrayOfTindicator")
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
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "ru.novolabs.Integration.FTMIS", name = "PatientInfo", scope = QueryAnalysis.class)
    public JAXBElement<PatientInfo> createQueryAnalysisPatientInfo(PatientInfo value) {
        return new JAXBElement<PatientInfo>(_QueryAnalysisPatientInfo_QNAME, PatientInfo.class, QueryAnalysis.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiagnosticRequestInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "ru.novolabs.Integration.FTMIS", name = "DiagnosticRequestInfo", scope = QueryAnalysis.class)
    public JAXBElement<DiagnosticRequestInfo> createQueryAnalysisDiagnosticRequestInfo(DiagnosticRequestInfo value) {
        return new JAXBElement<DiagnosticRequestInfo>(_QueryAnalysisDiagnosticRequestInfo_QNAME, DiagnosticRequestInfo.class, QueryAnalysis.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BiomaterialInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "ru.novolabs.Integration.FTMIS", name = "BiomaterialInfo", scope = QueryAnalysis.class)
    public JAXBElement<BiomaterialInfo> createQueryAnalysisBiomaterialInfo(BiomaterialInfo value) {
        return new JAXBElement<BiomaterialInfo>(_QueryAnalysisBiomaterialInfo_QNAME, BiomaterialInfo.class, QueryAnalysis.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfOrderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "ru.novolabs.Integration.FTMIS", name = "OrderInfoList", scope = QueryAnalysis.class)
    public JAXBElement<ArrayOfOrderInfo> createQueryAnalysisOrderInfoList(ArrayOfOrderInfo value) {
        return new JAXBElement<ArrayOfOrderInfo>(_QueryAnalysisOrderInfoList_QNAME, ArrayOfOrderInfo.class, QueryAnalysis.class, value);
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
