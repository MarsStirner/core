
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ANY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ANY">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}HXIT">
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="flavorId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="updateMode" type="{urn:hl7-org:v3}UpdateMode" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ANY")
@XmlSeeAlso({
    II.class,
    CS.class,
    SLISTPQ.class,
    QSETREAL.class,
    NPPDTS.class,
    SLISTINT.class,
    NPPDST.class,
    SLISTTS.class,
    NPPDREAL.class,
    UVPINT.class,
    UVPREAL.class,
    TEL.class,
    NPPDRTO.class,
    UVPRTO.class,
    COLLINT.class,
    ED.class,
    EN.class,
    SLISTREAL.class,
    QSETMO.class,
    AD.class,
    BL.class,
    CD.class,
    QSETQTY.class,
    NPPDTEL.class,
    ST.class,
    QSETCO.class,
    NPPDMO.class,
    UVPSC.class,
    NPPDINT.class,
    UVPST.class,
    QSETINT.class,
    UVPTS.class,
    NPPDSC.class,
    UVPMO.class,
    NPPDPQ.class,
    UVPPQ.class,
    NPPDEN.class,
    COLLTEL.class,
    NPPDED.class,
    QSETPQ.class,
    NPPDCO.class,
    NPPDCS.class,
    NPPDCD.class,
    NPPDII.class,
    COLLST.class,
    COLLSC.class,
    COLLTS.class,
    UVPBL.class,
    QSETTS.class,
    UVPAD.class,
    UVPCS.class,
    COLLPQ.class,
    UVPCD.class,
    COLLRTO.class,
    UVPCO.class,
    NPPDAD.class,
    NPPDBL.class,
    COLLMO.class,
    GLISTREAL.class,
    UVPII.class,
    UVPTEL.class,
    COLLII.class,
    COLLAD.class,
    GLISTTS.class,
    UVPEN.class,
    COLLED.class,
    COLLEN.class,
    UVPED.class,
    COLLREAL.class,
    QTY.class,
    GLISTPQ.class,
    COLLCO.class,
    COLLCD.class,
    COLLCS.class,
    COLLBL.class
})
public class ANY
    extends HXIT
{

    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "flavorId")
    protected String flavorId;
    @XmlAttribute(name = "updateMode")
    protected UpdateMode updateMode;

    /**
     * Gets the value of the nullFlavor property.
     * 
     * @return
     *     possible object is
     *     {@link NullFlavor }
     *     
     */
    public NullFlavor getNullFlavor() {
        return nullFlavor;
    }

    /**
     * Sets the value of the nullFlavor property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullFlavor }
     *     
     */
    public void setNullFlavor(NullFlavor value) {
        this.nullFlavor = value;
    }

    /**
     * Gets the value of the flavorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorId() {
        return flavorId;
    }

    /**
     * Sets the value of the flavorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorId(String value) {
        this.flavorId = value;
    }

    /**
     * Gets the value of the updateMode property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateMode }
     *     
     */
    public UpdateMode getUpdateMode() {
        return updateMode;
    }

    /**
     * Sets the value of the updateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateMode }
     *     
     */
    public void setUpdateMode(UpdateMode value) {
        this.updateMode = value;
    }

}
