package ru.korus.tmis.ws.transfusion.devtest;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.02.2013, 11:48:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
enum PropType {
    DIAGNOSIS("trfuReqBloodCompDiagnosis", "String"), // Основной клинический диагноз
    BLOOD_COMP_TYPE("trfuReqBloodCompId", "rbBloodComponentType"), // Требуемый компонент крови
    TYPE("trfuReqBloodCompType", "String"), // Вид трансфузии
    VOLUME("trfuReqBloodCompValue", "Integer"), // Объем требуемого компонента крови (все, кроме тромбоцитов)
    ROOT_CAUSE("trfuReqBloodCompRootCause", "String"), // Показания к проведению трансфузии
    ORDER_REQUEST_ID("trfuReqBloodCompResult", "String"), // Результат передачи требования в систему ТРФУ
    ORDER_ISSUE_RES_DATE("trfuReqBloodCompDate", "Date"), // Дата выдачи КК
    ORDER_ISSUE_RES_TIME("trfuReqBloodCompTime", "Time"), // Время выдачи КК
    ORDER_ISSUE_BLOOD_COMP_PASPORT("trfuReqBloodCompPasport", "Integer"),
    PATIENT_ORG_STRUCT("orgStructStay", "OrgStructure"),

    DONOR_ID("trfuProcedureDonor", "Integer"),
    CONTRAINDICATION("trfuProcedureContraindication", "String"),
    BEFORE_HEMODYNAMICS_PULSE("trfuProcedureBeforeHemodynamicsPulse", "String"),
    AFTER_HEMODYNAMICS_PULSE("trfuProcedureAfterHemodynamicsPulse", "String"),
    BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE("trfuProcedureBeforeHemodynamicsArterialPressure", "String"),
    AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE("trfuProcedureAfterHemodynamicsArterialPressure", "String"),
    BEFORE_HEMODYNAMICS_TEMPERATURE("trfuProcedureBeforeHemodynamicsTemperature", "String"),
    AFTER_HEMODYNAMICS_TEMPERATURE("trfuProcedureAfterHemodynamicsTemperature", "String"),
    COMPLICATIONS("trfuProcedureComplications", "String"),
    INITIAL_VOLUME("trfuProcedureInitialVolume", "Double"),
    CHANGE_VOLUME("trfuProcedureChangeVolume", "Double"),
    INITIAL_TBV("trfuProcedureInitialTbv", "String"),
    CHANGE_TBV("trfuProcedureChangeTbv", "String"),
    INITIAL_SPEED("trfuProcedureInitialSpeed", "String"),
    CHANGE_SPEED("trfuProcedureChangeSpeed", "String"),
    INITIAL_INLETACRATIO("trfuProcedureInitialInletAcRatio", "String"),
    CHANGE_INLETACRATIO("trfuProcedureChangeInletAcRatio", "String"),
    INITIAL_TIME("trfuProcedureInitialTime", "String"),
    CHANGE_TIME("trfuProcedureChangeTime", "String"),
    INITIAL_PRODUCT_VOLUME("trfuProcedureInitialProductVolume", "Double"),
    CHANGE_PRODUCT_VOLUME("trfuProcedureChangeProductVolume", "Double"),
    ACD_LOAD("trfuProcedureAcdLoad", "String"),
    NACL_LOAD("trfuProcedureNaClLoad", "String"),
    CA_LOAD("trfuProcedureCaLoad", "String"),
    OTHER_LOAD("trfuProcedureOtherLoad", "String"),
    TOTAL_LOAD("trfuProcedureTotalLoad", "String"),
    PACK_REMOVE("trfuProcedurePackRemove", "String"),
    OTHER_REMOVE("trfuProcedureOtherRemove", "String"),
    TOTAL_REMOVE("trfuProcedureTotalRemove", "String"),
    BALANCE("trfuProcedureBalance", "String"),
    MAKER("trfuProcedureMaker", "String"),
    NUMBER("trfuProcedureNumber", "String"),
    BLOOD_GROUP_ID("trfuProcedureBloodGroupId", "Integer"),
    RHESUS_FACTOR_ID("trfuProcedureRhesusFactorId", "Integer"),
    VOLUME_PROC_RES("trfuProcedureVolume", "Double"),
    PRODUCTION_DATE("trfuProcedureProductionDate", "Date"),
    EXPIRATION_DATE("trfuProcedureExpirationDate", "Date"),
    HT("trfuProcedureHt", "Double"),
    SALINE_VOLUME("trfuProcedureSalineVolume", "Double"),
    FINAL_HT("trfuProcedureFinalHt", "Double");

    private Integer id;
    private final String code;
    private final String type;

    PropType(final String code, final String type) {
        this.code = code;
        this.type = type;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

}