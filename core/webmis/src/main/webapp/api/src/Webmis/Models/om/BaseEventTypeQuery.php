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
use Webmis\Models\EventType;
use Webmis\Models\EventTypePeer;
use Webmis\Models\EventTypeQuery;
use Webmis\Models\RbEventTypePurpose;
use Webmis\Models\RbResult;

/**
 * Base class that represents a query for the 'EventType' table.
 *
 *
 *
 * @method EventTypeQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method EventTypeQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method EventTypeQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method EventTypeQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method EventTypeQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method EventTypeQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method EventTypeQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method EventTypeQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method EventTypeQuery orderBypurposeId($order = Criteria::ASC) Order by the purpose_id column
 * @method EventTypeQuery orderByfinanceId($order = Criteria::ASC) Order by the finance_id column
 * @method EventTypeQuery orderBysceneId($order = Criteria::ASC) Order by the scene_id column
 * @method EventTypeQuery orderByvisitServiceModifier($order = Criteria::ASC) Order by the visitServiceModifier column
 * @method EventTypeQuery orderByvisitServiceFilter($order = Criteria::ASC) Order by the visitServiceFilter column
 * @method EventTypeQuery orderByvisitFinance($order = Criteria::ASC) Order by the visitFinance column
 * @method EventTypeQuery orderByactionFinance($order = Criteria::ASC) Order by the actionFinance column
 * @method EventTypeQuery orderByperiod($order = Criteria::ASC) Order by the period column
 * @method EventTypeQuery orderBysingleinPeriod($order = Criteria::ASC) Order by the singleInPeriod column
 * @method EventTypeQuery orderByisLong($order = Criteria::ASC) Order by the isLong column
 * @method EventTypeQuery orderBydateInput($order = Criteria::ASC) Order by the dateInput column
 * @method EventTypeQuery orderByserviceId($order = Criteria::ASC) Order by the service_id column
 * @method EventTypeQuery orderBycontext($order = Criteria::ASC) Order by the context column
 * @method EventTypeQuery orderByform($order = Criteria::ASC) Order by the form column
 * @method EventTypeQuery orderByminDuration($order = Criteria::ASC) Order by the minDuration column
 * @method EventTypeQuery orderBymaxDuration($order = Criteria::ASC) Order by the maxDuration column
 * @method EventTypeQuery orderByshowStatusActionsInPlanner($order = Criteria::ASC) Order by the showStatusActionsInPlanner column
 * @method EventTypeQuery orderByshowDiagnosticActionsInPlanner($order = Criteria::ASC) Order by the showDiagnosticActionsInPlanner column
 * @method EventTypeQuery orderByshowCureActionsInPlanner($order = Criteria::ASC) Order by the showCureActionsInPlanner column
 * @method EventTypeQuery orderByshowMiscActionsInPlanner($order = Criteria::ASC) Order by the showMiscActionsInPlanner column
 * @method EventTypeQuery orderBylimitStatusActionsInput($order = Criteria::ASC) Order by the limitStatusActionsInput column
 * @method EventTypeQuery orderBylimitDiagnosticActionsInput($order = Criteria::ASC) Order by the limitDiagnosticActionsInput column
 * @method EventTypeQuery orderBylimitCureActionsInput($order = Criteria::ASC) Order by the limitCureActionsInput column
 * @method EventTypeQuery orderBylimitMiscActionsInput($order = Criteria::ASC) Order by the limitMiscActionsInput column
 * @method EventTypeQuery orderByshowTime($order = Criteria::ASC) Order by the showTime column
 * @method EventTypeQuery orderBymedicalAidTypeId($order = Criteria::ASC) Order by the medicalAidType_id column
 * @method EventTypeQuery orderByeventProfileId($order = Criteria::ASC) Order by the eventProfile_id column
 * @method EventTypeQuery orderBymesRequired($order = Criteria::ASC) Order by the mesRequired column
 * @method EventTypeQuery orderBymesCodeMask($order = Criteria::ASC) Order by the mesCodeMask column
 * @method EventTypeQuery orderBymesNameMask($order = Criteria::ASC) Order by the mesNameMask column
 * @method EventTypeQuery orderBycounterId($order = Criteria::ASC) Order by the counter_id column
 * @method EventTypeQuery orderByisExternal($order = Criteria::ASC) Order by the isExternal column
 * @method EventTypeQuery orderByisAssistant($order = Criteria::ASC) Order by the isAssistant column
 * @method EventTypeQuery orderByisCurator($order = Criteria::ASC) Order by the isCurator column
 * @method EventTypeQuery orderBycanHavePayableActions($order = Criteria::ASC) Order by the canHavePayableActions column
 * @method EventTypeQuery orderByisRequiredCoordination($order = Criteria::ASC) Order by the isRequiredCoordination column
 * @method EventTypeQuery orderByisOrgStructurePriority($order = Criteria::ASC) Order by the isOrgStructurePriority column
 * @method EventTypeQuery orderByisTakenTissue($order = Criteria::ASC) Order by the isTakenTissue column
 * @method EventTypeQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method EventTypeQuery orderByage($order = Criteria::ASC) Order by the age column
 * @method EventTypeQuery orderByrbMedicalKindId($order = Criteria::ASC) Order by the rbMedicalKind_id column
 * @method EventTypeQuery orderByageBu($order = Criteria::ASC) Order by the age_bu column
 * @method EventTypeQuery orderByageBc($order = Criteria::ASC) Order by the age_bc column
 * @method EventTypeQuery orderByageEu($order = Criteria::ASC) Order by the age_eu column
 * @method EventTypeQuery orderByageEc($order = Criteria::ASC) Order by the age_ec column
 * @method EventTypeQuery orderByrequestTypeId($order = Criteria::ASC) Order by the requestType_id column
 *
 * @method EventTypeQuery groupByid() Group by the id column
 * @method EventTypeQuery groupBycreateDatetime() Group by the createDatetime column
 * @method EventTypeQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method EventTypeQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method EventTypeQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method EventTypeQuery groupBydeleted() Group by the deleted column
 * @method EventTypeQuery groupBycode() Group by the code column
 * @method EventTypeQuery groupByname() Group by the name column
 * @method EventTypeQuery groupBypurposeId() Group by the purpose_id column
 * @method EventTypeQuery groupByfinanceId() Group by the finance_id column
 * @method EventTypeQuery groupBysceneId() Group by the scene_id column
 * @method EventTypeQuery groupByvisitServiceModifier() Group by the visitServiceModifier column
 * @method EventTypeQuery groupByvisitServiceFilter() Group by the visitServiceFilter column
 * @method EventTypeQuery groupByvisitFinance() Group by the visitFinance column
 * @method EventTypeQuery groupByactionFinance() Group by the actionFinance column
 * @method EventTypeQuery groupByperiod() Group by the period column
 * @method EventTypeQuery groupBysingleinPeriod() Group by the singleInPeriod column
 * @method EventTypeQuery groupByisLong() Group by the isLong column
 * @method EventTypeQuery groupBydateInput() Group by the dateInput column
 * @method EventTypeQuery groupByserviceId() Group by the service_id column
 * @method EventTypeQuery groupBycontext() Group by the context column
 * @method EventTypeQuery groupByform() Group by the form column
 * @method EventTypeQuery groupByminDuration() Group by the minDuration column
 * @method EventTypeQuery groupBymaxDuration() Group by the maxDuration column
 * @method EventTypeQuery groupByshowStatusActionsInPlanner() Group by the showStatusActionsInPlanner column
 * @method EventTypeQuery groupByshowDiagnosticActionsInPlanner() Group by the showDiagnosticActionsInPlanner column
 * @method EventTypeQuery groupByshowCureActionsInPlanner() Group by the showCureActionsInPlanner column
 * @method EventTypeQuery groupByshowMiscActionsInPlanner() Group by the showMiscActionsInPlanner column
 * @method EventTypeQuery groupBylimitStatusActionsInput() Group by the limitStatusActionsInput column
 * @method EventTypeQuery groupBylimitDiagnosticActionsInput() Group by the limitDiagnosticActionsInput column
 * @method EventTypeQuery groupBylimitCureActionsInput() Group by the limitCureActionsInput column
 * @method EventTypeQuery groupBylimitMiscActionsInput() Group by the limitMiscActionsInput column
 * @method EventTypeQuery groupByshowTime() Group by the showTime column
 * @method EventTypeQuery groupBymedicalAidTypeId() Group by the medicalAidType_id column
 * @method EventTypeQuery groupByeventProfileId() Group by the eventProfile_id column
 * @method EventTypeQuery groupBymesRequired() Group by the mesRequired column
 * @method EventTypeQuery groupBymesCodeMask() Group by the mesCodeMask column
 * @method EventTypeQuery groupBymesNameMask() Group by the mesNameMask column
 * @method EventTypeQuery groupBycounterId() Group by the counter_id column
 * @method EventTypeQuery groupByisExternal() Group by the isExternal column
 * @method EventTypeQuery groupByisAssistant() Group by the isAssistant column
 * @method EventTypeQuery groupByisCurator() Group by the isCurator column
 * @method EventTypeQuery groupBycanHavePayableActions() Group by the canHavePayableActions column
 * @method EventTypeQuery groupByisRequiredCoordination() Group by the isRequiredCoordination column
 * @method EventTypeQuery groupByisOrgStructurePriority() Group by the isOrgStructurePriority column
 * @method EventTypeQuery groupByisTakenTissue() Group by the isTakenTissue column
 * @method EventTypeQuery groupBysex() Group by the sex column
 * @method EventTypeQuery groupByage() Group by the age column
 * @method EventTypeQuery groupByrbMedicalKindId() Group by the rbMedicalKind_id column
 * @method EventTypeQuery groupByageBu() Group by the age_bu column
 * @method EventTypeQuery groupByageBc() Group by the age_bc column
 * @method EventTypeQuery groupByageEu() Group by the age_eu column
 * @method EventTypeQuery groupByageEc() Group by the age_ec column
 * @method EventTypeQuery groupByrequestTypeId() Group by the requestType_id column
 *
 * @method EventTypeQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method EventTypeQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method EventTypeQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method EventTypeQuery leftJoinRbEventTypePurpose($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbEventTypePurpose relation
 * @method EventTypeQuery rightJoinRbEventTypePurpose($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbEventTypePurpose relation
 * @method EventTypeQuery innerJoinRbEventTypePurpose($relationAlias = null) Adds a INNER JOIN clause to the query using the RbEventTypePurpose relation
 *
 * @method EventTypeQuery leftJoinRbResult($relationAlias = null) Adds a LEFT JOIN clause to the query using the RbResult relation
 * @method EventTypeQuery rightJoinRbResult($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RbResult relation
 * @method EventTypeQuery innerJoinRbResult($relationAlias = null) Adds a INNER JOIN clause to the query using the RbResult relation
 *
 * @method EventTypeQuery leftJoinEvent($relationAlias = null) Adds a LEFT JOIN clause to the query using the Event relation
 * @method EventTypeQuery rightJoinEvent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Event relation
 * @method EventTypeQuery innerJoinEvent($relationAlias = null) Adds a INNER JOIN clause to the query using the Event relation
 *
 * @method EventType findOne(PropelPDO $con = null) Return the first EventType matching the query
 * @method EventType findOneOrCreate(PropelPDO $con = null) Return the first EventType matching the query, or a new EventType object populated from the query conditions when no match is found
 *
 * @method EventType findOneBycreateDatetime(string $createDatetime) Return the first EventType filtered by the createDatetime column
 * @method EventType findOneBycreatePersonId(int $createPerson_id) Return the first EventType filtered by the createPerson_id column
 * @method EventType findOneBymodifyDatetime(string $modifyDatetime) Return the first EventType filtered by the modifyDatetime column
 * @method EventType findOneBymodifyPersonId(int $modifyPerson_id) Return the first EventType filtered by the modifyPerson_id column
 * @method EventType findOneBydeleted(boolean $deleted) Return the first EventType filtered by the deleted column
 * @method EventType findOneBycode(string $code) Return the first EventType filtered by the code column
 * @method EventType findOneByname(string $name) Return the first EventType filtered by the name column
 * @method EventType findOneBypurposeId(int $purpose_id) Return the first EventType filtered by the purpose_id column
 * @method EventType findOneByfinanceId(int $finance_id) Return the first EventType filtered by the finance_id column
 * @method EventType findOneBysceneId(int $scene_id) Return the first EventType filtered by the scene_id column
 * @method EventType findOneByvisitServiceModifier(string $visitServiceModifier) Return the first EventType filtered by the visitServiceModifier column
 * @method EventType findOneByvisitServiceFilter(string $visitServiceFilter) Return the first EventType filtered by the visitServiceFilter column
 * @method EventType findOneByvisitFinance(boolean $visitFinance) Return the first EventType filtered by the visitFinance column
 * @method EventType findOneByactionFinance(boolean $actionFinance) Return the first EventType filtered by the actionFinance column
 * @method EventType findOneByperiod(int $period) Return the first EventType filtered by the period column
 * @method EventType findOneBysingleinPeriod(int $singleInPeriod) Return the first EventType filtered by the singleInPeriod column
 * @method EventType findOneByisLong(boolean $isLong) Return the first EventType filtered by the isLong column
 * @method EventType findOneBydateInput(boolean $dateInput) Return the first EventType filtered by the dateInput column
 * @method EventType findOneByserviceId(int $service_id) Return the first EventType filtered by the service_id column
 * @method EventType findOneBycontext(string $context) Return the first EventType filtered by the context column
 * @method EventType findOneByform(string $form) Return the first EventType filtered by the form column
 * @method EventType findOneByminDuration(int $minDuration) Return the first EventType filtered by the minDuration column
 * @method EventType findOneBymaxDuration(int $maxDuration) Return the first EventType filtered by the maxDuration column
 * @method EventType findOneByshowStatusActionsInPlanner(boolean $showStatusActionsInPlanner) Return the first EventType filtered by the showStatusActionsInPlanner column
 * @method EventType findOneByshowDiagnosticActionsInPlanner(boolean $showDiagnosticActionsInPlanner) Return the first EventType filtered by the showDiagnosticActionsInPlanner column
 * @method EventType findOneByshowCureActionsInPlanner(boolean $showCureActionsInPlanner) Return the first EventType filtered by the showCureActionsInPlanner column
 * @method EventType findOneByshowMiscActionsInPlanner(boolean $showMiscActionsInPlanner) Return the first EventType filtered by the showMiscActionsInPlanner column
 * @method EventType findOneBylimitStatusActionsInput(boolean $limitStatusActionsInput) Return the first EventType filtered by the limitStatusActionsInput column
 * @method EventType findOneBylimitDiagnosticActionsInput(boolean $limitDiagnosticActionsInput) Return the first EventType filtered by the limitDiagnosticActionsInput column
 * @method EventType findOneBylimitCureActionsInput(boolean $limitCureActionsInput) Return the first EventType filtered by the limitCureActionsInput column
 * @method EventType findOneBylimitMiscActionsInput(boolean $limitMiscActionsInput) Return the first EventType filtered by the limitMiscActionsInput column
 * @method EventType findOneByshowTime(boolean $showTime) Return the first EventType filtered by the showTime column
 * @method EventType findOneBymedicalAidTypeId(int $medicalAidType_id) Return the first EventType filtered by the medicalAidType_id column
 * @method EventType findOneByeventProfileId(int $eventProfile_id) Return the first EventType filtered by the eventProfile_id column
 * @method EventType findOneBymesRequired(int $mesRequired) Return the first EventType filtered by the mesRequired column
 * @method EventType findOneBymesCodeMask(string $mesCodeMask) Return the first EventType filtered by the mesCodeMask column
 * @method EventType findOneBymesNameMask(string $mesNameMask) Return the first EventType filtered by the mesNameMask column
 * @method EventType findOneBycounterId(int $counter_id) Return the first EventType filtered by the counter_id column
 * @method EventType findOneByisExternal(boolean $isExternal) Return the first EventType filtered by the isExternal column
 * @method EventType findOneByisAssistant(boolean $isAssistant) Return the first EventType filtered by the isAssistant column
 * @method EventType findOneByisCurator(boolean $isCurator) Return the first EventType filtered by the isCurator column
 * @method EventType findOneBycanHavePayableActions(boolean $canHavePayableActions) Return the first EventType filtered by the canHavePayableActions column
 * @method EventType findOneByisRequiredCoordination(boolean $isRequiredCoordination) Return the first EventType filtered by the isRequiredCoordination column
 * @method EventType findOneByisOrgStructurePriority(boolean $isOrgStructurePriority) Return the first EventType filtered by the isOrgStructurePriority column
 * @method EventType findOneByisTakenTissue(boolean $isTakenTissue) Return the first EventType filtered by the isTakenTissue column
 * @method EventType findOneBysex(int $sex) Return the first EventType filtered by the sex column
 * @method EventType findOneByage(string $age) Return the first EventType filtered by the age column
 * @method EventType findOneByrbMedicalKindId(int $rbMedicalKind_id) Return the first EventType filtered by the rbMedicalKind_id column
 * @method EventType findOneByageBu(int $age_bu) Return the first EventType filtered by the age_bu column
 * @method EventType findOneByageBc(int $age_bc) Return the first EventType filtered by the age_bc column
 * @method EventType findOneByageEu(int $age_eu) Return the first EventType filtered by the age_eu column
 * @method EventType findOneByageEc(int $age_ec) Return the first EventType filtered by the age_ec column
 * @method EventType findOneByrequestTypeId(int $requestType_id) Return the first EventType filtered by the requestType_id column
 *
 * @method array findByid(int $id) Return EventType objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return EventType objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return EventType objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return EventType objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return EventType objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return EventType objects filtered by the deleted column
 * @method array findBycode(string $code) Return EventType objects filtered by the code column
 * @method array findByname(string $name) Return EventType objects filtered by the name column
 * @method array findBypurposeId(int $purpose_id) Return EventType objects filtered by the purpose_id column
 * @method array findByfinanceId(int $finance_id) Return EventType objects filtered by the finance_id column
 * @method array findBysceneId(int $scene_id) Return EventType objects filtered by the scene_id column
 * @method array findByvisitServiceModifier(string $visitServiceModifier) Return EventType objects filtered by the visitServiceModifier column
 * @method array findByvisitServiceFilter(string $visitServiceFilter) Return EventType objects filtered by the visitServiceFilter column
 * @method array findByvisitFinance(boolean $visitFinance) Return EventType objects filtered by the visitFinance column
 * @method array findByactionFinance(boolean $actionFinance) Return EventType objects filtered by the actionFinance column
 * @method array findByperiod(int $period) Return EventType objects filtered by the period column
 * @method array findBysingleinPeriod(int $singleInPeriod) Return EventType objects filtered by the singleInPeriod column
 * @method array findByisLong(boolean $isLong) Return EventType objects filtered by the isLong column
 * @method array findBydateInput(boolean $dateInput) Return EventType objects filtered by the dateInput column
 * @method array findByserviceId(int $service_id) Return EventType objects filtered by the service_id column
 * @method array findBycontext(string $context) Return EventType objects filtered by the context column
 * @method array findByform(string $form) Return EventType objects filtered by the form column
 * @method array findByminDuration(int $minDuration) Return EventType objects filtered by the minDuration column
 * @method array findBymaxDuration(int $maxDuration) Return EventType objects filtered by the maxDuration column
 * @method array findByshowStatusActionsInPlanner(boolean $showStatusActionsInPlanner) Return EventType objects filtered by the showStatusActionsInPlanner column
 * @method array findByshowDiagnosticActionsInPlanner(boolean $showDiagnosticActionsInPlanner) Return EventType objects filtered by the showDiagnosticActionsInPlanner column
 * @method array findByshowCureActionsInPlanner(boolean $showCureActionsInPlanner) Return EventType objects filtered by the showCureActionsInPlanner column
 * @method array findByshowMiscActionsInPlanner(boolean $showMiscActionsInPlanner) Return EventType objects filtered by the showMiscActionsInPlanner column
 * @method array findBylimitStatusActionsInput(boolean $limitStatusActionsInput) Return EventType objects filtered by the limitStatusActionsInput column
 * @method array findBylimitDiagnosticActionsInput(boolean $limitDiagnosticActionsInput) Return EventType objects filtered by the limitDiagnosticActionsInput column
 * @method array findBylimitCureActionsInput(boolean $limitCureActionsInput) Return EventType objects filtered by the limitCureActionsInput column
 * @method array findBylimitMiscActionsInput(boolean $limitMiscActionsInput) Return EventType objects filtered by the limitMiscActionsInput column
 * @method array findByshowTime(boolean $showTime) Return EventType objects filtered by the showTime column
 * @method array findBymedicalAidTypeId(int $medicalAidType_id) Return EventType objects filtered by the medicalAidType_id column
 * @method array findByeventProfileId(int $eventProfile_id) Return EventType objects filtered by the eventProfile_id column
 * @method array findBymesRequired(int $mesRequired) Return EventType objects filtered by the mesRequired column
 * @method array findBymesCodeMask(string $mesCodeMask) Return EventType objects filtered by the mesCodeMask column
 * @method array findBymesNameMask(string $mesNameMask) Return EventType objects filtered by the mesNameMask column
 * @method array findBycounterId(int $counter_id) Return EventType objects filtered by the counter_id column
 * @method array findByisExternal(boolean $isExternal) Return EventType objects filtered by the isExternal column
 * @method array findByisAssistant(boolean $isAssistant) Return EventType objects filtered by the isAssistant column
 * @method array findByisCurator(boolean $isCurator) Return EventType objects filtered by the isCurator column
 * @method array findBycanHavePayableActions(boolean $canHavePayableActions) Return EventType objects filtered by the canHavePayableActions column
 * @method array findByisRequiredCoordination(boolean $isRequiredCoordination) Return EventType objects filtered by the isRequiredCoordination column
 * @method array findByisOrgStructurePriority(boolean $isOrgStructurePriority) Return EventType objects filtered by the isOrgStructurePriority column
 * @method array findByisTakenTissue(boolean $isTakenTissue) Return EventType objects filtered by the isTakenTissue column
 * @method array findBysex(int $sex) Return EventType objects filtered by the sex column
 * @method array findByage(string $age) Return EventType objects filtered by the age column
 * @method array findByrbMedicalKindId(int $rbMedicalKind_id) Return EventType objects filtered by the rbMedicalKind_id column
 * @method array findByageBu(int $age_bu) Return EventType objects filtered by the age_bu column
 * @method array findByageBc(int $age_bc) Return EventType objects filtered by the age_bc column
 * @method array findByageEu(int $age_eu) Return EventType objects filtered by the age_eu column
 * @method array findByageEc(int $age_ec) Return EventType objects filtered by the age_ec column
 * @method array findByrequestTypeId(int $requestType_id) Return EventType objects filtered by the requestType_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseEventTypeQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseEventTypeQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\EventType', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new EventTypeQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   EventTypeQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return EventTypeQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof EventTypeQuery) {
            return $criteria;
        }
        $query = new EventTypeQuery();
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
     * @return   EventType|EventType[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = EventTypePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 EventType A model object, or null if the key is not found
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
     * @return                 EventType A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `code`, `name`, `purpose_id`, `finance_id`, `scene_id`, `visitServiceModifier`, `visitServiceFilter`, `visitFinance`, `actionFinance`, `period`, `singleInPeriod`, `isLong`, `dateInput`, `service_id`, `context`, `form`, `minDuration`, `maxDuration`, `showStatusActionsInPlanner`, `showDiagnosticActionsInPlanner`, `showCureActionsInPlanner`, `showMiscActionsInPlanner`, `limitStatusActionsInput`, `limitDiagnosticActionsInput`, `limitCureActionsInput`, `limitMiscActionsInput`, `showTime`, `medicalAidType_id`, `eventProfile_id`, `mesRequired`, `mesCodeMask`, `mesNameMask`, `counter_id`, `isExternal`, `isAssistant`, `isCurator`, `canHavePayableActions`, `isRequiredCoordination`, `isOrgStructurePriority`, `isTakenTissue`, `sex`, `age`, `rbMedicalKind_id`, `age_bu`, `age_bc`, `age_eu`, `age_ec`, `requestType_id` FROM `EventType` WHERE `id` = :p0';
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
            $obj = new EventType();
            $obj->hydrate($row);
            EventTypePeer::addInstanceToPool($obj, (string) $key);
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
     * @return EventType|EventType[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|EventType[]|mixed the list of results, formatted by the current formatter
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(EventTypePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(EventTypePeer::ID, $keys, Criteria::IN);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(EventTypePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(EventTypePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::ID, $id, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(EventTypePeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(EventTypePeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(EventTypePeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(EventTypePeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(EventTypePeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(EventTypePeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(EventTypePeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(EventTypePeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::DELETED, $deleted, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(EventTypePeer::CODE, $code, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(EventTypePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the purpose_id column
     *
     * Example usage:
     * <code>
     * $query->filterBypurposeId(1234); // WHERE purpose_id = 1234
     * $query->filterBypurposeId(array(12, 34)); // WHERE purpose_id IN (12, 34)
     * $query->filterBypurposeId(array('min' => 12)); // WHERE purpose_id >= 12
     * $query->filterBypurposeId(array('max' => 12)); // WHERE purpose_id <= 12
     * </code>
     *
     * @see       filterByRbEventTypePurpose()
     *
     * @see       filterByRbResult()
     *
     * @param     mixed $purposeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBypurposeId($purposeId = null, $comparison = null)
    {
        if (is_array($purposeId)) {
            $useMinMax = false;
            if (isset($purposeId['min'])) {
                $this->addUsingAlias(EventTypePeer::PURPOSE_ID, $purposeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($purposeId['max'])) {
                $this->addUsingAlias(EventTypePeer::PURPOSE_ID, $purposeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::PURPOSE_ID, $purposeId, $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByfinanceId($financeId = null, $comparison = null)
    {
        if (is_array($financeId)) {
            $useMinMax = false;
            if (isset($financeId['min'])) {
                $this->addUsingAlias(EventTypePeer::FINANCE_ID, $financeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($financeId['max'])) {
                $this->addUsingAlias(EventTypePeer::FINANCE_ID, $financeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::FINANCE_ID, $financeId, $comparison);
    }

    /**
     * Filter the query on the scene_id column
     *
     * Example usage:
     * <code>
     * $query->filterBysceneId(1234); // WHERE scene_id = 1234
     * $query->filterBysceneId(array(12, 34)); // WHERE scene_id IN (12, 34)
     * $query->filterBysceneId(array('min' => 12)); // WHERE scene_id >= 12
     * $query->filterBysceneId(array('max' => 12)); // WHERE scene_id <= 12
     * </code>
     *
     * @param     mixed $sceneId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBysceneId($sceneId = null, $comparison = null)
    {
        if (is_array($sceneId)) {
            $useMinMax = false;
            if (isset($sceneId['min'])) {
                $this->addUsingAlias(EventTypePeer::SCENE_ID, $sceneId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sceneId['max'])) {
                $this->addUsingAlias(EventTypePeer::SCENE_ID, $sceneId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::SCENE_ID, $sceneId, $comparison);
    }

    /**
     * Filter the query on the visitServiceModifier column
     *
     * Example usage:
     * <code>
     * $query->filterByvisitServiceModifier('fooValue');   // WHERE visitServiceModifier = 'fooValue'
     * $query->filterByvisitServiceModifier('%fooValue%'); // WHERE visitServiceModifier LIKE '%fooValue%'
     * </code>
     *
     * @param     string $visitServiceModifier The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByvisitServiceModifier($visitServiceModifier = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($visitServiceModifier)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $visitServiceModifier)) {
                $visitServiceModifier = str_replace('*', '%', $visitServiceModifier);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::VISITSERVICEMODIFIER, $visitServiceModifier, $comparison);
    }

    /**
     * Filter the query on the visitServiceFilter column
     *
     * Example usage:
     * <code>
     * $query->filterByvisitServiceFilter('fooValue');   // WHERE visitServiceFilter = 'fooValue'
     * $query->filterByvisitServiceFilter('%fooValue%'); // WHERE visitServiceFilter LIKE '%fooValue%'
     * </code>
     *
     * @param     string $visitServiceFilter The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByvisitServiceFilter($visitServiceFilter = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($visitServiceFilter)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $visitServiceFilter)) {
                $visitServiceFilter = str_replace('*', '%', $visitServiceFilter);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::VISITSERVICEFILTER, $visitServiceFilter, $comparison);
    }

    /**
     * Filter the query on the visitFinance column
     *
     * Example usage:
     * <code>
     * $query->filterByvisitFinance(true); // WHERE visitFinance = true
     * $query->filterByvisitFinance('yes'); // WHERE visitFinance = true
     * </code>
     *
     * @param     boolean|string $visitFinance The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByvisitFinance($visitFinance = null, $comparison = null)
    {
        if (is_string($visitFinance)) {
            $visitFinance = in_array(strtolower($visitFinance), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::VISITFINANCE, $visitFinance, $comparison);
    }

    /**
     * Filter the query on the actionFinance column
     *
     * Example usage:
     * <code>
     * $query->filterByactionFinance(true); // WHERE actionFinance = true
     * $query->filterByactionFinance('yes'); // WHERE actionFinance = true
     * </code>
     *
     * @param     boolean|string $actionFinance The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByactionFinance($actionFinance = null, $comparison = null)
    {
        if (is_string($actionFinance)) {
            $actionFinance = in_array(strtolower($actionFinance), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ACTIONFINANCE, $actionFinance, $comparison);
    }

    /**
     * Filter the query on the period column
     *
     * Example usage:
     * <code>
     * $query->filterByperiod(1234); // WHERE period = 1234
     * $query->filterByperiod(array(12, 34)); // WHERE period IN (12, 34)
     * $query->filterByperiod(array('min' => 12)); // WHERE period >= 12
     * $query->filterByperiod(array('max' => 12)); // WHERE period <= 12
     * </code>
     *
     * @param     mixed $period The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByperiod($period = null, $comparison = null)
    {
        if (is_array($period)) {
            $useMinMax = false;
            if (isset($period['min'])) {
                $this->addUsingAlias(EventTypePeer::PERIOD, $period['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($period['max'])) {
                $this->addUsingAlias(EventTypePeer::PERIOD, $period['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::PERIOD, $period, $comparison);
    }

    /**
     * Filter the query on the singleInPeriod column
     *
     * Example usage:
     * <code>
     * $query->filterBysingleinPeriod(1234); // WHERE singleInPeriod = 1234
     * $query->filterBysingleinPeriod(array(12, 34)); // WHERE singleInPeriod IN (12, 34)
     * $query->filterBysingleinPeriod(array('min' => 12)); // WHERE singleInPeriod >= 12
     * $query->filterBysingleinPeriod(array('max' => 12)); // WHERE singleInPeriod <= 12
     * </code>
     *
     * @param     mixed $singleinPeriod The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBysingleinPeriod($singleinPeriod = null, $comparison = null)
    {
        if (is_array($singleinPeriod)) {
            $useMinMax = false;
            if (isset($singleinPeriod['min'])) {
                $this->addUsingAlias(EventTypePeer::SINGLEINPERIOD, $singleinPeriod['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($singleinPeriod['max'])) {
                $this->addUsingAlias(EventTypePeer::SINGLEINPERIOD, $singleinPeriod['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::SINGLEINPERIOD, $singleinPeriod, $comparison);
    }

    /**
     * Filter the query on the isLong column
     *
     * Example usage:
     * <code>
     * $query->filterByisLong(true); // WHERE isLong = true
     * $query->filterByisLong('yes'); // WHERE isLong = true
     * </code>
     *
     * @param     boolean|string $isLong The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisLong($isLong = null, $comparison = null)
    {
        if (is_string($isLong)) {
            $isLong = in_array(strtolower($isLong), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISLONG, $isLong, $comparison);
    }

    /**
     * Filter the query on the dateInput column
     *
     * Example usage:
     * <code>
     * $query->filterBydateInput(true); // WHERE dateInput = true
     * $query->filterBydateInput('yes'); // WHERE dateInput = true
     * </code>
     *
     * @param     boolean|string $dateInput The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBydateInput($dateInput = null, $comparison = null)
    {
        if (is_string($dateInput)) {
            $dateInput = in_array(strtolower($dateInput), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::DATEINPUT, $dateInput, $comparison);
    }

    /**
     * Filter the query on the service_id column
     *
     * Example usage:
     * <code>
     * $query->filterByserviceId(1234); // WHERE service_id = 1234
     * $query->filterByserviceId(array(12, 34)); // WHERE service_id IN (12, 34)
     * $query->filterByserviceId(array('min' => 12)); // WHERE service_id >= 12
     * $query->filterByserviceId(array('max' => 12)); // WHERE service_id <= 12
     * </code>
     *
     * @param     mixed $serviceId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByserviceId($serviceId = null, $comparison = null)
    {
        if (is_array($serviceId)) {
            $useMinMax = false;
            if (isset($serviceId['min'])) {
                $this->addUsingAlias(EventTypePeer::SERVICE_ID, $serviceId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($serviceId['max'])) {
                $this->addUsingAlias(EventTypePeer::SERVICE_ID, $serviceId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::SERVICE_ID, $serviceId, $comparison);
    }

    /**
     * Filter the query on the context column
     *
     * Example usage:
     * <code>
     * $query->filterBycontext('fooValue');   // WHERE context = 'fooValue'
     * $query->filterBycontext('%fooValue%'); // WHERE context LIKE '%fooValue%'
     * </code>
     *
     * @param     string $context The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBycontext($context = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($context)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $context)) {
                $context = str_replace('*', '%', $context);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::CONTEXT, $context, $comparison);
    }

    /**
     * Filter the query on the form column
     *
     * Example usage:
     * <code>
     * $query->filterByform('fooValue');   // WHERE form = 'fooValue'
     * $query->filterByform('%fooValue%'); // WHERE form LIKE '%fooValue%'
     * </code>
     *
     * @param     string $form The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByform($form = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($form)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $form)) {
                $form = str_replace('*', '%', $form);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::FORM, $form, $comparison);
    }

    /**
     * Filter the query on the minDuration column
     *
     * Example usage:
     * <code>
     * $query->filterByminDuration(1234); // WHERE minDuration = 1234
     * $query->filterByminDuration(array(12, 34)); // WHERE minDuration IN (12, 34)
     * $query->filterByminDuration(array('min' => 12)); // WHERE minDuration >= 12
     * $query->filterByminDuration(array('max' => 12)); // WHERE minDuration <= 12
     * </code>
     *
     * @param     mixed $minDuration The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByminDuration($minDuration = null, $comparison = null)
    {
        if (is_array($minDuration)) {
            $useMinMax = false;
            if (isset($minDuration['min'])) {
                $this->addUsingAlias(EventTypePeer::MINDURATION, $minDuration['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($minDuration['max'])) {
                $this->addUsingAlias(EventTypePeer::MINDURATION, $minDuration['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MINDURATION, $minDuration, $comparison);
    }

    /**
     * Filter the query on the maxDuration column
     *
     * Example usage:
     * <code>
     * $query->filterBymaxDuration(1234); // WHERE maxDuration = 1234
     * $query->filterBymaxDuration(array(12, 34)); // WHERE maxDuration IN (12, 34)
     * $query->filterBymaxDuration(array('min' => 12)); // WHERE maxDuration >= 12
     * $query->filterBymaxDuration(array('max' => 12)); // WHERE maxDuration <= 12
     * </code>
     *
     * @param     mixed $maxDuration The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymaxDuration($maxDuration = null, $comparison = null)
    {
        if (is_array($maxDuration)) {
            $useMinMax = false;
            if (isset($maxDuration['min'])) {
                $this->addUsingAlias(EventTypePeer::MAXDURATION, $maxDuration['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($maxDuration['max'])) {
                $this->addUsingAlias(EventTypePeer::MAXDURATION, $maxDuration['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MAXDURATION, $maxDuration, $comparison);
    }

    /**
     * Filter the query on the showStatusActionsInPlanner column
     *
     * Example usage:
     * <code>
     * $query->filterByshowStatusActionsInPlanner(true); // WHERE showStatusActionsInPlanner = true
     * $query->filterByshowStatusActionsInPlanner('yes'); // WHERE showStatusActionsInPlanner = true
     * </code>
     *
     * @param     boolean|string $showStatusActionsInPlanner The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByshowStatusActionsInPlanner($showStatusActionsInPlanner = null, $comparison = null)
    {
        if (is_string($showStatusActionsInPlanner)) {
            $showStatusActionsInPlanner = in_array(strtolower($showStatusActionsInPlanner), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::SHOWSTATUSACTIONSINPLANNER, $showStatusActionsInPlanner, $comparison);
    }

    /**
     * Filter the query on the showDiagnosticActionsInPlanner column
     *
     * Example usage:
     * <code>
     * $query->filterByshowDiagnosticActionsInPlanner(true); // WHERE showDiagnosticActionsInPlanner = true
     * $query->filterByshowDiagnosticActionsInPlanner('yes'); // WHERE showDiagnosticActionsInPlanner = true
     * </code>
     *
     * @param     boolean|string $showDiagnosticActionsInPlanner The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByshowDiagnosticActionsInPlanner($showDiagnosticActionsInPlanner = null, $comparison = null)
    {
        if (is_string($showDiagnosticActionsInPlanner)) {
            $showDiagnosticActionsInPlanner = in_array(strtolower($showDiagnosticActionsInPlanner), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER, $showDiagnosticActionsInPlanner, $comparison);
    }

    /**
     * Filter the query on the showCureActionsInPlanner column
     *
     * Example usage:
     * <code>
     * $query->filterByshowCureActionsInPlanner(true); // WHERE showCureActionsInPlanner = true
     * $query->filterByshowCureActionsInPlanner('yes'); // WHERE showCureActionsInPlanner = true
     * </code>
     *
     * @param     boolean|string $showCureActionsInPlanner The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByshowCureActionsInPlanner($showCureActionsInPlanner = null, $comparison = null)
    {
        if (is_string($showCureActionsInPlanner)) {
            $showCureActionsInPlanner = in_array(strtolower($showCureActionsInPlanner), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::SHOWCUREACTIONSINPLANNER, $showCureActionsInPlanner, $comparison);
    }

    /**
     * Filter the query on the showMiscActionsInPlanner column
     *
     * Example usage:
     * <code>
     * $query->filterByshowMiscActionsInPlanner(true); // WHERE showMiscActionsInPlanner = true
     * $query->filterByshowMiscActionsInPlanner('yes'); // WHERE showMiscActionsInPlanner = true
     * </code>
     *
     * @param     boolean|string $showMiscActionsInPlanner The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByshowMiscActionsInPlanner($showMiscActionsInPlanner = null, $comparison = null)
    {
        if (is_string($showMiscActionsInPlanner)) {
            $showMiscActionsInPlanner = in_array(strtolower($showMiscActionsInPlanner), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::SHOWMISCACTIONSINPLANNER, $showMiscActionsInPlanner, $comparison);
    }

    /**
     * Filter the query on the limitStatusActionsInput column
     *
     * Example usage:
     * <code>
     * $query->filterBylimitStatusActionsInput(true); // WHERE limitStatusActionsInput = true
     * $query->filterBylimitStatusActionsInput('yes'); // WHERE limitStatusActionsInput = true
     * </code>
     *
     * @param     boolean|string $limitStatusActionsInput The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBylimitStatusActionsInput($limitStatusActionsInput = null, $comparison = null)
    {
        if (is_string($limitStatusActionsInput)) {
            $limitStatusActionsInput = in_array(strtolower($limitStatusActionsInput), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::LIMITSTATUSACTIONSINPUT, $limitStatusActionsInput, $comparison);
    }

    /**
     * Filter the query on the limitDiagnosticActionsInput column
     *
     * Example usage:
     * <code>
     * $query->filterBylimitDiagnosticActionsInput(true); // WHERE limitDiagnosticActionsInput = true
     * $query->filterBylimitDiagnosticActionsInput('yes'); // WHERE limitDiagnosticActionsInput = true
     * </code>
     *
     * @param     boolean|string $limitDiagnosticActionsInput The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBylimitDiagnosticActionsInput($limitDiagnosticActionsInput = null, $comparison = null)
    {
        if (is_string($limitDiagnosticActionsInput)) {
            $limitDiagnosticActionsInput = in_array(strtolower($limitDiagnosticActionsInput), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT, $limitDiagnosticActionsInput, $comparison);
    }

    /**
     * Filter the query on the limitCureActionsInput column
     *
     * Example usage:
     * <code>
     * $query->filterBylimitCureActionsInput(true); // WHERE limitCureActionsInput = true
     * $query->filterBylimitCureActionsInput('yes'); // WHERE limitCureActionsInput = true
     * </code>
     *
     * @param     boolean|string $limitCureActionsInput The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBylimitCureActionsInput($limitCureActionsInput = null, $comparison = null)
    {
        if (is_string($limitCureActionsInput)) {
            $limitCureActionsInput = in_array(strtolower($limitCureActionsInput), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::LIMITCUREACTIONSINPUT, $limitCureActionsInput, $comparison);
    }

    /**
     * Filter the query on the limitMiscActionsInput column
     *
     * Example usage:
     * <code>
     * $query->filterBylimitMiscActionsInput(true); // WHERE limitMiscActionsInput = true
     * $query->filterBylimitMiscActionsInput('yes'); // WHERE limitMiscActionsInput = true
     * </code>
     *
     * @param     boolean|string $limitMiscActionsInput The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBylimitMiscActionsInput($limitMiscActionsInput = null, $comparison = null)
    {
        if (is_string($limitMiscActionsInput)) {
            $limitMiscActionsInput = in_array(strtolower($limitMiscActionsInput), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::LIMITMISCACTIONSINPUT, $limitMiscActionsInput, $comparison);
    }

    /**
     * Filter the query on the showTime column
     *
     * Example usage:
     * <code>
     * $query->filterByshowTime(true); // WHERE showTime = true
     * $query->filterByshowTime('yes'); // WHERE showTime = true
     * </code>
     *
     * @param     boolean|string $showTime The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByshowTime($showTime = null, $comparison = null)
    {
        if (is_string($showTime)) {
            $showTime = in_array(strtolower($showTime), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::SHOWTIME, $showTime, $comparison);
    }

    /**
     * Filter the query on the medicalAidType_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymedicalAidTypeId(1234); // WHERE medicalAidType_id = 1234
     * $query->filterBymedicalAidTypeId(array(12, 34)); // WHERE medicalAidType_id IN (12, 34)
     * $query->filterBymedicalAidTypeId(array('min' => 12)); // WHERE medicalAidType_id >= 12
     * $query->filterBymedicalAidTypeId(array('max' => 12)); // WHERE medicalAidType_id <= 12
     * </code>
     *
     * @param     mixed $medicalAidTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymedicalAidTypeId($medicalAidTypeId = null, $comparison = null)
    {
        if (is_array($medicalAidTypeId)) {
            $useMinMax = false;
            if (isset($medicalAidTypeId['min'])) {
                $this->addUsingAlias(EventTypePeer::MEDICALAIDTYPE_ID, $medicalAidTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($medicalAidTypeId['max'])) {
                $this->addUsingAlias(EventTypePeer::MEDICALAIDTYPE_ID, $medicalAidTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MEDICALAIDTYPE_ID, $medicalAidTypeId, $comparison);
    }

    /**
     * Filter the query on the eventProfile_id column
     *
     * Example usage:
     * <code>
     * $query->filterByeventProfileId(1234); // WHERE eventProfile_id = 1234
     * $query->filterByeventProfileId(array(12, 34)); // WHERE eventProfile_id IN (12, 34)
     * $query->filterByeventProfileId(array('min' => 12)); // WHERE eventProfile_id >= 12
     * $query->filterByeventProfileId(array('max' => 12)); // WHERE eventProfile_id <= 12
     * </code>
     *
     * @param     mixed $eventProfileId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByeventProfileId($eventProfileId = null, $comparison = null)
    {
        if (is_array($eventProfileId)) {
            $useMinMax = false;
            if (isset($eventProfileId['min'])) {
                $this->addUsingAlias(EventTypePeer::EVENTPROFILE_ID, $eventProfileId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($eventProfileId['max'])) {
                $this->addUsingAlias(EventTypePeer::EVENTPROFILE_ID, $eventProfileId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::EVENTPROFILE_ID, $eventProfileId, $comparison);
    }

    /**
     * Filter the query on the mesRequired column
     *
     * Example usage:
     * <code>
     * $query->filterBymesRequired(1234); // WHERE mesRequired = 1234
     * $query->filterBymesRequired(array(12, 34)); // WHERE mesRequired IN (12, 34)
     * $query->filterBymesRequired(array('min' => 12)); // WHERE mesRequired >= 12
     * $query->filterBymesRequired(array('max' => 12)); // WHERE mesRequired <= 12
     * </code>
     *
     * @param     mixed $mesRequired The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymesRequired($mesRequired = null, $comparison = null)
    {
        if (is_array($mesRequired)) {
            $useMinMax = false;
            if (isset($mesRequired['min'])) {
                $this->addUsingAlias(EventTypePeer::MESREQUIRED, $mesRequired['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($mesRequired['max'])) {
                $this->addUsingAlias(EventTypePeer::MESREQUIRED, $mesRequired['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MESREQUIRED, $mesRequired, $comparison);
    }

    /**
     * Filter the query on the mesCodeMask column
     *
     * Example usage:
     * <code>
     * $query->filterBymesCodeMask('fooValue');   // WHERE mesCodeMask = 'fooValue'
     * $query->filterBymesCodeMask('%fooValue%'); // WHERE mesCodeMask LIKE '%fooValue%'
     * </code>
     *
     * @param     string $mesCodeMask The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymesCodeMask($mesCodeMask = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($mesCodeMask)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $mesCodeMask)) {
                $mesCodeMask = str_replace('*', '%', $mesCodeMask);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MESCODEMASK, $mesCodeMask, $comparison);
    }

    /**
     * Filter the query on the mesNameMask column
     *
     * Example usage:
     * <code>
     * $query->filterBymesNameMask('fooValue');   // WHERE mesNameMask = 'fooValue'
     * $query->filterBymesNameMask('%fooValue%'); // WHERE mesNameMask LIKE '%fooValue%'
     * </code>
     *
     * @param     string $mesNameMask The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBymesNameMask($mesNameMask = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($mesNameMask)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $mesNameMask)) {
                $mesNameMask = str_replace('*', '%', $mesNameMask);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::MESNAMEMASK, $mesNameMask, $comparison);
    }

    /**
     * Filter the query on the counter_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycounterId(1234); // WHERE counter_id = 1234
     * $query->filterBycounterId(array(12, 34)); // WHERE counter_id IN (12, 34)
     * $query->filterBycounterId(array('min' => 12)); // WHERE counter_id >= 12
     * $query->filterBycounterId(array('max' => 12)); // WHERE counter_id <= 12
     * </code>
     *
     * @param     mixed $counterId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBycounterId($counterId = null, $comparison = null)
    {
        if (is_array($counterId)) {
            $useMinMax = false;
            if (isset($counterId['min'])) {
                $this->addUsingAlias(EventTypePeer::COUNTER_ID, $counterId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($counterId['max'])) {
                $this->addUsingAlias(EventTypePeer::COUNTER_ID, $counterId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::COUNTER_ID, $counterId, $comparison);
    }

    /**
     * Filter the query on the isExternal column
     *
     * Example usage:
     * <code>
     * $query->filterByisExternal(true); // WHERE isExternal = true
     * $query->filterByisExternal('yes'); // WHERE isExternal = true
     * </code>
     *
     * @param     boolean|string $isExternal The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisExternal($isExternal = null, $comparison = null)
    {
        if (is_string($isExternal)) {
            $isExternal = in_array(strtolower($isExternal), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISEXTERNAL, $isExternal, $comparison);
    }

    /**
     * Filter the query on the isAssistant column
     *
     * Example usage:
     * <code>
     * $query->filterByisAssistant(true); // WHERE isAssistant = true
     * $query->filterByisAssistant('yes'); // WHERE isAssistant = true
     * </code>
     *
     * @param     boolean|string $isAssistant The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisAssistant($isAssistant = null, $comparison = null)
    {
        if (is_string($isAssistant)) {
            $isAssistant = in_array(strtolower($isAssistant), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISASSISTANT, $isAssistant, $comparison);
    }

    /**
     * Filter the query on the isCurator column
     *
     * Example usage:
     * <code>
     * $query->filterByisCurator(true); // WHERE isCurator = true
     * $query->filterByisCurator('yes'); // WHERE isCurator = true
     * </code>
     *
     * @param     boolean|string $isCurator The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisCurator($isCurator = null, $comparison = null)
    {
        if (is_string($isCurator)) {
            $isCurator = in_array(strtolower($isCurator), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISCURATOR, $isCurator, $comparison);
    }

    /**
     * Filter the query on the canHavePayableActions column
     *
     * Example usage:
     * <code>
     * $query->filterBycanHavePayableActions(true); // WHERE canHavePayableActions = true
     * $query->filterBycanHavePayableActions('yes'); // WHERE canHavePayableActions = true
     * </code>
     *
     * @param     boolean|string $canHavePayableActions The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBycanHavePayableActions($canHavePayableActions = null, $comparison = null)
    {
        if (is_string($canHavePayableActions)) {
            $canHavePayableActions = in_array(strtolower($canHavePayableActions), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::CANHAVEPAYABLEACTIONS, $canHavePayableActions, $comparison);
    }

    /**
     * Filter the query on the isRequiredCoordination column
     *
     * Example usage:
     * <code>
     * $query->filterByisRequiredCoordination(true); // WHERE isRequiredCoordination = true
     * $query->filterByisRequiredCoordination('yes'); // WHERE isRequiredCoordination = true
     * </code>
     *
     * @param     boolean|string $isRequiredCoordination The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisRequiredCoordination($isRequiredCoordination = null, $comparison = null)
    {
        if (is_string($isRequiredCoordination)) {
            $isRequiredCoordination = in_array(strtolower($isRequiredCoordination), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISREQUIREDCOORDINATION, $isRequiredCoordination, $comparison);
    }

    /**
     * Filter the query on the isOrgStructurePriority column
     *
     * Example usage:
     * <code>
     * $query->filterByisOrgStructurePriority(true); // WHERE isOrgStructurePriority = true
     * $query->filterByisOrgStructurePriority('yes'); // WHERE isOrgStructurePriority = true
     * </code>
     *
     * @param     boolean|string $isOrgStructurePriority The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisOrgStructurePriority($isOrgStructurePriority = null, $comparison = null)
    {
        if (is_string($isOrgStructurePriority)) {
            $isOrgStructurePriority = in_array(strtolower($isOrgStructurePriority), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISORGSTRUCTUREPRIORITY, $isOrgStructurePriority, $comparison);
    }

    /**
     * Filter the query on the isTakenTissue column
     *
     * Example usage:
     * <code>
     * $query->filterByisTakenTissue(true); // WHERE isTakenTissue = true
     * $query->filterByisTakenTissue('yes'); // WHERE isTakenTissue = true
     * </code>
     *
     * @param     boolean|string $isTakenTissue The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByisTakenTissue($isTakenTissue = null, $comparison = null)
    {
        if (is_string($isTakenTissue)) {
            $isTakenTissue = in_array(strtolower($isTakenTissue), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(EventTypePeer::ISTAKENTISSUE, $isTakenTissue, $comparison);
    }

    /**
     * Filter the query on the sex column
     *
     * Example usage:
     * <code>
     * $query->filterBysex(1234); // WHERE sex = 1234
     * $query->filterBysex(array(12, 34)); // WHERE sex IN (12, 34)
     * $query->filterBysex(array('min' => 12)); // WHERE sex >= 12
     * $query->filterBysex(array('max' => 12)); // WHERE sex <= 12
     * </code>
     *
     * @param     mixed $sex The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_array($sex)) {
            $useMinMax = false;
            if (isset($sex['min'])) {
                $this->addUsingAlias(EventTypePeer::SEX, $sex['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sex['max'])) {
                $this->addUsingAlias(EventTypePeer::SEX, $sex['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::SEX, $sex, $comparison);
    }

    /**
     * Filter the query on the age column
     *
     * Example usage:
     * <code>
     * $query->filterByage('fooValue');   // WHERE age = 'fooValue'
     * $query->filterByage('%fooValue%'); // WHERE age LIKE '%fooValue%'
     * </code>
     *
     * @param     string $age The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByage($age = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($age)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $age)) {
                $age = str_replace('*', '%', $age);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(EventTypePeer::AGE, $age, $comparison);
    }

    /**
     * Filter the query on the rbMedicalKind_id column
     *
     * Example usage:
     * <code>
     * $query->filterByrbMedicalKindId(1234); // WHERE rbMedicalKind_id = 1234
     * $query->filterByrbMedicalKindId(array(12, 34)); // WHERE rbMedicalKind_id IN (12, 34)
     * $query->filterByrbMedicalKindId(array('min' => 12)); // WHERE rbMedicalKind_id >= 12
     * $query->filterByrbMedicalKindId(array('max' => 12)); // WHERE rbMedicalKind_id <= 12
     * </code>
     *
     * @param     mixed $rbMedicalKindId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByrbMedicalKindId($rbMedicalKindId = null, $comparison = null)
    {
        if (is_array($rbMedicalKindId)) {
            $useMinMax = false;
            if (isset($rbMedicalKindId['min'])) {
                $this->addUsingAlias(EventTypePeer::RBMEDICALKIND_ID, $rbMedicalKindId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($rbMedicalKindId['max'])) {
                $this->addUsingAlias(EventTypePeer::RBMEDICALKIND_ID, $rbMedicalKindId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::RBMEDICALKIND_ID, $rbMedicalKindId, $comparison);
    }

    /**
     * Filter the query on the age_bu column
     *
     * Example usage:
     * <code>
     * $query->filterByageBu(1234); // WHERE age_bu = 1234
     * $query->filterByageBu(array(12, 34)); // WHERE age_bu IN (12, 34)
     * $query->filterByageBu(array('min' => 12)); // WHERE age_bu >= 12
     * $query->filterByageBu(array('max' => 12)); // WHERE age_bu <= 12
     * </code>
     *
     * @param     mixed $ageBu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByageBu($ageBu = null, $comparison = null)
    {
        if (is_array($ageBu)) {
            $useMinMax = false;
            if (isset($ageBu['min'])) {
                $this->addUsingAlias(EventTypePeer::AGE_BU, $ageBu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBu['max'])) {
                $this->addUsingAlias(EventTypePeer::AGE_BU, $ageBu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::AGE_BU, $ageBu, $comparison);
    }

    /**
     * Filter the query on the age_bc column
     *
     * Example usage:
     * <code>
     * $query->filterByageBc(1234); // WHERE age_bc = 1234
     * $query->filterByageBc(array(12, 34)); // WHERE age_bc IN (12, 34)
     * $query->filterByageBc(array('min' => 12)); // WHERE age_bc >= 12
     * $query->filterByageBc(array('max' => 12)); // WHERE age_bc <= 12
     * </code>
     *
     * @param     mixed $ageBc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByageBc($ageBc = null, $comparison = null)
    {
        if (is_array($ageBc)) {
            $useMinMax = false;
            if (isset($ageBc['min'])) {
                $this->addUsingAlias(EventTypePeer::AGE_BC, $ageBc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBc['max'])) {
                $this->addUsingAlias(EventTypePeer::AGE_BC, $ageBc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::AGE_BC, $ageBc, $comparison);
    }

    /**
     * Filter the query on the age_eu column
     *
     * Example usage:
     * <code>
     * $query->filterByageEu(1234); // WHERE age_eu = 1234
     * $query->filterByageEu(array(12, 34)); // WHERE age_eu IN (12, 34)
     * $query->filterByageEu(array('min' => 12)); // WHERE age_eu >= 12
     * $query->filterByageEu(array('max' => 12)); // WHERE age_eu <= 12
     * </code>
     *
     * @param     mixed $ageEu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByageEu($ageEu = null, $comparison = null)
    {
        if (is_array($ageEu)) {
            $useMinMax = false;
            if (isset($ageEu['min'])) {
                $this->addUsingAlias(EventTypePeer::AGE_EU, $ageEu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEu['max'])) {
                $this->addUsingAlias(EventTypePeer::AGE_EU, $ageEu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::AGE_EU, $ageEu, $comparison);
    }

    /**
     * Filter the query on the age_ec column
     *
     * Example usage:
     * <code>
     * $query->filterByageEc(1234); // WHERE age_ec = 1234
     * $query->filterByageEc(array(12, 34)); // WHERE age_ec IN (12, 34)
     * $query->filterByageEc(array('min' => 12)); // WHERE age_ec >= 12
     * $query->filterByageEc(array('max' => 12)); // WHERE age_ec <= 12
     * </code>
     *
     * @param     mixed $ageEc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByageEc($ageEc = null, $comparison = null)
    {
        if (is_array($ageEc)) {
            $useMinMax = false;
            if (isset($ageEc['min'])) {
                $this->addUsingAlias(EventTypePeer::AGE_EC, $ageEc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEc['max'])) {
                $this->addUsingAlias(EventTypePeer::AGE_EC, $ageEc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::AGE_EC, $ageEc, $comparison);
    }

    /**
     * Filter the query on the requestType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByrequestTypeId(1234); // WHERE requestType_id = 1234
     * $query->filterByrequestTypeId(array(12, 34)); // WHERE requestType_id IN (12, 34)
     * $query->filterByrequestTypeId(array('min' => 12)); // WHERE requestType_id >= 12
     * $query->filterByrequestTypeId(array('max' => 12)); // WHERE requestType_id <= 12
     * </code>
     *
     * @param     mixed $requestTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function filterByrequestTypeId($requestTypeId = null, $comparison = null)
    {
        if (is_array($requestTypeId)) {
            $useMinMax = false;
            if (isset($requestTypeId['min'])) {
                $this->addUsingAlias(EventTypePeer::REQUESTTYPE_ID, $requestTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($requestTypeId['max'])) {
                $this->addUsingAlias(EventTypePeer::REQUESTTYPE_ID, $requestTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(EventTypePeer::REQUESTTYPE_ID, $requestTypeId, $comparison);
    }

    /**
     * Filter the query by a related RbEventTypePurpose object
     *
     * @param   RbEventTypePurpose|PropelObjectCollection $rbEventTypePurpose The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbEventTypePurpose($rbEventTypePurpose, $comparison = null)
    {
        if ($rbEventTypePurpose instanceof RbEventTypePurpose) {
            return $this
                ->addUsingAlias(EventTypePeer::PURPOSE_ID, $rbEventTypePurpose->getid(), $comparison);
        } elseif ($rbEventTypePurpose instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventTypePeer::PURPOSE_ID, $rbEventTypePurpose->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByRbEventTypePurpose() only accepts arguments of type RbEventTypePurpose or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RbEventTypePurpose relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function joinRbEventTypePurpose($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RbEventTypePurpose');

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
            $this->addJoinObject($join, 'RbEventTypePurpose');
        }

        return $this;
    }

    /**
     * Use the RbEventTypePurpose relation RbEventTypePurpose object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RbEventTypePurposeQuery A secondary query class using the current class as primary query
     */
    public function useRbEventTypePurposeQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinRbEventTypePurpose($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RbEventTypePurpose', '\Webmis\Models\RbEventTypePurposeQuery');
    }

    /**
     * Filter the query by a related RbResult object
     *
     * @param   RbResult|PropelObjectCollection $rbResult The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRbResult($rbResult, $comparison = null)
    {
        if ($rbResult instanceof RbResult) {
            return $this
                ->addUsingAlias(EventTypePeer::PURPOSE_ID, $rbResult->geteventPurposeId(), $comparison);
        } elseif ($rbResult instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(EventTypePeer::PURPOSE_ID, $rbResult->toKeyValue('PrimaryKey', 'eventPurposeId'), $comparison);
        } else {
            throw new PropelException('filterByRbResult() only accepts arguments of type RbResult or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RbResult relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function joinRbResult($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RbResult');

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
            $this->addJoinObject($join, 'RbResult');
        }

        return $this;
    }

    /**
     * Use the RbResult relation RbResult object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RbResultQuery A secondary query class using the current class as primary query
     */
    public function useRbResultQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinRbResult($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RbResult', '\Webmis\Models\RbResultQuery');
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 EventTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEvent($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(EventTypePeer::ID, $event->geteventTypeId(), $comparison);
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
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function joinEvent($relationAlias = null, $joinType = Criteria::INNER_JOIN)
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
    public function useEventQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinEvent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Event', '\Webmis\Models\EventQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   EventType $eventType Object to remove from the list of results
     *
     * @return EventTypeQuery The current query, for fluid interface
     */
    public function prune($eventType = null)
    {
        if ($eventType) {
            $this->addUsingAlias(EventTypePeer::ID, $eventType->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(EventTypePeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(EventTypePeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(EventTypePeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(EventTypePeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(EventTypePeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     EventTypeQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(EventTypePeer::CREATEDATETIME);
    }
}
