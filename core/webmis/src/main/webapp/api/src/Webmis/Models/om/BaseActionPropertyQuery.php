<?php

namespace Webmis\Models\om;

use \Criteria;
use \Exception;
use \ModelCriteria;
use \ModelJoin;
use \PDO;
use \Propel;
use \PropelCollection;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\Action;
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyDate;
use Webmis\Models\ActionPropertyDouble;
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\ActionPropertyInteger;
use Webmis\Models\ActionPropertyOrgStructure;
use Webmis\Models\ActionPropertyPeer;
use Webmis\Models\ActionPropertyQuery;
use Webmis\Models\ActionPropertyString;
use Webmis\Models\ActionPropertyTime;
use Webmis\Models\ActionPropertyType;

/**
 * Base class that represents a query for the 'ActionProperty' table.
 *
 *
 *
 * @method ActionPropertyQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method ActionPropertyQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method ActionPropertyQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method ActionPropertyQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method ActionPropertyQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ActionPropertyQuery orderByactionId($order = Criteria::ASC) Order by the action_id column
 * @method ActionPropertyQuery orderBytypeId($order = Criteria::ASC) Order by the type_id column
 * @method ActionPropertyQuery orderByunitId($order = Criteria::ASC) Order by the unit_id column
 * @method ActionPropertyQuery orderBynorm($order = Criteria::ASC) Order by the norm column
 * @method ActionPropertyQuery orderByisAssigned($order = Criteria::ASC) Order by the isAssigned column
 * @method ActionPropertyQuery orderByevaluation($order = Criteria::ASC) Order by the evaluation column
 * @method ActionPropertyQuery orderByversion($order = Criteria::ASC) Order by the version column
 *
 * @method ActionPropertyQuery groupByid() Group by the id column
 * @method ActionPropertyQuery groupBycreateDatetime() Group by the createDatetime column
 * @method ActionPropertyQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method ActionPropertyQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method ActionPropertyQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method ActionPropertyQuery groupBydeleted() Group by the deleted column
 * @method ActionPropertyQuery groupByactionId() Group by the action_id column
 * @method ActionPropertyQuery groupBytypeId() Group by the type_id column
 * @method ActionPropertyQuery groupByunitId() Group by the unit_id column
 * @method ActionPropertyQuery groupBynorm() Group by the norm column
 * @method ActionPropertyQuery groupByisAssigned() Group by the isAssigned column
 * @method ActionPropertyQuery groupByevaluation() Group by the evaluation column
 * @method ActionPropertyQuery groupByversion() Group by the version column
 *
 * @method ActionPropertyQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyQuery leftJoinAction($relationAlias = null) Adds a LEFT JOIN clause to the query using the Action relation
 * @method ActionPropertyQuery rightJoinAction($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Action relation
 * @method ActionPropertyQuery innerJoinAction($relationAlias = null) Adds a INNER JOIN clause to the query using the Action relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyType($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyType relation
 * @method ActionPropertyQuery rightJoinActionPropertyType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyType relation
 * @method ActionPropertyQuery innerJoinActionPropertyType($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyType relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyString($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyString relation
 * @method ActionPropertyQuery rightJoinActionPropertyString($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyString relation
 * @method ActionPropertyQuery innerJoinActionPropertyString($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyString relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyInteger($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyInteger relation
 * @method ActionPropertyQuery rightJoinActionPropertyInteger($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyInteger relation
 * @method ActionPropertyQuery innerJoinActionPropertyInteger($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyInteger relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyDate($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyDate relation
 * @method ActionPropertyQuery rightJoinActionPropertyDate($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyDate relation
 * @method ActionPropertyQuery innerJoinActionPropertyDate($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyDate relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyDouble($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyDouble relation
 * @method ActionPropertyQuery rightJoinActionPropertyDouble($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyDouble relation
 * @method ActionPropertyQuery innerJoinActionPropertyDouble($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyDouble relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyOrgStructure($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyOrgStructure relation
 * @method ActionPropertyQuery rightJoinActionPropertyOrgStructure($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyOrgStructure relation
 * @method ActionPropertyQuery innerJoinActionPropertyOrgStructure($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyOrgStructure relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyFDRecord($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method ActionPropertyQuery rightJoinActionPropertyFDRecord($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method ActionPropertyQuery innerJoinActionPropertyFDRecord($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyFDRecord relation
 *
 * @method ActionPropertyQuery leftJoinActionPropertyTime($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyTime relation
 * @method ActionPropertyQuery rightJoinActionPropertyTime($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyTime relation
 * @method ActionPropertyQuery innerJoinActionPropertyTime($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyTime relation
 *
 * @method ActionProperty findOne(PropelPDO $con = null) Return the first ActionProperty matching the query
 * @method ActionProperty findOneOrCreate(PropelPDO $con = null) Return the first ActionProperty matching the query, or a new ActionProperty object populated from the query conditions when no match is found
 *
 * @method ActionProperty findOneBycreateDatetime(string $createDatetime) Return the first ActionProperty filtered by the createDatetime column
 * @method ActionProperty findOneBycreatePersonId(int $createPerson_id) Return the first ActionProperty filtered by the createPerson_id column
 * @method ActionProperty findOneBymodifyDatetime(string $modifyDatetime) Return the first ActionProperty filtered by the modifyDatetime column
 * @method ActionProperty findOneBymodifyPersonId(int $modifyPerson_id) Return the first ActionProperty filtered by the modifyPerson_id column
 * @method ActionProperty findOneBydeleted(boolean $deleted) Return the first ActionProperty filtered by the deleted column
 * @method ActionProperty findOneByactionId(int $action_id) Return the first ActionProperty filtered by the action_id column
 * @method ActionProperty findOneBytypeId(int $type_id) Return the first ActionProperty filtered by the type_id column
 * @method ActionProperty findOneByunitId(int $unit_id) Return the first ActionProperty filtered by the unit_id column
 * @method ActionProperty findOneBynorm(string $norm) Return the first ActionProperty filtered by the norm column
 * @method ActionProperty findOneByisAssigned(boolean $isAssigned) Return the first ActionProperty filtered by the isAssigned column
 * @method ActionProperty findOneByevaluation(boolean $evaluation) Return the first ActionProperty filtered by the evaluation column
 * @method ActionProperty findOneByversion(int $version) Return the first ActionProperty filtered by the version column
 *
 * @method array findByid(int $id) Return ActionProperty objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return ActionProperty objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return ActionProperty objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return ActionProperty objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return ActionProperty objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return ActionProperty objects filtered by the deleted column
 * @method array findByactionId(int $action_id) Return ActionProperty objects filtered by the action_id column
 * @method array findBytypeId(int $type_id) Return ActionProperty objects filtered by the type_id column
 * @method array findByunitId(int $unit_id) Return ActionProperty objects filtered by the unit_id column
 * @method array findBynorm(string $norm) Return ActionProperty objects filtered by the norm column
 * @method array findByisAssigned(boolean $isAssigned) Return ActionProperty objects filtered by the isAssigned column
 * @method array findByevaluation(boolean $evaluation) Return ActionProperty objects filtered by the evaluation column
 * @method array findByversion(int $version) Return ActionProperty objects filtered by the version column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionProperty', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyQuery) {
            return $criteria;
        }
        $query = new ActionPropertyQuery();
        if (null !== $modelAlias) {
            $query->setModelAlias($modelAlias);
        }
        if ($criteria instanceof Criteria) {
            $query->mergeWith($criteria);
        }

        return $query;
    }

    /**
     * Find object by primary key.
     * Propel uses the instance pool to skip the database if the object exists.
     * Go fast if the query is untouched.
     *
     * <code>
     * $obj  = $c->findPk(12, $con);
     * </code>
     *
     * @param mixed $key Primary key to use for the query
     * @param     PropelPDO $con an optional connection object
     *
     * @return   ActionProperty|ActionProperty[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }
        $this->basePreSelect($con);
        if ($this->formatter || $this->modelAlias || $this->with || $this->select
         || $this->selectColumns || $this->asColumns || $this->selectModifiers
         || $this->map || $this->having || $this->joins) {
            return $this->findPkComplex($key, $con);
        } else {
            return $this->findPkSimple($key, $con);
        }
    }

    /**
     * Alias of findPk to use instance pooling
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return                 ActionProperty A model object, or null if the key is not found
     * @throws PropelException
     */
     public function findOneByid($key, $con = null)
     {
        return $this->findPk($key, $con);
     }

    /**
     * Find object by primary key using raw SQL to go fast.
     * Bypass doSelect() and the object formatter by using generated code.
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return                 ActionProperty A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version` FROM `ActionProperty` WHERE `id` = :p0';
        try {
            $stmt = $con->prepare($sql);
            $stmt->bindValue(':p0', $key, PDO::PARAM_INT);
            $stmt->execute();
        } catch (Exception $e) {
            Propel::log($e->getMessage(), Propel::LOG_ERR);
            throw new PropelException(sprintf('Unable to execute SELECT statement [%s]', $sql), $e);
        }
        $obj = null;
        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $obj = new ActionProperty();
            $obj->hydrate($row);
            ActionPropertyPeer::addInstanceToPool($obj, (string) $key);
        }
        $stmt->closeCursor();

        return $obj;
    }

    /**
     * Find object by primary key.
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return ActionProperty|ActionProperty[]|mixed the result, formatted by the current formatter
     */
    protected function findPkComplex($key, $con)
    {
        // As the query uses a PK condition, no limit(1) is necessary.
        $criteria = $this->isKeepQuery() ? clone $this : $this;
        $stmt = $criteria
            ->filterByPrimaryKey($key)
            ->doSelect($con);

        return $criteria->getFormatter()->init($criteria)->formatOne($stmt);
    }

    /**
     * Find objects by primary key
     * <code>
     * $objs = $c->findPks(array(12, 56, 832), $con);
     * </code>
     * @param     array $keys Primary keys to use for the query
     * @param     PropelPDO $con an optional connection object
     *
     * @return PropelObjectCollection|ActionProperty[]|mixed the list of results, formatted by the current formatter
     */
    public function findPks($keys, $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection($this->getDbName(), Propel::CONNECTION_READ);
        }
        $this->basePreSelect($con);
        $criteria = $this->isKeepQuery() ? clone $this : $this;
        $stmt = $criteria
            ->filterByPrimaryKeys($keys)
            ->doSelect($con);

        return $criteria->getFormatter()->init($criteria)->format($stmt);
    }

    /**
     * Filter the query by primary key
     *
     * @param     mixed $key Primary key to use for the query
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ActionPropertyPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ActionPropertyPeer::ID, $keys, Criteria::IN);
    }

    /**
     * Filter the query on the id column
     *
     * Example usage:
     * <code>
     * $query->filterByid(1234); // WHERE id = 1234
     * $query->filterByid(array(12, 34)); // WHERE id IN (12, 34)
     * $query->filterByid(array('min' => 12)); // WHERE id >= 12
     * $query->filterByid(array('max' => 12)); // WHERE id <= 12
     * </code>
     *
     * @see       filterByActionPropertyString()
     *
     * @see       filterByActionPropertyInteger()
     *
     * @see       filterByActionPropertyDate()
     *
     * @see       filterByActionPropertyDouble()
     *
     * @see       filterByActionPropertyOrgStructure()
     *
     * @see       filterByActionPropertyFDRecord()
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the createDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBycreateDatetime('2011-03-14'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime('now'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime(array('max' => 'yesterday')); // WHERE createDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $createDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::CREATEDATETIME, $createDatetime, $comparison);
    }

    /**
     * Filter the query on the createPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycreatePersonId(1234); // WHERE createPerson_id = 1234
     * $query->filterBycreatePersonId(array(12, 34)); // WHERE createPerson_id IN (12, 34)
     * $query->filterBycreatePersonId(array('min' => 12)); // WHERE createPerson_id >= 12
     * $query->filterBycreatePersonId(array('max' => 12)); // WHERE createPerson_id <= 12
     * </code>
     *
     * @param     mixed $createPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::CREATEPERSON_ID, $createPersonId, $comparison);
    }

    /**
     * Filter the query on the modifyDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyDatetime('2011-03-14'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime('now'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime(array('max' => 'yesterday')); // WHERE modifyDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $modifyDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
    }

    /**
     * Filter the query on the modifyPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyPersonId(1234); // WHERE modifyPerson_id = 1234
     * $query->filterBymodifyPersonId(array(12, 34)); // WHERE modifyPerson_id IN (12, 34)
     * $query->filterBymodifyPersonId(array('min' => 12)); // WHERE modifyPerson_id >= 12
     * $query->filterBymodifyPersonId(array('max' => 12)); // WHERE modifyPerson_id <= 12
     * </code>
     *
     * @param     mixed $modifyPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
    }

    /**
     * Filter the query on the deleted column
     *
     * Example usage:
     * <code>
     * $query->filterBydeleted(true); // WHERE deleted = true
     * $query->filterBydeleted('yes'); // WHERE deleted = true
     * </code>
     *
     * @param     boolean|string $deleted The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyPeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the action_id column
     *
     * Example usage:
     * <code>
     * $query->filterByactionId(1234); // WHERE action_id = 1234
     * $query->filterByactionId(array(12, 34)); // WHERE action_id IN (12, 34)
     * $query->filterByactionId(array('min' => 12)); // WHERE action_id >= 12
     * $query->filterByactionId(array('max' => 12)); // WHERE action_id <= 12
     * </code>
     *
     * @see       filterByAction()
     *
     * @param     mixed $actionId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByactionId($actionId = null, $comparison = null)
    {
        if (is_array($actionId)) {
            $useMinMax = false;
            if (isset($actionId['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::ACTION_ID, $actionId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actionId['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::ACTION_ID, $actionId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::ACTION_ID, $actionId, $comparison);
    }

    /**
     * Filter the query on the type_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytypeId(1234); // WHERE type_id = 1234
     * $query->filterBytypeId(array(12, 34)); // WHERE type_id IN (12, 34)
     * $query->filterBytypeId(array('min' => 12)); // WHERE type_id >= 12
     * $query->filterBytypeId(array('max' => 12)); // WHERE type_id <= 12
     * </code>
     *
     * @see       filterByActionPropertyType()
     *
     * @param     mixed $typeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBytypeId($typeId = null, $comparison = null)
    {
        if (is_array($typeId)) {
            $useMinMax = false;
            if (isset($typeId['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::TYPE_ID, $typeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($typeId['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::TYPE_ID, $typeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::TYPE_ID, $typeId, $comparison);
    }

    /**
     * Filter the query on the unit_id column
     *
     * Example usage:
     * <code>
     * $query->filterByunitId(1234); // WHERE unit_id = 1234
     * $query->filterByunitId(array(12, 34)); // WHERE unit_id IN (12, 34)
     * $query->filterByunitId(array('min' => 12)); // WHERE unit_id >= 12
     * $query->filterByunitId(array('max' => 12)); // WHERE unit_id <= 12
     * </code>
     *
     * @param     mixed $unitId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByunitId($unitId = null, $comparison = null)
    {
        if (is_array($unitId)) {
            $useMinMax = false;
            if (isset($unitId['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::UNIT_ID, $unitId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($unitId['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::UNIT_ID, $unitId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::UNIT_ID, $unitId, $comparison);
    }

    /**
     * Filter the query on the norm column
     *
     * Example usage:
     * <code>
     * $query->filterBynorm('fooValue');   // WHERE norm = 'fooValue'
     * $query->filterBynorm('%fooValue%'); // WHERE norm LIKE '%fooValue%'
     * </code>
     *
     * @param     string $norm The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterBynorm($norm = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($norm)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $norm)) {
                $norm = str_replace('*', '%', $norm);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::NORM, $norm, $comparison);
    }

    /**
     * Filter the query on the isAssigned column
     *
     * Example usage:
     * <code>
     * $query->filterByisAssigned(true); // WHERE isAssigned = true
     * $query->filterByisAssigned('yes'); // WHERE isAssigned = true
     * </code>
     *
     * @param     boolean|string $isAssigned The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByisAssigned($isAssigned = null, $comparison = null)
    {
        if (is_string($isAssigned)) {
            $isAssigned = in_array(strtolower($isAssigned), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyPeer::ISASSIGNED, $isAssigned, $comparison);
    }

    /**
     * Filter the query on the evaluation column
     *
     * Example usage:
     * <code>
     * $query->filterByevaluation(true); // WHERE evaluation = true
     * $query->filterByevaluation('yes'); // WHERE evaluation = true
     * </code>
     *
     * @param     boolean|string $evaluation The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByevaluation($evaluation = null, $comparison = null)
    {
        if (is_string($evaluation)) {
            $evaluation = in_array(strtolower($evaluation), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyPeer::EVALUATION, $evaluation, $comparison);
    }

    /**
     * Filter the query on the version column
     *
     * Example usage:
     * <code>
     * $query->filterByversion(1234); // WHERE version = 1234
     * $query->filterByversion(array(12, 34)); // WHERE version IN (12, 34)
     * $query->filterByversion(array('min' => 12)); // WHERE version >= 12
     * $query->filterByversion(array('max' => 12)); // WHERE version <= 12
     * </code>
     *
     * @param     mixed $version The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(ActionPropertyPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(ActionPropertyPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByAction($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ACTION_ID, $action->getid(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ACTION_ID, $action->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByAction() only accepts arguments of type Action or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Action relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinAction($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Action');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'Action');
        }

        return $this;
    }

    /**
     * Use the Action relation Action object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionQuery A secondary query class using the current class as primary query
     */
    public function useActionQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinAction($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Action', '\Webmis\Models\ActionQuery');
    }

    /**
     * Filter the query by a related ActionPropertyType object
     *
     * @param   ActionPropertyType|PropelObjectCollection $actionPropertyType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyType($actionPropertyType, $comparison = null)
    {
        if ($actionPropertyType instanceof ActionPropertyType) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::TYPE_ID, $actionPropertyType->getid(), $comparison);
        } elseif ($actionPropertyType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::TYPE_ID, $actionPropertyType->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyType() only accepts arguments of type ActionPropertyType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyType relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyType($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyType');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyType');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyType relation ActionPropertyType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyTypeQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyType', '\Webmis\Models\ActionPropertyTypeQuery');
    }

    /**
     * Filter the query by a related ActionPropertyString object
     *
     * @param   ActionPropertyString|PropelObjectCollection $actionPropertyString The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyString($actionPropertyString, $comparison = null)
    {
        if ($actionPropertyString instanceof ActionPropertyString) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyString->getid(), $comparison);
        } elseif ($actionPropertyString instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyString->toKeyValue('id', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyString() only accepts arguments of type ActionPropertyString or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyString relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyString($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyString');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyString');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyString relation ActionPropertyString object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyStringQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyStringQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyString($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyString', '\Webmis\Models\ActionPropertyStringQuery');
    }

    /**
     * Filter the query by a related ActionPropertyInteger object
     *
     * @param   ActionPropertyInteger|PropelObjectCollection $actionPropertyInteger The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyInteger($actionPropertyInteger, $comparison = null)
    {
        if ($actionPropertyInteger instanceof ActionPropertyInteger) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyInteger->getid(), $comparison);
        } elseif ($actionPropertyInteger instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyInteger->toKeyValue('id', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyInteger() only accepts arguments of type ActionPropertyInteger or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyInteger relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyInteger($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyInteger');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyInteger');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyInteger relation ActionPropertyInteger object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyIntegerQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyIntegerQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyInteger($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyInteger', '\Webmis\Models\ActionPropertyIntegerQuery');
    }

    /**
     * Filter the query by a related ActionPropertyDate object
     *
     * @param   ActionPropertyDate|PropelObjectCollection $actionPropertyDate The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyDate($actionPropertyDate, $comparison = null)
    {
        if ($actionPropertyDate instanceof ActionPropertyDate) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyDate->getid(), $comparison);
        } elseif ($actionPropertyDate instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyDate->toKeyValue('id', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyDate() only accepts arguments of type ActionPropertyDate or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyDate relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyDate($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyDate');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyDate');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyDate relation ActionPropertyDate object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyDateQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyDateQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyDate($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyDate', '\Webmis\Models\ActionPropertyDateQuery');
    }

    /**
     * Filter the query by a related ActionPropertyDouble object
     *
     * @param   ActionPropertyDouble|PropelObjectCollection $actionPropertyDouble The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyDouble($actionPropertyDouble, $comparison = null)
    {
        if ($actionPropertyDouble instanceof ActionPropertyDouble) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyDouble->getid(), $comparison);
        } elseif ($actionPropertyDouble instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyDouble->toKeyValue('id', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyDouble() only accepts arguments of type ActionPropertyDouble or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyDouble relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyDouble($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyDouble');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyDouble');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyDouble relation ActionPropertyDouble object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyDoubleQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyDoubleQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyDouble($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyDouble', '\Webmis\Models\ActionPropertyDoubleQuery');
    }

    /**
     * Filter the query by a related ActionPropertyOrgStructure object
     *
     * @param   ActionPropertyOrgStructure|PropelObjectCollection $actionPropertyOrgStructure The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyOrgStructure($actionPropertyOrgStructure, $comparison = null)
    {
        if ($actionPropertyOrgStructure instanceof ActionPropertyOrgStructure) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyOrgStructure->getid(), $comparison);
        } elseif ($actionPropertyOrgStructure instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyOrgStructure->toKeyValue('id', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyOrgStructure() only accepts arguments of type ActionPropertyOrgStructure or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyOrgStructure relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyOrgStructure($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyOrgStructure');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyOrgStructure');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyOrgStructure relation ActionPropertyOrgStructure object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyOrgStructureQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyOrgStructureQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyOrgStructure($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyOrgStructure', '\Webmis\Models\ActionPropertyOrgStructureQuery');
    }

    /**
     * Filter the query by a related ActionPropertyFDRecord object
     *
     * @param   ActionPropertyFDRecord|PropelObjectCollection $actionPropertyFDRecord The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyFDRecord($actionPropertyFDRecord, $comparison = null)
    {
        if ($actionPropertyFDRecord instanceof ActionPropertyFDRecord) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyFDRecord->getId(), $comparison);
        } elseif ($actionPropertyFDRecord instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyFDRecord->toKeyValue('PrimaryKey', 'Id'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyFDRecord() only accepts arguments of type ActionPropertyFDRecord or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyFDRecord relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyFDRecord($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyFDRecord');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyFDRecord');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyFDRecord relation ActionPropertyFDRecord object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyFDRecordQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyFDRecordQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyFDRecord($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyFDRecord', '\Webmis\Models\ActionPropertyFDRecordQuery');
    }

    /**
     * Filter the query by a related ActionPropertyTime object
     *
     * @param   ActionPropertyTime|PropelObjectCollection $actionPropertyTime  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyTime($actionPropertyTime, $comparison = null)
    {
        if ($actionPropertyTime instanceof ActionPropertyTime) {
            return $this
                ->addUsingAlias(ActionPropertyPeer::ID, $actionPropertyTime->getid(), $comparison);
        } elseif ($actionPropertyTime instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyTimeQuery()
                ->filterByPrimaryKeys($actionPropertyTime->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionPropertyTime() only accepts arguments of type ActionPropertyTime or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyTime relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function joinActionPropertyTime($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyTime');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'ActionPropertyTime');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyTime relation ActionPropertyTime object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyTimeQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyTimeQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyTime($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyTime', '\Webmis\Models\ActionPropertyTimeQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   ActionProperty $actionProperty Object to remove from the list of results
     *
     * @return ActionPropertyQuery The current query, for fluid interface
     */
    public function prune($actionProperty = null)
    {
        if ($actionProperty) {
            $this->addUsingAlias(ActionPropertyPeer::ID, $actionProperty->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionPropertyPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionPropertyPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionPropertyPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionPropertyPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionPropertyPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     ActionPropertyQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionPropertyPeer::CREATEDATETIME);
    }
}
