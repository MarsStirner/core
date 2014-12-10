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
use Webmis\Models\ActionPropertyTime;
use Webmis\Models\ActionPropertyTimePeer;
use Webmis\Models\ActionPropertyTimeQuery;

/**
 * Base class that represents a query for the 'ActionProperty_Time' table.
 *
 *
 *
 * @method ActionPropertyTimeQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyTimeQuery orderByindex($order = Criteria::ASC) Order by the index column
 * @method ActionPropertyTimeQuery orderByialue($order = Criteria::ASC) Order by the value column
 *
 * @method ActionPropertyTimeQuery groupByid() Group by the id column
 * @method ActionPropertyTimeQuery groupByindex() Group by the index column
 * @method ActionPropertyTimeQuery groupByialue() Group by the value column
 *
 * @method ActionPropertyTimeQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyTimeQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyTimeQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyTimeQuery leftJoinActionProperty($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyTimeQuery rightJoinActionProperty($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyTimeQuery innerJoinActionProperty($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionProperty relation
 *
 * @method ActionPropertyTime findOne(PropelPDO $con = null) Return the first ActionPropertyTime matching the query
 * @method ActionPropertyTime findOneOrCreate(PropelPDO $con = null) Return the first ActionPropertyTime matching the query, or a new ActionPropertyTime object populated from the query conditions when no match is found
 *
 * @method ActionPropertyTime findOneByid(int $id) Return the first ActionPropertyTime filtered by the id column
 * @method ActionPropertyTime findOneByindex(int $index) Return the first ActionPropertyTime filtered by the index column
 * @method ActionPropertyTime findOneByialue(string $value) Return the first ActionPropertyTime filtered by the value column
 *
 * @method array findByid(int $id) Return ActionPropertyTime objects filtered by the id column
 * @method array findByindex(int $index) Return ActionPropertyTime objects filtered by the index column
 * @method array findByialue(string $value) Return ActionPropertyTime objects filtered by the value column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyTimeQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyTimeQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionPropertyTime', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyTimeQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyTimeQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyTimeQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyTimeQuery) {
            return $criteria;
        }
        $query = new ActionPropertyTimeQuery();
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
     * @return   ActionPropertyTime|ActionPropertyTime[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyTimePeer::getInstanceFromPool(serialize(array((string) $key[0], (string) $key[1]))))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyTimePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionPropertyTime A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `index`, `value` FROM `ActionProperty_Time` WHERE `id` = :p0 AND `index` = :p1';
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
            $obj = new ActionPropertyTime();
            $obj->hydrate($row);
            ActionPropertyTimePeer::addInstanceToPool($obj, serialize(array((string) $key[0], (string) $key[1])));
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
     * @return ActionPropertyTime|ActionPropertyTime[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ActionPropertyTime[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {
        $this->addUsingAlias(ActionPropertyTimePeer::ID, $key[0], Criteria::EQUAL);
        $this->addUsingAlias(ActionPropertyTimePeer::INDEX, $key[1], Criteria::EQUAL);

        return $this;
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {
        if (empty($keys)) {
            return $this->add(null, '1<>1', Criteria::CUSTOM);
        }
        foreach ($keys as $key) {
            $cton0 = $this->getNewCriterion(ActionPropertyTimePeer::ID, $key[0], Criteria::EQUAL);
            $cton1 = $this->getNewCriterion(ActionPropertyTimePeer::INDEX, $key[1], Criteria::EQUAL);
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
     * @see       filterByActionProperty()
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTimePeer::ID, $id, $comparison);
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
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function filterByindex($index = null, $comparison = null)
    {
        if (is_array($index)) {
            $useMinMax = false;
            if (isset($index['min'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::INDEX, $index['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($index['max'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::INDEX, $index['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTimePeer::INDEX, $index, $comparison);
    }

    /**
     * Filter the query on the value column
     *
     * Example usage:
     * <code>
     * $query->filterByialue('2011-03-14'); // WHERE value = '2011-03-14'
     * $query->filterByialue('now'); // WHERE value = '2011-03-14'
     * $query->filterByialue(array('max' => 'yesterday')); // WHERE value > '2011-03-13'
     * </code>
     *
     * @param     mixed $ialue The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function filterByialue($ialue = null, $comparison = null)
    {
        if (is_array($ialue)) {
            $useMinMax = false;
            if (isset($ialue['min'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::VALUE, $ialue['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ialue['max'])) {
                $this->addUsingAlias(ActionPropertyTimePeer::VALUE, $ialue['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyTimePeer::VALUE, $ialue, $comparison);
    }

    /**
     * Filter the query by a related ActionProperty object
     *
     * @param   ActionProperty|PropelObjectCollection $actionProperty The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyTimeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionProperty($actionProperty, $comparison = null)
    {
        if ($actionProperty instanceof ActionProperty) {
            return $this
                ->addUsingAlias(ActionPropertyTimePeer::ID, $actionProperty->getid(), $comparison);
        } elseif ($actionProperty instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyTimePeer::ID, $actionProperty->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ActionPropertyTimeQuery The current query, for fluid interface
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
     * @param   ActionPropertyTime $actionPropertyTime Object to remove from the list of results
     *
     * @return ActionPropertyTimeQuery The current query, for fluid interface
     */
    public function prune($actionPropertyTime = null)
    {
        if ($actionPropertyTime) {
            $this->addCond('pruneCond0', $this->getAliasedColName(ActionPropertyTimePeer::ID), $actionPropertyTime->getid(), Criteria::NOT_EQUAL);
            $this->addCond('pruneCond1', $this->getAliasedColName(ActionPropertyTimePeer::INDEX), $actionPropertyTime->getindex(), Criteria::NOT_EQUAL);
            $this->combine(array('pruneCond0', 'pruneCond1'), Criteria::LOGICAL_OR);
        }

        return $this;
    }

}
