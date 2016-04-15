
package ru.korus.tmis.laboratory.across.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ftmis.integration.novolabs.ru package. 
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
public class ObjectFactory_2 {

    private final static QName _QueryAnalysisPatientInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "PatientInfo");
    private final static QName _QueryAnalysisDiagnosticRequestInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "DiagnosticRequestInfo");
    private final static QName _QueryAnalysisBiomaterialInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "BiomaterialInfo");
    private final static QName _QueryAnalysisOrderInfo_QNAME = new QName("ru.novolabs.Integration.FTMIS", "OrderInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ftmis.integration.novolabs.ru
     * 
     */
    public ObjectFactory_2() {
    }

    /**
     * Create an instance of {@link QueryAnalysis }
     * 
     */
    public QueryAnalysis createQueryAnalysis() {
        return new QueryAnalysis();
    }

    /**
     * Create an instance of {@link QueryAnalysisResponse }
     * 
     */
    public QueryAnalysisResponse createQueryAnalysisResponse() {
        return new QueryAnalysisResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "ru.novolabs.Integration.FTMIS", name = "OrderInfo", scope = QueryAnalysis.class)
    public JAXBElement<OrderInfo> createQueryAnalysisOrderInfo(OrderInfo value) {
        return new JAXBElement<OrderInfo>(_QueryAnalysisOrderInfo_QNAME, OrderInfo.class, QueryAnalysis.class, value);
    }

}
