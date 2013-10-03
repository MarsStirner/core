
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ENXP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ENXP">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}XP">
 *       &lt;attribute name="type" type="{urn:hl7-org:v3}EntityNamePartType" />
 *       &lt;attribute name="qualifier" type="{urn:hl7-org:v3}set_EntityNamePartQualifier" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ENXP")
public class ENXP
    extends XP
{

    @XmlAttribute(name = "type")
    protected EntityNamePartType type;
    @XmlAttribute(name = "qualifier")
    protected List<EntityNamePartQualifier> qualifier;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link EntityNamePartType }
     *     
     */
    public EntityNamePartType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityNamePartType }
     *     
     */
    public void setType(EntityNamePartType value) {
        this.type = value;
    }

    /**
     * Gets the value of the qualifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityNamePartQualifier }
     * 
     * 
     */
    public List<EntityNamePartQualifier> getQualifier() {
        if (qualifier == null) {
            qualifier = new ArrayList<EntityNamePartQualifier>();
        }
        return this.qualifier;
    }

}
