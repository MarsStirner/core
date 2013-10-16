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
        "date",
        "status",
        "comment",
        "microOrgs",
        "results"
})
public class FakeResult {
    @XmlElement(required = true)
    public String orderMISId;

    @XmlElement(required = true)
    public String codeIsl;

    @XmlElement(required = true)
    public String nameIsl;

    @XmlElement(required = true)
    public String barCode;

    @XmlElement(required = true)
    public String doctorId;

    @XmlElement(required = true)
    public String doctorName;

    @XmlElement(required = true)
    public String date;

    @XmlElement(required = true)
    public String status;

    @XmlElement(required = true)
    public String comment;

    @XmlElement(required = true)
    public MicroOrgs microOrgs;

    @XmlElement(required = true)
    public Results results;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderMISId() {
        return orderMISId;
    }

    public void setOrderMISId(String orderMISId) {
        this.orderMISId = orderMISId;
    }

    public String getCodeIsl() {
        return codeIsl;
    }

    public void setCodeIsl(String codeIsl) {
        this.codeIsl = codeIsl;
    }

    public String getNameIsl() {
        return nameIsl;
    }

    public void setNameIsl(String nameIsl) {
        this.nameIsl = nameIsl;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public MicroOrgs getMicroOrgs() {
        return microOrgs;
    }

    public void setMicroOrgs(MicroOrgs microOrgs) {
        this.microOrgs = microOrgs;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FakeResult{");
        sb.append("orderMISId='").append(orderMISId).append('\'');
        sb.append(", codeIsl='").append(codeIsl).append('\'');
        sb.append(", nameIsl='").append(nameIsl).append('\'');
        sb.append(", barCode='").append(barCode).append('\'');
        sb.append(", doctorId='").append(doctorId).append('\'');
        sb.append(", doctorName='").append(doctorName).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", microOrgs=").append(microOrgs);
        sb.append(", results=").append(results);
        sb.append('}');
        return sb.toString();
    }
}

