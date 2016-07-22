package ru.korus.tmis.ws.transfusion;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.02.2013, 10:52:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Типы свойств действий ТРФУ
 */
public class Constants {

    public static final String TRANSFUSION_ACTION_FLAT_CODE = "TransfusionTherapy";


    //"Результат передачи требования в систему ТРФУ"
    public static final String APT_CODE_ORDER_RESULT = "trfuReqBloodCompResult";

    //"Дата"
    public static final String APT_CODE_ORDER_ISSUE_RES_DATE = "trfuReqBloodCompDate";

    //"Время"
    public static final String APT_CODE_ORDER_ISSUE_RES_TIME = "trfuReqBloodCompTime";

    /**
     * Свойства действия "Гемотрансфузионная терапия" (требование на выдачу КК)
     */

    //"Основной клинический диагноз"
    public static final String APT_CODE_DIAGNOSIS = "trfuReqBloodCompDiagnosis";

    //"Требуемый компонент крови"
    public static final String APT_CODE_BLOOD_COMP_TYPE = "trfuReqBloodCompId";

    //"Вид трансфузии"
    public static final String APT_CODE_TYPE = "trfuReqBloodCompType";

    //"Объем требуемого компонента крови (все, кроме тромбоцитов)"
    public static final String APT_CODE_VOLUME = "trfuReqBloodCompValue";

    //"Показания к проведению трансфузии"
    public static final String APT_CODE_ROOT_CAUSE = "trfuReqBloodCompRootCause";

