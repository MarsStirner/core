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
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\ActionPropertyFDRecordQuery;
use Webmis\Models\FDField;
use Webmis\Models\FDFieldQuery;
use Webmis\Models\FDFieldValue;
use Webmis\Models\FDFieldValuePeer;
use Webmis\Models\FDFieldValueQuery;
use Webmis\Models\FDRecord;
use Webmis\Models\FDRecordQuery;

/**
 * Base class that represents a row from the 'FDFieldValue' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseFDFieldValue extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\FDFieldValuePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        FDFieldValuePeer
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
     * The value for the fdrecord_id field.
     * @var        int
     */
    protected $fdrecord_id;

    /**
     * The value for the fdfield_id field.
     * @var        int
     */
    protected $fdfield_id;

    /**
     * The value for the value field.
     * @var        string
     */
    protected $value;

    /**
     * @var        FDField
     */
    protected $aFDField;

    /**
     * @var        FDRecord
     */
    protected $aFDRecord;

    /**
     * @var        PropelObjectCollection|ActionPropertyFDRecord[] Collection to store aggregation of ActionPropertyFDRecord objects.
     */
    protected $collActionPropertyFDRecords;
    protected $collActionPropertyFDRecordsPartial;

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
     * Get the [id] column value.
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Get the [fdrecord_id] column value.
     *
     * @return int
     */
    public function getFDRecordId()
    {
        return $this->fdrecord_id;
    }

    /**
     * Get the [fdfield_id] column value.
     *
     * @return int
     */
    public function getFDFieldId()
    {
        return $this->fdfield_id;
    }

    /**
     * Get the [value] column value.
     *
     * @return string
     */
    public function getValue()
    {
        return $this->value;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return FDFieldValue The current object (for fluent API support)
     */
    public function setId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = FDFieldValuePeer::ID;
        }


        return $this;
    } // setId()

    /**
     * Set the value of [fdrecord_id] column.
     *
     * @param int $v new value
     * @return FDFieldValue The current object (for fluent API support)
     */
    public function setFDRecordId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->fdrecord_id !== $v) {
            $this->fdrecord_id = $v;
            $this->modifiedColumns[] = FDFieldValuePeer::FDRECORD_ID;
        }

        if ($this->aFDRecord !== null && $this->aFDRecord->getId() !== $v) {
            $this->aFDRecord = null;
        }


        return $this;
    } // setFDRecordId()

    /**
     * Set the value of [fdfield_id] column.
     *
     * @param int $v new value
     * @return FDFieldValue The current object (for fluent API support)
     */
    public function setFDFieldId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->fdfield_id !== $v) {
            $this->fdfield_id = $v;
            $this->modifiedColumns[] = FDFieldValuePeer::FDFIELD_ID;
        }

        if ($this->aFDField !== null && $this->aFDField->getId() !== $v) {
            $this->aFDField = null;
        }


        return $this;
    } // setFDFieldId()

    /**
     * Set the value of [value] column.
     *
     * @param string $v new value
     * @return FDFieldValue The current object (for fluent API support)
     */
    public function setValue($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->value !== $v) {
            $this->value = $v;
            $this->modifiedColumns[] = FDFieldValuePeer::VALUE;
        }


        return $this;
    } // setValue()

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
            $this->fdrecord_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->fdfield_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->value = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 4; // 4 = FDFieldValuePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating FDFieldValue object", $e);
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

        if ($this->aFDRecord !== null && $this->fdrecord_id !== $this->aFDRecord->getId()) {
            $this->aFDRecord = null;
        }
        if ($this->aFDField !== null && $this->fdfield_id !== $this->aFDField->getId()) {
            $this->aFDField = null;
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
            $con = Propel::getConnection(FDFieldValuePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = FDFieldValuePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aFDField = null;
            $this->aFDRecord = null;
            $this->collActionPropertyFDRecords = null;

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
            $con = Propel::getConnection(FDFieldValuePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = FDFieldValueQuery::create()
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
            $con = Propel::getConnection(FDFieldValuePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                FDFieldValuePeer::addInstanceToPool($this);
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

            if ($this->aFDField !== null) {
                if ($this->aFDField->isModified() || $this->aFDField->isNew()) {
                    $affectedRows += $this->aFDField->save($con);
                }
                $this->setFDField($this->aFDField);
            }

            if ($this->aFDRecord !== null) {
                if ($this->aFDRecord->isModified() || $this->aFDRecord->isNew()) {
                    $affectedRows += $this->aFDRecord->save($con);
                }
                $this->setFDRecord($this->aFDRecord);
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

        $this->modifiedColumns[] = FDFieldValuePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . FDFieldValuePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(FDFieldValuePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(FDFieldValuePeer::FDRECORD_ID)) {
            $modifiedColumns[':p' . $index++]  = '`fdRecord_id`';
        }
        if ($this->isColumnModified(FDFieldValuePeer::FDFIELD_ID)) {
            $modifiedColumns[':p' . $index++]  = '`fdField_id`';
        }
        if ($this->isColumnModified(FDFieldValuePeer::VALUE)) {
            $modifiedColumns[':p' . $index++]  = '`value`';
        }

        $sql = sprintf(
            'INSERT INTO `FDFieldValue` (%s) VALUES (%s)',
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
                    case '`fdRecord_id`':
                        $stmt->bindValue($identifier, $this->fdrecord_id, PDO::PARAM_INT);
                        break;
                    case '`fdField_id`':
                        $stmt->bindValue($identifier, $this->fdfield_id, PDO::PARAM_INT);
                        break;
                    case '`value`':
                        $stmt->bindValue($identifier, $this->value, PDO::PARAM_STR);
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

            if ($this->aFDField !== null) {
                if (!$this->aFDField->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aFDField->getValidationFailures());
                }
            }

            if ($this->aFDRecord !== null) {
                if (!$this->aFDRecord->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aFDRecord->getValidationFailures());
                }
            }


            if (($retval = FDFieldValuePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionPropertyFDRecords !== null) {
                    foreach ($this->collActionPropertyFDRecords as $referrerFK) {
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
        $pos = FDFieldValuePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getFDRecordId();
                break;
            case 2:
                return $this->getFDFieldId();
                break;
            case 3:
                return $this->getValue();
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
        if (isset($alreadyDumpedObjects['FDFieldValue'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['FDFieldValue'][$this->getPrimaryKey()] = true;
        $keys = FDFieldValuePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getId(),
            $keys[1] => $this->getFDRecordId(),
            $keys[2] => $this->getFDFieldId(),
            $keys[3] => $this->getValue(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aFDField) {
                $result['FDField'] = $this->aFDField->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->aFDRecord) {
                $result['FDRecord'] = $this->aFDRecord->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActionPropertyFDRecords) {
                $result['ActionPropertyFDRecords'] = $this->collActionPropertyFDRecords->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = FDFieldValuePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setFDRecordId($value);
                break;
            case 2:
                $this->setFDFieldId($value);
                break;
            case 3:
                $this->setValue($value);
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
        $keys = FDFieldValuePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setId($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setFDRecordId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setFDFieldId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setValue($arr[$keys[3]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(FDFieldValuePeer::DATABASE_NAME);

        if ($this->isColumnModified(FDFieldValuePeer::ID)) $criteria->add(FDFieldValuePeer::ID, $this->id);
        if ($this->isColumnModified(FDFieldValuePeer::FDRECORD_ID)) $criteria->add(FDFieldValuePeer::FDRECORD_ID, $this->fdrecord_id);
        if ($this->isColumnModified(FDFieldValuePeer::FDFIELD_ID)) $criteria->add(FDFieldValuePeer::FDFIELD_ID, $this->fdfield_id);
        if ($this->isColumnModified(FDFieldValuePeer::VALUE)) $criteria->add(FDFieldValuePeer::VALUE, $this->value);

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
        $criteria = new Criteria(FDFieldValuePeer::DATABASE_NAME);
        $criteria->add(FDFieldValuePeer::ID, $this->id);

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
     * @param object $copyObj An object of FDFieldValue (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setFDRecordId($this->getFDRecordId());
        $copyObj->setFDFieldId($this->getFDFieldId());
        $copyObj->setValue($this->getValue());

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
     * @return FDFieldValue Clone of current object.
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
     * @return FDFieldValuePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new FDFieldValuePeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a FDField object.
     *
     * @param             FDField $v
     * @return FDFieldValue The current object (for fluent API support)
     * @throws PropelException
     */
    public function setFDField(FDField $v = null)
    {
        if ($v === null) {
            $this->setFDFieldId(NULL);
        } else {
            $this->setFDFieldId($v->getId());
        }

        $this->aFDField = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the FDField object, it will not be re-added.
        if ($v !== null) {
            $v->addFDFieldValue($this);
        }


        return $this;
    }


    /**
     * Get the associated FDField object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return FDField The associated FDField object.
     * @throws PropelException
     */
    public function getFDField(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aFDField === null && ($this->fdfield_id !== null) && $doQuery) {
            $this->aFDField = FDFieldQuery::create()->findPk($this->fdfield_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aFDField->addFDFieldValues($this);
             */
        }

        return $this->aFDField;
    }

    /**
     * Declares an association between this object and a FDRecord object.
     *
     * @param             FDRecord $v
     * @return FDFieldValue The current object (for fluent API support)
     * @throws PropelException
     */
    public function setFDRecord(FDRecord $v = null)
    {
        if ($v === null) {
            $this->setFDRecordId(NULL);
        } else {
            $this->setFDRecordId($v->getId());
        }

        $this->aFDRecord = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the FDRecord object, it will not be re-added.
        if ($v !== null) {
            $v->addFDFieldValue($this);
        }


        return $this;
    }


    /**
     * Get the associated FDRecord object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return FDRecord The associated FDRecord object.
     * @throws PropelException
     */
    public function getFDRecord(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aFDRecord === null && ($this->fdrecord_id !== null) && $doQuery) {
            $this->aFDRecord = FDRecordQuery::create()->findPk($this->fdrecord_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aFDRecord->addFDFieldValues($this);
             */
        }

        return $this->aFDRecord;
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
    }

    /**
     * Clears out the collActionPropertyFDRecords collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return FDFieldValue The current object (for fluent API support)
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
     * If this FDFieldValue is new, it will return
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
                    ->filterByFDFieldValue($this)
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
     * @return FDFieldValue The current object (for fluent API support)
     */
    public function setActionPropertyFDRecords(PropelCollection $actionPropertyFDRecords, PropelPDO $con = null)
    {
        $actionPropertyFDRecordsToDelete = $this->getActionPropertyFDRecords(new Criteria(), $con)->diff($actionPropertyFDRecords);

        $this->actionPropertyFDRecordsScheduledForDeletion = unserialize(serialize($actionPropertyFDRecordsToDelete));

        foreach ($actionPropertyFDRecordsToDelete as $actionPropertyFDRecordRemoved) {
            $actionPropertyFDRecordRemoved->setFDFieldValue(null);
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
                ->filterByFDFieldValue($this)
                ->count($con);
        }

        return count($this->collActionPropertyFDRecords);
    }

    /**
     * Method called to associate a ActionPropertyFDRecord object to this object
     * through the ActionPropertyFDRecord foreign key attribute.
     *
     * @param    ActionPropertyFDRecord $l ActionPropertyFDRecord
     * @return FDFieldValue The current object (for fluent API support)
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
        $actionPropertyFDRecord->setFDFieldValue($this);
    }

    /**
     * @param	ActionPropertyFDRecord $actionPropertyFDRecord The actionPropertyFDRecord object to remove.
     * @return FDFieldValue The current object (for fluent API support)
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
            $actionPropertyFDRecord->setFDFieldValue(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this FDFieldValue is new, it will return
     * an empty collection; or if this FDFieldValue has previously
     * been saved, it will retrieve related ActionPropertyFDRecords from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in FDFieldValue.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionPropertyFDRecord[] List of ActionPropertyFDRecord objects
     */
    public function getActionPropertyFDRecordsJoinFDRecord($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyFDRecordQuery::create(null, $criteria);
        $query->joinWith('FDRecord', $join_behavior);

        return $this->getActionPropertyFDRecords($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->fdrecord_id = null;
        $this->fdfield_id = null;
        $this->value = null;
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
            if ($this->aFDField instanceof Persistent) {
              $this->aFDField->clearAllReferences($deep);
            }
            if ($this->aFDRecord instanceof Persistent) {
              $this->aFDRecord->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionPropertyFDRecords instanceof PropelCollection) {
            $this->collActionPropertyFDRecords->clearIterator();
        }
        $this->collActionPropertyFDRecords = null;
        $this->aFDField = null;
        $this->aFDRecord = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(FDFieldValuePeer::DEFAULT_STRING_FORMAT);
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
