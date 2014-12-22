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
use Webmis\Models\ClientQuotingPeer;
use Webmis\Models\ClientQuotingQuery;
use Webmis\Models\QuotaType;
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbTreatment;

/**
 * Base class that represents a query for the 'Client_Quoting' table.
 *
 *
 *
 * @method ClientQuotingQuery orderById($order = Criteria::ASC) Order by the id column
 * @method ClientQuotingQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method ClientQuotingQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method ClientQuotingQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method ClientQuotingQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method ClientQuotingQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ClientQuotingQuery orderBymasterId($order = Criteria::ASC) Order by the master_id column
 * @method ClientQuotingQuery orderByidentifier($order = Criteria::ASC) Order by the identifier column
 * @method ClientQuotingQuery orderByquotaTicket($order = Criteria::ASC) Order by the quotaTicket column
 * @method ClientQuotingQuery orderByquotaTypeId($order = Criteria::ASC) Order by the quotaType_id column
 * @method ClientQuotingQuery orderBystage($order = Criteria::ASC) Order by the stage column
 * @method ClientQuotingQuery orderBydirectionDate($order = Criteria::ASC) Order by the directionDate column
 * @method ClientQuotingQuery orderByfreeInput($order = Criteria::ASC) Order by the freeInput column
 * @method ClientQuotingQuery orderByorgId($order = Criteria::ASC) Order by the org_id column
 * @method ClientQuotingQuery orderByamount($order = Criteria::ASC) Order by the amount column
 * @method ClientQuotingQuery orderBymkb($order = Criteria::ASC) Order by the MKB column
 * @method ClientQuotingQuery orderBystatus($order = Criteria::ASC) Order by the status column
 * @method ClientQuotingQuery orderByrequest($order = Criteria::ASC) Order by the request column
 * @method ClientQuotingQuery orderBystatment($order = Criteria::ASC) Order by the statment column
 * @method ClientQuotingQuery orderBydateRegistration($order = Criteria::ASC) Order by the dateRegistration column
 * @method ClientQuotingQuery orderBydateEnd($order = Criteria::ASC) Order by the dateEnd column
 * @method ClientQuotingQuery orderByorgStructureId($order = Criteria::ASC) Order by the orgStructure_id column
 * @method ClientQuotingQuery orderByregionCode($order = Criteria::ASC) Order by the regionCode column
 * @method ClientQuotingQuery orderBypacientModelId($order = Criteria::ASC) Order by the pacientModel_id column
 * @method ClientQuotingQuery orderBytreatmentId($order = Criteria::ASC) Order by the treatment_id column
 * @method ClientQuotingQuery orderByeventId($order = Criteria::ASC) Order by the event_id column
 * @method ClientQuotingQuery orderByprevTalonEventId($order = Criteria::ASC) Order by the prevTalon_event_id column
 * @method ClientQuotingQuery orderByversion($order = Criteria::ASC) Order by the version column
 *
 * @method ClientQuotingQuery groupById() Group by the id column
 * @method ClientQuotingQuery groupBycreateDatetime() Group by the createDatetime column
 * @method ClientQuotingQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method ClientQuotingQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method ClientQuotingQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method ClientQuotingQuery groupBydeleted() Group by the deleted column
 * @method ClientQuotingQuery groupBymasterId() Group by the master_id column
 * @method ClientQuotingQuery groupByidentifier() Group by the identifier column
 * @method ClientQuotingQuery groupByquotaTicket() Group by the quotaTicket column
 * @method ClientQuotingQuery groupByquotaTypeId() Group by the quotaType_id column
 * @method ClientQuotingQuery groupBystage() Group by the stage column
 * @method ClientQuotingQuery groupBydirectionDate() Group by the directionDate column
 * @method ClientQuotingQuery groupByfreeInput() Group by the freeInput column
 * @method ClientQuotingQuery groupByorgId() Group by the org_id column
 * @method ClientQuotingQuery groupByamount() Group by the amount column
 * @method ClientQuotingQuery groupBymkb() Group by the MKB column
 * @method ClientQuotingQuery groupBystatus() Group by the status column
 * @method ClientQuotingQuery groupByrequest() Group by the request column
 * @method ClientQuotingQuery groupBystatment() Group by the statment column
 * @method ClientQuotingQuery groupBydateRegistration() Group by the dateRegistration column
 * @method ClientQuotingQuery groupBydateEnd() Group by the dateEnd column
 * @method ClientQuotingQuery groupByorgStructureId() Group by the orgStructure_id column
 * @method ClientQuotingQuery groupByregionCode() Group by the regionCode column
 * @method ClientQuotingQuery groupBypacientModelId() Group by the pacientModel_id column
 * @method ClientQuotingQuery groupBytreatmentId() Group by the treatment_id column
 * @method ClientQuotingQuery groupByeventId() Group by the event_id column
 * @method ClientQuotingQuery groupByprevTalonEventId() Group by the prevTalon_event_id column
 * @method ClientQuotingQuery groupByversion() Group by the version column
 *
 * @method ClientQuotingQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ClientQuotingQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ClientQuotingQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ClientQuotingQuery leftJoinRbTreatment($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbTreatment relation
 * @method ClientQuotingQuery rightJoinRbTreatment($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbTreatment relation
 * @method ClientQuotingQuery innerJoinRbTreatment($relationAlias = null) Adds a INNER JOIN clause to the query using the RbTreatment relation
 *
 * @method ClientQuotingQuery leftJoinRbPacientModel($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbPacientModel relation
 * @method ClientQuotingQuery rightJoinRbPacientModel($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbPacientModel relation
 * @method ClientQuotingQuery innerJoinRbPacientModel($relationAlias = null) Adds a INNER JOIN clause to the query using the RbPacientModel relation
 *
 * @method ClientQuotingQuery leftJoinQuotaType($relationAlias = null) Adds a LEFT JOIN clause to the query using the QuotaType relation
 * @method ClientQuotingQuery rightJoinQuotaType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the QuotaType relation
 * @method ClientQuotingQuery innerJoinQuotaType($relationAlias = null) Adds a INNER JOIN clause to the query using the QuotaType relation
 *
 * @method ClientQuoting findOne(PropelPDO $con = null) Return the first ClientQuoting matching the query
 * @method ClientQuoting findOneOrCreate(PropelPDO $con = null) Return the first ClientQuoting matching the query, or a new ClientQuoting object populated from the query conditions when no match is found
 *
 * @method ClientQuoting findOneBycreateDatetime(string $createDatetime) Return the first ClientQuoting filtered by the createDatetime column
 * @method ClientQuoting findOneBycreatePersonId(int $createPerson_id) Return the first ClientQuoting filtered by the createPerson_id column
 * @method ClientQuoting findOneBymodifyDatetime(string $modifyDatetime) Return the first ClientQuoting filtered by the modifyDatetime column
 * @method ClientQuoting findOneBymodifyPersonId(int $modifyPerson_id) Return the first ClientQuoting filtered by the modifyPerson_id column
 * @method ClientQuoting findOneBydeleted(boolean $deleted) Return the first ClientQuoting filtered by the deleted column
 * @method ClientQuoting findOneBymasterId(int $master_id) Return the first ClientQuoting filtered by the master_id column
 * @method ClientQuoting findOneByidentifier(string $identifier) Return the first ClientQuoting filtered by the identifier column
 * @method ClientQuoting findOneByquotaTicket(string $quotaTicket) Return the first ClientQuoting filtered by the quotaTicket column
 * @method ClientQuoting findOneByquotaTypeId(int $quotaType_id) Return the first ClientQuoting filtered by the quotaType_id column
 * @method ClientQuoting findOneBystage(int $stage) Return the first ClientQuoting filtered by the stage column
 * @method ClientQuoting findOneBydirectionDate(string $directionDate) Return the first ClientQuoting filtered by the directionDate column
 * @method ClientQuoting findOneByfreeInput(string $freeInput) Return the first ClientQuoting filtered by the freeInput column
 * @method ClientQuoting findOneByorgId(int $org_id) Return the first ClientQuoting filtered by the org_id column
 * @method ClientQuoting findOneByamount(int $amount) Return the first ClientQuoting filtered by the amount column
 * @method ClientQuoting findOneBymkb(string $MKB) Return the first ClientQuoting filtered by the MKB column
 * @method ClientQuoting findOneBystatus(int $status) Return the first ClientQuoting filtered by the status column
 * @method ClientQuoting findOneByrequest(int $request) Return the first ClientQuoting filtered by the request column
 * @method ClientQuoting findOneBystatment(string $statment) Return the first ClientQuoting filtered by the statment column
 * @method ClientQuoting findOneBydateRegistration(string $dateRegistration) Return the first ClientQuoting filtered by the dateRegistration column
 * @method ClientQuoting findOneBydateEnd(string $dateEnd) Return the first ClientQuoting filtered by the dateEnd column
 * @method ClientQuoting findOneByorgStructureId(int $orgStructure_id) Return the first ClientQuoting filtered by the orgStructure_id column
 * @method ClientQuoting findOneByregionCode(string $regionCode) Return the first ClientQuoting filtered by the regionCode column
 * @method ClientQuoting findOneBypacientModelId(int $pacientModel_id) Return the first ClientQuoting filtered by the pacientModel_id column
 * @method ClientQuoting findOneBytreatmentId(int $treatment_id) Return the first ClientQuoting filtered by the treatment_id column
 * @method ClientQuoting findOneByeventId(int $event_id) Return the first ClientQuoting filtered by the event_id column
 * @method ClientQuoting findOneByprevTalonEventId(int $prevTalon_event_id) Return the first ClientQuoting filtered by the prevTalon_event_id column
 * @method ClientQuoting findOneByversion(int $version) Return the first ClientQuoting filtered by the version column
 *
 * @method array findById(int $id) Return ClientQuoting objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return ClientQuoting objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return ClientQuoting objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return ClientQuoting objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return ClientQuoting objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return ClientQuoting objects filtered by the deleted column
 * @method array findBymasterId(int $master_id) Return ClientQuoting objects filtered by the master_id column
 * @method array findByidentifier(string $identifier) Return ClientQuoting objects filtered by the identifier column
 * @method array findByquotaTicket(string $quotaTicket) Return ClientQuoting objects filtered by the quotaTicket column
 * @method array findByquotaTypeId(int $quotaType_id) Return ClientQuoting objects filtered by the quotaType_id column
 * @method array findBystage(int $stage) Return ClientQuoting objects filtered by the stage column
 * @method array findBydirectionDate(string $directionDate) Return ClientQuoting objects filtered by the directionDate column
 * @method array findByfreeInput(string $freeInput) Return ClientQuoting objects filtered by the freeInput column
 * @method array findByorgId(int $org_id) Return ClientQuoting objects filtered by the org_id column
 * @method array findByamount(int $amount) Return ClientQuoting objects filtered by the amount column
 * @method array findBymkb(string $MKB) Return ClientQuoting objects filtered by the MKB column
 * @method array findBystatus(int $status) Return ClientQuoting objects filtered by the status column
 * @method array findByrequest(int $request) Return ClientQuoting objects filtered by the request column
 * @method array findBystatment(string $statment) Return ClientQuoting objects filtered by the statment column
 * @method array findBydateRegistration(string $dateRegistration) Return ClientQuoting objects filtered by the dateRegistration column
 * @method array findBydateEnd(string $dateEnd) Return ClientQuoting objects filtered by the dateEnd column
 * @method array findByorgStructureId(int $orgStructure_id) Return ClientQuoting objects filtered by the orgStructure_id column
 * @method array findByregionCode(string $regionCode) Return ClientQuoting objects filtered by the regionCode column
 * @method array findBypacientModelId(int $pacientModel_id) Return ClientQuoting objects filtered by the pacientModel_id column
 * @method array findBytreatmentId(int $treatment_id) Return ClientQuoting objects filtered by the treatment_id column
 * @method array findByeventId(int $event_id) Return ClientQuoting objects filtered by the event_id column
 * @method array findByprevTalonEventId(int $prevTalon_event_id) Return ClientQuoting objects filtered by the prevTalon_event_id column
 * @method array findByversion(int $version) Return ClientQuoting objects filtered by the version column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseClientQuotingQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseClientQuotingQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ClientQuoting', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ClientQuotingQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ClientQuotingQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ClientQuotingQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ClientQuotingQuery) {
            return $criteria;
        }
        $query = new ClientQuotingQuery();
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
     * @return   ClientQuoting|ClientQuoting[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ClientQuotingPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ClientQuoting A model object, or null if the key is not found
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
     * @return                 ClientQuoting A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `master_id`, `identifier`, `quotaTicket`, `quotaType_id`, `stage`, `directionDate`, `freeInput`, `org_id`, `amount`, `MKB`, `status`, `request`, `statment`, `dateRegistration`, `dateEnd`, `orgStructure_id`, `regionCode`, `pacientModel_id`, `treatment_id`, `event_id`, `prevTalon_event_id`, `version` FROM `Client_Quoting` WHERE `id` = :p0';
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
            $obj = new ClientQuoting();
            $obj->hydrate($row);
            ClientQuotingPeer::addInstanceToPool($obj, (string) $key);
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
     * @return ClientQuoting|ClientQuoting[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ClientQuoting[]|mixed the list of results, formatted by the current formatter
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ClientQuotingPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ClientQuotingPeer::ID, $keys, Criteria::IN);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::ID, $id, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ClientQuotingPeer::DELETED, $deleted, $comparison);
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
     * @param     mixed $masterId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBymasterId($masterId = null, $comparison = null)
    {
        if (is_array($masterId)) {
            $useMinMax = false;
            if (isset($masterId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::MASTER_ID, $masterId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($masterId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::MASTER_ID, $masterId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::MASTER_ID, $masterId, $comparison);
    }

    /**
     * Filter the query on the identifier column
     *
     * Example usage:
     * <code>
     * $query->filterByidentifier('fooValue');   // WHERE identifier = 'fooValue'
     * $query->filterByidentifier('%fooValue%'); // WHERE identifier LIKE '%fooValue%'
     * </code>
     *
     * @param     string $identifier The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByidentifier($identifier = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($identifier)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $identifier)) {
                $identifier = str_replace('*', '%', $identifier);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::IDENTIFIER, $identifier, $comparison);
    }

    /**
     * Filter the query on the quotaTicket column
     *
     * Example usage:
     * <code>
     * $query->filterByquotaTicket('fooValue');   // WHERE quotaTicket = 'fooValue'
     * $query->filterByquotaTicket('%fooValue%'); // WHERE quotaTicket LIKE '%fooValue%'
     * </code>
     *
     * @param     string $quotaTicket The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByquotaTicket($quotaTicket = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($quotaTicket)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $quotaTicket)) {
                $quotaTicket = str_replace('*', '%', $quotaTicket);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::QUOTATICKET, $quotaTicket, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByquotaTypeId($quotaTypeId = null, $comparison = null)
    {
        if (is_array($quotaTypeId)) {
            $useMinMax = false;
            if (isset($quotaTypeId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::QUOTATYPE_ID, $quotaTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($quotaTypeId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::QUOTATYPE_ID, $quotaTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::QUOTATYPE_ID, $quotaTypeId, $comparison);
    }

    /**
     * Filter the query on the stage column
     *
     * Example usage:
     * <code>
     * $query->filterBystage(1234); // WHERE stage = 1234
     * $query->filterBystage(array(12, 34)); // WHERE stage IN (12, 34)
     * $query->filterBystage(array('min' => 12)); // WHERE stage >= 12
     * $query->filterBystage(array('max' => 12)); // WHERE stage <= 12
     * </code>
     *
     * @param     mixed $stage The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBystage($stage = null, $comparison = null)
    {
        if (is_array($stage)) {
            $useMinMax = false;
            if (isset($stage['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::STAGE, $stage['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($stage['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::STAGE, $stage['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::STAGE, $stage, $comparison);
    }

    /**
     * Filter the query on the directionDate column
     *
     * Example usage:
     * <code>
     * $query->filterBydirectionDate('2011-03-14'); // WHERE directionDate = '2011-03-14'
     * $query->filterBydirectionDate('now'); // WHERE directionDate = '2011-03-14'
     * $query->filterBydirectionDate(array('max' => 'yesterday')); // WHERE directionDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $directionDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBydirectionDate($directionDate = null, $comparison = null)
    {
        if (is_array($directionDate)) {
            $useMinMax = false;
            if (isset($directionDate['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::DIRECTIONDATE, $directionDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($directionDate['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::DIRECTIONDATE, $directionDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::DIRECTIONDATE, $directionDate, $comparison);
    }

    /**
     * Filter the query on the freeInput column
     *
     * Example usage:
     * <code>
     * $query->filterByfreeInput('fooValue');   // WHERE freeInput = 'fooValue'
     * $query->filterByfreeInput('%fooValue%'); // WHERE freeInput LIKE '%fooValue%'
     * </code>
     *
     * @param     string $freeInput The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByfreeInput($freeInput = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($freeInput)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $freeInput)) {
                $freeInput = str_replace('*', '%', $freeInput);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::FREEINPUT, $freeInput, $comparison);
    }

    /**
     * Filter the query on the org_id column
     *
     * Example usage:
     * <code>
     * $query->filterByorgId(1234); // WHERE org_id = 1234
     * $query->filterByorgId(array(12, 34)); // WHERE org_id IN (12, 34)
     * $query->filterByorgId(array('min' => 12)); // WHERE org_id >= 12
     * $query->filterByorgId(array('max' => 12)); // WHERE org_id <= 12
     * </code>
     *
     * @param     mixed $orgId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByorgId($orgId = null, $comparison = null)
    {
        if (is_array($orgId)) {
            $useMinMax = false;
            if (isset($orgId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::ORG_ID, $orgId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::ORG_ID, $orgId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::ORG_ID, $orgId, $comparison);
    }

    /**
     * Filter the query on the amount column
     *
     * Example usage:
     * <code>
     * $query->filterByamount(1234); // WHERE amount = 1234
     * $query->filterByamount(array(12, 34)); // WHERE amount IN (12, 34)
     * $query->filterByamount(array('min' => 12)); // WHERE amount >= 12
     * $query->filterByamount(array('max' => 12)); // WHERE amount <= 12
     * </code>
     *
     * @param     mixed $amount The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByamount($amount = null, $comparison = null)
    {
        if (is_array($amount)) {
            $useMinMax = false;
            if (isset($amount['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::AMOUNT, $amount['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($amount['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::AMOUNT, $amount['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::AMOUNT, $amount, $comparison);
    }

    /**
     * Filter the query on the MKB column
     *
     * Example usage:
     * <code>
     * $query->filterBymkb('fooValue');   // WHERE MKB = 'fooValue'
     * $query->filterBymkb('%fooValue%'); // WHERE MKB LIKE '%fooValue%'
     * </code>
     *
     * @param     string $mkb The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBymkb($mkb = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($mkb)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $mkb)) {
                $mkb = str_replace('*', '%', $mkb);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::MKB, $mkb, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBystatus($status = null, $comparison = null)
    {
        if (is_array($status)) {
            $useMinMax = false;
            if (isset($status['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::STATUS, $status['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($status['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::STATUS, $status['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::STATUS, $status, $comparison);
    }

    /**
     * Filter the query on the request column
     *
     * Example usage:
     * <code>
     * $query->filterByrequest(1234); // WHERE request = 1234
     * $query->filterByrequest(array(12, 34)); // WHERE request IN (12, 34)
     * $query->filterByrequest(array('min' => 12)); // WHERE request >= 12
     * $query->filterByrequest(array('max' => 12)); // WHERE request <= 12
     * </code>
     *
     * @param     mixed $request The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByrequest($request = null, $comparison = null)
    {
        if (is_array($request)) {
            $useMinMax = false;
            if (isset($request['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::REQUEST, $request['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($request['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::REQUEST, $request['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::REQUEST, $request, $comparison);
    }

    /**
     * Filter the query on the statment column
     *
     * Example usage:
     * <code>
     * $query->filterBystatment('fooValue');   // WHERE statment = 'fooValue'
     * $query->filterBystatment('%fooValue%'); // WHERE statment LIKE '%fooValue%'
     * </code>
     *
     * @param     string $statment The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBystatment($statment = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($statment)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $statment)) {
                $statment = str_replace('*', '%', $statment);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::STATMENT, $statment, $comparison);
    }

    /**
     * Filter the query on the dateRegistration column
     *
     * Example usage:
     * <code>
     * $query->filterBydateRegistration('2011-03-14'); // WHERE dateRegistration = '2011-03-14'
     * $query->filterBydateRegistration('now'); // WHERE dateRegistration = '2011-03-14'
     * $query->filterBydateRegistration(array('max' => 'yesterday')); // WHERE dateRegistration > '2011-03-13'
     * </code>
     *
     * @param     mixed $dateRegistration The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBydateRegistration($dateRegistration = null, $comparison = null)
    {
        if (is_array($dateRegistration)) {
            $useMinMax = false;
            if (isset($dateRegistration['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::DATEREGISTRATION, $dateRegistration['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dateRegistration['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::DATEREGISTRATION, $dateRegistration['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::DATEREGISTRATION, $dateRegistration, $comparison);
    }

    /**
     * Filter the query on the dateEnd column
     *
     * Example usage:
     * <code>
     * $query->filterBydateEnd('2011-03-14'); // WHERE dateEnd = '2011-03-14'
     * $query->filterBydateEnd('now'); // WHERE dateEnd = '2011-03-14'
     * $query->filterBydateEnd(array('max' => 'yesterday')); // WHERE dateEnd > '2011-03-13'
     * </code>
     *
     * @param     mixed $dateEnd The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBydateEnd($dateEnd = null, $comparison = null)
    {
        if (is_array($dateEnd)) {
            $useMinMax = false;
            if (isset($dateEnd['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::DATEEND, $dateEnd['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dateEnd['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::DATEEND, $dateEnd['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::DATEEND, $dateEnd, $comparison);
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
     * @param     mixed $orgStructureId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByorgStructureId($orgStructureId = null, $comparison = null)
    {
        if (is_array($orgStructureId)) {
            $useMinMax = false;
            if (isset($orgStructureId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::ORGSTRUCTURE_ID, $orgStructureId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgStructureId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::ORGSTRUCTURE_ID, $orgStructureId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::ORGSTRUCTURE_ID, $orgStructureId, $comparison);
    }

    /**
     * Filter the query on the regionCode column
     *
     * Example usage:
     * <code>
     * $query->filterByregionCode('fooValue');   // WHERE regionCode = 'fooValue'
     * $query->filterByregionCode('%fooValue%'); // WHERE regionCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $regionCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByregionCode($regionCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($regionCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $regionCode)) {
                $regionCode = str_replace('*', '%', $regionCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::REGIONCODE, $regionCode, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBypacientModelId($pacientModelId = null, $comparison = null)
    {
        if (is_array($pacientModelId)) {
            $useMinMax = false;
            if (isset($pacientModelId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::PACIENTMODEL_ID, $pacientModelId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($pacientModelId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::PACIENTMODEL_ID, $pacientModelId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::PACIENTMODEL_ID, $pacientModelId, $comparison);
    }

    /**
     * Filter the query on the treatment_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytreatmentId(1234); // WHERE treatment_id = 1234
     * $query->filterBytreatmentId(array(12, 34)); // WHERE treatment_id IN (12, 34)
     * $query->filterBytreatmentId(array('min' => 12)); // WHERE treatment_id >= 12
     * $query->filterBytreatmentId(array('max' => 12)); // WHERE treatment_id <= 12
     * </code>
     *
     * @see       filterByRbTreatment()
     *
     * @param     mixed $treatmentId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterBytreatmentId($treatmentId = null, $comparison = null)
    {
        if (is_array($treatmentId)) {
            $useMinMax = false;
            if (isset($treatmentId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::TREATMENT_ID, $treatmentId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($treatmentId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::TREATMENT_ID, $treatmentId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::TREATMENT_ID, $treatmentId, $comparison);
    }

    /**
     * Filter the query on the event_id column
     *
     * Example usage:
     * <code>
     * $query->filterByeventId(1234); // WHERE event_id = 1234
     * $query->filterByeventId(array(12, 34)); // WHERE event_id IN (12, 34)
     * $query->filterByeventId(array('min' => 12)); // WHERE event_id >= 12
     * $query->filterByeventId(array('max' => 12)); // WHERE event_id <= 12
     * </code>
     *
     * @param     mixed $eventId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByeventId($eventId = null, $comparison = null)
    {
        if (is_array($eventId)) {
            $useMinMax = false;
            if (isset($eventId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::EVENT_ID, $eventId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($eventId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::EVENT_ID, $eventId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::EVENT_ID, $eventId, $comparison);
    }

    /**
     * Filter the query on the prevTalon_event_id column
     *
     * Example usage:
     * <code>
     * $query->filterByprevTalonEventId(1234); // WHERE prevTalon_event_id = 1234
     * $query->filterByprevTalonEventId(array(12, 34)); // WHERE prevTalon_event_id IN (12, 34)
     * $query->filterByprevTalonEventId(array('min' => 12)); // WHERE prevTalon_event_id >= 12
     * $query->filterByprevTalonEventId(array('max' => 12)); // WHERE prevTalon_event_id <= 12
     * </code>
     *
     * @param     mixed $prevTalonEventId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByprevTalonEventId($prevTalonEventId = null, $comparison = null)
    {
        if (is_array($prevTalonEventId)) {
            $useMinMax = false;
            if (isset($prevTalonEventId['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::PREVTALON_EVENT_ID, $prevTalonEventId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($prevTalonEventId['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::PREVTALON_EVENT_ID, $prevTalonEventId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::PREVTALON_EVENT_ID, $prevTalonEventId, $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(ClientQuotingPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(ClientQuotingPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientQuotingPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query by a related RbTreatment object
     *
     * @param   RbTreatment|PropelObjectCollection $rbTreatment The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ClientQuotingQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbTreatment($rbTreatment, $comparison = null)
    {
        if ($rbTreatment instanceof RbTreatment) {
            return $this
                ->addUsingAlias(ClientQuotingPeer::TREATMENT_ID, $rbTreatment->getid(), $comparison);
        } elseif ($rbTreatment instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ClientQuotingPeer::TREATMENT_ID, $rbTreatment->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
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
     * Filter the query by a related RbPacientModel object
     *
     * @param   RbPacientModel|PropelObjectCollection $rbPacientModel The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ClientQuotingQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbPacientModel($rbPacientModel, $comparison = null)
    {
        if ($rbPacientModel instanceof RbPacientModel) {
            return $this
                ->addUsingAlias(ClientQuotingPeer::PACIENTMODEL_ID, $rbPacientModel->getid(), $comparison);
        } elseif ($rbPacientModel instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ClientQuotingPeer::PACIENTMODEL_ID, $rbPacientModel->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
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
     * Filter the query by a related QuotaType object
     *
     * @param   QuotaType|PropelObjectCollection $quotaType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ClientQuotingQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByQuotaType($quotaType, $comparison = null)
    {
        if ($quotaType instanceof QuotaType) {
            return $this
                ->addUsingAlias(ClientQuotingPeer::QUOTATYPE_ID, $quotaType->getid(), $comparison);
        } elseif ($quotaType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ClientQuotingPeer::QUOTATYPE_ID, $quotaType->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function joinQuotaType($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
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
    public function useQuotaTypeQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinQuotaType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'QuotaType', '\Webmis\Models\QuotaTypeQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   ClientQuoting $clientQuoting Object to remove from the list of results
     *
     * @return ClientQuotingQuery The current query, for fluid interface
     */
    public function prune($clientQuoting = null)
    {
        if ($clientQuoting) {
            $this->addUsingAlias(ClientQuotingPeer::ID, $clientQuoting->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(ClientQuotingPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(ClientQuotingPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(ClientQuotingPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(ClientQuotingPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(ClientQuotingPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     ClientQuotingQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(ClientQuotingPeer::CREATEDATETIME);
    }
}
