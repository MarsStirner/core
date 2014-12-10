<?php

namespace Webmis\Models\om;

use \BasePeer;
use \Criteria;
use \PDO;
use \PDOStatement;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\ActionPropertyTypePeer;
use Webmis\Models\ActionType;
use Webmis\Models\ActionTypePeer;
use Webmis\Models\map\ActionTypeTableMap;

/**
 * Base static class for performing query and update operations on the 'ActionType' table.
 *
 *
 *
 * @package propel.generator.Models.om
 */
abstract class BaseActionTypePeer
{

    /** the default database name for this class */
    const DATABASE_NAME = 'Webmis-API';

    /** the table name for this class */
    const TABLE_NAME = 'ActionType';

    /** the related Propel class for this table */
    const OM_CLASS = 'Webmis\\Models\\ActionType';

    /** the related TableMap class for this table */
    const TM_CLASS = 'ActionTypeTableMap';

    /** The total number of columns. */
    const NUM_COLUMNS = 45;

    /** The number of lazy-loaded columns. */
    const NUM_LAZY_LOAD_COLUMNS = 0;

    /** The number of columns to hydrate (NUM_COLUMNS - NUM_LAZY_LOAD_COLUMNS) */
    const NUM_HYDRATE_COLUMNS = 45;

    /** the column name for the id field */
    const ID = 'ActionType.id';

    /** the column name for the createDatetime field */
    const CREATEDATETIME = 'ActionType.createDatetime';

    /** the column name for the createPerson_id field */
    const CREATEPERSON_ID = 'ActionType.createPerson_id';

    /** the column name for the modifyDatetime field */
    const MODIFYDATETIME = 'ActionType.modifyDatetime';

    /** the column name for the modifyPerson_id field */
    const MODIFYPERSON_ID = 'ActionType.modifyPerson_id';

    /** the column name for the deleted field */
    const DELETED = 'ActionType.deleted';

    /** the column name for the class field */
    const CLAZZ = 'ActionType.class';

    /** the column name for the group_id field */
    const GROUP_ID = 'ActionType.group_id';

    /** the column name for the code field */
    const CODE = 'ActionType.code';

    /** the column name for the name field */
    const NAME = 'ActionType.name';

    /** the column name for the title field */
    const TITLE = 'ActionType.title';

    /** the column name for the flatCode field */
    const FLATCODE = 'ActionType.flatCode';

    /** the column name for the sex field */
    const SEX = 'ActionType.sex';

    /** the column name for the age field */
    const AGE = 'ActionType.age';

    /** the column name for the age_bu field */
    const AGE_BU = 'ActionType.age_bu';

    /** the column name for the age_bc field */
    const AGE_BC = 'ActionType.age_bc';

    /** the column name for the age_eu field */
    const AGE_EU = 'ActionType.age_eu';

    /** the column name for the age_ec field */
    const AGE_EC = 'ActionType.age_ec';

    /** the column name for the office field */
    const OFFICE = 'ActionType.office';

    /** the column name for the showInForm field */
    const SHOWINFORM = 'ActionType.showInForm';

    /** the column name for the genTimetable field */
    const GENTIMETABLE = 'ActionType.genTimetable';

    /** the column name for the service_id field */
    const SERVICE_ID = 'ActionType.service_id';

    /** the column name for the quotaType_id field */
    const QUOTATYPE_ID = 'ActionType.quotaType_id';

    /** the column name for the context field */
    const CONTEXT = 'ActionType.context';

    /** the column name for the amount field */
    const AMOUNT = 'ActionType.amount';

    /** the column name for the amountEvaluation field */
    const AMOUNTEVALUATION = 'ActionType.amountEvaluation';

    /** the column name for the defaultStatus field */
    const DEFAULTSTATUS = 'ActionType.defaultStatus';

    /** the column name for the defaultDirectionDate field */
    const DEFAULTDIRECTIONDATE = 'ActionType.defaultDirectionDate';

    /** the column name for the defaultPlannedEndDate field */
    const DEFAULTPLANNEDENDDATE = 'ActionType.defaultPlannedEndDate';

    /** the column name for the defaultEndDate field */
    const DEFAULTENDDATE = 'ActionType.defaultEndDate';

    /** the column name for the defaultExecPerson_id field */
    const DEFAULTEXECPERSON_ID = 'ActionType.defaultExecPerson_id';

    /** the column name for the defaultPersonInEvent field */
    const DEFAULTPERSONINEVENT = 'ActionType.defaultPersonInEvent';

    /** the column name for the defaultPersonInEditor field */
    const DEFAULTPERSONINEDITOR = 'ActionType.defaultPersonInEditor';

    /** the column name for the maxOccursInEvent field */
    const MAXOCCURSINEVENT = 'ActionType.maxOccursInEvent';

    /** the column name for the showTime field */
    const SHOWTIME = 'ActionType.showTime';

    /** the column name for the isMES field */
    const ISMES = 'ActionType.isMES';

    /** the column name for the nomenclativeService_id field */
    const NOMENCLATIVESERVICE_ID = 'ActionType.nomenclativeService_id';

    /** the column name for the isPreferable field */
    const ISPREFERABLE = 'ActionType.isPreferable';

    /** the column name for the prescribedType_id field */
    const PRESCRIBEDTYPE_ID = 'ActionType.prescribedType_id';

    /** the column name for the shedule_id field */
    const SHEDULE_ID = 'ActionType.shedule_id';

    /** the column name for the isRequiredCoordination field */
    const ISREQUIREDCOORDINATION = 'ActionType.isRequiredCoordination';

