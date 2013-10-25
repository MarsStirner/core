package ru.korus.tmis.laboratory.bak.ws.server.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 12:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class BakPosev {

    private long actionId;

    private List<Microorganism> microorganismList;


    public BakPosev(long actionId, List<Microorganism> microorganismList) {
        this.actionId = actionId;
        this.microorganismList = microorganismList;
    }


    public List<Microorganism> getMicroorganismList() {
        return microorganismList;
    }


    public void addAntibioticToOrganism(String codeMicroOrg, Antibiotic antibiotic) {
        for (Microorganism microorganism : microorganismList) {
            if (microorganism.getCode().equals(codeMicroOrg)) {
                microorganism.addAntibiotic(antibiotic);
                break;
            }
        }
    }
}
