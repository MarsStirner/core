package ru.korus.tmis.core.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.EventLocalContract;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.03.2015, 18:28 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentContractInfo {

    private String number;

    private Date date;

    public PaymentContractInfo() {
    }

    public PaymentContractInfo(EventLocalContract eventLocalContract) {

        number = eventLocalContract.getNumber();

        date = eventLocalContract.getDateContract();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
