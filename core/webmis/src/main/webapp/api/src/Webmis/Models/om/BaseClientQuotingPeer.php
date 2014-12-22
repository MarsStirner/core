<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\ClientQuoting;
use Webmis\Models\ClientQuotingPeer;
use Webmis\Models\QuotaTypePeer;
use Webmis\Models\RbPacientModelPeer;
use Webmis\Models\RbTreatmentPeer;
use Webmis\Models\map\ClientQuotingTableMap;

/**
 * Base static class for performing query and update operations on the 'Client_Quoting' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseClientQuotingPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'Client_Quoting';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\ClientQuoting';

    /** the related TableMap class for this table */
    const TM_CLASS = 'ClientQuotingTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 28;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 28;

    /** the column name for the id field */
    const ID = 'Client_Quoting.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'Client_Quoting.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'Client_Quoting.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'Client_Quoting.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'Client_Quoting.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'Client_Quoting.deleted';

    /** the column name for the master_id field */
    const MASTER_ID = 'Client_Quoting.master_id';

    /** the column name for the identifier field */
    const IDENTIFIER = 'Client_Quoting.identifier';

    /** the column name for the quotaTicket field */
    const QUOTATICKET = 'Client_Quoting.quotaTicket';

    /** the column name for the quotaType_id field */
    const QUOTATYPE_ID = 'Client_Quoting.quotaType_id';

    /** the column name for the stage field */
    const STAGE = 'Client_Quoting.stage';

    /** the column name for the directionDate field */
    const DIRECTIONDATE = 'Client_Quoting.directionDate';

    /** the column name for the freeInput field */
    const FREEINPUT = 'Client_Quoting.freeInput';

    /** the column name for the org_id field */
    const ORG_ID = 'Client_Quoting.org_id';

    /** the column name for the amount field */
    const AMOUNT = 'Client_Quoting.amount';

    /** the column name for the MKB field */
    const MKB = 'Client_Quoting.MKB';

    /** the column name for the status field */
    const STATUS = 'Client_Quoting.status';

    /** the column name for the request field */
    const REQUEST = 'Client_Quoting.request';

    /** the column name for the statment field */
    const STATMENT = 'Client_Quoting.statment';

    /** the column name for the dateRegistration field */
    const DATEREGISTRATION = 'Client_Quoting.dateRegistration';

    /** the column name for the dateEnd field */
    const DATEEND = 'Client_Quoting.dateEnd';

    /** the column name for the orgStructure_id field */
    const ORGSTRUCTURE_ID = 'Client_Quoting.orgStructure_id';

    /** the column name for the regionCode field */
    const REGIONCODE = 'Client_Quoting.regionCode';

    /** the column name for the pacientModel_id field */
    const PACIENTMODEL_ID = 'Client_Quoting.pacientModel_id';

    /** the column name for the treatment_id field */
    const TREATMENT_ID = 'Client_Quoting.treatment_id';

    /** the column name for the event_id field */
    const EVENT_ID = 'Client_Quoting.event_id';

    /** the column name for the prevTalon_event_id field */
    const PREVTALON_EVENT_ID = 'Client_Quoting.prevTalon_event_id';

    /** the column name for the version field */
    const VERSION = 'Client_Quoting.version';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of ClientQuoting objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array ClientQuoting[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. ClientQuotingPeer::$fieldNames[ClientQuotingPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('Id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'masterId', 'identifier', 'quotaTicket', 'quotaTypeId', 'stage', 'directionDate', 'freeInput', 'orgId', 'amount', 'mkb', 'status', 'request', 'statment', 'dateRegistration', 'dateEnd', 'orgStructureId', 'regionCode', 'pacientModelId', 'treatmentId', 'eventId', 'prevTalonEventId', 'version', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'masterId', 'identifier', 'quotaTicket', 'quotaTypeId', 'stage', 'directionDate', 'freeInput', 'orgId', 'amount', 'mkb', 'status', 'request', 'statment', 'dateRegistration', 'dateEnd', 'orgStructureId', 'regionCode', 'pacientModelId', 'treatmentId', 'eventId', 'prevTalonEventId', 'version', ),
        BasePeer::TYPE_COLNAME => array (ClientQuotingPeer::ID, ClientQuotingPeer::CREATEDATETIME, ClientQuotingPeer::CREATEPERSON_ID, ClientQuotingPeer::MODIFYDATETIME, ClientQuotingPeer::MODIFYPERSON_ID, ClientQuotingPeer::DELETED, ClientQuotingPeer::MASTER_ID, ClientQuotingPeer::IDENTIFIER, ClientQuotingPeer::QUOTATICKET, ClientQuotingPeer::QUOTATYPE_ID, ClientQuotingPeer::STAGE, ClientQuotingPeer::DIRECTIONDATE, ClientQuotingPeer::FREEINPUT, ClientQuotingPeer::ORG_ID, ClientQuotingPeer::AMOUNT, ClientQuotingPeer::MKB, ClientQuotingPeer::STATUS, ClientQuotingPeer::REQUEST, ClientQuotingPeer::STATMENT, ClientQuotingPeer::DATEREGISTRATION, ClientQuotingPeer::DATEEND, ClientQuotingPeer::ORGSTRUCTURE_ID, ClientQuotingPeer::REGIONCODE, ClientQuotingPeer::PACIENTMODEL_ID, ClientQuotingPeer::TREATMENT_ID, ClientQuotingPeer::EVENT_ID, ClientQuotingPeer::PREVTALON_EVENT_ID, ClientQuotingPeer::VERSION, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'MASTER_ID', 'IDENTIFIER', 'QUOTATICKET', 'QUOTATYPE_ID', 'STAGE', 'DIRECTIONDATE', 'FREEINPUT', 'ORG_ID', 'AMOUNT', 'MKB', 'STATUS', 'REQUEST', 'STATMENT', 'DATEREGISTRATION', 'DATEEND', 'ORGSTRUCTURE_ID', 'REGIONCODE', 'PACIENTMODEL_ID', 'TREATMENT_ID', 'EVENT_ID', 'PREVTALON_EVENT_ID', 'VERSION', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'master_id', 'identifier', 'quotaTicket', 'quotaType_id', 'stage', 'directionDate', 'freeInput', 'org_id', 'amount', 'MKB', 'status', 'request', 'statment', 'dateRegistration', 'dateEnd', 'orgStructure_id', 'regionCode', 'pacientModel_id', 'treatment_id', 'event_id', 'prevTalon_event_id', 'version', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. ClientQuotingPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('Id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'masterId' => 6, 'identifier' => 7, 'quotaTicket' => 8, 'quotaTypeId' => 9, 'stage' => 10, 'directionDate' => 11, 'freeInput' => 12, 'orgId' => 13, 'amount' => 14, 'mkb' => 15, 'status' => 16, 'request' => 17, 'statment' => 18, 'dateRegistration' => 19, 'dateEnd' => 20, 'orgStructureId' => 21, 'regionCode' => 22, 'pacientModelId' => 23, 'treatmentId' => 24, 'eventId' => 25, 'prevTalonEventId' => 26, 'version' => 27, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'masterId' => 6, 'identifier' => 7, 'quotaTicket' => 8, 'quotaTypeId' => 9, 'stage' => 10, 'directionDate' => 11, 'freeInput' => 12, 'orgId' => 13, 'amount' => 14, 'mkb' => 15, 'status' => 16, 'request' => 17, 'statment' => 18, 'dateRegistration' => 19, 'dateEnd' => 20, 'orgStructureId' => 21, 'regionCode' => 22, 'pacientModelId' => 23, 'treatmentId' => 24, 'eventId' => 25, 'prevTalonEventId' => 26, 'version' => 27, ),
        BasePeer::TYPE_COLNAME => array (ClientQuotingPeer::ID => 0, ClientQuotingPeer::CREATEDATETIME => 1, ClientQuotingPeer::CREATEPERSON_ID => 2, ClientQuotingPeer::MODIFYDATETIME => 3, ClientQuotingPeer::MODIFYPERSON_ID => 4, ClientQuotingPeer::DELETED => 5, ClientQuotingPeer::MASTER_ID => 6, ClientQuotingPeer::IDENTIFIER => 7, ClientQuotingPeer::QUOTATICKET => 8, ClientQuotingPeer::QUOTATYPE_ID => 9, ClientQuotingPeer::STAGE => 10, ClientQuotingPeer::DIRECTIONDATE => 11, ClientQuotingPeer::FREEINPUT => 12, ClientQuotingPeer::ORG_ID => 13, ClientQuotingPeer::AMOUNT => 14, ClientQuotingPeer::MKB => 15, ClientQuotingPeer::STATUS => 16, ClientQuotingPeer::REQUEST => 17, ClientQuotingPeer::STATMENT => 18, ClientQuotingPeer::DATEREGISTRATION => 19, ClientQuotingPeer::DATEEND => 20, ClientQuotingPeer::ORGSTRUCTURE_ID => 21, ClientQuotingPeer::REGIONCODE => 22, ClientQuotingPeer::PACIENTMODEL_ID => 23, ClientQuotingPeer::TREATMENT_ID => 24, ClientQuotingPeer::EVENT_ID => 25, ClientQuotingPeer::PREVTALON_EVENT_ID => 26, ClientQuotingPeer::VERSION => 27, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'MASTER_ID' => 6, 'IDENTIFIER' => 7, 'QUOTATICKET' => 8, 'QUOTATYPE_ID' => 9, 'STAGE' => 10, 'DIRECTIONDATE' => 11, 'FREEINPUT' => 12, 'ORG_ID' => 13, 'AMOUNT' => 14, 'MKB' => 15, 'STATUS' => 16, 'REQUEST' => 17, 'STATMENT' => 18, 'DATEREGISTRATION' => 19, 'DATEEND' => 20, 'ORGSTRUCTURE_ID' => 21, 'REGIONCODE' => 22, 'PACIENTMODEL_ID' => 23, 'TREATMENT_ID' => 24, 'EVENT_ID' => 25, 'PREVTALON_EVENT_ID' => 26, 'VERSION' => 27, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'master_id' => 6, 'identifier' => 7, 'quotaTicket' => 8, 'quotaType_id' => 9, 'stage' => 10, 'directionDate' => 11, 'freeInput' => 12, 'org_id' => 13, 'amount' => 14, 'MKB' => 15, 'status' => 16, 'request' => 17, 'statment' => 18, 'dateRegistration' => 19, 'dateEnd' => 20, 'orgStructure_id' => 21, 'regionCode' => 22, 'pacientModel_id' => 23, 'treatment_id' => 24, 'event_id' => 25, 'prevTalon_event_id' => 26, 'version' => 27, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, )
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
        $toNames = ClientQuotingPeer::getFieldNames($toType);
        $key = isset(ClientQuotingPeer::$fieldKeys[$fromType][$name]) ? ClientQuotingPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(ClientQuotingPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, ClientQuotingPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return ClientQuotingPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. ClientQuotingPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(ClientQuotingPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(ClientQuotingPeer::ID);
            $criteria->addSelectColumn(ClientQuotingPeer::CREATEDATETIME);
            $criteria->addSelectColumn(ClientQuotingPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(ClientQuotingPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::DELETED);
            $criteria->addSelectColumn(ClientQuotingPeer::MASTER_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::IDENTIFIER);
            $criteria->addSelectColumn(ClientQuotingPeer::QUOTATICKET);
            $criteria->addSelectColumn(ClientQuotingPeer::QUOTATYPE_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::STAGE);
            $criteria->addSelectColumn(ClientQuotingPeer::DIRECTIONDATE);
            $criteria->addSelectColumn(ClientQuotingPeer::FREEINPUT);
            $criteria->addSelectColumn(ClientQuotingPeer::ORG_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::AMOUNT);
            $criteria->addSelectColumn(ClientQuotingPeer::MKB);
            $criteria->addSelectColumn(ClientQuotingPeer::STATUS);
            $criteria->addSelectColumn(ClientQuotingPeer::REQUEST);
            $criteria->addSelectColumn(ClientQuotingPeer::STATMENT);
            $criteria->addSelectColumn(ClientQuotingPeer::DATEREGISTRATION);
            $criteria->addSelectColumn(ClientQuotingPeer::DATEEND);
            $criteria->addSelectColumn(ClientQuotingPeer::ORGSTRUCTURE_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::REGIONCODE);
            $criteria->addSelectColumn(ClientQuotingPeer::PACIENTMODEL_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::TREATMENT_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::EVENT_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::PREVTALON_EVENT_ID);
            $criteria->addSelectColumn(ClientQuotingPeer::VERSION);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.master_id');
            $criteria->addSelectColumn($alias . '.identifier');
            $criteria->addSelectColumn($alias . '.quotaTicket');
            $criteria->addSelectColumn($alias . '.quotaType_id');
            $criteria->addSelectColumn($alias . '.stage');
            $criteria->addSelectColumn($alias . '.directionDate');
            $criteria->addSelectColumn($alias . '.freeInput');
            $criteria->addSelectColumn($alias . '.org_id');
            $criteria->addSelectColumn($alias . '.amount');
            $criteria->addSelectColumn($alias . '.MKB');
            $criteria->addSelectColumn($alias . '.status');
            $criteria->addSelectColumn($alias . '.request');
            $criteria->addSelectColumn($alias . '.statment');
            $criteria->addSelectColumn($alias . '.dateRegistration');
            $criteria->addSelectColumn($alias . '.dateEnd');
            $criteria->addSelectColumn($alias . '.orgStructure_id');
            $criteria->addSelectColumn($alias . '.regionCode');
            $criteria->addSelectColumn($alias . '.pacientModel_id');
            $criteria->addSelectColumn($alias . '.treatment_id');
            $criteria->addSelectColumn($alias . '.event_id');
            $criteria->addSelectColumn($alias . '.prevTalon_event_id');
            $criteria->addSelectColumn($alias . '.version');
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
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ClientQuoting
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = ClientQuotingPeer::doSelect($critcopy, $con);
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
        return ClientQuotingPeer::populateObjects(ClientQuotingPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

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
     * @param      ClientQuoting $obj A ClientQuoting object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getId();
            } // if key === null
            ClientQuotingPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A ClientQuoting object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof ClientQuoting) {
                $key = (string) $value->getId();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or ClientQuoting object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(ClientQuotingPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   ClientQuoting Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(ClientQuotingPeer::$instances[$key])) {
                return ClientQuotingPeer::$instances[$key];
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
        foreach (ClientQuotingPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        ClientQuotingPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to Client_Quoting
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
        $cls = ClientQuotingPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = ClientQuotingPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                ClientQuotingPeer::addInstanceToPool($obj, $key);
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
     * @return array (ClientQuoting object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = ClientQuotingPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + ClientQuotingPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = ClientQuotingPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            ClientQuotingPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbTreatment table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinRbTreatment(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related RbPacientModel table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinRbPacientModel(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related QuotaType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinQuotaType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

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
     * Selects a collection of ClientQuoting objects pre-filled with their RbTreatment objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinRbTreatment(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;
        RbTreatmentPeer::addSelectColumns($criteria);

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = RbTreatmentPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = RbTreatmentPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbTreatmentPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    RbTreatmentPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ClientQuoting) to $obj2 (RbTreatment)
                $obj2->addClientQuoting($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ClientQuoting objects pre-filled with their RbPacientModel objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinRbPacientModel(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;
        RbPacientModelPeer::addSelectColumns($criteria);

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = RbPacientModelPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = RbPacientModelPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbPacientModelPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    RbPacientModelPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ClientQuoting) to $obj2 (RbPacientModel)
                $obj2->addClientQuoting($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ClientQuoting objects pre-filled with their QuotaType objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinQuotaType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;
        QuotaTypePeer::addSelectColumns($criteria);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = QuotaTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = QuotaTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = QuotaTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    QuotaTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ClientQuoting) to $obj2 (QuotaType)
                $obj2->addClientQuoting($obj1);

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
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

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
     * Selects a collection of ClientQuoting objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol2 = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;

        RbTreatmentPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbTreatmentPeer::NUM_HYDRATE_COLUMNS;

        RbPacientModelPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + RbPacientModelPeer::NUM_HYDRATE_COLUMNS;

        QuotaTypePeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + QuotaTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined RbTreatment rows

            $key2 = RbTreatmentPeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = RbTreatmentPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = RbTreatmentPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbTreatmentPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj2 (RbTreatment)
                $obj2->addClientQuoting($obj1);
            } // if joined row not null

            // Add objects for joined RbPacientModel rows

            $key3 = RbPacientModelPeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = RbPacientModelPeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = RbPacientModelPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    RbPacientModelPeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj3 (RbPacientModel)
                $obj3->addClientQuoting($obj1);
            } // if joined row not null

            // Add objects for joined QuotaType rows

            $key4 = QuotaTypePeer::getPrimaryKeyHashFromRow($row, $startcol4);
            if ($key4 !== null) {
                $obj4 = QuotaTypePeer::getInstanceFromPool($key4);
                if (!$obj4) {

                    $cls = QuotaTypePeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    QuotaTypePeer::addInstanceToPool($obj4, $key4);
                } // if obj4 loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj4 (QuotaType)
                $obj4->addClientQuoting($obj1);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related RbTreatment table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptRbTreatment(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related RbPacientModel table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptRbPacientModel(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related QuotaType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptQuotaType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientQuotingPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

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
     * Selects a collection of ClientQuoting objects pre-filled with all related objects except RbTreatment.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptRbTreatment(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol2 = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;

        RbPacientModelPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbPacientModelPeer::NUM_HYDRATE_COLUMNS;

        QuotaTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + QuotaTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined RbPacientModel rows

                $key2 = RbPacientModelPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = RbPacientModelPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = RbPacientModelPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbPacientModelPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj2 (RbPacientModel)
                $obj2->addClientQuoting($obj1);

            } // if joined row is not null

                // Add objects for joined QuotaType rows

                $key3 = QuotaTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = QuotaTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = QuotaTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    QuotaTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj3 (QuotaType)
                $obj3->addClientQuoting($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ClientQuoting objects pre-filled with all related objects except RbPacientModel.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptRbPacientModel(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol2 = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;

        RbTreatmentPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbTreatmentPeer::NUM_HYDRATE_COLUMNS;

        QuotaTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + QuotaTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::QUOTATYPE_ID, QuotaTypePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined RbTreatment rows

                $key2 = RbTreatmentPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = RbTreatmentPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = RbTreatmentPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbTreatmentPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj2 (RbTreatment)
                $obj2->addClientQuoting($obj1);

            } // if joined row is not null

                // Add objects for joined QuotaType rows

                $key3 = QuotaTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = QuotaTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = QuotaTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    QuotaTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj3 (QuotaType)
                $obj3->addClientQuoting($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ClientQuoting objects pre-filled with all related objects except QuotaType.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ClientQuoting objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptQuotaType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);
        }

        ClientQuotingPeer::addSelectColumns($criteria);
        $startcol2 = ClientQuotingPeer::NUM_HYDRATE_COLUMNS;

        RbTreatmentPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + RbTreatmentPeer::NUM_HYDRATE_COLUMNS;

        RbPacientModelPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + RbPacientModelPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ClientQuotingPeer::TREATMENT_ID, RbTreatmentPeer::ID, $join_behavior);

        $criteria->addJoin(ClientQuotingPeer::PACIENTMODEL_ID, RbPacientModelPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ClientQuotingPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ClientQuotingPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ClientQuotingPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ClientQuotingPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined RbTreatment rows

                $key2 = RbTreatmentPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = RbTreatmentPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = RbTreatmentPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    RbTreatmentPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj2 (RbTreatment)
                $obj2->addClientQuoting($obj1);

            } // if joined row is not null

                // Add objects for joined RbPacientModel rows

                $key3 = RbPacientModelPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = RbPacientModelPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = RbPacientModelPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    RbPacientModelPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ClientQuoting) to the collection in $obj3 (RbPacientModel)
                $obj3->addClientQuoting($obj1);

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
        return Propel::getDatabaseMap(ClientQuotingPeer::DATABASE_NAME)->getTable(ClientQuotingPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseClientQuotingPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseClientQuotingPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new ClientQuotingTableMap());
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
        return ClientQuotingPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a ClientQuoting or Criteria object.
     *
     * @param      mixed $values Criteria or ClientQuoting object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from ClientQuoting object
        }

        if ($criteria->containsKey(ClientQuotingPeer::ID) && $criteria->keyContainsValue(ClientQuotingPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.ClientQuotingPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a ClientQuoting or Criteria object.
     *
     * @param      mixed $values Criteria or ClientQuoting object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(ClientQuotingPeer::ID);
            $value = $criteria->remove(ClientQuotingPeer::ID);
            if ($value) {
                $selectCriteria->add(ClientQuotingPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(ClientQuotingPeer::TABLE_NAME);
            }

        } else { // $values is ClientQuoting object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the Client_Quoting table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(ClientQuotingPeer::TABLE_NAME, $con, ClientQuotingPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            ClientQuotingPeer::clearInstancePool();
            ClientQuotingPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a ClientQuoting or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or ClientQuoting object or primary key or array of primary keys
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
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            ClientQuotingPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof ClientQuoting) { // it's a model object
            // invalidate the cache for this single object
            ClientQuotingPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);
            $criteria->add(ClientQuotingPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                ClientQuotingPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(ClientQuotingPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            ClientQuotingPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given ClientQuoting object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      ClientQuoting $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(ClientQuotingPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(ClientQuotingPeer::TABLE_NAME);

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

        return BasePeer::doValidate(ClientQuotingPeer::DATABASE_NAME, ClientQuotingPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return ClientQuoting
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = ClientQuotingPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);
        $criteria->add(ClientQuotingPeer::ID, $pk);

        $v = ClientQuotingPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return ClientQuoting[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);
            $criteria->add(ClientQuotingPeer::ID, $pks, Criteria::IN);
            $objs = ClientQuotingPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseClientQuotingPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseClientQuotingPeer::buildTableMap();

