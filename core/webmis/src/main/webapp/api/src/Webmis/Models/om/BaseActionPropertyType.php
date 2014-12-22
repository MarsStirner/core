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
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyQuery;
use Webmis\Models\ActionPropertyType;
use Webmis\Models\ActionPropertyTypePeer;
use Webmis\Models\ActionPropertyTypeQuery;
use Webmis\Models\ActionType;
use Webmis\Models\ActionTypeQuery;

/**
 * Base class that represents a row from the 'ActionPropertyType' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseActionPropertyType extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\ActionPropertyTypePeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        ActionPropertyTypePeer
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
     * The value for the deleted field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $deleted;

    /**
     * The value for the actiontype_id field.
     * @var        int
     */
    protected $actiontype_id;

    /**
     * The value for the idx field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $idx;

    /**
     * The value for the template_id field.
     * @var        int
     */
    protected $template_id;

    /**
     * The value for the name field.
     * @var        string
     */
    protected $name;

    /**
     * The value for the descr field.
     * @var        string
     */
    protected $descr;

    /**
     * The value for the unit_id field.
     * @var        int
     */
    protected $unit_id;

    /**
     * The value for the typename field.
     * @var        string
     */
    protected $typename;

    /**
     * The value for the valuedomain field.
     * @var        string
     */
    protected $valuedomain;

    /**
     * The value for the defaultvalue field.
     * @var        string
     */
    protected $defaultvalue;

    /**
     * The value for the code field.
     * @var        string
     */
    protected $code;

    /**
     * The value for the isvector field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isvector;

    /**
     * The value for the norm field.
     * @var        string
     */
    protected $norm;

    /**
     * The value for the sex field.
     * @var        int
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
     * The value for the penalty field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $penalty;

    /**
     * The value for the visibleinjobticket field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $visibleinjobticket;

    /**
     * The value for the isassignable field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $isassignable;

    /**
     * The value for the test_id field.
     * @var        int
     */
    protected $test_id;

    /**
     * The value for the defaultevaluation field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $defaultevaluation;

    /**
     * The value for the toepicrisis field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $toepicrisis;

    /**
     * The value for the mandatory field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $mandatory;

    /**
     * The value for the readonly field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $readonly;

    /**
     * @var        ActionType
     */
    protected $aActionTypeRelatedByactionTypeId;

    /**
     * @var        PropelObjectCollection|ActionProperty[] Collection to store aggregation of ActionProperty objects.
     */
    protected $collActionPropertys;
    protected $collActionPropertysPartial;

    /**
     * @var        ActionType one-to-one related ActionType object
     */
    protected $singleActionTypeRelatedByid;

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
    protected $actionPropertysScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $actionTypesRelatedByidScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->idx = 0;
        $this->isvector = false;
        $this->penalty = 0;
        $this->visibleinjobticket = false;
        $this->isassignable = false;
        $this->defaultevaluation = false;
        $this->toepicrisis = false;
        $this->mandatory = false;
        $this->readonly = false;
    }

    /**
     * Initializes internal state of BaseActionPropertyType object.
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
     * Get the [deleted] column value.
     *
     * @return boolean
     */
    public function getdeleted()
    {
        return $this->deleted;
    }

    /**
     * Get the [actiontype_id] column value.
     *
     * @return int
     */
    public function getactionTypeId()
    {
        return $this->actiontype_id;
    }

    /**
     * Get the [idx] column value.
     *
     * @return int
     */
    public function getidx()
    {
        return $this->idx;
    }

    /**
     * Get the [template_id] column value.
     *
     * @return int
     */
    public function gettemplateId()
    {
        return $this->template_id;
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
     * Get the [descr] column value.
     *
     * @return string
     */
    public function getdescr()
    {
        return $this->descr;
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
     * Get the [typename] column value.
     *
     * @return string
     */
    public function gettypeName()
    {
        return $this->typename;
    }

    /**
     * Get the [valuedomain] column value.
     *
     * @return string
     */
    public function getValueDomain()
    {
        return $this->valuedomain;
    }

    /**
     * Get the [defaultvalue] column value.
     *
     * @return string
     */
    public function getdefaultValue()
    {
        return $this->defaultvalue;
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
     * Get the [isvector] column value.
     *
     * @return boolean
     */
    public function getisVector()
    {
        return $this->isvector;
    }

    /**
     * Get the [norm] column value.
     *
     * @return string
     */
    public function getnorm()
    {
        return $this->norm;
    }

    /**
     * Get the [sex] column value.
     *
     * @return int
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
     * Get the [penalty] column value.
     *
     * @return int
     */
    public function getpenalty()
    {
        return $this->penalty;
    }

    /**
     * Get the [visibleinjobticket] column value.
     *
     * @return boolean
     */
    public function getvisibleInJobTicket()
    {
        return $this->visibleinjobticket;
    }

    /**
     * Get the [isassignable] column value.
     *
     * @return boolean
     */
    public function getisAssignable()
    {
        return $this->isassignable;
    }

    /**
     * Get the [test_id] column value.
     *
     * @return int
     */
    public function gettestId()
    {
        return $this->test_id;
    }

    /**
     * Get the [defaultevaluation] column value.
     *
     * @return boolean
     */
    public function getdefaultEvaluation()
    {
        return $this->defaultevaluation;
    }

    /**
     * Get the [toepicrisis] column value.
     *
     * @return boolean
     */
    public function gettoEpicrisis()
    {
        return $this->toepicrisis;
    }

    /**
     * Get the [mandatory] column value.
     *
     * @return boolean
     */
    public function getmandatory()
    {
        return $this->mandatory;
    }

    /**
     * Get the [readonly] column value.
     *
     * @return boolean
     */
    public function getreadonly()
    {
        return $this->readonly;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of the [deleted] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
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
            $this->modifiedColumns[] = ActionPropertyTypePeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [actiontype_id] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setactionTypeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->actiontype_id !== $v) {
            $this->actiontype_id = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::ACTIONTYPE_ID;
        }

        if ($this->aActionTypeRelatedByactionTypeId !== null && $this->aActionTypeRelatedByactionTypeId->getid() !== $v) {
            $this->aActionTypeRelatedByactionTypeId = null;
        }


        return $this;
    } // setactionTypeId()

    /**
     * Set the value of [idx] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setidx($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->idx !== $v) {
            $this->idx = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::IDX;
        }


        return $this;
    } // setidx()

    /**
     * Set the value of [template_id] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function settemplateId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->template_id !== $v) {
            $this->template_id = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::TEMPLATE_ID;
        }


        return $this;
    } // settemplateId()

    /**
     * Set the value of [name] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setname($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->name !== $v) {
            $this->name = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::NAME;
        }


        return $this;
    } // setname()

    /**
     * Set the value of [descr] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setdescr($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->descr !== $v) {
            $this->descr = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::DESCR;
        }


        return $this;
    } // setdescr()

    /**
     * Set the value of [unit_id] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setunitId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->unit_id !== $v) {
            $this->unit_id = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::UNIT_ID;
        }


        return $this;
    } // setunitId()

    /**
     * Set the value of [typename] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function settypeName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->typename !== $v) {
            $this->typename = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::TYPENAME;
        }


        return $this;
    } // settypeName()

    /**
     * Set the value of [valuedomain] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setValueDomain($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->valuedomain !== $v) {
            $this->valuedomain = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::VALUEDOMAIN;
        }


        return $this;
    } // setValueDomain()

    /**
     * Set the value of [defaultvalue] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setdefaultValue($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->defaultvalue !== $v) {
            $this->defaultvalue = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::DEFAULTVALUE;
        }


        return $this;
    } // setdefaultValue()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Sets the value of the [isvector] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setisVector($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isvector !== $v) {
            $this->isvector = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::ISVECTOR;
        }


        return $this;
    } // setisVector()

    /**
     * Set the value of [norm] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setnorm($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->norm !== $v) {
            $this->norm = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::NORM;
        }


        return $this;
    } // setnorm()

    /**
     * Set the value of [sex] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setsex($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->sex !== $v) {
            $this->sex = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::SEX;
        }


        return $this;
    } // setsex()

    /**
     * Set the value of [age] column.
     *
     * @param string $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setage($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->age !== $v) {
            $this->age = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::AGE;
        }


        return $this;
    } // setage()

    /**
     * Set the value of [age_bu] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setageBu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bu !== $v) {
            $this->age_bu = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::AGE_BU;
        }


        return $this;
    } // setageBu()

    /**
     * Set the value of [age_bc] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setageBc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_bc !== $v) {
            $this->age_bc = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::AGE_BC;
        }


        return $this;
    } // setageBc()

    /**
     * Set the value of [age_eu] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setageEu($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_eu !== $v) {
            $this->age_eu = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::AGE_EU;
        }


        return $this;
    } // setageEu()

    /**
     * Set the value of [age_ec] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setageEc($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->age_ec !== $v) {
            $this->age_ec = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::AGE_EC;
        }


        return $this;
    } // setageEc()

    /**
     * Set the value of [penalty] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setpenalty($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->penalty !== $v) {
            $this->penalty = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::PENALTY;
        }


        return $this;
    } // setpenalty()

    /**
     * Sets the value of the [visibleinjobticket] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setvisibleInJobTicket($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->visibleinjobticket !== $v) {
            $this->visibleinjobticket = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::VISIBLEINJOBTICKET;
        }


        return $this;
    } // setvisibleInJobTicket()

    /**
     * Sets the value of the [isassignable] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setisAssignable($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->isassignable !== $v) {
            $this->isassignable = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::ISASSIGNABLE;
        }


        return $this;
    } // setisAssignable()

    /**
     * Set the value of [test_id] column.
     *
     * @param int $v new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function settestId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->test_id !== $v) {
            $this->test_id = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::TEST_ID;
        }


        return $this;
    } // settestId()

    /**
     * Sets the value of the [defaultevaluation] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setdefaultEvaluation($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->defaultevaluation !== $v) {
            $this->defaultevaluation = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::DEFAULTEVALUATION;
        }


        return $this;
    } // setdefaultEvaluation()

    /**
     * Sets the value of the [toepicrisis] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function settoEpicrisis($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->toepicrisis !== $v) {
            $this->toepicrisis = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::TOEPICRISIS;
        }


        return $this;
    } // settoEpicrisis()

    /**
     * Sets the value of the [mandatory] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setmandatory($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->mandatory !== $v) {
            $this->mandatory = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::MANDATORY;
        }


        return $this;
    } // setmandatory()

    /**
     * Sets the value of the [readonly] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setreadonly($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->readonly !== $v) {
            $this->readonly = $v;
            $this->modifiedColumns[] = ActionPropertyTypePeer::READONLY;
        }


        return $this;
    } // setreadonly()

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

            if ($this->idx !== 0) {
                return false;
            }

            if ($this->isvector !== false) {
                return false;
            }

            if ($this->penalty !== 0) {
                return false;
            }

            if ($this->visibleinjobticket !== false) {
                return false;
            }

            if ($this->isassignable !== false) {
                return false;
            }

            if ($this->defaultevaluation !== false) {
                return false;
            }

            if ($this->toepicrisis !== false) {
                return false;
            }

            if ($this->mandatory !== false) {
                return false;
            }

            if ($this->readonly !== false) {
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
            $this->deleted = ($row[$startcol + 1] !== null) ? (boolean) $row[$startcol + 1] : null;
            $this->actiontype_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->idx = ($row[$startcol + 3] !== null) ? (int) $row[$startcol + 3] : null;
            $this->template_id = ($row[$startcol + 4] !== null) ? (int) $row[$startcol + 4] : null;
            $this->name = ($row[$startcol + 5] !== null) ? (string) $row[$startcol + 5] : null;
            $this->descr = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->unit_id = ($row[$startcol + 7] !== null) ? (int) $row[$startcol + 7] : null;
            $this->typename = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->valuedomain = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->defaultvalue = ($row[$startcol + 10] !== null) ? (string) $row[$startcol + 10] : null;
            $this->code = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->isvector = ($row[$startcol + 12] !== null) ? (boolean) $row[$startcol + 12] : null;
            $this->norm = ($row[$startcol + 13] !== null) ? (string) $row[$startcol + 13] : null;
            $this->sex = ($row[$startcol + 14] !== null) ? (int) $row[$startcol + 14] : null;
            $this->age = ($row[$startcol + 15] !== null) ? (string) $row[$startcol + 15] : null;
            $this->age_bu = ($row[$startcol + 16] !== null) ? (int) $row[$startcol + 16] : null;
            $this->age_bc = ($row[$startcol + 17] !== null) ? (int) $row[$startcol + 17] : null;
            $this->age_eu = ($row[$startcol + 18] !== null) ? (int) $row[$startcol + 18] : null;
            $this->age_ec = ($row[$startcol + 19] !== null) ? (int) $row[$startcol + 19] : null;
            $this->penalty = ($row[$startcol + 20] !== null) ? (int) $row[$startcol + 20] : null;
            $this->visibleinjobticket = ($row[$startcol + 21] !== null) ? (boolean) $row[$startcol + 21] : null;
            $this->isassignable = ($row[$startcol + 22] !== null) ? (boolean) $row[$startcol + 22] : null;
            $this->test_id = ($row[$startcol + 23] !== null) ? (int) $row[$startcol + 23] : null;
            $this->defaultevaluation = ($row[$startcol + 24] !== null) ? (boolean) $row[$startcol + 24] : null;
            $this->toepicrisis = ($row[$startcol + 25] !== null) ? (boolean) $row[$startcol + 25] : null;
            $this->mandatory = ($row[$startcol + 26] !== null) ? (boolean) $row[$startcol + 26] : null;
            $this->readonly = ($row[$startcol + 27] !== null) ? (boolean) $row[$startcol + 27] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 28; // 28 = ActionPropertyTypePeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating ActionPropertyType object", $e);
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

        if ($this->aActionTypeRelatedByactionTypeId !== null && $this->actiontype_id !== $this->aActionTypeRelatedByactionTypeId->getid()) {
            $this->aActionTypeRelatedByactionTypeId = null;
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
            $con = Propel::getConnection(ActionPropertyTypePeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = ActionPropertyTypePeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->aActionTypeRelatedByactionTypeId = null;
            $this->collActionPropertys = null;

            $this->singleActionTypeRelatedByid = null;

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
            $con = Propel::getConnection(ActionPropertyTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = ActionPropertyTypeQuery::create()
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
            $con = Propel::getConnection(ActionPropertyTypePeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
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
                ActionPropertyTypePeer::addInstanceToPool($this);
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

            if ($this->aActionTypeRelatedByactionTypeId !== null) {
                if ($this->aActionTypeRelatedByactionTypeId->isModified() || $this->aActionTypeRelatedByactionTypeId->isNew()) {
                    $affectedRows += $this->aActionTypeRelatedByactionTypeId->save($con);
                }
                $this->setActionTypeRelatedByactionTypeId($this->aActionTypeRelatedByactionTypeId);
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

            if ($this->actionPropertysScheduledForDeletion !== null) {
                if (!$this->actionPropertysScheduledForDeletion->isEmpty()) {
                    ActionPropertyQuery::create()
                        ->filterByPrimaryKeys($this->actionPropertysScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionPropertysScheduledForDeletion = null;
                }
            }

            if ($this->collActionPropertys !== null) {
                foreach ($this->collActionPropertys as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->actionTypesRelatedByidScheduledForDeletion !== null) {
                if (!$this->actionTypesRelatedByidScheduledForDeletion->isEmpty()) {
                    ActionTypeQuery::create()
                        ->filterByPrimaryKeys($this->actionTypesRelatedByidScheduledForDeletion->getPrimaryKeys(false))
                        ->delete($con);
                    $this->actionTypesRelatedByidScheduledForDeletion = null;
                }
            }

            if ($this->singleActionTypeRelatedByid !== null) {
                if (!$this->singleActionTypeRelatedByid->isDeleted() && ($this->singleActionTypeRelatedByid->isNew() || $this->singleActionTypeRelatedByid->isModified())) {
                        $affectedRows += $this->singleActionTypeRelatedByid->save($con);
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

        $this->modifiedColumns[] = ActionPropertyTypePeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . ActionPropertyTypePeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(ActionPropertyTypePeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::ACTIONTYPE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`actionType_id`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::IDX)) {
            $modifiedColumns[':p' . $index++]  = '`idx`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::TEMPLATE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`template_id`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::NAME)) {
            $modifiedColumns[':p' . $index++]  = '`name`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::DESCR)) {
            $modifiedColumns[':p' . $index++]  = '`descr`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::UNIT_ID)) {
            $modifiedColumns[':p' . $index++]  = '`unit_id`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::TYPENAME)) {
            $modifiedColumns[':p' . $index++]  = '`typeName`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::VALUEDOMAIN)) {
            $modifiedColumns[':p' . $index++]  = '`valueDomain`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::DEFAULTVALUE)) {
            $modifiedColumns[':p' . $index++]  = '`defaultValue`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::ISVECTOR)) {
            $modifiedColumns[':p' . $index++]  = '`isVector`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::NORM)) {
            $modifiedColumns[':p' . $index++]  = '`norm`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::SEX)) {
            $modifiedColumns[':p' . $index++]  = '`sex`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE)) {
            $modifiedColumns[':p' . $index++]  = '`age`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_BU)) {
            $modifiedColumns[':p' . $index++]  = '`age_bu`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_BC)) {
            $modifiedColumns[':p' . $index++]  = '`age_bc`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_EU)) {
            $modifiedColumns[':p' . $index++]  = '`age_eu`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_EC)) {
            $modifiedColumns[':p' . $index++]  = '`age_ec`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::PENALTY)) {
            $modifiedColumns[':p' . $index++]  = '`penalty`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::VISIBLEINJOBTICKET)) {
            $modifiedColumns[':p' . $index++]  = '`visibleInJobTicket`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::ISASSIGNABLE)) {
            $modifiedColumns[':p' . $index++]  = '`isAssignable`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::TEST_ID)) {
            $modifiedColumns[':p' . $index++]  = '`test_id`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::DEFAULTEVALUATION)) {
            $modifiedColumns[':p' . $index++]  = '`defaultEvaluation`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::TOEPICRISIS)) {
            $modifiedColumns[':p' . $index++]  = '`toEpicrisis`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::MANDATORY)) {
            $modifiedColumns[':p' . $index++]  = '`mandatory`';
        }
        if ($this->isColumnModified(ActionPropertyTypePeer::READONLY)) {
            $modifiedColumns[':p' . $index++]  = '`readOnly`';
        }

        $sql = sprintf(
            'INSERT INTO `ActionPropertyType` (%s) VALUES (%s)',
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
                    case '`deleted`':
                        $stmt->bindValue($identifier, (int) $this->deleted, PDO::PARAM_INT);
                        break;
                    case '`actionType_id`':
                        $stmt->bindValue($identifier, $this->actiontype_id, PDO::PARAM_INT);
                        break;
                    case '`idx`':
                        $stmt->bindValue($identifier, $this->idx, PDO::PARAM_INT);
                        break;
                    case '`template_id`':
                        $stmt->bindValue($identifier, $this->template_id, PDO::PARAM_INT);
                        break;
                    case '`name`':
                        $stmt->bindValue($identifier, $this->name, PDO::PARAM_STR);
                        break;
                    case '`descr`':
                        $stmt->bindValue($identifier, $this->descr, PDO::PARAM_STR);
                        break;
                    case '`unit_id`':
                        $stmt->bindValue($identifier, $this->unit_id, PDO::PARAM_INT);
                        break;
                    case '`typeName`':
                        $stmt->bindValue($identifier, $this->typename, PDO::PARAM_STR);
                        break;
                    case '`valueDomain`':
                        $stmt->bindValue($identifier, $this->valuedomain, PDO::PARAM_STR);
                        break;
                    case '`defaultValue`':
                        $stmt->bindValue($identifier, $this->defaultvalue, PDO::PARAM_STR);
                        break;
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`isVector`':
                        $stmt->bindValue($identifier, (int) $this->isvector, PDO::PARAM_INT);
                        break;
                    case '`norm`':
                        $stmt->bindValue($identifier, $this->norm, PDO::PARAM_STR);
                        break;
                    case '`sex`':
                        $stmt->bindValue($identifier, $this->sex, PDO::PARAM_INT);
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
                    case '`penalty`':
                        $stmt->bindValue($identifier, $this->penalty, PDO::PARAM_INT);
                        break;
                    case '`visibleInJobTicket`':
                        $stmt->bindValue($identifier, (int) $this->visibleinjobticket, PDO::PARAM_INT);
                        break;
                    case '`isAssignable`':
                        $stmt->bindValue($identifier, (int) $this->isassignable, PDO::PARAM_INT);
                        break;
                    case '`test_id`':
                        $stmt->bindValue($identifier, $this->test_id, PDO::PARAM_INT);
                        break;
                    case '`defaultEvaluation`':
                        $stmt->bindValue($identifier, (int) $this->defaultevaluation, PDO::PARAM_INT);
                        break;
                    case '`toEpicrisis`':
                        $stmt->bindValue($identifier, (int) $this->toepicrisis, PDO::PARAM_INT);
                        break;
                    case '`mandatory`':
                        $stmt->bindValue($identifier, (int) $this->mandatory, PDO::PARAM_INT);
                        break;
                    case '`readOnly`':
                        $stmt->bindValue($identifier, (int) $this->readonly, PDO::PARAM_INT);
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

            if ($this->aActionTypeRelatedByactionTypeId !== null) {
                if (!$this->aActionTypeRelatedByactionTypeId->validate($columns)) {
                    $failureMap = array_merge($failureMap, $this->aActionTypeRelatedByactionTypeId->getValidationFailures());
                }
            }


            if (($retval = ActionPropertyTypePeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionPropertys !== null) {
                    foreach ($this->collActionPropertys as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->singleActionTypeRelatedByid !== null) {
                    if (!$this->singleActionTypeRelatedByid->validate($columns)) {
                        $failureMap = array_merge($failureMap, $this->singleActionTypeRelatedByid->getValidationFailures());
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
        $pos = ActionPropertyTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getdeleted();
                break;
            case 2:
                return $this->getactionTypeId();
                break;
            case 3:
                return $this->getidx();
                break;
            case 4:
                return $this->gettemplateId();
                break;
            case 5:
                return $this->getname();
                break;
            case 6:
                return $this->getdescr();
                break;
            case 7:
                return $this->getunitId();
                break;
            case 8:
                return $this->gettypeName();
                break;
            case 9:
                return $this->getValueDomain();
                break;
            case 10:
                return $this->getdefaultValue();
                break;
            case 11:
                return $this->getcode();
                break;
            case 12:
                return $this->getisVector();
                break;
            case 13:
                return $this->getnorm();
                break;
            case 14:
                return $this->getsex();
                break;
            case 15:
                return $this->getage();
                break;
            case 16:
                return $this->getageBu();
                break;
            case 17:
                return $this->getageBc();
                break;
            case 18:
                return $this->getageEu();
                break;
            case 19:
                return $this->getageEc();
                break;
            case 20:
                return $this->getpenalty();
                break;
            case 21:
                return $this->getvisibleInJobTicket();
                break;
            case 22:
                return $this->getisAssignable();
                break;
            case 23:
                return $this->gettestId();
                break;
            case 24:
                return $this->getdefaultEvaluation();
                break;
            case 25:
                return $this->gettoEpicrisis();
                break;
            case 26:
                return $this->getmandatory();
                break;
            case 27:
                return $this->getreadonly();
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
        if (isset($alreadyDumpedObjects['ActionPropertyType'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['ActionPropertyType'][$this->getPrimaryKey()] = true;
        $keys = ActionPropertyTypePeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getdeleted(),
            $keys[2] => $this->getactionTypeId(),
            $keys[3] => $this->getidx(),
            $keys[4] => $this->gettemplateId(),
            $keys[5] => $this->getname(),
            $keys[6] => $this->getdescr(),
            $keys[7] => $this->getunitId(),
            $keys[8] => $this->gettypeName(),
            $keys[9] => $this->getValueDomain(),
            $keys[10] => $this->getdefaultValue(),
            $keys[11] => $this->getcode(),
            $keys[12] => $this->getisVector(),
            $keys[13] => $this->getnorm(),
            $keys[14] => $this->getsex(),
            $keys[15] => $this->getage(),
            $keys[16] => $this->getageBu(),
            $keys[17] => $this->getageBc(),
            $keys[18] => $this->getageEu(),
            $keys[19] => $this->getageEc(),
            $keys[20] => $this->getpenalty(),
            $keys[21] => $this->getvisibleInJobTicket(),
            $keys[22] => $this->getisAssignable(),
            $keys[23] => $this->gettestId(),
            $keys[24] => $this->getdefaultEvaluation(),
            $keys[25] => $this->gettoEpicrisis(),
            $keys[26] => $this->getmandatory(),
            $keys[27] => $this->getreadonly(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->aActionTypeRelatedByactionTypeId) {
                $result['ActionTypeRelatedByactionTypeId'] = $this->aActionTypeRelatedByactionTypeId->toArray($keyType, $includeLazyLoadColumns,  $alreadyDumpedObjects, true);
            }
            if (null !== $this->collActionPropertys) {
                $result['ActionPropertys'] = $this->collActionPropertys->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->singleActionTypeRelatedByid) {
                $result['ActionTypeRelatedByid'] = $this->singleActionTypeRelatedByid->toArray($keyType, $includeLazyLoadColumns, $alreadyDumpedObjects, true);
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
        $pos = ActionPropertyTypePeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setdeleted($value);
                break;
            case 2:
                $this->setactionTypeId($value);
                break;
            case 3:
                $this->setidx($value);
                break;
            case 4:
                $this->settemplateId($value);
                break;
            case 5:
                $this->setname($value);
                break;
            case 6:
                $this->setdescr($value);
                break;
            case 7:
                $this->setunitId($value);
                break;
            case 8:
                $this->settypeName($value);
                break;
            case 9:
                $this->setValueDomain($value);
                break;
            case 10:
                $this->setdefaultValue($value);
                break;
            case 11:
                $this->setcode($value);
                break;
            case 12:
                $this->setisVector($value);
                break;
            case 13:
                $this->setnorm($value);
                break;
            case 14:
                $this->setsex($value);
                break;
            case 15:
                $this->setage($value);
                break;
            case 16:
                $this->setageBu($value);
                break;
            case 17:
                $this->setageBc($value);
                break;
            case 18:
                $this->setageEu($value);
                break;
            case 19:
                $this->setageEc($value);
                break;
            case 20:
                $this->setpenalty($value);
                break;
            case 21:
                $this->setvisibleInJobTicket($value);
                break;
            case 22:
                $this->setisAssignable($value);
                break;
            case 23:
                $this->settestId($value);
                break;
            case 24:
                $this->setdefaultEvaluation($value);
                break;
            case 25:
                $this->settoEpicrisis($value);
                break;
            case 26:
                $this->setmandatory($value);
                break;
            case 27:
                $this->setreadonly($value);
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
        $keys = ActionPropertyTypePeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setdeleted($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setactionTypeId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setidx($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->settemplateId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setname($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setdescr($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setunitId($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->settypeName($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setValueDomain($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setdefaultValue($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setcode($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setisVector($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setnorm($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setsex($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setage($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setageBu($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setageBc($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->setageEu($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setageEc($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setpenalty($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setvisibleInJobTicket($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setisAssignable($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->settestId($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->setdefaultEvaluation($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->settoEpicrisis($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->setmandatory($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setreadonly($arr[$keys[27]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(ActionPropertyTypePeer::DATABASE_NAME);

        if ($this->isColumnModified(ActionPropertyTypePeer::ID)) $criteria->add(ActionPropertyTypePeer::ID, $this->id);
        if ($this->isColumnModified(ActionPropertyTypePeer::DELETED)) $criteria->add(ActionPropertyTypePeer::DELETED, $this->deleted);
        if ($this->isColumnModified(ActionPropertyTypePeer::ACTIONTYPE_ID)) $criteria->add(ActionPropertyTypePeer::ACTIONTYPE_ID, $this->actiontype_id);
        if ($this->isColumnModified(ActionPropertyTypePeer::IDX)) $criteria->add(ActionPropertyTypePeer::IDX, $this->idx);
        if ($this->isColumnModified(ActionPropertyTypePeer::TEMPLATE_ID)) $criteria->add(ActionPropertyTypePeer::TEMPLATE_ID, $this->template_id);
        if ($this->isColumnModified(ActionPropertyTypePeer::NAME)) $criteria->add(ActionPropertyTypePeer::NAME, $this->name);
        if ($this->isColumnModified(ActionPropertyTypePeer::DESCR)) $criteria->add(ActionPropertyTypePeer::DESCR, $this->descr);
        if ($this->isColumnModified(ActionPropertyTypePeer::UNIT_ID)) $criteria->add(ActionPropertyTypePeer::UNIT_ID, $this->unit_id);
        if ($this->isColumnModified(ActionPropertyTypePeer::TYPENAME)) $criteria->add(ActionPropertyTypePeer::TYPENAME, $this->typename);
        if ($this->isColumnModified(ActionPropertyTypePeer::VALUEDOMAIN)) $criteria->add(ActionPropertyTypePeer::VALUEDOMAIN, $this->valuedomain);
        if ($this->isColumnModified(ActionPropertyTypePeer::DEFAULTVALUE)) $criteria->add(ActionPropertyTypePeer::DEFAULTVALUE, $this->defaultvalue);
        if ($this->isColumnModified(ActionPropertyTypePeer::CODE)) $criteria->add(ActionPropertyTypePeer::CODE, $this->code);
        if ($this->isColumnModified(ActionPropertyTypePeer::ISVECTOR)) $criteria->add(ActionPropertyTypePeer::ISVECTOR, $this->isvector);
        if ($this->isColumnModified(ActionPropertyTypePeer::NORM)) $criteria->add(ActionPropertyTypePeer::NORM, $this->norm);
        if ($this->isColumnModified(ActionPropertyTypePeer::SEX)) $criteria->add(ActionPropertyTypePeer::SEX, $this->sex);
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE)) $criteria->add(ActionPropertyTypePeer::AGE, $this->age);
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_BU)) $criteria->add(ActionPropertyTypePeer::AGE_BU, $this->age_bu);
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_BC)) $criteria->add(ActionPropertyTypePeer::AGE_BC, $this->age_bc);
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_EU)) $criteria->add(ActionPropertyTypePeer::AGE_EU, $this->age_eu);
        if ($this->isColumnModified(ActionPropertyTypePeer::AGE_EC)) $criteria->add(ActionPropertyTypePeer::AGE_EC, $this->age_ec);
        if ($this->isColumnModified(ActionPropertyTypePeer::PENALTY)) $criteria->add(ActionPropertyTypePeer::PENALTY, $this->penalty);
        if ($this->isColumnModified(ActionPropertyTypePeer::VISIBLEINJOBTICKET)) $criteria->add(ActionPropertyTypePeer::VISIBLEINJOBTICKET, $this->visibleinjobticket);
        if ($this->isColumnModified(ActionPropertyTypePeer::ISASSIGNABLE)) $criteria->add(ActionPropertyTypePeer::ISASSIGNABLE, $this->isassignable);
        if ($this->isColumnModified(ActionPropertyTypePeer::TEST_ID)) $criteria->add(ActionPropertyTypePeer::TEST_ID, $this->test_id);
        if ($this->isColumnModified(ActionPropertyTypePeer::DEFAULTEVALUATION)) $criteria->add(ActionPropertyTypePeer::DEFAULTEVALUATION, $this->defaultevaluation);
        if ($this->isColumnModified(ActionPropertyTypePeer::TOEPICRISIS)) $criteria->add(ActionPropertyTypePeer::TOEPICRISIS, $this->toepicrisis);
        if ($this->isColumnModified(ActionPropertyTypePeer::MANDATORY)) $criteria->add(ActionPropertyTypePeer::MANDATORY, $this->mandatory);
        if ($this->isColumnModified(ActionPropertyTypePeer::READONLY)) $criteria->add(ActionPropertyTypePeer::READONLY, $this->readonly);

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
        $criteria = new Criteria(ActionPropertyTypePeer::DATABASE_NAME);
        $criteria->add(ActionPropertyTypePeer::ID, $this->id);

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
     * @param object $copyObj An object of ActionPropertyType (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setdeleted($this->getdeleted());
        $copyObj->setactionTypeId($this->getactionTypeId());
        $copyObj->setidx($this->getidx());
        $copyObj->settemplateId($this->gettemplateId());
        $copyObj->setname($this->getname());
        $copyObj->setdescr($this->getdescr());
        $copyObj->setunitId($this->getunitId());
        $copyObj->settypeName($this->gettypeName());
        $copyObj->setValueDomain($this->getValueDomain());
        $copyObj->setdefaultValue($this->getdefaultValue());
        $copyObj->setcode($this->getcode());
        $copyObj->setisVector($this->getisVector());
        $copyObj->setnorm($this->getnorm());
        $copyObj->setsex($this->getsex());
        $copyObj->setage($this->getage());
        $copyObj->setageBu($this->getageBu());
        $copyObj->setageBc($this->getageBc());
        $copyObj->setageEu($this->getageEu());
        $copyObj->setageEc($this->getageEc());
        $copyObj->setpenalty($this->getpenalty());
        $copyObj->setvisibleInJobTicket($this->getvisibleInJobTicket());
        $copyObj->setisAssignable($this->getisAssignable());
        $copyObj->settestId($this->gettestId());
        $copyObj->setdefaultEvaluation($this->getdefaultEvaluation());
        $copyObj->settoEpicrisis($this->gettoEpicrisis());
        $copyObj->setmandatory($this->getmandatory());
        $copyObj->setreadonly($this->getreadonly());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActionPropertys() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionProperty($relObj->copy($deepCopy));
                }
            }

            $relObj = $this->getActionTypeRelatedByid();
            if ($relObj) {
                $copyObj->setActionTypeRelatedByid($relObj->copy($deepCopy));
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
     * @return ActionPropertyType Clone of current object.
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
     * @return ActionPropertyTypePeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new ActionPropertyTypePeer();
        }

        return self::$peer;
    }

    /**
     * Declares an association between this object and a ActionType object.
     *
     * @param             ActionType $v
     * @return ActionPropertyType The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionTypeRelatedByactionTypeId(ActionType $v = null)
    {
        if ($v === null) {
            $this->setactionTypeId(NULL);
        } else {
            $this->setactionTypeId($v->getid());
        }

        $this->aActionTypeRelatedByactionTypeId = $v;

        // Add binding for other direction of this n:n relationship.
        // If this object has already been added to the ActionType object, it will not be re-added.
        if ($v !== null) {
            $v->addActionPropertyTypeRelatedByactionTypeId($this);
        }


        return $this;
    }


    /**
     * Get the associated ActionType object
     *
     * @param PropelPDO $con Optional Connection object.
     * @param $doQuery Executes a query to get the object if required
     * @return ActionType The associated ActionType object.
     * @throws PropelException
     */
    public function getActionTypeRelatedByactionTypeId(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionTypeRelatedByactionTypeId === null && ($this->actiontype_id !== null) && $doQuery) {
            $this->aActionTypeRelatedByactionTypeId = ActionTypeQuery::create()->findPk($this->actiontype_id, $con);
            /* The following can be used additionally to
                guarantee the related object contains a reference
                to this object.  This level of coupling may, however, be
                undesirable since it could result in an only partially populated collection
                in the referenced object.
                $this->aActionTypeRelatedByactionTypeId->addActionPropertyTypesRelatedByactionTypeId($this);
             */
        }

        return $this->aActionTypeRelatedByactionTypeId;
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
        if ('ActionProperty' == $relationName) {
            $this->initActionPropertys();
        }
    }

    /**
     * Clears out the collActionPropertys collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return ActionPropertyType The current object (for fluent API support)
     * @see        addActionPropertys()
     */
    public function clearActionPropertys()
    {
        $this->collActionPropertys = null; // important to set this to null since that means it is uninitialized
        $this->collActionPropertysPartial = null;

        return $this;
    }

    /**
     * reset is the collActionPropertys collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionPropertys($v = true)
    {
        $this->collActionPropertysPartial = $v;
    }

    /**
     * Initializes the collActionPropertys collection.
     *
     * By default this just sets the collActionPropertys collection to an empty array (like clearcollActionPropertys());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionPropertys($overrideExisting = true)
    {
        if (null !== $this->collActionPropertys && !$overrideExisting) {
            return;
        }
        $this->collActionPropertys = new PropelObjectCollection();
        $this->collActionPropertys->setModel('ActionProperty');
    }

    /**
     * Gets an array of ActionProperty objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this ActionPropertyType is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     * @throws PropelException
     */
    public function getActionPropertys($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertysPartial && !$this->isNew();
        if (null === $this->collActionPropertys || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionPropertys) {
                // return empty collection
                $this->initActionPropertys();
            } else {
                $collActionPropertys = ActionPropertyQuery::create(null, $criteria)
                    ->filterByActionPropertyType($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionPropertysPartial && count($collActionPropertys)) {
                      $this->initActionPropertys(false);

                      foreach($collActionPropertys as $obj) {
                        if (false == $this->collActionPropertys->contains($obj)) {
                          $this->collActionPropertys->append($obj);
                        }
                      }

                      $this->collActionPropertysPartial = true;
                    }

                    $collActionPropertys->getInternalIterator()->rewind();
                    return $collActionPropertys;
                }

                if($partial && $this->collActionPropertys) {
                    foreach($this->collActionPropertys as $obj) {
                        if($obj->isNew()) {
                            $collActionPropertys[] = $obj;
                        }
                    }
                }

                $this->collActionPropertys = $collActionPropertys;
                $this->collActionPropertysPartial = false;
            }
        }

        return $this->collActionPropertys;
    }

    /**
     * Sets a collection of ActionProperty objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionPropertys A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function setActionPropertys(PropelCollection $actionPropertys, PropelPDO $con = null)
    {
        $actionPropertysToDelete = $this->getActionPropertys(new Criteria(), $con)->diff($actionPropertys);

        $this->actionPropertysScheduledForDeletion = unserialize(serialize($actionPropertysToDelete));

        foreach ($actionPropertysToDelete as $actionPropertyRemoved) {
            $actionPropertyRemoved->setActionPropertyType(null);
        }

        $this->collActionPropertys = null;
        foreach ($actionPropertys as $actionProperty) {
            $this->addActionProperty($actionProperty);
        }

        $this->collActionPropertys = $actionPropertys;
        $this->collActionPropertysPartial = false;

        return $this;
    }

    /**
     * Returns the number of related ActionProperty objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related ActionProperty objects.
     * @throws PropelException
     */
    public function countActionPropertys(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionPropertysPartial && !$this->isNew();
        if (null === $this->collActionPropertys || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionPropertys) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionPropertys());
            }
            $query = ActionPropertyQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByActionPropertyType($this)
                ->count($con);
        }

        return count($this->collActionPropertys);
    }

    /**
     * Method called to associate a ActionProperty object to this object
     * through the ActionProperty foreign key attribute.
     *
     * @param    ActionProperty $l ActionProperty
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function addActionProperty(ActionProperty $l)
    {
        if ($this->collActionPropertys === null) {
            $this->initActionPropertys();
            $this->collActionPropertysPartial = true;
        }
        if (!in_array($l, $this->collActionPropertys->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionProperty($l);
        }

        return $this;
    }

    /**
     * @param	ActionProperty $actionProperty The actionProperty object to add.
     */
    protected function doAddActionProperty($actionProperty)
    {
        $this->collActionPropertys[]= $actionProperty;
        $actionProperty->setActionPropertyType($this);
    }

    /**
     * @param	ActionProperty $actionProperty The actionProperty object to remove.
     * @return ActionPropertyType The current object (for fluent API support)
     */
    public function removeActionProperty($actionProperty)
    {
        if ($this->getActionPropertys()->contains($actionProperty)) {
            $this->collActionPropertys->remove($this->collActionPropertys->search($actionProperty));
            if (null === $this->actionPropertysScheduledForDeletion) {
                $this->actionPropertysScheduledForDeletion = clone $this->collActionPropertys;
                $this->actionPropertysScheduledForDeletion->clear();
            }
            $this->actionPropertysScheduledForDeletion[]= clone $actionProperty;
            $actionProperty->setActionPropertyType(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinAction($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('Action', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyString($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyString', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyInteger($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyInteger', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyDate($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyDate', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyDouble($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyDouble', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyOrgStructure', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this ActionPropertyType is new, it will return
     * an empty collection; or if this ActionPropertyType has previously
     * been saved, it will retrieve related ActionPropertys from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in ActionPropertyType.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|ActionProperty[] List of ActionProperty objects
     */
    public function getActionPropertysJoinActionPropertyFDRecord($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionPropertyQuery::create(null, $criteria);
        $query->joinWith('ActionPropertyFDRecord', $join_behavior);

        return $this->getActionPropertys($query, $con);
    }

    /**
     * Gets a single ActionType object, which is related to this object by a one-to-one relationship.
     *
     * @param PropelPDO $con optional connection object
     * @return ActionType
     * @throws PropelException
     */
    public function getActionTypeRelatedByid(PropelPDO $con = null)
    {

        if ($this->singleActionTypeRelatedByid === null && !$this->isNew()) {
            $this->singleActionTypeRelatedByid = ActionTypeQuery::create()->findPk($this->getPrimaryKey(), $con);
        }

        return $this->singleActionTypeRelatedByid;
    }

    /**
     * Sets a single ActionType object as related to this object by a one-to-one relationship.
     *
     * @param             ActionType $v ActionType
     * @return ActionPropertyType The current object (for fluent API support)
     * @throws PropelException
     */
    public function setActionTypeRelatedByid(ActionType $v = null)
    {
        $this->singleActionTypeRelatedByid = $v;

        // Make sure that that the passed-in ActionType isn't already associated with this object
        if ($v !== null && $v->getActionPropertyTypeRelatedByid(null, false) === null) {
            $v->setActionPropertyTypeRelatedByid($this);
        }

        return $this;
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->deleted = null;
        $this->actiontype_id = null;
        $this->idx = null;
        $this->template_id = null;
        $this->name = null;
        $this->descr = null;
        $this->unit_id = null;
        $this->typename = null;
        $this->valuedomain = null;
        $this->defaultvalue = null;
        $this->code = null;
        $this->isvector = null;
        $this->norm = null;
        $this->sex = null;
        $this->age = null;
        $this->age_bu = null;
        $this->age_bc = null;
        $this->age_eu = null;
        $this->age_ec = null;
        $this->penalty = null;
        $this->visibleinjobticket = null;
        $this->isassignable = null;
        $this->test_id = null;
        $this->defaultevaluation = null;
        $this->toepicrisis = null;
        $this->mandatory = null;
        $this->readonly = null;
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
            if ($this->collActionPropertys) {
                foreach ($this->collActionPropertys as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->singleActionTypeRelatedByid) {
                $this->singleActionTypeRelatedByid->clearAllReferences($deep);
            }
            if ($this->aActionTypeRelatedByactionTypeId instanceof Persistent) {
              $this->aActionTypeRelatedByactionTypeId->clearAllReferences($deep);
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionPropertys instanceof PropelCollection) {
            $this->collActionPropertys->clearIterator();
        }
        $this->collActionPropertys = null;
        if ($this->singleActionTypeRelatedByid instanceof PropelCollection) {
            $this->singleActionTypeRelatedByid->clearIterator();
        }
        $this->singleActionTypeRelatedByid = null;
        $this->aActionTypeRelatedByactionTypeId = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(ActionPropertyTypePeer::DEFAULT_STRING_FORMAT);
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
