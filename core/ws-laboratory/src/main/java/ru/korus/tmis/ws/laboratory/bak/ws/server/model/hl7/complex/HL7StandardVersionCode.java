
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HL7StandardVersionCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HL7StandardVersionCode">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="Ballot2008Jan"/>
 *     &lt;enumeration value="Ballot2008May"/>
 *     &lt;enumeration value="Ballot2008Sep"/>
 *     &lt;enumeration value="Ballot2009Jan"/>
 *     &lt;enumeration value="Ballot2009May"/>
 *     &lt;enumeration value="Ballot2009Sep"/>
 *     &lt;enumeration value="Ballot2010Jan"/>
 *     &lt;enumeration value="Ballot2010May"/>
 *     &lt;enumeration value="Ballot2010Sep"/>
 *     &lt;enumeration value="Ballot2011Jan"/>
 *     &lt;enumeration value="Ballot2011May"/>
 *     &lt;enumeration value="Ballot2011Sep"/>
 *     &lt;enumeration value="Ballot2012Jan"/>
 *     &lt;enumeration value="Ballot2012May"/>
 *     &lt;enumeration value="Ballot2012Sep"/>
 *     &lt;enumeration value="V3-2003-12"/>
 *     &lt;enumeration value="V3-2005N"/>
 *     &lt;enumeration value="V3-2006N"/>
 *     &lt;enumeration value="V3-2008N"/>
 *     &lt;enumeration value="V3-2009N"/>
 *     &lt;enumeration value="V3-2010N"/>
 *     &lt;enumeration value="V3-2011N"/>
 *     &lt;enumeration value="V3-2012N"/>
 *     &lt;enumeration value="V3PR1"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HL7StandardVersionCode")
@XmlEnum
public enum HL7StandardVersionCode {

    @XmlEnumValue("Ballot2008Jan")
    BALLOT_2008_JAN("Ballot2008Jan"),
    @XmlEnumValue("Ballot2008May")
    BALLOT_2008_MAY("Ballot2008May"),
    @XmlEnumValue("Ballot2008Sep")
    BALLOT_2008_SEP("Ballot2008Sep"),
    @XmlEnumValue("Ballot2009Jan")
    BALLOT_2009_JAN("Ballot2009Jan"),
    @XmlEnumValue("Ballot2009May")
    BALLOT_2009_MAY("Ballot2009May"),
    @XmlEnumValue("Ballot2009Sep")
    BALLOT_2009_SEP("Ballot2009Sep"),
    @XmlEnumValue("Ballot2010Jan")
    BALLOT_2010_JAN("Ballot2010Jan"),
    @XmlEnumValue("Ballot2010May")
    BALLOT_2010_MAY("Ballot2010May"),
    @XmlEnumValue("Ballot2010Sep")
    BALLOT_2010_SEP("Ballot2010Sep"),
    @XmlEnumValue("Ballot2011Jan")
    BALLOT_2011_JAN("Ballot2011Jan"),
    @XmlEnumValue("Ballot2011May")
    BALLOT_2011_MAY("Ballot2011May"),
    @XmlEnumValue("Ballot2011Sep")
    BALLOT_2011_SEP("Ballot2011Sep"),
    @XmlEnumValue("Ballot2012Jan")
    BALLOT_2012_JAN("Ballot2012Jan"),
    @XmlEnumValue("Ballot2012May")
    BALLOT_2012_MAY("Ballot2012May"),
    @XmlEnumValue("Ballot2012Sep")
    BALLOT_2012_SEP("Ballot2012Sep"),
    @XmlEnumValue("V3-2003-12")
    V_3_2003_12("V3-2003-12"),
    @XmlEnumValue("V3-2005N")
    V_3_2005_N("V3-2005N"),
    @XmlEnumValue("V3-2006N")
    V_3_2006_N("V3-2006N"),
    @XmlEnumValue("V3-2008N")
    V_3_2008_N("V3-2008N"),
    @XmlEnumValue("V3-2009N")
    V_3_2009_N("V3-2009N"),
    @XmlEnumValue("V3-2010N")
    V_3_2010_N("V3-2010N"),
    @XmlEnumValue("V3-2011N")
    V_3_2011_N("V3-2011N"),
    @XmlEnumValue("V3-2012N")
    V_3_2012_N("V3-2012N"),
    @XmlEnumValue("V3PR1")
    V_3_PR_1("V3PR1");
    private final String value;

    HL7StandardVersionCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HL7StandardVersionCode fromValue(String v) {
        for (HL7StandardVersionCode c: HL7StandardVersionCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
