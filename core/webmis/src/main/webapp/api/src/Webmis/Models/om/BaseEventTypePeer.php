<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\EventType;
use Webmis\Models\EventTypePeer;
use Webmis\Models\RbEventTypePurposePeer;
use Webmis\Models\RbResultPeer;
use Webmis\Models\map\EventTypeTableMap;

/**
 * Base static class for performing query and update operations on the 'EventType' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseEventTypePeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'EventType';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\EventType';

    /** the related TableMap class for this table */
    const TM_CLASS = 'EventTypeTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 54;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 54;

    /** the column name for the id field */
    const ID = 'EventType.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'EventType.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'EventType.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'EventType.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'EventType.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'EventType.deleted';

    /** the column name for the code field */
    const CODE = 'EventType.code';

    /** the column name for the name field */
    const NAME = 'EventType.name';

    /** the column name for the purpose_id field */
    const PURPOSE_ID = 'EventType.purpose_id';

    /** the column name for the finance_id field */
    const FINANCE_ID = 'EventType.finance_id';

    /** the column name for the scene_id field */
    const SCENE_ID = 'EventType.scene_id';

    /** the column name for the visitServiceModifier field */
    const VISITSERVICEMODIFIER = 'EventType.visitServiceModifier';

    /** the column name for the visitServiceFilter field */
    const VISITSERVICEFILTER = 'EventType.visitServiceFilter';

    /** the column name for the visitFinance field */
    const VISITFINANCE = 'EventType.visitFinance';

    /** the column name for the actionFinance field */
    const ACTIONFINANCE = 'EventType.actionFinance';

    /** the column name for the period field */
    const PERIOD = 'EventType.period';

    /** the column name for the singleInPeriod field */
    const SINGLEINPERIOD = 'EventType.singleInPeriod';

    /** the column name for the isLong field */
    const ISLONG = 'EventType.isLong';

    /** the column name for the dateInput field */
    const DATEINPUT = 'EventType.dateInput';

    /** the column name for the service_id field */
    const SERVICE_ID = 'EventType.service_id';

    /** the column name for the context field */
    const CONTEXT = 'EventType.context';

    /** the column name for the form field */
    const FORM = 'EventType.form';

    /** the column name for the minDuration field */
    const MINDURATION = 'EventType.minDuration';

    /** the column name for the maxDuration field */
    const MAXDURATION = 'EventType.maxDuration';

    /** the column name for the showStatusActionsInPlanner field */
    const SHOWSTATUSACTIONSINPLANNER = 'EventType.showStatusActionsInPlanner';

    /** the column name for the showDiagnosticActionsInPlanner field */
    const SHOWDIAGNOSTICACTIONSINPLANNER = 'EventType.showDiagnosticActionsInPlanner';

    /** the column name for the showCureActionsInPlanner field */
    const SHOWCUREACTIONSINPLANNER = 'EventType.showCureActionsInPlanner';

    /** the column name for the showMiscActionsInPlanner field */
    const SHOWMISCACTIONSINPLANNER = 'EventType.showMiscActionsInPlanner';

    /** the column name for the limitStatusActionsInput field */
    const LIMITSTATUSACTIONSINPUT = 'EventType.limitStatusActionsInput';

    /** the column name for the limitDiagnosticActionsInput field */
    const LIMITDIAGNOSTICACTIONSINPUT = 'EventType.limitDiagnosticActionsInput';

    /** the column name for the limitCureActionsInput field */
    const LIMITCUREACTIONSINPUT = 'EventType.limitCureActionsInput';

    /** the column name for the limitMiscActionsInput field */
    const LIMITMISCACTIONSINPUT = 'EventType.limitMiscActionsInput';

    /** the column name for the showTime field */
    const SHOWTIME = 'EventType.showTime';

    /** the column name for the medicalAidType_id field */
    const MEDICALAIDTYPE_ID = 'EventType.medicalAidType_id';

    /** the column name for the eventProfile_id field */
    const EVENTPROFILE_ID = 'EventType.eventProfile_id';

    /** the column name for the mesRequired field */
    const MESREQUIRED = 'EventType.mesRequired';

    /** the column name for the mesCodeMask field */
    const MESCODEMASK = 'EventType.mesCodeMask';

    /** the column name for the mesNameMask field */
    const MESNAMEMASK = 'EventType.mesNameMask';

    /** the column name for the counter_id field */
    const COUNTER_ID = 'EventType.counter_id';

    /** the column name for the isExternal field */
    const ISEXTERNAL = 'EventType.isExternal';

    /** the column name for the isAssistant field */
    const ISASSISTANT = 'EventType.isAssistant';

    /** the column name for the isCurator field */
    const ISCURATOR = 'EventType.isCurator';

    /** the column name for the canHavePayableActions field */
    const CANHAVEPAYABLEACTIONS = 'EventType.canHavePayableActions';

    /** the column name for the isRequiredCoordination field */
    const ISREQUIREDCOORDINATION = 'EventType.isRequiredCoordination';

    /** the column name for the isOrgStructurePriority field */
    const ISORGSTRUCTUREPRIORITY = 'EventType.isOrgStructurePriority';

    /** the column name for the isTakenTissue field */
    const ISTAKENTISSUE = 'EventType.isTakenTissue';

    /** the column name for the sex field */
    const SEX = 'EventType.sex';

    /** the column name for the age field */
    const AGE = 'EventType.age';

    /** the column name for the rbMedicalKind_id field */
    const RBMEDICALKIND_ID = 'EventType.rbMedicalKind_id';

    /** the column name for the age_bu field */
    const AGE_BU = 'EventType.age_bu';

    /** the column name for the age_bc field */
    const AGE_BC = 'EventType.age_bc';

    /** the column name for the age_eu field */
    const AGE_EU = 'EventType.age_eu';

    /** the column name for the age_ec field */
    const AGE_EC = 'EventType.age_ec';

    /** the column name for the requestType_id field */
    const REQUESTTYPE_ID = 'EventType.requestType_id';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of EventType objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array EventType[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. EventTypePeer::$fieldNames[EventTypePeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'code', 'name', 'purposeId', 'financeId', 'sceneId', 'visitServiceModifier', 'visitServiceFilter', 'visitFinance', 'actionFinance', 'period', 'singleinPeriod', 'isLong', 'dateInput', 'serviceId', 'context', 'form', 'minDuration', 'maxDuration', 'showStatusActionsInPlanner', 'showDiagnosticActionsInPlanner', 'showCureActionsInPlanner', 'showMiscActionsInPlanner', 'limitStatusActionsInput', 'limitDiagnosticActionsInput', 'limitCureActionsInput', 'limitMiscActionsInput', 'showTime', 'medicalAidTypeId', 'eventProfileId', 'mesRequired', 'mesCodeMask', 'mesNameMask', 'counterId', 'isExternal', 'isAssistant', 'isCurator', 'canHavePayableActions', 'isRequiredCoordination', 'isOrgStructurePriority', 'isTakenTissue', 'sex', 'age', 'rbMedicalKindId', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'requestTypeId', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'code', 'name', 'purposeId', 'financeId', 'sceneId', 'visitServiceModifier', 'visitServiceFilter', 'visitFinance', 'actionFinance', 'period', 'singleinPeriod', 'isLong', 'dateInput', 'serviceId', 'context', 'form', 'minDuration', 'maxDuration', 'showStatusActionsInPlanner', 'showDiagnosticActionsInPlanner', 'showCureActionsInPlanner', 'showMiscActionsInPlanner', 'limitStatusActionsInput', 'limitDiagnosticActionsInput', 'limitCureActionsInput', 'limitMiscActionsInput', 'showTime', 'medicalAidTypeId', 'eventProfileId', 'mesRequired', 'mesCodeMask', 'mesNameMask', 'counterId', 'isExternal', 'isAssistant', 'isCurator', 'canHavePayableActions', 'isRequiredCoordination', 'isOrgStructurePriority', 'isTakenTissue', 'sex', 'age', 'rbMedicalKindId', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'requestTypeId', ),
        BasePeer::TYPE_COLNAME => array (EventTypePeer::ID, EventTypePeer::CREATEDATETIME, EventTypePeer::CREATEPERSON_ID, EventTypePeer::MODIFYDATETIME, EventTypePeer::MODIFYPERSON_ID, EventTypePeer::DELETED, EventTypePeer::CODE, EventTypePeer::NAME, EventTypePeer::PURPOSE_ID, EventTypePeer::FINANCE_ID, EventTypePeer::SCENE_ID, EventTypePeer::VISITSERVICEMODIFIER, EventTypePeer::VISITSERVICEFILTER, EventTypePeer::VISITFINANCE, EventTypePeer::ACTIONFINANCE, EventTypePeer::PERIOD, EventTypePeer::SINGLEINPERIOD, EventTypePeer::ISLONG, EventTypePeer::DATEINPUT, EventTypePeer::SERVICE_ID, EventTypePeer::CONTEXT, EventTypePeer::FORM, EventTypePeer::MINDURATION, EventTypePeer::MAXDURATION, EventTypePeer::SHOWSTATUSACTIONSINPLANNER, EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER, EventTypePeer::SHOWCUREACTIONSINPLANNER, EventTypePeer::SHOWMISCACTIONSINPLANNER, EventTypePeer::LIMITSTATUSACTIONSINPUT, EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT, EventTypePeer::LIMITCUREACTIONSINPUT, EventTypePeer::LIMITMISCACTIONSINPUT, EventTypePeer::SHOWTIME, EventTypePeer::MEDICALAIDTYPE_ID, EventTypePeer::EVENTPROFILE_ID, EventTypePeer::MESREQUIRED, EventTypePeer::MESCODEMASK, EventTypePeer::MESNAMEMASK, EventTypePeer::COUNTER_ID, EventTypePeer::ISEXTERNAL, EventTypePeer::ISASSISTANT, EventTypePeer::ISCURATOR, EventTypePeer::CANHAVEPAYABLEACTIONS, EventTypePeer::ISREQUIREDCOORDINATION, EventTypePeer::ISORGSTRUCTUREPRIORITY, EventTypePeer::ISTAKENTISSUE, EventTypePeer::SEX, EventTypePeer::AGE, EventTypePeer::RBMEDICALKIND_ID, EventTypePeer::AGE_BU, EventTypePeer::AGE_BC, EventTypePeer::AGE_EU, EventTypePeer::AGE_EC, EventTypePeer::REQUESTTYPE_ID, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'CODE', 'NAME', 'PURPOSE_ID', 'FINANCE_ID', 'SCENE_ID', 'VISITSERVICEMODIFIER', 'VISITSERVICEFILTER', 'VISITFINANCE', 'ACTIONFINANCE', 'PERIOD', 'SINGLEINPERIOD', 'ISLONG', 'DATEINPUT', 'SERVICE_ID', 'CONTEXT', 'FORM', 'MINDURATION', 'MAXDURATION', 'SHOWSTATUSACTIONSINPLANNER', 'SHOWDIAGNOSTICACTIONSINPLANNER', 'SHOWCUREACTIONSINPLANNER', 'SHOWMISCACTIONSINPLANNER', 'LIMITSTATUSACTIONSINPUT', 'LIMITDIAGNOSTICACTIONSINPUT', 'LIMITCUREACTIONSINPUT', 'LIMITMISCACTIONSINPUT', 'SHOWTIME', 'MEDICALAIDTYPE_ID', 'EVENTPROFILE_ID', 'MESREQUIRED', 'MESCODEMASK', 'MESNAMEMASK', 'COUNTER_ID', 'ISEXTERNAL', 'ISASSISTANT', 'ISCURATOR', 'CANHAVEPAYABLEACTIONS', 'ISREQUIREDCOORDINATION', 'ISORGSTRUCTUREPRIORITY', 'ISTAKENTISSUE', 'SEX', 'AGE', 'RBMEDICALKIND_ID', 'AGE_BU', 'AGE_BC', 'AGE_EU', 'AGE_EC', 'REQUESTTYPE_ID', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'code', 'name', 'purpose_id', 'finance_id', 'scene_id', 'visitServiceModifier', 'visitServiceFilter', 'visitFinance', 'actionFinance', 'period', 'singleInPeriod', 'isLong', 'dateInput', 'service_id', 'context', 'form', 'minDuration', 'maxDuration', 'showStatusActionsInPlanner', 'showDiagnosticActionsInPlanner', 'showCureActionsInPlanner', 'showMiscActionsInPlanner', 'limitStatusActionsInput', 'limitDiagnosticActionsInput', 'limitCureActionsInput', 'limitMiscActionsInput', 'showTime', 'medicalAidType_id', 'eventProfile_id', 'mesRequired', 'mesCodeMask', 'mesNameMask', 'counter_id', 'isExternal', 'isAssistant', 'isCurator', 'canHavePayableActions', 'isRequiredCoordination', 'isOrgStructurePriority', 'isTakenTissue', 'sex', 'age', 'rbMedicalKind_id', 'age_bu', 'age_bc', 'age_eu', 'age_ec', 'requestType_id', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. EventTypePeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'code' => 6, 'name' => 7, 'purposeId' => 8, 'financeId' => 9, 'sceneId' => 10, 'visitServiceModifier' => 11, 'visitServiceFilter' => 12, 'visitFinance' => 13, 'actionFinance' => 14, 'period' => 15, 'singleinPeriod' => 16, 'isLong' => 17, 'dateInput' => 18, 'serviceId' => 19, 'context' => 20, 'form' => 21, 'minDuration' => 22, 'maxDuration' => 23, 'showStatusActionsInPlanner' => 24, 'showDiagnosticActionsInPlanner' => 25, 'showCureActionsInPlanner' => 26, 'showMiscActionsInPlanner' => 27, 'limitStatusActionsInput' => 28, 'limitDiagnosticActionsInput' => 29, 'limitCureActionsInput' => 30, 'limitMiscActionsInput' => 31, 'showTime' => 32, 'medicalAidTypeId' => 33, 'eventProfileId' => 34, 'mesRequired' => 35, 'mesCodeMask' => 36, 'mesNameMask' => 37, 'counterId' => 38, 'isExternal' => 39, 'isAssistant' => 40, 'isCurator' => 41, 'canHavePayableActions' => 42, 'isRequiredCoordination' => 43, 'isOrgStructurePriority' => 44, 'isTakenTissue' => 45, 'sex' => 46, 'age' => 47, 'rbMedicalKindId' => 48, 'ageBu' => 49, 'ageBc' => 50, 'ageEu' => 51, 'ageEc' => 52, 'requestTypeId' => 53, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'code' => 6, 'name' => 7, 'purposeId' => 8, 'financeId' => 9, 'sceneId' => 10, 'visitServiceModifier' => 11, 'visitServiceFilter' => 12, 'visitFinance' => 13, 'actionFinance' => 14, 'period' => 15, 'singleinPeriod' => 16, 'isLong' => 17, 'dateInput' => 18, 'serviceId' => 19, 'context' => 20, 'form' => 21, 'minDuration' => 22, 'maxDuration' => 23, 'showStatusActionsInPlanner' => 24, 'showDiagnosticActionsInPlanner' => 25, 'showCureActionsInPlanner' => 26, 'showMiscActionsInPlanner' => 27, 'limitStatusActionsInput' => 28, 'limitDiagnosticActionsInput' => 29, 'limitCureActionsInput' => 30, 'limitMiscActionsInput' => 31, 'showTime' => 32, 'medicalAidTypeId' => 33, 'eventProfileId' => 34, 'mesRequired' => 35, 'mesCodeMask' => 36, 'mesNameMask' => 37, 'counterId' => 38, 'isExternal' => 39, 'isAssistant' => 40, 'isCurator' => 41, 'canHavePayableActions' => 42, 'isRequiredCoordination' => 43, 'isOrgStructurePriority' => 44, 'isTakenTissue' => 45, 'sex' => 46, 'age' => 47, 'rbMedicalKindId' => 48, 'ageBu' => 49, 'ageBc' => 50, 'ageEu' => 51, 'ageEc' => 52, 'requestTypeId' => 53, ),
        BasePeer::TYPE_COLNAME => array (EventTypePeer::ID => 0, EventTypePeer::CREATEDATETIME => 1, EventTypePeer::CREATEPERSON_ID => 2, EventTypePeer::MODIFYDATETIME => 3, EventTypePeer::MODIFYPERSON_ID => 4, EventTypePeer::DELETED => 5, EventTypePeer::CODE => 6, EventTypePeer::NAME => 7, EventTypePeer::PURPOSE_ID => 8, EventTypePeer::FINANCE_ID => 9, EventTypePeer::SCENE_ID => 10, EventTypePeer::VISITSERVICEMODIFIER => 11, EventTypePeer::VISITSERVICEFILTER => 12, EventTypePeer::VISITFINANCE => 13, EventTypePeer::ACTIONFINANCE => 14, EventTypePeer::PERIOD => 15, EventTypePeer::SINGLEINPERIOD => 16, EventTypePeer::ISLONG => 17, EventTypePeer::DATEINPUT => 18, EventTypePeer::SERVICE_ID => 19, EventTypePeer::CONTEXT => 20, EventTypePeer::FORM => 21, EventTypePeer::MINDURATION => 22, EventTypePeer::MAXDURATION => 23, EventTypePeer::SHOWSTATUSACTIONSINPLANNER => 24, EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER => 25, EventTypePeer::SHOWCUREACTIONSINPLANNER => 26, EventTypePeer::SHOWMISCACTIONSINPLANNER => 27, EventTypePeer::LIMITSTATUSACTIONSINPUT => 28, EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT => 29, EventTypePeer::LIMITCUREACTIONSINPUT => 30, EventTypePeer::LIMITMISCACTIONSINPUT => 31, EventTypePeer::SHOWTIME => 32, EventTypePeer::MEDICALAIDTYPE_ID => 33, EventTypePeer::EVENTPROFILE_ID => 34, EventTypePeer::MESREQUIRED => 35, EventTypePeer::MESCODEMASK => 36, EventTypePeer::MESNAMEMASK => 37, EventTypePeer::COUNTER_ID => 38, EventTypePeer::ISEXTERNAL => 39, EventTypePeer::ISASSISTANT => 40, EventTypePeer::ISCURATOR => 41, EventTypePeer::CANHAVEPAYABLEACTIONS => 42, EventTypePeer::ISREQUIREDCOORDINATION => 43, EventTypePeer::ISORGSTRUCTUREPRIORITY => 44, EventTypePeer::ISTAKENTISSUE => 45, EventTypePeer::SEX => 46, EventTypePeer::AGE => 47, EventTypePeer::RBMEDICALKIND_ID => 48, EventTypePeer::AGE_BU => 49, EventTypePeer::AGE_BC => 50, EventTypePeer::AGE_EU => 51, EventTypePeer::AGE_EC => 52, EventTypePeer::REQUESTTYPE_ID => 53, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'CODE' => 6, 'NAME' => 7, 'PURPOSE_ID' => 8, 'FINANCE_ID' => 9, 'SCENE_ID' => 10, 'VISITSERVICEMODIFIER' => 11, 'VISITSERVICEFILTER' => 12, 'VISITFINANCE' => 13, 'ACTIONFINANCE' => 14, 'PERIOD' => 15, 'SINGLEINPERIOD' => 16, 'ISLONG' => 17, 'DATEINPUT' => 18, 'SERVICE_ID' => 19, 'CONTEXT' => 20, 'FORM' => 21, 'MINDURATION' => 22, 'MAXDURATION' => 23, 'SHOWSTATUSACTIONSINPLANNER' => 24, 'SHOWDIAGNOSTICACTIONSINPLANNER' => 25, 'SHOWCUREACTIONSINPLANNER' => 26, 'SHOWMISCACTIONSINPLANNER' => 27, 'LIMITSTATUSACTIONSINPUT' => 28, 'LIMITDIAGNOSTICACTIONSINPUT' => 29, 'LIMITCUREACTIONSINPUT' => 30, 'LIMITMISCACTIONSINPUT' => 31, 'SHOWTIME' => 32, 'MEDICALAIDTYPE_ID' => 33, 'EVENTPROFILE_ID' => 34, 'MESREQUIRED' => 35, 'MESCODEMASK' => 36, 'MESNAMEMASK' => 37, 'COUNTER_ID' => 38, 'ISEXTERNAL' => 39, 'ISASSISTANT' => 40, 'ISCURATOR' => 41, 'CANHAVEPAYABLEACTIONS' => 42, 'ISREQUIREDCOORDINATION' => 43, 'ISORGSTRUCTUREPRIORITY' => 44, 'ISTAKENTISSUE' => 45, 'SEX' => 46, 'AGE' => 47, 'RBMEDICALKIND_ID' => 48, 'AGE_BU' => 49, 'AGE_BC' => 50, 'AGE_EU' => 51, 'AGE_EC' => 52, 'REQUESTTYPE_ID' => 53, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'code' => 6, 'name' => 7, 'purpose_id' => 8, 'finance_id' => 9, 'scene_id' => 10, 'visitServiceModifier' => 11, 'visitServiceFilter' => 12, 'visitFinance' => 13, 'actionFinance' => 14, 'period' => 15, 'singleInPeriod' => 16, 'isLong' => 17, 'dateInput' => 18, 'service_id' => 19, 'context' => 20, 'form' => 21, 'minDuration' => 22, 'maxDuration' => 23, 'showStatusActionsInPlanner' => 24, 'showDiagnosticActionsInPlanner' => 25, 'showCureActionsInPlanner' => 26, 'showMiscActionsInPlanner' => 27, 'limitStatusActionsInput' => 28, 'limitDiagnosticActionsInput' => 29, 'limitCureActionsInput' => 30, 'limitMiscActionsInput' => 31, 'showTime' => 32, 'medicalAidType_id' => 33, 'eventProfile_id' => 34, 'mesRequired' => 35, 'mesCodeMask' => 36, 'mesNameMask' => 37, 'counter_id' => 38, 'isExternal' => 39, 'isAssistant' => 40, 'isCurator' => 41, 'canHavePayableActions' => 42, 'isRequiredCoordination' => 43, 'isOrgStructurePriority' => 44, 'isTakenTissue' => 45, 'sex' => 46, 'age' => 47, 'rbMedicalKind_id' => 48, 'age_bu' => 49, 'age_bc' => 50, 'age_eu' => 51, 'age_ec' => 52, 'requestType_id' => 53, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, )
    );

    /**
     * Translates a fieldname to another type
     *
     * @param      string $name field name
     * @param      string $fromType One of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *                         BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM
     * @param      string $toType   One of the class type constants
     * @return string          translated name of the field.
     * @throws PropelException - if the specified name could not be found in the fieldname mappings.
     */
    public static function translateFieldName($name, $fromType, $toType)
    {
        $toNames = EventTypePeer::getFieldNames($toType);
        $key = isset(EventTypePeer::$fieldKeys[$fromType][$name]) ? EventTypePeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(EventTypePeer::$fieldKeys[$fromType], true));
        }

        return $toNames[$key];
    }

    /**
     * Returns an array of field names.
     *
     * @param      string $type The type of fieldnames to return:
     *                      One of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *                      BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM
     * @return array           A list of field names
     * @throws PropelException - if the type is not valid.
     */
    public static function getFieldNames($type = BasePeer::TYPE_PHPNAME)
    {
        if (!array_key_exists($type, EventTypePeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return EventTypePeer::$fieldNames[$type];
    }

    /**
     * Convenience method which changes table.column to alias.column.
     *
     * Using this method you can maintain SQL abstraction while using column aliases.
     * <code>
     *		$c->addAlias("alias1", TablePeer::TABLE_NAME);
     *		$c->addJoin(TablePeer::alias("alias1", TablePeer::PRIMARY_KEY_COLUMN), TablePeer::PRIMARY_KEY_COLUMN);
     * </code>
     * @param      string $alias The alias for the current table.
     * @param      string $column The column name for current table. (i.e. EventTypePeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(EventTypePeer::TABLE_NAME.'.', $alias.'.', $column);
    }

    /**
     * Add all the columns needed to create a new object.
     *
     * Note: any columns that were marked with lazyLoad="true" in the
     * XML schema will not be added to the select list and only loaded
     * on demand.
     *
     * @param      Criteria $criteria object containing the columns to add.
     * @param      string   $alias    optional table alias
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function addSelectColumns(Criteria $criteria, $alias = null)
    {
        if (null === $alias) {
            $criteria->addSelectColumn(EventTypePeer::ID);
            $criteria->addSelectColumn(EventTypePeer::CREATEDATETIME);
            $criteria->addSelectColumn(EventTypePeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(EventTypePeer::MODIFYDATETIME);
            $criteria->addSelectColumn(EventTypePeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(EventTypePeer::DELETED);
            $criteria->addSelectColumn(EventTypePeer::CODE);
            $criteria->addSelectColumn(EventTypePeer::NAME);
            $criteria->addSelectColumn(EventTypePeer::PURPOSE_ID);
            $criteria->addSelectColumn(EventTypePeer::FINANCE_ID);
            $criteria->addSelectColumn(EventTypePeer::SCENE_ID);
            $criteria->addSelectColumn(EventTypePeer::VISITSERVICEMODIFIER);
            $criteria->addSelectColumn(EventTypePeer::VISITSERVICEFILTER);
            $criteria->addSelectColumn(EventTypePeer::VISITFINANCE);
            $criteria->addSelectColumn(EventTypePeer::ACTIONFINANCE);
            $criteria->addSelectColumn(EventTypePeer::PERIOD);
            $criteria->addSelectColumn(EventTypePeer::SINGLEINPERIOD);
            $criteria->addSelectColumn(EventTypePeer::ISLONG);
            $criteria->addSelectColumn(EventTypePeer::DATEINPUT);
            $criteria->addSelectColumn(EventTypePeer::SERVICE_ID);
            $criteria->addSelectColumn(EventTypePeer::CONTEXT);
            $criteria->addSelectColumn(EventTypePeer::FORM);
            $criteria->addSelectColumn(EventTypePeer::MINDURATION);
            $criteria->addSelectColumn(EventTypePeer::MAXDURATION);
            $criteria->addSelectColumn(EventTypePeer::SHOWSTATUSACTIONSINPLANNER);
            $criteria->addSelectColumn(EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER);
            $criteria->addSelectColumn(EventTypePeer::SHOWCUREACTIONSINPLANNER);
            $criteria->addSelectColumn(EventTypePeer::SHOWMISCACTIONSINPLANNER);
            $criteria->addSelectColumn(EventTypePeer::LIMITSTATUSACTIONSINPUT);
            $criteria->addSelectColumn(EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT);
            $criteria->addSelectColumn(EventTypePeer::LIMITCUREACTIONSINPUT);
            $criteria->addSelectColumn(EventTypePeer::LIMITMISCACTIONSINPUT);
            $criteria->addSelectColumn(EventTypePeer::SHOWTIME);
            $criteria->addSelectColumn(EventTypePeer::MEDICALAIDTYPE_ID);
            $criteria->addSelectColumn(EventTypePeer::EVENTPROFILE_ID);
            $criteria->addSelectColumn(EventTypePeer::MESREQUIRED);
            $criteria->addSelectColumn(EventTypePeer::MESCODEMASK);
            $criteria->addSelectColumn(EventTypePeer::MESNAMEMASK);
            $criteria->addSelectColumn(EventTypePeer::COUNTER_ID);
            $criteria->addSelectColumn(EventTypePeer::ISEXTERNAL);
            $criteria->addSelectColumn(EventTypePeer::ISASSISTANT);
            $criteria->addSelectColumn(EventTypePeer::ISCURATOR);
            $criteria->addSelectColumn(EventTypePeer::CANHAVEPAYABLEACTIONS);
            $criteria->addSelectColumn(EventTypePeer::ISREQUIREDCOORDINATION);
            $criteria->addSelectColumn(EventTypePeer::ISORGSTRUCTUREPRIORITY);
            $criteria->addSelectColumn(EventTypePeer::ISTAKENTISSUE);
            $criteria->addSelectColumn(EventTypePeer::SEX);
            $criteria->addSelectColumn(EventTypePeer::AGE);
            $criteria->addSelectColumn(EventTypePeer::RBMEDICALKIND_ID);
            $criteria->addSelectColumn(EventTypePeer::AGE_BU);
            $criteria->addSelectColumn(EventTypePeer::AGE_BC);
            $criteria->addSelectColumn(EventTypePeer::AGE_EU);
            $criteria->addSelectColumn(EventTypePeer::AGE_EC);
            $criteria->addSelectColumn(EventTypePeer::REQUESTTYPE_ID);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.code');
            $criteria->addSelectColumn($alias . '.name');
            $criteria->addSelectColumn($alias . '.purpose_id');
            $criteria->addSelectColumn($alias . '.finance_id');
            $criteria->addSelectColumn($alias . '.scene_id');
            $criteria->addSelectColumn($alias . '.visitServiceModifier');
            $criteria->addSelectColumn($alias . '.visitServiceFilter');
            $criteria->addSelectColumn($alias . '.visitFinance');
            $criteria->addSelectColumn($alias . '.actionFinance');
            $criteria->addSelectColumn($alias . '.period');
            $criteria->addSelectColumn($alias . '.singleInPeriod');
            $criteria->addSelectColumn($alias . '.isLong');
            $criteria->addSelectColumn($alias . '.dateInput');
            $criteria->addSelectColumn($alias . '.service_id');
            $criteria->addSelectColumn($alias . '.context');
            $criteria->addSelectColumn($alias . '.form');
            $criteria->addSelectColumn($alias . '.minDuration');
            $criteria->addSelectColumn($alias . '.maxDuration');
            $criteria->addSelectColumn($alias . '.showStatusActionsInPlanner');
            $criteria->addSelectColumn($alias . '.showDiagnosticActionsInPlanner');
            $criteria->addSelectColumn($alias . '.showCureActionsInPlanner');
            $criteria->addSelectColumn($alias . '.showMiscActionsInPlanner');
            $criteria->addSelectColumn($alias . '.limitStatusActionsInput');
            $criteria->addSelectColumn($alias . '.limitDiagnosticActionsInput');
            $criteria->addSelectColumn($alias . '.limitCureActionsInput');
            $criteria->addSelectColumn($alias . '.limitMiscActionsInput');
            $criteria->addSelectColumn($alias . '.showTime');
            $criteria->addSelectColumn($alias . '.medicalAidType_id');
            $criteria->addSelectColumn($alias . '.eventProfile_id');
            $criteria->addSelectColumn($alias . '.mesRequired');
            $criteria->addSelectColumn($alias . '.mesCodeMask');
            $criteria->addSelectColumn($alias . '.mesNameMask');
            $criteria->addSelectColumn($alias . '.counter_id');
            $criteria->addSelectColumn($alias . '.isExternal');
            $criteria->addSelectColumn($alias . '.isAssistant');
            $criteria->addSelectColumn($alias . '.isCurator');
            $criteria->addSelectColumn($alias . '.canHavePayableActions');
            $criteria->addSelectColumn($alias . '.isRequiredCoordination');
            $criteria->addSelectColumn($alias . '.isOrgStructurePriority');
            $criteria->addSelectColumn($alias . '.isTakenTissue');
            $criteria->addSelectColumn($alias . '.sex');
            $criteria->addSelectColumn($alias . '.age');
            $criteria->addSelectColumn($alias . '.rbMedicalKind_id');
            $criteria->addSelectColumn($alias . '.age_bu');
            $criteria->addSelectColumn($alias . '.age_bc');
            $criteria->addSelectColumn($alias . '.age_eu');
            $criteria->addSelectColumn($alias . '.age_ec');
            $criteria->addSelectColumn($alias . '.requestType_id');
        }
    }

    /**
     * Returns the number of rows matching criteria.
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @return int Number of matching rows.
     */
    public static function doCount(Criteria $criteria, $distinct = false, PropelPDO $con = null)
    {
        // we may modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(EventTypePeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }
        // BasePeer returns a PDOStatement
        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }
    /**
     * Selects one object from the DB.
     *
     * @param      Criteria $criteria object used to create the SELECT statement.
     * @param      PropelPDO $con
     * @return                 EventType
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = EventTypePeer::doSelect($critcopy, $con);
        if ($objects) {
            return $objects[0];
        }

        return null;
    }
    /**
     * Selects several row from the DB.
     *
     * @param      Criteria $criteria The Criteria object used to build the SELECT statement.
     * @param      PropelPDO $con
     * @return array           Array of selected Objects
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelect(Criteria $criteria, PropelPDO $con = null)
    {
        return EventTypePeer::populateObjects(EventTypePeer::doSelectStmt($criteria, $con));
    }
    /**
     * Prepares the Criteria object and uses the parent doSelect() method to execute a PDOStatement.
     *
     * Use this method directly if you want to work with an executed statement directly (for example
     * to perform your own object hydration).
     *
     * @param      Criteria $criteria The Criteria object used to build the SELECT statement.
     * @param      PropelPDO $con The connection to use
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     * @return PDOStatement The executed PDOStatement object.
     * @see        BasePeer::doSelect()
     */
    public static function doSelectStmt(Criteria $criteria, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            EventTypePeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        // BasePeer returns a PDOStatement
        return BasePeer::doSelect($criteria, $con);
    }
    /**
     * Adds an object to the instance pool.
     *
     * Propel keeps cached copies of objects in an instance pool when they are retrieved
     * from the database.  In some cases -- especially when you override doSelect*()
     * methods in your stub classes -- you may need to explicitly add objects
     * to the cache in order to ensure that the same objects are always returned by doSelect*()
     * and retrieveByPK*() calls.
     *
     * @param      EventType $obj A EventType object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            EventTypePeer::$instances[$key] = $obj;
        }
    }

    /**
     * Removes an object from the instance pool.
     *
     * Propel keeps cached copies of objects in an instance pool when they are retrieved
     * from the database.  In some cases -- especially when you override doDelete
     * methods in your stub classes -- you may need to explicitly remove objects
     * from the cache in order to prevent returning objects that no longer exist.
     *
     * @param      mixed $value A EventType object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof EventType) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or EventType object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(EventTypePeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   EventType Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(EventTypePeer::$instances[$key])) {
                return EventTypePeer::$instances[$key];
            }
        }

        return null; // just to be explicit
    }

    /**
     * Clear the instance pool.
     *
     * @return void
     */
    public static function clearInstancePool($and_clear_all_references = false)
    {
      if ($and_clear_all_references)
      {
        foreach (EventTypePeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        EventTypePeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to EventType
     * by a foreign key with ON DELETE CASCADE
     */
    public static function clearRelatedInstancePool()
    {
    }

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @return string A string version of PK or null if the components of primary key in result array are all null.
     */
    public static function getPrimaryKeyHashFromRow($row, $startcol = 0)
    {
        // If the PK cannot be derived from the row, return null.
        if ($row[$startcol] === null) {
            return null;
        }

        return (string) $row[$startcol];
    }

    /**
     * Retrieves the primary key from the DB resultset row
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, an array of the primary key columns will be returned.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @return mixed The primary key of the row
     */
    public static function getPrimaryKeyFromRow($row, $startcol = 0)
    {

        return (int) $row[$startcol];
    }

    /**
     * The returned array will contain objects of the default type or
     * objects that inherit from the default.
     *
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function populateObjects(PDOStatement $stmt)
    {
        $results = array();

        // set the class once to avoid overhead in the loop
        $cls = EventTypePeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = EventTypePeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                EventTypePeer::addInstanceToPool($obj, $key);
            } // if key exists
        }
        $stmt->closeCursor();

        return $results;
    }
    /**
     * Populates an object of the default type or an object that inherit from the default.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     * @return array (EventType object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = EventTypePeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + EventTypePeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = EventTypePeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            EventTypePeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbEventTypePurpose table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinRbEventTypePurpose(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);

        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbResult table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinRbResult(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);

        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }


    /**
     * Selects a collection of EventType objects pre-filled with their RbEventTypePurpose objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of EventType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinRbEventTypePurpose(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventTypePeer::DATABASE_NAME);
        }

        EventTypePeer::addSelectColumns($criteria);
        $startcol = EventTypePeer::NUM_HYDRATE_COLUMNS;
        RbEventTypePurposePeer::addSelectColumns($criteria);

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventTypePeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = RbEventTypePurposePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = RbEventTypePurposePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbEventTypePurposePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    RbEventTypePurposePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (EventType) to $obj2 (RbEventTypePurpose)
                $obj2->addEventType($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of EventType objects pre-filled with their RbResult objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of EventType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinRbResult(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventTypePeer::DATABASE_NAME);
        }

        EventTypePeer::addSelectColumns($criteria);
        $startcol = EventTypePeer::NUM_HYDRATE_COLUMNS;
        RbResultPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventTypePeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = RbResultPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = RbResultPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbResultPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    RbResultPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (EventType) to $obj2 (RbResult)
                $obj2->addEventType($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining all related tables
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAll(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);

        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }

    /**
     * Selects a collection of EventType objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of EventType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventTypePeer::DATABASE_NAME);
        }

        EventTypePeer::addSelectColumns($criteria);
        $startcol2 = EventTypePeer::NUM_HYDRATE_COLUMNS;

        RbEventTypePurposePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbEventTypePurposePeer::NUM_HYDRATE_COLUMNS;

        RbResultPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + RbResultPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventTypePeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined RbEventTypePurpose rows

            $key2 = RbEventTypePurposePeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = RbEventTypePurposePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbEventTypePurposePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbEventTypePurposePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (EventType) to the collection in $obj2 (RbEventTypePurpose)
                $obj2->addEventType($obj1);
            } // if joined row not null

            // Add objects for joined RbResult rows

            $key3 = RbResultPeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = RbResultPeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = RbResultPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    RbResultPeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (EventType) to the collection in $obj3 (RbResult)
                $obj3->addEventType($obj1);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbEventTypePurpose table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptRbEventTypePurpose(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);

        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbResult table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptRbResult(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);

        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }


    /**
     * Selects a collection of EventType objects pre-filled with all related objects except RbEventTypePurpose.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of EventType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptRbEventTypePurpose(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventTypePeer::DATABASE_NAME);
        }

        EventTypePeer::addSelectColumns($criteria);
        $startcol2 = EventTypePeer::NUM_HYDRATE_COLUMNS;

        RbResultPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbResultPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbResultPeer::EVENTPURPOSE_ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventTypePeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined RbResult rows

                $key2 = RbResultPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = RbResultPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = RbResultPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbResultPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (EventType) to the collection in $obj2 (RbResult)
                $obj2->addEventType($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of EventType objects pre-filled with all related objects except RbResult.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of EventType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptRbResult(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventTypePeer::DATABASE_NAME);
        }

        EventTypePeer::addSelectColumns($criteria);
        $startcol2 = EventTypePeer::NUM_HYDRATE_COLUMNS;

        RbEventTypePurposePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbEventTypePurposePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventTypePeer::PURPOSE_ID, RbEventTypePurposePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventTypePeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined RbEventTypePurpose rows

                $key2 = RbEventTypePurposePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = RbEventTypePurposePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = RbEventTypePurposePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbEventTypePurposePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (EventType) to the collection in $obj2 (RbEventTypePurpose)
                $obj2->addEventType($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }

    /**
     * Returns the TableMap related to this peer.
     * This method is not needed for general use but a specific application could have a need.
     * @return TableMap
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function getTableMap()
    {
        return Propel::getDatabaseMap(EventTypePeer::DATABASE_NAME)->getTable(EventTypePeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseEventTypePeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseEventTypePeer::TABLE_NAME)) {
        $dbMap->addTableObject(new EventTypeTableMap());
      }
    }

    /**
     * The class that the Peer will make instances of.
     *
     *
     * @return string ClassName
     */
    public static function getOMClass($row = 0, $colnum = 0)
    {
        return EventTypePeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a EventType or Criteria object.
     *
     * @param      mixed $values Criteria or EventType object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from EventType object
        }

        if ($criteria->containsKey(EventTypePeer::ID) && $criteria->keyContainsValue(EventTypePeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.EventTypePeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        try {
            // use transaction because $criteria could contain info
            // for more than one table (I guess, conceivably)
            $con->beginTransaction();
            $pk = BasePeer::doInsert($criteria, $con);
            $con->commit();
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }

        return $pk;
    }

    /**
     * Performs an UPDATE on the database, given a EventType or Criteria object.
     *
     * @param      mixed $values Criteria or EventType object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(EventTypePeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(EventTypePeer::ID);
            $value = $criteria->remove(EventTypePeer::ID);
            if ($value) {
                $selectCriteria->add(EventTypePeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(EventTypePeer::TABLE_NAME);
            }

        } else { // $values is EventType object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the EventType table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(EventTypePeer::TABLE_NAME, $con, EventTypePeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            EventTypePeer::clearInstancePool();
            EventTypePeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a EventType or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or EventType object or primary key or array of primary keys
     *              which is used to create the DELETE statement
     * @param      PropelPDO $con the connection to use
     * @return int The number of affected rows (if supported by underlying database driver).  This includes CASCADE-related rows
     *				if supported by native driver or if emulated using Propel.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
     public static function doDelete($values, PropelPDO $con = null)
     {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            EventTypePeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof EventType) { // it's a model object
            // invalidate the cache for this single object
            EventTypePeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(EventTypePeer::DATABASE_NAME);
            $criteria->add(EventTypePeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                EventTypePeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(EventTypePeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            EventTypePeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given EventType object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      EventType $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(EventTypePeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(EventTypePeer::TABLE_NAME);

            if (! is_array($cols)) {
                $cols = array($cols);
            }

            foreach ($cols as $colName) {
                if ($tableMap->hasColumn($colName)) {
                    $get = 'get' . $tableMap->getColumn($colName)->getPhpName();
                    $columns[$colName] = $obj->$get();
                }
            }
        } else {

        }

        return BasePeer::doValidate(EventTypePeer::DATABASE_NAME, EventTypePeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return EventType
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = EventTypePeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(EventTypePeer::DATABASE_NAME);
        $criteria->add(EventTypePeer::ID, $pk);

        $v = EventTypePeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return EventType[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(EventTypePeer::DATABASE_NAME);
            $criteria->add(EventTypePeer::ID, $pks, Criteria::IN);
            $objs = EventTypePeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseEventTypePeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseEventTypePeer::buildTableMap();

