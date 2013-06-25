package ru.korus.tmis.ws.laboratory.bak.ws.server.model;

import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.List;

import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.ROOT_TAG;

/**
 * @author anosov
 *         Date: 22.06.13 19:21
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ROOT_TAG, propOrder = {
        "typeId",
        "id",
        "code",
        "title",
        "effectiveTime",
        "confidentialityCode",
        "recordTarget",
        "author",
        "custodian",
        "componentOf"
})
public class ResponseHL7 {

    protected II typeId;
    protected II id;
    protected CE code;
    protected ST title;
    protected SXCMTS effectiveTime;
    protected CE confidentialityCode;

    @XmlElementRef(name = "recordTarget", namespace = NAMESPACE, type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT530000UVRecordTarget> recordTarget;

    @XmlElement
    protected COCTMT290000UV06Author1 author;

    @XmlElement(required = true, nillable = true)
    protected MFMIMT700701UV01Custodian custodian;

    @XmlElement(nillable = true)
    protected List<COCTMT290000UV06Component1> componentOf;

//    @XmlElementRef(name = "component", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
//    protected JAXBElement<COCTMT290000UV06Component2> component;

}
