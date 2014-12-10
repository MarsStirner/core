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
use Webmis\Models\RbStorage;
use Webmis\Models\RbStorageQuery;
use Webmis\Models\RlsBalanceOfGoods;
use Webmis\Models\RlsBalanceOfGoodsPeer;
use Webmis\Models\RlsBalanceOfGoodsQuery;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenQuery;

/**
 * Base class that represents a row from the 'rlsBalanceOfGoods' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRlsBalanceOfGoods extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\RlsBalanceOfGoodsPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        RlsBalanceOfGoodsPeer
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
     * The value for the rlsnomen_id field.
     * @var        int
     */
    protected $rlsnomen_id;

    /**
     * The value for the value field.
     * @var        double
     */
    protected $value;

    /**
     * The value for the bestbefore field.
     * @var        string
     */
    protected $bestbefore;

    /**
     * The value for the disabled field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $disabled;

    /**
     * The value for the updatedatetime field.
     * @var        string
     */
    protected $updatedatetime;

    /**
     * The value for the storage_id field.
     * @var        int
     */
    protected $storage_id;

    /**
     * @var        RbStorage
     */
    protected $aRbStorage;

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
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->disabled = 0;
    }

    /**
     * Initializes internal state of BaseRlsBalanceOfGoods object.
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
     * Get the [rlsnomen_id] column value.
     *
     * @return int
     */
    public function getrlsNomenId()
    {
        return $this->rlsnomen_id;
    }

    /**
     * Get the [value] column value.
     *
     * @return double
     */
    public function getvalue()
    {
        return $this->value;
    }

    /**
     * Get the [optionally formatted] temporal [bestbefore] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getbestBefore($format = '%Y-%m-%d')
    {
        if ($this->bestbefore === null) {
            return null;
        }

        if ($this->bestbefore === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->bestbefore);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->bestbefore, true), $x);
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
     * Get the [disabled] column value.
     *
     * @return int
     */
    public function getdisabled()
    {
        return $this->disabled;
    }

    /**
     * Get the [optionally formatted] temporal [updatedatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getupdateDateTime($format = 'Y-m-d H:i:s')
    {
        if ($this->updatedatetime === null) {
            return null;
        }

        if ($this->updatedatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->updatedatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->updatedatetime, true), $x);
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
     * Get the [storage_id] column value.
     *
     * @return int
     */
    public function getstorageId()
    {
        return $this->storage_id;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [rlsnomen_id] column.
     *
     * @param int $v new value
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setrlsNomenId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->rlsnomen_id !== $v) {
            $this->rlsnomen_id = $v;
            $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::RLSNOMEN_ID;
        }

        if ($this->arlsNomen !== null && $this->arlsNomen->getid() !== $v) {
            $this->arlsNomen = null;
        }


        return $this;
    } // setrlsNomenId()

    /**
     * Set the value of [value] column.
     *
     * @param double $v new value
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setvalue($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (double) $v;
        }

        if ($this->value !== $v) {
            $this->value = $v;
            $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::VALUE;
        }


        return $this;
    } // setvalue()

    /**
     * Sets the value of [bestbefore] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setbestBefore($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->bestbefore !== null || $dt !== null) {
            $currentDateAsString = ($this->bestbefore !== null && $tmpDt = new DateTime($this->bestbefore)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->bestbefore = $newDateAsString;
                $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::BESTBEFORE;
            }
        } // if either are not null


        return $this;
    } // setbestBefore()

    /**
     * Set the value of [disabled] column.
     *
     * @param int $v new value
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setdisabled($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->disabled !== $v) {
            $this->disabled = $v;
            $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::DISABLED;
        }


        return $this;
    } // setdisabled()

    /**
     * Sets the value of [updatedatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setupdateDateTime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->updatedatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->updatedatetime !== null && $tmpDt = new DateTime($this->updatedatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->updatedatetime = $newDateAsString;
                $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::UPDATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setupdateDateTime()

    /**
     * Set the value of [storage_id] column.
     *
     * @param int $v new value
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     */
    public function setstorageId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->storage_id !== $v) {
            $this->storage_id = $v;
            $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::STORAGE_ID;
        }

        if ($this->aRbStorage !== null && $this->aRbStorage->getid() !== $v) {
            $this->aRbStorage = null;
        }


        return $this;
    } // setstorageId()

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
            if ($this->disabled !== 0) {
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
            $this->rlsnomen_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->value = ($row[$startcol + 2] !== null) ? (double) $row[$startcol + 2] : null;
            $this->bestbefore = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->disabled = ($row[$startcol + 4] !== null) ? (int) $row[$startcol + 4] : null;
            $this->updatedatetime = ($row[$startcol + 5] !== null) ? (string) $row[$startcol + 5] : null;
            $this->storage_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 7; // 7 = RlsBalanceOfGoodsPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating RlsBalanceOfGoods object", $e);
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

        if ($this->arlsNomen !== null && $this->rlsnomen_id !== $this->arlsNomen->getid()) {
            $this->arlsNomen = null;
        }
        if ($this->aRbStorage !== null && $this->storage_id !== $this->aRbStorage->getid()) {
            $this->aRbStorage = null;
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
            $con = Propel::getConnection(RlsBalanceOfGoodsPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = RlsBalanceOfGoodsPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aRbStorage = null;
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
            $con = Propel::getConnection(RlsBalanceOfGoodsPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = RlsBalanceOfGoodsQuery::create()
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
            $con = Propel::getConnection(RlsBalanceOfGoodsPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                RlsBalanceOfGoodsPeer::addInstanceToPool($this);
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

            if ($this->aRbStorage !== null) {
                if ($this->aRbStorage->isModified() || $this->aRbStorage->isNew()) {
                    $affectedRows += $this->aRbStorage->save($con);
                }
                $this->setRbStorage($this->aRbStorage);
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

        $this->modifiedColumns[] = RlsBalanceOfGoodsPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . RlsBalanceOfGoodsPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::RLSNOMEN_ID)) {
            $modifiedColumns[':p' . $index++]  = '`rlsNomen_id`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::VALUE)) {
            $modifiedColumns[':p' . $index++]  = '`value`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::BESTBEFORE)) {
            $modifiedColumns[':p' . $index++]  = '`bestBefore`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::DISABLED)) {
            $modifiedColumns[':p' . $index++]  = '`disabled`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::UPDATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`updateDateTime`';
        }
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::STORAGE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`storage_id`';
        }

        $sql = sprintf(
            'INSERT INTO `rlsBalanceOfGoods` (%s) VALUES (%s)',
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
                    case '`rlsNomen_id`':
                        $stmt->bindValue($identifier, $this->rlsnomen_id, PDO::PARAM_INT);
                        break;
                    case '`value`':
                        $stmt->bindValue($identifier, $this->value, PDO::PARAM_STR);
                        break;
                    case '`bestBefore`':
                        $stmt->bindValue($identifier, $this->bestbefore, PDO::PARAM_STR);
                        break;
                    case '`disabled`':
                        $stmt->bindValue($identifier, $this->disabled, PDO::PARAM_INT);
                        break;
                    case '`updateDateTime`':
                        $stmt->bindValue($identifier, $this->updatedatetime, PDO::PARAM_STR);
                        break;
                    case '`storage_id`':
                        $stmt->bindValue($identifier, $this->storage_id, PDO::PARAM_INT);
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

            if ($this->aRbStorage !== null) {
                if (!$this->aRbStorage->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aRbStorage->getValidationFailures());
                }
            }

            if ($this->arlsNomen !== null) {
                if (!$this->arlsNomen->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsNomen->getValidationFailures());
                }
            }


            if (($retval = RlsBalanceOfGoodsPeer::doValidate($this, $columns)) !== true) {
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
        $pos = RlsBalanceOfGoodsPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getrlsNomenId();
                break;
            case 2:
                return $this->getvalue();
                break;
            case 3:
                return $this->getbestBefore();
                break;
            case 4:
                return $this->getdisabled();
                break;
            case 5:
                return $this->getupdateDateTime();
                break;
            case 6:
                return $this->getstorageId();
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
        if (isset($alreadyDumpedObjects['RlsBalanceOfGoods'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['RlsBalanceOfGoods'][$this->getPrimaryKey()] = true;
        $keys = RlsBalanceOfGoodsPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getrlsNomenId(),
            $keys[2] => $this->getvalue(),
            $keys[3] => $this->getbestBefore(),
            $keys[4] => $this->getdisabled(),
            $keys[5] => $this->getupdateDateTime(),
            $keys[6] => $this->getstorageId(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aRbStorage) {
                $result['RbStorage'] = $this->aRbStorage->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
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
        $pos = RlsBalanceOfGoodsPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setrlsNomenId($value);
                break;
            case 2:
                $this->setvalue($value);
                break;
            case 3:
                $this->setbestBefore($value);
                break;
            case 4:
                $this->setdisabled($value);
                break;
            case 5:
                $this->setupdateDateTime($value);
                break;
            case 6:
                $this->setstorageId($value);
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
        $keys = RlsBalanceOfGoodsPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setrlsNomenId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setvalue($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setbestBefore($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setdisabled($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setupdateDateTime($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setstorageId($arr[$keys[6]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(RlsBalanceOfGoodsPeer::DATABASE_NAME);

        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::ID)) $criteria->add(RlsBalanceOfGoodsPeer::ID, $this->id);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::RLSNOMEN_ID)) $criteria->add(RlsBalanceOfGoodsPeer::RLSNOMEN_ID, $this->rlsnomen_id);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::VALUE)) $criteria->add(RlsBalanceOfGoodsPeer::VALUE, $this->value);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::BESTBEFORE)) $criteria->add(RlsBalanceOfGoodsPeer::BESTBEFORE, $this->bestbefore);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::DISABLED)) $criteria->add(RlsBalanceOfGoodsPeer::DISABLED, $this->disabled);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::UPDATEDATETIME)) $criteria->add(RlsBalanceOfGoodsPeer::UPDATEDATETIME, $this->updatedatetime);
        if ($this->isColumnModified(RlsBalanceOfGoodsPeer::STORAGE_ID)) $criteria->add(RlsBalanceOfGoodsPeer::STORAGE_ID, $this->storage_id);

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
        $criteria = new Criteria(RlsBalanceOfGoodsPeer::DATABASE_NAME);
        $criteria->add(RlsBalanceOfGoodsPeer::ID, $this->id);

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
     * @param object $copyObj An object of RlsBalanceOfGoods (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setrlsNomenId($this->getrlsNomenId());
        $copyObj->setvalue($this->getvalue());
        $copyObj->setbestBefore($this->getbestBefore());
        $copyObj->setdisabled($this->getdisabled());
        $copyObj->setupdateDateTime($this->getupdateDateTime());
        $copyObj->setstorageId($this->getstorageId());

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
     * @return RlsBalanceOfGoods Clone of current object.
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
     * @return RlsBalanceOfGoodsPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new RlsBalanceOfGoodsPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a RbStorage object.
     *
     * @param             RbStorage $v
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     * @throws PropelException
     */
    public function setRbStorage(RbStorage $v = null)
    {
        if ($v === null) {
            $this->setstorageId(NULL);
        } else {
            $this->setstorageId($v->getid());
        }

        $this->aRbStorage = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the RbStorage object, it will not be re-added.
        if ($v !== null) {
            $v->addRlsBalanceOfGoods($this);
        }


        return $this;
    }


    /**
     * Get the associated RbStorage object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return RbStorage The associated RbStorage object.
     * @throws PropelException
     */
    public function getRbStorage(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aRbStorage === null && ($this->storage_id !== null) && $doQuery) {
            $this->aRbStorage = RbStorageQuery::create()->findPk($this->storage_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aRbStorage->addRlsBalanceOfGoodss($this);
             */
        }

        return $this->aRbStorage;
    }

    /**
     * Declares an association between this object and a rlsNomen object.
     *
     * @param             rlsNomen $v
     * @return RlsBalanceOfGoods The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsNomen(rlsNomen $v = null)
    {
        if ($v === null) {
            $this->setrlsNomenId(NULL);
        } else {
            $this->setrlsNomenId($v->getid());
        }

        $this->arlsNomen = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsNomen object, it will not be re-added.
        if ($v !== null) {
            $v->addRlsBalanceOfGoods($this);
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
        if ($this->arlsNomen === null && ($this->rlsnomen_id !== null) && $doQuery) {
            $this->arlsNomen = rlsNomenQuery::create()->findPk($this->rlsnomen_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsNomen->addRlsBalanceOfGoodss($this);
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
        $this->rlsnomen_id = null;
        $this->value = null;
        $this->bestbefore = null;
        $this->disabled = null;
        $this->updatedatetime = null;
        $this->storage_id = null;
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
            if ($this->aRbStorage instanceof Persistent) {
              $this->aRbStorage->clearAllReferences($deep);
            }
            if ($this->arlsNomen instanceof Persistent) {
              $this->arlsNomen->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        $this->aRbStorage = null;
        $this->arlsNomen = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(RlsBalanceOfGoodsPeer::DEFAULT_STRING_FORMAT);
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
