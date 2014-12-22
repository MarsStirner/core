<?php

namespace Webmis\Models\om;

use \BaseObject;
use \BasePeer;
use \Criteria;
use \DateTime;
use \Exception;
use \PDO;
use \Persistent;
use \Propel;
use \PropelCollection;
use \PropelDateTime;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\Action;
use Webmis\Models\ActionPropertyType;
use Webmis\Models\ActionPropertyTypeQuery;
use Webmis\Models\ActionQuery;
use Webmis\Models\ActionType;
use Webmis\Models\ActionTypePeer;
use Webmis\Models\ActionTypeQuery;

/**
 * Base class that represents a row from the 'ActionType' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionType extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\ActionTypePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        ActionTypePeer
     */
    protected static $peer;

    /**
     * The flag var to prevent infinit loop in deep copy
     * @var       boolean
     */
    protected $startCopy = false;

    /**
     * The value for the id field.
     * @var        int
     */
    protected $id;

    /**
     * The value for the createdatetime field.
     * @var        string
     */
    protected $createdatetime;

    /**
     * The value for the createperson_id field.
     * @var        int
     */
    protected $createperson_id;

    /**
     * The value for the modifydatetime field.
     * @var        string
     */
    protected $modifydatetime;

    /**
     * The value for the modifyperson_id field.
     * @var        int
     */
    protected $modifyperson_id;

    /**
     * The value for the deleted field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $deleted;

    /**
     * The value for the class field.
     * @var        boolean
     */
    protected $class;

    /**
     * The value for the group_id field.
     * @var        int
     */
    protected $group_id;

    /**
     * The value for the code field.
     * @var        string
     */
    protected $code;

    /**
     * The value for the name field.
     * @var        string
     */
    protected $name;

    /**
     * The value for the title field.
     * @var        string
     */
    protected $title;

    /**
     * The value for the flatcode field.
     * @var        string
     */
    protected $flatcode;

    /**
     * The value for the sex field.
     * @var        int
     */
    protected $sex;

    /**
     * The value for the age field.
     * @var        string
     */
    protected $age;

    /**
     * The value for the age_bu field.
     * @var        int
     */
    protected $age_bu;

    /**
     * The value for the age_bc field.
     * @var        int
     */
    protected $age_bc;

    /**
     * The value for the age_eu field.
     * @var        int
     */
    protected $age_eu;

    /**
     * The value for the age_ec field.
     * @var        int
     */
    protected $age_ec;

    /**
     * The value for the office field.
     * @var        string
     */
    protected $office;

    /**
     * The value for the showinform field.
     * @var        boolean
     */
    protected $showinform;

    /**
     * The value for the gentimetable field.
     * @var        boolean
     */
    protected $gentimetable;

    /**
     * The value for the service_id field.
     * @var        int
     */
    protected $service_id;

    /**
     * The value for the quotatype_id field.
     * @var        int
     */
    protected $quotatype_id;

    /**
     * The value for the context field.
     * @var        string
     */
    protected $context;

    /**
     * The value for the amount field.
     * Note: this column has a database default value of: 1
     * @var        double
     */
    protected $amount;

    /**
     * The value for the amountevaluation field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $amountevaluation;

    /**
     * The value for the defaultstatus field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $defaultstatus;

    /**
     * The value for the defaultdirectiondate field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $defaultdirectiondate;

    /**
     * The value for the defaultplannedenddate field.
     * @var        boolean
     */
    protected $defaultplannedenddate;

    /**
     * The value for the defaultenddate field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $defaultenddate;

    /**
     * The value for the defaultexecperson_id field.
     * @var        int
     */
    protected $defaultexecperson_id;

    /**
     * The value for the defaultpersoninevent field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $defaultpersoninevent;

    /**
     * The value for the defaultpersonineditor field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $defaultpersonineditor;

    /**
     * The value for the maxoccursinevent field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $maxoccursinevent;

    /**
     * The value for the showtime field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $showtime;

    /**
     * The value for the ismes field.
     * @var        int
     */
    protected $ismes;

    /**
     * The value for the nomenclativeservice_id field.
     * @var        int
     */
    protected $nomenclativeservice_id;

    /**
     * The value for the ispreferable field.
     * Note: this column has a database default value of: true
     * @var        boolean
     */
    protected $ispreferable;

    /**
     * The value for the prescribedtype_id field.
     * @var        int
     */
    protected $prescribedtype_id;

    /**
     * The value for the shedule_id field.
     * @var        int
     */
    protected $shedule_id;

    /**
     * The value for the isrequiredcoordination field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isrequiredcoordination;

    /**
     * The value for the isrequiredtissue field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isrequiredtissue;

    /**
     * The value for the testtubetype_id field.
     * @var        int
     */
    protected $testtubetype_id;

    /**
     * The value for the jobtype_id field.
     * @var        int
     */
    protected $jobtype_id;

    /**
     * The value for the mnem field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $mnem;

    /**
     * @var        ActionPropertyType
     */
    protected $aActionPropertyTypeRelatedByid;

    /**
     * @var        PropelObjectCollection|Action[] Collection to store aggregation of Action objects.
     */
    protected $collActions;
    protected $collActionsPartial;

    /**
     * @var        PropelObjectCollection|ActionPropertyType[] Collection to store aggregation of ActionPropertyType objects.
     */
    protected $collActionPropertyTypesRelatedByactionTypeId;
    protected $collActionPropertyTypesRelatedByactionTypeIdPartial;

    /**
     * Flag to prevent endless save loop, if this object is referenced
     * by another object which falls in this transaction.
     * @var        boolean
     */
    protected $alreadyInSave = false;

    /**
     * Flag to prevent endless validation loop, if this object is referenced
     * by another object which falls in this transaction.
     * @var        boolean
     */
    protected $alreadyInValidation = false;

    /**
     * Flag to prevent endless clearAllReferences($deep=true) loop, if this object is referenced
     * @var        boolean
     */
    protected $alreadyInClearAllReferencesDeep = false;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $actionsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->amount = 1;
        $this->amountevaluation = 0;
        $this->defaultstatus = 0;
        $this->defaultdirectiondate = 0;
        $this->defaultenddate = 0;
        $this->defaultpersoninevent = 0;
        $this->defaultpersonineditor = 0;
        $this->maxoccursinevent = 0;
        $this->showtime = false;
        $this->ispreferable = true;
        $this->isrequiredcoordination = false;
        $this->isrequiredtissue = false;
        $this->mnem = '';
    }

    /**
     * Initializes internal state of BaseActionType object.
     * @see        applyDefaults()
     */
    public function __construct()
    {
        parent::__construct();
        $this->applyDefaultValues();
    }

    /**
     * Get the [id] column value.
     *
     * @return int
     */
    public function getid()
    {
        return $this->id;
    }

    /**
     * Get the [optionally formatted] temporal [createdatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getcreateDatetime($format = 'Y-m-d H:i:s')
    {
        if ($this->createdatetime === null) {
            return null;
        }

        if ($this->createdatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->createdatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->createdatetime, true), $x);
        }

        if ($format === null) {
            // Because propel.useDateTimeClass is true, we return a DateTime object.
            return $dt;
        }

        if (strpos($format, '%') !== false) {
            return strftime($format, $dt->format('U'));
        }

        return $dt->format($format);

    }

    /**
     * Get the [createperson_id] column value.
     *
     * @return int
     */
    public function getcreatePersonId()
    {
        return $this->createperson_id;
    }

    /**
     * Get the [optionally formatted] temporal [modifydatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getmodifyDatetime($format = 'Y-m-d H:i:s')
    {
        if ($this->modifydatetime === null) {
            return null;
        }

        if ($this->modifydatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->modifydatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->modifydatetime, true), $x);
        }

        if ($format === null) {
            // Because propel.useDateTimeClass is true, we return a DateTime object.
            return $dt;
        }

        if (strpos($format, '%') !== false) {
            return strftime($format, $dt->format('U'));
        }

        return $dt->format($format);

    }

    /**
     * Get the [modifyperson_id] column value.
     *
     * @return int
     */
    public function getmodifyPersonId()
    {
        return $this->modifyperson_id;
    }

    /**
     * Get the [deleted] column value.
     *
     * @return boolean
     */
    public function getdeleted()
    {
        return $this->deleted;
    }

    /**
     * Get the [class] column value.
     *
     * @return boolean
     */
    public function getClass()
    {
        return $this->class;
    }

    /**
     * Get the [group_id] column value.
     *
     * @return int
     */
    public function getgroupId()
    {
        return $this->group_id;
    }

    /**
     * Get the [code] column value.
     *
     * @return string
     */
    public function getcode()
    {
        return $this->code;
    }

    /**
     * Get the [name] column value.
     *
     * @return string
     */
    public function getname()
    {
        return $this->name;
    }

    /**
     * Get the [title] column value.
     *
     * @return string
     */
    public function gettitle()
    {
        return $this->title;
    }

    /**
     * Get the [flatcode] column value.
     *
     * @return string
     */
    public function getflatCode()
    {
        return $this->flatcode;
    }

    /**
     * Get the [sex] column value.
     *
     * @return int
     */
    public function getsex()
    {
        return $this->sex;
    }

    /**
     * Get the [age] column value.
     *
     * @return string
     */
    public function getage()
    {
        return $this->age;
    }

    /**
     * Get the [age_bu] column value.
     *
     * @return int
     */
    public function getageBu()
    {
        return $this->age_bu;
    }

    /**
     * Get the [age_bc] column value.
     *
     * @return int
     */
    public function getageBc()
    {
        return $this->age_bc;
    }

    /**
     * Get the [age_eu] column value.
     *
     * @return int
     */
    public function getageEu()
    {
        return $this->age_eu;
    }

    /**
     * Get the [age_ec] column value.
     *
     * @return int
     */
    public function getageEc()
    {
        return $this->age_ec;
    }

    /**
     * Get the [office] column value.
     *
     * @return string
     */
    public function getoffice()
    {
        return $this->office;
    }

    /**
     * Get the [showinform] column value.
     *
     * @return boolean
     */
    public function getshowInForm()
    {
        return $this->showinform;
    }

    /**
     * Get the [gentimetable] column value.
     *
     * @return boolean
     */
    public function getgenTimeTable()
    {
        return $this->gentimetable;
    }

    /**
     * Get the [service_id] column value.
     *
     * @return int
     */
    public function getserviceId()
    {
        return $this->service_id;
    }

    /**
     * Get the [quotatype_id] column value.
     *
     * @return int
     */
    public function getquotaTypeId()
    {
        return $this->quotatype_id;
    }

    /**
     * Get the [context] column value.
     *
     * @return string
     */
    public function getcontext()
    {
        return $this->context;
    }

    /**
     * Get the [amount] column value.
     *
     * @return double
     */
    public function getamount()
    {
        return $this->amount;
    }

    /**
     * Get the [amountevaluation] column value.
     *
     * @return int
     */
    public function getamountEvaluation()
    {
        return $this->amountevaluation;
    }

    /**
     * Get the [defaultstatus] column value.
     *
     * @return int
     */
    public function getdefaultStatus()
    {
        return $this->defaultstatus;
    }

    /**
     * Get the [defaultdirectiondate] column value.
     *
     * @return int
     */
    public function getdefaultDirectionDate()
    {
        return $this->defaultdirectiondate;
    }

    /**
     * Get the [defaultplannedenddate] column value.
     *
     * @return boolean
     */
    public function getdefaultPlannedEndDate()
    {
        return $this->defaultplannedenddate;
    }

    /**
     * Get the [defaultenddate] column value.
     *
     * @return int
     */
    public function getdefaultEndDate()
    {
        return $this->defaultenddate;
    }

    /**
     * Get the [defaultexecperson_id] column value.
     *
     * @return int
     */
    public function getdefaultExecPersonId()
    {
        return $this->defaultexecperson_id;
    }

    /**
     * Get the [defaultpersoninevent] column value.
     *
     * @return int
     */
    public function getdefaultPersonInEvent()
    {
        return $this->defaultpersoninevent;
    }

    /**
     * Get the [defaultpersonineditor] column value.
     *
     * @return int
     */
    public function getdefaultPersonInEditor()
    {
        return $this->defaultpersonineditor;
    }

    /**
     * Get the [maxoccursinevent] column value.
     *
     * @return int
     */
    public function getmaxOccursInEvent()
    {
        return $this->maxoccursinevent;
    }

    /**
     * Get the [showtime] column value.
     *
     * @return boolean
     */
    public function getshowTime()
    {
        return $this->showtime;
    }

    /**
     * Get the [ismes] column value.
     *
     * @return int
     */
    public function getisMes()
    {
        return $this->ismes;
    }

    /**
     * Get the [nomenclativeservice_id] column value.
     *
     * @return int
     */
    public function getnomenclativeServiceId()
    {
        return $this->nomenclativeservice_id;
    }

    /**
     * Get the [ispreferable] column value.
     *
     * @return boolean
     */
    public function getisPreferable()
    {
        return $this->ispreferable;
    }

    /**
     * Get the [prescribedtype_id] column value.
     *
     * @return int
     */
    public function getprescribedTypeId()
    {
        return $this->prescribedtype_id;
    }

    /**
     * Get the [shedule_id] column value.
     *
     * @return int
     */
    public function getsheduleId()
    {
        return $this->shedule_id;
    }

    /**
     * Get the [isrequiredcoordination] column value.
     *
     * @return boolean
     */
    public function getisRequiredCoordination()
    {
        return $this->isrequiredcoordination;
    }

    /**
     * Get the [isrequiredtissue] column value.
     *
     * @return boolean
     */
    public function getisRequiredTissue()
    {
        return $this->isrequiredtissue;
    }

    /**
     * Get the [testtubetype_id] column value.
     *
     * @return int
     */
    public function gettestTubeTypeId()
    {
        return $this->testtubetype_id;
    }

    /**
     * Get the [jobtype_id] column value.
     *
     * @return int
     */
    public function getjobTypeId()
    {
        return $this->jobtype_id;
    }

    /**
     * Get the [mnem] column value.
     *
     * @return string
     */
    public function getmnem()
    {
        return $this->mnem;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = ActionTypePeer::ID;
        }

        if ($this->aActionPropertyTypeRelatedByid !== null && $this->aActionPropertyTypeRelatedByid->getactionTypeId() !== $v) {
            $this->aActionPropertyTypeRelatedByid = null;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ActionType The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionTypePeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ActionType The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionTypePeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::MODIFYPERSON_ID;
        }


        return $this;
    } // setmodifyPersonId()

    /**
     * Sets the value of the [deleted] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdeleted($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->deleted !== $v) {
            $this->deleted = $v;
            $this->modifiedColumns[] = ActionTypePeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Sets the value of the [class] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setClass($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->class !== $v) {
            $this->class = $v;
            $this->modifiedColumns[] = ActionTypePeer::CLAZZ;
        }


        return $this;
    } // setClass()

    /**
     * Set the value of [group_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setgroupId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->group_id !== $v) {
            $this->group_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::GROUP_ID;
        }


        return $this;
    } // setgroupId()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = ActionTypePeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = ActionTypePeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [title] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function settitle($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->title !== $v) {
            $this->title = $v;
            $this->modifiedColumns[] = ActionTypePeer::TITLE;
        }


        return $this;
    } // settitle()

    /**
     * Set the value of [flatcode] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setflatCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->flatcode !== $v) {
            $this->flatcode = $v;
            $this->modifiedColumns[] = ActionTypePeer::FLATCODE;
        }


        return $this;
    } // setflatCode()

    /**
     * Set the value of [sex] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setsex($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->sex !== $v) {
            $this->sex = $v;
            $this->modifiedColumns[] = ActionTypePeer::SEX;
        }


        return $this;
    } // setsex()

    /**
     * Set the value of [age] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setage($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->age !== $v) {
            $this->age = $v;
            $this->modifiedColumns[] = ActionTypePeer::AGE;
        }


        return $this;
    } // setage()

    /**
     * Set the value of [age_bu] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setageBu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bu !== $v) {
            $this->age_bu = $v;
            $this->modifiedColumns[] = ActionTypePeer::AGE_BU;
        }


        return $this;
    } // setageBu()

    /**
     * Set the value of [age_bc] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setageBc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bc !== $v) {
            $this->age_bc = $v;
            $this->modifiedColumns[] = ActionTypePeer::AGE_BC;
        }


        return $this;
    } // setageBc()

    /**
     * Set the value of [age_eu] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setageEu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_eu !== $v) {
            $this->age_eu = $v;
            $this->modifiedColumns[] = ActionTypePeer::AGE_EU;
        }


        return $this;
    } // setageEu()

    /**
     * Set the value of [age_ec] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setageEc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_ec !== $v) {
            $this->age_ec = $v;
            $this->modifiedColumns[] = ActionTypePeer::AGE_EC;
        }


        return $this;
    } // setageEc()

    /**
     * Set the value of [office] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setoffice($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->office !== $v) {
            $this->office = $v;
            $this->modifiedColumns[] = ActionTypePeer::OFFICE;
        }


        return $this;
    } // setoffice()

    /**
     * Sets the value of the [showinform] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setshowInForm($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showinform !== $v) {
            $this->showinform = $v;
            $this->modifiedColumns[] = ActionTypePeer::SHOWINFORM;
        }


        return $this;
    } // setshowInForm()

    /**
     * Sets the value of the [gentimetable] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setgenTimeTable($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->gentimetable !== $v) {
            $this->gentimetable = $v;
            $this->modifiedColumns[] = ActionTypePeer::GENTIMETABLE;
        }


        return $this;
    } // setgenTimeTable()

    /**
     * Set the value of [service_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setserviceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->service_id !== $v) {
            $this->service_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::SERVICE_ID;
        }


        return $this;
    } // setserviceId()

    /**
     * Set the value of [quotatype_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setquotaTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->quotatype_id !== $v) {
            $this->quotatype_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::QUOTATYPE_ID;
        }


        return $this;
    } // setquotaTypeId()

    /**
     * Set the value of [context] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setcontext($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->context !== $v) {
            $this->context = $v;
            $this->modifiedColumns[] = ActionTypePeer::CONTEXT;
        }


        return $this;
    } // setcontext()

    /**
     * Set the value of [amount] column.
     *
     * @param double $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setamount($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (double) $v;
        }

        if ($this->amount !== $v) {
            $this->amount = $v;
            $this->modifiedColumns[] = ActionTypePeer::AMOUNT;
        }


        return $this;
    } // setamount()

    /**
     * Set the value of [amountevaluation] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setamountEvaluation($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->amountevaluation !== $v) {
            $this->amountevaluation = $v;
            $this->modifiedColumns[] = ActionTypePeer::AMOUNTEVALUATION;
        }


        return $this;
    } // setamountEvaluation()

    /**
     * Set the value of [defaultstatus] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultStatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultstatus !== $v) {
            $this->defaultstatus = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTSTATUS;
        }


        return $this;
    } // setdefaultStatus()

    /**
     * Set the value of [defaultdirectiondate] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultDirectionDate($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultdirectiondate !== $v) {
            $this->defaultdirectiondate = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTDIRECTIONDATE;
        }


        return $this;
    } // setdefaultDirectionDate()

    /**
     * Sets the value of the [defaultplannedenddate] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultPlannedEndDate($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->defaultplannedenddate !== $v) {
            $this->defaultplannedenddate = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTPLANNEDENDDATE;
        }


        return $this;
    } // setdefaultPlannedEndDate()

    /**
     * Set the value of [defaultenddate] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultEndDate($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultenddate !== $v) {
            $this->defaultenddate = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTENDDATE;
        }


        return $this;
    } // setdefaultEndDate()

    /**
     * Set the value of [defaultexecperson_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultExecPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultexecperson_id !== $v) {
            $this->defaultexecperson_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTEXECPERSON_ID;
        }


        return $this;
    } // setdefaultExecPersonId()

    /**
     * Set the value of [defaultpersoninevent] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultPersonInEvent($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultpersoninevent !== $v) {
            $this->defaultpersoninevent = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTPERSONINEVENT;
        }


        return $this;
    } // setdefaultPersonInEvent()

    /**
     * Set the value of [defaultpersonineditor] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setdefaultPersonInEditor($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->defaultpersonineditor !== $v) {
            $this->defaultpersonineditor = $v;
            $this->modifiedColumns[] = ActionTypePeer::DEFAULTPERSONINEDITOR;
        }


        return $this;
    } // setdefaultPersonInEditor()

    /**
     * Set the value of [maxoccursinevent] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setmaxOccursInEvent($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->maxoccursinevent !== $v) {
            $this->maxoccursinevent = $v;
            $this->modifiedColumns[] = ActionTypePeer::MAXOCCURSINEVENT;
        }


        return $this;
    } // setmaxOccursInEvent()

    /**
     * Sets the value of the [showtime] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setshowTime($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showtime !== $v) {
            $this->showtime = $v;
            $this->modifiedColumns[] = ActionTypePeer::SHOWTIME;
        }


        return $this;
    } // setshowTime()

    /**
     * Set the value of [ismes] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setisMes($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->ismes !== $v) {
            $this->ismes = $v;
            $this->modifiedColumns[] = ActionTypePeer::ISMES;
        }


        return $this;
    } // setisMes()

    /**
     * Set the value of [nomenclativeservice_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setnomenclativeServiceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->nomenclativeservice_id !== $v) {
            $this->nomenclativeservice_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::NOMENCLATIVESERVICE_ID;
        }


        return $this;
    } // setnomenclativeServiceId()

    /**
     * Sets the value of the [ispreferable] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setisPreferable($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->ispreferable !== $v) {
            $this->ispreferable = $v;
            $this->modifiedColumns[] = ActionTypePeer::ISPREFERABLE;
        }


        return $this;
    } // setisPreferable()

    /**
     * Set the value of [prescribedtype_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setprescribedTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->prescribedtype_id !== $v) {
            $this->prescribedtype_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::PRESCRIBEDTYPE_ID;
        }


        return $this;
    } // setprescribedTypeId()

    /**
     * Set the value of [shedule_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setsheduleId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->shedule_id !== $v) {
            $this->shedule_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::SHEDULE_ID;
        }


        return $this;
    } // setsheduleId()

    /**
     * Sets the value of the [isrequiredcoordination] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setisRequiredCoordination($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isrequiredcoordination !== $v) {
            $this->isrequiredcoordination = $v;
            $this->modifiedColumns[] = ActionTypePeer::ISREQUIREDCOORDINATION;
        }


        return $this;
    } // setisRequiredCoordination()

    /**
     * Sets the value of the [isrequiredtissue] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setisRequiredTissue($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isrequiredtissue !== $v) {
            $this->isrequiredtissue = $v;
            $this->modifiedColumns[] = ActionTypePeer::ISREQUIREDTISSUE;
        }


        return $this;
    } // setisRequiredTissue()

    /**
     * Set the value of [testtubetype_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function settestTubeTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->testtubetype_id !== $v) {
            $this->testtubetype_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::TESTTUBETYPE_ID;
        }


        return $this;
    } // settestTubeTypeId()

    /**
     * Set the value of [jobtype_id] column.
     *
     * @param int $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setjobTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->jobtype_id !== $v) {
            $this->jobtype_id = $v;
            $this->modifiedColumns[] = ActionTypePeer::JOBTYPE_ID;
        }


        return $this;
    } // setjobTypeId()

    /**
     * Set the value of [mnem] column.
     *
     * @param string $v new value
     * @return ActionType The current object (for fluent API support)
     */
    public function setmnem($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->mnem !== $v) {
            $this->mnem = $v;
            $this->modifiedColumns[] = ActionTypePeer::MNEM;
        }


        return $this;
    } // setmnem()

    /**
     * Indicates whether the columns in this object are only set to default values.
     *
     * This method can be used in conjunction with isModified() to indicate whether an object is both
     * modified _and_ has some values set which are non-default.
     *
     * @return boolean Whether the columns in this object are only been set with default values.
     */
    public function hasOnlyDefaultValues()
    {
            if ($this->deleted !== false) {
                return false;
            }

            if ($this->amount !== 1) {
                return false;
            }

            if ($this->amountevaluation !== 0) {
                return false;
            }

            if ($this->defaultstatus !== 0) {
                return false;
            }

            if ($this->defaultdirectiondate !== 0) {
                return false;
            }

            if ($this->defaultenddate !== 0) {
                return false;
            }

            if ($this->defaultpersoninevent !== 0) {
                return false;
            }

            if ($this->defaultpersonineditor !== 0) {
                return false;
            }

            if ($this->maxoccursinevent !== 0) {
                return false;
            }

            if ($this->showtime !== false) {
                return false;
            }

            if ($this->ispreferable !== true) {
                return false;
            }

            if ($this->isrequiredcoordination !== false) {
                return false;
            }

            if ($this->isrequiredtissue !== false) {
                return false;
            }

            if ($this->mnem !== '') {
                return false;
            }

        // otherwise, everything was equal, so return true
        return true;
    } // hasOnlyDefaultValues()

    /**
     * Hydrates (populates) the object variables with values from the database resultset.
     *
     * An offset (0-based "start column") is specified so that objects can be hydrated
     * with a subset of the columns in the resultset rows.  This is needed, for example,
     * for results of JOIN queries where the resultset row includes columns from two or
     * more tables.
     *
     * @param array $row The row returned by PDOStatement->fetch(PDO::FETCH_NUM)
     * @param int $startcol 0-based offset column which indicates which restultset column to start with.
     * @param boolean $rehydrate Whether this object is being re-hydrated from the database.
     * @return int             next starting column
     * @throws PropelException - Any caught Exception will be rewrapped as a PropelException.
     */
    public function hydrate($row, $startcol = 0, $rehydrate = false)
    {
        try {

            $this->id = ($row[$startcol + 0] !== null) ? (int) $row[$startcol + 0] : null;
            $this->createdatetime = ($row[$startcol + 1] !== null) ? (string) $row[$startcol + 1] : null;
            $this->createperson_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->modifydatetime = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->modifyperson_id = ($row[$startcol + 4] !== null) ? (int) $row[$startcol + 4] : null;
            $this->deleted = ($row[$startcol + 5] !== null) ? (boolean) $row[$startcol + 5] : null;
            $this->class = ($row[$startcol + 6] !== null) ? (boolean) $row[$startcol + 6] : null;
            $this->group_id = ($row[$startcol + 7] !== null) ? (int) $row[$startcol + 7] : null;
            $this->code = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->name = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->title = ($row[$startcol + 10] !== null) ? (string) $row[$startcol + 10] : null;
            $this->flatcode = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->sex = ($row[$startcol + 12] !== null) ? (int) $row[$startcol + 12] : null;
            $this->age = ($row[$startcol + 13] !== null) ? (string) $row[$startcol + 13] : null;
            $this->age_bu = ($row[$startcol + 14] !== null) ? (int) $row[$startcol + 14] : null;
            $this->age_bc = ($row[$startcol + 15] !== null) ? (int) $row[$startcol + 15] : null;
            $this->age_eu = ($row[$startcol + 16] !== null) ? (int) $row[$startcol + 16] : null;
            $this->age_ec = ($row[$startcol + 17] !== null) ? (int) $row[$startcol + 17] : null;
            $this->office = ($row[$startcol + 18] !== null) ? (string) $row[$startcol + 18] : null;
            $this->showinform = ($row[$startcol + 19] !== null) ? (boolean) $row[$startcol + 19] : null;
            $this->gentimetable = ($row[$startcol + 20] !== null) ? (boolean) $row[$startcol + 20] : null;
            $this->service_id = ($row[$startcol + 21] !== null) ? (int) $row[$startcol + 21] : null;
            $this->quotatype_id = ($row[$startcol + 22] !== null) ? (int) $row[$startcol + 22] : null;
            $this->context = ($row[$startcol + 23] !== null) ? (string) $row[$startcol + 23] : null;
            $this->amount = ($row[$startcol + 24] !== null) ? (double) $row[$startcol + 24] : null;
            $this->amountevaluation = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->defaultstatus = ($row[$startcol + 26] !== null) ? (int) $row[$startcol + 26] : null;
            $this->defaultdirectiondate = ($row[$startcol + 27] !== null) ? (int) $row[$startcol + 27] : null;
            $this->defaultplannedenddate = ($row[$startcol + 28] !== null) ? (boolean) $row[$startcol + 28] : null;
            $this->defaultenddate = ($row[$startcol + 29] !== null) ? (int) $row[$startcol + 29] : null;
            $this->defaultexecperson_id = ($row[$startcol + 30] !== null) ? (int) $row[$startcol + 30] : null;
            $this->defaultpersoninevent = ($row[$startcol + 31] !== null) ? (int) $row[$startcol + 31] : null;
            $this->defaultpersonineditor = ($row[$startcol + 32] !== null) ? (int) $row[$startcol + 32] : null;
            $this->maxoccursinevent = ($row[$startcol + 33] !== null) ? (int) $row[$startcol + 33] : null;
            $this->showtime = ($row[$startcol + 34] !== null) ? (boolean) $row[$startcol + 34] : null;
            $this->ismes = ($row[$startcol + 35] !== null) ? (int) $row[$startcol + 35] : null;
            $this->nomenclativeservice_id = ($row[$startcol + 36] !== null) ? (int) $row[$startcol + 36] : null;
            $this->ispreferable = ($row[$startcol + 37] !== null) ? (boolean) $row[$startcol + 37] : null;
            $this->prescribedtype_id = ($row[$startcol + 38] !== null) ? (int) $row[$startcol + 38] : null;
            $this->shedule_id = ($row[$startcol + 39] !== null) ? (int) $row[$startcol + 39] : null;
            $this->isrequiredcoordination = ($row[$startcol + 40] !== null) ? (boolean) $row[$startcol + 40] : null;
            $this->isrequiredtissue = ($row[$startcol + 41] !== null) ? (boolean) $row[$startcol + 41] : null;
            $this->testtubetype_id = ($row[$startcol + 42] !== null) ? (int) $row[$startcol + 42] : null;
            $this->jobtype_id = ($row[$startcol + 43] !== null) ? (int) $row[$startcol + 43] : null;
            $this->mnem = ($row[$startcol + 44] !== null) ? (string) $row[$startcol + 44] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 45; // 45 = ActionTypePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating ActionType object", $e);
        }
    }

    /**
     * Checks and repairs the internal consistency of the object.
     *
     * This method is executed after an already-instantiated object is re-hydrated
     * from the database.  It exists to check any foreign keys to make sure that
     * the objects related to the current object are correct based on foreign key.
     *
     * You can override this method in the stub class, but you should always invoke
     * the base method from the overridden method (i.e. parent::ensureConsistency()),
     * in case your model changes.
     *
     * @throws PropelException
     */
    public function ensureConsistency()
    {

        if ($this->aActionPropertyTypeRelatedByid !== null && $this->id !== $this->aActionPropertyTypeRelatedByid->getactionTypeId()) {
            $this->aActionPropertyTypeRelatedByid = null;
        }
    } // ensureConsistency

    /**
     * Reloads this object from datastore based on primary key and (optionally) resets all associated objects.
     *
     * This will only work if the object has been saved and has a valid primary key set.
     *
     * @param boolean $deep (optional) Whether to also de-associated any related objects.
     * @param PropelPDO $con (optional) The PropelPDO connection to use.
     * @return void
     * @throws PropelException - if this object is deleted, unsaved or doesn't have pk match in db
     */
    public function reload($deep = false, PropelPDO $con = null)
    {
        if ($this->isDeleted()) {
            throw new PropelException("Cannot reload a deleted object.");
        }

        if ($this->isNew()) {
            throw new PropelException("Cannot reload an unsaved object.");
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = ActionTypePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aActionPropertyTypeRelatedByid = null;
            $this->collActions = null;

            $this->collActionPropertyTypesRelatedByactionTypeId = null;

        } // if (deep)
    }

    /**
     * Removes this object from datastore and sets delete attribute.
     *
     * @param PropelPDO $con
     * @return void
     * @throws PropelException
     * @throws Exception
     * @see        BaseObject::setDeleted()
     * @see        BaseObject::isDeleted()
     */
    public function delete(PropelPDO $con = null)
    {
        if ($this->isDeleted()) {
            throw new PropelException("This object has already been deleted.");
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = ActionTypeQuery::create()
                ->filterByPrimaryKey($this->getPrimaryKey());
            $ret = $this->preDelete($con);
            if ($ret) {
                $deleteQuery->delete($con);
                $this->postDelete($con);
                $con->commit();
                $this->setDeleted(true);
            } else {
                $con->commit();
            }
        } catch (Exception $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Persists this object to the database.
     *
     * If the object is new, it inserts it; otherwise an update is performed.
     * All modified related objects will also be persisted in the doSave()
     * method.  This method wraps all precipitate database operations in a
     * single transaction.
     *
     * @param PropelPDO $con
     * @return int             The number of rows affected by this insert/update and any referring fk objects' save() operations.
     * @throws PropelException
     * @throws Exception
     * @see        doSave()
     */
    public function save(PropelPDO $con = null)
    {
        if ($this->isDeleted()) {
            throw new PropelException("You cannot save an object that has been deleted.");
        }

        if ($con === null) {
            $con = Propel::getConnection(ActionTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(ActionTypePeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(ActionTypePeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(ActionTypePeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            }
            if ($ret) {
                $affectedRows = $this->doSave($con);
                if ($isInsert) {
                    $this->postInsert($con);
                } else {
                    $this->postUpdate($con);
                }
                $this->postSave($con);
                ActionTypePeer::addInstanceToPool($this);
            } else {
                $affectedRows = 0;
            }
            $con->commit();

            return $affectedRows;
        } catch (Exception $e) {
            $con->rollBack();
            throw $e;
        }
    }

    /**
     * Performs the work of inserting or updating the row in the database.
     *
     * If the object is new, it inserts it; otherwise an update is performed.
     * All related objects are also updated in this method.
     *
     * @param PropelPDO $con
     * @return int             The number of rows affected by this insert/update and any referring fk objects' save() operations.
     * @throws PropelException
     * @see        save()
     */
    protected function doSave(PropelPDO $con)
    {
        $affectedRows = 0; // initialize var to track total num of affected rows
        if (!$this->alreadyInSave) {
            $this->alreadyInSave = true;

            // We call the save method on the following object(s) if they
            // were passed to this object by their coresponding set
            // method.  This object relates to these object(s) by a
            // foreign key reference.

            if ($this->aActionPropertyTypeRelatedByid !== null) {
                if ($this->aActionPropertyTypeRelatedByid->isModified() || $this->aActionPropertyTypeRelatedByid->isNew()) {
                    $affectedRows += $this->aActionPropertyTypeRelatedByid->save($con);
                }
                $this->setActionPropertyTypeRelatedByid($this->aActionPropertyTypeRelatedByid);
            }

            if ($this->isNew() || $this->isModified()) {
                // persist changes
                if ($this->isNew()) {
                    $this->doInsert($con);
                } else {
                    $this->doUpdate($con);
                }
                $affectedRows += 1;
                $this->resetModified();
            }

            if ($this->actionsScheduledForDeletion !== null) {
                if (!$this->actionsScheduledForDeletion->isEmpty()) {
                    ActionQuery::create()
                        ->filterByPrimaryKeys($this->actionsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionsScheduledForDeletion = null;
                }
            }

            if ($this->collActions !== null) {
                foreach ($this->collActions as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion !== null) {
                if (!$this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion->isEmpty()) {
                    ActionPropertyTypeQuery::create()
                        ->filterByPrimaryKeys($this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion = null;
                }
            }

            if ($this->collActionPropertyTypesRelatedByactionTypeId !== null) {
                foreach ($this->collActionPropertyTypesRelatedByactionTypeId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            $this->alreadyInSave = false;

        }

        return $affectedRows;
    } // doSave()

    /**
     * Insert the row in the database.
     *
     * @param PropelPDO $con
     *
     * @throws PropelException
     * @see        doSave()
     */
    protected function doInsert(PropelPDO $con)
    {
        $modifiedColumns = array();
        $index = 0;

        $this->modifiedColumns[] = ActionTypePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . ActionTypePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(ActionTypePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(ActionTypePeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(ActionTypePeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(ActionTypePeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(ActionTypePeer::CLAZZ)) {
            $modifiedColumns[':p' . $index++]  = '`class`';
        }
        if ($this->isColumnModified(ActionTypePeer::GROUP_ID)) {
            $modifiedColumns[':p' . $index++]  = '`group_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(ActionTypePeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(ActionTypePeer::TITLE)) {
            $modifiedColumns[':p' . $index++]  = '`title`';
        }
        if ($this->isColumnModified(ActionTypePeer::FLATCODE)) {
            $modifiedColumns[':p' . $index++]  = '`flatCode`';
        }
        if ($this->isColumnModified(ActionTypePeer::SEX)) {
            $modifiedColumns[':p' . $index++]  = '`sex`';
        }
        if ($this->isColumnModified(ActionTypePeer::AGE)) {
            $modifiedColumns[':p' . $index++]  = '`age`';
        }
        if ($this->isColumnModified(ActionTypePeer::AGE_BU)) {
            $modifiedColumns[':p' . $index++]  = '`age_bu`';
        }
        if ($this->isColumnModified(ActionTypePeer::AGE_BC)) {
            $modifiedColumns[':p' . $index++]  = '`age_bc`';
        }
        if ($this->isColumnModified(ActionTypePeer::AGE_EU)) {
            $modifiedColumns[':p' . $index++]  = '`age_eu`';
        }
        if ($this->isColumnModified(ActionTypePeer::AGE_EC)) {
            $modifiedColumns[':p' . $index++]  = '`age_ec`';
        }
        if ($this->isColumnModified(ActionTypePeer::OFFICE)) {
            $modifiedColumns[':p' . $index++]  = '`office`';
        }
        if ($this->isColumnModified(ActionTypePeer::SHOWINFORM)) {
            $modifiedColumns[':p' . $index++]  = '`showInForm`';
        }
        if ($this->isColumnModified(ActionTypePeer::GENTIMETABLE)) {
            $modifiedColumns[':p' . $index++]  = '`genTimetable`';
        }
        if ($this->isColumnModified(ActionTypePeer::SERVICE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`service_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::QUOTATYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`quotaType_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::CONTEXT)) {
            $modifiedColumns[':p' . $index++]  = '`context`';
        }
        if ($this->isColumnModified(ActionTypePeer::AMOUNT)) {
            $modifiedColumns[':p' . $index++]  = '`amount`';
        }
        if ($this->isColumnModified(ActionTypePeer::AMOUNTEVALUATION)) {
            $modifiedColumns[':p' . $index++]  = '`amountEvaluation`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTSTATUS)) {
            $modifiedColumns[':p' . $index++]  = '`defaultStatus`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTDIRECTIONDATE)) {
            $modifiedColumns[':p' . $index++]  = '`defaultDirectionDate`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPLANNEDENDDATE)) {
            $modifiedColumns[':p' . $index++]  = '`defaultPlannedEndDate`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTENDDATE)) {
            $modifiedColumns[':p' . $index++]  = '`defaultEndDate`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTEXECPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`defaultExecPerson_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPERSONINEVENT)) {
            $modifiedColumns[':p' . $index++]  = '`defaultPersonInEvent`';
        }
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPERSONINEDITOR)) {
            $modifiedColumns[':p' . $index++]  = '`defaultPersonInEditor`';
        }
        if ($this->isColumnModified(ActionTypePeer::MAXOCCURSINEVENT)) {
            $modifiedColumns[':p' . $index++]  = '`maxOccursInEvent`';
        }
        if ($this->isColumnModified(ActionTypePeer::SHOWTIME)) {
            $modifiedColumns[':p' . $index++]  = '`showTime`';
        }
        if ($this->isColumnModified(ActionTypePeer::ISMES)) {
            $modifiedColumns[':p' . $index++]  = '`isMES`';
        }
        if ($this->isColumnModified(ActionTypePeer::NOMENCLATIVESERVICE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`nomenclativeService_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::ISPREFERABLE)) {
            $modifiedColumns[':p' . $index++]  = '`isPreferable`';
        }
        if ($this->isColumnModified(ActionTypePeer::PRESCRIBEDTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`prescribedType_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::SHEDULE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`shedule_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::ISREQUIREDCOORDINATION)) {
            $modifiedColumns[':p' . $index++]  = '`isRequiredCoordination`';
        }
        if ($this->isColumnModified(ActionTypePeer::ISREQUIREDTISSUE)) {
            $modifiedColumns[':p' . $index++]  = '`isRequiredTissue`';
        }
        if ($this->isColumnModified(ActionTypePeer::TESTTUBETYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`testTubeType_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::JOBTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`jobType_id`';
        }
        if ($this->isColumnModified(ActionTypePeer::MNEM)) {
            $modifiedColumns[':p' . $index++]  = '`mnem`';
        }

        $sql = sprintf(
            'INSERT INTO `ActionType` (%s) VALUES (%s)',
            implode(', ', $modifiedColumns),
            implode(', ', array_keys($modifiedColumns))
        );

        try {
            $stmt = $con->prepare($sql);
            foreach ($modifiedColumns as $identifier => $columnName) {
                switch ($columnName) {
                    case '`id`':
                        $stmt->bindValue($identifier, $this->id, PDO::PARAM_INT);
                        break;
                    case '`createDatetime`':
                        $stmt->bindValue($identifier, $this->createdatetime, PDO::PARAM_STR);
                        break;
                    case '`createPerson_id`':
                        $stmt->bindValue($identifier, $this->createperson_id, PDO::PARAM_INT);
                        break;
                    case '`modifyDatetime`':
                        $stmt->bindValue($identifier, $this->modifydatetime, PDO::PARAM_STR);
                        break;
                    case '`modifyPerson_id`':
                        $stmt->bindValue($identifier, $this->modifyperson_id, PDO::PARAM_INT);
                        break;
                    case '`deleted`':
                        $stmt->bindValue($identifier, (int) $this->deleted, PDO::PARAM_INT);
                        break;
                    case '`class`':
                        $stmt->bindValue($identifier, (int) $this->class, PDO::PARAM_INT);
                        break;
                    case '`group_id`':
                        $stmt->bindValue($identifier, $this->group_id, PDO::PARAM_INT);
                        break;
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`title`':
                        $stmt->bindValue($identifier, $this->title, PDO::PARAM_STR);
                        break;
                    case '`flatCode`':
                        $stmt->bindValue($identifier, $this->flatcode, PDO::PARAM_STR);
                        break;
                    case '`sex`':
                        $stmt->bindValue($identifier, $this->sex, PDO::PARAM_INT);
                        break;
                    case '`age`':
                        $stmt->bindValue($identifier, $this->age, PDO::PARAM_STR);
                        break;
                    case '`age_bu`':
                        $stmt->bindValue($identifier, $this->age_bu, PDO::PARAM_INT);
                        break;
                    case '`age_bc`':
                        $stmt->bindValue($identifier, $this->age_bc, PDO::PARAM_INT);
                        break;
                    case '`age_eu`':
                        $stmt->bindValue($identifier, $this->age_eu, PDO::PARAM_INT);
                        break;
                    case '`age_ec`':
                        $stmt->bindValue($identifier, $this->age_ec, PDO::PARAM_INT);
                        break;
                    case '`office`':
                        $stmt->bindValue($identifier, $this->office, PDO::PARAM_STR);
                        break;
                    case '`showInForm`':
                        $stmt->bindValue($identifier, (int) $this->showinform, PDO::PARAM_INT);
                        break;
                    case '`genTimetable`':
                        $stmt->bindValue($identifier, (int) $this->gentimetable, PDO::PARAM_INT);
                        break;
                    case '`service_id`':
                        $stmt->bindValue($identifier, $this->service_id, PDO::PARAM_INT);
                        break;
                    case '`quotaType_id`':
                        $stmt->bindValue($identifier, $this->quotatype_id, PDO::PARAM_INT);
                        break;
                    case '`context`':
                        $stmt->bindValue($identifier, $this->context, PDO::PARAM_STR);
                        break;
                    case '`amount`':
                        $stmt->bindValue($identifier, $this->amount, PDO::PARAM_STR);
                        break;
                    case '`amountEvaluation`':
                        $stmt->bindValue($identifier, $this->amountevaluation, PDO::PARAM_INT);
                        break;
                    case '`defaultStatus`':
                        $stmt->bindValue($identifier, $this->defaultstatus, PDO::PARAM_INT);
                        break;
                    case '`defaultDirectionDate`':
                        $stmt->bindValue($identifier, $this->defaultdirectiondate, PDO::PARAM_INT);
                        break;
                    case '`defaultPlannedEndDate`':
                        $stmt->bindValue($identifier, (int) $this->defaultplannedenddate, PDO::PARAM_INT);
                        break;
                    case '`defaultEndDate`':
                        $stmt->bindValue($identifier, $this->defaultenddate, PDO::PARAM_INT);
                        break;
                    case '`defaultExecPerson_id`':
                        $stmt->bindValue($identifier, $this->defaultexecperson_id, PDO::PARAM_INT);
                        break;
                    case '`defaultPersonInEvent`':
                        $stmt->bindValue($identifier, $this->defaultpersoninevent, PDO::PARAM_INT);
                        break;
                    case '`defaultPersonInEditor`':
                        $stmt->bindValue($identifier, $this->defaultpersonineditor, PDO::PARAM_INT);
                        break;
                    case '`maxOccursInEvent`':
                        $stmt->bindValue($identifier, $this->maxoccursinevent, PDO::PARAM_INT);
                        break;
                    case '`showTime`':
                        $stmt->bindValue($identifier, (int) $this->showtime, PDO::PARAM_INT);
                        break;
                    case '`isMES`':
                        $stmt->bindValue($identifier, $this->ismes, PDO::PARAM_INT);
                        break;
                    case '`nomenclativeService_id`':
                        $stmt->bindValue($identifier, $this->nomenclativeservice_id, PDO::PARAM_INT);
                        break;
                    case '`isPreferable`':
                        $stmt->bindValue($identifier, (int) $this->ispreferable, PDO::PARAM_INT);
                        break;
                    case '`prescribedType_id`':
                        $stmt->bindValue($identifier, $this->prescribedtype_id, PDO::PARAM_INT);
                        break;
                    case '`shedule_id`':
                        $stmt->bindValue($identifier, $this->shedule_id, PDO::PARAM_INT);
                        break;
                    case '`isRequiredCoordination`':
                        $stmt->bindValue($identifier, (int) $this->isrequiredcoordination, PDO::PARAM_INT);
                        break;
                    case '`isRequiredTissue`':
                        $stmt->bindValue($identifier, (int) $this->isrequiredtissue, PDO::PARAM_INT);
                        break;
                    case '`testTubeType_id`':
                        $stmt->bindValue($identifier, $this->testtubetype_id, PDO::PARAM_INT);
                        break;
                    case '`jobType_id`':
                        $stmt->bindValue($identifier, $this->jobtype_id, PDO::PARAM_INT);
                        break;
                    case '`mnem`':
                        $stmt->bindValue($identifier, $this->mnem, PDO::PARAM_STR);
                        break;
                }
            }
            $stmt->execute();
        } catch (Exception $e) {
            Propel::log($e->getMessage(), Propel::LOG_ERR);
            throw new PropelException(sprintf('Unable to execute INSERT statement [%s]', $sql), $e);
        }

        try {
            $pk = $con->lastInsertId();
        } catch (Exception $e) {
            throw new PropelException('Unable to get autoincrement id.', $e);
        }
        $this->setid($pk);

        $this->setNew(false);
    }

    /**
     * Update the row in the database.
     *
     * @param PropelPDO $con
     *
     * @see        doSave()
     */
    protected function doUpdate(PropelPDO $con)
    {
        $selectCriteria = $this->buildPkeyCriteria();
        $valuesCriteria = $this->buildCriteria();
        BasePeer::doUpdate($selectCriteria, $valuesCriteria, $con);
    }

    /**
     * Array of ValidationFailed objects.
     * @var        array ValidationFailed[]
     */
    protected $validationFailures = array();

    /**
     * Gets any ValidationFailed objects that resulted from last call to validate().
     *
     *
     * @return array ValidationFailed[]
     * @see        validate()
     */
    public function getValidationFailures()
    {
        return $this->validationFailures;
    }

    /**
     * Validates the objects modified field values and all objects related to this table.
     *
     * If $columns is either a column name or an array of column names
     * only those columns are validated.
     *
     * @param mixed $columns Column name or an array of column names.
     * @return boolean Whether all columns pass validation.
     * @see        doValidate()
     * @see        getValidationFailures()
     */
    public function validate($columns = null)
    {
        $res = $this->doValidate($columns);
        if ($res === true) {
            $this->validationFailures = array();

            return true;
        }

        $this->validationFailures = $res;

        return false;
    }

    /**
     * This function performs the validation work for complex object models.
     *
     * In addition to checking the current object, all related objects will
     * also be validated.  If all pass then <code>true</code> is returned; otherwise
     * an aggreagated array of ValidationFailed objects will be returned.
     *
     * @param array $columns Array of column names to validate.
     * @return mixed <code>true</code> if all validations pass; array of <code>ValidationFailed</code> objets otherwise.
     */
    protected function doValidate($columns = null)
    {
        if (!$this->alreadyInValidation) {
            $this->alreadyInValidation = true;
            $retval = null;

            $failureMap = array();


            // We call the validate method on the following object(s) if they
            // were passed to this object by their coresponding set
            // method.  This object relates to these object(s) by a
            // foreign key reference.

            if ($this->aActionPropertyTypeRelatedByid !== null) {
                if (!$this->aActionPropertyTypeRelatedByid->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyTypeRelatedByid->getValidationFailures());
                }
            }


            if (($retval = ActionTypePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActions !== null) {
                    foreach ($this->collActions as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collActionPropertyTypesRelatedByactionTypeId !== null) {
                    foreach ($this->collActionPropertyTypesRelatedByactionTypeId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }


            $this->alreadyInValidation = false;
        }

        return (!empty($failureMap) ? $failureMap : true);
    }

    /**
     * Retrieves a field from the object by name passed in as a string.
     *
     * @param string $name name
     * @param string $type The type of fieldname the $name is of:
     *               one of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *               BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM.
     *               Defaults to BasePeer::TYPE_PHPNAME
     * @return mixed Value of field.
     */
    public function getByName($name, $type = BasePeer::TYPE_PHPNAME)
    {
        $pos = ActionTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
        $field = $this->getByPosition($pos);

        return $field;
    }

    /**
     * Retrieves a field from the object by Position as specified in the xml schema.
     * Zero-based.
     *
     * @param int $pos position in xml schema
     * @return mixed Value of field at $pos
     */
    public function getByPosition($pos)
    {
        switch ($pos) {
            case 0:
                return $this->getid();
                break;
            case 1:
                return $this->getcreateDatetime();
                break;
            case 2:
                return $this->getcreatePersonId();
                break;
            case 3:
                return $this->getmodifyDatetime();
                break;
            case 4:
                return $this->getmodifyPersonId();
                break;
            case 5:
                return $this->getdeleted();
                break;
            case 6:
                return $this->getClass();
                break;
            case 7:
                return $this->getgroupId();
                break;
            case 8:
                return $this->getcode();
                break;
            case 9:
                return $this->getname();
                break;
            case 10:
                return $this->gettitle();
                break;
            case 11:
                return $this->getflatCode();
                break;
            case 12:
                return $this->getsex();
                break;
            case 13:
                return $this->getage();
                break;
            case 14:
                return $this->getageBu();
                break;
            case 15:
                return $this->getageBc();
                break;
            case 16:
                return $this->getageEu();
                break;
            case 17:
                return $this->getageEc();
                break;
            case 18:
                return $this->getoffice();
                break;
            case 19:
                return $this->getshowInForm();
                break;
            case 20:
                return $this->getgenTimeTable();
                break;
            case 21:
                return $this->getserviceId();
                break;
            case 22:
                return $this->getquotaTypeId();
                break;
            case 23:
                return $this->getcontext();
                break;
            case 24:
                return $this->getamount();
                break;
            case 25:
                return $this->getamountEvaluation();
                break;
            case 26:
                return $this->getdefaultStatus();
                break;
            case 27:
                return $this->getdefaultDirectionDate();
                break;
            case 28:
                return $this->getdefaultPlannedEndDate();
                break;
            case 29:
                return $this->getdefaultEndDate();
                break;
            case 30:
                return $this->getdefaultExecPersonId();
                break;
            case 31:
                return $this->getdefaultPersonInEvent();
                break;
            case 32:
                return $this->getdefaultPersonInEditor();
                break;
            case 33:
                return $this->getmaxOccursInEvent();
                break;
            case 34:
                return $this->getshowTime();
                break;
            case 35:
                return $this->getisMes();
                break;
            case 36:
                return $this->getnomenclativeServiceId();
                break;
            case 37:
                return $this->getisPreferable();
                break;
            case 38:
                return $this->getprescribedTypeId();
                break;
            case 39:
                return $this->getsheduleId();
                break;
            case 40:
                return $this->getisRequiredCoordination();
                break;
            case 41:
                return $this->getisRequiredTissue();
                break;
            case 42:
                return $this->gettestTubeTypeId();
                break;
            case 43:
                return $this->getjobTypeId();
                break;
            case 44:
                return $this->getmnem();
                break;
            default:
                return null;
                break;
        } // switch()
    }

    /**
     * Exports the object as an array.
     *
     * You can specify the key type of the array by passing one of the class
     * type constants.
     *
     * @param     string  $keyType (optional) One of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME,
     *                    BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM.
     *                    Defaults to BasePeer::TYPE_PHPNAME.
     * @param     boolean $includeLazyLoadColumns (optional) Whether to include lazy loaded columns. Defaults to true.
     * @param     array $alreadyDumpedObjects List of objects to skip to avoid recursion
     * @param     boolean $includeForeignObjects (optional) Whether to include hydrated related objects. Default to FALSE.
     *
     * @return array an associative array containing the field names (as keys) and field values
     */
    public function toArray($keyType = BasePeer::TYPE_PHPNAME, $includeLazyLoadColumns = true, $alreadyDumpedObjects = array(), $includeForeignObjects = false)
    {
        if (isset($alreadyDumpedObjects['ActionType'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['ActionType'][$this->getPrimaryKey()] = true;
        $keys = ActionTypePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getClass(),
            $keys[7] => $this->getgroupId(),
            $keys[8] => $this->getcode(),
            $keys[9] => $this->getname(),
            $keys[10] => $this->gettitle(),
            $keys[11] => $this->getflatCode(),
            $keys[12] => $this->getsex(),
            $keys[13] => $this->getage(),
            $keys[14] => $this->getageBu(),
            $keys[15] => $this->getageBc(),
            $keys[16] => $this->getageEu(),
            $keys[17] => $this->getageEc(),
            $keys[18] => $this->getoffice(),
            $keys[19] => $this->getshowInForm(),
            $keys[20] => $this->getgenTimeTable(),
            $keys[21] => $this->getserviceId(),
            $keys[22] => $this->getquotaTypeId(),
            $keys[23] => $this->getcontext(),
            $keys[24] => $this->getamount(),
            $keys[25] => $this->getamountEvaluation(),
            $keys[26] => $this->getdefaultStatus(),
            $keys[27] => $this->getdefaultDirectionDate(),
            $keys[28] => $this->getdefaultPlannedEndDate(),
            $keys[29] => $this->getdefaultEndDate(),
            $keys[30] => $this->getdefaultExecPersonId(),
            $keys[31] => $this->getdefaultPersonInEvent(),
            $keys[32] => $this->getdefaultPersonInEditor(),
            $keys[33] => $this->getmaxOccursInEvent(),
            $keys[34] => $this->getshowTime(),
            $keys[35] => $this->getisMes(),
            $keys[36] => $this->getnomenclativeServiceId(),
            $keys[37] => $this->getisPreferable(),
            $keys[38] => $this->getprescribedTypeId(),
            $keys[39] => $this->getsheduleId(),
            $keys[40] => $this->getisRequiredCoordination(),
            $keys[41] => $this->getisRequiredTissue(),
            $keys[42] => $this->gettestTubeTypeId(),
            $keys[43] => $this->getjobTypeId(),
            $keys[44] => $this->getmnem(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aActionPropertyTypeRelatedByid) {
                $result['ActionPropertyTypeRelatedByid'] = $this->aActionPropertyTypeRelatedByid->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActions) {
                $result['Actions'] = $this->collActions->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collActionPropertyTypesRelatedByactionTypeId) {
                $result['ActionPropertyTypesRelatedByactionTypeId'] = $this->collActionPropertyTypesRelatedByactionTypeId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
        }

        return $result;
    }

    /**
     * Sets a field from the object by name passed in as a string.
     *
     * @param string $name peer name
     * @param mixed $value field value
     * @param string $type The type of fieldname the $name is of:
     *                     one of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME
     *                     BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM.
     *                     Defaults to BasePeer::TYPE_PHPNAME
     * @return void
     */
    public function setByName($name, $value, $type = BasePeer::TYPE_PHPNAME)
    {
        $pos = ActionTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

        $this->setByPosition($pos, $value);
    }

    /**
     * Sets a field from the object by Position as specified in the xml schema.
     * Zero-based.
     *
     * @param int $pos position in xml schema
     * @param mixed $value field value
     * @return void
     */
    public function setByPosition($pos, $value)
    {
        switch ($pos) {
            case 0:
                $this->setid($value);
                break;
            case 1:
                $this->setcreateDatetime($value);
                break;
            case 2:
                $this->setcreatePersonId($value);
                break;
            case 3:
                $this->setmodifyDatetime($value);
                break;
            case 4:
                $this->setmodifyPersonId($value);
                break;
            case 5:
                $this->setdeleted($value);
                break;
            case 6:
                $this->setClass($value);
                break;
            case 7:
                $this->setgroupId($value);
                break;
            case 8:
                $this->setcode($value);
                break;
            case 9:
                $this->setname($value);
                break;
            case 10:
                $this->settitle($value);
                break;
            case 11:
                $this->setflatCode($value);
                break;
            case 12:
                $this->setsex($value);
                break;
            case 13:
                $this->setage($value);
                break;
            case 14:
                $this->setageBu($value);
                break;
            case 15:
                $this->setageBc($value);
                break;
            case 16:
                $this->setageEu($value);
                break;
            case 17:
                $this->setageEc($value);
                break;
            case 18:
                $this->setoffice($value);
                break;
            case 19:
                $this->setshowInForm($value);
                break;
            case 20:
                $this->setgenTimeTable($value);
                break;
            case 21:
                $this->setserviceId($value);
                break;
            case 22:
                $this->setquotaTypeId($value);
                break;
            case 23:
                $this->setcontext($value);
                break;
            case 24:
                $this->setamount($value);
                break;
            case 25:
                $this->setamountEvaluation($value);
                break;
            case 26:
                $this->setdefaultStatus($value);
                break;
            case 27:
                $this->setdefaultDirectionDate($value);
                break;
            case 28:
                $this->setdefaultPlannedEndDate($value);
                break;
            case 29:
                $this->setdefaultEndDate($value);
                break;
            case 30:
                $this->setdefaultExecPersonId($value);
                break;
            case 31:
                $this->setdefaultPersonInEvent($value);
                break;
            case 32:
                $this->setdefaultPersonInEditor($value);
                break;
            case 33:
                $this->setmaxOccursInEvent($value);
                break;
            case 34:
                $this->setshowTime($value);
                break;
            case 35:
                $this->setisMes($value);
                break;
            case 36:
                $this->setnomenclativeServiceId($value);
                break;
            case 37:
                $this->setisPreferable($value);
                break;
            case 38:
                $this->setprescribedTypeId($value);
                break;
            case 39:
                $this->setsheduleId($value);
                break;
            case 40:
                $this->setisRequiredCoordination($value);
                break;
            case 41:
                $this->setisRequiredTissue($value);
                break;
            case 42:
                $this->settestTubeTypeId($value);
                break;
            case 43:
                $this->setjobTypeId($value);
                break;
            case 44:
                $this->setmnem($value);
                break;
        } // switch()
    }

    /**
     * Populates the object using an array.
     *
     * This is particularly useful when populating an object from one of the
     * request arrays (e.g. $_POST).  This method goes through the column
     * names, checking to see whether a matching key exists in populated
     * array. If so the setByName() method is called for that column.
     *
     * You can specify the key type of the array by additionally passing one
     * of the class type constants BasePeer::TYPE_PHPNAME, BasePeer::TYPE_STUDLYPHPNAME,
     * BasePeer::TYPE_COLNAME, BasePeer::TYPE_FIELDNAME, BasePeer::TYPE_NUM.
     * The default key type is the column's BasePeer::TYPE_PHPNAME
     *
     * @param array  $arr     An array to populate the object from.
     * @param string $keyType The type of keys the array uses.
     * @return void
     */
    public function fromArray($arr, $keyType = BasePeer::TYPE_PHPNAME)
    {
        $keys = ActionTypePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setClass($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setgroupId($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setcode($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setname($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->settitle($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setflatCode($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setsex($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setage($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setageBu($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setageBc($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setageEu($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setageEc($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setoffice($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setshowInForm($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setgenTimeTable($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setserviceId($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setquotaTypeId($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setcontext($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setamount($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->setamountEvaluation($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->setdefaultStatus($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setdefaultDirectionDate($arr[$keys[27]]);
        if (array_key_exists($keys[28], $arr)) $this->setdefaultPlannedEndDate($arr[$keys[28]]);
        if (array_key_exists($keys[29], $arr)) $this->setdefaultEndDate($arr[$keys[29]]);
        if (array_key_exists($keys[30], $arr)) $this->setdefaultExecPersonId($arr[$keys[30]]);
        if (array_key_exists($keys[31], $arr)) $this->setdefaultPersonInEvent($arr[$keys[31]]);
        if (array_key_exists($keys[32], $arr)) $this->setdefaultPersonInEditor($arr[$keys[32]]);
        if (array_key_exists($keys[33], $arr)) $this->setmaxOccursInEvent($arr[$keys[33]]);
        if (array_key_exists($keys[34], $arr)) $this->setshowTime($arr[$keys[34]]);
        if (array_key_exists($keys[35], $arr)) $this->setisMes($arr[$keys[35]]);
        if (array_key_exists($keys[36], $arr)) $this->setnomenclativeServiceId($arr[$keys[36]]);
        if (array_key_exists($keys[37], $arr)) $this->setisPreferable($arr[$keys[37]]);
        if (array_key_exists($keys[38], $arr)) $this->setprescribedTypeId($arr[$keys[38]]);
        if (array_key_exists($keys[39], $arr)) $this->setsheduleId($arr[$keys[39]]);
        if (array_key_exists($keys[40], $arr)) $this->setisRequiredCoordination($arr[$keys[40]]);
        if (array_key_exists($keys[41], $arr)) $this->setisRequiredTissue($arr[$keys[41]]);
        if (array_key_exists($keys[42], $arr)) $this->settestTubeTypeId($arr[$keys[42]]);
        if (array_key_exists($keys[43], $arr)) $this->setjobTypeId($arr[$keys[43]]);
        if (array_key_exists($keys[44], $arr)) $this->setmnem($arr[$keys[44]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(ActionTypePeer::DATABASE_NAME);

        if ($this->isColumnModified(ActionTypePeer::ID)) $criteria->add(ActionTypePeer::ID, $this->id);
        if ($this->isColumnModified(ActionTypePeer::CREATEDATETIME)) $criteria->add(ActionTypePeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(ActionTypePeer::CREATEPERSON_ID)) $criteria->add(ActionTypePeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(ActionTypePeer::MODIFYDATETIME)) $criteria->add(ActionTypePeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(ActionTypePeer::MODIFYPERSON_ID)) $criteria->add(ActionTypePeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(ActionTypePeer::DELETED)) $criteria->add(ActionTypePeer::DELETED, $this->deleted);
        if ($this->isColumnModified(ActionTypePeer::CLAZZ)) $criteria->add(ActionTypePeer::CLAZZ, $this->class);
        if ($this->isColumnModified(ActionTypePeer::GROUP_ID)) $criteria->add(ActionTypePeer::GROUP_ID, $this->group_id);
        if ($this->isColumnModified(ActionTypePeer::CODE)) $criteria->add(ActionTypePeer::CODE, $this->code);
        if ($this->isColumnModified(ActionTypePeer::NAME)) $criteria->add(ActionTypePeer::NAME, $this->name);
        if ($this->isColumnModified(ActionTypePeer::TITLE)) $criteria->add(ActionTypePeer::TITLE, $this->title);
        if ($this->isColumnModified(ActionTypePeer::FLATCODE)) $criteria->add(ActionTypePeer::FLATCODE, $this->flatcode);
        if ($this->isColumnModified(ActionTypePeer::SEX)) $criteria->add(ActionTypePeer::SEX, $this->sex);
        if ($this->isColumnModified(ActionTypePeer::AGE)) $criteria->add(ActionTypePeer::AGE, $this->age);
        if ($this->isColumnModified(ActionTypePeer::AGE_BU)) $criteria->add(ActionTypePeer::AGE_BU, $this->age_bu);
        if ($this->isColumnModified(ActionTypePeer::AGE_BC)) $criteria->add(ActionTypePeer::AGE_BC, $this->age_bc);
        if ($this->isColumnModified(ActionTypePeer::AGE_EU)) $criteria->add(ActionTypePeer::AGE_EU, $this->age_eu);
        if ($this->isColumnModified(ActionTypePeer::AGE_EC)) $criteria->add(ActionTypePeer::AGE_EC, $this->age_ec);
        if ($this->isColumnModified(ActionTypePeer::OFFICE)) $criteria->add(ActionTypePeer::OFFICE, $this->office);
        if ($this->isColumnModified(ActionTypePeer::SHOWINFORM)) $criteria->add(ActionTypePeer::SHOWINFORM, $this->showinform);
        if ($this->isColumnModified(ActionTypePeer::GENTIMETABLE)) $criteria->add(ActionTypePeer::GENTIMETABLE, $this->gentimetable);
        if ($this->isColumnModified(ActionTypePeer::SERVICE_ID)) $criteria->add(ActionTypePeer::SERVICE_ID, $this->service_id);
        if ($this->isColumnModified(ActionTypePeer::QUOTATYPE_ID)) $criteria->add(ActionTypePeer::QUOTATYPE_ID, $this->quotatype_id);
        if ($this->isColumnModified(ActionTypePeer::CONTEXT)) $criteria->add(ActionTypePeer::CONTEXT, $this->context);
        if ($this->isColumnModified(ActionTypePeer::AMOUNT)) $criteria->add(ActionTypePeer::AMOUNT, $this->amount);
        if ($this->isColumnModified(ActionTypePeer::AMOUNTEVALUATION)) $criteria->add(ActionTypePeer::AMOUNTEVALUATION, $this->amountevaluation);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTSTATUS)) $criteria->add(ActionTypePeer::DEFAULTSTATUS, $this->defaultstatus);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTDIRECTIONDATE)) $criteria->add(ActionTypePeer::DEFAULTDIRECTIONDATE, $this->defaultdirectiondate);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPLANNEDENDDATE)) $criteria->add(ActionTypePeer::DEFAULTPLANNEDENDDATE, $this->defaultplannedenddate);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTENDDATE)) $criteria->add(ActionTypePeer::DEFAULTENDDATE, $this->defaultenddate);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTEXECPERSON_ID)) $criteria->add(ActionTypePeer::DEFAULTEXECPERSON_ID, $this->defaultexecperson_id);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPERSONINEVENT)) $criteria->add(ActionTypePeer::DEFAULTPERSONINEVENT, $this->defaultpersoninevent);
        if ($this->isColumnModified(ActionTypePeer::DEFAULTPERSONINEDITOR)) $criteria->add(ActionTypePeer::DEFAULTPERSONINEDITOR, $this->defaultpersonineditor);
        if ($this->isColumnModified(ActionTypePeer::MAXOCCURSINEVENT)) $criteria->add(ActionTypePeer::MAXOCCURSINEVENT, $this->maxoccursinevent);
        if ($this->isColumnModified(ActionTypePeer::SHOWTIME)) $criteria->add(ActionTypePeer::SHOWTIME, $this->showtime);
        if ($this->isColumnModified(ActionTypePeer::ISMES)) $criteria->add(ActionTypePeer::ISMES, $this->ismes);
        if ($this->isColumnModified(ActionTypePeer::NOMENCLATIVESERVICE_ID)) $criteria->add(ActionTypePeer::NOMENCLATIVESERVICE_ID, $this->nomenclativeservice_id);
        if ($this->isColumnModified(ActionTypePeer::ISPREFERABLE)) $criteria->add(ActionTypePeer::ISPREFERABLE, $this->ispreferable);
        if ($this->isColumnModified(ActionTypePeer::PRESCRIBEDTYPE_ID)) $criteria->add(ActionTypePeer::PRESCRIBEDTYPE_ID, $this->prescribedtype_id);
        if ($this->isColumnModified(ActionTypePeer::SHEDULE_ID)) $criteria->add(ActionTypePeer::SHEDULE_ID, $this->shedule_id);
        if ($this->isColumnModified(ActionTypePeer::ISREQUIREDCOORDINATION)) $criteria->add(ActionTypePeer::ISREQUIREDCOORDINATION, $this->isrequiredcoordination);
        if ($this->isColumnModified(ActionTypePeer::ISREQUIREDTISSUE)) $criteria->add(ActionTypePeer::ISREQUIREDTISSUE, $this->isrequiredtissue);
        if ($this->isColumnModified(ActionTypePeer::TESTTUBETYPE_ID)) $criteria->add(ActionTypePeer::TESTTUBETYPE_ID, $this->testtubetype_id);
        if ($this->isColumnModified(ActionTypePeer::JOBTYPE_ID)) $criteria->add(ActionTypePeer::JOBTYPE_ID, $this->jobtype_id);
        if ($this->isColumnModified(ActionTypePeer::MNEM)) $criteria->add(ActionTypePeer::MNEM, $this->mnem);

        return $criteria;
    }

    /**
     * Builds a Criteria object containing the primary key for this object.
     *
     * Unlike buildCriteria() this method includes the primary key values regardless
     * of whether or not they have been modified.
     *
     * @return Criteria The Criteria object containing value(s) for primary key(s).
     */
    public function buildPkeyCriteria()
    {
        $criteria = new Criteria(ActionTypePeer::DATABASE_NAME);
        $criteria->add(ActionTypePeer::ID, $this->id);

        return $criteria;
    }

    /**
     * Returns the primary key for this object (row).
     * @return int
     */
    public function getPrimaryKey()
    {
        return $this->getid();
    }

    /**
     * Generic method to set the primary key (id column).
     *
     * @param  int $key Primary key.
     * @return void
     */
    public function setPrimaryKey($key)
    {
        $this->setid($key);
    }

    /**
     * Returns true if the primary key for this object is null.
     * @return boolean
     */
    public function isPrimaryKeyNull()
    {

        return null === $this->getid();
    }

    /**
     * Sets contents of passed object to values from current object.
     *
     * If desired, this method can also make copies of all associated (fkey referrers)
     * objects.
     *
     * @param object $copyObj An object of ActionType (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setcreateDatetime($this->getcreateDatetime());
        $copyObj->setcreatePersonId($this->getcreatePersonId());
        $copyObj->setmodifyDatetime($this->getmodifyDatetime());
        $copyObj->setmodifyPersonId($this->getmodifyPersonId());
        $copyObj->setdeleted($this->getdeleted());
        $copyObj->setClass($this->getClass());
        $copyObj->setgroupId($this->getgroupId());
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());
        $copyObj->settitle($this->gettitle());
        $copyObj->setflatCode($this->getflatCode());
        $copyObj->setsex($this->getsex());
        $copyObj->setage($this->getage());
        $copyObj->setageBu($this->getageBu());
        $copyObj->setageBc($this->getageBc());
        $copyObj->setageEu($this->getageEu());
        $copyObj->setageEc($this->getageEc());
        $copyObj->setoffice($this->getoffice());
        $copyObj->setshowInForm($this->getshowInForm());
        $copyObj->setgenTimeTable($this->getgenTimeTable());
        $copyObj->setserviceId($this->getserviceId());
        $copyObj->setquotaTypeId($this->getquotaTypeId());
        $copyObj->setcontext($this->getcontext());
        $copyObj->setamount($this->getamount());
        $copyObj->setamountEvaluation($this->getamountEvaluation());
        $copyObj->setdefaultStatus($this->getdefaultStatus());
        $copyObj->setdefaultDirectionDate($this->getdefaultDirectionDate());
        $copyObj->setdefaultPlannedEndDate($this->getdefaultPlannedEndDate());
        $copyObj->setdefaultEndDate($this->getdefaultEndDate());
        $copyObj->setdefaultExecPersonId($this->getdefaultExecPersonId());
        $copyObj->setdefaultPersonInEvent($this->getdefaultPersonInEvent());
        $copyObj->setdefaultPersonInEditor($this->getdefaultPersonInEditor());
        $copyObj->setmaxOccursInEvent($this->getmaxOccursInEvent());
        $copyObj->setshowTime($this->getshowTime());
        $copyObj->setisMes($this->getisMes());
        $copyObj->setnomenclativeServiceId($this->getnomenclativeServiceId());
        $copyObj->setisPreferable($this->getisPreferable());
        $copyObj->setprescribedTypeId($this->getprescribedTypeId());
        $copyObj->setsheduleId($this->getsheduleId());
        $copyObj->setisRequiredCoordination($this->getisRequiredCoordination());
        $copyObj->setisRequiredTissue($this->getisRequiredTissue());
        $copyObj->settestTubeTypeId($this->gettestTubeTypeId());
        $copyObj->setjobTypeId($this->getjobTypeId());
        $copyObj->setmnem($this->getmnem());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActions() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addAction($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getActionPropertyTypesRelatedByactionTypeId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionPropertyTypeRelatedByactionTypeId($relObj->copy($deepCopy));
                }
            }

            $relObj = $this->getActionPropertyTypeRelatedByid();
            if ($relObj) {
                $copyObj->setActionPropertyTypeRelatedByid($relObj->copy($deepCopy));
            }

            //unflag object copy
            $this->startCopy = false;
        } // if ($deepCopy)

        if ($makeNew) {
            $copyObj->setNew(true);
            $copyObj->setid(NULL); // this is a auto-increment column, so set to default value
        }
    }

    /**
     * Makes a copy of this object that will be inserted as a new row in table when saved.
     * It creates a new object filling in the simple attributes, but skipping any primary
     * keys that are defined for the table.
     *
     * If desired, this method can also make copies of all associated (fkey referrers)
     * objects.
     *
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @return ActionType Clone of current object.
     * @throws PropelException
     */
    public function copy($deepCopy = false)
    {
        // we use get_class(), because this might be a subclass
        $clazz = get_class($this);
        $copyObj = new $clazz();
        $this->copyInto($copyObj, $deepCopy);

        return $copyObj;
    }

    /**
     * Returns a peer instance associated with this om.
     *
     * Since Peer classes are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     *
     * @return ActionTypePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new ActionTypePeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a ActionPropertyType object.
     *
     * @param             ActionPropertyType $v
     * @return ActionType The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyTypeRelatedByid(ActionPropertyType $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getactionTypeId());
        }

        $this->aActionPropertyTypeRelatedByid = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionTypeRelatedByid($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyType object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyType The associated ActionPropertyType object.
     * @throws PropelException
     */
    public function getActionPropertyTypeRelatedByid(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyTypeRelatedByid === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyTypeRelatedByid = ActionPropertyTypeQuery::create()
                ->filterByActionTypeRelatedByid($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyTypeRelatedByid->setActionTypeRelatedByid($this);
        }

        return $this->aActionPropertyTypeRelatedByid;
    }


    /**
     * Initializes a collection based on the name of a relation.
     * Avoids crafting an 'init[$relationName]s' method name
     * that wouldn't work when StandardEnglishPluralizer is used.
     *
     * @param string $relationName The name of the relation to initialize
     * @return void
     */
    public function initRelation($relationName)
    {
        if ('Action' == $relationName) {
            $this->initActions();
        }
        if ('ActionPropertyTypeRelatedByactionTypeId' == $relationName) {
            $this->initActionPropertyTypesRelatedByactionTypeId();
        }
    }

    /**
     * Clears out the collActions collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return ActionType The current object (for fluent API support)
     * @see        addActions()
     */
    public function clearActions()
    {
        $this->collActions = null; // important to set this to null since that means it is uninitialized
        $this->collActionsPartial = null;

        return $this;
    }

    /**
     * reset is the collActions collection loaded partially
     *
     * @return void
     */
    public function resetPartialActions($v = true)
    {
        $this->collActionsPartial = $v;
    }

    /**
     * Initializes the collActions collection.
     *
     * By default this just sets the collActions collection to an empty array (like clearcollActions());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActions($overrideExisting = true)
    {
        if (null !== $this->collActions && !$overrideExisting) {
            return;
        }
        $this->collActions = new PropelObjectCollection();
        $this->collActions->setModel('Action');
    }

    /**
     * Gets an array of Action objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this ActionType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Action[] List of Action objects
     * @throws PropelException
     */
    public function getActions($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionsPartial && !$this->isNew();
        if (null === $this->collActions || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActions) {
                // return empty collection
                $this->initActions();
            } else {
                $collActions = ActionQuery::create(null, $criteria)
                    ->filterByActionType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionsPartial && count($collActions)) {
                      $this->initActions(false);

                      foreach($collActions as $obj) {
                        if (false == $this->collActions->contains($obj)) {
                          $this->collActions->append($obj);
                        }
                      }

                      $this->collActionsPartial = true;
                    }

                    $collActions->getInternalIterator()->rewind();
                    return $collActions;
                }

                if($partial && $this->collActions) {
                    foreach($this->collActions as $obj) {
                        if($obj->isNew()) {
                            $collActions[] = $obj;
                        }
                    }
                }

                $this->collActions = $collActions;
                $this->collActionsPartial = false;
            }
        }

        return $this->collActions;
    }

    /**
     * Sets a collection of Action objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actions A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return ActionType The current object (for fluent API support)
     */
    public function setActions(PropelCollection $actions, PropelPDO $con = null)
    {
        $actionsToDelete = $this->getActions(new Criteria(), $con)->diff($actions);

        $this->actionsScheduledForDeletion = unserialize(serialize($actionsToDelete));

        foreach ($actionsToDelete as $actionRemoved) {
            $actionRemoved->setActionType(null);
        }

        $this->collActions = null;
        foreach ($actions as $action) {
            $this->addAction($action);
        }

        $this->collActions = $actions;
        $this->collActionsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Action objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Action objects.
     * @throws PropelException
     */
    public function countActions(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionsPartial && !$this->isNew();
        if (null === $this->collActions || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActions) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActions());
            }
            $query = ActionQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByActionType($this)
                ->count($con);
        }

        return count($this->collActions);
    }

    /**
     * Method called to associate a Action object to this object
     * through the Action foreign key attribute.
     *
     * @param    Action $l Action
     * @return ActionType The current object (for fluent API support)
     */
    public function addAction(Action $l)
    {
        if ($this->collActions === null) {
            $this->initActions();
            $this->collActionsPartial = true;
        }
        if (!in_array($l, $this->collActions->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddAction($l);
        }

        return $this;
    }

    /**
     * @param	Action $action The action object to add.
     */
    protected function doAddAction($action)
    {
        $this->collActions[]= $action;
        $action->setActionType($this);
    }

    /**
     * @param	Action $action The action object to remove.
     * @return ActionType The current object (for fluent API support)
     */
    public function removeAction($action)
    {
        if ($this->getActions()->contains($action)) {
            $this->collActions->remove($this->collActions->search($action));
            if (null === $this->actionsScheduledForDeletion) {
                $this->actionsScheduledForDeletion = clone $this->collActions;
                $this->actionsScheduledForDeletion->clear();
            }
            $this->actionsScheduledForDeletion[]= clone $action;
            $action->setActionType(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionType is new, it will return
     * an empty collection; or if this ActionType has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsJoinEvent($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('Event', $join_behavior);

        return $this->getActions($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionType is new, it will return
     * an empty collection; or if this ActionType has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsJoinCreatePerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('CreatePerson', $join_behavior);

        return $this->getActions($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionType is new, it will return
     * an empty collection; or if this ActionType has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsJoinModifyPerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('ModifyPerson', $join_behavior);

        return $this->getActions($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionType is new, it will return
     * an empty collection; or if this ActionType has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsJoinSetPerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('SetPerson', $join_behavior);

        return $this->getActions($query, $con);
    }

    /**
     * Clears out the collActionPropertyTypesRelatedByactionTypeId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return ActionType The current object (for fluent API support)
     * @see        addActionPropertyTypesRelatedByactionTypeId()
     */
    public function clearActionPropertyTypesRelatedByactionTypeId()
    {
        $this->collActionPropertyTypesRelatedByactionTypeId = null; // important to set this to null since that means it is uninitialized
        $this->collActionPropertyTypesRelatedByactionTypeIdPartial = null;

        return $this;
    }

    /**
     * reset is the collActionPropertyTypesRelatedByactionTypeId collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionPropertyTypesRelatedByactionTypeId($v = true)
    {
        $this->collActionPropertyTypesRelatedByactionTypeIdPartial = $v;
    }

    /**
     * Initializes the collActionPropertyTypesRelatedByactionTypeId collection.
     *
     * By default this just sets the collActionPropertyTypesRelatedByactionTypeId collection to an empty array (like clearcollActionPropertyTypesRelatedByactionTypeId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionPropertyTypesRelatedByactionTypeId($overrideExisting = true)
    {
        if (null !== $this->collActionPropertyTypesRelatedByactionTypeId && !$overrideExisting) {
            return;
        }
        $this->collActionPropertyTypesRelatedByactionTypeId = new PropelObjectCollection();
        $this->collActionPropertyTypesRelatedByactionTypeId->setModel('ActionPropertyType');
    }

    /**
     * Gets an array of ActionPropertyType objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this ActionType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ActionPropertyType[] List of ActionPropertyType objects
     * @throws PropelException
     */
    public function getActionPropertyTypesRelatedByactionTypeId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyTypesRelatedByactionTypeIdPartial && !$this->isNew();
        if (null === $this->collActionPropertyTypesRelatedByactionTypeId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyTypesRelatedByactionTypeId) {
                // return empty collection
                $this->initActionPropertyTypesRelatedByactionTypeId();
            } else {
                $collActionPropertyTypesRelatedByactionTypeId = ActionPropertyTypeQuery::create(null, $criteria)
                    ->filterByActionTypeRelatedByactionTypeId($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionPropertyTypesRelatedByactionTypeIdPartial && count($collActionPropertyTypesRelatedByactionTypeId)) {
                      $this->initActionPropertyTypesRelatedByactionTypeId(false);

                      foreach($collActionPropertyTypesRelatedByactionTypeId as $obj) {
                        if (false == $this->collActionPropertyTypesRelatedByactionTypeId->contains($obj)) {
                          $this->collActionPropertyTypesRelatedByactionTypeId->append($obj);
                        }
                      }

                      $this->collActionPropertyTypesRelatedByactionTypeIdPartial = true;
                    }

                    $collActionPropertyTypesRelatedByactionTypeId->getInternalIterator()->rewind();
                    return $collActionPropertyTypesRelatedByactionTypeId;
                }

                if($partial && $this->collActionPropertyTypesRelatedByactionTypeId) {
                    foreach($this->collActionPropertyTypesRelatedByactionTypeId as $obj) {
                        if($obj->isNew()) {
                            $collActionPropertyTypesRelatedByactionTypeId[] = $obj;
                        }
                    }
                }

                $this->collActionPropertyTypesRelatedByactionTypeId = $collActionPropertyTypesRelatedByactionTypeId;
                $this->collActionPropertyTypesRelatedByactionTypeIdPartial = false;
            }
        }

        return $this->collActionPropertyTypesRelatedByactionTypeId;
    }

    /**
     * Sets a collection of ActionPropertyTypeRelatedByactionTypeId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionPropertyTypesRelatedByactionTypeId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return ActionType The current object (for fluent API support)
     */
    public function setActionPropertyTypesRelatedByactionTypeId(PropelCollection $actionPropertyTypesRelatedByactionTypeId, PropelPDO $con = null)
    {
        $actionPropertyTypesRelatedByactionTypeIdToDelete = $this->getActionPropertyTypesRelatedByactionTypeId(new Criteria(), $con)->diff($actionPropertyTypesRelatedByactionTypeId);

        $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion = unserialize(serialize($actionPropertyTypesRelatedByactionTypeIdToDelete));

        foreach ($actionPropertyTypesRelatedByactionTypeIdToDelete as $actionPropertyTypeRelatedByactionTypeIdRemoved) {
            $actionPropertyTypeRelatedByactionTypeIdRemoved->setActionTypeRelatedByactionTypeId(null);
        }

        $this->collActionPropertyTypesRelatedByactionTypeId = null;
        foreach ($actionPropertyTypesRelatedByactionTypeId as $actionPropertyTypeRelatedByactionTypeId) {
            $this->addActionPropertyTypeRelatedByactionTypeId($actionPropertyTypeRelatedByactionTypeId);
        }

        $this->collActionPropertyTypesRelatedByactionTypeId = $actionPropertyTypesRelatedByactionTypeId;
        $this->collActionPropertyTypesRelatedByactionTypeIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ActionPropertyType objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ActionPropertyType objects.
     * @throws PropelException
     */
    public function countActionPropertyTypesRelatedByactionTypeId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyTypesRelatedByactionTypeIdPartial && !$this->isNew();
        if (null === $this->collActionPropertyTypesRelatedByactionTypeId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyTypesRelatedByactionTypeId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionPropertyTypesRelatedByactionTypeId());
            }
            $query = ActionPropertyTypeQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByActionTypeRelatedByactionTypeId($this)
                ->count($con);
        }

        return count($this->collActionPropertyTypesRelatedByactionTypeId);
    }

    /**
     * Method called to associate a ActionPropertyType object to this object
     * through the ActionPropertyType foreign key attribute.
     *
     * @param    ActionPropertyType $l ActionPropertyType
     * @return ActionType The current object (for fluent API support)
     */
    public function addActionPropertyTypeRelatedByactionTypeId(ActionPropertyType $l)
    {
        if ($this->collActionPropertyTypesRelatedByactionTypeId === null) {
            $this->initActionPropertyTypesRelatedByactionTypeId();
            $this->collActionPropertyTypesRelatedByactionTypeIdPartial = true;
        }
        if (!in_array($l, $this->collActionPropertyTypesRelatedByactionTypeId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionPropertyTypeRelatedByactionTypeId($l);
        }

        return $this;
    }

    /**
     * @param	ActionPropertyTypeRelatedByactionTypeId $actionPropertyTypeRelatedByactionTypeId The actionPropertyTypeRelatedByactionTypeId object to add.
     */
    protected function doAddActionPropertyTypeRelatedByactionTypeId($actionPropertyTypeRelatedByactionTypeId)
    {
        $this->collActionPropertyTypesRelatedByactionTypeId[]= $actionPropertyTypeRelatedByactionTypeId;
        $actionPropertyTypeRelatedByactionTypeId->setActionTypeRelatedByactionTypeId($this);
    }

    /**
     * @param	ActionPropertyTypeRelatedByactionTypeId $actionPropertyTypeRelatedByactionTypeId The actionPropertyTypeRelatedByactionTypeId object to remove.
     * @return ActionType The current object (for fluent API support)
     */
    public function removeActionPropertyTypeRelatedByactionTypeId($actionPropertyTypeRelatedByactionTypeId)
    {
        if ($this->getActionPropertyTypesRelatedByactionTypeId()->contains($actionPropertyTypeRelatedByactionTypeId)) {
            $this->collActionPropertyTypesRelatedByactionTypeId->remove($this->collActionPropertyTypesRelatedByactionTypeId->search($actionPropertyTypeRelatedByactionTypeId));
            if (null === $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion) {
                $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion = clone $this->collActionPropertyTypesRelatedByactionTypeId;
                $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion->clear();
            }
            $this->actionPropertyTypesRelatedByactionTypeIdScheduledForDeletion[]= clone $actionPropertyTypeRelatedByactionTypeId;
            $actionPropertyTypeRelatedByactionTypeId->setActionTypeRelatedByactionTypeId(null);
        }

        return $this;
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->createdatetime = null;
        $this->createperson_id = null;
        $this->modifydatetime = null;
        $this->modifyperson_id = null;
        $this->deleted = null;
        $this->class = null;
        $this->group_id = null;
        $this->code = null;
        $this->name = null;
        $this->title = null;
        $this->flatcode = null;
        $this->sex = null;
        $this->age = null;
        $this->age_bu = null;
        $this->age_bc = null;
        $this->age_eu = null;
        $this->age_ec = null;
        $this->office = null;
        $this->showinform = null;
        $this->gentimetable = null;
        $this->service_id = null;
        $this->quotatype_id = null;
        $this->context = null;
        $this->amount = null;
        $this->amountevaluation = null;
        $this->defaultstatus = null;
        $this->defaultdirectiondate = null;
        $this->defaultplannedenddate = null;
        $this->defaultenddate = null;
        $this->defaultexecperson_id = null;
        $this->defaultpersoninevent = null;
        $this->defaultpersonineditor = null;
        $this->maxoccursinevent = null;
        $this->showtime = null;
        $this->ismes = null;
        $this->nomenclativeservice_id = null;
        $this->ispreferable = null;
        $this->prescribedtype_id = null;
        $this->shedule_id = null;
        $this->isrequiredcoordination = null;
        $this->isrequiredtissue = null;
        $this->testtubetype_id = null;
        $this->jobtype_id = null;
        $this->mnem = null;
        $this->alreadyInSave = false;
        $this->alreadyInValidation = false;
        $this->alreadyInClearAllReferencesDeep = false;
        $this->clearAllReferences();
        $this->applyDefaultValues();
        $this->resetModified();
        $this->setNew(true);
        $this->setDeleted(false);
    }

    /**
     * Resets all references to other model objects or collections of model objects.
     *
     * This method is a user-space workaround for PHP's inability to garbage collect
     * objects with circular references (even in PHP 5.3). This is currently necessary
     * when using Propel in certain daemon or large-volumne/high-memory operations.
     *
     * @param boolean $deep Whether to also clear the references on all referrer objects.
     */
    public function clearAllReferences($deep = false)
    {
        if ($deep && !$this->alreadyInClearAllReferencesDeep) {
            $this->alreadyInClearAllReferencesDeep = true;
            if ($this->collActions) {
                foreach ($this->collActions as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collActionPropertyTypesRelatedByactionTypeId) {
                foreach ($this->collActionPropertyTypesRelatedByactionTypeId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aActionPropertyTypeRelatedByid instanceof Persistent) {
              $this->aActionPropertyTypeRelatedByid->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActions instanceof PropelCollection) {
            $this->collActions->clearIterator();
        }
        $this->collActions = null;
        if ($this->collActionPropertyTypesRelatedByactionTypeId instanceof PropelCollection) {
            $this->collActionPropertyTypesRelatedByactionTypeId->clearIterator();
        }
        $this->collActionPropertyTypesRelatedByactionTypeId = null;
        $this->aActionPropertyTypeRelatedByid = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(ActionTypePeer::DEFAULT_STRING_FORMAT);
    }

    /**
     * return true is the object is in saving state
     *
     * @return boolean
     */
    public function isAlreadyInSave()
    {
        return $this->alreadyInSave;
    }

    // timestampable behavior

    /**
     * Mark the current object so that the update date doesn't get updated during next save
     *
     * @return     ActionType The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = ActionTypePeer::MODIFYDATETIME;

        return $this;
    }

}
