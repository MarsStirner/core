
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseSerial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseSerial")
@XmlSeeAlso({
    Privilege.class,
    Occupation.class,
    HistoryEntry.class,
    IdentityDocument.class,
    Trustee.class,
    DispensarySupervision.class,
    Admission.class,
    Insurance.class,
    ContainerDocument.class,
    Name.class,
    CodeAndName.class,
    PrivilegeDocument.class,
    Address.class,
    ContactInfo.class
})
public class BaseSerial {


}
