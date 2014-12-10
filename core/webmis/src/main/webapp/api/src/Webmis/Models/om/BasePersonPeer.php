<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\Person;
use Webmis\Models\PersonPeer;
use Webmis\Models\map\PersonTableMap;

/**
 * Base static class for performing query and update operations on the 'Person' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BasePersonPeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'Person';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\Person';

    /** the related TableMap class for this table */
    const TM_CLASS = 'PersonTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 52;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 52;

    /** the column name for the id field */
    const ID = 'Person.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'Person.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'Person.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'Person.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'Person.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'Person.deleted';

    /** the column name for the code field */
    const CODE = 'Person.code';

    /** the column name for the federalCode field */
    const FEDERALCODE = 'Person.federalCode';

    /** the column name for the regionalCode field */
    const REGIONALCODE = 'Person.regionalCode';

    /** the column name for the lastName field */
    const LASTNAME = 'Person.lastName';

    /** the column name for the firstName field */
    const FIRSTNAME = 'Person.firstName';

    /** the column name for the patrName field */
    const PATRNAME = 'Person.patrName';

    /** the column name for the post_id field */
    const POST_ID = 'Person.post_id';

    /** the column name for the speciality_id field */
    const SPECIALITY_ID = 'Person.speciality_id';

    /** the column name for the org_id field */
    const ORG_ID = 'Person.org_id';

    /** the column name for the orgStructure_id field */
    const ORGSTRUCTURE_ID = 'Person.orgStructure_id';

    /** the column name for the office field */
    const OFFICE = 'Person.office';

    /** the column name for the office2 field */
    const OFFICE2 = 'Person.office2';

    /** the column name for the tariffCategory_id field */
    const TARIFFCATEGORY_ID = 'Person.tariffCategory_id';

    /** the column name for the finance_id field */
    const FINANCE_ID = 'Person.finance_id';

    /** the column name for the retireDate field */
    const RETIREDATE = 'Person.retireDate';

    /** the column name for the ambPlan field */
    const AMBPLAN = 'Person.ambPlan';

    /** the column name for the ambPlan2 field */
    const AMBPLAN2 = 'Person.ambPlan2';

    /** the column name for the ambNorm field */
    const AMBNORM = 'Person.ambNorm';

    /** the column name for the homPlan field */
    const HOMPLAN = 'Person.homPlan';

    /** the column name for the homPlan2 field */
    const HOMPLAN2 = 'Person.homPlan2';

    /** the column name for the homNorm field */
    const HOMNORM = 'Person.homNorm';

    /** the column name for the expPlan field */
    const EXPPLAN = 'Person.expPlan';

    /** the column name for the expNorm field */
    const EXPNORM = 'Person.expNorm';

    /** the column name for the login field */
    const LOGIN = 'Person.login';

    /** the column name for the password field */
    const PASSWORD = 'Person.password';

    /** the column name for the userProfile_id field */
    const USERPROFILE_ID = 'Person.userProfile_id';

    /** the column name for the retired field */
    const RETIRED = 'Person.retired';

    /** the column name for the birthDate field */
    const BIRTHDATE = 'Person.birthDate';

    /** the column name for the birthPlace field */
    const BIRTHPLACE = 'Person.birthPlace';

    /** the column name for the sex field */
    const SEX = 'Person.sex';

    /** the column name for the SNILS field */
    const SNILS = 'Person.SNILS';

    /** the column name for the INN field */
    const INN = 'Person.INN';

    /** the column name for the availableForExternal field */
    const AVAILABLEFOREXTERNAL = 'Person.availableForExternal';

    /** the column name for the primaryQuota field */
    const PRIMARYQUOTA = 'Person.primaryQuota';

    /** the column name for the ownQuota field */
    const OWNQUOTA = 'Person.ownQuota';

    /** the column name for the consultancyQuota field */
    const CONSULTANCYQUOTA = 'Person.consultancyQuota';

    /** the column name for the externalQuota field */
    const EXTERNALQUOTA = 'Person.externalQuota';

    /** the column name for the lastAccessibleTimelineDate field */
    const LASTACCESSIBLETIMELINEDATE = 'Person.lastAccessibleTimelineDate';

    /** the column name for the timelineAccessibleDays field */
    const TIMELINEACCESSIBLEDAYS = 'Person.timelineAccessibleDays';

    /** the column name for the typeTimeLinePerson field */
    const TYPETIMELINEPERSON = 'Person.typeTimeLinePerson';

    /** the column name for the maxOverQueue field */
    const MAXOVERQUEUE = 'Person.maxOverQueue';

    /** the column name for the maxCito field */
    const MAXCITO = 'Person.maxCito';

    /** the column name for the quotUnit field */
    const QUOTUNIT = 'Person.quotUnit';

    /** the column name for the academicdegree_id field */
    const ACADEMICDEGREE_ID = 'Person.academicdegree_id';

    /** the column name for the academicTitle_id field */
    const ACADEMICTITLE_ID = 'Person.academicTitle_id';

    /** the column name for the uuid_id field */
    const UUID_ID = 'Person.uuid_id';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of Person objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array Person[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. PersonPeer::$fieldNames[PersonPeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'code', 'federalCode', 'regionalCode', 'lastName', 'firstName', 'patrName', 'postId', 'specialityId', 'orgId', 'orgStructureId', 'office', 'office2', 'tariffCategoryId', 'financeId', 'retireDate', 'ambPlan', 'ambPlan2', 'ambNorm', 'homPlan', 'homPlan2', 'homNorm', 'expPlan', 'expNorm', 'login', 'password', 'userProfileId', 'retired', 'birthDate', 'birthPlace', 'sex', 'snils', 'inn', 'availableForExternal', 'primaryQuota', 'ownQuota', 'consultancyQuota', 'externalQuota', 'lastAccessibleTimelineDate', 'timelineAccessibleDays', 'typeTimeLinePerson', 'maxOverQueue', 'maxCito', 'quotUnit', 'academicDegreeId', 'academicTitleId', 'uuidId', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'code', 'federalCode', 'regionalCode', 'lastName', 'firstName', 'patrName', 'postId', 'specialityId', 'orgId', 'orgStructureId', 'office', 'office2', 'tariffCategoryId', 'financeId', 'retireDate', 'ambPlan', 'ambPlan2', 'ambNorm', 'homPlan', 'homPlan2', 'homNorm', 'expPlan', 'expNorm', 'login', 'password', 'userProfileId', 'retired', 'birthDate', 'birthPlace', 'sex', 'snils', 'inn', 'availableForExternal', 'primaryQuota', 'ownQuota', 'consultancyQuota', 'externalQuota', 'lastAccessibleTimelineDate', 'timelineAccessibleDays', 'typeTimeLinePerson', 'maxOverQueue', 'maxCito', 'quotUnit', 'academicDegreeId', 'academicTitleId', 'uuidId', ),
        BasePeer::TYPE_COLNAME => array (PersonPeer::ID, PersonPeer::CREATEDATETIME, PersonPeer::CREATEPERSON_ID, PersonPeer::MODIFYDATETIME, PersonPeer::MODIFYPERSON_ID, PersonPeer::DELETED, PersonPeer::CODE, PersonPeer::FEDERALCODE, PersonPeer::REGIONALCODE, PersonPeer::LASTNAME, PersonPeer::FIRSTNAME, PersonPeer::PATRNAME, PersonPeer::POST_ID, PersonPeer::SPECIALITY_ID, PersonPeer::ORG_ID, PersonPeer::ORGSTRUCTURE_ID, PersonPeer::OFFICE, PersonPeer::OFFICE2, PersonPeer::TARIFFCATEGORY_ID, PersonPeer::FINANCE_ID, PersonPeer::RETIREDATE, PersonPeer::AMBPLAN, PersonPeer::AMBPLAN2, PersonPeer::AMBNORM, PersonPeer::HOMPLAN, PersonPeer::HOMPLAN2, PersonPeer::HOMNORM, PersonPeer::EXPPLAN, PersonPeer::EXPNORM, PersonPeer::LOGIN, PersonPeer::PASSWORD, PersonPeer::USERPROFILE_ID, PersonPeer::RETIRED, PersonPeer::BIRTHDATE, PersonPeer::BIRTHPLACE, PersonPeer::SEX, PersonPeer::SNILS, PersonPeer::INN, PersonPeer::AVAILABLEFOREXTERNAL, PersonPeer::PRIMARYQUOTA, PersonPeer::OWNQUOTA, PersonPeer::CONSULTANCYQUOTA, PersonPeer::EXTERNALQUOTA, PersonPeer::LASTACCESSIBLETIMELINEDATE, PersonPeer::TIMELINEACCESSIBLEDAYS, PersonPeer::TYPETIMELINEPERSON, PersonPeer::MAXOVERQUEUE, PersonPeer::MAXCITO, PersonPeer::QUOTUNIT, PersonPeer::ACADEMICDEGREE_ID, PersonPeer::ACADEMICTITLE_ID, PersonPeer::UUID_ID, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'CODE', 'FEDERALCODE', 'REGIONALCODE', 'LASTNAME', 'FIRSTNAME', 'PATRNAME', 'POST_ID', 'SPECIALITY_ID', 'ORG_ID', 'ORGSTRUCTURE_ID', 'OFFICE', 'OFFICE2', 'TARIFFCATEGORY_ID', 'FINANCE_ID', 'RETIREDATE', 'AMBPLAN', 'AMBPLAN2', 'AMBNORM', 'HOMPLAN', 'HOMPLAN2', 'HOMNORM', 'EXPPLAN', 'EXPNORM', 'LOGIN', 'PASSWORD', 'USERPROFILE_ID', 'RETIRED', 'BIRTHDATE', 'BIRTHPLACE', 'SEX', 'SNILS', 'INN', 'AVAILABLEFOREXTERNAL', 'PRIMARYQUOTA', 'OWNQUOTA', 'CONSULTANCYQUOTA', 'EXTERNALQUOTA', 'LASTACCESSIBLETIMELINEDATE', 'TIMELINEACCESSIBLEDAYS', 'TYPETIMELINEPERSON', 'MAXOVERQUEUE', 'MAXCITO', 'QUOTUNIT', 'ACADEMICDEGREE_ID', 'ACADEMICTITLE_ID', 'UUID_ID', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'code', 'federalCode', 'regionalCode', 'lastName', 'firstName', 'patrName', 'post_id', 'speciality_id', 'org_id', 'orgStructure_id', 'office', 'office2', 'tariffCategory_id', 'finance_id', 'retireDate', 'ambPlan', 'ambPlan2', 'ambNorm', 'homPlan', 'homPlan2', 'homNorm', 'expPlan', 'expNorm', 'login', 'password', 'userProfile_id', 'retired', 'birthDate', 'birthPlace', 'sex', 'SNILS', 'INN', 'availableForExternal', 'primaryQuota', 'ownQuota', 'consultancyQuota', 'externalQuota', 'lastAccessibleTimelineDate', 'timelineAccessibleDays', 'typeTimeLinePerson', 'maxOverQueue', 'maxCito', 'quotUnit', 'academicdegree_id', 'academicTitle_id', 'uuid_id', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. PersonPeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'code' => 6, 'federalCode' => 7, 'regionalCode' => 8, 'lastName' => 9, 'firstName' => 10, 'patrName' => 11, 'postId' => 12, 'specialityId' => 13, 'orgId' => 14, 'orgStructureId' => 15, 'office' => 16, 'office2' => 17, 'tariffCategoryId' => 18, 'financeId' => 19, 'retireDate' => 20, 'ambPlan' => 21, 'ambPlan2' => 22, 'ambNorm' => 23, 'homPlan' => 24, 'homPlan2' => 25, 'homNorm' => 26, 'expPlan' => 27, 'expNorm' => 28, 'login' => 29, 'password' => 30, 'userProfileId' => 31, 'retired' => 32, 'birthDate' => 33, 'birthPlace' => 34, 'sex' => 35, 'snils' => 36, 'inn' => 37, 'availableForExternal' => 38, 'primaryQuota' => 39, 'ownQuota' => 40, 'consultancyQuota' => 41, 'externalQuota' => 42, 'lastAccessibleTimelineDate' => 43, 'timelineAccessibleDays' => 44, 'typeTimeLinePerson' => 45, 'maxOverQueue' => 46, 'maxCito' => 47, 'quotUnit' => 48, 'academicDegreeId' => 49, 'academicTitleId' => 50, 'uuidId' => 51, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'code' => 6, 'federalCode' => 7, 'regionalCode' => 8, 'lastName' => 9, 'firstName' => 10, 'patrName' => 11, 'postId' => 12, 'specialityId' => 13, 'orgId' => 14, 'orgStructureId' => 15, 'office' => 16, 'office2' => 17, 'tariffCategoryId' => 18, 'financeId' => 19, 'retireDate' => 20, 'ambPlan' => 21, 'ambPlan2' => 22, 'ambNorm' => 23, 'homPlan' => 24, 'homPlan2' => 25, 'homNorm' => 26, 'expPlan' => 27, 'expNorm' => 28, 'login' => 29, 'password' => 30, 'userProfileId' => 31, 'retired' => 32, 'birthDate' => 33, 'birthPlace' => 34, 'sex' => 35, 'snils' => 36, 'inn' => 37, 'availableForExternal' => 38, 'primaryQuota' => 39, 'ownQuota' => 40, 'consultancyQuota' => 41, 'externalQuota' => 42, 'lastAccessibleTimelineDate' => 43, 'timelineAccessibleDays' => 44, 'typeTimeLinePerson' => 45, 'maxOverQueue' => 46, 'maxCito' => 47, 'quotUnit' => 48, 'academicDegreeId' => 49, 'academicTitleId' => 50, 'uuidId' => 51, ),
        BasePeer::TYPE_COLNAME => array (PersonPeer::ID => 0, PersonPeer::CREATEDATETIME => 1, PersonPeer::CREATEPERSON_ID => 2, PersonPeer::MODIFYDATETIME => 3, PersonPeer::MODIFYPERSON_ID => 4, PersonPeer::DELETED => 5, PersonPeer::CODE => 6, PersonPeer::FEDERALCODE => 7, PersonPeer::REGIONALCODE => 8, PersonPeer::LASTNAME => 9, PersonPeer::FIRSTNAME => 10, PersonPeer::PATRNAME => 11, PersonPeer::POST_ID => 12, PersonPeer::SPECIALITY_ID => 13, PersonPeer::ORG_ID => 14, PersonPeer::ORGSTRUCTURE_ID => 15, PersonPeer::OFFICE => 16, PersonPeer::OFFICE2 => 17, PersonPeer::TARIFFCATEGORY_ID => 18, PersonPeer::FINANCE_ID => 19, PersonPeer::RETIREDATE => 20, PersonPeer::AMBPLAN => 21, PersonPeer::AMBPLAN2 => 22, PersonPeer::AMBNORM => 23, PersonPeer::HOMPLAN => 24, PersonPeer::HOMPLAN2 => 25, PersonPeer::HOMNORM => 26, PersonPeer::EXPPLAN => 27, PersonPeer::EXPNORM => 28, PersonPeer::LOGIN => 29, PersonPeer::PASSWORD => 30, PersonPeer::USERPROFILE_ID => 31, PersonPeer::RETIRED => 32, PersonPeer::BIRTHDATE => 33, PersonPeer::BIRTHPLACE => 34, PersonPeer::SEX => 35, PersonPeer::SNILS => 36, PersonPeer::INN => 37, PersonPeer::AVAILABLEFOREXTERNAL => 38, PersonPeer::PRIMARYQUOTA => 39, PersonPeer::OWNQUOTA => 40, PersonPeer::CONSULTANCYQUOTA => 41, PersonPeer::EXTERNALQUOTA => 42, PersonPeer::LASTACCESSIBLETIMELINEDATE => 43, PersonPeer::TIMELINEACCESSIBLEDAYS => 44, PersonPeer::TYPETIMELINEPERSON => 45, PersonPeer::MAXOVERQUEUE => 46, PersonPeer::MAXCITO => 47, PersonPeer::QUOTUNIT => 48, PersonPeer::ACADEMICDEGREE_ID => 49, PersonPeer::ACADEMICTITLE_ID => 50, PersonPeer::UUID_ID => 51, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'CODE' => 6, 'FEDERALCODE' => 7, 'REGIONALCODE' => 8, 'LASTNAME' => 9, 'FIRSTNAME' => 10, 'PATRNAME' => 11, 'POST_ID' => 12, 'SPECIALITY_ID' => 13, 'ORG_ID' => 14, 'ORGSTRUCTURE_ID' => 15, 'OFFICE' => 16, 'OFFICE2' => 17, 'TARIFFCATEGORY_ID' => 18, 'FINANCE_ID' => 19, 'RETIREDATE' => 20, 'AMBPLAN' => 21, 'AMBPLAN2' => 22, 'AMBNORM' => 23, 'HOMPLAN' => 24, 'HOMPLAN2' => 25, 'HOMNORM' => 26, 'EXPPLAN' => 27, 'EXPNORM' => 28, 'LOGIN' => 29, 'PASSWORD' => 30, 'USERPROFILE_ID' => 31, 'RETIRED' => 32, 'BIRTHDATE' => 33, 'BIRTHPLACE' => 34, 'SEX' => 35, 'SNILS' => 36, 'INN' => 37, 'AVAILABLEFOREXTERNAL' => 38, 'PRIMARYQUOTA' => 39, 'OWNQUOTA' => 40, 'CONSULTANCYQUOTA' => 41, 'EXTERNALQUOTA' => 42, 'LASTACCESSIBLETIMELINEDATE' => 43, 'TIMELINEACCESSIBLEDAYS' => 44, 'TYPETIMELINEPERSON' => 45, 'MAXOVERQUEUE' => 46, 'MAXCITO' => 47, 'QUOTUNIT' => 48, 'ACADEMICDEGREE_ID' => 49, 'ACADEMICTITLE_ID' => 50, 'UUID_ID' => 51, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'code' => 6, 'federalCode' => 7, 'regionalCode' => 8, 'lastName' => 9, 'firstName' => 10, 'patrName' => 11, 'post_id' => 12, 'speciality_id' => 13, 'org_id' => 14, 'orgStructure_id' => 15, 'office' => 16, 'office2' => 17, 'tariffCategory_id' => 18, 'finance_id' => 19, 'retireDate' => 20, 'ambPlan' => 21, 'ambPlan2' => 22, 'ambNorm' => 23, 'homPlan' => 24, 'homPlan2' => 25, 'homNorm' => 26, 'expPlan' => 27, 'expNorm' => 28, 'login' => 29, 'password' => 30, 'userProfile_id' => 31, 'retired' => 32, 'birthDate' => 33, 'birthPlace' => 34, 'sex' => 35, 'SNILS' => 36, 'INN' => 37, 'availableForExternal' => 38, 'primaryQuota' => 39, 'ownQuota' => 40, 'consultancyQuota' => 41, 'externalQuota' => 42, 'lastAccessibleTimelineDate' => 43, 'timelineAccessibleDays' => 44, 'typeTimeLinePerson' => 45, 'maxOverQueue' => 46, 'maxCito' => 47, 'quotUnit' => 48, 'academicdegree_id' => 49, 'academicTitle_id' => 50, 'uuid_id' => 51, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, )
    );

    /**
     * Translates a fieldname to another type
     *
     * @param      string $name field name
     * @param      string $fromType One of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *                         BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM
     * @param      string $toType   One of the class type constants
     * @return string          translated name of the field.
     * @throws PropelException - if the specified name could not be found in the fieldname mappings.
     */
    public static function translateFieldName($name, $fromType, $toType)
    {
        $toNames = PersonPeer::getFieldNames($toType);
        $key = isset(PersonPeer::$fieldKeys[$fromType][$name]) ? PersonPeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(PersonPeer::$fieldKeys[$fromType], true));
        }

        return $toNames[$key];
    }

    /**
     * Returns an array of field names.
     *
     * @param      string $type The type of fieldnames to return:
     *                      One of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *                      BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM
     * @return array           A list of field names
     * @throws PropelException - if the type is not valid.
     */
    public static function getFieldNames($type = BasePeer::TYPE_PHPNAME)
    {
        if (!array_key_exists($type, PersonPeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return PersonPeer::$fieldNames[$type];
    }

    /**
     * Convenience method which changes table.column to alias.column.
     *
     * Using this method you can maintain SQL abstraction while using column aliases.
     * <code>
     *		$c->addAlias("alias1", TablePeer::TABLE_NAME);
     *		$c->addJoin(TablePeer::alias("alias1", TablePeer::PRIMARY_KEY_COLUMN), TablePeer::PRIMARY_KEY_COLUMN);
     * </code>
     * @param      string $alias The alias for the current table.
     * @param      string $column The column name for current table. (i.e. PersonPeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(PersonPeer::TABLE_NAME.'.', $alias.'.', $column);
    }

    /**
     * Add all the columns needed to create a new object.
     *
     * Note: any columns that were marked with lazyLoad="true" in the
     * XML schema will not be added to the select list and only loaded
     * on demand.
     *
     * @param      Criteria $criteria object containing the columns to add.
     * @param      string   $alias    optional table alias
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function addSelectColumns(Criteria $criteria, $alias = null)
    {
        if (null === $alias) {
            $criteria->addSelectColumn(PersonPeer::ID);
            $criteria->addSelectColumn(PersonPeer::CREATEDATETIME);
            $criteria->addSelectColumn(PersonPeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(PersonPeer::MODIFYDATETIME);
            $criteria->addSelectColumn(PersonPeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(PersonPeer::DELETED);
            $criteria->addSelectColumn(PersonPeer::CODE);
            $criteria->addSelectColumn(PersonPeer::FEDERALCODE);
            $criteria->addSelectColumn(PersonPeer::REGIONALCODE);
            $criteria->addSelectColumn(PersonPeer::LASTNAME);
            $criteria->addSelectColumn(PersonPeer::FIRSTNAME);
            $criteria->addSelectColumn(PersonPeer::PATRNAME);
            $criteria->addSelectColumn(PersonPeer::POST_ID);
            $criteria->addSelectColumn(PersonPeer::SPECIALITY_ID);
            $criteria->addSelectColumn(PersonPeer::ORG_ID);
            $criteria->addSelectColumn(PersonPeer::ORGSTRUCTURE_ID);
            $criteria->addSelectColumn(PersonPeer::OFFICE);
            $criteria->addSelectColumn(PersonPeer::OFFICE2);
            $criteria->addSelectColumn(PersonPeer::TARIFFCATEGORY_ID);
            $criteria->addSelectColumn(PersonPeer::FINANCE_ID);
            $criteria->addSelectColumn(PersonPeer::RETIREDATE);
            $criteria->addSelectColumn(PersonPeer::AMBPLAN);
            $criteria->addSelectColumn(PersonPeer::AMBPLAN2);
            $criteria->addSelectColumn(PersonPeer::AMBNORM);
            $criteria->addSelectColumn(PersonPeer::HOMPLAN);
            $criteria->addSelectColumn(PersonPeer::HOMPLAN2);
            $criteria->addSelectColumn(PersonPeer::HOMNORM);
            $criteria->addSelectColumn(PersonPeer::EXPPLAN);
            $criteria->addSelectColumn(PersonPeer::EXPNORM);
            $criteria->addSelectColumn(PersonPeer::LOGIN);
            $criteria->addSelectColumn(PersonPeer::PASSWORD);
            $criteria->addSelectColumn(PersonPeer::USERPROFILE_ID);
            $criteria->addSelectColumn(PersonPeer::RETIRED);
            $criteria->addSelectColumn(PersonPeer::BIRTHDATE);
            $criteria->addSelectColumn(PersonPeer::BIRTHPLACE);
            $criteria->addSelectColumn(PersonPeer::SEX);
            $criteria->addSelectColumn(PersonPeer::SNILS);
            $criteria->addSelectColumn(PersonPeer::INN);
            $criteria->addSelectColumn(PersonPeer::AVAILABLEFOREXTERNAL);
            $criteria->addSelectColumn(PersonPeer::PRIMARYQUOTA);
            $criteria->addSelectColumn(PersonPeer::OWNQUOTA);
            $criteria->addSelectColumn(PersonPeer::CONSULTANCYQUOTA);
            $criteria->addSelectColumn(PersonPeer::EXTERNALQUOTA);
            $criteria->addSelectColumn(PersonPeer::LASTACCESSIBLETIMELINEDATE);
            $criteria->addSelectColumn(PersonPeer::TIMELINEACCESSIBLEDAYS);
            $criteria->addSelectColumn(PersonPeer::TYPETIMELINEPERSON);
            $criteria->addSelectColumn(PersonPeer::MAXOVERQUEUE);
            $criteria->addSelectColumn(PersonPeer::MAXCITO);
            $criteria->addSelectColumn(PersonPeer::QUOTUNIT);
            $criteria->addSelectColumn(PersonPeer::ACADEMICDEGREE_ID);
            $criteria->addSelectColumn(PersonPeer::ACADEMICTITLE_ID);
            $criteria->addSelectColumn(PersonPeer::UUID_ID);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.code');
            $criteria->addSelectColumn($alias . '.federalCode');
            $criteria->addSelectColumn($alias . '.regionalCode');
            $criteria->addSelectColumn($alias . '.lastName');
            $criteria->addSelectColumn($alias . '.firstName');
            $criteria->addSelectColumn($alias . '.patrName');
            $criteria->addSelectColumn($alias . '.post_id');
            $criteria->addSelectColumn($alias . '.speciality_id');
            $criteria->addSelectColumn($alias . '.org_id');
            $criteria->addSelectColumn($alias . '.orgStructure_id');
            $criteria->addSelectColumn($alias . '.office');
            $criteria->addSelectColumn($alias . '.office2');
            $criteria->addSelectColumn($alias . '.tariffCategory_id');
            $criteria->addSelectColumn($alias . '.finance_id');
            $criteria->addSelectColumn($alias . '.retireDate');
            $criteria->addSelectColumn($alias . '.ambPlan');
            $criteria->addSelectColumn($alias . '.ambPlan2');
            $criteria->addSelectColumn($alias . '.ambNorm');
            $criteria->addSelectColumn($alias . '.homPlan');
            $criteria->addSelectColumn($alias . '.homPlan2');
            $criteria->addSelectColumn($alias . '.homNorm');
            $criteria->addSelectColumn($alias . '.expPlan');
            $criteria->addSelectColumn($alias . '.expNorm');
            $criteria->addSelectColumn($alias . '.login');
            $criteria->addSelectColumn($alias . '.password');
            $criteria->addSelectColumn($alias . '.userProfile_id');
            $criteria->addSelectColumn($alias . '.retired');
            $criteria->addSelectColumn($alias . '.birthDate');
            $criteria->addSelectColumn($alias . '.birthPlace');
            $criteria->addSelectColumn($alias . '.sex');
            $criteria->addSelectColumn($alias . '.SNILS');
            $criteria->addSelectColumn($alias . '.INN');
            $criteria->addSelectColumn($alias . '.availableForExternal');
            $criteria->addSelectColumn($alias . '.primaryQuota');
            $criteria->addSelectColumn($alias . '.ownQuota');
            $criteria->addSelectColumn($alias . '.consultancyQuota');
            $criteria->addSelectColumn($alias . '.externalQuota');
            $criteria->addSelectColumn($alias . '.lastAccessibleTimelineDate');
            $criteria->addSelectColumn($alias . '.timelineAccessibleDays');
            $criteria->addSelectColumn($alias . '.typeTimeLinePerson');
            $criteria->addSelectColumn($alias . '.maxOverQueue');
            $criteria->addSelectColumn($alias . '.maxCito');
            $criteria->addSelectColumn($alias . '.quotUnit');
            $criteria->addSelectColumn($alias . '.academicdegree_id');
            $criteria->addSelectColumn($alias . '.academicTitle_id');
            $criteria->addSelectColumn($alias . '.uuid_id');
        }
    }

    /**
     * Returns the number of rows matching criteria.
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @return int Number of matching rows.
     */
    public static function doCount(Criteria $criteria, $distinct = false, PropelPDO $con = null)
    {
        // we may modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(PersonPeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            PersonPeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(PersonPeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }
        // BasePeer returns a PDOStatement
        $stmt = BasePeer::doCount($criteria, $con);

        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $count = (int) $row[0];
        } else {
            $count = 0; // no rows returned; we infer that means 0 matches.
        }
        $stmt->closeCursor();

        return $count;
    }
    /**
     * Selects one object from the DB.
     *
     * @param      Criteria $criteria object used to create the SELECT statement.
     * @param      PropelPDO $con
     * @return                 Person
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = PersonPeer::doSelect($critcopy, $con);
        if ($objects) {
            return $objects[0];
        }

        return null;
    }
    /**
     * Selects several row from the DB.
     *
     * @param      Criteria $criteria The Criteria object used to build the SELECT statement.
     * @param      PropelPDO $con
     * @return array           Array of selected Objects
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelect(Criteria $criteria, PropelPDO $con = null)
    {
        return PersonPeer::populateObjects(PersonPeer::doSelectStmt($criteria, $con));
    }
    /**
     * Prepares the Criteria object and uses the parent doSelect() method to execute a PDOStatement.
     *
     * Use this method directly if you want to work with an executed statement directly (for example
     * to perform your own object hydration).
     *
     * @param      Criteria $criteria The Criteria object used to build the SELECT statement.
     * @param      PropelPDO $con The connection to use
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     * @return PDOStatement The executed PDOStatement object.
     * @see        BasePeer::doSelect()
     */
    public static function doSelectStmt(Criteria $criteria, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            PersonPeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(PersonPeer::DATABASE_NAME);

        // BasePeer returns a PDOStatement
        return BasePeer::doSelect($criteria, $con);
    }
    /**
     * Adds an object to the instance pool.
     *
     * Propel keeps cached copies of objects in an instance pool when they are retrieved
     * from the database.  In some cases -- especially when you override doSelect*()
     * methods in your stub classes -- you may need to explicitly add objects
     * to the cache in order to ensure that the same objects are always returned by doSelect*()
     * and retrieveByPK*() calls.
     *
     * @param      Person $obj A Person object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            PersonPeer::$instances[$key] = $obj;
        }
    }

    /**
     * Removes an object from the instance pool.
     *
     * Propel keeps cached copies of objects in an instance pool when they are retrieved
     * from the database.  In some cases -- especially when you override doDelete
     * methods in your stub classes -- you may need to explicitly remove objects
     * from the cache in order to prevent returning objects that no longer exist.
     *
     * @param      mixed $value A Person object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof Person) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or Person object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(PersonPeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   Person Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(PersonPeer::$instances[$key])) {
                return PersonPeer::$instances[$key];
            }
        }

        return null; // just to be explicit
    }

    /**
     * Clear the instance pool.
     *
     * @return void
     */
    public static function clearInstancePool($and_clear_all_references = false)
    {
      if ($and_clear_all_references)
      {
        foreach (PersonPeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        PersonPeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to Person
     * by a foreign key with ON DELETE CASCADE
     */
    public static function clearRelatedInstancePool()
    {
    }

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @return string A string version of PK or null if the components of primary key in result array are all null.
     */
    public static function getPrimaryKeyHashFromRow($row, $startcol = 0)
    {
        // If the PK cannot be derived from the row, return null.
        if ($row[$startcol] === null) {
            return null;
        }

        return (string) $row[$startcol];
    }

    /**
     * Retrieves the primary key from the DB resultset row
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, an array of the primary key columns will be returned.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @return mixed The primary key of the row
     */
    public static function getPrimaryKeyFromRow($row, $startcol = 0)
    {

        return (int) $row[$startcol];
    }

    /**
     * The returned array will contain objects of the default type or
     * objects that inherit from the default.
     *
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function populateObjects(PDOStatement $stmt)
    {
        $results = array();

        // set the class once to avoid overhead in the loop
        $cls = PersonPeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = PersonPeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = PersonPeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                PersonPeer::addInstanceToPool($obj, $key);
            } // if key exists
        }
        $stmt->closeCursor();

        return $results;
    }
    /**
     * Populates an object of the default type or an object that inherit from the default.
     *
     * @param      array $row PropelPDO resultset row.
     * @param      int $startcol The 0-based offset for reading from the resultset row.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     * @return array (Person object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = PersonPeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = PersonPeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + PersonPeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = PersonPeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            PersonPeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }

    /**
     * Returns the TableMap related to this peer.
     * This method is not needed for general use but a specific application could have a need.
     * @return TableMap
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function getTableMap()
    {
        return Propel::getDatabaseMap(PersonPeer::DATABASE_NAME)->getTable(PersonPeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BasePersonPeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BasePersonPeer::TABLE_NAME)) {
        $dbMap->addTableObject(new PersonTableMap());
      }
    }

    /**
     * The class that the Peer will make instances of.
     *
     *
     * @return string ClassName
     */
    public static function getOMClass($row = 0, $colnum = 0)
    {
        return PersonPeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a Person or Criteria object.
     *
     * @param      mixed $values Criteria or Person object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from Person object
        }

        if ($criteria->containsKey(PersonPeer::ID) && $criteria->keyContainsValue(PersonPeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.PersonPeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(PersonPeer::DATABASE_NAME);

        try {
            // use transaction because $criteria could contain info
            // for more than one table (I guess, conceivably)
            $con->beginTransaction();
            $pk = BasePeer::doInsert($criteria, $con);
            $con->commit();
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }

        return $pk;
    }

    /**
     * Performs an UPDATE on the database, given a Person or Criteria object.
     *
     * @param      mixed $values Criteria or Person object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(PersonPeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(PersonPeer::ID);
            $value = $criteria->remove(PersonPeer::ID);
            if ($value) {
                $selectCriteria->add(PersonPeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(PersonPeer::TABLE_NAME);
            }

        } else { // $values is Person object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(PersonPeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the Person table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(PersonPeer::TABLE_NAME, $con, PersonPeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            PersonPeer::clearInstancePool();
            PersonPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a Person or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or Person object or primary key or array of primary keys
     *              which is used to create the DELETE statement
     * @param      PropelPDO $con the connection to use
     * @return int The number of affected rows (if supported by underlying database driver).  This includes CASCADE-related rows
     *				if supported by native driver or if emulated using Propel.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
     public static function doDelete($values, PropelPDO $con = null)
     {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            PersonPeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof Person) { // it's a model object
            // invalidate the cache for this single object
            PersonPeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(PersonPeer::DATABASE_NAME);
            $criteria->add(PersonPeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                PersonPeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(PersonPeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            PersonPeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given Person object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      Person $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(PersonPeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(PersonPeer::TABLE_NAME);

            if (! is_array($cols)) {
                $cols = array($cols);
            }

            foreach ($cols as $colName) {
                if ($tableMap->hasColumn($colName)) {
                    $get = 'get' . $tableMap->getColumn($colName)->getPhpName();
                    $columns[$colName] = $obj->$get();
                }
            }
        } else {

        }

        return BasePeer::doValidate(PersonPeer::DATABASE_NAME, PersonPeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return Person
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = PersonPeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(PersonPeer::DATABASE_NAME);
        $criteria->add(PersonPeer::ID, $pk);

        $v = PersonPeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return Person[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(PersonPeer::DATABASE_NAME);
            $criteria->add(PersonPeer::ID, $pks, Criteria::IN);
            $objs = PersonPeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BasePersonPeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BasePersonPeer::buildTableMap();

