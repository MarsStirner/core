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
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentQuery;
use Webmis\Models\rbUnit;
use Webmis\Models\rbUnitPeer;
use Webmis\Models\rbUnitQuery;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenQuery;

/**
 * Base class that represents a row from the 'rbUnit' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaserbUnit extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\rbUnitPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        rbUnitPeer
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
     * @var        PropelObjectCollection|DrugComponent[] Collection to store aggregation of DrugComponent objects.
     */
    protected $collDrugComponents;
    protected $collDrugComponentsPartial;

    /**
     * @var        PropelObjectCollection|rlsNomen[] Collection to store aggregation of rlsNomen objects.
     */
    protected $collrlsNomensRelatedByunitId;
    protected $collrlsNomensRelatedByunitIdPartial;

    /**
     * @var        PropelObjectCollection|rlsNomen[] Collection to store aggregation of rlsNomen objects.
     */
    protected $collrlsNomensRelatedBydosageUnitId;
    protected $collrlsNomensRelatedBydosageUnitIdPartial;

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
    protected $drugComponentsScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $rlsNomensRelatedByunitIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $rlsNomensRelatedBydosageUnitIdScheduledForDeletion = null;

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
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return rbUnit The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = rbUnitPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return rbUnit The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = rbUnitPeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return rbUnit The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = rbUnitPeer::NAME;
        }


        return $this;
    } // setname()

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
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 3; // 3 = rbUnitPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating rbUnit object", $e);
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
            $con = Propel::getConnection(rbUnitPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = rbUnitPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->collDrugComponents = null;

            $this->collrlsNomensRelatedByunitId = null;

            $this->collrlsNomensRelatedBydosageUnitId = null;

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
            $con = Propel::getConnection(rbUnitPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = rbUnitQuery::create()
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
            $con = Propel::getConnection(rbUnitPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                rbUnitPeer::addInstanceToPool($this);
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

            if ($this->drugComponentsScheduledForDeletion !== null) {
                if (!$this->drugComponentsScheduledForDeletion->isEmpty()) {
                    foreach ($this->drugComponentsScheduledForDeletion as $drugComponent) {
                        // need to save related object because we set the relation to null
                        $drugComponent->save($con);
                    }
                    $this->drugComponentsScheduledForDeletion = null;
                }
            }

            if ($this->collDrugComponents !== null) {
                foreach ($this->collDrugComponents as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->rlsNomensRelatedByunitIdScheduledForDeletion !== null) {
                if (!$this->rlsNomensRelatedByunitIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->rlsNomensRelatedByunitIdScheduledForDeletion as $rlsNomenRelatedByunitId) {
                        // need to save related object because we set the relation to null
                        $rlsNomenRelatedByunitId->save($con);
                    }
                    $this->rlsNomensRelatedByunitIdScheduledForDeletion = null;
                }
            }

            if ($this->collrlsNomensRelatedByunitId !== null) {
                foreach ($this->collrlsNomensRelatedByunitId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion !== null) {
                if (!$this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion as $rlsNomenRelatedBydosageUnitId) {
                        // need to save related object because we set the relation to null
                        $rlsNomenRelatedBydosageUnitId->save($con);
                    }
                    $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion = null;
                }
            }

            if ($this->collrlsNomensRelatedBydosageUnitId !== null) {
                foreach ($this->collrlsNomensRelatedBydosageUnitId as $referrerFK) {
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

        $this->modifiedColumns[] = rbUnitPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . rbUnitPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(rbUnitPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(rbUnitPeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(rbUnitPeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }

        $sql = sprintf(
            'INSERT INTO `rbUnit` (%s) VALUES (%s)',
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


            if (($retval = rbUnitPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collDrugComponents !== null) {
                    foreach ($this->collDrugComponents as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collrlsNomensRelatedByunitId !== null) {
                    foreach ($this->collrlsNomensRelatedByunitId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collrlsNomensRelatedBydosageUnitId !== null) {
                    foreach ($this->collrlsNomensRelatedBydosageUnitId as $referrerFK) {
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
        $pos = rbUnitPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
        if (isset($alreadyDumpedObjects['rbUnit'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['rbUnit'][$this->getPrimaryKey()] = true;
        $keys = rbUnitPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcode(),
            $keys[2] => $this->getname(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->collDrugComponents) {
                $result['DrugComponents'] = $this->collDrugComponents->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collrlsNomensRelatedByunitId) {
                $result['rlsNomensRelatedByunitId'] = $this->collrlsNomensRelatedByunitId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collrlsNomensRelatedBydosageUnitId) {
                $result['rlsNomensRelatedBydosageUnitId'] = $this->collrlsNomensRelatedBydosageUnitId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = rbUnitPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
        $keys = rbUnitPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcode($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setname($arr[$keys[2]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(rbUnitPeer::DATABASE_NAME);

        if ($this->isColumnModified(rbUnitPeer::ID)) $criteria->add(rbUnitPeer::ID, $this->id);
        if ($this->isColumnModified(rbUnitPeer::CODE)) $criteria->add(rbUnitPeer::CODE, $this->code);
        if ($this->isColumnModified(rbUnitPeer::NAME)) $criteria->add(rbUnitPeer::NAME, $this->name);

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
        $criteria = new Criteria(rbUnitPeer::DATABASE_NAME);
        $criteria->add(rbUnitPeer::ID, $this->id);

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
     * @param object $copyObj An object of rbUnit (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setcode($this->getcode());
        $copyObj->setname($this->getname());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getDrugComponents() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addDrugComponent($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getrlsNomensRelatedByunitId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addrlsNomenRelatedByunitId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getrlsNomensRelatedBydosageUnitId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addrlsNomenRelatedBydosageUnitId($relObj->copy($deepCopy));
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
     * @return rbUnit Clone of current object.
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
     * @return rbUnitPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new rbUnitPeer();
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
        if ('DrugComponent' == $relationName) {
            $this->initDrugComponents();
        }
        if ('rlsNomenRelatedByunitId' == $relationName) {
            $this->initrlsNomensRelatedByunitId();
        }
        if ('rlsNomenRelatedBydosageUnitId' == $relationName) {
            $this->initrlsNomensRelatedBydosageUnitId();
        }
    }

    /**
     * Clears out the collDrugComponents collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return rbUnit The current object (for fluent API support)
     * @see        addDrugComponents()
     */
    public function clearDrugComponents()
    {
        $this->collDrugComponents = null; // important to set this to null since that means it is uninitialized
        $this->collDrugComponentsPartial = null;

        return $this;
    }

    /**
     * reset is the collDrugComponents collection loaded partially
     *
     * @return void
     */
    public function resetPartialDrugComponents($v = true)
    {
        $this->collDrugComponentsPartial = $v;
    }

    /**
     * Initializes the collDrugComponents collection.
     *
     * By default this just sets the collDrugComponents collection to an empty array (like clearcollDrugComponents());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initDrugComponents($overrideExisting = true)
    {
        if (null !== $this->collDrugComponents && !$overrideExisting) {
            return;
        }
        $this->collDrugComponents = new PropelObjectCollection();
        $this->collDrugComponents->setModel('DrugComponent');
    }

    /**
     * Gets an array of DrugComponent objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this rbUnit is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     * @throws PropelException
     */
    public function getDrugComponents($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collDrugComponentsPartial && !$this->isNew();
        if (null === $this->collDrugComponents || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collDrugComponents) {
                // return empty collection
                $this->initDrugComponents();
            } else {
                $collDrugComponents = DrugComponentQuery::create(null, $criteria)
                    ->filterByrbUnit($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collDrugComponentsPartial && count($collDrugComponents)) {
                      $this->initDrugComponents(false);

                      foreach($collDrugComponents as $obj) {
                        if (false == $this->collDrugComponents->contains($obj)) {
                          $this->collDrugComponents->append($obj);
                        }
                      }

                      $this->collDrugComponentsPartial = true;
                    }

                    $collDrugComponents->getInternalIterator()->rewind();
                    return $collDrugComponents;
                }

                if($partial && $this->collDrugComponents) {
                    foreach($this->collDrugComponents as $obj) {
                        if($obj->isNew()) {
                            $collDrugComponents[] = $obj;
                        }
                    }
                }

                $this->collDrugComponents = $collDrugComponents;
                $this->collDrugComponentsPartial = false;
            }
        }

        return $this->collDrugComponents;
    }

    /**
     * Sets a collection of DrugComponent objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $drugComponents A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return rbUnit The current object (for fluent API support)
     */
    public function setDrugComponents(PropelCollection $drugComponents, PropelPDO $con = null)
    {
        $drugComponentsToDelete = $this->getDrugComponents(new Criteria(), $con)->diff($drugComponents);

        $this->drugComponentsScheduledForDeletion = unserialize(serialize($drugComponentsToDelete));

        foreach ($drugComponentsToDelete as $drugComponentRemoved) {
            $drugComponentRemoved->setrbUnit(null);
        }

        $this->collDrugComponents = null;
        foreach ($drugComponents as $drugComponent) {
            $this->addDrugComponent($drugComponent);
        }

        $this->collDrugComponents = $drugComponents;
        $this->collDrugComponentsPartial = false;

        return $this;
    }

    /**
     * Returns the number of related DrugComponent objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related DrugComponent objects.
     * @throws PropelException
     */
    public function countDrugComponents(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collDrugComponentsPartial && !$this->isNew();
        if (null === $this->collDrugComponents || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collDrugComponents) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getDrugComponents());
            }
            $query = DrugComponentQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByrbUnit($this)
                ->count($con);
        }

        return count($this->collDrugComponents);
    }

    /**
     * Method called to associate a DrugComponent object to this object
     * through the DrugComponent foreign key attribute.
     *
     * @param    DrugComponent $l DrugComponent
     * @return rbUnit The current object (for fluent API support)
     */
    public function addDrugComponent(DrugComponent $l)
    {
        if ($this->collDrugComponents === null) {
            $this->initDrugComponents();
            $this->collDrugComponentsPartial = true;
        }
        if (!in_array($l, $this->collDrugComponents->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddDrugComponent($l);
        }

        return $this;
    }

    /**
     * @param	DrugComponent $drugComponent The drugComponent object to add.
     */
    protected function doAddDrugComponent($drugComponent)
    {
        $this->collDrugComponents[]= $drugComponent;
        $drugComponent->setrbUnit($this);
    }

    /**
     * @param	DrugComponent $drugComponent The drugComponent object to remove.
     * @return rbUnit The current object (for fluent API support)
     */
    public function removeDrugComponent($drugComponent)
    {
        if ($this->getDrugComponents()->contains($drugComponent)) {
            $this->collDrugComponents->remove($this->collDrugComponents->search($drugComponent));
            if (null === $this->drugComponentsScheduledForDeletion) {
                $this->drugComponentsScheduledForDeletion = clone $this->collDrugComponents;
                $this->drugComponentsScheduledForDeletion->clear();
            }
            $this->drugComponentsScheduledForDeletion[]= $drugComponent;
            $drugComponent->setrbUnit(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     */
    public function getDrugComponentsJoinAction($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugComponentQuery::create(null, $criteria);
        $query->joinWith('Action', $join_behavior);

        return $this->getDrugComponents($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     */
    public function getDrugComponentsJoinrlsNomen($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugComponentQuery::create(null, $criteria);
        $query->joinWith('rlsNomen', $join_behavior);

        return $this->getDrugComponents($query, $con);
    }

    /**
     * Clears out the collrlsNomensRelatedByunitId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return rbUnit The current object (for fluent API support)
     * @see        addrlsNomensRelatedByunitId()
     */
    public function clearrlsNomensRelatedByunitId()
    {
        $this->collrlsNomensRelatedByunitId = null; // important to set this to null since that means it is uninitialized
        $this->collrlsNomensRelatedByunitIdPartial = null;

        return $this;
    }

    /**
     * reset is the collrlsNomensRelatedByunitId collection loaded partially
     *
     * @return void
     */
    public function resetPartialrlsNomensRelatedByunitId($v = true)
    {
        $this->collrlsNomensRelatedByunitIdPartial = $v;
    }

    /**
     * Initializes the collrlsNomensRelatedByunitId collection.
     *
     * By default this just sets the collrlsNomensRelatedByunitId collection to an empty array (like clearcollrlsNomensRelatedByunitId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initrlsNomensRelatedByunitId($overrideExisting = true)
    {
        if (null !== $this->collrlsNomensRelatedByunitId && !$overrideExisting) {
            return;
        }
        $this->collrlsNomensRelatedByunitId = new PropelObjectCollection();
        $this->collrlsNomensRelatedByunitId->setModel('rlsNomen');
    }

    /**
     * Gets an array of rlsNomen objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this rbUnit is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     * @throws PropelException
     */
    public function getrlsNomensRelatedByunitId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collrlsNomensRelatedByunitIdPartial && !$this->isNew();
        if (null === $this->collrlsNomensRelatedByunitId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collrlsNomensRelatedByunitId) {
                // return empty collection
                $this->initrlsNomensRelatedByunitId();
            } else {
                $collrlsNomensRelatedByunitId = rlsNomenQuery::create(null, $criteria)
                    ->filterByrbUnitRelatedByunitId($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collrlsNomensRelatedByunitIdPartial && count($collrlsNomensRelatedByunitId)) {
                      $this->initrlsNomensRelatedByunitId(false);

                      foreach($collrlsNomensRelatedByunitId as $obj) {
                        if (false == $this->collrlsNomensRelatedByunitId->contains($obj)) {
                          $this->collrlsNomensRelatedByunitId->append($obj);
                        }
                      }

                      $this->collrlsNomensRelatedByunitIdPartial = true;
                    }

                    $collrlsNomensRelatedByunitId->getInternalIterator()->rewind();
                    return $collrlsNomensRelatedByunitId;
                }

                if($partial && $this->collrlsNomensRelatedByunitId) {
                    foreach($this->collrlsNomensRelatedByunitId as $obj) {
                        if($obj->isNew()) {
                            $collrlsNomensRelatedByunitId[] = $obj;
                        }
                    }
                }

                $this->collrlsNomensRelatedByunitId = $collrlsNomensRelatedByunitId;
                $this->collrlsNomensRelatedByunitIdPartial = false;
            }
        }

        return $this->collrlsNomensRelatedByunitId;
    }

    /**
     * Sets a collection of rlsNomenRelatedByunitId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rlsNomensRelatedByunitId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return rbUnit The current object (for fluent API support)
     */
    public function setrlsNomensRelatedByunitId(PropelCollection $rlsNomensRelatedByunitId, PropelPDO $con = null)
    {
        $rlsNomensRelatedByunitIdToDelete = $this->getrlsNomensRelatedByunitId(new Criteria(), $con)->diff($rlsNomensRelatedByunitId);

        $this->rlsNomensRelatedByunitIdScheduledForDeletion = unserialize(serialize($rlsNomensRelatedByunitIdToDelete));

        foreach ($rlsNomensRelatedByunitIdToDelete as $rlsNomenRelatedByunitIdRemoved) {
            $rlsNomenRelatedByunitIdRemoved->setrbUnitRelatedByunitId(null);
        }

        $this->collrlsNomensRelatedByunitId = null;
        foreach ($rlsNomensRelatedByunitId as $rlsNomenRelatedByunitId) {
            $this->addrlsNomenRelatedByunitId($rlsNomenRelatedByunitId);
        }

        $this->collrlsNomensRelatedByunitId = $rlsNomensRelatedByunitId;
        $this->collrlsNomensRelatedByunitIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related rlsNomen objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related rlsNomen objects.
     * @throws PropelException
     */
    public function countrlsNomensRelatedByunitId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collrlsNomensRelatedByunitIdPartial && !$this->isNew();
        if (null === $this->collrlsNomensRelatedByunitId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collrlsNomensRelatedByunitId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getrlsNomensRelatedByunitId());
            }
            $query = rlsNomenQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByrbUnitRelatedByunitId($this)
                ->count($con);
        }

        return count($this->collrlsNomensRelatedByunitId);
    }

    /**
     * Method called to associate a rlsNomen object to this object
     * through the rlsNomen foreign key attribute.
     *
     * @param    rlsNomen $l rlsNomen
     * @return rbUnit The current object (for fluent API support)
     */
    public function addrlsNomenRelatedByunitId(rlsNomen $l)
    {
        if ($this->collrlsNomensRelatedByunitId === null) {
            $this->initrlsNomensRelatedByunitId();
            $this->collrlsNomensRelatedByunitIdPartial = true;
        }
        if (!in_array($l, $this->collrlsNomensRelatedByunitId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddrlsNomenRelatedByunitId($l);
        }

        return $this;
    }

    /**
     * @param	rlsNomenRelatedByunitId $rlsNomenRelatedByunitId The rlsNomenRelatedByunitId object to add.
     */
    protected function doAddrlsNomenRelatedByunitId($rlsNomenRelatedByunitId)
    {
        $this->collrlsNomensRelatedByunitId[]= $rlsNomenRelatedByunitId;
        $rlsNomenRelatedByunitId->setrbUnitRelatedByunitId($this);
    }

    /**
     * @param	rlsNomenRelatedByunitId $rlsNomenRelatedByunitId The rlsNomenRelatedByunitId object to remove.
     * @return rbUnit The current object (for fluent API support)
     */
    public function removerlsNomenRelatedByunitId($rlsNomenRelatedByunitId)
    {
        if ($this->getrlsNomensRelatedByunitId()->contains($rlsNomenRelatedByunitId)) {
            $this->collrlsNomensRelatedByunitId->remove($this->collrlsNomensRelatedByunitId->search($rlsNomenRelatedByunitId));
            if (null === $this->rlsNomensRelatedByunitIdScheduledForDeletion) {
                $this->rlsNomensRelatedByunitIdScheduledForDeletion = clone $this->collrlsNomensRelatedByunitId;
                $this->rlsNomensRelatedByunitIdScheduledForDeletion->clear();
            }
            $this->rlsNomensRelatedByunitIdScheduledForDeletion[]= $rlsNomenRelatedByunitId;
            $rlsNomenRelatedByunitId->setrbUnitRelatedByunitId(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedByunitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedByunitIdJoinrlsFilling($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsFilling', $join_behavior);

        return $this->getrlsNomensRelatedByunitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedByunitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedByunitIdJoinrlsForm($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsForm', $join_behavior);

        return $this->getrlsNomensRelatedByunitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedByunitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedByunitIdJoinrlsPacking($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsPacking', $join_behavior);

        return $this->getrlsNomensRelatedByunitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedByunitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedByunitIdJoinrlsActMatters($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsActMatters', $join_behavior);

        return $this->getrlsNomensRelatedByunitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedByunitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedByunitIdJoinrlsTradeName($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsTradeName', $join_behavior);

        return $this->getrlsNomensRelatedByunitId($query, $con);
    }

    /**
     * Clears out the collrlsNomensRelatedBydosageUnitId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return rbUnit The current object (for fluent API support)
     * @see        addrlsNomensRelatedBydosageUnitId()
     */
    public function clearrlsNomensRelatedBydosageUnitId()
    {
        $this->collrlsNomensRelatedBydosageUnitId = null; // important to set this to null since that means it is uninitialized
        $this->collrlsNomensRelatedBydosageUnitIdPartial = null;

        return $this;
    }

    /**
     * reset is the collrlsNomensRelatedBydosageUnitId collection loaded partially
     *
     * @return void
     */
    public function resetPartialrlsNomensRelatedBydosageUnitId($v = true)
    {
        $this->collrlsNomensRelatedBydosageUnitIdPartial = $v;
    }

    /**
     * Initializes the collrlsNomensRelatedBydosageUnitId collection.
     *
     * By default this just sets the collrlsNomensRelatedBydosageUnitId collection to an empty array (like clearcollrlsNomensRelatedBydosageUnitId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initrlsNomensRelatedBydosageUnitId($overrideExisting = true)
    {
        if (null !== $this->collrlsNomensRelatedBydosageUnitId && !$overrideExisting) {
            return;
        }
        $this->collrlsNomensRelatedBydosageUnitId = new PropelObjectCollection();
        $this->collrlsNomensRelatedBydosageUnitId->setModel('rlsNomen');
    }

    /**
     * Gets an array of rlsNomen objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this rbUnit is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     * @throws PropelException
     */
    public function getrlsNomensRelatedBydosageUnitId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collrlsNomensRelatedBydosageUnitIdPartial && !$this->isNew();
        if (null === $this->collrlsNomensRelatedBydosageUnitId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collrlsNomensRelatedBydosageUnitId) {
                // return empty collection
                $this->initrlsNomensRelatedBydosageUnitId();
            } else {
                $collrlsNomensRelatedBydosageUnitId = rlsNomenQuery::create(null, $criteria)
                    ->filterByrbUnitRelatedBydosageUnitId($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collrlsNomensRelatedBydosageUnitIdPartial && count($collrlsNomensRelatedBydosageUnitId)) {
                      $this->initrlsNomensRelatedBydosageUnitId(false);

                      foreach($collrlsNomensRelatedBydosageUnitId as $obj) {
                        if (false == $this->collrlsNomensRelatedBydosageUnitId->contains($obj)) {
                          $this->collrlsNomensRelatedBydosageUnitId->append($obj);
                        }
                      }

                      $this->collrlsNomensRelatedBydosageUnitIdPartial = true;
                    }

                    $collrlsNomensRelatedBydosageUnitId->getInternalIterator()->rewind();
                    return $collrlsNomensRelatedBydosageUnitId;
                }

                if($partial && $this->collrlsNomensRelatedBydosageUnitId) {
                    foreach($this->collrlsNomensRelatedBydosageUnitId as $obj) {
                        if($obj->isNew()) {
                            $collrlsNomensRelatedBydosageUnitId[] = $obj;
                        }
                    }
                }

                $this->collrlsNomensRelatedBydosageUnitId = $collrlsNomensRelatedBydosageUnitId;
                $this->collrlsNomensRelatedBydosageUnitIdPartial = false;
            }
        }

        return $this->collrlsNomensRelatedBydosageUnitId;
    }

    /**
     * Sets a collection of rlsNomenRelatedBydosageUnitId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rlsNomensRelatedBydosageUnitId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return rbUnit The current object (for fluent API support)
     */
    public function setrlsNomensRelatedBydosageUnitId(PropelCollection $rlsNomensRelatedBydosageUnitId, PropelPDO $con = null)
    {
        $rlsNomensRelatedBydosageUnitIdToDelete = $this->getrlsNomensRelatedBydosageUnitId(new Criteria(), $con)->diff($rlsNomensRelatedBydosageUnitId);

        $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion = unserialize(serialize($rlsNomensRelatedBydosageUnitIdToDelete));

        foreach ($rlsNomensRelatedBydosageUnitIdToDelete as $rlsNomenRelatedBydosageUnitIdRemoved) {
            $rlsNomenRelatedBydosageUnitIdRemoved->setrbUnitRelatedBydosageUnitId(null);
        }

        $this->collrlsNomensRelatedBydosageUnitId = null;
        foreach ($rlsNomensRelatedBydosageUnitId as $rlsNomenRelatedBydosageUnitId) {
            $this->addrlsNomenRelatedBydosageUnitId($rlsNomenRelatedBydosageUnitId);
        }

        $this->collrlsNomensRelatedBydosageUnitId = $rlsNomensRelatedBydosageUnitId;
        $this->collrlsNomensRelatedBydosageUnitIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related rlsNomen objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related rlsNomen objects.
     * @throws PropelException
     */
    public function countrlsNomensRelatedBydosageUnitId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collrlsNomensRelatedBydosageUnitIdPartial && !$this->isNew();
        if (null === $this->collrlsNomensRelatedBydosageUnitId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collrlsNomensRelatedBydosageUnitId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getrlsNomensRelatedBydosageUnitId());
            }
            $query = rlsNomenQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByrbUnitRelatedBydosageUnitId($this)
                ->count($con);
        }

        return count($this->collrlsNomensRelatedBydosageUnitId);
    }

    /**
     * Method called to associate a rlsNomen object to this object
     * through the rlsNomen foreign key attribute.
     *
     * @param    rlsNomen $l rlsNomen
     * @return rbUnit The current object (for fluent API support)
     */
    public function addrlsNomenRelatedBydosageUnitId(rlsNomen $l)
    {
        if ($this->collrlsNomensRelatedBydosageUnitId === null) {
            $this->initrlsNomensRelatedBydosageUnitId();
            $this->collrlsNomensRelatedBydosageUnitIdPartial = true;
        }
        if (!in_array($l, $this->collrlsNomensRelatedBydosageUnitId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddrlsNomenRelatedBydosageUnitId($l);
        }

        return $this;
    }

    /**
     * @param	rlsNomenRelatedBydosageUnitId $rlsNomenRelatedBydosageUnitId The rlsNomenRelatedBydosageUnitId object to add.
     */
    protected function doAddrlsNomenRelatedBydosageUnitId($rlsNomenRelatedBydosageUnitId)
    {
        $this->collrlsNomensRelatedBydosageUnitId[]= $rlsNomenRelatedBydosageUnitId;
        $rlsNomenRelatedBydosageUnitId->setrbUnitRelatedBydosageUnitId($this);
    }

    /**
     * @param	rlsNomenRelatedBydosageUnitId $rlsNomenRelatedBydosageUnitId The rlsNomenRelatedBydosageUnitId object to remove.
     * @return rbUnit The current object (for fluent API support)
     */
    public function removerlsNomenRelatedBydosageUnitId($rlsNomenRelatedBydosageUnitId)
    {
        if ($this->getrlsNomensRelatedBydosageUnitId()->contains($rlsNomenRelatedBydosageUnitId)) {
            $this->collrlsNomensRelatedBydosageUnitId->remove($this->collrlsNomensRelatedBydosageUnitId->search($rlsNomenRelatedBydosageUnitId));
            if (null === $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion) {
                $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion = clone $this->collrlsNomensRelatedBydosageUnitId;
                $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion->clear();
            }
            $this->rlsNomensRelatedBydosageUnitIdScheduledForDeletion[]= $rlsNomenRelatedBydosageUnitId;
            $rlsNomenRelatedBydosageUnitId->setrbUnitRelatedBydosageUnitId(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedBydosageUnitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedBydosageUnitIdJoinrlsFilling($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsFilling', $join_behavior);

        return $this->getrlsNomensRelatedBydosageUnitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedBydosageUnitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedBydosageUnitIdJoinrlsForm($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsForm', $join_behavior);

        return $this->getrlsNomensRelatedBydosageUnitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedBydosageUnitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedBydosageUnitIdJoinrlsPacking($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsPacking', $join_behavior);

        return $this->getrlsNomensRelatedBydosageUnitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedBydosageUnitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedBydosageUnitIdJoinrlsActMatters($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsActMatters', $join_behavior);

        return $this->getrlsNomensRelatedBydosageUnitId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rbUnit is new, it will return
     * an empty collection; or if this rbUnit has previously
     * been saved, it will retrieve related rlsNomensRelatedBydosageUnitId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rbUnit.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|rlsNomen[] List of rlsNomen objects
     */
    public function getrlsNomensRelatedBydosageUnitIdJoinrlsTradeName($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = rlsNomenQuery::create(null, $criteria);
        $query->joinWith('rlsTradeName', $join_behavior);

        return $this->getrlsNomensRelatedBydosageUnitId($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->code = null;
        $this->name = null;
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
            if ($this->collDrugComponents) {
                foreach ($this->collDrugComponents as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collrlsNomensRelatedByunitId) {
                foreach ($this->collrlsNomensRelatedByunitId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collrlsNomensRelatedBydosageUnitId) {
                foreach ($this->collrlsNomensRelatedBydosageUnitId as $o) {
                    $o->clearAllReferences($deep);
                }
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collDrugComponents instanceof PropelCollection) {
            $this->collDrugComponents->clearIterator();
        }
        $this->collDrugComponents = null;
        if ($this->collrlsNomensRelatedByunitId instanceof PropelCollection) {
            $this->collrlsNomensRelatedByunitId->clearIterator();
        }
        $this->collrlsNomensRelatedByunitId = null;
        if ($this->collrlsNomensRelatedBydosageUnitId instanceof PropelCollection) {
            $this->collrlsNomensRelatedBydosageUnitId->clearIterator();
        }
        $this->collrlsNomensRelatedBydosageUnitId = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(rbUnitPeer::DEFAULT_STRING_FORMAT);
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
