package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for setOrderIssueResult complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setOrderIssueResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/>
 *         &lt;element name="factDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" form="qualified"/>
 *         &lt;element name="components" type="{http://korus.ru/tmis/ws/transfusion}orderIssueInfo" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *         &lt;element name="orderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setOrderIssueResult", propOrder = { "requestId", "factDate", "components", "orderComment" })
public class SetOrderIssueResult {

    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    protected Integer requestId;
    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar factDate;
    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    protected List<OrderIssueInfo> components;
    @XmlElement(namespace = "http://korus.ru/tmis/ws/transfusion")
    protected String orderComment;

    /**
     * Gets the value of the requestId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setRequestId(final Integer value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the factDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getFactDate() {
        return factDate;
    }

    /**
     * Sets the value of the factDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setFactDate(final XMLGregorianCalendar value) {
        this.factDate = value;
    }

    /**
     * Gets the value of the components property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
     * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the components property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getComponents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link OrderIssueInfo }
     * 
     * 
     */
    public List<OrderIssueInfo> getComponents() {
        if (components == null) {
            components = new ArrayList<OrderIssueInfo>();
        }
        return this.components;
    }

    /**
     * Gets the value of the orderComment property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOrderComment() {
        return orderComment;
    }

    /**
     * Sets the value of the orderComment property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOrderComment(final String value) {
        this.orderComment = value;
    }

}
