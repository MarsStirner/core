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
use Webmis\Models\RbStorage;
use Webmis\Models\RlsBalanceOfGoods;
use Webmis\Models\RlsBalanceOfGoodsPeer;
use Webmis\Models\RlsBalanceOfGoodsQuery;
use Webmis\Models\rlsNomen;

/**
 * Base class that represents a query for the 'rlsBalanceOfGoods' table.
 *
 *
 *
 * @method RlsBalanceOfGoodsQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RlsBalanceOfGoodsQuery orderByrlsNomenId($order = Criteria::ASC) Order by the rlsNomen_id column
 * @method RlsBalanceOfGoodsQuery orderByvalue($order = Criteria::ASC) Order by the value column
 * @method RlsBalanceOfGoodsQuery orderBybestBefore($order = Criteria::ASC) Order by the bestBefore column
 * @method RlsBalanceOfGoodsQuery orderBydisabled($order = Criteria::ASC) Order by the disabled column
 * @method RlsBalanceOfGoodsQuery orderByupdateDateTime($order = Criteria::ASC) Order by the updateDateTime column
 * @method RlsBalanceOfGoodsQuery orderBystorageId($order = Criteria::ASC) Order by the storage_id column
 *
 * @method RlsBalanceOfGoodsQuery groupByid() Group by the id column
 * @method RlsBalanceOfGoodsQuery groupByrlsNomenId() Group by the rlsNomen_id column
 * @method RlsBalanceOfGoodsQuery groupByvalue() Group by the value column
 * @method RlsBalanceOfGoodsQuery groupBybestBefore() Group by the bestBefore column
 * @method RlsBalanceOfGoodsQuery groupBydisabled() Group by the disabled column
 * @method RlsBalanceOfGoodsQuery groupByupdateDateTime() Group by the updateDateTime column
 * @method RlsBalanceOfGoodsQuery groupBystorageId() Group by the storage_id column
 *
 * @method RlsBalanceOfGoodsQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RlsBalanceOfGoodsQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RlsBalanceOfGoodsQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RlsBalanceOfGoodsQuery leftJoinRbStorage($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbStorage relation
 * @method RlsBalanceOfGoodsQuery rightJoinRbStorage($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbStorage relation
 * @method RlsBalanceOfGoodsQuery innerJoinRbStorage($relationAlias = null) Adds a INNER JOIN clause to the query using the RbStorage relation
 *
 * @method RlsBalanceOfGoodsQuery leftJoinrlsNomen($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsNomen relation
 * @method RlsBalanceOfGoodsQuery rightJoinrlsNomen($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsNomen relation
 * @method RlsBalanceOfGoodsQuery innerJoinrlsNomen($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsNomen relation
 *
 * @method RlsBalanceOfGoods findOne(PropelPDO $con = null) Return the first RlsBalanceOfGoods matching the query
 * @method RlsBalanceOfGoods findOneOrCreate(PropelPDO $con = null) Return the first RlsBalanceOfGoods matching the query, or a new RlsBalanceOfGoods object populated from the query conditions when no match is found
 *
 * @method RlsBalanceOfGoods findOneByrlsNomenId(int $rlsNomen_id) Return the first RlsBalanceOfGoods filtered by the rlsNomen_id column
 * @method RlsBalanceOfGoods findOneByvalue(double $value) Return the first RlsBalanceOfGoods filtered by the value column
 * @method RlsBalanceOfGoods findOneBybestBefore(string $bestBefore) Return the first RlsBalanceOfGoods filtered by the bestBefore column
 * @method RlsBalanceOfGoods findOneBydisabled(int $disabled) Return the first RlsBalanceOfGoods filtered by the disabled column
 * @method RlsBalanceOfGoods findOneByupdateDateTime(string $updateDateTime) Return the first RlsBalanceOfGoods filtered by the updateDateTime column
 * @method RlsBalanceOfGoods findOneBystorageId(int $storage_id) Return the first RlsBalanceOfGoods filtered by the storage_id column
 *
 * @method array findByid(int $id) Return RlsBalanceOfGoods objects filtered by the id column
 * @method array findByrlsNomenId(int $rlsNomen_id) Return RlsBalanceOfGoods objects filtered by the rlsNomen_id column
 * @method array findByvalue(double $value) Return RlsBalanceOfGoods objects filtered by the value column
 * @method array findBybestBefore(string $bestBefore) Return RlsBalanceOfGoods objects filtered by the bestBefore column
 * @method array findBydisabled(int $disabled) Return RlsBalanceOfGoods objects filtered by the disabled column
 * @method array findByupdateDateTime(string $updateDateTime) Return RlsBalanceOfGoods objects filtered by the updateDateTime column
 * @method array findBystorageId(int $storage_id) Return RlsBalanceOfGoods objects filtered by the storage_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRlsBalanceOfGoodsQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRlsBalanceOfGoodsQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RlsBalanceOfGoods', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RlsBalanceOfGoodsQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RlsBalanceOfGoodsQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RlsBalanceOfGoodsQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RlsBalanceOfGoodsQuery) {
            return $criteria;
        }
        $query = new RlsBalanceOfGoodsQuery();
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
     * @return   RlsBalanceOfGoods|RlsBalanceOfGoods[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RlsBalanceOfGoodsPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RlsBalanceOfGoodsPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RlsBalanceOfGoods A model object, or null if the key is not found
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
     * @return                 RlsBalanceOfGoods A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `rlsNomen_id`, `value`, `bestBefore`, `disabled`, `updateDateTime`, `storage_id` FROM `rlsBalanceOfGoods` WHERE `id` = :p0';
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
            $obj = new RlsBalanceOfGoods();
            $obj->hydrate($row);
            RlsBalanceOfGoodsPeer::addInstanceToPool($obj, (string) $key);
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
     * @return RlsBalanceOfGoods|RlsBalanceOfGoods[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RlsBalanceOfGoods[]|mixed the list of results, formatted by the current formatter
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
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $keys, Criteria::IN);
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
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the rlsNomen_id column
     *
     * Example usage:
     * <code>
     * $query->filterByrlsNomenId(1234); // WHERE rlsNomen_id = 1234
     * $query->filterByrlsNomenId(array(12, 34)); // WHERE rlsNomen_id IN (12, 34)
     * $query->filterByrlsNomenId(array('min' => 12)); // WHERE rlsNomen_id >= 12
     * $query->filterByrlsNomenId(array('max' => 12)); // WHERE rlsNomen_id <= 12
     * </code>
     *
     * @see       filterByrlsNomen()
     *
     * @param     mixed $rlsNomenId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByrlsNomenId($rlsNomenId = null, $comparison = null)
    {
        if (is_array($rlsNomenId)) {
            $useMinMax = false;
            if (isset($rlsNomenId['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $rlsNomenId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($rlsNomenId['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $rlsNomenId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $rlsNomenId, $comparison);
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
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByvalue($value = null, $comparison = null)
    {
        if (is_array($value)) {
            $useMinMax = false;
            if (isset($value['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::VALUE, $value['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($value['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::VALUE, $value['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::VALUE, $value, $comparison);
    }

    /**
     * Filter the query on the bestBefore column
     *
     * Example usage:
     * <code>
     * $query->filterBybestBefore('2011-03-14'); // WHERE bestBefore = '2011-03-14'
     * $query->filterBybestBefore('now'); // WHERE bestBefore = '2011-03-14'
     * $query->filterBybestBefore(array('max' => 'yesterday')); // WHERE bestBefore > '2011-03-13'
     * </code>
     *
     * @param     mixed $bestBefore The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterBybestBefore($bestBefore = null, $comparison = null)
    {
        if (is_array($bestBefore)) {
            $useMinMax = false;
            if (isset($bestBefore['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::BESTBEFORE, $bestBefore['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($bestBefore['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::BESTBEFORE, $bestBefore['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::BESTBEFORE, $bestBefore, $comparison);
    }

    /**
     * Filter the query on the disabled column
     *
     * Example usage:
     * <code>
     * $query->filterBydisabled(1234); // WHERE disabled = 1234
     * $query->filterBydisabled(array(12, 34)); // WHERE disabled IN (12, 34)
     * $query->filterBydisabled(array('min' => 12)); // WHERE disabled >= 12
     * $query->filterBydisabled(array('max' => 12)); // WHERE disabled <= 12
     * </code>
     *
     * @param     mixed $disabled The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterBydisabled($disabled = null, $comparison = null)
    {
        if (is_array($disabled)) {
            $useMinMax = false;
            if (isset($disabled['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::DISABLED, $disabled['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($disabled['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::DISABLED, $disabled['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::DISABLED, $disabled, $comparison);
    }

    /**
     * Filter the query on the updateDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterByupdateDateTime('2011-03-14'); // WHERE updateDateTime = '2011-03-14'
     * $query->filterByupdateDateTime('now'); // WHERE updateDateTime = '2011-03-14'
     * $query->filterByupdateDateTime(array('max' => 'yesterday')); // WHERE updateDateTime > '2011-03-13'
     * </code>
     *
     * @param     mixed $updateDateTime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterByupdateDateTime($updateDateTime = null, $comparison = null)
    {
        if (is_array($updateDateTime)) {
            $useMinMax = false;
            if (isset($updateDateTime['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::UPDATEDATETIME, $updateDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($updateDateTime['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::UPDATEDATETIME, $updateDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::UPDATEDATETIME, $updateDateTime, $comparison);
    }

    /**
     * Filter the query on the storage_id column
     *
     * Example usage:
     * <code>
     * $query->filterBystorageId(1234); // WHERE storage_id = 1234
     * $query->filterBystorageId(array(12, 34)); // WHERE storage_id IN (12, 34)
     * $query->filterBystorageId(array('min' => 12)); // WHERE storage_id >= 12
     * $query->filterBystorageId(array('max' => 12)); // WHERE storage_id <= 12
     * </code>
     *
     * @see       filterByRbStorage()
     *
     * @param     mixed $storageId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function filterBystorageId($storageId = null, $comparison = null)
    {
        if (is_array($storageId)) {
            $useMinMax = false;
            if (isset($storageId['min'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::STORAGE_ID, $storageId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($storageId['max'])) {
                $this->addUsingAlias(RlsBalanceOfGoodsPeer::STORAGE_ID, $storageId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RlsBalanceOfGoodsPeer::STORAGE_ID, $storageId, $comparison);
    }

    /**
     * Filter the query by a related RbStorage object
     *
     * @param   RbStorage|PropelObjectCollection $rbStorage The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RlsBalanceOfGoodsQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbStorage($rbStorage, $comparison = null)
    {
        if ($rbStorage instanceof RbStorage) {
            return $this
                ->addUsingAlias(RlsBalanceOfGoodsPeer::STORAGE_ID, $rbStorage->getid(), $comparison);
        } elseif ($rbStorage instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(RlsBalanceOfGoodsPeer::STORAGE_ID, $rbStorage->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByRbStorage() only accepts arguments of type RbStorage or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RbStorage relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function joinRbStorage($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RbStorage');

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
            $this->addJoinObject($join, 'RbStorage');
        }

        return $this;
    }

    /**
     * Use the RbStorage relation RbStorage object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RbStorageQuery A secondary query class using the current class as primary query
     */
    public function useRbStorageQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinRbStorage($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RbStorage', '\Webmis\Models\RbStorageQuery');
    }

    /**
     * Filter the query by a related rlsNomen object
     *
     * @param   rlsNomen|PropelObjectCollection $rlsNomen The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RlsBalanceOfGoodsQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsNomen($rlsNomen, $comparison = null)
    {
        if ($rlsNomen instanceof rlsNomen) {
            return $this
                ->addUsingAlias(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $rlsNomen->getid(), $comparison);
        } elseif ($rlsNomen instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $rlsNomen->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsNomen() only accepts arguments of type rlsNomen or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsNomen relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function joinrlsNomen($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsNomen');

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
            $this->addJoinObject($join, 'rlsNomen');
        }

        return $this;
    }

    /**
     * Use the rlsNomen relation rlsNomen object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsNomenQuery A secondary query class using the current class as primary query
     */
    public function userlsNomenQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinrlsNomen($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsNomen', '\Webmis\Models\rlsNomenQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   RlsBalanceOfGoods $rlsBalanceOfGoods Object to remove from the list of results
     *
     * @return RlsBalanceOfGoodsQuery The current query, for fluid interface
     */
    public function prune($rlsBalanceOfGoods = null)
    {
        if ($rlsBalanceOfGoods) {
            $this->addUsingAlias(RlsBalanceOfGoodsPeer::ID, $rlsBalanceOfGoods->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
