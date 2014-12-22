<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\Mkb;
use Webmis\Models\MkbPeer;
use Webmis\Models\map\MkbTableMap;

/**
 * Base static class for performing query and update operations on the 'MKB' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseMkbPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'MKB';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\Mkb';

    /** the related TableMap class for this table */
    const TM_CLASS = 'MkbTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 18;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 18;

    /** the column name for the id field */
    const ID = 'MKB.id';

    /** the column name for the ClassID field */
    const CLASSID = 'MKB.ClassID';

    /** the column name for the ClassName field */
    const CLASSNAME = 'MKB.ClassName';

    /** the column name for the BlockID field */
    const BLOCKID = 'MKB.BlockID';

    /** the column name for the BlockName field */
    const BLOCKNAME = 'MKB.BlockName';

    /** the column name for the DiagID field */
    const DIAGID = 'MKB.DiagID';

    /** the column name for the DiagName field */
    const DIAGNAME = 'MKB.DiagName';

    /** the column name for the Prim field */
    const PRIM = 'MKB.Prim';

    /** the column name for the sex field */
    const SEX = 'MKB.sex';

    /** the column name for the age field */
    const AGE = 'MKB.age';

    /** the column name for the age_bu field */
    const AGE_BU = 'MKB.age_bu';

    /** the column name for the age_bc field */
    const AGE_BC = 'MKB.age_bc';

    /** the column name for the age_eu field */
    const AGE_EU = 'MKB.age_eu';

    /** the column name for the age_ec field */
    const AGE_EC = 'MKB.age_ec';

    /** the column name for the characters field */
    const CHARACTERS = 'MKB.characters';

    /** the column name for the duration field */
    const DURATION = 'MKB.duration';

    /** the column name for the service_id field */
    const SERVICE_ID = 'MKB.service_id';

    /** the column name for the MKBSubclass_id field */
    const MKBSUBCLASS_ID = 'MKB.MKBSubclass_id';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of Mkb objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array Mkb[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. MkbPeer::$fieldNames[MkbPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'classId', 'className', 'blockId', 'blockName', 'diagId', 'diagName', 'prim', 'sex', 'age', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'characters', 'duration', 'serviceId', 'MkbSubclassId', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'classId', 'className', 'blockId', 'blockName', 'diagId', 'diagName', 'prim', 'sex', 'age', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'characters', 'duration', 'serviceId', 'mkbSubclassId', ),
        BasePeer::TYPE_COLNAME => array (MkbPeer::ID, MkbPeer::CLASSID, MkbPeer::CLASSNAME, MkbPeer::BLOCKID, MkbPeer::BLOCKNAME, MkbPeer::DIAGID, MkbPeer::DIAGNAME, MkbPeer::PRIM, MkbPeer::SEX, MkbPeer::AGE, MkbPeer::AGE_BU, MkbPeer::AGE_BC, MkbPeer::AGE_EU, MkbPeer::AGE_EC, MkbPeer::CHARACTERS, MkbPeer::DURATION, MkbPeer::SERVICE_ID, MkbPeer::MKBSUBCLASS_ID, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CLASSID', 'CLASSNAME', 'BLOCKID', 'BLOCKNAME', 'DIAGID', 'DIAGNAME', 'PRIM', 'SEX', 'AGE', 'AGE_BU', 'AGE_BC', 'AGE_EU', 'AGE_EC', 'CHARACTERS', 'DURATION', 'SERVICE_ID', 'MKBSUBCLASS_ID', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'ClassID', 'ClassName', 'BlockID', 'BlockName', 'DiagID', 'DiagName', 'Prim', 'sex', 'age', 'age_bu', 'age_bc', 'age_eu', 'age_ec', 'characters', 'duration', 'service_id', 'MKBSubclass_id', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. MkbPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'classId' => 1, 'className' => 2, 'blockId' => 3, 'blockName' => 4, 'diagId' => 5, 'diagName' => 6, 'prim' => 7, 'sex' => 8, 'age' => 9, 'ageBu' => 10, 'ageBc' => 11, 'ageEu' => 12, 'ageEc' => 13, 'characters' => 14, 'duration' => 15, 'serviceId' => 16, 'MkbSubclassId' => 17, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'classId' => 1, 'className' => 2, 'blockId' => 3, 'blockName' => 4, 'diagId' => 5, 'diagName' => 6, 'prim' => 7, 'sex' => 8, 'age' => 9, 'ageBu' => 10, 'ageBc' => 11, 'ageEu' => 12, 'ageEc' => 13, 'characters' => 14, 'duration' => 15, 'serviceId' => 16, 'mkbSubclassId' => 17, ),
        BasePeer::TYPE_COLNAME => array (MkbPeer::ID => 0, MkbPeer::CLASSID => 1, MkbPeer::CLASSNAME => 2, MkbPeer::BLOCKID => 3, MkbPeer::BLOCKNAME => 4, MkbPeer::DIAGID => 5, MkbPeer::DIAGNAME => 6, MkbPeer::PRIM => 7, MkbPeer::SEX => 8, MkbPeer::AGE => 9, MkbPeer::AGE_BU => 10, MkbPeer::AGE_BC => 11, MkbPeer::AGE_EU => 12, MkbPeer::AGE_EC => 13, MkbPeer::CHARACTERS => 14, MkbPeer::DURATION => 15, MkbPeer::SERVICE_ID => 16, MkbPeer::MKBSUBCLASS_ID => 17, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CLASSID' => 1, 'CLASSNAME' => 2, 'BLOCKID' => 3, 'BLOCKNAME' => 4, 'DIAGID' => 5, 'DIAGNAME' => 6, 'PRIM' => 7, 'SEX' => 8, 'AGE' => 9, 'AGE_BU' => 10, 'AGE_BC' => 11, 'AGE_EU' => 12, 'AGE_EC' => 13, 'CHARACTERS' => 14, 'DURATION' => 15, 'SERVICE_ID' => 16, 'MKBSUBCLASS_ID' => 17, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'ClassID' => 1, 'ClassName' => 2, 'BlockID' => 3, 'BlockName' => 4, 'DiagID' => 5, 'DiagName' => 6, 'Prim' => 7, 'sex' => 8, 'age' => 9, 'age_bu' => 10, 'age_bc' => 11, 'age_eu' => 12, 'age_ec' => 13, 'characters' => 14, 'duration' => 15, 'service_id' => 16, 'MKBSubclass_id' => 17, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, )
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
        $toNames = MkbPeer::getFieldNames($toType);
        $key = isset(MkbPeer::$fieldKeys[$fromType][$name]) ? MkbPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(MkbPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, MkbPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return MkbPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. MkbPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(MkbPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(MkbPeer::ID);
            $criteria->addSelectColumn(MkbPeer::CLASSID);
            $criteria->addSelectColumn(MkbPeer::CLASSNAME);
            $criteria->addSelectColumn(MkbPeer::BLOCKID);
            $criteria->addSelectColumn(MkbPeer::BLOCKNAME);
            $criteria->addSelectColumn(MkbPeer::DIAGID);
            $criteria->addSelectColumn(MkbPeer::DIAGNAME);
            $criteria->addSelectColumn(MkbPeer::PRIM);
            $criteria->addSelectColumn(MkbPeer::SEX);
            $criteria->addSelectColumn(MkbPeer::AGE);
            $criteria->addSelectColumn(MkbPeer::AGE_BU);
            $criteria->addSelectColumn(MkbPeer::AGE_BC);
            $criteria->addSelectColumn(MkbPeer::AGE_EU);
            $criteria->addSelectColumn(MkbPeer::AGE_EC);
            $criteria->addSelectColumn(MkbPeer::CHARACTERS);
            $criteria->addSelectColumn(MkbPeer::DURATION);
            $criteria->addSelectColumn(MkbPeer::SERVICE_ID);
            $criteria->addSelectColumn(MkbPeer::MKBSUBCLASS_ID);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.ClassID');
            $criteria->addSelectColumn($alias . '.ClassName');
            $criteria->addSelectColumn($alias . '.BlockID');
            $criteria->addSelectColumn($alias . '.BlockName');
            $criteria->addSelectColumn($alias . '.DiagID');
            $criteria->addSelectColumn($alias . '.DiagName');
            $criteria->addSelectColumn($alias . '.Prim');
            $criteria->addSelectColumn($alias . '.sex');
            $criteria->addSelectColumn($alias . '.age');
            $criteria->addSelectColumn($alias . '.age_bu');
            $criteria->addSelectColumn($alias . '.age_bc');
            $criteria->addSelectColumn($alias . '.age_eu');
            $criteria->addSelectColumn($alias . '.age_ec');
            $criteria->addSelectColumn($alias . '.characters');
            $criteria->addSelectColumn($alias . '.duration');
            $criteria->addSelectColumn($alias . '.service_id');
            $criteria->addSelectColumn($alias . '.MKBSubclass_id');
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
        $criteria->setPrimaryTableName(MkbPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            MkbPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(MkbPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Mkb
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = MkbPeer::doSelect($critcopy, $con);
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
        return MkbPeer::populateObjects(MkbPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            MkbPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(MkbPeer::DATABASE_NAME);

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
     * @param      Mkb $obj A Mkb object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            MkbPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A Mkb object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof Mkb) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or Mkb object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(MkbPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   Mkb Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(MkbPeer::$instances[$key])) {
                return MkbPeer::$instances[$key];
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
        foreach (MkbPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        MkbPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to MKB
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
        $cls = MkbPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = MkbPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = MkbPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                MkbPeer::addInstanceToPool($obj, $key);
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
     * @return array (Mkb object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = MkbPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = MkbPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + MkbPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = MkbPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            MkbPeer::addInstanceToPool($obj, $key);
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
        return Propel::getDatabaseMap(MkbPeer::DATABASE_NAME)->getTable(MkbPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseMkbPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseMkbPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new MkbTableMap());
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
        return MkbPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a Mkb or Criteria object.
     *
     * @param      mixed $values Criteria or Mkb object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from Mkb object
        }

        if ($criteria->containsKey(MkbPeer::ID) && $criteria->keyContainsValue(MkbPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.MkbPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(MkbPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a Mkb or Criteria object.
     *
     * @param      mixed $values Criteria or Mkb object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(MkbPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(MkbPeer::ID);
            $value = $criteria->remove(MkbPeer::ID);
            if ($value) {
                $selectCriteria->add(MkbPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(MkbPeer::TABLE_NAME);
            }

        } else { // $values is Mkb object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(MkbPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the MKB table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(MkbPeer::TABLE_NAME, $con, MkbPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            MkbPeer::clearInstancePool();
            MkbPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a Mkb or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or Mkb object or primary key or array of primary keys
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
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            MkbPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof Mkb) { // it's a model object
            // invalidate the cache for this single object
            MkbPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(MkbPeer::DATABASE_NAME);
            $criteria->add(MkbPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                MkbPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(MkbPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            MkbPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given Mkb object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      Mkb $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(MkbPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(MkbPeer::TABLE_NAME);

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

        return BasePeer::doValidate(MkbPeer::DATABASE_NAME, MkbPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return Mkb
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = MkbPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(MkbPeer::DATABASE_NAME);
        $criteria->add(MkbPeer::ID, $pk);

        $v = MkbPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return Mkb[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(MkbPeer::DATABASE_NAME);
            $criteria->add(MkbPeer::ID, $pks, Criteria::IN);
            $objs = MkbPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseMkbPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseMkbPeer::buildTableMap();

