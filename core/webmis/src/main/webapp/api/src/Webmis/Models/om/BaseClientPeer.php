<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\Client;
use Webmis\Models\ClientPeer;
use Webmis\Models\map\ClientTableMap;

/**
 * Base static class for performing query and update operations on the 'Client' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseClientPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'Client';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\Client';

    /** the related TableMap class for this table */
    const TM_CLASS = 'ClientTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 21;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 21;

    /** the column name for the id field */
    const ID = 'Client.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'Client.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'Client.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'Client.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'Client.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'Client.deleted';

    /** the column name for the lastName field */
    const LASTNAME = 'Client.lastName';

    /** the column name for the firstName field */
    const FIRSTNAME = 'Client.firstName';

    /** the column name for the patrName field */
    const PATRNAME = 'Client.patrName';

    /** the column name for the birthDate field */
    const BIRTHDATE = 'Client.birthDate';

    /** the column name for the sex field */
    const SEX = 'Client.sex';

    /** the column name for the SNILS field */
    const SNILS = 'Client.SNILS';

    /** the column name for the bloodType_id field */
    const BLOODTYPE_ID = 'Client.bloodType_id';

    /** the column name for the bloodDate field */
    const BLOODDATE = 'Client.bloodDate';

    /** the column name for the bloodNotes field */
    const BLOODNOTES = 'Client.bloodNotes';

    /** the column name for the growth field */
    const GROWTH = 'Client.growth';

    /** the column name for the weight field */
    const WEIGHT = 'Client.weight';

    /** the column name for the notes field */
    const NOTES = 'Client.notes';

    /** the column name for the version field */
    const VERSION = 'Client.version';

    /** the column name for the birthPlace field */
    const BIRTHPLACE = 'Client.birthPlace';

    /** the column name for the uuid_id field */
    const UUID_ID = 'Client.uuid_id';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of Client objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array Client[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. ClientPeer::$fieldNames[ClientPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'lastName', 'firstName', 'patrName', 'birthDate', 'sex', 'snils', 'bloodTypeId', 'bloodDate', 'bloodNotes', 'growth', 'weight', 'notes', 'version', 'birthPlace', 'uuidId', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'lastName', 'firstName', 'patrName', 'birthDate', 'sex', 'snils', 'bloodTypeId', 'bloodDate', 'bloodNotes', 'growth', 'weight', 'notes', 'version', 'birthPlace', 'uuidId', ),
        BasePeer::TYPE_COLNAME => array (ClientPeer::ID, ClientPeer::CREATEDATETIME, ClientPeer::CREATEPERSON_ID, ClientPeer::MODIFYDATETIME, ClientPeer::MODIFYPERSON_ID, ClientPeer::DELETED, ClientPeer::LASTNAME, ClientPeer::FIRSTNAME, ClientPeer::PATRNAME, ClientPeer::BIRTHDATE, ClientPeer::SEX, ClientPeer::SNILS, ClientPeer::BLOODTYPE_ID, ClientPeer::BLOODDATE, ClientPeer::BLOODNOTES, ClientPeer::GROWTH, ClientPeer::WEIGHT, ClientPeer::NOTES, ClientPeer::VERSION, ClientPeer::BIRTHPLACE, ClientPeer::UUID_ID, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'LASTNAME', 'FIRSTNAME', 'PATRNAME', 'BIRTHDATE', 'SEX', 'SNILS', 'BLOODTYPE_ID', 'BLOODDATE', 'BLOODNOTES', 'GROWTH', 'WEIGHT', 'NOTES', 'VERSION', 'BIRTHPLACE', 'UUID_ID', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'lastName', 'firstName', 'patrName', 'birthDate', 'sex', 'SNILS', 'bloodType_id', 'bloodDate', 'bloodNotes', 'growth', 'weight', 'notes', 'version', 'birthPlace', 'uuid_id', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. ClientPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'lastName' => 6, 'firstName' => 7, 'patrName' => 8, 'birthDate' => 9, 'sex' => 10, 'snils' => 11, 'bloodTypeId' => 12, 'bloodDate' => 13, 'bloodNotes' => 14, 'growth' => 15, 'weight' => 16, 'notes' => 17, 'version' => 18, 'birthPlace' => 19, 'uuidId' => 20, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'lastName' => 6, 'firstName' => 7, 'patrName' => 8, 'birthDate' => 9, 'sex' => 10, 'snils' => 11, 'bloodTypeId' => 12, 'bloodDate' => 13, 'bloodNotes' => 14, 'growth' => 15, 'weight' => 16, 'notes' => 17, 'version' => 18, 'birthPlace' => 19, 'uuidId' => 20, ),
        BasePeer::TYPE_COLNAME => array (ClientPeer::ID => 0, ClientPeer::CREATEDATETIME => 1, ClientPeer::CREATEPERSON_ID => 2, ClientPeer::MODIFYDATETIME => 3, ClientPeer::MODIFYPERSON_ID => 4, ClientPeer::DELETED => 5, ClientPeer::LASTNAME => 6, ClientPeer::FIRSTNAME => 7, ClientPeer::PATRNAME => 8, ClientPeer::BIRTHDATE => 9, ClientPeer::SEX => 10, ClientPeer::SNILS => 11, ClientPeer::BLOODTYPE_ID => 12, ClientPeer::BLOODDATE => 13, ClientPeer::BLOODNOTES => 14, ClientPeer::GROWTH => 15, ClientPeer::WEIGHT => 16, ClientPeer::NOTES => 17, ClientPeer::VERSION => 18, ClientPeer::BIRTHPLACE => 19, ClientPeer::UUID_ID => 20, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'LASTNAME' => 6, 'FIRSTNAME' => 7, 'PATRNAME' => 8, 'BIRTHDATE' => 9, 'SEX' => 10, 'SNILS' => 11, 'BLOODTYPE_ID' => 12, 'BLOODDATE' => 13, 'BLOODNOTES' => 14, 'GROWTH' => 15, 'WEIGHT' => 16, 'NOTES' => 17, 'VERSION' => 18, 'BIRTHPLACE' => 19, 'UUID_ID' => 20, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'lastName' => 6, 'firstName' => 7, 'patrName' => 8, 'birthDate' => 9, 'sex' => 10, 'SNILS' => 11, 'bloodType_id' => 12, 'bloodDate' => 13, 'bloodNotes' => 14, 'growth' => 15, 'weight' => 16, 'notes' => 17, 'version' => 18, 'birthPlace' => 19, 'uuid_id' => 20, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, )
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
        $toNames = ClientPeer::getFieldNames($toType);
        $key = isset(ClientPeer::$fieldKeys[$fromType][$name]) ? ClientPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(ClientPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, ClientPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return ClientPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. ClientPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(ClientPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(ClientPeer::ID);
            $criteria->addSelectColumn(ClientPeer::CREATEDATETIME);
            $criteria->addSelectColumn(ClientPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(ClientPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(ClientPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(ClientPeer::DELETED);
            $criteria->addSelectColumn(ClientPeer::LASTNAME);
            $criteria->addSelectColumn(ClientPeer::FIRSTNAME);
            $criteria->addSelectColumn(ClientPeer::PATRNAME);
            $criteria->addSelectColumn(ClientPeer::BIRTHDATE);
            $criteria->addSelectColumn(ClientPeer::SEX);
            $criteria->addSelectColumn(ClientPeer::SNILS);
            $criteria->addSelectColumn(ClientPeer::BLOODTYPE_ID);
            $criteria->addSelectColumn(ClientPeer::BLOODDATE);
            $criteria->addSelectColumn(ClientPeer::BLOODNOTES);
            $criteria->addSelectColumn(ClientPeer::GROWTH);
            $criteria->addSelectColumn(ClientPeer::WEIGHT);
            $criteria->addSelectColumn(ClientPeer::NOTES);
            $criteria->addSelectColumn(ClientPeer::VERSION);
            $criteria->addSelectColumn(ClientPeer::BIRTHPLACE);
            $criteria->addSelectColumn(ClientPeer::UUID_ID);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.lastName');
            $criteria->addSelectColumn($alias . '.firstName');
            $criteria->addSelectColumn($alias . '.patrName');
            $criteria->addSelectColumn($alias . '.birthDate');
            $criteria->addSelectColumn($alias . '.sex');
            $criteria->addSelectColumn($alias . '.SNILS');
            $criteria->addSelectColumn($alias . '.bloodType_id');
            $criteria->addSelectColumn($alias . '.bloodDate');
            $criteria->addSelectColumn($alias . '.bloodNotes');
            $criteria->addSelectColumn($alias . '.growth');
            $criteria->addSelectColumn($alias . '.weight');
            $criteria->addSelectColumn($alias . '.notes');
            $criteria->addSelectColumn($alias . '.version');
            $criteria->addSelectColumn($alias . '.birthPlace');
            $criteria->addSelectColumn($alias . '.uuid_id');
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
        $criteria->setPrimaryTableName(ClientPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ClientPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(ClientPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Client
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = ClientPeer::doSelect($critcopy, $con);
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
        return ClientPeer::populateObjects(ClientPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            ClientPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(ClientPeer::DATABASE_NAME);

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
     * @param      Client $obj A Client object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            ClientPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A Client object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof Client) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or Client object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(ClientPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   Client Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(ClientPeer::$instances[$key])) {
                return ClientPeer::$instances[$key];
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
        foreach (ClientPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        ClientPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to Client
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
        $cls = ClientPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = ClientPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = ClientPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                ClientPeer::addInstanceToPool($obj, $key);
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
     * @return array (Client object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = ClientPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = ClientPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + ClientPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = ClientPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            ClientPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
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
        return Propel::getDatabaseMap(ClientPeer::DATABASE_NAME)->getTable(ClientPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseClientPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseClientPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new ClientTableMap());
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
        return ClientPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a Client or Criteria object.
     *
     * @param      mixed $values Criteria or Client object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from Client object
        }

        if ($criteria->containsKey(ClientPeer::ID) && $criteria->keyContainsValue(ClientPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.ClientPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(ClientPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a Client or Criteria object.
     *
     * @param      mixed $values Criteria or Client object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(ClientPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(ClientPeer::ID);
            $value = $criteria->remove(ClientPeer::ID);
            if ($value) {
                $selectCriteria->add(ClientPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(ClientPeer::TABLE_NAME);
            }

        } else { // $values is Client object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(ClientPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the Client table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(ClientPeer::TABLE_NAME, $con, ClientPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            ClientPeer::clearInstancePool();
            ClientPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a Client or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or Client object or primary key or array of primary keys
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
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            ClientPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof Client) { // it's a model object
            // invalidate the cache for this single object
            ClientPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(ClientPeer::DATABASE_NAME);
            $criteria->add(ClientPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                ClientPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(ClientPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            ClientPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given Client object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      Client $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(ClientPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(ClientPeer::TABLE_NAME);

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

        return BasePeer::doValidate(ClientPeer::DATABASE_NAME, ClientPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return Client
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = ClientPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(ClientPeer::DATABASE_NAME);
        $criteria->add(ClientPeer::ID, $pk);

        $v = ClientPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return Client[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(ClientPeer::DATABASE_NAME);
            $criteria->add(ClientPeer::ID, $pks, Criteria::IN);
            $objs = ClientPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseClientPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseClientPeer::buildTableMap();

