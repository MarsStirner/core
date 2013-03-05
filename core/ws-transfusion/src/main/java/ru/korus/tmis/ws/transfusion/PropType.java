package ru.korus.tmis.ws.transfusion;

import ru.korus.tmis.core.entity.model.APValueDate;
import ru.korus.tmis.core.entity.model.APValueDouble;
import ru.korus.tmis.core.entity.model.APValueInteger;
import ru.korus.tmis.core.entity.model.APValueRbBloodComponentType;
import ru.korus.tmis.core.entity.model.APValueString;
import ru.korus.tmis.core.entity.model.APValueTime;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.02.2013, 10:52:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Типы свойств действий ТРФУ
 */
public enum PropType {
    /**
     * Общие свойства ТРФУ
     */
    ORDER_REQUEST_ID("trfuReqBloodCompResult", APValueString.class, "Результат передачи требования в систему ТРФУ"),

    ORDER_ISSUE_RES_DATE("trfuReqBloodCompDate", APValueDate.class, "Дата"),

    ORDER_ISSUE_RES_TIME("trfuReqBloodCompTime", APValueTime.class, "Время"),

    /**
     * Свойства действия "Гемотрансфузионная терапия" (требование на выдачу КК)
     */
    DIAGNOSIS("trfuReqBloodCompDiagnosis", APValueString.class, "Основной клинический диагноз"),

    BLOOD_COMP_TYPE("trfuReqBloodCompId", APValueRbBloodComponentType.class, "Требуемый компонент крови"),

    TYPE("trfuReqBloodCompType", APValueString.class, "Вид трансфузии"),

    VOLUME("trfuReqBloodCompValue", APValueInteger.class, "Объем требуемого компонента крови (все, кроме тромбоцитов)"),

    DOSE_COUNT("trfuReqBloodCompDose", APValueDouble.class, "Количество требуемых донорских доз (тромбоциты)"),

    ROOT_CAUSE("trfuReqBloodCompRootCause", APValueString.class, "Показания к проведению трансфузии"),

    ORDER_ISSUE_BLOOD_COMP_PASPORT("trfuReqBloodCompPasport", APValueInteger.class, "Паспортные данные выданных компонентов крови"),

    /**
     * Свойства лечебных процедур ТРФУ
     */
    DONOR_ID("trfuProcedureDonor", APValueInteger.class, "Донор"),

    CONTRAINDICATION("trfuProcedureContraindication", APValueString.class, "Противопоказания к проведению процедуры"),

    BEFORE_HEMODYNAMICS_PULSE("trfuProcedureBeforeHemodynamicsPulse", APValueString.class, "Пульс до процедуры"),

    AFTER_HEMODYNAMICS_PULSE("trfuProcedureAfterHemodynamicsPulse", APValueString.class, "Пульс после процедуры"),

    BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE("trfuProcedureBeforeHemodynamicsArterialPressure", APValueString.class, "Артериальное давление до процедуры"),

    AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE("trfuProcedureAfterHemodynamicsArterialPressure", APValueString.class, "Артериальное давление после процедуры"),

    BEFORE_HEMODYNAMICS_TEMPERATURE("trfuProcedureBeforeHemodynamicsTemperature", APValueString.class, "Температура до процедуры"),

    AFTER_HEMODYNAMICS_TEMPERATURE("trfuProcedureAfterHemodynamicsTemperature", APValueString.class, "Температура после процедуры"),

    COMPLICATIONS("trfuProcedureComplications", APValueString.class, "Осложнения"),

    INITIAL_VOLUME("trfuProcedureInitialVolume", APValueDouble.class, "объем афереза - Инициально (параметры процедуры)", Database.UNIT_MILILITER),

    CHANGE_VOLUME("trfuProcedureChangeVolume", APValueDouble.class, "объем афереза – Изменения (параметры процедуры)", Database.UNIT_MILILITER),

    INITIAL_TBV("trfuProcedureInitialTbv", APValueString.class, "обработанный TBV - Инициально (параметры процедуры)"),

    CHANGE_TBV("trfuProcedureChangeTbv", APValueString.class, "обработанный TBV - Изменения (параметры процедуры)"),

    INITIAL_SPEED("trfuProcedureInitialSpeed", APValueString.class, "скорость забора – Инициально (параметры процедуры)"),

