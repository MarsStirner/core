<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\rbUnitPeer;
use Webmis\Models\rlsActMattersPeer;
use Webmis\Models\rlsFillingPeer;
use Webmis\Models\rlsFormPeer;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenPeer;
use Webmis\Models\rlsPackingPeer;
use Webmis\Models\rlsTradeNamePeer;
use Webmis\Models\map\rlsNomenTableMap;

/**
 * Base static class for performing query and update operations on the 'rlsNomen' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaserlsNomenPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'rlsNomen';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\rlsNomen';

    /** the related TableMap class for this table */
    const TM_CLASS = 'rlsNomenTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 12;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 12;

    /** the column name for the id field */
    const ID = 'rlsNomen.id';

    /** the column name for the actMatters_id field */
    const ACTMATTERS_ID = 'rlsNomen.actMatters_id';

    /** the column name for the tradeName_id field */
    const TRADENAME_ID = 'rlsNomen.tradeName_id';

    /** the column name for the form_id field */
    const FORM_ID = 'rlsNomen.form_id';

    /** the column name for the packing_id field */
    const PACKING_ID = 'rlsNomen.packing_id';

    /** the column name for the filling_id field */
    const FILLING_ID = 'rlsNomen.filling_id';

    /** the column name for the unit_id field */
    const UNIT_ID = 'rlsNomen.unit_id';

    /** the column name for the dosageValue field */
    const DOSAGEVALUE = 'rlsNomen.dosageValue';

    /** the column name for the dosageUnit_id field */
    const DOSAGEUNIT_ID = 'rlsNomen.dosageUnit_id';

    /** the column name for the drugLifetime field */
    const DRUGLIFETIME = 'rlsNomen.drugLifetime';

    /** the column name for the regDate field */
    const REGDATE = 'rlsNomen.regDate';

    /** the column name for the annDate field */
    const ANNDATE = 'rlsNomen.annDate';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of rlsNomen objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array rlsNomen[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. rlsNomenPeer::$fieldNames[rlsNomenPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'actMattersId', 'tradeNameId', 'formId', 'packingId', 'fillingId', 'unitId', 'dosageValue', 'dosageUnitId', 'drugLifetime', 'regDate', 'annDate', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'actMattersId', 'tradeNameId', 'formId', 'packingId', 'fillingId', 'unitId', 'dosageValue', 'dosageUnitId', 'drugLifetime', 'regDate', 'annDate', ),
        BasePeer::TYPE_COLNAME => array (rlsNomenPeer::ID, rlsNomenPeer::ACTMATTERS_ID, rlsNomenPeer::TRADENAME_ID, rlsNomenPeer::FORM_ID, rlsNomenPeer::PACKING_ID, rlsNomenPeer::FILLING_ID, rlsNomenPeer::UNIT_ID, rlsNomenPeer::DOSAGEVALUE, rlsNomenPeer::DOSAGEUNIT_ID, rlsNomenPeer::DRUGLIFETIME, rlsNomenPeer::REGDATE, rlsNomenPeer::ANNDATE, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'ACTMATTERS_ID', 'TRADENAME_ID', 'FORM_ID', 'PACKING_ID', 'FILLING_ID', 'UNIT_ID', 'DOSAGEVALUE', 'DOSAGEUNIT_ID', 'DRUGLIFETIME', 'REGDATE', 'ANNDATE', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'actMatters_id', 'tradeName_id', 'form_id', 'packing_id', 'filling_id', 'unit_id', 'dosageValue', 'dosageUnit_id', 'drugLifetime', 'regDate', 'annDate', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. rlsNomenPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'actMattersId' => 1, 'tradeNameId' => 2, 'formId' => 3, 'packingId' => 4, 'fillingId' => 5, 'unitId' => 6, 'dosageValue' => 7, 'dosageUnitId' => 8, 'drugLifetime' => 9, 'regDate' => 10, 'annDate' => 11, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'actMattersId' => 1, 'tradeNameId' => 2, 'formId' => 3, 'packingId' => 4, 'fillingId' => 5, 'unitId' => 6, 'dosageValue' => 7, 'dosageUnitId' => 8, 'drugLifetime' => 9, 'regDate' => 10, 'annDate' => 11, ),
        BasePeer::TYPE_COLNAME => array (rlsNomenPeer::ID => 0, rlsNomenPeer::ACTMATTERS_ID => 1, rlsNomenPeer::TRADENAME_ID => 2, rlsNomenPeer::FORM_ID => 3, rlsNomenPeer::PACKING_ID => 4, rlsNomenPeer::FILLING_ID => 5, rlsNomenPeer::UNIT_ID => 6, rlsNomenPeer::DOSAGEVALUE => 7, rlsNomenPeer::DOSAGEUNIT_ID => 8, rlsNomenPeer::DRUGLIFETIME => 9, rlsNomenPeer::REGDATE => 10, rlsNomenPeer::ANNDATE => 11, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'ACTMATTERS_ID' => 1, 'TRADENAME_ID' => 2, 'FORM_ID' => 3, 'PACKING_ID' => 4, 'FILLING_ID' => 5, 'UNIT_ID' => 6, 'DOSAGEVALUE' => 7, 'DOSAGEUNIT_ID' => 8, 'DRUGLIFETIME' => 9, 'REGDATE' => 10, 'ANNDATE' => 11, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'actMatters_id' => 1, 'tradeName_id' => 2, 'form_id' => 3, 'packing_id' => 4, 'filling_id' => 5, 'unit_id' => 6, 'dosageValue' => 7, 'dosageUnit_id' => 8, 'drugLifetime' => 9, 'regDate' => 10, 'annDate' => 11, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, )
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
        $toNames = rlsNomenPeer::getFieldNames($toType);
        $key = isset(rlsNomenPeer::$fieldKeys[$fromType][$name]) ? rlsNomenPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(rlsNomenPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, rlsNomenPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return rlsNomenPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. rlsNomenPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(rlsNomenPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(rlsNomenPeer::ID);
            $criteria->addSelectColumn(rlsNomenPeer::ACTMATTERS_ID);
            $criteria->addSelectColumn(rlsNomenPeer::TRADENAME_ID);
            $criteria->addSelectColumn(rlsNomenPeer::FORM_ID);
            $criteria->addSelectColumn(rlsNomenPeer::PACKING_ID);
            $criteria->addSelectColumn(rlsNomenPeer::FILLING_ID);
            $criteria->addSelectColumn(rlsNomenPeer::UNIT_ID);
            $criteria->addSelectColumn(rlsNomenPeer::DOSAGEVALUE);
            $criteria->addSelectColumn(rlsNomenPeer::DOSAGEUNIT_ID);
            $criteria->addSelectColumn(rlsNomenPeer::DRUGLIFETIME);
            $criteria->addSelectColumn(rlsNomenPeer::REGDATE);
            $criteria->addSelectColumn(rlsNomenPeer::ANNDATE);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.actMatters_id');
            $criteria->addSelectColumn($alias . '.tradeName_id');
            $criteria->addSelectColumn($alias . '.form_id');
            $criteria->addSelectColumn($alias . '.packing_id');
            $criteria->addSelectColumn($alias . '.filling_id');
            $criteria->addSelectColumn($alias . '.unit_id');
            $criteria->addSelectColumn($alias . '.dosageValue');
            $criteria->addSelectColumn($alias . '.dosageUnit_id');
            $criteria->addSelectColumn($alias . '.drugLifetime');
            $criteria->addSelectColumn($alias . '.regDate');
            $criteria->addSelectColumn($alias . '.annDate');
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
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 rlsNomen
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = rlsNomenPeer::doSelect($critcopy, $con);
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
        return rlsNomenPeer::populateObjects(rlsNomenPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            rlsNomenPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

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
     * @param      rlsNomen $obj A rlsNomen object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            rlsNomenPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A rlsNomen object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof rlsNomen) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or rlsNomen object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(rlsNomenPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   rlsNomen Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(rlsNomenPeer::$instances[$key])) {
                return rlsNomenPeer::$instances[$key];
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
        foreach (rlsNomenPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        rlsNomenPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to rlsNomen
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
        $cls = rlsNomenPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = rlsNomenPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                rlsNomenPeer::addInstanceToPool($obj, $key);
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
     * @return array (rlsNomen object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = rlsNomenPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = rlsNomenPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = rlsNomenPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            rlsNomenPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related rbUnitRelatedByunitId table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrbUnitRelatedByunitId(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rbUnitRelatedBydosageUnitId table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrbUnitRelatedBydosageUnitId(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsFilling table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrlsFilling(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsForm table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrlsForm(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsPacking table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrlsPacking(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsActMatters table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrlsActMatters(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsTradeName table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinrlsTradeName(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Selects a collection of rlsNomen objects pre-filled with their rbUnit objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrbUnitRelatedByunitId(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rbUnitPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rbUnit objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrbUnitRelatedBydosageUnitId(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rbUnitPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rlsFilling objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrlsFilling(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rlsFillingPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rlsFillingPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rlsFillingPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rlsFillingPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rlsFilling)
                $obj2->addrlsNomen($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rlsForm objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrlsForm(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rlsFormPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rlsFormPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rlsFormPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rlsFormPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rlsForm)
                $obj2->addrlsNomen($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rlsPacking objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrlsPacking(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rlsPackingPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rlsPackingPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rlsPackingPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rlsPackingPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rlsPacking)
                $obj2->addrlsNomen($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rlsActMatters objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrlsActMatters(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rlsActMattersPeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rlsActMattersPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rlsActMattersPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rlsActMattersPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rlsActMatters)
                $obj2->addrlsNomen($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with their rlsTradeName objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinrlsTradeName(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol = rlsNomenPeer::NUM_HYDRATE_COLUMNS;
        rlsTradeNamePeer::addSelectColumns($criteria);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = rlsTradeNamePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rlsTradeNamePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    rlsTradeNamePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (rlsNomen) to $obj2 (rlsTradeName)
                $obj2->addrlsNomen($obj1);

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
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Selects a collection of rlsNomen objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined rbUnit rows

            $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);
            } // if joined row not null

            // Add objects for joined rbUnit rows

            $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);
            } // if joined row not null

            // Add objects for joined rlsFilling rows

            $key4 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
            if ($key4 !== null) {
                $obj4 = rlsFillingPeer::getInstanceFromPool($key4);
                if (!$obj4) {

                    $cls = rlsFillingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFillingPeer::addInstanceToPool($obj4, $key4);
                } // if obj4 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsFilling)
                $obj4->addrlsNomen($obj1);
            } // if joined row not null

            // Add objects for joined rlsForm rows

            $key5 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol5);
            if ($key5 !== null) {
                $obj5 = rlsFormPeer::getInstanceFromPool($key5);
                if (!$obj5) {

                    $cls = rlsFormPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsFormPeer::addInstanceToPool($obj5, $key5);
                } // if obj5 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsForm)
                $obj5->addrlsNomen($obj1);
            } // if joined row not null

            // Add objects for joined rlsPacking rows

            $key6 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol6);
            if ($key6 !== null) {
                $obj6 = rlsPackingPeer::getInstanceFromPool($key6);
                if (!$obj6) {

                    $cls = rlsPackingPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsPackingPeer::addInstanceToPool($obj6, $key6);
                } // if obj6 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsPacking)
                $obj6->addrlsNomen($obj1);
            } // if joined row not null

            // Add objects for joined rlsActMatters rows

            $key7 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol7);
            if ($key7 !== null) {
                $obj7 = rlsActMattersPeer::getInstanceFromPool($key7);
                if (!$obj7) {

                    $cls = rlsActMattersPeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsActMattersPeer::addInstanceToPool($obj7, $key7);
                } // if obj7 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsActMatters)
                $obj7->addrlsNomen($obj1);
            } // if joined row not null

            // Add objects for joined rlsTradeName rows

            $key8 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol8);
            if ($key8 !== null) {
                $obj8 = rlsTradeNamePeer::getInstanceFromPool($key8);
                if (!$obj8) {

                    $cls = rlsTradeNamePeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    rlsTradeNamePeer::addInstanceToPool($obj8, $key8);
                } // if obj8 loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj8 (rlsTradeName)
                $obj8->addrlsNomen($obj1);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related rbUnitRelatedByunitId table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrbUnitRelatedByunitId(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rbUnitRelatedBydosageUnitId table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrbUnitRelatedBydosageUnitId(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsFilling table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrlsFilling(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsForm table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrlsForm(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsPacking table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrlsPacking(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsActMatters table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrlsActMatters(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related rlsTradeName table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptrlsTradeName(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            rlsNomenPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

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
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rbUnitRelatedByunitId.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrbUnitRelatedByunitId(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rlsFilling rows

                $key2 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rlsFillingPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rlsFillingPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rlsFilling)
                $obj2->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key3 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rlsFormPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rlsFormPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rlsForm)
                $obj3->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key4 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsPackingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsPackingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsPacking)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key5 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsActMattersPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsActMattersPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsActMatters)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key6 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsTradeNamePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsTradeNamePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsTradeName)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rbUnitRelatedBydosageUnitId.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrbUnitRelatedBydosageUnitId(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rlsFilling rows

                $key2 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rlsFillingPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rlsFillingPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rlsFilling)
                $obj2->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key3 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rlsFormPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rlsFormPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rlsForm)
                $obj3->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key4 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsPackingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsPackingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsPacking)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key5 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsActMattersPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsActMattersPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsActMatters)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key6 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsTradeNamePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsTradeNamePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsTradeName)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rlsFilling.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrlsFilling(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rbUnit rows

                $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row is not null

                // Add objects for joined rbUnit rows

                $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key4 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsFormPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFormPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsForm)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key5 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsPackingPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsPackingPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsPacking)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key6 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsActMattersPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsActMattersPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsActMatters)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key7 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = rlsTradeNamePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsTradeNamePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsTradeName)
                $obj7->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rlsForm.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrlsForm(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rbUnit rows

                $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row is not null

                // Add objects for joined rbUnit rows

                $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row is not null

                // Add objects for joined rlsFilling rows

                $key4 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsFillingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFillingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsFilling)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key5 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsPackingPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsPackingPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsPacking)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key6 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsActMattersPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsActMattersPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsActMatters)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key7 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = rlsTradeNamePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsTradeNamePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsTradeName)
                $obj7->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rlsPacking.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrlsPacking(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rbUnit rows

                $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row is not null

                // Add objects for joined rbUnit rows

                $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row is not null

                // Add objects for joined rlsFilling rows

                $key4 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsFillingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFillingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsFilling)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key5 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsFormPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsFormPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsForm)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key6 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsActMattersPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsActMattersPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsActMatters)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key7 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = rlsTradeNamePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsTradeNamePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsTradeName)
                $obj7->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rlsActMatters.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrlsActMatters(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsTradeNamePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsTradeNamePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::TRADENAME_ID, rlsTradeNamePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rbUnit rows

                $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row is not null

                // Add objects for joined rbUnit rows

                $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row is not null

                // Add objects for joined rlsFilling rows

                $key4 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsFillingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFillingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsFilling)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key5 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsFormPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsFormPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsForm)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key6 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsPackingPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsPackingPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsPacking)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsTradeName rows

                $key7 = rlsTradeNamePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = rlsTradeNamePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = rlsTradeNamePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsTradeNamePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsTradeName)
                $obj7->addrlsNomen($obj1);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of rlsNomen objects pre-filled with all related objects except rlsTradeName.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of rlsNomen objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptrlsTradeName(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);
        }

        rlsNomenPeer::addSelectColumns($criteria);
        $startcol2 = rlsNomenPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rbUnitPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + rbUnitPeer::NUM_HYDRATE_COLUMNS;

        rlsFillingPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + rlsFillingPeer::NUM_HYDRATE_COLUMNS;

        rlsFormPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + rlsFormPeer::NUM_HYDRATE_COLUMNS;

        rlsPackingPeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + rlsPackingPeer::NUM_HYDRATE_COLUMNS;

        rlsActMattersPeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + rlsActMattersPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(rlsNomenPeer::UNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::DOSAGEUNIT_ID, rbUnitPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FILLING_ID, rlsFillingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::FORM_ID, rlsFormPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::PACKING_ID, rlsPackingPeer::ID, $join_behavior);

        $criteria->addJoin(rlsNomenPeer::ACTMATTERS_ID, rlsActMattersPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = rlsNomenPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = rlsNomenPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = rlsNomenPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                rlsNomenPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined rbUnit rows

                $key2 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = rbUnitPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    rbUnitPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj2 (rbUnit)
                $obj2->addrlsNomenRelatedByunitId($obj1);

            } // if joined row is not null

                // Add objects for joined rbUnit rows

                $key3 = rbUnitPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = rbUnitPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = rbUnitPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    rbUnitPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj3 (rbUnit)
                $obj3->addrlsNomenRelatedBydosageUnitId($obj1);

            } // if joined row is not null

                // Add objects for joined rlsFilling rows

                $key4 = rlsFillingPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = rlsFillingPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = rlsFillingPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    rlsFillingPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj4 (rlsFilling)
                $obj4->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsForm rows

                $key5 = rlsFormPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = rlsFormPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = rlsFormPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    rlsFormPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj5 (rlsForm)
                $obj5->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsPacking rows

                $key6 = rlsPackingPeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = rlsPackingPeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = rlsPackingPeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    rlsPackingPeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj6 (rlsPacking)
                $obj6->addrlsNomen($obj1);

            } // if joined row is not null

                // Add objects for joined rlsActMatters rows

                $key7 = rlsActMattersPeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = rlsActMattersPeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = rlsActMattersPeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    rlsActMattersPeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (rlsNomen) to the collection in $obj7 (rlsActMatters)
                $obj7->addrlsNomen($obj1);

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
        return Propel::getDatabaseMap(rlsNomenPeer::DATABASE_NAME)->getTable(rlsNomenPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaserlsNomenPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaserlsNomenPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new rlsNomenTableMap());
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
        return rlsNomenPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a rlsNomen or Criteria object.
     *
     * @param      mixed $values Criteria or rlsNomen object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from rlsNomen object
        }


        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a rlsNomen or Criteria object.
     *
     * @param      mixed $values Criteria or rlsNomen object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(rlsNomenPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(rlsNomenPeer::ID);
            $value = $criteria->remove(rlsNomenPeer::ID);
            if ($value) {
                $selectCriteria->add(rlsNomenPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(rlsNomenPeer::TABLE_NAME);
            }

        } else { // $values is rlsNomen object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the rlsNomen table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(rlsNomenPeer::TABLE_NAME, $con, rlsNomenPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            rlsNomenPeer::clearInstancePool();
            rlsNomenPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a rlsNomen or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or rlsNomen object or primary key or array of primary keys
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
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            rlsNomenPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof rlsNomen) { // it's a model object
            // invalidate the cache for this single object
            rlsNomenPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(rlsNomenPeer::DATABASE_NAME);
            $criteria->add(rlsNomenPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                rlsNomenPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(rlsNomenPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            rlsNomenPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given rlsNomen object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      rlsNomen $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(rlsNomenPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(rlsNomenPeer::TABLE_NAME);

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

        return BasePeer::doValidate(rlsNomenPeer::DATABASE_NAME, rlsNomenPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return rlsNomen
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = rlsNomenPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(rlsNomenPeer::DATABASE_NAME);
        $criteria->add(rlsNomenPeer::ID, $pk);

        $v = rlsNomenPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return rlsNomen[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(rlsNomenPeer::DATABASE_NAME);
            $criteria->add(rlsNomenPeer::ID, $pks, Criteria::IN);
            $objs = rlsNomenPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaserlsNomenPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaserlsNomenPeer::buildTableMap();

