
package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResult", propOrder = {"resultCode", "requestId", "resultInfo" } )
public class OrderResult {
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final int RESULT_CODE_ALREADY_PROCESSED = 1;
    public static final int RESULT_CODE_WRONG_DATA = 2;
    public static final int RESULT_CODE_INTERNAL_ERROR = 3;


    @XmlElement(name = "requestId")
    private Integer requestId;
    @XmlElement(name = "resultCode")
    private Integer resultCode;
    @XmlElement(name = "resultInfo")
    private String resultInfo;


    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(final Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(final String resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderResult{");
        sb.append("requestId=").append(requestId);
        sb.append(", resultCode=").append(resultCode);
        sb.append(", resultInfo='").append(resultInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
