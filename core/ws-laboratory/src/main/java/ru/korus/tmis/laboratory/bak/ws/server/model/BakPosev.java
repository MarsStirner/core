package ru.korus.tmis.laboratory.bak.ws.server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 12:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Модель результата БАК-посева<br>
 */
public class BakPosev {
    /**
     * Идентификатор исследования
     */
    private int actionId;
    /**
     * Штрих-код результата исследования
     */
    private String barCode;
    /**
     * Врач, сделавший анализ
     */
    private Doctor doctor;

    /**
     * Флаг окончательного результата
     */
    private boolean isComplete;

    /**
     * Основной комментарий к исследованию
     */
    private String generalComment;
    /**
     * Список микроорганизмов
     */
    private List<Microorganism> microorganismList;


    public BakPosev(int actionId) {
        this.actionId = actionId;
        this.microorganismList = new LinkedList<Microorganism>();
    }

    public void addMicroorganism(final Microorganism microorganism) {
        if (microorganism != null) {
            microorganismList.add(microorganism);
        }
    }

    public int getActionId() {
        return actionId;
    }

    public List<Microorganism> getMicroorganismList() {
        return new ArrayList<Microorganism>(microorganismList);
    }

    public void addAntibioticToOrganism(String codeMicroOrg, Antibiotic antibiotic) {
        for (Microorganism microorganism : microorganismList) {
            if (microorganism.getCode().equals(codeMicroOrg)) {
                microorganism.addAntibiotic(antibiotic);
                break;
            }
        }
    }

    public String getBarCode() {
        return barCode;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BakPosev{");
        sb.append("actionId=").append(actionId);
        sb.append(", barCode='").append(barCode).append('\'');
        sb.append(", doctor=").append(doctor);
        sb.append(", isComplete=").append(isComplete);
        sb.append(", generalComment='").append(generalComment).append('\'');
        sb.append(", microorganismList=").append(microorganismList);
        sb.append('}');
        return sb.toString();
    }
}
