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
use Webmis\Models\OrgStructure;
use Webmis\Models\RbStorage;
use Webmis\Models\RbStoragePeer;
use Webmis\Models\RbStorageQuery;
use Webmis\Models\RlsBalanceOfGoods;

/**
 * Base class that represents a query for the 'rbStorage' table.
 *
 *
 *
 * @method RbStorageQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RbStorageQuery orderByuuid($order = Criteria::ASC) Order by the uuid column
 * @method RbStorageQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method RbStorageQuery orderByorgStructureId($order = Criteria::ASC) Order by the orgStructure_id column
 *
 * @method RbStorageQuery groupByid() Group by the id column
 * @method RbStorageQuery groupByuuid() Group by the uuid column
 * @method RbStorageQuery groupByname() Group by the name column
 * @method RbStorageQuery groupByorgStructureId() Group by the orgStructure_id column
 *
 * @method RbStorageQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RbStorageQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RbStorageQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RbStorageQuery leftJoinOrgStructure($relationAlias = null) Adds a LEFT JOIN clause to the query using the OrgStructure relation
 * @method RbStorageQuery rightJoinOrgStructure($relationAlias = null) Adds a RIGHT JOIN clause to the query using the OrgStructure relation
 * @method RbStorageQuery innerJoinOrgStructure($relationAlias = null) Adds a INNER JOIN clause to the query using the OrgStructure relation
 *
 * @method RbStorageQuery leftJoinRlsBalanceOfGoods($relationAlias = null) Adds a LEFT JOIN clause to the query using the RlsBalanceOfGoods relation
 * @method RbStorageQuery rightJoinRlsBalanceOfGoods($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RlsBalanceOfGoods relation
 * @method RbStorageQuery innerJoinRlsBalanceOfGoods($relationAlias = null) Adds a INNER JOIN clause to the query using the RlsBalanceOfGoods relation
 *
 * @method RbStorage findOne(PropelPDO $con = null) Return the first RbStorage matching the query
 * @method RbStorage findOneOrCreate(PropelPDO $con = null) Return the first RbStorage matching the query, or a new RbStorage object populated from the query conditions when no match is found
 *
 * @method RbStorage findOneByuuid(string $uuid) Return the first RbStorage filtered by the uuid column
 * @method RbStorage findOneByname(string $name) Return the first RbStorage filtered by the name column
 * @method RbStorage findOneByorgStructureId(int $orgStructure_id) Return the first RbStorage filtered by the orgStructure_id column
 *
 * @method array findByid(int $id) Return RbStorage objects filtered by the id column
 * @method array findByuuid(string $uuid) Return RbStorage objects filtered by the uuid column
 * @method array findByname(string $name) Return RbStorage objects filtered by the name column
 * @method array findByorgStructureId(int $orgStructure_id) Return RbStorage objects filtered by the orgStructure_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbStorageQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRbStorageQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RbStorage', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RbStorageQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RbStorageQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RbStorageQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RbStorageQuery) {
            return $criteria;
        }
        $query = new RbStorageQuery();
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
     * @return   RbStorage|RbStorage[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RbStoragePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RbStoragePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RbStorage A model object, or null if the key is not found
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
     * @return                 RbStorage A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `uuid`, `name`, `orgStructure_id` FROM `rbStorage` WHERE `id` = :p0';
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
            $obj = new RbStorage();
            $obj->hydrate($row);
            RbStoragePeer::addInstanceToPool($obj, (string) $key);
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
     * @return RbStorage|RbStorage[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RbStorage[]|mixed the list of results, formatted by the current formatter
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
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RbStoragePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RbStoragePeer::ID, $keys, Criteria::IN);
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
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RbStoragePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RbStoragePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbStoragePeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the uuid column
     *
     * Example usage:
     * <code>
     * $query->filterByuuid('fooValue');   // WHERE uuid = 'fooValue'
     * $query->filterByuuid('%fooValue%'); // WHERE uuid LIKE '%fooValue%'
     * </code>
     *
     * @param     string $uuid The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function filterByuuid($uuid = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($uuid)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $uuid)) {
                $uuid = str_replace('*', '%', $uuid);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(RbStoragePeer::UUID, $uuid, $comparison);
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
     * @return RbStorageQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbStoragePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the orgStructure_id column
     *
     * Example usage:
     * <code>
     * $query->filterByorgStructureId(1234); // WHERE orgStructure_id = 1234
     * $query->filterByorgStructureId(array(12, 34)); // WHERE orgStructure_id IN (12, 34)
     * $query->filterByorgStructureId(array('min' => 12)); // WHERE orgStructure_id >= 12
     * $query->filterByorgStructureId(array('max' => 12)); // WHERE orgStructure_id <= 12
     * </code>
     *
     * @see       filterByOrgStructure()
     *
     * @param     mixed $orgStructureId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function filterByorgStructureId($orgStructureId = null, $comparison = null)
    {
        if (is_array($orgStructureId)) {
            $useMinMax = false;
            if (isset($orgStructureId['min'])) {
                $this->addUsingAlias(RbStoragePeer::ORGSTRUCTURE_ID, $orgStructureId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgStructureId['max'])) {
                $this->addUsingAlias(RbStoragePeer::ORGSTRUCTURE_ID, $orgStructureId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbStoragePeer::ORGSTRUCTURE_ID, $orgStructureId, $comparison);
    }

    /**
     * Filter the query by a related OrgStructure object
     *
     * @param   OrgStructure|PropelObjectCollection $orgStructure The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbStorageQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByOrgStructure($orgStructure, $comparison = null)
    {
        if ($orgStructure instanceof OrgStructure) {
            return $this
                ->addUsingAlias(RbStoragePeer::ORGSTRUCTURE_ID, $orgStructure->getId(), $comparison);
        } elseif ($orgStructure instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(RbStoragePeer::ORGSTRUCTURE_ID, $orgStructure->toKeyValue('PrimaryKey', 'Id'), $comparison);
        } else {
            throw new PropelException('filterByOrgStructure() only accepts arguments of type OrgStructure or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the OrgStructure relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function joinOrgStructure($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('OrgStructure');

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
            $this->addJoinObject($join, 'OrgStructure');
        }

        return $this;
    }

    /**
     * Use the OrgStructure relation OrgStructure object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\OrgStructureQuery A secondary query class using the current class as primary query
     */
    public function useOrgStructureQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinOrgStructure($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'OrgStructure', '\Webmis\Models\OrgStructureQuery');
    }

    /**
     * Filter the query by a related RlsBalanceOfGoods object
     *
     * @param   RlsBalanceOfGoods|PropelObjectCollection $rlsBalanceOfGoods  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbStorageQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRlsBalanceOfGoods($rlsBalanceOfGoods, $comparison = null)
    {
        if ($rlsBalanceOfGoods instanceof RlsBalanceOfGoods) {
            return $this
                ->addUsingAlias(RbStoragePeer::ID, $rlsBalanceOfGoods->getstorageId(), $comparison);
        } elseif ($rlsBalanceOfGoods instanceof PropelObjectCollection) {
            return $this
                ->useRlsBalanceOfGoodsQuery()
                ->filterByPrimaryKeys($rlsBalanceOfGoods->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByRlsBalanceOfGoods() only accepts arguments of type RlsBalanceOfGoods or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RlsBalanceOfGoods relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function joinRlsBalanceOfGoods($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RlsBalanceOfGoods');

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
            $this->addJoinObject($join, 'RlsBalanceOfGoods');
        }

        return $this;
    }

    /**
     * Use the RlsBalanceOfGoods relation RlsBalanceOfGoods object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RlsBalanceOfGoodsQuery A secondary query class using the current class as primary query
     */
    public function useRlsBalanceOfGoodsQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinRlsBalanceOfGoods($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RlsBalanceOfGoods', '\Webmis\Models\RlsBalanceOfGoodsQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   RbStorage $rbStorage Object to remove from the list of results
     *
     * @return RbStorageQuery The current query, for fluid interface
     */
    public function prune($rbStorage = null)
    {
        if ($rbStorage) {
            $this->addUsingAlias(RbStoragePeer::ID, $rbStorage->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
