package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for setProcedureResultResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setProcedureResultResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://korus.ru/tmis/ws/transfusion}issueResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setProcedureResultResponse", propOrder = { "_return" })
public class SetProcedureResultResponse {

    @XmlElement(name = "return")
    protected IssueResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return possible object is {@link IssueResult }
     * 
     */
    public IssueResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *            allowed object is {@link IssueResult }
     * 
     */
    public void setReturn(final IssueResult value) {
        this._return = value;
    }

}
