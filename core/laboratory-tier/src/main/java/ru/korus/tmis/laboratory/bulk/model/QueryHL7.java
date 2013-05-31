package ru.korus.tmis.laboratory.bulk.model;

import ru.korus.tmis.util.CompileTimeConfigManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

/**
 * Модель запроса в лабораторию
 *
 * @author anosov@outlook.com
 *         date: 5/22/13
 */
@XmlRootElement (
        namespace = CompileTimeConfigManager.Laboratory.Namespace,
        name = "queryAnalysis")
public class QueryHL7 {

    @XmlElement
    private PatientInfo patientInfo;

    @XmlElement
    private DiagnosticRequestInfo diagnosticRequestInfo;

    @XmlElement
    private BiomaterialInfo biomaterialInfo;

    @XmlElement
    private OrderInfo orderInfo;

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public DiagnosticRequestInfo getDiagnosticRequestInfo() {
        return diagnosticRequestInfo;
    }

    public void setDiagnosticRequestInfo(DiagnosticRequestInfo diagnosticRequestInfo) {
        this.diagnosticRequestInfo = diagnosticRequestInfo;
    }

    public BiomaterialInfo getBiomaterialInfo() {
        return biomaterialInfo;
    }

    public void setBiomaterialInfo(BiomaterialInfo biomaterialInfo) {
        this.biomaterialInfo = biomaterialInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String toXML() {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("ru.korus.tmis.laboratory.bulk.model");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.marshal(this, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
