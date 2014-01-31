package ru.korus.tmis.pix.sda;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;


/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        09.07.2013, 16:18:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class DiagnosisInfo {

    /**
     * Дата/время диагноза
     */
    private final XMLGregorianCalendar createDate;

    /**
     * Код МКБ
     */
    private final CodeNamePair mkb;

    /**
     * Код типа диагноза
     */
    private final String diagTypeCode;

    /**
     * Наименование типа диагноза
     */
    private final String diagTypeName;

    /**
     * Идентификатор в МИС
     */
    private final String diagId;

    /**
     * Врач
     */
    private final EmployeeInfo enteredPerson;

    /**
     * Тип диагноза
     */
    private final CodeNamePair diagType;

    /**
     * Острое или хроническое заболевание
     */
    private final Boolean acuteOrChronic;

    /**
     * Диспансерное наблюдение
     */
    private final DispensaryInfo dispensarySuperVision;

    /**
     * Вид травмы
     */
    private final CodeNamePair traumaType;

    /**
     * Кол-во госпитализаций в текущем году с данным диагнозом
     */
    private final Long countAdmissionsThisYear;


    public DiagnosisInfo(Diagnostic diagnostic) {
        final Diagnosis diagnosis = diagnostic.getDiagnosis();
        final RbDiagnosisType diagnosisType = diagnostic.getDiagnosisType();
        XMLGregorianCalendar createDate = null;
        try {
            createDate = Database.toGregorianCalendar(diagnosis.getCreateDatetime());
        } catch (DatatypeConfigurationException e) {
        }
        this.createDate = createDate;
        final Mkb mkb = diagnosis.getMkb();
        final String mkbCode = mkb != null ? mkb.getDiagID() : null;
        final String diagName = (mkb != null ? mkb.getDiagName() : "").trim() + "(" + (diagnosisType != null ? diagnosisType.getName() : "") + ")";
        this.mkb = new CodeNamePair(mkbCode, diagName);
        RbDiagnosisType dt = diagnosis.getDiagnosisType();
        String diagTypeName = null;
        String diagTypeCode = null;
        if (dt != null) {
            diagTypeCode = "".equals(dt.getCode())? null : dt.getCode();
            diagTypeName = "".equals(dt.getName())? null : dt.getName();
        }
        diagType = new CodeNamePair(diagTypeCode, diagTypeName);
        this.diagTypeName = diagTypeName;
        this.diagTypeCode = diagTypeCode;
        this.diagId = String.valueOf(diagnosis.getId());
        final Staff person = diagnosis.getPerson();
        this.enteredPerson = person == null ? null : new EmployeeInfo(person);
        this.acuteOrChronic = diagnosis.getCharacterId() == 1; //TODO: add entity for rbDiseaseCharacter!
        dispensarySuperVision = new DispensaryInfo(diagnostic);
        final RbTraumaType traumaTypeDb = diagnostic.getTraumaType();
        traumaType = traumaTypeDb == null ? null : new CodeNamePair(traumaTypeDb.getCode(), traumaTypeDb.getName());

        diagnostic.getEvent().getEventType().getRequestType().getCode()


        countAdmissionsThisYear = //TODO;
    }



    /**
     * @return the createDate
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * @return the mkb
     */
    public CodeNamePair getMkb() {
        return mkb;
    }


    public String getDiagTypeCode() {
        return diagTypeCode;
    }

    public String getDiagTypeName() {
        return diagTypeName;
    }


    public String getDiagId() {
        return diagId;
    }

    public EmployeeInfo getEnteredPerson() {
        return enteredPerson;
    }

    public CodeNamePair getDiagType() {
        return diagType;
    }

    public Boolean getAcuteOrChronic() {
        return acuteOrChronic;
    }

    public DispensaryInfo getDispensarySuperVision() {
        return dispensarySuperVision;
    }

    public CodeNamePair getTraumaType() {
        return traumaType;
    }

    public Long getCountAdmissionsThisYear() {
        return countAdmissionsThisYear;
    }
}
