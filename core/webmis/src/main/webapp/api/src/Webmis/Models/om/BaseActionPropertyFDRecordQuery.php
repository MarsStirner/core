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
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\ActionPropertyFDRecordPeer;
use Webmis\Models\ActionPropertyFDRecordQuery;
use Webmis\Models\FDFieldValue;
use Webmis\Models\FDRecord;

/**
 * Base class that represents a query for the 'ActionProperty_FDRecord' table.
 *
 *
 *
 * @method ActionPropertyFDRecordQuery orderById($order = Criteria::ASC) Order by the id column
 * @method ActionPropertyFDRecordQuery orderByIndex($order = Criteria::ASC) Order by the index column
 * @method ActionPropertyFDRecordQuery orderByValue($order = Criteria::ASC) Order by the value column
 *
 * @method ActionPropertyFDRecordQuery groupById() Group by the id column
 * @method ActionPropertyFDRecordQuery groupByIndex() Group by the index column
 * @method ActionPropertyFDRecordQuery groupByValue() Group by the value column
 *
 * @method ActionPropertyFDRecordQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionPropertyFDRecordQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionPropertyFDRecordQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionPropertyFDRecordQuery leftJoinFDRecord($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDRecord relation
 * @method ActionPropertyFDRecordQuery rightJoinFDRecord($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDRecord relation
 * @method ActionPropertyFDRecordQuery innerJoinFDRecord($relationAlias = null) Adds a INNER JOIN clause to the query using the FDRecord relation
 *
 * @method ActionPropertyFDRecordQuery leftJoinFDFieldValue($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDFieldValue relation
 * @method ActionPropertyFDRecordQuery rightJoinFDFieldValue($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDFieldValue relation
 * @method ActionPropertyFDRecordQuery innerJoinFDFieldValue($relationAlias = null) Adds a INNER JOIN clause to the query using the FDFieldValue relation
 *
 * @method ActionPropertyFDRecordQuery leftJoinActionProperty($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyFDRecordQuery rightJoinActionProperty($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionProperty relation
 * @method ActionPropertyFDRecordQuery innerJoinActionProperty($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionProperty relation
 *
 * @method ActionPropertyFDRecord findOne(PropelPDO $con = null) Return the first ActionPropertyFDRecord matching the query
 * @method ActionPropertyFDRecord findOneOrCreate(PropelPDO $con = null) Return the first ActionPropertyFDRecord matching the query, or a new ActionPropertyFDRecord object populated from the query conditions when no match is found
 *
 * @method ActionPropertyFDRecord findOneByIndex(int $index) Return the first ActionPropertyFDRecord filtered by the index column
 * @method ActionPropertyFDRecord findOneByValue(int $value) Return the first ActionPropertyFDRecord filtered by the value column
 *
 * @method array findById(int $id) Return ActionPropertyFDRecord objects filtered by the id column
 * @method array findByIndex(int $index) Return ActionPropertyFDRecord objects filtered by the index column
 * @method array findByValue(int $value) Return ActionPropertyFDRecord objects filtered by the value column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyFDRecordQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionPropertyFDRecordQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionPropertyFDRecord', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionPropertyFDRecordQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionPropertyFDRecordQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionPropertyFDRecordQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionPropertyFDRecordQuery) {
            return $criteria;
        }
        $query = new ActionPropertyFDRecordQuery();
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
     * @return   ActionPropertyFDRecord|ActionPropertyFDRecord[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPropertyFDRecordPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPropertyFDRecordPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionPropertyFDRecord A model object, or null if the key is not found
     * @throws PropelException
     */
     public function findOneById($key, $con = null)
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
     * @return                 ActionPropertyFDRecord A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `index`, `value` FROM `ActionProperty_FDRecord` WHERE `id` = :p0';
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
            $obj = new ActionPropertyFDRecord();
            $obj->hydrate($row);
            ActionPropertyFDRecordPeer::addInstanceToPool($obj, (string) $key);
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
     * @return ActionPropertyFDRecord|ActionPropertyFDRecord[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ActionPropertyFDRecord[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $keys, Criteria::IN);
    }

    /**
     * Filter the query on the id column
     *
     * Example usage:
     * <code>
     * $query->filterById(1234); // WHERE id = 1234
     * $query->filterById(array(12, 34)); // WHERE id IN (12, 34)
     * $query->filterById(array('min' => 12)); // WHERE id >= 12
     * $query->filterById(array('max' => 12)); // WHERE id <= 12
     * </code>
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the index column
     *
     * Example usage:
     * <code>
     * $query->filterByIndex(1234); // WHERE index = 1234
     * $query->filterByIndex(array(12, 34)); // WHERE index IN (12, 34)
     * $query->filterByIndex(array('min' => 12)); // WHERE index >= 12
     * $query->filterByIndex(array('max' => 12)); // WHERE index <= 12
     * </code>
     *
     * @param     mixed $index The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function filterByIndex($index = null, $comparison = null)
    {
        if (is_array($index)) {
            $useMinMax = false;
            if (isset($index['min'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::INDEX, $index['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($index['max'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::INDEX, $index['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyFDRecordPeer::INDEX, $index, $comparison);
    }

    /**
     * Filter the query on the value column
     *
     * Example usage:
     * <code>
     * $query->filterByValue(1234); // WHERE value = 1234
     * $query->filterByValue(array(12, 34)); // WHERE value IN (12, 34)
     * $query->filterByValue(array('min' => 12)); // WHERE value >= 12
     * $query->filterByValue(array('max' => 12)); // WHERE value <= 12
     * </code>
     *
     * @see       filterByFDRecord()
     *
     * @see       filterByFDFieldValue()
     *
     * @param     mixed $value The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function filterByValue($value = null, $comparison = null)
    {
        if (is_array($value)) {
            $useMinMax = false;
            if (isset($value['min'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $value['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($value['max'])) {
                $this->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $value['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $value, $comparison);
    }

    /**
     * Filter the query by a related FDRecord object
     *
     * @param   FDRecord|PropelObjectCollection $fDRecord The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyFDRecordQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDRecord($fDRecord, $comparison = null)
    {
        if ($fDRecord instanceof FDRecord) {
            return $this
                ->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $fDRecord->getId(), $comparison);
        } elseif ($fDRecord instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $fDRecord->toKeyValue('PrimaryKey', 'Id'), $comparison);
        } else {
            throw new PropelException('filterByFDRecord() only accepts arguments of type FDRecord or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the FDRecord relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function joinFDRecord($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('FDRecord');

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
            $this->addJoinObject($join, 'FDRecord');
        }

        return $this;
    }

    /**
     * Use the FDRecord relation FDRecord object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\FDRecordQuery A secondary query class using the current class as primary query
     */
    public function useFDRecordQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinFDRecord($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'FDRecord', '\Webmis\Models\FDRecordQuery');
    }

    /**
     * Filter the query by a related FDFieldValue object
     *
     * @param   FDFieldValue|PropelObjectCollection $fDFieldValue The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyFDRecordQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDFieldValue($fDFieldValue, $comparison = null)
    {
        if ($fDFieldValue instanceof FDFieldValue) {
            return $this
                ->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $fDFieldValue->getFDRecordId(), $comparison);
        } elseif ($fDFieldValue instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPropertyFDRecordPeer::VALUE, $fDFieldValue->toKeyValue('PrimaryKey', 'FDRecordId'), $comparison);
        } else {
            throw new PropelException('filterByFDFieldValue() only accepts arguments of type FDFieldValue or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the FDFieldValue relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function joinFDFieldValue($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('FDFieldValue');

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
            $this->addJoinObject($join, 'FDFieldValue');
        }

        return $this;
    }

    /**
     * Use the FDFieldValue relation FDFieldValue object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\FDFieldValueQuery A secondary query class using the current class as primary query
     */
    public function useFDFieldValueQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinFDFieldValue($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'FDFieldValue', '\Webmis\Models\FDFieldValueQuery');
    }

    /**
     * Filter the query by a related ActionProperty object
     *
     * @param   ActionProperty|PropelObjectCollection $actionProperty  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionPropertyFDRecordQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionProperty($actionProperty, $comparison = null)
    {
        if ($actionProperty instanceof ActionProperty) {
            return $this
                ->addUsingAlias(ActionPropertyFDRecordPeer::ID, $actionProperty->getid(), $comparison);
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
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
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
     * @param   ActionPropertyFDRecord $actionPropertyFDRecord Object to remove from the list of results
     *
     * @return ActionPropertyFDRecordQuery The current query, for fluid interface
     */
    public function prune($actionPropertyFDRecord = null)
    {
        if ($actionPropertyFDRecord) {
            $this->addUsingAlias(ActionPropertyFDRecordPeer::ID, $actionPropertyFDRecord->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
