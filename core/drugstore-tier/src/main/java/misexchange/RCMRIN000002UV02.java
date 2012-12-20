
package misexchange;

import javax.xml.bind.annotation.*;

import org.hl7.v3.RCMRIN000002UV022;


/**
 * <p>Java class for RCMR_IN000002UV02 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RCMR_IN000002UV02">
 *   &lt;complexContent>
 *     &lt;extension base="{MISExchange}Request">
 *       &lt;sequence>
 *         &lt;element name="Message" type="{urn:hl7-org:v3}RCMR_IN000002UV02"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RCMR_IN000002UV02", propOrder = {
    "message"
})
@XmlRootElement
public class RCMRIN000002UV02
    extends Request
{

    @XmlElement(name = "Message", required = true)
    protected RCMRIN000002UV022 message;

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link RCMRIN000002UV022 }
     *     
     */
    public RCMRIN000002UV022 getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link RCMRIN000002UV022 }
     *     
     */
    public void setMessage(RCMRIN000002UV022 value) {
        this.message = value;
    }

}
