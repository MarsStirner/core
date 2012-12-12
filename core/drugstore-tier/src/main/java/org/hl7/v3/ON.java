
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ON complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ON">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:hl7-org:v3}EN">
 *       &lt;choice>
 *         &lt;element name="delimiter" type="{urn:hl7-org:v3}en.delimiter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prefix" type="{urn:hl7-org:v3}en.prefix" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="suffix" type="{urn:hl7-org:v3}en.suffix" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="validTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="use" type="{urn:hl7-org:v3}set_EntityNameUse" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ON")
public class ON
    extends EN
{


}
