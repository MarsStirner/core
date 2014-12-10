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
use Webmis\Models\MkbQuotaTypePacientModel;
use Webmis\Models\MkbQuotaTypePacientModelPeer;
use Webmis\Models\MkbQuotaTypePacientModelQuery;
use Webmis\Models\QuotaType;
use Webmis\Models\RbPacientModel;

/**
 * Base class that represents a query for the 'MKB_QuotaType_PacientModel' table.
 *
 *
 *
 * @method MkbQuotaTypePacientModelQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method MkbQuotaTypePacientModelQuery orderBymkbId($order = Criteria::ASC) Order by the MKB_id column
 * @method MkbQuotaTypePacientModelQuery orderBypacientModelId($order = Criteria::ASC) Order by the pacientModel_id column
 * @method MkbQuotaTypePacientModelQuery orderByquotaTypeId($order = Criteria::ASC) Order by the quotaType_id column
 *
 * @method MkbQuotaTypePacientModelQuery groupByid() Group by the id column
 * @method MkbQuotaTypePacientModelQuery groupBymkbId() Group by the MKB_id column
 * @method MkbQuotaTypePacientModelQuery groupBypacientModelId() Group by the pacientModel_id column
 * @method MkbQuotaTypePacientModelQuery groupByquotaTypeId() Group by the quotaType_id column
 *
 * @method MkbQuotaTypePacientModelQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method MkbQuotaTypePacientModelQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method MkbQuotaTypePacientModelQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method MkbQuotaTypePacientModelQuery leftJoinQuotaType($relationAlias = null) Adds a LEFT JOIN clause to the query using the QuotaType relation
 * @method MkbQuotaTypePacientModelQuery rightJoinQuotaType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the QuotaType relation
 * @method MkbQuotaTypePacientModelQuery innerJoinQuotaType($relationAlias = null) Adds a INNER JOIN clause to the query using the QuotaType relation
 *
 * @method MkbQuotaTypePacientModelQuery leftJoinRbPacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbPacientModel relation
 * @method MkbQuotaTypePacientModelQuery rightJoinRbPacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbPacientModel relation
 * @method MkbQuotaTypePacientModelQuery innerJoinRbPacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the RbPacientModel relation
 *
 * @method MkbQuotaTypePacientModel findOne(PropelPDO $con = null) Return the first MkbQuotaTypePacientModel matching the query
 * @method MkbQuotaTypePacientModel findOneOrCreate(PropelPDO $con = null) Return the first MkbQuotaTypePacientModel matching the query, or a new MkbQuotaTypePacientModel object populated from the query conditions when no match is found
 *
 * @method MkbQuotaTypePacientModel findOneBymkbId(int $MKB_id) Return the first MkbQuotaTypePacientModel filtered by the MKB_id column
 * @method MkbQuotaTypePacientModel findOneBypacientModelId(int $pacientModel_id) Return the first MkbQuotaTypePacientModel filtered by the pacientModel_id column
 * @method MkbQuotaTypePacientModel findOneByquotaTypeId(int $quotaType_id) Return the first MkbQuotaTypePacientModel filtered by the quotaType_id column
 *
 * @method array findByid(int $id) Return MkbQuotaTypePacientModel objects filtered by the id column
 * @method array findBymkbId(int $MKB_id) Return MkbQuotaTypePacientModel objects filtered by the MKB_id column
 * @method array findBypacientModelId(int $pacientModel_id) Return MkbQuotaTypePacientModel objects filtered by the pacientModel_id column
 * @method array findByquotaTypeId(int $quotaType_id) Return MkbQuotaTypePacientModel objects filtered by the quotaType_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseMkbQuotaTypePacientModelQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseMkbQuotaTypePacientModelQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\MkbQuotaTypePacientModel', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new MkbQuotaTypePacientModelQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   MkbQuotaTypePacientModelQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return MkbQuotaTypePacientModelQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof MkbQuotaTypePacientModelQuery) {
            return $criteria;
        }
        $query = new MkbQuotaTypePacientModelQuery();
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
     * @return   MkbQuotaTypePacientModel|MkbQuotaTypePacientModel[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = MkbQuotaTypePacientModelPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(MkbQuotaTypePacientModelPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 MkbQuotaTypePacientModel A model object, or null if the key is not found
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
     * @return                 MkbQuotaTypePacientModel A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `MKB_id`, `pacientModel_id`, `quotaType_id` FROM `MKB_QuotaType_PacientModel` WHERE `id` = :p0';
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
            $obj = new MkbQuotaTypePacientModel();
            $obj->hydrate($row);
            MkbQuotaTypePacientModelPeer::addInstanceToPool($obj, (string) $key);
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
     * @return MkbQuotaTypePacientModel|MkbQuotaTypePacientModel[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|MkbQuotaTypePacientModel[]|mixed the list of results, formatted by the current formatter
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
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $keys, Criteria::IN);
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
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the MKB_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymkbId(1234); // WHERE MKB_id = 1234
     * $query->filterBymkbId(array(12, 34)); // WHERE MKB_id IN (12, 34)
     * $query->filterBymkbId(array('min' => 12)); // WHERE MKB_id >= 12
     * $query->filterBymkbId(array('max' => 12)); // WHERE MKB_id <= 12
     * </code>
     *
     * @param     mixed $mkbId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterBymkbId($mkbId = null, $comparison = null)
    {
        if (is_array($mkbId)) {
            $useMinMax = false;
            if (isset($mkbId['min'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::MKB_ID, $mkbId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($mkbId['max'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::MKB_ID, $mkbId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::MKB_ID, $mkbId, $comparison);
    }

    /**
     * Filter the query on the pacientModel_id column
     *
     * Example usage:
     * <code>
     * $query->filterBypacientModelId(1234); // WHERE pacientModel_id = 1234
     * $query->filterBypacientModelId(array(12, 34)); // WHERE pacientModel_id IN (12, 34)
     * $query->filterBypacientModelId(array('min' => 12)); // WHERE pacientModel_id >= 12
     * $query->filterBypacientModelId(array('max' => 12)); // WHERE pacientModel_id <= 12
     * </code>
     *
     * @see       filterByRbPacientModel()
     *
     * @param     mixed $pacientModelId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterBypacientModelId($pacientModelId = null, $comparison = null)
    {
        if (is_array($pacientModelId)) {
            $useMinMax = false;
            if (isset($pacientModelId['min'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::PACIENTMODEL_ID, $pacientModelId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($pacientModelId['max'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::PACIENTMODEL_ID, $pacientModelId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::PACIENTMODEL_ID, $pacientModelId, $comparison);
    }

    /**
     * Filter the query on the quotaType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByquotaTypeId(1234); // WHERE quotaType_id = 1234
     * $query->filterByquotaTypeId(array(12, 34)); // WHERE quotaType_id IN (12, 34)
     * $query->filterByquotaTypeId(array('min' => 12)); // WHERE quotaType_id >= 12
     * $query->filterByquotaTypeId(array('max' => 12)); // WHERE quotaType_id <= 12
     * </code>
     *
     * @see       filterByQuotaType()
     *
     * @param     mixed $quotaTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function filterByquotaTypeId($quotaTypeId = null, $comparison = null)
    {
        if (is_array($quotaTypeId)) {
            $useMinMax = false;
            if (isset($quotaTypeId['min'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::QUOTATYPE_ID, $quotaTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($quotaTypeId['max'])) {
                $this->addUsingAlias(MkbQuotaTypePacientModelPeer::QUOTATYPE_ID, $quotaTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbQuotaTypePacientModelPeer::QUOTATYPE_ID, $quotaTypeId, $comparison);
    }

    /**
     * Filter the query by a related QuotaType object
     *
     * @param   QuotaType|PropelObjectCollection $quotaType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 MkbQuotaTypePacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByQuotaType($quotaType, $comparison = null)
    {
        if ($quotaType instanceof QuotaType) {
            return $this
                ->addUsingAlias(MkbQuotaTypePacientModelPeer::QUOTATYPE_ID, $quotaType->getid(), $comparison);
        } elseif ($quotaType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(MkbQuotaTypePacientModelPeer::QUOTATYPE_ID, $quotaType->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByQuotaType() only accepts arguments of type QuotaType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the QuotaType relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function joinQuotaType($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('QuotaType');

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
            $this->addJoinObject($join, 'QuotaType');
        }

        return $this;
    }

    /**
     * Use the QuotaType relation QuotaType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\QuotaTypeQuery A secondary query class using the current class as primary query
     */
    public function useQuotaTypeQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinQuotaType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'QuotaType', '\Webmis\Models\QuotaTypeQuery');
    }

    /**
     * Filter the query by a related RbPacientModel object
     *
     * @param   RbPacientModel|PropelObjectCollection $rbPacientModel The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 MkbQuotaTypePacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbPacientModel($rbPacientModel, $comparison = null)
    {
        if ($rbPacientModel instanceof RbPacientModel) {
            return $this
                ->addUsingAlias(MkbQuotaTypePacientModelPeer::PACIENTMODEL_ID, $rbPacientModel->getid(), $comparison);
        } elseif ($rbPacientModel instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(MkbQuotaTypePacientModelPeer::PACIENTMODEL_ID, $rbPacientModel->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByRbPacientModel() only accepts arguments of type RbPacientModel or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RbPacientModel relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function joinRbPacientModel($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RbPacientModel');

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
            $this->addJoinObject($join, 'RbPacientModel');
        }

        return $this;
    }

    /**
     * Use the RbPacientModel relation RbPacientModel object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RbPacientModelQuery A secondary query class using the current class as primary query
     */
    public function useRbPacientModelQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinRbPacientModel($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RbPacientModel', '\Webmis\Models\RbPacientModelQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   MkbQuotaTypePacientModel $mkbQuotaTypePacientModel Object to remove from the list of results
     *
     * @return MkbQuotaTypePacientModelQuery The current query, for fluid interface
     */
    public function prune($mkbQuotaTypePacientModel = null)
    {
        if ($mkbQuotaTypePacientModel) {
            $this->addUsingAlias(MkbQuotaTypePacientModelPeer::ID, $mkbQuotaTypePacientModel->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
