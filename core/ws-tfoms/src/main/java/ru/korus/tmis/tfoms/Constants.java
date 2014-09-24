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

    public static final String STOMATOLOGY_MEDICAL_KIND_CODE = "T";
    public static final String UET_UNIT_CODE = "5";
    public static final String DISPANSERIZATION_MEDICAL_KIND_CODE = "H";
    public static final ImmutableSet<String> VNOV_D_CSG_CODE_LIST = ImmutableSet.of("067", "068");

    public static final String POLICLINIC_SELECT_STATEMENT = "SpecialVar_TFOMS_Policlinic";
    public static final String POLICLINIC_PROPERTIES = "SpecialVar_TFOMS_Policlinic_Properties";
    public static final String POLICLINIC_TARIFF = "SpecialVar_TFOMS_Policlinic_Tariff";
    public static final String POLICLINIC_EXCEPTION = "SpecialVar_TFOMS_Policlinic_Exception";
    public static final String POLICLINIC_USL_PROPERTIES = "SpecialVar_TFOMS_Policlinic_USL_Properties";
    public static final String POLICLINIC_CODE_MES1 = "SpecialVar_TFOMS_Policlinic_CODE_MES1";
    public static final String POLICLINIC_CODE_MES2 = "SpecialVar_TFOMS_Policlinic_CODE_MES2";


    public static final String STATIONARY_SELECT_STATEMENT = "SpecialVar_TFOMS_Stationary";
    public static final String STATIONARY_PROPERTIES = "SpecialVar_TFOMS_Stationary_Properties";
    public static final String STATIONARY_TARIFF = "SpecialVar_TFOMS_Stationary_Tariff";
    public static final String STATIONARY_EXCEPTION = "SpecialVar_TFOMS_Stationary_Exception";
    public static final String STATIONARY_USL_PROPERTIES = "SpecialVar_TFOMS_Stationary_USL_Properties";
    public static final String STATIONARY_CODE_MES1 = "SpecialVar_TFOMS_Stationary_CODE_MES1";
    public static final String STATIONARY_CODE_MES2 = "SpecialVar_TFOMS_Stationary_CODE_MES2";

    public static final String ACCOUNTITEM_CHECK_PRIMARY = "SpecialVar_TFOMS_AccountItemCheck_Primary";
    public static final String ACCOUNTITEM_CHECK_ADDITIONAL = "SpecialVar_TFOMS_AccountItemCheck_Additional";

    public static final String PATIENT_PROPERTIES = "SpecialVar_TFOMS_Patient_Properties";
    public static final String PATIENT_DOCUMENT = "SpecialVar_TFOMS_Patient_Document";
    public static final String PATIENT_POLICY = "SpecialVar_TFOMS_Patient_Policy";
    public static final String PATIENT_OKATOG = "SpecialVar_TFOMS_Patient_OKATOG";
    public static final String PATIENT_OKATOP = "SpecialVar_TFOMS_Patient_OKATOP";
    public static final String PATIENT_SPOKESMAN = "SpecialVar_TFOMS_Patient_Spokesman";

    public static final String SLUCH_PROPERTIES = "SpecialVar_TFOMS_Sluch_Properties";

    public static final String OS_SLUCH = "SpecialVar_TFOMS_OS_SLUCH";

    public static final String ADDITIONAL_POLICLINIC_SELECT_STATEMENT = "SpecialVar_TFOMS_Policlinic_Additional";
    public static final String ADDITIONAL_DISPANSERIZATION_SELECT_STATEMENT = "SpecialVar_TFOMS_Dispanserization_Additional";
    public static final String ADDITIONAL_DISPANSERIZATION_TARIFF = "SpecialVar_TFOMS_Dispanserization_Additional_Tariff";

    public static final String NULL = "NULL";


    //Dispanserization specifies
    //WMIS-40
    public static final Set<String> DISPANSERIZATION_IDSP_LIST =
            ImmutableSet.of("11", "94", "95", "96", "98");
    public static final Set<String> DISPANSERIZATION_SERVICE_INFIS_PART =
            ImmutableSet.of("026", "031", "076", "081");

    //Навзания параметров
    public static final String EVENT_ID_PARAM = "@eventId";
    public static final String ACTION_ID_PARAM = "@actionId";
    public static final String MEDICAL_KIND_ID_PARAM = "@medicalKindId";
    public static final String MEDICAL_KIND_CODE_PARAM = "@medicalKindCode";
    public static final String PATIENT_ID_PARAM = "@patientId";
    public static final String EVENT_TYPE_ID_PARAM = "@eventTypeId";
    public static final String SERVICE_ID_PARAM = "@serviceId";
    public static final String SERVICE_INFIS_PARAM = "@serviceInfis";
    public static final String CHECK_DATE_PARAM = "@checkDate";
    public static final String CHECK_DATE_BEGIN_PARAM = "@checkBeginDate";
    public static final String PROFIL_CODE_PARAM = "@PROFIL";
    public static final String RSLT_PARAM = "@RSLT";
    public static final String ISHOD_PARAM = "@ISHOD";
    public static final String DS0_PARAM = "@DS0";
    public static final String DS1_PARAM = "@DS1";
    public static final String DS2_PARAM = "@DS2";
    public static final String NHISTORY_PARAM = "@NHISTORY";
    public static final String RSLT_REGIONAL_CODE_PARAM = "@RESULTRegionalCode";
    public static final String DOCUMENT_ID_PARAM = "@documentId";
    public static final String POLICY_ID_PARAM = "@policyId";
    public static final String INSURER_ID_PARAM = "@insurerId";
    public static final String SEX_PARAM = "@sex";
    public static final String BIRTH_DATE_PARAM = "@birthDate";
    public static final String SPOKESMAN_ID_PARAM = "@spokesmanId";
    public static final String MKU_ID_PARAM = "@MKU";
    public static final String ED_COL_PARAM = "@ED_COL";
    public static final String IDSP_PARAM = "@IDSP";
    public static final String UNIT_ID_PARAM = "@unitId";
    public static final String CONTRACT_ID_PARAM = "@contractId";
    public static final String BEGIN_DATE_PARAM = "@begDate";
    public static final String END_DATE_PARAM = "@endDate";
    public static final String ORGANISATION_ID_PARAM = "@organisationId";
    public static final String ORG_STRUCTURE_ID_LIST_PARAM = "@orgStructureIdList";
    public static final String OBSOLETE_INFIS_CODE_PARAM = "@obsoleteInfisCode";
    public static final String LEVEL_MO_PARAM = "@levelMO";
    public static final String SPOKESMAN_DOCUMENT_ID_PARAM = "@spokesmanDocumentId";
    public static final String SPOKESMAN_POLICY_ID_PARAM = "@spokesmanPolicyId";

    public static final String CSG_GROUP_ID_PARAM = "@CSG";

    //Master param names
    public static final String MASTER_SHECK_DATE_PARAM = "@masterCheckDate";
    public static final String MASTER_EVENT_ID_PARAM = "@masterEventId";
    public static final String MASTER_EVENT_TYPE_ID = "@masterEventTypeId";
    public static final String MASTER_DS1 = "@masterDS1";


    private Constants() {
    }

}
