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
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyType;
use Webmis\Models\ActionPropertyTypePeer;
use Webmis\Models\ActionPropertyTypeQuery;
use Webmis\Models\ActionType;

/**
 * Base class that represents a query for the 'ActionPropertyType' table.
 *
 *
 *
 * @method ActionPropertyTypeQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyTypeQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ActionPropertyTypeQuery orderByactionTypeId($order = Criteria::ASC) Order by the actionType_id column
 * @method ActionPropertyTypeQuery orderByidx($order = Criteria::ASC) Order by the idx column
 * @method ActionPropertyTypeQuery orderBytemplateId($order = Criteria::ASC) Order by the template_id column
 * @method ActionPropertyTypeQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method ActionPropertyTypeQuery orderBydescr($order = Criteria::ASC) Order by the descr column
 * @method ActionPropertyTypeQuery orderByunitId($order = Criteria::ASC) Order by the unit_id column
 * @method ActionPropertyTypeQuery orderBytypeName($order = Criteria::ASC) Order by the typeName column
 * @method ActionPropertyTypeQuery orderByValueDomain($order = Criteria::ASC) Order by the valueDomain column
 * @method ActionPropertyTypeQuery orderBydefaultValue($order = Criteria::ASC) Order by the defaultValue column
 * @method ActionPropertyTypeQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method ActionPropertyTypeQuery orderByisVector($order = Criteria::ASC) Order by the isVector column
 * @method ActionPropertyTypeQuery orderBynorm($order = Criteria::ASC) Order by the norm column
 * @method ActionPropertyTypeQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method ActionPropertyTypeQuery orderByage($order = Criteria::ASC) Order by the age column
 * @method ActionPropertyTypeQuery orderByageBu($order = Criteria::ASC) Order by the age_bu column
 * @method ActionPropertyTypeQuery orderByageBc($order = Criteria::ASC) Order by the age_bc column
 * @method ActionPropertyTypeQuery orderByageEu($order = Criteria::ASC) Order by the age_eu column
 * @method ActionPropertyTypeQuery orderByageEc($order = Criteria::ASC) Order by the age_ec column
 * @method ActionPropertyTypeQuery orderBypenalty($order = Criteria::ASC) Order by the penalty column
 * @method ActionPropertyTypeQuery orderByvisibleInJobTicket($order = Criteria::ASC) Order by the visibleInJobTicket column
 * @method ActionPropertyTypeQuery orderByisAssignable($order = Criteria::ASC) Order by the isAssignable column
 * @method ActionPropertyTypeQuery orderBytestId($order = Criteria::ASC) Order by the test_id column
 * @method ActionPropertyTypeQuery orderBydefaultEvaluation($order = Criteria::ASC) Order by the defaultEvaluation column
 * @method ActionPropertyTypeQuery orderBytoEpicrisis($order = Criteria::ASC) Order by the toEpicrisis column
 * @method ActionPropertyTypeQuery orderBymandatory($order = Criteria::ASC) Order by the mandatory column
 * @method ActionPropertyTypeQuery orderByreadonly($order = Criteria::ASC) Order by the readOnly column
 *
 * @method ActionPropertyTypeQuery groupByid() Group by the id column
 * @method ActionPropertyTypeQuery groupBydeleted() Group by the deleted column
 * @method ActionPropertyTypeQuery groupByactionTypeId() Group by the actionType_id column
 * @method ActionPropertyTypeQuery groupByidx() Group by the idx column
 * @method ActionPropertyTypeQuery groupBytemplateId() Group by the template_id column
 * @method ActionPropertyTypeQuery groupByname() Group by the name column
 * @method ActionPropertyTypeQuery groupBydescr() Group by the descr column
 * @method ActionPropertyTypeQuery groupByunitId() Group by the unit_id column
 * @method ActionPropertyTypeQuery groupBytypeName() Group by the typeName column
 * @method ActionPropertyTypeQuery groupByValueDomain() Group by the valueDomain column
 * @method ActionPropertyTypeQuery groupBydefaultValue() Group by the defaultValue column
 * @method ActionPropertyTypeQuery groupBycode() Group by the code column
 * @method ActionPropertyTypeQuery groupByisVector() Group by the isVector column
 * @method ActionPropertyTypeQuery groupBynorm() Group by the norm column
 * @method ActionPropertyTypeQuery groupBysex() Group by the sex column
 * @method ActionPropertyTypeQuery groupByage() Group by the age column
 * @method ActionPropertyTypeQuery groupByageBu() Group by the age_bu column
 * @method ActionPropertyTypeQuery groupByageBc() Group by the age_bc column
 * @method ActionPropertyTypeQuery groupByageEu() Group by the age_eu column
 * @method ActionPropertyTypeQuery groupByageEc() Group by the age_ec column
 * @method ActionPropertyTypeQuery groupBypenalty() Group by the penalty column
 * @method ActionPropertyTypeQuery groupByvisibleInJobTicket() Group by the visibleInJobTicket column
 * @method ActionPropertyTypeQuery groupByisAssignable() Group by the isAssignable column
 * @method ActionPropertyTypeQuery groupBytestId() Group by the test_id column
 * @method ActionPropertyTypeQuery groupBydefaultEvaluation() Group by the defaultEvaluation column
 * @method ActionPropertyTypeQuery groupBytoEpicrisis() Group by the toEpicrisis column
 * @method ActionPropertyTypeQuery groupBymandatory() Group by the mandatory column
 * @method ActionPropertyTypeQuery groupByreadonly() Group by the readOnly column
 *
 * @method ActionPropertyTypeQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyTypeQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyTypeQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyTypeQuery leftJoinActionTypeRelatedByactionTypeId($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionTypeRelatedByactionTypeId relation
 * @method ActionPropertyTypeQuery rightJoinActionTypeRelatedByactionTypeId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionTypeRelatedByactionTypeId relation
 * @method ActionPropertyTypeQuery innerJoinActionTypeRelatedByactionTypeId($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionTypeRelatedByactionTypeId relation
 *
 * @method ActionPropertyTypeQuery leftJoinActionProperty($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyTypeQuery rightJoinActionProperty($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyTypeQuery innerJoinActionProperty($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionProperty relation
 *
 * @method ActionPropertyTypeQuery leftJoinActionTypeRelatedByid($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionTypeRelatedByid relation
 * @method ActionPropertyTypeQuery rightJoinActionTypeRelatedByid($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionTypeRelatedByid relation
 * @method ActionPropertyTypeQuery innerJoinActionTypeRelatedByid($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionTypeRelatedByid relation
 *
 * @method ActionPropertyType findOne(PropelPDO $con = null) Return the first ActionPropertyType matching the query
 * @method ActionPropertyType findOneOrCreate(PropelPDO $con = null) Return the first ActionPropertyType matching the query, or a new ActionPropertyType object populated from the query conditions when no match is found
 *
 * @method ActionPropertyType findOneBydeleted(boolean $deleted) Return the first ActionPropertyType filtered by the deleted column
 * @method ActionPropertyType findOneByactionTypeId(int $actionType_id) Return the first ActionPropertyType filtered by the actionType_id column
 * @method ActionPropertyType findOneByidx(int $idx) Return the first ActionPropertyType filtered by the idx column
 * @method ActionPropertyType findOneBytemplateId(int $template_id) Return the first ActionPropertyType filtered by the template_id column
 * @method ActionPropertyType findOneByname(string $name) Return the first ActionPropertyType filtered by the name column
 * @method ActionPropertyType findOneBydescr(string $descr) Return the first ActionPropertyType filtered by the descr column
 * @method ActionPropertyType findOneByunitId(int $unit_id) Return the first ActionPropertyType filtered by the unit_id column
 * @method ActionPropertyType findOneBytypeName(string $typeName) Return the first ActionPropertyType filtered by the typeName column
 * @method ActionPropertyType findOneByValueDomain(string $valueDomain) Return the first ActionPropertyType filtered by the valueDomain column
 * @method ActionPropertyType findOneBydefaultValue(string $defaultValue) Return the first ActionPropertyType filtered by the defaultValue column
 * @method ActionPropertyType findOneBycode(string $code) Return the first ActionPropertyType filtered by the code column
 * @method ActionPropertyType findOneByisVector(boolean $isVector) Return the first ActionPropertyType filtered by the isVector column
 * @method ActionPropertyType findOneBynorm(string $norm) Return the first ActionPropertyType filtered by the norm column
 * @method ActionPropertyType findOneBysex(int $sex) Return the first ActionPropertyType filtered by the sex column
 * @method ActionPropertyType findOneByage(string $age) Return the first ActionPropertyType filtered by the age column
 * @method ActionPropertyType findOneByageBu(int $age_bu) Return the first ActionPropertyType filtered by the age_bu column
 * @method ActionPropertyType findOneByageBc(int $age_bc) Return the first ActionPropertyType filtered by the age_bc column
 * @method ActionPropertyType findOneByageEu(int $age_eu) Return the first ActionPropertyType filtered by the age_eu column
 * @method ActionPropertyType findOneByageEc(int $age_ec) Return the first ActionPropertyType filtered by the age_ec column
 * @method ActionPropertyType findOneBypenalty(int $penalty) Return the first ActionPropertyType filtered by the penalty column
 * @method ActionPropertyType findOneByvisibleInJobTicket(boolean $visibleInJobTicket) Return the first ActionPropertyType filtered by the visibleInJobTicket column
 * @method ActionPropertyType findOneByisAssignable(boolean $isAssignable) Return the first ActionPropertyType filtered by the isAssignable column
 * @method ActionPropertyType findOneBytestId(int $test_id) Return the first ActionPropertyType filtered by the test_id column
 * @method ActionPropertyType findOneBydefaultEvaluation(boolean $defaultEvaluation) Return the first ActionPropertyType filtered by the defaultEvaluation column
 * @method ActionPropertyType findOneBytoEpicrisis(boolean $toEpicrisis) Return the first ActionPropertyType filtered by the toEpicrisis column
 * @method ActionPropertyType findOneBymandatory(boolean $mandatory) Return the first ActionPropertyType filtered by the mandatory column
 * @method ActionPropertyType findOneByreadonly(boolean $readOnly) Return the first ActionPropertyType filtered by the readOnly column
 *
 * @method array findByid(int $id) Return ActionPropertyType objects filtered by the id column
 * @method array findBydeleted(boolean $deleted) Return ActionPropertyType objects filtered by the deleted column
 * @method array findByactionTypeId(int $actionType_id) Return ActionPropertyType objects filtered by the actionType_id column
 * @method array findByidx(int $idx) Return ActionPropertyType objects filtered by the idx column
 * @method array findBytemplateId(int $template_id) Return ActionPropertyType objects filtered by the template_id column
 * @method array findByname(string $name) Return ActionPropertyType objects filtered by the name column
 * @method array findBydescr(string $descr) Return ActionPropertyType objects filtered by the descr column
 * @method array findByunitId(int $unit_id) Return ActionPropertyType objects filtered by the unit_id column
 * @method array findBytypeName(string $typeName) Return ActionPropertyType objects filtered by the typeName column
 * @method array findByValueDomain(string $valueDomain) Return ActionPropertyType objects filtered by the valueDomain column
 * @method array findBydefaultValue(string $defaultValue) Return ActionPropertyType objects filtered by the defaultValue column
 * @method array findBycode(string $code) Return ActionPropertyType objects filtered by the code column
 * @method array findByisVector(boolean $isVector) Return ActionPropertyType objects filtered by the isVector column
 * @method array findBynorm(string $norm) Return ActionPropertyType objects filtered by the norm column
 * @method array findBysex(int $sex) Return ActionPropertyType objects filtered by the sex column
 * @method array findByage(string $age) Return ActionPropertyType objects filtered by the age column
 * @method array findByageBu(int $age_bu) Return ActionPropertyType objects filtered by the age_bu column
 * @method array findByageBc(int $age_bc) Return ActionPropertyType objects filtered by the age_bc column
 * @method array findByageEu(int $age_eu) Return ActionPropertyType objects filtered by the age_eu column
 * @method array findByageEc(int $age_ec) Return ActionPropertyType objects filtered by the age_ec column
 * @method array findBypenalty(int $penalty) Return ActionPropertyType objects filtered by the penalty column
 * @method array findByvisibleInJobTicket(boolean $visibleInJobTicket) Return ActionPropertyType objects filtered by the visibleInJobTicket column
 * @method array findByisAssignable(boolean $isAssignable) Return ActionPropertyType objects filtered by the isAssignable column
 * @method array findBytestId(int $test_id) Return ActionPropertyType objects filtered by the test_id column
 * @method array findBydefaultEvaluation(boolean $defaultEvaluation) Return ActionPropertyType objects filtered by the defaultEvaluation column
 * @method array findBytoEpicrisis(boolean $toEpicrisis) Return ActionPropertyType objects filtered by the toEpicrisis column
 * @method array findBymandatory(boolean $mandatory) Return ActionPropertyType objects filtered by the mandatory column
 * @method array findByreadonly(boolean $readOnly) Return ActionPropertyType objects filtered by the readOnly column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyTypeQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyTypeQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionPropertyType', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyTypeQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyTypeQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyTypeQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyTypeQuery) {
            return $criteria;
        }
        $query = new ActionPropertyTypeQuery();
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
     * @return   ActionPropertyType|ActionPropertyType[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyTypePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionPropertyType A model object, or null if the key is not found
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
     * @return                 ActionPropertyType A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `deleted`, `actionType_id`, `idx`, `template_id`, `name`, `descr`, `unit_id`, `typeName`, `valueDomain`, `defaultValue`, `code`, `isVector`, `norm`, `sex`, `age`, `age_bu`, `age_bc`, `age_eu`, `age_ec`, `penalty`, `visibleInJobTicket`, `isAssignable`, `test_id`, `defaultEvaluation`, `toEpicrisis`, `mandatory`, `readOnly` FROM `ActionPropertyType` WHERE `id` = :p0';
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
            $obj = new ActionPropertyType();
            $obj->hydrate($row);
            ActionPropertyTypePeer::addInstanceToPool($obj, (string) $key);
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
     * @return ActionPropertyType|ActionPropertyType[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ActionPropertyType[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ActionPropertyTypePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ActionPropertyTypePeer::ID, $keys, Criteria::IN);
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
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::ID, $id, $comparison);
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
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the actionType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByactionTypeId(1234); // WHERE actionType_id = 1234
     * $query->filterByactionTypeId(array(12, 34)); // WHERE actionType_id IN (12, 34)
     * $query->filterByactionTypeId(array('min' => 12)); // WHERE actionType_id >= 12
     * $query->filterByactionTypeId(array('max' => 12)); // WHERE actionType_id <= 12
     * </code>
     *
     * @see       filterByActionTypeRelatedByactionTypeId()
     *
     * @param     mixed $actionTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByactionTypeId($actionTypeId = null, $comparison = null)
    {
        if (is_array($actionTypeId)) {
            $useMinMax = false;
            if (isset($actionTypeId['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actionTypeId['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionTypeId, $comparison);
    }

    /**
     * Filter the query on the idx column
     *
     * Example usage:
     * <code>
     * $query->filterByidx(1234); // WHERE idx = 1234
     * $query->filterByidx(array(12, 34)); // WHERE idx IN (12, 34)
     * $query->filterByidx(array('min' => 12)); // WHERE idx >= 12
     * $query->filterByidx(array('max' => 12)); // WHERE idx <= 12
     * </code>
     *
     * @param     mixed $idx The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByidx($idx = null, $comparison = null)
    {
        if (is_array($idx)) {
            $useMinMax = false;
            if (isset($idx['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::IDX, $idx['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($idx['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::IDX, $idx['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::IDX, $idx, $comparison);
    }

    /**
     * Filter the query on the template_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytemplateId(1234); // WHERE template_id = 1234
     * $query->filterBytemplateId(array(12, 34)); // WHERE template_id IN (12, 34)
     * $query->filterBytemplateId(array('min' => 12)); // WHERE template_id >= 12
     * $query->filterBytemplateId(array('max' => 12)); // WHERE template_id <= 12
     * </code>
     *
     * @param     mixed $templateId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBytemplateId($templateId = null, $comparison = null)
    {
        if (is_array($templateId)) {
            $useMinMax = false;
            if (isset($templateId['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::TEMPLATE_ID, $templateId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($templateId['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::TEMPLATE_ID, $templateId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::TEMPLATE_ID, $templateId, $comparison);
    }

    /**
     * Filter the query on the name column
     *
     * Example usage:
     * <code>
     * $query->filterByname('fooValue');   // WHERE name = 'fooValue'
     * $query->filterByname('%fooValue%'); // WHERE name LIKE '%fooValue%'
     * </code>
     *
     * @param     string $name The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByname($name = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($name)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $name)) {
                $name = str_replace('*', '%', $name);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the descr column
     *
     * Example usage:
     * <code>
     * $query->filterBydescr('fooValue');   // WHERE descr = 'fooValue'
     * $query->filterBydescr('%fooValue%'); // WHERE descr LIKE '%fooValue%'
     * </code>
     *
     * @param     string $descr The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBydescr($descr = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($descr)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $descr)) {
                $descr = str_replace('*', '%', $descr);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::DESCR, $descr, $comparison);
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
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByunitId($unitId = null, $comparison = null)
    {
        if (is_array($unitId)) {
            $useMinMax = false;
            if (isset($unitId['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::UNIT_ID, $unitId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($unitId['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::UNIT_ID, $unitId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::UNIT_ID, $unitId, $comparison);
    }

    /**
     * Filter the query on the typeName column
     *
     * Example usage:
     * <code>
     * $query->filterBytypeName('fooValue');   // WHERE typeName = 'fooValue'
     * $query->filterBytypeName('%fooValue%'); // WHERE typeName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $typeName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBytypeName($typeName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($typeName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $typeName)) {
                $typeName = str_replace('*', '%', $typeName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::TYPENAME, $typeName, $comparison);
    }

    /**
     * Filter the query on the valueDomain column
     *
     * Example usage:
     * <code>
     * $query->filterByValueDomain('fooValue');   // WHERE valueDomain = 'fooValue'
     * $query->filterByValueDomain('%fooValue%'); // WHERE valueDomain LIKE '%fooValue%'
     * </code>
     *
     * @param     string $valueDomain The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByValueDomain($valueDomain = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($valueDomain)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $valueDomain)) {
                $valueDomain = str_replace('*', '%', $valueDomain);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::VALUEDOMAIN, $valueDomain, $comparison);
    }

    /**
     * Filter the query on the defaultValue column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultValue('fooValue');   // WHERE defaultValue = 'fooValue'
     * $query->filterBydefaultValue('%fooValue%'); // WHERE defaultValue LIKE '%fooValue%'
     * </code>
     *
     * @param     string $defaultValue The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultValue($defaultValue = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($defaultValue)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $defaultValue)) {
                $defaultValue = str_replace('*', '%', $defaultValue);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::DEFAULTVALUE, $defaultValue, $comparison);
    }

    /**
     * Filter the query on the code column
     *
     * Example usage:
     * <code>
     * $query->filterBycode('fooValue');   // WHERE code = 'fooValue'
     * $query->filterBycode('%fooValue%'); // WHERE code LIKE '%fooValue%'
     * </code>
     *
     * @param     string $code The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBycode($code = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($code)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $code)) {
                $code = str_replace('*', '%', $code);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::CODE, $code, $comparison);
    }

    /**
     * Filter the query on the isVector column
     *
     * Example usage:
     * <code>
     * $query->filterByisVector(true); // WHERE isVector = true
     * $query->filterByisVector('yes'); // WHERE isVector = true
     * </code>
     *
     * @param     boolean|string $isVector The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByisVector($isVector = null, $comparison = null)
    {
        if (is_string($isVector)) {
            $isVector = in_array(strtolower($isVector), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::ISVECTOR, $isVector, $comparison);
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
     * @return ActionPropertyTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionPropertyTypePeer::NORM, $norm, $comparison);
    }

    /**
     * Filter the query on the sex column
     *
     * Example usage:
     * <code>
     * $query->filterBysex(1234); // WHERE sex = 1234
     * $query->filterBysex(array(12, 34)); // WHERE sex IN (12, 34)
     * $query->filterBysex(array('min' => 12)); // WHERE sex >= 12
     * $query->filterBysex(array('max' => 12)); // WHERE sex <= 12
     * </code>
     *
     * @param     mixed $sex The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_array($sex)) {
            $useMinMax = false;
            if (isset($sex['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::SEX, $sex['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sex['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::SEX, $sex['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::SEX, $sex, $comparison);
    }

    /**
     * Filter the query on the age column
     *
     * Example usage:
     * <code>
     * $query->filterByage('fooValue');   // WHERE age = 'fooValue'
     * $query->filterByage('%fooValue%'); // WHERE age LIKE '%fooValue%'
     * </code>
     *
     * @param     string $age The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByage($age = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($age)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $age)) {
                $age = str_replace('*', '%', $age);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::AGE, $age, $comparison);
    }

    /**
     * Filter the query on the age_bu column
     *
     * Example usage:
     * <code>
     * $query->filterByageBu(1234); // WHERE age_bu = 1234
     * $query->filterByageBu(array(12, 34)); // WHERE age_bu IN (12, 34)
     * $query->filterByageBu(array('min' => 12)); // WHERE age_bu >= 12
     * $query->filterByageBu(array('max' => 12)); // WHERE age_bu <= 12
     * </code>
     *
     * @param     mixed $ageBu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByageBu($ageBu = null, $comparison = null)
    {
        if (is_array($ageBu)) {
            $useMinMax = false;
            if (isset($ageBu['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_BU, $ageBu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBu['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_BU, $ageBu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::AGE_BU, $ageBu, $comparison);
    }

    /**
     * Filter the query on the age_bc column
     *
     * Example usage:
     * <code>
     * $query->filterByageBc(1234); // WHERE age_bc = 1234
     * $query->filterByageBc(array(12, 34)); // WHERE age_bc IN (12, 34)
     * $query->filterByageBc(array('min' => 12)); // WHERE age_bc >= 12
     * $query->filterByageBc(array('max' => 12)); // WHERE age_bc <= 12
     * </code>
     *
     * @param     mixed $ageBc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByageBc($ageBc = null, $comparison = null)
    {
        if (is_array($ageBc)) {
            $useMinMax = false;
            if (isset($ageBc['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_BC, $ageBc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBc['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_BC, $ageBc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::AGE_BC, $ageBc, $comparison);
    }

    /**
     * Filter the query on the age_eu column
     *
     * Example usage:
     * <code>
     * $query->filterByageEu(1234); // WHERE age_eu = 1234
     * $query->filterByageEu(array(12, 34)); // WHERE age_eu IN (12, 34)
     * $query->filterByageEu(array('min' => 12)); // WHERE age_eu >= 12
     * $query->filterByageEu(array('max' => 12)); // WHERE age_eu <= 12
     * </code>
     *
     * @param     mixed $ageEu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByageEu($ageEu = null, $comparison = null)
    {
        if (is_array($ageEu)) {
            $useMinMax = false;
            if (isset($ageEu['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_EU, $ageEu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEu['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_EU, $ageEu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::AGE_EU, $ageEu, $comparison);
    }

    /**
     * Filter the query on the age_ec column
     *
     * Example usage:
     * <code>
     * $query->filterByageEc(1234); // WHERE age_ec = 1234
     * $query->filterByageEc(array(12, 34)); // WHERE age_ec IN (12, 34)
     * $query->filterByageEc(array('min' => 12)); // WHERE age_ec >= 12
     * $query->filterByageEc(array('max' => 12)); // WHERE age_ec <= 12
     * </code>
     *
     * @param     mixed $ageEc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByageEc($ageEc = null, $comparison = null)
    {
        if (is_array($ageEc)) {
            $useMinMax = false;
            if (isset($ageEc['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_EC, $ageEc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEc['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::AGE_EC, $ageEc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::AGE_EC, $ageEc, $comparison);
    }

    /**
     * Filter the query on the penalty column
     *
     * Example usage:
     * <code>
     * $query->filterBypenalty(1234); // WHERE penalty = 1234
     * $query->filterBypenalty(array(12, 34)); // WHERE penalty IN (12, 34)
     * $query->filterBypenalty(array('min' => 12)); // WHERE penalty >= 12
     * $query->filterBypenalty(array('max' => 12)); // WHERE penalty <= 12
     * </code>
     *
     * @param     mixed $penalty The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBypenalty($penalty = null, $comparison = null)
    {
        if (is_array($penalty)) {
            $useMinMax = false;
            if (isset($penalty['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::PENALTY, $penalty['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($penalty['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::PENALTY, $penalty['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::PENALTY, $penalty, $comparison);
    }

    /**
     * Filter the query on the visibleInJobTicket column
     *
     * Example usage:
     * <code>
     * $query->filterByvisibleInJobTicket(true); // WHERE visibleInJobTicket = true
     * $query->filterByvisibleInJobTicket('yes'); // WHERE visibleInJobTicket = true
     * </code>
     *
     * @param     boolean|string $visibleInJobTicket The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByvisibleInJobTicket($visibleInJobTicket = null, $comparison = null)
    {
        if (is_string($visibleInJobTicket)) {
            $visibleInJobTicket = in_array(strtolower($visibleInJobTicket), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::VISIBLEINJOBTICKET, $visibleInJobTicket, $comparison);
    }

    /**
     * Filter the query on the isAssignable column
     *
     * Example usage:
     * <code>
     * $query->filterByisAssignable(true); // WHERE isAssignable = true
     * $query->filterByisAssignable('yes'); // WHERE isAssignable = true
     * </code>
     *
     * @param     boolean|string $isAssignable The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByisAssignable($isAssignable = null, $comparison = null)
    {
        if (is_string($isAssignable)) {
            $isAssignable = in_array(strtolower($isAssignable), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::ISASSIGNABLE, $isAssignable, $comparison);
    }

    /**
     * Filter the query on the test_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytestId(1234); // WHERE test_id = 1234
     * $query->filterBytestId(array(12, 34)); // WHERE test_id IN (12, 34)
     * $query->filterBytestId(array('min' => 12)); // WHERE test_id >= 12
     * $query->filterBytestId(array('max' => 12)); // WHERE test_id <= 12
     * </code>
     *
     * @param     mixed $testId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBytestId($testId = null, $comparison = null)
    {
        if (is_array($testId)) {
            $useMinMax = false;
            if (isset($testId['min'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::TEST_ID, $testId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($testId['max'])) {
                $this->addUsingAlias(ActionPropertyTypePeer::TEST_ID, $testId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::TEST_ID, $testId, $comparison);
    }

    /**
     * Filter the query on the defaultEvaluation column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultEvaluation(true); // WHERE defaultEvaluation = true
     * $query->filterBydefaultEvaluation('yes'); // WHERE defaultEvaluation = true
     * </code>
     *
     * @param     boolean|string $defaultEvaluation The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultEvaluation($defaultEvaluation = null, $comparison = null)
    {
        if (is_string($defaultEvaluation)) {
            $defaultEvaluation = in_array(strtolower($defaultEvaluation), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::DEFAULTEVALUATION, $defaultEvaluation, $comparison);
    }

    /**
     * Filter the query on the toEpicrisis column
     *
     * Example usage:
     * <code>
     * $query->filterBytoEpicrisis(true); // WHERE toEpicrisis = true
     * $query->filterBytoEpicrisis('yes'); // WHERE toEpicrisis = true
     * </code>
     *
     * @param     boolean|string $toEpicrisis The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBytoEpicrisis($toEpicrisis = null, $comparison = null)
    {
        if (is_string($toEpicrisis)) {
            $toEpicrisis = in_array(strtolower($toEpicrisis), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::TOEPICRISIS, $toEpicrisis, $comparison);
    }

    /**
     * Filter the query on the mandatory column
     *
     * Example usage:
     * <code>
     * $query->filterBymandatory(true); // WHERE mandatory = true
     * $query->filterBymandatory('yes'); // WHERE mandatory = true
     * </code>
     *
     * @param     boolean|string $mandatory The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterBymandatory($mandatory = null, $comparison = null)
    {
        if (is_string($mandatory)) {
            $mandatory = in_array(strtolower($mandatory), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::MANDATORY, $mandatory, $comparison);
    }

    /**
     * Filter the query on the readOnly column
     *
     * Example usage:
     * <code>
     * $query->filterByreadonly(true); // WHERE readOnly = true
     * $query->filterByreadonly('yes'); // WHERE readOnly = true
     * </code>
     *
     * @param     boolean|string $readonly The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function filterByreadonly($readonly = null, $comparison = null)
    {
        if (is_string($readonly)) {
            $readonly = in_array(strtolower($readonly), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPropertyTypePeer::READONLY, $readonly, $comparison);
    }

    /**
     * Filter the query by a related ActionType object
     *
     * @param   ActionType|PropelObjectCollection $actionType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionTypeRelatedByactionTypeId($actionType, $comparison = null)
    {
        if ($actionType instanceof ActionType) {
            return $this
                ->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionType->getid(), $comparison);
        } elseif ($actionType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionType->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionTypeRelatedByactionTypeId() only accepts arguments of type ActionType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionTypeRelatedByactionTypeId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function joinActionTypeRelatedByactionTypeId($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionTypeRelatedByactionTypeId');

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
            $this->addJoinObject($join, 'ActionTypeRelatedByactionTypeId');
        }

        return $this;
    }

    /**
     * Use the ActionTypeRelatedByactionTypeId relation ActionType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionTypeRelatedByactionTypeIdQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionTypeRelatedByactionTypeId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionTypeRelatedByactionTypeId', '\Webmis\Models\ActionTypeQuery');
    }

    /**
     * Filter the query by a related ActionProperty object
     *
     * @param   ActionProperty|PropelObjectCollection $actionProperty  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionProperty($actionProperty, $comparison = null)
    {
        if ($actionProperty instanceof ActionProperty) {
            return $this
                ->addUsingAlias(ActionPropertyTypePeer::ID, $actionProperty->gettypeId(), $comparison);
        } elseif ($actionProperty instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyQuery()
                ->filterByPrimaryKeys($actionProperty->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionProperty() only accepts arguments of type ActionProperty or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionProperty relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function joinActionProperty($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionProperty');

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
            $this->addJoinObject($join, 'ActionProperty');
        }

        return $this;
    }

    /**
     * Use the ActionProperty relation ActionProperty object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionProperty($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionProperty', '\Webmis\Models\ActionPropertyQuery');
    }

    /**
     * Filter the query by a related ActionType object
     *
     * @param   ActionType|PropelObjectCollection $actionType  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionTypeRelatedByid($actionType, $comparison = null)
    {
        if ($actionType instanceof ActionType) {
            return $this
                ->addUsingAlias(ActionPropertyTypePeer::ACTIONTYPE_ID, $actionType->getid(), $comparison);
        } elseif ($actionType instanceof PropelObjectCollection) {
            return $this
                ->useActionTypeRelatedByidQuery()
                ->filterByPrimaryKeys($actionType->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionTypeRelatedByid() only accepts arguments of type ActionType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionTypeRelatedByid relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function joinActionTypeRelatedByid($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionTypeRelatedByid');

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
            $this->addJoinObject($join, 'ActionTypeRelatedByid');
        }

        return $this;
    }

    /**
     * Use the ActionTypeRelatedByid relation ActionType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionTypeRelatedByidQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionTypeRelatedByid($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionTypeRelatedByid', '\Webmis\Models\ActionTypeQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   ActionPropertyType $actionPropertyType Object to remove from the list of results
     *
     * @return ActionPropertyTypeQuery The current query, for fluid interface
     */
    public function prune($actionPropertyType = null)
    {
        if ($actionPropertyType) {
            $this->addUsingAlias(ActionPropertyTypePeer::ID, $actionPropertyType->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
