package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 15.07.2016, 14:57 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */

@XmlType(propOrder = {"name", "code", "personId", "personInfo", "finished", "comment", "executeDateTime", "results"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ResearchResult {
    /**
     * Код исследования
     */
    @XmlElement(name = "code")
    private String code;

    /**
     * Название исследования
     */

    @XmlElement(name = "name")
    private String name;

    /**
     * Идентифкатор врача из ЛИС, выполнившего исследования
     */
    @XmlElement(name = "personId")
    private int personId;

    /**
     * Инфа о враче, выполнившем исследования в ЛИС (ФИО)
     */
    @XmlElement(name = "personInfo")
    private String personInfo;

    /**
     * Флаг завершенности всех тестов по этому исследованию в ЛИС (должны ли присылаться изменения)
     */
    @XmlElement(name = "finished")
    private boolean finished;

    /**
     * Примечание к исследованию
     */
    @XmlElement(name = "comment")
    private String comment;

    /**
     * Время выполнения тестов по исследованию
     */
    @XmlElement(name = "executeDateTime")
    private Date executeDateTime;

    /**
     * Список результатов конкретных тестов
     */
    @XmlElement(name = "results")
    private List<AnalysisResult> results;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(final int personId) {
        this.personId = personId;
    }

    public String getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(final String personInfo) {
        this.personInfo = personInfo;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Date getExecuteDateTime() {
        return executeDateTime;
    }

    public void setExecuteDateTime(final Date executeDateTime) {
        this.executeDateTime = executeDateTime;
    }

    public List<AnalysisResult> getResults() {
        return results;
    }

    public void setResults(final List<AnalysisResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResearchResult{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", personId=").append(personId);
        sb.append(", personInfo='").append(personInfo).append('\'');
        sb.append(", finished=").append(finished);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", executeDateTime=").append(executeDateTime);
        sb.append(", results=").append(results);
        sb.append('}');
        return sb.toString();
    }
}
