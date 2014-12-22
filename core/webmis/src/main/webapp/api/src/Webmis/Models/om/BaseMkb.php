<?php

namespace Webmis\Models\om;

use \BaseObject;
use \BasePeer;
use \Criteria;
use \Exception;
use \PDO;
use \Persistent;
use \Propel;
use \PropelException;
use \PropelPDO;
use Webmis\Models\Mkb;
use Webmis\Models\MkbPeer;
use Webmis\Models\MkbQuery;

/**
 * Base class that represents a row from the 'MKB' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseMkb extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\MkbPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        MkbPeer
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
     * The value for the classid field.
     * @var        string
     */
    protected $classid;

    /**
     * The value for the classname field.
     * @var        string
     */
    protected $classname;

    /**
     * The value for the blockid field.
     * @var        string
     */
    protected $blockid;

    /**
     * The value for the blockname field.
     * @var        string
     */
    protected $blockname;

    /**
     * The value for the diagid field.
     * @var        string
     */
    protected $diagid;

    /**
     * The value for the diagname field.
     * @var        string
     */
    protected $diagname;

    /**
     * The value for the prim field.
     * @var        string
     */
    protected $prim;

    /**
     * The value for the sex field.
     * @var        boolean
     */
    protected $sex;

    /**
     * The value for the age field.
     * @var        string
     */
    protected $age;

    /**
     * The value for the age_bu field.
     * @var        int
     */
    protected $age_bu;

    /**
     * The value for the age_bc field.
     * @var        int
     */
    protected $age_bc;

    /**
     * The value for the age_eu field.
     * @var        int
     */
    protected $age_eu;

    /**
     * The value for the age_ec field.
     * @var        int
     */
    protected $age_ec;

    /**
     * The value for the characters field.
     * @var        int
     */
    protected $characters;

    /**
     * The value for the duration field.
     * @var        int
     */
    protected $duration;

    /**
     * The value for the service_id field.
     * @var        int
     */
    protected $service_id;

    /**
     * The value for the mkbsubclass_id field.
     * @var        int
     */
    protected $mkbsubclass_id;

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
     * Get the [classid] column value.
     *
     * @return string
     */
    public function getclassId()
    {
        return $this->classid;
    }

    /**
     * Get the [classname] column value.
     *
     * @return string
     */
    public function getclassName()
    {
        return $this->classname;
    }

    /**
     * Get the [blockid] column value.
     *
     * @return string
     */
    public function getblockId()
    {
        return $this->blockid;
    }

    /**
     * Get the [blockname] column value.
     *
     * @return string
     */
    public function getblockName()
    {
        return $this->blockname;
    }

    /**
     * Get the [diagid] column value.
     *
     * @return string
     */
    public function getdiagId()
    {
        return $this->diagid;
    }

    /**
     * Get the [diagname] column value.
     *
     * @return string
     */
    public function getdiagName()
    {
        return $this->diagname;
    }

    /**
     * Get the [prim] column value.
     *
     * @return string
     */
    public function getprim()
    {
        return $this->prim;
    }

    /**
     * Get the [sex] column value.
     *
     * @return boolean
     */
    public function getsex()
    {
        return $this->sex;
    }

    /**
     * Get the [age] column value.
     *
     * @return string
     */
    public function getage()
    {
        return $this->age;
    }

    /**
     * Get the [age_bu] column value.
     *
     * @return int
     */
    public function getageBu()
    {
        return $this->age_bu;
    }

    /**
     * Get the [age_bc] column value.
     *
     * @return int
     */
    public function getageBc()
    {
        return $this->age_bc;
    }

    /**
     * Get the [age_eu] column value.
     *
     * @return int
     */
    public function getageEu()
    {
        return $this->age_eu;
    }

    /**
     * Get the [age_ec] column value.
     *
     * @return int
     */
    public function getageEc()
    {
        return $this->age_ec;
    }

    /**
     * Get the [characters] column value.
     *
     * @return int
     */
    public function getcharacters()
    {
        return $this->characters;
    }

    /**
     * Get the [duration] column value.
     *
     * @return int
     */
    public function getduration()
    {
        return $this->duration;
    }

    /**
     * Get the [service_id] column value.
     *
     * @return int
     */
    public function getserviceId()
    {
        return $this->service_id;
    }

    /**
     * Get the [mkbsubclass_id] column value.
     *
     * @return int
     */
    public function getMkbSubclassId()
    {
        return $this->mkbsubclass_id;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = MkbPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [classid] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setclassId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->classid !== $v) {
            $this->classid = $v;
            $this->modifiedColumns[] = MkbPeer::CLASSID;
        }


        return $this;
    } // setclassId()

    /**
     * Set the value of [classname] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setclassName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->classname !== $v) {
            $this->classname = $v;
            $this->modifiedColumns[] = MkbPeer::CLASSNAME;
        }


        return $this;
    } // setclassName()

    /**
     * Set the value of [blockid] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setblockId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->blockid !== $v) {
            $this->blockid = $v;
            $this->modifiedColumns[] = MkbPeer::BLOCKID;
        }


        return $this;
    } // setblockId()

    /**
     * Set the value of [blockname] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setblockName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->blockname !== $v) {
            $this->blockname = $v;
            $this->modifiedColumns[] = MkbPeer::BLOCKNAME;
        }


        return $this;
    } // setblockName()

    /**
     * Set the value of [diagid] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setdiagId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->diagid !== $v) {
            $this->diagid = $v;
            $this->modifiedColumns[] = MkbPeer::DIAGID;
        }


        return $this;
    } // setdiagId()

    /**
     * Set the value of [diagname] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setdiagName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->diagname !== $v) {
            $this->diagname = $v;
            $this->modifiedColumns[] = MkbPeer::DIAGNAME;
        }


        return $this;
    } // setdiagName()

    /**
     * Set the value of [prim] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setprim($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->prim !== $v) {
            $this->prim = $v;
            $this->modifiedColumns[] = MkbPeer::PRIM;
        }


        return $this;
    } // setprim()

    /**
     * Sets the value of the [sex] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setsex($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->sex !== $v) {
            $this->sex = $v;
            $this->modifiedColumns[] = MkbPeer::SEX;
        }


        return $this;
    } // setsex()

    /**
     * Set the value of [age] column.
     *
     * @param string $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setage($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->age !== $v) {
            $this->age = $v;
            $this->modifiedColumns[] = MkbPeer::AGE;
        }


        return $this;
    } // setage()

    /**
     * Set the value of [age_bu] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setageBu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bu !== $v) {
            $this->age_bu = $v;
            $this->modifiedColumns[] = MkbPeer::AGE_BU;
        }


        return $this;
    } // setageBu()

    /**
     * Set the value of [age_bc] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setageBc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bc !== $v) {
            $this->age_bc = $v;
            $this->modifiedColumns[] = MkbPeer::AGE_BC;
        }


        return $this;
    } // setageBc()

    /**
     * Set the value of [age_eu] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setageEu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_eu !== $v) {
            $this->age_eu = $v;
            $this->modifiedColumns[] = MkbPeer::AGE_EU;
        }


        return $this;
    } // setageEu()

    /**
     * Set the value of [age_ec] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setageEc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_ec !== $v) {
            $this->age_ec = $v;
            $this->modifiedColumns[] = MkbPeer::AGE_EC;
        }


        return $this;
    } // setageEc()

    /**
     * Set the value of [characters] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setcharacters($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->characters !== $v) {
            $this->characters = $v;
            $this->modifiedColumns[] = MkbPeer::CHARACTERS;
        }


        return $this;
    } // setcharacters()

    /**
     * Set the value of [duration] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setduration($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->duration !== $v) {
            $this->duration = $v;
            $this->modifiedColumns[] = MkbPeer::DURATION;
        }


        return $this;
    } // setduration()

    /**
     * Set the value of [service_id] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setserviceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->service_id !== $v) {
            $this->service_id = $v;
            $this->modifiedColumns[] = MkbPeer::SERVICE_ID;
        }


        return $this;
    } // setserviceId()

    /**
     * Set the value of [mkbsubclass_id] column.
     *
     * @param int $v new value
     * @return Mkb The current object (for fluent API support)
     */
    public function setMkbSubclassId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->mkbsubclass_id !== $v) {
            $this->mkbsubclass_id = $v;
            $this->modifiedColumns[] = MkbPeer::MKBSUBCLASS_ID;
        }


        return $this;
    } // setMkbSubclassId()

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
            $this->classid = ($row[$startcol + 1] !== null) ? (string) $row[$startcol + 1] : null;
            $this->classname = ($row[$startcol + 2] !== null) ? (string) $row[$startcol + 2] : null;
            $this->blockid = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->blockname = ($row[$startcol + 4] !== null) ? (string) $row[$startcol + 4] : null;
            $this->diagid = ($row[$startcol + 5] !== null) ? (string) $row[$startcol + 5] : null;
            $this->diagname = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->prim = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->sex = ($row[$startcol + 8] !== null) ? (boolean) $row[$startcol + 8] : null;
            $this->age = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->age_bu = ($row[$startcol + 10] !== null) ? (int) $row[$startcol + 10] : null;
            $this->age_bc = ($row[$startcol + 11] !== null) ? (int) $row[$startcol + 11] : null;
            $this->age_eu = ($row[$startcol + 12] !== null) ? (int) $row[$startcol + 12] : null;
            $this->age_ec = ($row[$startcol + 13] !== null) ? (int) $row[$startcol + 13] : null;
            $this->characters = ($row[$startcol + 14] !== null) ? (int) $row[$startcol + 14] : null;
            $this->duration = ($row[$startcol + 15] !== null) ? (int) $row[$startcol + 15] : null;
            $this->service_id = ($row[$startcol + 16] !== null) ? (int) $row[$startcol + 16] : null;
            $this->mkbsubclass_id = ($row[$startcol + 17] !== null) ? (int) $row[$startcol + 17] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 18; // 18 = MkbPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating Mkb object", $e);
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
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = MkbPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

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
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = MkbQuery::create()
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
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                MkbPeer::addInstanceToPool($this);
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

        $this->modifiedColumns[] = MkbPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . MkbPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(MkbPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(MkbPeer::CLASSID)) {
            $modifiedColumns[':p' . $index++]  = '`ClassID`';
        }
        if ($this->isColumnModified(MkbPeer::CLASSNAME)) {
            $modifiedColumns[':p' . $index++]  = '`ClassName`';
        }
        if ($this->isColumnModified(MkbPeer::BLOCKID)) {
            $modifiedColumns[':p' . $index++]  = '`BlockID`';
        }
        if ($this->isColumnModified(MkbPeer::BLOCKNAME)) {
            $modifiedColumns[':p' . $index++]  = '`BlockName`';
        }
        if ($this->isColumnModified(MkbPeer::DIAGID)) {
            $modifiedColumns[':p' . $index++]  = '`DiagID`';
        }
        if ($this->isColumnModified(MkbPeer::DIAGNAME)) {
            $modifiedColumns[':p' . $index++]  = '`DiagName`';
        }
        if ($this->isColumnModified(MkbPeer::PRIM)) {
            $modifiedColumns[':p' . $index++]  = '`Prim`';
        }
        if ($this->isColumnModified(MkbPeer::SEX)) {
            $modifiedColumns[':p' . $index++]  = '`sex`';
        }
        if ($this->isColumnModified(MkbPeer::AGE)) {
            $modifiedColumns[':p' . $index++]  = '`age`';
        }
        if ($this->isColumnModified(MkbPeer::AGE_BU)) {
            $modifiedColumns[':p' . $index++]  = '`age_bu`';
        }
        if ($this->isColumnModified(MkbPeer::AGE_BC)) {
            $modifiedColumns[':p' . $index++]  = '`age_bc`';
        }
        if ($this->isColumnModified(MkbPeer::AGE_EU)) {
            $modifiedColumns[':p' . $index++]  = '`age_eu`';
        }
        if ($this->isColumnModified(MkbPeer::AGE_EC)) {
            $modifiedColumns[':p' . $index++]  = '`age_ec`';
        }
        if ($this->isColumnModified(MkbPeer::CHARACTERS)) {
            $modifiedColumns[':p' . $index++]  = '`characters`';
        }
        if ($this->isColumnModified(MkbPeer::DURATION)) {
            $modifiedColumns[':p' . $index++]  = '`duration`';
        }
        if ($this->isColumnModified(MkbPeer::SERVICE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`service_id`';
        }
        if ($this->isColumnModified(MkbPeer::MKBSUBCLASS_ID)) {
            $modifiedColumns[':p' . $index++]  = '`MKBSubclass_id`';
        }

        $sql = sprintf(
            'INSERT INTO `MKB` (%s) VALUES (%s)',
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
                    case '`ClassID`':
                        $stmt->bindValue($identifier, $this->classid, PDO::PARAM_STR);
                        break;
                    case '`ClassName`':
                        $stmt->bindValue($identifier, $this->classname, PDO::PARAM_STR);
                        break;
                    case '`BlockID`':
                        $stmt->bindValue($identifier, $this->blockid, PDO::PARAM_STR);
                        break;
                    case '`BlockName`':
                        $stmt->bindValue($identifier, $this->blockname, PDO::PARAM_STR);
                        break;
                    case '`DiagID`':
                        $stmt->bindValue($identifier, $this->diagid, PDO::PARAM_STR);
                        break;
                    case '`DiagName`':
                        $stmt->bindValue($identifier, $this->diagname, PDO::PARAM_STR);
                        break;
                    case '`Prim`':
                        $stmt->bindValue($identifier, $this->prim, PDO::PARAM_STR);
                        break;
                    case '`sex`':
                        $stmt->bindValue($identifier, (int) $this->sex, PDO::PARAM_INT);
                        break;
                    case '`age`':
                        $stmt->bindValue($identifier, $this->age, PDO::PARAM_STR);
                        break;
                    case '`age_bu`':
                        $stmt->bindValue($identifier, $this->age_bu, PDO::PARAM_INT);
                        break;
                    case '`age_bc`':
                        $stmt->bindValue($identifier, $this->age_bc, PDO::PARAM_INT);
                        break;
                    case '`age_eu`':
                        $stmt->bindValue($identifier, $this->age_eu, PDO::PARAM_INT);
                        break;
                    case '`age_ec`':
                        $stmt->bindValue($identifier, $this->age_ec, PDO::PARAM_INT);
                        break;
                    case '`characters`':
                        $stmt->bindValue($identifier, $this->characters, PDO::PARAM_INT);
                        break;
                    case '`duration`':
                        $stmt->bindValue($identifier, $this->duration, PDO::PARAM_INT);
                        break;
                    case '`service_id`':
                        $stmt->bindValue($identifier, $this->service_id, PDO::PARAM_INT);
                        break;
                    case '`MKBSubclass_id`':
                        $stmt->bindValue($identifier, $this->mkbsubclass_id, PDO::PARAM_INT);
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


            if (($retval = MkbPeer::doValidate($this, $columns)) !== true) {
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
        $pos = MkbPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getclassId();
                break;
            case 2:
                return $this->getclassName();
                break;
            case 3:
                return $this->getblockId();
                break;
            case 4:
                return $this->getblockName();
                break;
            case 5:
                return $this->getdiagId();
                break;
            case 6:
                return $this->getdiagName();
                break;
            case 7:
                return $this->getprim();
                break;
            case 8:
                return $this->getsex();
                break;
            case 9:
                return $this->getage();
                break;
            case 10:
                return $this->getageBu();
                break;
            case 11:
                return $this->getageBc();
                break;
            case 12:
                return $this->getageEu();
                break;
            case 13:
                return $this->getageEc();
                break;
            case 14:
                return $this->getcharacters();
                break;
            case 15:
                return $this->getduration();
                break;
            case 16:
                return $this->getserviceId();
                break;
            case 17:
                return $this->getMkbSubclassId();
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
     *
     * @return array an associative array containing the field names (as keys) and field values
     */
    public function toArray($keyType = BasePeer::TYPE_PHPNAME, $includeLazyLoadColumns = true, $alreadyDumpedObjects = array())
    {
        if (isset($alreadyDumpedObjects['Mkb'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['Mkb'][$this->getPrimaryKey()] = true;
        $keys = MkbPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getclassId(),
            $keys[2] => $this->getclassName(),
            $keys[3] => $this->getblockId(),
            $keys[4] => $this->getblockName(),
            $keys[5] => $this->getdiagId(),
            $keys[6] => $this->getdiagName(),
            $keys[7] => $this->getprim(),
            $keys[8] => $this->getsex(),
            $keys[9] => $this->getage(),
            $keys[10] => $this->getageBu(),
            $keys[11] => $this->getageBc(),
            $keys[12] => $this->getageEu(),
            $keys[13] => $this->getageEc(),
            $keys[14] => $this->getcharacters(),
            $keys[15] => $this->getduration(),
            $keys[16] => $this->getserviceId(),
            $keys[17] => $this->getMkbSubclassId(),
        );

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
        $pos = MkbPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setclassId($value);
                break;
            case 2:
                $this->setclassName($value);
                break;
            case 3:
                $this->setblockId($value);
                break;
            case 4:
                $this->setblockName($value);
                break;
            case 5:
                $this->setdiagId($value);
                break;
            case 6:
                $this->setdiagName($value);
                break;
            case 7:
                $this->setprim($value);
                break;
            case 8:
                $this->setsex($value);
                break;
            case 9:
                $this->setage($value);
                break;
            case 10:
                $this->setageBu($value);
                break;
            case 11:
                $this->setageBc($value);
                break;
            case 12:
                $this->setageEu($value);
                break;
            case 13:
                $this->setageEc($value);
                break;
            case 14:
                $this->setcharacters($value);
                break;
            case 15:
                $this->setduration($value);
                break;
            case 16:
                $this->setserviceId($value);
                break;
            case 17:
                $this->setMkbSubclassId($value);
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
        $keys = MkbPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setclassId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setclassName($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setblockId($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setblockName($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdiagId($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setdiagName($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setprim($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setsex($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setage($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setageBu($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setageBc($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setageEu($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setageEc($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setcharacters($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setduration($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setserviceId($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setMkbSubclassId($arr[$keys[17]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(MkbPeer::DATABASE_NAME);

        if ($this->isColumnModified(MkbPeer::ID)) $criteria->add(MkbPeer::ID, $this->id);
        if ($this->isColumnModified(MkbPeer::CLASSID)) $criteria->add(MkbPeer::CLASSID, $this->classid);
        if ($this->isColumnModified(MkbPeer::CLASSNAME)) $criteria->add(MkbPeer::CLASSNAME, $this->classname);
        if ($this->isColumnModified(MkbPeer::BLOCKID)) $criteria->add(MkbPeer::BLOCKID, $this->blockid);
        if ($this->isColumnModified(MkbPeer::BLOCKNAME)) $criteria->add(MkbPeer::BLOCKNAME, $this->blockname);
        if ($this->isColumnModified(MkbPeer::DIAGID)) $criteria->add(MkbPeer::DIAGID, $this->diagid);
        if ($this->isColumnModified(MkbPeer::DIAGNAME)) $criteria->add(MkbPeer::DIAGNAME, $this->diagname);
        if ($this->isColumnModified(MkbPeer::PRIM)) $criteria->add(MkbPeer::PRIM, $this->prim);
        if ($this->isColumnModified(MkbPeer::SEX)) $criteria->add(MkbPeer::SEX, $this->sex);
        if ($this->isColumnModified(MkbPeer::AGE)) $criteria->add(MkbPeer::AGE, $this->age);
        if ($this->isColumnModified(MkbPeer::AGE_BU)) $criteria->add(MkbPeer::AGE_BU, $this->age_bu);
        if ($this->isColumnModified(MkbPeer::AGE_BC)) $criteria->add(MkbPeer::AGE_BC, $this->age_bc);
        if ($this->isColumnModified(MkbPeer::AGE_EU)) $criteria->add(MkbPeer::AGE_EU, $this->age_eu);
        if ($this->isColumnModified(MkbPeer::AGE_EC)) $criteria->add(MkbPeer::AGE_EC, $this->age_ec);
        if ($this->isColumnModified(MkbPeer::CHARACTERS)) $criteria->add(MkbPeer::CHARACTERS, $this->characters);
        if ($this->isColumnModified(MkbPeer::DURATION)) $criteria->add(MkbPeer::DURATION, $this->duration);
        if ($this->isColumnModified(MkbPeer::SERVICE_ID)) $criteria->add(MkbPeer::SERVICE_ID, $this->service_id);
        if ($this->isColumnModified(MkbPeer::MKBSUBCLASS_ID)) $criteria->add(MkbPeer::MKBSUBCLASS_ID, $this->mkbsubclass_id);

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
        $criteria = new Criteria(MkbPeer::DATABASE_NAME);
        $criteria->add(MkbPeer::ID, $this->id);

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
     * @param object $copyObj An object of Mkb (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setclassId($this->getclassId());
        $copyObj->setclassName($this->getclassName());
        $copyObj->setblockId($this->getblockId());
        $copyObj->setblockName($this->getblockName());
        $copyObj->setdiagId($this->getdiagId());
        $copyObj->setdiagName($this->getdiagName());
        $copyObj->setprim($this->getprim());
        $copyObj->setsex($this->getsex());
        $copyObj->setage($this->getage());
        $copyObj->setageBu($this->getageBu());
        $copyObj->setageBc($this->getageBc());
        $copyObj->setageEu($this->getageEu());
        $copyObj->setageEc($this->getageEc());
        $copyObj->setcharacters($this->getcharacters());
        $copyObj->setduration($this->getduration());
        $copyObj->setserviceId($this->getserviceId());
        $copyObj->setMkbSubclassId($this->getMkbSubclassId());
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
     * @return Mkb Clone of current object.
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
     * @return MkbPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new MkbPeer();
        }

        return self::$peer;
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->classid = null;
        $this->classname = null;
        $this->blockid = null;
        $this->blockname = null;
        $this->diagid = null;
        $this->diagname = null;
        $this->prim = null;
        $this->sex = null;
        $this->age = null;
        $this->age_bu = null;
        $this->age_bc = null;
        $this->age_eu = null;
        $this->age_ec = null;
        $this->characters = null;
        $this->duration = null;
        $this->service_id = null;
        $this->mkbsubclass_id = null;
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

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(MkbPeer::DEFAULT_STRING_FORMAT);
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
