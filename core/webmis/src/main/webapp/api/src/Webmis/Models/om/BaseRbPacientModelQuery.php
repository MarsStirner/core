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
use Webmis\Models\ClientQuoting;
use Webmis\Models\MkbQuotaTypePacientModel;
use Webmis\Models\QuotaType;
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbPacientModelPeer;
use Webmis\Models\RbPacientModelQuery;
use Webmis\Models\RbTreatment;

/**
 * Base class that represents a query for the 'rbPacientModel' table.
 *
 *
 *
 * @method RbPacientModelQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RbPacientModelQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method RbPacientModelQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method RbPacientModelQuery orderByquotaTypeId($order = Criteria::ASC) Order by the quotaType_id column
 *
 * @method RbPacientModelQuery groupByid() Group by the id column
 * @method RbPacientModelQuery groupBycode() Group by the code column
 * @method RbPacientModelQuery groupByname() Group by the name column
 * @method RbPacientModelQuery groupByquotaTypeId() Group by the quotaType_id column
 *
 * @method RbPacientModelQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RbPacientModelQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RbPacientModelQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RbPacientModelQuery leftJoinQuotaType($relationAlias = null) Adds a LEFT JOIN clause to the query using the QuotaType relation
 * @method RbPacientModelQuery rightJoinQuotaType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the QuotaType relation
 * @method RbPacientModelQuery innerJoinQuotaType($relationAlias = null) Adds a INNER JOIN clause to the query using the QuotaType relation
 *
 * @method RbPacientModelQuery leftJoinClientQuoting($relationAlias = null) Adds a LEFT JOIN clause to the query using the ClientQuoting relation
 * @method RbPacientModelQuery rightJoinClientQuoting($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ClientQuoting relation
 * @method RbPacientModelQuery innerJoinClientQuoting($relationAlias = null) Adds a INNER JOIN clause to the query using the ClientQuoting relation
 *
 * @method RbPacientModelQuery leftJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the MkbQuotaTypePacientModel relation
 * @method RbPacientModelQuery rightJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the MkbQuotaTypePacientModel relation
 * @method RbPacientModelQuery innerJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the MkbQuotaTypePacientModel relation
 *
 * @method RbPacientModelQuery leftJoinRbTreatment($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbTreatment relation
 * @method RbPacientModelQuery rightJoinRbTreatment($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbTreatment relation
 * @method RbPacientModelQuery innerJoinRbTreatment($relationAlias = null) Adds a INNER JOIN clause to the query using the RbTreatment relation
 *
 * @method RbPacientModel findOne(PropelPDO $con = null) Return the first RbPacientModel matching the query
 * @method RbPacientModel findOneOrCreate(PropelPDO $con = null) Return the first RbPacientModel matching the query, or a new RbPacientModel object populated from the query conditions when no match is found
 *
 * @method RbPacientModel findOneBycode(string $code) Return the first RbPacientModel filtered by the code column
 * @method RbPacientModel findOneByname(string $name) Return the first RbPacientModel filtered by the name column
 * @method RbPacientModel findOneByquotaTypeId(int $quotaType_id) Return the first RbPacientModel filtered by the quotaType_id column
 *
 * @method array findByid(int $id) Return RbPacientModel objects filtered by the id column
 * @method array findBycode(string $code) Return RbPacientModel objects filtered by the code column
 * @method array findByname(string $name) Return RbPacientModel objects filtered by the name column
 * @method array findByquotaTypeId(int $quotaType_id) Return RbPacientModel objects filtered by the quotaType_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbPacientModelQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRbPacientModelQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RbPacientModel', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RbPacientModelQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RbPacientModelQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RbPacientModelQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RbPacientModelQuery) {
            return $criteria;
        }
        $query = new RbPacientModelQuery();
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
     * @return   RbPacientModel|RbPacientModel[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RbPacientModelPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RbPacientModelPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RbPacientModel A model object, or null if the key is not found
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
     * @return                 RbPacientModel A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `code`, `name`, `quotaType_id` FROM `rbPacientModel` WHERE `id` = :p0';
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
            $obj = new RbPacientModel();
            $obj->hydrate($row);
            RbPacientModelPeer::addInstanceToPool($obj, (string) $key);
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
     * @return RbPacientModel|RbPacientModel[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RbPacientModel[]|mixed the list of results, formatted by the current formatter
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
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RbPacientModelPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RbPacientModelPeer::ID, $keys, Criteria::IN);
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
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RbPacientModelPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RbPacientModelPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbPacientModelPeer::ID, $id, $comparison);
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
     * @return RbPacientModelQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbPacientModelPeer::CODE, $code, $comparison);
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
     * @return RbPacientModelQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbPacientModelPeer::NAME, $name, $comparison);
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
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function filterByquotaTypeId($quotaTypeId = null, $comparison = null)
    {
        if (is_array($quotaTypeId)) {
            $useMinMax = false;
            if (isset($quotaTypeId['min'])) {
                $this->addUsingAlias(RbPacientModelPeer::QUOTATYPE_ID, $quotaTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($quotaTypeId['max'])) {
                $this->addUsingAlias(RbPacientModelPeer::QUOTATYPE_ID, $quotaTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbPacientModelPeer::QUOTATYPE_ID, $quotaTypeId, $comparison);
    }

    /**
     * Filter the query by a related QuotaType object
     *
     * @param   QuotaType|PropelObjectCollection $quotaType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbPacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByQuotaType($quotaType, $comparison = null)
    {
        if ($quotaType instanceof QuotaType) {
            return $this
                ->addUsingAlias(RbPacientModelPeer::QUOTATYPE_ID, $quotaType->getid(), $comparison);
        } elseif ($quotaType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(RbPacientModelPeer::QUOTATYPE_ID, $quotaType->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return RbPacientModelQuery The current query, for fluid interface
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
     * Filter the query by a related ClientQuoting object
     *
     * @param   ClientQuoting|PropelObjectCollection $clientQuoting  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbPacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByClientQuoting($clientQuoting, $comparison = null)
    {
        if ($clientQuoting instanceof ClientQuoting) {
            return $this
                ->addUsingAlias(RbPacientModelPeer::ID, $clientQuoting->getpacientModelId(), $comparison);
        } elseif ($clientQuoting instanceof PropelObjectCollection) {
            return $this
                ->useClientQuotingQuery()
                ->filterByPrimaryKeys($clientQuoting->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByClientQuoting() only accepts arguments of type ClientQuoting or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ClientQuoting relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function joinClientQuoting($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ClientQuoting');

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
            $this->addJoinObject($join, 'ClientQuoting');
        }

        return $this;
    }

    /**
     * Use the ClientQuoting relation ClientQuoting object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ClientQuotingQuery A secondary query class using the current class as primary query
     */
    public function useClientQuotingQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinClientQuoting($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ClientQuoting', '\Webmis\Models\ClientQuotingQuery');
    }

    /**
     * Filter the query by a related MkbQuotaTypePacientModel object
     *
     * @param   MkbQuotaTypePacientModel|PropelObjectCollection $mkbQuotaTypePacientModel  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbPacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByMkbQuotaTypePacientModel($mkbQuotaTypePacientModel, $comparison = null)
    {
        if ($mkbQuotaTypePacientModel instanceof MkbQuotaTypePacientModel) {
            return $this
                ->addUsingAlias(RbPacientModelPeer::ID, $mkbQuotaTypePacientModel->getpacientModelId(), $comparison);
        } elseif ($mkbQuotaTypePacientModel instanceof PropelObjectCollection) {
            return $this
                ->useMkbQuotaTypePacientModelQuery()
                ->filterByPrimaryKeys($mkbQuotaTypePacientModel->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByMkbQuotaTypePacientModel() only accepts arguments of type MkbQuotaTypePacientModel or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the MkbQuotaTypePacientModel relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function joinMkbQuotaTypePacientModel($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('MkbQuotaTypePacientModel');

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
            $this->addJoinObject($join, 'MkbQuotaTypePacientModel');
        }

        return $this;
    }

    /**
     * Use the MkbQuotaTypePacientModel relation MkbQuotaTypePacientModel object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\MkbQuotaTypePacientModelQuery A secondary query class using the current class as primary query
     */
    public function useMkbQuotaTypePacientModelQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinMkbQuotaTypePacientModel($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'MkbQuotaTypePacientModel', '\Webmis\Models\MkbQuotaTypePacientModelQuery');
    }

    /**
     * Filter the query by a related RbTreatment object
     *
     * @param   RbTreatment|PropelObjectCollection $rbTreatment  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbPacientModelQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbTreatment($rbTreatment, $comparison = null)
    {
        if ($rbTreatment instanceof RbTreatment) {
            return $this
                ->addUsingAlias(RbPacientModelPeer::ID, $rbTreatment->getPacientModelId(), $comparison);
        } elseif ($rbTreatment instanceof PropelObjectCollection) {
            return $this
                ->useRbTreatmentQuery()
                ->filterByPrimaryKeys($rbTreatment->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByRbTreatment() only accepts arguments of type RbTreatment or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RbTreatment relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function joinRbTreatment($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RbTreatment');

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
            $this->addJoinObject($join, 'RbTreatment');
        }

        return $this;
    }

    /**
     * Use the RbTreatment relation RbTreatment object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RbTreatmentQuery A secondary query class using the current class as primary query
     */
    public function useRbTreatmentQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinRbTreatment($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RbTreatment', '\Webmis\Models\RbTreatmentQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   RbPacientModel $rbPacientModel Object to remove from the list of results
     *
     * @return RbPacientModelQuery The current query, for fluid interface
     */
    public function prune($rbPacientModel = null)
    {
        if ($rbPacientModel) {
            $this->addUsingAlias(RbPacientModelPeer::ID, $rbPacientModel->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
