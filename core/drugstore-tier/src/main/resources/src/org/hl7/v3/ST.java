
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ST complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ST">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:hl7-org:v3}ED">
 *       &lt;attribute name="representation" type="{urn:hl7-org:v3}BinaryDataEncoding" fixed="TXT" />
 *       &lt;attribute name="mediaType" type="{urn:hl7-org:v3}cs" fixed="text/plain" />
 *       &lt;attribute name="language" type="{urn:hl7-org:v3}cs" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ST")
@XmlSeeAlso({
    SC.class,
    ENXP.class,
    ADXP.class
})
public class ST
    extends ED
{


}
