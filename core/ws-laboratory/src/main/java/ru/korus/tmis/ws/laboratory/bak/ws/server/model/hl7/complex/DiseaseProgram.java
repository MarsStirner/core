
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseProgram.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiseaseProgram">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="DISEASEPRG"/>
 *     &lt;enumeration value="HIVAIDS"/>
 *     &lt;enumeration value="CANPRG"/>
 *     &lt;enumeration value="ENDRENAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DiseaseProgram")
@XmlEnum
public enum DiseaseProgram {

    DISEASEPRG,
    HIVAIDS,
    CANPRG,
    ENDRENAL;

    public String value() {
        return name();
    }

    public static DiseaseProgram fromValue(String v) {
        return valueOf(v);
    }

}