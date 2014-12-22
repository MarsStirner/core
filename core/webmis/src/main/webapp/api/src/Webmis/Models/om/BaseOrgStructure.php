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
use Webmis\Models\OrgStructure;
use Webmis\Models\OrgStructurePeer;
use Webmis\Models\OrgStructureQuery;
use Webmis\Models\RbStorage;
use Webmis\Models\RbStorageQuery;

/**
 * Base class that represents a row from the 'OrgStructure' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseOrgStructure extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\OrgStructurePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        OrgStructurePeer
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
     * The value for the organisation_id field.
     * @var        int
     */
    protected $organisation_id;

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
     * The value for the parent_id field.
     * @var        int
     */
    protected $parent_id;

    /**
     * The value for the type field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $type;

    /**
     * The value for the net_id field.
     * @var        int
     */
    protected $net_id;

    /**
     * The value for the isarea field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $isarea;

    /**
     * The value for the hashospitalbeds field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $hashospitalbeds;

    /**
     * The value for the hasstocks field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $hasstocks;

    /**
     * The value for the infiscode field.
     * @var        string
     */
    protected $infiscode;

    /**
     * The value for the infisinternalcode field.
     * @var        string
     */
    protected $infisinternalcode;

    /**
     * The value for the infisdeptypecode field.
     * @var        string
     */
    protected $infisdeptypecode;

    /**
     * The value for the infistariffcode field.
     * @var        string
     */
    protected $infistariffcode;

    /**
     * The value for the availableforexternal field.
     * Note: this column has a database default value of: 1
     * @var        int
     */
    protected $availableforexternal;

    /**
     * The value for the address field.
     * @var        string
     */
    protected $address;

    /**
     * The value for the inheriteventtypes field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $inheriteventtypes;

    /**
     * The value for the inheritactiontypes field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $inheritactiontypes;

    /**
     * The value for the inheritgaps field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $inheritgaps;

    /**
     * The value for the uuid_id field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $uuid_id;

    /**
     * The value for the show field.
     * Note: this column has a database default value of: 1
     * @var        int
     */
    protected $show;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEvents;
    protected $collEventsPartial;

    /**
     * @var        PropelObjectCollection|RbStorage[] Collection to store aggregation of RbStorage objects.
     */
    protected $collRbStorages;
    protected $collRbStoragesPartial;

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
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $rbStoragesScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->type = 0;
        $this->isarea = 0;
        $this->hashospitalbeds = false;
        $this->hasstocks = false;
        $this->availableforexternal = 1;
        $this->inheriteventtypes = false;
        $this->inheritactiontypes = false;
        $this->inheritgaps = false;
        $this->uuid_id = 0;
        $this->show = 1;
    }

    /**
     * Initializes internal state of BaseOrgStructure object.
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
     * Get the [organisation_id] column value.
     *
     * @return int
     */
    public function getorganisationId()
    {
        return $this->organisation_id;
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
     * Get the [parent_id] column value.
     *
     * @return int
     */
    public function getparentId()
    {
        return $this->parent_id;
    }

    /**
     * Get the [type] column value.
     *
     * @return int
     */
    public function gettype()
    {
        return $this->type;
    }

    /**
     * Get the [net_id] column value.
     *
     * @return int
     */
    public function getnetId()
    {
        return $this->net_id;
    }

    /**
     * Get the [isarea] column value.
     *
     * @return int
     */
    public function getisArea()
    {
        return $this->isarea;
    }

    /**
     * Get the [hashospitalbeds] column value.
     *
     * @return boolean
     */
    public function gethasHospitalBeds()
    {
        return $this->hashospitalbeds;
    }

    /**
     * Get the [hasstocks] column value.
     *
     * @return boolean
     */
    public function gethasStocks()
    {
        return $this->hasstocks;
    }

    /**
     * Get the [infiscode] column value.
     *
     * @return string
     */
    public function getinfisCode()
    {
        return $this->infiscode;
    }

    /**
     * Get the [infisinternalcode] column value.
     *
     * @return string
     */
    public function getinfisInternalCode()
    {
        return $this->infisinternalcode;
    }

    /**
     * Get the [infisdeptypecode] column value.
     *
     * @return string
     */
    public function getinfisDepTypeCode()
    {
        return $this->infisdeptypecode;
    }

    /**
     * Get the [infistariffcode] column value.
     *
     * @return string
     */
    public function getinfisTariffCode()
    {
        return $this->infistariffcode;
    }

    /**
     * Get the [availableforexternal] column value.
     *
     * @return int
     */
    public function getavailableForExternal()
    {
        return $this->availableforexternal;
    }

    /**
     * Get the [address] column value.
     *
     * @return string
     */
    public function getaddress()
    {
        return $this->address;
    }

    /**
     * Get the [inheriteventtypes] column value.
     *
     * @return boolean
     */
    public function getinheritEventTypes()
    {
        return $this->inheriteventtypes;
    }

    /**
     * Get the [inheritactiontypes] column value.
     *
     * @return boolean
     */
    public function getinheritActionTypes()
    {
        return $this->inheritactiontypes;
    }

    /**
     * Get the [inheritgaps] column value.
     *
     * @return boolean
     */
    public function getinheritGaps()
    {
        return $this->inheritgaps;
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
     * Get the [show] column value.
     *
     * @return int
     */
    public function getshow()
    {
        return $this->show;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::ID;
        }


        return $this;
    } // setId()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = OrgStructurePeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = OrgStructurePeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::MODIFYPERSON_ID;
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
     * @return OrgStructure The current object (for fluent API support)
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
            $this->modifiedColumns[] = OrgStructurePeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [organisation_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setorganisationId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->organisation_id !== $v) {
            $this->organisation_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::ORGANISATION_ID;
        }


        return $this;
    } // setorganisationId()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = OrgStructurePeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = OrgStructurePeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [parent_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setparentId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->parent_id !== $v) {
            $this->parent_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::PARENT_ID;
        }


        return $this;
    } // setparentId()

    /**
     * Set the value of [type] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function settype($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->type !== $v) {
            $this->type = $v;
            $this->modifiedColumns[] = OrgStructurePeer::TYPE;
        }


        return $this;
    } // settype()

    /**
     * Set the value of [net_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setnetId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->net_id !== $v) {
            $this->net_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::NET_ID;
        }


        return $this;
    } // setnetId()

    /**
     * Set the value of [isarea] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setisArea($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->isarea !== $v) {
            $this->isarea = $v;
            $this->modifiedColumns[] = OrgStructurePeer::ISAREA;
        }


        return $this;
    } // setisArea()

    /**
     * Sets the value of the [hashospitalbeds] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function sethasHospitalBeds($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->hashospitalbeds !== $v) {
            $this->hashospitalbeds = $v;
            $this->modifiedColumns[] = OrgStructurePeer::HASHOSPITALBEDS;
        }


        return $this;
    } // sethasHospitalBeds()

    /**
     * Sets the value of the [hasstocks] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function sethasStocks($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->hasstocks !== $v) {
            $this->hasstocks = $v;
            $this->modifiedColumns[] = OrgStructurePeer::HASSTOCKS;
        }


        return $this;
    } // sethasStocks()

    /**
     * Set the value of [infiscode] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinfisCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->infiscode !== $v) {
            $this->infiscode = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INFISCODE;
        }


        return $this;
    } // setinfisCode()

    /**
     * Set the value of [infisinternalcode] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinfisInternalCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->infisinternalcode !== $v) {
            $this->infisinternalcode = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INFISINTERNALCODE;
        }


        return $this;
    } // setinfisInternalCode()

    /**
     * Set the value of [infisdeptypecode] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinfisDepTypeCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->infisdeptypecode !== $v) {
            $this->infisdeptypecode = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INFISDEPTYPECODE;
        }


        return $this;
    } // setinfisDepTypeCode()

    /**
     * Set the value of [infistariffcode] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinfisTariffCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->infistariffcode !== $v) {
            $this->infistariffcode = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INFISTARIFFCODE;
        }


        return $this;
    } // setinfisTariffCode()

    /**
     * Set the value of [availableforexternal] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setavailableForExternal($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->availableforexternal !== $v) {
            $this->availableforexternal = $v;
            $this->modifiedColumns[] = OrgStructurePeer::AVAILABLEFOREXTERNAL;
        }


        return $this;
    } // setavailableForExternal()

    /**
     * Set the value of [address] column.
     *
     * @param string $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setaddress($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->address !== $v) {
            $this->address = $v;
            $this->modifiedColumns[] = OrgStructurePeer::ADDRESS;
        }


        return $this;
    } // setaddress()

    /**
     * Sets the value of the [inheriteventtypes] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinheritEventTypes($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->inheriteventtypes !== $v) {
            $this->inheriteventtypes = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INHERITEVENTTYPES;
        }


        return $this;
    } // setinheritEventTypes()

    /**
     * Sets the value of the [inheritactiontypes] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinheritActionTypes($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->inheritactiontypes !== $v) {
            $this->inheritactiontypes = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INHERITACTIONTYPES;
        }


        return $this;
    } // setinheritActionTypes()

    /**
     * Sets the value of the [inheritgaps] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setinheritGaps($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->inheritgaps !== $v) {
            $this->inheritgaps = $v;
            $this->modifiedColumns[] = OrgStructurePeer::INHERITGAPS;
        }


        return $this;
    } // setinheritGaps()

    /**
     * Set the value of [uuid_id] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setuuidId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->uuid_id !== $v) {
            $this->uuid_id = $v;
            $this->modifiedColumns[] = OrgStructurePeer::UUID_ID;
        }


        return $this;
    } // setuuidId()

    /**
     * Set the value of [show] column.
     *
     * @param int $v new value
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setshow($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->show !== $v) {
            $this->show = $v;
            $this->modifiedColumns[] = OrgStructurePeer::SHOW;
        }


        return $this;
    } // setshow()

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

            if ($this->type !== 0) {
                return false;
            }

            if ($this->isarea !== 0) {
                return false;
            }

            if ($this->hashospitalbeds !== false) {
                return false;
            }

            if ($this->hasstocks !== false) {
                return false;
            }

            if ($this->availableforexternal !== 1) {
                return false;
            }

            if ($this->inheriteventtypes !== false) {
                return false;
            }

            if ($this->inheritactiontypes !== false) {
                return false;
            }

            if ($this->inheritgaps !== false) {
                return false;
            }

            if ($this->uuid_id !== 0) {
                return false;
            }

            if ($this->show !== 1) {
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
            $this->organisation_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->code = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->name = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->parent_id = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->type = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->net_id = ($row[$startcol + 11] !== null) ? (int) $row[$startcol + 11] : null;
            $this->isarea = ($row[$startcol + 12] !== null) ? (int) $row[$startcol + 12] : null;
            $this->hashospitalbeds = ($row[$startcol + 13] !== null) ? (boolean) $row[$startcol + 13] : null;
            $this->hasstocks = ($row[$startcol + 14] !== null) ? (boolean) $row[$startcol + 14] : null;
            $this->infiscode = ($row[$startcol + 15] !== null) ? (string) $row[$startcol + 15] : null;
            $this->infisinternalcode = ($row[$startcol + 16] !== null) ? (string) $row[$startcol + 16] : null;
            $this->infisdeptypecode = ($row[$startcol + 17] !== null) ? (string) $row[$startcol + 17] : null;
            $this->infistariffcode = ($row[$startcol + 18] !== null) ? (string) $row[$startcol + 18] : null;
            $this->availableforexternal = ($row[$startcol + 19] !== null) ? (int) $row[$startcol + 19] : null;
            $this->address = ($row[$startcol + 20] !== null) ? (string) $row[$startcol + 20] : null;
            $this->inheriteventtypes = ($row[$startcol + 21] !== null) ? (boolean) $row[$startcol + 21] : null;
            $this->inheritactiontypes = ($row[$startcol + 22] !== null) ? (boolean) $row[$startcol + 22] : null;
            $this->inheritgaps = ($row[$startcol + 23] !== null) ? (boolean) $row[$startcol + 23] : null;
            $this->uuid_id = ($row[$startcol + 24] !== null) ? (int) $row[$startcol + 24] : null;
            $this->show = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 26; // 26 = OrgStructurePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating OrgStructure object", $e);
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
            $con = Propel::getConnection(OrgStructurePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = OrgStructurePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->collEvents = null;

            $this->collRbStorages = null;

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
            $con = Propel::getConnection(OrgStructurePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = OrgStructureQuery::create()
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
            $con = Propel::getConnection(OrgStructurePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(OrgStructurePeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(OrgStructurePeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(OrgStructurePeer::MODIFYDATETIME)) {
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
                OrgStructurePeer::addInstanceToPool($this);
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
                    foreach ($this->eventsScheduledForDeletion as $event) {
                        // need to save related object because we set the relation to null
                        $event->save($con);
                    }
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

            if ($this->rbStoragesScheduledForDeletion !== null) {
                if (!$this->rbStoragesScheduledForDeletion->isEmpty()) {
                    foreach ($this->rbStoragesScheduledForDeletion as $rbStorage) {
                        // need to save related object because we set the relation to null
                        $rbStorage->save($con);
                    }
                    $this->rbStoragesScheduledForDeletion = null;
                }
            }

            if ($this->collRbStorages !== null) {
                foreach ($this->collRbStorages as $referrerFK) {
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

        $this->modifiedColumns[] = OrgStructurePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . OrgStructurePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(OrgStructurePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(OrgStructurePeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(OrgStructurePeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(OrgStructurePeer::ORGANISATION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`organisation_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(OrgStructurePeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(OrgStructurePeer::PARENT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`parent_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::TYPE)) {
            $modifiedColumns[':p' . $index++]  = '`type`';
        }
        if ($this->isColumnModified(OrgStructurePeer::NET_ID)) {
            $modifiedColumns[':p' . $index++]  = '`net_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::ISAREA)) {
            $modifiedColumns[':p' . $index++]  = '`isArea`';
        }
        if ($this->isColumnModified(OrgStructurePeer::HASHOSPITALBEDS)) {
            $modifiedColumns[':p' . $index++]  = '`hasHospitalBeds`';
        }
        if ($this->isColumnModified(OrgStructurePeer::HASSTOCKS)) {
            $modifiedColumns[':p' . $index++]  = '`hasStocks`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INFISCODE)) {
            $modifiedColumns[':p' . $index++]  = '`infisCode`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INFISINTERNALCODE)) {
            $modifiedColumns[':p' . $index++]  = '`infisInternalCode`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INFISDEPTYPECODE)) {
            $modifiedColumns[':p' . $index++]  = '`infisDepTypeCode`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INFISTARIFFCODE)) {
            $modifiedColumns[':p' . $index++]  = '`infisTariffCode`';
        }
        if ($this->isColumnModified(OrgStructurePeer::AVAILABLEFOREXTERNAL)) {
            $modifiedColumns[':p' . $index++]  = '`availableForExternal`';
        }
        if ($this->isColumnModified(OrgStructurePeer::ADDRESS)) {
            $modifiedColumns[':p' . $index++]  = '`Address`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INHERITEVENTTYPES)) {
            $modifiedColumns[':p' . $index++]  = '`inheritEventTypes`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INHERITACTIONTYPES)) {
            $modifiedColumns[':p' . $index++]  = '`inheritActionTypes`';
        }
        if ($this->isColumnModified(OrgStructurePeer::INHERITGAPS)) {
            $modifiedColumns[':p' . $index++]  = '`inheritGaps`';
        }
        if ($this->isColumnModified(OrgStructurePeer::UUID_ID)) {
            $modifiedColumns[':p' . $index++]  = '`uuid_id`';
        }
        if ($this->isColumnModified(OrgStructurePeer::SHOW)) {
            $modifiedColumns[':p' . $index++]  = '`show`';
        }

        $sql = sprintf(
            'INSERT INTO `OrgStructure` (%s) VALUES (%s)',
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
                    case '`organisation_id`':
                        $stmt->bindValue($identifier, $this->organisation_id, PDO::PARAM_INT);
                        break;
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`parent_id`':
                        $stmt->bindValue($identifier, $this->parent_id, PDO::PARAM_INT);
                        break;
                    case '`type`':
                        $stmt->bindValue($identifier, $this->type, PDO::PARAM_INT);
                        break;
                    case '`net_id`':
                        $stmt->bindValue($identifier, $this->net_id, PDO::PARAM_INT);
                        break;
                    case '`isArea`':
                        $stmt->bindValue($identifier, $this->isarea, PDO::PARAM_INT);
                        break;
                    case '`hasHospitalBeds`':
                        $stmt->bindValue($identifier, (int) $this->hashospitalbeds, PDO::PARAM_INT);
                        break;
                    case '`hasStocks`':
                        $stmt->bindValue($identifier, (int) $this->hasstocks, PDO::PARAM_INT);
                        break;
                    case '`infisCode`':
                        $stmt->bindValue($identifier, $this->infiscode, PDO::PARAM_STR);
                        break;
                    case '`infisInternalCode`':
                        $stmt->bindValue($identifier, $this->infisinternalcode, PDO::PARAM_STR);
                        break;
                    case '`infisDepTypeCode`':
                        $stmt->bindValue($identifier, $this->infisdeptypecode, PDO::PARAM_STR);
                        break;
                    case '`infisTariffCode`':
                        $stmt->bindValue($identifier, $this->infistariffcode, PDO::PARAM_STR);
                        break;
                    case '`availableForExternal`':
                        $stmt->bindValue($identifier, $this->availableforexternal, PDO::PARAM_INT);
                        break;
                    case '`Address`':
                        $stmt->bindValue($identifier, $this->address, PDO::PARAM_STR);
                        break;
                    case '`inheritEventTypes`':
                        $stmt->bindValue($identifier, (int) $this->inheriteventtypes, PDO::PARAM_INT);
                        break;
                    case '`inheritActionTypes`':
                        $stmt->bindValue($identifier, (int) $this->inheritactiontypes, PDO::PARAM_INT);
                        break;
                    case '`inheritGaps`':
                        $stmt->bindValue($identifier, (int) $this->inheritgaps, PDO::PARAM_INT);
                        break;
                    case '`uuid_id`':
                        $stmt->bindValue($identifier, $this->uuid_id, PDO::PARAM_INT);
                        break;
                    case '`show`':
                        $stmt->bindValue($identifier, $this->show, PDO::PARAM_INT);
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


            if (($retval = OrgStructurePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collEvents !== null) {
                    foreach ($this->collEvents as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collRbStorages !== null) {
                    foreach ($this->collRbStorages as $referrerFK) {
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
        $pos = OrgStructurePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getorganisationId();
                break;
            case 7:
                return $this->getcode();
                break;
            case 8:
                return $this->getname();
                break;
            case 9:
                return $this->getparentId();
                break;
            case 10:
                return $this->gettype();
                break;
            case 11:
                return $this->getnetId();
                break;
            case 12:
                return $this->getisArea();
                break;
            case 13:
                return $this->gethasHospitalBeds();
                break;
            case 14:
                return $this->gethasStocks();
                break;
            case 15:
                return $this->getinfisCode();
                break;
            case 16:
                return $this->getinfisInternalCode();
                break;
            case 17:
                return $this->getinfisDepTypeCode();
                break;
            case 18:
                return $this->getinfisTariffCode();
                break;
            case 19:
                return $this->getavailableForExternal();
                break;
            case 20:
                return $this->getaddress();
                break;
            case 21:
                return $this->getinheritEventTypes();
                break;
            case 22:
                return $this->getinheritActionTypes();
                break;
            case 23:
                return $this->getinheritGaps();
                break;
            case 24:
                return $this->getuuidId();
                break;
            case 25:
                return $this->getshow();
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
        if (isset($alreadyDumpedObjects['OrgStructure'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['OrgStructure'][$this->getPrimaryKey()] = true;
        $keys = OrgStructurePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getId(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getorganisationId(),
            $keys[7] => $this->getcode(),
            $keys[8] => $this->getname(),
            $keys[9] => $this->getparentId(),
            $keys[10] => $this->gettype(),
            $keys[11] => $this->getnetId(),
            $keys[12] => $this->getisArea(),
            $keys[13] => $this->gethasHospitalBeds(),
            $keys[14] => $this->gethasStocks(),
            $keys[15] => $this->getinfisCode(),
            $keys[16] => $this->getinfisInternalCode(),
            $keys[17] => $this->getinfisDepTypeCode(),
            $keys[18] => $this->getinfisTariffCode(),
            $keys[19] => $this->getavailableForExternal(),
            $keys[20] => $this->getaddress(),
            $keys[21] => $this->getinheritEventTypes(),
            $keys[22] => $this->getinheritActionTypes(),
            $keys[23] => $this->getinheritGaps(),
            $keys[24] => $this->getuuidId(),
            $keys[25] => $this->getshow(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->collEvents) {
                $result['Events'] = $this->collEvents->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collRbStorages) {
                $result['RbStorages'] = $this->collRbStorages->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = OrgStructurePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setorganisationId($value);
                break;
            case 7:
                $this->setcode($value);
                break;
            case 8:
                $this->setname($value);
                break;
            case 9:
                $this->setparentId($value);
                break;
            case 10:
                $this->settype($value);
                break;
            case 11:
                $this->setnetId($value);
                break;
            case 12:
                $this->setisArea($value);
                break;
            case 13:
                $this->sethasHospitalBeds($value);
                break;
            case 14:
                $this->sethasStocks($value);
                break;
            case 15:
                $this->setinfisCode($value);
                break;
            case 16:
                $this->setinfisInternalCode($value);
                break;
            case 17:
                $this->setinfisDepTypeCode($value);
                break;
            case 18:
                $this->setinfisTariffCode($value);
                break;
            case 19:
                $this->setavailableForExternal($value);
                break;
            case 20:
                $this->setaddress($value);
                break;
            case 21:
                $this->setinheritEventTypes($value);
                break;
            case 22:
                $this->setinheritActionTypes($value);
                break;
            case 23:
                $this->setinheritGaps($value);
                break;
            case 24:
                $this->setuuidId($value);
                break;
            case 25:
                $this->setshow($value);
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
        $keys = OrgStructurePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setId($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setorganisationId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setcode($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setname($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setparentId($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->settype($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setnetId($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setisArea($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->sethasHospitalBeds($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->sethasStocks($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setinfisCode($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setinfisInternalCode($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setinfisDepTypeCode($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setinfisTariffCode($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setavailableForExternal($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setaddress($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setinheritEventTypes($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setinheritActionTypes($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setinheritGaps($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setuuidId($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->setshow($arr[$keys[25]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(OrgStructurePeer::DATABASE_NAME);

        if ($this->isColumnModified(OrgStructurePeer::ID)) $criteria->add(OrgStructurePeer::ID, $this->id);
        if ($this->isColumnModified(OrgStructurePeer::CREATEDATETIME)) $criteria->add(OrgStructurePeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(OrgStructurePeer::CREATEPERSON_ID)) $criteria->add(OrgStructurePeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(OrgStructurePeer::MODIFYDATETIME)) $criteria->add(OrgStructurePeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(OrgStructurePeer::MODIFYPERSON_ID)) $criteria->add(OrgStructurePeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(OrgStructurePeer::DELETED)) $criteria->add(OrgStructurePeer::DELETED, $this->deleted);
        if ($this->isColumnModified(OrgStructurePeer::ORGANISATION_ID)) $criteria->add(OrgStructurePeer::ORGANISATION_ID, $this->organisation_id);
        if ($this->isColumnModified(OrgStructurePeer::CODE)) $criteria->add(OrgStructurePeer::CODE, $this->code);
        if ($this->isColumnModified(OrgStructurePeer::NAME)) $criteria->add(OrgStructurePeer::NAME, $this->name);
        if ($this->isColumnModified(OrgStructurePeer::PARENT_ID)) $criteria->add(OrgStructurePeer::PARENT_ID, $this->parent_id);
        if ($this->isColumnModified(OrgStructurePeer::TYPE)) $criteria->add(OrgStructurePeer::TYPE, $this->type);
        if ($this->isColumnModified(OrgStructurePeer::NET_ID)) $criteria->add(OrgStructurePeer::NET_ID, $this->net_id);
        if ($this->isColumnModified(OrgStructurePeer::ISAREA)) $criteria->add(OrgStructurePeer::ISAREA, $this->isarea);
        if ($this->isColumnModified(OrgStructurePeer::HASHOSPITALBEDS)) $criteria->add(OrgStructurePeer::HASHOSPITALBEDS, $this->hashospitalbeds);
        if ($this->isColumnModified(OrgStructurePeer::HASSTOCKS)) $criteria->add(OrgStructurePeer::HASSTOCKS, $this->hasstocks);
        if ($this->isColumnModified(OrgStructurePeer::INFISCODE)) $criteria->add(OrgStructurePeer::INFISCODE, $this->infiscode);
        if ($this->isColumnModified(OrgStructurePeer::INFISINTERNALCODE)) $criteria->add(OrgStructurePeer::INFISINTERNALCODE, $this->infisinternalcode);
        if ($this->isColumnModified(OrgStructurePeer::INFISDEPTYPECODE)) $criteria->add(OrgStructurePeer::INFISDEPTYPECODE, $this->infisdeptypecode);
        if ($this->isColumnModified(OrgStructurePeer::INFISTARIFFCODE)) $criteria->add(OrgStructurePeer::INFISTARIFFCODE, $this->infistariffcode);
        if ($this->isColumnModified(OrgStructurePeer::AVAILABLEFOREXTERNAL)) $criteria->add(OrgStructurePeer::AVAILABLEFOREXTERNAL, $this->availableforexternal);
        if ($this->isColumnModified(OrgStructurePeer::ADDRESS)) $criteria->add(OrgStructurePeer::ADDRESS, $this->address);
        if ($this->isColumnModified(OrgStructurePeer::INHERITEVENTTYPES)) $criteria->add(OrgStructurePeer::INHERITEVENTTYPES, $this->inheriteventtypes);
        if ($this->isColumnModified(OrgStructurePeer::INHERITACTIONTYPES)) $criteria->add(OrgStructurePeer::INHERITACTIONTYPES, $this->inheritactiontypes);
        if ($this->isColumnModified(OrgStructurePeer::INHERITGAPS)) $criteria->add(OrgStructurePeer::INHERITGAPS, $this->inheritgaps);
        if ($this->isColumnModified(OrgStructurePeer::UUID_ID)) $criteria->add(OrgStructurePeer::UUID_ID, $this->uuid_id);
        if ($this->isColumnModified(OrgStructurePeer::SHOW)) $criteria->add(OrgStructurePeer::SHOW, $this->show);

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
        $criteria = new Criteria(OrgStructurePeer::DATABASE_NAME);
        $criteria->add(OrgStructurePeer::ID, $this->id);

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
     * @param object $copyObj An object of OrgStructure (or compatible) type.
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
        $copyObj->setorganisationId($this->getorganisationId());
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());
        $copyObj->setparentId($this->getparentId());
        $copyObj->settype($this->gettype());
        $copyObj->setnetId($this->getnetId());
        $copyObj->setisArea($this->getisArea());
        $copyObj->sethasHospitalBeds($this->gethasHospitalBeds());
        $copyObj->sethasStocks($this->gethasStocks());
        $copyObj->setinfisCode($this->getinfisCode());
        $copyObj->setinfisInternalCode($this->getinfisInternalCode());
        $copyObj->setinfisDepTypeCode($this->getinfisDepTypeCode());
        $copyObj->setinfisTariffCode($this->getinfisTariffCode());
        $copyObj->setavailableForExternal($this->getavailableForExternal());
        $copyObj->setaddress($this->getaddress());
        $copyObj->setinheritEventTypes($this->getinheritEventTypes());
        $copyObj->setinheritActionTypes($this->getinheritActionTypes());
        $copyObj->setinheritGaps($this->getinheritGaps());
        $copyObj->setuuidId($this->getuuidId());
        $copyObj->setshow($this->getshow());

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

            foreach ($this->getRbStorages() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addRbStorage($relObj->copy($deepCopy));
                }
            }

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
     * @return OrgStructure Clone of current object.
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
     * @return OrgStructurePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new OrgStructurePeer();
        }

        return self::$peer;
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
        if ('RbStorage' == $relationName) {
            $this->initRbStorages();
        }
    }

    /**
     * Clears out the collEvents collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return OrgStructure The current object (for fluent API support)
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
     * If this OrgStructure is new, it will return
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
                    ->filterByOrgStructure($this)
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
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setEvents(PropelCollection $events, PropelPDO $con = null)
    {
        $eventsToDelete = $this->getEvents(new Criteria(), $con)->diff($events);

        $this->eventsScheduledForDeletion = unserialize(serialize($eventsToDelete));

        foreach ($eventsToDelete as $eventRemoved) {
            $eventRemoved->setOrgStructure(null);
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
                ->filterByOrgStructure($this)
                ->count($con);
        }

        return count($this->collEvents);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return OrgStructure The current object (for fluent API support)
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
        $event->setOrgStructure($this);
    }

    /**
     * @param	Event $event The event object to remove.
     * @return OrgStructure The current object (for fluent API support)
     */
    public function removeEvent($event)
    {
        if ($this->getEvents()->contains($event)) {
            $this->collEvents->remove($this->collEvents->search($event));
            if (null === $this->eventsScheduledForDeletion) {
                $this->eventsScheduledForDeletion = clone $this->collEvents;
                $this->eventsScheduledForDeletion->clear();
            }
            $this->eventsScheduledForDeletion[]= $event;
            $event->setOrgStructure(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsJoinEventType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('EventType', $join_behavior);

        return $this->getEvents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
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
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
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
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
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
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
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
     * Otherwise if this OrgStructure is new, it will return
     * an empty collection; or if this OrgStructure has previously
     * been saved, it will retrieve related Events from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in OrgStructure.
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
     * Clears out the collRbStorages collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return OrgStructure The current object (for fluent API support)
     * @see        addRbStorages()
     */
    public function clearRbStorages()
    {
        $this->collRbStorages = null; // important to set this to null since that means it is uninitialized
        $this->collRbStoragesPartial = null;

        return $this;
    }

    /**
     * reset is the collRbStorages collection loaded partially
     *
     * @return void
     */
    public function resetPartialRbStorages($v = true)
    {
        $this->collRbStoragesPartial = $v;
    }

    /**
     * Initializes the collRbStorages collection.
     *
     * By default this just sets the collRbStorages collection to an empty array (like clearcollRbStorages());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initRbStorages($overrideExisting = true)
    {
        if (null !== $this->collRbStorages && !$overrideExisting) {
            return;
        }
        $this->collRbStorages = new PropelObjectCollection();
        $this->collRbStorages->setModel('RbStorage');
    }

    /**
     * Gets an array of RbStorage objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this OrgStructure is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|RbStorage[] List of RbStorage objects
     * @throws PropelException
     */
    public function getRbStorages($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collRbStoragesPartial && !$this->isNew();
        if (null === $this->collRbStorages || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collRbStorages) {
                // return empty collection
                $this->initRbStorages();
            } else {
                $collRbStorages = RbStorageQuery::create(null, $criteria)
                    ->filterByOrgStructure($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collRbStoragesPartial && count($collRbStorages)) {
                      $this->initRbStorages(false);

                      foreach($collRbStorages as $obj) {
                        if (false == $this->collRbStorages->contains($obj)) {
                          $this->collRbStorages->append($obj);
                        }
                      }

                      $this->collRbStoragesPartial = true;
                    }

                    $collRbStorages->getInternalIterator()->rewind();
                    return $collRbStorages;
                }

                if($partial && $this->collRbStorages) {
                    foreach($this->collRbStorages as $obj) {
                        if($obj->isNew()) {
                            $collRbStorages[] = $obj;
                        }
                    }
                }

                $this->collRbStorages = $collRbStorages;
                $this->collRbStoragesPartial = false;
            }
        }

        return $this->collRbStorages;
    }

    /**
     * Sets a collection of RbStorage objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rbStorages A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return OrgStructure The current object (for fluent API support)
     */
    public function setRbStorages(PropelCollection $rbStorages, PropelPDO $con = null)
    {
        $rbStoragesToDelete = $this->getRbStorages(new Criteria(), $con)->diff($rbStorages);

        $this->rbStoragesScheduledForDeletion = unserialize(serialize($rbStoragesToDelete));

        foreach ($rbStoragesToDelete as $rbStorageRemoved) {
            $rbStorageRemoved->setOrgStructure(null);
        }

        $this->collRbStorages = null;
        foreach ($rbStorages as $rbStorage) {
            $this->addRbStorage($rbStorage);
        }

        $this->collRbStorages = $rbStorages;
        $this->collRbStoragesPartial = false;

        return $this;
    }

    /**
     * Returns the number of related RbStorage objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related RbStorage objects.
     * @throws PropelException
     */
    public function countRbStorages(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collRbStoragesPartial && !$this->isNew();
        if (null === $this->collRbStorages || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collRbStorages) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getRbStorages());
            }
            $query = RbStorageQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByOrgStructure($this)
                ->count($con);
        }

        return count($this->collRbStorages);
    }

    /**
     * Method called to associate a RbStorage object to this object
     * through the RbStorage foreign key attribute.
     *
     * @param    RbStorage $l RbStorage
     * @return OrgStructure The current object (for fluent API support)
     */
    public function addRbStorage(RbStorage $l)
    {
        if ($this->collRbStorages === null) {
            $this->initRbStorages();
            $this->collRbStoragesPartial = true;
        }
        if (!in_array($l, $this->collRbStorages->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddRbStorage($l);
        }

        return $this;
    }

    /**
     * @param	RbStorage $rbStorage The rbStorage object to add.
     */
    protected function doAddRbStorage($rbStorage)
    {
        $this->collRbStorages[]= $rbStorage;
        $rbStorage->setOrgStructure($this);
    }

    /**
     * @param	RbStorage $rbStorage The rbStorage object to remove.
     * @return OrgStructure The current object (for fluent API support)
     */
    public function removeRbStorage($rbStorage)
    {
        if ($this->getRbStorages()->contains($rbStorage)) {
            $this->collRbStorages->remove($this->collRbStorages->search($rbStorage));
            if (null === $this->rbStoragesScheduledForDeletion) {
                $this->rbStoragesScheduledForDeletion = clone $this->collRbStorages;
                $this->rbStoragesScheduledForDeletion->clear();
            }
            $this->rbStoragesScheduledForDeletion[]= $rbStorage;
            $rbStorage->setOrgStructure(null);
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
        $this->organisation_id = null;
        $this->code = null;
        $this->name = null;
        $this->parent_id = null;
        $this->type = null;
        $this->net_id = null;
        $this->isarea = null;
        $this->hashospitalbeds = null;
        $this->hasstocks = null;
        $this->infiscode = null;
        $this->infisinternalcode = null;
        $this->infisdeptypecode = null;
        $this->infistariffcode = null;
        $this->availableforexternal = null;
        $this->address = null;
        $this->inheriteventtypes = null;
        $this->inheritactiontypes = null;
        $this->inheritgaps = null;
        $this->uuid_id = null;
        $this->show = null;
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
            if ($this->collRbStorages) {
                foreach ($this->collRbStorages as $o) {
                    $o->clearAllReferences($deep);
                }
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collEvents instanceof PropelCollection) {
            $this->collEvents->clearIterator();
        }
        $this->collEvents = null;
        if ($this->collRbStorages instanceof PropelCollection) {
            $this->collRbStorages->clearIterator();
        }
        $this->collRbStorages = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(OrgStructurePeer::DEFAULT_STRING_FORMAT);
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
     * @return     OrgStructure The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = OrgStructurePeer::MODIFYDATETIME;

        return $this;
    }

}
