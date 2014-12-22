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
use Webmis\Models\Event;
use Webmis\Models\OrgStructure;
use Webmis\Models\OrgStructurePeer;
use Webmis\Models\OrgStructureQuery;
use Webmis\Models\RbStorage;

/**
 * Base class that represents a query for the 'OrgStructure' table.
 *
 *
 *
 * @method OrgStructureQuery orderById($order = Criteria::ASC) Order by the id column
 * @method OrgStructureQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method OrgStructureQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method OrgStructureQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method OrgStructureQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method OrgStructureQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method OrgStructureQuery orderByorganisationId($order = Criteria::ASC) Order by the organisation_id column
 * @method OrgStructureQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method OrgStructureQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method OrgStructureQuery orderByparentId($order = Criteria::ASC) Order by the parent_id column
 * @method OrgStructureQuery orderBytype($order = Criteria::ASC) Order by the type column
 * @method OrgStructureQuery orderBynetId($order = Criteria::ASC) Order by the net_id column
 * @method OrgStructureQuery orderByisArea($order = Criteria::ASC) Order by the isArea column
 * @method OrgStructureQuery orderByhasHospitalBeds($order = Criteria::ASC) Order by the hasHospitalBeds column
 * @method OrgStructureQuery orderByhasStocks($order = Criteria::ASC) Order by the hasStocks column
 * @method OrgStructureQuery orderByinfisCode($order = Criteria::ASC) Order by the infisCode column
 * @method OrgStructureQuery orderByinfisInternalCode($order = Criteria::ASC) Order by the infisInternalCode column
 * @method OrgStructureQuery orderByinfisDepTypeCode($order = Criteria::ASC) Order by the infisDepTypeCode column
 * @method OrgStructureQuery orderByinfisTariffCode($order = Criteria::ASC) Order by the infisTariffCode column
 * @method OrgStructureQuery orderByavailableForExternal($order = Criteria::ASC) Order by the availableForExternal column
 * @method OrgStructureQuery orderByaddress($order = Criteria::ASC) Order by the Address column
 * @method OrgStructureQuery orderByinheritEventTypes($order = Criteria::ASC) Order by the inheritEventTypes column
 * @method OrgStructureQuery orderByinheritActionTypes($order = Criteria::ASC) Order by the inheritActionTypes column
 * @method OrgStructureQuery orderByinheritGaps($order = Criteria::ASC) Order by the inheritGaps column
 * @method OrgStructureQuery orderByuuidId($order = Criteria::ASC) Order by the uuid_id column
 * @method OrgStructureQuery orderByshow($order = Criteria::ASC) Order by the show column
 *
 * @method OrgStructureQuery groupById() Group by the id column
 * @method OrgStructureQuery groupBycreateDatetime() Group by the createDatetime column
 * @method OrgStructureQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method OrgStructureQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method OrgStructureQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method OrgStructureQuery groupBydeleted() Group by the deleted column
 * @method OrgStructureQuery groupByorganisationId() Group by the organisation_id column
 * @method OrgStructureQuery groupBycode() Group by the code column
 * @method OrgStructureQuery groupByname() Group by the name column
 * @method OrgStructureQuery groupByparentId() Group by the parent_id column
 * @method OrgStructureQuery groupBytype() Group by the type column
 * @method OrgStructureQuery groupBynetId() Group by the net_id column
 * @method OrgStructureQuery groupByisArea() Group by the isArea column
 * @method OrgStructureQuery groupByhasHospitalBeds() Group by the hasHospitalBeds column
 * @method OrgStructureQuery groupByhasStocks() Group by the hasStocks column
 * @method OrgStructureQuery groupByinfisCode() Group by the infisCode column
 * @method OrgStructureQuery groupByinfisInternalCode() Group by the infisInternalCode column
 * @method OrgStructureQuery groupByinfisDepTypeCode() Group by the infisDepTypeCode column
 * @method OrgStructureQuery groupByinfisTariffCode() Group by the infisTariffCode column
 * @method OrgStructureQuery groupByavailableForExternal() Group by the availableForExternal column
 * @method OrgStructureQuery groupByaddress() Group by the Address column
 * @method OrgStructureQuery groupByinheritEventTypes() Group by the inheritEventTypes column
 * @method OrgStructureQuery groupByinheritActionTypes() Group by the inheritActionTypes column
 * @method OrgStructureQuery groupByinheritGaps() Group by the inheritGaps column
 * @method OrgStructureQuery groupByuuidId() Group by the uuid_id column
 * @method OrgStructureQuery groupByshow() Group by the show column
 *
 * @method OrgStructureQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method OrgStructureQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method OrgStructureQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method OrgStructureQuery leftJoinEvent($relationAlias = null) Adds a LEFT JOIN clause to the query using the Event relation
 * @method OrgStructureQuery rightJoinEvent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Event relation
 * @method OrgStructureQuery innerJoinEvent($relationAlias = null) Adds a INNER JOIN clause to the query using the Event relation
 *
 * @method OrgStructureQuery leftJoinRbStorage($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbStorage relation
 * @method OrgStructureQuery rightJoinRbStorage($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbStorage relation
 * @method OrgStructureQuery innerJoinRbStorage($relationAlias = null) Adds a INNER JOIN clause to the query using the RbStorage relation
 *
 * @method OrgStructure findOne(PropelPDO $con = null) Return the first OrgStructure matching the query
 * @method OrgStructure findOneOrCreate(PropelPDO $con = null) Return the first OrgStructure matching the query, or a new OrgStructure object populated from the query conditions when no match is found
 *
 * @method OrgStructure findOneBycreateDatetime(string $createDatetime) Return the first OrgStructure filtered by the createDatetime column
 * @method OrgStructure findOneBycreatePersonId(int $createPerson_id) Return the first OrgStructure filtered by the createPerson_id column
 * @method OrgStructure findOneBymodifyDatetime(string $modifyDatetime) Return the first OrgStructure filtered by the modifyDatetime column
 * @method OrgStructure findOneBymodifyPersonId(int $modifyPerson_id) Return the first OrgStructure filtered by the modifyPerson_id column
 * @method OrgStructure findOneBydeleted(boolean $deleted) Return the first OrgStructure filtered by the deleted column
 * @method OrgStructure findOneByorganisationId(int $organisation_id) Return the first OrgStructure filtered by the organisation_id column
 * @method OrgStructure findOneBycode(string $code) Return the first OrgStructure filtered by the code column
 * @method OrgStructure findOneByname(string $name) Return the first OrgStructure filtered by the name column
 * @method OrgStructure findOneByparentId(int $parent_id) Return the first OrgStructure filtered by the parent_id column
 * @method OrgStructure findOneBytype(int $type) Return the first OrgStructure filtered by the type column
 * @method OrgStructure findOneBynetId(int $net_id) Return the first OrgStructure filtered by the net_id column
 * @method OrgStructure findOneByisArea(int $isArea) Return the first OrgStructure filtered by the isArea column
 * @method OrgStructure findOneByhasHospitalBeds(boolean $hasHospitalBeds) Return the first OrgStructure filtered by the hasHospitalBeds column
 * @method OrgStructure findOneByhasStocks(boolean $hasStocks) Return the first OrgStructure filtered by the hasStocks column
 * @method OrgStructure findOneByinfisCode(string $infisCode) Return the first OrgStructure filtered by the infisCode column
 * @method OrgStructure findOneByinfisInternalCode(string $infisInternalCode) Return the first OrgStructure filtered by the infisInternalCode column
 * @method OrgStructure findOneByinfisDepTypeCode(string $infisDepTypeCode) Return the first OrgStructure filtered by the infisDepTypeCode column
 * @method OrgStructure findOneByinfisTariffCode(string $infisTariffCode) Return the first OrgStructure filtered by the infisTariffCode column
 * @method OrgStructure findOneByavailableForExternal(int $availableForExternal) Return the first OrgStructure filtered by the availableForExternal column
 * @method OrgStructure findOneByaddress(string $Address) Return the first OrgStructure filtered by the Address column
 * @method OrgStructure findOneByinheritEventTypes(boolean $inheritEventTypes) Return the first OrgStructure filtered by the inheritEventTypes column
 * @method OrgStructure findOneByinheritActionTypes(boolean $inheritActionTypes) Return the first OrgStructure filtered by the inheritActionTypes column
 * @method OrgStructure findOneByinheritGaps(boolean $inheritGaps) Return the first OrgStructure filtered by the inheritGaps column
 * @method OrgStructure findOneByuuidId(int $uuid_id) Return the first OrgStructure filtered by the uuid_id column
 * @method OrgStructure findOneByshow(int $show) Return the first OrgStructure filtered by the show column
 *
 * @method array findById(int $id) Return OrgStructure objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return OrgStructure objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return OrgStructure objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return OrgStructure objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return OrgStructure objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return OrgStructure objects filtered by the deleted column
 * @method array findByorganisationId(int $organisation_id) Return OrgStructure objects filtered by the organisation_id column
 * @method array findBycode(string $code) Return OrgStructure objects filtered by the code column
 * @method array findByname(string $name) Return OrgStructure objects filtered by the name column
 * @method array findByparentId(int $parent_id) Return OrgStructure objects filtered by the parent_id column
 * @method array findBytype(int $type) Return OrgStructure objects filtered by the type column
 * @method array findBynetId(int $net_id) Return OrgStructure objects filtered by the net_id column
 * @method array findByisArea(int $isArea) Return OrgStructure objects filtered by the isArea column
 * @method array findByhasHospitalBeds(boolean $hasHospitalBeds) Return OrgStructure objects filtered by the hasHospitalBeds column
 * @method array findByhasStocks(boolean $hasStocks) Return OrgStructure objects filtered by the hasStocks column
 * @method array findByinfisCode(string $infisCode) Return OrgStructure objects filtered by the infisCode column
 * @method array findByinfisInternalCode(string $infisInternalCode) Return OrgStructure objects filtered by the infisInternalCode column
 * @method array findByinfisDepTypeCode(string $infisDepTypeCode) Return OrgStructure objects filtered by the infisDepTypeCode column
 * @method array findByinfisTariffCode(string $infisTariffCode) Return OrgStructure objects filtered by the infisTariffCode column
 * @method array findByavailableForExternal(int $availableForExternal) Return OrgStructure objects filtered by the availableForExternal column
 * @method array findByaddress(string $Address) Return OrgStructure objects filtered by the Address column
 * @method array findByinheritEventTypes(boolean $inheritEventTypes) Return OrgStructure objects filtered by the inheritEventTypes column
 * @method array findByinheritActionTypes(boolean $inheritActionTypes) Return OrgStructure objects filtered by the inheritActionTypes column
 * @method array findByinheritGaps(boolean $inheritGaps) Return OrgStructure objects filtered by the inheritGaps column
 * @method array findByuuidId(int $uuid_id) Return OrgStructure objects filtered by the uuid_id column
 * @method array findByshow(int $show) Return OrgStructure objects filtered by the show column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseOrgStructureQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseOrgStructureQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\OrgStructure', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new OrgStructureQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   OrgStructureQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return OrgStructureQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof OrgStructureQuery) {
            return $criteria;
        }
        $query = new OrgStructureQuery();
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
     * @return   OrgStructure|OrgStructure[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = OrgStructurePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(OrgStructurePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 OrgStructure A model object, or null if the key is not found
     * @throws PropelException
     */
     public function findOneById($key, $con = null)
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
     * @return                 OrgStructure A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `organisation_id`, `code`, `name`, `parent_id`, `type`, `net_id`, `isArea`, `hasHospitalBeds`, `hasStocks`, `infisCode`, `infisInternalCode`, `infisDepTypeCode`, `infisTariffCode`, `availableForExternal`, `Address`, `inheritEventTypes`, `inheritActionTypes`, `inheritGaps`, `uuid_id`, `show` FROM `OrgStructure` WHERE `id` = :p0';
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
            $obj = new OrgStructure();
            $obj->hydrate($row);
            OrgStructurePeer::addInstanceToPool($obj, (string) $key);
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
     * @return OrgStructure|OrgStructure[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|OrgStructure[]|mixed the list of results, formatted by the current formatter
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(OrgStructurePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(OrgStructurePeer::ID, $keys, Criteria::IN);
    }

    /**
     * Filter the query on the id column
     *
     * Example usage:
     * <code>
     * $query->filterById(1234); // WHERE id = 1234
     * $query->filterById(array(12, 34)); // WHERE id IN (12, 34)
     * $query->filterById(array('min' => 12)); // WHERE id >= 12
     * $query->filterById(array('max' => 12)); // WHERE id <= 12
     * </code>
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(OrgStructurePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(OrgStructurePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::ID, $id, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(OrgStructurePeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(OrgStructurePeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(OrgStructurePeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(OrgStructurePeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the organisation_id column
     *
     * Example usage:
     * <code>
     * $query->filterByorganisationId(1234); // WHERE organisation_id = 1234
     * $query->filterByorganisationId(array(12, 34)); // WHERE organisation_id IN (12, 34)
     * $query->filterByorganisationId(array('min' => 12)); // WHERE organisation_id >= 12
     * $query->filterByorganisationId(array('max' => 12)); // WHERE organisation_id <= 12
     * </code>
     *
     * @param     mixed $organisationId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByorganisationId($organisationId = null, $comparison = null)
    {
        if (is_array($organisationId)) {
            $useMinMax = false;
            if (isset($organisationId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::ORGANISATION_ID, $organisationId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($organisationId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::ORGANISATION_ID, $organisationId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::ORGANISATION_ID, $organisationId, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
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

        return $this->addUsingAlias(OrgStructurePeer::CODE, $code, $comparison);
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
     * @return OrgStructureQuery The current query, for fluid interface
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

        return $this->addUsingAlias(OrgStructurePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the parent_id column
     *
     * Example usage:
     * <code>
     * $query->filterByparentId(1234); // WHERE parent_id = 1234
     * $query->filterByparentId(array(12, 34)); // WHERE parent_id IN (12, 34)
     * $query->filterByparentId(array('min' => 12)); // WHERE parent_id >= 12
     * $query->filterByparentId(array('max' => 12)); // WHERE parent_id <= 12
     * </code>
     *
     * @param     mixed $parentId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByparentId($parentId = null, $comparison = null)
    {
        if (is_array($parentId)) {
            $useMinMax = false;
            if (isset($parentId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::PARENT_ID, $parentId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($parentId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::PARENT_ID, $parentId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::PARENT_ID, $parentId, $comparison);
    }

    /**
     * Filter the query on the type column
     *
     * Example usage:
     * <code>
     * $query->filterBytype(1234); // WHERE type = 1234
     * $query->filterBytype(array(12, 34)); // WHERE type IN (12, 34)
     * $query->filterBytype(array('min' => 12)); // WHERE type >= 12
     * $query->filterBytype(array('max' => 12)); // WHERE type <= 12
     * </code>
     *
     * @param     mixed $type The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBytype($type = null, $comparison = null)
    {
        if (is_array($type)) {
            $useMinMax = false;
            if (isset($type['min'])) {
                $this->addUsingAlias(OrgStructurePeer::TYPE, $type['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($type['max'])) {
                $this->addUsingAlias(OrgStructurePeer::TYPE, $type['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::TYPE, $type, $comparison);
    }

    /**
     * Filter the query on the net_id column
     *
     * Example usage:
     * <code>
     * $query->filterBynetId(1234); // WHERE net_id = 1234
     * $query->filterBynetId(array(12, 34)); // WHERE net_id IN (12, 34)
     * $query->filterBynetId(array('min' => 12)); // WHERE net_id >= 12
     * $query->filterBynetId(array('max' => 12)); // WHERE net_id <= 12
     * </code>
     *
     * @param     mixed $netId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterBynetId($netId = null, $comparison = null)
    {
        if (is_array($netId)) {
            $useMinMax = false;
            if (isset($netId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::NET_ID, $netId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($netId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::NET_ID, $netId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::NET_ID, $netId, $comparison);
    }

    /**
     * Filter the query on the isArea column
     *
     * Example usage:
     * <code>
     * $query->filterByisArea(1234); // WHERE isArea = 1234
     * $query->filterByisArea(array(12, 34)); // WHERE isArea IN (12, 34)
     * $query->filterByisArea(array('min' => 12)); // WHERE isArea >= 12
     * $query->filterByisArea(array('max' => 12)); // WHERE isArea <= 12
     * </code>
     *
     * @param     mixed $isArea The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByisArea($isArea = null, $comparison = null)
    {
        if (is_array($isArea)) {
            $useMinMax = false;
            if (isset($isArea['min'])) {
                $this->addUsingAlias(OrgStructurePeer::ISAREA, $isArea['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($isArea['max'])) {
                $this->addUsingAlias(OrgStructurePeer::ISAREA, $isArea['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::ISAREA, $isArea, $comparison);
    }

    /**
     * Filter the query on the hasHospitalBeds column
     *
     * Example usage:
     * <code>
     * $query->filterByhasHospitalBeds(true); // WHERE hasHospitalBeds = true
     * $query->filterByhasHospitalBeds('yes'); // WHERE hasHospitalBeds = true
     * </code>
     *
     * @param     boolean|string $hasHospitalBeds The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByhasHospitalBeds($hasHospitalBeds = null, $comparison = null)
    {
        if (is_string($hasHospitalBeds)) {
            $hasHospitalBeds = in_array(strtolower($hasHospitalBeds), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::HASHOSPITALBEDS, $hasHospitalBeds, $comparison);
    }

    /**
     * Filter the query on the hasStocks column
     *
     * Example usage:
     * <code>
     * $query->filterByhasStocks(true); // WHERE hasStocks = true
     * $query->filterByhasStocks('yes'); // WHERE hasStocks = true
     * </code>
     *
     * @param     boolean|string $hasStocks The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByhasStocks($hasStocks = null, $comparison = null)
    {
        if (is_string($hasStocks)) {
            $hasStocks = in_array(strtolower($hasStocks), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::HASSTOCKS, $hasStocks, $comparison);
    }

    /**
     * Filter the query on the infisCode column
     *
     * Example usage:
     * <code>
     * $query->filterByinfisCode('fooValue');   // WHERE infisCode = 'fooValue'
     * $query->filterByinfisCode('%fooValue%'); // WHERE infisCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $infisCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinfisCode($infisCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($infisCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $infisCode)) {
                $infisCode = str_replace('*', '%', $infisCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::INFISCODE, $infisCode, $comparison);
    }

    /**
     * Filter the query on the infisInternalCode column
     *
     * Example usage:
     * <code>
     * $query->filterByinfisInternalCode('fooValue');   // WHERE infisInternalCode = 'fooValue'
     * $query->filterByinfisInternalCode('%fooValue%'); // WHERE infisInternalCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $infisInternalCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinfisInternalCode($infisInternalCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($infisInternalCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $infisInternalCode)) {
                $infisInternalCode = str_replace('*', '%', $infisInternalCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::INFISINTERNALCODE, $infisInternalCode, $comparison);
    }

    /**
     * Filter the query on the infisDepTypeCode column
     *
     * Example usage:
     * <code>
     * $query->filterByinfisDepTypeCode('fooValue');   // WHERE infisDepTypeCode = 'fooValue'
     * $query->filterByinfisDepTypeCode('%fooValue%'); // WHERE infisDepTypeCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $infisDepTypeCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinfisDepTypeCode($infisDepTypeCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($infisDepTypeCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $infisDepTypeCode)) {
                $infisDepTypeCode = str_replace('*', '%', $infisDepTypeCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::INFISDEPTYPECODE, $infisDepTypeCode, $comparison);
    }

    /**
     * Filter the query on the infisTariffCode column
     *
     * Example usage:
     * <code>
     * $query->filterByinfisTariffCode('fooValue');   // WHERE infisTariffCode = 'fooValue'
     * $query->filterByinfisTariffCode('%fooValue%'); // WHERE infisTariffCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $infisTariffCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinfisTariffCode($infisTariffCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($infisTariffCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $infisTariffCode)) {
                $infisTariffCode = str_replace('*', '%', $infisTariffCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::INFISTARIFFCODE, $infisTariffCode, $comparison);
    }

    /**
     * Filter the query on the availableForExternal column
     *
     * Example usage:
     * <code>
     * $query->filterByavailableForExternal(1234); // WHERE availableForExternal = 1234
     * $query->filterByavailableForExternal(array(12, 34)); // WHERE availableForExternal IN (12, 34)
     * $query->filterByavailableForExternal(array('min' => 12)); // WHERE availableForExternal >= 12
     * $query->filterByavailableForExternal(array('max' => 12)); // WHERE availableForExternal <= 12
     * </code>
     *
     * @param     mixed $availableForExternal The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByavailableForExternal($availableForExternal = null, $comparison = null)
    {
        if (is_array($availableForExternal)) {
            $useMinMax = false;
            if (isset($availableForExternal['min'])) {
                $this->addUsingAlias(OrgStructurePeer::AVAILABLEFOREXTERNAL, $availableForExternal['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($availableForExternal['max'])) {
                $this->addUsingAlias(OrgStructurePeer::AVAILABLEFOREXTERNAL, $availableForExternal['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::AVAILABLEFOREXTERNAL, $availableForExternal, $comparison);
    }

    /**
     * Filter the query on the Address column
     *
     * Example usage:
     * <code>
     * $query->filterByaddress('fooValue');   // WHERE Address = 'fooValue'
     * $query->filterByaddress('%fooValue%'); // WHERE Address LIKE '%fooValue%'
     * </code>
     *
     * @param     string $address The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByaddress($address = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($address)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $address)) {
                $address = str_replace('*', '%', $address);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::ADDRESS, $address, $comparison);
    }

    /**
     * Filter the query on the inheritEventTypes column
     *
     * Example usage:
     * <code>
     * $query->filterByinheritEventTypes(true); // WHERE inheritEventTypes = true
     * $query->filterByinheritEventTypes('yes'); // WHERE inheritEventTypes = true
     * </code>
     *
     * @param     boolean|string $inheritEventTypes The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinheritEventTypes($inheritEventTypes = null, $comparison = null)
    {
        if (is_string($inheritEventTypes)) {
            $inheritEventTypes = in_array(strtolower($inheritEventTypes), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::INHERITEVENTTYPES, $inheritEventTypes, $comparison);
    }

    /**
     * Filter the query on the inheritActionTypes column
     *
     * Example usage:
     * <code>
     * $query->filterByinheritActionTypes(true); // WHERE inheritActionTypes = true
     * $query->filterByinheritActionTypes('yes'); // WHERE inheritActionTypes = true
     * </code>
     *
     * @param     boolean|string $inheritActionTypes The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinheritActionTypes($inheritActionTypes = null, $comparison = null)
    {
        if (is_string($inheritActionTypes)) {
            $inheritActionTypes = in_array(strtolower($inheritActionTypes), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::INHERITACTIONTYPES, $inheritActionTypes, $comparison);
    }

    /**
     * Filter the query on the inheritGaps column
     *
     * Example usage:
     * <code>
     * $query->filterByinheritGaps(true); // WHERE inheritGaps = true
     * $query->filterByinheritGaps('yes'); // WHERE inheritGaps = true
     * </code>
     *
     * @param     boolean|string $inheritGaps The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByinheritGaps($inheritGaps = null, $comparison = null)
    {
        if (is_string($inheritGaps)) {
            $inheritGaps = in_array(strtolower($inheritGaps), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(OrgStructurePeer::INHERITGAPS, $inheritGaps, $comparison);
    }

    /**
     * Filter the query on the uuid_id column
     *
     * Example usage:
     * <code>
     * $query->filterByuuidId(1234); // WHERE uuid_id = 1234
     * $query->filterByuuidId(array(12, 34)); // WHERE uuid_id IN (12, 34)
     * $query->filterByuuidId(array('min' => 12)); // WHERE uuid_id >= 12
     * $query->filterByuuidId(array('max' => 12)); // WHERE uuid_id <= 12
     * </code>
     *
     * @param     mixed $uuidId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByuuidId($uuidId = null, $comparison = null)
    {
        if (is_array($uuidId)) {
            $useMinMax = false;
            if (isset($uuidId['min'])) {
                $this->addUsingAlias(OrgStructurePeer::UUID_ID, $uuidId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uuidId['max'])) {
                $this->addUsingAlias(OrgStructurePeer::UUID_ID, $uuidId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::UUID_ID, $uuidId, $comparison);
    }

    /**
     * Filter the query on the show column
     *
     * Example usage:
     * <code>
     * $query->filterByshow(1234); // WHERE show = 1234
     * $query->filterByshow(array(12, 34)); // WHERE show IN (12, 34)
     * $query->filterByshow(array('min' => 12)); // WHERE show >= 12
     * $query->filterByshow(array('max' => 12)); // WHERE show <= 12
     * </code>
     *
     * @param     mixed $show The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function filterByshow($show = null, $comparison = null)
    {
        if (is_array($show)) {
            $useMinMax = false;
            if (isset($show['min'])) {
                $this->addUsingAlias(OrgStructurePeer::SHOW, $show['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($show['max'])) {
                $this->addUsingAlias(OrgStructurePeer::SHOW, $show['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(OrgStructurePeer::SHOW, $show, $comparison);
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 OrgStructureQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEvent($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(OrgStructurePeer::ID, $event->getorgStructureId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEvent() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Event relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function joinEvent($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Event');

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
            $this->addJoinObject($join, 'Event');
        }

        return $this;
    }

    /**
     * Use the Event relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEvent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Event', '\Webmis\Models\EventQuery');
    }

    /**
     * Filter the query by a related RbStorage object
     *
     * @param   RbStorage|PropelObjectCollection $rbStorage  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 OrgStructureQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbStorage($rbStorage, $comparison = null)
    {
        if ($rbStorage instanceof RbStorage) {
            return $this
                ->addUsingAlias(OrgStructurePeer::ID, $rbStorage->getorgStructureId(), $comparison);
        } elseif ($rbStorage instanceof PropelObjectCollection) {
            return $this
                ->useRbStorageQuery()
                ->filterByPrimaryKeys($rbStorage->getPrimaryKeys())
                ->endUse();
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
     * @return OrgStructureQuery The current query, for fluid interface
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
     * Exclude object from result
     *
     * @param   OrgStructure $orgStructure Object to remove from the list of results
     *
     * @return OrgStructureQuery The current query, for fluid interface
     */
    public function prune($orgStructure = null)
    {
        if ($orgStructure) {
            $this->addUsingAlias(OrgStructurePeer::ID, $orgStructure->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(OrgStructurePeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(OrgStructurePeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(OrgStructurePeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(OrgStructurePeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(OrgStructurePeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     OrgStructureQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(OrgStructurePeer::CREATEDATETIME);
    }
}
