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
use Webmis\Models\DrugComponent;
use Webmis\Models\rbUnit;
use Webmis\Models\rbUnitPeer;
use Webmis\Models\rbUnitQuery;
use Webmis\Models\rlsNomen;

/**
 * Base class that represents a query for the 'rbUnit' table.
 *
 *
 *
 * @method rbUnitQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method rbUnitQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method rbUnitQuery orderByname($order = Criteria::ASC) Order by the name column
 *
 * @method rbUnitQuery groupByid() Group by the id column
 * @method rbUnitQuery groupBycode() Group by the code column
 * @method rbUnitQuery groupByname() Group by the name column
 *
 * @method rbUnitQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method rbUnitQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method rbUnitQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method rbUnitQuery leftJoinDrugComponent($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugComponent relation
 * @method rbUnitQuery rightJoinDrugComponent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugComponent relation
 * @method rbUnitQuery innerJoinDrugComponent($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugComponent relation
 *
 * @method rbUnitQuery leftJoinrlsNomenRelatedByunitId($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsNomenRelatedByunitId relation
 * @method rbUnitQuery rightJoinrlsNomenRelatedByunitId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsNomenRelatedByunitId relation
 * @method rbUnitQuery innerJoinrlsNomenRelatedByunitId($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsNomenRelatedByunitId relation
 *
 * @method rbUnitQuery leftJoinrlsNomenRelatedBydosageUnitId($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsNomenRelatedBydosageUnitId relation
 * @method rbUnitQuery rightJoinrlsNomenRelatedBydosageUnitId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsNomenRelatedBydosageUnitId relation
 * @method rbUnitQuery innerJoinrlsNomenRelatedBydosageUnitId($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsNomenRelatedBydosageUnitId relation
 *
 * @method rbUnit findOne(PropelPDO $con = null) Return the first rbUnit matching the query
 * @method rbUnit findOneOrCreate(PropelPDO $con = null) Return the first rbUnit matching the query, or a new rbUnit object populated from the query conditions when no match is found
 *
 * @method rbUnit findOneBycode(string $code) Return the first rbUnit filtered by the code column
 * @method rbUnit findOneByname(string $name) Return the first rbUnit filtered by the name column
 *
 * @method array findByid(int $id) Return rbUnit objects filtered by the id column
 * @method array findBycode(string $code) Return rbUnit objects filtered by the code column
 * @method array findByname(string $name) Return rbUnit objects filtered by the name column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaserbUnitQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaserbUnitQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\rbUnit', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new rbUnitQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   rbUnitQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return rbUnitQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof rbUnitQuery) {
            return $criteria;
        }
        $query = new rbUnitQuery();
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
     * @return   rbUnit|rbUnit[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = rbUnitPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(rbUnitPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 rbUnit A model object, or null if the key is not found
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
     * @return                 rbUnit A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `code`, `name` FROM `rbUnit` WHERE `id` = :p0';
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
            $obj = new rbUnit();
            $obj->hydrate($row);
            rbUnitPeer::addInstanceToPool($obj, (string) $key);
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
     * @return rbUnit|rbUnit[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|rbUnit[]|mixed the list of results, formatted by the current formatter
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
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(rbUnitPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(rbUnitPeer::ID, $keys, Criteria::IN);
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
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(rbUnitPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(rbUnitPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rbUnitPeer::ID, $id, $comparison);
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
     * @return rbUnitQuery The current query, for fluid interface
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

        return $this->addUsingAlias(rbUnitPeer::CODE, $code, $comparison);
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
     * @return rbUnitQuery The current query, for fluid interface
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

        return $this->addUsingAlias(rbUnitPeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query by a related DrugComponent object
     *
     * @param   DrugComponent|PropelObjectCollection $drugComponent  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rbUnitQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugComponent($drugComponent, $comparison = null)
    {
        if ($drugComponent instanceof DrugComponent) {
            return $this
                ->addUsingAlias(rbUnitPeer::ID, $drugComponent->getunit(), $comparison);
        } elseif ($drugComponent instanceof PropelObjectCollection) {
            return $this
                ->useDrugComponentQuery()
                ->filterByPrimaryKeys($drugComponent->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByDrugComponent() only accepts arguments of type DrugComponent or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the DrugComponent relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function joinDrugComponent($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('DrugComponent');

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
            $this->addJoinObject($join, 'DrugComponent');
        }

        return $this;
    }

    /**
     * Use the DrugComponent relation DrugComponent object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\DrugComponentQuery A secondary query class using the current class as primary query
     */
    public function useDrugComponentQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinDrugComponent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugComponent', '\Webmis\Models\DrugComponentQuery');
    }

    /**
     * Filter the query by a related rlsNomen object
     *
     * @param   rlsNomen|PropelObjectCollection $rlsNomen  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rbUnitQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsNomenRelatedByunitId($rlsNomen, $comparison = null)
    {
        if ($rlsNomen instanceof rlsNomen) {
            return $this
                ->addUsingAlias(rbUnitPeer::ID, $rlsNomen->getunitId(), $comparison);
        } elseif ($rlsNomen instanceof PropelObjectCollection) {
            return $this
                ->userlsNomenRelatedByunitIdQuery()
                ->filterByPrimaryKeys($rlsNomen->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByrlsNomenRelatedByunitId() only accepts arguments of type rlsNomen or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsNomenRelatedByunitId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function joinrlsNomenRelatedByunitId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsNomenRelatedByunitId');

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
            $this->addJoinObject($join, 'rlsNomenRelatedByunitId');
        }

        return $this;
    }

    /**
     * Use the rlsNomenRelatedByunitId relation rlsNomen object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsNomenQuery A secondary query class using the current class as primary query
     */
    public function userlsNomenRelatedByunitIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsNomenRelatedByunitId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsNomenRelatedByunitId', '\Webmis\Models\rlsNomenQuery');
    }

    /**
     * Filter the query by a related rlsNomen object
     *
     * @param   rlsNomen|PropelObjectCollection $rlsNomen  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rbUnitQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsNomenRelatedBydosageUnitId($rlsNomen, $comparison = null)
    {
        if ($rlsNomen instanceof rlsNomen) {
            return $this
                ->addUsingAlias(rbUnitPeer::ID, $rlsNomen->getdosageUnitId(), $comparison);
        } elseif ($rlsNomen instanceof PropelObjectCollection) {
            return $this
                ->userlsNomenRelatedBydosageUnitIdQuery()
                ->filterByPrimaryKeys($rlsNomen->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByrlsNomenRelatedBydosageUnitId() only accepts arguments of type rlsNomen or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsNomenRelatedBydosageUnitId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function joinrlsNomenRelatedBydosageUnitId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsNomenRelatedBydosageUnitId');

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
            $this->addJoinObject($join, 'rlsNomenRelatedBydosageUnitId');
        }

        return $this;
    }

    /**
     * Use the rlsNomenRelatedBydosageUnitId relation rlsNomen object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsNomenQuery A secondary query class using the current class as primary query
     */
    public function userlsNomenRelatedBydosageUnitIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsNomenRelatedBydosageUnitId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsNomenRelatedBydosageUnitId', '\Webmis\Models\rlsNomenQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   rbUnit $rbUnit Object to remove from the list of results
     *
     * @return rbUnitQuery The current query, for fluid interface
     */
    public function prune($rbUnit = null)
    {
        if ($rbUnit) {
            $this->addUsingAlias(rbUnitPeer::ID, $rbUnit->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
