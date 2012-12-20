
package org.hl7.v3;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for MCCI_IN000002UV01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MCCI_IN000002UV01">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}MCCI_MT000200UV01.Message">
 *       &lt;attribute name="ITSVersion" type="{http://www.w3.org/2001/XMLSchema}string" fixed="XML_1.0" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MCCI_IN000002UV01")
@XmlRootElement
public class MCCIIN000002UV01
    extends MCCIMT000200UV01Message
{

    @XmlAttribute(name = "ITSVersion")
    protected String itsVersion;

    /**
     * Gets the value of the itsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getITSVersion() {
        if (itsVersion == null) {
            return "XML_1.0";
        } else {
            return itsVersion;
        }
    }

    /**
     * Sets the value of the itsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setITSVersion(String value) {
        this.itsVersion = value;
    }

}
