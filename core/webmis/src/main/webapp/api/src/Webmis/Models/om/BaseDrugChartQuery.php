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
use Webmis\Models\DrugChart;
use Webmis\Models\DrugChartPeer;
use Webmis\Models\DrugChartQuery;

/**
 * Base class that represents a query for the 'DrugChart' table.
 *
 *
 *
 * @method DrugChartQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method DrugChartQuery orderByactionId($order = Criteria::ASC) Order by the action_id column
 * @method DrugChartQuery orderBymasterId($order = Criteria::ASC) Order by the master_id column
 * @method DrugChartQuery orderBybegDateTime($order = Criteria::ASC) Order by the begDateTime column
 * @method DrugChartQuery orderByendDateTime($order = Criteria::ASC) Order by the endDateTime column
 * @method DrugChartQuery orderBystatus($order = Criteria::ASC) Order by the status column
 * @method DrugChartQuery orderBystatusDateTime($order = Criteria::ASC) Order by the statusDateTime column
 * @method DrugChartQuery orderBynote($order = Criteria::ASC) Order by the note column
 * @method DrugChartQuery orderByuuid($order = Criteria::ASC) Order by the uuid column
 * @method DrugChartQuery orderByversion($order = Criteria::ASC) Order by the version column
 *
 * @method DrugChartQuery groupByid() Group by the id column
 * @method DrugChartQuery groupByactionId() Group by the action_id column
 * @method DrugChartQuery groupBymasterId() Group by the master_id column
 * @method DrugChartQuery groupBybegDateTime() Group by the begDateTime column
 * @method DrugChartQuery groupByendDateTime() Group by the endDateTime column
 * @method DrugChartQuery groupBystatus() Group by the status column
 * @method DrugChartQuery groupBystatusDateTime() Group by the statusDateTime column
 * @method DrugChartQuery groupBynote() Group by the note column
 * @method DrugChartQuery groupByuuid() Group by the uuid column
 * @method DrugChartQuery groupByversion() Group by the version column
 *
 * @method DrugChartQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method DrugChartQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method DrugChartQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method DrugChartQuery leftJoinAction($relationAlias = null) Adds a LEFT JOIN clause to the query using the Action relation
 * @method DrugChartQuery rightJoinAction($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Action relation
 * @method DrugChartQuery innerJoinAction($relationAlias = null) Adds a INNER JOIN clause to the query using the Action relation
 *
 * @method DrugChartQuery leftJoinDrugChartRelatedBymasterId($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugChartRelatedBymasterId relation
 * @method DrugChartQuery rightJoinDrugChartRelatedBymasterId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugChartRelatedBymasterId relation
 * @method DrugChartQuery innerJoinDrugChartRelatedBymasterId($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugChartRelatedBymasterId relation
 *
 * @method DrugChartQuery leftJoinDrugChartRelatedByid($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugChartRelatedByid relation
 * @method DrugChartQuery rightJoinDrugChartRelatedByid($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugChartRelatedByid relation
 * @method DrugChartQuery innerJoinDrugChartRelatedByid($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugChartRelatedByid relation
 *
 * @method DrugChart findOne(PropelPDO $con = null) Return the first DrugChart matching the query
 * @method DrugChart findOneOrCreate(PropelPDO $con = null) Return the first DrugChart matching the query, or a new DrugChart object populated from the query conditions when no match is found
 *
 * @method DrugChart findOneByactionId(int $action_id) Return the first DrugChart filtered by the action_id column
 * @method DrugChart findOneBymasterId(int $master_id) Return the first DrugChart filtered by the master_id column
 * @method DrugChart findOneBybegDateTime(string $begDateTime) Return the first DrugChart filtered by the begDateTime column
 * @method DrugChart findOneByendDateTime(string $endDateTime) Return the first DrugChart filtered by the endDateTime column
 * @method DrugChart findOneBystatus(int $status) Return the first DrugChart filtered by the status column
 * @method DrugChart findOneBystatusDateTime(int $statusDateTime) Return the first DrugChart filtered by the statusDateTime column
 * @method DrugChart findOneBynote(string $note) Return the first DrugChart filtered by the note column
 * @method DrugChart findOneByuuid(string $uuid) Return the first DrugChart filtered by the uuid column
 * @method DrugChart findOneByversion(int $version) Return the first DrugChart filtered by the version column
 *
 * @method array findByid(int $id) Return DrugChart objects filtered by the id column
 * @method array findByactionId(int $action_id) Return DrugChart objects filtered by the action_id column
 * @method array findBymasterId(int $master_id) Return DrugChart objects filtered by the master_id column
 * @method array findBybegDateTime(string $begDateTime) Return DrugChart objects filtered by the begDateTime column
 * @method array findByendDateTime(string $endDateTime) Return DrugChart objects filtered by the endDateTime column
 * @method array findBystatus(int $status) Return DrugChart objects filtered by the status column
 * @method array findBystatusDateTime(int $statusDateTime) Return DrugChart objects filtered by the statusDateTime column
 * @method array findBynote(string $note) Return DrugChart objects filtered by the note column
 * @method array findByuuid(string $uuid) Return DrugChart objects filtered by the uuid column
 * @method array findByversion(int $version) Return DrugChart objects filtered by the version column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseDrugChartQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseDrugChartQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\DrugChart', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new DrugChartQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   DrugChartQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return DrugChartQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof DrugChartQuery) {
            return $criteria;
        }
        $query = new DrugChartQuery();
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
     * @return   DrugChart|DrugChart[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = DrugChartPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(DrugChartPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 DrugChart A model object, or null if the key is not found
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
     * @return                 DrugChart A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version` FROM `DrugChart` WHERE `id` = :p0';
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
            $obj = new DrugChart();
            $obj->hydrate($row);
            DrugChartPeer::addInstanceToPool($obj, (string) $key);
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
     * @return DrugChart|DrugChart[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|DrugChart[]|mixed the list of results, formatted by the current formatter
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
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(DrugChartPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(DrugChartPeer::ID, $keys, Criteria::IN);
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
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(DrugChartPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(DrugChartPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::ID, $id, $comparison);
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
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByactionId($actionId = null, $comparison = null)
    {
        if (is_array($actionId)) {
            $useMinMax = false;
            if (isset($actionId['min'])) {
                $this->addUsingAlias(DrugChartPeer::ACTION_ID, $actionId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actionId['max'])) {
                $this->addUsingAlias(DrugChartPeer::ACTION_ID, $actionId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::ACTION_ID, $actionId, $comparison);
    }

    /**
     * Filter the query on the master_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymasterId(1234); // WHERE master_id = 1234
     * $query->filterBymasterId(array(12, 34)); // WHERE master_id IN (12, 34)
     * $query->filterBymasterId(array('min' => 12)); // WHERE master_id >= 12
     * $query->filterBymasterId(array('max' => 12)); // WHERE master_id <= 12
     * </code>
     *
     * @see       filterByDrugChartRelatedBymasterId()
     *
     * @param     mixed $masterId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterBymasterId($masterId = null, $comparison = null)
    {
        if (is_array($masterId)) {
            $useMinMax = false;
            if (isset($masterId['min'])) {
                $this->addUsingAlias(DrugChartPeer::MASTER_ID, $masterId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($masterId['max'])) {
                $this->addUsingAlias(DrugChartPeer::MASTER_ID, $masterId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::MASTER_ID, $masterId, $comparison);
    }

    /**
     * Filter the query on the begDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterBybegDateTime('2011-03-14'); // WHERE begDateTime = '2011-03-14'
     * $query->filterBybegDateTime('now'); // WHERE begDateTime = '2011-03-14'
     * $query->filterBybegDateTime(array('max' => 'yesterday')); // WHERE begDateTime > '2011-03-13'
     * </code>
     *
     * @param     mixed $begDateTime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterBybegDateTime($begDateTime = null, $comparison = null)
    {
        if (is_array($begDateTime)) {
            $useMinMax = false;
            if (isset($begDateTime['min'])) {
                $this->addUsingAlias(DrugChartPeer::BEGDATETIME, $begDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($begDateTime['max'])) {
                $this->addUsingAlias(DrugChartPeer::BEGDATETIME, $begDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::BEGDATETIME, $begDateTime, $comparison);
    }

    /**
     * Filter the query on the endDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterByendDateTime('2011-03-14'); // WHERE endDateTime = '2011-03-14'
     * $query->filterByendDateTime('now'); // WHERE endDateTime = '2011-03-14'
     * $query->filterByendDateTime(array('max' => 'yesterday')); // WHERE endDateTime > '2011-03-13'
     * </code>
     *
     * @param     mixed $endDateTime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByendDateTime($endDateTime = null, $comparison = null)
    {
        if (is_array($endDateTime)) {
            $useMinMax = false;
            if (isset($endDateTime['min'])) {
                $this->addUsingAlias(DrugChartPeer::ENDDATETIME, $endDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($endDateTime['max'])) {
                $this->addUsingAlias(DrugChartPeer::ENDDATETIME, $endDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::ENDDATETIME, $endDateTime, $comparison);
    }

    /**
     * Filter the query on the status column
     *
     * Example usage:
     * <code>
     * $query->filterBystatus(1234); // WHERE status = 1234
     * $query->filterBystatus(array(12, 34)); // WHERE status IN (12, 34)
     * $query->filterBystatus(array('min' => 12)); // WHERE status >= 12
     * $query->filterBystatus(array('max' => 12)); // WHERE status <= 12
     * </code>
     *
     * @param     mixed $status The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterBystatus($status = null, $comparison = null)
    {
        if (is_array($status)) {
            $useMinMax = false;
            if (isset($status['min'])) {
                $this->addUsingAlias(DrugChartPeer::STATUS, $status['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($status['max'])) {
                $this->addUsingAlias(DrugChartPeer::STATUS, $status['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::STATUS, $status, $comparison);
    }

    /**
     * Filter the query on the statusDateTime column
     *
     * Example usage:
     * <code>
     * $query->filterBystatusDateTime(1234); // WHERE statusDateTime = 1234
     * $query->filterBystatusDateTime(array(12, 34)); // WHERE statusDateTime IN (12, 34)
     * $query->filterBystatusDateTime(array('min' => 12)); // WHERE statusDateTime >= 12
     * $query->filterBystatusDateTime(array('max' => 12)); // WHERE statusDateTime <= 12
     * </code>
     *
     * @param     mixed $statusDateTime The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterBystatusDateTime($statusDateTime = null, $comparison = null)
    {
        if (is_array($statusDateTime)) {
            $useMinMax = false;
            if (isset($statusDateTime['min'])) {
                $this->addUsingAlias(DrugChartPeer::STATUSDATETIME, $statusDateTime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($statusDateTime['max'])) {
                $this->addUsingAlias(DrugChartPeer::STATUSDATETIME, $statusDateTime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::STATUSDATETIME, $statusDateTime, $comparison);
    }

    /**
     * Filter the query on the note column
     *
     * Example usage:
     * <code>
     * $query->filterBynote('fooValue');   // WHERE note = 'fooValue'
     * $query->filterBynote('%fooValue%'); // WHERE note LIKE '%fooValue%'
     * </code>
     *
     * @param     string $note The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterBynote($note = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($note)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $note)) {
                $note = str_replace('*', '%', $note);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::NOTE, $note, $comparison);
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
     * @return DrugChartQuery The current query, for fluid interface
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

        return $this->addUsingAlias(DrugChartPeer::UUID, $uuid, $comparison);
    }

    /**
     * Filter the query on the version column
     *
     * Example usage:
     * <code>
     * $query->filterByversion(1234); // WHERE version = 1234
     * $query->filterByversion(array(12, 34)); // WHERE version IN (12, 34)
     * $query->filterByversion(array('min' => 12)); // WHERE version >= 12
     * $query->filterByversion(array('max' => 12)); // WHERE version <= 12
     * </code>
     *
     * @param     mixed $version The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(DrugChartPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(DrugChartPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(DrugChartPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugChartQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByAction($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(DrugChartPeer::ACTION_ID, $action->getid(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(DrugChartPeer::ACTION_ID, $action->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return DrugChartQuery The current query, for fluid interface
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
     * Filter the query by a related DrugChart object
     *
     * @param   DrugChart|PropelObjectCollection $drugChart The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugChartQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugChartRelatedBymasterId($drugChart, $comparison = null)
    {
        if ($drugChart instanceof DrugChart) {
            return $this
                ->addUsingAlias(DrugChartPeer::MASTER_ID, $drugChart->getid(), $comparison);
        } elseif ($drugChart instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(DrugChartPeer::MASTER_ID, $drugChart->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByDrugChartRelatedBymasterId() only accepts arguments of type DrugChart or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the DrugChartRelatedBymasterId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function joinDrugChartRelatedBymasterId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('DrugChartRelatedBymasterId');

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
            $this->addJoinObject($join, 'DrugChartRelatedBymasterId');
        }

        return $this;
    }

    /**
     * Use the DrugChartRelatedBymasterId relation DrugChart object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\DrugChartQuery A secondary query class using the current class as primary query
     */
    public function useDrugChartRelatedBymasterIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinDrugChartRelatedBymasterId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugChartRelatedBymasterId', '\Webmis\Models\DrugChartQuery');
    }

    /**
     * Filter the query by a related DrugChart object
     *
     * @param   DrugChart|PropelObjectCollection $drugChart  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 DrugChartQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugChartRelatedByid($drugChart, $comparison = null)
    {
        if ($drugChart instanceof DrugChart) {
            return $this
                ->addUsingAlias(DrugChartPeer::ID, $drugChart->getmasterId(), $comparison);
        } elseif ($drugChart instanceof PropelObjectCollection) {
            return $this
                ->useDrugChartRelatedByidQuery()
                ->filterByPrimaryKeys($drugChart->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByDrugChartRelatedByid() only accepts arguments of type DrugChart or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the DrugChartRelatedByid relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function joinDrugChartRelatedByid($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('DrugChartRelatedByid');

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
            $this->addJoinObject($join, 'DrugChartRelatedByid');
        }

        return $this;
    }

    /**
     * Use the DrugChartRelatedByid relation DrugChart object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\DrugChartQuery A secondary query class using the current class as primary query
     */
    public function useDrugChartRelatedByidQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinDrugChartRelatedByid($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugChartRelatedByid', '\Webmis\Models\DrugChartQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   DrugChart $drugChart Object to remove from the list of results
     *
     * @return DrugChartQuery The current query, for fluid interface
     */
    public function prune($drugChart = null)
    {
        if ($drugChart) {
            $this->addUsingAlias(DrugChartPeer::ID, $drugChart->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
