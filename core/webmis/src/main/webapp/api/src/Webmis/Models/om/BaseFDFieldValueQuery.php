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
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\FDField;
use Webmis\Models\FDFieldValue;
use Webmis\Models\FDFieldValuePeer;
use Webmis\Models\FDFieldValueQuery;
use Webmis\Models\FDRecord;

/**
 * Base class that represents a query for the 'FDFieldValue' table.
 *
 *
 *
 * @method FDFieldValueQuery orderById($order = Criteria::ASC) Order by the id column
 * @method FDFieldValueQuery orderByFDRecordId($order = Criteria::ASC) Order by the fdRecord_id column
 * @method FDFieldValueQuery orderByFDFieldId($order = Criteria::ASC) Order by the fdField_id column
 * @method FDFieldValueQuery orderByValue($order = Criteria::ASC) Order by the value column
 *
 * @method FDFieldValueQuery groupById() Group by the id column
 * @method FDFieldValueQuery groupByFDRecordId() Group by the fdRecord_id column
 * @method FDFieldValueQuery groupByFDFieldId() Group by the fdField_id column
 * @method FDFieldValueQuery groupByValue() Group by the value column
 *
 * @method FDFieldValueQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method FDFieldValueQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method FDFieldValueQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method FDFieldValueQuery leftJoinFDField($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDField relation
 * @method FDFieldValueQuery rightJoinFDField($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDField relation
 * @method FDFieldValueQuery innerJoinFDField($relationAlias = null) Adds a INNER JOIN clause to the query using the FDField relation
 *
 * @method FDFieldValueQuery leftJoinFDRecord($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDRecord relation
 * @method FDFieldValueQuery rightJoinFDRecord($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDRecord relation
 * @method FDFieldValueQuery innerJoinFDRecord($relationAlias = null) Adds a INNER JOIN clause to the query using the FDRecord relation
 *
 * @method FDFieldValueQuery leftJoinActionPropertyFDRecord($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method FDFieldValueQuery rightJoinActionPropertyFDRecord($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method FDFieldValueQuery innerJoinActionPropertyFDRecord($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyFDRecord relation
 *
 * @method FDFieldValue findOne(PropelPDO $con = null) Return the first FDFieldValue matching the query
 * @method FDFieldValue findOneOrCreate(PropelPDO $con = null) Return the first FDFieldValue matching the query, or a new FDFieldValue object populated from the query conditions when no match is found
 *
 * @method FDFieldValue findOneByFDRecordId(int $fdRecord_id) Return the first FDFieldValue filtered by the fdRecord_id column
 * @method FDFieldValue findOneByFDFieldId(int $fdField_id) Return the first FDFieldValue filtered by the fdField_id column
 * @method FDFieldValue findOneByValue(string $value) Return the first FDFieldValue filtered by the value column
 *
 * @method array findById(int $id) Return FDFieldValue objects filtered by the id column
 * @method array findByFDRecordId(int $fdRecord_id) Return FDFieldValue objects filtered by the fdRecord_id column
 * @method array findByFDFieldId(int $fdField_id) Return FDFieldValue objects filtered by the fdField_id column
 * @method array findByValue(string $value) Return FDFieldValue objects filtered by the value column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseFDFieldValueQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseFDFieldValueQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\FDFieldValue', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new FDFieldValueQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   FDFieldValueQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return FDFieldValueQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof FDFieldValueQuery) {
            return $criteria;
        }
        $query = new FDFieldValueQuery();
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
     * @return   FDFieldValue|FDFieldValue[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = FDFieldValuePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(FDFieldValuePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 FDFieldValue A model object, or null if the key is not found
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
     * @return                 FDFieldValue A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `fdRecord_id`, `fdField_id`, `value` FROM `FDFieldValue` WHERE `id` = :p0';
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
            $obj = new FDFieldValue();
            $obj->hydrate($row);
            FDFieldValuePeer::addInstanceToPool($obj, (string) $key);
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
     * @return FDFieldValue|FDFieldValue[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|FDFieldValue[]|mixed the list of results, formatted by the current formatter
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
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(FDFieldValuePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(FDFieldValuePeer::ID, $keys, Criteria::IN);
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
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(FDFieldValuePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(FDFieldValuePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldValuePeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the fdRecord_id column
     *
     * Example usage:
     * <code>
     * $query->filterByFDRecordId(1234); // WHERE fdRecord_id = 1234
     * $query->filterByFDRecordId(array(12, 34)); // WHERE fdRecord_id IN (12, 34)
     * $query->filterByFDRecordId(array('min' => 12)); // WHERE fdRecord_id >= 12
     * $query->filterByFDRecordId(array('max' => 12)); // WHERE fdRecord_id <= 12
     * </code>
     *
     * @see       filterByFDRecord()
     *
     * @param     mixed $fDRecordId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterByFDRecordId($fDRecordId = null, $comparison = null)
    {
        if (is_array($fDRecordId)) {
            $useMinMax = false;
            if (isset($fDRecordId['min'])) {
                $this->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $fDRecordId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($fDRecordId['max'])) {
                $this->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $fDRecordId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $fDRecordId, $comparison);
    }

    /**
     * Filter the query on the fdField_id column
     *
     * Example usage:
     * <code>
     * $query->filterByFDFieldId(1234); // WHERE fdField_id = 1234
     * $query->filterByFDFieldId(array(12, 34)); // WHERE fdField_id IN (12, 34)
     * $query->filterByFDFieldId(array('min' => 12)); // WHERE fdField_id >= 12
     * $query->filterByFDFieldId(array('max' => 12)); // WHERE fdField_id <= 12
     * </code>
     *
     * @see       filterByFDField()
     *
     * @param     mixed $fDFieldId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterByFDFieldId($fDFieldId = null, $comparison = null)
    {
        if (is_array($fDFieldId)) {
            $useMinMax = false;
            if (isset($fDFieldId['min'])) {
                $this->addUsingAlias(FDFieldValuePeer::FDFIELD_ID, $fDFieldId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($fDFieldId['max'])) {
                $this->addUsingAlias(FDFieldValuePeer::FDFIELD_ID, $fDFieldId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldValuePeer::FDFIELD_ID, $fDFieldId, $comparison);
    }

    /**
     * Filter the query on the value column
     *
     * Example usage:
     * <code>
     * $query->filterByValue('fooValue');   // WHERE value = 'fooValue'
     * $query->filterByValue('%fooValue%'); // WHERE value LIKE '%fooValue%'
     * </code>
     *
     * @param     string $value The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function filterByValue($value = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($value)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $value)) {
                $value = str_replace('*', '%', $value);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(FDFieldValuePeer::VALUE, $value, $comparison);
    }

    /**
     * Filter the query by a related FDField object
     *
     * @param   FDField|PropelObjectCollection $fDField The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDFieldValueQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDField($fDField, $comparison = null)
    {
        if ($fDField instanceof FDField) {
            return $this
                ->addUsingAlias(FDFieldValuePeer::FDFIELD_ID, $fDField->getId(), $comparison);
        } elseif ($fDField instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(FDFieldValuePeer::FDFIELD_ID, $fDField->toKeyValue('PrimaryKey', 'Id'), $comparison);
        } else {
            throw new PropelException('filterByFDField() only accepts arguments of type FDField or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the FDField relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function joinFDField($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('FDField');

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
            $this->addJoinObject($join, 'FDField');
        }

        return $this;
    }

    /**
     * Use the FDField relation FDField object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\FDFieldQuery A secondary query class using the current class as primary query
     */
    public function useFDFieldQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinFDField($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'FDField', '\Webmis\Models\FDFieldQuery');
    }

    /**
     * Filter the query by a related FDRecord object
     *
     * @param   FDRecord|PropelObjectCollection $fDRecord The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDFieldValueQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDRecord($fDRecord, $comparison = null)
    {
        if ($fDRecord instanceof FDRecord) {
            return $this
                ->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $fDRecord->getId(), $comparison);
        } elseif ($fDRecord instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $fDRecord->toKeyValue('PrimaryKey', 'Id'), $comparison);
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
     * @return FDFieldValueQuery The current query, for fluid interface
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
     * Filter the query by a related ActionPropertyFDRecord object
     *
     * @param   ActionPropertyFDRecord|PropelObjectCollection $actionPropertyFDRecord  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDFieldValueQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyFDRecord($actionPropertyFDRecord, $comparison = null)
    {
        if ($actionPropertyFDRecord instanceof ActionPropertyFDRecord) {
            return $this
                ->addUsingAlias(FDFieldValuePeer::FDRECORD_ID, $actionPropertyFDRecord->getValue(), $comparison);
        } elseif ($actionPropertyFDRecord instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyFDRecordQuery()
                ->filterByPrimaryKeys($actionPropertyFDRecord->getPrimaryKeys())
                ->endUse();
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
     * @return FDFieldValueQuery The current query, for fluid interface
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
     * Exclude object from result
     *
     * @param   FDFieldValue $fDFieldValue Object to remove from the list of results
     *
     * @return FDFieldValueQuery The current query, for fluid interface
     */
    public function prune($fDFieldValue = null)
    {
        if ($fDFieldValue) {
            $this->addUsingAlias(FDFieldValuePeer::ID, $fDFieldValue->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
