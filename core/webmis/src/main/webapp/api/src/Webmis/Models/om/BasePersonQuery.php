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
use Webmis\Models\Event;
use Webmis\Models\Person;
use Webmis\Models\PersonPeer;
use Webmis\Models\PersonQuery;

/**
 * Base class that represents a query for the 'Person' table.
 *
 *
 *
 * @method PersonQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method PersonQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method PersonQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method PersonQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method PersonQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method PersonQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method PersonQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method PersonQuery orderByfederalCode($order = Criteria::ASC) Order by the federalCode column
 * @method PersonQuery orderByregionalCode($order = Criteria::ASC) Order by the regionalCode column
 * @method PersonQuery orderBylastName($order = Criteria::ASC) Order by the lastName column
 * @method PersonQuery orderByfirstName($order = Criteria::ASC) Order by the firstName column
 * @method PersonQuery orderBypatrName($order = Criteria::ASC) Order by the patrName column
 * @method PersonQuery orderBypostId($order = Criteria::ASC) Order by the post_id column
 * @method PersonQuery orderByspecialityId($order = Criteria::ASC) Order by the speciality_id column
 * @method PersonQuery orderByorgId($order = Criteria::ASC) Order by the org_id column
 * @method PersonQuery orderByorgStructureId($order = Criteria::ASC) Order by the orgStructure_id column
 * @method PersonQuery orderByoffice($order = Criteria::ASC) Order by the office column
 * @method PersonQuery orderByoffice2($order = Criteria::ASC) Order by the office2 column
 * @method PersonQuery orderBytariffCategoryId($order = Criteria::ASC) Order by the tariffCategory_id column
 * @method PersonQuery orderByfinanceId($order = Criteria::ASC) Order by the finance_id column
 * @method PersonQuery orderByretireDate($order = Criteria::ASC) Order by the retireDate column
 * @method PersonQuery orderByambPlan($order = Criteria::ASC) Order by the ambPlan column
 * @method PersonQuery orderByambPlan2($order = Criteria::ASC) Order by the ambPlan2 column
 * @method PersonQuery orderByambNorm($order = Criteria::ASC) Order by the ambNorm column
 * @method PersonQuery orderByhomPlan($order = Criteria::ASC) Order by the homPlan column
 * @method PersonQuery orderByhomPlan2($order = Criteria::ASC) Order by the homPlan2 column
 * @method PersonQuery orderByhomNorm($order = Criteria::ASC) Order by the homNorm column
 * @method PersonQuery orderByexpPlan($order = Criteria::ASC) Order by the expPlan column
 * @method PersonQuery orderByexpNorm($order = Criteria::ASC) Order by the expNorm column
 * @method PersonQuery orderBylogin($order = Criteria::ASC) Order by the login column
 * @method PersonQuery orderBypassword($order = Criteria::ASC) Order by the password column
 * @method PersonQuery orderByuserProfileId($order = Criteria::ASC) Order by the userProfile_id column
 * @method PersonQuery orderByretired($order = Criteria::ASC) Order by the retired column
 * @method PersonQuery orderBybirthDate($order = Criteria::ASC) Order by the birthDate column
 * @method PersonQuery orderBybirthPlace($order = Criteria::ASC) Order by the birthPlace column
 * @method PersonQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method PersonQuery orderBysnils($order = Criteria::ASC) Order by the SNILS column
 * @method PersonQuery orderByinn($order = Criteria::ASC) Order by the INN column
 * @method PersonQuery orderByavailableForExternal($order = Criteria::ASC) Order by the availableForExternal column
 * @method PersonQuery orderByprimaryQuota($order = Criteria::ASC) Order by the primaryQuota column
 * @method PersonQuery orderByownQuota($order = Criteria::ASC) Order by the ownQuota column
 * @method PersonQuery orderByconsultancyQuota($order = Criteria::ASC) Order by the consultancyQuota column
 * @method PersonQuery orderByexternalQuota($order = Criteria::ASC) Order by the externalQuota column
 * @method PersonQuery orderBylastAccessibleTimelineDate($order = Criteria::ASC) Order by the lastAccessibleTimelineDate column
 * @method PersonQuery orderBytimelineAccessibleDays($order = Criteria::ASC) Order by the timelineAccessibleDays column
 * @method PersonQuery orderBytypeTimeLinePerson($order = Criteria::ASC) Order by the typeTimeLinePerson column
 * @method PersonQuery orderBymaxOverQueue($order = Criteria::ASC) Order by the maxOverQueue column
 * @method PersonQuery orderBymaxCito($order = Criteria::ASC) Order by the maxCito column
 * @method PersonQuery orderByquotUnit($order = Criteria::ASC) Order by the quotUnit column
 * @method PersonQuery orderByacademicDegreeId($order = Criteria::ASC) Order by the academicdegree_id column
 * @method PersonQuery orderByacademicTitleId($order = Criteria::ASC) Order by the academicTitle_id column
 * @method PersonQuery orderByuuidId($order = Criteria::ASC) Order by the uuid_id column
 *
 * @method PersonQuery groupByid() Group by the id column
 * @method PersonQuery groupBycreateDatetime() Group by the createDatetime column
 * @method PersonQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method PersonQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method PersonQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method PersonQuery groupBydeleted() Group by the deleted column
 * @method PersonQuery groupBycode() Group by the code column
 * @method PersonQuery groupByfederalCode() Group by the federalCode column
 * @method PersonQuery groupByregionalCode() Group by the regionalCode column
 * @method PersonQuery groupBylastName() Group by the lastName column
 * @method PersonQuery groupByfirstName() Group by the firstName column
 * @method PersonQuery groupBypatrName() Group by the patrName column
 * @method PersonQuery groupBypostId() Group by the post_id column
 * @method PersonQuery groupByspecialityId() Group by the speciality_id column
 * @method PersonQuery groupByorgId() Group by the org_id column
 * @method PersonQuery groupByorgStructureId() Group by the orgStructure_id column
 * @method PersonQuery groupByoffice() Group by the office column
 * @method PersonQuery groupByoffice2() Group by the office2 column
 * @method PersonQuery groupBytariffCategoryId() Group by the tariffCategory_id column
 * @method PersonQuery groupByfinanceId() Group by the finance_id column
 * @method PersonQuery groupByretireDate() Group by the retireDate column
 * @method PersonQuery groupByambPlan() Group by the ambPlan column
 * @method PersonQuery groupByambPlan2() Group by the ambPlan2 column
 * @method PersonQuery groupByambNorm() Group by the ambNorm column
 * @method PersonQuery groupByhomPlan() Group by the homPlan column
 * @method PersonQuery groupByhomPlan2() Group by the homPlan2 column
 * @method PersonQuery groupByhomNorm() Group by the homNorm column
 * @method PersonQuery groupByexpPlan() Group by the expPlan column
 * @method PersonQuery groupByexpNorm() Group by the expNorm column
 * @method PersonQuery groupBylogin() Group by the login column
 * @method PersonQuery groupBypassword() Group by the password column
 * @method PersonQuery groupByuserProfileId() Group by the userProfile_id column
 * @method PersonQuery groupByretired() Group by the retired column
 * @method PersonQuery groupBybirthDate() Group by the birthDate column
 * @method PersonQuery groupBybirthPlace() Group by the birthPlace column
 * @method PersonQuery groupBysex() Group by the sex column
 * @method PersonQuery groupBysnils() Group by the SNILS column
 * @method PersonQuery groupByinn() Group by the INN column
 * @method PersonQuery groupByavailableForExternal() Group by the availableForExternal column
 * @method PersonQuery groupByprimaryQuota() Group by the primaryQuota column
 * @method PersonQuery groupByownQuota() Group by the ownQuota column
 * @method PersonQuery groupByconsultancyQuota() Group by the consultancyQuota column
 * @method PersonQuery groupByexternalQuota() Group by the externalQuota column
 * @method PersonQuery groupBylastAccessibleTimelineDate() Group by the lastAccessibleTimelineDate column
 * @method PersonQuery groupBytimelineAccessibleDays() Group by the timelineAccessibleDays column
 * @method PersonQuery groupBytypeTimeLinePerson() Group by the typeTimeLinePerson column
 * @method PersonQuery groupBymaxOverQueue() Group by the maxOverQueue column
 * @method PersonQuery groupBymaxCito() Group by the maxCito column
 * @method PersonQuery groupByquotUnit() Group by the quotUnit column
 * @method PersonQuery groupByacademicDegreeId() Group by the academicdegree_id column
 * @method PersonQuery groupByacademicTitleId() Group by the academicTitle_id column
 * @method PersonQuery groupByuuidId() Group by the uuid_id column
 *
 * @method PersonQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method PersonQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method PersonQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method PersonQuery leftJoinActionRelatedBycreatePersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionRelatedBycreatePersonId relation
 * @method PersonQuery rightJoinActionRelatedBycreatePersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionRelatedBycreatePersonId relation
 * @method PersonQuery innerJoinActionRelatedBycreatePersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionRelatedBycreatePersonId relation
 *
 * @method PersonQuery leftJoinActionRelatedBymodifyPersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionRelatedBymodifyPersonId relation
 * @method PersonQuery rightJoinActionRelatedBymodifyPersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionRelatedBymodifyPersonId relation
 * @method PersonQuery innerJoinActionRelatedBymodifyPersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionRelatedBymodifyPersonId relation
 *
 * @method PersonQuery leftJoinActionRelatedBysetPersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionRelatedBysetPersonId relation
 * @method PersonQuery rightJoinActionRelatedBysetPersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionRelatedBysetPersonId relation
 * @method PersonQuery innerJoinActionRelatedBysetPersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionRelatedBysetPersonId relation
 *
 * @method PersonQuery leftJoinEventRelatedBycreatePersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventRelatedBycreatePersonId relation
 * @method PersonQuery rightJoinEventRelatedBycreatePersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventRelatedBycreatePersonId relation
 * @method PersonQuery innerJoinEventRelatedBycreatePersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the EventRelatedBycreatePersonId relation
 *
 * @method PersonQuery leftJoinEventRelatedBymodifyPersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventRelatedBymodifyPersonId relation
 * @method PersonQuery rightJoinEventRelatedBymodifyPersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventRelatedBymodifyPersonId relation
 * @method PersonQuery innerJoinEventRelatedBymodifyPersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the EventRelatedBymodifyPersonId relation
 *
 * @method PersonQuery leftJoinEventRelatedBysetPersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventRelatedBysetPersonId relation
 * @method PersonQuery rightJoinEventRelatedBysetPersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventRelatedBysetPersonId relation
 * @method PersonQuery innerJoinEventRelatedBysetPersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the EventRelatedBysetPersonId relation
 *
 * @method PersonQuery leftJoinEventRelatedByexecPersonId($relationAlias = null) Adds a LEFT JOIN clause to the query using the EventRelatedByexecPersonId relation
 * @method PersonQuery rightJoinEventRelatedByexecPersonId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the EventRelatedByexecPersonId relation
 * @method PersonQuery innerJoinEventRelatedByexecPersonId($relationAlias = null) Adds a INNER JOIN clause to the query using the EventRelatedByexecPersonId relation
 *
 * @method Person findOne(PropelPDO $con = null) Return the first Person matching the query
 * @method Person findOneOrCreate(PropelPDO $con = null) Return the first Person matching the query, or a new Person object populated from the query conditions when no match is found
 *
 * @method Person findOneBycreateDatetime(string $createDatetime) Return the first Person filtered by the createDatetime column
 * @method Person findOneBycreatePersonId(int $createPerson_id) Return the first Person filtered by the createPerson_id column
 * @method Person findOneBymodifyDatetime(string $modifyDatetime) Return the first Person filtered by the modifyDatetime column
 * @method Person findOneBymodifyPersonId(int $modifyPerson_id) Return the first Person filtered by the modifyPerson_id column
 * @method Person findOneBydeleted(boolean $deleted) Return the first Person filtered by the deleted column
 * @method Person findOneBycode(string $code) Return the first Person filtered by the code column
 * @method Person findOneByfederalCode(string $federalCode) Return the first Person filtered by the federalCode column
 * @method Person findOneByregionalCode(string $regionalCode) Return the first Person filtered by the regionalCode column
 * @method Person findOneBylastName(string $lastName) Return the first Person filtered by the lastName column
 * @method Person findOneByfirstName(string $firstName) Return the first Person filtered by the firstName column
 * @method Person findOneBypatrName(string $patrName) Return the first Person filtered by the patrName column
 * @method Person findOneBypostId(int $post_id) Return the first Person filtered by the post_id column
 * @method Person findOneByspecialityId(int $speciality_id) Return the first Person filtered by the speciality_id column
 * @method Person findOneByorgId(int $org_id) Return the first Person filtered by the org_id column
 * @method Person findOneByorgStructureId(int $orgStructure_id) Return the first Person filtered by the orgStructure_id column
 * @method Person findOneByoffice(string $office) Return the first Person filtered by the office column
 * @method Person findOneByoffice2(string $office2) Return the first Person filtered by the office2 column
 * @method Person findOneBytariffCategoryId(int $tariffCategory_id) Return the first Person filtered by the tariffCategory_id column
 * @method Person findOneByfinanceId(int $finance_id) Return the first Person filtered by the finance_id column
 * @method Person findOneByretireDate(string $retireDate) Return the first Person filtered by the retireDate column
 * @method Person findOneByambPlan(int $ambPlan) Return the first Person filtered by the ambPlan column
 * @method Person findOneByambPlan2(int $ambPlan2) Return the first Person filtered by the ambPlan2 column
 * @method Person findOneByambNorm(int $ambNorm) Return the first Person filtered by the ambNorm column
 * @method Person findOneByhomPlan(int $homPlan) Return the first Person filtered by the homPlan column
 * @method Person findOneByhomPlan2(int $homPlan2) Return the first Person filtered by the homPlan2 column
 * @method Person findOneByhomNorm(int $homNorm) Return the first Person filtered by the homNorm column
 * @method Person findOneByexpPlan(int $expPlan) Return the first Person filtered by the expPlan column
 * @method Person findOneByexpNorm(int $expNorm) Return the first Person filtered by the expNorm column
 * @method Person findOneBylogin(string $login) Return the first Person filtered by the login column
 * @method Person findOneBypassword(string $password) Return the first Person filtered by the password column
 * @method Person findOneByuserProfileId(int $userProfile_id) Return the first Person filtered by the userProfile_id column
 * @method Person findOneByretired(boolean $retired) Return the first Person filtered by the retired column
 * @method Person findOneBybirthDate(string $birthDate) Return the first Person filtered by the birthDate column
 * @method Person findOneBybirthPlace(string $birthPlace) Return the first Person filtered by the birthPlace column
 * @method Person findOneBysex(int $sex) Return the first Person filtered by the sex column
 * @method Person findOneBysnils(string $SNILS) Return the first Person filtered by the SNILS column
 * @method Person findOneByinn(string $INN) Return the first Person filtered by the INN column
 * @method Person findOneByavailableForExternal(int $availableForExternal) Return the first Person filtered by the availableForExternal column
 * @method Person findOneByprimaryQuota(int $primaryQuota) Return the first Person filtered by the primaryQuota column
 * @method Person findOneByownQuota(int $ownQuota) Return the first Person filtered by the ownQuota column
 * @method Person findOneByconsultancyQuota(int $consultancyQuota) Return the first Person filtered by the consultancyQuota column
 * @method Person findOneByexternalQuota(int $externalQuota) Return the first Person filtered by the externalQuota column
 * @method Person findOneBylastAccessibleTimelineDate(string $lastAccessibleTimelineDate) Return the first Person filtered by the lastAccessibleTimelineDate column
 * @method Person findOneBytimelineAccessibleDays(int $timelineAccessibleDays) Return the first Person filtered by the timelineAccessibleDays column
 * @method Person findOneBytypeTimeLinePerson(int $typeTimeLinePerson) Return the first Person filtered by the typeTimeLinePerson column
 * @method Person findOneBymaxOverQueue(int $maxOverQueue) Return the first Person filtered by the maxOverQueue column
 * @method Person findOneBymaxCito(int $maxCito) Return the first Person filtered by the maxCito column
 * @method Person findOneByquotUnit(int $quotUnit) Return the first Person filtered by the quotUnit column
 * @method Person findOneByacademicDegreeId(int $academicdegree_id) Return the first Person filtered by the academicdegree_id column
 * @method Person findOneByacademicTitleId(int $academicTitle_id) Return the first Person filtered by the academicTitle_id column
 * @method Person findOneByuuidId(int $uuid_id) Return the first Person filtered by the uuid_id column
 *
 * @method array findByid(int $id) Return Person objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return Person objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return Person objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return Person objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return Person objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return Person objects filtered by the deleted column
 * @method array findBycode(string $code) Return Person objects filtered by the code column
 * @method array findByfederalCode(string $federalCode) Return Person objects filtered by the federalCode column
 * @method array findByregionalCode(string $regionalCode) Return Person objects filtered by the regionalCode column
 * @method array findBylastName(string $lastName) Return Person objects filtered by the lastName column
 * @method array findByfirstName(string $firstName) Return Person objects filtered by the firstName column
 * @method array findBypatrName(string $patrName) Return Person objects filtered by the patrName column
 * @method array findBypostId(int $post_id) Return Person objects filtered by the post_id column
 * @method array findByspecialityId(int $speciality_id) Return Person objects filtered by the speciality_id column
 * @method array findByorgId(int $org_id) Return Person objects filtered by the org_id column
 * @method array findByorgStructureId(int $orgStructure_id) Return Person objects filtered by the orgStructure_id column
 * @method array findByoffice(string $office) Return Person objects filtered by the office column
 * @method array findByoffice2(string $office2) Return Person objects filtered by the office2 column
 * @method array findBytariffCategoryId(int $tariffCategory_id) Return Person objects filtered by the tariffCategory_id column
 * @method array findByfinanceId(int $finance_id) Return Person objects filtered by the finance_id column
 * @method array findByretireDate(string $retireDate) Return Person objects filtered by the retireDate column
 * @method array findByambPlan(int $ambPlan) Return Person objects filtered by the ambPlan column
 * @method array findByambPlan2(int $ambPlan2) Return Person objects filtered by the ambPlan2 column
 * @method array findByambNorm(int $ambNorm) Return Person objects filtered by the ambNorm column
 * @method array findByhomPlan(int $homPlan) Return Person objects filtered by the homPlan column
 * @method array findByhomPlan2(int $homPlan2) Return Person objects filtered by the homPlan2 column
 * @method array findByhomNorm(int $homNorm) Return Person objects filtered by the homNorm column
 * @method array findByexpPlan(int $expPlan) Return Person objects filtered by the expPlan column
 * @method array findByexpNorm(int $expNorm) Return Person objects filtered by the expNorm column
 * @method array findBylogin(string $login) Return Person objects filtered by the login column
 * @method array findBypassword(string $password) Return Person objects filtered by the password column
 * @method array findByuserProfileId(int $userProfile_id) Return Person objects filtered by the userProfile_id column
 * @method array findByretired(boolean $retired) Return Person objects filtered by the retired column
 * @method array findBybirthDate(string $birthDate) Return Person objects filtered by the birthDate column
 * @method array findBybirthPlace(string $birthPlace) Return Person objects filtered by the birthPlace column
 * @method array findBysex(int $sex) Return Person objects filtered by the sex column
 * @method array findBysnils(string $SNILS) Return Person objects filtered by the SNILS column
 * @method array findByinn(string $INN) Return Person objects filtered by the INN column
 * @method array findByavailableForExternal(int $availableForExternal) Return Person objects filtered by the availableForExternal column
 * @method array findByprimaryQuota(int $primaryQuota) Return Person objects filtered by the primaryQuota column
 * @method array findByownQuota(int $ownQuota) Return Person objects filtered by the ownQuota column
 * @method array findByconsultancyQuota(int $consultancyQuota) Return Person objects filtered by the consultancyQuota column
 * @method array findByexternalQuota(int $externalQuota) Return Person objects filtered by the externalQuota column
 * @method array findBylastAccessibleTimelineDate(string $lastAccessibleTimelineDate) Return Person objects filtered by the lastAccessibleTimelineDate column
 * @method array findBytimelineAccessibleDays(int $timelineAccessibleDays) Return Person objects filtered by the timelineAccessibleDays column
 * @method array findBytypeTimeLinePerson(int $typeTimeLinePerson) Return Person objects filtered by the typeTimeLinePerson column
 * @method array findBymaxOverQueue(int $maxOverQueue) Return Person objects filtered by the maxOverQueue column
 * @method array findBymaxCito(int $maxCito) Return Person objects filtered by the maxCito column
 * @method array findByquotUnit(int $quotUnit) Return Person objects filtered by the quotUnit column
 * @method array findByacademicDegreeId(int $academicdegree_id) Return Person objects filtered by the academicdegree_id column
 * @method array findByacademicTitleId(int $academicTitle_id) Return Person objects filtered by the academicTitle_id column
 * @method array findByuuidId(int $uuid_id) Return Person objects filtered by the uuid_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BasePersonQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BasePersonQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\Person', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new PersonQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   PersonQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return PersonQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof PersonQuery) {
            return $criteria;
        }
        $query = new PersonQuery();
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
     * @return   Person|Person[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = PersonPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Person A model object, or null if the key is not found
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
     * @return                 Person A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `code`, `federalCode`, `regionalCode`, `lastName`, `firstName`, `patrName`, `post_id`, `speciality_id`, `org_id`, `orgStructure_id`, `office`, `office2`, `tariffCategory_id`, `finance_id`, `retireDate`, `ambPlan`, `ambPlan2`, `ambNorm`, `homPlan`, `homPlan2`, `homNorm`, `expPlan`, `expNorm`, `login`, `password`, `userProfile_id`, `retired`, `birthDate`, `birthPlace`, `sex`, `SNILS`, `INN`, `availableForExternal`, `primaryQuota`, `ownQuota`, `consultancyQuota`, `externalQuota`, `lastAccessibleTimelineDate`, `timelineAccessibleDays`, `typeTimeLinePerson`, `maxOverQueue`, `maxCito`, `quotUnit`, `academicdegree_id`, `academicTitle_id`, `uuid_id` FROM `Person` WHERE `id` = :p0';
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
            $obj = new Person();
            $obj->hydrate($row);
            PersonPeer::addInstanceToPool($obj, (string) $key);
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
     * @return Person|Person[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|Person[]|mixed the list of results, formatted by the current formatter
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(PersonPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(PersonPeer::ID, $keys, Criteria::IN);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(PersonPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(PersonPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::ID, $id, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(PersonPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(PersonPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::CREATEDATETIME, $createDatetime, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(PersonPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(PersonPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::CREATEPERSON_ID, $createPersonId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(PersonPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(PersonPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(PersonPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(PersonPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(PersonPeer::DELETED, $deleted, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
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

        return $this->addUsingAlias(PersonPeer::CODE, $code, $comparison);
    }

    /**
     * Filter the query on the federalCode column
     *
     * Example usage:
     * <code>
     * $query->filterByfederalCode('fooValue');   // WHERE federalCode = 'fooValue'
     * $query->filterByfederalCode('%fooValue%'); // WHERE federalCode LIKE '%fooValue%'
     * </code>
     *
     * @param     string $federalCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByfederalCode($federalCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($federalCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $federalCode)) {
                $federalCode = str_replace('*', '%', $federalCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::FEDERALCODE, $federalCode, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
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

        return $this->addUsingAlias(PersonPeer::REGIONALCODE, $regionalCode, $comparison);
    }

    /**
     * Filter the query on the lastName column
     *
     * Example usage:
     * <code>
     * $query->filterBylastName('fooValue');   // WHERE lastName = 'fooValue'
     * $query->filterBylastName('%fooValue%'); // WHERE lastName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $lastName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBylastName($lastName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($lastName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $lastName)) {
                $lastName = str_replace('*', '%', $lastName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::LASTNAME, $lastName, $comparison);
    }

    /**
     * Filter the query on the firstName column
     *
     * Example usage:
     * <code>
     * $query->filterByfirstName('fooValue');   // WHERE firstName = 'fooValue'
     * $query->filterByfirstName('%fooValue%'); // WHERE firstName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $firstName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByfirstName($firstName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($firstName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $firstName)) {
                $firstName = str_replace('*', '%', $firstName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::FIRSTNAME, $firstName, $comparison);
    }

    /**
     * Filter the query on the patrName column
     *
     * Example usage:
     * <code>
     * $query->filterBypatrName('fooValue');   // WHERE patrName = 'fooValue'
     * $query->filterBypatrName('%fooValue%'); // WHERE patrName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $patrName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBypatrName($patrName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($patrName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $patrName)) {
                $patrName = str_replace('*', '%', $patrName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::PATRNAME, $patrName, $comparison);
    }

    /**
     * Filter the query on the post_id column
     *
     * Example usage:
     * <code>
     * $query->filterBypostId(1234); // WHERE post_id = 1234
     * $query->filterBypostId(array(12, 34)); // WHERE post_id IN (12, 34)
     * $query->filterBypostId(array('min' => 12)); // WHERE post_id >= 12
     * $query->filterBypostId(array('max' => 12)); // WHERE post_id <= 12
     * </code>
     *
     * @param     mixed $postId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBypostId($postId = null, $comparison = null)
    {
        if (is_array($postId)) {
            $useMinMax = false;
            if (isset($postId['min'])) {
                $this->addUsingAlias(PersonPeer::POST_ID, $postId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($postId['max'])) {
                $this->addUsingAlias(PersonPeer::POST_ID, $postId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::POST_ID, $postId, $comparison);
    }

    /**
     * Filter the query on the speciality_id column
     *
     * Example usage:
     * <code>
     * $query->filterByspecialityId(1234); // WHERE speciality_id = 1234
     * $query->filterByspecialityId(array(12, 34)); // WHERE speciality_id IN (12, 34)
     * $query->filterByspecialityId(array('min' => 12)); // WHERE speciality_id >= 12
     * $query->filterByspecialityId(array('max' => 12)); // WHERE speciality_id <= 12
     * </code>
     *
     * @param     mixed $specialityId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByspecialityId($specialityId = null, $comparison = null)
    {
        if (is_array($specialityId)) {
            $useMinMax = false;
            if (isset($specialityId['min'])) {
                $this->addUsingAlias(PersonPeer::SPECIALITY_ID, $specialityId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($specialityId['max'])) {
                $this->addUsingAlias(PersonPeer::SPECIALITY_ID, $specialityId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::SPECIALITY_ID, $specialityId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByorgId($orgId = null, $comparison = null)
    {
        if (is_array($orgId)) {
            $useMinMax = false;
            if (isset($orgId['min'])) {
                $this->addUsingAlias(PersonPeer::ORG_ID, $orgId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgId['max'])) {
                $this->addUsingAlias(PersonPeer::ORG_ID, $orgId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::ORG_ID, $orgId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByorgStructureId($orgStructureId = null, $comparison = null)
    {
        if (is_array($orgStructureId)) {
            $useMinMax = false;
            if (isset($orgStructureId['min'])) {
                $this->addUsingAlias(PersonPeer::ORGSTRUCTURE_ID, $orgStructureId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($orgStructureId['max'])) {
                $this->addUsingAlias(PersonPeer::ORGSTRUCTURE_ID, $orgStructureId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::ORGSTRUCTURE_ID, $orgStructureId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
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

        return $this->addUsingAlias(PersonPeer::OFFICE, $office, $comparison);
    }

    /**
     * Filter the query on the office2 column
     *
     * Example usage:
     * <code>
     * $query->filterByoffice2('fooValue');   // WHERE office2 = 'fooValue'
     * $query->filterByoffice2('%fooValue%'); // WHERE office2 LIKE '%fooValue%'
     * </code>
     *
     * @param     string $office2 The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByoffice2($office2 = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($office2)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $office2)) {
                $office2 = str_replace('*', '%', $office2);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::OFFICE2, $office2, $comparison);
    }

    /**
     * Filter the query on the tariffCategory_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytariffCategoryId(1234); // WHERE tariffCategory_id = 1234
     * $query->filterBytariffCategoryId(array(12, 34)); // WHERE tariffCategory_id IN (12, 34)
     * $query->filterBytariffCategoryId(array('min' => 12)); // WHERE tariffCategory_id >= 12
     * $query->filterBytariffCategoryId(array('max' => 12)); // WHERE tariffCategory_id <= 12
     * </code>
     *
     * @param     mixed $tariffCategoryId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBytariffCategoryId($tariffCategoryId = null, $comparison = null)
    {
        if (is_array($tariffCategoryId)) {
            $useMinMax = false;
            if (isset($tariffCategoryId['min'])) {
                $this->addUsingAlias(PersonPeer::TARIFFCATEGORY_ID, $tariffCategoryId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($tariffCategoryId['max'])) {
                $this->addUsingAlias(PersonPeer::TARIFFCATEGORY_ID, $tariffCategoryId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::TARIFFCATEGORY_ID, $tariffCategoryId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByfinanceId($financeId = null, $comparison = null)
    {
        if (is_array($financeId)) {
            $useMinMax = false;
            if (isset($financeId['min'])) {
                $this->addUsingAlias(PersonPeer::FINANCE_ID, $financeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($financeId['max'])) {
                $this->addUsingAlias(PersonPeer::FINANCE_ID, $financeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::FINANCE_ID, $financeId, $comparison);
    }

    /**
     * Filter the query on the retireDate column
     *
     * Example usage:
     * <code>
     * $query->filterByretireDate('2011-03-14'); // WHERE retireDate = '2011-03-14'
     * $query->filterByretireDate('now'); // WHERE retireDate = '2011-03-14'
     * $query->filterByretireDate(array('max' => 'yesterday')); // WHERE retireDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $retireDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByretireDate($retireDate = null, $comparison = null)
    {
        if (is_array($retireDate)) {
            $useMinMax = false;
            if (isset($retireDate['min'])) {
                $this->addUsingAlias(PersonPeer::RETIREDATE, $retireDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($retireDate['max'])) {
                $this->addUsingAlias(PersonPeer::RETIREDATE, $retireDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::RETIREDATE, $retireDate, $comparison);
    }

    /**
     * Filter the query on the ambPlan column
     *
     * Example usage:
     * <code>
     * $query->filterByambPlan(1234); // WHERE ambPlan = 1234
     * $query->filterByambPlan(array(12, 34)); // WHERE ambPlan IN (12, 34)
     * $query->filterByambPlan(array('min' => 12)); // WHERE ambPlan >= 12
     * $query->filterByambPlan(array('max' => 12)); // WHERE ambPlan <= 12
     * </code>
     *
     * @param     mixed $ambPlan The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByambPlan($ambPlan = null, $comparison = null)
    {
        if (is_array($ambPlan)) {
            $useMinMax = false;
            if (isset($ambPlan['min'])) {
                $this->addUsingAlias(PersonPeer::AMBPLAN, $ambPlan['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ambPlan['max'])) {
                $this->addUsingAlias(PersonPeer::AMBPLAN, $ambPlan['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::AMBPLAN, $ambPlan, $comparison);
    }

    /**
     * Filter the query on the ambPlan2 column
     *
     * Example usage:
     * <code>
     * $query->filterByambPlan2(1234); // WHERE ambPlan2 = 1234
     * $query->filterByambPlan2(array(12, 34)); // WHERE ambPlan2 IN (12, 34)
     * $query->filterByambPlan2(array('min' => 12)); // WHERE ambPlan2 >= 12
     * $query->filterByambPlan2(array('max' => 12)); // WHERE ambPlan2 <= 12
     * </code>
     *
     * @param     mixed $ambPlan2 The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByambPlan2($ambPlan2 = null, $comparison = null)
    {
        if (is_array($ambPlan2)) {
            $useMinMax = false;
            if (isset($ambPlan2['min'])) {
                $this->addUsingAlias(PersonPeer::AMBPLAN2, $ambPlan2['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ambPlan2['max'])) {
                $this->addUsingAlias(PersonPeer::AMBPLAN2, $ambPlan2['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::AMBPLAN2, $ambPlan2, $comparison);
    }

    /**
     * Filter the query on the ambNorm column
     *
     * Example usage:
     * <code>
     * $query->filterByambNorm(1234); // WHERE ambNorm = 1234
     * $query->filterByambNorm(array(12, 34)); // WHERE ambNorm IN (12, 34)
     * $query->filterByambNorm(array('min' => 12)); // WHERE ambNorm >= 12
     * $query->filterByambNorm(array('max' => 12)); // WHERE ambNorm <= 12
     * </code>
     *
     * @param     mixed $ambNorm The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByambNorm($ambNorm = null, $comparison = null)
    {
        if (is_array($ambNorm)) {
            $useMinMax = false;
            if (isset($ambNorm['min'])) {
                $this->addUsingAlias(PersonPeer::AMBNORM, $ambNorm['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ambNorm['max'])) {
                $this->addUsingAlias(PersonPeer::AMBNORM, $ambNorm['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::AMBNORM, $ambNorm, $comparison);
    }

    /**
     * Filter the query on the homPlan column
     *
     * Example usage:
     * <code>
     * $query->filterByhomPlan(1234); // WHERE homPlan = 1234
     * $query->filterByhomPlan(array(12, 34)); // WHERE homPlan IN (12, 34)
     * $query->filterByhomPlan(array('min' => 12)); // WHERE homPlan >= 12
     * $query->filterByhomPlan(array('max' => 12)); // WHERE homPlan <= 12
     * </code>
     *
     * @param     mixed $homPlan The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByhomPlan($homPlan = null, $comparison = null)
    {
        if (is_array($homPlan)) {
            $useMinMax = false;
            if (isset($homPlan['min'])) {
                $this->addUsingAlias(PersonPeer::HOMPLAN, $homPlan['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($homPlan['max'])) {
                $this->addUsingAlias(PersonPeer::HOMPLAN, $homPlan['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::HOMPLAN, $homPlan, $comparison);
    }

    /**
     * Filter the query on the homPlan2 column
     *
     * Example usage:
     * <code>
     * $query->filterByhomPlan2(1234); // WHERE homPlan2 = 1234
     * $query->filterByhomPlan2(array(12, 34)); // WHERE homPlan2 IN (12, 34)
     * $query->filterByhomPlan2(array('min' => 12)); // WHERE homPlan2 >= 12
     * $query->filterByhomPlan2(array('max' => 12)); // WHERE homPlan2 <= 12
     * </code>
     *
     * @param     mixed $homPlan2 The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByhomPlan2($homPlan2 = null, $comparison = null)
    {
        if (is_array($homPlan2)) {
            $useMinMax = false;
            if (isset($homPlan2['min'])) {
                $this->addUsingAlias(PersonPeer::HOMPLAN2, $homPlan2['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($homPlan2['max'])) {
                $this->addUsingAlias(PersonPeer::HOMPLAN2, $homPlan2['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::HOMPLAN2, $homPlan2, $comparison);
    }

    /**
     * Filter the query on the homNorm column
     *
     * Example usage:
     * <code>
     * $query->filterByhomNorm(1234); // WHERE homNorm = 1234
     * $query->filterByhomNorm(array(12, 34)); // WHERE homNorm IN (12, 34)
     * $query->filterByhomNorm(array('min' => 12)); // WHERE homNorm >= 12
     * $query->filterByhomNorm(array('max' => 12)); // WHERE homNorm <= 12
     * </code>
     *
     * @param     mixed $homNorm The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByhomNorm($homNorm = null, $comparison = null)
    {
        if (is_array($homNorm)) {
            $useMinMax = false;
            if (isset($homNorm['min'])) {
                $this->addUsingAlias(PersonPeer::HOMNORM, $homNorm['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($homNorm['max'])) {
                $this->addUsingAlias(PersonPeer::HOMNORM, $homNorm['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::HOMNORM, $homNorm, $comparison);
    }

    /**
     * Filter the query on the expPlan column
     *
     * Example usage:
     * <code>
     * $query->filterByexpPlan(1234); // WHERE expPlan = 1234
     * $query->filterByexpPlan(array(12, 34)); // WHERE expPlan IN (12, 34)
     * $query->filterByexpPlan(array('min' => 12)); // WHERE expPlan >= 12
     * $query->filterByexpPlan(array('max' => 12)); // WHERE expPlan <= 12
     * </code>
     *
     * @param     mixed $expPlan The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByexpPlan($expPlan = null, $comparison = null)
    {
        if (is_array($expPlan)) {
            $useMinMax = false;
            if (isset($expPlan['min'])) {
                $this->addUsingAlias(PersonPeer::EXPPLAN, $expPlan['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($expPlan['max'])) {
                $this->addUsingAlias(PersonPeer::EXPPLAN, $expPlan['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::EXPPLAN, $expPlan, $comparison);
    }

    /**
     * Filter the query on the expNorm column
     *
     * Example usage:
     * <code>
     * $query->filterByexpNorm(1234); // WHERE expNorm = 1234
     * $query->filterByexpNorm(array(12, 34)); // WHERE expNorm IN (12, 34)
     * $query->filterByexpNorm(array('min' => 12)); // WHERE expNorm >= 12
     * $query->filterByexpNorm(array('max' => 12)); // WHERE expNorm <= 12
     * </code>
     *
     * @param     mixed $expNorm The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByexpNorm($expNorm = null, $comparison = null)
    {
        if (is_array($expNorm)) {
            $useMinMax = false;
            if (isset($expNorm['min'])) {
                $this->addUsingAlias(PersonPeer::EXPNORM, $expNorm['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($expNorm['max'])) {
                $this->addUsingAlias(PersonPeer::EXPNORM, $expNorm['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::EXPNORM, $expNorm, $comparison);
    }

    /**
     * Filter the query on the login column
     *
     * Example usage:
     * <code>
     * $query->filterBylogin('fooValue');   // WHERE login = 'fooValue'
     * $query->filterBylogin('%fooValue%'); // WHERE login LIKE '%fooValue%'
     * </code>
     *
     * @param     string $login The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBylogin($login = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($login)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $login)) {
                $login = str_replace('*', '%', $login);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::LOGIN, $login, $comparison);
    }

    /**
     * Filter the query on the password column
     *
     * Example usage:
     * <code>
     * $query->filterBypassword('fooValue');   // WHERE password = 'fooValue'
     * $query->filterBypassword('%fooValue%'); // WHERE password LIKE '%fooValue%'
     * </code>
     *
     * @param     string $password The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBypassword($password = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($password)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $password)) {
                $password = str_replace('*', '%', $password);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::PASSWORD, $password, $comparison);
    }

    /**
     * Filter the query on the userProfile_id column
     *
     * Example usage:
     * <code>
     * $query->filterByuserProfileId(1234); // WHERE userProfile_id = 1234
     * $query->filterByuserProfileId(array(12, 34)); // WHERE userProfile_id IN (12, 34)
     * $query->filterByuserProfileId(array('min' => 12)); // WHERE userProfile_id >= 12
     * $query->filterByuserProfileId(array('max' => 12)); // WHERE userProfile_id <= 12
     * </code>
     *
     * @param     mixed $userProfileId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByuserProfileId($userProfileId = null, $comparison = null)
    {
        if (is_array($userProfileId)) {
            $useMinMax = false;
            if (isset($userProfileId['min'])) {
                $this->addUsingAlias(PersonPeer::USERPROFILE_ID, $userProfileId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($userProfileId['max'])) {
                $this->addUsingAlias(PersonPeer::USERPROFILE_ID, $userProfileId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::USERPROFILE_ID, $userProfileId, $comparison);
    }

    /**
     * Filter the query on the retired column
     *
     * Example usage:
     * <code>
     * $query->filterByretired(true); // WHERE retired = true
     * $query->filterByretired('yes'); // WHERE retired = true
     * </code>
     *
     * @param     boolean|string $retired The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByretired($retired = null, $comparison = null)
    {
        if (is_string($retired)) {
            $retired = in_array(strtolower($retired), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(PersonPeer::RETIRED, $retired, $comparison);
    }

    /**
     * Filter the query on the birthDate column
     *
     * Example usage:
     * <code>
     * $query->filterBybirthDate('2011-03-14'); // WHERE birthDate = '2011-03-14'
     * $query->filterBybirthDate('now'); // WHERE birthDate = '2011-03-14'
     * $query->filterBybirthDate(array('max' => 'yesterday')); // WHERE birthDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $birthDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBybirthDate($birthDate = null, $comparison = null)
    {
        if (is_array($birthDate)) {
            $useMinMax = false;
            if (isset($birthDate['min'])) {
                $this->addUsingAlias(PersonPeer::BIRTHDATE, $birthDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($birthDate['max'])) {
                $this->addUsingAlias(PersonPeer::BIRTHDATE, $birthDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::BIRTHDATE, $birthDate, $comparison);
    }

    /**
     * Filter the query on the birthPlace column
     *
     * Example usage:
     * <code>
     * $query->filterBybirthPlace('fooValue');   // WHERE birthPlace = 'fooValue'
     * $query->filterBybirthPlace('%fooValue%'); // WHERE birthPlace LIKE '%fooValue%'
     * </code>
     *
     * @param     string $birthPlace The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBybirthPlace($birthPlace = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($birthPlace)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $birthPlace)) {
                $birthPlace = str_replace('*', '%', $birthPlace);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::BIRTHPLACE, $birthPlace, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_array($sex)) {
            $useMinMax = false;
            if (isset($sex['min'])) {
                $this->addUsingAlias(PersonPeer::SEX, $sex['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sex['max'])) {
                $this->addUsingAlias(PersonPeer::SEX, $sex['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::SEX, $sex, $comparison);
    }

    /**
     * Filter the query on the SNILS column
     *
     * Example usage:
     * <code>
     * $query->filterBysnils('fooValue');   // WHERE SNILS = 'fooValue'
     * $query->filterBysnils('%fooValue%'); // WHERE SNILS LIKE '%fooValue%'
     * </code>
     *
     * @param     string $snils The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBysnils($snils = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($snils)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $snils)) {
                $snils = str_replace('*', '%', $snils);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::SNILS, $snils, $comparison);
    }

    /**
     * Filter the query on the INN column
     *
     * Example usage:
     * <code>
     * $query->filterByinn('fooValue');   // WHERE INN = 'fooValue'
     * $query->filterByinn('%fooValue%'); // WHERE INN LIKE '%fooValue%'
     * </code>
     *
     * @param     string $inn The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByinn($inn = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($inn)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $inn)) {
                $inn = str_replace('*', '%', $inn);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(PersonPeer::INN, $inn, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByavailableForExternal($availableForExternal = null, $comparison = null)
    {
        if (is_array($availableForExternal)) {
            $useMinMax = false;
            if (isset($availableForExternal['min'])) {
                $this->addUsingAlias(PersonPeer::AVAILABLEFOREXTERNAL, $availableForExternal['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($availableForExternal['max'])) {
                $this->addUsingAlias(PersonPeer::AVAILABLEFOREXTERNAL, $availableForExternal['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::AVAILABLEFOREXTERNAL, $availableForExternal, $comparison);
    }

    /**
     * Filter the query on the primaryQuota column
     *
     * Example usage:
     * <code>
     * $query->filterByprimaryQuota(1234); // WHERE primaryQuota = 1234
     * $query->filterByprimaryQuota(array(12, 34)); // WHERE primaryQuota IN (12, 34)
     * $query->filterByprimaryQuota(array('min' => 12)); // WHERE primaryQuota >= 12
     * $query->filterByprimaryQuota(array('max' => 12)); // WHERE primaryQuota <= 12
     * </code>
     *
     * @param     mixed $primaryQuota The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByprimaryQuota($primaryQuota = null, $comparison = null)
    {
        if (is_array($primaryQuota)) {
            $useMinMax = false;
            if (isset($primaryQuota['min'])) {
                $this->addUsingAlias(PersonPeer::PRIMARYQUOTA, $primaryQuota['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($primaryQuota['max'])) {
                $this->addUsingAlias(PersonPeer::PRIMARYQUOTA, $primaryQuota['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::PRIMARYQUOTA, $primaryQuota, $comparison);
    }

    /**
     * Filter the query on the ownQuota column
     *
     * Example usage:
     * <code>
     * $query->filterByownQuota(1234); // WHERE ownQuota = 1234
     * $query->filterByownQuota(array(12, 34)); // WHERE ownQuota IN (12, 34)
     * $query->filterByownQuota(array('min' => 12)); // WHERE ownQuota >= 12
     * $query->filterByownQuota(array('max' => 12)); // WHERE ownQuota <= 12
     * </code>
     *
     * @param     mixed $ownQuota The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByownQuota($ownQuota = null, $comparison = null)
    {
        if (is_array($ownQuota)) {
            $useMinMax = false;
            if (isset($ownQuota['min'])) {
                $this->addUsingAlias(PersonPeer::OWNQUOTA, $ownQuota['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ownQuota['max'])) {
                $this->addUsingAlias(PersonPeer::OWNQUOTA, $ownQuota['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::OWNQUOTA, $ownQuota, $comparison);
    }

    /**
     * Filter the query on the consultancyQuota column
     *
     * Example usage:
     * <code>
     * $query->filterByconsultancyQuota(1234); // WHERE consultancyQuota = 1234
     * $query->filterByconsultancyQuota(array(12, 34)); // WHERE consultancyQuota IN (12, 34)
     * $query->filterByconsultancyQuota(array('min' => 12)); // WHERE consultancyQuota >= 12
     * $query->filterByconsultancyQuota(array('max' => 12)); // WHERE consultancyQuota <= 12
     * </code>
     *
     * @param     mixed $consultancyQuota The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByconsultancyQuota($consultancyQuota = null, $comparison = null)
    {
        if (is_array($consultancyQuota)) {
            $useMinMax = false;
            if (isset($consultancyQuota['min'])) {
                $this->addUsingAlias(PersonPeer::CONSULTANCYQUOTA, $consultancyQuota['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($consultancyQuota['max'])) {
                $this->addUsingAlias(PersonPeer::CONSULTANCYQUOTA, $consultancyQuota['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::CONSULTANCYQUOTA, $consultancyQuota, $comparison);
    }

    /**
     * Filter the query on the externalQuota column
     *
     * Example usage:
     * <code>
     * $query->filterByexternalQuota(1234); // WHERE externalQuota = 1234
     * $query->filterByexternalQuota(array(12, 34)); // WHERE externalQuota IN (12, 34)
     * $query->filterByexternalQuota(array('min' => 12)); // WHERE externalQuota >= 12
     * $query->filterByexternalQuota(array('max' => 12)); // WHERE externalQuota <= 12
     * </code>
     *
     * @param     mixed $externalQuota The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByexternalQuota($externalQuota = null, $comparison = null)
    {
        if (is_array($externalQuota)) {
            $useMinMax = false;
            if (isset($externalQuota['min'])) {
                $this->addUsingAlias(PersonPeer::EXTERNALQUOTA, $externalQuota['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($externalQuota['max'])) {
                $this->addUsingAlias(PersonPeer::EXTERNALQUOTA, $externalQuota['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::EXTERNALQUOTA, $externalQuota, $comparison);
    }

    /**
     * Filter the query on the lastAccessibleTimelineDate column
     *
     * Example usage:
     * <code>
     * $query->filterBylastAccessibleTimelineDate('2011-03-14'); // WHERE lastAccessibleTimelineDate = '2011-03-14'
     * $query->filterBylastAccessibleTimelineDate('now'); // WHERE lastAccessibleTimelineDate = '2011-03-14'
     * $query->filterBylastAccessibleTimelineDate(array('max' => 'yesterday')); // WHERE lastAccessibleTimelineDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $lastAccessibleTimelineDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBylastAccessibleTimelineDate($lastAccessibleTimelineDate = null, $comparison = null)
    {
        if (is_array($lastAccessibleTimelineDate)) {
            $useMinMax = false;
            if (isset($lastAccessibleTimelineDate['min'])) {
                $this->addUsingAlias(PersonPeer::LASTACCESSIBLETIMELINEDATE, $lastAccessibleTimelineDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($lastAccessibleTimelineDate['max'])) {
                $this->addUsingAlias(PersonPeer::LASTACCESSIBLETIMELINEDATE, $lastAccessibleTimelineDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::LASTACCESSIBLETIMELINEDATE, $lastAccessibleTimelineDate, $comparison);
    }

    /**
     * Filter the query on the timelineAccessibleDays column
     *
     * Example usage:
     * <code>
     * $query->filterBytimelineAccessibleDays(1234); // WHERE timelineAccessibleDays = 1234
     * $query->filterBytimelineAccessibleDays(array(12, 34)); // WHERE timelineAccessibleDays IN (12, 34)
     * $query->filterBytimelineAccessibleDays(array('min' => 12)); // WHERE timelineAccessibleDays >= 12
     * $query->filterBytimelineAccessibleDays(array('max' => 12)); // WHERE timelineAccessibleDays <= 12
     * </code>
     *
     * @param     mixed $timelineAccessibleDays The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBytimelineAccessibleDays($timelineAccessibleDays = null, $comparison = null)
    {
        if (is_array($timelineAccessibleDays)) {
            $useMinMax = false;
            if (isset($timelineAccessibleDays['min'])) {
                $this->addUsingAlias(PersonPeer::TIMELINEACCESSIBLEDAYS, $timelineAccessibleDays['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($timelineAccessibleDays['max'])) {
                $this->addUsingAlias(PersonPeer::TIMELINEACCESSIBLEDAYS, $timelineAccessibleDays['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::TIMELINEACCESSIBLEDAYS, $timelineAccessibleDays, $comparison);
    }

    /**
     * Filter the query on the typeTimeLinePerson column
     *
     * Example usage:
     * <code>
     * $query->filterBytypeTimeLinePerson(1234); // WHERE typeTimeLinePerson = 1234
     * $query->filterBytypeTimeLinePerson(array(12, 34)); // WHERE typeTimeLinePerson IN (12, 34)
     * $query->filterBytypeTimeLinePerson(array('min' => 12)); // WHERE typeTimeLinePerson >= 12
     * $query->filterBytypeTimeLinePerson(array('max' => 12)); // WHERE typeTimeLinePerson <= 12
     * </code>
     *
     * @param     mixed $typeTimeLinePerson The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBytypeTimeLinePerson($typeTimeLinePerson = null, $comparison = null)
    {
        if (is_array($typeTimeLinePerson)) {
            $useMinMax = false;
            if (isset($typeTimeLinePerson['min'])) {
                $this->addUsingAlias(PersonPeer::TYPETIMELINEPERSON, $typeTimeLinePerson['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($typeTimeLinePerson['max'])) {
                $this->addUsingAlias(PersonPeer::TYPETIMELINEPERSON, $typeTimeLinePerson['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::TYPETIMELINEPERSON, $typeTimeLinePerson, $comparison);
    }

    /**
     * Filter the query on the maxOverQueue column
     *
     * Example usage:
     * <code>
     * $query->filterBymaxOverQueue(1234); // WHERE maxOverQueue = 1234
     * $query->filterBymaxOverQueue(array(12, 34)); // WHERE maxOverQueue IN (12, 34)
     * $query->filterBymaxOverQueue(array('min' => 12)); // WHERE maxOverQueue >= 12
     * $query->filterBymaxOverQueue(array('max' => 12)); // WHERE maxOverQueue <= 12
     * </code>
     *
     * @param     mixed $maxOverQueue The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBymaxOverQueue($maxOverQueue = null, $comparison = null)
    {
        if (is_array($maxOverQueue)) {
            $useMinMax = false;
            if (isset($maxOverQueue['min'])) {
                $this->addUsingAlias(PersonPeer::MAXOVERQUEUE, $maxOverQueue['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($maxOverQueue['max'])) {
                $this->addUsingAlias(PersonPeer::MAXOVERQUEUE, $maxOverQueue['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::MAXOVERQUEUE, $maxOverQueue, $comparison);
    }

    /**
     * Filter the query on the maxCito column
     *
     * Example usage:
     * <code>
     * $query->filterBymaxCito(1234); // WHERE maxCito = 1234
     * $query->filterBymaxCito(array(12, 34)); // WHERE maxCito IN (12, 34)
     * $query->filterBymaxCito(array('min' => 12)); // WHERE maxCito >= 12
     * $query->filterBymaxCito(array('max' => 12)); // WHERE maxCito <= 12
     * </code>
     *
     * @param     mixed $maxCito The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterBymaxCito($maxCito = null, $comparison = null)
    {
        if (is_array($maxCito)) {
            $useMinMax = false;
            if (isset($maxCito['min'])) {
                $this->addUsingAlias(PersonPeer::MAXCITO, $maxCito['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($maxCito['max'])) {
                $this->addUsingAlias(PersonPeer::MAXCITO, $maxCito['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::MAXCITO, $maxCito, $comparison);
    }

    /**
     * Filter the query on the quotUnit column
     *
     * Example usage:
     * <code>
     * $query->filterByquotUnit(1234); // WHERE quotUnit = 1234
     * $query->filterByquotUnit(array(12, 34)); // WHERE quotUnit IN (12, 34)
     * $query->filterByquotUnit(array('min' => 12)); // WHERE quotUnit >= 12
     * $query->filterByquotUnit(array('max' => 12)); // WHERE quotUnit <= 12
     * </code>
     *
     * @param     mixed $quotUnit The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByquotUnit($quotUnit = null, $comparison = null)
    {
        if (is_array($quotUnit)) {
            $useMinMax = false;
            if (isset($quotUnit['min'])) {
                $this->addUsingAlias(PersonPeer::QUOTUNIT, $quotUnit['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($quotUnit['max'])) {
                $this->addUsingAlias(PersonPeer::QUOTUNIT, $quotUnit['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::QUOTUNIT, $quotUnit, $comparison);
    }

    /**
     * Filter the query on the academicdegree_id column
     *
     * Example usage:
     * <code>
     * $query->filterByacademicDegreeId(1234); // WHERE academicdegree_id = 1234
     * $query->filterByacademicDegreeId(array(12, 34)); // WHERE academicdegree_id IN (12, 34)
     * $query->filterByacademicDegreeId(array('min' => 12)); // WHERE academicdegree_id >= 12
     * $query->filterByacademicDegreeId(array('max' => 12)); // WHERE academicdegree_id <= 12
     * </code>
     *
     * @param     mixed $academicDegreeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByacademicDegreeId($academicDegreeId = null, $comparison = null)
    {
        if (is_array($academicDegreeId)) {
            $useMinMax = false;
            if (isset($academicDegreeId['min'])) {
                $this->addUsingAlias(PersonPeer::ACADEMICDEGREE_ID, $academicDegreeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($academicDegreeId['max'])) {
                $this->addUsingAlias(PersonPeer::ACADEMICDEGREE_ID, $academicDegreeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::ACADEMICDEGREE_ID, $academicDegreeId, $comparison);
    }

    /**
     * Filter the query on the academicTitle_id column
     *
     * Example usage:
     * <code>
     * $query->filterByacademicTitleId(1234); // WHERE academicTitle_id = 1234
     * $query->filterByacademicTitleId(array(12, 34)); // WHERE academicTitle_id IN (12, 34)
     * $query->filterByacademicTitleId(array('min' => 12)); // WHERE academicTitle_id >= 12
     * $query->filterByacademicTitleId(array('max' => 12)); // WHERE academicTitle_id <= 12
     * </code>
     *
     * @param     mixed $academicTitleId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByacademicTitleId($academicTitleId = null, $comparison = null)
    {
        if (is_array($academicTitleId)) {
            $useMinMax = false;
            if (isset($academicTitleId['min'])) {
                $this->addUsingAlias(PersonPeer::ACADEMICTITLE_ID, $academicTitleId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($academicTitleId['max'])) {
                $this->addUsingAlias(PersonPeer::ACADEMICTITLE_ID, $academicTitleId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::ACADEMICTITLE_ID, $academicTitleId, $comparison);
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
     * @return PersonQuery The current query, for fluid interface
     */
    public function filterByuuidId($uuidId = null, $comparison = null)
    {
        if (is_array($uuidId)) {
            $useMinMax = false;
            if (isset($uuidId['min'])) {
                $this->addUsingAlias(PersonPeer::UUID_ID, $uuidId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uuidId['max'])) {
                $this->addUsingAlias(PersonPeer::UUID_ID, $uuidId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(PersonPeer::UUID_ID, $uuidId, $comparison);
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionRelatedBycreatePersonId($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $action->getcreatePersonId(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            return $this
                ->useActionRelatedBycreatePersonIdQuery()
                ->filterByPrimaryKeys($action->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionRelatedBycreatePersonId() only accepts arguments of type Action or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionRelatedBycreatePersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinActionRelatedBycreatePersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionRelatedBycreatePersonId');

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
            $this->addJoinObject($join, 'ActionRelatedBycreatePersonId');
        }

        return $this;
    }

    /**
     * Use the ActionRelatedBycreatePersonId relation Action object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionQuery A secondary query class using the current class as primary query
     */
    public function useActionRelatedBycreatePersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinActionRelatedBycreatePersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionRelatedBycreatePersonId', '\Webmis\Models\ActionQuery');
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionRelatedBymodifyPersonId($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $action->getmodifyPersonId(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            return $this
                ->useActionRelatedBymodifyPersonIdQuery()
                ->filterByPrimaryKeys($action->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionRelatedBymodifyPersonId() only accepts arguments of type Action or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionRelatedBymodifyPersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinActionRelatedBymodifyPersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionRelatedBymodifyPersonId');

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
            $this->addJoinObject($join, 'ActionRelatedBymodifyPersonId');
        }

        return $this;
    }

    /**
     * Use the ActionRelatedBymodifyPersonId relation Action object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionQuery A secondary query class using the current class as primary query
     */
    public function useActionRelatedBymodifyPersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinActionRelatedBymodifyPersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionRelatedBymodifyPersonId', '\Webmis\Models\ActionQuery');
    }

    /**
     * Filter the query by a related Action object
     *
     * @param   Action|PropelObjectCollection $action  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionRelatedBysetPersonId($action, $comparison = null)
    {
        if ($action instanceof Action) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $action->getsetPersonId(), $comparison);
        } elseif ($action instanceof PropelObjectCollection) {
            return $this
                ->useActionRelatedBysetPersonIdQuery()
                ->filterByPrimaryKeys($action->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionRelatedBysetPersonId() only accepts arguments of type Action or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionRelatedBysetPersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinActionRelatedBysetPersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionRelatedBysetPersonId');

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
            $this->addJoinObject($join, 'ActionRelatedBysetPersonId');
        }

        return $this;
    }

    /**
     * Use the ActionRelatedBysetPersonId relation Action object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionQuery A secondary query class using the current class as primary query
     */
    public function useActionRelatedBysetPersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinActionRelatedBysetPersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionRelatedBysetPersonId', '\Webmis\Models\ActionQuery');
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventRelatedBycreatePersonId($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $event->getcreatePersonId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventRelatedBycreatePersonIdQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEventRelatedBycreatePersonId() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the EventRelatedBycreatePersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinEventRelatedBycreatePersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('EventRelatedBycreatePersonId');

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
            $this->addJoinObject($join, 'EventRelatedBycreatePersonId');
        }

        return $this;
    }

    /**
     * Use the EventRelatedBycreatePersonId relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventRelatedBycreatePersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEventRelatedBycreatePersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventRelatedBycreatePersonId', '\Webmis\Models\EventQuery');
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventRelatedBymodifyPersonId($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $event->getmodifyPersonId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventRelatedBymodifyPersonIdQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEventRelatedBymodifyPersonId() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the EventRelatedBymodifyPersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinEventRelatedBymodifyPersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('EventRelatedBymodifyPersonId');

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
            $this->addJoinObject($join, 'EventRelatedBymodifyPersonId');
        }

        return $this;
    }

    /**
     * Use the EventRelatedBymodifyPersonId relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventRelatedBymodifyPersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEventRelatedBymodifyPersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventRelatedBymodifyPersonId', '\Webmis\Models\EventQuery');
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventRelatedBysetPersonId($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $event->getsetPersonId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventRelatedBysetPersonIdQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEventRelatedBysetPersonId() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the EventRelatedBysetPersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinEventRelatedBysetPersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('EventRelatedBysetPersonId');

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
            $this->addJoinObject($join, 'EventRelatedBysetPersonId');
        }

        return $this;
    }

    /**
     * Use the EventRelatedBysetPersonId relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventRelatedBysetPersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEventRelatedBysetPersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventRelatedBysetPersonId', '\Webmis\Models\EventQuery');
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 PersonQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEventRelatedByexecPersonId($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(PersonPeer::ID, $event->getexecPersonId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventRelatedByexecPersonIdQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEventRelatedByexecPersonId() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the EventRelatedByexecPersonId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function joinEventRelatedByexecPersonId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('EventRelatedByexecPersonId');

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
            $this->addJoinObject($join, 'EventRelatedByexecPersonId');
        }

        return $this;
    }

    /**
     * Use the EventRelatedByexecPersonId relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventRelatedByexecPersonIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEventRelatedByexecPersonId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'EventRelatedByexecPersonId', '\Webmis\Models\EventQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   Person $person Object to remove from the list of results
     *
     * @return PersonQuery The current query, for fluid interface
     */
    public function prune($person = null)
    {
        if ($person) {
            $this->addUsingAlias(PersonPeer::ID, $person->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(PersonPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(PersonPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(PersonPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(PersonPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(PersonPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     PersonQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(PersonPeer::CREATEDATETIME);
    }
}
