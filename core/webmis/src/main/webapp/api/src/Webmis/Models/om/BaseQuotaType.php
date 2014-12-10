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
use Webmis\Models\ClientQuoting;
use Webmis\Models\ClientQuotingQuery;
use Webmis\Models\MkbQuotaTypePacientModel;
use Webmis\Models\MkbQuotaTypePacientModelQuery;
use Webmis\Models\QuotaType;
use Webmis\Models\QuotaTypePeer;
use Webmis\Models\QuotaTypeQuery;
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbPacientModelQuery;

/**
 * Base class that represents a row from the 'QuotaType' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseQuotaType extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\QuotaTypePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        QuotaTypePeer
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
     * The value for the group_code field.
     * @var        string
     */
    protected $group_code;

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
     * The value for the teenolder field.
     * @var        boolean
     */
    protected $teenolder;

    /**
     * @var        PropelObjectCollection|ClientQuoting[] Collection to store aggregation of ClientQuoting objects.
     */
    protected $collClientQuotings;
    protected $collClientQuotingsPartial;

    /**
     * @var        PropelObjectCollection|MkbQuotaTypePacientModel[] Collection to store aggregation of MkbQuotaTypePacientModel objects.
     */
    protected $collMkbQuotaTypePacientModels;
    protected $collMkbQuotaTypePacientModelsPartial;

    /**
     * @var        PropelObjectCollection|RbPacientModel[] Collection to store aggregation of RbPacientModel objects.
     */
    protected $collRbPacientModels;
    protected $collRbPacientModelsPartial;

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
    protected $clientQuotingsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $mkbQuotaTypePacientModelsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $rbPacientModelsScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
    }

    /**
     * Initializes internal state of BaseQuotaType object.
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
     * Get the [group_code] column value.
     *
     * @return string
     */
    public function getgroupCode()
    {
        return $this->group_code;
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
     * Get the [teenolder] column value.
     *
     * @return boolean
     */
    public function getteenOlder()
    {
        return $this->teenolder;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = QuotaTypePeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return QuotaType The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = QuotaTypePeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = QuotaTypePeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return QuotaType The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = QuotaTypePeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = QuotaTypePeer::MODIFYPERSON_ID;
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
     * @return QuotaType The current object (for fluent API support)
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
            $this->modifiedColumns[] = QuotaTypePeer::DELETED;
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
     * @return QuotaType The current object (for fluent API support)
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
            $this->modifiedColumns[] = QuotaTypePeer::CLAZZ;
        }


        return $this;
    } // setClass()

    /**
     * Set the value of [group_code] column.
     *
     * @param string $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setgroupCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->group_code !== $v) {
            $this->group_code = $v;
            $this->modifiedColumns[] = QuotaTypePeer::GROUP_CODE;
        }


        return $this;
    } // setgroupCode()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = QuotaTypePeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = QuotaTypePeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Sets the value of the [teenolder] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return QuotaType The current object (for fluent API support)
     */
    public function setteenOlder($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->teenolder !== $v) {
            $this->teenolder = $v;
            $this->modifiedColumns[] = QuotaTypePeer::TEENOLDER;
        }


        return $this;
    } // setteenOlder()

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
            $this->group_code = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->code = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->name = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->teenolder = ($row[$startcol + 10] !== null) ? (boolean) $row[$startcol + 10] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 11; // 11 = QuotaTypePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating QuotaType object", $e);
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
            $con = Propel::getConnection(QuotaTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = QuotaTypePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->collClientQuotings = null;

            $this->collMkbQuotaTypePacientModels = null;

            $this->collRbPacientModels = null;

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
            $con = Propel::getConnection(QuotaTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = QuotaTypeQuery::create()
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
            $con = Propel::getConnection(QuotaTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(QuotaTypePeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(QuotaTypePeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(QuotaTypePeer::MODIFYDATETIME)) {
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
                QuotaTypePeer::addInstanceToPool($this);
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

            if ($this->clientQuotingsScheduledForDeletion !== null) {
                if (!$this->clientQuotingsScheduledForDeletion->isEmpty()) {
                    foreach ($this->clientQuotingsScheduledForDeletion as $clientQuoting) {
                        // need to save related object because we set the relation to null
                        $clientQuoting->save($con);
                    }
                    $this->clientQuotingsScheduledForDeletion = null;
                }
            }

            if ($this->collClientQuotings !== null) {
                foreach ($this->collClientQuotings as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->mkbQuotaTypePacientModelsScheduledForDeletion !== null) {
                if (!$this->mkbQuotaTypePacientModelsScheduledForDeletion->isEmpty()) {
                    MkbQuotaTypePacientModelQuery::create()
                        ->filterByPrimaryKeys($this->mkbQuotaTypePacientModelsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->mkbQuotaTypePacientModelsScheduledForDeletion = null;
                }
            }

            if ($this->collMkbQuotaTypePacientModels !== null) {
                foreach ($this->collMkbQuotaTypePacientModels as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->rbPacientModelsScheduledForDeletion !== null) {
                if (!$this->rbPacientModelsScheduledForDeletion->isEmpty()) {
                    RbPacientModelQuery::create()
                        ->filterByPrimaryKeys($this->rbPacientModelsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->rbPacientModelsScheduledForDeletion = null;
                }
            }

            if ($this->collRbPacientModels !== null) {
                foreach ($this->collRbPacientModels as $referrerFK) {
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

        $this->modifiedColumns[] = QuotaTypePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . QuotaTypePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(QuotaTypePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(QuotaTypePeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(QuotaTypePeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(QuotaTypePeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(QuotaTypePeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(QuotaTypePeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(QuotaTypePeer::CLAZZ)) {
            $modifiedColumns[':p' . $index++]  = '`class`';
        }
        if ($this->isColumnModified(QuotaTypePeer::GROUP_CODE)) {
            $modifiedColumns[':p' . $index++]  = '`group_code`';
        }
        if ($this->isColumnModified(QuotaTypePeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(QuotaTypePeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(QuotaTypePeer::TEENOLDER)) {
            $modifiedColumns[':p' . $index++]  = '`teenOlder`';
        }

        $sql = sprintf(
            'INSERT INTO `QuotaType` (%s) VALUES (%s)',
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
                    case '`group_code`':
                        $stmt->bindValue($identifier, $this->group_code, PDO::PARAM_STR);
                        break;
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`teenOlder`':
                        $stmt->bindValue($identifier, (int) $this->teenolder, PDO::PARAM_INT);
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


            if (($retval = QuotaTypePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collClientQuotings !== null) {
                    foreach ($this->collClientQuotings as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collMkbQuotaTypePacientModels !== null) {
                    foreach ($this->collMkbQuotaTypePacientModels as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collRbPacientModels !== null) {
                    foreach ($this->collRbPacientModels as $referrerFK) {
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
        $pos = QuotaTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getgroupCode();
                break;
            case 8:
                return $this->getcode();
                break;
            case 9:
                return $this->getname();
                break;
            case 10:
                return $this->getteenOlder();
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
        if (isset($alreadyDumpedObjects['QuotaType'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['QuotaType'][$this->getPrimaryKey()] = true;
        $keys = QuotaTypePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getClass(),
            $keys[7] => $this->getgroupCode(),
            $keys[8] => $this->getcode(),
            $keys[9] => $this->getname(),
            $keys[10] => $this->getteenOlder(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->collClientQuotings) {
                $result['ClientQuotings'] = $this->collClientQuotings->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collMkbQuotaTypePacientModels) {
                $result['MkbQuotaTypePacientModels'] = $this->collMkbQuotaTypePacientModels->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collRbPacientModels) {
                $result['RbPacientModels'] = $this->collRbPacientModels->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = QuotaTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setgroupCode($value);
                break;
            case 8:
                $this->setcode($value);
                break;
            case 9:
                $this->setname($value);
                break;
            case 10:
                $this->setteenOlder($value);
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
        $keys = QuotaTypePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setClass($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setgroupCode($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setcode($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setname($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setteenOlder($arr[$keys[10]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(QuotaTypePeer::DATABASE_NAME);

        if ($this->isColumnModified(QuotaTypePeer::ID)) $criteria->add(QuotaTypePeer::ID, $this->id);
        if ($this->isColumnModified(QuotaTypePeer::CREATEDATETIME)) $criteria->add(QuotaTypePeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(QuotaTypePeer::CREATEPERSON_ID)) $criteria->add(QuotaTypePeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(QuotaTypePeer::MODIFYDATETIME)) $criteria->add(QuotaTypePeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(QuotaTypePeer::MODIFYPERSON_ID)) $criteria->add(QuotaTypePeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(QuotaTypePeer::DELETED)) $criteria->add(QuotaTypePeer::DELETED, $this->deleted);
        if ($this->isColumnModified(QuotaTypePeer::CLAZZ)) $criteria->add(QuotaTypePeer::CLAZZ, $this->class);
        if ($this->isColumnModified(QuotaTypePeer::GROUP_CODE)) $criteria->add(QuotaTypePeer::GROUP_CODE, $this->group_code);
        if ($this->isColumnModified(QuotaTypePeer::CODE)) $criteria->add(QuotaTypePeer::CODE, $this->code);
        if ($this->isColumnModified(QuotaTypePeer::NAME)) $criteria->add(QuotaTypePeer::NAME, $this->name);
        if ($this->isColumnModified(QuotaTypePeer::TEENOLDER)) $criteria->add(QuotaTypePeer::TEENOLDER, $this->teenolder);

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
        $criteria = new Criteria(QuotaTypePeer::DATABASE_NAME);
        $criteria->add(QuotaTypePeer::ID, $this->id);

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
     * @param object $copyObj An object of QuotaType (or compatible) type.
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
        $copyObj->setgroupCode($this->getgroupCode());
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());
        $copyObj->setteenOlder($this->getteenOlder());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getClientQuotings() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addClientQuoting($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getMkbQuotaTypePacientModels() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addMkbQuotaTypePacientModel($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getRbPacientModels() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addRbPacientModel($relObj->copy($deepCopy));
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
     * @return QuotaType Clone of current object.
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
     * @return QuotaTypePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new QuotaTypePeer();
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
        if ('ClientQuoting' == $relationName) {
            $this->initClientQuotings();
        }
        if ('MkbQuotaTypePacientModel' == $relationName) {
            $this->initMkbQuotaTypePacientModels();
        }
        if ('RbPacientModel' == $relationName) {
            $this->initRbPacientModels();
        }
    }

    /**
     * Clears out the collClientQuotings collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return QuotaType The current object (for fluent API support)
     * @see        addClientQuotings()
     */
    public function clearClientQuotings()
    {
        $this->collClientQuotings = null; // important to set this to null since that means it is uninitialized
        $this->collClientQuotingsPartial = null;

        return $this;
    }

    /**
     * reset is the collClientQuotings collection loaded partially
     *
     * @return void
     */
    public function resetPartialClientQuotings($v = true)
    {
        $this->collClientQuotingsPartial = $v;
    }

    /**
     * Initializes the collClientQuotings collection.
     *
     * By default this just sets the collClientQuotings collection to an empty array (like clearcollClientQuotings());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initClientQuotings($overrideExisting = true)
    {
        if (null !== $this->collClientQuotings && !$overrideExisting) {
            return;
        }
        $this->collClientQuotings = new PropelObjectCollection();
        $this->collClientQuotings->setModel('ClientQuoting');
    }

    /**
     * Gets an array of ClientQuoting objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this QuotaType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ClientQuoting[] List of ClientQuoting objects
     * @throws PropelException
     */
    public function getClientQuotings($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collClientQuotingsPartial && !$this->isNew();
        if (null === $this->collClientQuotings || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collClientQuotings) {
                // return empty collection
                $this->initClientQuotings();
            } else {
                $collClientQuotings = ClientQuotingQuery::create(null, $criteria)
                    ->filterByQuotaType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collClientQuotingsPartial && count($collClientQuotings)) {
                      $this->initClientQuotings(false);

                      foreach($collClientQuotings as $obj) {
                        if (false == $this->collClientQuotings->contains($obj)) {
                          $this->collClientQuotings->append($obj);
                        }
                      }

                      $this->collClientQuotingsPartial = true;
                    }

                    $collClientQuotings->getInternalIterator()->rewind();
                    return $collClientQuotings;
                }

                if($partial && $this->collClientQuotings) {
                    foreach($this->collClientQuotings as $obj) {
                        if($obj->isNew()) {
                            $collClientQuotings[] = $obj;
                        }
                    }
                }

                $this->collClientQuotings = $collClientQuotings;
                $this->collClientQuotingsPartial = false;
            }
        }

        return $this->collClientQuotings;
    }

    /**
     * Sets a collection of ClientQuoting objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $clientQuotings A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return QuotaType The current object (for fluent API support)
     */
    public function setClientQuotings(PropelCollection $clientQuotings, PropelPDO $con = null)
    {
        $clientQuotingsToDelete = $this->getClientQuotings(new Criteria(), $con)->diff($clientQuotings);

        $this->clientQuotingsScheduledForDeletion = unserialize(serialize($clientQuotingsToDelete));

        foreach ($clientQuotingsToDelete as $clientQuotingRemoved) {
            $clientQuotingRemoved->setQuotaType(null);
        }

        $this->collClientQuotings = null;
        foreach ($clientQuotings as $clientQuoting) {
            $this->addClientQuoting($clientQuoting);
        }

        $this->collClientQuotings = $clientQuotings;
        $this->collClientQuotingsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ClientQuoting objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ClientQuoting objects.
     * @throws PropelException
     */
    public function countClientQuotings(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collClientQuotingsPartial && !$this->isNew();
        if (null === $this->collClientQuotings || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collClientQuotings) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getClientQuotings());
            }
            $query = ClientQuotingQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByQuotaType($this)
                ->count($con);
        }

        return count($this->collClientQuotings);
    }

    /**
     * Method called to associate a ClientQuoting object to this object
     * through the ClientQuoting foreign key attribute.
     *
     * @param    ClientQuoting $l ClientQuoting
     * @return QuotaType The current object (for fluent API support)
     */
    public function addClientQuoting(ClientQuoting $l)
    {
        if ($this->collClientQuotings === null) {
            $this->initClientQuotings();
            $this->collClientQuotingsPartial = true;
        }
        if (!in_array($l, $this->collClientQuotings->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddClientQuoting($l);
        }

        return $this;
    }

    /**
     * @param	ClientQuoting $clientQuoting The clientQuoting object to add.
     */
    protected function doAddClientQuoting($clientQuoting)
    {
        $this->collClientQuotings[]= $clientQuoting;
        $clientQuoting->setQuotaType($this);
    }

    /**
     * @param	ClientQuoting $clientQuoting The clientQuoting object to remove.
     * @return QuotaType The current object (for fluent API support)
     */
    public function removeClientQuoting($clientQuoting)
    {
        if ($this->getClientQuotings()->contains($clientQuoting)) {
            $this->collClientQuotings->remove($this->collClientQuotings->search($clientQuoting));
            if (null === $this->clientQuotingsScheduledForDeletion) {
                $this->clientQuotingsScheduledForDeletion = clone $this->collClientQuotings;
                $this->clientQuotingsScheduledForDeletion->clear();
            }
            $this->clientQuotingsScheduledForDeletion[]= $clientQuoting;
            $clientQuoting->setQuotaType(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this QuotaType is new, it will return
     * an empty collection; or if this QuotaType has previously
     * been saved, it will retrieve related ClientQuotings from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in QuotaType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ClientQuoting[] List of ClientQuoting objects
     */
    public function getClientQuotingsJoinRbTreatment($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ClientQuotingQuery::create(null, $criteria);
        $query->joinWith('RbTreatment', $join_behavior);

        return $this->getClientQuotings($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this QuotaType is new, it will return
     * an empty collection; or if this QuotaType has previously
     * been saved, it will retrieve related ClientQuotings from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in QuotaType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ClientQuoting[] List of ClientQuoting objects
     */
    public function getClientQuotingsJoinRbPacientModel($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ClientQuotingQuery::create(null, $criteria);
        $query->joinWith('RbPacientModel', $join_behavior);

        return $this->getClientQuotings($query, $con);
    }

    /**
     * Clears out the collMkbQuotaTypePacientModels collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return QuotaType The current object (for fluent API support)
     * @see        addMkbQuotaTypePacientModels()
     */
    public function clearMkbQuotaTypePacientModels()
    {
        $this->collMkbQuotaTypePacientModels = null; // important to set this to null since that means it is uninitialized
        $this->collMkbQuotaTypePacientModelsPartial = null;

        return $this;
    }

    /**
     * reset is the collMkbQuotaTypePacientModels collection loaded partially
     *
     * @return void
     */
    public function resetPartialMkbQuotaTypePacientModels($v = true)
    {
        $this->collMkbQuotaTypePacientModelsPartial = $v;
    }

    /**
     * Initializes the collMkbQuotaTypePacientModels collection.
     *
     * By default this just sets the collMkbQuotaTypePacientModels collection to an empty array (like clearcollMkbQuotaTypePacientModels());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initMkbQuotaTypePacientModels($overrideExisting = true)
    {
        if (null !== $this->collMkbQuotaTypePacientModels && !$overrideExisting) {
            return;
        }
        $this->collMkbQuotaTypePacientModels = new PropelObjectCollection();
        $this->collMkbQuotaTypePacientModels->setModel('MkbQuotaTypePacientModel');
    }

    /**
     * Gets an array of MkbQuotaTypePacientModel objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this QuotaType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|MkbQuotaTypePacientModel[] List of MkbQuotaTypePacientModel objects
     * @throws PropelException
     */
    public function getMkbQuotaTypePacientModels($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collMkbQuotaTypePacientModelsPartial && !$this->isNew();
        if (null === $this->collMkbQuotaTypePacientModels || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collMkbQuotaTypePacientModels) {
                // return empty collection
                $this->initMkbQuotaTypePacientModels();
            } else {
                $collMkbQuotaTypePacientModels = MkbQuotaTypePacientModelQuery::create(null, $criteria)
                    ->filterByQuotaType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collMkbQuotaTypePacientModelsPartial && count($collMkbQuotaTypePacientModels)) {
                      $this->initMkbQuotaTypePacientModels(false);

                      foreach($collMkbQuotaTypePacientModels as $obj) {
                        if (false == $this->collMkbQuotaTypePacientModels->contains($obj)) {
                          $this->collMkbQuotaTypePacientModels->append($obj);
                        }
                      }

                      $this->collMkbQuotaTypePacientModelsPartial = true;
                    }

                    $collMkbQuotaTypePacientModels->getInternalIterator()->rewind();
                    return $collMkbQuotaTypePacientModels;
                }

                if($partial && $this->collMkbQuotaTypePacientModels) {
                    foreach($this->collMkbQuotaTypePacientModels as $obj) {
                        if($obj->isNew()) {
                            $collMkbQuotaTypePacientModels[] = $obj;
                        }
                    }
                }

                $this->collMkbQuotaTypePacientModels = $collMkbQuotaTypePacientModels;
                $this->collMkbQuotaTypePacientModelsPartial = false;
            }
        }

        return $this->collMkbQuotaTypePacientModels;
    }

    /**
     * Sets a collection of MkbQuotaTypePacientModel objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $mkbQuotaTypePacientModels A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return QuotaType The current object (for fluent API support)
     */
    public function setMkbQuotaTypePacientModels(PropelCollection $mkbQuotaTypePacientModels, PropelPDO $con = null)
    {
        $mkbQuotaTypePacientModelsToDelete = $this->getMkbQuotaTypePacientModels(new Criteria(), $con)->diff($mkbQuotaTypePacientModels);

        $this->mkbQuotaTypePacientModelsScheduledForDeletion = unserialize(serialize($mkbQuotaTypePacientModelsToDelete));

        foreach ($mkbQuotaTypePacientModelsToDelete as $mkbQuotaTypePacientModelRemoved) {
            $mkbQuotaTypePacientModelRemoved->setQuotaType(null);
        }

        $this->collMkbQuotaTypePacientModels = null;
        foreach ($mkbQuotaTypePacientModels as $mkbQuotaTypePacientModel) {
            $this->addMkbQuotaTypePacientModel($mkbQuotaTypePacientModel);
        }

        $this->collMkbQuotaTypePacientModels = $mkbQuotaTypePacientModels;
        $this->collMkbQuotaTypePacientModelsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related MkbQuotaTypePacientModel objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related MkbQuotaTypePacientModel objects.
     * @throws PropelException
     */
    public function countMkbQuotaTypePacientModels(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collMkbQuotaTypePacientModelsPartial && !$this->isNew();
        if (null === $this->collMkbQuotaTypePacientModels || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collMkbQuotaTypePacientModels) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getMkbQuotaTypePacientModels());
            }
            $query = MkbQuotaTypePacientModelQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByQuotaType($this)
                ->count($con);
        }

        return count($this->collMkbQuotaTypePacientModels);
    }

    /**
     * Method called to associate a MkbQuotaTypePacientModel object to this object
     * through the MkbQuotaTypePacientModel foreign key attribute.
     *
     * @param    MkbQuotaTypePacientModel $l MkbQuotaTypePacientModel
     * @return QuotaType The current object (for fluent API support)
     */
    public function addMkbQuotaTypePacientModel(MkbQuotaTypePacientModel $l)
    {
        if ($this->collMkbQuotaTypePacientModels === null) {
            $this->initMkbQuotaTypePacientModels();
            $this->collMkbQuotaTypePacientModelsPartial = true;
        }
        if (!in_array($l, $this->collMkbQuotaTypePacientModels->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddMkbQuotaTypePacientModel($l);
        }

        return $this;
    }

    /**
     * @param	MkbQuotaTypePacientModel $mkbQuotaTypePacientModel The mkbQuotaTypePacientModel object to add.
     */
    protected function doAddMkbQuotaTypePacientModel($mkbQuotaTypePacientModel)
    {
        $this->collMkbQuotaTypePacientModels[]= $mkbQuotaTypePacientModel;
        $mkbQuotaTypePacientModel->setQuotaType($this);
    }

    /**
     * @param	MkbQuotaTypePacientModel $mkbQuotaTypePacientModel The mkbQuotaTypePacientModel object to remove.
     * @return QuotaType The current object (for fluent API support)
     */
    public function removeMkbQuotaTypePacientModel($mkbQuotaTypePacientModel)
    {
        if ($this->getMkbQuotaTypePacientModels()->contains($mkbQuotaTypePacientModel)) {
            $this->collMkbQuotaTypePacientModels->remove($this->collMkbQuotaTypePacientModels->search($mkbQuotaTypePacientModel));
            if (null === $this->mkbQuotaTypePacientModelsScheduledForDeletion) {
                $this->mkbQuotaTypePacientModelsScheduledForDeletion = clone $this->collMkbQuotaTypePacientModels;
                $this->mkbQuotaTypePacientModelsScheduledForDeletion->clear();
            }
            $this->mkbQuotaTypePacientModelsScheduledForDeletion[]= clone $mkbQuotaTypePacientModel;
            $mkbQuotaTypePacientModel->setQuotaType(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this QuotaType is new, it will return
     * an empty collection; or if this QuotaType has previously
     * been saved, it will retrieve related MkbQuotaTypePacientModels from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in QuotaType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|MkbQuotaTypePacientModel[] List of MkbQuotaTypePacientModel objects
     */
    public function getMkbQuotaTypePacientModelsJoinRbPacientModel($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = MkbQuotaTypePacientModelQuery::create(null, $criteria);
        $query->joinWith('RbPacientModel', $join_behavior);

        return $this->getMkbQuotaTypePacientModels($query, $con);
    }

    /**
     * Clears out the collRbPacientModels collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return QuotaType The current object (for fluent API support)
     * @see        addRbPacientModels()
     */
    public function clearRbPacientModels()
    {
        $this->collRbPacientModels = null; // important to set this to null since that means it is uninitialized
        $this->collRbPacientModelsPartial = null;

        return $this;
    }

    /**
     * reset is the collRbPacientModels collection loaded partially
     *
     * @return void
     */
    public function resetPartialRbPacientModels($v = true)
    {
        $this->collRbPacientModelsPartial = $v;
    }

    /**
     * Initializes the collRbPacientModels collection.
     *
     * By default this just sets the collRbPacientModels collection to an empty array (like clearcollRbPacientModels());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initRbPacientModels($overrideExisting = true)
    {
        if (null !== $this->collRbPacientModels && !$overrideExisting) {
            return;
        }
        $this->collRbPacientModels = new PropelObjectCollection();
        $this->collRbPacientModels->setModel('RbPacientModel');
    }

    /**
     * Gets an array of RbPacientModel objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this QuotaType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|RbPacientModel[] List of RbPacientModel objects
     * @throws PropelException
     */
    public function getRbPacientModels($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collRbPacientModelsPartial && !$this->isNew();
        if (null === $this->collRbPacientModels || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collRbPacientModels) {
                // return empty collection
                $this->initRbPacientModels();
            } else {
                $collRbPacientModels = RbPacientModelQuery::create(null, $criteria)
                    ->filterByQuotaType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collRbPacientModelsPartial && count($collRbPacientModels)) {
                      $this->initRbPacientModels(false);

                      foreach($collRbPacientModels as $obj) {
                        if (false == $this->collRbPacientModels->contains($obj)) {
                          $this->collRbPacientModels->append($obj);
                        }
                      }

                      $this->collRbPacientModelsPartial = true;
                    }

                    $collRbPacientModels->getInternalIterator()->rewind();
                    return $collRbPacientModels;
                }

                if($partial && $this->collRbPacientModels) {
                    foreach($this->collRbPacientModels as $obj) {
                        if($obj->isNew()) {
                            $collRbPacientModels[] = $obj;
                        }
                    }
                }

                $this->collRbPacientModels = $collRbPacientModels;
                $this->collRbPacientModelsPartial = false;
            }
        }

        return $this->collRbPacientModels;
    }

    /**
     * Sets a collection of RbPacientModel objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rbPacientModels A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return QuotaType The current object (for fluent API support)
     */
    public function setRbPacientModels(PropelCollection $rbPacientModels, PropelPDO $con = null)
    {
        $rbPacientModelsToDelete = $this->getRbPacientModels(new Criteria(), $con)->diff($rbPacientModels);

        $this->rbPacientModelsScheduledForDeletion = unserialize(serialize($rbPacientModelsToDelete));

        foreach ($rbPacientModelsToDelete as $rbPacientModelRemoved) {
            $rbPacientModelRemoved->setQuotaType(null);
        }

        $this->collRbPacientModels = null;
        foreach ($rbPacientModels as $rbPacientModel) {
            $this->addRbPacientModel($rbPacientModel);
        }

        $this->collRbPacientModels = $rbPacientModels;
        $this->collRbPacientModelsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related RbPacientModel objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related RbPacientModel objects.
     * @throws PropelException
     */
    public function countRbPacientModels(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collRbPacientModelsPartial && !$this->isNew();
        if (null === $this->collRbPacientModels || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collRbPacientModels) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getRbPacientModels());
            }
            $query = RbPacientModelQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByQuotaType($this)
                ->count($con);
        }

        return count($this->collRbPacientModels);
    }

    /**
     * Method called to associate a RbPacientModel object to this object
     * through the RbPacientModel foreign key attribute.
     *
     * @param    RbPacientModel $l RbPacientModel
     * @return QuotaType The current object (for fluent API support)
     */
    public function addRbPacientModel(RbPacientModel $l)
    {
        if ($this->collRbPacientModels === null) {
            $this->initRbPacientModels();
            $this->collRbPacientModelsPartial = true;
        }
        if (!in_array($l, $this->collRbPacientModels->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddRbPacientModel($l);
        }

        return $this;
    }

    /**
     * @param	RbPacientModel $rbPacientModel The rbPacientModel object to add.
     */
    protected function doAddRbPacientModel($rbPacientModel)
    {
        $this->collRbPacientModels[]= $rbPacientModel;
        $rbPacientModel->setQuotaType($this);
    }

    /**
     * @param	RbPacientModel $rbPacientModel The rbPacientModel object to remove.
     * @return QuotaType The current object (for fluent API support)
     */
    public function removeRbPacientModel($rbPacientModel)
    {
        if ($this->getRbPacientModels()->contains($rbPacientModel)) {
            $this->collRbPacientModels->remove($this->collRbPacientModels->search($rbPacientModel));
            if (null === $this->rbPacientModelsScheduledForDeletion) {
                $this->rbPacientModelsScheduledForDeletion = clone $this->collRbPacientModels;
                $this->rbPacientModelsScheduledForDeletion->clear();
            }
            $this->rbPacientModelsScheduledForDeletion[]= clone $rbPacientModel;
            $rbPacientModel->setQuotaType(null);
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
        $this->group_code = null;
        $this->code = null;
        $this->name = null;
        $this->teenolder = null;
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
            if ($this->collClientQuotings) {
                foreach ($this->collClientQuotings as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collMkbQuotaTypePacientModels) {
                foreach ($this->collMkbQuotaTypePacientModels as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collRbPacientModels) {
                foreach ($this->collRbPacientModels as $o) {
                    $o->clearAllReferences($deep);
                }
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collClientQuotings instanceof PropelCollection) {
            $this->collClientQuotings->clearIterator();
        }
        $this->collClientQuotings = null;
        if ($this->collMkbQuotaTypePacientModels instanceof PropelCollection) {
            $this->collMkbQuotaTypePacientModels->clearIterator();
        }
        $this->collMkbQuotaTypePacientModels = null;
        if ($this->collRbPacientModels instanceof PropelCollection) {
            $this->collRbPacientModels->clearIterator();
        }
        $this->collRbPacientModels = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(QuotaTypePeer::DEFAULT_STRING_FORMAT);
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
     * @return     QuotaType The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = QuotaTypePeer::MODIFYDATETIME;

        return $this;
    }

}
