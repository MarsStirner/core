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
use Webmis\Models\DrugChart;
use Webmis\Models\DrugChartPeer;
use Webmis\Models\DrugChartQuery;

/**
 * Base class that represents a row from the 'DrugChart' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseDrugChart extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\DrugChartPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        DrugChartPeer
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
     * The value for the action_id field.
     * @var        int
     */
    protected $action_id;

    /**
     * The value for the master_id field.
     * @var        int
     */
    protected $master_id;

    /**
     * The value for the begdatetime field.
     * @var        string
     */
    protected $begdatetime;

    /**
     * The value for the enddatetime field.
     * @var        string
     */
    protected $enddatetime;

    /**
     * The value for the status field.
     * @var        int
     */
    protected $status;

    /**
     * The value for the statusdatetime field.
     * @var        int
     */
    protected $statusdatetime;

    /**
     * The value for the note field.
     * Note: this column has a database default value of: ''
     * @var        string
     */
    protected $note;

    /**
     * The value for the uuid field.
     * @var        string
     */
    protected $uuid;

    /**
     * The value for the version field.
     * @var        int
     */
    protected $version;

    /**
     * @var        Action
     */
    protected $aAction;

    /**
     * @var        DrugChart
     */
    protected $aDrugChartRelatedBymasterId;

    /**
     * @var        PropelObjectCollection|DrugChart[] Collection to store aggregation of DrugChart objects.
     */
    protected $collDrugChartsRelatedByid;
    protected $collDrugChartsRelatedByidPartial;

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
    protected $drugChartsRelatedByidScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->note = '';
    }

    /**
     * Initializes internal state of BaseDrugChart object.
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
     * Get the [action_id] column value.
     *
     * @return int
     */
    public function getactionId()
    {
        return $this->action_id;
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
     * Get the [optionally formatted] temporal [begdatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getbegDateTime($format = 'Y-m-d H:i:s')
    {
        if ($this->begdatetime === null) {
            return null;
        }

        if ($this->begdatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->begdatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->begdatetime, true), $x);
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
     * Get the [optionally formatted] temporal [enddatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getendDateTime($format = 'Y-m-d H:i:s')
    {
        if ($this->enddatetime === null) {
            return null;
        }

        if ($this->enddatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->enddatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->enddatetime, true), $x);
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
     * Get the [statusdatetime] column value.
     *
     * @return int
     */
    public function getstatusDateTime()
    {
        return $this->statusdatetime;
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
     * Get the [uuid] column value.
     *
     * @return string
     */
    public function getuuid()
    {
        return $this->uuid;
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
     * @return DrugChart The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = DrugChartPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [action_id] column.
     *
     * @param int $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setactionId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->action_id !== $v) {
            $this->action_id = $v;
            $this->modifiedColumns[] = DrugChartPeer::ACTION_ID;
        }

        if ($this->aAction !== null && $this->aAction->getid() !== $v) {
            $this->aAction = null;
        }


        return $this;
    } // setactionId()

    /**
     * Set the value of [master_id] column.
     *
     * @param int $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setmasterId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->master_id !== $v) {
            $this->master_id = $v;
            $this->modifiedColumns[] = DrugChartPeer::MASTER_ID;
        }

        if ($this->aDrugChartRelatedBymasterId !== null && $this->aDrugChartRelatedBymasterId->getid() !== $v) {
            $this->aDrugChartRelatedBymasterId = null;
        }


        return $this;
    } // setmasterId()

    /**
     * Sets the value of [begdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return DrugChart The current object (for fluent API support)
     */
    public function setbegDateTime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->begdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->begdatetime !== null && $tmpDt = new DateTime($this->begdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->begdatetime = $newDateAsString;
                $this->modifiedColumns[] = DrugChartPeer::BEGDATETIME;
            }
        } // if either are not null


        return $this;
    } // setbegDateTime()

    /**
     * Sets the value of [enddatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return DrugChart The current object (for fluent API support)
     */
    public function setendDateTime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->enddatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->enddatetime !== null && $tmpDt = new DateTime($this->enddatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->enddatetime = $newDateAsString;
                $this->modifiedColumns[] = DrugChartPeer::ENDDATETIME;
            }
        } // if either are not null


        return $this;
    } // setendDateTime()

    /**
     * Set the value of [status] column.
     *
     * @param int $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setstatus($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->status !== $v) {
            $this->status = $v;
            $this->modifiedColumns[] = DrugChartPeer::STATUS;
        }


        return $this;
    } // setstatus()

    /**
     * Set the value of [statusdatetime] column.
     *
     * @param int $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setstatusDateTime($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->statusdatetime !== $v) {
            $this->statusdatetime = $v;
            $this->modifiedColumns[] = DrugChartPeer::STATUSDATETIME;
        }


        return $this;
    } // setstatusDateTime()

    /**
     * Set the value of [note] column.
     *
     * @param string $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setnote($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->note !== $v) {
            $this->note = $v;
            $this->modifiedColumns[] = DrugChartPeer::NOTE;
        }


        return $this;
    } // setnote()

    /**
     * Set the value of [uuid] column.
     *
     * @param string $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setuuid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->uuid !== $v) {
            $this->uuid = $v;
            $this->modifiedColumns[] = DrugChartPeer::UUID;
        }


        return $this;
    } // setuuid()

    /**
     * Set the value of [version] column.
     *
     * @param int $v new value
     * @return DrugChart The current object (for fluent API support)
     */
    public function setversion($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->version !== $v) {
            $this->version = $v;
            $this->modifiedColumns[] = DrugChartPeer::VERSION;
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
            if ($this->note !== '') {
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
            $this->action_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->master_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->begdatetime = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->enddatetime = ($row[$startcol + 4] !== null) ? (string) $row[$startcol + 4] : null;
            $this->status = ($row[$startcol + 5] !== null) ? (int) $row[$startcol + 5] : null;
            $this->statusdatetime = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->note = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->uuid = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->version = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 10; // 10 = DrugChartPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating DrugChart object", $e);
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

        if ($this->aAction !== null && $this->action_id !== $this->aAction->getid()) {
            $this->aAction = null;
        }
        if ($this->aDrugChartRelatedBymasterId !== null && $this->master_id !== $this->aDrugChartRelatedBymasterId->getid()) {
            $this->aDrugChartRelatedBymasterId = null;
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
            $con = Propel::getConnection(DrugChartPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = DrugChartPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aAction = null;
            $this->aDrugChartRelatedBymasterId = null;
            $this->collDrugChartsRelatedByid = null;

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
            $con = Propel::getConnection(DrugChartPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = DrugChartQuery::create()
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
            $con = Propel::getConnection(DrugChartPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
            } else {
                $ret = $ret && $this->preUpdate($con);
            }
            if ($ret) {
                $affectedRows = $this->doSave($con);
                if ($isInsert) {
                    $this->postInsert($con);
                } else {
                    $this->postUpdate($con);
                }
                $this->postSave($con);
                DrugChartPeer::addInstanceToPool($this);
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

            if ($this->aDrugChartRelatedBymasterId !== null) {
                if ($this->aDrugChartRelatedBymasterId->isModified() || $this->aDrugChartRelatedBymasterId->isNew()) {
                    $affectedRows += $this->aDrugChartRelatedBymasterId->save($con);
                }
                $this->setDrugChartRelatedBymasterId($this->aDrugChartRelatedBymasterId);
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

            if ($this->drugChartsRelatedByidScheduledForDeletion !== null) {
                if (!$this->drugChartsRelatedByidScheduledForDeletion->isEmpty()) {
                    foreach ($this->drugChartsRelatedByidScheduledForDeletion as $drugChartRelatedByid) {
                        // need to save related object because we set the relation to null
                        $drugChartRelatedByid->save($con);
                    }
                    $this->drugChartsRelatedByidScheduledForDeletion = null;
                }
            }

            if ($this->collDrugChartsRelatedByid !== null) {
                foreach ($this->collDrugChartsRelatedByid as $referrerFK) {
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

        $this->modifiedColumns[] = DrugChartPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . DrugChartPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(DrugChartPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(DrugChartPeer::ACTION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`action_id`';
        }
        if ($this->isColumnModified(DrugChartPeer::MASTER_ID)) {
            $modifiedColumns[':p' . $index++]  = '`master_id`';
        }
        if ($this->isColumnModified(DrugChartPeer::BEGDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`begDateTime`';
        }
        if ($this->isColumnModified(DrugChartPeer::ENDDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`endDateTime`';
        }
        if ($this->isColumnModified(DrugChartPeer::STATUS)) {
            $modifiedColumns[':p' . $index++]  = '`status`';
        }
        if ($this->isColumnModified(DrugChartPeer::STATUSDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`statusDateTime`';
        }
        if ($this->isColumnModified(DrugChartPeer::NOTE)) {
            $modifiedColumns[':p' . $index++]  = '`note`';
        }
        if ($this->isColumnModified(DrugChartPeer::UUID)) {
            $modifiedColumns[':p' . $index++]  = '`uuid`';
        }
        if ($this->isColumnModified(DrugChartPeer::VERSION)) {
            $modifiedColumns[':p' . $index++]  = '`version`';
        }

        $sql = sprintf(
            'INSERT INTO `DrugChart` (%s) VALUES (%s)',
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
                    case '`action_id`':
                        $stmt->bindValue($identifier, $this->action_id, PDO::PARAM_INT);
                        break;
                    case '`master_id`':
                        $stmt->bindValue($identifier, $this->master_id, PDO::PARAM_INT);
                        break;
                    case '`begDateTime`':
                        $stmt->bindValue($identifier, $this->begdatetime, PDO::PARAM_STR);
                        break;
                    case '`endDateTime`':
                        $stmt->bindValue($identifier, $this->enddatetime, PDO::PARAM_STR);
                        break;
                    case '`status`':
                        $stmt->bindValue($identifier, $this->status, PDO::PARAM_INT);
                        break;
                    case '`statusDateTime`':
                        $stmt->bindValue($identifier, $this->statusdatetime, PDO::PARAM_INT);
                        break;
                    case '`note`':
                        $stmt->bindValue($identifier, $this->note, PDO::PARAM_STR);
                        break;
                    case '`uuid`':
                        $stmt->bindValue($identifier, $this->uuid, PDO::PARAM_STR);
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

            if ($this->aDrugChartRelatedBymasterId !== null) {
                if (!$this->aDrugChartRelatedBymasterId->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aDrugChartRelatedBymasterId->getValidationFailures());
                }
            }


            if (($retval = DrugChartPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collDrugChartsRelatedByid !== null) {
                    foreach ($this->collDrugChartsRelatedByid as $referrerFK) {
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
        $pos = DrugChartPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getactionId();
                break;
            case 2:
                return $this->getmasterId();
                break;
            case 3:
                return $this->getbegDateTime();
                break;
            case 4:
                return $this->getendDateTime();
                break;
            case 5:
                return $this->getstatus();
                break;
            case 6:
                return $this->getstatusDateTime();
                break;
            case 7:
                return $this->getnote();
                break;
            case 8:
                return $this->getuuid();
                break;
            case 9:
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
        if (isset($alreadyDumpedObjects['DrugChart'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['DrugChart'][$this->getPrimaryKey()] = true;
        $keys = DrugChartPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getactionId(),
            $keys[2] => $this->getmasterId(),
            $keys[3] => $this->getbegDateTime(),
            $keys[4] => $this->getendDateTime(),
            $keys[5] => $this->getstatus(),
            $keys[6] => $this->getstatusDateTime(),
            $keys[7] => $this->getnote(),
            $keys[8] => $this->getuuid(),
            $keys[9] => $this->getversion(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aAction) {
                $result['Action'] = $this->aAction->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aDrugChartRelatedBymasterId) {
                $result['DrugChartRelatedBymasterId'] = $this->aDrugChartRelatedBymasterId->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collDrugChartsRelatedByid) {
                $result['DrugChartsRelatedByid'] = $this->collDrugChartsRelatedByid->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = DrugChartPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setactionId($value);
                break;
            case 2:
                $this->setmasterId($value);
                break;
            case 3:
                $this->setbegDateTime($value);
                break;
            case 4:
                $this->setendDateTime($value);
                break;
            case 5:
                $this->setstatus($value);
                break;
            case 6:
                $this->setstatusDateTime($value);
                break;
            case 7:
                $this->setnote($value);
                break;
            case 8:
                $this->setuuid($value);
                break;
            case 9:
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
        $keys = DrugChartPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setactionId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setmasterId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setbegDateTime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setendDateTime($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setstatus($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setstatusDateTime($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setnote($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setuuid($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setversion($arr[$keys[9]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(DrugChartPeer::DATABASE_NAME);

        if ($this->isColumnModified(DrugChartPeer::ID)) $criteria->add(DrugChartPeer::ID, $this->id);
        if ($this->isColumnModified(DrugChartPeer::ACTION_ID)) $criteria->add(DrugChartPeer::ACTION_ID, $this->action_id);
        if ($this->isColumnModified(DrugChartPeer::MASTER_ID)) $criteria->add(DrugChartPeer::MASTER_ID, $this->master_id);
        if ($this->isColumnModified(DrugChartPeer::BEGDATETIME)) $criteria->add(DrugChartPeer::BEGDATETIME, $this->begdatetime);
        if ($this->isColumnModified(DrugChartPeer::ENDDATETIME)) $criteria->add(DrugChartPeer::ENDDATETIME, $this->enddatetime);
        if ($this->isColumnModified(DrugChartPeer::STATUS)) $criteria->add(DrugChartPeer::STATUS, $this->status);
        if ($this->isColumnModified(DrugChartPeer::STATUSDATETIME)) $criteria->add(DrugChartPeer::STATUSDATETIME, $this->statusdatetime);
        if ($this->isColumnModified(DrugChartPeer::NOTE)) $criteria->add(DrugChartPeer::NOTE, $this->note);
        if ($this->isColumnModified(DrugChartPeer::UUID)) $criteria->add(DrugChartPeer::UUID, $this->uuid);
        if ($this->isColumnModified(DrugChartPeer::VERSION)) $criteria->add(DrugChartPeer::VERSION, $this->version);

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
        $criteria = new Criteria(DrugChartPeer::DATABASE_NAME);
        $criteria->add(DrugChartPeer::ID, $this->id);

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
     * @param object $copyObj An object of DrugChart (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setactionId($this->getactionId());
        $copyObj->setmasterId($this->getmasterId());
        $copyObj->setbegDateTime($this->getbegDateTime());
        $copyObj->setendDateTime($this->getendDateTime());
        $copyObj->setstatus($this->getstatus());
        $copyObj->setstatusDateTime($this->getstatusDateTime());
        $copyObj->setnote($this->getnote());
        $copyObj->setuuid($this->getuuid());
        $copyObj->setversion($this->getversion());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getDrugChartsRelatedByid() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addDrugChartRelatedByid($relObj->copy($deepCopy));
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
     * @return DrugChart Clone of current object.
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
     * @return DrugChartPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new DrugChartPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a Action object.
     *
     * @param             Action $v
     * @return DrugChart The current object (for fluent API support)
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
            $v->addDrugChart($this);
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
                $this->aAction->addDrugCharts($this);
             */
        }

        return $this->aAction;
    }

    /**
     * Declares an association between this object and a DrugChart object.
     *
     * @param             DrugChart $v
     * @return DrugChart The current object (for fluent API support)
     * @throws PropelException
     */
    public function setDrugChartRelatedBymasterId(DrugChart $v = null)
    {
        if ($v === null) {
            $this->setmasterId(NULL);
        } else {
            $this->setmasterId($v->getid());
        }

        $this->aDrugChartRelatedBymasterId = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the DrugChart object, it will not be re-added.
        if ($v !== null) {
            $v->addDrugChartRelatedByid($this);
        }


        return $this;
    }


    /**
     * Get the associated DrugChart object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return DrugChart The associated DrugChart object.
     * @throws PropelException
     */
    public function getDrugChartRelatedBymasterId(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aDrugChartRelatedBymasterId === null && ($this->master_id !== null) && $doQuery) {
            $this->aDrugChartRelatedBymasterId = DrugChartQuery::create()->findPk($this->master_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aDrugChartRelatedBymasterId->addDrugChartsRelatedByid($this);
             */
        }

        return $this->aDrugChartRelatedBymasterId;
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
        if ('DrugChartRelatedByid' == $relationName) {
            $this->initDrugChartsRelatedByid();
        }
    }

    /**
     * Clears out the collDrugChartsRelatedByid collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return DrugChart The current object (for fluent API support)
     * @see        addDrugChartsRelatedByid()
     */
    public function clearDrugChartsRelatedByid()
    {
        $this->collDrugChartsRelatedByid = null; // important to set this to null since that means it is uninitialized
        $this->collDrugChartsRelatedByidPartial = null;

        return $this;
    }

    /**
     * reset is the collDrugChartsRelatedByid collection loaded partially
     *
     * @return void
     */
    public function resetPartialDrugChartsRelatedByid($v = true)
    {
        $this->collDrugChartsRelatedByidPartial = $v;
    }

    /**
     * Initializes the collDrugChartsRelatedByid collection.
     *
     * By default this just sets the collDrugChartsRelatedByid collection to an empty array (like clearcollDrugChartsRelatedByid());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initDrugChartsRelatedByid($overrideExisting = true)
    {
        if (null !== $this->collDrugChartsRelatedByid && !$overrideExisting) {
            return;
        }
        $this->collDrugChartsRelatedByid = new PropelObjectCollection();
        $this->collDrugChartsRelatedByid->setModel('DrugChart');
    }

    /**
     * Gets an array of DrugChart objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this DrugChart is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|DrugChart[] List of DrugChart objects
     * @throws PropelException
     */
    public function getDrugChartsRelatedByid($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collDrugChartsRelatedByidPartial && !$this->isNew();
        if (null === $this->collDrugChartsRelatedByid || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collDrugChartsRelatedByid) {
                // return empty collection
                $this->initDrugChartsRelatedByid();
            } else {
                $collDrugChartsRelatedByid = DrugChartQuery::create(null, $criteria)
                    ->filterByDrugChartRelatedBymasterId($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collDrugChartsRelatedByidPartial && count($collDrugChartsRelatedByid)) {
                      $this->initDrugChartsRelatedByid(false);

                      foreach($collDrugChartsRelatedByid as $obj) {
                        if (false == $this->collDrugChartsRelatedByid->contains($obj)) {
                          $this->collDrugChartsRelatedByid->append($obj);
                        }
                      }

                      $this->collDrugChartsRelatedByidPartial = true;
                    }

                    $collDrugChartsRelatedByid->getInternalIterator()->rewind();
                    return $collDrugChartsRelatedByid;
                }

                if($partial && $this->collDrugChartsRelatedByid) {
                    foreach($this->collDrugChartsRelatedByid as $obj) {
                        if($obj->isNew()) {
                            $collDrugChartsRelatedByid[] = $obj;
                        }
                    }
                }

                $this->collDrugChartsRelatedByid = $collDrugChartsRelatedByid;
                $this->collDrugChartsRelatedByidPartial = false;
            }
        }

        return $this->collDrugChartsRelatedByid;
    }

    /**
     * Sets a collection of DrugChartRelatedByid objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $drugChartsRelatedByid A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return DrugChart The current object (for fluent API support)
     */
    public function setDrugChartsRelatedByid(PropelCollection $drugChartsRelatedByid, PropelPDO $con = null)
    {
        $drugChartsRelatedByidToDelete = $this->getDrugChartsRelatedByid(new Criteria(), $con)->diff($drugChartsRelatedByid);

        $this->drugChartsRelatedByidScheduledForDeletion = unserialize(serialize($drugChartsRelatedByidToDelete));

        foreach ($drugChartsRelatedByidToDelete as $drugChartRelatedByidRemoved) {
            $drugChartRelatedByidRemoved->setDrugChartRelatedBymasterId(null);
        }

        $this->collDrugChartsRelatedByid = null;
        foreach ($drugChartsRelatedByid as $drugChartRelatedByid) {
            $this->addDrugChartRelatedByid($drugChartRelatedByid);
        }

        $this->collDrugChartsRelatedByid = $drugChartsRelatedByid;
        $this->collDrugChartsRelatedByidPartial = false;

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
    public function countDrugChartsRelatedByid(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collDrugChartsRelatedByidPartial && !$this->isNew();
        if (null === $this->collDrugChartsRelatedByid || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collDrugChartsRelatedByid) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getDrugChartsRelatedByid());
            }
            $query = DrugChartQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByDrugChartRelatedBymasterId($this)
                ->count($con);
        }

        return count($this->collDrugChartsRelatedByid);
    }

    /**
     * Method called to associate a DrugChart object to this object
     * through the DrugChart foreign key attribute.
     *
     * @param    DrugChart $l DrugChart
     * @return DrugChart The current object (for fluent API support)
     */
    public function addDrugChartRelatedByid(DrugChart $l)
    {
        if ($this->collDrugChartsRelatedByid === null) {
            $this->initDrugChartsRelatedByid();
            $this->collDrugChartsRelatedByidPartial = true;
        }
        if (!in_array($l, $this->collDrugChartsRelatedByid->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddDrugChartRelatedByid($l);
        }

        return $this;
    }

    /**
     * @param	DrugChartRelatedByid $drugChartRelatedByid The drugChartRelatedByid object to add.
     */
    protected function doAddDrugChartRelatedByid($drugChartRelatedByid)
    {
        $this->collDrugChartsRelatedByid[]= $drugChartRelatedByid;
        $drugChartRelatedByid->setDrugChartRelatedBymasterId($this);
    }

    /**
     * @param	DrugChartRelatedByid $drugChartRelatedByid The drugChartRelatedByid object to remove.
     * @return DrugChart The current object (for fluent API support)
     */
    public function removeDrugChartRelatedByid($drugChartRelatedByid)
    {
        if ($this->getDrugChartsRelatedByid()->contains($drugChartRelatedByid)) {
            $this->collDrugChartsRelatedByid->remove($this->collDrugChartsRelatedByid->search($drugChartRelatedByid));
            if (null === $this->drugChartsRelatedByidScheduledForDeletion) {
                $this->drugChartsRelatedByidScheduledForDeletion = clone $this->collDrugChartsRelatedByid;
                $this->drugChartsRelatedByidScheduledForDeletion->clear();
            }
            $this->drugChartsRelatedByidScheduledForDeletion[]= $drugChartRelatedByid;
            $drugChartRelatedByid->setDrugChartRelatedBymasterId(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this DrugChart is new, it will return
     * an empty collection; or if this DrugChart has previously
     * been saved, it will retrieve related DrugChartsRelatedByid from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in DrugChart.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugChart[] List of DrugChart objects
     */
    public function getDrugChartsRelatedByidJoinAction($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugChartQuery::create(null, $criteria);
        $query->joinWith('Action', $join_behavior);

        return $this->getDrugChartsRelatedByid($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->action_id = null;
        $this->master_id = null;
        $this->begdatetime = null;
        $this->enddatetime = null;
        $this->status = null;
        $this->statusdatetime = null;
        $this->note = null;
        $this->uuid = null;
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
            if ($this->collDrugChartsRelatedByid) {
                foreach ($this->collDrugChartsRelatedByid as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aAction instanceof Persistent) {
              $this->aAction->clearAllReferences($deep);
            }
            if ($this->aDrugChartRelatedBymasterId instanceof Persistent) {
              $this->aDrugChartRelatedBymasterId->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collDrugChartsRelatedByid instanceof PropelCollection) {
            $this->collDrugChartsRelatedByid->clearIterator();
        }
        $this->collDrugChartsRelatedByid = null;
        $this->aAction = null;
        $this->aDrugChartRelatedBymasterId = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(DrugChartPeer::DEFAULT_STRING_FORMAT);
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

}
