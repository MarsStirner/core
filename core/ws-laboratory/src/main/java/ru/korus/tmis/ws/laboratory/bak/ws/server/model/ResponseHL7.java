package ru.korus.tmis.ws.laboratory.bak.ws.server.model;

import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.CE;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.II;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.ST;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.SXCMTS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

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
        "confidentialityCode"
})
public class ResponseHL7 {

    protected II typeId;
    protected II id;
    protected CE code;
    protected ST title;
    protected SXCMTS effectiveTime;
    protected CE confidentialityCode;

}
