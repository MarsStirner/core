package ru.korus.tmis.laboratory.bak.model;

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
    public
    PatientInfo patientInfo;

    @XmlElement
    public
    DiagnosticRequestInfo diagnosticRequestInfo;

    @XmlElement
    public BiomaterialInfo biomaterialInfo;

    @XmlElement
    public
    OrderInfo orderInfo;


    public String toXML() {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.marshal(this, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
