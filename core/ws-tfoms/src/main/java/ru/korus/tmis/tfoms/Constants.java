package ru.korus.tmis.tfoms;


import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 28.06.13, 12:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: Набор констант  <br>
 */
public final class Constants {
    //TFOMS Upload POLICY
    public static final int POLICY_NUMBER_MAX_LENGTH = 16;
    public static final int POLICY_SERIAL_MAX_LENGTH = 16;

    //PreSelect
    public static final String QUERY_NAME_MOVING_ACTION_TYPE = "SpecialVar_getMovingActionTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_MAIN_DIAGNOSIS = "SpecialVar_getStationaryDiagnosisActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_SECONDARY_DIAGNOSIS = "SpecialVar_getStationarySecondaryDiagnosisActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_ISHOD = "SpecialVar_getStationaryIshodActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_RESULT = "SpecialVar_getStationaryResultActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_CSG = "SpecialVar_getStationaryCSGActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_STAGE = "SpecialVar_getStationaryStageActionPropertyTypeId";
    public static final String QUERY_NAME_STATIONARY_PROPERTY_TYPE_HOSPITAL_BED_PROFILE = "SpecialVar_getStationaryHospitalBedProfileActionPropertyTypeId";

    public static final String QUERY_NAME_MULTIPLE_BIRTH_CONTACT_TYPE = "SpecialVar_getMultipleBirthContactTypeId";

    //MainQueries
    public static final String QUERY_NAME_STATIONARY_MAIN = "SpecialVar_TFOMSv2_FlatStationar";
    public static final String QUERY_NAME_POLICLINIC_MAIN = "SpecialVar_TFOMSv2_Policlinic";
    public static final String QUERY_NAME_POLICLINIC_ADDITIONAL = "SpecialVar_TFOMSv2_Policlinic_Additional";
    public static final String QUERY_NAME_DISPANSERIZATION_ADDITIONAL = "SpecialVar_TFOMSv2_Dispanserization_Additional";

    public static final String STOMATOLOGY_MEDICAL_KIND_CODE = "T";
    public static final String UET_UNIT_CODE = "5";

    public static final String QUERY_NAME_ACCOUNTITEM_CHECK_PRIMARY = "SpecialVar_TFOMS_AccountItemCheck_Primary";
    public static final String QUERY_NAME_ACCOUNTITEM_CHECK_ADDITIONAL = "SpecialVar_TFOMS_AccountItemCheck_Additional";

    public static final String QUERY_NAME_PATIENT_PROPERTIES = "SpecialVar_TFOMS_Patient_Properties";
    public static final String QUERY_NAME_PATIENT_DOCUMENT = "SpecialVar_TFOMS_Patient_Document";
    public static final String QUERY_NAME_PATIENT_POLICY = "SpecialVar_TFOMS_Patient_Policy";
    public static final String QUERY_NAME_PATIENT_OKATOG = "SpecialVar_TFOMS_Patient_OKATOG";
    public static final String QUERY_NAME_PATIENT_OKATOP = "SpecialVar_TFOMS_Patient_OKATOP";
    public static final String QUERY_NAME_PATIENT_SPOKESMAN = "SpecialVar_TFOMS_Patient_Spokesman";

    public static final String OS_SLUCH = "SpecialVar_TFOMS_OS_SLUCH";
    public static final String NULL = "NULL";


    //Dispanserization specifies
    //WMIS-40
    public static final Set<String> DISPANSERIZATION_IDSP_LIST =
            ImmutableSet.of("11", "94", "95", "96", "98");
    public static final Set<String> DISPANSERIZATION_SERVICE_INFIS_PART =
            ImmutableSet.of("026", "031", "076", "081");

    public static final String ADDITIONAL_SKIP_RESULT = "304";

    //Навзания параметров
    //Основные параметры
    public static final String PARAM_NAME_CONTRACT_ID = "@contractId";
    public static final String PARAM_NAME_BEGIN_DATE = "@beginInterval";
    public static final String PARAM_NAME_END_DATE = "@endInterval";
    public static final String PARAM_NAME_ORGANISATION_ID = "@organisationId";
    public static final String PARAM_NAME_ORG_STRUCTURE_ID_LIST = "@orgStructureIdList";
    public static final String PARAM_NAME_OBSOLETE_INFIS_CODE = "@obsoleteInfisCode";
    public static final String PARAM_NAME_LEVEL_MO = "@levelMO";
    public static final String PARAM_NAME_SMO_AREA = "@SMOArea";

    //Pre Select section
    public static final String PARAM_NAME_MOVING_ACTION_TYPE = "@movingActionTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_MAIN_DIAGNOSIS = "@stationaryMainDiagnosisActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_SECONDARY_DIAGNOSIS = "@stationarySecondaryDiagnosisActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_ISHOD = "@stationaryIshodActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_RESULT = "@stationaryResultActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_CSG = "@stationaryCSGActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_STAGE = "@stationaryStageActionPropertyTypeId";
    public static final String PARAM_NAME_STATIONARY_PROPERTY_TYPE_HOSPITAL_BED_PROFILE = "@stationaryHospitalBedProfileActionPropertyTypeId";

    public static final String PARAM_NAME_MULTIPLE_BIRTH_CONTACT_TYPE = "@multipleBirthContactTypeId";

    //Параметры случая
    public static final String PARAM_NAME_CLIENT_ID = "@clientId";
    public static final String PARAM_NAME_RB_SERVICE_INFIS = "@serviceInfisCode";
    public static final String PARAM_NAME_EVENT_TYPE_ID = "@eventTypeId";
    public static final String PARAM_NAME_CHECK_DATE = "@checkDate";
    public static final String PARAM_NAME_INSURER_AREA = "@InsurerArea";
    public static final String PARAM_NAME_ACTION_ID = "@actionId";


    private Constants() {
    }

}
