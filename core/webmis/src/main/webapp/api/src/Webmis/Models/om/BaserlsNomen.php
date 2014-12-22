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
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentQuery;
use Webmis\Models\RlsBalanceOfGoods;
use Webmis\Models\RlsBalanceOfGoodsQuery;
use Webmis\Models\rbUnit;
use Webmis\Models\rbUnitQuery;
use Webmis\Models\rlsActMatters;
use Webmis\Models\rlsActMattersQuery;
use Webmis\Models\rlsFilling;
use Webmis\Models\rlsFillingQuery;
use Webmis\Models\rlsForm;
use Webmis\Models\rlsFormQuery;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenPeer;
use Webmis\Models\rlsNomenQuery;
use Webmis\Models\rlsPacking;
use Webmis\Models\rlsPackingQuery;
use Webmis\Models\rlsTradeName;
use Webmis\Models\rlsTradeNameQuery;

/**
 * Base class that represents a row from the 'rlsNomen' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaserlsNomen extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\rlsNomenPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        rlsNomenPeer
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
     * The value for the actmatters_id field.
     * @var        int
     */
    protected $actmatters_id;

    /**
     * The value for the tradename_id field.
     * @var        int
     */
    protected $tradename_id;

    /**
     * The value for the form_id field.
     * @var        int
     */
    protected $form_id;

    /**
     * The value for the packing_id field.
     * @var        int
     */
    protected $packing_id;

    /**
     * The value for the filling_id field.
     * @var        int
     */
    protected $filling_id;

    /**
     * The value for the unit_id field.
     * @var        int
     */
    protected $unit_id;

    /**
     * The value for the dosagevalue field.
     * @var        string
     */
    protected $dosagevalue;

    /**
     * The value for the dosageunit_id field.
     * @var        int
     */
    protected $dosageunit_id;

    /**
     * The value for the druglifetime field.
     * @var        int
     */
    protected $druglifetime;

    /**
     * The value for the regdate field.
     * @var        string
     */
    protected $regdate;

    /**
     * The value for the anndate field.
     * @var        string
     */
    protected $anndate;

    /**
     * @var        rbUnit
     */
    protected $arbUnitRelatedByunitId;

    /**
     * @var        rbUnit
     */
    protected $arbUnitRelatedBydosageUnitId;

    /**
     * @var        rlsFilling
     */
    protected $arlsFilling;

    /**
     * @var        rlsForm
     */
    protected $arlsForm;

    /**
     * @var        rlsPacking
     */
    protected $arlsPacking;

    /**
     * @var        rlsActMatters
     */
    protected $arlsActMatters;

    /**
     * @var        rlsTradeName
     */
    protected $arlsTradeName;

    /**
     * @var        PropelObjectCollection|DrugComponent[] Collection to store aggregation of DrugComponent objects.
     */
    protected $collDrugComponents;
    protected $collDrugComponentsPartial;

    /**
     * @var        PropelObjectCollection|RlsBalanceOfGoods[] Collection to store aggregation of RlsBalanceOfGoods objects.
     */
    protected $collRlsBalanceOfGoodss;
    protected $collRlsBalanceOfGoodssPartial;

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
    protected $rlsBalanceOfGoodssScheduledForDeletion = null;

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
     * Get the [actmatters_id] column value.
     *
     * @return int
     */
    public function getactMattersId()
    {
        return $this->actmatters_id;
    }

    /**
     * Get the [tradename_id] column value.
     *
     * @return int
     */
    public function gettradeNameId()
    {
        return $this->tradename_id;
    }

    /**
     * Get the [form_id] column value.
     *
     * @return int
     */
    public function getformId()
    {
        return $this->form_id;
    }

    /**
     * Get the [packing_id] column value.
     *
     * @return int
     */
    public function getpackingId()
    {
        return $this->packing_id;
    }

    /**
     * Get the [filling_id] column value.
     *
     * @return int
     */
    public function getfillingId()
    {
        return $this->filling_id;
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
     * Get the [dosagevalue] column value.
     *
     * @return string
     */
    public function getdosageValue()
    {
        return $this->dosagevalue;
    }

    /**
     * Get the [dosageunit_id] column value.
     *
     * @return int
     */
    public function getdosageUnitId()
    {
        return $this->dosageunit_id;
    }

    /**
     * Get the [druglifetime] column value.
     *
     * @return int
     */
    public function getdrugLifetime()
    {
        return $this->druglifetime;
    }

    /**
     * Get the [optionally formatted] temporal [regdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getregDate($format = '%Y-%m-%d')
    {
        if ($this->regdate === null) {
            return null;
        }

        if ($this->regdate === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->regdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->regdate, true), $x);
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
     * Get the [optionally formatted] temporal [anndate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getannDate($format = '%Y-%m-%d')
    {
        if ($this->anndate === null) {
            return null;
        }

        if ($this->anndate === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->anndate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->anndate, true), $x);
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
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Set the value of [actmatters_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setactMattersId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->actmatters_id !== $v) {
            $this->actmatters_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::ACTMATTERS_ID;
        }

        if ($this->arlsActMatters !== null && $this->arlsActMatters->getid() !== $v) {
            $this->arlsActMatters = null;
        }


        return $this;
    } // setactMattersId()

    /**
     * Set the value of [tradename_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function settradeNameId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->tradename_id !== $v) {
            $this->tradename_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::TRADENAME_ID;
        }

        if ($this->arlsTradeName !== null && $this->arlsTradeName->getid() !== $v) {
            $this->arlsTradeName = null;
        }


        return $this;
    } // settradeNameId()

    /**
     * Set the value of [form_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setformId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->form_id !== $v) {
            $this->form_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::FORM_ID;
        }

        if ($this->arlsForm !== null && $this->arlsForm->getid() !== $v) {
            $this->arlsForm = null;
        }


        return $this;
    } // setformId()

    /**
     * Set the value of [packing_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setpackingId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->packing_id !== $v) {
            $this->packing_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::PACKING_ID;
        }

        if ($this->arlsPacking !== null && $this->arlsPacking->getid() !== $v) {
            $this->arlsPacking = null;
        }


        return $this;
    } // setpackingId()

    /**
     * Set the value of [filling_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setfillingId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->filling_id !== $v) {
            $this->filling_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::FILLING_ID;
        }

        if ($this->arlsFilling !== null && $this->arlsFilling->getid() !== $v) {
            $this->arlsFilling = null;
        }


        return $this;
    } // setfillingId()

    /**
     * Set the value of [unit_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setunitId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->unit_id !== $v) {
            $this->unit_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::UNIT_ID;
        }

        if ($this->arbUnitRelatedByunitId !== null && $this->arbUnitRelatedByunitId->getid() !== $v) {
            $this->arbUnitRelatedByunitId = null;
        }


        return $this;
    } // setunitId()

    /**
     * Set the value of [dosagevalue] column.
     *
     * @param string $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setdosageValue($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->dosagevalue !== $v) {
            $this->dosagevalue = $v;
            $this->modifiedColumns[] = rlsNomenPeer::DOSAGEVALUE;
        }


        return $this;
    } // setdosageValue()

    /**
     * Set the value of [dosageunit_id] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setdosageUnitId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->dosageunit_id !== $v) {
            $this->dosageunit_id = $v;
            $this->modifiedColumns[] = rlsNomenPeer::DOSAGEUNIT_ID;
        }

        if ($this->arbUnitRelatedBydosageUnitId !== null && $this->arbUnitRelatedBydosageUnitId->getid() !== $v) {
            $this->arbUnitRelatedBydosageUnitId = null;
        }


        return $this;
    } // setdosageUnitId()

    /**
     * Set the value of [druglifetime] column.
     *
     * @param int $v new value
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setdrugLifetime($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->druglifetime !== $v) {
            $this->druglifetime = $v;
            $this->modifiedColumns[] = rlsNomenPeer::DRUGLIFETIME;
        }


        return $this;
    } // setdrugLifetime()

    /**
     * Sets the value of [regdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setregDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->regdate !== null || $dt !== null) {
            $currentDateAsString = ($this->regdate !== null && $tmpDt = new DateTime($this->regdate)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->regdate = $newDateAsString;
                $this->modifiedColumns[] = rlsNomenPeer::REGDATE;
            }
        } // if either are not null


        return $this;
    } // setregDate()

    /**
     * Sets the value of [anndate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setannDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->anndate !== null || $dt !== null) {
            $currentDateAsString = ($this->anndate !== null && $tmpDt = new DateTime($this->anndate)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->anndate = $newDateAsString;
                $this->modifiedColumns[] = rlsNomenPeer::ANNDATE;
            }
        } // if either are not null


        return $this;
    } // setannDate()

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
            $this->actmatters_id = ($row[$startcol + 1] !== null) ? (int) $row[$startcol + 1] : null;
            $this->tradename_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->form_id = ($row[$startcol + 3] !== null) ? (int) $row[$startcol + 3] : null;
            $this->packing_id = ($row[$startcol + 4] !== null) ? (int) $row[$startcol + 4] : null;
            $this->filling_id = ($row[$startcol + 5] !== null) ? (int) $row[$startcol + 5] : null;
            $this->unit_id = ($row[$startcol + 6] !== null) ? (int) $row[$startcol + 6] : null;
            $this->dosagevalue = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->dosageunit_id = ($row[$startcol + 8] !== null) ? (int) $row[$startcol + 8] : null;
            $this->druglifetime = ($row[$startcol + 9] !== null) ? (int) $row[$startcol + 9] : null;
            $this->regdate = ($row[$startcol + 10] !== null) ? (string) $row[$startcol + 10] : null;
            $this->anndate = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 12; // 12 = rlsNomenPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating rlsNomen object", $e);
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

        if ($this->arlsActMatters !== null && $this->actmatters_id !== $this->arlsActMatters->getid()) {
            $this->arlsActMatters = null;
        }
        if ($this->arlsTradeName !== null && $this->tradename_id !== $this->arlsTradeName->getid()) {
            $this->arlsTradeName = null;
        }
        if ($this->arlsForm !== null && $this->form_id !== $this->arlsForm->getid()) {
            $this->arlsForm = null;
        }
        if ($this->arlsPacking !== null && $this->packing_id !== $this->arlsPacking->getid()) {
            $this->arlsPacking = null;
        }
        if ($this->arlsFilling !== null && $this->filling_id !== $this->arlsFilling->getid()) {
            $this->arlsFilling = null;
        }
        if ($this->arbUnitRelatedByunitId !== null && $this->unit_id !== $this->arbUnitRelatedByunitId->getid()) {
            $this->arbUnitRelatedByunitId = null;
        }
        if ($this->arbUnitRelatedBydosageUnitId !== null && $this->dosageunit_id !== $this->arbUnitRelatedBydosageUnitId->getid()) {
            $this->arbUnitRelatedBydosageUnitId = null;
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
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = rlsNomenPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->arbUnitRelatedByunitId = null;
            $this->arbUnitRelatedBydosageUnitId = null;
            $this->arlsFilling = null;
            $this->arlsForm = null;
            $this->arlsPacking = null;
            $this->arlsActMatters = null;
            $this->arlsTradeName = null;
            $this->collDrugComponents = null;

            $this->collRlsBalanceOfGoodss = null;

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
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = rlsNomenQuery::create()
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
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                rlsNomenPeer::addInstanceToPool($this);
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

            if ($this->arbUnitRelatedByunitId !== null) {
                if ($this->arbUnitRelatedByunitId->isModified() || $this->arbUnitRelatedByunitId->isNew()) {
                    $affectedRows += $this->arbUnitRelatedByunitId->save($con);
                }
                $this->setrbUnitRelatedByunitId($this->arbUnitRelatedByunitId);
            }

            if ($this->arbUnitRelatedBydosageUnitId !== null) {
                if ($this->arbUnitRelatedBydosageUnitId->isModified() || $this->arbUnitRelatedBydosageUnitId->isNew()) {
                    $affectedRows += $this->arbUnitRelatedBydosageUnitId->save($con);
                }
                $this->setrbUnitRelatedBydosageUnitId($this->arbUnitRelatedBydosageUnitId);
            }

            if ($this->arlsFilling !== null) {
                if ($this->arlsFilling->isModified() || $this->arlsFilling->isNew()) {
                    $affectedRows += $this->arlsFilling->save($con);
                }
                $this->setrlsFilling($this->arlsFilling);
            }

            if ($this->arlsForm !== null) {
                if ($this->arlsForm->isModified() || $this->arlsForm->isNew()) {
                    $affectedRows += $this->arlsForm->save($con);
                }
                $this->setrlsForm($this->arlsForm);
            }

            if ($this->arlsPacking !== null) {
                if ($this->arlsPacking->isModified() || $this->arlsPacking->isNew()) {
                    $affectedRows += $this->arlsPacking->save($con);
                }
                $this->setrlsPacking($this->arlsPacking);
            }

            if ($this->arlsActMatters !== null) {
                if ($this->arlsActMatters->isModified() || $this->arlsActMatters->isNew()) {
                    $affectedRows += $this->arlsActMatters->save($con);
                }
                $this->setrlsActMatters($this->arlsActMatters);
            }

            if ($this->arlsTradeName !== null) {
                if ($this->arlsTradeName->isModified() || $this->arlsTradeName->isNew()) {
                    $affectedRows += $this->arlsTradeName->save($con);
                }
                $this->setrlsTradeName($this->arlsTradeName);
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

            if ($this->rlsBalanceOfGoodssScheduledForDeletion !== null) {
                if (!$this->rlsBalanceOfGoodssScheduledForDeletion->isEmpty()) {
                    RlsBalanceOfGoodsQuery::create()
                        ->filterByPrimaryKeys($this->rlsBalanceOfGoodssScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->rlsBalanceOfGoodssScheduledForDeletion = null;
                }
            }

            if ($this->collRlsBalanceOfGoodss !== null) {
                foreach ($this->collRlsBalanceOfGoodss as $referrerFK) {
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


         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(rlsNomenPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::ACTMATTERS_ID)) {
            $modifiedColumns[':p' . $index++]  = '`actMatters_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::TRADENAME_ID)) {
            $modifiedColumns[':p' . $index++]  = '`tradeName_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::FORM_ID)) {
            $modifiedColumns[':p' . $index++]  = '`form_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::PACKING_ID)) {
            $modifiedColumns[':p' . $index++]  = '`packing_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::FILLING_ID)) {
            $modifiedColumns[':p' . $index++]  = '`filling_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::UNIT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`unit_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::DOSAGEVALUE)) {
            $modifiedColumns[':p' . $index++]  = '`dosageValue`';
        }
        if ($this->isColumnModified(rlsNomenPeer::DOSAGEUNIT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`dosageUnit_id`';
        }
        if ($this->isColumnModified(rlsNomenPeer::DRUGLIFETIME)) {
            $modifiedColumns[':p' . $index++]  = '`drugLifetime`';
        }
        if ($this->isColumnModified(rlsNomenPeer::REGDATE)) {
            $modifiedColumns[':p' . $index++]  = '`regDate`';
        }
        if ($this->isColumnModified(rlsNomenPeer::ANNDATE)) {
            $modifiedColumns[':p' . $index++]  = '`annDate`';
        }

        $sql = sprintf(
            'INSERT INTO `rlsNomen` (%s) VALUES (%s)',
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
                    case '`actMatters_id`':
                        $stmt->bindValue($identifier, $this->actmatters_id, PDO::PARAM_INT);
                        break;
                    case '`tradeName_id`':
                        $stmt->bindValue($identifier, $this->tradename_id, PDO::PARAM_INT);
                        break;
                    case '`form_id`':
                        $stmt->bindValue($identifier, $this->form_id, PDO::PARAM_INT);
                        break;
                    case '`packing_id`':
                        $stmt->bindValue($identifier, $this->packing_id, PDO::PARAM_INT);
                        break;
                    case '`filling_id`':
                        $stmt->bindValue($identifier, $this->filling_id, PDO::PARAM_INT);
                        break;
                    case '`unit_id`':
                        $stmt->bindValue($identifier, $this->unit_id, PDO::PARAM_INT);
                        break;
                    case '`dosageValue`':
                        $stmt->bindValue($identifier, $this->dosagevalue, PDO::PARAM_STR);
                        break;
                    case '`dosageUnit_id`':
                        $stmt->bindValue($identifier, $this->dosageunit_id, PDO::PARAM_INT);
                        break;
                    case '`drugLifetime`':
                        $stmt->bindValue($identifier, $this->druglifetime, PDO::PARAM_INT);
                        break;
                    case '`regDate`':
                        $stmt->bindValue($identifier, $this->regdate, PDO::PARAM_STR);
                        break;
                    case '`annDate`':
                        $stmt->bindValue($identifier, $this->anndate, PDO::PARAM_STR);
                        break;
                }
            }
            $stmt->execute();
        } catch (Exception $e) {
            Propel::log($e->getMessage(), Propel::LOG_ERR);
            throw new PropelException(sprintf('Unable to execute INSERT statement [%s]', $sql), $e);
        }

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

            if ($this->arbUnitRelatedByunitId !== null) {
                if (!$this->arbUnitRelatedByunitId->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arbUnitRelatedByunitId->getValidationFailures());
                }
            }

            if ($this->arbUnitRelatedBydosageUnitId !== null) {
                if (!$this->arbUnitRelatedBydosageUnitId->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arbUnitRelatedBydosageUnitId->getValidationFailures());
                }
            }

            if ($this->arlsFilling !== null) {
                if (!$this->arlsFilling->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsFilling->getValidationFailures());
                }
            }

            if ($this->arlsForm !== null) {
                if (!$this->arlsForm->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsForm->getValidationFailures());
                }
            }

            if ($this->arlsPacking !== null) {
                if (!$this->arlsPacking->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsPacking->getValidationFailures());
                }
            }

            if ($this->arlsActMatters !== null) {
                if (!$this->arlsActMatters->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsActMatters->getValidationFailures());
                }
            }

            if ($this->arlsTradeName !== null) {
                if (!$this->arlsTradeName->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->arlsTradeName->getValidationFailures());
                }
            }


            if (($retval = rlsNomenPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collDrugComponents !== null) {
                    foreach ($this->collDrugComponents as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collRlsBalanceOfGoodss !== null) {
                    foreach ($this->collRlsBalanceOfGoodss as $referrerFK) {
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
        $pos = rlsNomenPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getactMattersId();
                break;
            case 2:
                return $this->gettradeNameId();
                break;
            case 3:
                return $this->getformId();
                break;
            case 4:
                return $this->getpackingId();
                break;
            case 5:
                return $this->getfillingId();
                break;
            case 6:
                return $this->getunitId();
                break;
            case 7:
                return $this->getdosageValue();
                break;
            case 8:
                return $this->getdosageUnitId();
                break;
            case 9:
                return $this->getdrugLifetime();
                break;
            case 10:
                return $this->getregDate();
                break;
            case 11:
                return $this->getannDate();
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
        if (isset($alreadyDumpedObjects['rlsNomen'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['rlsNomen'][$this->getPrimaryKey()] = true;
        $keys = rlsNomenPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getactMattersId(),
            $keys[2] => $this->gettradeNameId(),
            $keys[3] => $this->getformId(),
            $keys[4] => $this->getpackingId(),
            $keys[5] => $this->getfillingId(),
            $keys[6] => $this->getunitId(),
            $keys[7] => $this->getdosageValue(),
            $keys[8] => $this->getdosageUnitId(),
            $keys[9] => $this->getdrugLifetime(),
            $keys[10] => $this->getregDate(),
            $keys[11] => $this->getannDate(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->arbUnitRelatedByunitId) {
                $result['rbUnitRelatedByunitId'] = $this->arbUnitRelatedByunitId->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arbUnitRelatedBydosageUnitId) {
                $result['rbUnitRelatedBydosageUnitId'] = $this->arbUnitRelatedBydosageUnitId->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsFilling) {
                $result['rlsFilling'] = $this->arlsFilling->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsForm) {
                $result['rlsForm'] = $this->arlsForm->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsPacking) {
                $result['rlsPacking'] = $this->arlsPacking->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsActMatters) {
                $result['rlsActMatters'] = $this->arlsActMatters->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->arlsTradeName) {
                $result['rlsTradeName'] = $this->arlsTradeName->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collDrugComponents) {
                $result['DrugComponents'] = $this->collDrugComponents->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collRlsBalanceOfGoodss) {
                $result['RlsBalanceOfGoodss'] = $this->collRlsBalanceOfGoodss->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = rlsNomenPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setactMattersId($value);
                break;
            case 2:
                $this->settradeNameId($value);
                break;
            case 3:
                $this->setformId($value);
                break;
            case 4:
                $this->setpackingId($value);
                break;
            case 5:
                $this->setfillingId($value);
                break;
            case 6:
                $this->setunitId($value);
                break;
            case 7:
                $this->setdosageValue($value);
                break;
            case 8:
                $this->setdosageUnitId($value);
                break;
            case 9:
                $this->setdrugLifetime($value);
                break;
            case 10:
                $this->setregDate($value);
                break;
            case 11:
                $this->setannDate($value);
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
        $keys = rlsNomenPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setactMattersId($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->settradeNameId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setformId($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setpackingId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setfillingId($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setunitId($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setdosageValue($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setdosageUnitId($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setdrugLifetime($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setregDate($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setannDate($arr[$keys[11]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(rlsNomenPeer::DATABASE_NAME);

        if ($this->isColumnModified(rlsNomenPeer::ID)) $criteria->add(rlsNomenPeer::ID, $this->id);
        if ($this->isColumnModified(rlsNomenPeer::ACTMATTERS_ID)) $criteria->add(rlsNomenPeer::ACTMATTERS_ID, $this->actmatters_id);
        if ($this->isColumnModified(rlsNomenPeer::TRADENAME_ID)) $criteria->add(rlsNomenPeer::TRADENAME_ID, $this->tradename_id);
        if ($this->isColumnModified(rlsNomenPeer::FORM_ID)) $criteria->add(rlsNomenPeer::FORM_ID, $this->form_id);
        if ($this->isColumnModified(rlsNomenPeer::PACKING_ID)) $criteria->add(rlsNomenPeer::PACKING_ID, $this->packing_id);
        if ($this->isColumnModified(rlsNomenPeer::FILLING_ID)) $criteria->add(rlsNomenPeer::FILLING_ID, $this->filling_id);
        if ($this->isColumnModified(rlsNomenPeer::UNIT_ID)) $criteria->add(rlsNomenPeer::UNIT_ID, $this->unit_id);
        if ($this->isColumnModified(rlsNomenPeer::DOSAGEVALUE)) $criteria->add(rlsNomenPeer::DOSAGEVALUE, $this->dosagevalue);
        if ($this->isColumnModified(rlsNomenPeer::DOSAGEUNIT_ID)) $criteria->add(rlsNomenPeer::DOSAGEUNIT_ID, $this->dosageunit_id);
        if ($this->isColumnModified(rlsNomenPeer::DRUGLIFETIME)) $criteria->add(rlsNomenPeer::DRUGLIFETIME, $this->druglifetime);
        if ($this->isColumnModified(rlsNomenPeer::REGDATE)) $criteria->add(rlsNomenPeer::REGDATE, $this->regdate);
        if ($this->isColumnModified(rlsNomenPeer::ANNDATE)) $criteria->add(rlsNomenPeer::ANNDATE, $this->anndate);

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
        $criteria = new Criteria(rlsNomenPeer::DATABASE_NAME);
        $criteria->add(rlsNomenPeer::ID, $this->id);

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
     * @param object $copyObj An object of rlsNomen (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setactMattersId($this->getactMattersId());
        $copyObj->settradeNameId($this->gettradeNameId());
        $copyObj->setformId($this->getformId());
        $copyObj->setpackingId($this->getpackingId());
        $copyObj->setfillingId($this->getfillingId());
        $copyObj->setunitId($this->getunitId());
        $copyObj->setdosageValue($this->getdosageValue());
        $copyObj->setdosageUnitId($this->getdosageUnitId());
        $copyObj->setdrugLifetime($this->getdrugLifetime());
        $copyObj->setregDate($this->getregDate());
        $copyObj->setannDate($this->getannDate());

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

            foreach ($this->getRlsBalanceOfGoodss() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addRlsBalanceOfGoods($relObj->copy($deepCopy));
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
     * @return rlsNomen Clone of current object.
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
     * @return rlsNomenPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new rlsNomenPeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a rbUnit object.
     *
     * @param             rbUnit $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrbUnitRelatedByunitId(rbUnit $v = null)
    {
        if ($v === null) {
            $this->setunitId(NULL);
        } else {
            $this->setunitId($v->getid());
        }

        $this->arbUnitRelatedByunitId = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rbUnit object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomenRelatedByunitId($this);
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
    public function getrbUnitRelatedByunitId(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arbUnitRelatedByunitId === null && ($this->unit_id !== null) && $doQuery) {
            $this->arbUnitRelatedByunitId = rbUnitQuery::create()->findPk($this->unit_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arbUnitRelatedByunitId->addrlsNomensRelatedByunitId($this);
             */
        }

        return $this->arbUnitRelatedByunitId;
    }

    /**
     * Declares an association between this object and a rbUnit object.
     *
     * @param             rbUnit $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrbUnitRelatedBydosageUnitId(rbUnit $v = null)
    {
        if ($v === null) {
            $this->setdosageUnitId(NULL);
        } else {
            $this->setdosageUnitId($v->getid());
        }

        $this->arbUnitRelatedBydosageUnitId = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rbUnit object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomenRelatedBydosageUnitId($this);
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
    public function getrbUnitRelatedBydosageUnitId(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arbUnitRelatedBydosageUnitId === null && ($this->dosageunit_id !== null) && $doQuery) {
            $this->arbUnitRelatedBydosageUnitId = rbUnitQuery::create()->findPk($this->dosageunit_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arbUnitRelatedBydosageUnitId->addrlsNomensRelatedBydosageUnitId($this);
             */
        }

        return $this->arbUnitRelatedBydosageUnitId;
    }

    /**
     * Declares an association between this object and a rlsFilling object.
     *
     * @param             rlsFilling $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsFilling(rlsFilling $v = null)
    {
        if ($v === null) {
            $this->setfillingId(NULL);
        } else {
            $this->setfillingId($v->getid());
        }

        $this->arlsFilling = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsFilling object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomen($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsFilling object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsFilling The associated rlsFilling object.
     * @throws PropelException
     */
    public function getrlsFilling(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsFilling === null && ($this->filling_id !== null) && $doQuery) {
            $this->arlsFilling = rlsFillingQuery::create()->findPk($this->filling_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsFilling->addrlsNomens($this);
             */
        }

        return $this->arlsFilling;
    }

    /**
     * Declares an association between this object and a rlsForm object.
     *
     * @param             rlsForm $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsForm(rlsForm $v = null)
    {
        if ($v === null) {
            $this->setformId(NULL);
        } else {
            $this->setformId($v->getid());
        }

        $this->arlsForm = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsForm object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomen($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsForm object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsForm The associated rlsForm object.
     * @throws PropelException
     */
    public function getrlsForm(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsForm === null && ($this->form_id !== null) && $doQuery) {
            $this->arlsForm = rlsFormQuery::create()->findPk($this->form_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsForm->addrlsNomens($this);
             */
        }

        return $this->arlsForm;
    }

    /**
     * Declares an association between this object and a rlsPacking object.
     *
     * @param             rlsPacking $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsPacking(rlsPacking $v = null)
    {
        if ($v === null) {
            $this->setpackingId(NULL);
        } else {
            $this->setpackingId($v->getid());
        }

        $this->arlsPacking = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsPacking object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomen($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsPacking object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsPacking The associated rlsPacking object.
     * @throws PropelException
     */
    public function getrlsPacking(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsPacking === null && ($this->packing_id !== null) && $doQuery) {
            $this->arlsPacking = rlsPackingQuery::create()->findPk($this->packing_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsPacking->addrlsNomens($this);
             */
        }

        return $this->arlsPacking;
    }

    /**
     * Declares an association between this object and a rlsActMatters object.
     *
     * @param             rlsActMatters $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsActMatters(rlsActMatters $v = null)
    {
        if ($v === null) {
            $this->setactMattersId(NULL);
        } else {
            $this->setactMattersId($v->getid());
        }

        $this->arlsActMatters = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsActMatters object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomen($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsActMatters object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsActMatters The associated rlsActMatters object.
     * @throws PropelException
     */
    public function getrlsActMatters(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsActMatters === null && ($this->actmatters_id !== null) && $doQuery) {
            $this->arlsActMatters = rlsActMattersQuery::create()->findPk($this->actmatters_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsActMatters->addrlsNomens($this);
             */
        }

        return $this->arlsActMatters;
    }

    /**
     * Declares an association between this object and a rlsTradeName object.
     *
     * @param             rlsTradeName $v
     * @return rlsNomen The current object (for fluent API support)
     * @throws PropelException
     */
    public function setrlsTradeName(rlsTradeName $v = null)
    {
        if ($v === null) {
            $this->settradeNameId(NULL);
        } else {
            $this->settradeNameId($v->getid());
        }

        $this->arlsTradeName = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the rlsTradeName object, it will not be re-added.
        if ($v !== null) {
            $v->addrlsNomen($this);
        }


        return $this;
    }


    /**
     * Get the associated rlsTradeName object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return rlsTradeName The associated rlsTradeName object.
     * @throws PropelException
     */
    public function getrlsTradeName(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->arlsTradeName === null && ($this->tradename_id !== null) && $doQuery) {
            $this->arlsTradeName = rlsTradeNameQuery::create()->findPk($this->tradename_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->arlsTradeName->addrlsNomens($this);
             */
        }

        return $this->arlsTradeName;
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
        if ('RlsBalanceOfGoods' == $relationName) {
            $this->initRlsBalanceOfGoodss();
        }
    }

    /**
     * Clears out the collDrugComponents collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return rlsNomen The current object (for fluent API support)
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
     * If this rlsNomen is new, it will return
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
                    ->filterByrlsNomen($this)
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
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setDrugComponents(PropelCollection $drugComponents, PropelPDO $con = null)
    {
        $drugComponentsToDelete = $this->getDrugComponents(new Criteria(), $con)->diff($drugComponents);

        $this->drugComponentsScheduledForDeletion = unserialize(serialize($drugComponentsToDelete));

        foreach ($drugComponentsToDelete as $drugComponentRemoved) {
            $drugComponentRemoved->setrlsNomen(null);
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
                ->filterByrlsNomen($this)
                ->count($con);
        }

        return count($this->collDrugComponents);
    }

    /**
     * Method called to associate a DrugComponent object to this object
     * through the DrugComponent foreign key attribute.
     *
     * @param    DrugComponent $l DrugComponent
     * @return rlsNomen The current object (for fluent API support)
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
        $drugComponent->setrlsNomen($this);
    }

    /**
     * @param	DrugComponent $drugComponent The drugComponent object to remove.
     * @return rlsNomen The current object (for fluent API support)
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
            $drugComponent->setrlsNomen(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rlsNomen is new, it will return
     * an empty collection; or if this rlsNomen has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rlsNomen.
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
     * Otherwise if this rlsNomen is new, it will return
     * an empty collection; or if this rlsNomen has previously
     * been saved, it will retrieve related DrugComponents from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rlsNomen.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|DrugComponent[] List of DrugComponent objects
     */
    public function getDrugComponentsJoinrbUnit($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = DrugComponentQuery::create(null, $criteria);
        $query->joinWith('rbUnit', $join_behavior);

        return $this->getDrugComponents($query, $con);
    }

    /**
     * Clears out the collRlsBalanceOfGoodss collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return rlsNomen The current object (for fluent API support)
     * @see        addRlsBalanceOfGoodss()
     */
    public function clearRlsBalanceOfGoodss()
    {
        $this->collRlsBalanceOfGoodss = null; // important to set this to null since that means it is uninitialized
        $this->collRlsBalanceOfGoodssPartial = null;

        return $this;
    }

    /**
     * reset is the collRlsBalanceOfGoodss collection loaded partially
     *
     * @return void
     */
    public function resetPartialRlsBalanceOfGoodss($v = true)
    {
        $this->collRlsBalanceOfGoodssPartial = $v;
    }

    /**
     * Initializes the collRlsBalanceOfGoodss collection.
     *
     * By default this just sets the collRlsBalanceOfGoodss collection to an empty array (like clearcollRlsBalanceOfGoodss());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initRlsBalanceOfGoodss($overrideExisting = true)
    {
        if (null !== $this->collRlsBalanceOfGoodss && !$overrideExisting) {
            return;
        }
        $this->collRlsBalanceOfGoodss = new PropelObjectCollection();
        $this->collRlsBalanceOfGoodss->setModel('RlsBalanceOfGoods');
    }

    /**
     * Gets an array of RlsBalanceOfGoods objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this rlsNomen is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|RlsBalanceOfGoods[] List of RlsBalanceOfGoods objects
     * @throws PropelException
     */
    public function getRlsBalanceOfGoodss($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collRlsBalanceOfGoodssPartial && !$this->isNew();
        if (null === $this->collRlsBalanceOfGoodss || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collRlsBalanceOfGoodss) {
                // return empty collection
                $this->initRlsBalanceOfGoodss();
            } else {
                $collRlsBalanceOfGoodss = RlsBalanceOfGoodsQuery::create(null, $criteria)
                    ->filterByrlsNomen($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collRlsBalanceOfGoodssPartial && count($collRlsBalanceOfGoodss)) {
                      $this->initRlsBalanceOfGoodss(false);

                      foreach($collRlsBalanceOfGoodss as $obj) {
                        if (false == $this->collRlsBalanceOfGoodss->contains($obj)) {
                          $this->collRlsBalanceOfGoodss->append($obj);
                        }
                      }

                      $this->collRlsBalanceOfGoodssPartial = true;
                    }

                    $collRlsBalanceOfGoodss->getInternalIterator()->rewind();
                    return $collRlsBalanceOfGoodss;
                }

                if($partial && $this->collRlsBalanceOfGoodss) {
                    foreach($this->collRlsBalanceOfGoodss as $obj) {
                        if($obj->isNew()) {
                            $collRlsBalanceOfGoodss[] = $obj;
                        }
                    }
                }

                $this->collRlsBalanceOfGoodss = $collRlsBalanceOfGoodss;
                $this->collRlsBalanceOfGoodssPartial = false;
            }
        }

        return $this->collRlsBalanceOfGoodss;
    }

    /**
     * Sets a collection of RlsBalanceOfGoods objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $rlsBalanceOfGoodss A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return rlsNomen The current object (for fluent API support)
     */
    public function setRlsBalanceOfGoodss(PropelCollection $rlsBalanceOfGoodss, PropelPDO $con = null)
    {
        $rlsBalanceOfGoodssToDelete = $this->getRlsBalanceOfGoodss(new Criteria(), $con)->diff($rlsBalanceOfGoodss);

        $this->rlsBalanceOfGoodssScheduledForDeletion = unserialize(serialize($rlsBalanceOfGoodssToDelete));

        foreach ($rlsBalanceOfGoodssToDelete as $rlsBalanceOfGoodsRemoved) {
            $rlsBalanceOfGoodsRemoved->setrlsNomen(null);
        }

        $this->collRlsBalanceOfGoodss = null;
        foreach ($rlsBalanceOfGoodss as $rlsBalanceOfGoods) {
            $this->addRlsBalanceOfGoods($rlsBalanceOfGoods);
        }

        $this->collRlsBalanceOfGoodss = $rlsBalanceOfGoodss;
        $this->collRlsBalanceOfGoodssPartial = false;

        return $this;
    }

    /**
     * Returns the number of related RlsBalanceOfGoods objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related RlsBalanceOfGoods objects.
     * @throws PropelException
     */
    public function countRlsBalanceOfGoodss(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collRlsBalanceOfGoodssPartial && !$this->isNew();
        if (null === $this->collRlsBalanceOfGoodss || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collRlsBalanceOfGoodss) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getRlsBalanceOfGoodss());
            }
            $query = RlsBalanceOfGoodsQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByrlsNomen($this)
                ->count($con);
        }

        return count($this->collRlsBalanceOfGoodss);
    }

    /**
     * Method called to associate a RlsBalanceOfGoods object to this object
     * through the RlsBalanceOfGoods foreign key attribute.
     *
     * @param    RlsBalanceOfGoods $l RlsBalanceOfGoods
     * @return rlsNomen The current object (for fluent API support)
     */
    public function addRlsBalanceOfGoods(RlsBalanceOfGoods $l)
    {
        if ($this->collRlsBalanceOfGoodss === null) {
            $this->initRlsBalanceOfGoodss();
            $this->collRlsBalanceOfGoodssPartial = true;
        }
        if (!in_array($l, $this->collRlsBalanceOfGoodss->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddRlsBalanceOfGoods($l);
        }

        return $this;
    }

    /**
     * @param	RlsBalanceOfGoods $rlsBalanceOfGoods The rlsBalanceOfGoods object to add.
     */
    protected function doAddRlsBalanceOfGoods($rlsBalanceOfGoods)
    {
        $this->collRlsBalanceOfGoodss[]= $rlsBalanceOfGoods;
        $rlsBalanceOfGoods->setrlsNomen($this);
    }

    /**
     * @param	RlsBalanceOfGoods $rlsBalanceOfGoods The rlsBalanceOfGoods object to remove.
     * @return rlsNomen The current object (for fluent API support)
     */
    public function removeRlsBalanceOfGoods($rlsBalanceOfGoods)
    {
        if ($this->getRlsBalanceOfGoodss()->contains($rlsBalanceOfGoods)) {
            $this->collRlsBalanceOfGoodss->remove($this->collRlsBalanceOfGoodss->search($rlsBalanceOfGoods));
            if (null === $this->rlsBalanceOfGoodssScheduledForDeletion) {
                $this->rlsBalanceOfGoodssScheduledForDeletion = clone $this->collRlsBalanceOfGoodss;
                $this->rlsBalanceOfGoodssScheduledForDeletion->clear();
            }
            $this->rlsBalanceOfGoodssScheduledForDeletion[]= clone $rlsBalanceOfGoods;
            $rlsBalanceOfGoods->setrlsNomen(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this rlsNomen is new, it will return
     * an empty collection; or if this rlsNomen has previously
     * been saved, it will retrieve related RlsBalanceOfGoodss from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in rlsNomen.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|RlsBalanceOfGoods[] List of RlsBalanceOfGoods objects
     */
    public function getRlsBalanceOfGoodssJoinRbStorage($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = RlsBalanceOfGoodsQuery::create(null, $criteria);
        $query->joinWith('RbStorage', $join_behavior);

        return $this->getRlsBalanceOfGoodss($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->actmatters_id = null;
        $this->tradename_id = null;
        $this->form_id = null;
        $this->packing_id = null;
        $this->filling_id = null;
        $this->unit_id = null;
        $this->dosagevalue = null;
        $this->dosageunit_id = null;
        $this->druglifetime = null;
        $this->regdate = null;
        $this->anndate = null;
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
            if ($this->collRlsBalanceOfGoodss) {
                foreach ($this->collRlsBalanceOfGoodss as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->arbUnitRelatedByunitId instanceof Persistent) {
              $this->arbUnitRelatedByunitId->clearAllReferences($deep);
            }
            if ($this->arbUnitRelatedBydosageUnitId instanceof Persistent) {
              $this->arbUnitRelatedBydosageUnitId->clearAllReferences($deep);
            }
            if ($this->arlsFilling instanceof Persistent) {
              $this->arlsFilling->clearAllReferences($deep);
            }
            if ($this->arlsForm instanceof Persistent) {
              $this->arlsForm->clearAllReferences($deep);
            }
            if ($this->arlsPacking instanceof Persistent) {
              $this->arlsPacking->clearAllReferences($deep);
            }
            if ($this->arlsActMatters instanceof Persistent) {
              $this->arlsActMatters->clearAllReferences($deep);
            }
            if ($this->arlsTradeName instanceof Persistent) {
              $this->arlsTradeName->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collDrugComponents instanceof PropelCollection) {
            $this->collDrugComponents->clearIterator();
        }
        $this->collDrugComponents = null;
        if ($this->collRlsBalanceOfGoodss instanceof PropelCollection) {
            $this->collRlsBalanceOfGoodss->clearIterator();
        }
        $this->collRlsBalanceOfGoodss = null;
        $this->arbUnitRelatedByunitId = null;
        $this->arbUnitRelatedBydosageUnitId = null;
        $this->arlsFilling = null;
        $this->arlsForm = null;
        $this->arlsPacking = null;
        $this->arlsActMatters = null;
        $this->arlsTradeName = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(rlsNomenPeer::DEFAULT_STRING_FORMAT);
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
