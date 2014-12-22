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
use Webmis\Models\Event;
use Webmis\Models\EventQuery;
use Webmis\Models\EventType;
use Webmis\Models\EventTypePeer;
use Webmis\Models\EventTypeQuery;
use Webmis\Models\RbEventTypePurpose;
use Webmis\Models\RbEventTypePurposeQuery;
use Webmis\Models\RbResult;
use Webmis\Models\RbResultQuery;

/**
 * Base class that represents a row from the 'EventType' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseEventType extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\EventTypePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        EventTypePeer
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
     * The value for the purpose_id field.
     * @var        int
     */
    protected $purpose_id;

    /**
     * The value for the finance_id field.
     * @var        int
     */
    protected $finance_id;

    /**
     * The value for the scene_id field.
     * @var        int
     */
    protected $scene_id;

    /**
     * The value for the visitservicemodifier field.
     * @var        string
     */
    protected $visitservicemodifier;

    /**
     * The value for the visitservicefilter field.
     * @var        string
     */
    protected $visitservicefilter;

    /**
     * The value for the visitfinance field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $visitfinance;

    /**
     * The value for the actionfinance field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $actionfinance;

    /**
     * The value for the period field.
     * @var        int
     */
    protected $period;

    /**
     * The value for the singleinperiod field.
     * @var        int
     */
    protected $singleinperiod;

    /**
     * The value for the islong field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $islong;

    /**
     * The value for the dateinput field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $dateinput;

    /**
     * The value for the service_id field.
     * @var        int
     */
    protected $service_id;

    /**
     * The value for the context field.
     * @var        string
     */
    protected $context;

    /**
     * The value for the form field.
     * @var        string
     */
    protected $form;

    /**
     * The value for the minduration field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $minduration;

    /**
     * The value for the maxduration field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $maxduration;

    /**
     * The value for the showstatusactionsinplanner field.
     * Note: this column has a database default value of: true
     * @var        boolean
     */
    protected $showstatusactionsinplanner;

    /**
     * The value for the showdiagnosticactionsinplanner field.
     * Note: this column has a database default value of: true
     * @var        boolean
     */
    protected $showdiagnosticactionsinplanner;

    /**
     * The value for the showcureactionsinplanner field.
     * Note: this column has a database default value of: true
     * @var        boolean
     */
    protected $showcureactionsinplanner;

    /**
     * The value for the showmiscactionsinplanner field.
     * Note: this column has a database default value of: true
     * @var        boolean
     */
    protected $showmiscactionsinplanner;

    /**
     * The value for the limitstatusactionsinput field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $limitstatusactionsinput;

    /**
     * The value for the limitdiagnosticactionsinput field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $limitdiagnosticactionsinput;

    /**
     * The value for the limitcureactionsinput field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $limitcureactionsinput;

    /**
     * The value for the limitmiscactionsinput field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $limitmiscactionsinput;

    /**
     * The value for the showtime field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $showtime;

    /**
     * The value for the medicalaidtype_id field.
     * @var        int
     */
    protected $medicalaidtype_id;

    /**
     * The value for the eventprofile_id field.
     * @var        int
     */
    protected $eventprofile_id;

    /**
     * The value for the mesrequired field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $mesrequired;

    /**
     * The value for the mescodemask field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $mescodemask;

    /**
     * The value for the mesnamemask field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $mesnamemask;

    /**
     * The value for the counter_id field.
     * @var        int
     */
    protected $counter_id;

    /**
     * The value for the isexternal field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isexternal;

    /**
     * The value for the isassistant field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isassistant;

    /**
     * The value for the iscurator field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $iscurator;

    /**
     * The value for the canhavepayableactions field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $canhavepayableactions;

    /**
     * The value for the isrequiredcoordination field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isrequiredcoordination;

    /**
     * The value for the isorgstructurepriority field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isorgstructurepriority;

    /**
     * The value for the istakentissue field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $istakentissue;

    /**
     * The value for the sex field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $sex;

    /**
     * The value for the age field.
     * @var        string
     */
    protected $age;

    /**
     * The value for the rbmedicalkind_id field.
     * @var        int
     */
    protected $rbmedicalkind_id;

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
     * The value for the requesttype_id field.
     * @var        int
     */
    protected $requesttype_id;

    /**
     * @var        RbEventTypePurpose
     */
    protected $aRbEventTypePurpose;

    /**
     * @var        RbResult
     */
    protected $aRbResult;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEvents;
    protected $collEventsPartial;

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
    protected $eventsScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->visitfinance = false;
        $this->actionfinance = false;
        $this->islong = false;
        $this->dateinput = false;
        $this->minduration = 0;
        $this->maxduration = 0;
        $this->showstatusactionsinplanner = true;
        $this->showdiagnosticactionsinplanner = true;
        $this->showcureactionsinplanner = true;
        $this->showmiscactionsinplanner = true;
        $this->limitstatusactionsinput = false;
        $this->limitdiagnosticactionsinput = false;
        $this->limitcureactionsinput = false;
        $this->limitmiscactionsinput = false;
        $this->showtime = false;
        $this->mesrequired = 0;
        $this->mescodemask = '';
        $this->mesnamemask = '';
        $this->isexternal = false;
        $this->isassistant = false;
        $this->iscurator = false;
        $this->canhavepayableactions = false;
        $this->isrequiredcoordination = false;
        $this->isorgstructurepriority = false;
        $this->istakentissue = false;
        $this->sex = 0;
    }

    /**
     * Initializes internal state of BaseEventType object.
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
     * Get the [purpose_id] column value.
     *
     * @return int
     */
    public function getpurposeId()
    {
        return $this->purpose_id;
    }

    /**
     * Get the [finance_id] column value.
     *
     * @return int
     */
    public function getfinanceId()
    {
        return $this->finance_id;
    }

    /**
     * Get the [scene_id] column value.
     *
     * @return int
     */
    public function getsceneId()
    {
        return $this->scene_id;
    }

    /**
     * Get the [visitservicemodifier] column value.
     *
     * @return string
     */
    public function getvisitServiceModifier()
    {
        return $this->visitservicemodifier;
    }

    /**
     * Get the [visitservicefilter] column value.
     *
     * @return string
     */
    public function getvisitServiceFilter()
    {
        return $this->visitservicefilter;
    }

    /**
     * Get the [visitfinance] column value.
     *
     * @return boolean
     */
    public function getvisitFinance()
    {
        return $this->visitfinance;
    }

    /**
     * Get the [actionfinance] column value.
     *
     * @return boolean
     */
    public function getactionFinance()
    {
        return $this->actionfinance;
    }

    /**
     * Get the [period] column value.
     *
     * @return int
     */
    public function getperiod()
    {
        return $this->period;
    }

    /**
     * Get the [singleinperiod] column value.
     *
     * @return int
     */
    public function getsingleinPeriod()
    {
        return $this->singleinperiod;
    }

    /**
     * Get the [islong] column value.
     *
     * @return boolean
     */
    public function getisLong()
    {
        return $this->islong;
    }

    /**
     * Get the [dateinput] column value.
     *
     * @return boolean
     */
    public function getdateInput()
    {
        return $this->dateinput;
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
     * Get the [context] column value.
     *
     * @return string
     */
    public function getcontext()
    {
        return $this->context;
    }

    /**
     * Get the [form] column value.
     *
     * @return string
     */
    public function getform()
    {
        return $this->form;
    }

    /**
     * Get the [minduration] column value.
     *
     * @return int
     */
    public function getminDuration()
    {
        return $this->minduration;
    }

    /**
     * Get the [maxduration] column value.
     *
     * @return int
     */
    public function getmaxDuration()
    {
        return $this->maxduration;
    }

    /**
     * Get the [showstatusactionsinplanner] column value.
     *
     * @return boolean
     */
    public function getshowStatusActionsInPlanner()
    {
        return $this->showstatusactionsinplanner;
    }

    /**
     * Get the [showdiagnosticactionsinplanner] column value.
     *
     * @return boolean
     */
    public function getshowDiagnosticActionsInPlanner()
    {
        return $this->showdiagnosticactionsinplanner;
    }

    /**
     * Get the [showcureactionsinplanner] column value.
     *
     * @return boolean
     */
    public function getshowCureActionsInPlanner()
    {
        return $this->showcureactionsinplanner;
    }

    /**
     * Get the [showmiscactionsinplanner] column value.
     *
     * @return boolean
     */
    public function getshowMiscActionsInPlanner()
    {
        return $this->showmiscactionsinplanner;
    }

    /**
     * Get the [limitstatusactionsinput] column value.
     *
     * @return boolean
     */
    public function getlimitStatusActionsInput()
    {
        return $this->limitstatusactionsinput;
    }

    /**
     * Get the [limitdiagnosticactionsinput] column value.
     *
     * @return boolean
     */
    public function getlimitDiagnosticActionsInput()
    {
        return $this->limitdiagnosticactionsinput;
    }

    /**
     * Get the [limitcureactionsinput] column value.
     *
     * @return boolean
     */
    public function getlimitCureActionsInput()
    {
        return $this->limitcureactionsinput;
    }

    /**
     * Get the [limitmiscactionsinput] column value.
     *
     * @return boolean
     */
    public function getlimitMiscActionsInput()
    {
        return $this->limitmiscactionsinput;
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
     * Get the [medicalaidtype_id] column value.
     *
     * @return int
     */
    public function getmedicalAidTypeId()
    {
        return $this->medicalaidtype_id;
    }

    /**
     * Get the [eventprofile_id] column value.
     *
     * @return int
     */
    public function geteventProfileId()
    {
        return $this->eventprofile_id;
    }

    /**
     * Get the [mesrequired] column value.
     *
     * @return int
     */
    public function getmesRequired()
    {
        return $this->mesrequired;
    }

    /**
     * Get the [mescodemask] column value.
     *
     * @return string
     */
    public function getmesCodeMask()
    {
        return $this->mescodemask;
    }

    /**
     * Get the [mesnamemask] column value.
     *
     * @return string
     */
    public function getmesNameMask()
    {
        return $this->mesnamemask;
    }

    /**
     * Get the [counter_id] column value.
     *
     * @return int
     */
    public function getcounterId()
    {
        return $this->counter_id;
    }

    /**
     * Get the [isexternal] column value.
     *
     * @return boolean
     */
    public function getisExternal()
    {
        return $this->isexternal;
    }

    /**
     * Get the [isassistant] column value.
     *
     * @return boolean
     */
    public function getisAssistant()
    {
        return $this->isassistant;
    }

    /**
     * Get the [iscurator] column value.
     *
     * @return boolean
     */
    public function getisCurator()
    {
        return $this->iscurator;
    }

    /**
     * Get the [canhavepayableactions] column value.
     *
     * @return boolean
     */
    public function getcanHavePayableActions()
    {
        return $this->canhavepayableactions;
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
     * Get the [isorgstructurepriority] column value.
     *
     * @return boolean
     */
    public function getisOrgStructurePriority()
    {
        return $this->isorgstructurepriority;
    }

    /**
     * Get the [istakentissue] column value.
     *
     * @return boolean
     */
    public function getisTakenTissue()
    {
        return $this->istakentissue;
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
     * Get the [rbmedicalkind_id] column value.
     *
     * @return int
     */
    public function getrbMedicalKindId()
    {
        return $this->rbmedicalkind_id;
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
     * Get the [requesttype_id] column value.
     *
     * @return int
     */
    public function getrequestTypeId()
    {
        return $this->requesttype_id;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = EventTypePeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return EventType The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = EventTypePeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = EventTypePeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return EventType The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = EventTypePeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = EventTypePeer::MODIFYPERSON_ID;
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
     * @return EventType The current object (for fluent API support)
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
            $this->modifiedColumns[] = EventTypePeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = EventTypePeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = EventTypePeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [purpose_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setpurposeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->purpose_id !== $v) {
            $this->purpose_id = $v;
            $this->modifiedColumns[] = EventTypePeer::PURPOSE_ID;
        }

        if ($this->aRbEventTypePurpose !== null && $this->aRbEventTypePurpose->getid() !== $v) {
            $this->aRbEventTypePurpose = null;
        }

        if ($this->aRbResult !== null && $this->aRbResult->geteventPurposeId() !== $v) {
            $this->aRbResult = null;
        }


        return $this;
    } // setpurposeId()

    /**
     * Set the value of [finance_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setfinanceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->finance_id !== $v) {
            $this->finance_id = $v;
            $this->modifiedColumns[] = EventTypePeer::FINANCE_ID;
        }


        return $this;
    } // setfinanceId()

    /**
     * Set the value of [scene_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setsceneId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->scene_id !== $v) {
            $this->scene_id = $v;
            $this->modifiedColumns[] = EventTypePeer::SCENE_ID;
        }


        return $this;
    } // setsceneId()

    /**
     * Set the value of [visitservicemodifier] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setvisitServiceModifier($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->visitservicemodifier !== $v) {
            $this->visitservicemodifier = $v;
            $this->modifiedColumns[] = EventTypePeer::VISITSERVICEMODIFIER;
        }


        return $this;
    } // setvisitServiceModifier()

    /**
     * Set the value of [visitservicefilter] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setvisitServiceFilter($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->visitservicefilter !== $v) {
            $this->visitservicefilter = $v;
            $this->modifiedColumns[] = EventTypePeer::VISITSERVICEFILTER;
        }


        return $this;
    } // setvisitServiceFilter()

    /**
     * Sets the value of the [visitfinance] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setvisitFinance($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->visitfinance !== $v) {
            $this->visitfinance = $v;
            $this->modifiedColumns[] = EventTypePeer::VISITFINANCE;
        }


        return $this;
    } // setvisitFinance()

    /**
     * Sets the value of the [actionfinance] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setactionFinance($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->actionfinance !== $v) {
            $this->actionfinance = $v;
            $this->modifiedColumns[] = EventTypePeer::ACTIONFINANCE;
        }


        return $this;
    } // setactionFinance()

    /**
     * Set the value of [period] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setperiod($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->period !== $v) {
            $this->period = $v;
            $this->modifiedColumns[] = EventTypePeer::PERIOD;
        }


        return $this;
    } // setperiod()

    /**
     * Set the value of [singleinperiod] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setsingleinPeriod($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->singleinperiod !== $v) {
            $this->singleinperiod = $v;
            $this->modifiedColumns[] = EventTypePeer::SINGLEINPERIOD;
        }


        return $this;
    } // setsingleinPeriod()

    /**
     * Sets the value of the [islong] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisLong($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->islong !== $v) {
            $this->islong = $v;
            $this->modifiedColumns[] = EventTypePeer::ISLONG;
        }


        return $this;
    } // setisLong()

    /**
     * Sets the value of the [dateinput] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setdateInput($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->dateinput !== $v) {
            $this->dateinput = $v;
            $this->modifiedColumns[] = EventTypePeer::DATEINPUT;
        }


        return $this;
    } // setdateInput()

    /**
     * Set the value of [service_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setserviceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->service_id !== $v) {
            $this->service_id = $v;
            $this->modifiedColumns[] = EventTypePeer::SERVICE_ID;
        }


        return $this;
    } // setserviceId()

    /**
     * Set the value of [context] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setcontext($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->context !== $v) {
            $this->context = $v;
            $this->modifiedColumns[] = EventTypePeer::CONTEXT;
        }


        return $this;
    } // setcontext()

    /**
     * Set the value of [form] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setform($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->form !== $v) {
            $this->form = $v;
            $this->modifiedColumns[] = EventTypePeer::FORM;
        }


        return $this;
    } // setform()

    /**
     * Set the value of [minduration] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setminDuration($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->minduration !== $v) {
            $this->minduration = $v;
            $this->modifiedColumns[] = EventTypePeer::MINDURATION;
        }


        return $this;
    } // setminDuration()

    /**
     * Set the value of [maxduration] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmaxDuration($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->maxduration !== $v) {
            $this->maxduration = $v;
            $this->modifiedColumns[] = EventTypePeer::MAXDURATION;
        }


        return $this;
    } // setmaxDuration()

    /**
     * Sets the value of the [showstatusactionsinplanner] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setshowStatusActionsInPlanner($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showstatusactionsinplanner !== $v) {
            $this->showstatusactionsinplanner = $v;
            $this->modifiedColumns[] = EventTypePeer::SHOWSTATUSACTIONSINPLANNER;
        }


        return $this;
    } // setshowStatusActionsInPlanner()

    /**
     * Sets the value of the [showdiagnosticactionsinplanner] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setshowDiagnosticActionsInPlanner($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showdiagnosticactionsinplanner !== $v) {
            $this->showdiagnosticactionsinplanner = $v;
            $this->modifiedColumns[] = EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER;
        }


        return $this;
    } // setshowDiagnosticActionsInPlanner()

    /**
     * Sets the value of the [showcureactionsinplanner] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setshowCureActionsInPlanner($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showcureactionsinplanner !== $v) {
            $this->showcureactionsinplanner = $v;
            $this->modifiedColumns[] = EventTypePeer::SHOWCUREACTIONSINPLANNER;
        }


        return $this;
    } // setshowCureActionsInPlanner()

    /**
     * Sets the value of the [showmiscactionsinplanner] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setshowMiscActionsInPlanner($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->showmiscactionsinplanner !== $v) {
            $this->showmiscactionsinplanner = $v;
            $this->modifiedColumns[] = EventTypePeer::SHOWMISCACTIONSINPLANNER;
        }


        return $this;
    } // setshowMiscActionsInPlanner()

    /**
     * Sets the value of the [limitstatusactionsinput] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setlimitStatusActionsInput($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->limitstatusactionsinput !== $v) {
            $this->limitstatusactionsinput = $v;
            $this->modifiedColumns[] = EventTypePeer::LIMITSTATUSACTIONSINPUT;
        }


        return $this;
    } // setlimitStatusActionsInput()

    /**
     * Sets the value of the [limitdiagnosticactionsinput] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setlimitDiagnosticActionsInput($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->limitdiagnosticactionsinput !== $v) {
            $this->limitdiagnosticactionsinput = $v;
            $this->modifiedColumns[] = EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT;
        }


        return $this;
    } // setlimitDiagnosticActionsInput()

    /**
     * Sets the value of the [limitcureactionsinput] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setlimitCureActionsInput($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->limitcureactionsinput !== $v) {
            $this->limitcureactionsinput = $v;
            $this->modifiedColumns[] = EventTypePeer::LIMITCUREACTIONSINPUT;
        }


        return $this;
    } // setlimitCureActionsInput()

    /**
     * Sets the value of the [limitmiscactionsinput] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setlimitMiscActionsInput($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->limitmiscactionsinput !== $v) {
            $this->limitmiscactionsinput = $v;
            $this->modifiedColumns[] = EventTypePeer::LIMITMISCACTIONSINPUT;
        }


        return $this;
    } // setlimitMiscActionsInput()

    /**
     * Sets the value of the [showtime] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
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
            $this->modifiedColumns[] = EventTypePeer::SHOWTIME;
        }


        return $this;
    } // setshowTime()

    /**
     * Set the value of [medicalaidtype_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmedicalAidTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->medicalaidtype_id !== $v) {
            $this->medicalaidtype_id = $v;
            $this->modifiedColumns[] = EventTypePeer::MEDICALAIDTYPE_ID;
        }


        return $this;
    } // setmedicalAidTypeId()

    /**
     * Set the value of [eventprofile_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function seteventProfileId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->eventprofile_id !== $v) {
            $this->eventprofile_id = $v;
            $this->modifiedColumns[] = EventTypePeer::EVENTPROFILE_ID;
        }


        return $this;
    } // seteventProfileId()

    /**
     * Set the value of [mesrequired] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmesRequired($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->mesrequired !== $v) {
            $this->mesrequired = $v;
            $this->modifiedColumns[] = EventTypePeer::MESREQUIRED;
        }


        return $this;
    } // setmesRequired()

    /**
     * Set the value of [mescodemask] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmesCodeMask($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->mescodemask !== $v) {
            $this->mescodemask = $v;
            $this->modifiedColumns[] = EventTypePeer::MESCODEMASK;
        }


        return $this;
    } // setmesCodeMask()

    /**
     * Set the value of [mesnamemask] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setmesNameMask($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->mesnamemask !== $v) {
            $this->mesnamemask = $v;
            $this->modifiedColumns[] = EventTypePeer::MESNAMEMASK;
        }


        return $this;
    } // setmesNameMask()

    /**
     * Set the value of [counter_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setcounterId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->counter_id !== $v) {
            $this->counter_id = $v;
            $this->modifiedColumns[] = EventTypePeer::COUNTER_ID;
        }


        return $this;
    } // setcounterId()

    /**
     * Sets the value of the [isexternal] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisExternal($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isexternal !== $v) {
            $this->isexternal = $v;
            $this->modifiedColumns[] = EventTypePeer::ISEXTERNAL;
        }


        return $this;
    } // setisExternal()

    /**
     * Sets the value of the [isassistant] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisAssistant($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isassistant !== $v) {
            $this->isassistant = $v;
            $this->modifiedColumns[] = EventTypePeer::ISASSISTANT;
        }


        return $this;
    } // setisAssistant()

    /**
     * Sets the value of the [iscurator] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisCurator($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->iscurator !== $v) {
            $this->iscurator = $v;
            $this->modifiedColumns[] = EventTypePeer::ISCURATOR;
        }


        return $this;
    } // setisCurator()

    /**
     * Sets the value of the [canhavepayableactions] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setcanHavePayableActions($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->canhavepayableactions !== $v) {
            $this->canhavepayableactions = $v;
            $this->modifiedColumns[] = EventTypePeer::CANHAVEPAYABLEACTIONS;
        }


        return $this;
    } // setcanHavePayableActions()

    /**
     * Sets the value of the [isrequiredcoordination] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
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
            $this->modifiedColumns[] = EventTypePeer::ISREQUIREDCOORDINATION;
        }


        return $this;
    } // setisRequiredCoordination()

    /**
     * Sets the value of the [isorgstructurepriority] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisOrgStructurePriority($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isorgstructurepriority !== $v) {
            $this->isorgstructurepriority = $v;
            $this->modifiedColumns[] = EventTypePeer::ISORGSTRUCTUREPRIORITY;
        }


        return $this;
    } // setisOrgStructurePriority()

    /**
     * Sets the value of the [istakentissue] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return EventType The current object (for fluent API support)
     */
    public function setisTakenTissue($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->istakentissue !== $v) {
            $this->istakentissue = $v;
            $this->modifiedColumns[] = EventTypePeer::ISTAKENTISSUE;
        }


        return $this;
    } // setisTakenTissue()

    /**
     * Set the value of [sex] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setsex($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->sex !== $v) {
            $this->sex = $v;
            $this->modifiedColumns[] = EventTypePeer::SEX;
        }


        return $this;
    } // setsex()

    /**
     * Set the value of [age] column.
     *
     * @param string $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setage($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->age !== $v) {
            $this->age = $v;
            $this->modifiedColumns[] = EventTypePeer::AGE;
        }


        return $this;
    } // setage()

    /**
     * Set the value of [rbmedicalkind_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setrbMedicalKindId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->rbmedicalkind_id !== $v) {
            $this->rbmedicalkind_id = $v;
            $this->modifiedColumns[] = EventTypePeer::RBMEDICALKIND_ID;
        }


        return $this;
    } // setrbMedicalKindId()

    /**
     * Set the value of [age_bu] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setageBu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bu !== $v) {
            $this->age_bu = $v;
            $this->modifiedColumns[] = EventTypePeer::AGE_BU;
        }


        return $this;
    } // setageBu()

    /**
     * Set the value of [age_bc] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setageBc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bc !== $v) {
            $this->age_bc = $v;
            $this->modifiedColumns[] = EventTypePeer::AGE_BC;
        }


        return $this;
    } // setageBc()

    /**
     * Set the value of [age_eu] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setageEu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_eu !== $v) {
            $this->age_eu = $v;
            $this->modifiedColumns[] = EventTypePeer::AGE_EU;
        }


        return $this;
    } // setageEu()

    /**
     * Set the value of [age_ec] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setageEc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_ec !== $v) {
            $this->age_ec = $v;
            $this->modifiedColumns[] = EventTypePeer::AGE_EC;
        }


        return $this;
    } // setageEc()

    /**
     * Set the value of [requesttype_id] column.
     *
     * @param int $v new value
     * @return EventType The current object (for fluent API support)
     */
    public function setrequestTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->requesttype_id !== $v) {
            $this->requesttype_id = $v;
            $this->modifiedColumns[] = EventTypePeer::REQUESTTYPE_ID;
        }


        return $this;
    } // setrequestTypeId()

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

            if ($this->visitfinance !== false) {
                return false;
            }

            if ($this->actionfinance !== false) {
                return false;
            }

            if ($this->islong !== false) {
                return false;
            }

            if ($this->dateinput !== false) {
                return false;
            }

            if ($this->minduration !== 0) {
                return false;
            }

            if ($this->maxduration !== 0) {
                return false;
            }

            if ($this->showstatusactionsinplanner !== true) {
                return false;
            }

            if ($this->showdiagnosticactionsinplanner !== true) {
                return false;
            }

            if ($this->showcureactionsinplanner !== true) {
                return false;
            }

            if ($this->showmiscactionsinplanner !== true) {
                return false;
            }

            if ($this->limitstatusactionsinput !== false) {
                return false;
            }

            if ($this->limitdiagnosticactionsinput !== false) {
                return false;
            }

            if ($this->limitcureactionsinput !== false) {
                return false;
            }

            if ($this->limitmiscactionsinput !== false) {
                return false;
            }

            if ($this->showtime !== false) {
                return false;
            }

            if ($this->mesrequired !== 0) {
                return false;
            }

            if ($this->mescodemask !== '') {
                return false;
            }

            if ($this->mesnamemask !== '') {
                return false;
            }

            if ($this->isexternal !== false) {
                return false;
            }

            if ($this->isassistant !== false) {
                return false;
            }

            if ($this->iscurator !== false) {
                return false;
            }

            if ($this->canhavepayableactions !== false) {
                return false;
            }

            if ($this->isrequiredcoordination !== false) {
                return false;
            }

            if ($this->isorgstructurepriority !== false) {
                return false;
            }

            if ($this->istakentissue !== false) {
                return false;
            }

            if ($this->sex !== 0) {
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
            $this->code = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->name = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->purpose_id = ($row[$startcol + 8] !== null) ? (int) $row[$startcol + 8] : null;
            $this->finance_id = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->scene_id = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->visitservicemodifier = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->visitservicefilter = ($row[$startcol + 12] !== null) ? (string) $row[$startcol + 12] : null;
            $this->visitfinance = ($row[$startcol + 13] !== null) ? (boolean) $row[$startcol + 13] : null;
            $this->actionfinance = ($row[$startcol + 14] !== null) ? (boolean) $row[$startcol + 14] : null;
            $this->period = ($row[$startcol + 15] !== null) ? (int) $row[$startcol + 15] : null;
            $this->singleinperiod = ($row[$startcol + 16] !== null) ? (int) $row[$startcol + 16] : null;
            $this->islong = ($row[$startcol + 17] !== null) ? (boolean) $row[$startcol + 17] : null;
            $this->dateinput = ($row[$startcol + 18] !== null) ? (boolean) $row[$startcol + 18] : null;
            $this->service_id = ($row[$startcol + 19] !== null) ? (int) $row[$startcol + 19] : null;
            $this->context = ($row[$startcol + 20] !== null) ? (string) $row[$startcol + 20] : null;
            $this->form = ($row[$startcol + 21] !== null) ? (string) $row[$startcol + 21] : null;
            $this->minduration = ($row[$startcol + 22] !== null) ? (int) $row[$startcol + 22] : null;
            $this->maxduration = ($row[$startcol + 23] !== null) ? (int) $row[$startcol + 23] : null;
            $this->showstatusactionsinplanner = ($row[$startcol + 24] !== null) ? (boolean) $row[$startcol + 24] : null;
            $this->showdiagnosticactionsinplanner = ($row[$startcol + 25] !== null) ? (boolean) $row[$startcol + 25] : null;
            $this->showcureactionsinplanner = ($row[$startcol + 26] !== null) ? (boolean) $row[$startcol + 26] : null;
            $this->showmiscactionsinplanner = ($row[$startcol + 27] !== null) ? (boolean) $row[$startcol + 27] : null;
            $this->limitstatusactionsinput = ($row[$startcol + 28] !== null) ? (boolean) $row[$startcol + 28] : null;
            $this->limitdiagnosticactionsinput = ($row[$startcol + 29] !== null) ? (boolean) $row[$startcol + 29] : null;
            $this->limitcureactionsinput = ($row[$startcol + 30] !== null) ? (boolean) $row[$startcol + 30] : null;
            $this->limitmiscactionsinput = ($row[$startcol + 31] !== null) ? (boolean) $row[$startcol + 31] : null;
            $this->showtime = ($row[$startcol + 32] !== null) ? (boolean) $row[$startcol + 32] : null;
            $this->medicalaidtype_id = ($row[$startcol + 33] !== null) ? (int) $row[$startcol + 33] : null;
            $this->eventprofile_id = ($row[$startcol + 34] !== null) ? (int) $row[$startcol + 34] : null;
            $this->mesrequired = ($row[$startcol + 35] !== null) ? (int) $row[$startcol + 35] : null;
            $this->mescodemask = ($row[$startcol + 36] !== null) ? (string) $row[$startcol + 36] : null;
            $this->mesnamemask = ($row[$startcol + 37] !== null) ? (string) $row[$startcol + 37] : null;
            $this->counter_id = ($row[$startcol + 38] !== null) ? (int) $row[$startcol + 38] : null;
            $this->isexternal = ($row[$startcol + 39] !== null) ? (boolean) $row[$startcol + 39] : null;
            $this->isassistant = ($row[$startcol + 40] !== null) ? (boolean) $row[$startcol + 40] : null;
            $this->iscurator = ($row[$startcol + 41] !== null) ? (boolean) $row[$startcol + 41] : null;
            $this->canhavepayableactions = ($row[$startcol + 42] !== null) ? (boolean) $row[$startcol + 42] : null;
            $this->isrequiredcoordination = ($row[$startcol + 43] !== null) ? (boolean) $row[$startcol + 43] : null;
            $this->isorgstructurepriority = ($row[$startcol + 44] !== null) ? (boolean) $row[$startcol + 44] : null;
            $this->istakentissue = ($row[$startcol + 45] !== null) ? (boolean) $row[$startcol + 45] : null;
            $this->sex = ($row[$startcol + 46] !== null) ? (int) $row[$startcol + 46] : null;
            $this->age = ($row[$startcol + 47] !== null) ? (string) $row[$startcol + 47] : null;
            $this->rbmedicalkind_id = ($row[$startcol + 48] !== null) ? (int) $row[$startcol + 48] : null;
            $this->age_bu = ($row[$startcol + 49] !== null) ? (int) $row[$startcol + 49] : null;
            $this->age_bc = ($row[$startcol + 50] !== null) ? (int) $row[$startcol + 50] : null;
            $this->age_eu = ($row[$startcol + 51] !== null) ? (int) $row[$startcol + 51] : null;
            $this->age_ec = ($row[$startcol + 52] !== null) ? (int) $row[$startcol + 52] : null;
            $this->requesttype_id = ($row[$startcol + 53] !== null) ? (int) $row[$startcol + 53] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 54; // 54 = EventTypePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating EventType object", $e);
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

        if ($this->aRbEventTypePurpose !== null && $this->purpose_id !== $this->aRbEventTypePurpose->getid()) {
            $this->aRbEventTypePurpose = null;
        }
        if ($this->aRbResult !== null && $this->purpose_id !== $this->aRbResult->geteventPurposeId()) {
            $this->aRbResult = null;
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
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = EventTypePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aRbEventTypePurpose = null;
            $this->aRbResult = null;
            $this->collEvents = null;

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
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = EventTypeQuery::create()
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
            $con = Propel::getConnection(EventTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(EventTypePeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(EventTypePeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(EventTypePeer::MODIFYDATETIME)) {
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
                EventTypePeer::addInstanceToPool($this);
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

            if ($this->aRbEventTypePurpose !== null) {
                if ($this->aRbEventTypePurpose->isModified() || $this->aRbEventTypePurpose->isNew()) {
                    $affectedRows += $this->aRbEventTypePurpose->save($con);
                }
                $this->setRbEventTypePurpose($this->aRbEventTypePurpose);
            }

            if ($this->aRbResult !== null) {
                if ($this->aRbResult->isModified() || $this->aRbResult->isNew()) {
                    $affectedRows += $this->aRbResult->save($con);
                }
                $this->setRbResult($this->aRbResult);
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

            if ($this->eventsScheduledForDeletion !== null) {
                if (!$this->eventsScheduledForDeletion->isEmpty()) {
                    EventQuery::create()
                        ->filterByPrimaryKeys($this->eventsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->eventsScheduledForDeletion = null;
                }
            }

            if ($this->collEvents !== null) {
                foreach ($this->collEvents as $referrerFK) {
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

        $this->modifiedColumns[] = EventTypePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . EventTypePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(EventTypePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(EventTypePeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(EventTypePeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(EventTypePeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(EventTypePeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(EventTypePeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(EventTypePeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(EventTypePeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(EventTypePeer::PURPOSE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`purpose_id`';
        }
        if ($this->isColumnModified(EventTypePeer::FINANCE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`finance_id`';
        }
        if ($this->isColumnModified(EventTypePeer::SCENE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`scene_id`';
        }
        if ($this->isColumnModified(EventTypePeer::VISITSERVICEMODIFIER)) {
            $modifiedColumns[':p' . $index++]  = '`visitServiceModifier`';
        }
        if ($this->isColumnModified(EventTypePeer::VISITSERVICEFILTER)) {
            $modifiedColumns[':p' . $index++]  = '`visitServiceFilter`';
        }
        if ($this->isColumnModified(EventTypePeer::VISITFINANCE)) {
            $modifiedColumns[':p' . $index++]  = '`visitFinance`';
        }
        if ($this->isColumnModified(EventTypePeer::ACTIONFINANCE)) {
            $modifiedColumns[':p' . $index++]  = '`actionFinance`';
        }
        if ($this->isColumnModified(EventTypePeer::PERIOD)) {
            $modifiedColumns[':p' . $index++]  = '`period`';
        }
        if ($this->isColumnModified(EventTypePeer::SINGLEINPERIOD)) {
            $modifiedColumns[':p' . $index++]  = '`singleInPeriod`';
        }
        if ($this->isColumnModified(EventTypePeer::ISLONG)) {
            $modifiedColumns[':p' . $index++]  = '`isLong`';
        }
        if ($this->isColumnModified(EventTypePeer::DATEINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`dateInput`';
        }
        if ($this->isColumnModified(EventTypePeer::SERVICE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`service_id`';
        }
        if ($this->isColumnModified(EventTypePeer::CONTEXT)) {
            $modifiedColumns[':p' . $index++]  = '`context`';
        }
        if ($this->isColumnModified(EventTypePeer::FORM)) {
            $modifiedColumns[':p' . $index++]  = '`form`';
        }
        if ($this->isColumnModified(EventTypePeer::MINDURATION)) {
            $modifiedColumns[':p' . $index++]  = '`minDuration`';
        }
        if ($this->isColumnModified(EventTypePeer::MAXDURATION)) {
            $modifiedColumns[':p' . $index++]  = '`maxDuration`';
        }
        if ($this->isColumnModified(EventTypePeer::SHOWSTATUSACTIONSINPLANNER)) {
            $modifiedColumns[':p' . $index++]  = '`showStatusActionsInPlanner`';
        }
        if ($this->isColumnModified(EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER)) {
            $modifiedColumns[':p' . $index++]  = '`showDiagnosticActionsInPlanner`';
        }
        if ($this->isColumnModified(EventTypePeer::SHOWCUREACTIONSINPLANNER)) {
            $modifiedColumns[':p' . $index++]  = '`showCureActionsInPlanner`';
        }
        if ($this->isColumnModified(EventTypePeer::SHOWMISCACTIONSINPLANNER)) {
            $modifiedColumns[':p' . $index++]  = '`showMiscActionsInPlanner`';
        }
        if ($this->isColumnModified(EventTypePeer::LIMITSTATUSACTIONSINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`limitStatusActionsInput`';
        }
        if ($this->isColumnModified(EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`limitDiagnosticActionsInput`';
        }
        if ($this->isColumnModified(EventTypePeer::LIMITCUREACTIONSINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`limitCureActionsInput`';
        }
        if ($this->isColumnModified(EventTypePeer::LIMITMISCACTIONSINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`limitMiscActionsInput`';
        }
        if ($this->isColumnModified(EventTypePeer::SHOWTIME)) {
            $modifiedColumns[':p' . $index++]  = '`showTime`';
        }
        if ($this->isColumnModified(EventTypePeer::MEDICALAIDTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`medicalAidType_id`';
        }
        if ($this->isColumnModified(EventTypePeer::EVENTPROFILE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`eventProfile_id`';
        }
        if ($this->isColumnModified(EventTypePeer::MESREQUIRED)) {
            $modifiedColumns[':p' . $index++]  = '`mesRequired`';
        }
        if ($this->isColumnModified(EventTypePeer::MESCODEMASK)) {
            $modifiedColumns[':p' . $index++]  = '`mesCodeMask`';
        }
        if ($this->isColumnModified(EventTypePeer::MESNAMEMASK)) {
            $modifiedColumns[':p' . $index++]  = '`mesNameMask`';
        }
        if ($this->isColumnModified(EventTypePeer::COUNTER_ID)) {
            $modifiedColumns[':p' . $index++]  = '`counter_id`';
        }
        if ($this->isColumnModified(EventTypePeer::ISEXTERNAL)) {
            $modifiedColumns[':p' . $index++]  = '`isExternal`';
        }
        if ($this->isColumnModified(EventTypePeer::ISASSISTANT)) {
            $modifiedColumns[':p' . $index++]  = '`isAssistant`';
        }
        if ($this->isColumnModified(EventTypePeer::ISCURATOR)) {
            $modifiedColumns[':p' . $index++]  = '`isCurator`';
        }
        if ($this->isColumnModified(EventTypePeer::CANHAVEPAYABLEACTIONS)) {
            $modifiedColumns[':p' . $index++]  = '`canHavePayableActions`';
        }
        if ($this->isColumnModified(EventTypePeer::ISREQUIREDCOORDINATION)) {
            $modifiedColumns[':p' . $index++]  = '`isRequiredCoordination`';
        }
        if ($this->isColumnModified(EventTypePeer::ISORGSTRUCTUREPRIORITY)) {
            $modifiedColumns[':p' . $index++]  = '`isOrgStructurePriority`';
        }
        if ($this->isColumnModified(EventTypePeer::ISTAKENTISSUE)) {
            $modifiedColumns[':p' . $index++]  = '`isTakenTissue`';
        }
        if ($this->isColumnModified(EventTypePeer::SEX)) {
            $modifiedColumns[':p' . $index++]  = '`sex`';
        }
        if ($this->isColumnModified(EventTypePeer::AGE)) {
            $modifiedColumns[':p' . $index++]  = '`age`';
        }
        if ($this->isColumnModified(EventTypePeer::RBMEDICALKIND_ID)) {
            $modifiedColumns[':p' . $index++]  = '`rbMedicalKind_id`';
        }
        if ($this->isColumnModified(EventTypePeer::AGE_BU)) {
            $modifiedColumns[':p' . $index++]  = '`age_bu`';
        }
        if ($this->isColumnModified(EventTypePeer::AGE_BC)) {
            $modifiedColumns[':p' . $index++]  = '`age_bc`';
        }
        if ($this->isColumnModified(EventTypePeer::AGE_EU)) {
            $modifiedColumns[':p' . $index++]  = '`age_eu`';
        }
        if ($this->isColumnModified(EventTypePeer::AGE_EC)) {
            $modifiedColumns[':p' . $index++]  = '`age_ec`';
        }
        if ($this->isColumnModified(EventTypePeer::REQUESTTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`requestType_id`';
        }

        $sql = sprintf(
            'INSERT INTO `EventType` (%s) VALUES (%s)',
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
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`purpose_id`':
                        $stmt->bindValue($identifier, $this->purpose_id, PDO::PARAM_INT);
                        break;
                    case '`finance_id`':
                        $stmt->bindValue($identifier, $this->finance_id, PDO::PARAM_INT);
                        break;
                    case '`scene_id`':
                        $stmt->bindValue($identifier, $this->scene_id, PDO::PARAM_INT);
                        break;
                    case '`visitServiceModifier`':
                        $stmt->bindValue($identifier, $this->visitservicemodifier, PDO::PARAM_STR);
                        break;
                    case '`visitServiceFilter`':
                        $stmt->bindValue($identifier, $this->visitservicefilter, PDO::PARAM_STR);
                        break;
                    case '`visitFinance`':
                        $stmt->bindValue($identifier, (int) $this->visitfinance, PDO::PARAM_INT);
                        break;
                    case '`actionFinance`':
                        $stmt->bindValue($identifier, (int) $this->actionfinance, PDO::PARAM_INT);
                        break;
                    case '`period`':
                        $stmt->bindValue($identifier, $this->period, PDO::PARAM_INT);
                        break;
                    case '`singleInPeriod`':
                        $stmt->bindValue($identifier, $this->singleinperiod, PDO::PARAM_INT);
                        break;
                    case '`isLong`':
                        $stmt->bindValue($identifier, (int) $this->islong, PDO::PARAM_INT);
                        break;
                    case '`dateInput`':
                        $stmt->bindValue($identifier, (int) $this->dateinput, PDO::PARAM_INT);
                        break;
                    case '`service_id`':
                        $stmt->bindValue($identifier, $this->service_id, PDO::PARAM_INT);
                        break;
                    case '`context`':
                        $stmt->bindValue($identifier, $this->context, PDO::PARAM_STR);
                        break;
                    case '`form`':
                        $stmt->bindValue($identifier, $this->form, PDO::PARAM_STR);
                        break;
                    case '`minDuration`':
                        $stmt->bindValue($identifier, $this->minduration, PDO::PARAM_INT);
                        break;
                    case '`maxDuration`':
                        $stmt->bindValue($identifier, $this->maxduration, PDO::PARAM_INT);
                        break;
                    case '`showStatusActionsInPlanner`':
                        $stmt->bindValue($identifier, (int) $this->showstatusactionsinplanner, PDO::PARAM_INT);
                        break;
                    case '`showDiagnosticActionsInPlanner`':
                        $stmt->bindValue($identifier, (int) $this->showdiagnosticactionsinplanner, PDO::PARAM_INT);
                        break;
                    case '`showCureActionsInPlanner`':
                        $stmt->bindValue($identifier, (int) $this->showcureactionsinplanner, PDO::PARAM_INT);
                        break;
                    case '`showMiscActionsInPlanner`':
                        $stmt->bindValue($identifier, (int) $this->showmiscactionsinplanner, PDO::PARAM_INT);
                        break;
                    case '`limitStatusActionsInput`':
                        $stmt->bindValue($identifier, (int) $this->limitstatusactionsinput, PDO::PARAM_INT);
                        break;
                    case '`limitDiagnosticActionsInput`':
                        $stmt->bindValue($identifier, (int) $this->limitdiagnosticactionsinput, PDO::PARAM_INT);
                        break;
                    case '`limitCureActionsInput`':
                        $stmt->bindValue($identifier, (int) $this->limitcureactionsinput, PDO::PARAM_INT);
                        break;
                    case '`limitMiscActionsInput`':
                        $stmt->bindValue($identifier, (int) $this->limitmiscactionsinput, PDO::PARAM_INT);
                        break;
                    case '`showTime`':
                        $stmt->bindValue($identifier, (int) $this->showtime, PDO::PARAM_INT);
                        break;
                    case '`medicalAidType_id`':
                        $stmt->bindValue($identifier, $this->medicalaidtype_id, PDO::PARAM_INT);
                        break;
                    case '`eventProfile_id`':
                        $stmt->bindValue($identifier, $this->eventprofile_id, PDO::PARAM_INT);
                        break;
                    case '`mesRequired`':
                        $stmt->bindValue($identifier, $this->mesrequired, PDO::PARAM_INT);
                        break;
                    case '`mesCodeMask`':
                        $stmt->bindValue($identifier, $this->mescodemask, PDO::PARAM_STR);
                        break;
                    case '`mesNameMask`':
                        $stmt->bindValue($identifier, $this->mesnamemask, PDO::PARAM_STR);
                        break;
                    case '`counter_id`':
                        $stmt->bindValue($identifier, $this->counter_id, PDO::PARAM_INT);
                        break;
                    case '`isExternal`':
                        $stmt->bindValue($identifier, (int) $this->isexternal, PDO::PARAM_INT);
                        break;
                    case '`isAssistant`':
                        $stmt->bindValue($identifier, (int) $this->isassistant, PDO::PARAM_INT);
                        break;
                    case '`isCurator`':
                        $stmt->bindValue($identifier, (int) $this->iscurator, PDO::PARAM_INT);
                        break;
                    case '`canHavePayableActions`':
                        $stmt->bindValue($identifier, (int) $this->canhavepayableactions, PDO::PARAM_INT);
                        break;
                    case '`isRequiredCoordination`':
                        $stmt->bindValue($identifier, (int) $this->isrequiredcoordination, PDO::PARAM_INT);
                        break;
                    case '`isOrgStructurePriority`':
                        $stmt->bindValue($identifier, (int) $this->isorgstructurepriority, PDO::PARAM_INT);
                        break;
                    case '`isTakenTissue`':
                        $stmt->bindValue($identifier, (int) $this->istakentissue, PDO::PARAM_INT);
                        break;
                    case '`sex`':
                        $stmt->bindValue($identifier, $this->sex, PDO::PARAM_INT);
                        break;
                    case '`age`':
                        $stmt->bindValue($identifier, $this->age, PDO::PARAM_STR);
                        break;
                    case '`rbMedicalKind_id`':
                        $stmt->bindValue($identifier, $this->rbmedicalkind_id, PDO::PARAM_INT);
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
                    case '`requestType_id`':
                        $stmt->bindValue($identifier, $this->requesttype_id, PDO::PARAM_INT);
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

            if ($this->aRbEventTypePurpose !== null) {
                if (!$this->aRbEventTypePurpose->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aRbEventTypePurpose->getValidationFailures());
                }
            }

            if ($this->aRbResult !== null) {
                if (!$this->aRbResult->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aRbResult->getValidationFailures());
                }
            }


            if (($retval = EventTypePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collEvents !== null) {
                    foreach ($this->collEvents as $referrerFK) {
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
        $pos = EventTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getcode();
                break;
            case 7:
                return $this->getname();
                break;
            case 8:
                return $this->getpurposeId();
                break;
            case 9:
                return $this->getfinanceId();
                break;
            case 10:
                return $this->getsceneId();
                break;
            case 11:
                return $this->getvisitServiceModifier();
                break;
            case 12:
                return $this->getvisitServiceFilter();
                break;
            case 13:
                return $this->getvisitFinance();
                break;
            case 14:
                return $this->getactionFinance();
                break;
            case 15:
                return $this->getperiod();
                break;
            case 16:
                return $this->getsingleinPeriod();
                break;
            case 17:
                return $this->getisLong();
                break;
            case 18:
                return $this->getdateInput();
                break;
            case 19:
                return $this->getserviceId();
                break;
            case 20:
                return $this->getcontext();
                break;
            case 21:
                return $this->getform();
                break;
            case 22:
                return $this->getminDuration();
                break;
            case 23:
                return $this->getmaxDuration();
                break;
            case 24:
                return $this->getshowStatusActionsInPlanner();
                break;
            case 25:
                return $this->getshowDiagnosticActionsInPlanner();
                break;
            case 26:
                return $this->getshowCureActionsInPlanner();
                break;
            case 27:
                return $this->getshowMiscActionsInPlanner();
                break;
            case 28:
                return $this->getlimitStatusActionsInput();
                break;
            case 29:
                return $this->getlimitDiagnosticActionsInput();
                break;
            case 30:
                return $this->getlimitCureActionsInput();
                break;
            case 31:
                return $this->getlimitMiscActionsInput();
                break;
            case 32:
                return $this->getshowTime();
                break;
            case 33:
                return $this->getmedicalAidTypeId();
                break;
            case 34:
                return $this->geteventProfileId();
                break;
            case 35:
                return $this->getmesRequired();
                break;
            case 36:
                return $this->getmesCodeMask();
                break;
            case 37:
                return $this->getmesNameMask();
                break;
            case 38:
                return $this->getcounterId();
                break;
            case 39:
                return $this->getisExternal();
                break;
            case 40:
                return $this->getisAssistant();
                break;
            case 41:
                return $this->getisCurator();
                break;
            case 42:
                return $this->getcanHavePayableActions();
                break;
            case 43:
                return $this->getisRequiredCoordination();
                break;
            case 44:
                return $this->getisOrgStructurePriority();
                break;
            case 45:
                return $this->getisTakenTissue();
                break;
            case 46:
                return $this->getsex();
                break;
            case 47:
                return $this->getage();
                break;
            case 48:
                return $this->getrbMedicalKindId();
                break;
            case 49:
                return $this->getageBu();
                break;
            case 50:
                return $this->getageBc();
                break;
            case 51:
                return $this->getageEu();
                break;
            case 52:
                return $this->getageEc();
                break;
            case 53:
                return $this->getrequestTypeId();
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
        if (isset($alreadyDumpedObjects['EventType'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['EventType'][$this->getPrimaryKey()] = true;
        $keys = EventTypePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getcode(),
            $keys[7] => $this->getname(),
            $keys[8] => $this->getpurposeId(),
            $keys[9] => $this->getfinanceId(),
            $keys[10] => $this->getsceneId(),
            $keys[11] => $this->getvisitServiceModifier(),
            $keys[12] => $this->getvisitServiceFilter(),
            $keys[13] => $this->getvisitFinance(),
            $keys[14] => $this->getactionFinance(),
            $keys[15] => $this->getperiod(),
            $keys[16] => $this->getsingleinPeriod(),
            $keys[17] => $this->getisLong(),
            $keys[18] => $this->getdateInput(),
            $keys[19] => $this->getserviceId(),
            $keys[20] => $this->getcontext(),
            $keys[21] => $this->getform(),
            $keys[22] => $this->getminDuration(),
            $keys[23] => $this->getmaxDuration(),
            $keys[24] => $this->getshowStatusActionsInPlanner(),
            $keys[25] => $this->getshowDiagnosticActionsInPlanner(),
            $keys[26] => $this->getshowCureActionsInPlanner(),
            $keys[27] => $this->getshowMiscActionsInPlanner(),
            $keys[28] => $this->getlimitStatusActionsInput(),
            $keys[29] => $this->getlimitDiagnosticActionsInput(),
            $keys[30] => $this->getlimitCureActionsInput(),
            $keys[31] => $this->getlimitMiscActionsInput(),
            $keys[32] => $this->getshowTime(),
            $keys[33] => $this->getmedicalAidTypeId(),
            $keys[34] => $this->geteventProfileId(),
            $keys[35] => $this->getmesRequired(),
            $keys[36] => $this->getmesCodeMask(),
            $keys[37] => $this->getmesNameMask(),
            $keys[38] => $this->getcounterId(),
            $keys[39] => $this->getisExternal(),
            $keys[40] => $this->getisAssistant(),
            $keys[41] => $this->getisCurator(),
            $keys[42] => $this->getcanHavePayableActions(),
            $keys[43] => $this->getisRequiredCoordination(),
            $keys[44] => $this->getisOrgStructurePriority(),
            $keys[45] => $this->getisTakenTissue(),
            $keys[46] => $this->getsex(),
            $keys[47] => $this->getage(),
            $keys[48] => $this->getrbMedicalKindId(),
            $keys[49] => $this->getageBu(),
            $keys[50] => $this->getageBc(),
            $keys[51] => $this->getageEu(),
            $keys[52] => $this->getageEc(),
            $keys[53] => $this->getrequestTypeId(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aRbEventTypePurpose) {
                $result['RbEventTypePurpose'] = $this->aRbEventTypePurpose->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aRbResult) {
                $result['RbResult'] = $this->aRbResult->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collEvents) {
                $result['Events'] = $this->collEvents->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = EventTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setcode($value);
                break;
            case 7:
                $this->setname($value);
                break;
            case 8:
                $this->setpurposeId($value);
                break;
            case 9:
                $this->setfinanceId($value);
                break;
            case 10:
                $this->setsceneId($value);
                break;
            case 11:
                $this->setvisitServiceModifier($value);
                break;
            case 12:
                $this->setvisitServiceFilter($value);
                break;
            case 13:
                $this->setvisitFinance($value);
                break;
            case 14:
                $this->setactionFinance($value);
                break;
            case 15:
                $this->setperiod($value);
                break;
            case 16:
                $this->setsingleinPeriod($value);
                break;
            case 17:
                $this->setisLong($value);
                break;
            case 18:
                $this->setdateInput($value);
                break;
            case 19:
                $this->setserviceId($value);
                break;
            case 20:
                $this->setcontext($value);
                break;
            case 21:
                $this->setform($value);
                break;
            case 22:
                $this->setminDuration($value);
                break;
            case 23:
                $this->setmaxDuration($value);
                break;
            case 24:
                $this->setshowStatusActionsInPlanner($value);
                break;
            case 25:
                $this->setshowDiagnosticActionsInPlanner($value);
                break;
            case 26:
                $this->setshowCureActionsInPlanner($value);
                break;
            case 27:
                $this->setshowMiscActionsInPlanner($value);
                break;
            case 28:
                $this->setlimitStatusActionsInput($value);
                break;
            case 29:
                $this->setlimitDiagnosticActionsInput($value);
                break;
            case 30:
                $this->setlimitCureActionsInput($value);
                break;
            case 31:
                $this->setlimitMiscActionsInput($value);
                break;
            case 32:
                $this->setshowTime($value);
                break;
            case 33:
                $this->setmedicalAidTypeId($value);
                break;
            case 34:
                $this->seteventProfileId($value);
                break;
            case 35:
                $this->setmesRequired($value);
                break;
            case 36:
                $this->setmesCodeMask($value);
                break;
            case 37:
                $this->setmesNameMask($value);
                break;
            case 38:
                $this->setcounterId($value);
                break;
            case 39:
                $this->setisExternal($value);
                break;
            case 40:
                $this->setisAssistant($value);
                break;
            case 41:
                $this->setisCurator($value);
                break;
            case 42:
                $this->setcanHavePayableActions($value);
                break;
            case 43:
                $this->setisRequiredCoordination($value);
                break;
            case 44:
                $this->setisOrgStructurePriority($value);
                break;
            case 45:
                $this->setisTakenTissue($value);
                break;
            case 46:
                $this->setsex($value);
                break;
            case 47:
                $this->setage($value);
                break;
            case 48:
                $this->setrbMedicalKindId($value);
                break;
            case 49:
                $this->setageBu($value);
                break;
            case 50:
                $this->setageBc($value);
                break;
            case 51:
                $this->setageEu($value);
                break;
            case 52:
                $this->setageEc($value);
                break;
            case 53:
                $this->setrequestTypeId($value);
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
        $keys = EventTypePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setcode($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setname($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setpurposeId($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setfinanceId($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setsceneId($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setvisitServiceModifier($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setvisitServiceFilter($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setvisitFinance($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setactionFinance($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setperiod($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setsingleinPeriod($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setisLong($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setdateInput($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setserviceId($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setcontext($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setform($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setminDuration($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setmaxDuration($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setshowStatusActionsInPlanner($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->setshowDiagnosticActionsInPlanner($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->setshowCureActionsInPlanner($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setshowMiscActionsInPlanner($arr[$keys[27]]);
        if (array_key_exists($keys[28], $arr)) $this->setlimitStatusActionsInput($arr[$keys[28]]);
        if (array_key_exists($keys[29], $arr)) $this->setlimitDiagnosticActionsInput($arr[$keys[29]]);
        if (array_key_exists($keys[30], $arr)) $this->setlimitCureActionsInput($arr[$keys[30]]);
        if (array_key_exists($keys[31], $arr)) $this->setlimitMiscActionsInput($arr[$keys[31]]);
        if (array_key_exists($keys[32], $arr)) $this->setshowTime($arr[$keys[32]]);
        if (array_key_exists($keys[33], $arr)) $this->setmedicalAidTypeId($arr[$keys[33]]);
        if (array_key_exists($keys[34], $arr)) $this->seteventProfileId($arr[$keys[34]]);
        if (array_key_exists($keys[35], $arr)) $this->setmesRequired($arr[$keys[35]]);
        if (array_key_exists($keys[36], $arr)) $this->setmesCodeMask($arr[$keys[36]]);
        if (array_key_exists($keys[37], $arr)) $this->setmesNameMask($arr[$keys[37]]);
        if (array_key_exists($keys[38], $arr)) $this->setcounterId($arr[$keys[38]]);
        if (array_key_exists($keys[39], $arr)) $this->setisExternal($arr[$keys[39]]);
        if (array_key_exists($keys[40], $arr)) $this->setisAssistant($arr[$keys[40]]);
        if (array_key_exists($keys[41], $arr)) $this->setisCurator($arr[$keys[41]]);
        if (array_key_exists($keys[42], $arr)) $this->setcanHavePayableActions($arr[$keys[42]]);
        if (array_key_exists($keys[43], $arr)) $this->setisRequiredCoordination($arr[$keys[43]]);
        if (array_key_exists($keys[44], $arr)) $this->setisOrgStructurePriority($arr[$keys[44]]);
        if (array_key_exists($keys[45], $arr)) $this->setisTakenTissue($arr[$keys[45]]);
        if (array_key_exists($keys[46], $arr)) $this->setsex($arr[$keys[46]]);
        if (array_key_exists($keys[47], $arr)) $this->setage($arr[$keys[47]]);
        if (array_key_exists($keys[48], $arr)) $this->setrbMedicalKindId($arr[$keys[48]]);
        if (array_key_exists($keys[49], $arr)) $this->setageBu($arr[$keys[49]]);
        if (array_key_exists($keys[50], $arr)) $this->setageBc($arr[$keys[50]]);
        if (array_key_exists($keys[51], $arr)) $this->setageEu($arr[$keys[51]]);
        if (array_key_exists($keys[52], $arr)) $this->setageEc($arr[$keys[52]]);
        if (array_key_exists($keys[53], $arr)) $this->setrequestTypeId($arr[$keys[53]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(EventTypePeer::DATABASE_NAME);

        if ($this->isColumnModified(EventTypePeer::ID)) $criteria->add(EventTypePeer::ID, $this->id);
        if ($this->isColumnModified(EventTypePeer::CREATEDATETIME)) $criteria->add(EventTypePeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(EventTypePeer::CREATEPERSON_ID)) $criteria->add(EventTypePeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(EventTypePeer::MODIFYDATETIME)) $criteria->add(EventTypePeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(EventTypePeer::MODIFYPERSON_ID)) $criteria->add(EventTypePeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(EventTypePeer::DELETED)) $criteria->add(EventTypePeer::DELETED, $this->deleted);
        if ($this->isColumnModified(EventTypePeer::CODE)) $criteria->add(EventTypePeer::CODE, $this->code);
        if ($this->isColumnModified(EventTypePeer::NAME)) $criteria->add(EventTypePeer::NAME, $this->name);
        if ($this->isColumnModified(EventTypePeer::PURPOSE_ID)) $criteria->add(EventTypePeer::PURPOSE_ID, $this->purpose_id);
        if ($this->isColumnModified(EventTypePeer::FINANCE_ID)) $criteria->add(EventTypePeer::FINANCE_ID, $this->finance_id);
        if ($this->isColumnModified(EventTypePeer::SCENE_ID)) $criteria->add(EventTypePeer::SCENE_ID, $this->scene_id);
        if ($this->isColumnModified(EventTypePeer::VISITSERVICEMODIFIER)) $criteria->add(EventTypePeer::VISITSERVICEMODIFIER, $this->visitservicemodifier);
        if ($this->isColumnModified(EventTypePeer::VISITSERVICEFILTER)) $criteria->add(EventTypePeer::VISITSERVICEFILTER, $this->visitservicefilter);
        if ($this->isColumnModified(EventTypePeer::VISITFINANCE)) $criteria->add(EventTypePeer::VISITFINANCE, $this->visitfinance);
        if ($this->isColumnModified(EventTypePeer::ACTIONFINANCE)) $criteria->add(EventTypePeer::ACTIONFINANCE, $this->actionfinance);
        if ($this->isColumnModified(EventTypePeer::PERIOD)) $criteria->add(EventTypePeer::PERIOD, $this->period);
        if ($this->isColumnModified(EventTypePeer::SINGLEINPERIOD)) $criteria->add(EventTypePeer::SINGLEINPERIOD, $this->singleinperiod);
        if ($this->isColumnModified(EventTypePeer::ISLONG)) $criteria->add(EventTypePeer::ISLONG, $this->islong);
        if ($this->isColumnModified(EventTypePeer::DATEINPUT)) $criteria->add(EventTypePeer::DATEINPUT, $this->dateinput);
        if ($this->isColumnModified(EventTypePeer::SERVICE_ID)) $criteria->add(EventTypePeer::SERVICE_ID, $this->service_id);
        if ($this->isColumnModified(EventTypePeer::CONTEXT)) $criteria->add(EventTypePeer::CONTEXT, $this->context);
        if ($this->isColumnModified(EventTypePeer::FORM)) $criteria->add(EventTypePeer::FORM, $this->form);
        if ($this->isColumnModified(EventTypePeer::MINDURATION)) $criteria->add(EventTypePeer::MINDURATION, $this->minduration);
        if ($this->isColumnModified(EventTypePeer::MAXDURATION)) $criteria->add(EventTypePeer::MAXDURATION, $this->maxduration);
        if ($this->isColumnModified(EventTypePeer::SHOWSTATUSACTIONSINPLANNER)) $criteria->add(EventTypePeer::SHOWSTATUSACTIONSINPLANNER, $this->showstatusactionsinplanner);
        if ($this->isColumnModified(EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER)) $criteria->add(EventTypePeer::SHOWDIAGNOSTICACTIONSINPLANNER, $this->showdiagnosticactionsinplanner);
        if ($this->isColumnModified(EventTypePeer::SHOWCUREACTIONSINPLANNER)) $criteria->add(EventTypePeer::SHOWCUREACTIONSINPLANNER, $this->showcureactionsinplanner);
        if ($this->isColumnModified(EventTypePeer::SHOWMISCACTIONSINPLANNER)) $criteria->add(EventTypePeer::SHOWMISCACTIONSINPLANNER, $this->showmiscactionsinplanner);
        if ($this->isColumnModified(EventTypePeer::LIMITSTATUSACTIONSINPUT)) $criteria->add(EventTypePeer::LIMITSTATUSACTIONSINPUT, $this->limitstatusactionsinput);
        if ($this->isColumnModified(EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT)) $criteria->add(EventTypePeer::LIMITDIAGNOSTICACTIONSINPUT, $this->limitdiagnosticactionsinput);
        if ($this->isColumnModified(EventTypePeer::LIMITCUREACTIONSINPUT)) $criteria->add(EventTypePeer::LIMITCUREACTIONSINPUT, $this->limitcureactionsinput);
        if ($this->isColumnModified(EventTypePeer::LIMITMISCACTIONSINPUT)) $criteria->add(EventTypePeer::LIMITMISCACTIONSINPUT, $this->limitmiscactionsinput);
        if ($this->isColumnModified(EventTypePeer::SHOWTIME)) $criteria->add(EventTypePeer::SHOWTIME, $this->showtime);
        if ($this->isColumnModified(EventTypePeer::MEDICALAIDTYPE_ID)) $criteria->add(EventTypePeer::MEDICALAIDTYPE_ID, $this->medicalaidtype_id);
        if ($this->isColumnModified(EventTypePeer::EVENTPROFILE_ID)) $criteria->add(EventTypePeer::EVENTPROFILE_ID, $this->eventprofile_id);
        if ($this->isColumnModified(EventTypePeer::MESREQUIRED)) $criteria->add(EventTypePeer::MESREQUIRED, $this->mesrequired);
        if ($this->isColumnModified(EventTypePeer::MESCODEMASK)) $criteria->add(EventTypePeer::MESCODEMASK, $this->mescodemask);
        if ($this->isColumnModified(EventTypePeer::MESNAMEMASK)) $criteria->add(EventTypePeer::MESNAMEMASK, $this->mesnamemask);
        if ($this->isColumnModified(EventTypePeer::COUNTER_ID)) $criteria->add(EventTypePeer::COUNTER_ID, $this->counter_id);
        if ($this->isColumnModified(EventTypePeer::ISEXTERNAL)) $criteria->add(EventTypePeer::ISEXTERNAL, $this->isexternal);
        if ($this->isColumnModified(EventTypePeer::ISASSISTANT)) $criteria->add(EventTypePeer::ISASSISTANT, $this->isassistant);
        if ($this->isColumnModified(EventTypePeer::ISCURATOR)) $criteria->add(EventTypePeer::ISCURATOR, $this->iscurator);
        if ($this->isColumnModified(EventTypePeer::CANHAVEPAYABLEACTIONS)) $criteria->add(EventTypePeer::CANHAVEPAYABLEACTIONS, $this->canhavepayableactions);
        if ($this->isColumnModified(EventTypePeer::ISREQUIREDCOORDINATION)) $criteria->add(EventTypePeer::ISREQUIREDCOORDINATION, $this->isrequiredcoordination);
        if ($this->isColumnModified(EventTypePeer::ISORGSTRUCTUREPRIORITY)) $criteria->add(EventTypePeer::ISORGSTRUCTUREPRIORITY, $this->isorgstructurepriority);
        if ($this->isColumnModified(EventTypePeer::ISTAKENTISSUE)) $criteria->add(EventTypePeer::ISTAKENTISSUE, $this->istakentissue);
        if ($this->isColumnModified(EventTypePeer::SEX)) $criteria->add(EventTypePeer::SEX, $this->sex);
        if ($this->isColumnModified(EventTypePeer::AGE)) $criteria->add(EventTypePeer::AGE, $this->age);
        if ($this->isColumnModified(EventTypePeer::RBMEDICALKIND_ID)) $criteria->add(EventTypePeer::RBMEDICALKIND_ID, $this->rbmedicalkind_id);
        if ($this->isColumnModified(EventTypePeer::AGE_BU)) $criteria->add(EventTypePeer::AGE_BU, $this->age_bu);
        if ($this->isColumnModified(EventTypePeer::AGE_BC)) $criteria->add(EventTypePeer::AGE_BC, $this->age_bc);
        if ($this->isColumnModified(EventTypePeer::AGE_EU)) $criteria->add(EventTypePeer::AGE_EU, $this->age_eu);
        if ($this->isColumnModified(EventTypePeer::AGE_EC)) $criteria->add(EventTypePeer::AGE_EC, $this->age_ec);
        if ($this->isColumnModified(EventTypePeer::REQUESTTYPE_ID)) $criteria->add(EventTypePeer::REQUESTTYPE_ID, $this->requesttype_id);

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
        $criteria = new Criteria(EventTypePeer::DATABASE_NAME);
        $criteria->add(EventTypePeer::ID, $this->id);

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
     * @param object $copyObj An object of EventType (or compatible) type.
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
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());
        $copyObj->setpurposeId($this->getpurposeId());
        $copyObj->setfinanceId($this->getfinanceId());
        $copyObj->setsceneId($this->getsceneId());
        $copyObj->setvisitServiceModifier($this->getvisitServiceModifier());
        $copyObj->setvisitServiceFilter($this->getvisitServiceFilter());
        $copyObj->setvisitFinance($this->getvisitFinance());
        $copyObj->setactionFinance($this->getactionFinance());
        $copyObj->setperiod($this->getperiod());
        $copyObj->setsingleinPeriod($this->getsingleinPeriod());
        $copyObj->setisLong($this->getisLong());
        $copyObj->setdateInput($this->getdateInput());
        $copyObj->setserviceId($this->getserviceId());
        $copyObj->setcontext($this->getcontext());
        $copyObj->setform($this->getform());
        $copyObj->setminDuration($this->getminDuration());
        $copyObj->setmaxDuration($this->getmaxDuration());
        $copyObj->setshowStatusActionsInPlanner($this->getshowStatusActionsInPlanner());
        $copyObj->setshowDiagnosticActionsInPlanner($this->getshowDiagnosticActionsInPlanner());
        $copyObj->setshowCureActionsInPlanner($this->getshowCureActionsInPlanner());
        $copyObj->setshowMiscActionsInPlanner($this->getshowMiscActionsInPlanner());
        $copyObj->setlimitStatusActionsInput($this->getlimitStatusActionsInput());
        $copyObj->setlimitDiagnosticActionsInput($this->getlimitDiagnosticActionsInput());
        $copyObj->setlimitCureActionsInput($this->getlimitCureActionsInput());
        $copyObj->setlimitMiscActionsInput($this->getlimitMiscActionsInput());
        $copyObj->setshowTime($this->getshowTime());
        $copyObj->setmedicalAidTypeId($this->getmedicalAidTypeId());
        $copyObj->seteventProfileId($this->geteventProfileId());
        $copyObj->setmesRequired($this->getmesRequired());
        $copyObj->setmesCodeMask($this->getmesCodeMask());
        $copyObj->setmesNameMask($this->getmesNameMask());
        $copyObj->setcounterId($this->getcounterId());
        $copyObj->setisExternal($this->getisExternal());
        $copyObj->setisAssistant($this->getisAssistant());
        $copyObj->setisCurator($this->getisCurator());
        $copyObj->setcanHavePayableActions($this->getcanHavePayableActions());
        $copyObj->setisRequiredCoordination($this->getisRequiredCoordination());
        $copyObj->setisOrgStructurePriority($this->getisOrgStructurePriority());
        $copyObj->setisTakenTissue($this->getisTakenTissue());
        $copyObj->setsex($this->getsex());
        $copyObj->setage($this->getage());
        $copyObj->setrbMedicalKindId($this->getrbMedicalKindId());
        $copyObj->setageBu($this->getageBu());
        $copyObj->setageBc($this->getageBc());
        $copyObj->setageEu($this->getageEu());
        $copyObj->setageEc($this->getageEc());
        $copyObj->setrequestTypeId($this->getrequestTypeId());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getEvents() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addEvent($relObj->copy($deepCopy));
                }
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
     * @return EventType Clone of current object.
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
     * @return EventTypePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new EventTypePeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a RbEventTypePurpose object.
     *
     * @param             RbEventTypePurpose $v
     * @return EventType The current object (for fluent API support)
     * @throws PropelException
     */
    public function setRbEventTypePurpose(RbEventTypePurpose $v = null)
    {
        if ($v === null) {
            $this->setpurposeId(NULL);
        } else {
            $this->setpurposeId($v->getid());
        }

        $this->aRbEventTypePurpose = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the RbEventTypePurpose object, it will not be re-added.
        if ($v !== null) {
            $v->addEventType($this);
        }


        return $this;
    }


    /**
     * Get the associated RbEventTypePurpose object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return RbEventTypePurpose The associated RbEventTypePurpose object.
     * @throws PropelException
     */
    public function getRbEventTypePurpose(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aRbEventTypePurpose === null && ($this->purpose_id !== null) && $doQuery) {
            $this->aRbEventTypePurpose = RbEventTypePurposeQuery::create()->findPk($this->purpose_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aRbEventTypePurpose->addEventTypes($this);
             */
        }

        return $this->aRbEventTypePurpose;
    }

    /**
     * Declares an association between this object and a RbResult object.
     *
     * @param             RbResult $v
     * @return EventType The current object (for fluent API support)
     * @throws PropelException
     */
    public function setRbResult(RbResult $v = null)
    {
        if ($v === null) {
            $this->setpurposeId(NULL);
        } else {
            $this->setpurposeId($v->geteventPurposeId());
        }

        $this->aRbResult = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the RbResult object, it will not be re-added.
        if ($v !== null) {
            $v->addEventType($this);
        }


        return $this;
    }


    /**
     * Get the associated RbResult object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return RbResult The associated RbResult object.
     * @throws PropelException
     */
    public function getRbResult(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aRbResult === null && ($this->purpose_id !== null) && $doQuery) {
            $this->aRbResult = RbResultQuery::create()
                ->filterByEventType($this) // here
                ->findOne($con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aRbResult->addEventTypes($this);
             */
        }

        return $this->aRbResult;
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
        if ('Event' == $relationName) {
            $this->initEvents();
        }
    }

    /**
     * Clears out the collEvents collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return EventType The current object (for fluent API support)
     * @see        addEvents()
     */
    public function clearEvents()
    {
        $this->collEvents = null; // important to set this to null since that means it is uninitialized
        $this->collEventsPartial = null;

        return $this;
    }

    /**
     * reset is the collEvents collection loaded partially
     *
     * @return void
     */
    public function resetPartialEvents($v = true)
    {
        $this->collEventsPartial = $v;
    }

    /**
     * Initializes the collEvents collection.
     *
     * By default this just sets the collEvents collection to an empty array (like clearcollEvents());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initEvents($overrideExisting = true)
    {
        if (null !== $this->collEvents && !$overrideExisting) {
            return;
        }
        $this->collEvents = new PropelObjectCollection();
        $this->collEvents->setModel('Event');
    }

    /**
     * Gets an array of Event objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this EventType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Event[] List of Event objects
     * @throws PropelException
     */
    public function getEvents($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collEventsPartial && !$this->isNew();
        if (null === $this->collEvents || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collEvents) {
                // return empty collection
                $this->initEvents();
            } else {
                $collEvents = EventQuery::create(null, $criteria)
                    ->filterByEventType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collEventsPartial && count($collEvents)) {
                      $this->initEvents(false);

                      foreach($collEvents as $obj) {
                        if (false == $this->collEvents->contains($obj)) {
                          $this->collEvents->append($obj);
                        }
                      }

                      $this->collEventsPartial = true;
                    }

                    $collEvents->getInternalIterator()->rewind();
                    return $collEvents;
                }

                if($partial && $this->collEvents) {
                    foreach($this->collEvents as $obj) {
                        if($obj->isNew()) {
                            $collEvents[] = $obj;
                        }
                    }
                }

                $this->collEvents = $collEvents;
                $this->collEventsPartial = false;
            }
        }

        return $this->collEvents;
    }

    /**
     * Sets a collection of Event objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $events A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return EventType The current object (for fluent API support)
     */
    public function setEvents(PropelCollection $events, PropelPDO $con = null)
    {
        $eventsToDelete = $this->getEvents(new Criteria(), $con)->diff($events);

        $this->eventsScheduledForDeletion = unserialize(serialize($eventsToDelete));

        foreach ($eventsToDelete as $eventRemoved) {
            $eventRemoved->setEventType(null);
        }

        $this->collEvents = null;
        foreach ($events as $event) {
            $this->addEvent($event);
        }

        $this->collEvents = $events;
        $this->collEventsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Event objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Event objects.
     * @throws PropelException
     */
    public function countEvents(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collEventsPartial && !$this->isNew();
        if (null === $this->collEvents || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collEvents) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getEvents());
            }
            $query = EventQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByEventType($this)
                ->count($con);
        }

        return count($this->collEvents);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return EventType The current object (for fluent API support)
     */
    public function addEvent(Event $l)
    {
        if ($this->collEvents === null) {
            $this->initEvents();
            $this->collEventsPartial = true;
        }
        if (!in_array($l, $this->collEvents->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddEvent($l);
        }

        return $this;
    }

    /**
     * @param	Event $event The event object to add.
     */
    protected function doAddEvent($event)
    {
        $this->collEvents[]= $event;
        $event->setEventType($this);
    }

    /**
     * @param	Event $event The event object to remove.
     * @return EventType The current object (for fluent API support)
     */
    public function removeEvent($event)
    {
        if ($this->getEvents()->contains($event)) {
            $this->collEvents->remove($this->collEvents->search($event));
            if (null === $this->eventsScheduledForDeletion) {
                $this->eventsScheduledForDeletion = clone $this->collEvents;
                $this->eventsScheduledForDeletion->clear();
            }
            $this->eventsScheduledForDeletion[]= clone $event;
            $event->setEventType(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinClient($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Client', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinCreatePerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('CreatePerson', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinModifyPerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('ModifyPerson', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinSetPerson($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('SetPerson', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinDoctor($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Doctor', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this EventType is new, it will return
     * an empty collection; or if this EventType has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in EventType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('OrgStructure', $join_behavior);

        return $this->getEvents($query, $con);
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
        $this->code = null;
        $this->name = null;
        $this->purpose_id = null;
        $this->finance_id = null;
        $this->scene_id = null;
        $this->visitservicemodifier = null;
        $this->visitservicefilter = null;
        $this->visitfinance = null;
        $this->actionfinance = null;
        $this->period = null;
        $this->singleinperiod = null;
        $this->islong = null;
        $this->dateinput = null;
        $this->service_id = null;
        $this->context = null;
        $this->form = null;
        $this->minduration = null;
        $this->maxduration = null;
        $this->showstatusactionsinplanner = null;
        $this->showdiagnosticactionsinplanner = null;
        $this->showcureactionsinplanner = null;
        $this->showmiscactionsinplanner = null;
        $this->limitstatusactionsinput = null;
        $this->limitdiagnosticactionsinput = null;
        $this->limitcureactionsinput = null;
        $this->limitmiscactionsinput = null;
        $this->showtime = null;
        $this->medicalaidtype_id = null;
        $this->eventprofile_id = null;
        $this->mesrequired = null;
        $this->mescodemask = null;
        $this->mesnamemask = null;
        $this->counter_id = null;
        $this->isexternal = null;
        $this->isassistant = null;
        $this->iscurator = null;
        $this->canhavepayableactions = null;
        $this->isrequiredcoordination = null;
        $this->isorgstructurepriority = null;
        $this->istakentissue = null;
        $this->sex = null;
        $this->age = null;
        $this->rbmedicalkind_id = null;
        $this->age_bu = null;
        $this->age_bc = null;
        $this->age_eu = null;
        $this->age_ec = null;
        $this->requesttype_id = null;
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
            if ($this->collEvents) {
                foreach ($this->collEvents as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aRbEventTypePurpose instanceof Persistent) {
              $this->aRbEventTypePurpose->clearAllReferences($deep);
            }
            if ($this->aRbResult instanceof Persistent) {
              $this->aRbResult->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collEvents instanceof PropelCollection) {
            $this->collEvents->clearIterator();
        }
        $this->collEvents = null;
        $this->aRbEventTypePurpose = null;
        $this->aRbResult = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(EventTypePeer::DEFAULT_STRING_FORMAT);
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
     * @return     EventType The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = EventTypePeer::MODIFYDATETIME;

        return $this;
    }

}
