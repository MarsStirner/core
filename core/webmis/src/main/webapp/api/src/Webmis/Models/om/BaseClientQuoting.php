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
use \PropelDateTime;
use \PropelException;
use \PropelPDO;
use Webmis\Models\ClientQuoting;
use Webmis\Models\ClientQuotingPeer;
use Webmis\Models\ClientQuotingQuery;
use Webmis\Models\QuotaType;
use Webmis\Models\QuotaTypeQuery;
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbPacientModelQuery;
use Webmis\Models\RbTreatment;
use Webmis\Models\RbTreatmentQuery;

/**
 * Base class that represents a row from the 'Client_Quoting' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseClientQuoting extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\ClientQuotingPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        ClientQuotingPeer
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
     * The value for the master_id field.
     * @var        int
     */
    protected $master_id;

    /**
     * The value for the identifier field.
     * @var        string
     */
    protected $identifier;

    /**
     * The value for the quotaticket field.
     * @var        string
     */
    protected $quotaticket;

    /**
     * The value for the quotatype_id field.
     * @var        int
     */
    protected $quotatype_id;

    /**
     * The value for the stage field.
     * @var        int
     */
    protected $stage;

    /**
     * The value for the directiondate field.
     * @var        string
     */
    protected $directiondate;

    /**
     * The value for the freeinput field.
     * @var        string
     */
    protected $freeinput;

    /**
     * The value for the org_id field.
     * @var        int
     */
    protected $org_id;

    /**
     * The value for the amount field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $amount;

    /**
     * The value for the mkb field.
     * @var        string
     */
    protected $mkb;

    /**
     * The value for the status field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $status;

    /**
     * The value for the request field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $request;

    /**
     * The value for the statment field.
     * @var        string
     */
    protected $statment;

    /**
     * The value for the dateregistration field.
     * @var        string
     */
    protected $dateregistration;

    /**
     * The value for the dateend field.
     * @var        string
     */
    protected $dateend;

    /**
     * The value for the orgstructure_id field.
     * @var        int
     */
    protected $orgstructure_id;

    /**
     * The value for the regioncode field.
     * @var        string
     */
    protected $regioncode;

    /**
     * The value for the pacientmodel_id field.
     * @var        int
     */
    protected $pacientmodel_id;

    /**
     * The value for the treatment_id field.
     * @var        int
     */
    protected $treatment_id;

    /**
     * The value for the event_id field.
     * @var        int
     */
    protected $event_id;

    /**
     * The value for the prevtalon_event_id field.
     * @var        int
     */
    protected $prevtalon_event_id;

    /**
     * The value for the version field.
     * @var        int
     */
    protected $version;

    /**
     * @var        RbTreatment
     */
    protected $aRbTreatment;

    /**
     * @var        RbPacientModel
     */
    protected $aRbPacientModel;

    /**
     * @var        QuotaType
     */
    protected $aQuotaType;

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
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->amount = 0;
        $this->status = 0;
        $this->request = 0;
    }

    /**
     * Initializes internal state of BaseClientQuoting object.
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
    public function getId()
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
     * Get the [master_id] column value.
     *
     * @return int
     */
    public function getmasterId()
    {
        return $this->master_id;
    }

    /**
     * Get the [identifier] column value.
     *
     * @return string
     */
    public function getidentifier()
    {
        return $this->identifier;
    }

    /**
     * Get the [quotaticket] column value.
     *
     * @return string
     */
    public function getquotaTicket()
    {
        return $this->quotaticket;
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
     * Get the [stage] column value.
     *
     * @return int
     */
    public function getstage()
    {
        return $this->stage;
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
     * Get the [freeinput] column value.
     *
     * @return string
     */
    public function getfreeInput()
    {
        return $this->freeinput;
    }

    /**
     * Get the [org_id] column value.
     *
     * @return int
     */
    public function getorgId()
    {
        return $this->org_id;
    }

    /**
     * Get the [amount] column value.
     *
     * @return int
     */
    public function getamount()
    {
        return $this->amount;
    }

    /**
     * Get the [mkb] column value.
     *
     * @return string
     */
    public function getmkb()
    {
        return $this->mkb;
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
     * Get the [request] column value.
     *
     * @return int
     */
    public function getrequest()
    {
        return $this->request;
    }

    /**
     * Get the [statment] column value.
     *
     * @return string
     */
    public function getstatment()
    {
        return $this->statment;
    }

    /**
     * Get the [optionally formatted] temporal [dateregistration] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getdateRegistration($format = 'Y-m-d H:i:s')
    {
        if ($this->dateregistration === null) {
            return null;
        }

        if ($this->dateregistration === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->dateregistration);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->dateregistration, true), $x);
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
     * Get the [optionally formatted] temporal [dateend] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getdateEnd($format = 'Y-m-d H:i:s')
    {
        if ($this->dateend === null) {
            return null;
        }

        if ($this->dateend === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->dateend);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->dateend, true), $x);
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
     * Get the [orgstructure_id] column value.
     *
     * @return int
     */
    public function getorgStructureId()
    {
        return $this->orgstructure_id;
    }

    /**
     * Get the [regioncode] column value.
     *
     * @return string
     */
    public function getregionCode()
    {
        return $this->regioncode;
    }

    /**
     * Get the [pacientmodel_id] column value.
     *
     * @return int
     */
    public function getpacientModelId()
    {
        return $this->pacientmodel_id;
    }

    /**
     * Get the [treatment_id] column value.
     *
     * @return int
     */
    public function gettreatmentId()
    {
        return $this->treatment_id;
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
     * Get the [prevtalon_event_id] column value.
     *
     * @return int
     */
    public function getprevTalonEventId()
    {
        return $this->prevtalon_event_id;
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
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::ID;
        }


        return $this;
    } // setId()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = ClientQuotingPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = ClientQuotingPeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::MODIFYPERSON_ID;
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
     * @return ClientQuoting The current object (for fluent API support)
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
            $this->modifiedColumns[] = ClientQuotingPeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [master_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setmasterId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->master_id !== $v) {
            $this->master_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::MASTER_ID;
        }


        return $this;
    } // setmasterId()

    /**
     * Set the value of [identifier] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setidentifier($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->identifier !== $v) {
            $this->identifier = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::IDENTIFIER;
        }


        return $this;
    } // setidentifier()

    /**
     * Set the value of [quotaticket] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setquotaTicket($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->quotaticket !== $v) {
            $this->quotaticket = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::QUOTATICKET;
        }


        return $this;
    } // setquotaTicket()

    /**
     * Set the value of [quotatype_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setquotaTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->quotatype_id !== $v) {
            $this->quotatype_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::QUOTATYPE_ID;
        }

        if ($this->aQuotaType !== null && $this->aQuotaType->getid() !== $v) {
            $this->aQuotaType = null;
        }


        return $this;
    } // setquotaTypeId()

    /**
     * Set the value of [stage] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setstage($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->stage !== $v) {
            $this->stage = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::STAGE;
        }


        return $this;
    } // setstage()

    /**
     * Sets the value of [directiondate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setdirectionDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->directiondate !== null || $dt !== null) {
            $currentDateAsString = ($this->directiondate !== null && $tmpDt = new DateTime($this->directiondate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->directiondate = $newDateAsString;
                $this->modifiedColumns[] = ClientQuotingPeer::DIRECTIONDATE;
            }
        } // if either are not null


        return $this;
    } // setdirectionDate()

    /**
     * Set the value of [freeinput] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setfreeInput($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->freeinput !== $v) {
            $this->freeinput = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::FREEINPUT;
        }


        return $this;
    } // setfreeInput()

    /**
     * Set the value of [org_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setorgId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->org_id !== $v) {
            $this->org_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::ORG_ID;
        }


        return $this;
    } // setorgId()

    /**
     * Set the value of [amount] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setamount($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->amount !== $v) {
            $this->amount = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::AMOUNT;
        }


        return $this;
    } // setamount()

    /**
     * Set the value of [mkb] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setmkb($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->mkb !== $v) {
            $this->mkb = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::MKB;
        }


        return $this;
    } // setmkb()

    /**
     * Set the value of [status] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setstatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->status !== $v) {
            $this->status = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::STATUS;
        }


        return $this;
    } // setstatus()

    /**
     * Set the value of [request] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setrequest($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->request !== $v) {
            $this->request = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::REQUEST;
        }


        return $this;
    } // setrequest()

    /**
     * Set the value of [statment] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setstatment($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->statment !== $v) {
            $this->statment = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::STATMENT;
        }


        return $this;
    } // setstatment()

    /**
     * Sets the value of [dateregistration] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setdateRegistration($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->dateregistration !== null || $dt !== null) {
            $currentDateAsString = ($this->dateregistration !== null && $tmpDt = new DateTime($this->dateregistration)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->dateregistration = $newDateAsString;
                $this->modifiedColumns[] = ClientQuotingPeer::DATEREGISTRATION;
            }
        } // if either are not null


        return $this;
    } // setdateRegistration()

    /**
     * Sets the value of [dateend] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setdateEnd($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->dateend !== null || $dt !== null) {
            $currentDateAsString = ($this->dateend !== null && $tmpDt = new DateTime($this->dateend)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->dateend = $newDateAsString;
                $this->modifiedColumns[] = ClientQuotingPeer::DATEEND;
            }
        } // if either are not null


        return $this;
    } // setdateEnd()

    /**
     * Set the value of [orgstructure_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setorgStructureId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->orgstructure_id !== $v) {
            $this->orgstructure_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::ORGSTRUCTURE_ID;
        }


        return $this;
    } // setorgStructureId()

    /**
     * Set the value of [regioncode] column.
     *
     * @param string $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setregionCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->regioncode !== $v) {
            $this->regioncode = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::REGIONCODE;
        }


        return $this;
    } // setregionCode()

    /**
     * Set the value of [pacientmodel_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setpacientModelId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->pacientmodel_id !== $v) {
            $this->pacientmodel_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::PACIENTMODEL_ID;
        }

        if ($this->aRbPacientModel !== null && $this->aRbPacientModel->getid() !== $v) {
            $this->aRbPacientModel = null;
        }


        return $this;
    } // setpacientModelId()

    /**
     * Set the value of [treatment_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function settreatmentId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->treatment_id !== $v) {
            $this->treatment_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::TREATMENT_ID;
        }

        if ($this->aRbTreatment !== null && $this->aRbTreatment->getid() !== $v) {
            $this->aRbTreatment = null;
        }


        return $this;
    } // settreatmentId()

    /**
     * Set the value of [event_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function seteventId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->event_id !== $v) {
            $this->event_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::EVENT_ID;
        }


        return $this;
    } // seteventId()

    /**
     * Set the value of [prevtalon_event_id] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setprevTalonEventId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->prevtalon_event_id !== $v) {
            $this->prevtalon_event_id = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::PREVTALON_EVENT_ID;
        }


        return $this;
    } // setprevTalonEventId()

    /**
     * Set the value of [version] column.
     *
     * @param int $v new value
     * @return ClientQuoting The current object (for fluent API support)
     */
    public function setversion($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->version !== $v) {
            $this->version = $v;
            $this->modifiedColumns[] = ClientQuotingPeer::VERSION;
        }


        return $this;
    } // setversion()

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

            if ($this->amount !== 0) {
                return false;
            }

            if ($this->status !== 0) {
                return false;
            }

            if ($this->request !== 0) {
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
            $this->master_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->identifier = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->quotaticket = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->quotatype_id = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->stage = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->directiondate = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->freeinput = ($row[$startcol + 12] !== null) ? (string) $row[$startcol + 12] : null;
            $this->org_id = ($row[$startcol + 13] !== null) ? (int) $row[$startcol + 13] : null;
            $this->amount = ($row[$startcol + 14] !== null) ? (int) $row[$startcol + 14] : null;
            $this->mkb = ($row[$startcol + 15] !== null) ? (string) $row[$startcol + 15] : null;
            $this->status = ($row[$startcol + 16] !== null) ? (int) $row[$startcol + 16] : null;
            $this->request = ($row[$startcol + 17] !== null) ? (int) $row[$startcol + 17] : null;
            $this->statment = ($row[$startcol + 18] !== null) ? (string) $row[$startcol + 18] : null;
            $this->dateregistration = ($row[$startcol + 19] !== null) ? (string) $row[$startcol + 19] : null;
            $this->dateend = ($row[$startcol + 20] !== null) ? (string) $row[$startcol + 20] : null;
            $this->orgstructure_id = ($row[$startcol + 21] !== null) ? (int) $row[$startcol + 21] : null;
            $this->regioncode = ($row[$startcol + 22] !== null) ? (string) $row[$startcol + 22] : null;
            $this->pacientmodel_id = ($row[$startcol + 23] !== null) ? (int) $row[$startcol + 23] : null;
            $this->treatment_id = ($row[$startcol + 24] !== null) ? (int) $row[$startcol + 24] : null;
            $this->event_id = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->prevtalon_event_id = ($row[$startcol + 26] !== null) ? (int) $row[$startcol + 26] : null;
            $this->version = ($row[$startcol + 27] !== null) ? (int) $row[$startcol + 27] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 28; // 28 = ClientQuotingPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating ClientQuoting object", $e);
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

        if ($this->aQuotaType !== null && $this->quotatype_id !== $this->aQuotaType->getid()) {
            $this->aQuotaType = null;
        }
        if ($this->aRbPacientModel !== null && $this->pacientmodel_id !== $this->aRbPacientModel->getid()) {
            $this->aRbPacientModel = null;
        }
        if ($this->aRbTreatment !== null && $this->treatment_id !== $this->aRbTreatment->getid()) {
            $this->aRbTreatment = null;
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
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = ClientQuotingPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aRbTreatment = null;
            $this->aRbPacientModel = null;
            $this->aQuotaType = null;
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
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = ClientQuotingQuery::create()
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
            $con = Propel::getConnection(ClientQuotingPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(ClientQuotingPeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(ClientQuotingPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(ClientQuotingPeer::MODIFYDATETIME)) {
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
                ClientQuotingPeer::addInstanceToPool($this);
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

            if ($this->aRbTreatment !== null) {
                if ($this->aRbTreatment->isModified() || $this->aRbTreatment->isNew()) {
                    $affectedRows += $this->aRbTreatment->save($con);
                }
                $this->setRbTreatment($this->aRbTreatment);
            }

            if ($this->aRbPacientModel !== null) {
                if ($this->aRbPacientModel->isModified() || $this->aRbPacientModel->isNew()) {
                    $affectedRows += $this->aRbPacientModel->save($con);
                }
                $this->setRbPacientModel($this->aRbPacientModel);
            }

            if ($this->aQuotaType !== null) {
                if ($this->aQuotaType->isModified() || $this->aQuotaType->isNew()) {
                    $affectedRows += $this->aQuotaType->save($con);
                }
                $this->setQuotaType($this->aQuotaType);
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

        $this->modifiedColumns[] = ClientQuotingPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . ClientQuotingPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(ClientQuotingPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::MASTER_ID)) {
            $modifiedColumns[':p' . $index++]  = '`master_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::IDENTIFIER)) {
            $modifiedColumns[':p' . $index++]  = '`identifier`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::QUOTATICKET)) {
            $modifiedColumns[':p' . $index++]  = '`quotaTicket`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::QUOTATYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`quotaType_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::STAGE)) {
            $modifiedColumns[':p' . $index++]  = '`stage`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::DIRECTIONDATE)) {
            $modifiedColumns[':p' . $index++]  = '`directionDate`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::FREEINPUT)) {
            $modifiedColumns[':p' . $index++]  = '`freeInput`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::ORG_ID)) {
            $modifiedColumns[':p' . $index++]  = '`org_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::AMOUNT)) {
            $modifiedColumns[':p' . $index++]  = '`amount`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::MKB)) {
            $modifiedColumns[':p' . $index++]  = '`MKB`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::STATUS)) {
            $modifiedColumns[':p' . $index++]  = '`status`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::REQUEST)) {
            $modifiedColumns[':p' . $index++]  = '`request`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::STATMENT)) {
            $modifiedColumns[':p' . $index++]  = '`statment`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::DATEREGISTRATION)) {
            $modifiedColumns[':p' . $index++]  = '`dateRegistration`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::DATEEND)) {
            $modifiedColumns[':p' . $index++]  = '`dateEnd`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::ORGSTRUCTURE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`orgStructure_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::REGIONCODE)) {
            $modifiedColumns[':p' . $index++]  = '`regionCode`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::PACIENTMODEL_ID)) {
            $modifiedColumns[':p' . $index++]  = '`pacientModel_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::TREATMENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`treatment_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::EVENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`event_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::PREVTALON_EVENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`prevTalon_event_id`';
        }
        if ($this->isColumnModified(ClientQuotingPeer::VERSION)) {
            $modifiedColumns[':p' . $index++]  = '`version`';
        }

        $sql = sprintf(
            'INSERT INTO `Client_Quoting` (%s) VALUES (%s)',
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
                    case '`master_id`':
                        $stmt->bindValue($identifier, $this->master_id, PDO::PARAM_INT);
                        break;
                    case '`identifier`':
                        $stmt->bindValue($identifier, $this->identifier, PDO::PARAM_STR);
                        break;
                    case '`quotaTicket`':
                        $stmt->bindValue($identifier, $this->quotaticket, PDO::PARAM_STR);
                        break;
                    case '`quotaType_id`':
                        $stmt->bindValue($identifier, $this->quotatype_id, PDO::PARAM_INT);
                        break;
                    case '`stage`':
                        $stmt->bindValue($identifier, $this->stage, PDO::PARAM_INT);
                        break;
                    case '`directionDate`':
                        $stmt->bindValue($identifier, $this->directiondate, PDO::PARAM_STR);
                        break;
                    case '`freeInput`':
                        $stmt->bindValue($identifier, $this->freeinput, PDO::PARAM_STR);
                        break;
                    case '`org_id`':
                        $stmt->bindValue($identifier, $this->org_id, PDO::PARAM_INT);
                        break;
                    case '`amount`':
                        $stmt->bindValue($identifier, $this->amount, PDO::PARAM_INT);
                        break;
                    case '`MKB`':
                        $stmt->bindValue($identifier, $this->mkb, PDO::PARAM_STR);
                        break;
                    case '`status`':
                        $stmt->bindValue($identifier, $this->status, PDO::PARAM_INT);
                        break;
                    case '`request`':
                        $stmt->bindValue($identifier, $this->request, PDO::PARAM_INT);
                        break;
                    case '`statment`':
                        $stmt->bindValue($identifier, $this->statment, PDO::PARAM_STR);
                        break;
                    case '`dateRegistration`':
                        $stmt->bindValue($identifier, $this->dateregistration, PDO::PARAM_STR);
                        break;
                    case '`dateEnd`':
                        $stmt->bindValue($identifier, $this->dateend, PDO::PARAM_STR);
                        break;
                    case '`orgStructure_id`':
                        $stmt->bindValue($identifier, $this->orgstructure_id, PDO::PARAM_INT);
                        break;
                    case '`regionCode`':
                        $stmt->bindValue($identifier, $this->regioncode, PDO::PARAM_STR);
                        break;
                    case '`pacientModel_id`':
                        $stmt->bindValue($identifier, $this->pacientmodel_id, PDO::PARAM_INT);
                        break;
                    case '`treatment_id`':
                        $stmt->bindValue($identifier, $this->treatment_id, PDO::PARAM_INT);
                        break;
                    case '`event_id`':
                        $stmt->bindValue($identifier, $this->event_id, PDO::PARAM_INT);
                        break;
                    case '`prevTalon_event_id`':
                        $stmt->bindValue($identifier, $this->prevtalon_event_id, PDO::PARAM_INT);
                        break;
                    case '`version`':
                        $stmt->bindValue($identifier, $this->version, PDO::PARAM_INT);
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
        $this->setId($pk);

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

            if ($this->aRbTreatment !== null) {
                if (!$this->aRbTreatment->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aRbTreatment->getValidationFailures());
                }
            }

            if ($this->aRbPacientModel !== null) {
                if (!$this->aRbPacientModel->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aRbPacientModel->getValidationFailures());
                }
            }

            if ($this->aQuotaType !== null) {
                if (!$this->aQuotaType->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aQuotaType->getValidationFailures());
                }
            }


            if (($retval = ClientQuotingPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
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
        $pos = ClientQuotingPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getId();
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
                return $this->getmasterId();
                break;
            case 7:
                return $this->getidentifier();
                break;
            case 8:
                return $this->getquotaTicket();
                break;
            case 9:
                return $this->getquotaTypeId();
                break;
            case 10:
                return $this->getstage();
                break;
            case 11:
                return $this->getdirectionDate();
                break;
            case 12:
                return $this->getfreeInput();
                break;
            case 13:
                return $this->getorgId();
                break;
            case 14:
                return $this->getamount();
                break;
            case 15:
                return $this->getmkb();
                break;
            case 16:
                return $this->getstatus();
                break;
            case 17:
                return $this->getrequest();
                break;
            case 18:
                return $this->getstatment();
                break;
            case 19:
                return $this->getdateRegistration();
                break;
            case 20:
                return $this->getdateEnd();
                break;
            case 21:
                return $this->getorgStructureId();
                break;
            case 22:
                return $this->getregionCode();
                break;
            case 23:
                return $this->getpacientModelId();
                break;
            case 24:
                return $this->gettreatmentId();
                break;
            case 25:
                return $this->geteventId();
                break;
            case 26:
                return $this->getprevTalonEventId();
                break;
            case 27:
                return $this->getversion();
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
        if (isset($alreadyDumpedObjects['ClientQuoting'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['ClientQuoting'][$this->getPrimaryKey()] = true;
        $keys = ClientQuotingPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getId(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getmasterId(),
            $keys[7] => $this->getidentifier(),
            $keys[8] => $this->getquotaTicket(),
            $keys[9] => $this->getquotaTypeId(),
            $keys[10] => $this->getstage(),
            $keys[11] => $this->getdirectionDate(),
            $keys[12] => $this->getfreeInput(),
            $keys[13] => $this->getorgId(),
            $keys[14] => $this->getamount(),
            $keys[15] => $this->getmkb(),
            $keys[16] => $this->getstatus(),
            $keys[17] => $this->getrequest(),
            $keys[18] => $this->getstatment(),
            $keys[19] => $this->getdateRegistration(),
            $keys[20] => $this->getdateEnd(),
            $keys[21] => $this->getorgStructureId(),
            $keys[22] => $this->getregionCode(),
            $keys[23] => $this->getpacientModelId(),
            $keys[24] => $this->gettreatmentId(),
            $keys[25] => $this->geteventId(),
            $keys[26] => $this->getprevTalonEventId(),
            $keys[27] => $this->getversion(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aRbTreatment) {
                $result['RbTreatment'] = $this->aRbTreatment->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aRbPacientModel) {
                $result['RbPacientModel'] = $this->aRbPacientModel->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aQuotaType) {
                $result['QuotaType'] = $this->aQuotaType->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
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
        $pos = ClientQuotingPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setId($value);
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
                $this->setmasterId($value);
                break;
            case 7:
                $this->setidentifier($value);
                break;
            case 8:
                $this->setquotaTicket($value);
                break;
            case 9:
                $this->setquotaTypeId($value);
                break;
            case 10:
                $this->setstage($value);
                break;
            case 11:
                $this->setdirectionDate($value);
                break;
            case 12:
                $this->setfreeInput($value);
                break;
            case 13:
                $this->setorgId($value);
                break;
            case 14:
                $this->setamount($value);
                break;
            case 15:
                $this->setmkb($value);
                break;
            case 16:
                $this->setstatus($value);
                break;
            case 17:
                $this->setrequest($value);
                break;
            case 18:
                $this->setstatment($value);
                break;
            case 19:
                $this->setdateRegistration($value);
                break;
            case 20:
                $this->setdateEnd($value);
                break;
            case 21:
                $this->setorgStructureId($value);
                break;
            case 22:
                $this->setregionCode($value);
                break;
            case 23:
                $this->setpacientModelId($value);
                break;
            case 24:
                $this->settreatmentId($value);
                break;
            case 25:
                $this->seteventId($value);
                break;
            case 26:
                $this->setprevTalonEventId($value);
                break;
            case 27:
                $this->setversion($value);
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
        $keys = ClientQuotingPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setId($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setmasterId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setidentifier($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setquotaTicket($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setquotaTypeId($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setstage($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setdirectionDate($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setfreeInput($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setorgId($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setamount($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setmkb($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setstatus($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setrequest($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setstatment($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setdateRegistration($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setdateEnd($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setorgStructureId($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setregionCode($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setpacientModelId($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->settreatmentId($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->seteventId($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->setprevTalonEventId($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setversion($arr[$keys[27]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);

        if ($this->isColumnModified(ClientQuotingPeer::ID)) $criteria->add(ClientQuotingPeer::ID, $this->id);
        if ($this->isColumnModified(ClientQuotingPeer::CREATEDATETIME)) $criteria->add(ClientQuotingPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(ClientQuotingPeer::CREATEPERSON_ID)) $criteria->add(ClientQuotingPeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(ClientQuotingPeer::MODIFYDATETIME)) $criteria->add(ClientQuotingPeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(ClientQuotingPeer::MODIFYPERSON_ID)) $criteria->add(ClientQuotingPeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(ClientQuotingPeer::DELETED)) $criteria->add(ClientQuotingPeer::DELETED, $this->deleted);
        if ($this->isColumnModified(ClientQuotingPeer::MASTER_ID)) $criteria->add(ClientQuotingPeer::MASTER_ID, $this->master_id);
        if ($this->isColumnModified(ClientQuotingPeer::IDENTIFIER)) $criteria->add(ClientQuotingPeer::IDENTIFIER, $this->identifier);
        if ($this->isColumnModified(ClientQuotingPeer::QUOTATICKET)) $criteria->add(ClientQuotingPeer::QUOTATICKET, $this->quotaticket);
        if ($this->isColumnModified(ClientQuotingPeer::QUOTATYPE_ID)) $criteria->add(ClientQuotingPeer::QUOTATYPE_ID, $this->quotatype_id);
        if ($this->isColumnModified(ClientQuotingPeer::STAGE)) $criteria->add(ClientQuotingPeer::STAGE, $this->stage);
        if ($this->isColumnModified(ClientQuotingPeer::DIRECTIONDATE)) $criteria->add(ClientQuotingPeer::DIRECTIONDATE, $this->directiondate);
        if ($this->isColumnModified(ClientQuotingPeer::FREEINPUT)) $criteria->add(ClientQuotingPeer::FREEINPUT, $this->freeinput);
        if ($this->isColumnModified(ClientQuotingPeer::ORG_ID)) $criteria->add(ClientQuotingPeer::ORG_ID, $this->org_id);
        if ($this->isColumnModified(ClientQuotingPeer::AMOUNT)) $criteria->add(ClientQuotingPeer::AMOUNT, $this->amount);
        if ($this->isColumnModified(ClientQuotingPeer::MKB)) $criteria->add(ClientQuotingPeer::MKB, $this->mkb);
        if ($this->isColumnModified(ClientQuotingPeer::STATUS)) $criteria->add(ClientQuotingPeer::STATUS, $this->status);
        if ($this->isColumnModified(ClientQuotingPeer::REQUEST)) $criteria->add(ClientQuotingPeer::REQUEST, $this->request);
        if ($this->isColumnModified(ClientQuotingPeer::STATMENT)) $criteria->add(ClientQuotingPeer::STATMENT, $this->statment);
        if ($this->isColumnModified(ClientQuotingPeer::DATEREGISTRATION)) $criteria->add(ClientQuotingPeer::DATEREGISTRATION, $this->dateregistration);
        if ($this->isColumnModified(ClientQuotingPeer::DATEEND)) $criteria->add(ClientQuotingPeer::DATEEND, $this->dateend);
        if ($this->isColumnModified(ClientQuotingPeer::ORGSTRUCTURE_ID)) $criteria->add(ClientQuotingPeer::ORGSTRUCTURE_ID, $this->orgstructure_id);
        if ($this->isColumnModified(ClientQuotingPeer::REGIONCODE)) $criteria->add(ClientQuotingPeer::REGIONCODE, $this->regioncode);
        if ($this->isColumnModified(ClientQuotingPeer::PACIENTMODEL_ID)) $criteria->add(ClientQuotingPeer::PACIENTMODEL_ID, $this->pacientmodel_id);
        if ($this->isColumnModified(ClientQuotingPeer::TREATMENT_ID)) $criteria->add(ClientQuotingPeer::TREATMENT_ID, $this->treatment_id);
        if ($this->isColumnModified(ClientQuotingPeer::EVENT_ID)) $criteria->add(ClientQuotingPeer::EVENT_ID, $this->event_id);
        if ($this->isColumnModified(ClientQuotingPeer::PREVTALON_EVENT_ID)) $criteria->add(ClientQuotingPeer::PREVTALON_EVENT_ID, $this->prevtalon_event_id);
        if ($this->isColumnModified(ClientQuotingPeer::VERSION)) $criteria->add(ClientQuotingPeer::VERSION, $this->version);

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
        $criteria = new Criteria(ClientQuotingPeer::DATABASE_NAME);
        $criteria->add(ClientQuotingPeer::ID, $this->id);

        return $criteria;
    }

    /**
     * Returns the primary key for this object (row).
     * @return int
     */
    public function getPrimaryKey()
    {
        return $this->getId();
    }

    /**
     * Generic method to set the primary key (id column).
     *
     * @param  int $key Primary key.
     * @return void
     */
    public function setPrimaryKey($key)
    {
        $this->setId($key);
    }

    /**
     * Returns true if the primary key for this object is null.
     * @return boolean
     */
    public function isPrimaryKeyNull()
    {

        return null === $this->getId();
    }

    /**
     * Sets contents of passed object to values from current object.
     *
     * If desired, this method can also make copies of all associated (fkey referrers)
     * objects.
     *
     * @param object $copyObj An object of ClientQuoting (or compatible) type.
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
        $copyObj->setmasterId($this->getmasterId());
        $copyObj->setidentifier($this->getidentifier());
        $copyObj->setquotaTicket($this->getquotaTicket());
        $copyObj->setquotaTypeId($this->getquotaTypeId());
        $copyObj->setstage($this->getstage());
        $copyObj->setdirectionDate($this->getdirectionDate());
        $copyObj->setfreeInput($this->getfreeInput());
        $copyObj->setorgId($this->getorgId());
        $copyObj->setamount($this->getamount());
        $copyObj->setmkb($this->getmkb());
        $copyObj->setstatus($this->getstatus());
        $copyObj->setrequest($this->getrequest());
        $copyObj->setstatment($this->getstatment());
        $copyObj->setdateRegistration($this->getdateRegistration());
        $copyObj->setdateEnd($this->getdateEnd());
        $copyObj->setorgStructureId($this->getorgStructureId());
        $copyObj->setregionCode($this->getregionCode());
        $copyObj->setpacientModelId($this->getpacientModelId());
        $copyObj->settreatmentId($this->gettreatmentId());
        $copyObj->seteventId($this->geteventId());
        $copyObj->setprevTalonEventId($this->getprevTalonEventId());
        $copyObj->setversion($this->getversion());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            //unflag object copy
            $this->startCopy = false;
        } // if ($deepCopy)

        if ($makeNew) {
            $copyObj->setNew(true);
            $copyObj->setId(NULL); // this is a auto-increment column, so set to default value
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
     * @return ClientQuoting Clone of current object.
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
     * @return ClientQuotingPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new ClientQuotingPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a RbTreatment object.
     *
     * @param             RbTreatment $v
     * @return ClientQuoting The current object (for fluent API support)
     * @throws PropelException
     */
    public function setRbTreatment(RbTreatment $v = null)
    {
        if ($v === null) {
            $this->settreatmentId(NULL);
        } else {
            $this->settreatmentId($v->getid());
        }

        $this->aRbTreatment = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the RbTreatment object, it will not be re-added.
        if ($v !== null) {
            $v->addClientQuoting($this);
        }


        return $this;
    }


    /**
     * Get the associated RbTreatment object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return RbTreatment The associated RbTreatment object.
     * @throws PropelException
     */
    public function getRbTreatment(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aRbTreatment === null && ($this->treatment_id !== null) && $doQuery) {
            $this->aRbTreatment = RbTreatmentQuery::create()->findPk($this->treatment_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aRbTreatment->addClientQuotings($this);
             */
        }

        return $this->aRbTreatment;
    }

    /**
     * Declares an association between this object and a RbPacientModel object.
     *
     * @param             RbPacientModel $v
     * @return ClientQuoting The current object (for fluent API support)
     * @throws PropelException
     */
    public function setRbPacientModel(RbPacientModel $v = null)
    {
        if ($v === null) {
            $this->setpacientModelId(NULL);
        } else {
            $this->setpacientModelId($v->getid());
        }

        $this->aRbPacientModel = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the RbPacientModel object, it will not be re-added.
        if ($v !== null) {
            $v->addClientQuoting($this);
        }


        return $this;
    }


    /**
     * Get the associated RbPacientModel object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return RbPacientModel The associated RbPacientModel object.
     * @throws PropelException
     */
    public function getRbPacientModel(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aRbPacientModel === null && ($this->pacientmodel_id !== null) && $doQuery) {
            $this->aRbPacientModel = RbPacientModelQuery::create()->findPk($this->pacientmodel_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aRbPacientModel->addClientQuotings($this);
             */
        }

        return $this->aRbPacientModel;
    }

    /**
     * Declares an association between this object and a QuotaType object.
     *
     * @param             QuotaType $v
     * @return ClientQuoting The current object (for fluent API support)
     * @throws PropelException
     */
    public function setQuotaType(QuotaType $v = null)
    {
        if ($v === null) {
            $this->setquotaTypeId(NULL);
        } else {
            $this->setquotaTypeId($v->getid());
        }

        $this->aQuotaType = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the QuotaType object, it will not be re-added.
        if ($v !== null) {
            $v->addClientQuoting($this);
        }


        return $this;
    }


    /**
     * Get the associated QuotaType object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return QuotaType The associated QuotaType object.
     * @throws PropelException
     */
    public function getQuotaType(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aQuotaType === null && ($this->quotatype_id !== null) && $doQuery) {
            $this->aQuotaType = QuotaTypeQuery::create()->findPk($this->quotatype_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aQuotaType->addClientQuotings($this);
             */
        }

        return $this->aQuotaType;
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
        $this->master_id = null;
        $this->identifier = null;
        $this->quotaticket = null;
        $this->quotatype_id = null;
        $this->stage = null;
        $this->directiondate = null;
        $this->freeinput = null;
        $this->org_id = null;
        $this->amount = null;
        $this->mkb = null;
        $this->status = null;
        $this->request = null;
        $this->statment = null;
        $this->dateregistration = null;
        $this->dateend = null;
        $this->orgstructure_id = null;
        $this->regioncode = null;
        $this->pacientmodel_id = null;
        $this->treatment_id = null;
        $this->event_id = null;
        $this->prevtalon_event_id = null;
        $this->version = null;
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
            if ($this->aRbTreatment instanceof Persistent) {
              $this->aRbTreatment->clearAllReferences($deep);
            }
            if ($this->aRbPacientModel instanceof Persistent) {
              $this->aRbPacientModel->clearAllReferences($deep);
            }
            if ($this->aQuotaType instanceof Persistent) {
              $this->aQuotaType->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        $this->aRbTreatment = null;
        $this->aRbPacientModel = null;
        $this->aQuotaType = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(ClientQuotingPeer::DEFAULT_STRING_FORMAT);
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
     * @return     ClientQuoting The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = ClientQuotingPeer::MODIFYDATETIME;

        return $this;
    }

}