    /** the column name for the isRequiredTissue field */
    const ISREQUIREDTISSUE = 'ActionType.isRequiredTissue';

    /** the column name for the testTubeType_id field */
    const TESTTUBETYPE_ID = 'ActionType.testTubeType_id';

    /** the column name for the jobType_id field */
    const JOBTYPE_ID = 'ActionType.jobType_id';

    /** the column name for the mnem field */
    const MNEM = 'ActionType.mnem';

    /** The default string format for model objects of the related table **/
    const DEFAULT_STRING_FORMAT = 'YAML';

    /**
     * An identiy map to hold any loaded instances of ActionType objects.
     * This must be public so that other peer classes can access this when hydrating from JOIN
     * queries.
     * @var        array ActionType[]
     */
    public static $instances = array();


    /**
     * holds an array of fieldnames
     *
     * first dimension keys are the type constants
     * e.g. ActionTypePeer::$fieldNames[ActionTypePeer::TYPE_PHPNAME][0] = 'Id'
     */
    protected static $fieldNames = array (
        BasePeer::TYPE_PHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'Class', 'groupId', 'code', 'name', 'title', 'flatCode', 'sex', 'age', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'office', 'showInForm', 'genTimeTable', 'serviceId', 'quotaTypeId', 'context', 'amount', 'amountEvaluation', 'defaultStatus', 'defaultDirectionDate', 'defaultPlannedEndDate', 'defaultEndDate', 'defaultExecPersonId', 'defaultPersonInEvent', 'defaultPersonInEditor', 'maxOccursInEvent', 'showTime', 'isMes', 'nomenclativeServiceId', 'isPreferable', 'prescribedTypeId', 'sheduleId', 'isRequiredCoordination', 'isRequiredTissue', 'testTubeTypeId', 'jobTypeId', 'mnem', ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id', 'createDatetime', 'createPersonId', 'modifyDatetime', 'modifyPersonId', 'deleted', 'class', 'groupId', 'code', 'name', 'title', 'flatCode', 'sex', 'age', 'ageBu', 'ageBc', 'ageEu', 'ageEc', 'office', 'showInForm', 'genTimeTable', 'serviceId', 'quotaTypeId', 'context', 'amount', 'amountEvaluation', 'defaultStatus', 'defaultDirectionDate', 'defaultPlannedEndDate', 'defaultEndDate', 'defaultExecPersonId', 'defaultPersonInEvent', 'defaultPersonInEditor', 'maxOccursInEvent', 'showTime', 'isMes', 'nomenclativeServiceId', 'isPreferable', 'prescribedTypeId', 'sheduleId', 'isRequiredCoordination', 'isRequiredTissue', 'testTubeTypeId', 'jobTypeId', 'mnem', ),
        BasePeer::TYPE_COLNAME => array (ActionTypePeer::ID, ActionTypePeer::CREATEDATETIME, ActionTypePeer::CREATEPERSON_ID, ActionTypePeer::MODIFYDATETIME, ActionTypePeer::MODIFYPERSON_ID, ActionTypePeer::DELETED, ActionTypePeer::CLAZZ, ActionTypePeer::GROUP_ID, ActionTypePeer::CODE, ActionTypePeer::NAME, ActionTypePeer::TITLE, ActionTypePeer::FLATCODE, ActionTypePeer::SEX, ActionTypePeer::AGE, ActionTypePeer::AGE_BU, ActionTypePeer::AGE_BC, ActionTypePeer::AGE_EU, ActionTypePeer::AGE_EC, ActionTypePeer::OFFICE, ActionTypePeer::SHOWINFORM, ActionTypePeer::GENTIMETABLE, ActionTypePeer::SERVICE_ID, ActionTypePeer::QUOTATYPE_ID, ActionTypePeer::CONTEXT, ActionTypePeer::AMOUNT, ActionTypePeer::AMOUNTEVALUATION, ActionTypePeer::DEFAULTSTATUS, ActionTypePeer::DEFAULTDIRECTIONDATE, ActionTypePeer::DEFAULTPLANNEDENDDATE, ActionTypePeer::DEFAULTENDDATE, ActionTypePeer::DEFAULTEXECPERSON_ID, ActionTypePeer::DEFAULTPERSONINEVENT, ActionTypePeer::DEFAULTPERSONINEDITOR, ActionTypePeer::MAXOCCURSINEVENT, ActionTypePeer::SHOWTIME, ActionTypePeer::ISMES, ActionTypePeer::NOMENCLATIVESERVICE_ID, ActionTypePeer::ISPREFERABLE, ActionTypePeer::PRESCRIBEDTYPE_ID, ActionTypePeer::SHEDULE_ID, ActionTypePeer::ISREQUIREDCOORDINATION, ActionTypePeer::ISREQUIREDTISSUE, ActionTypePeer::TESTTUBETYPE_ID, ActionTypePeer::JOBTYPE_ID, ActionTypePeer::MNEM, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID', 'CREATEDATETIME', 'CREATEPERSON_ID', 'MODIFYDATETIME', 'MODIFYPERSON_ID', 'DELETED', 'CLAZZ', 'GROUP_ID', 'CODE', 'NAME', 'TITLE', 'FLATCODE', 'SEX', 'AGE', 'AGE_BU', 'AGE_BC', 'AGE_EU', 'AGE_EC', 'OFFICE', 'SHOWINFORM', 'GENTIMETABLE', 'SERVICE_ID', 'QUOTATYPE_ID', 'CONTEXT', 'AMOUNT', 'AMOUNTEVALUATION', 'DEFAULTSTATUS', 'DEFAULTDIRECTIONDATE', 'DEFAULTPLANNEDENDDATE', 'DEFAULTENDDATE', 'DEFAULTEXECPERSON_ID', 'DEFAULTPERSONINEVENT', 'DEFAULTPERSONINEDITOR', 'MAXOCCURSINEVENT', 'SHOWTIME', 'ISMES', 'NOMENCLATIVESERVICE_ID', 'ISPREFERABLE', 'PRESCRIBEDTYPE_ID', 'SHEDULE_ID', 'ISREQUIREDCOORDINATION', 'ISREQUIREDTISSUE', 'TESTTUBETYPE_ID', 'JOBTYPE_ID', 'MNEM', ),
        BasePeer::TYPE_FIELDNAME => array ('id', 'createDatetime', 'createPerson_id', 'modifyDatetime', 'modifyPerson_id', 'deleted', 'class', 'group_id', 'code', 'name', 'title', 'flatCode', 'sex', 'age', 'age_bu', 'age_bc', 'age_eu', 'age_ec', 'office', 'showInForm', 'genTimetable', 'service_id', 'quotaType_id', 'context', 'amount', 'amountEvaluation', 'defaultStatus', 'defaultDirectionDate', 'defaultPlannedEndDate', 'defaultEndDate', 'defaultExecPerson_id', 'defaultPersonInEvent', 'defaultPersonInEditor', 'maxOccursInEvent', 'showTime', 'isMES', 'nomenclativeService_id', 'isPreferable', 'prescribedType_id', 'shedule_id', 'isRequiredCoordination', 'isRequiredTissue', 'testTubeType_id', 'jobType_id', 'mnem', ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, )
    );

    /**
     * holds an array of keys for quick access to the fieldnames array
     *
     * first dimension keys are the type constants
     * e.g. ActionTypePeer::$fieldNames[BasePeer::TYPE_PHPNAME]['Id'] = 0
     */
    protected static $fieldKeys = array (
        BasePeer::TYPE_PHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'Class' => 6, 'groupId' => 7, 'code' => 8, 'name' => 9, 'title' => 10, 'flatCode' => 11, 'sex' => 12, 'age' => 13, 'ageBu' => 14, 'ageBc' => 15, 'ageEu' => 16, 'ageEc' => 17, 'office' => 18, 'showInForm' => 19, 'genTimeTable' => 20, 'serviceId' => 21, 'quotaTypeId' => 22, 'context' => 23, 'amount' => 24, 'amountEvaluation' => 25, 'defaultStatus' => 26, 'defaultDirectionDate' => 27, 'defaultPlannedEndDate' => 28, 'defaultEndDate' => 29, 'defaultExecPersonId' => 30, 'defaultPersonInEvent' => 31, 'defaultPersonInEditor' => 32, 'maxOccursInEvent' => 33, 'showTime' => 34, 'isMes' => 35, 'nomenclativeServiceId' => 36, 'isPreferable' => 37, 'prescribedTypeId' => 38, 'sheduleId' => 39, 'isRequiredCoordination' => 40, 'isRequiredTissue' => 41, 'testTubeTypeId' => 42, 'jobTypeId' => 43, 'mnem' => 44, ),
        BasePeer::TYPE_STUDLYPHPNAME => array ('id' => 0, 'createDatetime' => 1, 'createPersonId' => 2, 'modifyDatetime' => 3, 'modifyPersonId' => 4, 'deleted' => 5, 'class' => 6, 'groupId' => 7, 'code' => 8, 'name' => 9, 'title' => 10, 'flatCode' => 11, 'sex' => 12, 'age' => 13, 'ageBu' => 14, 'ageBc' => 15, 'ageEu' => 16, 'ageEc' => 17, 'office' => 18, 'showInForm' => 19, 'genTimeTable' => 20, 'serviceId' => 21, 'quotaTypeId' => 22, 'context' => 23, 'amount' => 24, 'amountEvaluation' => 25, 'defaultStatus' => 26, 'defaultDirectionDate' => 27, 'defaultPlannedEndDate' => 28, 'defaultEndDate' => 29, 'defaultExecPersonId' => 30, 'defaultPersonInEvent' => 31, 'defaultPersonInEditor' => 32, 'maxOccursInEvent' => 33, 'showTime' => 34, 'isMes' => 35, 'nomenclativeServiceId' => 36, 'isPreferable' => 37, 'prescribedTypeId' => 38, 'sheduleId' => 39, 'isRequiredCoordination' => 40, 'isRequiredTissue' => 41, 'testTubeTypeId' => 42, 'jobTypeId' => 43, 'mnem' => 44, ),
        BasePeer::TYPE_COLNAME => array (ActionTypePeer::ID => 0, ActionTypePeer::CREATEDATETIME => 1, ActionTypePeer::CREATEPERSON_ID => 2, ActionTypePeer::MODIFYDATETIME => 3, ActionTypePeer::MODIFYPERSON_ID => 4, ActionTypePeer::DELETED => 5, ActionTypePeer::CLAZZ => 6, ActionTypePeer::GROUP_ID => 7, ActionTypePeer::CODE => 8, ActionTypePeer::NAME => 9, ActionTypePeer::TITLE => 10, ActionTypePeer::FLATCODE => 11, ActionTypePeer::SEX => 12, ActionTypePeer::AGE => 13, ActionTypePeer::AGE_BU => 14, ActionTypePeer::AGE_BC => 15, ActionTypePeer::AGE_EU => 16, ActionTypePeer::AGE_EC => 17, ActionTypePeer::OFFICE => 18, ActionTypePeer::SHOWINFORM => 19, ActionTypePeer::GENTIMETABLE => 20, ActionTypePeer::SERVICE_ID => 21, ActionTypePeer::QUOTATYPE_ID => 22, ActionTypePeer::CONTEXT => 23, ActionTypePeer::AMOUNT => 24, ActionTypePeer::AMOUNTEVALUATION => 25, ActionTypePeer::DEFAULTSTATUS => 26, ActionTypePeer::DEFAULTDIRECTIONDATE => 27, ActionTypePeer::DEFAULTPLANNEDENDDATE => 28, ActionTypePeer::DEFAULTENDDATE => 29, ActionTypePeer::DEFAULTEXECPERSON_ID => 30, ActionTypePeer::DEFAULTPERSONINEVENT => 31, ActionTypePeer::DEFAULTPERSONINEDITOR => 32, ActionTypePeer::MAXOCCURSINEVENT => 33, ActionTypePeer::SHOWTIME => 34, ActionTypePeer::ISMES => 35, ActionTypePeer::NOMENCLATIVESERVICE_ID => 36, ActionTypePeer::ISPREFERABLE => 37, ActionTypePeer::PRESCRIBEDTYPE_ID => 38, ActionTypePeer::SHEDULE_ID => 39, ActionTypePeer::ISREQUIREDCOORDINATION => 40, ActionTypePeer::ISREQUIREDTISSUE => 41, ActionTypePeer::TESTTUBETYPE_ID => 42, ActionTypePeer::JOBTYPE_ID => 43, ActionTypePeer::MNEM => 44, ),
        BasePeer::TYPE_RAW_COLNAME => array ('ID' => 0, 'CREATEDATETIME' => 1, 'CREATEPERSON_ID' => 2, 'MODIFYDATETIME' => 3, 'MODIFYPERSON_ID' => 4, 'DELETED' => 5, 'CLAZZ' => 6, 'GROUP_ID' => 7, 'CODE' => 8, 'NAME' => 9, 'TITLE' => 10, 'FLATCODE' => 11, 'SEX' => 12, 'AGE' => 13, 'AGE_BU' => 14, 'AGE_BC' => 15, 'AGE_EU' => 16, 'AGE_EC' => 17, 'OFFICE' => 18, 'SHOWINFORM' => 19, 'GENTIMETABLE' => 20, 'SERVICE_ID' => 21, 'QUOTATYPE_ID' => 22, 'CONTEXT' => 23, 'AMOUNT' => 24, 'AMOUNTEVALUATION' => 25, 'DEFAULTSTATUS' => 26, 'DEFAULTDIRECTIONDATE' => 27, 'DEFAULTPLANNEDENDDATE' => 28, 'DEFAULTENDDATE' => 29, 'DEFAULTEXECPERSON_ID' => 30, 'DEFAULTPERSONINEVENT' => 31, 'DEFAULTPERSONINEDITOR' => 32, 'MAXOCCURSINEVENT' => 33, 'SHOWTIME' => 34, 'ISMES' => 35, 'NOMENCLATIVESERVICE_ID' => 36, 'ISPREFERABLE' => 37, 'PRESCRIBEDTYPE_ID' => 38, 'SHEDULE_ID' => 39, 'ISREQUIREDCOORDINATION' => 40, 'ISREQUIREDTISSUE' => 41, 'TESTTUBETYPE_ID' => 42, 'JOBTYPE_ID' => 43, 'MNEM' => 44, ),
        BasePeer::TYPE_FIELDNAME => array ('id' => 0, 'createDatetime' => 1, 'createPerson_id' => 2, 'modifyDatetime' => 3, 'modifyPerson_id' => 4, 'deleted' => 5, 'class' => 6, 'group_id' => 7, 'code' => 8, 'name' => 9, 'title' => 10, 'flatCode' => 11, 'sex' => 12, 'age' => 13, 'age_bu' => 14, 'age_bc' => 15, 'age_eu' => 16, 'age_ec' => 17, 'office' => 18, 'showInForm' => 19, 'genTimetable' => 20, 'service_id' => 21, 'quotaType_id' => 22, 'context' => 23, 'amount' => 24, 'amountEvaluation' => 25, 'defaultStatus' => 26, 'defaultDirectionDate' => 27, 'defaultPlannedEndDate' => 28, 'defaultEndDate' => 29, 'defaultExecPerson_id' => 30, 'defaultPersonInEvent' => 31, 'defaultPersonInEditor' => 32, 'maxOccursInEvent' => 33, 'showTime' => 34, 'isMES' => 35, 'nomenclativeService_id' => 36, 'isPreferable' => 37, 'prescribedType_id' => 38, 'shedule_id' => 39, 'isRequiredCoordination' => 40, 'isRequiredTissue' => 41, 'testTubeType_id' => 42, 'jobType_id' => 43, 'mnem' => 44, ),
        BasePeer::TYPE_NUM => array (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, )
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
        $toNames = ActionTypePeer::getFieldNames($toType);
        $key = isset(ActionTypePeer::$fieldKeys[$fromType][$name]) ? ActionTypePeer::$fieldKeys[$fromType][$name] : null;
        if ($key === null) {
            throw new PropelException("'$name' could not be found in the field names of type '$fromType'. These are: " . print_r(ActionTypePeer::$fieldKeys[$fromType], true));
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
        if (!array_key_exists($type, ActionTypePeer::$fieldNames)) {
            throw new PropelException('Method getFieldNames() expects the parameter $type to be one of the class constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME, BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM. ' . $type . ' was given.');
        }

        return ActionTypePeer::$fieldNames[$type];
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
     * @param      string $column The column name for current table. (i.e. ActionTypePeer::COLUMN_NAME).
     * @return string
     */
    public static function alias($alias, $column)
    {
        return str_replace(ActionTypePeer::TABLE_NAME.'.', $alias.'.', $column);
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
            $criteria->addSelectColumn(ActionTypePeer::ID);
            $criteria->addSelectColumn(ActionTypePeer::CREATEDATETIME);
            $criteria->addSelectColumn(ActionTypePeer::CREATEPERSON_ID);
            $criteria->addSelectColumn(ActionTypePeer::MODIFYDATETIME);
            $criteria->addSelectColumn(ActionTypePeer::MODIFYPERSON_ID);
            $criteria->addSelectColumn(ActionTypePeer::DELETED);
            $criteria->addSelectColumn(ActionTypePeer::CLAZZ);
            $criteria->addSelectColumn(ActionTypePeer::GROUP_ID);
            $criteria->addSelectColumn(ActionTypePeer::CODE);
            $criteria->addSelectColumn(ActionTypePeer::NAME);
            $criteria->addSelectColumn(ActionTypePeer::TITLE);
            $criteria->addSelectColumn(ActionTypePeer::FLATCODE);
            $criteria->addSelectColumn(ActionTypePeer::SEX);
            $criteria->addSelectColumn(ActionTypePeer::AGE);
            $criteria->addSelectColumn(ActionTypePeer::AGE_BU);
            $criteria->addSelectColumn(ActionTypePeer::AGE_BC);
            $criteria->addSelectColumn(ActionTypePeer::AGE_EU);
            $criteria->addSelectColumn(ActionTypePeer::AGE_EC);
            $criteria->addSelectColumn(ActionTypePeer::OFFICE);
            $criteria->addSelectColumn(ActionTypePeer::SHOWINFORM);
            $criteria->addSelectColumn(ActionTypePeer::GENTIMETABLE);
            $criteria->addSelectColumn(ActionTypePeer::SERVICE_ID);
            $criteria->addSelectColumn(ActionTypePeer::QUOTATYPE_ID);
            $criteria->addSelectColumn(ActionTypePeer::CONTEXT);
            $criteria->addSelectColumn(ActionTypePeer::AMOUNT);
            $criteria->addSelectColumn(ActionTypePeer::AMOUNTEVALUATION);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTSTATUS);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTDIRECTIONDATE);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTPLANNEDENDDATE);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTENDDATE);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTEXECPERSON_ID);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTPERSONINEVENT);
            $criteria->addSelectColumn(ActionTypePeer::DEFAULTPERSONINEDITOR);
            $criteria->addSelectColumn(ActionTypePeer::MAXOCCURSINEVENT);
            $criteria->addSelectColumn(ActionTypePeer::SHOWTIME);
            $criteria->addSelectColumn(ActionTypePeer::ISMES);
            $criteria->addSelectColumn(ActionTypePeer::NOMENCLATIVESERVICE_ID);
            $criteria->addSelectColumn(ActionTypePeer::ISPREFERABLE);
            $criteria->addSelectColumn(ActionTypePeer::PRESCRIBEDTYPE_ID);
            $criteria->addSelectColumn(ActionTypePeer::SHEDULE_ID);
            $criteria->addSelectColumn(ActionTypePeer::ISREQUIREDCOORDINATION);
            $criteria->addSelectColumn(ActionTypePeer::ISREQUIREDTISSUE);
            $criteria->addSelectColumn(ActionTypePeer::TESTTUBETYPE_ID);
            $criteria->addSelectColumn(ActionTypePeer::JOBTYPE_ID);
            $criteria->addSelectColumn(ActionTypePeer::MNEM);
        } else {
            $criteria->addSelectColumn($alias . '.id');
            $criteria->addSelectColumn($alias . '.createDatetime');
            $criteria->addSelectColumn($alias . '.createPerson_id');
            $criteria->addSelectColumn($alias . '.modifyDatetime');
            $criteria->addSelectColumn($alias . '.modifyPerson_id');
            $criteria->addSelectColumn($alias . '.deleted');
            $criteria->addSelectColumn($alias . '.class');
            $criteria->addSelectColumn($alias . '.group_id');
            $criteria->addSelectColumn($alias . '.code');
            $criteria->addSelectColumn($alias . '.name');
            $criteria->addSelectColumn($alias . '.title');
            $criteria->addSelectColumn($alias . '.flatCode');
            $criteria->addSelectColumn($alias . '.sex');
            $criteria->addSelectColumn($alias . '.age');
            $criteria->addSelectColumn($alias . '.age_bu');
            $criteria->addSelectColumn($alias . '.age_bc');
            $criteria->addSelectColumn($alias . '.age_eu');
            $criteria->addSelectColumn($alias . '.age_ec');
            $criteria->addSelectColumn($alias . '.office');
            $criteria->addSelectColumn($alias . '.showInForm');
            $criteria->addSelectColumn($alias . '.genTimetable');
            $criteria->addSelectColumn($alias . '.service_id');
            $criteria->addSelectColumn($alias . '.quotaType_id');
            $criteria->addSelectColumn($alias . '.context');
            $criteria->addSelectColumn($alias . '.amount');
            $criteria->addSelectColumn($alias . '.amountEvaluation');
            $criteria->addSelectColumn($alias . '.defaultStatus');
            $criteria->addSelectColumn($alias . '.defaultDirectionDate');
            $criteria->addSelectColumn($alias . '.defaultPlannedEndDate');
            $criteria->addSelectColumn($alias . '.defaultEndDate');
            $criteria->addSelectColumn($alias . '.defaultExecPerson_id');
            $criteria->addSelectColumn($alias . '.defaultPersonInEvent');
            $criteria->addSelectColumn($alias . '.defaultPersonInEditor');
            $criteria->addSelectColumn($alias . '.maxOccursInEvent');
            $criteria->addSelectColumn($alias . '.showTime');
            $criteria->addSelectColumn($alias . '.isMES');
            $criteria->addSelectColumn($alias . '.nomenclativeService_id');
            $criteria->addSelectColumn($alias . '.isPreferable');
            $criteria->addSelectColumn($alias . '.prescribedType_id');
            $criteria->addSelectColumn($alias . '.shedule_id');
            $criteria->addSelectColumn($alias . '.isRequiredCoordination');
            $criteria->addSelectColumn($alias . '.isRequiredTissue');
            $criteria->addSelectColumn($alias . '.testTubeType_id');
            $criteria->addSelectColumn($alias . '.jobType_id');
            $criteria->addSelectColumn($alias . '.mnem');
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
        $criteria->setPrimaryTableName(ActionTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME); // Set the correct dbName

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 ActionType
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectOne(Criteria $criteria, PropelPDO $con = null)
    {
        $critcopy = clone $criteria;
        $critcopy->setLimit(1);
        $objects = ActionTypePeer::doSelect($critcopy, $con);
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
        return ActionTypePeer::populateObjects(ActionTypePeer::doSelectStmt($criteria, $con));
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
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        if (!$criteria->hasSelectClause()) {
            $criteria = clone $criteria;
            ActionTypePeer::addSelectColumns($criteria);
        }

        // Set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

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
     * @param      ActionType $obj A ActionType object.
     * @param      string $key (optional) key to use for instance map (for performance boost if key was already calculated externally).
     */
    public static function addInstanceToPool($obj, $key = null)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if ($key === null) {
                $key = (string) $obj->getid();
            } // if key === null
            ActionTypePeer::$instances[$key] = $obj;
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
     * @param      mixed $value A ActionType object or a primary key value.
     *
     * @return void
     * @throws PropelException - if the value is invalid.
     */
    public static function removeInstanceFromPool($value)
    {
        if (Propel::isInstancePoolingEnabled() && $value !== null) {
            if (is_object($value) && $value instanceof ActionType) {
                $key = (string) $value->getid();
            } elseif (is_scalar($value)) {
                // assume we've been passed a primary key
                $key = (string) $value;
            } else {
                $e = new PropelException("Invalid value passed to removeInstanceFromPool().  Expected primary key or ActionType object; got " . (is_object($value) ? get_class($value) . ' object.' : var_export($value,true)));
                throw $e;
            }

            unset(ActionTypePeer::$instances[$key]);
        }
    } // removeInstanceFromPool()

    /**
     * Retrieves a string version of the primary key from the DB resultset row that can be used to uniquely identify a row in this table.
     *
     * For tables with a single-column primary key, that simple pkey value will be returned.  For tables with
     * a multi-column primary key, a serialize()d version of the primary key will be returned.
     *
     * @param      string $key The key (@see getPrimaryKeyHash()) for this instance.
     * @return   ActionType Found object or null if 1) no instance exists for specified key or 2) instance pooling has been disabled.
     * @see        getPrimaryKeyHash()
     */
    public static function getInstanceFromPool($key)
    {
        if (Propel::isInstancePoolingEnabled()) {
            if (isset(ActionTypePeer::$instances[$key])) {
                return ActionTypePeer::$instances[$key];
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
        foreach (ActionTypePeer::$instances as $instance)
        {
          $instance->clearAllReferences(true);
        }
      }
        ActionTypePeer::$instances = array();
    }

    /**
     * Method to invalidate the instance pool of all tables related to ActionType
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
        $cls = ActionTypePeer::getOMClass();
        // populate the object(s)
        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key = ActionTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj = ActionTypePeer::getInstanceFromPool($key))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj->hydrate($row, 0, true); // rehydrate
                $results[] = $obj;
            } else {
                $obj = new $cls();
                $obj->hydrate($row);
                $results[] = $obj;
                ActionTypePeer::addInstanceToPool($obj, $key);
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
     * @return array (ActionType object, last column rank)
     */
    public static function populateObject($row, $startcol = 0)
    {
        $key = ActionTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
        if (null !== ($obj = ActionTypePeer::getInstanceFromPool($key))) {
            // We no longer rehydrate the object, since this can cause data loss.
            // See http://www.propelorm.org/ticket/509
            // $obj->hydrate($row, $startcol, true); // rehydrate
            $col = $startcol + ActionTypePeer::NUM_HYDRATE_COLUMNS;
        } else {
            $cls = ActionTypePeer::OM_CLASS;
            $obj = new $cls();
            $col = $obj->hydrate($row, $startcol);
            ActionTypePeer::addInstanceToPool($obj, $key);
        }

        return array($obj, $col);
    }


    /**
     * Returns the number of rows matching criteria, joining the related ActionPropertyTypeRelatedByid table
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinActionPropertyTypeRelatedByid(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionTypePeer::ID, ActionPropertyTypePeer::ACTIONTYPE_ID, $join_behavior);

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
     * Selects a collection of ActionType objects pre-filled with their ActionPropertyType objects.
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinActionPropertyTypeRelatedByid(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionTypePeer::DATABASE_NAME);
        }

        ActionTypePeer::addSelectColumns($criteria);
        $startcol = ActionTypePeer::NUM_HYDRATE_COLUMNS;
        ActionPropertyTypePeer::addSelectColumns($criteria);

        $criteria->addJoin(ActionTypePeer::ID, ActionPropertyTypePeer::ACTIONTYPE_ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {

                $cls = ActionTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionTypePeer::addInstanceToPool($obj1, $key1);
            } // if $obj1 already loaded

            $key2 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol);
            if ($key2 !== null) {
                $obj2 = ActionPropertyTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol);
                    ActionPropertyTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 already loaded

                // Add the $obj1 (ActionType) to $obj2 (ActionPropertyType)
                // one to one relationship
                $obj1->setActionPropertyType($obj2);

            } // if joined row was not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
    }


    /**
     * Returns the number of rows matching criteria, joining all related tables
     *
     * @param      Criteria $criteria
     * @param      boolean $distinct Whether to select only distinct columns; deprecated: use Criteria->setDistinct() instead.
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return int Number of matching rows.
     */
    public static function doCountJoinAll(Criteria $criteria, $distinct = false, PropelPDO $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        // we're going to modify criteria, so copy it first
        $criteria = clone $criteria;

        // We need to set the primary table name, since in the case that there are no WHERE columns
        // it will be impossible for the BasePeer::createSelectSql() method to determine which
        // tables go into the FROM clause.
        $criteria->setPrimaryTableName(ActionTypePeer::TABLE_NAME);

        if ($distinct && !in_array(Criteria::DISTINCT, $criteria->getSelectModifiers())) {
            $criteria->setDistinct();
        }

        if (!$criteria->hasSelectClause()) {
            ActionTypePeer::addSelectColumns($criteria);
        }

        $criteria->clearOrderByColumns(); // ORDER BY won't ever affect the count

        // Set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria->addJoin(ActionTypePeer::ID, ActionPropertyTypePeer::ACTIONTYPE_ID, $join_behavior);

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
     * Selects a collection of ActionType objects pre-filled with all related objects.
     *
     * @param      Criteria  $criteria
     * @param      PropelPDO $con
     * @param      String    $join_behavior the type of joins to use, defaults to Criteria::LEFT_JOIN
     * @return array           Array of ActionType objects.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doSelectJoinAll(Criteria $criteria, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $criteria = clone $criteria;

        // Set the correct dbName if it has not been overridden
        if ($criteria->getDbName() == Propel::getDefaultDB()) {
            $criteria->setDbName(ActionTypePeer::DATABASE_NAME);
        }

        ActionTypePeer::addSelectColumns($criteria);
        $startcol2 = ActionTypePeer::NUM_HYDRATE_COLUMNS;

        ActionPropertyTypePeer::addSelectColumns($criteria);
        $startcol3 = $startcol2 + ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS;

        $criteria->addJoin(ActionTypePeer::ID, ActionPropertyTypePeer::ACTIONTYPE_ID, $join_behavior);

        $stmt = BasePeer::doSelect($criteria, $con);
        $results = array();

        while ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $key1 = ActionTypePeer::getPrimaryKeyHashFromRow($row, 0);
            if (null !== ($obj1 = ActionTypePeer::getInstanceFromPool($key1))) {
                // We no longer rehydrate the object, since this can cause data loss.
                // See http://www.propelorm.org/ticket/509
                // $obj1->hydrate($row, 0, true); // rehydrate
            } else {
                $cls = ActionTypePeer::getOMClass();

                $obj1 = new $cls();
                $obj1->hydrate($row);
                ActionTypePeer::addInstanceToPool($obj1, $key1);
            } // if obj1 already loaded

            // Add objects for joined ActionPropertyType rows

            $key2 = ActionPropertyTypePeer::getPrimaryKeyHashFromRow($row, $startcol2);
            if ($key2 !== null) {
                $obj2 = ActionPropertyTypePeer::getInstanceFromPool($key2);
                if (!$obj2) {

                    $cls = ActionPropertyTypePeer::getOMClass();

                    $obj2 = new $cls();
                    $obj2->hydrate($row, $startcol2);
                    ActionPropertyTypePeer::addInstanceToPool($obj2, $key2);
                } // if obj2 loaded

                // Add the $obj1 (ActionType) to the collection in $obj2 (ActionPropertyType)
                $obj1->setActionPropertyType($obj2);
            } // if joined row not null

            $results[] = $obj1;
        }
        $stmt->closeCursor();

        return $results;
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
        return Propel::getDatabaseMap(ActionTypePeer::DATABASE_NAME)->getTable(ActionTypePeer::TABLE_NAME);
    }

    /**
     * Add a TableMap instance to the database for this peer class.
     */
    public static function buildTableMap()
    {
      $dbMap = Propel::getDatabaseMap(BaseActionTypePeer::DATABASE_NAME);
      if (!$dbMap->hasTable(BaseActionTypePeer::TABLE_NAME)) {
        $dbMap->addTableObject(new ActionTypeTableMap());
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
        return ActionTypePeer::OM_CLASS;
    }

    /**
     * Performs an INSERT on the database, given a ActionType or Criteria object.
     *
     * @param      mixed $values Criteria or ActionType object containing data that is used to create the INSERT statement.
     * @param      PropelPDO $con the PropelPDO connection to use
     * @return mixed           The new primary key.
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doInsert($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity
        } else {
            $criteria = $values->buildCriteria(); // build Criteria from ActionType object
        }

        if ($criteria->containsKey(ActionTypePeer::ID) && $criteria->keyContainsValue(ActionTypePeer::ID) ) {
            throw new PropelException('Cannot insert a value for auto-increment primary key ('.ActionTypePeer::ID.')');
        }


        // Set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

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
     * Performs an UPDATE on the database, given a ActionType or Criteria object.
     *
     * @param      mixed $values Criteria or ActionType object containing data that is used to create the UPDATE statement.
     * @param      PropelPDO $con The connection to use (specify PropelPDO connection object to exert more control over transactions).
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function doUpdate($values, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $selectCriteria = new Criteria(ActionTypePeer::DATABASE_NAME);

        if ($values instanceof Criteria) {
            $criteria = clone $values; // rename for clarity

            $comparison = $criteria->getComparison(ActionTypePeer::ID);
            $value = $criteria->remove(ActionTypePeer::ID);
            if ($value) {
                $selectCriteria->add(ActionTypePeer::ID, $value, $comparison);
            } else {
                $selectCriteria->setPrimaryTableName(ActionTypePeer::TABLE_NAME);
            }

        } else { // $values is ActionType object
            $criteria = $values->buildCriteria(); // gets full criteria
            $selectCriteria = $values->buildPkeyCriteria(); // gets criteria w/ primary key(s)
        }

        // set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

        return BasePeer::doUpdate($selectCriteria, $criteria, $con);
    }

    /**
     * Deletes all rows from the ActionType table.
     *
     * @param      PropelPDO $con the connection to use
     * @return int             The number of affected rows (if supported by underlying database driver).
     * @throws PropelException
     */
    public static function doDeleteAll(PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }
        $affectedRows = 0; // initialize var to track total num of affected rows
        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();
            $affectedRows += BasePeer::doDeleteAll(ActionTypePeer::TABLE_NAME, $con, ActionTypePeer::DATABASE_NAME);
            // Because this db requires some delete cascade/set null emulation, we have to
            // clear the cached instance *after* the emulation has happened (since
            // instances get re-added by the select statement contained therein).
            ActionTypePeer::clearInstancePool();
            ActionTypePeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs a DELETE on the database, given a ActionType or Criteria object OR a primary key value.
     *
     * @param      mixed $values Criteria or ActionType object or primary key or array of primary keys
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
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        if ($values instanceof Criteria) {
            // invalidate the cache for all objects of this type, since we have no
            // way of knowing (without running a query) what objects should be invalidated
            // from the cache based on this Criteria.
            ActionTypePeer::clearInstancePool();
            // rename for clarity
            $criteria = clone $values;
        } elseif ($values instanceof ActionType) { // it's a model object
            // invalidate the cache for this single object
            ActionTypePeer::removeInstanceFromPool($values);
            // create criteria based on pk values
            $criteria = $values->buildPkeyCriteria();
        } else { // it's a primary key, or an array of pks
            $criteria = new Criteria(ActionTypePeer::DATABASE_NAME);
            $criteria->add(ActionTypePeer::ID, (array) $values, Criteria::IN);
            // invalidate the cache for this object(s)
            foreach ((array) $values as $singleval) {
                ActionTypePeer::removeInstanceFromPool($singleval);
            }
        }

        // Set the correct dbName
        $criteria->setDbName(ActionTypePeer::DATABASE_NAME);

        $affectedRows = 0; // initialize var to track total num of affected rows

        try {
            // use transaction because $criteria could contain info
            // for more than one table or we could emulating ON DELETE CASCADE, etc.
            $con->beginTransaction();

            $affectedRows += BasePeer::doDelete($criteria, $con);
            ActionTypePeer::clearRelatedInstancePool();
            $con->commit();

            return $affectedRows;
        } catch (PropelException $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Validates all modified columns of given ActionType object.
     * If parameter $columns is either a single column name or an array of column names
     * than only those columns are validated.
     *
     * NOTICE: This does not apply to primary or foreign keys for now.
     *
     * @param      ActionType $obj The object to validate.
     * @param      mixed $cols Column name or array of column names.
     *
     * @return mixed TRUE if all columns are valid or the error message of the first invalid column.
     */
    public static function doValidate($obj, $cols = null)
    {
        $columns = array();

        if ($cols) {
            $dbMap = Propel::getDatabaseMap(ActionTypePeer::DATABASE_NAME);
            $tableMap = $dbMap->getTable(ActionTypePeer::TABLE_NAME);

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

        return BasePeer::doValidate(ActionTypePeer::DATABASE_NAME, ActionTypePeer::TABLE_NAME, $columns);
    }

    /**
     * Retrieve a single object by pkey.
     *
     * @param      int $pk the primary key.
     * @param      PropelPDO $con the connection to use
     * @return ActionType
     */
    public static function retrieveByPK($pk, PropelPDO $con = null)
    {

        if (null !== ($obj = ActionTypePeer::getInstanceFromPool((string) $pk))) {
            return $obj;
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $criteria = new Criteria(ActionTypePeer::DATABASE_NAME);
        $criteria->add(ActionTypePeer::ID, $pk);

        $v = ActionTypePeer::doSelect($criteria, $con);

        return !empty($v) > 0 ? $v[0] : null;
    }

    /**
     * Retrieve multiple objects by pkey.
     *
     * @param      array $pks List of primary keys
     * @param      PropelPDO $con the connection to use
     * @return ActionType[]
     * @throws PropelException Any exceptions caught during processing will be
     *		 rethrown wrapped into a PropelException.
     */
    public static function retrieveByPKs($pks, PropelPDO $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        $objs = null;
        if (empty($pks)) {
            $objs = array();
        } else {
            $criteria = new Criteria(ActionTypePeer::DATABASE_NAME);
            $criteria->add(ActionTypePeer::ID, $pks, Criteria::IN);
            $objs = ActionTypePeer::doSelect($criteria, $con);
        }

        return $objs;
    }

} // BaseActionTypePeer

// This is the static code needed to register the TableMap for this table with the main Propel class.
//
BaseActionTypePeer::buildTableMap();

