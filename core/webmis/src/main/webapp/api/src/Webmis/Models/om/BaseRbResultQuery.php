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
use Webmis\Models\EventType;
use Webmis\Models\RbResult;
use Webmis\Models\RbResultPeer;
use Webmis\Models\RbResultQuery;

/**
 * Base class that represents a query for the 'rbResult' table.
 *
 *
 *
 * @method RbResultQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RbResultQuery orderByeventPurposeId($order = Criteria::ASC) Order by the eventPurpose_id column
 * @method RbResultQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method RbResultQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method RbResultQuery orderBycontinued($order = Criteria::ASC) Order by the continued column
 * @method RbResultQuery orderByregionalCode($order = Criteria::ASC) Order by the regionalCode column
 *
 * @method RbResultQuery groupByid() Group by the id column
 * @method RbResultQuery groupByeventPurposeId() Group by the eventPurpose_id column
 * @method RbResultQuery groupBycode() Group by the code column
 * @method RbResultQuery groupByname() Group by the name column
 * @method RbResultQuery groupBycontinued() Group by the continued column
 * @method RbResultQuery groupByregionalCode() Group by the regionalCode column
 *
 * @method RbResultQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RbResultQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RbResultQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RbResultQuery leftJoinEventType($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventType relation
 * @method RbResultQuery rightJoinEventType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventType relation
 * @method RbResultQuery innerJoinEventType($relationAlias = null) Adds a INNER JOIN clause to the query using the EventType relation
 *
 * @method RbResult findOne(PropelPDO $con = null) Return the first RbResult matching the query
 * @method RbResult findOneOrCreate(PropelPDO $con = null) Return the first RbResult matching the query, or a new RbResult object populated from the query conditions when no match is found
 *
 * @method RbResult findOneByeventPurposeId(int $eventPurpose_id) Return the first RbResult filtered by the eventPurpose_id column
 * @method RbResult findOneBycode(string $code) Return the first RbResult filtered by the code column
 * @method RbResult findOneByname(string $name) Return the first RbResult filtered by the name column
 * @method RbResult findOneBycontinued(boolean $continued) Return the first RbResult filtered by the continued column
 * @method RbResult findOneByregionalCode(string $regionalCode) Return the first RbResult filtered by the regionalCode column
 *
 * @method array findByid(int $id) Return RbResult objects filtered by the id column
 * @method array findByeventPurposeId(int $eventPurpose_id) Return RbResult objects filtered by the eventPurpose_id column
 * @method array findBycode(string $code) Return RbResult objects filtered by the code column
 * @method array findByname(string $name) Return RbResult objects filtered by the name column
 * @method array findBycontinued(boolean $continued) Return RbResult objects filtered by the continued column
 * @method array findByregionalCode(string $regionalCode) Return RbResult objects filtered by the regionalCode column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbResultQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRbResultQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RbResult', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RbResultQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RbResultQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RbResultQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RbResultQuery) {
            return $criteria;
        }
        $query = new RbResultQuery();
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
     * @return   RbResult|RbResult[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RbResultPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RbResultPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RbResult A model object, or null if the key is not found
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
     * @return                 RbResult A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `eventPurpose_id`, `code`, `name`, `continued`, `regionalCode` FROM `rbResult` WHERE `id` = :p0';
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
            $obj = new RbResult();
            $obj->hydrate($row);
            RbResultPeer::addInstanceToPool($obj, (string) $key);
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
     * @return RbResult|RbResult[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RbResult[]|mixed the list of results, formatted by the current formatter
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
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RbResultPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RbResultPeer::ID, $keys, Criteria::IN);
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
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RbResultPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RbResultPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbResultPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the eventPurpose_id column
     *
     * Example usage:
     * <code>
     * $query->filterByeventPurposeId(1234); // WHERE eventPurpose_id = 1234
     * $query->filterByeventPurposeId(array(12, 34)); // WHERE eventPurpose_id IN (12, 34)
     * $query->filterByeventPurposeId(array('min' => 12)); // WHERE eventPurpose_id >= 12
     * $query->filterByeventPurposeId(array('max' => 12)); // WHERE eventPurpose_id <= 12
     * </code>
     *
     * @param     mixed $eventPurposeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterByeventPurposeId($eventPurposeId = null, $comparison = null)
    {
        if (is_array($eventPurposeId)) {
            $useMinMax = false;
            if (isset($eventPurposeId['min'])) {
                $this->addUsingAlias(RbResultPeer::EVENTPURPOSE_ID, $eventPurposeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($eventPurposeId['max'])) {
                $this->addUsingAlias(RbResultPeer::EVENTPURPOSE_ID, $eventPurposeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbResultPeer::EVENTPURPOSE_ID, $eventPurposeId, $comparison);
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
     * @return RbResultQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbResultPeer::CODE, $code, $comparison);
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
     * @return RbResultQuery The current query, for fluid interface
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

        return $this->addUsingAlias(RbResultPeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the continued column
     *
     * Example usage:
     * <code>
     * $query->filterBycontinued(true); // WHERE continued = true
     * $query->filterBycontinued('yes'); // WHERE continued = true
     * </code>
     *
     * @param     boolean|string $continued The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterBycontinued($continued = null, $comparison = null)
    {
        if (is_string($continued)) {
            $continued = in_array(strtolower($continued), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(RbResultPeer::CONTINUED, $continued, $comparison);
    }

    /**
     * Filter the query on the regionalCode column
     *
     * Example usage:
     * <code>
     * $query->filterByregionalCode('fooValue');   // WHERE regionalCode = 'fooValue'
     * $query->filterByregionalCode('%fooValue%'); // WHERE regionalCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $regionalCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function filterByregionalCode($regionalCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($regionalCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $regionalCode)) {
                $regionalCode = str_replace('*', '%', $regionalCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(RbResultPeer::REGIONALCODE, $regionalCode, $comparison);
    }

    /**
     * Filter the query by a related EventType object
     *
     * @param   EventType|PropelObjectCollection $eventType  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 RbResultQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventType($eventType, $comparison = null)
    {
        if ($eventType instanceof EventType) {
            return $this
                ->addUsingAlias(RbResultPeer::EVENTPURPOSE_ID, $eventType->getpurposeId(), $comparison);
        } elseif ($eventType instanceof PropelObjectCollection) {
            return $this
                ->useEventTypeQuery()
                ->filterByPrimaryKeys($eventType->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEventType() only accepts arguments of type EventType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the EventType relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function joinEventType($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('EventType');

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
            $this->addJoinObject($join, 'EventType');
        }

        return $this;
    }

    /**
     * Use the EventType relation EventType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventTypeQuery A secondary query class using the current class as primary query
     */
    public function useEventTypeQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEventType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventType', '\Webmis\Models\EventTypeQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   RbResult $rbResult Object to remove from the list of results
     *
     * @return RbResultQuery The current query, for fluid interface
     */
    public function prune($rbResult = null)
    {
        if ($rbResult) {
            $this->addUsingAlias(RbResultPeer::ID, $rbResult->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
