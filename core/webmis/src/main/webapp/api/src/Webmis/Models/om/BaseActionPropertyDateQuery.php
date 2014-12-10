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
use Webmis\Models\ActionPropertyDate;
use Webmis\Models\ActionPropertyDatePeer;
use Webmis\Models\ActionPropertyDateQuery;

/**
 * Base class that represents a query for the 'ActionProperty_Date' table.
 *
 *
 *
 * @method ActionPropertyDateQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyDateQuery orderByindex($order = Criteria::ASC) Order by the index column
 * @method ActionPropertyDateQuery orderByvalue($order = Criteria::ASC) Order by the value column
 *
 * @method ActionPropertyDateQuery groupByid() Group by the id column
 * @method ActionPropertyDateQuery groupByindex() Group by the index column
 * @method ActionPropertyDateQuery groupByvalue() Group by the value column
 *
 * @method ActionPropertyDateQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyDateQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyDateQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyDateQuery leftJoinActionProperty($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyDateQuery rightJoinActionProperty($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyDateQuery innerJoinActionProperty($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionProperty relation
 *
 * @method ActionPropertyDate findOne(PropelPDO $con = null) Return the first ActionPropertyDate matching the query
 * @method ActionPropertyDate findOneOrCreate(PropelPDO $con = null) Return the first ActionPropertyDate matching the query, or a new ActionPropertyDate object populated from the query conditions when no match is found
 *
 * @method ActionPropertyDate findOneByid(int $id) Return the first ActionPropertyDate filtered by the id column
 * @method ActionPropertyDate findOneByindex(int $index) Return the first ActionPropertyDate filtered by the index column
 * @method ActionPropertyDate findOneByvalue(string $value) Return the first ActionPropertyDate filtered by the value column
 *
 * @method array findByid(int $id) Return ActionPropertyDate objects filtered by the id column
 * @method array findByindex(int $index) Return ActionPropertyDate objects filtered by the index column
 * @method array findByvalue(string $value) Return ActionPropertyDate objects filtered by the value column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyDateQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyDateQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionPropertyDate', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyDateQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyDateQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyDateQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyDateQuery) {
            return $criteria;
        }
        $query = new ActionPropertyDateQuery();
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
     * @return   ActionPropertyDate|ActionPropertyDate[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyDatePeer::getInstanceFromPool(serialize(array((string) $key[0], (string) $key[1]))))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyDatePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionPropertyDate A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `index`, `value` FROM `ActionProperty_Date` WHERE `id` = :p0 AND `index` = :p1';
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
            $obj = new ActionPropertyDate();
            $obj->hydrate($row);
            ActionPropertyDatePeer::addInstanceToPool($obj, serialize(array((string) $key[0], (string) $key[1])));
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
     * @return ActionPropertyDate|ActionPropertyDate[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ActionPropertyDate[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {
        $this->addUsingAlias(ActionPropertyDatePeer::ID, $key[0], Criteria::EQUAL);
        $this->addUsingAlias(ActionPropertyDatePeer::INDEX, $key[1], Criteria::EQUAL);

        return $this;
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {
        if (empty($keys)) {
            return $this->add(null, '1<>1', Criteria::CUSTOM);
        }
        foreach ($keys as $key) {
            $cton0 = $this->getNewCriterion(ActionPropertyDatePeer::ID, $key[0], Criteria::EQUAL);
            $cton1 = $this->getNewCriterion(ActionPropertyDatePeer::INDEX, $key[1], Criteria::EQUAL);
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
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyDatePeer::ID, $id, $comparison);
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
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function filterByindex($index = null, $comparison = null)
    {
        if (is_array($index)) {
            $useMinMax = false;
            if (isset($index['min'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::INDEX, $index['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($index['max'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::INDEX, $index['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyDatePeer::INDEX, $index, $comparison);
    }

    /**
     * Filter the query on the value column
     *
     * Example usage:
     * <code>
     * $query->filterByvalue('2011-03-14'); // WHERE value = '2011-03-14'
     * $query->filterByvalue('now'); // WHERE value = '2011-03-14'
     * $query->filterByvalue(array('max' => 'yesterday')); // WHERE value > '2011-03-13'
     * </code>
     *
     * @param     mixed $value The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function filterByvalue($value = null, $comparison = null)
    {
        if (is_array($value)) {
            $useMinMax = false;
            if (isset($value['min'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::VALUE, $value['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($value['max'])) {
                $this->addUsingAlias(ActionPropertyDatePeer::VALUE, $value['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyDatePeer::VALUE, $value, $comparison);
    }

    /**
     * Filter the query by a related ActionProperty object
     *
     * @param   ActionProperty|PropelObjectCollection $actionProperty  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyDateQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionProperty($actionProperty, $comparison = null)
    {
        if ($actionProperty instanceof ActionProperty) {
            return $this
                ->addUsingAlias(ActionPropertyDatePeer::ID, $actionProperty->getid(), $comparison);
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
     * @return ActionPropertyDateQuery The current query, for fluid interface
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
     * Exclude object from result
     *
     * @param   ActionPropertyDate $actionPropertyDate Object to remove from the list of results
     *
     * @return ActionPropertyDateQuery The current query, for fluid interface
     */
    public function prune($actionPropertyDate = null)
    {
        if ($actionPropertyDate) {
            $this->addCond('pruneCond0', $this->getAliasedColName(ActionPropertyDatePeer::ID), $actionPropertyDate->getid(), Criteria::NOT_EQUAL);
            $this->addCond('pruneCond1', $this->getAliasedColName(ActionPropertyDatePeer::INDEX), $actionPropertyDate->getindex(), Criteria::NOT_EQUAL);
            $this->combine(array('pruneCond0', 'pruneCond1'), Criteria::LOGICAL_OR);
        }

        return $this;
    }

}
