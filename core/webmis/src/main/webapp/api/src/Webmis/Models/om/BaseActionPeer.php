<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\Action;
use Webmis\Models\ActionPeer;
use Webmis\Models\ActionTypePeer;
use Webmis\Models\EventPeer;
use Webmis\Models\PersonPeer;
use Webmis\Models\map\ActionTableMap;

/**
 * Base static class for performing query and update operations on the 'Action' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseActionPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'Action';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\Action';

    /** the related TableMap class for this table */
    const TM_CLASS = 'ActionTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 39;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 39;

    /** the column name for the id field */
    const ID = 'Action.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'Action.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'Action.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'Action.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'Action.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'Action.deleted';

    /** the column name for the actionType_id field */
    const ACTIONTYPE_ID = 'Action.actionType_id';

    /** the column name for the event_id field */
    const EVENT_ID = 'Action.event_id';

    /** the column name for the idx field */
    const IDX = 'Action.idx';

    /** the column name for the directionDate field */
    const DIRECTIONDATE = 'Action.directionDate';

    /** the column name for the status field */
    const STATUS = 'Action.status';

    /** the column name for the setPerson_id field */
    const SETPERSON_ID = 'Action.setPerson_id';

    /** the column name for the isUrgent field */
    const ISURGENT = 'Action.isUrgent';

    /** the column name for the begDate field */
    const BEGDATE = 'Action.begDate';

    /** the column name for the plannedEndDate field */
    const PLANNEDENDDATE = 'Action.plannedEndDate';

    /** the column name for the endDate field */
    const ENDDATE = 'Action.endDate';

    /** the column name for the note field */
    const NOTE = 'Action.note';

    /** the column name for the person_id field */
    const PERSON_ID = 'Action.person_id';

    /** the column name for the office field */
    const OFFICE = 'Action.office';

    /** the column name for the amount field */
    const AMOUNT = 'Action.amount';

    /** the column name for the uet field */
    const UET = 'Action.uet';

    /** the column name for the expose field */
    const EXPOSE = 'Action.expose';

    /** the column name for the payStatus field */
    const PAYSTATUS = 'Action.payStatus';

    /** the column name for the account field */
    const ACCOUNT = 'Action.account';

    /** the column name for the finance_id field */
    const FINANCE_ID = 'Action.finance_id';

    /** the column name for the prescription_id field */
    const PRESCRIPTION_ID = 'Action.prescription_id';

    /** the column name for the takenTissueJournal_id field */
    const TAKENTISSUEJOURNAL_ID = 'Action.takenTissueJournal_id';

    /** the column name for the contract_id field */
    const CONTRACT_ID = 'Action.contract_id';

    /** the column name for the coordDate field */
    const COORDDATE = 'Action.coordDate';

    /** the column name for the coordAgent field */
    const COORDAGENT = 'Action.coordAgent';

    /** the column name for the coordInspector field */
    const COORDINSPECTOR = 'Action.coordInspector';

    /** the column name for the coordText field */
    const COORDTEXT = 'Action.coordText';

    /** the column name for the hospitalUidFrom field */
    const HOSPITALUIDFROM = 'Action.hospitalUidFrom';

    /** the column name for the pacientInQueueType field */
    const PACIENTINQUEUETYPE = 'Action.pacientInQueueType';

    /** the column name for the AppointmentType field */
    const APPOINTMENTTYPE = 'Action.AppointmentType';

    /** the column name for the version field */
    const VERSION = 'Action.version';

    /** the column name for the parentAction_id field */
    const PARENTACTION_ID = 'Action.parentAction_id';

    /** the column name for the uuid_id field */
    const UUID_ID = 'Action.uuid_id';

    /** the column name for the dcm_study_uid field */
    const DCM_STUDY_UID = 'Action.dcm_study_uid';

    /** The enumerated values for the AppointmentType field */
    const APPOINTMENTTYPE_0 = '0';
    const APPOINTMENTTYPE_AMB = 'amb';
    const APPOINTMENTTYPE_HOSPITAL = 'hospital';
    const APPOINTMENTTYPE_POLYCLINIC = 'polyclinic';
    const APPOINTMENTTYPE_DIAGNOSTICS = 'diagnostics';
    const APPOINTMENTTYPE_PORTAL = 'portal';
    const APPOINTMENTTYPE_OTHERLPU = 'otherLPU';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of Action objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array Action[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. ActionPeer::$fieldNames[ActionPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'actionTypeId', 'eventId', 'idx', 'directionDate', 'status', 'setPersonId', 'isUrgent', 'begDate', 'plannedEndDate', 'endDate', 'note', 'personId', 'office', 'amount', 'uet', 'expose', 'payStatus', 'account', 'financeId', 'prescriptionId', 'takenTissueJournalId', 'contractId', 'coordDate', 'coordAgent', 'coordInspector', 'coordText', 'hospitalUidFrom', 'pacientInQueueType', 'appointmentType', 'version', 'parentActionId', 'uuidId', 'dcmStudyUid', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'actionTypeId', 'eventId', 'idx', 'directionDate', 'status', 'setPersonId', 'isUrgent', 'begDate', 'plannedEndDate', 'endDate', 'note', 'personId', 'office', 'amount', 'uet', 'expose', 'payStatus', 'account', 'financeId', 'prescriptionId', 'takenTissueJournalId', 'contractId', 'coordDate', 'coordAgent', 'coordInspector', 'coordText', 'hospitalUidFrom', 'pacientInQueueType', 'appointmentType', 'version', 'parentActionId', 'uuidId', 'dcmStudyUid', ),
        BasePeer::TYPE_COLNAME => array (ActionPeer::ID, ActionPeer::CREATEDATETIME, ActionPeer::CREATEPERSON_ID, ActionPeer::MODIFYDATETIME, ActionPeer::MODIFYPERSON_ID, ActionPeer::DELETED, ActionPeer::ACTIONTYPE_ID, ActionPeer::EVENT_ID, ActionPeer::IDX, ActionPeer::DIRECTIONDATE, ActionPeer::STATUS, ActionPeer::SETPERSON_ID, ActionPeer::ISURGENT, ActionPeer::BEGDATE, ActionPeer::PLANNEDENDDATE, ActionPeer::ENDDATE, ActionPeer::NOTE, ActionPeer::PERSON_ID, ActionPeer::OFFICE, ActionPeer::AMOUNT, ActionPeer::UET, ActionPeer::EXPOSE, ActionPeer::PAYSTATUS, ActionPeer::ACCOUNT, ActionPeer::FINANCE_ID, ActionPeer::PRESCRIPTION_ID, ActionPeer::TAKENTISSUEJOURNAL_ID, ActionPeer::CONTRACT_ID, ActionPeer::COORDDATE, ActionPeer::COORDAGENT, ActionPeer::COORDINSPECTOR, ActionPeer::COORDTEXT, ActionPeer::HOSPITALUIDFROM, ActionPeer::PACIENTINQUEUETYPE, ActionPeer::APPOINTMENTTYPE, ActionPeer::VERSION, ActionPeer::PARENTACTION_ID, ActionPeer::UUID_ID, ActionPeer::DCM_STUDY_UID, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'ACTIONTYPE_ID', 'EVENT_ID', 'IDX', 'DIRECTIONDATE', 'STATUS', 'SETPERSON_ID', 'ISURGENT', 'BEGDATE', 'PLANNEDENDDATE', 'ENDDATE', 'NOTE', 'PERSON_ID', 'OFFICE', 'AMOUNT', 'UET', 'EXPOSE', 'PAYSTATUS', 'ACCOUNT', 'FINANCE_ID', 'PRESCRIPTION_ID', 'TAKENTISSUEJOURNAL_ID', 'CONTRACT_ID', 'COORDDATE', 'COORDAGENT', 'COORDINSPECTOR', 'COORDTEXT', 'HOSPITALUIDFROM', 'PACIENTINQUEUETYPE', 'APPOINTMENTTYPE', 'VERSION', 'PARENTACTION_ID', 'UUID_ID', 'DCM_STUDY_UID', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'actionType_id', 'event_id', 'idx', 'directionDate', 'status', 'setPerson_id', 'isUrgent', 'begDate', 'plannedEndDate', 'endDate', 'note', 'person_id', 'office', 'amount', 'uet', 'expose', 'payStatus', 'account', 'finance_id', 'prescription_id', 'takenTissueJournal_id', 'contract_id', 'coordDate', 'coordAgent', 'coordInspector', 'coordText', 'hospitalUidFrom', 'pacientInQueueType', 'AppointmentType', 'version', 'parentAction_id', 'uuid_id', 'dcm_study_uid', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. ActionPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'actionTypeId' => 6, 'eventId' => 7, 'idx' => 8, 'directionDate' => 9, 'status' => 10, 'setPersonId' => 11, 'isUrgent' => 12, 'begDate' => 13, 'plannedEndDate' => 14, 'endDate' => 15, 'note' => 16, 'personId' => 17, 'office' => 18, 'amount' => 19, 'uet' => 20, 'expose' => 21, 'payStatus' => 22, 'account' => 23, 'financeId' => 24, 'prescriptionId' => 25, 'takenTissueJournalId' => 26, 'contractId' => 27, 'coordDate' => 28, 'coordAgent' => 29, 'coordInspector' => 30, 'coordText' => 31, 'hospitalUidFrom' => 32, 'pacientInQueueType' => 33, 'appointmentType' => 34, 'version' => 35, 'parentActionId' => 36, 'uuidId' => 37, 'dcmStudyUid' => 38, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'actionTypeId' => 6, 'eventId' => 7, 'idx' => 8, 'directionDate' => 9, 'status' => 10, 'setPersonId' => 11, 'isUrgent' => 12, 'begDate' => 13, 'plannedEndDate' => 14, 'endDate' => 15, 'note' => 16, 'personId' => 17, 'office' => 18, 'amount' => 19, 'uet' => 20, 'expose' => 21, 'payStatus' => 22, 'account' => 23, 'financeId' => 24, 'prescriptionId' => 25, 'takenTissueJournalId' => 26, 'contractId' => 27, 'coordDate' => 28, 'coordAgent' => 29, 'coordInspector' => 30, 'coordText' => 31, 'hospitalUidFrom' => 32, 'pacientInQueueType' => 33, 'appointmentType' => 34, 'version' => 35, 'parentActionId' => 36, 'uuidId' => 37, 'dcmStudyUid' => 38, ),
        BasePeer::TYPE_COLNAME => array (ActionPeer::ID => 0, ActionPeer::CREATEDATETIME => 1, ActionPeer::CREATEPERSON_ID => 2, ActionPeer::MODIFYDATETIME => 3, ActionPeer::MODIFYPERSON_ID => 4, ActionPeer::DELETED => 5, ActionPeer::ACTIONTYPE_ID => 6, ActionPeer::EVENT_ID => 7, ActionPeer::IDX => 8, ActionPeer::DIRECTIONDATE => 9, ActionPeer::STATUS => 10, ActionPeer::SETPERSON_ID => 11, ActionPeer::ISURGENT => 12, ActionPeer::BEGDATE => 13, ActionPeer::PLANNEDENDDATE => 14, ActionPeer::ENDDATE => 15, ActionPeer::NOTE => 16, ActionPeer::PERSON_ID => 17, ActionPeer::OFFICE => 18, ActionPeer::AMOUNT => 19, ActionPeer::UET => 20, ActionPeer::EXPOSE => 21, ActionPeer::PAYSTATUS => 22, ActionPeer::ACCOUNT => 23, ActionPeer::FINANCE_ID => 24, ActionPeer::PRESCRIPTION_ID => 25, ActionPeer::TAKENTISSUEJOURNAL_ID => 26, ActionPeer::CONTRACT_ID => 27, ActionPeer::COORDDATE => 28, ActionPeer::COORDAGENT => 29, ActionPeer::COORDINSPECTOR => 30, ActionPeer::COORDTEXT => 31, ActionPeer::HOSPITALUIDFROM => 32, ActionPeer::PACIENTINQUEUETYPE => 33, ActionPeer::APPOINTMENTTYPE => 34, ActionPeer::VERSION => 35, ActionPeer::PARENTACTION_ID => 36, ActionPeer::UUID_ID => 37, ActionPeer::DCM_STUDY_UID => 38, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'ACTIONTYPE_ID' => 6, 'EVENT_ID' => 7, 'IDX' => 8, 'DIRECTIONDATE' => 9, 'STATUS' => 10, 'SETPERSON_ID' => 11, 'ISURGENT' => 12, 'BEGDATE' => 13, 'PLANNEDENDDATE' => 14, 'ENDDATE' => 15, 'NOTE' => 16, 'PERSON_ID' => 17, 'OFFICE' => 18, 'AMOUNT' => 19, 'UET' => 20, 'EXPOSE' => 21, 'PAYSTATUS' => 22, 'ACCOUNT' => 23, 'FINANCE_ID' => 24, 'PRESCRIPTION_ID' => 25, 'TAKENTISSUEJOURNAL_ID' => 26, 'CONTRACT_ID' => 27, 'COORDDATE' => 28, 'COORDAGENT' => 29, 'COORDINSPECTOR' => 30, 'COORDTEXT' => 31, 'HOSPITALUIDFROM' => 32, 'PACIENTINQUEUETYPE' => 33, 'APPOINTMENTTYPE' => 34, 'VERSION' => 35, 'PARENTACTION_ID' => 36, 'UUID_ID' => 37, 'DCM_STUDY_UID' => 38, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'actionType_id' => 6, 'event_id' => 7, 'idx' => 8, 'directionDate' => 9, 'status' => 10, 'setPerson_id' => 11, 'isUrgent' => 12, 'begDate' => 13, 'plannedEndDate' => 14, 'endDate' => 15, 'note' => 16, 'person_id' => 17, 'office' => 18, 'amount' => 19, 'uet' => 20, 'expose' => 21, 'payStatus' => 22, 'account' => 23, 'finance_id' => 24, 'prescription_id' => 25, 'takenTissueJournal_id' => 26, 'contract_id' => 27, 'coordDate' => 28, 'coordAgent' => 29, 'coordInspector' => 30, 'coordText' => 31, 'hospitalUidFrom' => 32, 'pacientInQueueType' => 33, 'AppointmentType' => 34, 'version' => 35, 'parentAction_id' => 36, 'uuid_id' => 37, 'dcm_study_uid' => 38, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, )
    );

    /** The enumerated values for this table */
    protected static $enumValueSets = array(
        ActionPeer::APPOINTMENTTYPE => array(
            ActionPeer::APPOINTMENTTYPE_0,
            ActionPeer::APPOINTMENTTYPE_AMB,
            ActionPeer::APPOINTMENTTYPE_HOSPITAL,
            ActionPeer::APPOINTMENTTYPE_POLYCLINIC,
            ActionPeer::APPOINTMENTTYPE_DIAGNOSTICS,
            ActionPeer::APPOINTMENTTYPE_PORTAL,
            ActionPeer::APPOINTMENTTYPE_OTHERLPU,
        ),
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
        $toNames = ActionPeer::getFieldNames($toType);
        $key = isset(ActionPeer::$fieldKeys[$fromType][$name]) ? ActionPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(ActionPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, ActionPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return ActionPeer::$fieldNames[$type];
    }

    /**
     * Gets the list of values for all ENUM columns
     * @return array
     */
    public static function getValueSets()
    {
      return ActionPeer::$enumValueSets;
    }

    /**
     * Gets the list of values for an ENUM column
     *
     * @param string $colname The ENUM column name.
     *
     * @return array list of possible values for the column
     */
    public static function getValueSet($colname)
    {
        $valueSets = ActionPeer::getValueSets();

        if (!isset($valueSets[$colname])) {
            throw new PropelException(sprintf('Column "%s" has no ValueSet.', $colname));
        }

        return $valueSets[$colname];
    }

    /**
     * Gets the SQL value for the ENUM column value
     *
     * @param string $colname ENUM column name.
     * @param string $enumVal ENUM value.
     *
     * @return int            SQL value
     */
    public static function getSqlValueForEnum($colname, $enumVal)
    {
        $values = ActionPeer::getValueSet($colname);
        if (!in_array($enumVal, $values)) {
            throw new PropelException(sprintf('Value "%s" is not accepted in this enumerated column', $colname));
        }
        return array_search($enumVal, $values);
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
     * @param      string $column The column name for current table. (i.e. ActionPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(ActionPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(ActionPeer::ID);
            $criteria->addSelectColumn(ActionPeer::CREATEDATETIME);
            $criteria->addSelectColumn(ActionPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(ActionPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(ActionPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(ActionPeer::DELETED);
            $criteria->addSelectColumn(ActionPeer::ACTIONTYPE_ID);
            $criteria->addSelectColumn(ActionPeer::EVENT_ID);
            $criteria->addSelectColumn(ActionPeer::IDX);
            $criteria->addSelectColumn(ActionPeer::DIRECTIONDATE);
            $criteria->addSelectColumn(ActionPeer::STATUS);
            $criteria->addSelectColumn(ActionPeer::SETPERSON_ID);
            $criteria->addSelectColumn(ActionPeer::ISURGENT);
            $criteria->addSelectColumn(ActionPeer::BEGDATE);
            $criteria->addSelectColumn(ActionPeer::PLANNEDENDDATE);
            $criteria->addSelectColumn(ActionPeer::ENDDATE);
            $criteria->addSelectColumn(ActionPeer::NOTE);
            $criteria->addSelectColumn(ActionPeer::PERSON_ID);
            $criteria->addSelectColumn(ActionPeer::OFFICE);
            $criteria->addSelectColumn(ActionPeer::AMOUNT);
            $criteria->addSelectColumn(ActionPeer::UET);
            $criteria->addSelectColumn(ActionPeer::EXPOSE);
            $criteria->addSelectColumn(ActionPeer::PAYSTATUS);
            $criteria->addSelectColumn(ActionPeer::ACCOUNT);
            $criteria->addSelectColumn(ActionPeer::FINANCE_ID);
            $criteria->addSelectColumn(ActionPeer::PRESCRIPTION_ID);
            $criteria->addSelectColumn(ActionPeer::TAKENTISSUEJOURNAL_ID);
            $criteria->addSelectColumn(ActionPeer::CONTRACT_ID);
            $criteria->addSelectColumn(ActionPeer::COORDDATE);
            $criteria->addSelectColumn(ActionPeer::COORDAGENT);
            $criteria->addSelectColumn(ActionPeer::COORDINSPECTOR);
            $criteria->addSelectColumn(ActionPeer::COORDTEXT);
            $criteria->addSelectColumn(ActionPeer::HOSPITALUIDFROM);
            $criteria->addSelectColumn(ActionPeer::PACIENTINQUEUETYPE);
            $criteria->addSelectColumn(ActionPeer::APPOINTMENTTYPE);
            $criteria->addSelectColumn(ActionPeer::VERSION);
            $criteria->addSelectColumn(ActionPeer::PARENTACTION_ID);
            $criteria->addSelectColumn(ActionPeer::UUID_ID);
            $criteria->addSelectColumn(ActionPeer::DCM_STUDY_UID);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.actionType_id');
            $criteria->addSelectColumn($alias . '.event_id');
            $criteria->addSelectColumn($alias . '.idx');
            $criteria->addSelectColumn($alias . '.directionDate');
            $criteria->addSelectColumn($alias . '.status');
            $criteria->addSelectColumn($alias . '.setPerson_id');
            $criteria->addSelectColumn($alias . '.isUrgent');
            $criteria->addSelectColumn($alias . '.begDate');
            $criteria->addSelectColumn($alias . '.plannedEndDate');
            $criteria->addSelectColumn($alias . '.endDate');
            $criteria->addSelectColumn($alias . '.note');
            $criteria->addSelectColumn($alias . '.person_id');
            $criteria->addSelectColumn($alias . '.office');
            $criteria->addSelectColumn($alias . '.amount');
            $criteria->addSelectColumn($alias . '.uet');
            $criteria->addSelectColumn($alias . '.expose');
            $criteria->addSelectColumn($alias . '.payStatus');
            $criteria->addSelectColumn($alias . '.account');
            $criteria->addSelectColumn($alias . '.finance_id');
            $criteria->addSelectColumn($alias . '.prescription_id');
            $criteria->addSelectColumn($alias . '.takenTissueJournal_id');
            $criteria->addSelectColumn($alias . '.contract_id');
            $criteria->addSelectColumn($alias . '.coordDate');
            $criteria->addSelectColumn($alias . '.coordAgent');
            $criteria->addSelectColumn($alias . '.coordInspector');
            $criteria->addSelectColumn($alias . '.coordText');
            $criteria->addSelectColumn($alias . '.hospitalUidFrom');
            $criteria->addSelectColumn($alias . '.pacientInQueueType');
            $criteria->addSelectColumn($alias . '.AppointmentType');
            $criteria->addSelectColumn($alias . '.version');
            $criteria->addSelectColumn($alias . '.parentAction_id');
            $criteria->addSelectColumn($alias . '.uuid_id');
            $criteria->addSelectColumn($alias . '.dcm_study_uid');
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
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(ActionPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Action
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = ActionPeer::doSelect($critcopy, $con);
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
        return ActionPeer::populateObjects(ActionPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            ActionPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

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
     * @param      Action $obj A Action object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            ActionPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A Action object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof Action) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or Action object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(ActionPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   Action Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(ActionPeer::$instances[$key])) {
                return ActionPeer::$instances[$key];
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
        foreach (ActionPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        ActionPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to Action
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
        $cls = ActionPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = ActionPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                ActionPeer::addInstanceToPool($obj, $key);
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
     * @return array (Action object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = ActionPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + ActionPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = ActionPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            ActionPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related Event table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinEvent(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related CreatePerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinCreatePerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ModifyPerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinModifyPerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related SetPerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinSetPerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Selects a collection of Action objects pre-filled with their Event objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinEvent(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol = ActionPeer::NUM_HYDRATE_COLUMNS;
        EventPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = EventPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Action) to $obj2 (Event)
                $obj2->addAction($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinCreatePerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol = ActionPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = PersonPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = PersonPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    PersonPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Action) to $obj2 (Person)
                $obj2->addActionRelatedBycreatePersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinModifyPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol = ActionPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = PersonPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = PersonPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    PersonPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Action) to $obj2 (Person)
                $obj2->addActionRelatedBymodifyPersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinSetPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol = ActionPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = PersonPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = PersonPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    PersonPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Action) to $obj2 (Person)
                $obj2->addActionRelatedBysetPersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with their ActionType objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol = ActionPeer::NUM_HYDRATE_COLUMNS;
        ActionTypePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Action) to $obj2 (ActionType)
                $obj2->addAction($obj1);

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
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Selects a collection of Action objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        EventPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        ActionTypePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined Event rows

            $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = EventPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Event)
                $obj2->addAction($obj1);
            } // if joined row not null

            // Add objects for joined Person rows

            $key3 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = PersonPeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = PersonPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    PersonPeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (Action) to the collection in $obj3 (Person)
                $obj3->addActionRelatedBycreatePersonId($obj1);
            } // if joined row not null

            // Add objects for joined Person rows

            $key4 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol4);
            if ($key4 !== null) {
                $obj4 = PersonPeer::getInstanceFromPool($key4);
                if (!$obj4) {

                    $cls = PersonPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    PersonPeer::addInstanceToPool($obj4, $key4);
                } // if obj4 loaded

                // Add the $obj1 (Action) to the collection in $obj4 (Person)
                $obj4->addActionRelatedBymodifyPersonId($obj1);
            } // if joined row not null

            // Add objects for joined Person rows

            $key5 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol5);
            if ($key5 !== null) {
                $obj5 = PersonPeer::getInstanceFromPool($key5);
                if (!$obj5) {

                    $cls = PersonPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    PersonPeer::addInstanceToPool($obj5, $key5);
                } // if obj5 loaded

                // Add the $obj1 (Action) to the collection in $obj5 (Person)
                $obj5->addActionRelatedBysetPersonId($obj1);
            } // if joined row not null

            // Add objects for joined ActionType rows

            $key6 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol6);
            if ($key6 !== null) {
                $obj6 = ActionTypePeer::getInstanceFromPool($key6);
                if (!$obj6) {

                    $cls = ActionTypePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionTypePeer::addInstanceToPool($obj6, $key6);
                } // if obj6 loaded

                // Add the $obj1 (Action) to the collection in $obj6 (ActionType)
                $obj6->addAction($obj1);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related Event table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptEvent(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related CreatePerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptCreatePerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ModifyPerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptModifyPerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related SetPerson table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptSetPerson(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Selects a collection of Action objects pre-filled with all related objects except Event.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptEvent(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        ActionTypePeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Person rows

                $key2 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = PersonPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = PersonPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    PersonPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Person)
                $obj2->addActionRelatedBycreatePersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key3 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = PersonPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = PersonPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    PersonPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Action) to the collection in $obj3 (Person)
                $obj3->addActionRelatedBymodifyPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key4 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = PersonPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = PersonPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    PersonPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Action) to the collection in $obj4 (Person)
                $obj4->addActionRelatedBysetPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined ActionType rows

                $key5 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionTypePeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionTypePeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionTypePeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (Action) to the collection in $obj5 (ActionType)
                $obj5->addAction($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with all related objects except CreatePerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptCreatePerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        EventPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventPeer::NUM_HYDRATE_COLUMNS;

        ActionTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Event rows

                $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Event)
                $obj2->addAction($obj1);

            } // if joined row is not null

                // Add objects for joined ActionType rows

                $key3 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Action) to the collection in $obj3 (ActionType)
                $obj3->addAction($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with all related objects except ModifyPerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptModifyPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        EventPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventPeer::NUM_HYDRATE_COLUMNS;

        ActionTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Event rows

                $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Event)
                $obj2->addAction($obj1);

            } // if joined row is not null

                // Add objects for joined ActionType rows

                $key3 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Action) to the collection in $obj3 (ActionType)
                $obj3->addAction($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with all related objects except SetPerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptSetPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        EventPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventPeer::NUM_HYDRATE_COLUMNS;

        ActionTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::ACTIONTYPE_ID, ActionTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Event rows

                $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Event)
                $obj2->addAction($obj1);

            } // if joined row is not null

                // Add objects for joined ActionType rows

                $key3 = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Action) to the collection in $obj3 (ActionType)
                $obj3->addAction($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Action objects pre-filled with all related objects except ActionType.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Action objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPeer::DATABASE_NAME);
        }

        ActionPeer::addSelectColumns($criteria);
        $startcol2 = ActionPeer::NUM_HYDRATE_COLUMNS;

        EventPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPeer::EVENT_ID, EventPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Event rows

                $key2 = EventPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Action) to the collection in $obj2 (Event)
                $obj2->addAction($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key3 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = PersonPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = PersonPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    PersonPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Action) to the collection in $obj3 (Person)
                $obj3->addActionRelatedBycreatePersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key4 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = PersonPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = PersonPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    PersonPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Action) to the collection in $obj4 (Person)
                $obj4->addActionRelatedBymodifyPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key5 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = PersonPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = PersonPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    PersonPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (Action) to the collection in $obj5 (Person)
                $obj5->addActionRelatedBysetPersonId($obj1);

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
        return Propel::getDatabaseMap(ActionPeer::DATABASE_NAME)->getTable(ActionPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseActionPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseActionPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new ActionTableMap());
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
        return ActionPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a Action or Criteria object.
     *
     * @param      mixed $values Criteria or Action object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from Action object
        }

        if ($criteria->containsKey(ActionPeer::ID) && $criteria->keyContainsValue(ActionPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.ActionPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a Action or Criteria object.
     *
     * @param      mixed $values Criteria or Action object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(ActionPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(ActionPeer::ID);
            $value = $criteria->remove(ActionPeer::ID);
            if ($value) {
                $selectCriteria->add(ActionPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(ActionPeer::TABLE_NAME);
            }

        } else { // $values is Action object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the Action table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(ActionPeer::TABLE_NAME, $con, ActionPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            ActionPeer::clearInstancePool();
            ActionPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a Action or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or Action object or primary key or array of primary keys
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
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            ActionPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof Action) { // it's a model object
            // invalidate the cache for this single object
            ActionPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(ActionPeer::DATABASE_NAME);
            $criteria->add(ActionPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                ActionPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(ActionPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            ActionPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given Action object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      Action $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(ActionPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(ActionPeer::TABLE_NAME);

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

        return BasePeer::doValidate(ActionPeer::DATABASE_NAME, ActionPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return Action
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = ActionPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(ActionPeer::DATABASE_NAME);
        $criteria->add(ActionPeer::ID, $pk);

        $v = ActionPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return Action[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(ActionPeer::DATABASE_NAME);
            $criteria->add(ActionPeer::ID, $pks, Criteria::IN);
            $objs = ActionPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseActionPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseActionPeer::buildTableMap();

