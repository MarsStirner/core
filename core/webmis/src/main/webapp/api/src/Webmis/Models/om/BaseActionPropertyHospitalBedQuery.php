<?php

namespace Webmis\Models\om;

use \Criteria;
use \Exception;
use \ModelCriteria;
use \PDO;
use \Propel;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\ActionPropertyHospitalBed;
use Webmis\Models\ActionPropertyHospitalBedPeer;
use Webmis\Models\ActionPropertyHospitalBedQuery;

/**
 * Base class that represents a query for the 'ActionProperty_HospitalBed' table.
 *
 *
 *
 * @method ActionPropertyHospitalBedQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyHospitalBedQuery orderByindex($order = Criteria::ASC) Order by the index column
 * @method ActionPropertyHospitalBedQuery orderByvalue($order = Criteria::ASC) Order by the value column
 *
 * @method ActionPropertyHospitalBedQuery groupByid() Group by the id column
 * @method ActionPropertyHospitalBedQuery groupByindex() Group by the index column
 * @method ActionPropertyHospitalBedQuery groupByvalue() Group by the value column
 *
 * @method ActionPropertyHospitalBedQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyHospitalBedQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyHospitalBedQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyHospitalBed findOne(PropelPDO $con = null) Return the first ActionPropertyHospitalBed matching the query
 * @method ActionPropertyHospitalBed findOneOrCreate(PropelPDO $con = null) Return the first ActionPropertyHospitalBed matching the query, or a new ActionPropertyHospitalBed object populated from the query conditions when no match is found
 *
 * @method ActionPropertyHospitalBed findOneByid(int $id) Return the first ActionPropertyHospitalBed filtered by the id column
 * @method ActionPropertyHospitalBed findOneByindex(int $index) Return the first ActionPropertyHospitalBed filtered by the index column
 * @method ActionPropertyHospitalBed findOneByvalue(int $value) Return the first ActionPropertyHospitalBed filtered by the value column
 *
 * @method array findByid(int $id) Return ActionPropertyHospitalBed objects filtered by the id column
 * @method array findByindex(int $index) Return ActionPropertyHospitalBed objects filtered by the index column
 * @method array findByvalue(int $value) Return ActionPropertyHospitalBed objects filtered by the value column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyHospitalBedQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyHospitalBedQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionPropertyHospitalBed', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyHospitalBedQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyHospitalBedQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyHospitalBedQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyHospitalBedQuery) {
            return $criteria;
        }
        $query = new ActionPropertyHospitalBedQuery();
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
     * $obj = $c->findPk(array(12, 34), $con);
     * </code>
     *
     * @param array $key Primary key to use for the query
                         A Primary key composition: [$id, $index]
     * @param     PropelPDO $con an optional connection object
     *
     * @return   ActionPropertyHospitalBed|ActionPropertyHospitalBed[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyHospitalBedPeer::getInstanceFromPool(serialize(array((string) $key[0], (string) $key[1]))))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyHospitalBedPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * Find object by primary key using raw SQL to go fast.
     * Bypass doSelect() and the object formatter by using generated code.
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return                 ActionPropertyHospitalBed A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `index`, `value` FROM `ActionProperty_HospitalBed` WHERE `id` = :p0 AND `index` = :p1';
        try {
            $stmt = $con->prepare($sql);
            $stmt->bindValue(':p0', $key[0], PDO::PARAM_INT);
            $stmt->bindValue(':p1', $key[1], PDO::PARAM_INT);
            $stmt->execute();
        } catch (Exception $e) {
            Propel::log($e->getMessage(), Propel::LOG_ERR);
            throw new PropelException(sprintf('Unable to execute SELECT statement [%s]', $sql), $e);
        }
        $obj = null;
        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $obj = new ActionPropertyHospitalBed();
            $obj->hydrate($row);
            ActionPropertyHospitalBedPeer::addInstanceToPool($obj, serialize(array((string) $key[0], (string) $key[1])));
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
     * @return ActionPropertyHospitalBed|ActionPropertyHospitalBed[]|mixed the result, formatted by the current formatter
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
     * $objs = $c->findPks(array(array(12, 56), array(832, 123), array(123, 456)), $con);
     * </code>
     * @param     array $keys Primary keys to use for the query
     * @param     PropelPDO $con an optional connection object
     *
     * @return PropelObjectCollection|ActionPropertyHospitalBed[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {
        $this->addUsingAlias(ActionPropertyHospitalBedPeer::ID, $key[0], Criteria::EQUAL);
        $this->addUsingAlias(ActionPropertyHospitalBedPeer::INDEX, $key[1], Criteria::EQUAL);

        return $this;
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {
        if (empty($keys)) {
            return $this->add(null, '1<>1', Criteria::CUSTOM);
        }
        foreach ($keys as $key) {
            $cton0 = $this->getNewCriterion(ActionPropertyHospitalBedPeer::ID, $key[0], Criteria::EQUAL);
            $cton1 = $this->getNewCriterion(ActionPropertyHospitalBedPeer::INDEX, $key[1], Criteria::EQUAL);
            $cton0->addAnd($cton1);
            $this->addOr($cton0);
        }

        return $this;
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
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyHospitalBedPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the index column
     *
     * Example usage:
     * <code>
     * $query->filterByindex(1234); // WHERE index = 1234
     * $query->filterByindex(array(12, 34)); // WHERE index IN (12, 34)
     * $query->filterByindex(array('min' => 12)); // WHERE index >= 12
     * $query->filterByindex(array('max' => 12)); // WHERE index <= 12
     * </code>
     *
     * @param     mixed $index The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function filterByindex($index = null, $comparison = null)
    {
        if (is_array($index)) {
            $useMinMax = false;
            if (isset($index['min'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::INDEX, $index['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($index['max'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::INDEX, $index['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyHospitalBedPeer::INDEX, $index, $comparison);
    }

    /**
     * Filter the query on the value column
     *
     * Example usage:
     * <code>
     * $query->filterByvalue(1234); // WHERE value = 1234
     * $query->filterByvalue(array(12, 34)); // WHERE value IN (12, 34)
     * $query->filterByvalue(array('min' => 12)); // WHERE value >= 12
     * $query->filterByvalue(array('max' => 12)); // WHERE value <= 12
     * </code>
     *
     * @param     mixed $value The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function filterByvalue($value = null, $comparison = null)
    {
        if (is_array($value)) {
            $useMinMax = false;
            if (isset($value['min'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::VALUE, $value['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($value['max'])) {
                $this->addUsingAlias(ActionPropertyHospitalBedPeer::VALUE, $value['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyHospitalBedPeer::VALUE, $value, $comparison);
    }

    /**
     * Exclude object from result
     *
     * @param   ActionPropertyHospitalBed $actionPropertyHospitalBed Object to remove from the list of results
     *
     * @return ActionPropertyHospitalBedQuery The current query, for fluid interface
     */
    public function prune($actionPropertyHospitalBed = null)
    {
        if ($actionPropertyHospitalBed) {
            $this->addCond('pruneCond0', $this->getAliasedColName(ActionPropertyHospitalBedPeer::ID), $actionPropertyHospitalBed->getid(), Criteria::NOT_EQUAL);
            $this->addCond('pruneCond1', $this->getAliasedColName(ActionPropertyHospitalBedPeer::INDEX), $actionPropertyHospitalBed->getindex(), Criteria::NOT_EQUAL);
            $this->combine(array('pruneCond0', 'pruneCond1'), Criteria::LOGICAL_OR);
        }

        return $this;
    }

}
