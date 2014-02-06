package ru.korus.tmis.pix.sda;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.DbQueryBeanLocal;
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
    private final CodeNameSystem mkb;

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
    private final CodeNameSystem diagType;

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
    private final CodeNameSystem traumaType;

    /**
     * Кол-во госпитализаций в текущем году с данным диагнозом
     */
    private final Long countAdmissionsThisYear;


    public DiagnosisInfo(final Event event, final Diagnostic diagnostic, final DbQueryBeanLocal dbQueryBean) {
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
        this.mkb = RbManager.get(RbManager.RbType.MKB308, CodeNameSystem.newInstance(mkbCode, diagName, "1.2.643.5.1.13.2.1.1.643"));
        RbDiagnosisType dt = diagnosis.getDiagnosisType();
        String diagTypeName = null;
        String diagTypeCode = null;
        if (dt != null) {
            diagTypeCode = "".equals(dt.getCode()) ? null : dt.getCode();
            diagTypeName = "".equals(dt.getName()) ? null : dt.getName();
        }
        diagType = new CodeNameSystem(diagTypeCode, diagTypeName);
        this.diagTypeName = diagTypeName;
        this.diagTypeCode = diagTypeCode;
        this.diagId = String.valueOf(diagnosis.getId());
        final Staff person = diagnosis.getPerson();
        this.enteredPerson = EmployeeInfo.newInstance(person);
        this.acuteOrChronic = diagnosis.getCharacterId() == 1; //TODO: add entity for rbDiseaseCharacter!
        dispensarySuperVision = new DispensaryInfo(diagnostic);
        final RbTraumaType traumaTypeDb = diagnostic.getTraumaType();
        traumaType = traumaTypeDb == null ? null : RbManager.get(RbManager.RbType.rbTraumaType,
                CodeNameSystem.newInstance(traumaTypeDb.getCode(), traumaTypeDb.getName(), "1.2.643.5.1.13.2.1.1.105"));

        //diagnostic.getEvent().getEventType().getRequestType().getCode()
        countAdmissionsThisYear = dbQueryBean.countAdmissionsThisYear(event, diagnosis); // 311 Рассчитать кол-во стационарных Event'ов,  с одинаковым значением Diagnosis.MKB
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
    public CodeNameSystem getMkb() {
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

    public CodeNameSystem getDiagType() {
        return diagType;
    }

    public Boolean getAcuteOrChronic() {
        return acuteOrChronic;
    }

    public DispensaryInfo getDispensarySuperVision() {
        return dispensarySuperVision;
    }

    public CodeNameSystem getTraumaType() {
        return traumaType;
    }

    public Long getCountAdmissionsThisYear() {
        return countAdmissionsThisYear;
    }
}
