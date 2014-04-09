package ru.korus.tmis.pix.sda;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.DbQueryBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;

import java.util.HashMap;
import java.util.Map;


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

    /**
     * Вид диагноза
     */
    private final CodeNameSystem diagKind;


    public DiagnosisInfo(final Event event, final Diagnostic diagnostic, final DbQueryBeanLocal dbQueryBean) {
        final Diagnosis diagnosis = diagnostic.getDiagnosis();
        XMLGregorianCalendar createDate = null;
        try {
            createDate = Database.toGregorianCalendar(diagnosis.getCreateDatetime());
        } catch (DatatypeConfigurationException e) {
        }
        this.createDate = createDate;
        this.mkb = toMKB308Diagnosis(diagnostic);
        RbDiagnosisType dt = diagnosis.getDiagnosisType();
        diagType = (dt == null || dt.getFlatCode() == null) ? null : toHSDiagType(dt.getFlatCode());
        diagKind = (dt == null || dt.getFlatCode() == null) ? null : toHSKindType(dt.getFlatCode());
        this.diagId = String.valueOf(diagnosis.getId());
        final Staff person = diagnosis.getPerson();
        this.enteredPerson = EmployeeInfo.newInstance(person);
        final RbDiseaseCharacter character = diagnosis.getCharacter();
        this.acuteOrChronic = character == null ? false : "1".equals(character.getCode());
        dispensarySuperVision = new DispensaryInfo(diagnostic);
        final RbTraumaType traumaTypeDb = diagnostic.getTraumaType();
        traumaType = traumaTypeDb == null ? null : RbManager.get(RbManager.RbType.rbTraumaType,
                CodeNameSystem.newInstance(traumaTypeDb.getCode(), traumaTypeDb.getName(), "1.2.643.5.1.13.2.1.1.105"));

        //diagnostic.getEvent().getEventType().getRequestType().getCode()
        countAdmissionsThisYear = null;//dbQueryBean.countAdmissionsThisYear(event, diagnosis); // 311 Рассчитать кол-во стационарных Event'ов,  с одинаковым значением Diagnosis.MKB
    }

    private CodeNameSystem toHSDiagType(String diagTypeCode) {
        Map<String, CodeNameSystem> mapFlatCode = new HashMap<String, CodeNameSystem>(){{
            put("finalMkb", CodeNameSystem.newInstance("FINAL", "заключительный", null));
            put("diagRecievedMkb", CodeNameSystem.newInstance("WAY", "направившего учреждения", null));
            //put("mainDiagMkbPat", CodeNameSystem.newInstance("DEAD", "Непосредственная причина смерти", null));
            //put("mainDiagMkbPat", CodeNameSystem.newInstance("IL", "Заболевание, вызвавшее смерть", null));
            put("mainDiagMkbPat", CodeNameSystem.newInstance("DPA", "Патологоанатомический диагноз", null));
        }};
        return mapFlatCode.get(diagTypeCode);
    }

    private CodeNameSystem toHSKindType(String diagTypeCode) {
        Map<String, CodeNameSystem> mapFlatCode = new HashMap<String, CodeNameSystem>(){{
            put("diagComplMkb", CodeNameSystem.newInstance("DIFF", "осложнение основного", null));
            put("assocDiagMkb", CodeNameSystem.newInstance("SEC", "сопутствующий", null));
        }};
        return mapFlatCode.get(diagTypeCode);
    }


    static public CodeNameSystem toMKB308Diagnosis(Diagnostic diagnostic) {
        final Diagnosis diagnosis = diagnostic.getDiagnosis();
        final RbDiagnosisType diagnosisType = diagnostic.getDiagnosisType();
        final Mkb mkb = diagnosis.getMkb();
        final String mkbCode = mkb != null ? mkb.getDiagID() : null;
        final String diagName = (mkb != null ? mkb.getDiagName() : "").trim() + "(" + (diagnosisType != null ? diagnosisType.getName() : "") + ")";
        return RbManager.get(RbManager.RbType.MKB308, CodeNameSystem.newInstance(mkbCode, diagName, "1.2.643.5.1.13.2.1.1.643"));
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

    public CodeNameSystem getDiagKind() {
        return diagKind;
    }
}
