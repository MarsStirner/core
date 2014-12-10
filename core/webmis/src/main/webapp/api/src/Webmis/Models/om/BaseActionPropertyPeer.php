<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\ActionPeer;
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyDatePeer;
use Webmis\Models\ActionPropertyDoublePeer;
use Webmis\Models\ActionPropertyFDRecordPeer;
use Webmis\Models\ActionPropertyIntegerPeer;
use Webmis\Models\ActionPropertyOrgStructurePeer;
use Webmis\Models\ActionPropertyPeer;
use Webmis\Models\ActionPropertyStringPeer;
use Webmis\Models\ActionPropertyTimePeer;
use Webmis\Models\ActionPropertyTypePeer;
use Webmis\Models\map\ActionPropertyTableMap;

/**
 * Base static class for performing query and update operations on the 'ActionProperty' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseActionPropertyPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'ActionProperty';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\ActionProperty';

    /** the related TableMap class for this table */
    const TM_CLASS = 'ActionPropertyTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 13;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 13;

    /** the column name for the id field */
    const ID = 'ActionProperty.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'ActionProperty.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'ActionProperty.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'ActionProperty.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'ActionProperty.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'ActionProperty.deleted';

    /** the column name for the action_id field */
    const ACTION_ID = 'ActionProperty.action_id';

    /** the column name for the type_id field */
    const TYPE_ID = 'ActionProperty.type_id';

    /** the column name for the unit_id field */
    const UNIT_ID = 'ActionProperty.unit_id';

    /** the column name for the norm field */
    const NORM = 'ActionProperty.norm';

    /** the column name for the isAssigned field */
    const ISASSIGNED = 'ActionProperty.isAssigned';

    /** the column name for the evaluation field */
    const EVALUATION = 'ActionProperty.evaluation';

    /** the column name for the version field */
    const VERSION = 'ActionProperty.version';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of ActionProperty objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array ActionProperty[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. ActionPropertyPeer::$fieldNames[ActionPropertyPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'actionId', 'typeId', 'unitId', 'norm', 'isAssigned', 'evaluation', 'version', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'actionId', 'typeId', 'unitId', 'norm', 'isAssigned', 'evaluation', 'version', ),
        BasePeer::TYPE_COLNAME => array (ActionPropertyPeer::ID, ActionPropertyPeer::CREATEDATETIME, ActionPropertyPeer::CREATEPERSON_ID, ActionPropertyPeer::MODIFYDATETIME, ActionPropertyPeer::MODIFYPERSON_ID, ActionPropertyPeer::DELETED, ActionPropertyPeer::ACTION_ID, ActionPropertyPeer::TYPE_ID, ActionPropertyPeer::UNIT_ID, ActionPropertyPeer::NORM, ActionPropertyPeer::ISASSIGNED, ActionPropertyPeer::EVALUATION, ActionPropertyPeer::VERSION, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'ACTION_ID', 'TYPE_ID', 'UNIT_ID', 'NORM', 'ISASSIGNED', 'EVALUATION', 'VERSION', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'action_id', 'type_id', 'unit_id', 'norm', 'isAssigned', 'evaluation', 'version', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. ActionPropertyPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'actionId' => 6, 'typeId' => 7, 'unitId' => 8, 'norm' => 9, 'isAssigned' => 10, 'evaluation' => 11, 'version' => 12, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'actionId' => 6, 'typeId' => 7, 'unitId' => 8, 'norm' => 9, 'isAssigned' => 10, 'evaluation' => 11, 'version' => 12, ),
        BasePeer::TYPE_COLNAME => array (ActionPropertyPeer::ID => 0, ActionPropertyPeer::CREATEDATETIME => 1, ActionPropertyPeer::CREATEPERSON_ID => 2, ActionPropertyPeer::MODIFYDATETIME => 3, ActionPropertyPeer::MODIFYPERSON_ID => 4, ActionPropertyPeer::DELETED => 5, ActionPropertyPeer::ACTION_ID => 6, ActionPropertyPeer::TYPE_ID => 7, ActionPropertyPeer::UNIT_ID => 8, ActionPropertyPeer::NORM => 9, ActionPropertyPeer::ISASSIGNED => 10, ActionPropertyPeer::EVALUATION => 11, ActionPropertyPeer::VERSION => 12, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'ACTION_ID' => 6, 'TYPE_ID' => 7, 'UNIT_ID' => 8, 'NORM' => 9, 'ISASSIGNED' => 10, 'EVALUATION' => 11, 'VERSION' => 12, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'action_id' => 6, 'type_id' => 7, 'unit_id' => 8, 'norm' => 9, 'isAssigned' => 10, 'evaluation' => 11, 'version' => 12, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, )
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
        $toNames = ActionPropertyPeer::getFieldNames($toType);
        $key = isset(ActionPropertyPeer::$fieldKeys[$fromType][$name]) ? ActionPropertyPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(ActionPropertyPeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, ActionPropertyPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return ActionPropertyPeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. ActionPropertyPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(ActionPropertyPeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(ActionPropertyPeer::ID);
            $criteria->addSelectColumn(ActionPropertyPeer::CREATEDATETIME);
            $criteria->addSelectColumn(ActionPropertyPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(ActionPropertyPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(ActionPropertyPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(ActionPropertyPeer::DELETED);
            $criteria->addSelectColumn(ActionPropertyPeer::ACTION_ID);
            $criteria->addSelectColumn(ActionPropertyPeer::TYPE_ID);
            $criteria->addSelectColumn(ActionPropertyPeer::UNIT_ID);
            $criteria->addSelectColumn(ActionPropertyPeer::NORM);
            $criteria->addSelectColumn(ActionPropertyPeer::ISASSIGNED);
            $criteria->addSelectColumn(ActionPropertyPeer::EVALUATION);
            $criteria->addSelectColumn(ActionPropertyPeer::VERSION);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.action_id');
            $criteria->addSelectColumn($alias . '.type_id');
            $criteria->addSelectColumn($alias . '.unit_id');
            $criteria->addSelectColumn($alias . '.norm');
            $criteria->addSelectColumn($alias . '.isAssigned');
            $criteria->addSelectColumn($alias . '.evaluation');
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
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionProperty
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = ActionPropertyPeer::doSelect($critcopy, $con);
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
        return ActionPropertyPeer::populateObjects(ActionPropertyPeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

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
     * @param      ActionProperty $obj A ActionProperty object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            ActionPropertyPeer::$instances[$key] = $obj;
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
     * @param      mixed $value A ActionProperty object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof ActionProperty) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or ActionProperty object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(ActionPropertyPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   ActionProperty Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(ActionPropertyPeer::$instances[$key])) {
                return ActionPropertyPeer::$instances[$key];
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
        foreach (ActionPropertyPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        ActionPropertyPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to ActionProperty
     * by a foreign key with ON DELETE CASCADE
     */
    public static function clearRelatedInstancePool()
    {
        // Invalidate objects in ActionPropertyTimePeer instance pool,
        // since one or more of them may be deleted by ON DELETE CASCADE/SETNULL rule.
        ActionPropertyTimePeer::clearInstancePool();
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
        $cls = ActionPropertyPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = ActionPropertyPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                ActionPropertyPeer::addInstanceToPool($obj, $key);
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
     * @return array (ActionProperty object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = ActionPropertyPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = ActionPropertyPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            ActionPropertyPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related Action table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAction(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyString table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyString(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyInteger table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyInteger(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyDate table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyDate(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyDouble table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyDouble(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyOrgStructure table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyOrgStructure(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyFDRecord table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyFDRecord(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Selects a collection of ActionProperty objects pre-filled with their Action objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAction(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyType objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyTypePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyType)
                $obj2->addActionProperty($obj1);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyString objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyString(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyStringPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyStringPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyStringPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyStringPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyString)
                // one to one relationship
                $obj1->setActionPropertyString($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyInteger objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyInteger(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyIntegerPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyIntegerPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyInteger)
                // one to one relationship
                $obj1->setActionPropertyInteger($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyDate objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyDate(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyDatePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyDatePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyDatePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyDatePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyDate)
                // one to one relationship
                $obj1->setActionPropertyDate($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyDouble objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyDouble(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyDoublePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyDoublePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyDoublePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyDouble)
                // one to one relationship
                $obj1->setActionPropertyDouble($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyOrgStructure objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyOrgStructure(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyOrgStructure)
                // one to one relationship
                $obj1->setActionPropertyOrgStructure($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with their ActionPropertyFDRecord objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyFDRecord(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyFDRecordPeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyFDRecordPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionProperty) to $obj2 (ActionPropertyFDRecord)
                // one to one relationship
                $obj1->setActionPropertyFDRecord($obj2);

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
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Selects a collection of ActionProperty objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol10 = $startcol9 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined Action rows

            $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = ActionPeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);
            } // if joined row not null

            // Add objects for joined ActionPropertyType rows

            $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
            if ($key3 !== null) {
                $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                if (!$obj3) {

                    $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if obj3 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);
            } // if joined row not null

            // Add objects for joined ActionPropertyString rows

            $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
            if ($key4 !== null) {
                $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                if (!$obj4) {

                    $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if obj4 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);
            } // if joined row not null

            // Add objects for joined ActionPropertyInteger rows

            $key5 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol5);
            if ($key5 !== null) {
                $obj5 = ActionPropertyIntegerPeer::getInstanceFromPool($key5);
                if (!$obj5) {

                    $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj5, $key5);
                } // if obj5 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj5);
            } // if joined row not null

            // Add objects for joined ActionPropertyDate rows

            $key6 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol6);
            if ($key6 !== null) {
                $obj6 = ActionPropertyDatePeer::getInstanceFromPool($key6);
                if (!$obj6) {

                    $cls = ActionPropertyDatePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDatePeer::addInstanceToPool($obj6, $key6);
                } // if obj6 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj6);
            } // if joined row not null

            // Add objects for joined ActionPropertyDouble rows

            $key7 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol7);
            if ($key7 !== null) {
                $obj7 = ActionPropertyDoublePeer::getInstanceFromPool($key7);
                if (!$obj7) {

                    $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyDoublePeer::addInstanceToPool($obj7, $key7);
                } // if obj7 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj7);
            } // if joined row not null

            // Add objects for joined ActionPropertyOrgStructure rows

            $key8 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol8);
            if ($key8 !== null) {
                $obj8 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key8);
                if (!$obj8) {

                    $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj8, $key8);
                } // if obj8 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj8);
            } // if joined row not null

            // Add objects for joined ActionPropertyFDRecord rows

            $key9 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol9);
            if ($key9 !== null) {
                $obj9 = ActionPropertyFDRecordPeer::getInstanceFromPool($key9);
                if (!$obj9) {

                    $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj9 = new $cls();
                    $obj9->hydrate($row, $startcol9);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj9, $key9);
                } // if obj9 loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj9 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj9);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining the related Action table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptAction(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyType table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyType(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyString table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyString(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyInteger table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyInteger(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyDate table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyDate(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyDouble table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyDouble(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyOrgStructure table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyOrgStructure(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);

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
     * Returns the number of rows matching criteria, joining the related ActionPropertyFDRecord table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAllExceptActionPropertyFDRecord(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionPropertyPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY should not affect count

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

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
     * Selects a collection of ActionProperty objects pre-filled with all related objects except Action.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptAction(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined ActionPropertyType rows

                $key2 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPropertyTypePeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPropertyTypePeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (ActionPropertyType)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key3 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyStringPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyStringPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyString)
                $obj1->setActionPropertyString($obj3);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key4 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyIntegerPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key5 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyDatePeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyDatePeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key6 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDoublePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDoublePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyType.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyType(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key3 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyStringPeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyStringPeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyString)
                $obj1->setActionPropertyString($obj3);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key4 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyIntegerPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key5 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyDatePeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyDatePeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key6 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDoublePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDoublePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyString.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyString(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key4 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyIntegerPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key5 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyDatePeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyDatePeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key6 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDoublePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDoublePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyInteger.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyInteger(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key5 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyDatePeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyDatePeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key6 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDoublePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDoublePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyDate.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyDate(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key5 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyIntegerPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key6 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDoublePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDoublePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyDouble.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyDouble(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key5 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyIntegerPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key6 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDatePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDatePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key7 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyOrgStructure.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyOrgStructure(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyFDRecordPeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyFDRecordPeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyFDRecordPeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key5 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyIntegerPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key6 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDatePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDatePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key7 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyDoublePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyDoublePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyFDRecord rows

                $key8 = ActionPropertyFDRecordPeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyFDRecordPeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyFDRecordPeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyFDRecordPeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyFDRecord)
                $obj1->setActionPropertyFDRecord($obj8);

            } // if joined row is not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Selects a collection of ActionProperty objects pre-filled with all related objects except ActionPropertyFDRecord.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionProperty objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAllExceptActionPropertyFDRecord(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        // $criteria->getDbName() will return the same object if not set to another value
        // so == check is okay and faster
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);
        }

        ActionPropertyPeer::addSelectColumns($criteria);
        $startcol2 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS;

        ActionPeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol4 = $startcol3 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyStringPeer::addSelectColumns($criteria);
        $startcol5 = $startcol4 + ActionPropertyStringPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyIntegerPeer::addSelectColumns($criteria);
        $startcol6 = $startcol5 + ActionPropertyIntegerPeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDatePeer::addSelectColumns($criteria);
        $startcol7 = $startcol6 + ActionPropertyDatePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyDoublePeer::addSelectColumns($criteria);
        $startcol8 = $startcol7 + ActionPropertyDoublePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyOrgStructurePeer::addSelectColumns($criteria);
        $startcol9 = $startcol8 + ActionPropertyOrgStructurePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionPropertyPeer::ACTION_ID, ActionPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::TYPE_ID, ActionPropertyTypePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyStringPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyIntegerPeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDatePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyDoublePeer::ID, $join_behavior);

        $criteria->addJoin(ActionPropertyPeer::ID, ActionPropertyOrgStructurePeer::ID, $join_behavior);


        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionPropertyPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionPropertyPeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionPropertyPeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionPropertyPeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

                // Add objects for joined Action rows

                $key2 = ActionPeer::getPrimaryKeyHashFromRow($row, $startcol2);
                if ($key2 !== null) {
                    $obj2 = ActionPeer::getInstanceFromPool($key2);
                    if (!$obj2) {

                        $cls = ActionPeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPeer::addInstanceToPool($obj2, $key2);
                } // if $obj2 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj2 (Action)
                $obj2->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyType rows

                $key3 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol3);
                if ($key3 !== null) {
                    $obj3 = ActionPropertyTypePeer::getInstanceFromPool($key3);
                    if (!$obj3) {

                        $cls = ActionPropertyTypePeer::getOMClass();

                    $obj3 = new $cls();
                    $obj3->hydrate($row, $startcol3);
                    ActionPropertyTypePeer::addInstanceToPool($obj3, $key3);
                } // if $obj3 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj3 (ActionPropertyType)
                $obj3->addActionProperty($obj1);

            } // if joined row is not null

                // Add objects for joined ActionPropertyString rows

                $key4 = ActionPropertyStringPeer::getPrimaryKeyHashFromRow($row, $startcol4);
                if ($key4 !== null) {
                    $obj4 = ActionPropertyStringPeer::getInstanceFromPool($key4);
                    if (!$obj4) {

                        $cls = ActionPropertyStringPeer::getOMClass();

                    $obj4 = new $cls();
                    $obj4->hydrate($row, $startcol4);
                    ActionPropertyStringPeer::addInstanceToPool($obj4, $key4);
                } // if $obj4 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj4 (ActionPropertyString)
                $obj1->setActionPropertyString($obj4);

            } // if joined row is not null

                // Add objects for joined ActionPropertyInteger rows

                $key5 = ActionPropertyIntegerPeer::getPrimaryKeyHashFromRow($row, $startcol5);
                if ($key5 !== null) {
                    $obj5 = ActionPropertyIntegerPeer::getInstanceFromPool($key5);
                    if (!$obj5) {

                        $cls = ActionPropertyIntegerPeer::getOMClass();

                    $obj5 = new $cls();
                    $obj5->hydrate($row, $startcol5);
                    ActionPropertyIntegerPeer::addInstanceToPool($obj5, $key5);
                } // if $obj5 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj5 (ActionPropertyInteger)
                $obj1->setActionPropertyInteger($obj5);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDate rows

                $key6 = ActionPropertyDatePeer::getPrimaryKeyHashFromRow($row, $startcol6);
                if ($key6 !== null) {
                    $obj6 = ActionPropertyDatePeer::getInstanceFromPool($key6);
                    if (!$obj6) {

                        $cls = ActionPropertyDatePeer::getOMClass();

                    $obj6 = new $cls();
                    $obj6->hydrate($row, $startcol6);
                    ActionPropertyDatePeer::addInstanceToPool($obj6, $key6);
                } // if $obj6 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj6 (ActionPropertyDate)
                $obj1->setActionPropertyDate($obj6);

            } // if joined row is not null

                // Add objects for joined ActionPropertyDouble rows

                $key7 = ActionPropertyDoublePeer::getPrimaryKeyHashFromRow($row, $startcol7);
                if ($key7 !== null) {
                    $obj7 = ActionPropertyDoublePeer::getInstanceFromPool($key7);
                    if (!$obj7) {

                        $cls = ActionPropertyDoublePeer::getOMClass();

                    $obj7 = new $cls();
                    $obj7->hydrate($row, $startcol7);
                    ActionPropertyDoublePeer::addInstanceToPool($obj7, $key7);
                } // if $obj7 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj7 (ActionPropertyDouble)
                $obj1->setActionPropertyDouble($obj7);

            } // if joined row is not null

                // Add objects for joined ActionPropertyOrgStructure rows

                $key8 = ActionPropertyOrgStructurePeer::getPrimaryKeyHashFromRow($row, $startcol8);
                if ($key8 !== null) {
                    $obj8 = ActionPropertyOrgStructurePeer::getInstanceFromPool($key8);
                    if (!$obj8) {

                        $cls = ActionPropertyOrgStructurePeer::getOMClass();

                    $obj8 = new $cls();
                    $obj8->hydrate($row, $startcol8);
                    ActionPropertyOrgStructurePeer::addInstanceToPool($obj8, $key8);
                } // if $obj8 already loaded

                // Add the $obj1 (ActionProperty) to the collection in $obj8 (ActionPropertyOrgStructure)
                $obj1->setActionPropertyOrgStructure($obj8);

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
        return Propel::getDatabaseMap(ActionPropertyPeer::DATABASE_NAME)->getTable(ActionPropertyPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseActionPropertyPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseActionPropertyPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new ActionPropertyTableMap());
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
        return ActionPropertyPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a ActionProperty or Criteria object.
     *
     * @param      mixed $values Criteria or ActionProperty object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from ActionProperty object
        }

        if ($criteria->containsKey(ActionPropertyPeer::ID) && $criteria->keyContainsValue(ActionPropertyPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.ActionPropertyPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a ActionProperty or Criteria object.
     *
     * @param      mixed $values Criteria or ActionProperty object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(ActionPropertyPeer::ID);
            $value = $criteria->remove(ActionPropertyPeer::ID);
            if ($value) {
                $selectCriteria->add(ActionPropertyPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(ActionPropertyPeer::TABLE_NAME);
            }

        } else { // $values is ActionProperty object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the ActionProperty table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += ActionPropertyPeer::doOnDeleteCascade(new Criteria(ActionPropertyPeer::DATABASE_NAME), $con);
            $affectedRows += BasePeer::doDeleteAll(ActionPropertyPeer::TABLE_NAME, $con, ActionPropertyPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            ActionPropertyPeer::clearInstancePool();
            ActionPropertyPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a ActionProperty or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or ActionProperty object or primary key or array of primary keys
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
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof ActionProperty) { // it's a model object
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);
            $criteria->add(ActionPropertyPeer::ID, (array) $values, Criteria::IN);
        }

        // Set the correct dbName
        $criteria->setDbName(ActionPropertyPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            // cloning the Criteria in case it's modified by doSelect() or doSelectStmt()
            $c = clone $criteria;
            $affectedRows += ActionPropertyPeer::doOnDeleteCascade($c, $con);

            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            if ($values instanceof Criteria) {
                ActionPropertyPeer::clearInstancePool();
            } elseif ($values instanceof ActionProperty) { // it's a model object
                ActionPropertyPeer::removeInstanceFromPool($values);
            } else { // it's a primary key, or an array of pks
                foreach ((array) $values as $singleval) {
                    ActionPropertyPeer::removeInstanceFromPool($singleval);
                }
            }

            $affectedRows += BasePeer::doDelete($criteria, $con);
            ActionPropertyPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * This is a method for emulating ON DELETE CASCADE for DBs that don't support this
     * feature (like MySQL or SQLite).
     *
     * This method is not very speedy because it must perform a query first to get
     * the implicated records and then perform the deletes by calling those Peer classes.
     *
     * This method should be used within a transaction if possible.
     *
     * @param      Criteria $criteria
     * @param      PropelPDO $con
     * @return int The number of affected rows (if supported by underlying database driver).
     */
    protected static function doOnDeleteCascade(Criteria $criteria, PropelPDO $con)
    {
        // initialize var to track total num of affected rows
        $affectedRows = 0;

        // first find the objects that are implicated by the $criteria
        $objects = ActionPropertyPeer::doSelect($criteria, $con);
        foreach ($objects as $obj) {


            // delete related ActionPropertyTime objects
            $criteria = new Criteria(ActionPropertyTimePeer::DATABASE_NAME);

            $criteria->add(ActionPropertyTimePeer::ID, $obj->getid());
            $affectedRows += ActionPropertyTimePeer::doDelete($criteria, $con);
        }

        return $affectedRows;
    }

    /**
     * Validates all modified columns of given ActionProperty object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      ActionProperty $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(ActionPropertyPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(ActionPropertyPeer::TABLE_NAME);

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

        return BasePeer::doValidate(ActionPropertyPeer::DATABASE_NAME, ActionPropertyPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return ActionProperty
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = ActionPropertyPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);
        $criteria->add(ActionPropertyPeer::ID, $pk);

        $v = ActionPropertyPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return ActionProperty[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);
            $criteria->add(ActionPropertyPeer::ID, $pks, Criteria::IN);
            $objs = ActionPropertyPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseActionPropertyPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseActionPropertyPeer::buildTableMap();

