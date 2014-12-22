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
use Webmis\Models\Action;
use Webmis\Models\ActionQuery;
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentPeer;
use Webmis\Models\DrugComponentQuery;
use Webmis\Models\rbUnit;
use Webmis\Models\rbUnitQuery;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenQuery;

/**
 * Base class that represents a row from the 'DrugComponent' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseDrugComponent extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\DrugComponentPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        DrugComponentPeer
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
     * The value for the nomen field.
     * @var        int
     */
    protected $nomen;

    /**
     * The value for the name field.
     * @var        string
     */
    protected $name;

    /**
     * The value for the dose field.
     * @var        double
     */
    protected $dose;

    /**
     * The value for the unit field.
     * @var        int
     */
    protected $unit;

    /**
     * The value for the createdatetime field.
     * @var        string
     */
    protected $createdatetime;

    /**
     * The value for the canceldatetime field.
     * @var        string
     */
    protected $canceldatetime;

    /**
     * @var        Action
     */
    protected $aAction;

    /**
     * @var        rbUnit
     */
    protected $arbUnit;

    /**
     * @var        rlsNomen
     */
    protected $arlsNomen;

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
     * Get the [nomen] column value.
     *
     * @return int
     */
    public function getnomen()
    {
        return $this->nomen;
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
     * Get the [dose] column value.
     *
     * @return double
     */
    public function getdose()
    {
        return $this->dose;
    }

    /**
     * Get the [unit] column value.
     *
     * @return int
     */
    public function getunit()
    {
        return $this->unit;
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
    public function getcreateDateTime($format = 'Y-m-d H:i:s')
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
     * Get the [optionally formatted] temporal [canceldatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getcancelDateTime($format = 'Y-m-d H:i:s')
    {
        if ($this->canceldatetime === null) {
            return null;
        }

        if ($this->canceldatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->canceldatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->canceldatetime, true), $x);
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
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = DrugComponentPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [action_id] column.
     *
     * @param int $v new value
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setactionId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->action_id !== $v) {
            $this->action_id = $v;
            $this->modifiedColumns[] = DrugComponentPeer::ACTION_ID;
        }

        if ($this->aAction !== null && $this->aAction->getid() !== $v) {
            $this->aAction = null;
        }


        return $this;
    } // setactionId()

    /**
     * Set the value of [nomen] column.
     *
     * @param int $v new value
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setnomen($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->nomen !== $v) {
            $this->nomen = $v;
            $this->modifiedColumns[] = DrugComponentPeer::NOMEN;
        }

        if ($this->arlsNomen !== null && $this->arlsNomen->getid() !== $v) {
            $this->arlsNomen = null;
        }


        return $this;
    } // setnomen()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = DrugComponentPeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [dose] column.
     *
     * @param double $v new value
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setdose($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (double) $v;
        }

        if ($this->dose !== $v) {
            $this->dose = $v;
            $this->modifiedColumns[] = DrugComponentPeer::DOSE;
        }


        return $this;
    } // setdose()

    /**
     * Set the value of [unit] column.
     *
     * @param int $v new value
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setunit($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->unit !== $v) {
            $this->unit = $v;
            $this->modifiedColumns[] = DrugComponentPeer::UNIT;
        }

        if ($this->arbUnit !== null && $this->arbUnit->getid() !== $v) {
            $this->arbUnit = null;
        }


        return $this;
    } // setunit()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setcreateDateTime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = DrugComponentPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDateTime()

    /**
     * Sets the value of [canceldatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return DrugComponent The current object (for fluent API support)
     */
    public function setcancelDateTime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->canceldatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->canceldatetime !== null && $tmpDt = new DateTime($this->canceldatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->canceldatetime = $newDateAsString;
                $this->modifiedColumns[] = DrugComponentPeer::CANCELDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcancelDateTime()

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
            $this->action_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->nomen = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->name = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->dose = ($row[$startcol + 4] !== null) ? (double) $row[$startcol + 4] : null;
            $this->unit = ($row[$startcol + 5] !== null) ? (int) $row[$startcol + 5] : null;
            $this->createdatetime = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->canceldatetime = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 8; // 8 = DrugComponentPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating DrugComponent object", $e);
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
        if ($this->arlsNomen !== null && $this->nomen !== $this->arlsNomen->getid()) {
            $this->arlsNomen = null;
        }
        if ($this->arbUnit !== null && $this->unit !== $this->arbUnit->getid()) {
            $this->arbUnit = null;
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
            $con = Propel::getConnection(DrugComponentPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = DrugComponentPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aAction = null;
            $this->arbUnit = null;
            $this->arlsNomen = null;
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
            $con = Propel::getConnection(DrugComponentPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = DrugComponentQuery::create()
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
            $con = Propel::getConnection(DrugComponentPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(DrugComponentPeer::CREATEDATETIME)) {
                    $this->setcreateDateTime(time());
                }
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
                DrugComponentPeer::addInstanceToPool($this);
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

            if ($this->arbUnit !== null) {
                if ($this->arbUnit->isModified() || $this->arbUnit->isNew()) {
                    $affectedRows += $this->arbUnit->save($con);
                }
                $this->setrbUnit($this->arbUnit);
            }

            if ($this->arlsNomen !== null) {
                if ($this->arlsNomen->isModified() || $this->arlsNomen->isNew()) {
                    $affectedRows += $this->arlsNomen->save($con);
                }
                $this->setrlsNomen($this->arlsNomen);
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

        $this->modifiedColumns[] = DrugComponentPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . DrugComponentPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(DrugComponentPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(DrugComponentPeer::ACTION_ID)) {
            $modifiedColumns[':p' . $index++]  = '`action_id`';
        }
        if ($this->isColumnModified(DrugComponentPeer::NOMEN)) {
            $modifiedColumns[':p' . $index++]  = '`nomen`';
        }
        if ($this->isColumnModified(DrugComponentPeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(DrugComponentPeer::DOSE)) {
            $modifiedColumns[':p' . $index++]  = '`dose`';
        }
        if ($this->isColumnModified(DrugComponentPeer::UNIT)) {
            $modifiedColumns[':p' . $index++]  = '`unit`';
        }
        if ($this->isColumnModified(DrugComponentPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDateTime`';
        }
        if ($this->isColumnModified(DrugComponentPeer::CANCELDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`cancelDateTime`';
        }

        $sql = sprintf(
            'INSERT INTO `DrugComponent` (%s) VALUES (%s)',
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
                    case '`nomen`':
                        $stmt->bindValue($identifier, $this->nomen, PDO::PARAM_INT);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`dose`':
                        $stmt->bindValue($identifier, $this->dose, PDO::PARAM_STR);
                        break;
                    case '`unit`':
                        $stmt->bindValue($identifier, $this->unit, PDO::PARAM_INT);
                        break;
                    case '`createDateTime`':
                        $stmt->bindValue($identifier, $this->createdatetime, PDO::PARAM_STR);
                        break;
                    case '`cancelDateTime`':
                        $stmt->bindValue($identifier, $this->canceldatetime, PDO::PARAM_STR);
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

            if ($this->arbUnit !== null) {
                if (!$this->arbUnit->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arbUnit->getValidationFailures());
                }
            }

            if ($this->arlsNomen !== null) {
                if (!$this->arlsNomen->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsNomen->getValidationFailures());
                }
            }


            if (($retval = DrugComponentPeer::doValidate($this, $columns)) !== true) {
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
        $pos = DrugComponentPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getnomen();
                break;
            case 3:
                return $this->getname();
                break;
            case 4:
                return $this->getdose();
                break;
            case 5:
                return $this->getunit();
                break;
            case 6:
                return $this->getcreateDateTime();
                break;
            case 7:
                return $this->getcancelDateTime();
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
        if (isset($alreadyDumpedObjects['DrugComponent'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['DrugComponent'][$this->getPrimaryKey()] = true;
        $keys = DrugComponentPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getactionId(),
            $keys[2] => $this->getnomen(),
            $keys[3] => $this->getname(),
            $keys[4] => $this->getdose(),
            $keys[5] => $this->getunit(),
            $keys[6] => $this->getcreateDateTime(),
            $keys[7] => $this->getcancelDateTime(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aAction) {
                $result['Action'] = $this->aAction->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arbUnit) {
                $result['rbUnit'] = $this->arbUnit->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsNomen) {
                $result['rlsNomen'] = $this->arlsNomen->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
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
        $pos = DrugComponentPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setnomen($value);
                break;
            case 3:
                $this->setname($value);
                break;
            case 4:
                $this->setdose($value);
                break;
            case 5:
                $this->setunit($value);
                break;
            case 6:
                $this->setcreateDateTime($value);
                break;
            case 7:
                $this->setcancelDateTime($value);
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
        $keys = DrugComponentPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setactionId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setnomen($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setname($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setdose($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setunit($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setcreateDateTime($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setcancelDateTime($arr[$keys[7]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(DrugComponentPeer::DATABASE_NAME);

        if ($this->isColumnModified(DrugComponentPeer::ID)) $criteria->add(DrugComponentPeer::ID, $this->id);
        if ($this->isColumnModified(DrugComponentPeer::ACTION_ID)) $criteria->add(DrugComponentPeer::ACTION_ID, $this->action_id);
        if ($this->isColumnModified(DrugComponentPeer::NOMEN)) $criteria->add(DrugComponentPeer::NOMEN, $this->nomen);
        if ($this->isColumnModified(DrugComponentPeer::NAME)) $criteria->add(DrugComponentPeer::NAME, $this->name);
        if ($this->isColumnModified(DrugComponentPeer::DOSE)) $criteria->add(DrugComponentPeer::DOSE, $this->dose);
        if ($this->isColumnModified(DrugComponentPeer::UNIT)) $criteria->add(DrugComponentPeer::UNIT, $this->unit);
        if ($this->isColumnModified(DrugComponentPeer::CREATEDATETIME)) $criteria->add(DrugComponentPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(DrugComponentPeer::CANCELDATETIME)) $criteria->add(DrugComponentPeer::CANCELDATETIME, $this->canceldatetime);

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
        $criteria = new Criteria(DrugComponentPeer::DATABASE_NAME);
        $criteria->add(DrugComponentPeer::ID, $this->id);

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
     * @param object $copyObj An object of DrugComponent (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setactionId($this->getactionId());
        $copyObj->setnomen($this->getnomen());
        $copyObj->setname($this->getname());
        $copyObj->setdose($this->getdose());
        $copyObj->setunit($this->getunit());
        $copyObj->setcreateDateTime($this->getcreateDateTime());
        $copyObj->setcancelDateTime($this->getcancelDateTime());

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
     * @return DrugComponent Clone of current object.
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
     * @return DrugComponentPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new DrugComponentPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a Action object.
     *
     * @param             Action $v
     * @return DrugComponent The current object (for fluent API support)
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
            $v->addDrugComponent($this);
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
                $this->aAction->addDrugComponents($this);
             */
        }

        return $this->aAction;
    }

    /**
     * Declares an association between this object and a rbUnit object.
     *
     * @param             rbUnit $v
     * @return DrugComponent The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrbUnit(rbUnit $v = null)
    {
        if ($v === null) {
            $this->setunit(NULL);
        } else {
            $this->setunit($v->getid());
        }

        $this->arbUnit = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rbUnit object, it will not be re-added.
        if ($v !== null) {
            $v->addDrugComponent($this);
        }


        return $this;
    }


    /**
     * Get the associated rbUnit object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rbUnit The associated rbUnit object.
     * @throws PropelException
     */
    public function getrbUnit(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arbUnit === null && ($this->unit !== null) && $doQuery) {
            $this->arbUnit = rbUnitQuery::create()->findPk($this->unit, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arbUnit->addDrugComponents($this);
             */
        }

        return $this->arbUnit;
    }

    /**
     * Declares an association between this object and a rlsNomen object.
     *
     * @param             rlsNomen $v
     * @return DrugComponent The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsNomen(rlsNomen $v = null)
    {
        if ($v === null) {
            $this->setnomen(NULL);
        } else {
            $this->setnomen($v->getid());
        }

        $this->arlsNomen = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsNomen object, it will not be re-added.
        if ($v !== null) {
            $v->addDrugComponent($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsNomen object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsNomen The associated rlsNomen object.
     * @throws PropelException
     */
    public function getrlsNomen(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsNomen === null && ($this->nomen !== null) && $doQuery) {
            $this->arlsNomen = rlsNomenQuery::create()->findPk($this->nomen, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsNomen->addDrugComponents($this);
             */
        }

        return $this->arlsNomen;
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->action_id = null;
        $this->nomen = null;
        $this->name = null;
        $this->dose = null;
        $this->unit = null;
        $this->createdatetime = null;
        $this->canceldatetime = null;
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
            if ($this->aAction instanceof Persistent) {
              $this->aAction->clearAllReferences($deep);
            }
            if ($this->arbUnit instanceof Persistent) {
              $this->arbUnit->clearAllReferences($deep);
            }
            if ($this->arlsNomen instanceof Persistent) {
              $this->arlsNomen->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        $this->aAction = null;
        $this->arbUnit = null;
        $this->arlsNomen = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(DrugComponentPeer::DEFAULT_STRING_FORMAT);
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
