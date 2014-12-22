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
use Webmis\Models\ActionPeer;
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionQuery;
use Webmis\Models\ActionType;
use Webmis\Models\DrugChart;
use Webmis\Models\DrugComponent;
use Webmis\Models\Event;
use Webmis\Models\Person;

/**
 * Base class that represents a query for the 'Action' table.
 *
 *
 *
 * @method ActionQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method ActionQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method ActionQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method ActionQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method ActionQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ActionQuery orderByactionTypeId($order = Criteria::ASC) Order by the actionType_id column
 * @method ActionQuery orderByeventId($order = Criteria::ASC) Order by the event_id column
 * @method ActionQuery orderByidx($order = Criteria::ASC) Order by the idx column
 * @method ActionQuery orderBydirectionDate($order = Criteria::ASC) Order by the directionDate column
 * @method ActionQuery orderBystatus($order = Criteria::ASC) Order by the status column
 * @method ActionQuery orderBysetPersonId($order = Criteria::ASC) Order by the setPerson_id column
 * @method ActionQuery orderByisUrgent($order = Criteria::ASC) Order by the isUrgent column
 * @method ActionQuery orderBybegDate($order = Criteria::ASC) Order by the begDate column
 * @method ActionQuery orderByplannedEndDate($order = Criteria::ASC) Order by the plannedEndDate column
 * @method ActionQuery orderByendDate($order = Criteria::ASC) Order by the endDate column
 * @method ActionQuery orderBynote($order = Criteria::ASC) Order by the note column
 * @method ActionQuery orderBypersonId($order = Criteria::ASC) Order by the person_id column
 * @method ActionQuery orderByoffice($order = Criteria::ASC) Order by the office column
 * @method ActionQuery orderByamount($order = Criteria::ASC) Order by the amount column
 * @method ActionQuery orderByuet($order = Criteria::ASC) Order by the uet column
 * @method ActionQuery orderByexpose($order = Criteria::ASC) Order by the expose column
 * @method ActionQuery orderBypayStatus($order = Criteria::ASC) Order by the payStatus column
 * @method ActionQuery orderByaccount($order = Criteria::ASC) Order by the account column
 * @method ActionQuery orderByfinanceId($order = Criteria::ASC) Order by the finance_id column
 * @method ActionQuery orderByprescriptionId($order = Criteria::ASC) Order by the prescription_id column
 * @method ActionQuery orderBytakenTissueJournalId($order = Criteria::ASC) Order by the takenTissueJournal_id column
 * @method ActionQuery orderBycontractId($order = Criteria::ASC) Order by the contract_id column
 * @method ActionQuery orderBycoordDate($order = Criteria::ASC) Order by the coordDate column
 * @method ActionQuery orderBycoordAgent($order = Criteria::ASC) Order by the coordAgent column
 * @method ActionQuery orderBycoordInspector($order = Criteria::ASC) Order by the coordInspector column
 * @method ActionQuery orderBycoordText($order = Criteria::ASC) Order by the coordText column
 * @method ActionQuery orderByhospitalUidFrom($order = Criteria::ASC) Order by the hospitalUidFrom column
 * @method ActionQuery orderBypacientInQueueType($order = Criteria::ASC) Order by the pacientInQueueType column
 * @method ActionQuery orderByappointmentType($order = Criteria::ASC) Order by the AppointmentType column
 * @method ActionQuery orderByversion($order = Criteria::ASC) Order by the version column
 * @method ActionQuery orderByparentActionId($order = Criteria::ASC) Order by the parentAction_id column
 * @method ActionQuery orderByuuidId($order = Criteria::ASC) Order by the uuid_id column
 * @method ActionQuery orderBydcmStudyUid($order = Criteria::ASC) Order by the dcm_study_uid column
 *
 * @method ActionQuery groupByid() Group by the id column
 * @method ActionQuery groupBycreateDatetime() Group by the createDatetime column
 * @method ActionQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method ActionQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method ActionQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method ActionQuery groupBydeleted() Group by the deleted column
 * @method ActionQuery groupByactionTypeId() Group by the actionType_id column
 * @method ActionQuery groupByeventId() Group by the event_id column
 * @method ActionQuery groupByidx() Group by the idx column
 * @method ActionQuery groupBydirectionDate() Group by the directionDate column
 * @method ActionQuery groupBystatus() Group by the status column
 * @method ActionQuery groupBysetPersonId() Group by the setPerson_id column
 * @method ActionQuery groupByisUrgent() Group by the isUrgent column
 * @method ActionQuery groupBybegDate() Group by the begDate column
 * @method ActionQuery groupByplannedEndDate() Group by the plannedEndDate column
 * @method ActionQuery groupByendDate() Group by the endDate column
 * @method ActionQuery groupBynote() Group by the note column
 * @method ActionQuery groupBypersonId() Group by the person_id column
 * @method ActionQuery groupByoffice() Group by the office column
 * @method ActionQuery groupByamount() Group by the amount column
 * @method ActionQuery groupByuet() Group by the uet column
 * @method ActionQuery groupByexpose() Group by the expose column
 * @method ActionQuery groupBypayStatus() Group by the payStatus column
 * @method ActionQuery groupByaccount() Group by the account column
 * @method ActionQuery groupByfinanceId() Group by the finance_id column
 * @method ActionQuery groupByprescriptionId() Group by the prescription_id column
 * @method ActionQuery groupBytakenTissueJournalId() Group by the takenTissueJournal_id column
 * @method ActionQuery groupBycontractId() Group by the contract_id column
 * @method ActionQuery groupBycoordDate() Group by the coordDate column
 * @method ActionQuery groupBycoordAgent() Group by the coordAgent column
 * @method ActionQuery groupBycoordInspector() Group by the coordInspector column
 * @method ActionQuery groupBycoordText() Group by the coordText column
 * @method ActionQuery groupByhospitalUidFrom() Group by the hospitalUidFrom column
 * @method ActionQuery groupBypacientInQueueType() Group by the pacientInQueueType column
 * @method ActionQuery groupByappointmentType() Group by the AppointmentType column
 * @method ActionQuery groupByversion() Group by the version column
 * @method ActionQuery groupByparentActionId() Group by the parentAction_id column
 * @method ActionQuery groupByuuidId() Group by the uuid_id column
 * @method ActionQuery groupBydcmStudyUid() Group by the dcm_study_uid column
 *
 * @method ActionQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionQuery leftJoinEvent($relationAlias = null) Adds a LEFT JOIN clause to the query using the Event relation
 * @method ActionQuery rightJoinEvent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Event relation
 * @method ActionQuery innerJoinEvent($relationAlias = null) Adds a INNER JOIN clause to the query using the Event relation
 *
 * @method ActionQuery leftJoinCreatePerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the CreatePerson relation
 * @method ActionQuery rightJoinCreatePerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the CreatePerson relation
 * @method ActionQuery innerJoinCreatePerson($relationAlias = null) Adds a INNER JOIN clause to the query using the CreatePerson relation
 *
 * @method ActionQuery leftJoinModifyPerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the ModifyPerson relation
 * @method ActionQuery rightJoinModifyPerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ModifyPerson relation
 * @method ActionQuery innerJoinModifyPerson($relationAlias = null) Adds a INNER JOIN clause to the query using the ModifyPerson relation
 *
 * @method ActionQuery leftJoinSetPerson($relationAlias = null) Adds a LEFT JOIN clause to the query using the SetPerson relation
 * @method ActionQuery rightJoinSetPerson($relationAlias = null) Adds a RIGHT JOIN clause to the query using the SetPerson relation
 * @method ActionQuery innerJoinSetPerson($relationAlias = null) Adds a INNER JOIN clause to the query using the SetPerson relation
 *
 * @method ActionQuery leftJoinActionType($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionType relation
 * @method ActionQuery rightJoinActionType($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionType relation
 * @method ActionQuery innerJoinActionType($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionType relation
 *
 * @method ActionQuery leftJoinActionProperty($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionProperty relation
 * @method ActionQuery rightJoinActionProperty($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionProperty relation
 * @method ActionQuery innerJoinActionProperty($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionProperty relation
 *
 * @method ActionQuery leftJoinDrugChart($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugChart relation
 * @method ActionQuery rightJoinDrugChart($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugChart relation
 * @method ActionQuery innerJoinDrugChart($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugChart relation
 *
 * @method ActionQuery leftJoinDrugComponent($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugComponent relation
 * @method ActionQuery rightJoinDrugComponent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugComponent relation
 * @method ActionQuery innerJoinDrugComponent($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugComponent relation
 *
 * @method Action findOne(PropelPDO $con = null) Return the first Action matching the query
 * @method Action findOneOrCreate(PropelPDO $con = null) Return the first Action matching the query, or a new Action object populated from the query conditions when no match is found
 *
 * @method Action findOneBycreateDatetime(string $createDatetime) Return the first Action filtered by the createDatetime column
 * @method Action findOneBycreatePersonId(int $createPerson_id) Return the first Action filtered by the createPerson_id column
 * @method Action findOneBymodifyDatetime(string $modifyDatetime) Return the first Action filtered by the modifyDatetime column
 * @method Action findOneBymodifyPersonId(int $modifyPerson_id) Return the first Action filtered by the modifyPerson_id column
 * @method Action findOneBydeleted(boolean $deleted) Return the first Action filtered by the deleted column
 * @method Action findOneByactionTypeId(int $actionType_id) Return the first Action filtered by the actionType_id column
 * @method Action findOneByeventId(int $event_id) Return the first Action filtered by the event_id column
 * @method Action findOneByidx(int $idx) Return the first Action filtered by the idx column
 * @method Action findOneBydirectionDate(string $directionDate) Return the first Action filtered by the directionDate column
 * @method Action findOneBystatus(int $status) Return the first Action filtered by the status column
 * @method Action findOneBysetPersonId(int $setPerson_id) Return the first Action filtered by the setPerson_id column
 * @method Action findOneByisUrgent(boolean $isUrgent) Return the first Action filtered by the isUrgent column
 * @method Action findOneBybegDate(string $begDate) Return the first Action filtered by the begDate column
 * @method Action findOneByplannedEndDate(string $plannedEndDate) Return the first Action filtered by the plannedEndDate column
 * @method Action findOneByendDate(string $endDate) Return the first Action filtered by the endDate column
 * @method Action findOneBynote(string $note) Return the first Action filtered by the note column
 * @method Action findOneBypersonId(int $person_id) Return the first Action filtered by the person_id column
 * @method Action findOneByoffice(string $office) Return the first Action filtered by the office column
 * @method Action findOneByamount(double $amount) Return the first Action filtered by the amount column
 * @method Action findOneByuet(double $uet) Return the first Action filtered by the uet column
 * @method Action findOneByexpose(int $expose) Return the first Action filtered by the expose column
 * @method Action findOneBypayStatus(int $payStatus) Return the first Action filtered by the payStatus column
 * @method Action findOneByaccount(boolean $account) Return the first Action filtered by the account column
 * @method Action findOneByfinanceId(int $finance_id) Return the first Action filtered by the finance_id column
 * @method Action findOneByprescriptionId(int $prescription_id) Return the first Action filtered by the prescription_id column
 * @method Action findOneBytakenTissueJournalId(int $takenTissueJournal_id) Return the first Action filtered by the takenTissueJournal_id column
 * @method Action findOneBycontractId(int $contract_id) Return the first Action filtered by the contract_id column
 * @method Action findOneBycoordDate(string $coordDate) Return the first Action filtered by the coordDate column
 * @method Action findOneBycoordAgent(string $coordAgent) Return the first Action filtered by the coordAgent column
 * @method Action findOneBycoordInspector(string $coordInspector) Return the first Action filtered by the coordInspector column
 * @method Action findOneBycoordText(string $coordText) Return the first Action filtered by the coordText column
 * @method Action findOneByhospitalUidFrom(string $hospitalUidFrom) Return the first Action filtered by the hospitalUidFrom column
 * @method Action findOneBypacientInQueueType(int $pacientInQueueType) Return the first Action filtered by the pacientInQueueType column
 * @method Action findOneByappointmentType(string $AppointmentType) Return the first Action filtered by the AppointmentType column
 * @method Action findOneByversion(int $version) Return the first Action filtered by the version column
 * @method Action findOneByparentActionId(int $parentAction_id) Return the first Action filtered by the parentAction_id column
 * @method Action findOneByuuidId(int $uuid_id) Return the first Action filtered by the uuid_id column
 * @method Action findOneBydcmStudyUid(string $dcm_study_uid) Return the first Action filtered by the dcm_study_uid column
 *
 * @method array findByid(int $id) Return Action objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return Action objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return Action objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return Action objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return Action objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return Action objects filtered by the deleted column
 * @method array findByactionTypeId(int $actionType_id) Return Action objects filtered by the actionType_id column
 * @method array findByeventId(int $event_id) Return Action objects filtered by the event_id column
 * @method array findByidx(int $idx) Return Action objects filtered by the idx column
 * @method array findBydirectionDate(string $directionDate) Return Action objects filtered by the directionDate column
 * @method array findBystatus(int $status) Return Action objects filtered by the status column
 * @method array findBysetPersonId(int $setPerson_id) Return Action objects filtered by the setPerson_id column
 * @method array findByisUrgent(boolean $isUrgent) Return Action objects filtered by the isUrgent column
 * @method array findBybegDate(string $begDate) Return Action objects filtered by the begDate column
 * @method array findByplannedEndDate(string $plannedEndDate) Return Action objects filtered by the plannedEndDate column
 * @method array findByendDate(string $endDate) Return Action objects filtered by the endDate column
 * @method array findBynote(string $note) Return Action objects filtered by the note column
 * @method array findBypersonId(int $person_id) Return Action objects filtered by the person_id column
 * @method array findByoffice(string $office) Return Action objects filtered by the office column
 * @method array findByamount(double $amount) Return Action objects filtered by the amount column
 * @method array findByuet(double $uet) Return Action objects filtered by the uet column
 * @method array findByexpose(int $expose) Return Action objects filtered by the expose column
 * @method array findBypayStatus(int $payStatus) Return Action objects filtered by the payStatus column
 * @method array findByaccount(boolean $account) Return Action objects filtered by the account column
 * @method array findByfinanceId(int $finance_id) Return Action objects filtered by the finance_id column
 * @method array findByprescriptionId(int $prescription_id) Return Action objects filtered by the prescription_id column
 * @method array findBytakenTissueJournalId(int $takenTissueJournal_id) Return Action objects filtered by the takenTissueJournal_id column
 * @method array findBycontractId(int $contract_id) Return Action objects filtered by the contract_id column
 * @method array findBycoordDate(string $coordDate) Return Action objects filtered by the coordDate column
 * @method array findBycoordAgent(string $coordAgent) Return Action objects filtered by the coordAgent column
 * @method array findBycoordInspector(string $coordInspector) Return Action objects filtered by the coordInspector column
 * @method array findBycoordText(string $coordText) Return Action objects filtered by the coordText column
 * @method array findByhospitalUidFrom(string $hospitalUidFrom) Return Action objects filtered by the hospitalUidFrom column
 * @method array findBypacientInQueueType(int $pacientInQueueType) Return Action objects filtered by the pacientInQueueType column
 * @method array findByappointmentType(string $AppointmentType) Return Action objects filtered by the AppointmentType column
 * @method array findByversion(int $version) Return Action objects filtered by the version column
 * @method array findByparentActionId(int $parentAction_id) Return Action objects filtered by the parentAction_id column
 * @method array findByuuidId(int $uuid_id) Return Action objects filtered by the uuid_id column
 * @method array findBydcmStudyUid(string $dcm_study_uid) Return Action objects filtered by the dcm_study_uid column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\Action', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionQuery) {
            return $criteria;
        }
        $query = new ActionQuery();
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
     * @return   Action|Action[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Action A model object, or null if the key is not found
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
     * @return                 Action A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `AppointmentType`, `version`, `parentAction_id`, `uuid_id`, `dcm_study_uid` FROM `Action` WHERE `id` = :p0';
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
            $obj = new Action();
            $obj->hydrate($row);
            ActionPeer::addInstanceToPool($obj, (string) $key);
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
     * @return Action|Action[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|Action[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ActionPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ActionPeer::ID, $keys, Criteria::IN);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::ID, $id, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(ActionPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(ActionPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(ActionPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(ActionPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(ActionPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(ActionPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(ActionPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(ActionPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the actionType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByactionTypeId(1234); // WHERE actionType_id = 1234
     * $query->filterByactionTypeId(array(12, 34)); // WHERE actionType_id IN (12, 34)
     * $query->filterByactionTypeId(array('min' => 12)); // WHERE actionType_id >= 12
     * $query->filterByactionTypeId(array('max' => 12)); // WHERE actionType_id <= 12
     * </code>
     *
     * @see       filterByActionType()
     *
     * @param     mixed $actionTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByactionTypeId($actionTypeId = null, $comparison = null)
    {
        if (is_array($actionTypeId)) {
            $useMinMax = false;
            if (isset($actionTypeId['min'])) {
                $this->addUsingAlias(ActionPeer::ACTIONTYPE_ID, $actionTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actionTypeId['max'])) {
                $this->addUsingAlias(ActionPeer::ACTIONTYPE_ID, $actionTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::ACTIONTYPE_ID, $actionTypeId, $comparison);
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
     * @see       filterByEvent()
     *
     * @param     mixed $eventId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByeventId($eventId = null, $comparison = null)
    {
        if (is_array($eventId)) {
            $useMinMax = false;
            if (isset($eventId['min'])) {
                $this->addUsingAlias(ActionPeer::EVENT_ID, $eventId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($eventId['max'])) {
                $this->addUsingAlias(ActionPeer::EVENT_ID, $eventId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::EVENT_ID, $eventId, $comparison);
    }

    /**
     * Filter the query on the idx column
     *
     * Example usage:
     * <code>
     * $query->filterByidx(1234); // WHERE idx = 1234
     * $query->filterByidx(array(12, 34)); // WHERE idx IN (12, 34)
     * $query->filterByidx(array('min' => 12)); // WHERE idx >= 12
     * $query->filterByidx(array('max' => 12)); // WHERE idx <= 12
     * </code>
     *
     * @param     mixed $idx The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByidx($idx = null, $comparison = null)
    {
        if (is_array($idx)) {
            $useMinMax = false;
            if (isset($idx['min'])) {
                $this->addUsingAlias(ActionPeer::IDX, $idx['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($idx['max'])) {
                $this->addUsingAlias(ActionPeer::IDX, $idx['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::IDX, $idx, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBydirectionDate($directionDate = null, $comparison = null)
    {
        if (is_array($directionDate)) {
            $useMinMax = false;
            if (isset($directionDate['min'])) {
                $this->addUsingAlias(ActionPeer::DIRECTIONDATE, $directionDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($directionDate['max'])) {
                $this->addUsingAlias(ActionPeer::DIRECTIONDATE, $directionDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::DIRECTIONDATE, $directionDate, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBystatus($status = null, $comparison = null)
    {
        if (is_array($status)) {
            $useMinMax = false;
            if (isset($status['min'])) {
                $this->addUsingAlias(ActionPeer::STATUS, $status['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($status['max'])) {
                $this->addUsingAlias(ActionPeer::STATUS, $status['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::STATUS, $status, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBysetPersonId($setPersonId = null, $comparison = null)
    {
        if (is_array($setPersonId)) {
            $useMinMax = false;
            if (isset($setPersonId['min'])) {
                $this->addUsingAlias(ActionPeer::SETPERSON_ID, $setPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($setPersonId['max'])) {
                $this->addUsingAlias(ActionPeer::SETPERSON_ID, $setPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::SETPERSON_ID, $setPersonId, $comparison);
    }

    /**
     * Filter the query on the isUrgent column
     *
     * Example usage:
     * <code>
     * $query->filterByisUrgent(true); // WHERE isUrgent = true
     * $query->filterByisUrgent('yes'); // WHERE isUrgent = true
     * </code>
     *
     * @param     boolean|string $isUrgent The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByisUrgent($isUrgent = null, $comparison = null)
    {
        if (is_string($isUrgent)) {
            $isUrgent = in_array(strtolower($isUrgent), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPeer::ISURGENT, $isUrgent, $comparison);
    }

    /**
     * Filter the query on the begDate column
     *
     * Example usage:
     * <code>
     * $query->filterBybegDate('2011-03-14'); // WHERE begDate = '2011-03-14'
     * $query->filterBybegDate('now'); // WHERE begDate = '2011-03-14'
     * $query->filterBybegDate(array('max' => 'yesterday')); // WHERE begDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $begDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBybegDate($begDate = null, $comparison = null)
    {
        if (is_array($begDate)) {
            $useMinMax = false;
            if (isset($begDate['min'])) {
                $this->addUsingAlias(ActionPeer::BEGDATE, $begDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($begDate['max'])) {
                $this->addUsingAlias(ActionPeer::BEGDATE, $begDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::BEGDATE, $begDate, $comparison);
    }

    /**
     * Filter the query on the plannedEndDate column
     *
     * Example usage:
     * <code>
     * $query->filterByplannedEndDate('2011-03-14'); // WHERE plannedEndDate = '2011-03-14'
     * $query->filterByplannedEndDate('now'); // WHERE plannedEndDate = '2011-03-14'
     * $query->filterByplannedEndDate(array('max' => 'yesterday')); // WHERE plannedEndDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $plannedEndDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByplannedEndDate($plannedEndDate = null, $comparison = null)
    {
        if (is_array($plannedEndDate)) {
            $useMinMax = false;
            if (isset($plannedEndDate['min'])) {
                $this->addUsingAlias(ActionPeer::PLANNEDENDDATE, $plannedEndDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($plannedEndDate['max'])) {
                $this->addUsingAlias(ActionPeer::PLANNEDENDDATE, $plannedEndDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PLANNEDENDDATE, $plannedEndDate, $comparison);
    }

    /**
     * Filter the query on the endDate column
     *
     * Example usage:
     * <code>
     * $query->filterByendDate('2011-03-14'); // WHERE endDate = '2011-03-14'
     * $query->filterByendDate('now'); // WHERE endDate = '2011-03-14'
     * $query->filterByendDate(array('max' => 'yesterday')); // WHERE endDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $endDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByendDate($endDate = null, $comparison = null)
    {
        if (is_array($endDate)) {
            $useMinMax = false;
            if (isset($endDate['min'])) {
                $this->addUsingAlias(ActionPeer::ENDDATE, $endDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($endDate['max'])) {
                $this->addUsingAlias(ActionPeer::ENDDATE, $endDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::ENDDATE, $endDate, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionPeer::NOTE, $note, $comparison);
    }

    /**
     * Filter the query on the person_id column
     *
     * Example usage:
     * <code>
     * $query->filterBypersonId(1234); // WHERE person_id = 1234
     * $query->filterBypersonId(array(12, 34)); // WHERE person_id IN (12, 34)
     * $query->filterBypersonId(array('min' => 12)); // WHERE person_id >= 12
     * $query->filterBypersonId(array('max' => 12)); // WHERE person_id <= 12
     * </code>
     *
     * @param     mixed $personId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBypersonId($personId = null, $comparison = null)
    {
        if (is_array($personId)) {
            $useMinMax = false;
            if (isset($personId['min'])) {
                $this->addUsingAlias(ActionPeer::PERSON_ID, $personId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($personId['max'])) {
                $this->addUsingAlias(ActionPeer::PERSON_ID, $personId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PERSON_ID, $personId, $comparison);
    }

    /**
     * Filter the query on the office column
     *
     * Example usage:
     * <code>
     * $query->filterByoffice('fooValue');   // WHERE office = 'fooValue'
     * $query->filterByoffice('%fooValue%'); // WHERE office LIKE '%fooValue%'
     * </code>
     *
     * @param     string $office The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByoffice($office = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($office)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $office)) {
                $office = str_replace('*', '%', $office);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::OFFICE, $office, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByamount($amount = null, $comparison = null)
    {
        if (is_array($amount)) {
            $useMinMax = false;
            if (isset($amount['min'])) {
                $this->addUsingAlias(ActionPeer::AMOUNT, $amount['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($amount['max'])) {
                $this->addUsingAlias(ActionPeer::AMOUNT, $amount['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::AMOUNT, $amount, $comparison);
    }

    /**
     * Filter the query on the uet column
     *
     * Example usage:
     * <code>
     * $query->filterByuet(1234); // WHERE uet = 1234
     * $query->filterByuet(array(12, 34)); // WHERE uet IN (12, 34)
     * $query->filterByuet(array('min' => 12)); // WHERE uet >= 12
     * $query->filterByuet(array('max' => 12)); // WHERE uet <= 12
     * </code>
     *
     * @param     mixed $uet The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByuet($uet = null, $comparison = null)
    {
        if (is_array($uet)) {
            $useMinMax = false;
            if (isset($uet['min'])) {
                $this->addUsingAlias(ActionPeer::UET, $uet['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uet['max'])) {
                $this->addUsingAlias(ActionPeer::UET, $uet['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::UET, $uet, $comparison);
    }

    /**
     * Filter the query on the expose column
     *
     * Example usage:
     * <code>
     * $query->filterByexpose(1234); // WHERE expose = 1234
     * $query->filterByexpose(array(12, 34)); // WHERE expose IN (12, 34)
     * $query->filterByexpose(array('min' => 12)); // WHERE expose >= 12
     * $query->filterByexpose(array('max' => 12)); // WHERE expose <= 12
     * </code>
     *
     * @param     mixed $expose The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByexpose($expose = null, $comparison = null)
    {
        if (is_array($expose)) {
            $useMinMax = false;
            if (isset($expose['min'])) {
                $this->addUsingAlias(ActionPeer::EXPOSE, $expose['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($expose['max'])) {
                $this->addUsingAlias(ActionPeer::EXPOSE, $expose['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::EXPOSE, $expose, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBypayStatus($payStatus = null, $comparison = null)
    {
        if (is_array($payStatus)) {
            $useMinMax = false;
            if (isset($payStatus['min'])) {
                $this->addUsingAlias(ActionPeer::PAYSTATUS, $payStatus['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($payStatus['max'])) {
                $this->addUsingAlias(ActionPeer::PAYSTATUS, $payStatus['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PAYSTATUS, $payStatus, $comparison);
    }

    /**
     * Filter the query on the account column
     *
     * Example usage:
     * <code>
     * $query->filterByaccount(true); // WHERE account = true
     * $query->filterByaccount('yes'); // WHERE account = true
     * </code>
     *
     * @param     boolean|string $account The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByaccount($account = null, $comparison = null)
    {
        if (is_string($account)) {
            $account = in_array(strtolower($account), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionPeer::ACCOUNT, $account, $comparison);
    }

    /**
     * Filter the query on the finance_id column
     *
     * Example usage:
     * <code>
     * $query->filterByfinanceId(1234); // WHERE finance_id = 1234
     * $query->filterByfinanceId(array(12, 34)); // WHERE finance_id IN (12, 34)
     * $query->filterByfinanceId(array('min' => 12)); // WHERE finance_id >= 12
     * $query->filterByfinanceId(array('max' => 12)); // WHERE finance_id <= 12
     * </code>
     *
     * @param     mixed $financeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByfinanceId($financeId = null, $comparison = null)
    {
        if (is_array($financeId)) {
            $useMinMax = false;
            if (isset($financeId['min'])) {
                $this->addUsingAlias(ActionPeer::FINANCE_ID, $financeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($financeId['max'])) {
                $this->addUsingAlias(ActionPeer::FINANCE_ID, $financeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::FINANCE_ID, $financeId, $comparison);
    }

    /**
     * Filter the query on the prescription_id column
     *
     * Example usage:
     * <code>
     * $query->filterByprescriptionId(1234); // WHERE prescription_id = 1234
     * $query->filterByprescriptionId(array(12, 34)); // WHERE prescription_id IN (12, 34)
     * $query->filterByprescriptionId(array('min' => 12)); // WHERE prescription_id >= 12
     * $query->filterByprescriptionId(array('max' => 12)); // WHERE prescription_id <= 12
     * </code>
     *
     * @param     mixed $prescriptionId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByprescriptionId($prescriptionId = null, $comparison = null)
    {
        if (is_array($prescriptionId)) {
            $useMinMax = false;
            if (isset($prescriptionId['min'])) {
                $this->addUsingAlias(ActionPeer::PRESCRIPTION_ID, $prescriptionId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($prescriptionId['max'])) {
                $this->addUsingAlias(ActionPeer::PRESCRIPTION_ID, $prescriptionId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PRESCRIPTION_ID, $prescriptionId, $comparison);
    }

    /**
     * Filter the query on the takenTissueJournal_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytakenTissueJournalId(1234); // WHERE takenTissueJournal_id = 1234
     * $query->filterBytakenTissueJournalId(array(12, 34)); // WHERE takenTissueJournal_id IN (12, 34)
     * $query->filterBytakenTissueJournalId(array('min' => 12)); // WHERE takenTissueJournal_id >= 12
     * $query->filterBytakenTissueJournalId(array('max' => 12)); // WHERE takenTissueJournal_id <= 12
     * </code>
     *
     * @param     mixed $takenTissueJournalId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBytakenTissueJournalId($takenTissueJournalId = null, $comparison = null)
    {
        if (is_array($takenTissueJournalId)) {
            $useMinMax = false;
            if (isset($takenTissueJournalId['min'])) {
                $this->addUsingAlias(ActionPeer::TAKENTISSUEJOURNAL_ID, $takenTissueJournalId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($takenTissueJournalId['max'])) {
                $this->addUsingAlias(ActionPeer::TAKENTISSUEJOURNAL_ID, $takenTissueJournalId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::TAKENTISSUEJOURNAL_ID, $takenTissueJournalId, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycontractId($contractId = null, $comparison = null)
    {
        if (is_array($contractId)) {
            $useMinMax = false;
            if (isset($contractId['min'])) {
                $this->addUsingAlias(ActionPeer::CONTRACT_ID, $contractId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($contractId['max'])) {
                $this->addUsingAlias(ActionPeer::CONTRACT_ID, $contractId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::CONTRACT_ID, $contractId, $comparison);
    }

    /**
     * Filter the query on the coordDate column
     *
     * Example usage:
     * <code>
     * $query->filterBycoordDate('2011-03-14'); // WHERE coordDate = '2011-03-14'
     * $query->filterBycoordDate('now'); // WHERE coordDate = '2011-03-14'
     * $query->filterBycoordDate(array('max' => 'yesterday')); // WHERE coordDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $coordDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycoordDate($coordDate = null, $comparison = null)
    {
        if (is_array($coordDate)) {
            $useMinMax = false;
            if (isset($coordDate['min'])) {
                $this->addUsingAlias(ActionPeer::COORDDATE, $coordDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($coordDate['max'])) {
                $this->addUsingAlias(ActionPeer::COORDDATE, $coordDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::COORDDATE, $coordDate, $comparison);
    }

    /**
     * Filter the query on the coordAgent column
     *
     * Example usage:
     * <code>
     * $query->filterBycoordAgent('fooValue');   // WHERE coordAgent = 'fooValue'
     * $query->filterBycoordAgent('%fooValue%'); // WHERE coordAgent LIKE '%fooValue%'
     * </code>
     *
     * @param     string $coordAgent The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycoordAgent($coordAgent = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($coordAgent)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $coordAgent)) {
                $coordAgent = str_replace('*', '%', $coordAgent);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::COORDAGENT, $coordAgent, $comparison);
    }

    /**
     * Filter the query on the coordInspector column
     *
     * Example usage:
     * <code>
     * $query->filterBycoordInspector('fooValue');   // WHERE coordInspector = 'fooValue'
     * $query->filterBycoordInspector('%fooValue%'); // WHERE coordInspector LIKE '%fooValue%'
     * </code>
     *
     * @param     string $coordInspector The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycoordInspector($coordInspector = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($coordInspector)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $coordInspector)) {
                $coordInspector = str_replace('*', '%', $coordInspector);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::COORDINSPECTOR, $coordInspector, $comparison);
    }

    /**
     * Filter the query on the coordText column
     *
     * Example usage:
     * <code>
     * $query->filterBycoordText('fooValue');   // WHERE coordText = 'fooValue'
     * $query->filterBycoordText('%fooValue%'); // WHERE coordText LIKE '%fooValue%'
     * </code>
     *
     * @param     string $coordText The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBycoordText($coordText = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($coordText)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $coordText)) {
                $coordText = str_replace('*', '%', $coordText);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::COORDTEXT, $coordText, $comparison);
    }

    /**
     * Filter the query on the hospitalUidFrom column
     *
     * Example usage:
     * <code>
     * $query->filterByhospitalUidFrom('fooValue');   // WHERE hospitalUidFrom = 'fooValue'
     * $query->filterByhospitalUidFrom('%fooValue%'); // WHERE hospitalUidFrom LIKE '%fooValue%'
     * </code>
     *
     * @param     string $hospitalUidFrom The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByhospitalUidFrom($hospitalUidFrom = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($hospitalUidFrom)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $hospitalUidFrom)) {
                $hospitalUidFrom = str_replace('*', '%', $hospitalUidFrom);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::HOSPITALUIDFROM, $hospitalUidFrom, $comparison);
    }

    /**
     * Filter the query on the pacientInQueueType column
     *
     * Example usage:
     * <code>
     * $query->filterBypacientInQueueType(1234); // WHERE pacientInQueueType = 1234
     * $query->filterBypacientInQueueType(array(12, 34)); // WHERE pacientInQueueType IN (12, 34)
     * $query->filterBypacientInQueueType(array('min' => 12)); // WHERE pacientInQueueType >= 12
     * $query->filterBypacientInQueueType(array('max' => 12)); // WHERE pacientInQueueType <= 12
     * </code>
     *
     * @param     mixed $pacientInQueueType The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBypacientInQueueType($pacientInQueueType = null, $comparison = null)
    {
        if (is_array($pacientInQueueType)) {
            $useMinMax = false;
            if (isset($pacientInQueueType['min'])) {
                $this->addUsingAlias(ActionPeer::PACIENTINQUEUETYPE, $pacientInQueueType['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($pacientInQueueType['max'])) {
                $this->addUsingAlias(ActionPeer::PACIENTINQUEUETYPE, $pacientInQueueType['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PACIENTINQUEUETYPE, $pacientInQueueType, $comparison);
    }

    /**
     * Filter the query on the AppointmentType column
     *
     * Example usage:
     * <code>
     * $query->filterByappointmentType('fooValue');   // WHERE AppointmentType = 'fooValue'
     * $query->filterByappointmentType('%fooValue%'); // WHERE AppointmentType LIKE '%fooValue%'
     * </code>
     *
     * @param     string $appointmentType The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByappointmentType($appointmentType = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($appointmentType)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $appointmentType)) {
                $appointmentType = str_replace('*', '%', $appointmentType);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::APPOINTMENTTYPE, $appointmentType, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(ActionPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(ActionPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query on the parentAction_id column
     *
     * Example usage:
     * <code>
     * $query->filterByparentActionId(1234); // WHERE parentAction_id = 1234
     * $query->filterByparentActionId(array(12, 34)); // WHERE parentAction_id IN (12, 34)
     * $query->filterByparentActionId(array('min' => 12)); // WHERE parentAction_id >= 12
     * $query->filterByparentActionId(array('max' => 12)); // WHERE parentAction_id <= 12
     * </code>
     *
     * @param     mixed $parentActionId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByparentActionId($parentActionId = null, $comparison = null)
    {
        if (is_array($parentActionId)) {
            $useMinMax = false;
            if (isset($parentActionId['min'])) {
                $this->addUsingAlias(ActionPeer::PARENTACTION_ID, $parentActionId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($parentActionId['max'])) {
                $this->addUsingAlias(ActionPeer::PARENTACTION_ID, $parentActionId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::PARENTACTION_ID, $parentActionId, $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterByuuidId($uuidId = null, $comparison = null)
    {
        if (is_array($uuidId)) {
            $useMinMax = false;
            if (isset($uuidId['min'])) {
                $this->addUsingAlias(ActionPeer::UUID_ID, $uuidId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uuidId['max'])) {
                $this->addUsingAlias(ActionPeer::UUID_ID, $uuidId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionPeer::UUID_ID, $uuidId, $comparison);
    }

    /**
     * Filter the query on the dcm_study_uid column
     *
     * Example usage:
     * <code>
     * $query->filterBydcmStudyUid('fooValue');   // WHERE dcm_study_uid = 'fooValue'
     * $query->filterBydcmStudyUid('%fooValue%'); // WHERE dcm_study_uid LIKE '%fooValue%'
     * </code>
     *
     * @param     string $dcmStudyUid The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function filterBydcmStudyUid($dcmStudyUid = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($dcmStudyUid)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $dcmStudyUid)) {
                $dcmStudyUid = str_replace('*', '%', $dcmStudyUid);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionPeer::DCM_STUDY_UID, $dcmStudyUid, $comparison);
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEvent($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(ActionPeer::EVENT_ID, $event->getid(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPeer::EVENT_ID, $event->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ActionQuery The current query, for fluid interface
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
     * Filter the query by a related Person object
     *
     * @param   Person|PropelObjectCollection $person The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByCreatePerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(ActionPeer::CREATEPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPeer::CREATEPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ActionQuery The current query, for fluid interface
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
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByModifyPerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(ActionPeer::MODIFYPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPeer::MODIFYPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ActionQuery The current query, for fluid interface
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
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterBySetPerson($person, $comparison = null)
    {
        if ($person instanceof Person) {
            return $this
                ->addUsingAlias(ActionPeer::SETPERSON_ID, $person->getid(), $comparison);
        } elseif ($person instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPeer::SETPERSON_ID, $person->toKeyValue('PrimaryKey', 'id'), $comparison);
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
     * @return ActionQuery The current query, for fluid interface
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
     * Filter the query by a related ActionType object
     *
     * @param   ActionType|PropelObjectCollection $actionType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionType($actionType, $comparison = null)
    {
        if ($actionType instanceof ActionType) {
            return $this
                ->addUsingAlias(ActionPeer::ACTIONTYPE_ID, $actionType->getid(), $comparison);
        } elseif ($actionType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionPeer::ACTIONTYPE_ID, $actionType->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByActionType() only accepts arguments of type ActionType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionType relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function joinActionType($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionType');

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
            $this->addJoinObject($join, 'ActionType');
        }

        return $this;
    }

    /**
     * Use the ActionType relation ActionType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionTypeQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionType($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionType', '\Webmis\Models\ActionTypeQuery');
    }

    /**
     * Filter the query by a related ActionProperty object
     *
     * @param   ActionProperty|PropelObjectCollection $actionProperty  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionProperty($actionProperty, $comparison = null)
    {
        if ($actionProperty instanceof ActionProperty) {
            return $this
                ->addUsingAlias(ActionPeer::ID, $actionProperty->getactionId(), $comparison);
        } elseif ($actionProperty instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyQuery()
                ->filterByPrimaryKeys($actionProperty->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionProperty() only accepts arguments of type ActionProperty or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionProperty relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function joinActionProperty($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionProperty');

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
            $this->addJoinObject($join, 'ActionProperty');
        }

        return $this;
    }

    /**
     * Use the ActionProperty relation ActionProperty object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionProperty($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionProperty', '\Webmis\Models\ActionPropertyQuery');
    }

    /**
     * Filter the query by a related DrugChart object
     *
     * @param   DrugChart|PropelObjectCollection $drugChart  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugChart($drugChart, $comparison = null)
    {
        if ($drugChart instanceof DrugChart) {
            return $this
                ->addUsingAlias(ActionPeer::ID, $drugChart->getactionId(), $comparison);
        } elseif ($drugChart instanceof PropelObjectCollection) {
            return $this
                ->useDrugChartQuery()
                ->filterByPrimaryKeys($drugChart->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByDrugChart() only accepts arguments of type DrugChart or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the DrugChart relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function joinDrugChart($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('DrugChart');

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
            $this->addJoinObject($join, 'DrugChart');
        }

        return $this;
    }

    /**
     * Use the DrugChart relation DrugChart object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\DrugChartQuery A secondary query class using the current class as primary query
     */
    public function useDrugChartQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinDrugChart($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugChart', '\Webmis\Models\DrugChartQuery');
    }

    /**
     * Filter the query by a related DrugComponent object
     *
     * @param   DrugComponent|PropelObjectCollection $drugComponent  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugComponent($drugComponent, $comparison = null)
    {
        if ($drugComponent instanceof DrugComponent) {
            return $this
                ->addUsingAlias(ActionPeer::ID, $drugComponent->getactionId(), $comparison);
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
     * @return ActionQuery The current query, for fluid interface
     */
    public function joinDrugComponent($relationAlias = null, $joinType = Criteria::INNER_JOIN)
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
    public function useDrugComponentQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinDrugComponent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugComponent', '\Webmis\Models\DrugComponentQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   Action $action Object to remove from the list of results
     *
     * @return ActionQuery The current query, for fluid interface
     */
    public function prune($action = null)
    {
        if ($action) {
            $this->addUsingAlias(ActionPeer::ID, $action->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     ActionQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionPeer::CREATEDATETIME);
    }
}
