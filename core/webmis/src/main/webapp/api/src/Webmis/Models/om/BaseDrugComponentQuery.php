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
use Webmis\Models\Action;
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentPeer;
use Webmis\Models\DrugComponentQuery;
use Webmis\Models\rbUnit;
use Webmis\Models\rlsNomen;

/**
 * Base class that represents a query for the 'DrugComponent' table.
 *
 *
 *
 * @method DrugComponentQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method DrugComponentQuery orderByactionId($order = Criteria::ASC) Order by the action_id column
 * @method DrugComponentQuery orderBynomen($order = Criteria::ASC) Order by the nomen column
 * @method DrugComponentQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method DrugComponentQuery orderBydose($order = Criteria::ASC) Order by the dose column
 * @method DrugComponentQuery orderByunit($order = Criteria::ASC) Order by the unit column
 * @method DrugComponentQuery orderBycreateDateTime($order = Criteria::ASC) Order by the createDateTime column
 * @method DrugComponentQuery orderBycancelDateTime($order = Criteria::ASC) Order by the cancelDateTime column
 *
 * @method DrugComponentQuery groupByid() Group by the id column
 * @method DrugComponentQuery groupByactionId() Group by the action_id column
 * @method DrugComponentQuery groupBynomen() Group by the nomen column
 * @method DrugComponentQuery groupByname() Group by the name column
 * @method DrugComponentQuery groupBydose() Group by the dose column
 * @method DrugComponentQuery groupByunit() Group by the unit column
 * @method DrugComponentQuery groupBycreateDateTime() Group by the createDateTime column
 * @method DrugComponentQuery groupBycancelDateTime() Group by the cancelDateTime column
 *
 * @method DrugComponentQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method DrugComponentQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method DrugComponentQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method DrugComponentQuery leftJoinAction($relationAlias = null) Adds a LEFT JOIN clause to the query using the Action relation
 * @method DrugComponentQuery rightJoinAction($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Action relation
 * @method DrugComponentQuery innerJoinAction($relationAlias = null) Adds a INNER JOIN clause to the query using the Action relation
 *
 * @method DrugComponentQuery leftJoinrbUnit($relationAlias = null) Adds a LEFT JOIN clause to the query using the rbUnit relation
 * @method DrugComponentQuery rightJoinrbUnit($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rbUnit relation
 * @method DrugComponentQuery innerJoinrbUnit($relationAlias = null) Adds a INNER JOIN clause to the query using the rbUnit relation
 *
 * @method DrugComponentQuery leftJoinrlsNomen($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsNomen relation
 * @method DrugComponentQuery rightJoinrlsNomen($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsNomen relation
 * @method DrugComponentQuery innerJoinrlsNomen($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsNomen relation
 *
 * @method DrugComponent findOne(PropelPDO $con = null) Return the first DrugComponent matching the query
 * @method DrugComponent findOneOrCreate(PropelPDO $con = null) Return the first DrugComponent matching the query, or a new DrugComponent object populated from the query conditions when no match is found
 *
 * @method DrugComponent findOneByactionId(int $action_id) Return the first DrugComponent filtered by the action_id column
 * @method DrugComponent findOneBynomen(int $nomen) Return the first DrugComponent filtered by the nomen column
 * @method DrugComponent findOneByname(string $name) Return the first DrugComponent filtered by the name column
 * @method DrugComponent findOneBydose(double $dose) Return the first DrugComponent filtered by the dose column
 * @method DrugComponent findOneByunit(int $unit) Return the first DrugComponent filtered by the unit column
 * @method DrugComponent findOneBycreateDateTime(string $createDateTime) Return the first DrugComponent filtered by the createDateTime column
 * @method DrugComponent findOneBycancelDateTime(string $cancelDateTime) Return the first DrugComponent filtered by the cancelDateTime column
 *
 * @method array findByid(int $id) Return DrugComponent objects filtered by the id column
 * @method array findByactionId(int $action_id) Return DrugComponent objects filtered by the action_id column
 * @method array findBynomen(int $nomen) Return DrugComponent objects filtered by the nomen column
 * @method array findByname(string $name) Return DrugComponent objects filtered by the name column
 * @method array findBydose(double $dose) Return DrugComponent objects filtered by the dose column
 * @method array findByunit(int $unit) Return DrugComponent objects filtered by the unit column
 * @method array findBycreateDateTime(string $createDateTime) Return DrugComponent objects filtered by the createDateTime column
 * @method array findBycancelDateTime(string $cancelDateTime) Return DrugComponent objects filtered by the cancelDateTime column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseDrugComponentQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseDrugComponentQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\DrugComponent', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new DrugComponentQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   DrugComponentQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return DrugComponentQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof DrugComponentQuery) {
            return $criteria;
        }
        $query = new DrugComponentQuery();
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
     * @return   DrugComponent|DrugComponent[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = DrugComponentPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(DrugComponentPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 DrugComponent A model object, or null if the key is not found
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
     * @return                 DrugComponent A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `action_id`, `nomen`, `name`, `dose`, `unit`, `createDateTime`, `cancelDateTime` FROM `DrugComponent` WHERE `id` = :p0';
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
            $obj = new DrugComponent();
            $obj->hydrate($row);
            DrugComponentPeer::addInstanceToPool($obj, (string) $key);
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
     * @return DrugComponent|DrugComponent[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|DrugComponent[]|mixed the list of results, formatted by the current formatter
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
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(DrugComponentPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(DrugComponentPeer::ID, $keys, Criteria::IN);
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
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(DrugComponentPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(DrugComponentPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the action_id column
     *
     * Example usage:
     * <code>
     * $query->filterByactionId(1234); // WHERE action_id = 1234
     * $query->filterByactionId(array(12, 34)); // WHERE action_id IN (12, 34)
     * $query->filterByactionId(array('min' => 12)); // WHERE action_id >= 12
     * $query->filterByactionId(array('max' => 12)); // WHERE action_id <= 12
     * </code>
     *
     * @see       filterByAction()
     *
     * @param     mixed $actionId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterByactionId($actionId = null, $comparison = null)
    {
        if (is_array($actionId)) {
            $useMinMax = false;
            if (isset($actionId['min'])) {
                $this->addUsingAlias(DrugComponentPeer::ACTION_ID, $actionId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actionId['max'])) {
                $this->addUsingAlias(DrugComponentPeer::ACTION_ID, $actionId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::ACTION_ID, $actionId, $comparison);
    }

    /**
     * Filter the query on the nomen column
     *
     * Example usage:
     * <code>
     * $query->filterBynomen(1234); // WHERE nomen = 1234
     * $query->filterBynomen(array(12, 34)); // WHERE nomen IN (12, 34)
     * $query->filterBynomen(array('min' => 12)); // WHERE nomen >= 12
     * $query->filterBynomen(array('max' => 12)); // WHERE nomen <= 12
     * </code>
     *
     * @see       filterByrlsNomen()
     *
     * @param     mixed $nomen The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterBynomen($nomen = null, $comparison = null)
    {
        if (is_array($nomen)) {
            $useMinMax = false;
            if (isset($nomen['min'])) {
                $this->addUsingAlias(DrugComponentPeer::NOMEN, $nomen['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($nomen['max'])) {
                $this->addUsingAlias(DrugComponentPeer::NOMEN, $nomen['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::NOMEN, $nomen, $comparison);
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
     * @return DrugComponentQuery The current query, for fluid interface
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

        return $this->addUsingAlias(DrugComponentPeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the dose column
     *
     * Example usage:
     * <code>
     * $query->filterBydose(1234); // WHERE dose = 1234
     * $query->filterBydose(array(12, 34)); // WHERE dose IN (12, 34)
     * $query->filterBydose(array('min' => 12)); // WHERE dose >= 12
     * $query->filterBydose(array('max' => 12)); // WHERE dose <= 12
     * </code>
     *
     * @param     mixed $dose The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterBydose($dose = null, $comparison = null)
    {
        if (is_array($dose)) {
            $useMinMax = false;
            if (isset($dose['min'])) {
                $this->addUsingAlias(DrugComponentPeer::DOSE, $dose['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dose['max'])) {
                $this->addUsingAlias(DrugComponentPeer::DOSE, $dose['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::DOSE, $dose, $comparison);
    }

    /**
     * Filter the query on the unit column
     *
     * Example usage:
     * <code>
     * $query->filterByunit(1234); // WHERE unit = 1234
     * $query->filterByunit(array(12, 34)); // WHERE unit IN (12, 34)
     * $query->filterByunit(array('min' => 12)); // WHERE unit >= 12
     * $query->filterByunit(array('max' => 12)); // WHERE unit <= 12
     * </code>
     *
     * @see       filterByrbUnit()
     *
     * @param     mixed $unit The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterByunit($unit = null, $comparison = null)
    {
        if (is_array($unit)) {
            $useMinMax = false;
            if (isset($unit['min'])) {
                $this->addUsingAlias(DrugComponentPeer::UNIT, $unit['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($unit['max'])) {
                $this->addUsingAlias(DrugComponentPeer::UNIT, $unit['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::UNIT, $unit, $comparison);
    }

    /**
     * Filter the query on the createDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterBycreateDateTime('2011-03-14'); // WHERE createDateTime = '2011-03-14'
     * $query->filterBycreateDateTime('now'); // WHERE createDateTime = '2011-03-14'
     * $query->filterBycreateDateTime(array('max' => 'yesterday')); // WHERE createDateTime > '2011-03-13'
     * </code>
     *
     * @param     mixed $createDateTime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterBycreateDateTime($createDateTime = null, $comparison = null)
    {
        if (is_array($createDateTime)) {
            $useMinMax = false;
            if (isset($createDateTime['min'])) {
                $this->addUsingAlias(DrugComponentPeer::CREATEDATETIME, $createDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDateTime['max'])) {
                $this->addUsingAlias(DrugComponentPeer::CREATEDATETIME, $createDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::CREATEDATETIME, $createDateTime, $comparison);
    }

    /**
     * Filter the query on the cancelDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterBycancelDateTime('2011-03-14'); // WHERE cancelDateTime = '2011-03-14'
     * $query->filterBycancelDateTime('now'); // WHERE cancelDateTime = '2011-03-14'
     * $query->filterBycancelDateTime(array('max' => 'yesterday')); // WHERE cancelDateTime > '2011-03-13'
     * </code>
     *
     * @param     mixed $cancelDateTime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function filterBycancelDateTime($cancelDateTime = null, $comparison = null)
    {
        if (is_array($cancelDateTime)) {
            $useMinMax = false;
            if (isset($cancelDateTime['min'])) {
                $this->addUsingAlias(DrugComponentPeer::CANCELDATETIME, $cancelDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($cancelDateTime['max'])) {
                $this->addUsingAlias(DrugComponentPeer::CANCELDATETIME, $cancelDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugComponentPeer::CANCELDATETIME, $cancelDateTime, $comparison);
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugComponentQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByAction($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(DrugComponentPeer::ACTION_ID, $action->getid(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(DrugComponentPeer::ACTION_ID, $action->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByAction() only accepts arguments of type Action or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Action relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function joinAction($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Action');

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
            $this->addJoinObject($join, 'Action');
        }

        return $this;
    }

    /**
     * Use the Action relation Action object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionQuery A secondary query class using the current class as primary query
     */
    public function useActionQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinAction($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Action', '\Webmis\Models\ActionQuery');
    }

    /**
     * Filter the query by a related rbUnit object
     *
     * @param   rbUnit|PropelObjectCollection $rbUnit The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugComponentQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrbUnit($rbUnit, $comparison = null)
    {
        if ($rbUnit instanceof rbUnit) {
            return $this
                ->addUsingAlias(DrugComponentPeer::UNIT, $rbUnit->getid(), $comparison);
        } elseif ($rbUnit instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(DrugComponentPeer::UNIT, $rbUnit->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrbUnit() only accepts arguments of type rbUnit or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rbUnit relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function joinrbUnit($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rbUnit');

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
            $this->addJoinObject($join, 'rbUnit');
        }

        return $this;
    }

    /**
     * Use the rbUnit relation rbUnit object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rbUnitQuery A secondary query class using the current class as primary query
     */
    public function userbUnitQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrbUnit($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rbUnit', '\Webmis\Models\rbUnitQuery');
    }

    /**
     * Filter the query by a related rlsNomen object
     *
     * @param   rlsNomen|PropelObjectCollection $rlsNomen The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugComponentQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsNomen($rlsNomen, $comparison = null)
    {
        if ($rlsNomen instanceof rlsNomen) {
            return $this
                ->addUsingAlias(DrugComponentPeer::NOMEN, $rlsNomen->getid(), $comparison);
        } elseif ($rlsNomen instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(DrugComponentPeer::NOMEN, $rlsNomen->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function joinrlsNomen($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
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
    public function userlsNomenQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsNomen($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsNomen', '\Webmis\Models\rlsNomenQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   DrugComponent $drugComponent Object to remove from the list of results
     *
     * @return DrugComponentQuery The current query, for fluid interface
     */
    public function prune($drugComponent = null)
    {
        if ($drugComponent) {
            $this->addUsingAlias(DrugComponentPeer::ID, $drugComponent->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     DrugComponentQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(DrugComponentPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     DrugComponentQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(DrugComponentPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     DrugComponentQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(DrugComponentPeer::CREATEDATETIME);
    }
}