    CHANGE_SPEED("trfuProcedureChangeSpeed", APValueString.class, "скорость забора – Изменения (параметры процедуры)"),

    INITIAL_INLETACRATIO("trfuProcedureInitialInletAcRatio", APValueString.class, "inletAcRatio – Инициально (параметры процедуры)"),

    CHANGE_INLETACRATIO("trfuProcedureChangeInletAcRatio", APValueString.class, "inletAcRatio – Изменения (параметры процедуры )"),

    INITIAL_TIME("trfuProcedureInitialTime", APValueString.class, "время афереза – Инициально(параметры процедуры)"),

    CHANGE_TIME("trfuProcedureChangeTime", APValueString.class, "время афереза – Изменения (параметры процедуры)"),

    INITIAL_PRODUCT_VOLUME("trfuProcedureInitialProductVolume", APValueDouble.class, "объем продукта афереза - Инициально (параметры процедуры)",
            Database.UNIT_MILILITER),

    CHANGE_PRODUCT_VOLUME("trfuProcedureChangeProductVolume", APValueDouble.class, "объем продукта афереза – Изменения (параметры процедуры)",
            Database.UNIT_MILILITER),

    ACD_LOAD("trfuProcedureAcdLoad", APValueString.class, "введено ACD (баланс жидкостей)"),

    NACL_LOAD("trfuProcedureNaClLoad", APValueString.class, "введено NaCl,9% (баланс жидкостей)"),

    CA_LOAD("trfuProcedureCaLoad", APValueString.class, "введено Ca++ (баланс жидкостей)"),

    OTHER_LOAD("trfuProcedureOtherLoad", APValueString.class, "введено другое (баланс жидкостей)"),

    TOTAL_LOAD("trfuProcedureTotalLoad", APValueString.class, "введено всего (баланс жидкостей)"),

    PACK_REMOVE("trfuProcedurePackRemove", APValueString.class, "удалено в пакете (баланс жидкостей)"),

    OTHER_REMOVE("trfuProcedureOtherRemove", APValueString.class, "удалено другое (баланс жидкостей)"),

    TOTAL_REMOVE("trfuProcedureTotalRemove", APValueString.class, "удалено всего (баланс жидкостей)"),

    BALANCE("trfuProcedureBalance", APValueString.class, "баланс (баланс жидкостей)"),

    MAKER("trfuProcedureMaker", APValueString.class, "производитель эритроцитарной массы"),

    NUMBER("trfuProcedureNumber", APValueString.class, "номер пакета эритроцитарной массы"),

    BLOOD_GROUP_ID("trfuProcedureBloodGroupId",
            APValueInteger.class,
            "идентификатор группы крови эритроцитарной массы (1- первая 0 (I), 2 – вторая А (II), 3 – третья В (III), 4 – четвертая АВ (IV))"),

    RHESUS_FACTOR_ID("trfuProcedureRhesusFactorId",
            APValueInteger.class,
            "идентификатор резус-фактора эритроцитарной массы (0 – Положительный, 1 - Отрицательный)"),

    VOLUME_PROC_RES("trfuProcedureVolume", APValueDouble.class, "объем эритроцитарной массы", Database.UNIT_MILILITER),

    PRODUCTION_DATE("trfuProcedureProductionDate", APValueDate.class, "дата изготовления"),

    EXPIRATION_DATE("trfuProcedureExpirationDate", APValueDate.class, "срок годности"),

    HT("trfuProcedureHt", APValueDouble.class, "гематокрит эритроцитарной массы", Database.UNIT_MILILITER),

    SALINE_VOLUME("trfuProcedureSalineVolume", APValueDouble.class, "объем добавленного физ. раствора", Database.UNIT_MILILITER),

    FINAL_HT("trfuProcedureFinalHt", APValueDouble.class, "финальный гематокрит");

    @SuppressWarnings("rawtypes")
    private final Class valueClass;

    private final String code;

    private final String name;

    private final String unitCode;

    PropType(final String code, @SuppressWarnings("rawtypes") final Class valueClass, final String name) {
        this(code, valueClass, name, null);
    }

    PropType(final String code, @SuppressWarnings("rawtypes") final Class valueClass, final String name, final String unitCode) {
        this.code = code;
        this.valueClass = valueClass;
        this.name = name;
        this.unitCode = unitCode;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @SuppressWarnings("rawtypes")
    public Class getValueClass() {
        return valueClass;
    }

    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

}