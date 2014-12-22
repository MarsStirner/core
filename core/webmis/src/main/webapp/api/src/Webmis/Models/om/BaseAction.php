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
use Webmis\Models\ActionPeer;
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyQuery;
use Webmis\Models\ActionQuery;
use Webmis\Models\ActionType;
use Webmis\Models\ActionTypeQuery;
use Webmis\Models\DrugChart;
use Webmis\Models\DrugChartQuery;
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentQuery;
use Webmis\Models\Event;
use Webmis\Models\EventQuery;
use Webmis\Models\Person;
use Webmis\Models\PersonQuery;

/**
 * Base class that represents a row from the 'Action' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseAction extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\ActionPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        ActionPeer
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
     * The value for the actiontype_id field.
     * @var        int
     */
    protected $actiontype_id;

    /**
     * The value for the event_id field.
     * @var        int
     */
    protected $event_id;

    /**
     * The value for the idx field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $idx;

    /**
     * The value for the directiondate field.
     * @var        string
     */
    protected $directiondate;

    /**
     * The value for the status field.
     * @var        int
     */
    protected $status;

    /**
     * The value for the setperson_id field.
     * @var        int
     */
    protected $setperson_id;

    /**
     * The value for the isurgent field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isurgent;

    /**
     * The value for the begdate field.
     * @var        string
     */
    protected $begdate;

    /**
     * The value for the plannedenddate field.
     * @var        string
     */
    protected $plannedenddate;

    /**
     * The value for the enddate field.
     * @var        string
     */
    protected $enddate;

    /**
     * The value for the note field.
     * @var        string
     */
    protected $note;

    /**
     * The value for the person_id field.
     * @var        int
     */
    protected $person_id;

    /**
     * The value for the office field.
     * @var        string
     */
    protected $office;

    /**
     * The value for the amount field.
     * @var        double
     */
    protected $amount;

    /**
     * The value for the uet field.
     * Note: this column has a database default value of: 0
     * @var        double
     */
    protected $uet;

    /**
     * The value for the expose field.
     * Note: this column has a database default value of: 1
     * @var        int
     */
    protected $expose;

    /**
     * The value for the paystatus field.
     * @var        int
     */
    protected $paystatus;

    /**
     * The value for the account field.
     * @var        boolean
     */
    protected $account;

    /**
     * The value for the finance_id field.
     * @var        int
     */
    protected $finance_id;

    /**
     * The value for the prescription_id field.
     * @var        int
     */
    protected $prescription_id;

    /**
     * The value for the takentissuejournal_id field.
     * @var        int
     */
    protected $takentissuejournal_id;

    /**
     * The value for the contract_id field.
     * @var        int
     */
    protected $contract_id;

    /**
     * The value for the coorddate field.
     * @var        string
     */
    protected $coorddate;

    /**
     * The value for the coordagent field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $coordagent;

    /**
     * The value for the coordinspector field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $coordinspector;

    /**
     * The value for the coordtext field.
     * @var        string
     */
    protected $coordtext;

    /**
     * The value for the hospitaluidfrom field.
     * Note: this column has a database default value of: '0'
     * @var        string
     */
    protected $hospitaluidfrom;

    /**
     * The value for the pacientinqueuetype field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $pacientinqueuetype;

    /**
     * The value for the appointmenttype field.
     * @var        string
     */
    protected $appointmenttype;

    /**
     * The value for the version field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $version;

    /**
     * The value for the parentaction_id field.
     * @var        int
     */
    protected $parentaction_id;

    /**
     * The value for the uuid_id field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $uuid_id;

    /**
     * The value for the dcm_study_uid field.
     * @var        string
     */
    protected $dcm_study_uid;

    /**
     * @var        Event
     */
    protected $aEvent;

    /**
     * @var        Person
     */
    protected $aCreatePerson;

    /**
     * @var        Person
     */
    protected $aModifyPerson;

    /**
     * @var        Person
     */
    protected $aSetPerson;

    /**
     * @var        ActionType
     */
    protected $aActionType;

    /**
     * @var        PropelObjectCollection|ActionProperty[] Collection to store aggregation of ActionProperty objects.
     */
    protected $collActionPropertys;
    protected $collActionPropertysPartial;

    /**
     * @var        PropelObjectCollection|DrugChart[] Collection to store aggregation of DrugChart objects.
     */
    protected $collDrugCharts;
    protected $collDrugChartsPartial;

    /**
     * @var        PropelObjectCollection|DrugComponent[] Collection to store aggregation of DrugComponent objects.
     */
    protected $collDrugComponents;
    protected $collDrugComponentsPartial;

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
    protected $actionPropertysScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $drugChartsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $drugComponentsScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->idx = 0;
        $this->isurgent = false;
        $this->uet = 0;
        $this->expose = 1;
        $this->coordagent = '';
        $this->coordinspector = '';
        $this->hospitaluidfrom = '0';
        $this->pacientinqueuetype = 0;
        $this->version = 0;
        $this->uuid_id = 0;
    }

    /**
     * Initializes internal state of BaseAction object.
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
     * Get the [actiontype_id] column value.
     *
     * @return int
     */
    public function getactionTypeId()
    {
        return $this->actiontype_id;
    }

    /**
     * Get the [event_id] column value.
     *
     * @return int
     */
    public function geteventId()
    {
        return $this->event_id;
    }

    /**
     * Get the [idx] column value.
     *
     * @return int
     */
    public function getidx()
    {
        return $this->idx;
    }

    /**
     * Get the [optionally formatted] temporal [directiondate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getdirectionDate($format = 'Y-m-d H:i:s')
    {
        if ($this->directiondate === null) {
            return null;
        }

        if ($this->directiondate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->directiondate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->directiondate, true), $x);
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
     * Get the [status] column value.
     *
     * @return int
     */
    public function getstatus()
    {
        return $this->status;
    }

    /**
     * Get the [setperson_id] column value.
     *
     * @return int
     */
    public function getsetPersonId()
    {
        return $this->setperson_id;
    }

    /**
     * Get the [isurgent] column value.
     *
     * @return boolean
     */
    public function getisUrgent()
    {
        return $this->isurgent;
    }

    /**
     * Get the [optionally formatted] temporal [begdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getbegDate($format = 'Y-m-d H:i:s')
    {
        if ($this->begdate === null) {
            return null;
        }

        if ($this->begdate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->begdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->begdate, true), $x);
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
     * Get the [optionally formatted] temporal [plannedenddate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getplannedEndDate($format = 'Y-m-d H:i:s')
    {
        if ($this->plannedenddate === null) {
            return null;
        }

        if ($this->plannedenddate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->plannedenddate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->plannedenddate, true), $x);
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
     * Get the [optionally formatted] temporal [enddate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getendDate($format = 'Y-m-d H:i:s')
    {
        if ($this->enddate === null) {
            return null;
        }

        if ($this->enddate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->enddate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->enddate, true), $x);
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
     * Get the [note] column value.
     *
     * @return string
     */
    public function getnote()
    {
        return $this->note;
    }

    /**
     * Get the [person_id] column value.
     *
     * @return int
     */
    public function getpersonId()
    {
        return $this->person_id;
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
     * Get the [amount] column value.
     *
     * @return double
     */
    public function getamount()
    {
        return $this->amount;
    }

    /**
     * Get the [uet] column value.
     *
     * @return double
     */
    public function getuet()
    {
        return $this->uet;
    }

    /**
     * Get the [expose] column value.
     *
     * @return int
     */
    public function getexpose()
    {
        return $this->expose;
    }

    /**
     * Get the [paystatus] column value.
     *
     * @return int
     */
    public function getpayStatus()
    {
        return $this->paystatus;
    }

    /**
     * Get the [account] column value.
     *
     * @return boolean
     */
    public function getaccount()
    {
        return $this->account;
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
     * Get the [prescription_id] column value.
     *
     * @return int
     */
    public function getprescriptionId()
    {
        return $this->prescription_id;
    }

    /**
     * Get the [takentissuejournal_id] column value.
     *
     * @return int
     */
    public function gettakenTissueJournalId()
    {
        return $this->takentissuejournal_id;
    }

    /**
     * Get the [contract_id] column value.
     *
     * @return int
     */
    public function getcontractId()
    {
        return $this->contract_id;
    }

    /**
     * Get the [optionally formatted] temporal [coorddate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getcoordDate($format = 'Y-m-d H:i:s')
    {
        if ($this->coorddate === null) {
            return null;
        }

        if ($this->coorddate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->coorddate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->coorddate, true), $x);
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
     * Get the [coordagent] column value.
     *
     * @return string
     */
    public function getcoordAgent()
    {
        return $this->coordagent;
    }

    /**
     * Get the [coordinspector] column value.
     *
     * @return string
     */
    public function getcoordInspector()
    {
        return $this->coordinspector;
    }

    /**
     * Get the [coordtext] column value.
     *
     * @return string
     */
    public function getcoordText()
    {
        return $this->coordtext;
    }

    /**
     * Get the [hospitaluidfrom] column value.
     *
     * @return string
     */
    public function gethospitalUidFrom()
    {
        return $this->hospitaluidfrom;
    }

    /**
     * Get the [pacientinqueuetype] column value.
     *
     * @return int
     */
    public function getpacientInQueueType()
    {
        return $this->pacientinqueuetype;
    }

    /**
     * Get the [appointmenttype] column value.
     *
     * @return string
     */
    public function getappointmentType()
    {
        return $this->appointmenttype;
    }

    /**
     * Get the [version] column value.
     *
     * @return int
     */
    public function getversion()
    {
        return $this->version;
    }

    /**
     * Get the [parentaction_id] column value.
     *
     * @return int
     */
    public function getparentActionId()
    {
        return $this->parentaction_id;
    }

    /**
     * Get the [uuid_id] column value.
     *
     * @return int
     */
    public function getuuidId()
    {
        return $this->uuid_id;
    }

    /**
     * Get the [dcm_study_uid] column value.
     *
     * @return string
     */
    public function getdcmStudyUid()
    {
        return $this->dcm_study_uid;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = ActionPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = ActionPeer::CREATEPERSON_ID;
        }

        if ($this->aCreatePerson !== null && $this->aCreatePerson->getid() !== $v) {
            $this->aCreatePerson = null;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = ActionPeer::MODIFYPERSON_ID;
        }

        if ($this->aModifyPerson !== null && $this->aModifyPerson->getid() !== $v) {
            $this->aModifyPerson = null;
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
     * @return Action The current object (for fluent API support)
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
            $this->modifiedColumns[] = ActionPeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [actiontype_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setactionTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->actiontype_id !== $v) {
            $this->actiontype_id = $v;
            $this->modifiedColumns[] = ActionPeer::ACTIONTYPE_ID;
        }

        if ($this->aActionType !== null && $this->aActionType->getid() !== $v) {
            $this->aActionType = null;
        }


        return $this;
    } // setactionTypeId()

    /**
     * Set the value of [event_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function seteventId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->event_id !== $v) {
            $this->event_id = $v;
            $this->modifiedColumns[] = ActionPeer::EVENT_ID;
        }

        if ($this->aEvent !== null && $this->aEvent->getid() !== $v) {
            $this->aEvent = null;
        }


        return $this;
    } // seteventId()

    /**
     * Set the value of [idx] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setidx($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->idx !== $v) {
            $this->idx = $v;
            $this->modifiedColumns[] = ActionPeer::IDX;
        }


        return $this;
    } // setidx()

    /**
     * Sets the value of [directiondate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setdirectionDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->directiondate !== null || $dt !== null) {
            $currentDateAsString = ($this->directiondate !== null && $tmpDt = new DateTime($this->directiondate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->directiondate = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::DIRECTIONDATE;
            }
        } // if either are not null


        return $this;
    } // setdirectionDate()

    /**
     * Set the value of [status] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setstatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->status !== $v) {
            $this->status = $v;
            $this->modifiedColumns[] = ActionPeer::STATUS;
        }


        return $this;
    } // setstatus()

    /**
     * Set the value of [setperson_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setsetPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->setperson_id !== $v) {
            $this->setperson_id = $v;
            $this->modifiedColumns[] = ActionPeer::SETPERSON_ID;
        }

        if ($this->aSetPerson !== null && $this->aSetPerson->getid() !== $v) {
            $this->aSetPerson = null;
        }


        return $this;
    } // setsetPersonId()

    /**
     * Sets the value of the [isurgent] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Action The current object (for fluent API support)
     */
    public function setisUrgent($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isurgent !== $v) {
            $this->isurgent = $v;
            $this->modifiedColumns[] = ActionPeer::ISURGENT;
        }


        return $this;
    } // setisUrgent()

    /**
     * Sets the value of [begdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setbegDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->begdate !== null || $dt !== null) {
            $currentDateAsString = ($this->begdate !== null && $tmpDt = new DateTime($this->begdate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->begdate = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::BEGDATE;
            }
        } // if either are not null


        return $this;
    } // setbegDate()

    /**
     * Sets the value of [plannedenddate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setplannedEndDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->plannedenddate !== null || $dt !== null) {
            $currentDateAsString = ($this->plannedenddate !== null && $tmpDt = new DateTime($this->plannedenddate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->plannedenddate = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::PLANNEDENDDATE;
            }
        } // if either are not null


        return $this;
    } // setplannedEndDate()

    /**
     * Sets the value of [enddate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setendDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->enddate !== null || $dt !== null) {
            $currentDateAsString = ($this->enddate !== null && $tmpDt = new DateTime($this->enddate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->enddate = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::ENDDATE;
            }
        } // if either are not null


        return $this;
    } // setendDate()

    /**
     * Set the value of [note] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setnote($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->note !== $v) {
            $this->note = $v;
            $this->modifiedColumns[] = ActionPeer::NOTE;
        }


        return $this;
    } // setnote()

    /**
     * Set the value of [person_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setpersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->person_id !== $v) {
            $this->person_id = $v;
            $this->modifiedColumns[] = ActionPeer::PERSON_ID;
        }


        return $this;
    } // setpersonId()

    /**
     * Set the value of [office] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setoffice($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->office !== $v) {
            $this->office = $v;
            $this->modifiedColumns[] = ActionPeer::OFFICE;
        }


        return $this;
    } // setoffice()

    /**
     * Set the value of [amount] column.
     *
     * @param double $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setamount($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (double) $v;
        }

        if ($this->amount !== $v) {
            $this->amount = $v;
            $this->modifiedColumns[] = ActionPeer::AMOUNT;
        }


        return $this;
    } // setamount()

    /**
     * Set the value of [uet] column.
     *
     * @param double $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setuet($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (double) $v;
        }

        if ($this->uet !== $v) {
            $this->uet = $v;
            $this->modifiedColumns[] = ActionPeer::UET;
        }


        return $this;
    } // setuet()

    /**
     * Set the value of [expose] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setexpose($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->expose !== $v) {
            $this->expose = $v;
            $this->modifiedColumns[] = ActionPeer::EXPOSE;
        }


        return $this;
    } // setexpose()

    /**
     * Set the value of [paystatus] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setpayStatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->paystatus !== $v) {
            $this->paystatus = $v;
            $this->modifiedColumns[] = ActionPeer::PAYSTATUS;
        }


        return $this;
    } // setpayStatus()

    /**
     * Sets the value of the [account] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Action The current object (for fluent API support)
     */
    public function setaccount($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->account !== $v) {
            $this->account = $v;
            $this->modifiedColumns[] = ActionPeer::ACCOUNT;
        }


        return $this;
    } // setaccount()

    /**
     * Set the value of [finance_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setfinanceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->finance_id !== $v) {
            $this->finance_id = $v;
            $this->modifiedColumns[] = ActionPeer::FINANCE_ID;
        }


        return $this;
    } // setfinanceId()

    /**
     * Set the value of [prescription_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setprescriptionId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->prescription_id !== $v) {
            $this->prescription_id = $v;
            $this->modifiedColumns[] = ActionPeer::PRESCRIPTION_ID;
        }


        return $this;
    } // setprescriptionId()

    /**
     * Set the value of [takentissuejournal_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function settakenTissueJournalId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->takentissuejournal_id !== $v) {
            $this->takentissuejournal_id = $v;
            $this->modifiedColumns[] = ActionPeer::TAKENTISSUEJOURNAL_ID;
        }


        return $this;
    } // settakenTissueJournalId()

    /**
     * Set the value of [contract_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setcontractId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->contract_id !== $v) {
            $this->contract_id = $v;
            $this->modifiedColumns[] = ActionPeer::CONTRACT_ID;
        }


        return $this;
    } // setcontractId()

    /**
     * Sets the value of [coorddate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Action The current object (for fluent API support)
     */
    public function setcoordDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->coorddate !== null || $dt !== null) {
            $currentDateAsString = ($this->coorddate !== null && $tmpDt = new DateTime($this->coorddate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->coorddate = $newDateAsString;
                $this->modifiedColumns[] = ActionPeer::COORDDATE;
            }
        } // if either are not null


        return $this;
    } // setcoordDate()

    /**
     * Set the value of [coordagent] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setcoordAgent($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->coordagent !== $v) {
            $this->coordagent = $v;
            $this->modifiedColumns[] = ActionPeer::COORDAGENT;
        }


        return $this;
    } // setcoordAgent()

    /**
     * Set the value of [coordinspector] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setcoordInspector($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->coordinspector !== $v) {
            $this->coordinspector = $v;
            $this->modifiedColumns[] = ActionPeer::COORDINSPECTOR;
        }


        return $this;
    } // setcoordInspector()

    /**
     * Set the value of [coordtext] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setcoordText($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->coordtext !== $v) {
            $this->coordtext = $v;
            $this->modifiedColumns[] = ActionPeer::COORDTEXT;
        }


        return $this;
    } // setcoordText()

    /**
     * Set the value of [hospitaluidfrom] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function sethospitalUidFrom($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->hospitaluidfrom !== $v) {
            $this->hospitaluidfrom = $v;
            $this->modifiedColumns[] = ActionPeer::HOSPITALUIDFROM;
        }


        return $this;
    } // sethospitalUidFrom()

    /**
     * Set the value of [pacientinqueuetype] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setpacientInQueueType($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->pacientinqueuetype !== $v) {
            $this->pacientinqueuetype = $v;
            $this->modifiedColumns[] = ActionPeer::PACIENTINQUEUETYPE;
        }


        return $this;
    } // setpacientInQueueType()

    /**
     * Set the value of [appointmenttype] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setappointmentType($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->appointmenttype !== $v) {
            $this->appointmenttype = $v;
            $this->modifiedColumns[] = ActionPeer::APPOINTMENTTYPE;
        }


        return $this;
    } // setappointmentType()

    /**
     * Set the value of [version] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setversion($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->version !== $v) {
            $this->version = $v;
            $this->modifiedColumns[] = ActionPeer::VERSION;
        }


        return $this;
    } // setversion()

    /**
     * Set the value of [parentaction_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setparentActionId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->parentaction_id !== $v) {
            $this->parentaction_id = $v;
            $this->modifiedColumns[] = ActionPeer::PARENTACTION_ID;
        }


        return $this;
    } // setparentActionId()

    /**
     * Set the value of [uuid_id] column.
     *
     * @param int $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setuuidId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->uuid_id !== $v) {
            $this->uuid_id = $v;
            $this->modifiedColumns[] = ActionPeer::UUID_ID;
        }


        return $this;
    } // setuuidId()

    /**
     * Set the value of [dcm_study_uid] column.
     *
     * @param string $v new value
     * @return Action The current object (for fluent API support)
     */
    public function setdcmStudyUid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->dcm_study_uid !== $v) {
            $this->dcm_study_uid = $v;
            $this->modifiedColumns[] = ActionPeer::DCM_STUDY_UID;
        }


        return $this;
    } // setdcmStudyUid()

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

            if ($this->idx !== 0) {
                return false;
            }

            if ($this->isurgent !== false) {
                return false;
            }

            if ($this->uet !== 0) {
                return false;
            }

            if ($this->expose !== 1) {
                return false;
            }

            if ($this->coordagent !== '') {
                return false;
            }

            if ($this->coordinspector !== '') {
                return false;
            }

            if ($this->hospitaluidfrom !== '0') {
                return false;
            }

            if ($this->pacientinqueuetype !== 0) {
                return false;
            }

            if ($this->version !== 0) {
                return false;
            }

            if ($this->uuid_id !== 0) {
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
            $this->actiontype_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->event_id = ($row[$startcol + 7] !== null) ? (int) $row[$startcol + 7] : null;
            $this->idx = ($row[$startcol + 8] !== null) ? (int) $row[$startcol + 8] : null;
            $this->directiondate = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->status = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->setperson_id = ($row[$startcol + 11] !== null) ? (int) $row[$startcol + 11] : null;
            $this->isurgent = ($row[$startcol + 12] !== null) ? (boolean) $row[$startcol + 12] : null;
            $this->begdate = ($row[$startcol + 13] !== null) ? (string) $row[$startcol + 13] : null;
            $this->plannedenddate = ($row[$startcol + 14] !== null) ? (string) $row[$startcol + 14] : null;
            $this->enddate = ($row[$startcol + 15] !== null) ? (string) $row[$startcol + 15] : null;
            $this->note = ($row[$startcol + 16] !== null) ? (string) $row[$startcol + 16] : null;
            $this->person_id = ($row[$startcol + 17] !== null) ? (int) $row[$startcol + 17] : null;
            $this->office = ($row[$startcol + 18] !== null) ? (string) $row[$startcol + 18] : null;
            $this->amount = ($row[$startcol + 19] !== null) ? (double) $row[$startcol + 19] : null;
            $this->uet = ($row[$startcol + 20] !== null) ? (double) $row[$startcol + 20] : null;
            $this->expose = ($row[$startcol + 21] !== null) ? (int) $row[$startcol + 21] : null;
            $this->paystatus = ($row[$startcol + 22] !== null) ? (int) $row[$startcol + 22] : null;
            $this->account = ($row[$startcol + 23] !== null) ? (boolean) $row[$startcol + 23] : null;
            $this->finance_id = ($row[$startcol + 24] !== null) ? (int) $row[$startcol + 24] : null;
            $this->prescription_id = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->takentissuejournal_id = ($row[$startcol + 26] !== null) ? (int) $row[$startcol + 26] : null;
            $this->contract_id = ($row[$startcol + 27] !== null) ? (int) $row[$startcol + 27] : null;
            $this->coorddate = ($row[$startcol + 28] !== null) ? (string) $row[$startcol + 28] : null;
            $this->coordagent = ($row[$startcol + 29] !== null) ? (string) $row[$startcol + 29] : null;
            $this->coordinspector = ($row[$startcol + 30] !== null) ? (string) $row[$startcol + 30] : null;
            $this->coordtext = ($row[$startcol + 31] !== null) ? (string) $row[$startcol + 31] : null;
            $this->hospitaluidfrom = ($row[$startcol + 32] !== null) ? (string) $row[$startcol + 32] : null;
            $this->pacientinqueuetype = ($row[$startcol + 33] !== null) ? (int) $row[$startcol + 33] : null;
            $this->appointmenttype = ($row[$startcol + 34] !== null) ? (string) $row[$startcol + 34] : null;
            $this->version = ($row[$startcol + 35] !== null) ? (int) $row[$startcol + 35] : null;
            $this->parentaction_id = ($row[$startcol + 36] !== null) ? (int) $row[$startcol + 36] : null;
            $this->uuid_id = ($row[$startcol + 37] !== null) ? (int) $row[$startcol + 37] : null;
            $this->dcm_study_uid = ($row[$startcol + 38] !== null) ? (string) $row[$startcol + 38] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 39; // 39 = ActionPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating Action object", $e);
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

        if ($this->aCreatePerson !== null && $this->createperson_id !== $this->aCreatePerson->getid()) {
            $this->aCreatePerson = null;
        }
        if ($this->aModifyPerson !== null && $this->modifyperson_id !== $this->aModifyPerson->getid()) {
            $this->aModifyPerson = null;
        }
        if ($this->aActionType !== null && $this->actiontype_id !== $this->aActionType->getid()) {
            $this->aActionType = null;
        }
        if ($this->aEvent !== null && $this->event_id !== $this->aEvent->getid()) {
            $this->aEvent = null;
        }
        if ($this->aSetPerson !== null && $this->setperson_id !== $this->aSetPerson->getid()) {
            $this->aSetPerson = null;
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
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = ActionPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aEvent = null;
            $this->aCreatePerson = null;
            $this->aModifyPerson = null;
            $this->aSetPerson = null;
            $this->aActionType = null;
            $this->collActionPropertys = null;

            $this->collDrugCharts = null;

            $this->collDrugComponents = null;

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
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = ActionQuery::create()
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
            $con = Propel::getConnection(ActionPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(ActionPeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(ActionPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(ActionPeer::MODIFYDATETIME)) {
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
                ActionPeer::addInstanceToPool($this);
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

            if ($this->aEvent !== null) {
                if ($this->aEvent->isModified() || $this->aEvent->isNew()) {
                    $affectedRows += $this->aEvent->save($con);
                }
                $this->setEvent($this->aEvent);
            }

            if ($this->aCreatePerson !== null) {
                if ($this->aCreatePerson->isModified() || $this->aCreatePerson->isNew()) {
                    $affectedRows += $this->aCreatePerson->save($con);
                }
                $this->setCreatePerson($this->aCreatePerson);
            }

            if ($this->aModifyPerson !== null) {
                if ($this->aModifyPerson->isModified() || $this->aModifyPerson->isNew()) {
                    $affectedRows += $this->aModifyPerson->save($con);
                }
                $this->setModifyPerson($this->aModifyPerson);
            }

            if ($this->aSetPerson !== null) {
                if ($this->aSetPerson->isModified() || $this->aSetPerson->isNew()) {
                    $affectedRows += $this->aSetPerson->save($con);
                }
                $this->setSetPerson($this->aSetPerson);
            }

            if ($this->aActionType !== null) {
                if ($this->aActionType->isModified() || $this->aActionType->isNew()) {
                    $affectedRows += $this->aActionType->save($con);
                }
                $this->setActionType($this->aActionType);
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

            if ($this->actionPropertysScheduledForDeletion !== null) {
                if (!$this->actionPropertysScheduledForDeletion->isEmpty()) {
                    ActionPropertyQuery::create()
                        ->filterByPrimaryKeys($this->actionPropertysScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionPropertysScheduledForDeletion = null;
                }
            }

            if ($this->collActionPropertys !== null) {
                foreach ($this->collActionPropertys as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->drugChartsScheduledForDeletion !== null) {
                if (!$this->drugChartsScheduledForDeletion->isEmpty()) {
                    DrugChartQuery::create()
                        ->filterByPrimaryKeys($this->drugChartsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->drugChartsScheduledForDeletion = null;
                }
            }

            if ($this->collDrugCharts !== null) {
                foreach ($this->collDrugCharts as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->drugComponentsScheduledForDeletion !== null) {
                if (!$this->drugComponentsScheduledForDeletion->isEmpty()) {
                    DrugComponentQuery::create()
                        ->filterByPrimaryKeys($this->drugComponentsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->drugComponentsScheduledForDeletion = null;
                }
            }

            if ($this->collDrugComponents !== null) {
                foreach ($this->collDrugComponents as $referrerFK) {
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

        $this->modifiedColumns[] = ActionPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . ActionPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(ActionPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(ActionPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(ActionPeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(ActionPeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(ActionPeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(ActionPeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(ActionPeer::ACTIONTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`actionType_id`';
        }
        if ($this->isColumnModified(ActionPeer::EVENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`event_id`';
        }
        if ($this->isColumnModified(ActionPeer::IDX)) {
            $modifiedColumns[':p' . $index++]  = '`idx`';
        }
        if ($this->isColumnModified(ActionPeer::DIRECTIONDATE)) {
            $modifiedColumns[':p' . $index++]  = '`directionDate`';
        }
        if ($this->isColumnModified(ActionPeer::STATUS)) {
            $modifiedColumns[':p' . $index++]  = '`status`';
        }
        if ($this->isColumnModified(ActionPeer::SETPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`setPerson_id`';
        }
        if ($this->isColumnModified(ActionPeer::ISURGENT)) {
            $modifiedColumns[':p' . $index++]  = '`isUrgent`';
        }
        if ($this->isColumnModified(ActionPeer::BEGDATE)) {
            $modifiedColumns[':p' . $index++]  = '`begDate`';
        }
        if ($this->isColumnModified(ActionPeer::PLANNEDENDDATE)) {
            $modifiedColumns[':p' . $index++]  = '`plannedEndDate`';
        }
        if ($this->isColumnModified(ActionPeer::ENDDATE)) {
            $modifiedColumns[':p' . $index++]  = '`endDate`';
        }
        if ($this->isColumnModified(ActionPeer::NOTE)) {
            $modifiedColumns[':p' . $index++]  = '`note`';
        }
        if ($this->isColumnModified(ActionPeer::PERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`person_id`';
        }
        if ($this->isColumnModified(ActionPeer::OFFICE)) {
            $modifiedColumns[':p' . $index++]  = '`office`';
        }
        if ($this->isColumnModified(ActionPeer::AMOUNT)) {
            $modifiedColumns[':p' . $index++]  = '`amount`';
        }
        if ($this->isColumnModified(ActionPeer::UET)) {
            $modifiedColumns[':p' . $index++]  = '`uet`';
        }
        if ($this->isColumnModified(ActionPeer::EXPOSE)) {
            $modifiedColumns[':p' . $index++]  = '`expose`';
        }
        if ($this->isColumnModified(ActionPeer::PAYSTATUS)) {
            $modifiedColumns[':p' . $index++]  = '`payStatus`';
        }
        if ($this->isColumnModified(ActionPeer::ACCOUNT)) {
            $modifiedColumns[':p' . $index++]  = '`account`';
        }
        if ($this->isColumnModified(ActionPeer::FINANCE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`finance_id`';
        }
        if ($this->isColumnModified(ActionPeer::PRESCRIPTION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`prescription_id`';
        }
        if ($this->isColumnModified(ActionPeer::TAKENTISSUEJOURNAL_ID)) {
            $modifiedColumns[':p' . $index++]  = '`takenTissueJournal_id`';
        }
        if ($this->isColumnModified(ActionPeer::CONTRACT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`contract_id`';
        }
        if ($this->isColumnModified(ActionPeer::COORDDATE)) {
            $modifiedColumns[':p' . $index++]  = '`coordDate`';
        }
        if ($this->isColumnModified(ActionPeer::COORDAGENT)) {
            $modifiedColumns[':p' . $index++]  = '`coordAgent`';
        }
        if ($this->isColumnModified(ActionPeer::COORDINSPECTOR)) {
            $modifiedColumns[':p' . $index++]  = '`coordInspector`';
        }
        if ($this->isColumnModified(ActionPeer::COORDTEXT)) {
            $modifiedColumns[':p' . $index++]  = '`coordText`';
        }
        if ($this->isColumnModified(ActionPeer::HOSPITALUIDFROM)) {
            $modifiedColumns[':p' . $index++]  = '`hospitalUidFrom`';
        }
        if ($this->isColumnModified(ActionPeer::PACIENTINQUEUETYPE)) {
            $modifiedColumns[':p' . $index++]  = '`pacientInQueueType`';
        }
        if ($this->isColumnModified(ActionPeer::APPOINTMENTTYPE)) {
            $modifiedColumns[':p' . $index++]  = '`AppointmentType`';
        }
        if ($this->isColumnModified(ActionPeer::VERSION)) {
            $modifiedColumns[':p' . $index++]  = '`version`';
        }
        if ($this->isColumnModified(ActionPeer::PARENTACTION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`parentAction_id`';
        }
        if ($this->isColumnModified(ActionPeer::UUID_ID)) {
            $modifiedColumns[':p' . $index++]  = '`uuid_id`';
        }
        if ($this->isColumnModified(ActionPeer::DCM_STUDY_UID)) {
            $modifiedColumns[':p' . $index++]  = '`dcm_study_uid`';
        }

        $sql = sprintf(
            'INSERT INTO `Action` (%s) VALUES (%s)',
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
                    case '`actionType_id`':
                        $stmt->bindValue($identifier, $this->actiontype_id, PDO::PARAM_INT);
                        break;
                    case '`event_id`':
                        $stmt->bindValue($identifier, $this->event_id, PDO::PARAM_INT);
                        break;
                    case '`idx`':
                        $stmt->bindValue($identifier, $this->idx, PDO::PARAM_INT);
                        break;
                    case '`directionDate`':
                        $stmt->bindValue($identifier, $this->directiondate, PDO::PARAM_STR);
                        break;
                    case '`status`':
                        $stmt->bindValue($identifier, $this->status, PDO::PARAM_INT);
                        break;
                    case '`setPerson_id`':
                        $stmt->bindValue($identifier, $this->setperson_id, PDO::PARAM_INT);
                        break;
                    case '`isUrgent`':
                        $stmt->bindValue($identifier, (int) $this->isurgent, PDO::PARAM_INT);
                        break;
                    case '`begDate`':
                        $stmt->bindValue($identifier, $this->begdate, PDO::PARAM_STR);
                        break;
                    case '`plannedEndDate`':
                        $stmt->bindValue($identifier, $this->plannedenddate, PDO::PARAM_STR);
                        break;
                    case '`endDate`':
                        $stmt->bindValue($identifier, $this->enddate, PDO::PARAM_STR);
                        break;
                    case '`note`':
                        $stmt->bindValue($identifier, $this->note, PDO::PARAM_STR);
                        break;
                    case '`person_id`':
                        $stmt->bindValue($identifier, $this->person_id, PDO::PARAM_INT);
                        break;
                    case '`office`':
                        $stmt->bindValue($identifier, $this->office, PDO::PARAM_STR);
                        break;
                    case '`amount`':
                        $stmt->bindValue($identifier, $this->amount, PDO::PARAM_STR);
                        break;
                    case '`uet`':
                        $stmt->bindValue($identifier, $this->uet, PDO::PARAM_STR);
                        break;
                    case '`expose`':
                        $stmt->bindValue($identifier, $this->expose, PDO::PARAM_INT);
                        break;
                    case '`payStatus`':
                        $stmt->bindValue($identifier, $this->paystatus, PDO::PARAM_INT);
                        break;
                    case '`account`':
                        $stmt->bindValue($identifier, (int) $this->account, PDO::PARAM_INT);
                        break;
                    case '`finance_id`':
                        $stmt->bindValue($identifier, $this->finance_id, PDO::PARAM_INT);
                        break;
                    case '`prescription_id`':
                        $stmt->bindValue($identifier, $this->prescription_id, PDO::PARAM_INT);
                        break;
                    case '`takenTissueJournal_id`':
                        $stmt->bindValue($identifier, $this->takentissuejournal_id, PDO::PARAM_INT);
                        break;
                    case '`contract_id`':
                        $stmt->bindValue($identifier, $this->contract_id, PDO::PARAM_INT);
                        break;
                    case '`coordDate`':
                        $stmt->bindValue($identifier, $this->coorddate, PDO::PARAM_STR);
                        break;
                    case '`coordAgent`':
                        $stmt->bindValue($identifier, $this->coordagent, PDO::PARAM_STR);
                        break;
                    case '`coordInspector`':
                        $stmt->bindValue($identifier, $this->coordinspector, PDO::PARAM_STR);
                        break;
                    case '`coordText`':
                        $stmt->bindValue($identifier, $this->coordtext, PDO::PARAM_STR);
                        break;
                    case '`hospitalUidFrom`':
                        $stmt->bindValue($identifier, $this->hospitaluidfrom, PDO::PARAM_STR);
                        break;
                    case '`pacientInQueueType`':
                        $stmt->bindValue($identifier, $this->pacientinqueuetype, PDO::PARAM_INT);
                        break;
                    case '`AppointmentType`':
                        $stmt->bindValue($identifier, $this->appointmenttype, PDO::PARAM_STR);
                        break;
                    case '`version`':
                        $stmt->bindValue($identifier, $this->version, PDO::PARAM_INT);
                        break;
                    case '`parentAction_id`':
                        $stmt->bindValue($identifier, $this->parentaction_id, PDO::PARAM_INT);
                        break;
                    case '`uuid_id`':
                        $stmt->bindValue($identifier, $this->uuid_id, PDO::PARAM_INT);
                        break;
                    case '`dcm_study_uid`':
                        $stmt->bindValue($identifier, $this->dcm_study_uid, PDO::PARAM_STR);
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

            if ($this->aEvent !== null) {
                if (!$this->aEvent->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aEvent->getValidationFailures());
                }
            }

            if ($this->aCreatePerson !== null) {
                if (!$this->aCreatePerson->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aCreatePerson->getValidationFailures());
                }
            }

            if ($this->aModifyPerson !== null) {
                if (!$this->aModifyPerson->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aModifyPerson->getValidationFailures());
                }
            }

            if ($this->aSetPerson !== null) {
                if (!$this->aSetPerson->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aSetPerson->getValidationFailures());
                }
            }

            if ($this->aActionType !== null) {
                if (!$this->aActionType->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionType->getValidationFailures());
                }
            }


            if (($retval = ActionPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionPropertys !== null) {
                    foreach ($this->collActionPropertys as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collDrugCharts !== null) {
                    foreach ($this->collDrugCharts as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collDrugComponents !== null) {
                    foreach ($this->collDrugComponents as $referrerFK) {
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
        $pos = ActionPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getactionTypeId();
                break;
            case 7:
                return $this->geteventId();
                break;
            case 8:
                return $this->getidx();
                break;
            case 9:
                return $this->getdirectionDate();
                break;
            case 10:
                return $this->getstatus();
                break;
            case 11:
                return $this->getsetPersonId();
                break;
            case 12:
                return $this->getisUrgent();
                break;
            case 13:
                return $this->getbegDate();
                break;
            case 14:
                return $this->getplannedEndDate();
                break;
            case 15:
                return $this->getendDate();
                break;
            case 16:
                return $this->getnote();
                break;
            case 17:
                return $this->getpersonId();
                break;
            case 18:
                return $this->getoffice();
                break;
            case 19:
                return $this->getamount();
                break;
            case 20:
                return $this->getuet();
                break;
            case 21:
                return $this->getexpose();
                break;
            case 22:
                return $this->getpayStatus();
                break;
            case 23:
                return $this->getaccount();
                break;
            case 24:
                return $this->getfinanceId();
                break;
            case 25:
                return $this->getprescriptionId();
                break;
            case 26:
                return $this->gettakenTissueJournalId();
                break;
            case 27:
                return $this->getcontractId();
                break;
            case 28:
                return $this->getcoordDate();
                break;
            case 29:
                return $this->getcoordAgent();
                break;
            case 30:
                return $this->getcoordInspector();
                break;
            case 31:
                return $this->getcoordText();
                break;
            case 32:
                return $this->gethospitalUidFrom();
                break;
            case 33:
                return $this->getpacientInQueueType();
                break;
            case 34:
                return $this->getappointmentType();
                break;
            case 35:
                return $this->getversion();
                break;
            case 36:
                return $this->getparentActionId();
                break;
            case 37:
                return $this->getuuidId();
                break;
            case 38:
                return $this->getdcmStudyUid();
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
        if (isset($alreadyDumpedObjects['Action'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['Action'][$this->getPrimaryKey()] = true;
        $keys = ActionPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getactionTypeId(),
            $keys[7] => $this->geteventId(),
            $keys[8] => $this->getidx(),
            $keys[9] => $this->getdirectionDate(),
            $keys[10] => $this->getstatus(),
            $keys[11] => $this->getsetPersonId(),
            $keys[12] => $this->getisUrgent(),
            $keys[13] => $this->getbegDate(),
            $keys[14] => $this->getplannedEndDate(),
            $keys[15] => $this->getendDate(),
            $keys[16] => $this->getnote(),
            $keys[17] => $this->getpersonId(),
            $keys[18] => $this->getoffice(),
            $keys[19] => $this->getamount(),
            $keys[20] => $this->getuet(),
            $keys[21] => $this->getexpose(),
            $keys[22] => $this->getpayStatus(),
            $keys[23] => $this->getaccount(),
            $keys[24] => $this->getfinanceId(),
            $keys[25] => $this->getprescriptionId(),
            $keys[26] => $this->gettakenTissueJournalId(),
            $keys[27] => $this->getcontractId(),
            $keys[28] => $this->getcoordDate(),
            $keys[29] => $this->getcoordAgent(),
            $keys[30] => $this->getcoordInspector(),
            $keys[31] => $this->getcoordText(),
            $keys[32] => $this->gethospitalUidFrom(),
            $keys[33] => $this->getpacientInQueueType(),
            $keys[34] => $this->getappointmentType(),
            $keys[35] => $this->getversion(),
            $keys[36] => $this->getparentActionId(),
            $keys[37] => $this->getuuidId(),
            $keys[38] => $this->getdcmStudyUid(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aEvent) {
                $result['Event'] = $this->aEvent->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aCreatePerson) {
                $result['CreatePerson'] = $this->aCreatePerson->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aModifyPerson) {
                $result['ModifyPerson'] = $this->aModifyPerson->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aSetPerson) {
                $result['SetPerson'] = $this->aSetPerson->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionType) {
                $result['ActionType'] = $this->aActionType->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActionPropertys) {
                $result['ActionPropertys'] = $this->collActionPropertys->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collDrugCharts) {
                $result['DrugCharts'] = $this->collDrugCharts->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collDrugComponents) {
                $result['DrugComponents'] = $this->collDrugComponents->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = ActionPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setactionTypeId($value);
                break;
            case 7:
                $this->seteventId($value);
                break;
            case 8:
                $this->setidx($value);
                break;
            case 9:
                $this->setdirectionDate($value);
                break;
            case 10:
                $this->setstatus($value);
                break;
            case 11:
                $this->setsetPersonId($value);
                break;
            case 12:
                $this->setisUrgent($value);
                break;
            case 13:
                $this->setbegDate($value);
                break;
            case 14:
                $this->setplannedEndDate($value);
                break;
            case 15:
                $this->setendDate($value);
                break;
            case 16:
                $this->setnote($value);
                break;
            case 17:
                $this->setpersonId($value);
                break;
            case 18:
                $this->setoffice($value);
                break;
            case 19:
                $this->setamount($value);
                break;
            case 20:
                $this->setuet($value);
                break;
            case 21:
                $this->setexpose($value);
                break;
            case 22:
                $this->setpayStatus($value);
                break;
            case 23:
                $this->setaccount($value);
                break;
            case 24:
                $this->setfinanceId($value);
                break;
            case 25:
                $this->setprescriptionId($value);
                break;
            case 26:
                $this->settakenTissueJournalId($value);
                break;
            case 27:
                $this->setcontractId($value);
                break;
            case 28:
                $this->setcoordDate($value);
                break;
            case 29:
                $this->setcoordAgent($value);
                break;
            case 30:
                $this->setcoordInspector($value);
                break;
            case 31:
                $this->setcoordText($value);
                break;
            case 32:
                $this->sethospitalUidFrom($value);
                break;
            case 33:
                $this->setpacientInQueueType($value);
                break;
            case 34:
                $this->setappointmentType($value);
                break;
            case 35:
                $this->setversion($value);
                break;
            case 36:
                $this->setparentActionId($value);
                break;
            case 37:
                $this->setuuidId($value);
                break;
            case 38:
                $this->setdcmStudyUid($value);
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
        $keys = ActionPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setactionTypeId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->seteventId($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setidx($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setdirectionDate($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setstatus($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setsetPersonId($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setisUrgent($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setbegDate($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setplannedEndDate($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setendDate($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setnote($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setpersonId($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setoffice($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setamount($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setuet($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setexpose($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setpayStatus($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setaccount($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setfinanceId($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->setprescriptionId($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->settakenTissueJournalId($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setcontractId($arr[$keys[27]]);
        if (array_key_exists($keys[28], $arr)) $this->setcoordDate($arr[$keys[28]]);
        if (array_key_exists($keys[29], $arr)) $this->setcoordAgent($arr[$keys[29]]);
        if (array_key_exists($keys[30], $arr)) $this->setcoordInspector($arr[$keys[30]]);
        if (array_key_exists($keys[31], $arr)) $this->setcoordText($arr[$keys[31]]);
        if (array_key_exists($keys[32], $arr)) $this->sethospitalUidFrom($arr[$keys[32]]);
        if (array_key_exists($keys[33], $arr)) $this->setpacientInQueueType($arr[$keys[33]]);
        if (array_key_exists($keys[34], $arr)) $this->setappointmentType($arr[$keys[34]]);
        if (array_key_exists($keys[35], $arr)) $this->setversion($arr[$keys[35]]);
        if (array_key_exists($keys[36], $arr)) $this->setparentActionId($arr[$keys[36]]);
        if (array_key_exists($keys[37], $arr)) $this->setuuidId($arr[$keys[37]]);
        if (array_key_exists($keys[38], $arr)) $this->setdcmStudyUid($arr[$keys[38]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(ActionPeer::DATABASE_NAME);

        if ($this->isColumnModified(ActionPeer::ID)) $criteria->add(ActionPeer::ID, $this->id);
        if ($this->isColumnModified(ActionPeer::CREATEDATETIME)) $criteria->add(ActionPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(ActionPeer::CREATEPERSON_ID)) $criteria->add(ActionPeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(ActionPeer::MODIFYDATETIME)) $criteria->add(ActionPeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(ActionPeer::MODIFYPERSON_ID)) $criteria->add(ActionPeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(ActionPeer::DELETED)) $criteria->add(ActionPeer::DELETED, $this->deleted);
        if ($this->isColumnModified(ActionPeer::ACTIONTYPE_ID)) $criteria->add(ActionPeer::ACTIONTYPE_ID, $this->actiontype_id);
        if ($this->isColumnModified(ActionPeer::EVENT_ID)) $criteria->add(ActionPeer::EVENT_ID, $this->event_id);
        if ($this->isColumnModified(ActionPeer::IDX)) $criteria->add(ActionPeer::IDX, $this->idx);
        if ($this->isColumnModified(ActionPeer::DIRECTIONDATE)) $criteria->add(ActionPeer::DIRECTIONDATE, $this->directiondate);
        if ($this->isColumnModified(ActionPeer::STATUS)) $criteria->add(ActionPeer::STATUS, $this->status);
        if ($this->isColumnModified(ActionPeer::SETPERSON_ID)) $criteria->add(ActionPeer::SETPERSON_ID, $this->setperson_id);
        if ($this->isColumnModified(ActionPeer::ISURGENT)) $criteria->add(ActionPeer::ISURGENT, $this->isurgent);
        if ($this->isColumnModified(ActionPeer::BEGDATE)) $criteria->add(ActionPeer::BEGDATE, $this->begdate);
        if ($this->isColumnModified(ActionPeer::PLANNEDENDDATE)) $criteria->add(ActionPeer::PLANNEDENDDATE, $this->plannedenddate);
        if ($this->isColumnModified(ActionPeer::ENDDATE)) $criteria->add(ActionPeer::ENDDATE, $this->enddate);
        if ($this->isColumnModified(ActionPeer::NOTE)) $criteria->add(ActionPeer::NOTE, $this->note);
        if ($this->isColumnModified(ActionPeer::PERSON_ID)) $criteria->add(ActionPeer::PERSON_ID, $this->person_id);
        if ($this->isColumnModified(ActionPeer::OFFICE)) $criteria->add(ActionPeer::OFFICE, $this->office);
        if ($this->isColumnModified(ActionPeer::AMOUNT)) $criteria->add(ActionPeer::AMOUNT, $this->amount);
        if ($this->isColumnModified(ActionPeer::UET)) $criteria->add(ActionPeer::UET, $this->uet);
        if ($this->isColumnModified(ActionPeer::EXPOSE)) $criteria->add(ActionPeer::EXPOSE, $this->expose);
        if ($this->isColumnModified(ActionPeer::PAYSTATUS)) $criteria->add(ActionPeer::PAYSTATUS, $this->paystatus);
        if ($this->isColumnModified(ActionPeer::ACCOUNT)) $criteria->add(ActionPeer::ACCOUNT, $this->account);
        if ($this->isColumnModified(ActionPeer::FINANCE_ID)) $criteria->add(ActionPeer::FINANCE_ID, $this->finance_id);
        if ($this->isColumnModified(ActionPeer::PRESCRIPTION_ID)) $criteria->add(ActionPeer::PRESCRIPTION_ID, $this->prescription_id);
        if ($this->isColumnModified(ActionPeer::TAKENTISSUEJOURNAL_ID)) $criteria->add(ActionPeer::TAKENTISSUEJOURNAL_ID, $this->takentissuejournal_id);
        if ($this->isColumnModified(ActionPeer::CONTRACT_ID)) $criteria->add(ActionPeer::CONTRACT_ID, $this->contract_id);
        if ($this->isColumnModified(ActionPeer::COORDDATE)) $criteria->add(ActionPeer::COORDDATE, $this->coorddate);
        if ($this->isColumnModified(ActionPeer::COORDAGENT)) $criteria->add(ActionPeer::COORDAGENT, $this->coordagent);
        if ($this->isColumnModified(ActionPeer::COORDINSPECTOR)) $criteria->add(ActionPeer::COORDINSPECTOR, $this->coordinspector);
        if ($this->isColumnModified(ActionPeer::COORDTEXT)) $criteria->add(ActionPeer::COORDTEXT, $this->coordtext);
        if ($this->isColumnModified(ActionPeer::HOSPITALUIDFROM)) $criteria->add(ActionPeer::HOSPITALUIDFROM, $this->hospitaluidfrom);
        if ($this->isColumnModified(ActionPeer::PACIENTINQUEUETYPE)) $criteria->add(ActionPeer::PACIENTINQUEUETYPE, $this->pacientinqueuetype);
        if ($this->isColumnModified(ActionPeer::APPOINTMENTTYPE)) $criteria->add(ActionPeer::APPOINTMENTTYPE, $this->appointmenttype);
        if ($this->isColumnModified(ActionPeer::VERSION)) $criteria->add(ActionPeer::VERSION, $this->version);
        if ($this->isColumnModified(ActionPeer::PARENTACTION_ID)) $criteria->add(ActionPeer::PARENTACTION_ID, $this->parentaction_id);
        if ($this->isColumnModified(ActionPeer::UUID_ID)) $criteria->add(ActionPeer::UUID_ID, $this->uuid_id);
        if ($this->isColumnModified(ActionPeer::DCM_STUDY_UID)) $criteria->add(ActionPeer::DCM_STUDY_UID, $this->dcm_study_uid);

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
        $criteria = new Criteria(ActionPeer::DATABASE_NAME);
        $criteria->add(ActionPeer::ID, $this->id);

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
     * @param object $copyObj An object of Action (or compatible) type.
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
        $copyObj->setactionTypeId($this->getactionTypeId());
        $copyObj->seteventId($this->geteventId());
        $copyObj->setidx($this->getidx());
        $copyObj->setdirectionDate($this->getdirectionDate());
        $copyObj->setstatus($this->getstatus());
        $copyObj->setsetPersonId($this->getsetPersonId());
        $copyObj->setisUrgent($this->getisUrgent());
        $copyObj->setbegDate($this->getbegDate());
        $copyObj->setplannedEndDate($this->getplannedEndDate());
        $copyObj->setendDate($this->getendDate());
        $copyObj->setnote($this->getnote());
        $copyObj->setpersonId($this->getpersonId());
        $copyObj->setoffice($this->getoffice());
        $copyObj->setamount($this->getamount());
        $copyObj->setuet($this->getuet());
        $copyObj->setexpose($this->getexpose());
        $copyObj->setpayStatus($this->getpayStatus());
        $copyObj->setaccount($this->getaccount());
        $copyObj->setfinanceId($this->getfinanceId());
        $copyObj->setprescriptionId($this->getprescriptionId());
        $copyObj->settakenTissueJournalId($this->gettakenTissueJournalId());
        $copyObj->setcontractId($this->getcontractId());
        $copyObj->setcoordDate($this->getcoordDate());
        $copyObj->setcoordAgent($this->getcoordAgent());
        $copyObj->setcoordInspector($this->getcoordInspector());
        $copyObj->setcoordText($this->getcoordText());
        $copyObj->sethospitalUidFrom($this->gethospitalUidFrom());
        $copyObj->setpacientInQueueType($this->getpacientInQueueType());
        $copyObj->setappointmentType($this->getappointmentType());
        $copyObj->setversion($this->getversion());
        $copyObj->setparentActionId($this->getparentActionId());
        $copyObj->setuuidId($this->getuuidId());
        $copyObj->setdcmStudyUid($this->getdcmStudyUid());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActionPropertys() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionProperty($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getDrugCharts() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addDrugChart($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getDrugComponents() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addDrugComponent($relObj->copy($deepCopy));
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
     * @return Action Clone of current object.
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
     * @return ActionPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new ActionPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a Event object.
     *
     * @param             Event $v
     * @return Action The current object (for fluent API support)
     * @throws PropelException
     */
    public function setEvent(Event $v = null)
    {
        if ($v === null) {
            $this->seteventId(NULL);
        } else {
            $this->seteventId($v->getid());
        }

        $this->aEvent = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Event object, it will not be re-added.
        if ($v !== null) {
            $v->addAction($this);
        }


        return $this;
    }


    /**
     * Get the associated Event object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Event The associated Event object.
     * @throws PropelException
     */
    public function getEvent(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aEvent === null && ($this->event_id !== null) && $doQuery) {
            $this->aEvent = EventQuery::create()->findPk($this->event_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aEvent->addActions($this);
             */
        }

        return $this->aEvent;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Action The current object (for fluent API support)
     * @throws PropelException
     */
    public function setCreatePerson(Person $v = null)
    {
        if ($v === null) {
            $this->setcreatePersonId(NULL);
        } else {
            $this->setcreatePersonId($v->getid());
        }

        $this->aCreatePerson = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Person object, it will not be re-added.
        if ($v !== null) {
            $v->addActionRelatedBycreatePersonId($this);
        }


        return $this;
    }


    /**
     * Get the associated Person object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Person The associated Person object.
     * @throws PropelException
     */
    public function getCreatePerson(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aCreatePerson === null && ($this->createperson_id !== null) && $doQuery) {
            $this->aCreatePerson = PersonQuery::create()->findPk($this->createperson_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aCreatePerson->addActionsRelatedBycreatePersonId($this);
             */
        }

        return $this->aCreatePerson;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Action The current object (for fluent API support)
     * @throws PropelException
     */
    public function setModifyPerson(Person $v = null)
    {
        if ($v === null) {
            $this->setmodifyPersonId(NULL);
        } else {
            $this->setmodifyPersonId($v->getid());
        }

        $this->aModifyPerson = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Person object, it will not be re-added.
        if ($v !== null) {
            $v->addActionRelatedBymodifyPersonId($this);
        }


        return $this;
    }


    /**
     * Get the associated Person object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Person The associated Person object.
     * @throws PropelException
     */
    public function getModifyPerson(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aModifyPerson === null && ($this->modifyperson_id !== null) && $doQuery) {
            $this->aModifyPerson = PersonQuery::create()->findPk($this->modifyperson_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aModifyPerson->addActionsRelatedBymodifyPersonId($this);
             */
        }

        return $this->aModifyPerson;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Action The current object (for fluent API support)
     * @throws PropelException
     */
    public function setSetPerson(Person $v = null)
    {
        if ($v === null) {
            $this->setsetPersonId(NULL);
        } else {
            $this->setsetPersonId($v->getid());
        }

        $this->aSetPerson = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Person object, it will not be re-added.
        if ($v !== null) {
            $v->addActionRelatedBysetPersonId($this);
        }


        return $this;
    }


    /**
     * Get the associated Person object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Person The associated Person object.
     * @throws PropelException
     */
    public function getSetPerson(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aSetPerson === null && ($this->setperson_id !== null) && $doQuery) {
            $this->aSetPerson = PersonQuery::create()->findPk($this->setperson_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aSetPerson->addActionsRelatedBysetPersonId($this);
             */
        }

        return $this->aSetPerson;
    }

    /**
     * Declares an association between this object and a ActionType object.
     *
     * @param             ActionType $v
     * @return Action The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionType(ActionType $v = null)
    {
        if ($v === null) {
            $this->setactionTypeId(NULL);
        } else {
            $this->setactionTypeId($v->getid());
        }

        $this->aActionType = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the ActionType object, it will not be re-added.
        if ($v !== null) {
            $v->addAction($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionType object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionType The associated ActionType object.
     * @throws PropelException
     */
    public function getActionType(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionType === null && ($this->actiontype_id !== null) && $doQuery) {
            $this->aActionType = ActionTypeQuery::create()->findPk($this->actiontype_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aActionType->addActions($this);
             */
        }

        return $this->aActionType;
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
        if ('ActionProperty' == $relationName) {
            $this->initActionPropertys();
        }
        if ('DrugChart' == $relationName) {
            $this->initDrugCharts();
        }
        if ('DrugComponent' == $relationName) {
            $this->initDrugComponents();
        }
    }

    /**
     * Clears out the collActionPropertys collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Action The current object (for fluent API support)
     * @see        addActionPropertys()
     */
    public function clearActionPropertys()
    {
        $this->collActionPropertys = null; // important to set this to null since that means it is uninitialized
        $this->collActionPropertysPartial = null;

        return $this;
    }

    /**
     * reset is the collActionPropertys collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionPropertys($v = true)
    {
        $this->collActionPropertysPartial = $v;
    }

    /**
     * Initializes the collActionPropertys collection.
     *
     * By default this just sets the collActionPropertys collection to an empty array (like clearcollActionPropertys());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionPropertys($overrideExisting = true)
    {
        if (null !== $this->collActionPropertys && !$overrideExisting) {
            return;
        }
        $this->collActionPropertys = new PropelObjectCollection();
        $this->collActionPropertys->setModel('ActionProperty');
    }

    /**
     * Gets an array of ActionProperty objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Action is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     * @throws PropelException
     */
    public function getActionPropertys($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertysPartial && !$this->isNew();
        if (null === $this->collActionPropertys || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionPropertys) {
                // return empty collection
                $this->initActionPropertys();
            } else {
                $collActionPropertys = ActionPropertyQuery::create(null, $criteria)
                    ->filterByAction($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionPropertysPartial && count($collActionPropertys)) {
                      $this->initActionPropertys(false);

                      foreach($collActionPropertys as $obj) {
                        if (false == $this->collActionPropertys->contains($obj)) {
                          $this->collActionPropertys->append($obj);
                        }
                      }

                      $this->collActionPropertysPartial = true;
                    }

                    $collActionPropertys->getInternalIterator()->rewind();
                    return $collActionPropertys;
                }

                if($partial && $this->collActionPropertys) {
                    foreach($this->collActionPropertys as $obj) {
                        if($obj->isNew()) {
                            $collActionPropertys[] = $obj;
                        }
                    }
                }

                $this->collActionPropertys = $collActionPropertys;
                $this->collActionPropertysPartial = false;
            }
        }

        return $this->collActionPropertys;
    }

    /**
     * Sets a collection of ActionProperty objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionPropertys A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Action The current object (for fluent API support)
     */
    public function setActionPropertys(PropelCollection $actionPropertys, PropelPDO $con = null)
    {
        $actionPropertysToDelete = $this->getActionPropertys(new Criteria(), $con)->diff($actionPropertys);

        $this->actionPropertysScheduledForDeletion = unserialize(serialize($actionPropertysToDelete));

        foreach ($actionPropertysToDelete as $actionPropertyRemoved) {
            $actionPropertyRemoved->setAction(null);
        }

        $this->collActionPropertys = null;
        foreach ($actionPropertys as $actionProperty) {
            $this->addActionProperty($actionProperty);
        }

        $this->collActionPropertys = $actionPropertys;
        $this->collActionPropertysPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ActionProperty objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ActionProperty objects.
     * @throws PropelException
     */
    public function countActionPropertys(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertysPartial && !$this->isNew();
        if (null === $this->collActionPropertys || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionPropertys) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionPropertys());
            }
            $query = ActionPropertyQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByAction($this)
                ->count($con);
        }

        return count($this->collActionPropertys);
    }

    /**
     * Method called to associate a ActionProperty object to this object
     * through the ActionProperty foreign key attribute.
     *
     * @param    ActionProperty $l ActionProperty
     * @return Action The current object (for fluent API support)
     */
    public function addActionProperty(ActionProperty $l)
    {
        if ($this->collActionPropertys === null) {
            $this->initActionPropertys();
            $this->collActionPropertysPartial = true;
        }
        if (!in_array($l, $this->collActionPropertys->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionProperty($l);
        }

        return $this;
    }

    /**
     * @param	ActionProperty $actionProperty The actionProperty object to add.
     */
    protected function doAddActionProperty($actionProperty)
    {
        $this->collActionPropertys[]= $actionProperty;
        $actionProperty->setAction($this);
    }

    /**
     * @param	ActionProperty $actionProperty The actionProperty object to remove.
     * @return Action The current object (for fluent API support)
     */
    public function removeActionProperty($actionProperty)
    {
        if ($this->getActionPropertys()->contains($actionProperty)) {
            $this->collActionPropertys->remove($this->collActionPropertys->search($actionProperty));
            if (null === $this->actionPropertysScheduledForDeletion) {
                $this->actionPropertysScheduledForDeletion = clone $this->collActionPropertys;
                $this->actionPropertysScheduledForDeletion->clear();
            }
            $this->actionPropertysScheduledForDeletion[]= clone $actionProperty;
            $actionProperty->setAction(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyType', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyString($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyString', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyInteger($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyInteger', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyDate($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyDate', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyDouble($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyDouble', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyOrgStructure', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyFDRecord($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyFDRecord', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }

    /**
     * Clears out the collDrugCharts collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Action The current object (for fluent API support)
     * @see        addDrugCharts()
     */
    public function clearDrugCharts()
    {
        $this->collDrugCharts = null; // important to set this to null since that means it is uninitialized
        $this->collDrugChartsPartial = null;

        return $this;
    }

    /**
     * reset is the collDrugCharts collection loaded partially
     *
     * @return void
     */
    public function resetPartialDrugCharts($v = true)
    {
        $this->collDrugChartsPartial = $v;
    }

    /**
     * Initializes the collDrugCharts collection.
     *
     * By default this just sets the collDrugCharts collection to an empty array (like clearcollDrugCharts());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initDrugCharts($overrideExisting = true)
    {
        if (null !== $this->collDrugCharts && !$overrideExisting) {
            return;
        }
        $this->collDrugCharts = new PropelObjectCollection();
        $this->collDrugCharts->setModel('DrugChart');
    }

    /**
     * Gets an array of DrugChart objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Action is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|DrugChart[] List of DrugChart objects
     * @throws PropelException
     */
    public function getDrugCharts($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collDrugChartsPartial && !$this->isNew();
        if (null === $this->collDrugCharts || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collDrugCharts) {
                // return empty collection
                $this->initDrugCharts();
            } else {
                $collDrugCharts = DrugChartQuery::create(null, $criteria)
                    ->filterByAction($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collDrugChartsPartial && count($collDrugCharts)) {
                      $this->initDrugCharts(false);

                      foreach($collDrugCharts as $obj) {
                        if (false == $this->collDrugCharts->contains($obj)) {
                          $this->collDrugCharts->append($obj);
                        }
                      }

                      $this->collDrugChartsPartial = true;
                    }

                    $collDrugCharts->getInternalIterator()->rewind();
                    return $collDrugCharts;
                }

                if($partial && $this->collDrugCharts) {
                    foreach($this->collDrugCharts as $obj) {
                        if($obj->isNew()) {
                            $collDrugCharts[] = $obj;
                        }
                    }
                }

                $this->collDrugCharts = $collDrugCharts;
                $this->collDrugChartsPartial = false;
            }
        }

        return $this->collDrugCharts;
    }

    /**
     * Sets a collection of DrugChart objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $drugCharts A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Action The current object (for fluent API support)
     */
    public function setDrugCharts(PropelCollection $drugCharts, PropelPDO $con = null)
    {
        $drugChartsToDelete = $this->getDrugCharts(new Criteria(), $con)->diff($drugCharts);

        $this->drugChartsScheduledForDeletion = unserialize(serialize($drugChartsToDelete));

        foreach ($drugChartsToDelete as $drugChartRemoved) {
            $drugChartRemoved->setAction(null);
        }

        $this->collDrugCharts = null;
        foreach ($drugCharts as $drugChart) {
            $this->addDrugChart($drugChart);
        }

        $this->collDrugCharts = $drugCharts;
        $this->collDrugChartsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related DrugChart objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related DrugChart objects.
     * @throws PropelException
     */
    public function countDrugCharts(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collDrugChartsPartial && !$this->isNew();
        if (null === $this->collDrugCharts || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collDrugCharts) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getDrugCharts());
            }
            $query = DrugChartQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByAction($this)
                ->count($con);
        }

        return count($this->collDrugCharts);
    }

    /**
     * Method called to associate a DrugChart object to this object
     * through the DrugChart foreign key attribute.
     *
     * @param    DrugChart $l DrugChart
     * @return Action The current object (for fluent API support)
     */
    public function addDrugChart(DrugChart $l)
    {
        if ($this->collDrugCharts === null) {
            $this->initDrugCharts();
            $this->collDrugChartsPartial = true;
        }
        if (!in_array($l, $this->collDrugCharts->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddDrugChart($l);
        }

        return $this;
    }

    /**
     * @param	DrugChart $drugChart The drugChart object to add.
     */
    protected function doAddDrugChart($drugChart)
    {
        $this->collDrugCharts[]= $drugChart;
        $drugChart->setAction($this);
    }

    /**
     * @param	DrugChart $drugChart The drugChart object to remove.
     * @return Action The current object (for fluent API support)
     */
    public function removeDrugChart($drugChart)
    {
        if ($this->getDrugCharts()->contains($drugChart)) {
            $this->collDrugCharts->remove($this->collDrugCharts->search($drugChart));
            if (null === $this->drugChartsScheduledForDeletion) {
                $this->drugChartsScheduledForDeletion = clone $this->collDrugCharts;
                $this->drugChartsScheduledForDeletion->clear();
            }
            $this->drugChartsScheduledForDeletion[]= clone $drugChart;
            $drugChart->setAction(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related DrugCharts from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugChart[] List of DrugChart objects
     */
    public function getDrugChartsJoinDrugChartRelatedBymasterId($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugChartQuery::create(null, $criteria);
        $query->joinWith('DrugChartRelatedBymasterId', $join_behavior);

        return $this->getDrugCharts($query, $con);
    }

    /**
     * Clears out the collDrugComponents collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Action The current object (for fluent API support)
     * @see        addDrugComponents()
     */
    public function clearDrugComponents()
    {
        $this->collDrugComponents = null; // important to set this to null since that means it is uninitialized
        $this->collDrugComponentsPartial = null;

        return $this;
    }

    /**
     * reset is the collDrugComponents collection loaded partially
     *
     * @return void
     */
    public function resetPartialDrugComponents($v = true)
    {
        $this->collDrugComponentsPartial = $v;
    }

    /**
     * Initializes the collDrugComponents collection.
     *
     * By default this just sets the collDrugComponents collection to an empty array (like clearcollDrugComponents());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initDrugComponents($overrideExisting = true)
    {
        if (null !== $this->collDrugComponents && !$overrideExisting) {
            return;
        }
        $this->collDrugComponents = new PropelObjectCollection();
        $this->collDrugComponents->setModel('DrugComponent');
    }

    /**
     * Gets an array of DrugComponent objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Action is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     * @throws PropelException
     */
    public function getDrugComponents($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collDrugComponentsPartial && !$this->isNew();
        if (null === $this->collDrugComponents || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collDrugComponents) {
                // return empty collection
                $this->initDrugComponents();
            } else {
                $collDrugComponents = DrugComponentQuery::create(null, $criteria)
                    ->filterByAction($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collDrugComponentsPartial && count($collDrugComponents)) {
                      $this->initDrugComponents(false);

                      foreach($collDrugComponents as $obj) {
                        if (false == $this->collDrugComponents->contains($obj)) {
                          $this->collDrugComponents->append($obj);
                        }
                      }

                      $this->collDrugComponentsPartial = true;
                    }

                    $collDrugComponents->getInternalIterator()->rewind();
                    return $collDrugComponents;
                }

                if($partial && $this->collDrugComponents) {
                    foreach($this->collDrugComponents as $obj) {
                        if($obj->isNew()) {
                            $collDrugComponents[] = $obj;
                        }
                    }
                }

                $this->collDrugComponents = $collDrugComponents;
                $this->collDrugComponentsPartial = false;
            }
        }

        return $this->collDrugComponents;
    }

    /**
     * Sets a collection of DrugComponent objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $drugComponents A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Action The current object (for fluent API support)
     */
    public function setDrugComponents(PropelCollection $drugComponents, PropelPDO $con = null)
    {
        $drugComponentsToDelete = $this->getDrugComponents(new Criteria(), $con)->diff($drugComponents);

        $this->drugComponentsScheduledForDeletion = unserialize(serialize($drugComponentsToDelete));

        foreach ($drugComponentsToDelete as $drugComponentRemoved) {
            $drugComponentRemoved->setAction(null);
        }

        $this->collDrugComponents = null;
        foreach ($drugComponents as $drugComponent) {
            $this->addDrugComponent($drugComponent);
        }

        $this->collDrugComponents = $drugComponents;
        $this->collDrugComponentsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related DrugComponent objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related DrugComponent objects.
     * @throws PropelException
     */
    public function countDrugComponents(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collDrugComponentsPartial && !$this->isNew();
        if (null === $this->collDrugComponents || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collDrugComponents) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getDrugComponents());
            }
            $query = DrugComponentQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByAction($this)
                ->count($con);
        }

        return count($this->collDrugComponents);
    }

    /**
     * Method called to associate a DrugComponent object to this object
     * through the DrugComponent foreign key attribute.
     *
     * @param    DrugComponent $l DrugComponent
     * @return Action The current object (for fluent API support)
     */
    public function addDrugComponent(DrugComponent $l)
    {
        if ($this->collDrugComponents === null) {
            $this->initDrugComponents();
            $this->collDrugComponentsPartial = true;
        }
        if (!in_array($l, $this->collDrugComponents->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddDrugComponent($l);
        }

        return $this;
    }

    /**
     * @param	DrugComponent $drugComponent The drugComponent object to add.
     */
    protected function doAddDrugComponent($drugComponent)
    {
        $this->collDrugComponents[]= $drugComponent;
        $drugComponent->setAction($this);
    }

    /**
     * @param	DrugComponent $drugComponent The drugComponent object to remove.
     * @return Action The current object (for fluent API support)
     */
    public function removeDrugComponent($drugComponent)
    {
        if ($this->getDrugComponents()->contains($drugComponent)) {
            $this->collDrugComponents->remove($this->collDrugComponents->search($drugComponent));
            if (null === $this->drugComponentsScheduledForDeletion) {
                $this->drugComponentsScheduledForDeletion = clone $this->collDrugComponents;
                $this->drugComponentsScheduledForDeletion->clear();
            }
            $this->drugComponentsScheduledForDeletion[]= clone $drugComponent;
            $drugComponent->setAction(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     */
    public function getDrugComponentsJoinrbUnit($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugComponentQuery::create(null, $criteria);
        $query->joinWith('rbUnit', $join_behavior);

        return $this->getDrugComponents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Action is new, it will return
     * an empty collection; or if this Action has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Action.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     */
    public function getDrugComponentsJoinrlsNomen($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugComponentQuery::create(null, $criteria);
        $query->joinWith('rlsNomen', $join_behavior);

        return $this->getDrugComponents($query, $con);
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
        $this->actiontype_id = null;
        $this->event_id = null;
        $this->idx = null;
        $this->directiondate = null;
        $this->status = null;
        $this->setperson_id = null;
        $this->isurgent = null;
        $this->begdate = null;
        $this->plannedenddate = null;
        $this->enddate = null;
        $this->note = null;
        $this->person_id = null;
        $this->office = null;
        $this->amount = null;
        $this->uet = null;
        $this->expose = null;
        $this->paystatus = null;
        $this->account = null;
        $this->finance_id = null;
        $this->prescription_id = null;
        $this->takentissuejournal_id = null;
        $this->contract_id = null;
        $this->coorddate = null;
        $this->coordagent = null;
        $this->coordinspector = null;
        $this->coordtext = null;
        $this->hospitaluidfrom = null;
        $this->pacientinqueuetype = null;
        $this->appointmenttype = null;
        $this->version = null;
        $this->parentaction_id = null;
        $this->uuid_id = null;
        $this->dcm_study_uid = null;
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
            if ($this->collActionPropertys) {
                foreach ($this->collActionPropertys as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collDrugCharts) {
                foreach ($this->collDrugCharts as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collDrugComponents) {
                foreach ($this->collDrugComponents as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aEvent instanceof Persistent) {
              $this->aEvent->clearAllReferences($deep);
            }
            if ($this->aCreatePerson instanceof Persistent) {
              $this->aCreatePerson->clearAllReferences($deep);
            }
            if ($this->aModifyPerson instanceof Persistent) {
              $this->aModifyPerson->clearAllReferences($deep);
            }
            if ($this->aSetPerson instanceof Persistent) {
              $this->aSetPerson->clearAllReferences($deep);
            }
            if ($this->aActionType instanceof Persistent) {
              $this->aActionType->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionPropertys instanceof PropelCollection) {
            $this->collActionPropertys->clearIterator();
        }
        $this->collActionPropertys = null;
        if ($this->collDrugCharts instanceof PropelCollection) {
            $this->collDrugCharts->clearIterator();
        }
        $this->collDrugCharts = null;
        if ($this->collDrugComponents instanceof PropelCollection) {
            $this->collDrugComponents->clearIterator();
        }
        $this->collDrugComponents = null;
        $this->aEvent = null;
        $this->aCreatePerson = null;
        $this->aModifyPerson = null;
        $this->aSetPerson = null;
        $this->aActionType = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(ActionPeer::DEFAULT_STRING_FORMAT);
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
     * @return     Action The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = ActionPeer::MODIFYDATETIME;

        return $this;
    }

}
