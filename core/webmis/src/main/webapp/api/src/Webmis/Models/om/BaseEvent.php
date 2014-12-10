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
use Webmis\Models\ActionQuery;
use Webmis\Models\Client;
use Webmis\Models\ClientQuery;
use Webmis\Models\Event;
use Webmis\Models\EventPeer;
use Webmis\Models\EventQuery;
use Webmis\Models\EventType;
use Webmis\Models\EventTypeQuery;
use Webmis\Models\OrgStructure;
use Webmis\Models\OrgStructureQuery;
use Webmis\Models\Person;
use Webmis\Models\PersonQuery;

/**
 * Base class that represents a row from the 'Event' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseEvent extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\EventPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        EventPeer
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
     * The value for the externalid field.
     * @var        string
     */
    protected $externalid;

    /**
     * The value for the eventtype_id field.
     * @var        int
     */
    protected $eventtype_id;

    /**
     * The value for the org_id field.
     * @var        int
     */
    protected $org_id;

    /**
     * The value for the client_id field.
     * @var        int
     */
    protected $client_id;

    /**
     * The value for the contract_id field.
     * @var        int
     */
    protected $contract_id;

    /**
     * The value for the preveventdate field.
     * @var        string
     */
    protected $preveventdate;

    /**
     * The value for the setdate field.
     * @var        string
     */
    protected $setdate;

    /**
     * The value for the setperson_id field.
     * @var        int
     */
    protected $setperson_id;

    /**
     * The value for the execdate field.
     * @var        string
     */
    protected $execdate;

    /**
     * The value for the execperson_id field.
     * @var        int
     */
    protected $execperson_id;

    /**
     * The value for the isprimary field.
     * @var        boolean
     */
    protected $isprimary;

    /**
     * The value for the order field.
     * @var        boolean
     */
    protected $order;

    /**
     * The value for the result_id field.
     * @var        int
     */
    protected $result_id;

    /**
     * The value for the nexteventdate field.
     * @var        string
     */
    protected $nexteventdate;

    /**
     * The value for the paystatus field.
     * @var        int
     */
    protected $paystatus;

    /**
     * The value for the typeasset_id field.
     * @var        int
     */
    protected $typeasset_id;

    /**
     * The value for the note field.
     * @var        string
     */
    protected $note;

    /**
     * The value for the curator_id field.
     * @var        int
     */
    protected $curator_id;

    /**
     * The value for the assistant_id field.
     * @var        int
     */
    protected $assistant_id;

    /**
     * The value for the pregnancyweek field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $pregnancyweek;

    /**
     * The value for the mes_id field.
     * @var        int
     */
    protected $mes_id;

    /**
     * The value for the messpecification_id field.
     * @var        int
     */
    protected $messpecification_id;

    /**
     * The value for the rbacheresult_id field.
     * @var        int
     */
    protected $rbacheresult_id;

    /**
     * The value for the version field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $version;

    /**
     * The value for the privilege field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $privilege;

    /**
     * The value for the urgent field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $urgent;

    /**
     * The value for the orgstructure_id field.
     * @var        int
     */
    protected $orgstructure_id;

    /**
     * The value for the uuid_id field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $uuid_id;

    /**
     * The value for the lpu_transfer field.
     * @var        string
     */
    protected $lpu_transfer;

    /**
     * @var        EventType
     */
    protected $aEventType;

    /**
     * @var        Client
     */
    protected $aClient;

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
     * @var        Person
     */
    protected $aDoctor;

    /**
     * @var        OrgStructure
     */
    protected $aOrgStructure;

    /**
     * @var        PropelObjectCollection|Action[] Collection to store aggregation of Action objects.
     */
    protected $collActions;
    protected $collActionsPartial;

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
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->pregnancyweek = 0;
        $this->version = 0;
        $this->privilege = false;
        $this->urgent = false;
        $this->uuid_id = 0;
    }

    /**
     * Initializes internal state of BaseEvent object.
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
     * Get the [externalid] column value.
     *
     * @return string
     */
    public function getexternalId()
    {
        return $this->externalid;
    }

    /**
     * Get the [eventtype_id] column value.
     *
     * @return int
     */
    public function geteventTypeId()
    {
        return $this->eventtype_id;
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
     * Get the [client_id] column value.
     *
     * @return int
     */
    public function getclientId()
    {
        return $this->client_id;
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
     * Get the [optionally formatted] temporal [preveventdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getprevEventDate($format = 'Y-m-d H:i:s')
    {
        if ($this->preveventdate === null) {
            return null;
        }

        if ($this->preveventdate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->preveventdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->preveventdate, true), $x);
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
     * Get the [optionally formatted] temporal [setdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getsetDate($format = 'Y-m-d H:i:s')
    {
        if ($this->setdate === null) {
            return null;
        }

        if ($this->setdate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->setdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->setdate, true), $x);
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
     * Get the [setperson_id] column value.
     *
     * @return int
     */
    public function getsetPersonId()
    {
        return $this->setperson_id;
    }

    /**
     * Get the [optionally formatted] temporal [execdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getexecDate($format = 'Y-m-d H:i:s')
    {
        if ($this->execdate === null) {
            return null;
        }

        if ($this->execdate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->execdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->execdate, true), $x);
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
     * Get the [execperson_id] column value.
     *
     * @return int
     */
    public function getexecPersonId()
    {
        return $this->execperson_id;
    }

    /**
     * Get the [isprimary] column value.
     *
     * @return boolean
     */
    public function getisPrimary()
    {
        return $this->isprimary;
    }

    /**
     * Get the [order] column value.
     *
     * @return boolean
     */
    public function getorder()
    {
        return $this->order;
    }

    /**
     * Get the [result_id] column value.
     *
     * @return int
     */
    public function getresultId()
    {
        return $this->result_id;
    }

    /**
     * Get the [optionally formatted] temporal [nexteventdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getnextEventDate($format = 'Y-m-d H:i:s')
    {
        if ($this->nexteventdate === null) {
            return null;
        }

        if ($this->nexteventdate === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->nexteventdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->nexteventdate, true), $x);
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
     * Get the [paystatus] column value.
     *
     * @return int
     */
    public function getpayStatus()
    {
        return $this->paystatus;
    }

    /**
     * Get the [typeasset_id] column value.
     *
     * @return int
     */
    public function gettypeAssetId()
    {
        return $this->typeasset_id;
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
     * Get the [curator_id] column value.
     *
     * @return int
     */
    public function getcuratorId()
    {
        return $this->curator_id;
    }

    /**
     * Get the [assistant_id] column value.
     *
     * @return int
     */
    public function getassistantId()
    {
        return $this->assistant_id;
    }

    /**
     * Get the [pregnancyweek] column value.
     *
     * @return int
     */
    public function getpregnancyWeek()
    {
        return $this->pregnancyweek;
    }

    /**
     * Get the [mes_id] column value.
     *
     * @return int
     */
    public function getmesId()
    {
        return $this->mes_id;
    }

    /**
     * Get the [messpecification_id] column value.
     *
     * @return int
     */
    public function getmesSpecificationId()
    {
        return $this->messpecification_id;
    }

    /**
     * Get the [rbacheresult_id] column value.
     *
     * @return int
     */
    public function getrbAcheResultId()
    {
        return $this->rbacheresult_id;
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
     * Get the [privilege] column value.
     *
     * @return boolean
     */
    public function getprivilege()
    {
        return $this->privilege;
    }

    /**
     * Get the [urgent] column value.
     *
     * @return boolean
     */
    public function geturgent()
    {
        return $this->urgent;
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
     * Get the [uuid_id] column value.
     *
     * @return int
     */
    public function getuuidId()
    {
        return $this->uuid_id;
    }

    /**
     * Get the [lpu_transfer] column value.
     *
     * @return string
     */
    public function getlpuTransfer()
    {
        return $this->lpu_transfer;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = EventPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Event The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = EventPeer::CREATEPERSON_ID;
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
     * @return Event The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = EventPeer::MODIFYPERSON_ID;
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
     * @return Event The current object (for fluent API support)
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
            $this->modifiedColumns[] = EventPeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [externalid] column.
     *
     * @param string $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setexternalId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->externalid !== $v) {
            $this->externalid = $v;
            $this->modifiedColumns[] = EventPeer::EXTERNALID;
        }


        return $this;
    } // setexternalId()

    /**
     * Set the value of [eventtype_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function seteventTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->eventtype_id !== $v) {
            $this->eventtype_id = $v;
            $this->modifiedColumns[] = EventPeer::EVENTTYPE_ID;
        }

        if ($this->aEventType !== null && $this->aEventType->getid() !== $v) {
            $this->aEventType = null;
        }


        return $this;
    } // seteventTypeId()

    /**
     * Set the value of [org_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setorgId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->org_id !== $v) {
            $this->org_id = $v;
            $this->modifiedColumns[] = EventPeer::ORG_ID;
        }


        return $this;
    } // setorgId()

    /**
     * Set the value of [client_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setclientId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->client_id !== $v) {
            $this->client_id = $v;
            $this->modifiedColumns[] = EventPeer::CLIENT_ID;
        }

        if ($this->aClient !== null && $this->aClient->getid() !== $v) {
            $this->aClient = null;
        }


        return $this;
    } // setclientId()

    /**
     * Set the value of [contract_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setcontractId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->contract_id !== $v) {
            $this->contract_id = $v;
            $this->modifiedColumns[] = EventPeer::CONTRACT_ID;
        }


        return $this;
    } // setcontractId()

    /**
     * Sets the value of [preveventdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Event The current object (for fluent API support)
     */
    public function setprevEventDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->preveventdate !== null || $dt !== null) {
            $currentDateAsString = ($this->preveventdate !== null && $tmpDt = new DateTime($this->preveventdate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->preveventdate = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::PREVEVENTDATE;
            }
        } // if either are not null


        return $this;
    } // setprevEventDate()

    /**
     * Sets the value of [setdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Event The current object (for fluent API support)
     */
    public function setsetDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->setdate !== null || $dt !== null) {
            $currentDateAsString = ($this->setdate !== null && $tmpDt = new DateTime($this->setdate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->setdate = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::SETDATE;
            }
        } // if either are not null


        return $this;
    } // setsetDate()

    /**
     * Set the value of [setperson_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setsetPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->setperson_id !== $v) {
            $this->setperson_id = $v;
            $this->modifiedColumns[] = EventPeer::SETPERSON_ID;
        }

        if ($this->aSetPerson !== null && $this->aSetPerson->getid() !== $v) {
            $this->aSetPerson = null;
        }


        return $this;
    } // setsetPersonId()

    /**
     * Sets the value of [execdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Event The current object (for fluent API support)
     */
    public function setexecDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->execdate !== null || $dt !== null) {
            $currentDateAsString = ($this->execdate !== null && $tmpDt = new DateTime($this->execdate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->execdate = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::EXECDATE;
            }
        } // if either are not null


        return $this;
    } // setexecDate()

    /**
     * Set the value of [execperson_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setexecPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->execperson_id !== $v) {
            $this->execperson_id = $v;
            $this->modifiedColumns[] = EventPeer::EXECPERSON_ID;
        }

        if ($this->aDoctor !== null && $this->aDoctor->getid() !== $v) {
            $this->aDoctor = null;
        }


        return $this;
    } // setexecPersonId()

    /**
     * Sets the value of the [isprimary] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Event The current object (for fluent API support)
     */
    public function setisPrimary($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isprimary !== $v) {
            $this->isprimary = $v;
            $this->modifiedColumns[] = EventPeer::ISPRIMARY;
        }


        return $this;
    } // setisPrimary()

    /**
     * Sets the value of the [order] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Event The current object (for fluent API support)
     */
    public function setorder($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->order !== $v) {
            $this->order = $v;
            $this->modifiedColumns[] = EventPeer::ORDER;
        }


        return $this;
    } // setorder()

    /**
     * Set the value of [result_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setresultId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->result_id !== $v) {
            $this->result_id = $v;
            $this->modifiedColumns[] = EventPeer::RESULT_ID;
        }


        return $this;
    } // setresultId()

    /**
     * Sets the value of [nexteventdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Event The current object (for fluent API support)
     */
    public function setnextEventDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->nexteventdate !== null || $dt !== null) {
            $currentDateAsString = ($this->nexteventdate !== null && $tmpDt = new DateTime($this->nexteventdate)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->nexteventdate = $newDateAsString;
                $this->modifiedColumns[] = EventPeer::NEXTEVENTDATE;
            }
        } // if either are not null


        return $this;
    } // setnextEventDate()

    /**
     * Set the value of [paystatus] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setpayStatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->paystatus !== $v) {
            $this->paystatus = $v;
            $this->modifiedColumns[] = EventPeer::PAYSTATUS;
        }


        return $this;
    } // setpayStatus()

    /**
     * Set the value of [typeasset_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function settypeAssetId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->typeasset_id !== $v) {
            $this->typeasset_id = $v;
            $this->modifiedColumns[] = EventPeer::TYPEASSET_ID;
        }


        return $this;
    } // settypeAssetId()

    /**
     * Set the value of [note] column.
     *
     * @param string $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setnote($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->note !== $v) {
            $this->note = $v;
            $this->modifiedColumns[] = EventPeer::NOTE;
        }


        return $this;
    } // setnote()

    /**
     * Set the value of [curator_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setcuratorId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->curator_id !== $v) {
            $this->curator_id = $v;
            $this->modifiedColumns[] = EventPeer::CURATOR_ID;
        }


        return $this;
    } // setcuratorId()

    /**
     * Set the value of [assistant_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setassistantId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->assistant_id !== $v) {
            $this->assistant_id = $v;
            $this->modifiedColumns[] = EventPeer::ASSISTANT_ID;
        }


        return $this;
    } // setassistantId()

    /**
     * Set the value of [pregnancyweek] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setpregnancyWeek($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->pregnancyweek !== $v) {
            $this->pregnancyweek = $v;
            $this->modifiedColumns[] = EventPeer::PREGNANCYWEEK;
        }


        return $this;
    } // setpregnancyWeek()

    /**
     * Set the value of [mes_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setmesId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->mes_id !== $v) {
            $this->mes_id = $v;
            $this->modifiedColumns[] = EventPeer::MES_ID;
        }


        return $this;
    } // setmesId()

    /**
     * Set the value of [messpecification_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setmesSpecificationId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->messpecification_id !== $v) {
            $this->messpecification_id = $v;
            $this->modifiedColumns[] = EventPeer::MESSPECIFICATION_ID;
        }


        return $this;
    } // setmesSpecificationId()

    /**
     * Set the value of [rbacheresult_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setrbAcheResultId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->rbacheresult_id !== $v) {
            $this->rbacheresult_id = $v;
            $this->modifiedColumns[] = EventPeer::RBACHERESULT_ID;
        }


        return $this;
    } // setrbAcheResultId()

    /**
     * Set the value of [version] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setversion($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->version !== $v) {
            $this->version = $v;
            $this->modifiedColumns[] = EventPeer::VERSION;
        }


        return $this;
    } // setversion()

    /**
     * Sets the value of the [privilege] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Event The current object (for fluent API support)
     */
    public function setprivilege($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->privilege !== $v) {
            $this->privilege = $v;
            $this->modifiedColumns[] = EventPeer::PRIVILEGE;
        }


        return $this;
    } // setprivilege()

    /**
     * Sets the value of the [urgent] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Event The current object (for fluent API support)
     */
    public function seturgent($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->urgent !== $v) {
            $this->urgent = $v;
            $this->modifiedColumns[] = EventPeer::URGENT;
        }


        return $this;
    } // seturgent()

    /**
     * Set the value of [orgstructure_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setorgStructureId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->orgstructure_id !== $v) {
            $this->orgstructure_id = $v;
            $this->modifiedColumns[] = EventPeer::ORGSTRUCTURE_ID;
        }

        if ($this->aOrgStructure !== null && $this->aOrgStructure->getId() !== $v) {
            $this->aOrgStructure = null;
        }


        return $this;
    } // setorgStructureId()

    /**
     * Set the value of [uuid_id] column.
     *
     * @param int $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setuuidId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->uuid_id !== $v) {
            $this->uuid_id = $v;
            $this->modifiedColumns[] = EventPeer::UUID_ID;
        }


        return $this;
    } // setuuidId()

    /**
     * Set the value of [lpu_transfer] column.
     *
     * @param string $v new value
     * @return Event The current object (for fluent API support)
     */
    public function setlpuTransfer($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->lpu_transfer !== $v) {
            $this->lpu_transfer = $v;
            $this->modifiedColumns[] = EventPeer::LPU_TRANSFER;
        }


        return $this;
    } // setlpuTransfer()

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

            if ($this->pregnancyweek !== 0) {
                return false;
            }

            if ($this->version !== 0) {
                return false;
            }

            if ($this->privilege !== false) {
                return false;
            }

            if ($this->urgent !== false) {
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
            $this->externalid = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->eventtype_id = ($row[$startcol + 7] !== null) ? (int) $row[$startcol + 7] : null;
            $this->org_id = ($row[$startcol + 8] !== null) ? (int) $row[$startcol + 8] : null;
            $this->client_id = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->contract_id = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->preveventdate = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->setdate = ($row[$startcol + 12] !== null) ? (string) $row[$startcol + 12] : null;
            $this->setperson_id = ($row[$startcol + 13] !== null) ? (int) $row[$startcol + 13] : null;
            $this->execdate = ($row[$startcol + 14] !== null) ? (string) $row[$startcol + 14] : null;
            $this->execperson_id = ($row[$startcol + 15] !== null) ? (int) $row[$startcol + 15] : null;
            $this->isprimary = ($row[$startcol + 16] !== null) ? (boolean) $row[$startcol + 16] : null;
            $this->order = ($row[$startcol + 17] !== null) ? (boolean) $row[$startcol + 17] : null;
            $this->result_id = ($row[$startcol + 18] !== null) ? (int) $row[$startcol + 18] : null;
            $this->nexteventdate = ($row[$startcol + 19] !== null) ? (string) $row[$startcol + 19] : null;
            $this->paystatus = ($row[$startcol + 20] !== null) ? (int) $row[$startcol + 20] : null;
            $this->typeasset_id = ($row[$startcol + 21] !== null) ? (int) $row[$startcol + 21] : null;
            $this->note = ($row[$startcol + 22] !== null) ? (string) $row[$startcol + 22] : null;
            $this->curator_id = ($row[$startcol + 23] !== null) ? (int) $row[$startcol + 23] : null;
            $this->assistant_id = ($row[$startcol + 24] !== null) ? (int) $row[$startcol + 24] : null;
            $this->pregnancyweek = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->mes_id = ($row[$startcol + 26] !== null) ? (int) $row[$startcol + 26] : null;
            $this->messpecification_id = ($row[$startcol + 27] !== null) ? (int) $row[$startcol + 27] : null;
            $this->rbacheresult_id = ($row[$startcol + 28] !== null) ? (int) $row[$startcol + 28] : null;
            $this->version = ($row[$startcol + 29] !== null) ? (int) $row[$startcol + 29] : null;
            $this->privilege = ($row[$startcol + 30] !== null) ? (boolean) $row[$startcol + 30] : null;
            $this->urgent = ($row[$startcol + 31] !== null) ? (boolean) $row[$startcol + 31] : null;
            $this->orgstructure_id = ($row[$startcol + 32] !== null) ? (int) $row[$startcol + 32] : null;
            $this->uuid_id = ($row[$startcol + 33] !== null) ? (int) $row[$startcol + 33] : null;
            $this->lpu_transfer = ($row[$startcol + 34] !== null) ? (string) $row[$startcol + 34] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 35; // 35 = EventPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating Event object", $e);
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
        if ($this->aEventType !== null && $this->eventtype_id !== $this->aEventType->getid()) {
            $this->aEventType = null;
        }
        if ($this->aClient !== null && $this->client_id !== $this->aClient->getid()) {
            $this->aClient = null;
        }
        if ($this->aSetPerson !== null && $this->setperson_id !== $this->aSetPerson->getid()) {
            $this->aSetPerson = null;
        }
        if ($this->aDoctor !== null && $this->execperson_id !== $this->aDoctor->getid()) {
            $this->aDoctor = null;
        }
        if ($this->aOrgStructure !== null && $this->orgstructure_id !== $this->aOrgStructure->getId()) {
            $this->aOrgStructure = null;
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
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = EventPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aEventType = null;
            $this->aClient = null;
            $this->aCreatePerson = null;
            $this->aModifyPerson = null;
            $this->aSetPerson = null;
            $this->aDoctor = null;
            $this->aOrgStructure = null;
            $this->collActions = null;

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
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = EventQuery::create()
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
            $con = Propel::getConnection(EventPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(EventPeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(EventPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(EventPeer::MODIFYDATETIME)) {
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
                EventPeer::addInstanceToPool($this);
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

            if ($this->aEventType !== null) {
                if ($this->aEventType->isModified() || $this->aEventType->isNew()) {
                    $affectedRows += $this->aEventType->save($con);
                }
                $this->setEventType($this->aEventType);
            }

            if ($this->aClient !== null) {
                if ($this->aClient->isModified() || $this->aClient->isNew()) {
                    $affectedRows += $this->aClient->save($con);
                }
                $this->setClient($this->aClient);
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

            if ($this->aDoctor !== null) {
                if ($this->aDoctor->isModified() || $this->aDoctor->isNew()) {
                    $affectedRows += $this->aDoctor->save($con);
                }
                $this->setDoctor($this->aDoctor);
            }

            if ($this->aOrgStructure !== null) {
                if ($this->aOrgStructure->isModified() || $this->aOrgStructure->isNew()) {
                    $affectedRows += $this->aOrgStructure->save($con);
                }
                $this->setOrgStructure($this->aOrgStructure);
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
                    foreach ($this->actionsScheduledForDeletion as $action) {
                        // need to save related object because we set the relation to null
                        $action->save($con);
                    }
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

        $this->modifiedColumns[] = EventPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . EventPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(EventPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(EventPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(EventPeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(EventPeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(EventPeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(EventPeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(EventPeer::EXTERNALID)) {
            $modifiedColumns[':p' . $index++]  = '`externalId`';
        }
        if ($this->isColumnModified(EventPeer::EVENTTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`eventType_id`';
        }
        if ($this->isColumnModified(EventPeer::ORG_ID)) {
            $modifiedColumns[':p' . $index++]  = '`org_id`';
        }
        if ($this->isColumnModified(EventPeer::CLIENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`client_id`';
        }
        if ($this->isColumnModified(EventPeer::CONTRACT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`contract_id`';
        }
        if ($this->isColumnModified(EventPeer::PREVEVENTDATE)) {
            $modifiedColumns[':p' . $index++]  = '`prevEventDate`';
        }
        if ($this->isColumnModified(EventPeer::SETDATE)) {
            $modifiedColumns[':p' . $index++]  = '`setDate`';
        }
        if ($this->isColumnModified(EventPeer::SETPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`setPerson_id`';
        }
        if ($this->isColumnModified(EventPeer::EXECDATE)) {
            $modifiedColumns[':p' . $index++]  = '`execDate`';
        }
        if ($this->isColumnModified(EventPeer::EXECPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`execPerson_id`';
        }
        if ($this->isColumnModified(EventPeer::ISPRIMARY)) {
            $modifiedColumns[':p' . $index++]  = '`isPrimary`';
        }
        if ($this->isColumnModified(EventPeer::ORDER)) {
            $modifiedColumns[':p' . $index++]  = '`order`';
        }
        if ($this->isColumnModified(EventPeer::RESULT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`result_id`';
        }
        if ($this->isColumnModified(EventPeer::NEXTEVENTDATE)) {
            $modifiedColumns[':p' . $index++]  = '`nextEventDate`';
        }
        if ($this->isColumnModified(EventPeer::PAYSTATUS)) {
            $modifiedColumns[':p' . $index++]  = '`payStatus`';
        }
        if ($this->isColumnModified(EventPeer::TYPEASSET_ID)) {
            $modifiedColumns[':p' . $index++]  = '`typeAsset_id`';
        }
        if ($this->isColumnModified(EventPeer::NOTE)) {
            $modifiedColumns[':p' . $index++]  = '`note`';
        }
        if ($this->isColumnModified(EventPeer::CURATOR_ID)) {
            $modifiedColumns[':p' . $index++]  = '`curator_id`';
        }
        if ($this->isColumnModified(EventPeer::ASSISTANT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`assistant_id`';
        }
        if ($this->isColumnModified(EventPeer::PREGNANCYWEEK)) {
            $modifiedColumns[':p' . $index++]  = '`pregnancyWeek`';
        }
        if ($this->isColumnModified(EventPeer::MES_ID)) {
            $modifiedColumns[':p' . $index++]  = '`MES_id`';
        }
        if ($this->isColumnModified(EventPeer::MESSPECIFICATION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`mesSpecification_id`';
        }
        if ($this->isColumnModified(EventPeer::RBACHERESULT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`rbAcheResult_id`';
        }
        if ($this->isColumnModified(EventPeer::VERSION)) {
            $modifiedColumns[':p' . $index++]  = '`version`';
        }
        if ($this->isColumnModified(EventPeer::PRIVILEGE)) {
            $modifiedColumns[':p' . $index++]  = '`privilege`';
        }
        if ($this->isColumnModified(EventPeer::URGENT)) {
            $modifiedColumns[':p' . $index++]  = '`urgent`';
        }
        if ($this->isColumnModified(EventPeer::ORGSTRUCTURE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`orgStructure_id`';
        }
        if ($this->isColumnModified(EventPeer::UUID_ID)) {
            $modifiedColumns[':p' . $index++]  = '`uuid_id`';
        }
        if ($this->isColumnModified(EventPeer::LPU_TRANSFER)) {
            $modifiedColumns[':p' . $index++]  = '`lpu_transfer`';
        }

        $sql = sprintf(
            'INSERT INTO `Event` (%s) VALUES (%s)',
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
                    case '`externalId`':
                        $stmt->bindValue($identifier, $this->externalid, PDO::PARAM_STR);
                        break;
                    case '`eventType_id`':
                        $stmt->bindValue($identifier, $this->eventtype_id, PDO::PARAM_INT);
                        break;
                    case '`org_id`':
                        $stmt->bindValue($identifier, $this->org_id, PDO::PARAM_INT);
                        break;
                    case '`client_id`':
                        $stmt->bindValue($identifier, $this->client_id, PDO::PARAM_INT);
                        break;
                    case '`contract_id`':
                        $stmt->bindValue($identifier, $this->contract_id, PDO::PARAM_INT);
                        break;
                    case '`prevEventDate`':
                        $stmt->bindValue($identifier, $this->preveventdate, PDO::PARAM_STR);
                        break;
                    case '`setDate`':
                        $stmt->bindValue($identifier, $this->setdate, PDO::PARAM_STR);
                        break;
                    case '`setPerson_id`':
                        $stmt->bindValue($identifier, $this->setperson_id, PDO::PARAM_INT);
                        break;
                    case '`execDate`':
                        $stmt->bindValue($identifier, $this->execdate, PDO::PARAM_STR);
                        break;
                    case '`execPerson_id`':
                        $stmt->bindValue($identifier, $this->execperson_id, PDO::PARAM_INT);
                        break;
                    case '`isPrimary`':
                        $stmt->bindValue($identifier, (int) $this->isprimary, PDO::PARAM_INT);
                        break;
                    case '`order`':
                        $stmt->bindValue($identifier, (int) $this->order, PDO::PARAM_INT);
                        break;
                    case '`result_id`':
                        $stmt->bindValue($identifier, $this->result_id, PDO::PARAM_INT);
                        break;
                    case '`nextEventDate`':
                        $stmt->bindValue($identifier, $this->nexteventdate, PDO::PARAM_STR);
                        break;
                    case '`payStatus`':
                        $stmt->bindValue($identifier, $this->paystatus, PDO::PARAM_INT);
                        break;
                    case '`typeAsset_id`':
                        $stmt->bindValue($identifier, $this->typeasset_id, PDO::PARAM_INT);
                        break;
                    case '`note`':
                        $stmt->bindValue($identifier, $this->note, PDO::PARAM_STR);
                        break;
                    case '`curator_id`':
                        $stmt->bindValue($identifier, $this->curator_id, PDO::PARAM_INT);
                        break;
                    case '`assistant_id`':
                        $stmt->bindValue($identifier, $this->assistant_id, PDO::PARAM_INT);
                        break;
                    case '`pregnancyWeek`':
                        $stmt->bindValue($identifier, $this->pregnancyweek, PDO::PARAM_INT);
                        break;
                    case '`MES_id`':
                        $stmt->bindValue($identifier, $this->mes_id, PDO::PARAM_INT);
                        break;
                    case '`mesSpecification_id`':
                        $stmt->bindValue($identifier, $this->messpecification_id, PDO::PARAM_INT);
                        break;
                    case '`rbAcheResult_id`':
                        $stmt->bindValue($identifier, $this->rbacheresult_id, PDO::PARAM_INT);
                        break;
                    case '`version`':
                        $stmt->bindValue($identifier, $this->version, PDO::PARAM_INT);
                        break;
                    case '`privilege`':
                        $stmt->bindValue($identifier, (int) $this->privilege, PDO::PARAM_INT);
                        break;
                    case '`urgent`':
                        $stmt->bindValue($identifier, (int) $this->urgent, PDO::PARAM_INT);
                        break;
                    case '`orgStructure_id`':
                        $stmt->bindValue($identifier, $this->orgstructure_id, PDO::PARAM_INT);
                        break;
                    case '`uuid_id`':
                        $stmt->bindValue($identifier, $this->uuid_id, PDO::PARAM_INT);
                        break;
                    case '`lpu_transfer`':
                        $stmt->bindValue($identifier, $this->lpu_transfer, PDO::PARAM_STR);
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

            if ($this->aEventType !== null) {
                if (!$this->aEventType->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aEventType->getValidationFailures());
                }
            }

            if ($this->aClient !== null) {
                if (!$this->aClient->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aClient->getValidationFailures());
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

            if ($this->aDoctor !== null) {
                if (!$this->aDoctor->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aDoctor->getValidationFailures());
                }
            }

            if ($this->aOrgStructure !== null) {
                if (!$this->aOrgStructure->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aOrgStructure->getValidationFailures());
                }
            }


            if (($retval = EventPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActions !== null) {
                    foreach ($this->collActions as $referrerFK) {
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
        $pos = EventPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getexternalId();
                break;
            case 7:
                return $this->geteventTypeId();
                break;
            case 8:
                return $this->getorgId();
                break;
            case 9:
                return $this->getclientId();
                break;
            case 10:
                return $this->getcontractId();
                break;
            case 11:
                return $this->getprevEventDate();
                break;
            case 12:
                return $this->getsetDate();
                break;
            case 13:
                return $this->getsetPersonId();
                break;
            case 14:
                return $this->getexecDate();
                break;
            case 15:
                return $this->getexecPersonId();
                break;
            case 16:
                return $this->getisPrimary();
                break;
            case 17:
                return $this->getorder();
                break;
            case 18:
                return $this->getresultId();
                break;
            case 19:
                return $this->getnextEventDate();
                break;
            case 20:
                return $this->getpayStatus();
                break;
            case 21:
                return $this->gettypeAssetId();
                break;
            case 22:
                return $this->getnote();
                break;
            case 23:
                return $this->getcuratorId();
                break;
            case 24:
                return $this->getassistantId();
                break;
            case 25:
                return $this->getpregnancyWeek();
                break;
            case 26:
                return $this->getmesId();
                break;
            case 27:
                return $this->getmesSpecificationId();
                break;
            case 28:
                return $this->getrbAcheResultId();
                break;
            case 29:
                return $this->getversion();
                break;
            case 30:
                return $this->getprivilege();
                break;
            case 31:
                return $this->geturgent();
                break;
            case 32:
                return $this->getorgStructureId();
                break;
            case 33:
                return $this->getuuidId();
                break;
            case 34:
                return $this->getlpuTransfer();
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
        if (isset($alreadyDumpedObjects['Event'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['Event'][$this->getPrimaryKey()] = true;
        $keys = EventPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getexternalId(),
            $keys[7] => $this->geteventTypeId(),
            $keys[8] => $this->getorgId(),
            $keys[9] => $this->getclientId(),
            $keys[10] => $this->getcontractId(),
            $keys[11] => $this->getprevEventDate(),
            $keys[12] => $this->getsetDate(),
            $keys[13] => $this->getsetPersonId(),
            $keys[14] => $this->getexecDate(),
            $keys[15] => $this->getexecPersonId(),
            $keys[16] => $this->getisPrimary(),
            $keys[17] => $this->getorder(),
            $keys[18] => $this->getresultId(),
            $keys[19] => $this->getnextEventDate(),
            $keys[20] => $this->getpayStatus(),
            $keys[21] => $this->gettypeAssetId(),
            $keys[22] => $this->getnote(),
            $keys[23] => $this->getcuratorId(),
            $keys[24] => $this->getassistantId(),
            $keys[25] => $this->getpregnancyWeek(),
            $keys[26] => $this->getmesId(),
            $keys[27] => $this->getmesSpecificationId(),
            $keys[28] => $this->getrbAcheResultId(),
            $keys[29] => $this->getversion(),
            $keys[30] => $this->getprivilege(),
            $keys[31] => $this->geturgent(),
            $keys[32] => $this->getorgStructureId(),
            $keys[33] => $this->getuuidId(),
            $keys[34] => $this->getlpuTransfer(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aEventType) {
                $result['EventType'] = $this->aEventType->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aClient) {
                $result['Client'] = $this->aClient->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
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
            if (null !== $this->aDoctor) {
                $result['Doctor'] = $this->aDoctor->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aOrgStructure) {
                $result['OrgStructure'] = $this->aOrgStructure->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActions) {
                $result['Actions'] = $this->collActions->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = EventPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setexternalId($value);
                break;
            case 7:
                $this->seteventTypeId($value);
                break;
            case 8:
                $this->setorgId($value);
                break;
            case 9:
                $this->setclientId($value);
                break;
            case 10:
                $this->setcontractId($value);
                break;
            case 11:
                $this->setprevEventDate($value);
                break;
            case 12:
                $this->setsetDate($value);
                break;
            case 13:
                $this->setsetPersonId($value);
                break;
            case 14:
                $this->setexecDate($value);
                break;
            case 15:
                $this->setexecPersonId($value);
                break;
            case 16:
                $this->setisPrimary($value);
                break;
            case 17:
                $this->setorder($value);
                break;
            case 18:
                $this->setresultId($value);
                break;
            case 19:
                $this->setnextEventDate($value);
                break;
            case 20:
                $this->setpayStatus($value);
                break;
            case 21:
                $this->settypeAssetId($value);
                break;
            case 22:
                $this->setnote($value);
                break;
            case 23:
                $this->setcuratorId($value);
                break;
            case 24:
                $this->setassistantId($value);
                break;
            case 25:
                $this->setpregnancyWeek($value);
                break;
            case 26:
                $this->setmesId($value);
                break;
            case 27:
                $this->setmesSpecificationId($value);
                break;
            case 28:
                $this->setrbAcheResultId($value);
                break;
            case 29:
                $this->setversion($value);
                break;
            case 30:
                $this->setprivilege($value);
                break;
            case 31:
                $this->seturgent($value);
                break;
            case 32:
                $this->setorgStructureId($value);
                break;
            case 33:
                $this->setuuidId($value);
                break;
            case 34:
                $this->setlpuTransfer($value);
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
        $keys = EventPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setexternalId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->seteventTypeId($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setorgId($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setclientId($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setcontractId($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setprevEventDate($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setsetDate($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setsetPersonId($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setexecDate($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setexecPersonId($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setisPrimary($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setorder($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setresultId($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setnextEventDate($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setpayStatus($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->settypeAssetId($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setnote($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setcuratorId($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setassistantId($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->setpregnancyWeek($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->setmesId($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setmesSpecificationId($arr[$keys[27]]);
        if (array_key_exists($keys[28], $arr)) $this->setrbAcheResultId($arr[$keys[28]]);
        if (array_key_exists($keys[29], $arr)) $this->setversion($arr[$keys[29]]);
        if (array_key_exists($keys[30], $arr)) $this->setprivilege($arr[$keys[30]]);
        if (array_key_exists($keys[31], $arr)) $this->seturgent($arr[$keys[31]]);
        if (array_key_exists($keys[32], $arr)) $this->setorgStructureId($arr[$keys[32]]);
        if (array_key_exists($keys[33], $arr)) $this->setuuidId($arr[$keys[33]]);
        if (array_key_exists($keys[34], $arr)) $this->setlpuTransfer($arr[$keys[34]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(EventPeer::DATABASE_NAME);

        if ($this->isColumnModified(EventPeer::ID)) $criteria->add(EventPeer::ID, $this->id);
        if ($this->isColumnModified(EventPeer::CREATEDATETIME)) $criteria->add(EventPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(EventPeer::CREATEPERSON_ID)) $criteria->add(EventPeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(EventPeer::MODIFYDATETIME)) $criteria->add(EventPeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(EventPeer::MODIFYPERSON_ID)) $criteria->add(EventPeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(EventPeer::DELETED)) $criteria->add(EventPeer::DELETED, $this->deleted);
        if ($this->isColumnModified(EventPeer::EXTERNALID)) $criteria->add(EventPeer::EXTERNALID, $this->externalid);
        if ($this->isColumnModified(EventPeer::EVENTTYPE_ID)) $criteria->add(EventPeer::EVENTTYPE_ID, $this->eventtype_id);
        if ($this->isColumnModified(EventPeer::ORG_ID)) $criteria->add(EventPeer::ORG_ID, $this->org_id);
        if ($this->isColumnModified(EventPeer::CLIENT_ID)) $criteria->add(EventPeer::CLIENT_ID, $this->client_id);
        if ($this->isColumnModified(EventPeer::CONTRACT_ID)) $criteria->add(EventPeer::CONTRACT_ID, $this->contract_id);
        if ($this->isColumnModified(EventPeer::PREVEVENTDATE)) $criteria->add(EventPeer::PREVEVENTDATE, $this->preveventdate);
        if ($this->isColumnModified(EventPeer::SETDATE)) $criteria->add(EventPeer::SETDATE, $this->setdate);
        if ($this->isColumnModified(EventPeer::SETPERSON_ID)) $criteria->add(EventPeer::SETPERSON_ID, $this->setperson_id);
        if ($this->isColumnModified(EventPeer::EXECDATE)) $criteria->add(EventPeer::EXECDATE, $this->execdate);
        if ($this->isColumnModified(EventPeer::EXECPERSON_ID)) $criteria->add(EventPeer::EXECPERSON_ID, $this->execperson_id);
        if ($this->isColumnModified(EventPeer::ISPRIMARY)) $criteria->add(EventPeer::ISPRIMARY, $this->isprimary);
        if ($this->isColumnModified(EventPeer::ORDER)) $criteria->add(EventPeer::ORDER, $this->order);
        if ($this->isColumnModified(EventPeer::RESULT_ID)) $criteria->add(EventPeer::RESULT_ID, $this->result_id);
        if ($this->isColumnModified(EventPeer::NEXTEVENTDATE)) $criteria->add(EventPeer::NEXTEVENTDATE, $this->nexteventdate);
        if ($this->isColumnModified(EventPeer::PAYSTATUS)) $criteria->add(EventPeer::PAYSTATUS, $this->paystatus);
        if ($this->isColumnModified(EventPeer::TYPEASSET_ID)) $criteria->add(EventPeer::TYPEASSET_ID, $this->typeasset_id);
        if ($this->isColumnModified(EventPeer::NOTE)) $criteria->add(EventPeer::NOTE, $this->note);
        if ($this->isColumnModified(EventPeer::CURATOR_ID)) $criteria->add(EventPeer::CURATOR_ID, $this->curator_id);
        if ($this->isColumnModified(EventPeer::ASSISTANT_ID)) $criteria->add(EventPeer::ASSISTANT_ID, $this->assistant_id);
        if ($this->isColumnModified(EventPeer::PREGNANCYWEEK)) $criteria->add(EventPeer::PREGNANCYWEEK, $this->pregnancyweek);
        if ($this->isColumnModified(EventPeer::MES_ID)) $criteria->add(EventPeer::MES_ID, $this->mes_id);
        if ($this->isColumnModified(EventPeer::MESSPECIFICATION_ID)) $criteria->add(EventPeer::MESSPECIFICATION_ID, $this->messpecification_id);
        if ($this->isColumnModified(EventPeer::RBACHERESULT_ID)) $criteria->add(EventPeer::RBACHERESULT_ID, $this->rbacheresult_id);
        if ($this->isColumnModified(EventPeer::VERSION)) $criteria->add(EventPeer::VERSION, $this->version);
        if ($this->isColumnModified(EventPeer::PRIVILEGE)) $criteria->add(EventPeer::PRIVILEGE, $this->privilege);
        if ($this->isColumnModified(EventPeer::URGENT)) $criteria->add(EventPeer::URGENT, $this->urgent);
        if ($this->isColumnModified(EventPeer::ORGSTRUCTURE_ID)) $criteria->add(EventPeer::ORGSTRUCTURE_ID, $this->orgstructure_id);
        if ($this->isColumnModified(EventPeer::UUID_ID)) $criteria->add(EventPeer::UUID_ID, $this->uuid_id);
        if ($this->isColumnModified(EventPeer::LPU_TRANSFER)) $criteria->add(EventPeer::LPU_TRANSFER, $this->lpu_transfer);

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
        $criteria = new Criteria(EventPeer::DATABASE_NAME);
        $criteria->add(EventPeer::ID, $this->id);

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
     * @param object $copyObj An object of Event (or compatible) type.
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
        $copyObj->setexternalId($this->getexternalId());
        $copyObj->seteventTypeId($this->geteventTypeId());
        $copyObj->setorgId($this->getorgId());
        $copyObj->setclientId($this->getclientId());
        $copyObj->setcontractId($this->getcontractId());
        $copyObj->setprevEventDate($this->getprevEventDate());
        $copyObj->setsetDate($this->getsetDate());
        $copyObj->setsetPersonId($this->getsetPersonId());
        $copyObj->setexecDate($this->getexecDate());
        $copyObj->setexecPersonId($this->getexecPersonId());
        $copyObj->setisPrimary($this->getisPrimary());
        $copyObj->setorder($this->getorder());
        $copyObj->setresultId($this->getresultId());
        $copyObj->setnextEventDate($this->getnextEventDate());
        $copyObj->setpayStatus($this->getpayStatus());
        $copyObj->settypeAssetId($this->gettypeAssetId());
        $copyObj->setnote($this->getnote());
        $copyObj->setcuratorId($this->getcuratorId());
        $copyObj->setassistantId($this->getassistantId());
        $copyObj->setpregnancyWeek($this->getpregnancyWeek());
        $copyObj->setmesId($this->getmesId());
        $copyObj->setmesSpecificationId($this->getmesSpecificationId());
        $copyObj->setrbAcheResultId($this->getrbAcheResultId());
        $copyObj->setversion($this->getversion());
        $copyObj->setprivilege($this->getprivilege());
        $copyObj->seturgent($this->geturgent());
        $copyObj->setorgStructureId($this->getorgStructureId());
        $copyObj->setuuidId($this->getuuidId());
        $copyObj->setlpuTransfer($this->getlpuTransfer());

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
     * @return Event Clone of current object.
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
     * @return EventPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new EventPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a EventType object.
     *
     * @param             EventType $v
     * @return Event The current object (for fluent API support)
     * @throws PropelException
     */
    public function setEventType(EventType $v = null)
    {
        if ($v === null) {
            $this->seteventTypeId(NULL);
        } else {
            $this->seteventTypeId($v->getid());
        }

        $this->aEventType = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the EventType object, it will not be re-added.
        if ($v !== null) {
            $v->addEvent($this);
        }


        return $this;
    }


    /**
     * Get the associated EventType object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return EventType The associated EventType object.
     * @throws PropelException
     */
    public function getEventType(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aEventType === null && ($this->eventtype_id !== null) && $doQuery) {
            $this->aEventType = EventTypeQuery::create()->findPk($this->eventtype_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aEventType->addEvents($this);
             */
        }

        return $this->aEventType;
    }

    /**
     * Declares an association between this object and a Client object.
     *
     * @param             Client $v
     * @return Event The current object (for fluent API support)
     * @throws PropelException
     */
    public function setClient(Client $v = null)
    {
        if ($v === null) {
            $this->setclientId(NULL);
        } else {
            $this->setclientId($v->getid());
        }

        $this->aClient = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Client object, it will not be re-added.
        if ($v !== null) {
            $v->addEvent($this);
        }


        return $this;
    }


    /**
     * Get the associated Client object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Client The associated Client object.
     * @throws PropelException
     */
    public function getClient(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aClient === null && ($this->client_id !== null) && $doQuery) {
            $this->aClient = ClientQuery::create()->findPk($this->client_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aClient->addEvents($this);
             */
        }

        return $this->aClient;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Event The current object (for fluent API support)
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
            $v->addEventRelatedBycreatePersonId($this);
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
                $this->aCreatePerson->addEventsRelatedBycreatePersonId($this);
             */
        }

        return $this->aCreatePerson;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Event The current object (for fluent API support)
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
            $v->addEventRelatedBymodifyPersonId($this);
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
                $this->aModifyPerson->addEventsRelatedBymodifyPersonId($this);
             */
        }

        return $this->aModifyPerson;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Event The current object (for fluent API support)
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
            $v->addEventRelatedBysetPersonId($this);
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
                $this->aSetPerson->addEventsRelatedBysetPersonId($this);
             */
        }

        return $this->aSetPerson;
    }

    /**
     * Declares an association between this object and a Person object.
     *
     * @param             Person $v
     * @return Event The current object (for fluent API support)
     * @throws PropelException
     */
    public function setDoctor(Person $v = null)
    {
        if ($v === null) {
            $this->setexecPersonId(NULL);
        } else {
            $this->setexecPersonId($v->getid());
        }

        $this->aDoctor = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Person object, it will not be re-added.
        if ($v !== null) {
            $v->addEventRelatedByexecPersonId($this);
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
    public function getDoctor(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aDoctor === null && ($this->execperson_id !== null) && $doQuery) {
            $this->aDoctor = PersonQuery::create()->findPk($this->execperson_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aDoctor->addEventsRelatedByexecPersonId($this);
             */
        }

        return $this->aDoctor;
    }

    /**
     * Declares an association between this object and a OrgStructure object.
     *
     * @param             OrgStructure $v
     * @return Event The current object (for fluent API support)
     * @throws PropelException
     */
    public function setOrgStructure(OrgStructure $v = null)
    {
        if ($v === null) {
            $this->setorgStructureId(NULL);
        } else {
            $this->setorgStructureId($v->getId());
        }

        $this->aOrgStructure = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the OrgStructure object, it will not be re-added.
        if ($v !== null) {
            $v->addEvent($this);
        }


        return $this;
    }


    /**
     * Get the associated OrgStructure object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return OrgStructure The associated OrgStructure object.
     * @throws PropelException
     */
    public function getOrgStructure(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aOrgStructure === null && ($this->orgstructure_id !== null) && $doQuery) {
            $this->aOrgStructure = OrgStructureQuery::create()->findPk($this->orgstructure_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aOrgStructure->addEvents($this);
             */
        }

        return $this->aOrgStructure;
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
    }

    /**
     * Clears out the collActions collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Event The current object (for fluent API support)
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
     * If this Event is new, it will return
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
                    ->filterByEvent($this)
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
     * @return Event The current object (for fluent API support)
     */
    public function setActions(PropelCollection $actions, PropelPDO $con = null)
    {
        $actionsToDelete = $this->getActions(new Criteria(), $con)->diff($actions);

        $this->actionsScheduledForDeletion = unserialize(serialize($actionsToDelete));

        foreach ($actionsToDelete as $actionRemoved) {
            $actionRemoved->setEvent(null);
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
                ->filterByEvent($this)
                ->count($con);
        }

        return count($this->collActions);
    }

    /**
     * Method called to associate a Action object to this object
     * through the Action foreign key attribute.
     *
     * @param    Action $l Action
     * @return Event The current object (for fluent API support)
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
        $action->setEvent($this);
    }

    /**
     * @param	Action $action The action object to remove.
     * @return Event The current object (for fluent API support)
     */
    public function removeAction($action)
    {
        if ($this->getActions()->contains($action)) {
            $this->collActions->remove($this->collActions->search($action));
            if (null === $this->actionsScheduledForDeletion) {
                $this->actionsScheduledForDeletion = clone $this->collActions;
                $this->actionsScheduledForDeletion->clear();
            }
            $this->actionsScheduledForDeletion[]= $action;
            $action->setEvent(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Event is new, it will return
     * an empty collection; or if this Event has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Event.
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
     * Otherwise if this Event is new, it will return
     * an empty collection; or if this Event has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Event.
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
     * Otherwise if this Event is new, it will return
     * an empty collection; or if this Event has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Event.
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
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Event is new, it will return
     * an empty collection; or if this Event has previously
     * been saved, it will retrieve related Actions from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Event.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsJoinActionType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('ActionType', $join_behavior);

        return $this->getActions($query, $con);
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
        $this->externalid = null;
        $this->eventtype_id = null;
        $this->org_id = null;
        $this->client_id = null;
        $this->contract_id = null;
        $this->preveventdate = null;
        $this->setdate = null;
        $this->setperson_id = null;
        $this->execdate = null;
        $this->execperson_id = null;
        $this->isprimary = null;
        $this->order = null;
        $this->result_id = null;
        $this->nexteventdate = null;
        $this->paystatus = null;
        $this->typeasset_id = null;
        $this->note = null;
        $this->curator_id = null;
        $this->assistant_id = null;
        $this->pregnancyweek = null;
        $this->mes_id = null;
        $this->messpecification_id = null;
        $this->rbacheresult_id = null;
        $this->version = null;
        $this->privilege = null;
        $this->urgent = null;
        $this->orgstructure_id = null;
        $this->uuid_id = null;
        $this->lpu_transfer = null;
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
            if ($this->aEventType instanceof Persistent) {
              $this->aEventType->clearAllReferences($deep);
            }
            if ($this->aClient instanceof Persistent) {
              $this->aClient->clearAllReferences($deep);
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
            if ($this->aDoctor instanceof Persistent) {
              $this->aDoctor->clearAllReferences($deep);
            }
            if ($this->aOrgStructure instanceof Persistent) {
              $this->aOrgStructure->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActions instanceof PropelCollection) {
            $this->collActions->clearIterator();
        }
        $this->collActions = null;
        $this->aEventType = null;
        $this->aClient = null;
        $this->aCreatePerson = null;
        $this->aModifyPerson = null;
        $this->aSetPerson = null;
        $this->aDoctor = null;
        $this->aOrgStructure = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(EventPeer::DEFAULT_STRING_FORMAT);
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
     * @return     Event The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = EventPeer::MODIFYDATETIME;

        return $this;
    }

}
