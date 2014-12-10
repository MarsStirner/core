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
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\ActionPropertyFDRecordQuery;
use Webmis\Models\FDFieldValue;
use Webmis\Models\FDFieldValueQuery;
use Webmis\Models\FDRecord;
use Webmis\Models\FDRecordPeer;
use Webmis\Models\FDRecordQuery;

/**
 * Base class that represents a row from the 'FDRecord' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseFDRecord extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\FDRecordPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        FDRecordPeer
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
     * The value for the flatdirectory_id field.
     * @var        int
     */
    protected $flatdirectory_id;

    /**
     * The value for the flatdirectory_code field.
     * @var        string
     */
    protected $flatdirectory_code;

    /**
     * The value for the order field.
     * @var        int
     */
    protected $order;

    /**
     * The value for the name field.
     * @var        string
     */
    protected $name;

    /**
     * The value for the description field.
     * @var        string
     */
    protected $description;

    /**
     * The value for the datestart field.
     * @var        string
     */
    protected $datestart;

    /**
     * The value for the dateend field.
     * @var        string
     */
    protected $dateend;

    /**
     * @var        PropelObjectCollection|ActionPropertyFDRecord[] Collection to store aggregation of ActionPropertyFDRecord objects.
     */
    protected $collActionPropertyFDRecords;
    protected $collActionPropertyFDRecordsPartial;

    /**
     * @var        PropelObjectCollection|FDFieldValue[] Collection to store aggregation of FDFieldValue objects.
     */
    protected $collFDFieldValues;
    protected $collFDFieldValuesPartial;

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
    protected $actionPropertyFDRecordsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $fDFieldValuesScheduledForDeletion = null;

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
     * Get the [flatdirectory_id] column value.
     *
     * @return int
     */
    public function getFlatDirectoryId()
    {
        return $this->flatdirectory_id;
    }

    /**
     * Get the [flatdirectory_code] column value.
     *
     * @return string
     */
    public function getFlatDirectoryCode()
    {
        return $this->flatdirectory_code;
    }

    /**
     * Get the [order] column value.
     *
     * @return int
     */
    public function getOrder()
    {
        return $this->order;
    }

    /**
     * Get the [name] column value.
     *
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Get the [description] column value.
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Get the [optionally formatted] temporal [datestart] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getDateStart($format = 'Y-m-d H:i:s')
    {
        if ($this->datestart === null) {
            return null;
        }

        if ($this->datestart === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->datestart);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->datestart, true), $x);
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
    public function getDateEnd($format = 'Y-m-d H:i:s')
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
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = FDRecordPeer::ID;
        }


        return $this;
    } // setId()

    /**
     * Set the value of [flatdirectory_id] column.
     *
     * @param int $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setFlatDirectoryId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->flatdirectory_id !== $v) {
            $this->flatdirectory_id = $v;
            $this->modifiedColumns[] = FDRecordPeer::FLATDIRECTORY_ID;
        }


        return $this;
    } // setFlatDirectoryId()

    /**
     * Set the value of [flatdirectory_code] column.
     *
     * @param string $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setFlatDirectoryCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->flatdirectory_code !== $v) {
            $this->flatdirectory_code = $v;
            $this->modifiedColumns[] = FDRecordPeer::FLATDIRECTORY_CODE;
        }


        return $this;
    } // setFlatDirectoryCode()

    /**
     * Set the value of [order] column.
     *
     * @param int $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setOrder($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->order !== $v) {
            $this->order = $v;
            $this->modifiedColumns[] = FDRecordPeer::ORDER;
        }


        return $this;
    } // setOrder()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = FDRecordPeer::NAME;
        }


        return $this;
    } // setName()

    /**
     * Set the value of [description] column.
     *
     * @param string $v new value
     * @return FDRecord The current object (for fluent API support)
     */
    public function setDescription($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->description !== $v) {
            $this->description = $v;
            $this->modifiedColumns[] = FDRecordPeer::DESCRIPTION;
        }


        return $this;
    } // setDescription()

    /**
     * Sets the value of [datestart] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return FDRecord The current object (for fluent API support)
     */
    public function setDateStart($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->datestart !== null || $dt !== null) {
            $currentDateAsString = ($this->datestart !== null && $tmpDt = new DateTime($this->datestart)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->datestart = $newDateAsString;
                $this->modifiedColumns[] = FDRecordPeer::DATESTART;
            }
        } // if either are not null


        return $this;
    } // setDateStart()

    /**
     * Sets the value of [dateend] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return FDRecord The current object (for fluent API support)
     */
    public function setDateEnd($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->dateend !== null || $dt !== null) {
            $currentDateAsString = ($this->dateend !== null && $tmpDt = new DateTime($this->dateend)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->dateend = $newDateAsString;
                $this->modifiedColumns[] = FDRecordPeer::DATEEND;
            }
        } // if either are not null


        return $this;
    } // setDateEnd()

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
            $this->flatdirectory_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->flatdirectory_code = ($row[$startcol + 2] !== null) ? (string) $row[$startcol + 2] : null;
            $this->order = ($row[$startcol + 3] !== null) ? (int) $row[$startcol + 3] : null;
            $this->name = ($row[$startcol + 4] !== null) ? (string) $row[$startcol + 4] : null;
            $this->description = ($row[$startcol + 5] !== null) ? (string) $row[$startcol + 5] : null;
            $this->datestart = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->dateend = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 8; // 8 = FDRecordPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating FDRecord object", $e);
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
            $con = Propel::getConnection(FDRecordPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = FDRecordPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->collActionPropertyFDRecords = null;

            $this->collFDFieldValues = null;

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
            $con = Propel::getConnection(FDRecordPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = FDRecordQuery::create()
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
            $con = Propel::getConnection(FDRecordPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                FDRecordPeer::addInstanceToPool($this);
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

            if ($this->actionPropertyFDRecordsScheduledForDeletion !== null) {
                if (!$this->actionPropertyFDRecordsScheduledForDeletion->isEmpty()) {
                    ActionPropertyFDRecordQuery::create()
                        ->filterByPrimaryKeys($this->actionPropertyFDRecordsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionPropertyFDRecordsScheduledForDeletion = null;
                }
            }

            if ($this->collActionPropertyFDRecords !== null) {
                foreach ($this->collActionPropertyFDRecords as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->fDFieldValuesScheduledForDeletion !== null) {
                if (!$this->fDFieldValuesScheduledForDeletion->isEmpty()) {
                    FDFieldValueQuery::create()
                        ->filterByPrimaryKeys($this->fDFieldValuesScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->fDFieldValuesScheduledForDeletion = null;
                }
            }

            if ($this->collFDFieldValues !== null) {
                foreach ($this->collFDFieldValues as $referrerFK) {
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

        $this->modifiedColumns[] = FDRecordPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . FDRecordPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(FDRecordPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(FDRecordPeer::FLATDIRECTORY_ID)) {
            $modifiedColumns[':p' . $index++]  = '`flatDirectory_id`';
        }
        if ($this->isColumnModified(FDRecordPeer::FLATDIRECTORY_CODE)) {
            $modifiedColumns[':p' . $index++]  = '`flatDirectory_code`';
        }
        if ($this->isColumnModified(FDRecordPeer::ORDER)) {
            $modifiedColumns[':p' . $index++]  = '`order`';
        }
        if ($this->isColumnModified(FDRecordPeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(FDRecordPeer::DESCRIPTION)) {
            $modifiedColumns[':p' . $index++]  = '`description`';
        }
        if ($this->isColumnModified(FDRecordPeer::DATESTART)) {
            $modifiedColumns[':p' . $index++]  = '`dateStart`';
        }
        if ($this->isColumnModified(FDRecordPeer::DATEEND)) {
            $modifiedColumns[':p' . $index++]  = '`dateEnd`';
        }

        $sql = sprintf(
            'INSERT INTO `FDRecord` (%s) VALUES (%s)',
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
                    case '`flatDirectory_id`':
                        $stmt->bindValue($identifier, $this->flatdirectory_id, PDO::PARAM_INT);
                        break;
                    case '`flatDirectory_code`':
                        $stmt->bindValue($identifier, $this->flatdirectory_code, PDO::PARAM_STR);
                        break;
                    case '`order`':
                        $stmt->bindValue($identifier, $this->order, PDO::PARAM_INT);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`description`':
                        $stmt->bindValue($identifier, $this->description, PDO::PARAM_STR);
                        break;
                    case '`dateStart`':
                        $stmt->bindValue($identifier, $this->datestart, PDO::PARAM_STR);
                        break;
                    case '`dateEnd`':
                        $stmt->bindValue($identifier, $this->dateend, PDO::PARAM_STR);
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


            if (($retval = FDRecordPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionPropertyFDRecords !== null) {
                    foreach ($this->collActionPropertyFDRecords as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collFDFieldValues !== null) {
                    foreach ($this->collFDFieldValues as $referrerFK) {
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
        $pos = FDRecordPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getFlatDirectoryId();
                break;
            case 2:
                return $this->getFlatDirectoryCode();
                break;
            case 3:
                return $this->getOrder();
                break;
            case 4:
                return $this->getName();
                break;
            case 5:
                return $this->getDescription();
                break;
            case 6:
                return $this->getDateStart();
                break;
            case 7:
                return $this->getDateEnd();
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
        if (isset($alreadyDumpedObjects['FDRecord'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['FDRecord'][$this->getPrimaryKey()] = true;
        $keys = FDRecordPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getId(),
            $keys[1] => $this->getFlatDirectoryId(),
            $keys[2] => $this->getFlatDirectoryCode(),
            $keys[3] => $this->getOrder(),
            $keys[4] => $this->getName(),
            $keys[5] => $this->getDescription(),
            $keys[6] => $this->getDateStart(),
            $keys[7] => $this->getDateEnd(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->collActionPropertyFDRecords) {
                $result['ActionPropertyFDRecords'] = $this->collActionPropertyFDRecords->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collFDFieldValues) {
                $result['FDFieldValues'] = $this->collFDFieldValues->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = FDRecordPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setFlatDirectoryId($value);
                break;
            case 2:
                $this->setFlatDirectoryCode($value);
                break;
            case 3:
                $this->setOrder($value);
                break;
            case 4:
                $this->setName($value);
                break;
            case 5:
                $this->setDescription($value);
                break;
            case 6:
                $this->setDateStart($value);
                break;
            case 7:
                $this->setDateEnd($value);
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
        $keys = FDRecordPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setId($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setFlatDirectoryId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setFlatDirectoryCode($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setOrder($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setName($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setDescription($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setDateStart($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setDateEnd($arr[$keys[7]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(FDRecordPeer::DATABASE_NAME);

        if ($this->isColumnModified(FDRecordPeer::ID)) $criteria->add(FDRecordPeer::ID, $this->id);
        if ($this->isColumnModified(FDRecordPeer::FLATDIRECTORY_ID)) $criteria->add(FDRecordPeer::FLATDIRECTORY_ID, $this->flatdirectory_id);
        if ($this->isColumnModified(FDRecordPeer::FLATDIRECTORY_CODE)) $criteria->add(FDRecordPeer::FLATDIRECTORY_CODE, $this->flatdirectory_code);
        if ($this->isColumnModified(FDRecordPeer::ORDER)) $criteria->add(FDRecordPeer::ORDER, $this->order);
        if ($this->isColumnModified(FDRecordPeer::NAME)) $criteria->add(FDRecordPeer::NAME, $this->name);
        if ($this->isColumnModified(FDRecordPeer::DESCRIPTION)) $criteria->add(FDRecordPeer::DESCRIPTION, $this->description);
        if ($this->isColumnModified(FDRecordPeer::DATESTART)) $criteria->add(FDRecordPeer::DATESTART, $this->datestart);
        if ($this->isColumnModified(FDRecordPeer::DATEEND)) $criteria->add(FDRecordPeer::DATEEND, $this->dateend);

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
        $criteria = new Criteria(FDRecordPeer::DATABASE_NAME);
        $criteria->add(FDRecordPeer::ID, $this->id);

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
     * @param object $copyObj An object of FDRecord (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setFlatDirectoryId($this->getFlatDirectoryId());
        $copyObj->setFlatDirectoryCode($this->getFlatDirectoryCode());
        $copyObj->setOrder($this->getOrder());
        $copyObj->setName($this->getName());
        $copyObj->setDescription($this->getDescription());
        $copyObj->setDateStart($this->getDateStart());
        $copyObj->setDateEnd($this->getDateEnd());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActionPropertyFDRecords() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionPropertyFDRecord($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getFDFieldValues() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addFDFieldValue($relObj->copy($deepCopy));
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
     * @return FDRecord Clone of current object.
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
     * @return FDRecordPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new FDRecordPeer();
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
        if ('ActionPropertyFDRecord' == $relationName) {
            $this->initActionPropertyFDRecords();
        }
        if ('FDFieldValue' == $relationName) {
            $this->initFDFieldValues();
        }
    }

    /**
     * Clears out the collActionPropertyFDRecords collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return FDRecord The current object (for fluent API support)
     * @see        addActionPropertyFDRecords()
     */
    public function clearActionPropertyFDRecords()
    {
        $this->collActionPropertyFDRecords = null; // important to set this to null since that means it is uninitialized
        $this->collActionPropertyFDRecordsPartial = null;

        return $this;
    }

    /**
     * reset is the collActionPropertyFDRecords collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionPropertyFDRecords($v = true)
    {
        $this->collActionPropertyFDRecordsPartial = $v;
    }

    /**
     * Initializes the collActionPropertyFDRecords collection.
     *
     * By default this just sets the collActionPropertyFDRecords collection to an empty array (like clearcollActionPropertyFDRecords());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionPropertyFDRecords($overrideExisting = true)
    {
        if (null !== $this->collActionPropertyFDRecords && !$overrideExisting) {
            return;
        }
        $this->collActionPropertyFDRecords = new PropelObjectCollection();
        $this->collActionPropertyFDRecords->setModel('ActionPropertyFDRecord');
    }

    /**
     * Gets an array of ActionPropertyFDRecord objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this FDRecord is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ActionPropertyFDRecord[] List of ActionPropertyFDRecord objects
     * @throws PropelException
     */
    public function getActionPropertyFDRecords($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyFDRecordsPartial && !$this->isNew();
        if (null === $this->collActionPropertyFDRecords || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyFDRecords) {
                // return empty collection
                $this->initActionPropertyFDRecords();
            } else {
                $collActionPropertyFDRecords = ActionPropertyFDRecordQuery::create(null, $criteria)
                    ->filterByFDRecord($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionPropertyFDRecordsPartial && count($collActionPropertyFDRecords)) {
                      $this->initActionPropertyFDRecords(false);

                      foreach($collActionPropertyFDRecords as $obj) {
                        if (false == $this->collActionPropertyFDRecords->contains($obj)) {
                          $this->collActionPropertyFDRecords->append($obj);
                        }
                      }

                      $this->collActionPropertyFDRecordsPartial = true;
                    }

                    $collActionPropertyFDRecords->getInternalIterator()->rewind();
                    return $collActionPropertyFDRecords;
                }

                if($partial && $this->collActionPropertyFDRecords) {
                    foreach($this->collActionPropertyFDRecords as $obj) {
                        if($obj->isNew()) {
                            $collActionPropertyFDRecords[] = $obj;
                        }
                    }
                }

                $this->collActionPropertyFDRecords = $collActionPropertyFDRecords;
                $this->collActionPropertyFDRecordsPartial = false;
            }
        }

        return $this->collActionPropertyFDRecords;
    }

    /**
     * Sets a collection of ActionPropertyFDRecord objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionPropertyFDRecords A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return FDRecord The current object (for fluent API support)
     */
    public function setActionPropertyFDRecords(PropelCollection $actionPropertyFDRecords, PropelPDO $con = null)
    {
        $actionPropertyFDRecordsToDelete = $this->getActionPropertyFDRecords(new Criteria(), $con)->diff($actionPropertyFDRecords);

        $this->actionPropertyFDRecordsScheduledForDeletion = unserialize(serialize($actionPropertyFDRecordsToDelete));

        foreach ($actionPropertyFDRecordsToDelete as $actionPropertyFDRecordRemoved) {
            $actionPropertyFDRecordRemoved->setFDRecord(null);
        }

        $this->collActionPropertyFDRecords = null;
        foreach ($actionPropertyFDRecords as $actionPropertyFDRecord) {
            $this->addActionPropertyFDRecord($actionPropertyFDRecord);
        }

        $this->collActionPropertyFDRecords = $actionPropertyFDRecords;
        $this->collActionPropertyFDRecordsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ActionPropertyFDRecord objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ActionPropertyFDRecord objects.
     * @throws PropelException
     */
    public function countActionPropertyFDRecords(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertyFDRecordsPartial && !$this->isNew();
        if (null === $this->collActionPropertyFDRecords || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionPropertyFDRecords) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionPropertyFDRecords());
            }
            $query = ActionPropertyFDRecordQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByFDRecord($this)
                ->count($con);
        }

        return count($this->collActionPropertyFDRecords);
    }

    /**
     * Method called to associate a ActionPropertyFDRecord object to this object
     * through the ActionPropertyFDRecord foreign key attribute.
     *
     * @param    ActionPropertyFDRecord $l ActionPropertyFDRecord
     * @return FDRecord The current object (for fluent API support)
     */
    public function addActionPropertyFDRecord(ActionPropertyFDRecord $l)
    {
        if ($this->collActionPropertyFDRecords === null) {
            $this->initActionPropertyFDRecords();
            $this->collActionPropertyFDRecordsPartial = true;
        }
        if (!in_array($l, $this->collActionPropertyFDRecords->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionPropertyFDRecord($l);
        }

        return $this;
    }

    /**
     * @param	ActionPropertyFDRecord $actionPropertyFDRecord The actionPropertyFDRecord object to add.
     */
    protected function doAddActionPropertyFDRecord($actionPropertyFDRecord)
    {
        $this->collActionPropertyFDRecords[]= $actionPropertyFDRecord;
        $actionPropertyFDRecord->setFDRecord($this);
    }

    /**
     * @param	ActionPropertyFDRecord $actionPropertyFDRecord The actionPropertyFDRecord object to remove.
     * @return FDRecord The current object (for fluent API support)
     */
    public function removeActionPropertyFDRecord($actionPropertyFDRecord)
    {
        if ($this->getActionPropertyFDRecords()->contains($actionPropertyFDRecord)) {
            $this->collActionPropertyFDRecords->remove($this->collActionPropertyFDRecords->search($actionPropertyFDRecord));
            if (null === $this->actionPropertyFDRecordsScheduledForDeletion) {
                $this->actionPropertyFDRecordsScheduledForDeletion = clone $this->collActionPropertyFDRecords;
                $this->actionPropertyFDRecordsScheduledForDeletion->clear();
            }
            $this->actionPropertyFDRecordsScheduledForDeletion[]= clone $actionPropertyFDRecord;
            $actionPropertyFDRecord->setFDRecord(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this FDRecord is new, it will return
     * an empty collection; or if this FDRecord has previously
     * been saved, it will retrieve related ActionPropertyFDRecords from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in FDRecord.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionPropertyFDRecord[] List of ActionPropertyFDRecord objects
     */
    public function getActionPropertyFDRecordsJoinFDFieldValue($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyFDRecordQuery::create(null, $criteria);
        $query->joinWith('FDFieldValue', $join_behavior);

        return $this->getActionPropertyFDRecords($query, $con);
    }

    /**
     * Clears out the collFDFieldValues collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return FDRecord The current object (for fluent API support)
     * @see        addFDFieldValues()
     */
    public function clearFDFieldValues()
    {
        $this->collFDFieldValues = null; // important to set this to null since that means it is uninitialized
        $this->collFDFieldValuesPartial = null;

        return $this;
    }

    /**
     * reset is the collFDFieldValues collection loaded partially
     *
     * @return void
     */
    public function resetPartialFDFieldValues($v = true)
    {
        $this->collFDFieldValuesPartial = $v;
    }

    /**
     * Initializes the collFDFieldValues collection.
     *
     * By default this just sets the collFDFieldValues collection to an empty array (like clearcollFDFieldValues());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initFDFieldValues($overrideExisting = true)
    {
        if (null !== $this->collFDFieldValues && !$overrideExisting) {
            return;
        }
        $this->collFDFieldValues = new PropelObjectCollection();
        $this->collFDFieldValues->setModel('FDFieldValue');
    }

    /**
     * Gets an array of FDFieldValue objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this FDRecord is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|FDFieldValue[] List of FDFieldValue objects
     * @throws PropelException
     */
    public function getFDFieldValues($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collFDFieldValuesPartial && !$this->isNew();
        if (null === $this->collFDFieldValues || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collFDFieldValues) {
                // return empty collection
                $this->initFDFieldValues();
            } else {
                $collFDFieldValues = FDFieldValueQuery::create(null, $criteria)
                    ->filterByFDRecord($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collFDFieldValuesPartial && count($collFDFieldValues)) {
                      $this->initFDFieldValues(false);

                      foreach($collFDFieldValues as $obj) {
                        if (false == $this->collFDFieldValues->contains($obj)) {
                          $this->collFDFieldValues->append($obj);
                        }
                      }

                      $this->collFDFieldValuesPartial = true;
                    }

                    $collFDFieldValues->getInternalIterator()->rewind();
                    return $collFDFieldValues;
                }

                if($partial && $this->collFDFieldValues) {
                    foreach($this->collFDFieldValues as $obj) {
                        if($obj->isNew()) {
                            $collFDFieldValues[] = $obj;
                        }
                    }
                }

                $this->collFDFieldValues = $collFDFieldValues;
                $this->collFDFieldValuesPartial = false;
            }
        }

        return $this->collFDFieldValues;
    }

    /**
     * Sets a collection of FDFieldValue objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $fDFieldValues A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return FDRecord The current object (for fluent API support)
     */
    public function setFDFieldValues(PropelCollection $fDFieldValues, PropelPDO $con = null)
    {
        $fDFieldValuesToDelete = $this->getFDFieldValues(new Criteria(), $con)->diff($fDFieldValues);

        $this->fDFieldValuesScheduledForDeletion = unserialize(serialize($fDFieldValuesToDelete));

        foreach ($fDFieldValuesToDelete as $fDFieldValueRemoved) {
            $fDFieldValueRemoved->setFDRecord(null);
        }

        $this->collFDFieldValues = null;
        foreach ($fDFieldValues as $fDFieldValue) {
            $this->addFDFieldValue($fDFieldValue);
        }

        $this->collFDFieldValues = $fDFieldValues;
        $this->collFDFieldValuesPartial = false;

        return $this;
    }

    /**
     * Returns the number of related FDFieldValue objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related FDFieldValue objects.
     * @throws PropelException
     */
    public function countFDFieldValues(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collFDFieldValuesPartial && !$this->isNew();
        if (null === $this->collFDFieldValues || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collFDFieldValues) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getFDFieldValues());
            }
            $query = FDFieldValueQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByFDRecord($this)
                ->count($con);
        }

        return count($this->collFDFieldValues);
    }

    /**
     * Method called to associate a FDFieldValue object to this object
     * through the FDFieldValue foreign key attribute.
     *
     * @param    FDFieldValue $l FDFieldValue
     * @return FDRecord The current object (for fluent API support)
     */
    public function addFDFieldValue(FDFieldValue $l)
    {
        if ($this->collFDFieldValues === null) {
            $this->initFDFieldValues();
            $this->collFDFieldValuesPartial = true;
        }
        if (!in_array($l, $this->collFDFieldValues->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddFDFieldValue($l);
        }

        return $this;
    }

    /**
     * @param	FDFieldValue $fDFieldValue The fDFieldValue object to add.
     */
    protected function doAddFDFieldValue($fDFieldValue)
    {
        $this->collFDFieldValues[]= $fDFieldValue;
        $fDFieldValue->setFDRecord($this);
    }

    /**
     * @param	FDFieldValue $fDFieldValue The fDFieldValue object to remove.
     * @return FDRecord The current object (for fluent API support)
     */
    public function removeFDFieldValue($fDFieldValue)
    {
        if ($this->getFDFieldValues()->contains($fDFieldValue)) {
            $this->collFDFieldValues->remove($this->collFDFieldValues->search($fDFieldValue));
            if (null === $this->fDFieldValuesScheduledForDeletion) {
                $this->fDFieldValuesScheduledForDeletion = clone $this->collFDFieldValues;
                $this->fDFieldValuesScheduledForDeletion->clear();
            }
            $this->fDFieldValuesScheduledForDeletion[]= clone $fDFieldValue;
            $fDFieldValue->setFDRecord(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this FDRecord is new, it will return
     * an empty collection; or if this FDRecord has previously
     * been saved, it will retrieve related FDFieldValues from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in FDRecord.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|FDFieldValue[] List of FDFieldValue objects
     */
    public function getFDFieldValuesJoinFDField($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = FDFieldValueQuery::create(null, $criteria);
        $query->joinWith('FDField', $join_behavior);

        return $this->getFDFieldValues($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->flatdirectory_id = null;
        $this->flatdirectory_code = null;
        $this->order = null;
        $this->name = null;
        $this->description = null;
        $this->datestart = null;
        $this->dateend = null;
        $this->alreadyInSave = false;
        $this->alreadyInValidation = false;
        $this->alreadyInClearAllReferencesDeep = false;
        $this->clearAllReferences();
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
            if ($this->collActionPropertyFDRecords) {
                foreach ($this->collActionPropertyFDRecords as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collFDFieldValues) {
                foreach ($this->collFDFieldValues as $o) {
                    $o->clearAllReferences($deep);
                }
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionPropertyFDRecords instanceof PropelCollection) {
            $this->collActionPropertyFDRecords->clearIterator();
        }
        $this->collActionPropertyFDRecords = null;
        if ($this->collFDFieldValues instanceof PropelCollection) {
            $this->collFDFieldValues->clearIterator();
        }
        $this->collFDFieldValues = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(FDRecordPeer::DEFAULT_STRING_FORMAT);
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
