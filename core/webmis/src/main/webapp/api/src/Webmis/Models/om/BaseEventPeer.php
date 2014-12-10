<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\ClientPeer;
use Webmis\Models\Event;
use Webmis\Models\EventPeer;
use Webmis\Models\EventTypePeer;
use Webmis\Models\OrgStructurePeer;
use Webmis\Models\PersonPeer;
use Webmis\Models\map\EventTableMap;

/**
 * Base static class for performing query and update operations on the 'Event' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseEventPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'Event';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\Event';

    /** the related TableMap class for this table */
    const TM_CLASS = 'EventTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 35;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 35;

    /** the column name for the id field */
    const ID = 'Event.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'Event.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'Event.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'Event.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'Event.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'Event.deleted';

    /** the column name for the externalId field */
    const EXTERNALID = 'Event.externalId';

    /** the column name for the eventType_id field */
    const EVENTTYPE_ID = 'Event.eventType_id';

    /** the column name for the org_id field */
    const ORG_ID = 'Event.org_id';

    /** the column name for the client_id field */
    const CLIENT_ID = 'Event.client_id';

    /** the column name for the contract_id field */
    const CONTRACT_ID = 'Event.contract_id';

    /** the column name for the prevEventDate field */
    const PREVEVENTDATE = 'Event.prevEventDate';

    /** the column name for the setDate field */
    const SETDATE = 'Event.setDate';

    /** the column name for the setPerson_id field */
    const SETPERSON_ID = 'Event.setPerson_id';

    /** the column name for the execDate field */
    const EXECDATE = 'Event.execDate';

    /** the column name for the execPerson_id field */
    const EXECPERSON_ID = 'Event.execPerson_id';

    /** the column name for the isPrimary field */
    const ISPRIMARY = 'Event.isPrimary';

    /** the column name for the order field */
    const ORDER = 'Event.order';

    /** the column name for the result_id field */
    const RESULT_ID = 'Event.result_id';

    /** the column name for the nextEventDate field */
    const NEXTEVENTDATE = 'Event.nextEventDate';

    /** the column name for the payStatus field */
    const PAYSTATUS = 'Event.payStatus';

    /** the column name for the typeAsset_id field */
    const TYPEASSET_ID = 'Event.typeAsset_id';

    /** the column name for the note field */
    const NOTE = 'Event.note';

    /** the column name for the curator_id field */
    const CURATOR_ID = 'Event.curator_id';

    /** the column name for the assistant_id field */
    const ASSISTANT_ID = 'Event.assistant_id';

    /** the column name for the pregnancyWeek field */
    const PREGNANCYWEEK = 'Event.pregnancyWeek';

    /** the column name for the MES_id field */
    const MES_ID = 'Event.MES_id';

    /** the column name for the mesSpecification_id field */
    const MESSPECIFICATION_ID = 'Event.mesSpecification_id';

    /** the column name for the rbAcheResult_id field */
    const RBACHERESULT_ID = 'Event.rbAcheResult_id';

    /** the column name for the version field */
    const VERSION = 'Event.version';

    /** the column name for the privilege field */
    const PRIVILEGE = 'Event.privilege';

    /** the column name for the urgent field */
    const URGENT = 'Event.urgent';

    /** the column name for the orgStructure_id field */
    const ORGSTRUCTURE_ID = 'Event.orgStructure_id';

    /** the column name for the uuid_id field */
    const UUID_ID = 'Event.uuid_id';

    /** the column name for the lpu_transfer field */
    const LPU_TRANSFER = 'Event.lpu_transfer';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of Event objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array Event[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. EventPeer::$fieldNames[EventPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'externalId', 'eventTypeId', 'orgId', 'clientId', 'contractId', 'prevEventDate', 'setDate', 'setPersonId', 'execDate', 'execPersonId', 'isPrimary', 'order', 'resultId', 'nextEventDate', 'payStatus', 'typeAssetId', 'note', 'curatorId', 'assistantId', 'pregnancyWeek', 'mesId', 'mesSpecificationId', 'rbAcheResultId', 'version', 'privilege', 'urgent', 'orgStructureId', 'uuidId', 'lpuTransfer', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'externalId', 'eventTypeId', 'orgId', 'clientId', 'contractId', 'prevEventDate', 'setDate', 'setPersonId', 'execDate', 'execPersonId', 'isPrimary', 'order', 'resultId', 'nextEventDate', 'payStatus', 'typeAssetId', 'note', 'curatorId', 'assistantId', 'pregnancyWeek', 'mesId', 'mesSpecificationId', 'rbAcheResultId', 'version', 'privilege', 'urgent', 'orgStructureId', 'uuidId', 'lpuTransfer', ),
        BasePeer::TYPE_COLNAME => array (EventPeer::ID, EventPeer::CREATEDATETIME, EventPeer::CREATEPERSON_ID, EventPeer::MODIFYDATETIME, EventPeer::MODIFYPERSON_ID, EventPeer::DELETED, EventPeer::EXTERNALID, EventPeer::EVENTTYPE_ID, EventPeer::ORG_ID, EventPeer::CLIENT_ID, EventPeer::CONTRACT_ID, EventPeer::PREVEVENTDATE, EventPeer::SETDATE, EventPeer::SETPERSON_ID, EventPeer::EXECDATE, EventPeer::EXECPERSON_ID, EventPeer::ISPRIMARY, EventPeer::ORDER, EventPeer::RESULT_ID, EventPeer::NEXTEVENTDATE, EventPeer::PAYSTATUS, EventPeer::TYPEASSET_ID, EventPeer::NOTE, EventPeer::CURATOR_ID, EventPeer::ASSISTANT_ID, EventPeer::PREGNANCYWEEK, EventPeer::MES_ID, EventPeer::MESSPECIFICATION_ID, EventPeer::RBACHERESULT_ID, EventPeer::VERSION, EventPeer::PRIVILEGE, EventPeer::URGENT, EventPeer::ORGSTRUCTURE_ID, EventPeer::UUID_ID, EventPeer::LPU_TRANSFER, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'EXTERNALID', 'EVENTTYPE_ID', 'ORG_ID', 'CLIENT_ID', 'CONTRACT_ID', 'PREVEVENTDATE', 'SETDATE', 'SETPERSON_ID', 'EXECDATE', 'EXECPERSON_ID', 'ISPRIMARY', 'ORDER', 'RESULT_ID', 'NEXTEVENTDATE', 'PAYSTATUS', 'TYPEASSET_ID', 'NOTE', 'CURATOR_ID', 'ASSISTANT_ID', 'PREGNANCYWEEK', 'MES_ID', 'MESSPECIFICATION_ID', 'RBACHERESULT_ID', 'VERSION', 'PRIVILEGE', 'URGENT', 'ORGSTRUCTURE_ID', 'UUID_ID', 'LPU_TRANSFER', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'externalId', 'eventType_id', 'org_id', 'client_id', 'contract_id', 'prevEventDate', 'setDate', 'setPerson_id', 'execDate', 'execPerson_id', 'isPrimary', 'order', 'result_id', 'nextEventDate', 'payStatus', 'typeAsset_id', 'note', 'curator_id', 'assistant_id', 'pregnancyWeek', 'MES_id', 'mesSpecification_id', 'rbAcheResult_id', 'version', 'privilege', 'urgent', 'orgStructure_id', 'uuid_id', 'lpu_transfer', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. EventPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'externalId' => 6, 'eventTypeId' => 7, 'orgId' => 8, 'clientId' => 9, 'contractId' => 10, 'prevEventDate' => 11, 'setDate' => 12, 'setPersonId' => 13, 'execDate' => 14, 'execPersonId' => 15, 'isPrimary' => 16, 'order' => 17, 'resultId' => 18, 'nextEventDate' => 19, 'payStatus' => 20, 'typeAssetId' => 21, 'note' => 22, 'curatorId' => 23, 'assistantId' => 24, 'pregnancyWeek' => 25, 'mesId' => 26, 'mesSpecificationId' => 27, 'rbAcheResultId' => 28, 'version' => 29, 'privilege' => 30, 'urgent' => 31, 'orgStructureId' => 32, 'uuidId' => 33, 'lpuTransfer' => 34, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'externalId' => 6, 'eventTypeId' => 7, 'orgId' => 8, 'clientId' => 9, 'contractId' => 10, 'prevEventDate' => 11, 'setDate' => 12, 'setPersonId' => 13, 'execDate' => 14, 'execPersonId' => 15, 'isPrimary' => 16, 'order' => 17, 'resultId' => 18, 'nextEventDate' => 19, 'payStatus' => 20, 'typeAssetId' => 21, 'note' => 22, 'curatorId' => 23, 'assistantId' => 24, 'pregnancyWeek' => 25, 'mesId' => 26, 'mesSpecificationId' => 27, 'rbAcheResultId' => 28, 'version' => 29, 'privilege' => 30, 'urgent' => 31, 'orgStructureId' => 32, 'uuidId' => 33, 'lpuTransfer' => 34, ),
        BasePeer::TYPE_COLNAME => array (EventPeer::ID => 0, EventPeer::CREATEDATETIME => 1, EventPeer::CREATEPERSON_ID => 2, EventPeer::MODIFYDATETIME => 3, EventPeer::MODIFYPERSON_ID => 4, EventPeer::DELETED => 5, EventPeer::EXTERNALID => 6, EventPeer::EVENTTYPE_ID => 7, EventPeer::ORG_ID => 8, EventPeer::CLIENT_ID => 9, EventPeer::CONTRACT_ID => 10, EventPeer::PREVEVENTDATE => 11, EventPeer::SETDATE => 12, EventPeer::SETPERSON_ID => 13, EventPeer::EXECDATE => 14, EventPeer::EXECPERSON_ID => 15, EventPeer::ISPRIMARY => 16, EventPeer::ORDER => 17, EventPeer::RESULT_ID => 18, EventPeer::NEXTEVENTDATE => 19, EventPeer::PAYSTATUS => 20, EventPeer::TYPEASSET_ID => 21, EventPeer::NOTE => 22, EventPeer::CURATOR_ID => 23, EventPeer::ASSISTANT_ID => 24, EventPeer::PREGNANCYWEEK => 25, EventPeer::MES_ID => 26, EventPeer::MESSPECIFICATION_ID => 27, EventPeer::RBACHERESULT_ID => 28, EventPeer::VERSION => 29, EventPeer::PRIVILEGE => 30, EventPeer::URGENT => 31, EventPeer::ORGSTRUCTURE_ID => 32, EventPeer::UUID_ID => 33, EventPeer::LPU_TRANSFER => 34, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'EXTERNALID' => 6, 'EVENTTYPE_ID' => 7, 'ORG_ID' => 8, 'CLIENT_ID' => 9, 'CONTRACT_ID' => 10, 'PREVEVENTDATE' => 11, 'SETDATE' => 12, 'SETPERSON_ID' => 13, 'EXECDATE' => 14, 'EXECPERSON_ID' => 15, 'ISPRIMARY' => 16, 'ORDER' => 17, 'RESULT_ID' => 18, 'NEXTEVENTDATE' => 19, 'PAYSTATUS' => 20, 'TYPEASSET_ID' => 21, 'NOTE' => 22, 'CURATOR_ID' => 23, 'ASSISTANT_ID' => 24, 'PREGNANCYWEEK' => 25, 'MES_ID' => 26, 'MESSPECIFICATION_ID' => 27, 'RBACHERESULT_ID' => 28, 'VERSION' => 29, 'PRIVILEGE' => 30, 'URGENT' => 31, 'ORGSTRUCTURE_ID' => 32, 'UUID_ID' => 33, 'LPU_TRANSFER' => 34, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'externalId' => 6, 'eventType_id' => 7, 'org_id' => 8, 'client_id' => 9, 'contract_id' => 10, 'prevEventDate' => 11, 'setDate' => 12, 'setPerson_id' => 13, 'execDate' => 14, 'execPerson_id' => 15, 'isPrimary' => 16, 'order' => 17, 'result_id' => 18, 'nextEventDate' => 19, 'payStatus' => 20, 'typeAsset_id' => 21, 'note' => 22, 'curator_id' => 23, 'assistant_id' => 24, 'pregnancyWeek' => 25, 'MES_id' => 26, 'mesSpecification_id' => 27, 'rbAcheResult_id' => 28, 'version' => 29, 'privilege' => 30, 'urgent' => 31, 'orgStructure_id' => 32, 'uuid_id' => 33, 'lpu_transfer' => 34, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, )
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
        $toNames = EventPeer::getFieldNames($toType);
        $key = isset(EventPeer::$fieldKeys[$fromType][$name]) ? EventPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(EventPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, EventPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return EventPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. EventPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(EventPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(EventPeer::ID);
            $criteria->addSelectColumn(EventPeer::CREATEDATETIME);
            $criteria->addSelectColumn(EventPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(EventPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(EventPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(EventPeer::DELETED);
            $criteria->addSelectColumn(EventPeer::EXTERNALID);
            $criteria->addSelectColumn(EventPeer::EVENTTYPE_ID);
            $criteria->addSelectColumn(EventPeer::ORG_ID);
            $criteria->addSelectColumn(EventPeer::CLIENT_ID);
            $criteria->addSelectColumn(EventPeer::CONTRACT_ID);
            $criteria->addSelectColumn(EventPeer::PREVEVENTDATE);
            $criteria->addSelectColumn(EventPeer::SETDATE);
            $criteria->addSelectColumn(EventPeer::SETPERSON_ID);
            $criteria->addSelectColumn(EventPeer::EXECDATE);
            $criteria->addSelectColumn(EventPeer::EXECPERSON_ID);
            $criteria->addSelectColumn(EventPeer::ISPRIMARY);
            $criteria->addSelectColumn(EventPeer::ORDER);
            $criteria->addSelectColumn(EventPeer::RESULT_ID);
            $criteria->addSelectColumn(EventPeer::NEXTEVENTDATE);
            $criteria->addSelectColumn(EventPeer::PAYSTATUS);
            $criteria->addSelectColumn(EventPeer::TYPEASSET_ID);
            $criteria->addSelectColumn(EventPeer::NOTE);
            $criteria->addSelectColumn(EventPeer::CURATOR_ID);
            $criteria->addSelectColumn(EventPeer::ASSISTANT_ID);
            $criteria->addSelectColumn(EventPeer::PREGNANCYWEEK);
            $criteria->addSelectColumn(EventPeer::MES_ID);
            $criteria->addSelectColumn(EventPeer::MESSPECIFICATION_ID);
            $criteria->addSelectColumn(EventPeer::RBACHERESULT_ID);
            $criteria->addSelectColumn(EventPeer::VERSION);
            $criteria->addSelectColumn(EventPeer::PRIVILEGE);
            $criteria->addSelectColumn(EventPeer::URGENT);
            $criteria->addSelectColumn(EventPeer::ORGSTRUCTURE_ID);
            $criteria->addSelectColumn(EventPeer::UUID_ID);
            $criteria->addSelectColumn(EventPeer::LPU_TRANSFER);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.externalId');
            $criteria->addSelectColumn($alias . '.eventType_id');
            $criteria->addSelectColumn($alias . '.org_id');
            $criteria->addSelectColumn($alias . '.client_id');
            $criteria->addSelectColumn($alias . '.contract_id');
            $criteria->addSelectColumn($alias . '.prevEventDate');
            $criteria->addSelectColumn($alias . '.setDate');
            $criteria->addSelectColumn($alias . '.setPerson_id');
            $criteria->addSelectColumn($alias . '.execDate');
            $criteria->addSelectColumn($alias . '.execPerson_id');
            $criteria->addSelectColumn($alias . '.isPrimary');
            $criteria->addSelectColumn($alias . '.order');
            $criteria->addSelectColumn($alias . '.result_id');
            $criteria->addSelectColumn($alias . '.nextEventDate');
            $criteria->addSelectColumn($alias . '.payStatus');
            $criteria->addSelectColumn($alias . '.typeAsset_id');
            $criteria->addSelectColumn($alias . '.note');
            $criteria->addSelectColumn($alias . '.curator_id');
            $criteria->addSelectColumn($alias . '.assistant_id');
            $criteria->addSelectColumn($alias . '.pregnancyWeek');
            $criteria->addSelectColumn($alias . '.MES_id');
            $criteria->addSelectColumn($alias . '.mesSpecification_id');
            $criteria->addSelectColumn($alias . '.rbAcheResult_id');
            $criteria->addSelectColumn($alias . '.version');
            $criteria->addSelectColumn($alias . '.privilege');
            $criteria->addSelectColumn($alias . '.urgent');
            $criteria->addSelectColumn($alias . '.orgStructure_id');
            $criteria->addSelectColumn($alias . '.uuid_id');
            $criteria->addSelectColumn($alias . '.lpu_transfer');
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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(EventPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Event
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = EventPeer::doSelect($critcopy, $con);
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
        return EventPeer::populateObjects(EventPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            EventPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

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
     * @param      Event $obj A Event object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            EventPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A Event object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof Event) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or Event object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(EventPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   Event Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(EventPeer::$instances[$key])) {
                return EventPeer::$instances[$key];
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
        foreach (EventPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        EventPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to Event
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
        $cls = EventPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = EventPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                EventPeer::addInstanceToPool($obj, $key);
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
     * @return array (Event object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = EventPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = EventPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + EventPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = EventPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            EventPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related EventType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinEventType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related Client table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinClient(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related Doctor table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinDoctor(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related OrgStructure table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinOrgStructure(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
     * Selects a collection of Event objects pre-filled with their EventType objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinEventType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        EventTypePeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = EventTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Event) to $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their Client objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinClient(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        ClientPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ClientPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ClientPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ClientPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Event) to $obj2 (Client)
                $obj2->addEvent($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinCreatePerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
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

                // Add the $obj1 (Event) to $obj2 (Person)
                $obj2->addEventRelatedBycreatePersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinModifyPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
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

                // Add the $obj1 (Event) to $obj2 (Person)
                $obj2->addEventRelatedBymodifyPersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinSetPerson(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
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

                // Add the $obj1 (Event) to $obj2 (Person)
                $obj2->addEventRelatedBysetPersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their Person objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinDoctor(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        PersonPeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
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

                // Add the $obj1 (Event) to $obj2 (Person)
                $obj2->addEventRelatedByexecPersonId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with their OrgStructure objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinOrgStructure(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol = EventPeer::NUM_HYDRATE_COLUMNS;
        OrgStructurePeer::addSelectColumns($criteria);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = OrgStructurePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = OrgStructurePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    OrgStructurePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (Event) to $obj2 (OrgStructure)
                $obj2->addEvent($obj1);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
     * Selects a collection of Event objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + PersonPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined EventType rows

            $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = EventTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);
            } // if joined row not null

            // Add objects for joined Client rows

            $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = ClientPeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);
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

                // Add the $obj1 (Event) to the collection in $obj4 (Person)
                $obj4->addEventRelatedBycreatePersonId($obj1);
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

                // Add the $obj1 (Event) to the collection in $obj5 (Person)
                $obj5->addEventRelatedBymodifyPersonId($obj1);
            } // if joined row not null

            // Add objects for joined Person rows

            $key6 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol6);
            if ($key6 !== null) {
                $obj6 = PersonPeer::getInstanceFromPool($key6);
                if (!$obj6) {

                    $cls = PersonPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    PersonPeer::addInstanceToPool($obj6, $key6);
                } // if obj6 loaded

                // Add the $obj1 (Event) to the collection in $obj6 (Person)
                $obj6->addEventRelatedBysetPersonId($obj1);
            } // if joined row not null

            // Add objects for joined Person rows

            $key7 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol7);
            if ($key7 !== null) {
                $obj7 = PersonPeer::getInstanceFromPool($key7);
                if (!$obj7) {

                    $cls = PersonPeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    PersonPeer::addInstanceToPool($obj7, $key7);
                } // if obj7 loaded

                // Add the $obj1 (Event) to the collection in $obj7 (Person)
                $obj7->addEventRelatedByexecPersonId($obj1);
            } // if joined row not null

            // Add objects for joined OrgStructure rows

            $key8 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol8);
            if ($key8 !== null) {
                $obj8 = OrgStructurePeer::getInstanceFromPool($key8);
                if (!$obj8) {

                    $cls = OrgStructurePeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    OrgStructurePeer::addInstanceToPool($obj8, $key8);
                } // if obj8 loaded

                // Add the $obj1 (Event) to the collection in $obj8 (OrgStructure)
                $obj8->addEvent($obj1);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related EventType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptEventType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related Client table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptClient(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related Doctor table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptDoctor(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related OrgStructure table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptOrgStructure(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(EventPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            EventPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

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
     * Selects a collection of Event objects pre-filled with all related objects except EventType.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptEventType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ClientPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + PersonPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Client rows

                $key2 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ClientPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ClientPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ClientPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (Client)
                $obj2->addEvent($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj3 (Person)
                $obj3->addEventRelatedBycreatePersonId($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj4 (Person)
                $obj4->addEventRelatedBymodifyPersonId($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj5 (Person)
                $obj5->addEventRelatedBysetPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key6 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = PersonPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = PersonPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    PersonPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (Event) to the collection in $obj6 (Person)
                $obj6->addEventRelatedByexecPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key7 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = OrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    OrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (Event) to the collection in $obj7 (OrgStructure)
                $obj7->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except Client.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptClient(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + PersonPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj3 (Person)
                $obj3->addEventRelatedBycreatePersonId($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj4 (Person)
                $obj4->addEventRelatedBymodifyPersonId($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj5 (Person)
                $obj5->addEventRelatedBysetPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key6 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = PersonPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = PersonPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    PersonPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (Event) to the collection in $obj6 (Person)
                $obj6->addEventRelatedByexecPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key7 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = OrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    OrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (Event) to the collection in $obj7 (OrgStructure)
                $obj7->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except CreatePerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
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
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined Client rows

                $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ClientPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key4 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = OrgStructurePeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    OrgStructurePeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Event) to the collection in $obj4 (OrgStructure)
                $obj4->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except ModifyPerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
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
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined Client rows

                $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ClientPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key4 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = OrgStructurePeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    OrgStructurePeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Event) to the collection in $obj4 (OrgStructure)
                $obj4->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except SetPerson.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
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
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined Client rows

                $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ClientPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key4 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = OrgStructurePeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    OrgStructurePeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Event) to the collection in $obj4 (OrgStructure)
                $obj4->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except Doctor.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptDoctor(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        OrgStructurePeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + OrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::ORGSTRUCTURE_ID, OrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined Client rows

                $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ClientPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined OrgStructure rows

                $key4 = OrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = OrgStructurePeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = OrgStructurePeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    OrgStructurePeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (Event) to the collection in $obj4 (OrgStructure)
                $obj4->addEvent($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of Event objects pre-filled with all related objects except OrgStructure.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of Event objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptOrgStructure(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(EventPeer::DATABASE_NAME);
        }

        EventPeer::addSelectColumns($criteria);
        $startcol2 = EventPeer::NUM_HYDRATE_COLUMNS;

        EventTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + EventTypePeer::NUM_HYDRATE_COLUMNS;

        ClientPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ClientPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + PersonPeer::NUM_HYDRATE_COLUMNS;

        PersonPeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + PersonPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(EventPeer::EVENTTYPE_ID, EventTypePeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CLIENT_ID, ClientPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::CREATEPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::MODIFYPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::SETPERSON_ID, PersonPeer::ID, $join_behavior);

        $criteria->addJoin(EventPeer::EXECPERSON_ID, PersonPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = EventPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = EventPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = EventPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                EventPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined EventType rows

                $key2 = EventTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = EventTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = EventTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    EventTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (Event) to the collection in $obj2 (EventType)
                $obj2->addEvent($obj1);

            } // if joined row is not null

                // Add objects for joined Client rows

                $key3 = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ClientPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ClientPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ClientPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (Event) to the collection in $obj3 (Client)
                $obj3->addEvent($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj4 (Person)
                $obj4->addEventRelatedBycreatePersonId($obj1);

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

                // Add the $obj1 (Event) to the collection in $obj5 (Person)
                $obj5->addEventRelatedBymodifyPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key6 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = PersonPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = PersonPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    PersonPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (Event) to the collection in $obj6 (Person)
                $obj6->addEventRelatedBysetPersonId($obj1);

            } // if joined row is not null

                // Add objects for joined Person rows

                $key7 = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = PersonPeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = PersonPeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    PersonPeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (Event) to the collection in $obj7 (Person)
                $obj7->addEventRelatedByexecPersonId($obj1);

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
        return Propel::getDatabaseMap(EventPeer::DATABASE_NAME)->getTable(EventPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseEventPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseEventPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new EventTableMap());
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
        return EventPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a Event or Criteria object.
     *
     * @param      mixed $values Criteria or Event object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from Event object
        }

        if ($criteria->containsKey(EventPeer::ID) && $criteria->keyContainsValue(EventPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.EventPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a Event or Criteria object.
     *
     * @param      mixed $values Criteria or Event object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(EventPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(EventPeer::ID);
            $value = $criteria->remove(EventPeer::ID);
            if ($value) {
                $selectCriteria->add(EventPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(EventPeer::TABLE_NAME);
            }

        } else { // $values is Event object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the Event table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(EventPeer::TABLE_NAME, $con, EventPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            EventPeer::clearInstancePool();
            EventPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a Event or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or Event object or primary key or array of primary keys
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
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            EventPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof Event) { // it's a model object
            // invalidate the cache for this single object
            EventPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(EventPeer::DATABASE_NAME);
            $criteria->add(EventPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                EventPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(EventPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            EventPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given Event object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      Event $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(EventPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(EventPeer::TABLE_NAME);

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

        return BasePeer::doValidate(EventPeer::DATABASE_NAME, EventPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return Event
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = EventPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(EventPeer::DATABASE_NAME);
        $criteria->add(EventPeer::ID, $pk);

        $v = EventPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return Event[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(EventPeer::DATABASE_NAME);
            $criteria->add(EventPeer::ID, $pks, Criteria::IN);
            $objs = EventPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseEventPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseEventPeer::buildTableMap();

