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
use Webmis\Models\Client;
use Webmis\Models\Event;
use Webmis\Models\EventPeer;
use Webmis\Models\EventQuery;
use Webmis\Models\EventType;
use Webmis\Models\OrgStructure;
use Webmis\Models\Person;

/**
 * Base class that represents a query for the 'Event' table.
 *
 *
 *
 * @method EventQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method EventQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method EventQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method EventQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method EventQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method EventQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method EventQuery orderByexternalId($order = Criteria::ASC) Order by the externalId column
 * @method EventQuery orderByeventTypeId($order = Criteria::ASC) Order by the eventType_id column
 * @method EventQuery orderByorgId($order = Criteria::ASC) Order by the org_id column
 * @method EventQuery orderByclientId($order = Criteria::ASC) Order by the client_id column
 * @method EventQuery orderBycontractId($order = Criteria::ASC) Order by the contract_id column
 * @method EventQuery orderByprevEventDate($order = Criteria::ASC) Order by the prevEventDate column
 * @method EventQuery orderBysetDate($order = Criteria::ASC) Order by the setDate column
 * @method EventQuery orderBysetPersonId($order = Criteria::ASC) Order by the setPerson_id column
 * @method EventQuery orderByexecDate($order = Criteria::ASC) Order by the execDate column
 * @method EventQuery orderByexecPersonId($order = Criteria::ASC) Order by the execPerson_id column
 * @method EventQuery orderByisPrimary($order = Criteria::ASC) Order by the isPrimary column
 * @method EventQuery orderByorder($order = Criteria::ASC) Order by the order column
 * @method EventQuery orderByresultId($order = Criteria::ASC) Order by the result_id column
 * @method EventQuery orderBynextEventDate($order = Criteria::ASC) Order by the nextEventDate column
 * @method EventQuery orderBypayStatus($order = Criteria::ASC) Order by the payStatus column
 * @method EventQuery orderBytypeAssetId($order = Criteria::ASC) Order by the typeAsset_id column
 * @method EventQuery orderBynote($order = Criteria::ASC) Order by the note column
 * @method EventQuery orderBycuratorId($order = Criteria::ASC) Order by the curator_id column
 * @method EventQuery orderByassistantId($order = Criteria::ASC) Order by the assistant_id column
 * @method EventQuery orderBypregnancyWeek($order = Criteria::ASC) Order by the pregnancyWeek column
 * @method EventQuery orderBymesId($order = Criteria::ASC) Order by the MES_id column
 * @method EventQuery orderBymesSpecificationId($order = Criteria::ASC) Order by the mesSpecification_id column
 * @method EventQuery orderByrbAcheResultId($order = Criteria::ASC) Order by the rbAcheResult_id column
 * @method EventQuery orderByversion($order = Criteria::ASC) Order by the version column
 * @method EventQuery orderByprivilege($order = Criteria::ASC) Order by the privilege column
 * @method EventQuery orderByurgent($order = Criteria::ASC) Order by the urgent column
 * @method EventQuery orderByorgStructureId($order = Criteria::ASC) Order by the orgStructure_id column
 * @method EventQuery orderByuuidId($order = Criteria::ASC) Order by the uuid_id column
 * @method EventQuery orderBylpuTransfer($order = Criteria::ASC) Order by the lpu_transfer column
 *
 * @method EventQuery groupByid() Group by the id column
 * @method EventQuery groupBycreateDatetime() Group by the createDatetime column
 * @method EventQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method EventQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method EventQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method EventQuery groupBydeleted() Group by the deleted column
 * @method EventQuery groupByexternalId() Group by the externalId column
 * @method EventQuery groupByeventTypeId() Group by the eventType_id column
 * @method EventQuery groupByorgId() Group by the org_id column
 * @method EventQuery groupByclientId() Group by the client_id column
 * @method EventQuery groupBycontractId() Group by the contract_id column
 * @method EventQuery groupByprevEventDate() Group by the prevEventDate column
 * @method EventQuery groupBysetDate() Group by the setDate column
 * @method EventQuery groupBysetPersonId() Group by the setPerson_id column
 * @method EventQuery groupByexecDate() Group by the execDate column
 * @method EventQuery groupByexecPersonId() Group by the execPerson_id column
 * @method EventQuery groupByisPrimary() Group by the isPrimary column
 * @method EventQuery groupByorder() Group by the order column
 * @method EventQuery groupByresultId() Group by the result_id column
 * @method EventQuery groupBynextEventDate() Group by the nextEventDate column
 * @method EventQuery groupBypayStatus() Group by the payStatus column
 * @method EventQuery groupBytypeAssetId() Group by the typeAsset_id column
 * @method EventQuery groupBynote() Group by the note column
 * @method EventQuery groupBycuratorId() Group by the curator_id column
 * @method EventQuery groupByassistantId() Group by the assistant_id column
 * @method EventQuery groupBypregnancyWeek() Group by the pregnancyWeek column
 * @method EventQuery groupBymesId() Group by the MES_id column
 * @method EventQuery groupBymesSpecificationId() Group by the mesSpecification_id column
 * @method EventQuery groupByrbAcheResultId() Group by the rbAcheResult_id column
 * @method EventQuery groupByversion() Group by the version column
 * @method EventQuery groupByprivilege() Group by the privilege column
 * @method EventQuery groupByurgent() Group by the urgent column
 * @method EventQuery groupByorgStructureId() Group by the orgStructure_id column
 * @method EventQuery groupByuuidId() Group by the uuid_id column
 * @method EventQuery groupBylpuTransfer() Group by the lpu_transfer column
 *
 * @method EventQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method EventQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method EventQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method EventQuery leftJoinEventType($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventType relation
 * @method EventQuery rightJoinEventType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventType relation
 * @method EventQuery innerJoinEventType($relationAlias = null) Adds a INNER JOIN clause to the query using the EventType relation
 *
 * @method EventQuery leftJoinClient($relationAlias = null) Adds a LEFT JOIN clause to the query using the Client relation
 * @method EventQuery rightJoinClient($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Client relation
 * @method EventQuery innerJoinClient($relationAlias = null) Adds a INNER JOIN clause to the query using the Client relation
 *
 * @method EventQuery leftJoinCreatePerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the CreatePerson relation
 * @method EventQuery rightJoinCreatePerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the CreatePerson relation
 * @method EventQuery innerJoinCreatePerson($relationAlias = null) Adds a INNER JOIN clause to the query using the CreatePerson relation
 *
 * @method EventQuery leftJoinModifyPerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the ModifyPerson relation
 * @method EventQuery rightJoinModifyPerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ModifyPerson relation
 * @method EventQuery innerJoinModifyPerson($relationAlias = null) Adds a INNER JOIN clause to the query using the ModifyPerson relation
 *
 * @method EventQuery leftJoinSetPerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the SetPerson relation
 * @method EventQuery rightJoinSetPerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the SetPerson relation
 * @method EventQuery innerJoinSetPerson($relationAlias = null) Adds a INNER JOIN clause to the query using the SetPerson relation
 *
 * @method EventQuery leftJoinDoctor($relationAlias = null) Adds a LEFT JOIN clause to the query using the Doctor relation
 * @method EventQuery rightJoinDoctor($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Doctor relation
 * @method EventQuery innerJoinDoctor($relationAlias = null) Adds a INNER JOIN clause to the query using the Doctor relation
 *
 * @method EventQuery leftJoinOrgStructure($relationAlias = null) Adds a LEFT JOIN clause to the query using the OrgStructure relation
 * @method EventQuery rightJoinOrgStructure($relationAlias = null) Adds a RIGHT JOIN clause to the query using the OrgStructure relation
 * @method EventQuery innerJoinOrgStructure($relationAlias = null) Adds a INNER JOIN clause to the query using the OrgStructure relation
 *
 * @method EventQuery leftJoinAction($relationAlias = null) Adds a LEFT JOIN clause to the query using the Action relation
 * @method EventQuery rightJoinAction($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Action relation
 * @method EventQuery innerJoinAction($relationAlias = null) Adds a INNER JOIN clause to the query using the Action relation
 *
 * @method Event findOne(PropelPDO $con = null) Return the first Event matching the query
 * @method Event findOneOrCreate(PropelPDO $con = null) Return the first Event matching the query, or a new Event object populated from the query conditions when no match is found
 *
 * @method Event findOneBycreateDatetime(string $createDatetime) Return the first Event filtered by the createDatetime column
 * @method Event findOneBycreatePersonId(int $createPerson_id) Return the first Event filtered by the createPerson_id column
 * @method Event findOneBymodifyDatetime(string $modifyDatetime) Return the first Event filtered by the modifyDatetime column
 * @method Event findOneBymodifyPersonId(int $modifyPerson_id) Return the first Event filtered by the modifyPerson_id column
 * @method Event findOneBydeleted(boolean $deleted) Return the first Event filtered by the deleted column
 * @method Event findOneByexternalId(string $externalId) Return the first Event filtered by the externalId column
 * @method Event findOneByeventTypeId(int $eventType_id) Return the first Event filtered by the eventType_id column
 * @method Event findOneByorgId(int $org_id) Return the first Event filtered by the org_id column
 * @method Event findOneByclientId(int $client_id) Return the first Event filtered by the client_id column
 * @method Event findOneBycontractId(int $contract_id) Return the first Event filtered by the contract_id column
 * @method Event findOneByprevEventDate(string $prevEventDate) Return the first Event filtered by the prevEventDate column
 * @method Event findOneBysetDate(string $setDate) Return the first Event filtered by the setDate column
 * @method Event findOneBysetPersonId(int $setPerson_id) Return the first Event filtered by the setPerson_id column
 * @method Event findOneByexecDate(string $execDate) Return the first Event filtered by the execDate column
 * @method Event findOneByexecPersonId(int $execPerson_id) Return the first Event filtered by the execPerson_id column
 * @method Event findOneByisPrimary(boolean $isPrimary) Return the first Event filtered by the isPrimary column
 * @method Event findOneByorder(boolean $order) Return the first Event filtered by the order column
 * @method Event findOneByresultId(int $result_id) Return the first Event filtered by the result_id column
 * @method Event findOneBynextEventDate(string $nextEventDate) Return the first Event filtered by the nextEventDate column
 * @method Event findOneBypayStatus(int $payStatus) Return the first Event filtered by the payStatus column
 * @method Event findOneBytypeAssetId(int $typeAsset_id) Return the first Event filtered by the typeAsset_id column
 * @method Event findOneBynote(string $note) Return the first Event filtered by the note column
 * @method Event findOneBycuratorId(int $curator_id) Return the first Event filtered by the curator_id column
 * @method Event findOneByassistantId(int $assistant_id) Return the first Event filtered by the assistant_id column
 * @method Event findOneBypregnancyWeek(int $pregnancyWeek) Return the first Event filtered by the pregnancyWeek column
 * @method Event findOneBymesId(int $MES_id) Return the first Event filtered by the MES_id column
 * @method Event findOneBymesSpecificationId(int $mesSpecification_id) Return the first Event filtered by the mesSpecification_id column
 * @method Event findOneByrbAcheResultId(int $rbAcheResult_id) Return the first Event filtered by the rbAcheResult_id column
 * @method Event findOneByversion(int $version) Return the first Event filtered by the version column
 * @method Event findOneByprivilege(boolean $privilege) Return the first Event filtered by the privilege column
 * @method Event findOneByurgent(boolean $urgent) Return the first Event filtered by the urgent column
 * @method Event findOneByorgStructureId(int $orgStructure_id) Return the first Event filtered by the orgStructure_id column
 * @method Event findOneByuuidId(int $uuid_id) Return the first Event filtered by the uuid_id column
 * @method Event findOneBylpuTransfer(string $lpu_transfer) Return the first Event filtered by the lpu_transfer column
 *
 * @method array findByid(int $id) Return Event objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return Event objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return Event objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return Event objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return Event objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return Event objects filtered by the deleted column
 * @method array findByexternalId(string $externalId) Return Event objects filtered by the externalId column
 * @method array findByeventTypeId(int $eventType_id) Return Event objects filtered by the eventType_id column
 * @method array findByorgId(int $org_id) Return Event objects filtered by the org_id column
 * @method array findByclientId(int $client_id) Return Event objects filtered by the client_id column
 * @method array findBycontractId(int $contract_id) Return Event objects filtered by the contract_id column
 * @method array findByprevEventDate(string $prevEventDate) Return Event objects filtered by the prevEventDate column
 * @method array findBysetDate(string $setDate) Return Event objects filtered by the setDate column
 * @method array findBysetPersonId(int $setPerson_id) Return Event objects filtered by the setPerson_id column
 * @method array findByexecDate(string $execDate) Return Event objects filtered by the execDate column
 * @method array findByexecPersonId(int $execPerson_id) Return Event objects filtered by the execPerson_id column
 * @method array findByisPrimary(boolean $isPrimary) Return Event objects filtered by the isPrimary column
 * @method array findByorder(boolean $order) Return Event objects filtered by the order column
 * @method array findByresultId(int $result_id) Return Event objects filtered by the result_id column
 * @method array findBynextEventDate(string $nextEventDate) Return Event objects filtered by the nextEventDate column
 * @method array findBypayStatus(int $payStatus) Return Event objects filtered by the payStatus column
 * @method array findBytypeAssetId(int $typeAsset_id) Return Event objects filtered by the typeAsset_id column
 * @method array findBynote(string $note) Return Event objects filtered by the note column
 * @method array findBycuratorId(int $curator_id) Return Event objects filtered by the curator_id column
 * @method array findByassistantId(int $assistant_id) Return Event objects filtered by the assistant_id column
 * @method array findBypregnancyWeek(int $pregnancyWeek) Return Event objects filtered by the pregnancyWeek column
 * @method array findBymesId(int $MES_id) Return Event objects filtered by the MES_id column
 * @method array findBymesSpecificationId(int $mesSpecification_id) Return Event objects filtered by the mesSpecification_id column
 * @method array findByrbAcheResultId(int $rbAcheResult_id) Return Event objects filtered by the rbAcheResult_id column
 * @method array findByversion(int $version) Return Event objects filtered by the version column
 * @method array findByprivilege(boolean $privilege) Return Event objects filtered by the privilege column
 * @method array findByurgent(boolean $urgent) Return Event objects filtered by the urgent column
 * @method array findByorgStructureId(int $orgStructure_id) Return Event objects filtered by the orgStructure_id column
 * @method array findByuuidId(int $uuid_id) Return Event objects filtered by the uuid_id column
 * @method array findBylpuTransfer(string $lpu_transfer) Return Event objects filtered by the lpu_transfer column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseEventQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseEventQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\Event', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new EventQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   EventQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return EventQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof EventQuery) {
            return $criteria;
        }
        $query = new EventQuery();
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
     * @return   Event|Event[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = EventPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Event A model object, or null if the key is not found
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
     * @return                 Event A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `externalId`, `eventType_id`, `org_id`, `client_id`, `contract_id`, `prevEventDate`, `setDate`, `setPerson_id`, `execDate`, `execPerson_id`, `isPrimary`, `order`, `result_id`, `nextEventDate`, `payStatus`, `typeAsset_id`, `note`, `curator_id`, `assistant_id`, `pregnancyWeek`, `MES_id`, `mesSpecification_id`, `rbAcheResult_id`, `version`, `privilege`, `urgent`, `orgStructure_id`, `uuid_id`, `lpu_transfer` FROM `Event` WHERE `id` = :p0';
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
            $obj = new Event();
            $obj->hydrate($row);
            EventPeer::addInstanceToPool($obj, (string) $key);
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
     * @return Event|Event[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|Event[]|mixed the list of results, formatted by the current formatter
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(EventPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(EventPeer::ID, $keys, Criteria::IN);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(EventPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(EventPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::ID, $id, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(EventPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(EventPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @see       filterByCreatePerson()
     *
     * @param     mixed $createPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(EventPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(EventPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(EventPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(EventPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @see       filterByModifyPerson()
     *
     * @param     mixed $modifyPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(EventPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(EventPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventPeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the externalId column
     *
     * Example usage:
     * <code>
     * $query->filterByexternalId('fooValue');   // WHERE externalId = 'fooValue'
     * $query->filterByexternalId('%fooValue%'); // WHERE externalId LIKE '%fooValue%'
     * </code>
     *
     * @param     string $externalId The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByexternalId($externalId = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($externalId)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $externalId)) {
                $externalId = str_replace('*', '%', $externalId);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventPeer::EXTERNALID, $externalId, $comparison);
    }

    /**
     * Filter the query on the eventType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByeventTypeId(1234); // WHERE eventType_id = 1234
     * $query->filterByeventTypeId(array(12, 34)); // WHERE eventType_id IN (12, 34)
     * $query->filterByeventTypeId(array('min' => 12)); // WHERE eventType_id >= 12
     * $query->filterByeventTypeId(array('max' => 12)); // WHERE eventType_id <= 12
     * </code>
     *
     * @see       filterByEventType()
     *
     * @param     mixed $eventTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByeventTypeId($eventTypeId = null, $comparison = null)
    {
        if (is_array($eventTypeId)) {
            $useMinMax = false;
            if (isset($eventTypeId['min'])) {
                $this->addUsingAlias(EventPeer::EVENTTYPE_ID, $eventTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($eventTypeId['max'])) {
                $this->addUsingAlias(EventPeer::EVENTTYPE_ID, $eventTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::EVENTTYPE_ID, $eventTypeId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByorgId($orgId = null, $comparison = null)
    {
        if (is_array($orgId)) {
            $useMinMax = false;
            if (isset($orgId['min'])) {
                $this->addUsingAlias(EventPeer::ORG_ID, $orgId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgId['max'])) {
                $this->addUsingAlias(EventPeer::ORG_ID, $orgId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::ORG_ID, $orgId, $comparison);
    }

    /**
     * Filter the query on the client_id column
     *
     * Example usage:
     * <code>
     * $query->filterByclientId(1234); // WHERE client_id = 1234
     * $query->filterByclientId(array(12, 34)); // WHERE client_id IN (12, 34)
     * $query->filterByclientId(array('min' => 12)); // WHERE client_id >= 12
     * $query->filterByclientId(array('max' => 12)); // WHERE client_id <= 12
     * </code>
     *
     * @see       filterByClient()
     *
     * @param     mixed $clientId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByclientId($clientId = null, $comparison = null)
    {
        if (is_array($clientId)) {
            $useMinMax = false;
            if (isset($clientId['min'])) {
                $this->addUsingAlias(EventPeer::CLIENT_ID, $clientId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($clientId['max'])) {
                $this->addUsingAlias(EventPeer::CLIENT_ID, $clientId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::CLIENT_ID, $clientId, $comparison);
    }

    /**
     * Filter the query on the contract_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycontractId(1234); // WHERE contract_id = 1234
     * $query->filterBycontractId(array(12, 34)); // WHERE contract_id IN (12, 34)
     * $query->filterBycontractId(array('min' => 12)); // WHERE contract_id >= 12
     * $query->filterBycontractId(array('max' => 12)); // WHERE contract_id <= 12
     * </code>
     *
     * @param     mixed $contractId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBycontractId($contractId = null, $comparison = null)
    {
        if (is_array($contractId)) {
            $useMinMax = false;
            if (isset($contractId['min'])) {
                $this->addUsingAlias(EventPeer::CONTRACT_ID, $contractId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($contractId['max'])) {
                $this->addUsingAlias(EventPeer::CONTRACT_ID, $contractId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::CONTRACT_ID, $contractId, $comparison);
    }

    /**
     * Filter the query on the prevEventDate column
     *
     * Example usage:
     * <code>
     * $query->filterByprevEventDate('2011-03-14'); // WHERE prevEventDate = '2011-03-14'
     * $query->filterByprevEventDate('now'); // WHERE prevEventDate = '2011-03-14'
     * $query->filterByprevEventDate(array('max' => 'yesterday')); // WHERE prevEventDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $prevEventDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByprevEventDate($prevEventDate = null, $comparison = null)
    {
        if (is_array($prevEventDate)) {
            $useMinMax = false;
            if (isset($prevEventDate['min'])) {
                $this->addUsingAlias(EventPeer::PREVEVENTDATE, $prevEventDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($prevEventDate['max'])) {
                $this->addUsingAlias(EventPeer::PREVEVENTDATE, $prevEventDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::PREVEVENTDATE, $prevEventDate, $comparison);
    }

    /**
     * Filter the query on the setDate column
     *
     * Example usage:
     * <code>
     * $query->filterBysetDate('2011-03-14'); // WHERE setDate = '2011-03-14'
     * $query->filterBysetDate('now'); // WHERE setDate = '2011-03-14'
     * $query->filterBysetDate(array('max' => 'yesterday')); // WHERE setDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $setDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBysetDate($setDate = null, $comparison = null)
    {
        if (is_array($setDate)) {
            $useMinMax = false;
            if (isset($setDate['min'])) {
                $this->addUsingAlias(EventPeer::SETDATE, $setDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($setDate['max'])) {
                $this->addUsingAlias(EventPeer::SETDATE, $setDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::SETDATE, $setDate, $comparison);
    }

    /**
     * Filter the query on the setPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBysetPersonId(1234); // WHERE setPerson_id = 1234
     * $query->filterBysetPersonId(array(12, 34)); // WHERE setPerson_id IN (12, 34)
     * $query->filterBysetPersonId(array('min' => 12)); // WHERE setPerson_id >= 12
     * $query->filterBysetPersonId(array('max' => 12)); // WHERE setPerson_id <= 12
     * </code>
     *
     * @see       filterBySetPerson()
     *
     * @param     mixed $setPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBysetPersonId($setPersonId = null, $comparison = null)
    {
        if (is_array($setPersonId)) {
            $useMinMax = false;
            if (isset($setPersonId['min'])) {
                $this->addUsingAlias(EventPeer::SETPERSON_ID, $setPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($setPersonId['max'])) {
                $this->addUsingAlias(EventPeer::SETPERSON_ID, $setPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::SETPERSON_ID, $setPersonId, $comparison);
    }

    /**
     * Filter the query on the execDate column
     *
     * Example usage:
     * <code>
     * $query->filterByexecDate('2011-03-14'); // WHERE execDate = '2011-03-14'
     * $query->filterByexecDate('now'); // WHERE execDate = '2011-03-14'
     * $query->filterByexecDate(array('max' => 'yesterday')); // WHERE execDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $execDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByexecDate($execDate = null, $comparison = null)
    {
        if (is_array($execDate)) {
            $useMinMax = false;
            if (isset($execDate['min'])) {
                $this->addUsingAlias(EventPeer::EXECDATE, $execDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($execDate['max'])) {
                $this->addUsingAlias(EventPeer::EXECDATE, $execDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::EXECDATE, $execDate, $comparison);
    }

    /**
     * Filter the query on the execPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterByexecPersonId(1234); // WHERE execPerson_id = 1234
     * $query->filterByexecPersonId(array(12, 34)); // WHERE execPerson_id IN (12, 34)
     * $query->filterByexecPersonId(array('min' => 12)); // WHERE execPerson_id >= 12
     * $query->filterByexecPersonId(array('max' => 12)); // WHERE execPerson_id <= 12
     * </code>
     *
     * @see       filterByDoctor()
     *
     * @param     mixed $execPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByexecPersonId($execPersonId = null, $comparison = null)
    {
        if (is_array($execPersonId)) {
            $useMinMax = false;
            if (isset($execPersonId['min'])) {
                $this->addUsingAlias(EventPeer::EXECPERSON_ID, $execPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($execPersonId['max'])) {
                $this->addUsingAlias(EventPeer::EXECPERSON_ID, $execPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::EXECPERSON_ID, $execPersonId, $comparison);
    }

    /**
     * Filter the query on the isPrimary column
     *
     * Example usage:
     * <code>
     * $query->filterByisPrimary(true); // WHERE isPrimary = true
     * $query->filterByisPrimary('yes'); // WHERE isPrimary = true
     * </code>
     *
     * @param     boolean|string $isPrimary The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByisPrimary($isPrimary = null, $comparison = null)
    {
        if (is_string($isPrimary)) {
            $isPrimary = in_array(strtolower($isPrimary), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventPeer::ISPRIMARY, $isPrimary, $comparison);
    }

    /**
     * Filter the query on the order column
     *
     * Example usage:
     * <code>
     * $query->filterByorder(true); // WHERE order = true
     * $query->filterByorder('yes'); // WHERE order = true
     * </code>
     *
     * @param     boolean|string $order The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByorder($order = null, $comparison = null)
    {
        if (is_string($order)) {
            $order = in_array(strtolower($order), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventPeer::ORDER, $order, $comparison);
    }

    /**
     * Filter the query on the result_id column
     *
     * Example usage:
     * <code>
     * $query->filterByresultId(1234); // WHERE result_id = 1234
     * $query->filterByresultId(array(12, 34)); // WHERE result_id IN (12, 34)
     * $query->filterByresultId(array('min' => 12)); // WHERE result_id >= 12
     * $query->filterByresultId(array('max' => 12)); // WHERE result_id <= 12
     * </code>
     *
     * @param     mixed $resultId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByresultId($resultId = null, $comparison = null)
    {
        if (is_array($resultId)) {
            $useMinMax = false;
            if (isset($resultId['min'])) {
                $this->addUsingAlias(EventPeer::RESULT_ID, $resultId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($resultId['max'])) {
                $this->addUsingAlias(EventPeer::RESULT_ID, $resultId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::RESULT_ID, $resultId, $comparison);
    }

    /**
     * Filter the query on the nextEventDate column
     *
     * Example usage:
     * <code>
     * $query->filterBynextEventDate('2011-03-14'); // WHERE nextEventDate = '2011-03-14'
     * $query->filterBynextEventDate('now'); // WHERE nextEventDate = '2011-03-14'
     * $query->filterBynextEventDate(array('max' => 'yesterday')); // WHERE nextEventDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $nextEventDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBynextEventDate($nextEventDate = null, $comparison = null)
    {
        if (is_array($nextEventDate)) {
            $useMinMax = false;
            if (isset($nextEventDate['min'])) {
                $this->addUsingAlias(EventPeer::NEXTEVENTDATE, $nextEventDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($nextEventDate['max'])) {
                $this->addUsingAlias(EventPeer::NEXTEVENTDATE, $nextEventDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::NEXTEVENTDATE, $nextEventDate, $comparison);
    }

    /**
     * Filter the query on the payStatus column
     *
     * Example usage:
     * <code>
     * $query->filterBypayStatus(1234); // WHERE payStatus = 1234
     * $query->filterBypayStatus(array(12, 34)); // WHERE payStatus IN (12, 34)
     * $query->filterBypayStatus(array('min' => 12)); // WHERE payStatus >= 12
     * $query->filterBypayStatus(array('max' => 12)); // WHERE payStatus <= 12
     * </code>
     *
     * @param     mixed $payStatus The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBypayStatus($payStatus = null, $comparison = null)
    {
        if (is_array($payStatus)) {
            $useMinMax = false;
            if (isset($payStatus['min'])) {
                $this->addUsingAlias(EventPeer::PAYSTATUS, $payStatus['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($payStatus['max'])) {
                $this->addUsingAlias(EventPeer::PAYSTATUS, $payStatus['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::PAYSTATUS, $payStatus, $comparison);
    }

    /**
     * Filter the query on the typeAsset_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytypeAssetId(1234); // WHERE typeAsset_id = 1234
     * $query->filterBytypeAssetId(array(12, 34)); // WHERE typeAsset_id IN (12, 34)
     * $query->filterBytypeAssetId(array('min' => 12)); // WHERE typeAsset_id >= 12
     * $query->filterBytypeAssetId(array('max' => 12)); // WHERE typeAsset_id <= 12
     * </code>
     *
     * @param     mixed $typeAssetId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBytypeAssetId($typeAssetId = null, $comparison = null)
    {
        if (is_array($typeAssetId)) {
            $useMinMax = false;
            if (isset($typeAssetId['min'])) {
                $this->addUsingAlias(EventPeer::TYPEASSET_ID, $typeAssetId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($typeAssetId['max'])) {
                $this->addUsingAlias(EventPeer::TYPEASSET_ID, $typeAssetId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::TYPEASSET_ID, $typeAssetId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
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

        return $this->addUsingAlias(EventPeer::NOTE, $note, $comparison);
    }

    /**
     * Filter the query on the curator_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycuratorId(1234); // WHERE curator_id = 1234
     * $query->filterBycuratorId(array(12, 34)); // WHERE curator_id IN (12, 34)
     * $query->filterBycuratorId(array('min' => 12)); // WHERE curator_id >= 12
     * $query->filterBycuratorId(array('max' => 12)); // WHERE curator_id <= 12
     * </code>
     *
     * @param     mixed $curatorId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBycuratorId($curatorId = null, $comparison = null)
    {
        if (is_array($curatorId)) {
            $useMinMax = false;
            if (isset($curatorId['min'])) {
                $this->addUsingAlias(EventPeer::CURATOR_ID, $curatorId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($curatorId['max'])) {
                $this->addUsingAlias(EventPeer::CURATOR_ID, $curatorId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::CURATOR_ID, $curatorId, $comparison);
    }

    /**
     * Filter the query on the assistant_id column
     *
     * Example usage:
     * <code>
     * $query->filterByassistantId(1234); // WHERE assistant_id = 1234
     * $query->filterByassistantId(array(12, 34)); // WHERE assistant_id IN (12, 34)
     * $query->filterByassistantId(array('min' => 12)); // WHERE assistant_id >= 12
     * $query->filterByassistantId(array('max' => 12)); // WHERE assistant_id <= 12
     * </code>
     *
     * @param     mixed $assistantId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByassistantId($assistantId = null, $comparison = null)
    {
        if (is_array($assistantId)) {
            $useMinMax = false;
            if (isset($assistantId['min'])) {
                $this->addUsingAlias(EventPeer::ASSISTANT_ID, $assistantId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($assistantId['max'])) {
                $this->addUsingAlias(EventPeer::ASSISTANT_ID, $assistantId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::ASSISTANT_ID, $assistantId, $comparison);
    }

    /**
     * Filter the query on the pregnancyWeek column
     *
     * Example usage:
     * <code>
     * $query->filterBypregnancyWeek(1234); // WHERE pregnancyWeek = 1234
     * $query->filterBypregnancyWeek(array(12, 34)); // WHERE pregnancyWeek IN (12, 34)
     * $query->filterBypregnancyWeek(array('min' => 12)); // WHERE pregnancyWeek >= 12
     * $query->filterBypregnancyWeek(array('max' => 12)); // WHERE pregnancyWeek <= 12
     * </code>
     *
     * @param     mixed $pregnancyWeek The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBypregnancyWeek($pregnancyWeek = null, $comparison = null)
    {
        if (is_array($pregnancyWeek)) {
            $useMinMax = false;
            if (isset($pregnancyWeek['min'])) {
                $this->addUsingAlias(EventPeer::PREGNANCYWEEK, $pregnancyWeek['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($pregnancyWeek['max'])) {
                $this->addUsingAlias(EventPeer::PREGNANCYWEEK, $pregnancyWeek['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::PREGNANCYWEEK, $pregnancyWeek, $comparison);
    }

    /**
     * Filter the query on the MES_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymesId(1234); // WHERE MES_id = 1234
     * $query->filterBymesId(array(12, 34)); // WHERE MES_id IN (12, 34)
     * $query->filterBymesId(array('min' => 12)); // WHERE MES_id >= 12
     * $query->filterBymesId(array('max' => 12)); // WHERE MES_id <= 12
     * </code>
     *
     * @param     mixed $mesId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBymesId($mesId = null, $comparison = null)
    {
        if (is_array($mesId)) {
            $useMinMax = false;
            if (isset($mesId['min'])) {
                $this->addUsingAlias(EventPeer::MES_ID, $mesId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($mesId['max'])) {
                $this->addUsingAlias(EventPeer::MES_ID, $mesId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::MES_ID, $mesId, $comparison);
    }

    /**
     * Filter the query on the mesSpecification_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymesSpecificationId(1234); // WHERE mesSpecification_id = 1234
     * $query->filterBymesSpecificationId(array(12, 34)); // WHERE mesSpecification_id IN (12, 34)
     * $query->filterBymesSpecificationId(array('min' => 12)); // WHERE mesSpecification_id >= 12
     * $query->filterBymesSpecificationId(array('max' => 12)); // WHERE mesSpecification_id <= 12
     * </code>
     *
     * @param     mixed $mesSpecificationId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBymesSpecificationId($mesSpecificationId = null, $comparison = null)
    {
        if (is_array($mesSpecificationId)) {
            $useMinMax = false;
            if (isset($mesSpecificationId['min'])) {
                $this->addUsingAlias(EventPeer::MESSPECIFICATION_ID, $mesSpecificationId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($mesSpecificationId['max'])) {
                $this->addUsingAlias(EventPeer::MESSPECIFICATION_ID, $mesSpecificationId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::MESSPECIFICATION_ID, $mesSpecificationId, $comparison);
    }

    /**
     * Filter the query on the rbAcheResult_id column
     *
     * Example usage:
     * <code>
     * $query->filterByrbAcheResultId(1234); // WHERE rbAcheResult_id = 1234
     * $query->filterByrbAcheResultId(array(12, 34)); // WHERE rbAcheResult_id IN (12, 34)
     * $query->filterByrbAcheResultId(array('min' => 12)); // WHERE rbAcheResult_id >= 12
     * $query->filterByrbAcheResultId(array('max' => 12)); // WHERE rbAcheResult_id <= 12
     * </code>
     *
     * @param     mixed $rbAcheResultId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByrbAcheResultId($rbAcheResultId = null, $comparison = null)
    {
        if (is_array($rbAcheResultId)) {
            $useMinMax = false;
            if (isset($rbAcheResultId['min'])) {
                $this->addUsingAlias(EventPeer::RBACHERESULT_ID, $rbAcheResultId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($rbAcheResultId['max'])) {
                $this->addUsingAlias(EventPeer::RBACHERESULT_ID, $rbAcheResultId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::RBACHERESULT_ID, $rbAcheResultId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(EventPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(EventPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query on the privilege column
     *
     * Example usage:
     * <code>
     * $query->filterByprivilege(true); // WHERE privilege = true
     * $query->filterByprivilege('yes'); // WHERE privilege = true
     * </code>
     *
     * @param     boolean|string $privilege The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByprivilege($privilege = null, $comparison = null)
    {
        if (is_string($privilege)) {
            $privilege = in_array(strtolower($privilege), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventPeer::PRIVILEGE, $privilege, $comparison);
    }

    /**
     * Filter the query on the urgent column
     *
     * Example usage:
     * <code>
     * $query->filterByurgent(true); // WHERE urgent = true
     * $query->filterByurgent('yes'); // WHERE urgent = true
     * </code>
     *
     * @param     boolean|string $urgent The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByurgent($urgent = null, $comparison = null)
    {
        if (is_string($urgent)) {
            $urgent = in_array(strtolower($urgent), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventPeer::URGENT, $urgent, $comparison);
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
     * @see       filterByOrgStructure()
     *
     * @param     mixed $orgStructureId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByorgStructureId($orgStructureId = null, $comparison = null)
    {
        if (is_array($orgStructureId)) {
            $useMinMax = false;
            if (isset($orgStructureId['min'])) {
                $this->addUsingAlias(EventPeer::ORGSTRUCTURE_ID, $orgStructureId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgStructureId['max'])) {
                $this->addUsingAlias(EventPeer::ORGSTRUCTURE_ID, $orgStructureId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::ORGSTRUCTURE_ID, $orgStructureId, $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function filterByuuidId($uuidId = null, $comparison = null)
    {
        if (is_array($uuidId)) {
            $useMinMax = false;
            if (isset($uuidId['min'])) {
                $this->addUsingAlias(EventPeer::UUID_ID, $uuidId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uuidId['max'])) {
                $this->addUsingAlias(EventPeer::UUID_ID, $uuidId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventPeer::UUID_ID, $uuidId, $comparison);
    }

    /**
     * Filter the query on the lpu_transfer column
     *
     * Example usage:
     * <code>
     * $query->filterBylpuTransfer('fooValue');   // WHERE lpu_transfer = 'fooValue'
     * $query->filterBylpuTransfer('%fooValue%'); // WHERE lpu_transfer LIKE '%fooValue%'
     * </code>
     *
     * @param     string $lpuTransfer The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function filterBylpuTransfer($lpuTransfer = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($lpuTransfer)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $lpuTransfer)) {
                $lpuTransfer = str_replace('*', '%', $lpuTransfer);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventPeer::LPU_TRANSFER, $lpuTransfer, $comparison);
    }

    /**
     * Filter the query by a related EventType object
     *
     * @param   EventType|PropelObjectCollection $eventType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventType($eventType, $comparison = null)
    {
        if ($eventType instanceof EventType) {
            return $this
                ->addUsingAlias(EventPeer::EVENTTYPE_ID, $eventType->getid(), $comparison);
        } elseif ($eventType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::EVENTTYPE_ID, $eventType->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return EventQuery The current query, for fluid interface
     */
    public function joinEventType($relationAlias = null, $joinType = Criteria::INNER_JOIN)
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
    public function useEventTypeQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinEventType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventType', '\Webmis\Models\EventTypeQuery');
    }

    /**
     * Filter the query by a related Client object
     *
     * @param   Client|PropelObjectCollection $client The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByClient($client, $comparison = null)
    {
        if ($client instanceof Client) {
            return $this
                ->addUsingAlias(EventPeer::CLIENT_ID, $client->getid(), $comparison);
        } elseif ($client instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::CLIENT_ID, $client->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByClient() only accepts arguments of type Client or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Client relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinClient($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Client');

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
            $this->addJoinObject($join, 'Client');
        }

        return $this;
    }

    /**
     * Use the Client relation Client object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ClientQuery A secondary query class using the current class as primary query
     */
    public function useClientQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinClient($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Client', '\Webmis\Models\ClientQuery');
    }

    /**
     * Filter the query by a related Person object
     *
     * @param   Person|PropelObjectCollection $person The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByCreatePerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(EventPeer::CREATEPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::CREATEPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByCreatePerson() only accepts arguments of type Person or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the CreatePerson relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinCreatePerson($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('CreatePerson');

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
            $this->addJoinObject($join, 'CreatePerson');
        }

        return $this;
    }

    /**
     * Use the CreatePerson relation Person object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\PersonQuery A secondary query class using the current class as primary query
     */
    public function useCreatePersonQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinCreatePerson($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'CreatePerson', '\Webmis\Models\PersonQuery');
    }

    /**
     * Filter the query by a related Person object
     *
     * @param   Person|PropelObjectCollection $person The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByModifyPerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(EventPeer::MODIFYPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::MODIFYPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByModifyPerson() only accepts arguments of type Person or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ModifyPerson relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinModifyPerson($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ModifyPerson');

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
            $this->addJoinObject($join, 'ModifyPerson');
        }

        return $this;
    }

    /**
     * Use the ModifyPerson relation Person object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\PersonQuery A secondary query class using the current class as primary query
     */
    public function useModifyPersonQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinModifyPerson($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ModifyPerson', '\Webmis\Models\PersonQuery');
    }

    /**
     * Filter the query by a related Person object
     *
     * @param   Person|PropelObjectCollection $person The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterBySetPerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(EventPeer::SETPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::SETPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterBySetPerson() only accepts arguments of type Person or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the SetPerson relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinSetPerson($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('SetPerson');

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
            $this->addJoinObject($join, 'SetPerson');
        }

        return $this;
    }

    /**
     * Use the SetPerson relation Person object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\PersonQuery A secondary query class using the current class as primary query
     */
    public function useSetPersonQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinSetPerson($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'SetPerson', '\Webmis\Models\PersonQuery');
    }

    /**
     * Filter the query by a related Person object
     *
     * @param   Person|PropelObjectCollection $person The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDoctor($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(EventPeer::EXECPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::EXECPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByDoctor() only accepts arguments of type Person or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Doctor relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinDoctor($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Doctor');

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
            $this->addJoinObject($join, 'Doctor');
        }

        return $this;
    }

    /**
     * Use the Doctor relation Person object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\PersonQuery A secondary query class using the current class as primary query
     */
    public function useDoctorQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinDoctor($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Doctor', '\Webmis\Models\PersonQuery');
    }

    /**
     * Filter the query by a related OrgStructure object
     *
     * @param   OrgStructure|PropelObjectCollection $orgStructure The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByOrgStructure($orgStructure, $comparison = null)
    {
        if ($orgStructure instanceof OrgStructure) {
            return $this
                ->addUsingAlias(EventPeer::ORGSTRUCTURE_ID, $orgStructure->getId(), $comparison);
        } elseif ($orgStructure instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventPeer::ORGSTRUCTURE_ID, $orgStructure->toKeyValue('PrimaryKey', 'Id'), $comparison);
        } else {
            throw new PropelException('filterByOrgStructure() only accepts arguments of type OrgStructure or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the OrgStructure relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function joinOrgStructure($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('OrgStructure');

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
            $this->addJoinObject($join, 'OrgStructure');
        }

        return $this;
    }

    /**
     * Use the OrgStructure relation OrgStructure object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\OrgStructureQuery A secondary query class using the current class as primary query
     */
    public function useOrgStructureQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinOrgStructure($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'OrgStructure', '\Webmis\Models\OrgStructureQuery');
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByAction($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(EventPeer::ID, $action->geteventId(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            return $this
                ->useActionQuery()
                ->filterByPrimaryKeys($action->getPrimaryKeys())
                ->endUse();
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
     * @return EventQuery The current query, for fluid interface
     */
    public function joinAction($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
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
    public function useActionQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinAction($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Action', '\Webmis\Models\ActionQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   Event $event Object to remove from the list of results
     *
     * @return EventQuery The current query, for fluid interface
     */
    public function prune($event = null)
    {
        if ($event) {
            $this->addUsingAlias(EventPeer::ID, $event->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(EventPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(EventPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(EventPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(EventPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(EventPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     EventQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(EventPeer::CREATEDATETIME);
    }
}
