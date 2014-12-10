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
use Webmis\Models\ActionPropertyType;
use Webmis\Models\ActionType;
use Webmis\Models\ActionTypePeer;
use Webmis\Models\ActionTypeQuery;

/**
 * Base class that represents a query for the 'ActionType' table.
 *
 *
 *
 * @method ActionTypeQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ActionTypeQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method ActionTypeQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method ActionTypeQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method ActionTypeQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method ActionTypeQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ActionTypeQuery orderByClass($order = Criteria::ASC) Order by the class column
 * @method ActionTypeQuery orderBygroupId($order = Criteria::ASC) Order by the group_id column
 * @method ActionTypeQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method ActionTypeQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method ActionTypeQuery orderBytitle($order = Criteria::ASC) Order by the title column
 * @method ActionTypeQuery orderByflatCode($order = Criteria::ASC) Order by the flatCode column
 * @method ActionTypeQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method ActionTypeQuery orderByage($order = Criteria::ASC) Order by the age column
 * @method ActionTypeQuery orderByageBu($order = Criteria::ASC) Order by the age_bu column
 * @method ActionTypeQuery orderByageBc($order = Criteria::ASC) Order by the age_bc column
 * @method ActionTypeQuery orderByageEu($order = Criteria::ASC) Order by the age_eu column
 * @method ActionTypeQuery orderByageEc($order = Criteria::ASC) Order by the age_ec column
 * @method ActionTypeQuery orderByoffice($order = Criteria::ASC) Order by the office column
 * @method ActionTypeQuery orderByshowInForm($order = Criteria::ASC) Order by the showInForm column
 * @method ActionTypeQuery orderBygenTimeTable($order = Criteria::ASC) Order by the genTimetable column
 * @method ActionTypeQuery orderByserviceId($order = Criteria::ASC) Order by the service_id column
 * @method ActionTypeQuery orderByquotaTypeId($order = Criteria::ASC) Order by the quotaType_id column
 * @method ActionTypeQuery orderBycontext($order = Criteria::ASC) Order by the context column
 * @method ActionTypeQuery orderByamount($order = Criteria::ASC) Order by the amount column
 * @method ActionTypeQuery orderByamountEvaluation($order = Criteria::ASC) Order by the amountEvaluation column
 * @method ActionTypeQuery orderBydefaultStatus($order = Criteria::ASC) Order by the defaultStatus column
 * @method ActionTypeQuery orderBydefaultDirectionDate($order = Criteria::ASC) Order by the defaultDirectionDate column
 * @method ActionTypeQuery orderBydefaultPlannedEndDate($order = Criteria::ASC) Order by the defaultPlannedEndDate column
 * @method ActionTypeQuery orderBydefaultEndDate($order = Criteria::ASC) Order by the defaultEndDate column
 * @method ActionTypeQuery orderBydefaultExecPersonId($order = Criteria::ASC) Order by the defaultExecPerson_id column
 * @method ActionTypeQuery orderBydefaultPersonInEvent($order = Criteria::ASC) Order by the defaultPersonInEvent column
 * @method ActionTypeQuery orderBydefaultPersonInEditor($order = Criteria::ASC) Order by the defaultPersonInEditor column
 * @method ActionTypeQuery orderBymaxOccursInEvent($order = Criteria::ASC) Order by the maxOccursInEvent column
 * @method ActionTypeQuery orderByshowTime($order = Criteria::ASC) Order by the showTime column
 * @method ActionTypeQuery orderByisMes($order = Criteria::ASC) Order by the isMES column
 * @method ActionTypeQuery orderBynomenclativeServiceId($order = Criteria::ASC) Order by the nomenclativeService_id column
 * @method ActionTypeQuery orderByisPreferable($order = Criteria::ASC) Order by the isPreferable column
 * @method ActionTypeQuery orderByprescribedTypeId($order = Criteria::ASC) Order by the prescribedType_id column
 * @method ActionTypeQuery orderBysheduleId($order = Criteria::ASC) Order by the shedule_id column
 * @method ActionTypeQuery orderByisRequiredCoordination($order = Criteria::ASC) Order by the isRequiredCoordination column
 * @method ActionTypeQuery orderByisRequiredTissue($order = Criteria::ASC) Order by the isRequiredTissue column
 * @method ActionTypeQuery orderBytestTubeTypeId($order = Criteria::ASC) Order by the testTubeType_id column
 * @method ActionTypeQuery orderByjobTypeId($order = Criteria::ASC) Order by the jobType_id column
 * @method ActionTypeQuery orderBymnem($order = Criteria::ASC) Order by the mnem column
 *
 * @method ActionTypeQuery groupByid() Group by the id column
 * @method ActionTypeQuery groupBycreateDatetime() Group by the createDatetime column
 * @method ActionTypeQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method ActionTypeQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method ActionTypeQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method ActionTypeQuery groupBydeleted() Group by the deleted column
 * @method ActionTypeQuery groupByClass() Group by the class column
 * @method ActionTypeQuery groupBygroupId() Group by the group_id column
 * @method ActionTypeQuery groupBycode() Group by the code column
 * @method ActionTypeQuery groupByname() Group by the name column
 * @method ActionTypeQuery groupBytitle() Group by the title column
 * @method ActionTypeQuery groupByflatCode() Group by the flatCode column
 * @method ActionTypeQuery groupBysex() Group by the sex column
 * @method ActionTypeQuery groupByage() Group by the age column
 * @method ActionTypeQuery groupByageBu() Group by the age_bu column
 * @method ActionTypeQuery groupByageBc() Group by the age_bc column
 * @method ActionTypeQuery groupByageEu() Group by the age_eu column
 * @method ActionTypeQuery groupByageEc() Group by the age_ec column
 * @method ActionTypeQuery groupByoffice() Group by the office column
 * @method ActionTypeQuery groupByshowInForm() Group by the showInForm column
 * @method ActionTypeQuery groupBygenTimeTable() Group by the genTimetable column
 * @method ActionTypeQuery groupByserviceId() Group by the service_id column
 * @method ActionTypeQuery groupByquotaTypeId() Group by the quotaType_id column
 * @method ActionTypeQuery groupBycontext() Group by the context column
 * @method ActionTypeQuery groupByamount() Group by the amount column
 * @method ActionTypeQuery groupByamountEvaluation() Group by the amountEvaluation column
 * @method ActionTypeQuery groupBydefaultStatus() Group by the defaultStatus column
 * @method ActionTypeQuery groupBydefaultDirectionDate() Group by the defaultDirectionDate column
 * @method ActionTypeQuery groupBydefaultPlannedEndDate() Group by the defaultPlannedEndDate column
 * @method ActionTypeQuery groupBydefaultEndDate() Group by the defaultEndDate column
 * @method ActionTypeQuery groupBydefaultExecPersonId() Group by the defaultExecPerson_id column
 * @method ActionTypeQuery groupBydefaultPersonInEvent() Group by the defaultPersonInEvent column
 * @method ActionTypeQuery groupBydefaultPersonInEditor() Group by the defaultPersonInEditor column
 * @method ActionTypeQuery groupBymaxOccursInEvent() Group by the maxOccursInEvent column
 * @method ActionTypeQuery groupByshowTime() Group by the showTime column
 * @method ActionTypeQuery groupByisMes() Group by the isMES column
 * @method ActionTypeQuery groupBynomenclativeServiceId() Group by the nomenclativeService_id column
 * @method ActionTypeQuery groupByisPreferable() Group by the isPreferable column
 * @method ActionTypeQuery groupByprescribedTypeId() Group by the prescribedType_id column
 * @method ActionTypeQuery groupBysheduleId() Group by the shedule_id column
 * @method ActionTypeQuery groupByisRequiredCoordination() Group by the isRequiredCoordination column
 * @method ActionTypeQuery groupByisRequiredTissue() Group by the isRequiredTissue column
 * @method ActionTypeQuery groupBytestTubeTypeId() Group by the testTubeType_id column
 * @method ActionTypeQuery groupByjobTypeId() Group by the jobType_id column
 * @method ActionTypeQuery groupBymnem() Group by the mnem column
 *
 * @method ActionTypeQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ActionTypeQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ActionTypeQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ActionTypeQuery leftJoinActionPropertyTypeRelatedByid($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyTypeRelatedByid relation
 * @method ActionTypeQuery rightJoinActionPropertyTypeRelatedByid($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyTypeRelatedByid relation
 * @method ActionTypeQuery innerJoinActionPropertyTypeRelatedByid($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyTypeRelatedByid relation
 *
 * @method ActionTypeQuery leftJoinAction($relationAlias = null) Adds a LEFT JOIN clause to the query using the Action relation
 * @method ActionTypeQuery rightJoinAction($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Action relation
 * @method ActionTypeQuery innerJoinAction($relationAlias = null) Adds a INNER JOIN clause to the query using the Action relation
 *
 * @method ActionTypeQuery leftJoinActionPropertyTypeRelatedByactionTypeId($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyTypeRelatedByactionTypeId relation
 * @method ActionTypeQuery rightJoinActionPropertyTypeRelatedByactionTypeId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyTypeRelatedByactionTypeId relation
 * @method ActionTypeQuery innerJoinActionPropertyTypeRelatedByactionTypeId($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyTypeRelatedByactionTypeId relation
 *
 * @method ActionType findOne(PropelPDO $con = null) Return the first ActionType matching the query
 * @method ActionType findOneOrCreate(PropelPDO $con = null) Return the first ActionType matching the query, or a new ActionType object populated from the query conditions when no match is found
 *
 * @method ActionType findOneBycreateDatetime(string $createDatetime) Return the first ActionType filtered by the createDatetime column
 * @method ActionType findOneBycreatePersonId(int $createPerson_id) Return the first ActionType filtered by the createPerson_id column
 * @method ActionType findOneBymodifyDatetime(string $modifyDatetime) Return the first ActionType filtered by the modifyDatetime column
 * @method ActionType findOneBymodifyPersonId(int $modifyPerson_id) Return the first ActionType filtered by the modifyPerson_id column
 * @method ActionType findOneBydeleted(boolean $deleted) Return the first ActionType filtered by the deleted column
 * @method ActionType findOneByClass(boolean $class) Return the first ActionType filtered by the class column
 * @method ActionType findOneBygroupId(int $group_id) Return the first ActionType filtered by the group_id column
 * @method ActionType findOneBycode(string $code) Return the first ActionType filtered by the code column
 * @method ActionType findOneByname(string $name) Return the first ActionType filtered by the name column
 * @method ActionType findOneBytitle(string $title) Return the first ActionType filtered by the title column
 * @method ActionType findOneByflatCode(string $flatCode) Return the first ActionType filtered by the flatCode column
 * @method ActionType findOneBysex(int $sex) Return the first ActionType filtered by the sex column
 * @method ActionType findOneByage(string $age) Return the first ActionType filtered by the age column
 * @method ActionType findOneByageBu(int $age_bu) Return the first ActionType filtered by the age_bu column
 * @method ActionType findOneByageBc(int $age_bc) Return the first ActionType filtered by the age_bc column
 * @method ActionType findOneByageEu(int $age_eu) Return the first ActionType filtered by the age_eu column
 * @method ActionType findOneByageEc(int $age_ec) Return the first ActionType filtered by the age_ec column
 * @method ActionType findOneByoffice(string $office) Return the first ActionType filtered by the office column
 * @method ActionType findOneByshowInForm(boolean $showInForm) Return the first ActionType filtered by the showInForm column
 * @method ActionType findOneBygenTimeTable(boolean $genTimetable) Return the first ActionType filtered by the genTimetable column
 * @method ActionType findOneByserviceId(int $service_id) Return the first ActionType filtered by the service_id column
 * @method ActionType findOneByquotaTypeId(int $quotaType_id) Return the first ActionType filtered by the quotaType_id column
 * @method ActionType findOneBycontext(string $context) Return the first ActionType filtered by the context column
 * @method ActionType findOneByamount(double $amount) Return the first ActionType filtered by the amount column
 * @method ActionType findOneByamountEvaluation(int $amountEvaluation) Return the first ActionType filtered by the amountEvaluation column
 * @method ActionType findOneBydefaultStatus(int $defaultStatus) Return the first ActionType filtered by the defaultStatus column
 * @method ActionType findOneBydefaultDirectionDate(int $defaultDirectionDate) Return the first ActionType filtered by the defaultDirectionDate column
 * @method ActionType findOneBydefaultPlannedEndDate(boolean $defaultPlannedEndDate) Return the first ActionType filtered by the defaultPlannedEndDate column
 * @method ActionType findOneBydefaultEndDate(int $defaultEndDate) Return the first ActionType filtered by the defaultEndDate column
 * @method ActionType findOneBydefaultExecPersonId(int $defaultExecPerson_id) Return the first ActionType filtered by the defaultExecPerson_id column
 * @method ActionType findOneBydefaultPersonInEvent(int $defaultPersonInEvent) Return the first ActionType filtered by the defaultPersonInEvent column
 * @method ActionType findOneBydefaultPersonInEditor(int $defaultPersonInEditor) Return the first ActionType filtered by the defaultPersonInEditor column
 * @method ActionType findOneBymaxOccursInEvent(int $maxOccursInEvent) Return the first ActionType filtered by the maxOccursInEvent column
 * @method ActionType findOneByshowTime(boolean $showTime) Return the first ActionType filtered by the showTime column
 * @method ActionType findOneByisMes(int $isMES) Return the first ActionType filtered by the isMES column
 * @method ActionType findOneBynomenclativeServiceId(int $nomenclativeService_id) Return the first ActionType filtered by the nomenclativeService_id column
 * @method ActionType findOneByisPreferable(boolean $isPreferable) Return the first ActionType filtered by the isPreferable column
 * @method ActionType findOneByprescribedTypeId(int $prescribedType_id) Return the first ActionType filtered by the prescribedType_id column
 * @method ActionType findOneBysheduleId(int $shedule_id) Return the first ActionType filtered by the shedule_id column
 * @method ActionType findOneByisRequiredCoordination(boolean $isRequiredCoordination) Return the first ActionType filtered by the isRequiredCoordination column
 * @method ActionType findOneByisRequiredTissue(boolean $isRequiredTissue) Return the first ActionType filtered by the isRequiredTissue column
 * @method ActionType findOneBytestTubeTypeId(int $testTubeType_id) Return the first ActionType filtered by the testTubeType_id column
 * @method ActionType findOneByjobTypeId(int $jobType_id) Return the first ActionType filtered by the jobType_id column
 * @method ActionType findOneBymnem(string $mnem) Return the first ActionType filtered by the mnem column
 *
 * @method array findByid(int $id) Return ActionType objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return ActionType objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return ActionType objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return ActionType objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return ActionType objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return ActionType objects filtered by the deleted column
 * @method array findByClass(boolean $class) Return ActionType objects filtered by the class column
 * @method array findBygroupId(int $group_id) Return ActionType objects filtered by the group_id column
 * @method array findBycode(string $code) Return ActionType objects filtered by the code column
 * @method array findByname(string $name) Return ActionType objects filtered by the name column
 * @method array findBytitle(string $title) Return ActionType objects filtered by the title column
 * @method array findByflatCode(string $flatCode) Return ActionType objects filtered by the flatCode column
 * @method array findBysex(int $sex) Return ActionType objects filtered by the sex column
 * @method array findByage(string $age) Return ActionType objects filtered by the age column
 * @method array findByageBu(int $age_bu) Return ActionType objects filtered by the age_bu column
 * @method array findByageBc(int $age_bc) Return ActionType objects filtered by the age_bc column
 * @method array findByageEu(int $age_eu) Return ActionType objects filtered by the age_eu column
 * @method array findByageEc(int $age_ec) Return ActionType objects filtered by the age_ec column
 * @method array findByoffice(string $office) Return ActionType objects filtered by the office column
 * @method array findByshowInForm(boolean $showInForm) Return ActionType objects filtered by the showInForm column
 * @method array findBygenTimeTable(boolean $genTimetable) Return ActionType objects filtered by the genTimetable column
 * @method array findByserviceId(int $service_id) Return ActionType objects filtered by the service_id column
 * @method array findByquotaTypeId(int $quotaType_id) Return ActionType objects filtered by the quotaType_id column
 * @method array findBycontext(string $context) Return ActionType objects filtered by the context column
 * @method array findByamount(double $amount) Return ActionType objects filtered by the amount column
 * @method array findByamountEvaluation(int $amountEvaluation) Return ActionType objects filtered by the amountEvaluation column
 * @method array findBydefaultStatus(int $defaultStatus) Return ActionType objects filtered by the defaultStatus column
 * @method array findBydefaultDirectionDate(int $defaultDirectionDate) Return ActionType objects filtered by the defaultDirectionDate column
 * @method array findBydefaultPlannedEndDate(boolean $defaultPlannedEndDate) Return ActionType objects filtered by the defaultPlannedEndDate column
 * @method array findBydefaultEndDate(int $defaultEndDate) Return ActionType objects filtered by the defaultEndDate column
 * @method array findBydefaultExecPersonId(int $defaultExecPerson_id) Return ActionType objects filtered by the defaultExecPerson_id column
 * @method array findBydefaultPersonInEvent(int $defaultPersonInEvent) Return ActionType objects filtered by the defaultPersonInEvent column
 * @method array findBydefaultPersonInEditor(int $defaultPersonInEditor) Return ActionType objects filtered by the defaultPersonInEditor column
 * @method array findBymaxOccursInEvent(int $maxOccursInEvent) Return ActionType objects filtered by the maxOccursInEvent column
 * @method array findByshowTime(boolean $showTime) Return ActionType objects filtered by the showTime column
 * @method array findByisMes(int $isMES) Return ActionType objects filtered by the isMES column
 * @method array findBynomenclativeServiceId(int $nomenclativeService_id) Return ActionType objects filtered by the nomenclativeService_id column
 * @method array findByisPreferable(boolean $isPreferable) Return ActionType objects filtered by the isPreferable column
 * @method array findByprescribedTypeId(int $prescribedType_id) Return ActionType objects filtered by the prescribedType_id column
 * @method array findBysheduleId(int $shedule_id) Return ActionType objects filtered by the shedule_id column
 * @method array findByisRequiredCoordination(boolean $isRequiredCoordination) Return ActionType objects filtered by the isRequiredCoordination column
 * @method array findByisRequiredTissue(boolean $isRequiredTissue) Return ActionType objects filtered by the isRequiredTissue column
 * @method array findBytestTubeTypeId(int $testTubeType_id) Return ActionType objects filtered by the testTubeType_id column
 * @method array findByjobTypeId(int $jobType_id) Return ActionType objects filtered by the jobType_id column
 * @method array findBymnem(string $mnem) Return ActionType objects filtered by the mnem column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionTypeQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseActionTypeQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\ActionType', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ActionTypeQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ActionTypeQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ActionTypeQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ActionTypeQuery) {
            return $criteria;
        }
        $query = new ActionTypeQuery();
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
     * @return   ActionType|ActionType[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ActionTypePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionType A model object, or null if the key is not found
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
     * @return                 ActionType A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `class`, `group_id`, `code`, `name`, `title`, `flatCode`, `sex`, `age`, `age_bu`, `age_bc`, `age_eu`, `age_ec`, `office`, `showInForm`, `genTimetable`, `service_id`, `quotaType_id`, `context`, `amount`, `amountEvaluation`, `defaultStatus`, `defaultDirectionDate`, `defaultPlannedEndDate`, `defaultEndDate`, `defaultExecPerson_id`, `defaultPersonInEvent`, `defaultPersonInEditor`, `maxOccursInEvent`, `showTime`, `isMES`, `nomenclativeService_id`, `isPreferable`, `prescribedType_id`, `shedule_id`, `isRequiredCoordination`, `isRequiredTissue`, `testTubeType_id`, `jobType_id`, `mnem` FROM `ActionType` WHERE `id` = :p0';
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
            $obj = new ActionType();
            $obj->hydrate($row);
            ActionTypePeer::addInstanceToPool($obj, (string) $key);
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
     * @return ActionType|ActionType[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|ActionType[]|mixed the list of results, formatted by the current formatter
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ActionTypePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ActionTypePeer::ID, $keys, Criteria::IN);
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
     * @see       filterByActionPropertyTypeRelatedByid()
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ActionTypePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ActionTypePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::ID, $id, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(ActionTypePeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(ActionTypePeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(ActionTypePeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(ActionTypePeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(ActionTypePeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(ActionTypePeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(ActionTypePeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(ActionTypePeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::DELETED, $deleted, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByClass($class = null, $comparison = null)
    {
        if (is_string($class)) {
            $class = in_array(strtolower($class), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::CLAZZ, $class, $comparison);
    }

    /**
     * Filter the query on the group_id column
     *
     * Example usage:
     * <code>
     * $query->filterBygroupId(1234); // WHERE group_id = 1234
     * $query->filterBygroupId(array(12, 34)); // WHERE group_id IN (12, 34)
     * $query->filterBygroupId(array('min' => 12)); // WHERE group_id >= 12
     * $query->filterBygroupId(array('max' => 12)); // WHERE group_id <= 12
     * </code>
     *
     * @param     mixed $groupId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBygroupId($groupId = null, $comparison = null)
    {
        if (is_array($groupId)) {
            $useMinMax = false;
            if (isset($groupId['min'])) {
                $this->addUsingAlias(ActionTypePeer::GROUP_ID, $groupId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($groupId['max'])) {
                $this->addUsingAlias(ActionTypePeer::GROUP_ID, $groupId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::GROUP_ID, $groupId, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionTypePeer::CODE, $code, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionTypePeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the title column
     *
     * Example usage:
     * <code>
     * $query->filterBytitle('fooValue');   // WHERE title = 'fooValue'
     * $query->filterBytitle('%fooValue%'); // WHERE title LIKE '%fooValue%'
     * </code>
     *
     * @param     string $title The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBytitle($title = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($title)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $title)) {
                $title = str_replace('*', '%', $title);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::TITLE, $title, $comparison);
    }

    /**
     * Filter the query on the flatCode column
     *
     * Example usage:
     * <code>
     * $query->filterByflatCode('fooValue');   // WHERE flatCode = 'fooValue'
     * $query->filterByflatCode('%fooValue%'); // WHERE flatCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $flatCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByflatCode($flatCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($flatCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $flatCode)) {
                $flatCode = str_replace('*', '%', $flatCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::FLATCODE, $flatCode, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_array($sex)) {
            $useMinMax = false;
            if (isset($sex['min'])) {
                $this->addUsingAlias(ActionTypePeer::SEX, $sex['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sex['max'])) {
                $this->addUsingAlias(ActionTypePeer::SEX, $sex['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::SEX, $sex, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionTypePeer::AGE, $age, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByageBu($ageBu = null, $comparison = null)
    {
        if (is_array($ageBu)) {
            $useMinMax = false;
            if (isset($ageBu['min'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_BU, $ageBu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBu['max'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_BU, $ageBu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AGE_BU, $ageBu, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByageBc($ageBc = null, $comparison = null)
    {
        if (is_array($ageBc)) {
            $useMinMax = false;
            if (isset($ageBc['min'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_BC, $ageBc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBc['max'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_BC, $ageBc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AGE_BC, $ageBc, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByageEu($ageEu = null, $comparison = null)
    {
        if (is_array($ageEu)) {
            $useMinMax = false;
            if (isset($ageEu['min'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_EU, $ageEu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEu['max'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_EU, $ageEu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AGE_EU, $ageEu, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByageEc($ageEc = null, $comparison = null)
    {
        if (is_array($ageEc)) {
            $useMinMax = false;
            if (isset($ageEc['min'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_EC, $ageEc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEc['max'])) {
                $this->addUsingAlias(ActionTypePeer::AGE_EC, $ageEc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AGE_EC, $ageEc, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionTypePeer::OFFICE, $office, $comparison);
    }

    /**
     * Filter the query on the showInForm column
     *
     * Example usage:
     * <code>
     * $query->filterByshowInForm(true); // WHERE showInForm = true
     * $query->filterByshowInForm('yes'); // WHERE showInForm = true
     * </code>
     *
     * @param     boolean|string $showInForm The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByshowInForm($showInForm = null, $comparison = null)
    {
        if (is_string($showInForm)) {
            $showInForm = in_array(strtolower($showInForm), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::SHOWINFORM, $showInForm, $comparison);
    }

    /**
     * Filter the query on the genTimetable column
     *
     * Example usage:
     * <code>
     * $query->filterBygenTimeTable(true); // WHERE genTimetable = true
     * $query->filterBygenTimeTable('yes'); // WHERE genTimetable = true
     * </code>
     *
     * @param     boolean|string $genTimeTable The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBygenTimeTable($genTimeTable = null, $comparison = null)
    {
        if (is_string($genTimeTable)) {
            $genTimeTable = in_array(strtolower($genTimeTable), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::GENTIMETABLE, $genTimeTable, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByserviceId($serviceId = null, $comparison = null)
    {
        if (is_array($serviceId)) {
            $useMinMax = false;
            if (isset($serviceId['min'])) {
                $this->addUsingAlias(ActionTypePeer::SERVICE_ID, $serviceId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($serviceId['max'])) {
                $this->addUsingAlias(ActionTypePeer::SERVICE_ID, $serviceId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::SERVICE_ID, $serviceId, $comparison);
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
     * @param     mixed $quotaTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByquotaTypeId($quotaTypeId = null, $comparison = null)
    {
        if (is_array($quotaTypeId)) {
            $useMinMax = false;
            if (isset($quotaTypeId['min'])) {
                $this->addUsingAlias(ActionTypePeer::QUOTATYPE_ID, $quotaTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($quotaTypeId['max'])) {
                $this->addUsingAlias(ActionTypePeer::QUOTATYPE_ID, $quotaTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::QUOTATYPE_ID, $quotaTypeId, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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

        return $this->addUsingAlias(ActionTypePeer::CONTEXT, $context, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByamount($amount = null, $comparison = null)
    {
        if (is_array($amount)) {
            $useMinMax = false;
            if (isset($amount['min'])) {
                $this->addUsingAlias(ActionTypePeer::AMOUNT, $amount['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($amount['max'])) {
                $this->addUsingAlias(ActionTypePeer::AMOUNT, $amount['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AMOUNT, $amount, $comparison);
    }

    /**
     * Filter the query on the amountEvaluation column
     *
     * Example usage:
     * <code>
     * $query->filterByamountEvaluation(1234); // WHERE amountEvaluation = 1234
     * $query->filterByamountEvaluation(array(12, 34)); // WHERE amountEvaluation IN (12, 34)
     * $query->filterByamountEvaluation(array('min' => 12)); // WHERE amountEvaluation >= 12
     * $query->filterByamountEvaluation(array('max' => 12)); // WHERE amountEvaluation <= 12
     * </code>
     *
     * @param     mixed $amountEvaluation The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByamountEvaluation($amountEvaluation = null, $comparison = null)
    {
        if (is_array($amountEvaluation)) {
            $useMinMax = false;
            if (isset($amountEvaluation['min'])) {
                $this->addUsingAlias(ActionTypePeer::AMOUNTEVALUATION, $amountEvaluation['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($amountEvaluation['max'])) {
                $this->addUsingAlias(ActionTypePeer::AMOUNTEVALUATION, $amountEvaluation['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::AMOUNTEVALUATION, $amountEvaluation, $comparison);
    }

    /**
     * Filter the query on the defaultStatus column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultStatus(1234); // WHERE defaultStatus = 1234
     * $query->filterBydefaultStatus(array(12, 34)); // WHERE defaultStatus IN (12, 34)
     * $query->filterBydefaultStatus(array('min' => 12)); // WHERE defaultStatus >= 12
     * $query->filterBydefaultStatus(array('max' => 12)); // WHERE defaultStatus <= 12
     * </code>
     *
     * @param     mixed $defaultStatus The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultStatus($defaultStatus = null, $comparison = null)
    {
        if (is_array($defaultStatus)) {
            $useMinMax = false;
            if (isset($defaultStatus['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTSTATUS, $defaultStatus['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultStatus['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTSTATUS, $defaultStatus['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTSTATUS, $defaultStatus, $comparison);
    }

    /**
     * Filter the query on the defaultDirectionDate column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultDirectionDate(1234); // WHERE defaultDirectionDate = 1234
     * $query->filterBydefaultDirectionDate(array(12, 34)); // WHERE defaultDirectionDate IN (12, 34)
     * $query->filterBydefaultDirectionDate(array('min' => 12)); // WHERE defaultDirectionDate >= 12
     * $query->filterBydefaultDirectionDate(array('max' => 12)); // WHERE defaultDirectionDate <= 12
     * </code>
     *
     * @param     mixed $defaultDirectionDate The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultDirectionDate($defaultDirectionDate = null, $comparison = null)
    {
        if (is_array($defaultDirectionDate)) {
            $useMinMax = false;
            if (isset($defaultDirectionDate['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTDIRECTIONDATE, $defaultDirectionDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultDirectionDate['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTDIRECTIONDATE, $defaultDirectionDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTDIRECTIONDATE, $defaultDirectionDate, $comparison);
    }

    /**
     * Filter the query on the defaultPlannedEndDate column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultPlannedEndDate(true); // WHERE defaultPlannedEndDate = true
     * $query->filterBydefaultPlannedEndDate('yes'); // WHERE defaultPlannedEndDate = true
     * </code>
     *
     * @param     boolean|string $defaultPlannedEndDate The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultPlannedEndDate($defaultPlannedEndDate = null, $comparison = null)
    {
        if (is_string($defaultPlannedEndDate)) {
            $defaultPlannedEndDate = in_array(strtolower($defaultPlannedEndDate), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTPLANNEDENDDATE, $defaultPlannedEndDate, $comparison);
    }

    /**
     * Filter the query on the defaultEndDate column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultEndDate(1234); // WHERE defaultEndDate = 1234
     * $query->filterBydefaultEndDate(array(12, 34)); // WHERE defaultEndDate IN (12, 34)
     * $query->filterBydefaultEndDate(array('min' => 12)); // WHERE defaultEndDate >= 12
     * $query->filterBydefaultEndDate(array('max' => 12)); // WHERE defaultEndDate <= 12
     * </code>
     *
     * @param     mixed $defaultEndDate The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultEndDate($defaultEndDate = null, $comparison = null)
    {
        if (is_array($defaultEndDate)) {
            $useMinMax = false;
            if (isset($defaultEndDate['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTENDDATE, $defaultEndDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultEndDate['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTENDDATE, $defaultEndDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTENDDATE, $defaultEndDate, $comparison);
    }

    /**
     * Filter the query on the defaultExecPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultExecPersonId(1234); // WHERE defaultExecPerson_id = 1234
     * $query->filterBydefaultExecPersonId(array(12, 34)); // WHERE defaultExecPerson_id IN (12, 34)
     * $query->filterBydefaultExecPersonId(array('min' => 12)); // WHERE defaultExecPerson_id >= 12
     * $query->filterBydefaultExecPersonId(array('max' => 12)); // WHERE defaultExecPerson_id <= 12
     * </code>
     *
     * @param     mixed $defaultExecPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultExecPersonId($defaultExecPersonId = null, $comparison = null)
    {
        if (is_array($defaultExecPersonId)) {
            $useMinMax = false;
            if (isset($defaultExecPersonId['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTEXECPERSON_ID, $defaultExecPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultExecPersonId['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTEXECPERSON_ID, $defaultExecPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTEXECPERSON_ID, $defaultExecPersonId, $comparison);
    }

    /**
     * Filter the query on the defaultPersonInEvent column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultPersonInEvent(1234); // WHERE defaultPersonInEvent = 1234
     * $query->filterBydefaultPersonInEvent(array(12, 34)); // WHERE defaultPersonInEvent IN (12, 34)
     * $query->filterBydefaultPersonInEvent(array('min' => 12)); // WHERE defaultPersonInEvent >= 12
     * $query->filterBydefaultPersonInEvent(array('max' => 12)); // WHERE defaultPersonInEvent <= 12
     * </code>
     *
     * @param     mixed $defaultPersonInEvent The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultPersonInEvent($defaultPersonInEvent = null, $comparison = null)
    {
        if (is_array($defaultPersonInEvent)) {
            $useMinMax = false;
            if (isset($defaultPersonInEvent['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEVENT, $defaultPersonInEvent['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultPersonInEvent['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEVENT, $defaultPersonInEvent['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEVENT, $defaultPersonInEvent, $comparison);
    }

    /**
     * Filter the query on the defaultPersonInEditor column
     *
     * Example usage:
     * <code>
     * $query->filterBydefaultPersonInEditor(1234); // WHERE defaultPersonInEditor = 1234
     * $query->filterBydefaultPersonInEditor(array(12, 34)); // WHERE defaultPersonInEditor IN (12, 34)
     * $query->filterBydefaultPersonInEditor(array('min' => 12)); // WHERE defaultPersonInEditor >= 12
     * $query->filterBydefaultPersonInEditor(array('max' => 12)); // WHERE defaultPersonInEditor <= 12
     * </code>
     *
     * @param     mixed $defaultPersonInEditor The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBydefaultPersonInEditor($defaultPersonInEditor = null, $comparison = null)
    {
        if (is_array($defaultPersonInEditor)) {
            $useMinMax = false;
            if (isset($defaultPersonInEditor['min'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEDITOR, $defaultPersonInEditor['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($defaultPersonInEditor['max'])) {
                $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEDITOR, $defaultPersonInEditor['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::DEFAULTPERSONINEDITOR, $defaultPersonInEditor, $comparison);
    }

    /**
     * Filter the query on the maxOccursInEvent column
     *
     * Example usage:
     * <code>
     * $query->filterBymaxOccursInEvent(1234); // WHERE maxOccursInEvent = 1234
     * $query->filterBymaxOccursInEvent(array(12, 34)); // WHERE maxOccursInEvent IN (12, 34)
     * $query->filterBymaxOccursInEvent(array('min' => 12)); // WHERE maxOccursInEvent >= 12
     * $query->filterBymaxOccursInEvent(array('max' => 12)); // WHERE maxOccursInEvent <= 12
     * </code>
     *
     * @param     mixed $maxOccursInEvent The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBymaxOccursInEvent($maxOccursInEvent = null, $comparison = null)
    {
        if (is_array($maxOccursInEvent)) {
            $useMinMax = false;
            if (isset($maxOccursInEvent['min'])) {
                $this->addUsingAlias(ActionTypePeer::MAXOCCURSINEVENT, $maxOccursInEvent['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($maxOccursInEvent['max'])) {
                $this->addUsingAlias(ActionTypePeer::MAXOCCURSINEVENT, $maxOccursInEvent['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::MAXOCCURSINEVENT, $maxOccursInEvent, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByshowTime($showTime = null, $comparison = null)
    {
        if (is_string($showTime)) {
            $showTime = in_array(strtolower($showTime), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::SHOWTIME, $showTime, $comparison);
    }

    /**
     * Filter the query on the isMES column
     *
     * Example usage:
     * <code>
     * $query->filterByisMes(1234); // WHERE isMES = 1234
     * $query->filterByisMes(array(12, 34)); // WHERE isMES IN (12, 34)
     * $query->filterByisMes(array('min' => 12)); // WHERE isMES >= 12
     * $query->filterByisMes(array('max' => 12)); // WHERE isMES <= 12
     * </code>
     *
     * @param     mixed $isMes The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByisMes($isMes = null, $comparison = null)
    {
        if (is_array($isMes)) {
            $useMinMax = false;
            if (isset($isMes['min'])) {
                $this->addUsingAlias(ActionTypePeer::ISMES, $isMes['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($isMes['max'])) {
                $this->addUsingAlias(ActionTypePeer::ISMES, $isMes['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::ISMES, $isMes, $comparison);
    }

    /**
     * Filter the query on the nomenclativeService_id column
     *
     * Example usage:
     * <code>
     * $query->filterBynomenclativeServiceId(1234); // WHERE nomenclativeService_id = 1234
     * $query->filterBynomenclativeServiceId(array(12, 34)); // WHERE nomenclativeService_id IN (12, 34)
     * $query->filterBynomenclativeServiceId(array('min' => 12)); // WHERE nomenclativeService_id >= 12
     * $query->filterBynomenclativeServiceId(array('max' => 12)); // WHERE nomenclativeService_id <= 12
     * </code>
     *
     * @param     mixed $nomenclativeServiceId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBynomenclativeServiceId($nomenclativeServiceId = null, $comparison = null)
    {
        if (is_array($nomenclativeServiceId)) {
            $useMinMax = false;
            if (isset($nomenclativeServiceId['min'])) {
                $this->addUsingAlias(ActionTypePeer::NOMENCLATIVESERVICE_ID, $nomenclativeServiceId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($nomenclativeServiceId['max'])) {
                $this->addUsingAlias(ActionTypePeer::NOMENCLATIVESERVICE_ID, $nomenclativeServiceId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::NOMENCLATIVESERVICE_ID, $nomenclativeServiceId, $comparison);
    }

    /**
     * Filter the query on the isPreferable column
     *
     * Example usage:
     * <code>
     * $query->filterByisPreferable(true); // WHERE isPreferable = true
     * $query->filterByisPreferable('yes'); // WHERE isPreferable = true
     * </code>
     *
     * @param     boolean|string $isPreferable The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByisPreferable($isPreferable = null, $comparison = null)
    {
        if (is_string($isPreferable)) {
            $isPreferable = in_array(strtolower($isPreferable), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::ISPREFERABLE, $isPreferable, $comparison);
    }

    /**
     * Filter the query on the prescribedType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByprescribedTypeId(1234); // WHERE prescribedType_id = 1234
     * $query->filterByprescribedTypeId(array(12, 34)); // WHERE prescribedType_id IN (12, 34)
     * $query->filterByprescribedTypeId(array('min' => 12)); // WHERE prescribedType_id >= 12
     * $query->filterByprescribedTypeId(array('max' => 12)); // WHERE prescribedType_id <= 12
     * </code>
     *
     * @param     mixed $prescribedTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByprescribedTypeId($prescribedTypeId = null, $comparison = null)
    {
        if (is_array($prescribedTypeId)) {
            $useMinMax = false;
            if (isset($prescribedTypeId['min'])) {
                $this->addUsingAlias(ActionTypePeer::PRESCRIBEDTYPE_ID, $prescribedTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($prescribedTypeId['max'])) {
                $this->addUsingAlias(ActionTypePeer::PRESCRIBEDTYPE_ID, $prescribedTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::PRESCRIBEDTYPE_ID, $prescribedTypeId, $comparison);
    }

    /**
     * Filter the query on the shedule_id column
     *
     * Example usage:
     * <code>
     * $query->filterBysheduleId(1234); // WHERE shedule_id = 1234
     * $query->filterBysheduleId(array(12, 34)); // WHERE shedule_id IN (12, 34)
     * $query->filterBysheduleId(array('min' => 12)); // WHERE shedule_id >= 12
     * $query->filterBysheduleId(array('max' => 12)); // WHERE shedule_id <= 12
     * </code>
     *
     * @param     mixed $sheduleId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBysheduleId($sheduleId = null, $comparison = null)
    {
        if (is_array($sheduleId)) {
            $useMinMax = false;
            if (isset($sheduleId['min'])) {
                $this->addUsingAlias(ActionTypePeer::SHEDULE_ID, $sheduleId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sheduleId['max'])) {
                $this->addUsingAlias(ActionTypePeer::SHEDULE_ID, $sheduleId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::SHEDULE_ID, $sheduleId, $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByisRequiredCoordination($isRequiredCoordination = null, $comparison = null)
    {
        if (is_string($isRequiredCoordination)) {
            $isRequiredCoordination = in_array(strtolower($isRequiredCoordination), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::ISREQUIREDCOORDINATION, $isRequiredCoordination, $comparison);
    }

    /**
     * Filter the query on the isRequiredTissue column
     *
     * Example usage:
     * <code>
     * $query->filterByisRequiredTissue(true); // WHERE isRequiredTissue = true
     * $query->filterByisRequiredTissue('yes'); // WHERE isRequiredTissue = true
     * </code>
     *
     * @param     boolean|string $isRequiredTissue The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByisRequiredTissue($isRequiredTissue = null, $comparison = null)
    {
        if (is_string($isRequiredTissue)) {
            $isRequiredTissue = in_array(strtolower($isRequiredTissue), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ActionTypePeer::ISREQUIREDTISSUE, $isRequiredTissue, $comparison);
    }

    /**
     * Filter the query on the testTubeType_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytestTubeTypeId(1234); // WHERE testTubeType_id = 1234
     * $query->filterBytestTubeTypeId(array(12, 34)); // WHERE testTubeType_id IN (12, 34)
     * $query->filterBytestTubeTypeId(array('min' => 12)); // WHERE testTubeType_id >= 12
     * $query->filterBytestTubeTypeId(array('max' => 12)); // WHERE testTubeType_id <= 12
     * </code>
     *
     * @param     mixed $testTubeTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBytestTubeTypeId($testTubeTypeId = null, $comparison = null)
    {
        if (is_array($testTubeTypeId)) {
            $useMinMax = false;
            if (isset($testTubeTypeId['min'])) {
                $this->addUsingAlias(ActionTypePeer::TESTTUBETYPE_ID, $testTubeTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($testTubeTypeId['max'])) {
                $this->addUsingAlias(ActionTypePeer::TESTTUBETYPE_ID, $testTubeTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::TESTTUBETYPE_ID, $testTubeTypeId, $comparison);
    }

    /**
     * Filter the query on the jobType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByjobTypeId(1234); // WHERE jobType_id = 1234
     * $query->filterByjobTypeId(array(12, 34)); // WHERE jobType_id IN (12, 34)
     * $query->filterByjobTypeId(array('min' => 12)); // WHERE jobType_id >= 12
     * $query->filterByjobTypeId(array('max' => 12)); // WHERE jobType_id <= 12
     * </code>
     *
     * @param     mixed $jobTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterByjobTypeId($jobTypeId = null, $comparison = null)
    {
        if (is_array($jobTypeId)) {
            $useMinMax = false;
            if (isset($jobTypeId['min'])) {
                $this->addUsingAlias(ActionTypePeer::JOBTYPE_ID, $jobTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($jobTypeId['max'])) {
                $this->addUsingAlias(ActionTypePeer::JOBTYPE_ID, $jobTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::JOBTYPE_ID, $jobTypeId, $comparison);
    }

    /**
     * Filter the query on the mnem column
     *
     * Example usage:
     * <code>
     * $query->filterBymnem('fooValue');   // WHERE mnem = 'fooValue'
     * $query->filterBymnem('%fooValue%'); // WHERE mnem LIKE '%fooValue%'
     * </code>
     *
     * @param     string $mnem The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function filterBymnem($mnem = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($mnem)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $mnem)) {
                $mnem = str_replace('*', '%', $mnem);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ActionTypePeer::MNEM, $mnem, $comparison);
    }

    /**
     * Filter the query by a related ActionPropertyType object
     *
     * @param   ActionPropertyType|PropelObjectCollection $actionPropertyType The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyTypeRelatedByid($actionPropertyType, $comparison = null)
    {
        if ($actionPropertyType instanceof ActionPropertyType) {
            return $this
                ->addUsingAlias(ActionTypePeer::ID, $actionPropertyType->getactionTypeId(), $comparison);
        } elseif ($actionPropertyType instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(ActionTypePeer::ID, $actionPropertyType->toKeyValue('PrimaryKey', 'actionTypeId'), $comparison);
        } else {
            throw new PropelException('filterByActionPropertyTypeRelatedByid() only accepts arguments of type ActionPropertyType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyTypeRelatedByid relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function joinActionPropertyTypeRelatedByid($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyTypeRelatedByid');

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
            $this->addJoinObject($join, 'ActionPropertyTypeRelatedByid');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyTypeRelatedByid relation ActionPropertyType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyTypeRelatedByidQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyTypeRelatedByid($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyTypeRelatedByid', '\Webmis\Models\ActionPropertyTypeQuery');
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByAction($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(ActionTypePeer::ID, $action->getactionTypeId(), $comparison);
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
     * @return ActionTypeQuery The current query, for fluid interface
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
     * Filter the query by a related ActionPropertyType object
     *
     * @param   ActionPropertyType|PropelObjectCollection $actionPropertyType  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ActionTypeQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyTypeRelatedByactionTypeId($actionPropertyType, $comparison = null)
    {
        if ($actionPropertyType instanceof ActionPropertyType) {
            return $this
                ->addUsingAlias(ActionTypePeer::ID, $actionPropertyType->getactionTypeId(), $comparison);
        } elseif ($actionPropertyType instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyTypeRelatedByactionTypeIdQuery()
                ->filterByPrimaryKeys($actionPropertyType->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionPropertyTypeRelatedByactionTypeId() only accepts arguments of type ActionPropertyType or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyTypeRelatedByactionTypeId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function joinActionPropertyTypeRelatedByactionTypeId($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyTypeRelatedByactionTypeId');

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
            $this->addJoinObject($join, 'ActionPropertyTypeRelatedByactionTypeId');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyTypeRelatedByactionTypeId relation ActionPropertyType object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyTypeQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyTypeRelatedByactionTypeIdQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyTypeRelatedByactionTypeId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyTypeRelatedByactionTypeId', '\Webmis\Models\ActionPropertyTypeQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   ActionType $actionType Object to remove from the list of results
     *
     * @return ActionTypeQuery The current query, for fluid interface
     */
    public function prune($actionType = null)
    {
        if ($actionType) {
            $this->addUsingAlias(ActionTypePeer::ID, $actionType->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionTypePeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionTypePeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionTypePeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(ActionTypePeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(ActionTypePeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     ActionTypeQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(ActionTypePeer::CREATEDATETIME);
    }
}
