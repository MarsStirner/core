<?php

namespace Webmis\Models\om;

use \BaseObject;
use \BasePeer;
use \Criteria;
use \Exception;
use \PDO;
use \Persistent;
use \Propel;
use \PropelCollection;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\ClientQuoting;
use Webmis\Models\ClientQuotingQuery;
use Webmis\Models\MkbQuotaTypePacientModel;
use Webmis\Models\MkbQuotaTypePacientModelQuery;
use Webmis\Models\QuotaType;
use Webmis\Models\QuotaTypeQuery;
use Webmis\Models\RbPacientModel;
use Webmis\Models\RbPacientModelPeer;
use Webmis\Models\RbPacientModelQuery;
use Webmis\Models\RbTreatment;
use Webmis\Models\RbTreatmentQuery;

/**
 * Base class that represents a row from the 'rbPacientModel' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbPacientModel extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\RbPacientModelPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        RbPacientModelPeer
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
     * The value for the quotatype_id field.
     * @var        int
     */
    protected $quotatype_id;

    /**
     * @var        QuotaType
     */
    protected $aQuotaType;

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
     * @var        PropelObjectCollection|RbTreatment[] Collection to store aggregation of RbTreatment objects.
     */
    protected $collRbTreatments;
    protected $collRbTreatmentsPartial;

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
    protected $rbTreatmentsScheduledForDeletion = null;

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
     * Get the [quotatype_id] column value.
     *
     * @return int
     */
    public function getquotaTypeId()
    {
        return $this->quotatype_id;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = RbPacientModelPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = RbPacientModelPeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = RbPacientModelPeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [quotatype_id] column.
     *
     * @param int $v new value
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setquotaTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->quotatype_id !== $v) {
            $this->quotatype_id = $v;
            $this->modifiedColumns[] = RbPacientModelPeer::QUOTATYPE_ID;
        }

        if ($this->aQuotaType !== null && $this->aQuotaType->getid() !== $v) {
            $this->aQuotaType = null;
        }


        return $this;
    } // setquotaTypeId()

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
            $this->code = ($row[$startcol + 1] !== null) ? (string) $row[$startcol + 1] : null;
            $this->name = ($row[$startcol + 2] !== null) ? (string) $row[$startcol + 2] : null;
            $this->quotatype_id = ($row[$startcol + 3] !== null) ? (int) $row[$startcol + 3] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 4; // 4 = RbPacientModelPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating RbPacientModel object", $e);
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
            $con = Propel::getConnection(RbPacientModelPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = RbPacientModelPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aQuotaType = null;
            $this->collClientQuotings = null;

            $this->collMkbQuotaTypePacientModels = null;

            $this->collRbTreatments = null;

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
            $con = Propel::getConnection(RbPacientModelPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = RbPacientModelQuery::create()
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
            $con = Propel::getConnection(RbPacientModelPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                RbPacientModelPeer::addInstanceToPool($this);
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

            if ($this->clientQuotingsScheduledForDeletion !== null) {
                if (!$this->clientQuotingsScheduledForDeletion->isEmpty()) {
                    ClientQuotingQuery::create()
                        ->filterByPrimaryKeys($this->clientQuotingsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
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

            if ($this->rbTreatmentsScheduledForDeletion !== null) {
                if (!$this->rbTreatmentsScheduledForDeletion->isEmpty()) {
                    RbTreatmentQuery::create()
                        ->filterByPrimaryKeys($this->rbTreatmentsScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->rbTreatmentsScheduledForDeletion = null;
                }
            }

            if ($this->collRbTreatments !== null) {
                foreach ($this->collRbTreatments as $referrerFK) {
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

        $this->modifiedColumns[] = RbPacientModelPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . RbPacientModelPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(RbPacientModelPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(RbPacientModelPeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(RbPacientModelPeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(RbPacientModelPeer::QUOTATYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`quotaType_id`';
        }

        $sql = sprintf(
            'INSERT INTO `rbPacientModel` (%s) VALUES (%s)',
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
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`quotaType_id`':
                        $stmt->bindValue($identifier, $this->quotatype_id, PDO::PARAM_INT);
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

            if ($this->aQuotaType !== null) {
                if (!$this->aQuotaType->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aQuotaType->getValidationFailures());
                }
            }


            if (($retval = RbPacientModelPeer::doValidate($this, $columns)) !== true) {
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

                if ($this->collRbTreatments !== null) {
                    foreach ($this->collRbTreatments as $referrerFK) {
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
        $pos = RbPacientModelPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getcode();
                break;
            case 2:
                return $this->getname();
                break;
            case 3:
                return $this->getquotaTypeId();
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
        if (isset($alreadyDumpedObjects['RbPacientModel'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['RbPacientModel'][$this->getPrimaryKey()] = true;
        $keys = RbPacientModelPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcode(),
            $keys[2] => $this->getname(),
            $keys[3] => $this->getquotaTypeId(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aQuotaType) {
                $result['QuotaType'] = $this->aQuotaType->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collClientQuotings) {
                $result['ClientQuotings'] = $this->collClientQuotings->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collMkbQuotaTypePacientModels) {
                $result['MkbQuotaTypePacientModels'] = $this->collMkbQuotaTypePacientModels->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collRbTreatments) {
                $result['RbTreatments'] = $this->collRbTreatments->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = RbPacientModelPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setcode($value);
                break;
            case 2:
                $this->setname($value);
                break;
            case 3:
                $this->setquotaTypeId($value);
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
        $keys = RbPacientModelPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcode($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setname($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setquotaTypeId($arr[$keys[3]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(RbPacientModelPeer::DATABASE_NAME);

        if ($this->isColumnModified(RbPacientModelPeer::ID)) $criteria->add(RbPacientModelPeer::ID, $this->id);
        if ($this->isColumnModified(RbPacientModelPeer::CODE)) $criteria->add(RbPacientModelPeer::CODE, $this->code);
        if ($this->isColumnModified(RbPacientModelPeer::NAME)) $criteria->add(RbPacientModelPeer::NAME, $this->name);
        if ($this->isColumnModified(RbPacientModelPeer::QUOTATYPE_ID)) $criteria->add(RbPacientModelPeer::QUOTATYPE_ID, $this->quotatype_id);

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
        $criteria = new Criteria(RbPacientModelPeer::DATABASE_NAME);
        $criteria->add(RbPacientModelPeer::ID, $this->id);

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
     * @param object $copyObj An object of RbPacientModel (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());
        $copyObj->setquotaTypeId($this->getquotaTypeId());

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

            foreach ($this->getRbTreatments() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addRbTreatment($relObj->copy($deepCopy));
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
     * @return RbPacientModel Clone of current object.
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
     * @return RbPacientModelPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new RbPacientModelPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a QuotaType object.
     *
     * @param             QuotaType $v
     * @return RbPacientModel The current object (for fluent API support)
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
            $v->addRbPacientModel($this);
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
                $this->aQuotaType->addRbPacientModels($this);
             */
        }

        return $this->aQuotaType;
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
        if ('RbTreatment' == $relationName) {
            $this->initRbTreatments();
        }
    }

    /**
     * Clears out the collClientQuotings collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return RbPacientModel The current object (for fluent API support)
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
     * If this RbPacientModel is new, it will return
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
                    ->filterByRbPacientModel($this)
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
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setClientQuotings(PropelCollection $clientQuotings, PropelPDO $con = null)
    {
        $clientQuotingsToDelete = $this->getClientQuotings(new Criteria(), $con)->diff($clientQuotings);

        $this->clientQuotingsScheduledForDeletion = unserialize(serialize($clientQuotingsToDelete));

        foreach ($clientQuotingsToDelete as $clientQuotingRemoved) {
            $clientQuotingRemoved->setRbPacientModel(null);
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
                ->filterByRbPacientModel($this)
                ->count($con);
        }

        return count($this->collClientQuotings);
    }

    /**
     * Method called to associate a ClientQuoting object to this object
     * through the ClientQuoting foreign key attribute.
     *
     * @param    ClientQuoting $l ClientQuoting
     * @return RbPacientModel The current object (for fluent API support)
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
        $clientQuoting->setRbPacientModel($this);
    }

    /**
     * @param	ClientQuoting $clientQuoting The clientQuoting object to remove.
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function removeClientQuoting($clientQuoting)
    {
        if ($this->getClientQuotings()->contains($clientQuoting)) {
            $this->collClientQuotings->remove($this->collClientQuotings->search($clientQuoting));
            if (null === $this->clientQuotingsScheduledForDeletion) {
                $this->clientQuotingsScheduledForDeletion = clone $this->collClientQuotings;
                $this->clientQuotingsScheduledForDeletion->clear();
            }
            $this->clientQuotingsScheduledForDeletion[]= clone $clientQuoting;
            $clientQuoting->setRbPacientModel(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this RbPacientModel is new, it will return
     * an empty collection; or if this RbPacientModel has previously
     * been saved, it will retrieve related ClientQuotings from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in RbPacientModel.
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
     * Otherwise if this RbPacientModel is new, it will return
     * an empty collection; or if this RbPacientModel has previously
     * been saved, it will retrieve related ClientQuotings from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in RbPacientModel.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ClientQuoting[] List of ClientQuoting objects
     */
    public function getClientQuotingsJoinQuotaType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ClientQuotingQuery::create(null, $criteria);
        $query->joinWith('QuotaType', $join_behavior);

        return $this->getClientQuotings($query, $con);
    }

    /**
     * Clears out the collMkbQuotaTypePacientModels collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return RbPacientModel The current object (for fluent API support)
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
     * If this RbPacientModel is new, it will return
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
                    ->filterByRbPacientModel($this)
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
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setMkbQuotaTypePacientModels(PropelCollection $mkbQuotaTypePacientModels, PropelPDO $con = null)
    {
        $mkbQuotaTypePacientModelsToDelete = $this->getMkbQuotaTypePacientModels(new Criteria(), $con)->diff($mkbQuotaTypePacientModels);

        $this->mkbQuotaTypePacientModelsScheduledForDeletion = unserialize(serialize($mkbQuotaTypePacientModelsToDelete));

        foreach ($mkbQuotaTypePacientModelsToDelete as $mkbQuotaTypePacientModelRemoved) {
            $mkbQuotaTypePacientModelRemoved->setRbPacientModel(null);
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
                ->filterByRbPacientModel($this)
                ->count($con);
        }

        return count($this->collMkbQuotaTypePacientModels);
    }

    /**
     * Method called to associate a MkbQuotaTypePacientModel object to this object
     * through the MkbQuotaTypePacientModel foreign key attribute.
     *
     * @param    MkbQuotaTypePacientModel $l MkbQuotaTypePacientModel
     * @return RbPacientModel The current object (for fluent API support)
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
        $mkbQuotaTypePacientModel->setRbPacientModel($this);
    }

    /**
     * @param	MkbQuotaTypePacientModel $mkbQuotaTypePacientModel The mkbQuotaTypePacientModel object to remove.
     * @return RbPacientModel The current object (for fluent API support)
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
            $mkbQuotaTypePacientModel->setRbPacientModel(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this RbPacientModel is new, it will return
     * an empty collection; or if this RbPacientModel has previously
     * been saved, it will retrieve related MkbQuotaTypePacientModels from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in RbPacientModel.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|MkbQuotaTypePacientModel[] List of MkbQuotaTypePacientModel objects
     */
    public function getMkbQuotaTypePacientModelsJoinQuotaType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = MkbQuotaTypePacientModelQuery::create(null, $criteria);
        $query->joinWith('QuotaType', $join_behavior);

        return $this->getMkbQuotaTypePacientModels($query, $con);
    }

    /**
     * Clears out the collRbTreatments collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return RbPacientModel The current object (for fluent API support)
     * @see        addRbTreatments()
     */
    public function clearRbTreatments()
    {
        $this->collRbTreatments = null; // important to set this to null since that means it is uninitialized
        $this->collRbTreatmentsPartial = null;

        return $this;
    }

    /**
     * reset is the collRbTreatments collection loaded partially
     *
     * @return void
     */
    public function resetPartialRbTreatments($v = true)
    {
        $this->collRbTreatmentsPartial = $v;
    }

    /**
     * Initializes the collRbTreatments collection.
     *
     * By default this just sets the collRbTreatments collection to an empty array (like clearcollRbTreatments());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initRbTreatments($overrideExisting = true)
    {
        if (null !== $this->collRbTreatments && !$overrideExisting) {
            return;
        }
        $this->collRbTreatments = new PropelObjectCollection();
        $this->collRbTreatments->setModel('RbTreatment');
    }

    /**
     * Gets an array of RbTreatment objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this RbPacientModel is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|RbTreatment[] List of RbTreatment objects
     * @throws PropelException
     */
    public function getRbTreatments($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collRbTreatmentsPartial && !$this->isNew();
        if (null === $this->collRbTreatments || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collRbTreatments) {
                // return empty collection
                $this->initRbTreatments();
            } else {
                $collRbTreatments = RbTreatmentQuery::create(null, $criteria)
                    ->filterByRbPacientModel($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collRbTreatmentsPartial && count($collRbTreatments)) {
                      $this->initRbTreatments(false);

                      foreach($collRbTreatments as $obj) {
                        if (false == $this->collRbTreatments->contains($obj)) {
                          $this->collRbTreatments->append($obj);
                        }
                      }

                      $this->collRbTreatmentsPartial = true;
                    }

                    $collRbTreatments->getInternalIterator()->rewind();
                    return $collRbTreatments;
                }

                if($partial && $this->collRbTreatments) {
                    foreach($this->collRbTreatments as $obj) {
                        if($obj->isNew()) {
                            $collRbTreatments[] = $obj;
                        }
                    }
                }

                $this->collRbTreatments = $collRbTreatments;
                $this->collRbTreatmentsPartial = false;
            }
        }

        return $this->collRbTreatments;
    }

    /**
     * Sets a collection of RbTreatment objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rbTreatments A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function setRbTreatments(PropelCollection $rbTreatments, PropelPDO $con = null)
    {
        $rbTreatmentsToDelete = $this->getRbTreatments(new Criteria(), $con)->diff($rbTreatments);

        $this->rbTreatmentsScheduledForDeletion = unserialize(serialize($rbTreatmentsToDelete));

        foreach ($rbTreatmentsToDelete as $rbTreatmentRemoved) {
            $rbTreatmentRemoved->setRbPacientModel(null);
        }

        $this->collRbTreatments = null;
        foreach ($rbTreatments as $rbTreatment) {
            $this->addRbTreatment($rbTreatment);
        }

        $this->collRbTreatments = $rbTreatments;
        $this->collRbTreatmentsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related RbTreatment objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related RbTreatment objects.
     * @throws PropelException
     */
    public function countRbTreatments(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collRbTreatmentsPartial && !$this->isNew();
        if (null === $this->collRbTreatments || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collRbTreatments) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getRbTreatments());
            }
            $query = RbTreatmentQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByRbPacientModel($this)
                ->count($con);
        }

        return count($this->collRbTreatments);
    }

    /**
     * Method called to associate a RbTreatment object to this object
     * through the RbTreatment foreign key attribute.
     *
     * @param    RbTreatment $l RbTreatment
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function addRbTreatment(RbTreatment $l)
    {
        if ($this->collRbTreatments === null) {
            $this->initRbTreatments();
            $this->collRbTreatmentsPartial = true;
        }
        if (!in_array($l, $this->collRbTreatments->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddRbTreatment($l);
        }

        return $this;
    }

    /**
     * @param	RbTreatment $rbTreatment The rbTreatment object to add.
     */
    protected function doAddRbTreatment($rbTreatment)
    {
        $this->collRbTreatments[]= $rbTreatment;
        $rbTreatment->setRbPacientModel($this);
    }

    /**
     * @param	RbTreatment $rbTreatment The rbTreatment object to remove.
     * @return RbPacientModel The current object (for fluent API support)
     */
    public function removeRbTreatment($rbTreatment)
    {
        if ($this->getRbTreatments()->contains($rbTreatment)) {
            $this->collRbTreatments->remove($this->collRbTreatments->search($rbTreatment));
            if (null === $this->rbTreatmentsScheduledForDeletion) {
                $this->rbTreatmentsScheduledForDeletion = clone $this->collRbTreatments;
                $this->rbTreatmentsScheduledForDeletion->clear();
            }
            $this->rbTreatmentsScheduledForDeletion[]= clone $rbTreatment;
            $rbTreatment->setRbPacientModel(null);
        }

        return $this;
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->code = null;
        $this->name = null;
        $this->quotatype_id = null;
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
            if ($this->collRbTreatments) {
                foreach ($this->collRbTreatments as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->aQuotaType instanceof Persistent) {
              $this->aQuotaType->clearAllReferences($deep);
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
        if ($this->collRbTreatments instanceof PropelCollection) {
            $this->collRbTreatments->clearIterator();
        }
        $this->collRbTreatments = null;
        $this->aQuotaType = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(RbPacientModelPeer::DEFAULT_STRING_FORMAT);
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
