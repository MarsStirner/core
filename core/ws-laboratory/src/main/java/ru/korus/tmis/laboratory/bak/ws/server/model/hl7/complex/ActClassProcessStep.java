
package ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActClassProcessStep.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActClassProcessStep">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ACSN"/>
 *     &lt;enumeration value="CONTREG"/>
 *     &lt;enumeration value="OBS"/>
 *     &lt;enumeration value="ROIBND"/>
 *     &lt;enumeration value="ROIOVL"/>
 *     &lt;enumeration value="LLD"/>
 *     &lt;enumeration value="PRN"/>
 *     &lt;enumeration value="RLD"/>
 *     &lt;enumeration value="SFWL"/>
 *     &lt;enumeration value="SIT"/>
 *     &lt;enumeration value="STN"/>
 *     &lt;enumeration value="SUP"/>
 *     &lt;enumeration value="RTRD"/>
 *     &lt;enumeration value="TRD"/>
 *     &lt;enumeration value="ALRT"/>
 *     &lt;enumeration value="BATTERY"/>
 *     &lt;enumeration value="CLNTRL"/>
 *     &lt;enumeration value="CNOD"/>
 *     &lt;enumeration value="CONC"/>
 *     &lt;enumeration value="COND"/>
 *     &lt;enumeration value="CASE"/>
 *     &lt;enumeration value="OUTB"/>
 *     &lt;enumeration value="DGIMG"/>
 *     &lt;enumeration value="GEN"/>
 *     &lt;enumeration value="DETPOL"/>
 *     &lt;enumeration value="EXP"/>
 *     &lt;enumeration value="LOC"/>
 *     &lt;enumeration value="PHN"/>
 *     &lt;enumeration value="POL"/>
 *     &lt;enumeration value="SEQ"/>
 *     &lt;enumeration value="SEQVAR"/>
 *     &lt;enumeration value="INVSTG"/>
 *     &lt;enumeration value="OBSSER"/>
 *     &lt;enumeration value="OBSCOR"/>
 *     &lt;enumeration value="POS"/>
 *     &lt;enumeration value="POSACC"/>
 *     &lt;enumeration value="POSCOORD"/>
 *     &lt;enumeration value="SPCOBS"/>
 *     &lt;enumeration value="VERIF"/>
 *     &lt;enumeration value="PROC"/>
 *     &lt;enumeration value="SBADM"/>
 *     &lt;enumeration value="SBEXT"/>
 *     &lt;enumeration value="SPECCOLLECT"/>
 *     &lt;enumeration value="SPCTRT"/>
 *     &lt;enumeration value="SPECCOLLECT"/>
 *     &lt;enumeration value="STORE"/>
 *     &lt;enumeration value="TRNS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActClassProcessStep")
@XmlEnum
public enum ActClassProcessStep {

    ACSN,
    CONTREG,
    OBS,
    ROIBND,
    ROIOVL,
    LLD,
    PRN,
    RLD,
    SFWL,
    SIT,
    STN,
    SUP,
    RTRD,
    TRD,
    ALRT,
    BATTERY,
    CLNTRL,
    CNOD,
    CONC,
    COND,
    CASE,
    OUTB,
    DGIMG,
    GEN,
    DETPOL,
    EXP,
    LOC,
    PHN,
    POL,
    SEQ,
    SEQVAR,
    INVSTG,
    OBSSER,
    OBSCOR,
    POS,
    POSACC,
    POSCOORD,
    SPCOBS,
    VERIF,
    PROC,
    SBADM,
    SBEXT,
    SPECCOLLECT,
    SPCTRT,
    STORE,
    TRNS;

    public String value() {
        return name();
    }

    public static ActClassProcessStep fromValue(String v) {
        return valueOf(v);
    }

}
