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
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbTreatment;
use Webmis\Models\RbTreatmentPeer;
use Webmis\Models\RbTreatmentQuery;

/**
 * Base class that represents a query for the 'rbTreatment' table.
 *
 *
 *
 * @method RbTreatmentQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RbTreatmentQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method RbTreatmentQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method RbTreatmentQuery orderByPacientModelId($order = Criteria::ASC) Order by the pacientModel_id column
 *
 * @method RbTreatmentQuery groupByid() Group by the id column
 * @method RbTreatmentQuery groupBycode() Group by the code column
 * @method RbTreatmentQuery groupByname() Group by the name column
 * @method RbTreatmentQuery groupByPacientModelId() Group by the pacientModel_id column
 *
 * @method RbTreatmentQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RbTreatmentQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RbTreatmentQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RbTreatmentQuery leftJoinRbPacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbPacientModel relation
 * @method RbTreatmentQuery rightJoinRbPacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbPacientModel relation
 * @method RbTreatmentQuery innerJoinRbPacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the RbPacientModel relation
 *
 * @method RbTreatmentQuery leftJoinClientQuoting($relationAlias = null) Adds a LEFT JOIN clause to the query using the ClientQuoting relation
 * @method RbTreatmentQuery rightJoinClientQuoting($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ClientQuoting relation
 * @method RbTreatmentQuery innerJoinClientQuoting($relationAlias = null) Adds a INNER JOIN clause to the query using the ClientQuoting relation
 *
 * @method RbTreatment findOne(PropelPDO $con = null) Return the first RbTreatment matching the query
 * @method RbTreatment findOneOrCreate(PropelPDO $con = null) Return the first RbTreatment matching the query, or a new RbTreatment object populated from the query conditions when no match is found
 *
 * @method RbTreatment findOneBycode(string $code) Return the first RbTreatment filtered by the code column
 * @method RbTreatment findOneByname(string $name) Return the first RbTreatment filtered by the name column
 * @method RbTreatment findOneByPacientModelId(int $pacientModel_id) Return the first RbTreatment filtered by the pacientModel_id column
 *
 * @method array findByid(int $id) Return RbTreatment objects filtered by the id column
 * @method array findBycode(string $code) Return RbTreatment objects filtered by the code column
 * @method array findByname(string $name) Return RbTreatment objects filtered by the name column
 * @method array findByPacientModelId(int $pacientModel_id) Return RbTreatment objects filtered by the pacientModel_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbTreatmentQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRbTreatmentQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RbTreatment', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RbTreatmentQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RbTreatmentQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RbTreatmentQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RbTreatmentQuery) {
            return $criteria;
        }
        $query = new RbTreatmentQuery();
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
     * @return   RbTreatment|RbTreatment[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RbTreatmentPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RbTreatmentPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RbTreatment A model object, or null if the key is not found
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
     * @return                 RbTreatment A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `code`, `name`, `pacientModel_id` FROM `rbTreatment` WHERE `id` = :p0';
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
            $obj = new RbTreatment();
            $obj->hydrate($row);
            RbTreatmentPeer::addInstanceToPool($obj, (string) $key);
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
     * @return RbTreatment|RbTreatment[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RbTreatment[]|mixed the list of results, formatted by the current formatter
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
     * @return RbTreatmentQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RbTreatmentPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RbTreatmentQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RbTreatmentPeer::ID, $keys, Criteria::IN);
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
     * @return RbTreatmentQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RbTreatmentPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RbTreatmentPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbTreatmentPeer::ID, $id, $comparison);
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
     * @return RbTreatmentQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbTreatmentPeer::CODE, $code, $comparison);
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
     * @return RbTreatmentQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbTreatmentPeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the pacientModel_id column
     *
     * Example usage:
     * <code>
     * $query->filterByPacientModelId(1234); // WHERE pacientModel_id = 1234
     * $query->filterByPacientModelId(array(12, 34)); // WHERE pacientModel_id IN (12, 34)
     * $query->filterByPacientModelId(array('min' => 12)); // WHERE pacientModel_id >= 12
     * $query->filterByPacientModelId(array('max' => 12)); // WHERE pacientModel_id <= 12
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
     * @return RbTreatmentQuery The current query, for fluid interface
     */
    public function filterByPacientModelId($pacientModelId = null, $comparison = null)
    {
        if (is_array($pacientModelId)) {
            $useMinMax = false;
            if (isset($pacientModelId['min'])) {
                $this->addUsingAlias(RbTreatmentPeer::PACIENTMODEL_ID, $pacientModelId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($pacientModelId['max'])) {
                $this->addUsingAlias(RbTreatmentPeer::PACIENTMODEL_ID, $pacientModelId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbTreatmentPeer::PACIENTMODEL_ID, $pacientModelId, $comparison);
    }

    /**
     * Filter the query by a related RbPacientModel object
     *
     * @param   RbPacientModel|PropelObjectCollection $rbPacientModel The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbTreatmentQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbPacientModel($rbPacientModel, $comparison = null)
    {
        if ($rbPacientModel instanceof RbPacientModel) {
            return $this
                ->addUsingAlias(RbTreatmentPeer::PACIENTMODEL_ID, $rbPacientModel->getid(), $comparison);
        } elseif ($rbPacientModel instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(RbTreatmentPeer::PACIENTMODEL_ID, $rbPacientModel->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return RbTreatmentQuery The current query, for fluid interface
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
     * Filter the query by a related ClientQuoting object
     *
     * @param   ClientQuoting|PropelObjectCollection $clientQuoting  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbTreatmentQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByClientQuoting($clientQuoting, $comparison = null)
    {
        if ($clientQuoting instanceof ClientQuoting) {
            return $this
                ->addUsingAlias(RbTreatmentPeer::ID, $clientQuoting->gettreatmentId(), $comparison);
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
     * @return RbTreatmentQuery The current query, for fluid interface
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
     * Exclude object from result
     *
     * @param   RbTreatment $rbTreatment Object to remove from the list of results
     *
     * @return RbTreatmentQuery The current query, for fluid interface
     */
    public function prune($rbTreatment = null)
    {
        if ($rbTreatment) {
            $this->addUsingAlias(RbTreatmentPeer::ID, $rbTreatment->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
