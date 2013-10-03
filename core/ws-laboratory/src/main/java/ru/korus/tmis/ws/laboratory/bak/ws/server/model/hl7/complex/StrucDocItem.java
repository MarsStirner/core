
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Item complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.Item">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.Captioned">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="footnote" type="{urn:hl7-org:v3}StrucDoc.Footnote"/>
 *           &lt;element name="footnoteRef" type="{urn:hl7-org:v3}StrucDoc.FootnoteRef"/>
 *           &lt;element name="linkHtml" type="{urn:hl7-org:v3}StrucDoc.LinkHtml"/>
 *           &lt;element name="sub" type="{urn:hl7-org:v3}StrucDoc.Sub"/>
 *           &lt;element name="sup" type="{urn:hl7-org:v3}StrucDoc.Sup"/>
 *           &lt;element name="content" type="{urn:hl7-org:v3}StrucDoc.Content"/>
 *           &lt;element name="br" type="{urn:hl7-org:v3}StrucDoc.Br"/>
 *           &lt;element name="renderMultiMedia" type="{urn:hl7-org:v3}StrucDoc.RenderMultiMedia"/>
 *           &lt;element name="paragraph" type="{urn:hl7-org:v3}StrucDoc.Paragraph"/>
 *           &lt;element name="list" type="{urn:hl7-org:v3}StrucDoc.List"/>
 *           &lt;element name="table" type="{urn:hl7-org:v3}StrucDoc.Table"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.Item")
public class StrucDocItem
    extends StrucDocCaptioned
{


}
