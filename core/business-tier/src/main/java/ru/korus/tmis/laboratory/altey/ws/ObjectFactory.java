
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.ws.laboratory package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.ws.laboratory
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
     * Create an instance of {@link ru.korus.tmis.laboratory.altey.ws.QueryAnalysisResponse }
     *
     */
    public QueryAnalysisResponse createQueryAnalysisResponse() {
        return new QueryAnalysisResponse();
    }

    /**
     * Create an instance of {@link ru.korus.tmis.laboratory.altey.ws.PatientInfo }
     *
     */
    public PatientInfo createPatientInfo() {
        return new PatientInfo();
    }

    /**
     * Create an instance of {@link OrderInfo }
     *
     */
    public OrderInfo createOrderInfo() {
        return new OrderInfo();
    }

    /**
     * Create an instance of {@link ru.korus.tmis.laboratory.altey.ws.DiagnosticRequestInfo }
     *
     */
    public DiagnosticRequestInfo createDiagnosticRequestInfo() {
        return new DiagnosticRequestInfo();
    }

    /**
     * Create an instance of {@link ru.korus.tmis.laboratory.altey.ws.BiomaterialInfo }
     *
     */
    public BiomaterialInfo createBiomaterialInfo() {
        return new BiomaterialInfo();
    }

    /**
     * Create an instance of {@link ru.korus.tmis.laboratory.altey.ws.IndicatorMetodic }
     * 
     */
    public IndicatorMetodic createIndicatorMetodic() {
        return new IndicatorMetodic();
    }

}
