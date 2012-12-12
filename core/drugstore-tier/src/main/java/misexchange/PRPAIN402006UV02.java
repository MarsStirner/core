
package misexchange;

import javax.xml.bind.annotation.*;

import org.hl7.v3.PRPAIN402006UV022;


/**
 * <p>Java class for PRPA_IN402006UV02 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PRPA_IN402006UV02">
 *   &lt;complexContent>
 *     &lt;extension base="{MISExchange}Request">
 *       &lt;sequence>
 *         &lt;element name="Message" type="{urn:hl7-org:v3}PRPA_IN402006UV02"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PRPA_IN402006UV02", propOrder = {
    "message"
})
@XmlRootElement
public class PRPAIN402006UV02
    extends Request
{

    @XmlElement(name = "Message", required = true)
    protected PRPAIN402006UV022 message;

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link PRPAIN402006UV022 }
     *     
     */
    public PRPAIN402006UV022 getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRPAIN402006UV022 }
     *     
     */
    public void setMessage(PRPAIN402006UV022 value) {
        this.message = value;
    }

}
