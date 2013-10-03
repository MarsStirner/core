package ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 04.10.13 0:58
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FakeResult", propOrder = {
        "orderMISId",
        "codeIsl",
        "nameIsl",
        "barCode",
        "doctorId",
        "doctorName",
        "microOrgs",
        "results"
})
public class FakeResult {
    @XmlElement(required = true)
    protected String orderMISId;

    @XmlElement(required = true)
    protected String codeIsl;

    @XmlElement(required = true)
    protected String nameIsl;

    @XmlElement(required = true)
    protected String barCode;

    @XmlElement(required = true)
    protected String doctorId;

    @XmlElement(required = true)
    protected String doctorName;

    @XmlElement(required = true)
    protected MicroOrgs microOrgs;

    @XmlElement(required = true)
    protected Results results;


}
