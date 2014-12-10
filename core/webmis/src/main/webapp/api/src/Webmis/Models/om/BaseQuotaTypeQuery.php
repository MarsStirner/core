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
use Webmis\Models\QuotaTypePeer;
use Webmis\Models\QuotaTypeQuery;
use Webmis\Models\RbPacientModel;

/**
 * Base class that represents a query for the 'QuotaType' table.
 *
 *
 *
 * @method QuotaTypeQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method QuotaTypeQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method QuotaTypeQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method QuotaTypeQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method QuotaTypeQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method QuotaTypeQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method QuotaTypeQuery orderByClass($order = Criteria::ASC) Order by the class column
 * @method QuotaTypeQuery orderBygroupCode($order = Criteria::ASC) Order by the group_code column
 * @method QuotaTypeQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method QuotaTypeQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method QuotaTypeQuery orderByteenOlder($order = Criteria::ASC) Order by the teenOlder column
 *
 * @method QuotaTypeQuery groupByid() Group by the id column
 * @method QuotaTypeQuery groupBycreateDatetime() Group by the createDatetime column
 * @method QuotaTypeQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method QuotaTypeQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method QuotaTypeQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method QuotaTypeQuery groupBydeleted() Group by the deleted column
 * @method QuotaTypeQuery groupByClass() Group by the class column
 * @method QuotaTypeQuery groupBygroupCode() Group by the group_code column
 * @method QuotaTypeQuery groupBycode() Group by the code column
 * @method QuotaTypeQuery groupByname() Group by the name column
 * @method QuotaTypeQuery groupByteenOlder() Group by the teenOlder column
 *
 * @method QuotaTypeQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method QuotaTypeQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method QuotaTypeQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method QuotaTypeQuery leftJoinClientQuoting($relationAlias = null) Adds a LEFT JOIN clause to the query using the ClientQuoting relation
 * @method QuotaTypeQuery rightJoinClientQuoting($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ClientQuoting relation
 * @method QuotaTypeQuery innerJoinClientQuoting($relationAlias = null) Adds a INNER JOIN clause to the query using the ClientQuoting relation
 *
 * @method QuotaTypeQuery leftJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the MkbQuotaTypePacientModel relation
 * @method QuotaTypeQuery rightJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the MkbQuotaTypePacientModel relation
 * @method QuotaTypeQuery innerJoinMkbQuotaTypePacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the MkbQuotaTypePacientModel relation
 *
 * @method QuotaTypeQuery leftJoinRbPacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbPacientModel relation
 * @method QuotaTypeQuery rightJoinRbPacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbPacientModel relation
 * @method QuotaTypeQuery innerJoinRbPacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the RbPacientModel relation
 *
 * @method QuotaType findOne(PropelPDO $con = null) Return the first QuotaType matching the query
 * @method QuotaType findOneOrCreate(PropelPDO $con = null) Return the first QuotaType matching the query, or a new QuotaType object populated from the query conditions when no match is found
 *
 * @method QuotaType findOneBycreateDatetime(string $createDatetime) Return the first QuotaType filtered by the createDatetime column
 * @method QuotaType findOneBycreatePersonId(int $createPerson_id) Return the first QuotaType filtered by the createPerson_id column
 * @method QuotaType findOneBymodifyDatetime(string $modifyDatetime) Return the first QuotaType filtered by the modifyDatetime column
 * @method QuotaType findOneBymodifyPersonId(int $modifyPerson_id) Return the first QuotaType filtered by the modifyPerson_id column
 * @method QuotaType findOneBydeleted(boolean $deleted) Return the first QuotaType filtered by the deleted column
 * @method QuotaType findOneByClass(boolean $class) Return the first QuotaType filtered by the class column
 * @method QuotaType findOneBygroupCode(string $group_code) Return the first QuotaType filtered by the group_code column
 * @method QuotaType findOneBycode(string $code) Return the first QuotaType filtered by the code column
 * @method QuotaType findOneByname(string $name) Return the first QuotaType filtered by the name column
 * @method QuotaType findOneByteenOlder(boolean $teenOlder) Return the first QuotaType filtered by the teenOlder column
 *
 * @method array findByid(int $id) Return QuotaType objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return QuotaType objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return QuotaType objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return QuotaType objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return QuotaType objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return QuotaType objects filtered by the deleted column
 * @method array findByClass(boolean $class) Return QuotaType objects filtered by the class column
 * @method array findBygroupCode(string $group_code) Return QuotaType objects filtered by the group_code column
 * @method array findBycode(string $code) Return QuotaType objects filtered by the code column
 * @method array findByname(string $name) Return QuotaType objects filtered by the name column
 * @method array findByteenOlder(boolean $teenOlder) Return QuotaType objects filtered by the teenOlder column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseQuotaTypeQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseQuotaTypeQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\QuotaType', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new QuotaTypeQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   QuotaTypeQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return QuotaTypeQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof QuotaTypeQuery) {
            return $criteria;
        }
        $query = new QuotaTypeQuery();
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
     * @return   QuotaType|QuotaType[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = QuotaTypePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(QuotaTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 QuotaType A model object, or null if the key is not found
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
     * @return                 QuotaType A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `class`, `group_code`, `code`, `name`, `teenOlder` FROM `QuotaType` WHERE `id` = :p0';
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
            $obj = new QuotaType();
            $obj->hydrate($row);
            QuotaTypePeer::addInstanceToPool($obj, (string) $key);
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
     * @return QuotaType|QuotaType[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|QuotaType[]|mixed the list of results, formatted by the current formatter
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
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(QuotaTypePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(QuotaTypePeer::ID, $keys, Criteria::IN);
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
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(QuotaTypePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(QuotaTypePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the createDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBycreateDatetime('2011-03-14'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime('now'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime(array('max' => 'yesterday')); // WHERE createDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $createDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(QuotaTypePeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(QuotaTypePeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::CREATEDATETIME, $createDatetime, $comparison);
    }

    /**
     * Filter the query on the createPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycreatePersonId(1234); // WHERE createPerson_id = 1234
     * $query->filterBycreatePersonId(array(12, 34)); // WHERE createPerson_id IN (12, 34)
     * $query->filterBycreatePersonId(array('min' => 12)); // WHERE createPerson_id >= 12
     * $query->filterBycreatePersonId(array('max' => 12)); // WHERE createPerson_id <= 12
     * </code>
     *
     * @param     mixed $createPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(QuotaTypePeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(QuotaTypePeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::CREATEPERSON_ID, $createPersonId, $comparison);
    }

    /**
     * Filter the query on the modifyDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyDatetime('2011-03-14'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime('now'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime(array('max' => 'yesterday')); // WHERE modifyDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $modifyDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(QuotaTypePeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(QuotaTypePeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::MODIFYDATETIME, $modifyDatetime, $comparison);
    }

    /**
     * Filter the query on the modifyPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyPersonId(1234); // WHERE modifyPerson_id = 1234
     * $query->filterBymodifyPersonId(array(12, 34)); // WHERE modifyPerson_id IN (12, 34)
     * $query->filterBymodifyPersonId(array('min' => 12)); // WHERE modifyPerson_id >= 12
     * $query->filterBymodifyPersonId(array('max' => 12)); // WHERE modifyPerson_id <= 12
     * </code>
     *
     * @param     mixed $modifyPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(QuotaTypePeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(QuotaTypePeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
    }

    /**
     * Filter the query on the deleted column
     *
     * Example usage:
     * <code>
     * $query->filterBydeleted(true); // WHERE deleted = true
     * $query->filterBydeleted('yes'); // WHERE deleted = true
     * </code>
     *
     * @param     boolean|string $deleted The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(QuotaTypePeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the class column
     *
     * Example usage:
     * <code>
     * $query->filterByClass(true); // WHERE class = true
     * $query->filterByClass('yes'); // WHERE class = true
     * </code>
     *
     * @param     boolean|string $class The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterByClass($class = null, $comparison = null)
    {
        if (is_string($class)) {
            $class = in_array(strtolower($class), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(QuotaTypePeer::CLAZZ, $class, $comparison);
    }

    /**
     * Filter the query on the group_code column
     *
     * Example usage:
     * <code>
     * $query->filterBygroupCode('fooValue');   // WHERE group_code = 'fooValue'
     * $query->filterBygroupCode('%fooValue%'); // WHERE group_code LIKE '%fooValue%'
     * </code>
     *
     * @param     string $groupCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterBygroupCode($groupCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($groupCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $groupCode)) {
                $groupCode = str_replace('*', '%', $groupCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(QuotaTypePeer::GROUP_CODE, $groupCode, $comparison);
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
     * @return QuotaTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(QuotaTypePeer::CODE, $code, $comparison);
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
     * @return QuotaTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(QuotaTypePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the teenOlder column
     *
     * Example usage:
     * <code>
     * $query->filterByteenOlder(true); // WHERE teenOlder = true
     * $query->filterByteenOlder('yes'); // WHERE teenOlder = true
     * </code>
     *
     * @param     boolean|string $teenOlder The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function filterByteenOlder($teenOlder = null, $comparison = null)
    {
        if (is_string($teenOlder)) {
            $teenOlder = in_array(strtolower($teenOlder), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(QuotaTypePeer::TEENOLDER, $teenOlder, $comparison);
    }

    /**
     * Filter the query by a related ClientQuoting object
     *
     * @param   ClientQuoting|PropelObjectCollection $clientQuoting  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 QuotaTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByClientQuoting($clientQuoting, $comparison = null)
    {
        if ($clientQuoting instanceof ClientQuoting) {
            return $this
                ->addUsingAlias(QuotaTypePeer::ID, $clientQuoting->getquotaTypeId(), $comparison);
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
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function joinClientQuoting($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
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
    public function useClientQuotingQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
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
     * @return                 QuotaTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByMkbQuotaTypePacientModel($mkbQuotaTypePacientModel, $comparison = null)
    {
        if ($mkbQuotaTypePacientModel instanceof MkbQuotaTypePacientModel) {
            return $this
                ->addUsingAlias(QuotaTypePeer::ID, $mkbQuotaTypePacientModel->getquotaTypeId(), $comparison);
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
     * @return QuotaTypeQuery The current query, for fluid interface
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
     * Filter the query by a related RbPacientModel object
     *
     * @param   RbPacientModel|PropelObjectCollection $rbPacientModel  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 QuotaTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbPacientModel($rbPacientModel, $comparison = null)
    {
        if ($rbPacientModel instanceof RbPacientModel) {
            return $this
                ->addUsingAlias(QuotaTypePeer::ID, $rbPacientModel->getquotaTypeId(), $comparison);
        } elseif ($rbPacientModel instanceof PropelObjectCollection) {
            return $this
                ->useRbPacientModelQuery()
                ->filterByPrimaryKeys($rbPacientModel->getPrimaryKeys())
                ->endUse();
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
     * @return QuotaTypeQuery The current query, for fluid interface
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
     * @param   QuotaType $quotaType Object to remove from the list of results
     *
     * @return QuotaTypeQuery The current query, for fluid interface
     */
    public function prune($quotaType = null)
    {
        if ($quotaType) {
            $this->addUsingAlias(QuotaTypePeer::ID, $quotaType->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(QuotaTypePeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(QuotaTypePeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(QuotaTypePeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(QuotaTypePeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(QuotaTypePeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     QuotaTypeQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(QuotaTypePeer::CREATEDATETIME);
    }
}
