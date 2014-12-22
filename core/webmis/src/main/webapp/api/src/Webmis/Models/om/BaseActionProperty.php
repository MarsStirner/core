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
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyDate;
use Webmis\Models\ActionPropertyDateQuery;
use Webmis\Models\ActionPropertyDouble;
use Webmis\Models\ActionPropertyDoubleQuery;
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\ActionPropertyFDRecordQuery;
use Webmis\Models\ActionPropertyInteger;
use Webmis\Models\ActionPropertyIntegerQuery;
use Webmis\Models\ActionPropertyOrgStructure;
use Webmis\Models\ActionPropertyOrgStructureQuery;
use Webmis\Models\ActionPropertyPeer;
use Webmis\Models\ActionPropertyQuery;
use Webmis\Models\ActionPropertyString;
use Webmis\Models\ActionPropertyStringQuery;
use Webmis\Models\ActionPropertyTime;
use Webmis\Models\ActionPropertyTimeQuery;
use Webmis\Models\ActionPropertyType;
use Webmis\Models\ActionPropertyTypeQuery;
use Webmis\Models\ActionQuery;

/**
 * Base class that represents a row from the 'ActionProperty' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionProperty extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\ActionPropertyPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        ActionPropertyPeer
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
     * The value for the action_id field.
     * @var        int
     */
    protected $action_id;

    /**
     * The value for the type_id field.
     * @var        int
     */
    protected $type_id;

    /**
     * The value for the unit_id field.
     * @var        int
     */
    protected $unit_id;

    /**
     * The value for the norm field.
     * @var        string
     */
    protected $norm;

    /**
     * The value for the isassigned field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isassigned;

    /**
     * The value for the evaluation field.
     * @var        boolean
     */
    protected $evaluation;

    /**
     * The value for the version field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $version;

    /**
     * @var        Action
     */
    protected $aAction;

    /**
     * @var        ActionPropertyType
     */
    protected $aActionPropertyType;

    /**
     * @var        ActionPropertyString
     */
    protected $aActionPropertyString;

    /**
     * @var        ActionPropertyInteger
     */
    protected $aActionPropertyInteger;

    /**
     * @var        ActionPropertyDate
     */
    protected $aActionPropertyDate;

    /**
     * @var        ActionPropertyDouble
     */
    protected $aActionPropertyDouble;

    /**
     * @var        ActionPropertyOrgStructure
     */
    protected $aActionPropertyOrgStructure;

    /**
     * @var        ActionPropertyFDRecord
     */
    protected $aActionPropertyFDRecord;

    /**
     * @var        PropelObjectCollection|ActionPropertyTime[] Collection to store aggregation of ActionPropertyTime objects.
     */
    protected $collActionPropertyTimes;
    protected $collActionPropertyTimesPartial;

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
    protected $actionPropertyTimesScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->isassigned = false;
        $this->version = 0;
    }

    /**
     * Initializes internal state of BaseActionProperty object.
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
     * Get the [action_id] column value.
     *
     * @return int
     */
    public function getactionId()
    {
        return $this->action_id;
    }

    /**
     * Get the [type_id] column value.
     *
     * @return int
     */
    public function gettypeId()
    {
        return $this->type_id;
    }

    /**
     * Get the [unit_id] column value.
     *
     * @return int
     */
    public function getunitId()
    {
        return $this->unit_id;
    }

    /**
     * Get the [norm] column value.
     *
     * @return string
     */
    public function getnorm()
    {
        return $this->norm;
    }

    /**
     * Get the [isassigned] column value.
     *
     * @return boolean
     */
    public function getisAssigned()
    {
        return $this->isassigned;
    }

    /**
     * Get the [evaluation] column value.
     *
     * @return boolean
     */
    public function getevaluation()
    {
        return $this->evaluation;
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
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::ID;
        }

        if ($this->aActionPropertyString !== null && $this->aActionPropertyString->getid() !== $v) {
            $this->aActionPropertyString = null;
        }

        if ($this->aActionPropertyInteger !== null && $this->aActionPropertyInteger->getid() !== $v) {
            $this->aActionPropertyInteger = null;
        }

        if ($this->aActionPropertyDate !== null && $this->aActionPropertyDate->getid() !== $v) {
            $this->aActionPropertyDate = null;
        }

        if ($this->aActionPropertyDouble !== null && $this->aActionPropertyDouble->getid() !== $v) {
            $this->aActionPropertyDouble = null;
        }

        if ($this->aActionPropertyOrgStructure !== null && $this->aActionPropertyOrgStructure->getid() !== $v) {
            $this->aActionPropertyOrgStructure = null;
        }

        if ($this->aActionPropertyFDRecord !== null && $this->aActionPropertyFDRecord->getId() !== $v) {
            $this->aActionPropertyFDRecord = null;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionPropertyPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = ActionPropertyPeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::MODIFYPERSON_ID;
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
     * @return ActionProperty The current object (for fluent API support)
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
            $this->modifiedColumns[] = ActionPropertyPeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [action_id] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setactionId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->action_id !== $v) {
            $this->action_id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::ACTION_ID;
        }

        if ($this->aAction !== null && $this->aAction->getid() !== $v) {
            $this->aAction = null;
        }


        return $this;
    } // setactionId()

    /**
     * Set the value of [type_id] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function settypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->type_id !== $v) {
            $this->type_id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::TYPE_ID;
        }

        if ($this->aActionPropertyType !== null && $this->aActionPropertyType->getid() !== $v) {
            $this->aActionPropertyType = null;
        }


        return $this;
    } // settypeId()

    /**
     * Set the value of [unit_id] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setunitId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->unit_id !== $v) {
            $this->unit_id = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::UNIT_ID;
        }


        return $this;
    } // setunitId()

    /**
     * Set the value of [norm] column.
     *
     * @param string $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setnorm($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->norm !== $v) {
            $this->norm = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::NORM;
        }


        return $this;
    } // setnorm()

    /**
     * Sets the value of the [isassigned] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setisAssigned($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isassigned !== $v) {
            $this->isassigned = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::ISASSIGNED;
        }


        return $this;
    } // setisAssigned()

    /**
     * Sets the value of the [evaluation] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setevaluation($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->evaluation !== $v) {
            $this->evaluation = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::EVALUATION;
        }


        return $this;
    } // setevaluation()

    /**
     * Set the value of [version] column.
     *
     * @param int $v new value
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setversion($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->version !== $v) {
            $this->version = $v;
            $this->modifiedColumns[] = ActionPropertyPeer::VERSION;
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

            if ($this->isassigned !== false) {
                return false;
            }

            if ($this->version !== 0) {
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
            $this->action_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->type_id = ($row[$startcol + 7] !== null) ? (int) $row[$startcol + 7] : null;
            $this->unit_id = ($row[$startcol + 8] !== null) ? (int) $row[$startcol + 8] : null;
            $this->norm = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->isassigned = ($row[$startcol + 10] !== null) ? (boolean) $row[$startcol + 10] : null;
            $this->evaluation = ($row[$startcol + 11] !== null) ? (boolean) $row[$startcol + 11] : null;
            $this->version = ($row[$startcol + 12] !== null) ? (int) $row[$startcol + 12] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 13; // 13 = ActionPropertyPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating ActionProperty object", $e);
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

        if ($this->aActionPropertyString !== null && $this->id !== $this->aActionPropertyString->getid()) {
            $this->aActionPropertyString = null;
        }
        if ($this->aActionPropertyInteger !== null && $this->id !== $this->aActionPropertyInteger->getid()) {
            $this->aActionPropertyInteger = null;
        }
        if ($this->aActionPropertyDate !== null && $this->id !== $this->aActionPropertyDate->getid()) {
            $this->aActionPropertyDate = null;
        }
        if ($this->aActionPropertyDouble !== null && $this->id !== $this->aActionPropertyDouble->getid()) {
            $this->aActionPropertyDouble = null;
        }
        if ($this->aActionPropertyOrgStructure !== null && $this->id !== $this->aActionPropertyOrgStructure->getid()) {
            $this->aActionPropertyOrgStructure = null;
        }
        if ($this->aActionPropertyFDRecord !== null && $this->id !== $this->aActionPropertyFDRecord->getId()) {
            $this->aActionPropertyFDRecord = null;
        }
        if ($this->aAction !== null && $this->action_id !== $this->aAction->getid()) {
            $this->aAction = null;
        }
        if ($this->aActionPropertyType !== null && $this->type_id !== $this->aActionPropertyType->getid()) {
            $this->aActionPropertyType = null;
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
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = ActionPropertyPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aAction = null;
            $this->aActionPropertyType = null;
            $this->aActionPropertyString = null;
            $this->aActionPropertyInteger = null;
            $this->aActionPropertyDate = null;
            $this->aActionPropertyDouble = null;
            $this->aActionPropertyOrgStructure = null;
            $this->aActionPropertyFDRecord = null;
            $this->collActionPropertyTimes = null;

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
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = ActionPropertyQuery::create()
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
            $con = Propel::getConnection(ActionPropertyPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(ActionPropertyPeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(ActionPropertyPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(ActionPropertyPeer::MODIFYDATETIME)) {
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
                ActionPropertyPeer::addInstanceToPool($this);
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

            if ($this->aAction !== null) {
                if ($this->aAction->isModified() || $this->aAction->isNew()) {
                    $affectedRows += $this->aAction->save($con);
                }
                $this->setAction($this->aAction);
            }

            if ($this->aActionPropertyType !== null) {
                if ($this->aActionPropertyType->isModified() || $this->aActionPropertyType->isNew()) {
                    $affectedRows += $this->aActionPropertyType->save($con);
                }
                $this->setActionPropertyType($this->aActionPropertyType);
            }

            if ($this->aActionPropertyString !== null) {
                if ($this->aActionPropertyString->isModified() || $this->aActionPropertyString->isNew()) {
                    $affectedRows += $this->aActionPropertyString->save($con);
                }
                $this->setActionPropertyString($this->aActionPropertyString);
            }

            if ($this->aActionPropertyInteger !== null) {
                if ($this->aActionPropertyInteger->isModified() || $this->aActionPropertyInteger->isNew()) {
                    $affectedRows += $this->aActionPropertyInteger->save($con);
                }
                $this->setActionPropertyInteger($this->aActionPropertyInteger);
            }

            if ($this->aActionPropertyDate !== null) {
                if ($this->aActionPropertyDate->isModified() || $this->aActionPropertyDate->isNew()) {
                    $affectedRows += $this->aActionPropertyDate->save($con);
                }
                $this->setActionPropertyDate($this->aActionPropertyDate);
            }

            if ($this->aActionPropertyDouble !== null) {
                if ($this->aActionPropertyDouble->isModified() || $this->aActionPropertyDouble->isNew()) {
                    $affectedRows += $this->aActionPropertyDouble->save($con);
                }
                $this->setActionPropertyDouble($this->aActionPropertyDouble);
            }

            if ($this->aActionPropertyOrgStructure !== null) {
                if ($this->aActionPropertyOrgStructure->isModified() || $this->aActionPropertyOrgStructure->isNew()) {
                    $affectedRows += $this->aActionPropertyOrgStructure->save($con);
                }
                $this->setActionPropertyOrgStructure($this->aActionPropertyOrgStructure);
            }

            if ($this->aActionPropertyFDRecord !== null) {
                if ($this->aActionPropertyFDRecord->isModified() || $this->aActionPropertyFDRecord->isNew()) {
                    $affectedRows += $this->aActionPropertyFDRecord->save($con);
                }
                $this->setActionPropertyFDRecord($this->aActionPropertyFDRecord);
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

            if ($this->actionPropertyTimesScheduledForDeletion !== null) {
                if (!$this->actionPropertyTimesScheduledForDeletion->isEmpty()) {
                    ActionPropertyTimeQuery::create()
                        ->filterByPrimaryKeys($this->actionPropertyTimesScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionPropertyTimesScheduledForDeletion = null;
                }
            }

            if ($this->collActionPropertyTimes !== null) {
                foreach ($this->collActionPropertyTimes as $referrerFK) {
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

        $this->modifiedColumns[] = ActionPropertyPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . ActionPropertyPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(ActionPropertyPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::ACTION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`action_id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::TYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`type_id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::UNIT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`unit_id`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::NORM)) {
            $modifiedColumns[':p' . $index++]  = '`norm`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::ISASSIGNED)) {
            $modifiedColumns[':p' . $index++]  = '`isAssigned`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::EVALUATION)) {
            $modifiedColumns[':p' . $index++]  = '`evaluation`';
        }
        if ($this->isColumnModified(ActionPropertyPeer::VERSION)) {
            $modifiedColumns[':p' . $index++]  = '`version`';
        }

        $sql = sprintf(
            'INSERT INTO `ActionProperty` (%s) VALUES (%s)',
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
                    case '`action_id`':
                        $stmt->bindValue($identifier, $this->action_id, PDO::PARAM_INT);
                        break;
                    case '`type_id`':
                        $stmt->bindValue($identifier, $this->type_id, PDO::PARAM_INT);
                        break;
                    case '`unit_id`':
                        $stmt->bindValue($identifier, $this->unit_id, PDO::PARAM_INT);
                        break;
                    case '`norm`':
                        $stmt->bindValue($identifier, $this->norm, PDO::PARAM_STR);
                        break;
                    case '`isAssigned`':
                        $stmt->bindValue($identifier, (int) $this->isassigned, PDO::PARAM_INT);
                        break;
                    case '`evaluation`':
                        $stmt->bindValue($identifier, (int) $this->evaluation, PDO::PARAM_INT);
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

            if ($this->aAction !== null) {
                if (!$this->aAction->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aAction->getValidationFailures());
                }
            }

            if ($this->aActionPropertyType !== null) {
                if (!$this->aActionPropertyType->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyType->getValidationFailures());
                }
            }

            if ($this->aActionPropertyString !== null) {
                if (!$this->aActionPropertyString->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyString->getValidationFailures());
                }
            }

            if ($this->aActionPropertyInteger !== null) {
                if (!$this->aActionPropertyInteger->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyInteger->getValidationFailures());
                }
            }

            if ($this->aActionPropertyDate !== null) {
                if (!$this->aActionPropertyDate->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyDate->getValidationFailures());
                }
            }

            if ($this->aActionPropertyDouble !== null) {
                if (!$this->aActionPropertyDouble->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyDouble->getValidationFailures());
                }
            }

            if ($this->aActionPropertyOrgStructure !== null) {
                if (!$this->aActionPropertyOrgStructure->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyOrgStructure->getValidationFailures());
                }
            }

            if ($this->aActionPropertyFDRecord !== null) {
                if (!$this->aActionPropertyFDRecord->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionPropertyFDRecord->getValidationFailures());
                }
            }


            if (($retval = ActionPropertyPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionPropertyTimes !== null) {
                    foreach ($this->collActionPropertyTimes as $referrerFK) {
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
        $pos = ActionPropertyPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getactionId();
                break;
            case 7:
                return $this->gettypeId();
                break;
            case 8:
                return $this->getunitId();
                break;
            case 9:
                return $this->getnorm();
                break;
            case 10:
                return $this->getisAssigned();
                break;
            case 11:
                return $this->getevaluation();
                break;
            case 12:
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
        if (isset($alreadyDumpedObjects['ActionProperty'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['ActionProperty'][$this->getPrimaryKey()] = true;
        $keys = ActionPropertyPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getactionId(),
            $keys[7] => $this->gettypeId(),
            $keys[8] => $this->getunitId(),
            $keys[9] => $this->getnorm(),
            $keys[10] => $this->getisAssigned(),
            $keys[11] => $this->getevaluation(),
            $keys[12] => $this->getversion(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aAction) {
                $result['Action'] = $this->aAction->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyType) {
                $result['ActionPropertyType'] = $this->aActionPropertyType->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyString) {
                $result['ActionPropertyString'] = $this->aActionPropertyString->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyInteger) {
                $result['ActionPropertyInteger'] = $this->aActionPropertyInteger->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyDate) {
                $result['ActionPropertyDate'] = $this->aActionPropertyDate->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyDouble) {
                $result['ActionPropertyDouble'] = $this->aActionPropertyDouble->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyOrgStructure) {
                $result['ActionPropertyOrgStructure'] = $this->aActionPropertyOrgStructure->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aActionPropertyFDRecord) {
                $result['ActionPropertyFDRecord'] = $this->aActionPropertyFDRecord->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActionPropertyTimes) {
                $result['ActionPropertyTimes'] = $this->collActionPropertyTimes->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = ActionPropertyPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setactionId($value);
                break;
            case 7:
                $this->settypeId($value);
                break;
            case 8:
                $this->setunitId($value);
                break;
            case 9:
                $this->setnorm($value);
                break;
            case 10:
                $this->setisAssigned($value);
                break;
            case 11:
                $this->setevaluation($value);
                break;
            case 12:
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
        $keys = ActionPropertyPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setactionId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->settypeId($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setunitId($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setnorm($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setisAssigned($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setevaluation($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setversion($arr[$keys[12]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);

        if ($this->isColumnModified(ActionPropertyPeer::ID)) $criteria->add(ActionPropertyPeer::ID, $this->id);
        if ($this->isColumnModified(ActionPropertyPeer::CREATEDATETIME)) $criteria->add(ActionPropertyPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(ActionPropertyPeer::CREATEPERSON_ID)) $criteria->add(ActionPropertyPeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(ActionPropertyPeer::MODIFYDATETIME)) $criteria->add(ActionPropertyPeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(ActionPropertyPeer::MODIFYPERSON_ID)) $criteria->add(ActionPropertyPeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(ActionPropertyPeer::DELETED)) $criteria->add(ActionPropertyPeer::DELETED, $this->deleted);
        if ($this->isColumnModified(ActionPropertyPeer::ACTION_ID)) $criteria->add(ActionPropertyPeer::ACTION_ID, $this->action_id);
        if ($this->isColumnModified(ActionPropertyPeer::TYPE_ID)) $criteria->add(ActionPropertyPeer::TYPE_ID, $this->type_id);
        if ($this->isColumnModified(ActionPropertyPeer::UNIT_ID)) $criteria->add(ActionPropertyPeer::UNIT_ID, $this->unit_id);
        if ($this->isColumnModified(ActionPropertyPeer::NORM)) $criteria->add(ActionPropertyPeer::NORM, $this->norm);
        if ($this->isColumnModified(ActionPropertyPeer::ISASSIGNED)) $criteria->add(ActionPropertyPeer::ISASSIGNED, $this->isassigned);
        if ($this->isColumnModified(ActionPropertyPeer::EVALUATION)) $criteria->add(ActionPropertyPeer::EVALUATION, $this->evaluation);
        if ($this->isColumnModified(ActionPropertyPeer::VERSION)) $criteria->add(ActionPropertyPeer::VERSION, $this->version);

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
        $criteria = new Criteria(ActionPropertyPeer::DATABASE_NAME);
        $criteria->add(ActionPropertyPeer::ID, $this->id);

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
     * @param object $copyObj An object of ActionProperty (or compatible) type.
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
        $copyObj->setactionId($this->getactionId());
        $copyObj->settypeId($this->gettypeId());
        $copyObj->setunitId($this->getunitId());
        $copyObj->setnorm($this->getnorm());
        $copyObj->setisAssigned($this->getisAssigned());
        $copyObj->setevaluation($this->getevaluation());
        $copyObj->setversion($this->getversion());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActionPropertyTimes() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionPropertyTime($relObj->copy($deepCopy));
                }
            }

            $relObj = $this->getActionPropertyString();
            if ($relObj) {
                $copyObj->setActionPropertyString($relObj->copy($deepCopy));
            }

            $relObj = $this->getActionPropertyInteger();
            if ($relObj) {
                $copyObj->setActionPropertyInteger($relObj->copy($deepCopy));
            }

            $relObj = $this->getActionPropertyDate();
            if ($relObj) {
                $copyObj->setActionPropertyDate($relObj->copy($deepCopy));
            }

            $relObj = $this->getActionPropertyDouble();
            if ($relObj) {
                $copyObj->setActionPropertyDouble($relObj->copy($deepCopy));
            }

            $relObj = $this->getActionPropertyOrgStructure();
            if ($relObj) {
                $copyObj->setActionPropertyOrgStructure($relObj->copy($deepCopy));
            }

            $relObj = $this->getActionPropertyFDRecord();
            if ($relObj) {
                $copyObj->setActionPropertyFDRecord($relObj->copy($deepCopy));
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
     * @return ActionProperty Clone of current object.
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
     * @return ActionPropertyPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new ActionPropertyPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a Action object.
     *
     * @param             Action $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setAction(Action $v = null)
    {
        if ($v === null) {
            $this->setactionId(NULL);
        } else {
            $this->setactionId($v->getid());
        }

        $this->aAction = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the Action object, it will not be re-added.
        if ($v !== null) {
            $v->addActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated Action object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return Action The associated Action object.
     * @throws PropelException
     */
    public function getAction(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aAction === null && ($this->action_id !== null) && $doQuery) {
            $this->aAction = ActionQuery::create()->findPk($this->action_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aAction->addActionPropertys($this);
             */
        }

        return $this->aAction;
    }

    /**
     * Declares an association between this object and a ActionPropertyType object.
     *
     * @param             ActionPropertyType $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyType(ActionPropertyType $v = null)
    {
        if ($v === null) {
            $this->settypeId(NULL);
        } else {
            $this->settypeId($v->getid());
        }

        $this->aActionPropertyType = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the ActionPropertyType object, it will not be re-added.
        if ($v !== null) {
            $v->addActionProperty($this);
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
    public function getActionPropertyType(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyType === null && ($this->type_id !== null) && $doQuery) {
            $this->aActionPropertyType = ActionPropertyTypeQuery::create()->findPk($this->type_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aActionPropertyType->addActionPropertys($this);
             */
        }

        return $this->aActionPropertyType;
    }

    /**
     * Declares an association between this object and a ActionPropertyString object.
     *
     * @param             ActionPropertyString $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyString(ActionPropertyString $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getid());
        }

        $this->aActionPropertyString = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyString object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyString The associated ActionPropertyString object.
     * @throws PropelException
     */
    public function getActionPropertyString(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyString === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyString = ActionPropertyStringQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyString->setActionProperty($this);
        }

        return $this->aActionPropertyString;
    }

    /**
     * Declares an association between this object and a ActionPropertyInteger object.
     *
     * @param             ActionPropertyInteger $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyInteger(ActionPropertyInteger $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getid());
        }

        $this->aActionPropertyInteger = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyInteger object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyInteger The associated ActionPropertyInteger object.
     * @throws PropelException
     */
    public function getActionPropertyInteger(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyInteger === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyInteger = ActionPropertyIntegerQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyInteger->setActionProperty($this);
        }

        return $this->aActionPropertyInteger;
    }

    /**
     * Declares an association between this object and a ActionPropertyDate object.
     *
     * @param             ActionPropertyDate $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyDate(ActionPropertyDate $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getid());
        }

        $this->aActionPropertyDate = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyDate object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyDate The associated ActionPropertyDate object.
     * @throws PropelException
     */
    public function getActionPropertyDate(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyDate === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyDate = ActionPropertyDateQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyDate->setActionProperty($this);
        }

        return $this->aActionPropertyDate;
    }

    /**
     * Declares an association between this object and a ActionPropertyDouble object.
     *
     * @param             ActionPropertyDouble $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyDouble(ActionPropertyDouble $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getid());
        }

        $this->aActionPropertyDouble = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyDouble object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyDouble The associated ActionPropertyDouble object.
     * @throws PropelException
     */
    public function getActionPropertyDouble(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyDouble === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyDouble = ActionPropertyDoubleQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyDouble->setActionProperty($this);
        }

        return $this->aActionPropertyDouble;
    }

    /**
     * Declares an association between this object and a ActionPropertyOrgStructure object.
     *
     * @param             ActionPropertyOrgStructure $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyOrgStructure(ActionPropertyOrgStructure $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getid());
        }

        $this->aActionPropertyOrgStructure = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyOrgStructure object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyOrgStructure The associated ActionPropertyOrgStructure object.
     * @throws PropelException
     */
    public function getActionPropertyOrgStructure(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyOrgStructure === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyOrgStructure = ActionPropertyOrgStructureQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyOrgStructure->setActionProperty($this);
        }

        return $this->aActionPropertyOrgStructure;
    }

    /**
     * Declares an association between this object and a ActionPropertyFDRecord object.
     *
     * @param             ActionPropertyFDRecord $v
     * @return ActionProperty The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionPropertyFDRecord(ActionPropertyFDRecord $v = null)
    {
        if ($v === null) {
            $this->setid(NULL);
        } else {
            $this->setid($v->getId());
        }

        $this->aActionPropertyFDRecord = $v;

        // Add binding for other direction of this 1:1 relationship.
        if ($v !== null) {
            $v->setActionProperty($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionPropertyFDRecord object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionPropertyFDRecord The associated ActionPropertyFDRecord object.
     * @throws PropelException
     */
    public function getActionPropertyFDRecord(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyFDRecord === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyFDRecord = ActionPropertyFDRecordQuery::create()->findPk($this->id, $con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            $this->aActionPropertyFDRecord->setActionProperty($this);
        }

        return $this->aActionPropertyFDRecord;
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
        if ('ActionPropertyTime' == $relationName) {
            $this->initActionPropertyTimes();
        }
    }

    /**
     * Clears out the collActionPropertyTimes collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return ActionProperty The current object (for fluent API support)
     * @see        addActionPropertyTimes()
     */
    public function clearActionPropertyTimes()
    {
        $this->collActionPropertyTimes = null; // important to set this to null since that means it is uninitialized
        $this->collActionPropertyTimesPartial = null;

        return $this;
    }

    /**
     * reset is the collActionPropertyTimes collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionPropertyTimes($v = true)
    {
        $this->collActionPropertyTimesPartial = $v;
    }

    /**
     * Initializes the collActionPropertyTimes collection.
     *
     * By default this just sets the collActionPropertyTimes collection to an empty array (like clearcollActionPropertyTimes());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionPropertyTimes($overrideExisting = true)
    {
        if (null !== $this->collActionPropertyTimes && !$overrideExisting) {
            return;
        }
        $this->collActionPropertyTimes = new PropelObjectCollection();
        $this->collActionPropertyTimes->setModel('ActionPropertyTime');
    }

    /**
     * Gets an array of ActionPropertyTime objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this ActionProperty is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ActionPropertyTime[] List of ActionPropertyTime objects
     * @throws PropelException
     */
    public function getActionPropertyTimes($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyTimesPartial && !$this->isNew();
        if (null === $this->collActionPropertyTimes || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyTimes) {
                // return empty collection
                $this->initActionPropertyTimes();
            } else {
                $collActionPropertyTimes = ActionPropertyTimeQuery::create(null, $criteria)
                    ->filterByActionProperty($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionPropertyTimesPartial && count($collActionPropertyTimes)) {
                      $this->initActionPropertyTimes(false);

                      foreach($collActionPropertyTimes as $obj) {
                        if (false == $this->collActionPropertyTimes->contains($obj)) {
                          $this->collActionPropertyTimes->append($obj);
                        }
                      }

                      $this->collActionPropertyTimesPartial = true;
                    }

                    $collActionPropertyTimes->getInternalIterator()->rewind();
                    return $collActionPropertyTimes;
                }

                if($partial && $this->collActionPropertyTimes) {
                    foreach($this->collActionPropertyTimes as $obj) {
                        if($obj->isNew()) {
                            $collActionPropertyTimes[] = $obj;
                        }
                    }
                }

                $this->collActionPropertyTimes = $collActionPropertyTimes;
                $this->collActionPropertyTimesPartial = false;
            }
        }

        return $this->collActionPropertyTimes;
    }

    /**
     * Sets a collection of ActionPropertyTime objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionPropertyTimes A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return ActionProperty The current object (for fluent API support)
     */
    public function setActionPropertyTimes(PropelCollection $actionPropertyTimes, PropelPDO $con = null)
    {
        $actionPropertyTimesToDelete = $this->getActionPropertyTimes(new Criteria(), $con)->diff($actionPropertyTimes);

        $this->actionPropertyTimesScheduledForDeletion = unserialize(serialize($actionPropertyTimesToDelete));

        foreach ($actionPropertyTimesToDelete as $actionPropertyTimeRemoved) {
            $actionPropertyTimeRemoved->setActionProperty(null);
        }

        $this->collActionPropertyTimes = null;
        foreach ($actionPropertyTimes as $actionPropertyTime) {
            $this->addActionPropertyTime($actionPropertyTime);
        }

        $this->collActionPropertyTimes = $actionPropertyTimes;
        $this->collActionPropertyTimesPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ActionPropertyTime objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ActionPropertyTime objects.
     * @throws PropelException
     */
    public function countActionPropertyTimes(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyTimesPartial && !$this->isNew();
        if (null === $this->collActionPropertyTimes || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyTimes) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionPropertyTimes());
            }
            $query = ActionPropertyTimeQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByActionProperty($this)
                ->count($con);
        }

        return count($this->collActionPropertyTimes);
    }

    /**
     * Method called to associate a ActionPropertyTime object to this object
     * through the ActionPropertyTime foreign key attribute.
     *
     * @param    ActionPropertyTime $l ActionPropertyTime
     * @return ActionProperty The current object (for fluent API support)
     */
    public function addActionPropertyTime(ActionPropertyTime $l)
    {
        if ($this->collActionPropertyTimes === null) {
            $this->initActionPropertyTimes();
            $this->collActionPropertyTimesPartial = true;
        }
        if (!in_array($l, $this->collActionPropertyTimes->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionPropertyTime($l);
        }

        return $this;
    }

    /**
     * @param	ActionPropertyTime $actionPropertyTime The actionPropertyTime object to add.
     */
    protected function doAddActionPropertyTime($actionPropertyTime)
    {
        $this->collActionPropertyTimes[]= $actionPropertyTime;
        $actionPropertyTime->setActionProperty($this);
    }

    /**
     * @param	ActionPropertyTime $actionPropertyTime The actionPropertyTime object to remove.
     * @return ActionProperty The current object (for fluent API support)
     */
    public function removeActionPropertyTime($actionPropertyTime)
    {
        if ($this->getActionPropertyTimes()->contains($actionPropertyTime)) {
            $this->collActionPropertyTimes->remove($this->collActionPropertyTimes->search($actionPropertyTime));
            if (null === $this->actionPropertyTimesScheduledForDeletion) {
                $this->actionPropertyTimesScheduledForDeletion = clone $this->collActionPropertyTimes;
                $this->actionPropertyTimesScheduledForDeletion->clear();
            }
            $this->actionPropertyTimesScheduledForDeletion[]= clone $actionPropertyTime;
            $actionPropertyTime->setActionProperty(null);
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
        $this->action_id = null;
        $this->type_id = null;
        $this->unit_id = null;
        $this->norm = null;
        $this->isassigned = null;
        $this->evaluation = null;
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
            if ($this->collActionPropertyTimes) {
                foreach ($this->collActionPropertyTimes as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aAction instanceof Persistent) {
              $this->aAction->clearAllReferences($deep);
            }
            if ($this->aActionPropertyType instanceof Persistent) {
              $this->aActionPropertyType->clearAllReferences($deep);
            }
            if ($this->aActionPropertyString instanceof Persistent) {
              $this->aActionPropertyString->clearAllReferences($deep);
            }
            if ($this->aActionPropertyInteger instanceof Persistent) {
              $this->aActionPropertyInteger->clearAllReferences($deep);
            }
            if ($this->aActionPropertyDate instanceof Persistent) {
              $this->aActionPropertyDate->clearAllReferences($deep);
            }
            if ($this->aActionPropertyDouble instanceof Persistent) {
              $this->aActionPropertyDouble->clearAllReferences($deep);
            }
            if ($this->aActionPropertyOrgStructure instanceof Persistent) {
              $this->aActionPropertyOrgStructure->clearAllReferences($deep);
            }
            if ($this->aActionPropertyFDRecord instanceof Persistent) {
              $this->aActionPropertyFDRecord->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionPropertyTimes instanceof PropelCollection) {
            $this->collActionPropertyTimes->clearIterator();
        }
        $this->collActionPropertyTimes = null;
        $this->aAction = null;
        $this->aActionPropertyType = null;
        $this->aActionPropertyString = null;
        $this->aActionPropertyInteger = null;
        $this->aActionPropertyDate = null;
        $this->aActionPropertyDouble = null;
        $this->aActionPropertyOrgStructure = null;
        $this->aActionPropertyFDRecord = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(ActionPropertyPeer::DEFAULT_STRING_FORMAT);
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
     * @return     ActionProperty The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = ActionPropertyPeer::MODIFYDATETIME;

        return $this;
    }

}
