package ru.korus.tmis.core.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.EventLocalContract;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.03.2015, 18:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayerInfo {

    private String firstName;

    private String lastName;

    private String middleName;

    private Date birthDate;

    private Integer documentType;

    private String documentSeriesLeft;

    private String documentSeriesRight;

    private String documentNumber;

    private String address;

    private Integer company;

    private String companyName;

    public PayerInfo() {
    }

    public PayerInfo(EventLocalContract eventLocalContract) {

        firstName = eventLocalContract.getFirstName();

        lastName = eventLocalContract.getLastName();

        middleName = eventLocalContract.getPatrName();

        birthDate = eventLocalContract.getBirthDate();

        documentType = eventLocalContract.getRbDocumentType() == null ? null : eventLocalContract.getRbDocumentType().getId();

        documentSeriesLeft = eventLocalContract.getSerialLeft();

        documentSeriesRight = eventLocalContract.getSerialRight();

        documentNumber = eventLocalContract.getNumber();

        address = eventLocalContract.getRegAddress();

        company = eventLocalContract.getOrganisation() == null ? null :  eventLocalContract.getOrganisation().getId();

        companyName = eventLocalContract.getOrganisation() == null ? null :  eventLocalContract.getOrganisation().getShortName();

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getDocumentSeriesLeft() {
        return documentSeriesLeft;
    }

    public void setDocumentSeriesLeft(String documentSeriesLeft) {
        this.documentSeriesLeft = documentSeriesLeft;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getDocumentSeriesRight() {
        return documentSeriesRight;
    }

    public void setDocumentSeriesRight(String documentSeriesRight) {
        this.documentSeriesRight = documentSeriesRight;
    }

}