    //"Группа крови для заказа"
    public static final String APT_CODE_REQ_BLOOD_TYPE = "trfuReqBloodType";
    //"Паспортные данные выданных компонентов крови"
    public static final String APT_CODE_ORDER_ISSUE_BLOOD_COMP_PASPORT = "trfuReqBloodCompPasport";
    //"Донор"
    public static final String APT_CODE_DONOR_ID = "trfuProcedureDonor";
    //"Противопоказания к проведению процедуры"
    public static final String APT_CODE_CONTRAINDICATION = "trfuProcedureContraindication";
    //"Пульс до процедуры"
    public static final String APT_CODE_BEFORE_HEMODYNAMICS_PULSE = "trfuProcedureBeforeHemodynamicsPulse";
    //"Пульс после процедуры"
    public static final String APT_CODE_AFTER_HEMODYNAMICS_PULSE = "trfuProcedureAfterHemodynamicsPulse";
    //"Температура до процедуры"
    public static final String APT_CODE_BEFORE_HEMODYNAMICS_TEMPERATURE = "trfuProcedureBeforeHemodynamicsTemperature";
    //"Температура после процедуры"
    public static final String APT_CODE_AFTER_HEMODYNAMICS_TEMPERATURE = "trfuProcedureAfterHemodynamicsTemperature";
    //"Осложнения"
    public static final String APT_CODE_COMPLICATIONS = "trfuProcedureComplications";
    //"обработанный TBV - Инициально (параметры процедуры)"
    public static final String APT_CODE_INITIAL_TBV = "trfuProcedureInitialTbv";
    //"обработанный TBV - Изменения (параметры процедуры)"
    public static final String APT_CODE_CHANGE_TBV = "trfuProcedureChangeTbv";
    //"скорость забора – Инициально (параметры процедуры)"
    public static final String APT_CODE_INITIAL_SPEED = "trfuProcedureInitialSpeed";
    //"скорость забора – Изменения (параметры процедуры)"
    public static final String APT_CODE_CHANGE_SPEED = "trfuProcedureChangeSpeed";
    //"inletAcRatio – Инициально (параметры процедуры)"
    public static final String APT_CODE_INITIAL_INLETACRATIO = "trfuProcedureInitialInletAcRatio";
    //"inletAcRatio – Изменения (параметры процедуры )"
    public static final String APT_CODE_CHANGE_INLETACRATIO = "trfuProcedureChangeInletAcRatio";
    //"время афереза – Инициально(параметры процедуры)"
    public static final String APT_CODE_INITIAL_TIME = "trfuProcedureInitialTime";
    //"время афереза – Изменения (параметры процедуры)"
    public static final String APT_CODE_CHANGE_TIME = "trfuProcedureChangeTime";
    //"введено ACD (баланс жидкостей)"
    public static final String APT_CODE_ACD_LOAD = "trfuProcedureAcdLoad";
    //"введено NaCl,9% (баланс жидкостей)"
    public static final String APT_CODE_NACL_LOAD = "trfuProcedureNaClLoad";
    //"введено Ca++ (баланс жидкостей)"
    public static final String APT_CODE_CA_LOAD = "trfuProcedureCaLoad";
    //"введено другое (баланс жидкостей)"
    public static final String APT_CODE_OTHER_LOAD = "trfuProcedureOtherLoad";
    //"введено всего (баланс жидкостей)"
    public static final String APT_CODE_TOTAL_LOAD = "trfuProcedureTotalLoad";
    //"удалено в пакете (баланс жидкостей)"
    public static final String APT_CODE_PACK_REMOVE = "trfuProcedurePackRemove";
    //"удалено другое (баланс жидкостей)"
    public static final String APT_CODE_OTHER_REMOVE = "trfuProcedureOtherRemove";
    //"удалено всего (баланс жидкостей)"
    public static final String APT_CODE_TOTAL_REMOVE = "trfuProcedureTotalRemove";
    //"баланс (баланс жидкостей)"
    public static final String APT_CODE_BALANCE = "trfuProcedureBalance";
    //"производитель эритроцитарной массы"
    public static final String APT_CODE_MAKER = "trfuProcedureMaker";
    //"номер пакета эритроцитарной массы"
    public static final String APT_CODE_NUMBER = "trfuProcedureNumber";
    //"объем эритроцитарной массы"
    public static final String APT_CODE_VOLUME_PROC_RES = "trfuProcedureVolume";

//    BLOOD_GROUP_ID(
//            "trfuProcedureBloodGroupId",
//            APValueInteger.class,
//            "идентификатор группы крови эритроцитарной массы (1- первая 0 (I), 2 – вторая А (II), 3 – третья В (III), 4 – четвертая АВ (IV))"
//    ),
//
//    RHESUS_FACTOR_ID(
//            "trfuProcedureRhesusFactorId",
//            APValueInteger.class,
//            "идентификатор резус-фактора эритроцитарной массы (0 – Положительный, 1 - Отрицательный)"
//    ),
    //"дата изготовления"
    public static final String APT_CODE_PRODUCTION_DATE = "trfuProcedureProductionDate";
    //"срок годности"
    public static final String APT_CODE_EXPIRATION_DATE = "trfuProcedureExpirationDate";
    //"гематокрит эритроцитарной массы"
    public static final String APT_CODE_HT = "trfuProcedureHt";
    //"объем добавленного физ. раствора"
    public static final String APT_CODE_SALINE_VOLUME = "trfuProcedureSalineVolume";
    //"финальный гематокрит"
    public static final String APT_CODE_FINAL_HT = "trfuProcedureFinalHt";
    //"TRFU_LM" "лабораторные измерения";
    public static final String APT_CODE_LAB_MEASURE = "trfuLaboratoryMeasures";
    //"TRFU_FV"  "финальные объемы";
    public static final String APT_CODE_FINAL_VOLUME = "trfuFinalVolumes";


    public static Set<String> TRANSFUSION_THERAPY_APT_FOR_SEND = ImmutableSet.of(
            APT_CODE_DIAGNOSIS,
            APT_CODE_BLOOD_COMP_TYPE,
            APT_CODE_TYPE,
            APT_CODE_VOLUME,
            APT_CODE_ROOT_CAUSE,
            APT_CODE_REQ_BLOOD_TYPE
    );


}