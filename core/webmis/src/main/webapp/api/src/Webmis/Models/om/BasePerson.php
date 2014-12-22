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
use Webmis\Models\Event;
use Webmis\Models\EventQuery;
use Webmis\Models\Person;
use Webmis\Models\PersonPeer;
use Webmis\Models\PersonQuery;

/**
 * Base class that represents a row from the 'Person' table.
 *
 *
 *
 * @package    propel.generator.Models.om
 */
abstract class BasePerson extends BaseObject implements Persistent
{
    /**
     * Peer class name
     */
    const PEER = 'Webmis\\Models\\PersonPeer';

    /**
     * The Peer class.
     * Instance provides a convenient way of calling static methods on a class
     * that calling code may not be able to identify.
     * @var        PersonPeer
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
     * The value for the createdatetime field.
     * @var        string
     */
    protected $createdatetime;

    /**
     * The value for the createperson_id field.
     * @var        int
     */
    protected $createperson_id;

    /**
     * The value for the modifydatetime field.
     * @var        string
     */
    protected $modifydatetime;

    /**
     * The value for the modifyperson_id field.
     * @var        int
     */
    protected $modifyperson_id;

    /**
     * The value for the deleted field.
     * Note: this column has a database default value of: false
     * @var        boolean
     */
    protected $deleted;

    /**
     * The value for the code field.
     * @var        string
     */
    protected $code;

    /**
     * The value for the federalcode field.
     * @var        string
     */
    protected $federalcode;

    /**
     * The value for the regionalcode field.
     * @var        string
     */
    protected $regionalcode;

    /**
     * The value for the lastname field.
     * @var        string
     */
    protected $lastname;

    /**
     * The value for the firstname field.
     * @var        string
     */
    protected $firstname;

    /**
     * The value for the patrname field.
     * @var        string
     */
    protected $patrname;

    /**
     * The value for the post_id field.
     * @var        int
     */
    protected $post_id;

    /**
     * The value for the speciality_id field.
     * @var        int
     */
    protected $speciality_id;

    /**
     * The value for the org_id field.
     * @var        int
     */
    protected $org_id;

    /**
     * The value for the orgstructure_id field.
     * @var        int
     */
    protected $orgstructure_id;

    /**
     * The value for the office field.
     * @var        string
     */
    protected $office;

    /**
     * The value for the office2 field.
     * @var        string
     */
    protected $office2;

    /**
     * The value for the tariffcategory_id field.
     * @var        int
     */
    protected $tariffcategory_id;

    /**
     * The value for the finance_id field.
     * @var        int
     */
    protected $finance_id;

    /**
     * The value for the retiredate field.
     * @var        string
     */
    protected $retiredate;

    /**
     * The value for the ambplan field.
     * @var        int
     */
    protected $ambplan;

    /**
     * The value for the ambplan2 field.
     * @var        int
     */
    protected $ambplan2;

    /**
     * The value for the ambnorm field.
     * @var        int
     */
    protected $ambnorm;

    /**
     * The value for the homplan field.
     * @var        int
     */
    protected $homplan;

    /**
     * The value for the homplan2 field.
     * @var        int
     */
    protected $homplan2;

    /**
     * The value for the homnorm field.
     * @var        int
     */
    protected $homnorm;

    /**
     * The value for the expplan field.
     * @var        int
     */
    protected $expplan;

    /**
     * The value for the expnorm field.
     * @var        int
     */
    protected $expnorm;

    /**
     * The value for the login field.
     * @var        string
     */
    protected $login;

    /**
     * The value for the password field.
     * @var        string
     */
    protected $password;

    /**
     * The value for the userprofile_id field.
     * @var        int
     */
    protected $userprofile_id;

    /**
     * The value for the retired field.
     * @var        boolean
     */
    protected $retired;

    /**
     * The value for the birthdate field.
     * @var        string
     */
    protected $birthdate;

    /**
     * The value for the birthplace field.
     * @var        string
     */
    protected $birthplace;

    /**
     * The value for the sex field.
     * @var        int
     */
    protected $sex;

    /**
     * The value for the snils field.
     * @var        string
     */
    protected $snils;

    /**
     * The value for the inn field.
     * @var        string
     */
    protected $inn;

    /**
     * The value for the availableforexternal field.
     * Note: this column has a database default value of: 1
     * @var        int
     */
    protected $availableforexternal;

    /**
     * The value for the primaryquota field.
     * Note: this column has a database default value of: 50
     * @var        int
     */
    protected $primaryquota;

    /**
     * The value for the ownquota field.
     * Note: this column has a database default value of: 25
     * @var        int
     */
    protected $ownquota;

    /**
     * The value for the consultancyquota field.
     * Note: this column has a database default value of: 25
     * @var        int
     */
    protected $consultancyquota;

    /**
     * The value for the externalquota field.
     * Note: this column has a database default value of: 10
     * @var        int
     */
    protected $externalquota;

    /**
     * The value for the lastaccessibletimelinedate field.
     * @var        string
     */
    protected $lastaccessibletimelinedate;

    /**
     * The value for the timelineaccessibledays field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $timelineaccessibledays;

    /**
     * The value for the typetimelineperson field.
     * @var        int
     */
    protected $typetimelineperson;

    /**
     * The value for the maxoverqueue field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $maxoverqueue;

    /**
     * The value for the maxcito field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $maxcito;

    /**
     * The value for the quotunit field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $quotunit;

    /**
     * The value for the academicdegree_id field.
     * @var        int
     */
    protected $academicdegree_id;

    /**
     * The value for the academictitle_id field.
     * @var        int
     */
    protected $academictitle_id;

    /**
     * The value for the uuid_id field.
     * Note: this column has a database default value of: 0
     * @var        int
     */
    protected $uuid_id;

    /**
     * @var        PropelObjectCollection|Action[] Collection to store aggregation of Action objects.
     */
    protected $collActionsRelatedBycreatePersonId;
    protected $collActionsRelatedBycreatePersonIdPartial;

    /**
     * @var        PropelObjectCollection|Action[] Collection to store aggregation of Action objects.
     */
    protected $collActionsRelatedBymodifyPersonId;
    protected $collActionsRelatedBymodifyPersonIdPartial;

    /**
     * @var        PropelObjectCollection|Action[] Collection to store aggregation of Action objects.
     */
    protected $collActionsRelatedBysetPersonId;
    protected $collActionsRelatedBysetPersonIdPartial;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEventsRelatedBycreatePersonId;
    protected $collEventsRelatedBycreatePersonIdPartial;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEventsRelatedBymodifyPersonId;
    protected $collEventsRelatedBymodifyPersonIdPartial;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEventsRelatedBysetPersonId;
    protected $collEventsRelatedBysetPersonIdPartial;

    /**
     * @var        PropelObjectCollection|Event[] Collection to store aggregation of Event objects.
     */
    protected $collEventsRelatedByexecPersonId;
    protected $collEventsRelatedByexecPersonIdPartial;

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
    protected $actionsRelatedBycreatePersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $actionsRelatedBymodifyPersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $actionsRelatedBysetPersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $eventsRelatedBycreatePersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $eventsRelatedBymodifyPersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $eventsRelatedBysetPersonIdScheduledForDeletion = null;

    /**
     * An array of objects scheduled for deletion.
     * @var		PropelObjectCollection
     */
    protected $eventsRelatedByexecPersonIdScheduledForDeletion = null;

    /**
     * Applies default values to this object.
     * This method should be called from the object's constructor (or
     * equivalent initialization method).
     * @see        __construct()
     */
    public function applyDefaultValues()
    {
        $this->deleted = false;
        $this->availableforexternal = 1;
        $this->primaryquota = 50;
        $this->ownquota = 25;
        $this->consultancyquota = 25;
        $this->externalquota = 10;
        $this->timelineaccessibledays = 0;
        $this->maxoverqueue = 0;
        $this->maxcito = 0;
        $this->quotunit = 0;
        $this->uuid_id = 0;
    }

    /**
     * Initializes internal state of BasePerson object.
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
     * Get the [optionally formatted] temporal [createdatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getcreateDatetime($format = 'Y-m-d H:i:s')
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
     * Get the [createperson_id] column value.
     *
     * @return int
     */
    public function getcreatePersonId()
    {
        return $this->createperson_id;
    }

    /**
     * Get the [optionally formatted] temporal [modifydatetime] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00 00:00:00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getmodifyDatetime($format = 'Y-m-d H:i:s')
    {
        if ($this->modifydatetime === null) {
            return null;
        }

        if ($this->modifydatetime === '0000-00-00 00:00:00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->modifydatetime);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->modifydatetime, true), $x);
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
     * Get the [modifyperson_id] column value.
     *
     * @return int
     */
    public function getmodifyPersonId()
    {
        return $this->modifyperson_id;
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
     * Get the [code] column value.
     *
     * @return string
     */
    public function getcode()
    {
        return $this->code;
    }

    /**
     * Get the [federalcode] column value.
     *
     * @return string
     */
    public function getfederalCode()
    {
        return $this->federalcode;
    }

    /**
     * Get the [regionalcode] column value.
     *
     * @return string
     */
    public function getregionalCode()
    {
        return $this->regionalcode;
    }

    /**
     * Get the [lastname] column value.
     *
     * @return string
     */
    public function getlastName()
    {
        return $this->lastname;
    }

    /**
     * Get the [firstname] column value.
     *
     * @return string
     */
    public function getfirstName()
    {
        return $this->firstname;
    }

    /**
     * Get the [patrname] column value.
     *
     * @return string
     */
    public function getpatrName()
    {
        return $this->patrname;
    }

    /**
     * Get the [post_id] column value.
     *
     * @return int
     */
    public function getpostId()
    {
        return $this->post_id;
    }

    /**
     * Get the [speciality_id] column value.
     *
     * @return int
     */
    public function getspecialityId()
    {
        return $this->speciality_id;
    }

    /**
     * Get the [org_id] column value.
     *
     * @return int
     */
    public function getorgId()
    {
        return $this->org_id;
    }

    /**
     * Get the [orgstructure_id] column value.
     *
     * @return int
     */
    public function getorgStructureId()
    {
        return $this->orgstructure_id;
    }

    /**
     * Get the [office] column value.
     *
     * @return string
     */
    public function getoffice()
    {
        return $this->office;
    }

    /**
     * Get the [office2] column value.
     *
     * @return string
     */
    public function getoffice2()
    {
        return $this->office2;
    }

    /**
     * Get the [tariffcategory_id] column value.
     *
     * @return int
     */
    public function gettariffCategoryId()
    {
        return $this->tariffcategory_id;
    }

    /**
     * Get the [finance_id] column value.
     *
     * @return int
     */
    public function getfinanceId()
    {
        return $this->finance_id;
    }

    /**
     * Get the [optionally formatted] temporal [retiredate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getretireDate($format = '%Y-%m-%d')
    {
        if ($this->retiredate === null) {
            return null;
        }

        if ($this->retiredate === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->retiredate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->retiredate, true), $x);
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
     * Get the [ambplan] column value.
     *
     * @return int
     */
    public function getambPlan()
    {
        return $this->ambplan;
    }

    /**
     * Get the [ambplan2] column value.
     *
     * @return int
     */
    public function getambPlan2()
    {
        return $this->ambplan2;
    }

    /**
     * Get the [ambnorm] column value.
     *
     * @return int
     */
    public function getambNorm()
    {
        return $this->ambnorm;
    }

    /**
     * Get the [homplan] column value.
     *
     * @return int
     */
    public function gethomPlan()
    {
        return $this->homplan;
    }

    /**
     * Get the [homplan2] column value.
     *
     * @return int
     */
    public function gethomPlan2()
    {
        return $this->homplan2;
    }

    /**
     * Get the [homnorm] column value.
     *
     * @return int
     */
    public function gethomNorm()
    {
        return $this->homnorm;
    }

    /**
     * Get the [expplan] column value.
     *
     * @return int
     */
    public function getexpPlan()
    {
        return $this->expplan;
    }

    /**
     * Get the [expnorm] column value.
     *
     * @return int
     */
    public function getexpNorm()
    {
        return $this->expnorm;
    }

    /**
     * Get the [login] column value.
     *
     * @return string
     */
    public function getlogin()
    {
        return $this->login;
    }

    /**
     * Get the [password] column value.
     *
     * @return string
     */
    public function getpassword()
    {
        return $this->password;
    }

    /**
     * Get the [userprofile_id] column value.
     *
     * @return int
     */
    public function getuserProfileId()
    {
        return $this->userprofile_id;
    }

    /**
     * Get the [retired] column value.
     *
     * @return boolean
     */
    public function getretired()
    {
        return $this->retired;
    }

    /**
     * Get the [optionally formatted] temporal [birthdate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getbirthDate($format = '%Y-%m-%d')
    {
        if ($this->birthdate === null) {
            return null;
        }

        if ($this->birthdate === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->birthdate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->birthdate, true), $x);
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
     * Get the [birthplace] column value.
     *
     * @return string
     */
    public function getbirthPlace()
    {
        return $this->birthplace;
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
     * Get the [snils] column value.
     *
     * @return string
     */
    public function getsnils()
    {
        return $this->snils;
    }

    /**
     * Get the [inn] column value.
     *
     * @return string
     */
    public function getinn()
    {
        return $this->inn;
    }

    /**
     * Get the [availableforexternal] column value.
     *
     * @return int
     */
    public function getavailableForExternal()
    {
        return $this->availableforexternal;
    }

    /**
     * Get the [primaryquota] column value.
     *
     * @return int
     */
    public function getprimaryQuota()
    {
        return $this->primaryquota;
    }

    /**
     * Get the [ownquota] column value.
     *
     * @return int
     */
    public function getownQuota()
    {
        return $this->ownquota;
    }

    /**
     * Get the [consultancyquota] column value.
     *
     * @return int
     */
    public function getconsultancyQuota()
    {
        return $this->consultancyquota;
    }

    /**
     * Get the [externalquota] column value.
     *
     * @return int
     */
    public function getexternalQuota()
    {
        return $this->externalquota;
    }

    /**
     * Get the [optionally formatted] temporal [lastaccessibletimelinedate] column value.
     *
     *
     * @param string $format The date/time format string (either date()-style or strftime()-style).
     *				 If format is null, then the raw DateTime object will be returned.
     * @return mixed Formatted date/time value as string or DateTime object (if format is null), null if column is null, and 0 if column value is 0000-00-00
     * @throws PropelException - if unable to parse/validate the date/time value.
     */
    public function getlastAccessibleTimelineDate($format = '%Y-%m-%d')
    {
        if ($this->lastaccessibletimelinedate === null) {
            return null;
        }

        if ($this->lastaccessibletimelinedate === '0000-00-00') {
            // while technically this is not a default value of null,
            // this seems to be closest in meaning.
            return null;
        }

        try {
            $dt = new DateTime($this->lastaccessibletimelinedate);
        } catch (Exception $x) {
            throw new PropelException("Internally stored date/time/timestamp value could not be converted to DateTime: " . var_export($this->lastaccessibletimelinedate, true), $x);
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
     * Get the [timelineaccessibledays] column value.
     *
     * @return int
     */
    public function gettimelineAccessibleDays()
    {
        return $this->timelineaccessibledays;
    }

    /**
     * Get the [typetimelineperson] column value.
     *
     * @return int
     */
    public function gettypeTimeLinePerson()
    {
        return $this->typetimelineperson;
    }

    /**
     * Get the [maxoverqueue] column value.
     *
     * @return int
     */
    public function getmaxOverQueue()
    {
        return $this->maxoverqueue;
    }

    /**
     * Get the [maxcito] column value.
     *
     * @return int
     */
    public function getmaxCito()
    {
        return $this->maxcito;
    }

    /**
     * Get the [quotunit] column value.
     *
     * @return int
     */
    public function getquotUnit()
    {
        return $this->quotunit;
    }

    /**
     * Get the [academicdegree_id] column value.
     *
     * @return int
     */
    public function getacademicDegreeId()
    {
        return $this->academicdegree_id;
    }

    /**
     * Get the [academictitle_id] column value.
     *
     * @return int
     */
    public function getacademicTitleId()
    {
        return $this->academictitle_id;
    }

    /**
     * Get the [uuid_id] column value.
     *
     * @return int
     */
    public function getuuidId()
    {
        return $this->uuid_id;
    }

    /**
     * Set the value of [id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setid($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->id !== $v) {
            $this->id = $v;
            $this->modifiedColumns[] = PersonPeer::ID;
        }


        return $this;
    } // setid()

    /**
     * Sets the value of [createdatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Person The current object (for fluent API support)
     */
    public function setcreateDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->createdatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->createdatetime !== null && $tmpDt = new DateTime($this->createdatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->createdatetime = $newDateAsString;
                $this->modifiedColumns[] = PersonPeer::CREATEDATETIME;
            }
        } // if either are not null


        return $this;
    } // setcreateDatetime()

    /**
     * Set the value of [createperson_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setcreatePersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->createperson_id !== $v) {
            $this->createperson_id = $v;
            $this->modifiedColumns[] = PersonPeer::CREATEPERSON_ID;
        }


        return $this;
    } // setcreatePersonId()

    /**
     * Sets the value of [modifydatetime] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Person The current object (for fluent API support)
     */
    public function setmodifyDatetime($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->modifydatetime !== null || $dt !== null) {
            $currentDateAsString = ($this->modifydatetime !== null && $tmpDt = new DateTime($this->modifydatetime)) ? $tmpDt->format('Y-m-d H:i:s') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d H:i:s') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->modifydatetime = $newDateAsString;
                $this->modifiedColumns[] = PersonPeer::MODIFYDATETIME;
            }
        } // if either are not null


        return $this;
    } // setmodifyDatetime()

    /**
     * Set the value of [modifyperson_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setmodifyPersonId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->modifyperson_id !== $v) {
            $this->modifyperson_id = $v;
            $this->modifiedColumns[] = PersonPeer::MODIFYPERSON_ID;
        }


        return $this;
    } // setmodifyPersonId()

    /**
     * Sets the value of the [deleted] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Person The current object (for fluent API support)
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
            $this->modifiedColumns[] = PersonPeer::DELETED;
        }


        return $this;
    } // setdeleted()

    /**
     * Set the value of [code] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setcode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->code !== $v) {
            $this->code = $v;
            $this->modifiedColumns[] = PersonPeer::CODE;
        }


        return $this;
    } // setcode()

    /**
     * Set the value of [federalcode] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setfederalCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->federalcode !== $v) {
            $this->federalcode = $v;
            $this->modifiedColumns[] = PersonPeer::FEDERALCODE;
        }


        return $this;
    } // setfederalCode()

    /**
     * Set the value of [regionalcode] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setregionalCode($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->regionalcode !== $v) {
            $this->regionalcode = $v;
            $this->modifiedColumns[] = PersonPeer::REGIONALCODE;
        }


        return $this;
    } // setregionalCode()

    /**
     * Set the value of [lastname] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setlastName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->lastname !== $v) {
            $this->lastname = $v;
            $this->modifiedColumns[] = PersonPeer::LASTNAME;
        }


        return $this;
    } // setlastName()

    /**
     * Set the value of [firstname] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setfirstName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->firstname !== $v) {
            $this->firstname = $v;
            $this->modifiedColumns[] = PersonPeer::FIRSTNAME;
        }


        return $this;
    } // setfirstName()

    /**
     * Set the value of [patrname] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setpatrName($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->patrname !== $v) {
            $this->patrname = $v;
            $this->modifiedColumns[] = PersonPeer::PATRNAME;
        }


        return $this;
    } // setpatrName()

    /**
     * Set the value of [post_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setpostId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->post_id !== $v) {
            $this->post_id = $v;
            $this->modifiedColumns[] = PersonPeer::POST_ID;
        }


        return $this;
    } // setpostId()

    /**
     * Set the value of [speciality_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setspecialityId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->speciality_id !== $v) {
            $this->speciality_id = $v;
            $this->modifiedColumns[] = PersonPeer::SPECIALITY_ID;
        }


        return $this;
    } // setspecialityId()

    /**
     * Set the value of [org_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setorgId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->org_id !== $v) {
            $this->org_id = $v;
            $this->modifiedColumns[] = PersonPeer::ORG_ID;
        }


        return $this;
    } // setorgId()

    /**
     * Set the value of [orgstructure_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setorgStructureId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->orgstructure_id !== $v) {
            $this->orgstructure_id = $v;
            $this->modifiedColumns[] = PersonPeer::ORGSTRUCTURE_ID;
        }


        return $this;
    } // setorgStructureId()

    /**
     * Set the value of [office] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setoffice($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->office !== $v) {
            $this->office = $v;
            $this->modifiedColumns[] = PersonPeer::OFFICE;
        }


        return $this;
    } // setoffice()

    /**
     * Set the value of [office2] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setoffice2($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->office2 !== $v) {
            $this->office2 = $v;
            $this->modifiedColumns[] = PersonPeer::OFFICE2;
        }


        return $this;
    } // setoffice2()

    /**
     * Set the value of [tariffcategory_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function settariffCategoryId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->tariffcategory_id !== $v) {
            $this->tariffcategory_id = $v;
            $this->modifiedColumns[] = PersonPeer::TARIFFCATEGORY_ID;
        }


        return $this;
    } // settariffCategoryId()

    /**
     * Set the value of [finance_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setfinanceId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->finance_id !== $v) {
            $this->finance_id = $v;
            $this->modifiedColumns[] = PersonPeer::FINANCE_ID;
        }


        return $this;
    } // setfinanceId()

    /**
     * Sets the value of [retiredate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Person The current object (for fluent API support)
     */
    public function setretireDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->retiredate !== null || $dt !== null) {
            $currentDateAsString = ($this->retiredate !== null && $tmpDt = new DateTime($this->retiredate)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->retiredate = $newDateAsString;
                $this->modifiedColumns[] = PersonPeer::RETIREDATE;
            }
        } // if either are not null


        return $this;
    } // setretireDate()

    /**
     * Set the value of [ambplan] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setambPlan($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->ambplan !== $v) {
            $this->ambplan = $v;
            $this->modifiedColumns[] = PersonPeer::AMBPLAN;
        }


        return $this;
    } // setambPlan()

    /**
     * Set the value of [ambplan2] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setambPlan2($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->ambplan2 !== $v) {
            $this->ambplan2 = $v;
            $this->modifiedColumns[] = PersonPeer::AMBPLAN2;
        }


        return $this;
    } // setambPlan2()

    /**
     * Set the value of [ambnorm] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setambNorm($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->ambnorm !== $v) {
            $this->ambnorm = $v;
            $this->modifiedColumns[] = PersonPeer::AMBNORM;
        }


        return $this;
    } // setambNorm()

    /**
     * Set the value of [homplan] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function sethomPlan($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->homplan !== $v) {
            $this->homplan = $v;
            $this->modifiedColumns[] = PersonPeer::HOMPLAN;
        }


        return $this;
    } // sethomPlan()

    /**
     * Set the value of [homplan2] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function sethomPlan2($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->homplan2 !== $v) {
            $this->homplan2 = $v;
            $this->modifiedColumns[] = PersonPeer::HOMPLAN2;
        }


        return $this;
    } // sethomPlan2()

    /**
     * Set the value of [homnorm] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function sethomNorm($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->homnorm !== $v) {
            $this->homnorm = $v;
            $this->modifiedColumns[] = PersonPeer::HOMNORM;
        }


        return $this;
    } // sethomNorm()

    /**
     * Set the value of [expplan] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setexpPlan($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->expplan !== $v) {
            $this->expplan = $v;
            $this->modifiedColumns[] = PersonPeer::EXPPLAN;
        }


        return $this;
    } // setexpPlan()

    /**
     * Set the value of [expnorm] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setexpNorm($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->expnorm !== $v) {
            $this->expnorm = $v;
            $this->modifiedColumns[] = PersonPeer::EXPNORM;
        }


        return $this;
    } // setexpNorm()

    /**
     * Set the value of [login] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setlogin($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->login !== $v) {
            $this->login = $v;
            $this->modifiedColumns[] = PersonPeer::LOGIN;
        }


        return $this;
    } // setlogin()

    /**
     * Set the value of [password] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setpassword($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->password !== $v) {
            $this->password = $v;
            $this->modifiedColumns[] = PersonPeer::PASSWORD;
        }


        return $this;
    } // setpassword()

    /**
     * Set the value of [userprofile_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setuserProfileId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->userprofile_id !== $v) {
            $this->userprofile_id = $v;
            $this->modifiedColumns[] = PersonPeer::USERPROFILE_ID;
        }


        return $this;
    } // setuserProfileId()

    /**
     * Sets the value of the [retired] column.
     * Non-boolean arguments are converted using the following rules:
     *   * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *   * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     * Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     *
     * @param boolean|integer|string $v The new value
     * @return Person The current object (for fluent API support)
     */
    public function setretired($v)
    {
        if ($v !== null) {
            if (is_string($v)) {
                $v = in_array(strtolower($v), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
            } else {
                $v = (boolean) $v;
            }
        }

        if ($this->retired !== $v) {
            $this->retired = $v;
            $this->modifiedColumns[] = PersonPeer::RETIRED;
        }


        return $this;
    } // setretired()

    /**
     * Sets the value of [birthdate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Person The current object (for fluent API support)
     */
    public function setbirthDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->birthdate !== null || $dt !== null) {
            $currentDateAsString = ($this->birthdate !== null && $tmpDt = new DateTime($this->birthdate)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->birthdate = $newDateAsString;
                $this->modifiedColumns[] = PersonPeer::BIRTHDATE;
            }
        } // if either are not null


        return $this;
    } // setbirthDate()

    /**
     * Set the value of [birthplace] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setbirthPlace($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->birthplace !== $v) {
            $this->birthplace = $v;
            $this->modifiedColumns[] = PersonPeer::BIRTHPLACE;
        }


        return $this;
    } // setbirthPlace()

    /**
     * Set the value of [sex] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setsex($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->sex !== $v) {
            $this->sex = $v;
            $this->modifiedColumns[] = PersonPeer::SEX;
        }


        return $this;
    } // setsex()

    /**
     * Set the value of [snils] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setsnils($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->snils !== $v) {
            $this->snils = $v;
            $this->modifiedColumns[] = PersonPeer::SNILS;
        }


        return $this;
    } // setsnils()

    /**
     * Set the value of [inn] column.
     *
     * @param string $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setinn($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (string) $v;
        }

        if ($this->inn !== $v) {
            $this->inn = $v;
            $this->modifiedColumns[] = PersonPeer::INN;
        }


        return $this;
    } // setinn()

    /**
     * Set the value of [availableforexternal] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setavailableForExternal($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->availableforexternal !== $v) {
            $this->availableforexternal = $v;
            $this->modifiedColumns[] = PersonPeer::AVAILABLEFOREXTERNAL;
        }


        return $this;
    } // setavailableForExternal()

    /**
     * Set the value of [primaryquota] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setprimaryQuota($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->primaryquota !== $v) {
            $this->primaryquota = $v;
            $this->modifiedColumns[] = PersonPeer::PRIMARYQUOTA;
        }


        return $this;
    } // setprimaryQuota()

    /**
     * Set the value of [ownquota] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setownQuota($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->ownquota !== $v) {
            $this->ownquota = $v;
            $this->modifiedColumns[] = PersonPeer::OWNQUOTA;
        }


        return $this;
    } // setownQuota()

    /**
     * Set the value of [consultancyquota] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setconsultancyQuota($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->consultancyquota !== $v) {
            $this->consultancyquota = $v;
            $this->modifiedColumns[] = PersonPeer::CONSULTANCYQUOTA;
        }


        return $this;
    } // setconsultancyQuota()

    /**
     * Set the value of [externalquota] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setexternalQuota($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->externalquota !== $v) {
            $this->externalquota = $v;
            $this->modifiedColumns[] = PersonPeer::EXTERNALQUOTA;
        }


        return $this;
    } // setexternalQuota()

    /**
     * Sets the value of [lastaccessibletimelinedate] column to a normalized version of the date/time value specified.
     *
     * @param mixed $v string, integer (timestamp), or DateTime value.
     *               Empty strings are treated as null.
     * @return Person The current object (for fluent API support)
     */
    public function setlastAccessibleTimelineDate($v)
    {
        $dt = PropelDateTime::newInstance($v, null, 'DateTime');
        if ($this->lastaccessibletimelinedate !== null || $dt !== null) {
            $currentDateAsString = ($this->lastaccessibletimelinedate !== null && $tmpDt = new DateTime($this->lastaccessibletimelinedate)) ? $tmpDt->format('Y-m-d') : null;
            $newDateAsString = $dt ? $dt->format('Y-m-d') : null;
            if ($currentDateAsString !== $newDateAsString) {
                $this->lastaccessibletimelinedate = $newDateAsString;
                $this->modifiedColumns[] = PersonPeer::LASTACCESSIBLETIMELINEDATE;
            }
        } // if either are not null


        return $this;
    } // setlastAccessibleTimelineDate()

    /**
     * Set the value of [timelineaccessibledays] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function settimelineAccessibleDays($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->timelineaccessibledays !== $v) {
            $this->timelineaccessibledays = $v;
            $this->modifiedColumns[] = PersonPeer::TIMELINEACCESSIBLEDAYS;
        }


        return $this;
    } // settimelineAccessibleDays()

    /**
     * Set the value of [typetimelineperson] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function settypeTimeLinePerson($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->typetimelineperson !== $v) {
            $this->typetimelineperson = $v;
            $this->modifiedColumns[] = PersonPeer::TYPETIMELINEPERSON;
        }


        return $this;
    } // settypeTimeLinePerson()

    /**
     * Set the value of [maxoverqueue] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setmaxOverQueue($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->maxoverqueue !== $v) {
            $this->maxoverqueue = $v;
            $this->modifiedColumns[] = PersonPeer::MAXOVERQUEUE;
        }


        return $this;
    } // setmaxOverQueue()

    /**
     * Set the value of [maxcito] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setmaxCito($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->maxcito !== $v) {
            $this->maxcito = $v;
            $this->modifiedColumns[] = PersonPeer::MAXCITO;
        }


        return $this;
    } // setmaxCito()

    /**
     * Set the value of [quotunit] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setquotUnit($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->quotunit !== $v) {
            $this->quotunit = $v;
            $this->modifiedColumns[] = PersonPeer::QUOTUNIT;
        }


        return $this;
    } // setquotUnit()

    /**
     * Set the value of [academicdegree_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setacademicDegreeId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->academicdegree_id !== $v) {
            $this->academicdegree_id = $v;
            $this->modifiedColumns[] = PersonPeer::ACADEMICDEGREE_ID;
        }


        return $this;
    } // setacademicDegreeId()

    /**
     * Set the value of [academictitle_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setacademicTitleId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->academictitle_id !== $v) {
            $this->academictitle_id = $v;
            $this->modifiedColumns[] = PersonPeer::ACADEMICTITLE_ID;
        }


        return $this;
    } // setacademicTitleId()

    /**
     * Set the value of [uuid_id] column.
     *
     * @param int $v new value
     * @return Person The current object (for fluent API support)
     */
    public function setuuidId($v)
    {
        if ($v !== null && is_numeric($v)) {
            $v = (int) $v;
        }

        if ($this->uuid_id !== $v) {
            $this->uuid_id = $v;
            $this->modifiedColumns[] = PersonPeer::UUID_ID;
        }


        return $this;
    } // setuuidId()

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

            if ($this->availableforexternal !== 1) {
                return false;
            }

            if ($this->primaryquota !== 50) {
                return false;
            }

            if ($this->ownquota !== 25) {
                return false;
            }

            if ($this->consultancyquota !== 25) {
                return false;
            }

            if ($this->externalquota !== 10) {
                return false;
            }

            if ($this->timelineaccessibledays !== 0) {
                return false;
            }

            if ($this->maxoverqueue !== 0) {
                return false;
            }

            if ($this->maxcito !== 0) {
                return false;
            }

            if ($this->quotunit !== 0) {
                return false;
            }

            if ($this->uuid_id !== 0) {
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
            $this->createdatetime = ($row[$startcol + 1] !== null) ? (string) $row[$startcol + 1] : null;
            $this->createperson_id = ($row[$startcol + 2] !== null) ? (int) $row[$startcol + 2] : null;
            $this->modifydatetime = ($row[$startcol + 3] !== null) ? (string) $row[$startcol + 3] : null;
            $this->modifyperson_id = ($row[$startcol + 4] !== null) ? (int) $row[$startcol + 4] : null;
            $this->deleted = ($row[$startcol + 5] !== null) ? (boolean) $row[$startcol + 5] : null;
            $this->code = ($row[$startcol + 6] !== null) ? (string) $row[$startcol + 6] : null;
            $this->federalcode = ($row[$startcol + 7] !== null) ? (string) $row[$startcol + 7] : null;
            $this->regionalcode = ($row[$startcol + 8] !== null) ? (string) $row[$startcol + 8] : null;
            $this->lastname = ($row[$startcol + 9] !== null) ? (string) $row[$startcol + 9] : null;
            $this->firstname = ($row[$startcol + 10] !== null) ? (string) $row[$startcol + 10] : null;
            $this->patrname = ($row[$startcol + 11] !== null) ? (string) $row[$startcol + 11] : null;
            $this->post_id = ($row[$startcol + 12] !== null) ? (int) $row[$startcol + 12] : null;
            $this->speciality_id = ($row[$startcol + 13] !== null) ? (int) $row[$startcol + 13] : null;
            $this->org_id = ($row[$startcol + 14] !== null) ? (int) $row[$startcol + 14] : null;
            $this->orgstructure_id = ($row[$startcol + 15] !== null) ? (int) $row[$startcol + 15] : null;
            $this->office = ($row[$startcol + 16] !== null) ? (string) $row[$startcol + 16] : null;
            $this->office2 = ($row[$startcol + 17] !== null) ? (string) $row[$startcol + 17] : null;
            $this->tariffcategory_id = ($row[$startcol + 18] !== null) ? (int) $row[$startcol + 18] : null;
            $this->finance_id = ($row[$startcol + 19] !== null) ? (int) $row[$startcol + 19] : null;
            $this->retiredate = ($row[$startcol + 20] !== null) ? (string) $row[$startcol + 20] : null;
            $this->ambplan = ($row[$startcol + 21] !== null) ? (int) $row[$startcol + 21] : null;
            $this->ambplan2 = ($row[$startcol + 22] !== null) ? (int) $row[$startcol + 22] : null;
            $this->ambnorm = ($row[$startcol + 23] !== null) ? (int) $row[$startcol + 23] : null;
            $this->homplan = ($row[$startcol + 24] !== null) ? (int) $row[$startcol + 24] : null;
            $this->homplan2 = ($row[$startcol + 25] !== null) ? (int) $row[$startcol + 25] : null;
            $this->homnorm = ($row[$startcol + 26] !== null) ? (int) $row[$startcol + 26] : null;
            $this->expplan = ($row[$startcol + 27] !== null) ? (int) $row[$startcol + 27] : null;
            $this->expnorm = ($row[$startcol + 28] !== null) ? (int) $row[$startcol + 28] : null;
            $this->login = ($row[$startcol + 29] !== null) ? (string) $row[$startcol + 29] : null;
            $this->password = ($row[$startcol + 30] !== null) ? (string) $row[$startcol + 30] : null;
            $this->userprofile_id = ($row[$startcol + 31] !== null) ? (int) $row[$startcol + 31] : null;
            $this->retired = ($row[$startcol + 32] !== null) ? (boolean) $row[$startcol + 32] : null;
            $this->birthdate = ($row[$startcol + 33] !== null) ? (string) $row[$startcol + 33] : null;
            $this->birthplace = ($row[$startcol + 34] !== null) ? (string) $row[$startcol + 34] : null;
            $this->sex = ($row[$startcol + 35] !== null) ? (int) $row[$startcol + 35] : null;
            $this->snils = ($row[$startcol + 36] !== null) ? (string) $row[$startcol + 36] : null;
            $this->inn = ($row[$startcol + 37] !== null) ? (string) $row[$startcol + 37] : null;
            $this->availableforexternal = ($row[$startcol + 38] !== null) ? (int) $row[$startcol + 38] : null;
            $this->primaryquota = ($row[$startcol + 39] !== null) ? (int) $row[$startcol + 39] : null;
            $this->ownquota = ($row[$startcol + 40] !== null) ? (int) $row[$startcol + 40] : null;
            $this->consultancyquota = ($row[$startcol + 41] !== null) ? (int) $row[$startcol + 41] : null;
            $this->externalquota = ($row[$startcol + 42] !== null) ? (int) $row[$startcol + 42] : null;
            $this->lastaccessibletimelinedate = ($row[$startcol + 43] !== null) ? (string) $row[$startcol + 43] : null;
            $this->timelineaccessibledays = ($row[$startcol + 44] !== null) ? (int) $row[$startcol + 44] : null;
            $this->typetimelineperson = ($row[$startcol + 45] !== null) ? (int) $row[$startcol + 45] : null;
            $this->maxoverqueue = ($row[$startcol + 46] !== null) ? (int) $row[$startcol + 46] : null;
            $this->maxcito = ($row[$startcol + 47] !== null) ? (int) $row[$startcol + 47] : null;
            $this->quotunit = ($row[$startcol + 48] !== null) ? (int) $row[$startcol + 48] : null;
            $this->academicdegree_id = ($row[$startcol + 49] !== null) ? (int) $row[$startcol + 49] : null;
            $this->academictitle_id = ($row[$startcol + 50] !== null) ? (int) $row[$startcol + 50] : null;
            $this->uuid_id = ($row[$startcol + 51] !== null) ? (int) $row[$startcol + 51] : null;
            $this->resetModified();

            $this->setNew(false);

            if ($rehydrate) {
                $this->ensureConsistency();
            }
            $this->postHydrate($row, $startcol, $rehydrate);
            return $startcol + 52; // 52 = PersonPeer::NUM_HYDRATE_COLUMNS.

        } catch (Exception $e) {
            throw new PropelException("Error populating Person object", $e);
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
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }

        // We don't need to alter the object instance pool; we're just modifying this instance
        // already in the pool.

        $stmt = PersonPeer::doSelectStmt($this->buildPkeyCriteria(), $con);
        $row = $stmt->fetch(PDO::FETCH_NUM);
        $stmt->closeCursor();
        if (!$row) {
            throw new PropelException('Cannot find matching row in the database to reload object values.');
        }
        $this->hydrate($row, 0, true); // rehydrate

        if ($deep) {  // also de-associate any related objects?

            $this->collActionsRelatedBycreatePersonId = null;

            $this->collActionsRelatedBymodifyPersonId = null;

            $this->collActionsRelatedBysetPersonId = null;

            $this->collEventsRelatedBycreatePersonId = null;

            $this->collEventsRelatedBymodifyPersonId = null;

            $this->collEventsRelatedBysetPersonId = null;

            $this->collEventsRelatedByexecPersonId = null;

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
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        try {
            $deleteQuery = PersonQuery::create()
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
            $con = Propel::getConnection(PersonPeer::DATABASE_NAME, Propel::CONNECTION_WRITE);
        }

        $con->beginTransaction();
        $isInsert = $this->isNew();
        try {
            $ret = $this->preSave($con);
            if ($isInsert) {
                $ret = $ret && $this->preInsert($con);
                // timestampable behavior
                if (!$this->isColumnModified(PersonPeer::CREATEDATETIME)) {
                    $this->setcreateDatetime(time());
                }
                if (!$this->isColumnModified(PersonPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            } else {
                $ret = $ret && $this->preUpdate($con);
                // timestampable behavior
                if ($this->isModified() && !$this->isColumnModified(PersonPeer::MODIFYDATETIME)) {
                    $this->setmodifyDatetime(time());
                }
            }
            if ($ret) {
                $affectedRows = $this->doSave($con);
                if ($isInsert) {
                    $this->postInsert($con);
                } else {
                    $this->postUpdate($con);
                }
                $this->postSave($con);
                PersonPeer::addInstanceToPool($this);
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

            if ($this->actionsRelatedBycreatePersonIdScheduledForDeletion !== null) {
                if (!$this->actionsRelatedBycreatePersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->actionsRelatedBycreatePersonIdScheduledForDeletion as $actionRelatedBycreatePersonId) {
                        // need to save related object because we set the relation to null
                        $actionRelatedBycreatePersonId->save($con);
                    }
                    $this->actionsRelatedBycreatePersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collActionsRelatedBycreatePersonId !== null) {
                foreach ($this->collActionsRelatedBycreatePersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->actionsRelatedBymodifyPersonIdScheduledForDeletion !== null) {
                if (!$this->actionsRelatedBymodifyPersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->actionsRelatedBymodifyPersonIdScheduledForDeletion as $actionRelatedBymodifyPersonId) {
                        // need to save related object because we set the relation to null
                        $actionRelatedBymodifyPersonId->save($con);
                    }
                    $this->actionsRelatedBymodifyPersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collActionsRelatedBymodifyPersonId !== null) {
                foreach ($this->collActionsRelatedBymodifyPersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->actionsRelatedBysetPersonIdScheduledForDeletion !== null) {
                if (!$this->actionsRelatedBysetPersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->actionsRelatedBysetPersonIdScheduledForDeletion as $actionRelatedBysetPersonId) {
                        // need to save related object because we set the relation to null
                        $actionRelatedBysetPersonId->save($con);
                    }
                    $this->actionsRelatedBysetPersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collActionsRelatedBysetPersonId !== null) {
                foreach ($this->collActionsRelatedBysetPersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->eventsRelatedBycreatePersonIdScheduledForDeletion !== null) {
                if (!$this->eventsRelatedBycreatePersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->eventsRelatedBycreatePersonIdScheduledForDeletion as $eventRelatedBycreatePersonId) {
                        // need to save related object because we set the relation to null
                        $eventRelatedBycreatePersonId->save($con);
                    }
                    $this->eventsRelatedBycreatePersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collEventsRelatedBycreatePersonId !== null) {
                foreach ($this->collEventsRelatedBycreatePersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->eventsRelatedBymodifyPersonIdScheduledForDeletion !== null) {
                if (!$this->eventsRelatedBymodifyPersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->eventsRelatedBymodifyPersonIdScheduledForDeletion as $eventRelatedBymodifyPersonId) {
                        // need to save related object because we set the relation to null
                        $eventRelatedBymodifyPersonId->save($con);
                    }
                    $this->eventsRelatedBymodifyPersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collEventsRelatedBymodifyPersonId !== null) {
                foreach ($this->collEventsRelatedBymodifyPersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->eventsRelatedBysetPersonIdScheduledForDeletion !== null) {
                if (!$this->eventsRelatedBysetPersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->eventsRelatedBysetPersonIdScheduledForDeletion as $eventRelatedBysetPersonId) {
                        // need to save related object because we set the relation to null
                        $eventRelatedBysetPersonId->save($con);
                    }
                    $this->eventsRelatedBysetPersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collEventsRelatedBysetPersonId !== null) {
                foreach ($this->collEventsRelatedBysetPersonId as $referrerFK) {
                    if (!$referrerFK->isDeleted() && ($referrerFK->isNew() || $referrerFK->isModified())) {
                        $affectedRows += $referrerFK->save($con);
                    }
                }
            }

            if ($this->eventsRelatedByexecPersonIdScheduledForDeletion !== null) {
                if (!$this->eventsRelatedByexecPersonIdScheduledForDeletion->isEmpty()) {
                    foreach ($this->eventsRelatedByexecPersonIdScheduledForDeletion as $eventRelatedByexecPersonId) {
                        // need to save related object because we set the relation to null
                        $eventRelatedByexecPersonId->save($con);
                    }
                    $this->eventsRelatedByexecPersonIdScheduledForDeletion = null;
                }
            }

            if ($this->collEventsRelatedByexecPersonId !== null) {
                foreach ($this->collEventsRelatedByexecPersonId as $referrerFK) {
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

        $this->modifiedColumns[] = PersonPeer::ID;
        if (null !== $this->id) {
            throw new PropelException('Cannot insert a value for auto-increment primary key (' . PersonPeer::ID . ')');
        }

         // check the columns in natural order for more readable SQL queries
        if ($this->isColumnModified(PersonPeer::ID)) {
            $modifiedColumns[':p' . $index++]  = '`id`';
        }
        if ($this->isColumnModified(PersonPeer::CREATEDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`createDatetime`';
        }
        if ($this->isColumnModified(PersonPeer::CREATEPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`createPerson_id`';
        }
        if ($this->isColumnModified(PersonPeer::MODIFYDATETIME)) {
            $modifiedColumns[':p' . $index++]  = '`modifyDatetime`';
        }
        if ($this->isColumnModified(PersonPeer::MODIFYPERSON_ID)) {
            $modifiedColumns[':p' . $index++]  = '`modifyPerson_id`';
        }
        if ($this->isColumnModified(PersonPeer::DELETED)) {
            $modifiedColumns[':p' . $index++]  = '`deleted`';
        }
        if ($this->isColumnModified(PersonPeer::CODE)) {
            $modifiedColumns[':p' . $index++]  = '`code`';
        }
        if ($this->isColumnModified(PersonPeer::FEDERALCODE)) {
            $modifiedColumns[':p' . $index++]  = '`federalCode`';
        }
        if ($this->isColumnModified(PersonPeer::REGIONALCODE)) {
            $modifiedColumns[':p' . $index++]  = '`regionalCode`';
        }
        if ($this->isColumnModified(PersonPeer::LASTNAME)) {
            $modifiedColumns[':p' . $index++]  = '`lastName`';
        }
        if ($this->isColumnModified(PersonPeer::FIRSTNAME)) {
            $modifiedColumns[':p' . $index++]  = '`firstName`';
        }
        if ($this->isColumnModified(PersonPeer::PATRNAME)) {
            $modifiedColumns[':p' . $index++]  = '`patrName`';
        }
        if ($this->isColumnModified(PersonPeer::POST_ID)) {
            $modifiedColumns[':p' . $index++]  = '`post_id`';
        }
        if ($this->isColumnModified(PersonPeer::SPECIALITY_ID)) {
            $modifiedColumns[':p' . $index++]  = '`speciality_id`';
        }
        if ($this->isColumnModified(PersonPeer::ORG_ID)) {
            $modifiedColumns[':p' . $index++]  = '`org_id`';
        }
        if ($this->isColumnModified(PersonPeer::ORGSTRUCTURE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`orgStructure_id`';
        }
        if ($this->isColumnModified(PersonPeer::OFFICE)) {
            $modifiedColumns[':p' . $index++]  = '`office`';
        }
        if ($this->isColumnModified(PersonPeer::OFFICE2)) {
            $modifiedColumns[':p' . $index++]  = '`office2`';
        }
        if ($this->isColumnModified(PersonPeer::TARIFFCATEGORY_ID)) {
            $modifiedColumns[':p' . $index++]  = '`tariffCategory_id`';
        }
        if ($this->isColumnModified(PersonPeer::FINANCE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`finance_id`';
        }
        if ($this->isColumnModified(PersonPeer::RETIREDATE)) {
            $modifiedColumns[':p' . $index++]  = '`retireDate`';
        }
        if ($this->isColumnModified(PersonPeer::AMBPLAN)) {
            $modifiedColumns[':p' . $index++]  = '`ambPlan`';
        }
        if ($this->isColumnModified(PersonPeer::AMBPLAN2)) {
            $modifiedColumns[':p' . $index++]  = '`ambPlan2`';
        }
        if ($this->isColumnModified(PersonPeer::AMBNORM)) {
            $modifiedColumns[':p' . $index++]  = '`ambNorm`';
        }
        if ($this->isColumnModified(PersonPeer::HOMPLAN)) {
            $modifiedColumns[':p' . $index++]  = '`homPlan`';
        }
        if ($this->isColumnModified(PersonPeer::HOMPLAN2)) {
            $modifiedColumns[':p' . $index++]  = '`homPlan2`';
        }
        if ($this->isColumnModified(PersonPeer::HOMNORM)) {
            $modifiedColumns[':p' . $index++]  = '`homNorm`';
        }
        if ($this->isColumnModified(PersonPeer::EXPPLAN)) {
            $modifiedColumns[':p' . $index++]  = '`expPlan`';
        }
        if ($this->isColumnModified(PersonPeer::EXPNORM)) {
            $modifiedColumns[':p' . $index++]  = '`expNorm`';
        }
        if ($this->isColumnModified(PersonPeer::LOGIN)) {
            $modifiedColumns[':p' . $index++]  = '`login`';
        }
        if ($this->isColumnModified(PersonPeer::PASSWORD)) {
            $modifiedColumns[':p' . $index++]  = '`password`';
        }
        if ($this->isColumnModified(PersonPeer::USERPROFILE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`userProfile_id`';
        }
        if ($this->isColumnModified(PersonPeer::RETIRED)) {
            $modifiedColumns[':p' . $index++]  = '`retired`';
        }
        if ($this->isColumnModified(PersonPeer::BIRTHDATE)) {
            $modifiedColumns[':p' . $index++]  = '`birthDate`';
        }
        if ($this->isColumnModified(PersonPeer::BIRTHPLACE)) {
            $modifiedColumns[':p' . $index++]  = '`birthPlace`';
        }
        if ($this->isColumnModified(PersonPeer::SEX)) {
            $modifiedColumns[':p' . $index++]  = '`sex`';
        }
        if ($this->isColumnModified(PersonPeer::SNILS)) {
            $modifiedColumns[':p' . $index++]  = '`SNILS`';
        }
        if ($this->isColumnModified(PersonPeer::INN)) {
            $modifiedColumns[':p' . $index++]  = '`INN`';
        }
        if ($this->isColumnModified(PersonPeer::AVAILABLEFOREXTERNAL)) {
            $modifiedColumns[':p' . $index++]  = '`availableForExternal`';
        }
        if ($this->isColumnModified(PersonPeer::PRIMARYQUOTA)) {
            $modifiedColumns[':p' . $index++]  = '`primaryQuota`';
        }
        if ($this->isColumnModified(PersonPeer::OWNQUOTA)) {
            $modifiedColumns[':p' . $index++]  = '`ownQuota`';
        }
        if ($this->isColumnModified(PersonPeer::CONSULTANCYQUOTA)) {
            $modifiedColumns[':p' . $index++]  = '`consultancyQuota`';
        }
        if ($this->isColumnModified(PersonPeer::EXTERNALQUOTA)) {
            $modifiedColumns[':p' . $index++]  = '`externalQuota`';
        }
        if ($this->isColumnModified(PersonPeer::LASTACCESSIBLETIMELINEDATE)) {
            $modifiedColumns[':p' . $index++]  = '`lastAccessibleTimelineDate`';
        }
        if ($this->isColumnModified(PersonPeer::TIMELINEACCESSIBLEDAYS)) {
            $modifiedColumns[':p' . $index++]  = '`timelineAccessibleDays`';
        }
        if ($this->isColumnModified(PersonPeer::TYPETIMELINEPERSON)) {
            $modifiedColumns[':p' . $index++]  = '`typeTimeLinePerson`';
        }
        if ($this->isColumnModified(PersonPeer::MAXOVERQUEUE)) {
            $modifiedColumns[':p' . $index++]  = '`maxOverQueue`';
        }
        if ($this->isColumnModified(PersonPeer::MAXCITO)) {
            $modifiedColumns[':p' . $index++]  = '`maxCito`';
        }
        if ($this->isColumnModified(PersonPeer::QUOTUNIT)) {
            $modifiedColumns[':p' . $index++]  = '`quotUnit`';
        }
        if ($this->isColumnModified(PersonPeer::ACADEMICDEGREE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`academicdegree_id`';
        }
        if ($this->isColumnModified(PersonPeer::ACADEMICTITLE_ID)) {
            $modifiedColumns[':p' . $index++]  = '`academicTitle_id`';
        }
        if ($this->isColumnModified(PersonPeer::UUID_ID)) {
            $modifiedColumns[':p' . $index++]  = '`uuid_id`';
        }

        $sql = sprintf(
            'INSERT INTO `Person` (%s) VALUES (%s)',
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
                    case '`createDatetime`':
                        $stmt->bindValue($identifier, $this->createdatetime, PDO::PARAM_STR);
                        break;
                    case '`createPerson_id`':
                        $stmt->bindValue($identifier, $this->createperson_id, PDO::PARAM_INT);
                        break;
                    case '`modifyDatetime`':
                        $stmt->bindValue($identifier, $this->modifydatetime, PDO::PARAM_STR);
                        break;
                    case '`modifyPerson_id`':
                        $stmt->bindValue($identifier, $this->modifyperson_id, PDO::PARAM_INT);
                        break;
                    case '`deleted`':
                        $stmt->bindValue($identifier, (int) $this->deleted, PDO::PARAM_INT);
                        break;
                    case '`code`':
                        $stmt->bindValue($identifier, $this->code, PDO::PARAM_STR);
                        break;
                    case '`federalCode`':
                        $stmt->bindValue($identifier, $this->federalcode, PDO::PARAM_STR);
                        break;
                    case '`regionalCode`':
                        $stmt->bindValue($identifier, $this->regionalcode, PDO::PARAM_STR);
                        break;
                    case '`lastName`':
                        $stmt->bindValue($identifier, $this->lastname, PDO::PARAM_STR);
                        break;
                    case '`firstName`':
                        $stmt->bindValue($identifier, $this->firstname, PDO::PARAM_STR);
                        break;
                    case '`patrName`':
                        $stmt->bindValue($identifier, $this->patrname, PDO::PARAM_STR);
                        break;
                    case '`post_id`':
                        $stmt->bindValue($identifier, $this->post_id, PDO::PARAM_INT);
                        break;
                    case '`speciality_id`':
                        $stmt->bindValue($identifier, $this->speciality_id, PDO::PARAM_INT);
                        break;
                    case '`org_id`':
                        $stmt->bindValue($identifier, $this->org_id, PDO::PARAM_INT);
                        break;
                    case '`orgStructure_id`':
                        $stmt->bindValue($identifier, $this->orgstructure_id, PDO::PARAM_INT);
                        break;
                    case '`office`':
                        $stmt->bindValue($identifier, $this->office, PDO::PARAM_STR);
                        break;
                    case '`office2`':
                        $stmt->bindValue($identifier, $this->office2, PDO::PARAM_STR);
                        break;
                    case '`tariffCategory_id`':
                        $stmt->bindValue($identifier, $this->tariffcategory_id, PDO::PARAM_INT);
                        break;
                    case '`finance_id`':
                        $stmt->bindValue($identifier, $this->finance_id, PDO::PARAM_INT);
                        break;
                    case '`retireDate`':
                        $stmt->bindValue($identifier, $this->retiredate, PDO::PARAM_STR);
                        break;
                    case '`ambPlan`':
                        $stmt->bindValue($identifier, $this->ambplan, PDO::PARAM_INT);
                        break;
                    case '`ambPlan2`':
                        $stmt->bindValue($identifier, $this->ambplan2, PDO::PARAM_INT);
                        break;
                    case '`ambNorm`':
                        $stmt->bindValue($identifier, $this->ambnorm, PDO::PARAM_INT);
                        break;
                    case '`homPlan`':
                        $stmt->bindValue($identifier, $this->homplan, PDO::PARAM_INT);
                        break;
                    case '`homPlan2`':
                        $stmt->bindValue($identifier, $this->homplan2, PDO::PARAM_INT);
                        break;
                    case '`homNorm`':
                        $stmt->bindValue($identifier, $this->homnorm, PDO::PARAM_INT);
                        break;
                    case '`expPlan`':
                        $stmt->bindValue($identifier, $this->expplan, PDO::PARAM_INT);
                        break;
                    case '`expNorm`':
                        $stmt->bindValue($identifier, $this->expnorm, PDO::PARAM_INT);
                        break;
                    case '`login`':
                        $stmt->bindValue($identifier, $this->login, PDO::PARAM_STR);
                        break;
                    case '`password`':
                        $stmt->bindValue($identifier, $this->password, PDO::PARAM_STR);
                        break;
                    case '`userProfile_id`':
                        $stmt->bindValue($identifier, $this->userprofile_id, PDO::PARAM_INT);
                        break;
                    case '`retired`':
                        $stmt->bindValue($identifier, (int) $this->retired, PDO::PARAM_INT);
                        break;
                    case '`birthDate`':
                        $stmt->bindValue($identifier, $this->birthdate, PDO::PARAM_STR);
                        break;
                    case '`birthPlace`':
                        $stmt->bindValue($identifier, $this->birthplace, PDO::PARAM_STR);
                        break;
                    case '`sex`':
                        $stmt->bindValue($identifier, $this->sex, PDO::PARAM_INT);
                        break;
                    case '`SNILS`':
                        $stmt->bindValue($identifier, $this->snils, PDO::PARAM_STR);
                        break;
                    case '`INN`':
                        $stmt->bindValue($identifier, $this->inn, PDO::PARAM_STR);
                        break;
                    case '`availableForExternal`':
                        $stmt->bindValue($identifier, $this->availableforexternal, PDO::PARAM_INT);
                        break;
                    case '`primaryQuota`':
                        $stmt->bindValue($identifier, $this->primaryquota, PDO::PARAM_INT);
                        break;
                    case '`ownQuota`':
                        $stmt->bindValue($identifier, $this->ownquota, PDO::PARAM_INT);
                        break;
                    case '`consultancyQuota`':
                        $stmt->bindValue($identifier, $this->consultancyquota, PDO::PARAM_INT);
                        break;
                    case '`externalQuota`':
                        $stmt->bindValue($identifier, $this->externalquota, PDO::PARAM_INT);
                        break;
                    case '`lastAccessibleTimelineDate`':
                        $stmt->bindValue($identifier, $this->lastaccessibletimelinedate, PDO::PARAM_STR);
                        break;
                    case '`timelineAccessibleDays`':
                        $stmt->bindValue($identifier, $this->timelineaccessibledays, PDO::PARAM_INT);
                        break;
                    case '`typeTimeLinePerson`':
                        $stmt->bindValue($identifier, $this->typetimelineperson, PDO::PARAM_INT);
                        break;
                    case '`maxOverQueue`':
                        $stmt->bindValue($identifier, $this->maxoverqueue, PDO::PARAM_INT);
                        break;
                    case '`maxCito`':
                        $stmt->bindValue($identifier, $this->maxcito, PDO::PARAM_INT);
                        break;
                    case '`quotUnit`':
                        $stmt->bindValue($identifier, $this->quotunit, PDO::PARAM_INT);
                        break;
                    case '`academicdegree_id`':
                        $stmt->bindValue($identifier, $this->academicdegree_id, PDO::PARAM_INT);
                        break;
                    case '`academicTitle_id`':
                        $stmt->bindValue($identifier, $this->academictitle_id, PDO::PARAM_INT);
                        break;
                    case '`uuid_id`':
                        $stmt->bindValue($identifier, $this->uuid_id, PDO::PARAM_INT);
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


            if (($retval = PersonPeer::doValidate($this, $columns)) !== true) {
                $failureMap = array_merge($failureMap, $retval);
            }


                if ($this->collActionsRelatedBycreatePersonId !== null) {
                    foreach ($this->collActionsRelatedBycreatePersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collActionsRelatedBymodifyPersonId !== null) {
                    foreach ($this->collActionsRelatedBymodifyPersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collActionsRelatedBysetPersonId !== null) {
                    foreach ($this->collActionsRelatedBysetPersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collEventsRelatedBycreatePersonId !== null) {
                    foreach ($this->collEventsRelatedBycreatePersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collEventsRelatedBymodifyPersonId !== null) {
                    foreach ($this->collEventsRelatedBymodifyPersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collEventsRelatedBysetPersonId !== null) {
                    foreach ($this->collEventsRelatedBysetPersonId as $referrerFK) {
                        if (!$referrerFK->validate($columns)) {
                            $failureMap = array_merge($failureMap, $referrerFK->getValidationFailures());
                        }
                    }
                }

                if ($this->collEventsRelatedByexecPersonId !== null) {
                    foreach ($this->collEventsRelatedByexecPersonId as $referrerFK) {
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
        $pos = PersonPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);
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
                return $this->getcreateDatetime();
                break;
            case 2:
                return $this->getcreatePersonId();
                break;
            case 3:
                return $this->getmodifyDatetime();
                break;
            case 4:
                return $this->getmodifyPersonId();
                break;
            case 5:
                return $this->getdeleted();
                break;
            case 6:
                return $this->getcode();
                break;
            case 7:
                return $this->getfederalCode();
                break;
            case 8:
                return $this->getregionalCode();
                break;
            case 9:
                return $this->getlastName();
                break;
            case 10:
                return $this->getfirstName();
                break;
            case 11:
                return $this->getpatrName();
                break;
            case 12:
                return $this->getpostId();
                break;
            case 13:
                return $this->getspecialityId();
                break;
            case 14:
                return $this->getorgId();
                break;
            case 15:
                return $this->getorgStructureId();
                break;
            case 16:
                return $this->getoffice();
                break;
            case 17:
                return $this->getoffice2();
                break;
            case 18:
                return $this->gettariffCategoryId();
                break;
            case 19:
                return $this->getfinanceId();
                break;
            case 20:
                return $this->getretireDate();
                break;
            case 21:
                return $this->getambPlan();
                break;
            case 22:
                return $this->getambPlan2();
                break;
            case 23:
                return $this->getambNorm();
                break;
            case 24:
                return $this->gethomPlan();
                break;
            case 25:
                return $this->gethomPlan2();
                break;
            case 26:
                return $this->gethomNorm();
                break;
            case 27:
                return $this->getexpPlan();
                break;
            case 28:
                return $this->getexpNorm();
                break;
            case 29:
                return $this->getlogin();
                break;
            case 30:
                return $this->getpassword();
                break;
            case 31:
                return $this->getuserProfileId();
                break;
            case 32:
                return $this->getretired();
                break;
            case 33:
                return $this->getbirthDate();
                break;
            case 34:
                return $this->getbirthPlace();
                break;
            case 35:
                return $this->getsex();
                break;
            case 36:
                return $this->getsnils();
                break;
            case 37:
                return $this->getinn();
                break;
            case 38:
                return $this->getavailableForExternal();
                break;
            case 39:
                return $this->getprimaryQuota();
                break;
            case 40:
                return $this->getownQuota();
                break;
            case 41:
                return $this->getconsultancyQuota();
                break;
            case 42:
                return $this->getexternalQuota();
                break;
            case 43:
                return $this->getlastAccessibleTimelineDate();
                break;
            case 44:
                return $this->gettimelineAccessibleDays();
                break;
            case 45:
                return $this->gettypeTimeLinePerson();
                break;
            case 46:
                return $this->getmaxOverQueue();
                break;
            case 47:
                return $this->getmaxCito();
                break;
            case 48:
                return $this->getquotUnit();
                break;
            case 49:
                return $this->getacademicDegreeId();
                break;
            case 50:
                return $this->getacademicTitleId();
                break;
            case 51:
                return $this->getuuidId();
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
        if (isset($alreadyDumpedObjects['Person'][$this->getPrimaryKey()])) {
            return '*RECURSION*';
        }
        $alreadyDumpedObjects['Person'][$this->getPrimaryKey()] = true;
        $keys = PersonPeer::getFieldNames($keyType);
        $result = array(
            $keys[0] => $this->getid(),
            $keys[1] => $this->getcreateDatetime(),
            $keys[2] => $this->getcreatePersonId(),
            $keys[3] => $this->getmodifyDatetime(),
            $keys[4] => $this->getmodifyPersonId(),
            $keys[5] => $this->getdeleted(),
            $keys[6] => $this->getcode(),
            $keys[7] => $this->getfederalCode(),
            $keys[8] => $this->getregionalCode(),
            $keys[9] => $this->getlastName(),
            $keys[10] => $this->getfirstName(),
            $keys[11] => $this->getpatrName(),
            $keys[12] => $this->getpostId(),
            $keys[13] => $this->getspecialityId(),
            $keys[14] => $this->getorgId(),
            $keys[15] => $this->getorgStructureId(),
            $keys[16] => $this->getoffice(),
            $keys[17] => $this->getoffice2(),
            $keys[18] => $this->gettariffCategoryId(),
            $keys[19] => $this->getfinanceId(),
            $keys[20] => $this->getretireDate(),
            $keys[21] => $this->getambPlan(),
            $keys[22] => $this->getambPlan2(),
            $keys[23] => $this->getambNorm(),
            $keys[24] => $this->gethomPlan(),
            $keys[25] => $this->gethomPlan2(),
            $keys[26] => $this->gethomNorm(),
            $keys[27] => $this->getexpPlan(),
            $keys[28] => $this->getexpNorm(),
            $keys[29] => $this->getlogin(),
            $keys[30] => $this->getpassword(),
            $keys[31] => $this->getuserProfileId(),
            $keys[32] => $this->getretired(),
            $keys[33] => $this->getbirthDate(),
            $keys[34] => $this->getbirthPlace(),
            $keys[35] => $this->getsex(),
            $keys[36] => $this->getsnils(),
            $keys[37] => $this->getinn(),
            $keys[38] => $this->getavailableForExternal(),
            $keys[39] => $this->getprimaryQuota(),
            $keys[40] => $this->getownQuota(),
            $keys[41] => $this->getconsultancyQuota(),
            $keys[42] => $this->getexternalQuota(),
            $keys[43] => $this->getlastAccessibleTimelineDate(),
            $keys[44] => $this->gettimelineAccessibleDays(),
            $keys[45] => $this->gettypeTimeLinePerson(),
            $keys[46] => $this->getmaxOverQueue(),
            $keys[47] => $this->getmaxCito(),
            $keys[48] => $this->getquotUnit(),
            $keys[49] => $this->getacademicDegreeId(),
            $keys[50] => $this->getacademicTitleId(),
            $keys[51] => $this->getuuidId(),
        );
        if ($includeForeignObjects) {
            if (null !== $this->collActionsRelatedBycreatePersonId) {
                $result['ActionsRelatedBycreatePersonId'] = $this->collActionsRelatedBycreatePersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collActionsRelatedBymodifyPersonId) {
                $result['ActionsRelatedBymodifyPersonId'] = $this->collActionsRelatedBymodifyPersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collActionsRelatedBysetPersonId) {
                $result['ActionsRelatedBysetPersonId'] = $this->collActionsRelatedBysetPersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collEventsRelatedBycreatePersonId) {
                $result['EventsRelatedBycreatePersonId'] = $this->collEventsRelatedBycreatePersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collEventsRelatedBymodifyPersonId) {
                $result['EventsRelatedBymodifyPersonId'] = $this->collEventsRelatedBymodifyPersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collEventsRelatedBysetPersonId) {
                $result['EventsRelatedBysetPersonId'] = $this->collEventsRelatedBysetPersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
            }
            if (null !== $this->collEventsRelatedByexecPersonId) {
                $result['EventsRelatedByexecPersonId'] = $this->collEventsRelatedByexecPersonId->toArray(null, true, $keyType, $includeLazyLoadColumns, $alreadyDumpedObjects);
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
        $pos = PersonPeer::translateFieldName($name, $type, BasePeer::TYPE_NUM);

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
                $this->setcreateDatetime($value);
                break;
            case 2:
                $this->setcreatePersonId($value);
                break;
            case 3:
                $this->setmodifyDatetime($value);
                break;
            case 4:
                $this->setmodifyPersonId($value);
                break;
            case 5:
                $this->setdeleted($value);
                break;
            case 6:
                $this->setcode($value);
                break;
            case 7:
                $this->setfederalCode($value);
                break;
            case 8:
                $this->setregionalCode($value);
                break;
            case 9:
                $this->setlastName($value);
                break;
            case 10:
                $this->setfirstName($value);
                break;
            case 11:
                $this->setpatrName($value);
                break;
            case 12:
                $this->setpostId($value);
                break;
            case 13:
                $this->setspecialityId($value);
                break;
            case 14:
                $this->setorgId($value);
                break;
            case 15:
                $this->setorgStructureId($value);
                break;
            case 16:
                $this->setoffice($value);
                break;
            case 17:
                $this->setoffice2($value);
                break;
            case 18:
                $this->settariffCategoryId($value);
                break;
            case 19:
                $this->setfinanceId($value);
                break;
            case 20:
                $this->setretireDate($value);
                break;
            case 21:
                $this->setambPlan($value);
                break;
            case 22:
                $this->setambPlan2($value);
                break;
            case 23:
                $this->setambNorm($value);
                break;
            case 24:
                $this->sethomPlan($value);
                break;
            case 25:
                $this->sethomPlan2($value);
                break;
            case 26:
                $this->sethomNorm($value);
                break;
            case 27:
                $this->setexpPlan($value);
                break;
            case 28:
                $this->setexpNorm($value);
                break;
            case 29:
                $this->setlogin($value);
                break;
            case 30:
                $this->setpassword($value);
                break;
            case 31:
                $this->setuserProfileId($value);
                break;
            case 32:
                $this->setretired($value);
                break;
            case 33:
                $this->setbirthDate($value);
                break;
            case 34:
                $this->setbirthPlace($value);
                break;
            case 35:
                $this->setsex($value);
                break;
            case 36:
                $this->setsnils($value);
                break;
            case 37:
                $this->setinn($value);
                break;
            case 38:
                $this->setavailableForExternal($value);
                break;
            case 39:
                $this->setprimaryQuota($value);
                break;
            case 40:
                $this->setownQuota($value);
                break;
            case 41:
                $this->setconsultancyQuota($value);
                break;
            case 42:
                $this->setexternalQuota($value);
                break;
            case 43:
                $this->setlastAccessibleTimelineDate($value);
                break;
            case 44:
                $this->settimelineAccessibleDays($value);
                break;
            case 45:
                $this->settypeTimeLinePerson($value);
                break;
            case 46:
                $this->setmaxOverQueue($value);
                break;
            case 47:
                $this->setmaxCito($value);
                break;
            case 48:
                $this->setquotUnit($value);
                break;
            case 49:
                $this->setacademicDegreeId($value);
                break;
            case 50:
                $this->setacademicTitleId($value);
                break;
            case 51:
                $this->setuuidId($value);
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
        $keys = PersonPeer::getFieldNames($keyType);

        if (array_key_exists($keys[0], $arr)) $this->setid($arr[$keys[0]]);
        if (array_key_exists($keys[1], $arr)) $this->setcreateDatetime($arr[$keys[1]]);
        if (array_key_exists($keys[2], $arr)) $this->setcreatePersonId($arr[$keys[2]]);
        if (array_key_exists($keys[3], $arr)) $this->setmodifyDatetime($arr[$keys[3]]);
        if (array_key_exists($keys[4], $arr)) $this->setmodifyPersonId($arr[$keys[4]]);
        if (array_key_exists($keys[5], $arr)) $this->setdeleted($arr[$keys[5]]);
        if (array_key_exists($keys[6], $arr)) $this->setcode($arr[$keys[6]]);
        if (array_key_exists($keys[7], $arr)) $this->setfederalCode($arr[$keys[7]]);
        if (array_key_exists($keys[8], $arr)) $this->setregionalCode($arr[$keys[8]]);
        if (array_key_exists($keys[9], $arr)) $this->setlastName($arr[$keys[9]]);
        if (array_key_exists($keys[10], $arr)) $this->setfirstName($arr[$keys[10]]);
        if (array_key_exists($keys[11], $arr)) $this->setpatrName($arr[$keys[11]]);
        if (array_key_exists($keys[12], $arr)) $this->setpostId($arr[$keys[12]]);
        if (array_key_exists($keys[13], $arr)) $this->setspecialityId($arr[$keys[13]]);
        if (array_key_exists($keys[14], $arr)) $this->setorgId($arr[$keys[14]]);
        if (array_key_exists($keys[15], $arr)) $this->setorgStructureId($arr[$keys[15]]);
        if (array_key_exists($keys[16], $arr)) $this->setoffice($arr[$keys[16]]);
        if (array_key_exists($keys[17], $arr)) $this->setoffice2($arr[$keys[17]]);
        if (array_key_exists($keys[18], $arr)) $this->settariffCategoryId($arr[$keys[18]]);
        if (array_key_exists($keys[19], $arr)) $this->setfinanceId($arr[$keys[19]]);
        if (array_key_exists($keys[20], $arr)) $this->setretireDate($arr[$keys[20]]);
        if (array_key_exists($keys[21], $arr)) $this->setambPlan($arr[$keys[21]]);
        if (array_key_exists($keys[22], $arr)) $this->setambPlan2($arr[$keys[22]]);
        if (array_key_exists($keys[23], $arr)) $this->setambNorm($arr[$keys[23]]);
        if (array_key_exists($keys[24], $arr)) $this->sethomPlan($arr[$keys[24]]);
        if (array_key_exists($keys[25], $arr)) $this->sethomPlan2($arr[$keys[25]]);
        if (array_key_exists($keys[26], $arr)) $this->sethomNorm($arr[$keys[26]]);
        if (array_key_exists($keys[27], $arr)) $this->setexpPlan($arr[$keys[27]]);
        if (array_key_exists($keys[28], $arr)) $this->setexpNorm($arr[$keys[28]]);
        if (array_key_exists($keys[29], $arr)) $this->setlogin($arr[$keys[29]]);
        if (array_key_exists($keys[30], $arr)) $this->setpassword($arr[$keys[30]]);
        if (array_key_exists($keys[31], $arr)) $this->setuserProfileId($arr[$keys[31]]);
        if (array_key_exists($keys[32], $arr)) $this->setretired($arr[$keys[32]]);
        if (array_key_exists($keys[33], $arr)) $this->setbirthDate($arr[$keys[33]]);
        if (array_key_exists($keys[34], $arr)) $this->setbirthPlace($arr[$keys[34]]);
        if (array_key_exists($keys[35], $arr)) $this->setsex($arr[$keys[35]]);
        if (array_key_exists($keys[36], $arr)) $this->setsnils($arr[$keys[36]]);
        if (array_key_exists($keys[37], $arr)) $this->setinn($arr[$keys[37]]);
        if (array_key_exists($keys[38], $arr)) $this->setavailableForExternal($arr[$keys[38]]);
        if (array_key_exists($keys[39], $arr)) $this->setprimaryQuota($arr[$keys[39]]);
        if (array_key_exists($keys[40], $arr)) $this->setownQuota($arr[$keys[40]]);
        if (array_key_exists($keys[41], $arr)) $this->setconsultancyQuota($arr[$keys[41]]);
        if (array_key_exists($keys[42], $arr)) $this->setexternalQuota($arr[$keys[42]]);
        if (array_key_exists($keys[43], $arr)) $this->setlastAccessibleTimelineDate($arr[$keys[43]]);
        if (array_key_exists($keys[44], $arr)) $this->settimelineAccessibleDays($arr[$keys[44]]);
        if (array_key_exists($keys[45], $arr)) $this->settypeTimeLinePerson($arr[$keys[45]]);
        if (array_key_exists($keys[46], $arr)) $this->setmaxOverQueue($arr[$keys[46]]);
        if (array_key_exists($keys[47], $arr)) $this->setmaxCito($arr[$keys[47]]);
        if (array_key_exists($keys[48], $arr)) $this->setquotUnit($arr[$keys[48]]);
        if (array_key_exists($keys[49], $arr)) $this->setacademicDegreeId($arr[$keys[49]]);
        if (array_key_exists($keys[50], $arr)) $this->setacademicTitleId($arr[$keys[50]]);
        if (array_key_exists($keys[51], $arr)) $this->setuuidId($arr[$keys[51]]);
    }

    /**
     * Build a Criteria object containing the values of all modified columns in this object.
     *
     * @return Criteria The Criteria object containing all modified values.
     */
    public function buildCriteria()
    {
        $criteria = new Criteria(PersonPeer::DATABASE_NAME);

        if ($this->isColumnModified(PersonPeer::ID)) $criteria->add(PersonPeer::ID, $this->id);
        if ($this->isColumnModified(PersonPeer::CREATEDATETIME)) $criteria->add(PersonPeer::CREATEDATETIME, $this->createdatetime);
        if ($this->isColumnModified(PersonPeer::CREATEPERSON_ID)) $criteria->add(PersonPeer::CREATEPERSON_ID, $this->createperson_id);
        if ($this->isColumnModified(PersonPeer::MODIFYDATETIME)) $criteria->add(PersonPeer::MODIFYDATETIME, $this->modifydatetime);
        if ($this->isColumnModified(PersonPeer::MODIFYPERSON_ID)) $criteria->add(PersonPeer::MODIFYPERSON_ID, $this->modifyperson_id);
        if ($this->isColumnModified(PersonPeer::DELETED)) $criteria->add(PersonPeer::DELETED, $this->deleted);
        if ($this->isColumnModified(PersonPeer::CODE)) $criteria->add(PersonPeer::CODE, $this->code);
        if ($this->isColumnModified(PersonPeer::FEDERALCODE)) $criteria->add(PersonPeer::FEDERALCODE, $this->federalcode);
        if ($this->isColumnModified(PersonPeer::REGIONALCODE)) $criteria->add(PersonPeer::REGIONALCODE, $this->regionalcode);
        if ($this->isColumnModified(PersonPeer::LASTNAME)) $criteria->add(PersonPeer::LASTNAME, $this->lastname);
        if ($this->isColumnModified(PersonPeer::FIRSTNAME)) $criteria->add(PersonPeer::FIRSTNAME, $this->firstname);
        if ($this->isColumnModified(PersonPeer::PATRNAME)) $criteria->add(PersonPeer::PATRNAME, $this->patrname);
        if ($this->isColumnModified(PersonPeer::POST_ID)) $criteria->add(PersonPeer::POST_ID, $this->post_id);
        if ($this->isColumnModified(PersonPeer::SPECIALITY_ID)) $criteria->add(PersonPeer::SPECIALITY_ID, $this->speciality_id);
        if ($this->isColumnModified(PersonPeer::ORG_ID)) $criteria->add(PersonPeer::ORG_ID, $this->org_id);
        if ($this->isColumnModified(PersonPeer::ORGSTRUCTURE_ID)) $criteria->add(PersonPeer::ORGSTRUCTURE_ID, $this->orgstructure_id);
        if ($this->isColumnModified(PersonPeer::OFFICE)) $criteria->add(PersonPeer::OFFICE, $this->office);
        if ($this->isColumnModified(PersonPeer::OFFICE2)) $criteria->add(PersonPeer::OFFICE2, $this->office2);
        if ($this->isColumnModified(PersonPeer::TARIFFCATEGORY_ID)) $criteria->add(PersonPeer::TARIFFCATEGORY_ID, $this->tariffcategory_id);
        if ($this->isColumnModified(PersonPeer::FINANCE_ID)) $criteria->add(PersonPeer::FINANCE_ID, $this->finance_id);
        if ($this->isColumnModified(PersonPeer::RETIREDATE)) $criteria->add(PersonPeer::RETIREDATE, $this->retiredate);
        if ($this->isColumnModified(PersonPeer::AMBPLAN)) $criteria->add(PersonPeer::AMBPLAN, $this->ambplan);
        if ($this->isColumnModified(PersonPeer::AMBPLAN2)) $criteria->add(PersonPeer::AMBPLAN2, $this->ambplan2);
        if ($this->isColumnModified(PersonPeer::AMBNORM)) $criteria->add(PersonPeer::AMBNORM, $this->ambnorm);
        if ($this->isColumnModified(PersonPeer::HOMPLAN)) $criteria->add(PersonPeer::HOMPLAN, $this->homplan);
        if ($this->isColumnModified(PersonPeer::HOMPLAN2)) $criteria->add(PersonPeer::HOMPLAN2, $this->homplan2);
        if ($this->isColumnModified(PersonPeer::HOMNORM)) $criteria->add(PersonPeer::HOMNORM, $this->homnorm);
        if ($this->isColumnModified(PersonPeer::EXPPLAN)) $criteria->add(PersonPeer::EXPPLAN, $this->expplan);
        if ($this->isColumnModified(PersonPeer::EXPNORM)) $criteria->add(PersonPeer::EXPNORM, $this->expnorm);
        if ($this->isColumnModified(PersonPeer::LOGIN)) $criteria->add(PersonPeer::LOGIN, $this->login);
        if ($this->isColumnModified(PersonPeer::PASSWORD)) $criteria->add(PersonPeer::PASSWORD, $this->password);
        if ($this->isColumnModified(PersonPeer::USERPROFILE_ID)) $criteria->add(PersonPeer::USERPROFILE_ID, $this->userprofile_id);
        if ($this->isColumnModified(PersonPeer::RETIRED)) $criteria->add(PersonPeer::RETIRED, $this->retired);
        if ($this->isColumnModified(PersonPeer::BIRTHDATE)) $criteria->add(PersonPeer::BIRTHDATE, $this->birthdate);
        if ($this->isColumnModified(PersonPeer::BIRTHPLACE)) $criteria->add(PersonPeer::BIRTHPLACE, $this->birthplace);
        if ($this->isColumnModified(PersonPeer::SEX)) $criteria->add(PersonPeer::SEX, $this->sex);
        if ($this->isColumnModified(PersonPeer::SNILS)) $criteria->add(PersonPeer::SNILS, $this->snils);
        if ($this->isColumnModified(PersonPeer::INN)) $criteria->add(PersonPeer::INN, $this->inn);
        if ($this->isColumnModified(PersonPeer::AVAILABLEFOREXTERNAL)) $criteria->add(PersonPeer::AVAILABLEFOREXTERNAL, $this->availableforexternal);
        if ($this->isColumnModified(PersonPeer::PRIMARYQUOTA)) $criteria->add(PersonPeer::PRIMARYQUOTA, $this->primaryquota);
        if ($this->isColumnModified(PersonPeer::OWNQUOTA)) $criteria->add(PersonPeer::OWNQUOTA, $this->ownquota);
        if ($this->isColumnModified(PersonPeer::CONSULTANCYQUOTA)) $criteria->add(PersonPeer::CONSULTANCYQUOTA, $this->consultancyquota);
        if ($this->isColumnModified(PersonPeer::EXTERNALQUOTA)) $criteria->add(PersonPeer::EXTERNALQUOTA, $this->externalquota);
        if ($this->isColumnModified(PersonPeer::LASTACCESSIBLETIMELINEDATE)) $criteria->add(PersonPeer::LASTACCESSIBLETIMELINEDATE, $this->lastaccessibletimelinedate);
        if ($this->isColumnModified(PersonPeer::TIMELINEACCESSIBLEDAYS)) $criteria->add(PersonPeer::TIMELINEACCESSIBLEDAYS, $this->timelineaccessibledays);
        if ($this->isColumnModified(PersonPeer::TYPETIMELINEPERSON)) $criteria->add(PersonPeer::TYPETIMELINEPERSON, $this->typetimelineperson);
        if ($this->isColumnModified(PersonPeer::MAXOVERQUEUE)) $criteria->add(PersonPeer::MAXOVERQUEUE, $this->maxoverqueue);
        if ($this->isColumnModified(PersonPeer::MAXCITO)) $criteria->add(PersonPeer::MAXCITO, $this->maxcito);
        if ($this->isColumnModified(PersonPeer::QUOTUNIT)) $criteria->add(PersonPeer::QUOTUNIT, $this->quotunit);
        if ($this->isColumnModified(PersonPeer::ACADEMICDEGREE_ID)) $criteria->add(PersonPeer::ACADEMICDEGREE_ID, $this->academicdegree_id);
        if ($this->isColumnModified(PersonPeer::ACADEMICTITLE_ID)) $criteria->add(PersonPeer::ACADEMICTITLE_ID, $this->academictitle_id);
        if ($this->isColumnModified(PersonPeer::UUID_ID)) $criteria->add(PersonPeer::UUID_ID, $this->uuid_id);

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
        $criteria = new Criteria(PersonPeer::DATABASE_NAME);
        $criteria->add(PersonPeer::ID, $this->id);

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
     * @param object $copyObj An object of Person (or compatible) type.
     * @param boolean $deepCopy Whether to also copy all rows that refer (by fkey) to the current row.
     * @param boolean $makeNew Whether to reset autoincrement PKs and make the object new.
     * @throws PropelException
     */
    public function copyInto($copyObj, $deepCopy = false, $makeNew = true)
    {
        $copyObj->setcreateDatetime($this->getcreateDatetime());
        $copyObj->setcreatePersonId($this->getcreatePersonId());
        $copyObj->setmodifyDatetime($this->getmodifyDatetime());
        $copyObj->setmodifyPersonId($this->getmodifyPersonId());
        $copyObj->setdeleted($this->getdeleted());
        $copyObj->setcode($this->getcode());
        $copyObj->setfederalCode($this->getfederalCode());
        $copyObj->setregionalCode($this->getregionalCode());
        $copyObj->setlastName($this->getlastName());
        $copyObj->setfirstName($this->getfirstName());
        $copyObj->setpatrName($this->getpatrName());
        $copyObj->setpostId($this->getpostId());
        $copyObj->setspecialityId($this->getspecialityId());
        $copyObj->setorgId($this->getorgId());
        $copyObj->setorgStructureId($this->getorgStructureId());
        $copyObj->setoffice($this->getoffice());
        $copyObj->setoffice2($this->getoffice2());
        $copyObj->settariffCategoryId($this->gettariffCategoryId());
        $copyObj->setfinanceId($this->getfinanceId());
        $copyObj->setretireDate($this->getretireDate());
        $copyObj->setambPlan($this->getambPlan());
        $copyObj->setambPlan2($this->getambPlan2());
        $copyObj->setambNorm($this->getambNorm());
        $copyObj->sethomPlan($this->gethomPlan());
        $copyObj->sethomPlan2($this->gethomPlan2());
        $copyObj->sethomNorm($this->gethomNorm());
        $copyObj->setexpPlan($this->getexpPlan());
        $copyObj->setexpNorm($this->getexpNorm());
        $copyObj->setlogin($this->getlogin());
        $copyObj->setpassword($this->getpassword());
        $copyObj->setuserProfileId($this->getuserProfileId());
        $copyObj->setretired($this->getretired());
        $copyObj->setbirthDate($this->getbirthDate());
        $copyObj->setbirthPlace($this->getbirthPlace());
        $copyObj->setsex($this->getsex());
        $copyObj->setsnils($this->getsnils());
        $copyObj->setinn($this->getinn());
        $copyObj->setavailableForExternal($this->getavailableForExternal());
        $copyObj->setprimaryQuota($this->getprimaryQuota());
        $copyObj->setownQuota($this->getownQuota());
        $copyObj->setconsultancyQuota($this->getconsultancyQuota());
        $copyObj->setexternalQuota($this->getexternalQuota());
        $copyObj->setlastAccessibleTimelineDate($this->getlastAccessibleTimelineDate());
        $copyObj->settimelineAccessibleDays($this->gettimelineAccessibleDays());
        $copyObj->settypeTimeLinePerson($this->gettypeTimeLinePerson());
        $copyObj->setmaxOverQueue($this->getmaxOverQueue());
        $copyObj->setmaxCito($this->getmaxCito());
        $copyObj->setquotUnit($this->getquotUnit());
        $copyObj->setacademicDegreeId($this->getacademicDegreeId());
        $copyObj->setacademicTitleId($this->getacademicTitleId());
        $copyObj->setuuidId($this->getuuidId());

        if ($deepCopy && !$this->startCopy) {
            // important: temporarily setNew(false) because this affects the behavior of
            // the getter/setter methods for fkey referrer objects.
            $copyObj->setNew(false);
            // store object hash to prevent cycle
            $this->startCopy = true;

            foreach ($this->getActionsRelatedBycreatePersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionRelatedBycreatePersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getActionsRelatedBymodifyPersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionRelatedBymodifyPersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getActionsRelatedBysetPersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addActionRelatedBysetPersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getEventsRelatedBycreatePersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addEventRelatedBycreatePersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getEventsRelatedBymodifyPersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addEventRelatedBymodifyPersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getEventsRelatedBysetPersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addEventRelatedBysetPersonId($relObj->copy($deepCopy));
                }
            }

            foreach ($this->getEventsRelatedByexecPersonId() as $relObj) {
                if ($relObj !== $this) {  // ensure that we don't try to copy a reference to ourselves
                    $copyObj->addEventRelatedByexecPersonId($relObj->copy($deepCopy));
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
     * @return Person Clone of current object.
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
     * @return PersonPeer
     */
    public function getPeer()
    {
        if (self::$peer === null) {
            self::$peer = new PersonPeer();
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
        if ('ActionRelatedBycreatePersonId' == $relationName) {
            $this->initActionsRelatedBycreatePersonId();
        }
        if ('ActionRelatedBymodifyPersonId' == $relationName) {
            $this->initActionsRelatedBymodifyPersonId();
        }
        if ('ActionRelatedBysetPersonId' == $relationName) {
            $this->initActionsRelatedBysetPersonId();
        }
        if ('EventRelatedBycreatePersonId' == $relationName) {
            $this->initEventsRelatedBycreatePersonId();
        }
        if ('EventRelatedBymodifyPersonId' == $relationName) {
            $this->initEventsRelatedBymodifyPersonId();
        }
        if ('EventRelatedBysetPersonId' == $relationName) {
            $this->initEventsRelatedBysetPersonId();
        }
        if ('EventRelatedByexecPersonId' == $relationName) {
            $this->initEventsRelatedByexecPersonId();
        }
    }

    /**
     * Clears out the collActionsRelatedBycreatePersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addActionsRelatedBycreatePersonId()
     */
    public function clearActionsRelatedBycreatePersonId()
    {
        $this->collActionsRelatedBycreatePersonId = null; // important to set this to null since that means it is uninitialized
        $this->collActionsRelatedBycreatePersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collActionsRelatedBycreatePersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionsRelatedBycreatePersonId($v = true)
    {
        $this->collActionsRelatedBycreatePersonIdPartial = $v;
    }

    /**
     * Initializes the collActionsRelatedBycreatePersonId collection.
     *
     * By default this just sets the collActionsRelatedBycreatePersonId collection to an empty array (like clearcollActionsRelatedBycreatePersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionsRelatedBycreatePersonId($overrideExisting = true)
    {
        if (null !== $this->collActionsRelatedBycreatePersonId && !$overrideExisting) {
            return;
        }
        $this->collActionsRelatedBycreatePersonId = new PropelObjectCollection();
        $this->collActionsRelatedBycreatePersonId->setModel('Action');
    }

    /**
     * Gets an array of Action objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Action[] List of Action objects
     * @throws PropelException
     */
    public function getActionsRelatedBycreatePersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBycreatePersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBycreatePersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBycreatePersonId) {
                // return empty collection
                $this->initActionsRelatedBycreatePersonId();
            } else {
                $collActionsRelatedBycreatePersonId = ActionQuery::create(null, $criteria)
                    ->filterByCreatePerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionsRelatedBycreatePersonIdPartial && count($collActionsRelatedBycreatePersonId)) {
                      $this->initActionsRelatedBycreatePersonId(false);

                      foreach($collActionsRelatedBycreatePersonId as $obj) {
                        if (false == $this->collActionsRelatedBycreatePersonId->contains($obj)) {
                          $this->collActionsRelatedBycreatePersonId->append($obj);
                        }
                      }

                      $this->collActionsRelatedBycreatePersonIdPartial = true;
                    }

                    $collActionsRelatedBycreatePersonId->getInternalIterator()->rewind();
                    return $collActionsRelatedBycreatePersonId;
                }

                if($partial && $this->collActionsRelatedBycreatePersonId) {
                    foreach($this->collActionsRelatedBycreatePersonId as $obj) {
                        if($obj->isNew()) {
                            $collActionsRelatedBycreatePersonId[] = $obj;
                        }
                    }
                }

                $this->collActionsRelatedBycreatePersonId = $collActionsRelatedBycreatePersonId;
                $this->collActionsRelatedBycreatePersonIdPartial = false;
            }
        }

        return $this->collActionsRelatedBycreatePersonId;
    }

    /**
     * Sets a collection of ActionRelatedBycreatePersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionsRelatedBycreatePersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setActionsRelatedBycreatePersonId(PropelCollection $actionsRelatedBycreatePersonId, PropelPDO $con = null)
    {
        $actionsRelatedBycreatePersonIdToDelete = $this->getActionsRelatedBycreatePersonId(new Criteria(), $con)->diff($actionsRelatedBycreatePersonId);

        $this->actionsRelatedBycreatePersonIdScheduledForDeletion = unserialize(serialize($actionsRelatedBycreatePersonIdToDelete));

        foreach ($actionsRelatedBycreatePersonIdToDelete as $actionRelatedBycreatePersonIdRemoved) {
            $actionRelatedBycreatePersonIdRemoved->setCreatePerson(null);
        }

        $this->collActionsRelatedBycreatePersonId = null;
        foreach ($actionsRelatedBycreatePersonId as $actionRelatedBycreatePersonId) {
            $this->addActionRelatedBycreatePersonId($actionRelatedBycreatePersonId);
        }

        $this->collActionsRelatedBycreatePersonId = $actionsRelatedBycreatePersonId;
        $this->collActionsRelatedBycreatePersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Action objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Action objects.
     * @throws PropelException
     */
    public function countActionsRelatedBycreatePersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBycreatePersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBycreatePersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBycreatePersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionsRelatedBycreatePersonId());
            }
            $query = ActionQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByCreatePerson($this)
                ->count($con);
        }

        return count($this->collActionsRelatedBycreatePersonId);
    }

    /**
     * Method called to associate a Action object to this object
     * through the Action foreign key attribute.
     *
     * @param    Action $l Action
     * @return Person The current object (for fluent API support)
     */
    public function addActionRelatedBycreatePersonId(Action $l)
    {
        if ($this->collActionsRelatedBycreatePersonId === null) {
            $this->initActionsRelatedBycreatePersonId();
            $this->collActionsRelatedBycreatePersonIdPartial = true;
        }
        if (!in_array($l, $this->collActionsRelatedBycreatePersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionRelatedBycreatePersonId($l);
        }

        return $this;
    }

    /**
     * @param	ActionRelatedBycreatePersonId $actionRelatedBycreatePersonId The actionRelatedBycreatePersonId object to add.
     */
    protected function doAddActionRelatedBycreatePersonId($actionRelatedBycreatePersonId)
    {
        $this->collActionsRelatedBycreatePersonId[]= $actionRelatedBycreatePersonId;
        $actionRelatedBycreatePersonId->setCreatePerson($this);
    }

    /**
     * @param	ActionRelatedBycreatePersonId $actionRelatedBycreatePersonId The actionRelatedBycreatePersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeActionRelatedBycreatePersonId($actionRelatedBycreatePersonId)
    {
        if ($this->getActionsRelatedBycreatePersonId()->contains($actionRelatedBycreatePersonId)) {
            $this->collActionsRelatedBycreatePersonId->remove($this->collActionsRelatedBycreatePersonId->search($actionRelatedBycreatePersonId));
            if (null === $this->actionsRelatedBycreatePersonIdScheduledForDeletion) {
                $this->actionsRelatedBycreatePersonIdScheduledForDeletion = clone $this->collActionsRelatedBycreatePersonId;
                $this->actionsRelatedBycreatePersonIdScheduledForDeletion->clear();
            }
            $this->actionsRelatedBycreatePersonIdScheduledForDeletion[]= $actionRelatedBycreatePersonId;
            $actionRelatedBycreatePersonId->setCreatePerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBycreatePersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBycreatePersonIdJoinEvent($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('Event', $join_behavior);

        return $this->getActionsRelatedBycreatePersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBycreatePersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBycreatePersonIdJoinActionType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('ActionType', $join_behavior);

        return $this->getActionsRelatedBycreatePersonId($query, $con);
    }

    /**
     * Clears out the collActionsRelatedBymodifyPersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addActionsRelatedBymodifyPersonId()
     */
    public function clearActionsRelatedBymodifyPersonId()
    {
        $this->collActionsRelatedBymodifyPersonId = null; // important to set this to null since that means it is uninitialized
        $this->collActionsRelatedBymodifyPersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collActionsRelatedBymodifyPersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionsRelatedBymodifyPersonId($v = true)
    {
        $this->collActionsRelatedBymodifyPersonIdPartial = $v;
    }

    /**
     * Initializes the collActionsRelatedBymodifyPersonId collection.
     *
     * By default this just sets the collActionsRelatedBymodifyPersonId collection to an empty array (like clearcollActionsRelatedBymodifyPersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionsRelatedBymodifyPersonId($overrideExisting = true)
    {
        if (null !== $this->collActionsRelatedBymodifyPersonId && !$overrideExisting) {
            return;
        }
        $this->collActionsRelatedBymodifyPersonId = new PropelObjectCollection();
        $this->collActionsRelatedBymodifyPersonId->setModel('Action');
    }

    /**
     * Gets an array of Action objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Action[] List of Action objects
     * @throws PropelException
     */
    public function getActionsRelatedBymodifyPersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBymodifyPersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBymodifyPersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBymodifyPersonId) {
                // return empty collection
                $this->initActionsRelatedBymodifyPersonId();
            } else {
                $collActionsRelatedBymodifyPersonId = ActionQuery::create(null, $criteria)
                    ->filterByModifyPerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionsRelatedBymodifyPersonIdPartial && count($collActionsRelatedBymodifyPersonId)) {
                      $this->initActionsRelatedBymodifyPersonId(false);

                      foreach($collActionsRelatedBymodifyPersonId as $obj) {
                        if (false == $this->collActionsRelatedBymodifyPersonId->contains($obj)) {
                          $this->collActionsRelatedBymodifyPersonId->append($obj);
                        }
                      }

                      $this->collActionsRelatedBymodifyPersonIdPartial = true;
                    }

                    $collActionsRelatedBymodifyPersonId->getInternalIterator()->rewind();
                    return $collActionsRelatedBymodifyPersonId;
                }

                if($partial && $this->collActionsRelatedBymodifyPersonId) {
                    foreach($this->collActionsRelatedBymodifyPersonId as $obj) {
                        if($obj->isNew()) {
                            $collActionsRelatedBymodifyPersonId[] = $obj;
                        }
                    }
                }

                $this->collActionsRelatedBymodifyPersonId = $collActionsRelatedBymodifyPersonId;
                $this->collActionsRelatedBymodifyPersonIdPartial = false;
            }
        }

        return $this->collActionsRelatedBymodifyPersonId;
    }

    /**
     * Sets a collection of ActionRelatedBymodifyPersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionsRelatedBymodifyPersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setActionsRelatedBymodifyPersonId(PropelCollection $actionsRelatedBymodifyPersonId, PropelPDO $con = null)
    {
        $actionsRelatedBymodifyPersonIdToDelete = $this->getActionsRelatedBymodifyPersonId(new Criteria(), $con)->diff($actionsRelatedBymodifyPersonId);

        $this->actionsRelatedBymodifyPersonIdScheduledForDeletion = unserialize(serialize($actionsRelatedBymodifyPersonIdToDelete));

        foreach ($actionsRelatedBymodifyPersonIdToDelete as $actionRelatedBymodifyPersonIdRemoved) {
            $actionRelatedBymodifyPersonIdRemoved->setModifyPerson(null);
        }

        $this->collActionsRelatedBymodifyPersonId = null;
        foreach ($actionsRelatedBymodifyPersonId as $actionRelatedBymodifyPersonId) {
            $this->addActionRelatedBymodifyPersonId($actionRelatedBymodifyPersonId);
        }

        $this->collActionsRelatedBymodifyPersonId = $actionsRelatedBymodifyPersonId;
        $this->collActionsRelatedBymodifyPersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Action objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Action objects.
     * @throws PropelException
     */
    public function countActionsRelatedBymodifyPersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBymodifyPersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBymodifyPersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBymodifyPersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionsRelatedBymodifyPersonId());
            }
            $query = ActionQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByModifyPerson($this)
                ->count($con);
        }

        return count($this->collActionsRelatedBymodifyPersonId);
    }

    /**
     * Method called to associate a Action object to this object
     * through the Action foreign key attribute.
     *
     * @param    Action $l Action
     * @return Person The current object (for fluent API support)
     */
    public function addActionRelatedBymodifyPersonId(Action $l)
    {
        if ($this->collActionsRelatedBymodifyPersonId === null) {
            $this->initActionsRelatedBymodifyPersonId();
            $this->collActionsRelatedBymodifyPersonIdPartial = true;
        }
        if (!in_array($l, $this->collActionsRelatedBymodifyPersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionRelatedBymodifyPersonId($l);
        }

        return $this;
    }

    /**
     * @param	ActionRelatedBymodifyPersonId $actionRelatedBymodifyPersonId The actionRelatedBymodifyPersonId object to add.
     */
    protected function doAddActionRelatedBymodifyPersonId($actionRelatedBymodifyPersonId)
    {
        $this->collActionsRelatedBymodifyPersonId[]= $actionRelatedBymodifyPersonId;
        $actionRelatedBymodifyPersonId->setModifyPerson($this);
    }

    /**
     * @param	ActionRelatedBymodifyPersonId $actionRelatedBymodifyPersonId The actionRelatedBymodifyPersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeActionRelatedBymodifyPersonId($actionRelatedBymodifyPersonId)
    {
        if ($this->getActionsRelatedBymodifyPersonId()->contains($actionRelatedBymodifyPersonId)) {
            $this->collActionsRelatedBymodifyPersonId->remove($this->collActionsRelatedBymodifyPersonId->search($actionRelatedBymodifyPersonId));
            if (null === $this->actionsRelatedBymodifyPersonIdScheduledForDeletion) {
                $this->actionsRelatedBymodifyPersonIdScheduledForDeletion = clone $this->collActionsRelatedBymodifyPersonId;
                $this->actionsRelatedBymodifyPersonIdScheduledForDeletion->clear();
            }
            $this->actionsRelatedBymodifyPersonIdScheduledForDeletion[]= $actionRelatedBymodifyPersonId;
            $actionRelatedBymodifyPersonId->setModifyPerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBymodifyPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBymodifyPersonIdJoinEvent($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('Event', $join_behavior);

        return $this->getActionsRelatedBymodifyPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBymodifyPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBymodifyPersonIdJoinActionType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('ActionType', $join_behavior);

        return $this->getActionsRelatedBymodifyPersonId($query, $con);
    }

    /**
     * Clears out the collActionsRelatedBysetPersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addActionsRelatedBysetPersonId()
     */
    public function clearActionsRelatedBysetPersonId()
    {
        $this->collActionsRelatedBysetPersonId = null; // important to set this to null since that means it is uninitialized
        $this->collActionsRelatedBysetPersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collActionsRelatedBysetPersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialActionsRelatedBysetPersonId($v = true)
    {
        $this->collActionsRelatedBysetPersonIdPartial = $v;
    }

    /**
     * Initializes the collActionsRelatedBysetPersonId collection.
     *
     * By default this just sets the collActionsRelatedBysetPersonId collection to an empty array (like clearcollActionsRelatedBysetPersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initActionsRelatedBysetPersonId($overrideExisting = true)
    {
        if (null !== $this->collActionsRelatedBysetPersonId && !$overrideExisting) {
            return;
        }
        $this->collActionsRelatedBysetPersonId = new PropelObjectCollection();
        $this->collActionsRelatedBysetPersonId->setModel('Action');
    }

    /**
     * Gets an array of Action objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Action[] List of Action objects
     * @throws PropelException
     */
    public function getActionsRelatedBysetPersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBysetPersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBysetPersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBysetPersonId) {
                // return empty collection
                $this->initActionsRelatedBysetPersonId();
            } else {
                $collActionsRelatedBysetPersonId = ActionQuery::create(null, $criteria)
                    ->filterBySetPerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collActionsRelatedBysetPersonIdPartial && count($collActionsRelatedBysetPersonId)) {
                      $this->initActionsRelatedBysetPersonId(false);

                      foreach($collActionsRelatedBysetPersonId as $obj) {
                        if (false == $this->collActionsRelatedBysetPersonId->contains($obj)) {
                          $this->collActionsRelatedBysetPersonId->append($obj);
                        }
                      }

                      $this->collActionsRelatedBysetPersonIdPartial = true;
                    }

                    $collActionsRelatedBysetPersonId->getInternalIterator()->rewind();
                    return $collActionsRelatedBysetPersonId;
                }

                if($partial && $this->collActionsRelatedBysetPersonId) {
                    foreach($this->collActionsRelatedBysetPersonId as $obj) {
                        if($obj->isNew()) {
                            $collActionsRelatedBysetPersonId[] = $obj;
                        }
                    }
                }

                $this->collActionsRelatedBysetPersonId = $collActionsRelatedBysetPersonId;
                $this->collActionsRelatedBysetPersonIdPartial = false;
            }
        }

        return $this->collActionsRelatedBysetPersonId;
    }

    /**
     * Sets a collection of ActionRelatedBysetPersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $actionsRelatedBysetPersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setActionsRelatedBysetPersonId(PropelCollection $actionsRelatedBysetPersonId, PropelPDO $con = null)
    {
        $actionsRelatedBysetPersonIdToDelete = $this->getActionsRelatedBysetPersonId(new Criteria(), $con)->diff($actionsRelatedBysetPersonId);

        $this->actionsRelatedBysetPersonIdScheduledForDeletion = unserialize(serialize($actionsRelatedBysetPersonIdToDelete));

        foreach ($actionsRelatedBysetPersonIdToDelete as $actionRelatedBysetPersonIdRemoved) {
            $actionRelatedBysetPersonIdRemoved->setSetPerson(null);
        }

        $this->collActionsRelatedBysetPersonId = null;
        foreach ($actionsRelatedBysetPersonId as $actionRelatedBysetPersonId) {
            $this->addActionRelatedBysetPersonId($actionRelatedBysetPersonId);
        }

        $this->collActionsRelatedBysetPersonId = $actionsRelatedBysetPersonId;
        $this->collActionsRelatedBysetPersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Action objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Action objects.
     * @throws PropelException
     */
    public function countActionsRelatedBysetPersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collActionsRelatedBysetPersonIdPartial && !$this->isNew();
        if (null === $this->collActionsRelatedBysetPersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collActionsRelatedBysetPersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getActionsRelatedBysetPersonId());
            }
            $query = ActionQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterBySetPerson($this)
                ->count($con);
        }

        return count($this->collActionsRelatedBysetPersonId);
    }

    /**
     * Method called to associate a Action object to this object
     * through the Action foreign key attribute.
     *
     * @param    Action $l Action
     * @return Person The current object (for fluent API support)
     */
    public function addActionRelatedBysetPersonId(Action $l)
    {
        if ($this->collActionsRelatedBysetPersonId === null) {
            $this->initActionsRelatedBysetPersonId();
            $this->collActionsRelatedBysetPersonIdPartial = true;
        }
        if (!in_array($l, $this->collActionsRelatedBysetPersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddActionRelatedBysetPersonId($l);
        }

        return $this;
    }

    /**
     * @param	ActionRelatedBysetPersonId $actionRelatedBysetPersonId The actionRelatedBysetPersonId object to add.
     */
    protected function doAddActionRelatedBysetPersonId($actionRelatedBysetPersonId)
    {
        $this->collActionsRelatedBysetPersonId[]= $actionRelatedBysetPersonId;
        $actionRelatedBysetPersonId->setSetPerson($this);
    }

    /**
     * @param	ActionRelatedBysetPersonId $actionRelatedBysetPersonId The actionRelatedBysetPersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeActionRelatedBysetPersonId($actionRelatedBysetPersonId)
    {
        if ($this->getActionsRelatedBysetPersonId()->contains($actionRelatedBysetPersonId)) {
            $this->collActionsRelatedBysetPersonId->remove($this->collActionsRelatedBysetPersonId->search($actionRelatedBysetPersonId));
            if (null === $this->actionsRelatedBysetPersonIdScheduledForDeletion) {
                $this->actionsRelatedBysetPersonIdScheduledForDeletion = clone $this->collActionsRelatedBysetPersonId;
                $this->actionsRelatedBysetPersonIdScheduledForDeletion->clear();
            }
            $this->actionsRelatedBysetPersonIdScheduledForDeletion[]= $actionRelatedBysetPersonId;
            $actionRelatedBysetPersonId->setSetPerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBysetPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBysetPersonIdJoinEvent($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('Event', $join_behavior);

        return $this->getActionsRelatedBysetPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related ActionsRelatedBysetPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Action[] List of Action objects
     */
    public function getActionsRelatedBysetPersonIdJoinActionType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = ActionQuery::create(null, $criteria);
        $query->joinWith('ActionType', $join_behavior);

        return $this->getActionsRelatedBysetPersonId($query, $con);
    }

    /**
     * Clears out the collEventsRelatedBycreatePersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addEventsRelatedBycreatePersonId()
     */
    public function clearEventsRelatedBycreatePersonId()
    {
        $this->collEventsRelatedBycreatePersonId = null; // important to set this to null since that means it is uninitialized
        $this->collEventsRelatedBycreatePersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collEventsRelatedBycreatePersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialEventsRelatedBycreatePersonId($v = true)
    {
        $this->collEventsRelatedBycreatePersonIdPartial = $v;
    }

    /**
     * Initializes the collEventsRelatedBycreatePersonId collection.
     *
     * By default this just sets the collEventsRelatedBycreatePersonId collection to an empty array (like clearcollEventsRelatedBycreatePersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initEventsRelatedBycreatePersonId($overrideExisting = true)
    {
        if (null !== $this->collEventsRelatedBycreatePersonId && !$overrideExisting) {
            return;
        }
        $this->collEventsRelatedBycreatePersonId = new PropelObjectCollection();
        $this->collEventsRelatedBycreatePersonId->setModel('Event');
    }

    /**
     * Gets an array of Event objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Event[] List of Event objects
     * @throws PropelException
     */
    public function getEventsRelatedBycreatePersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBycreatePersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBycreatePersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBycreatePersonId) {
                // return empty collection
                $this->initEventsRelatedBycreatePersonId();
            } else {
                $collEventsRelatedBycreatePersonId = EventQuery::create(null, $criteria)
                    ->filterByCreatePerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collEventsRelatedBycreatePersonIdPartial && count($collEventsRelatedBycreatePersonId)) {
                      $this->initEventsRelatedBycreatePersonId(false);

                      foreach($collEventsRelatedBycreatePersonId as $obj) {
                        if (false == $this->collEventsRelatedBycreatePersonId->contains($obj)) {
                          $this->collEventsRelatedBycreatePersonId->append($obj);
                        }
                      }

                      $this->collEventsRelatedBycreatePersonIdPartial = true;
                    }

                    $collEventsRelatedBycreatePersonId->getInternalIterator()->rewind();
                    return $collEventsRelatedBycreatePersonId;
                }

                if($partial && $this->collEventsRelatedBycreatePersonId) {
                    foreach($this->collEventsRelatedBycreatePersonId as $obj) {
                        if($obj->isNew()) {
                            $collEventsRelatedBycreatePersonId[] = $obj;
                        }
                    }
                }

                $this->collEventsRelatedBycreatePersonId = $collEventsRelatedBycreatePersonId;
                $this->collEventsRelatedBycreatePersonIdPartial = false;
            }
        }

        return $this->collEventsRelatedBycreatePersonId;
    }

    /**
     * Sets a collection of EventRelatedBycreatePersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $eventsRelatedBycreatePersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setEventsRelatedBycreatePersonId(PropelCollection $eventsRelatedBycreatePersonId, PropelPDO $con = null)
    {
        $eventsRelatedBycreatePersonIdToDelete = $this->getEventsRelatedBycreatePersonId(new Criteria(), $con)->diff($eventsRelatedBycreatePersonId);

        $this->eventsRelatedBycreatePersonIdScheduledForDeletion = unserialize(serialize($eventsRelatedBycreatePersonIdToDelete));

        foreach ($eventsRelatedBycreatePersonIdToDelete as $eventRelatedBycreatePersonIdRemoved) {
            $eventRelatedBycreatePersonIdRemoved->setCreatePerson(null);
        }

        $this->collEventsRelatedBycreatePersonId = null;
        foreach ($eventsRelatedBycreatePersonId as $eventRelatedBycreatePersonId) {
            $this->addEventRelatedBycreatePersonId($eventRelatedBycreatePersonId);
        }

        $this->collEventsRelatedBycreatePersonId = $eventsRelatedBycreatePersonId;
        $this->collEventsRelatedBycreatePersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Event objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Event objects.
     * @throws PropelException
     */
    public function countEventsRelatedBycreatePersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBycreatePersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBycreatePersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBycreatePersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getEventsRelatedBycreatePersonId());
            }
            $query = EventQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByCreatePerson($this)
                ->count($con);
        }

        return count($this->collEventsRelatedBycreatePersonId);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return Person The current object (for fluent API support)
     */
    public function addEventRelatedBycreatePersonId(Event $l)
    {
        if ($this->collEventsRelatedBycreatePersonId === null) {
            $this->initEventsRelatedBycreatePersonId();
            $this->collEventsRelatedBycreatePersonIdPartial = true;
        }
        if (!in_array($l, $this->collEventsRelatedBycreatePersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddEventRelatedBycreatePersonId($l);
        }

        return $this;
    }

    /**
     * @param	EventRelatedBycreatePersonId $eventRelatedBycreatePersonId The eventRelatedBycreatePersonId object to add.
     */
    protected function doAddEventRelatedBycreatePersonId($eventRelatedBycreatePersonId)
    {
        $this->collEventsRelatedBycreatePersonId[]= $eventRelatedBycreatePersonId;
        $eventRelatedBycreatePersonId->setCreatePerson($this);
    }

    /**
     * @param	EventRelatedBycreatePersonId $eventRelatedBycreatePersonId The eventRelatedBycreatePersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeEventRelatedBycreatePersonId($eventRelatedBycreatePersonId)
    {
        if ($this->getEventsRelatedBycreatePersonId()->contains($eventRelatedBycreatePersonId)) {
            $this->collEventsRelatedBycreatePersonId->remove($this->collEventsRelatedBycreatePersonId->search($eventRelatedBycreatePersonId));
            if (null === $this->eventsRelatedBycreatePersonIdScheduledForDeletion) {
                $this->eventsRelatedBycreatePersonIdScheduledForDeletion = clone $this->collEventsRelatedBycreatePersonId;
                $this->eventsRelatedBycreatePersonIdScheduledForDeletion->clear();
            }
            $this->eventsRelatedBycreatePersonIdScheduledForDeletion[]= $eventRelatedBycreatePersonId;
            $eventRelatedBycreatePersonId->setCreatePerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBycreatePersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBycreatePersonIdJoinEventType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('EventType', $join_behavior);

        return $this->getEventsRelatedBycreatePersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBycreatePersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBycreatePersonIdJoinClient($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Client', $join_behavior);

        return $this->getEventsRelatedBycreatePersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBycreatePersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBycreatePersonIdJoinOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('OrgStructure', $join_behavior);

        return $this->getEventsRelatedBycreatePersonId($query, $con);
    }

    /**
     * Clears out the collEventsRelatedBymodifyPersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addEventsRelatedBymodifyPersonId()
     */
    public function clearEventsRelatedBymodifyPersonId()
    {
        $this->collEventsRelatedBymodifyPersonId = null; // important to set this to null since that means it is uninitialized
        $this->collEventsRelatedBymodifyPersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collEventsRelatedBymodifyPersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialEventsRelatedBymodifyPersonId($v = true)
    {
        $this->collEventsRelatedBymodifyPersonIdPartial = $v;
    }

    /**
     * Initializes the collEventsRelatedBymodifyPersonId collection.
     *
     * By default this just sets the collEventsRelatedBymodifyPersonId collection to an empty array (like clearcollEventsRelatedBymodifyPersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initEventsRelatedBymodifyPersonId($overrideExisting = true)
    {
        if (null !== $this->collEventsRelatedBymodifyPersonId && !$overrideExisting) {
            return;
        }
        $this->collEventsRelatedBymodifyPersonId = new PropelObjectCollection();
        $this->collEventsRelatedBymodifyPersonId->setModel('Event');
    }

    /**
     * Gets an array of Event objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Event[] List of Event objects
     * @throws PropelException
     */
    public function getEventsRelatedBymodifyPersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBymodifyPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBymodifyPersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBymodifyPersonId) {
                // return empty collection
                $this->initEventsRelatedBymodifyPersonId();
            } else {
                $collEventsRelatedBymodifyPersonId = EventQuery::create(null, $criteria)
                    ->filterByModifyPerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collEventsRelatedBymodifyPersonIdPartial && count($collEventsRelatedBymodifyPersonId)) {
                      $this->initEventsRelatedBymodifyPersonId(false);

                      foreach($collEventsRelatedBymodifyPersonId as $obj) {
                        if (false == $this->collEventsRelatedBymodifyPersonId->contains($obj)) {
                          $this->collEventsRelatedBymodifyPersonId->append($obj);
                        }
                      }

                      $this->collEventsRelatedBymodifyPersonIdPartial = true;
                    }

                    $collEventsRelatedBymodifyPersonId->getInternalIterator()->rewind();
                    return $collEventsRelatedBymodifyPersonId;
                }

                if($partial && $this->collEventsRelatedBymodifyPersonId) {
                    foreach($this->collEventsRelatedBymodifyPersonId as $obj) {
                        if($obj->isNew()) {
                            $collEventsRelatedBymodifyPersonId[] = $obj;
                        }
                    }
                }

                $this->collEventsRelatedBymodifyPersonId = $collEventsRelatedBymodifyPersonId;
                $this->collEventsRelatedBymodifyPersonIdPartial = false;
            }
        }

        return $this->collEventsRelatedBymodifyPersonId;
    }

    /**
     * Sets a collection of EventRelatedBymodifyPersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $eventsRelatedBymodifyPersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setEventsRelatedBymodifyPersonId(PropelCollection $eventsRelatedBymodifyPersonId, PropelPDO $con = null)
    {
        $eventsRelatedBymodifyPersonIdToDelete = $this->getEventsRelatedBymodifyPersonId(new Criteria(), $con)->diff($eventsRelatedBymodifyPersonId);

        $this->eventsRelatedBymodifyPersonIdScheduledForDeletion = unserialize(serialize($eventsRelatedBymodifyPersonIdToDelete));

        foreach ($eventsRelatedBymodifyPersonIdToDelete as $eventRelatedBymodifyPersonIdRemoved) {
            $eventRelatedBymodifyPersonIdRemoved->setModifyPerson(null);
        }

        $this->collEventsRelatedBymodifyPersonId = null;
        foreach ($eventsRelatedBymodifyPersonId as $eventRelatedBymodifyPersonId) {
            $this->addEventRelatedBymodifyPersonId($eventRelatedBymodifyPersonId);
        }

        $this->collEventsRelatedBymodifyPersonId = $eventsRelatedBymodifyPersonId;
        $this->collEventsRelatedBymodifyPersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Event objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Event objects.
     * @throws PropelException
     */
    public function countEventsRelatedBymodifyPersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBymodifyPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBymodifyPersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBymodifyPersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getEventsRelatedBymodifyPersonId());
            }
            $query = EventQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByModifyPerson($this)
                ->count($con);
        }

        return count($this->collEventsRelatedBymodifyPersonId);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return Person The current object (for fluent API support)
     */
    public function addEventRelatedBymodifyPersonId(Event $l)
    {
        if ($this->collEventsRelatedBymodifyPersonId === null) {
            $this->initEventsRelatedBymodifyPersonId();
            $this->collEventsRelatedBymodifyPersonIdPartial = true;
        }
        if (!in_array($l, $this->collEventsRelatedBymodifyPersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddEventRelatedBymodifyPersonId($l);
        }

        return $this;
    }

    /**
     * @param	EventRelatedBymodifyPersonId $eventRelatedBymodifyPersonId The eventRelatedBymodifyPersonId object to add.
     */
    protected function doAddEventRelatedBymodifyPersonId($eventRelatedBymodifyPersonId)
    {
        $this->collEventsRelatedBymodifyPersonId[]= $eventRelatedBymodifyPersonId;
        $eventRelatedBymodifyPersonId->setModifyPerson($this);
    }

    /**
     * @param	EventRelatedBymodifyPersonId $eventRelatedBymodifyPersonId The eventRelatedBymodifyPersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeEventRelatedBymodifyPersonId($eventRelatedBymodifyPersonId)
    {
        if ($this->getEventsRelatedBymodifyPersonId()->contains($eventRelatedBymodifyPersonId)) {
            $this->collEventsRelatedBymodifyPersonId->remove($this->collEventsRelatedBymodifyPersonId->search($eventRelatedBymodifyPersonId));
            if (null === $this->eventsRelatedBymodifyPersonIdScheduledForDeletion) {
                $this->eventsRelatedBymodifyPersonIdScheduledForDeletion = clone $this->collEventsRelatedBymodifyPersonId;
                $this->eventsRelatedBymodifyPersonIdScheduledForDeletion->clear();
            }
            $this->eventsRelatedBymodifyPersonIdScheduledForDeletion[]= $eventRelatedBymodifyPersonId;
            $eventRelatedBymodifyPersonId->setModifyPerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBymodifyPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBymodifyPersonIdJoinEventType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('EventType', $join_behavior);

        return $this->getEventsRelatedBymodifyPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBymodifyPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBymodifyPersonIdJoinClient($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Client', $join_behavior);

        return $this->getEventsRelatedBymodifyPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBymodifyPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBymodifyPersonIdJoinOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('OrgStructure', $join_behavior);

        return $this->getEventsRelatedBymodifyPersonId($query, $con);
    }

    /**
     * Clears out the collEventsRelatedBysetPersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addEventsRelatedBysetPersonId()
     */
    public function clearEventsRelatedBysetPersonId()
    {
        $this->collEventsRelatedBysetPersonId = null; // important to set this to null since that means it is uninitialized
        $this->collEventsRelatedBysetPersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collEventsRelatedBysetPersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialEventsRelatedBysetPersonId($v = true)
    {
        $this->collEventsRelatedBysetPersonIdPartial = $v;
    }

    /**
     * Initializes the collEventsRelatedBysetPersonId collection.
     *
     * By default this just sets the collEventsRelatedBysetPersonId collection to an empty array (like clearcollEventsRelatedBysetPersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initEventsRelatedBysetPersonId($overrideExisting = true)
    {
        if (null !== $this->collEventsRelatedBysetPersonId && !$overrideExisting) {
            return;
        }
        $this->collEventsRelatedBysetPersonId = new PropelObjectCollection();
        $this->collEventsRelatedBysetPersonId->setModel('Event');
    }

    /**
     * Gets an array of Event objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Event[] List of Event objects
     * @throws PropelException
     */
    public function getEventsRelatedBysetPersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBysetPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBysetPersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBysetPersonId) {
                // return empty collection
                $this->initEventsRelatedBysetPersonId();
            } else {
                $collEventsRelatedBysetPersonId = EventQuery::create(null, $criteria)
                    ->filterBySetPerson($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collEventsRelatedBysetPersonIdPartial && count($collEventsRelatedBysetPersonId)) {
                      $this->initEventsRelatedBysetPersonId(false);

                      foreach($collEventsRelatedBysetPersonId as $obj) {
                        if (false == $this->collEventsRelatedBysetPersonId->contains($obj)) {
                          $this->collEventsRelatedBysetPersonId->append($obj);
                        }
                      }

                      $this->collEventsRelatedBysetPersonIdPartial = true;
                    }

                    $collEventsRelatedBysetPersonId->getInternalIterator()->rewind();
                    return $collEventsRelatedBysetPersonId;
                }

                if($partial && $this->collEventsRelatedBysetPersonId) {
                    foreach($this->collEventsRelatedBysetPersonId as $obj) {
                        if($obj->isNew()) {
                            $collEventsRelatedBysetPersonId[] = $obj;
                        }
                    }
                }

                $this->collEventsRelatedBysetPersonId = $collEventsRelatedBysetPersonId;
                $this->collEventsRelatedBysetPersonIdPartial = false;
            }
        }

        return $this->collEventsRelatedBysetPersonId;
    }

    /**
     * Sets a collection of EventRelatedBysetPersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $eventsRelatedBysetPersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setEventsRelatedBysetPersonId(PropelCollection $eventsRelatedBysetPersonId, PropelPDO $con = null)
    {
        $eventsRelatedBysetPersonIdToDelete = $this->getEventsRelatedBysetPersonId(new Criteria(), $con)->diff($eventsRelatedBysetPersonId);

        $this->eventsRelatedBysetPersonIdScheduledForDeletion = unserialize(serialize($eventsRelatedBysetPersonIdToDelete));

        foreach ($eventsRelatedBysetPersonIdToDelete as $eventRelatedBysetPersonIdRemoved) {
            $eventRelatedBysetPersonIdRemoved->setSetPerson(null);
        }

        $this->collEventsRelatedBysetPersonId = null;
        foreach ($eventsRelatedBysetPersonId as $eventRelatedBysetPersonId) {
            $this->addEventRelatedBysetPersonId($eventRelatedBysetPersonId);
        }

        $this->collEventsRelatedBysetPersonId = $eventsRelatedBysetPersonId;
        $this->collEventsRelatedBysetPersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Event objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Event objects.
     * @throws PropelException
     */
    public function countEventsRelatedBysetPersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedBysetPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedBysetPersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedBysetPersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getEventsRelatedBysetPersonId());
            }
            $query = EventQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterBySetPerson($this)
                ->count($con);
        }

        return count($this->collEventsRelatedBysetPersonId);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return Person The current object (for fluent API support)
     */
    public function addEventRelatedBysetPersonId(Event $l)
    {
        if ($this->collEventsRelatedBysetPersonId === null) {
            $this->initEventsRelatedBysetPersonId();
            $this->collEventsRelatedBysetPersonIdPartial = true;
        }
        if (!in_array($l, $this->collEventsRelatedBysetPersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddEventRelatedBysetPersonId($l);
        }

        return $this;
    }

    /**
     * @param	EventRelatedBysetPersonId $eventRelatedBysetPersonId The eventRelatedBysetPersonId object to add.
     */
    protected function doAddEventRelatedBysetPersonId($eventRelatedBysetPersonId)
    {
        $this->collEventsRelatedBysetPersonId[]= $eventRelatedBysetPersonId;
        $eventRelatedBysetPersonId->setSetPerson($this);
    }

    /**
     * @param	EventRelatedBysetPersonId $eventRelatedBysetPersonId The eventRelatedBysetPersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeEventRelatedBysetPersonId($eventRelatedBysetPersonId)
    {
        if ($this->getEventsRelatedBysetPersonId()->contains($eventRelatedBysetPersonId)) {
            $this->collEventsRelatedBysetPersonId->remove($this->collEventsRelatedBysetPersonId->search($eventRelatedBysetPersonId));
            if (null === $this->eventsRelatedBysetPersonIdScheduledForDeletion) {
                $this->eventsRelatedBysetPersonIdScheduledForDeletion = clone $this->collEventsRelatedBysetPersonId;
                $this->eventsRelatedBysetPersonIdScheduledForDeletion->clear();
            }
            $this->eventsRelatedBysetPersonIdScheduledForDeletion[]= $eventRelatedBysetPersonId;
            $eventRelatedBysetPersonId->setSetPerson(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBysetPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBysetPersonIdJoinEventType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('EventType', $join_behavior);

        return $this->getEventsRelatedBysetPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBysetPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBysetPersonIdJoinClient($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Client', $join_behavior);

        return $this->getEventsRelatedBysetPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedBysetPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedBysetPersonIdJoinOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('OrgStructure', $join_behavior);

        return $this->getEventsRelatedBysetPersonId($query, $con);
    }

    /**
     * Clears out the collEventsRelatedByexecPersonId collection
     *
     * This does not modify the database; however, it will remove any associated objects, causing
     * them to be refetched by subsequent calls to accessor method.
     *
     * @return Person The current object (for fluent API support)
     * @see        addEventsRelatedByexecPersonId()
     */
    public function clearEventsRelatedByexecPersonId()
    {
        $this->collEventsRelatedByexecPersonId = null; // important to set this to null since that means it is uninitialized
        $this->collEventsRelatedByexecPersonIdPartial = null;

        return $this;
    }

    /**
     * reset is the collEventsRelatedByexecPersonId collection loaded partially
     *
     * @return void
     */
    public function resetPartialEventsRelatedByexecPersonId($v = true)
    {
        $this->collEventsRelatedByexecPersonIdPartial = $v;
    }

    /**
     * Initializes the collEventsRelatedByexecPersonId collection.
     *
     * By default this just sets the collEventsRelatedByexecPersonId collection to an empty array (like clearcollEventsRelatedByexecPersonId());
     * however, you may wish to override this method in your stub class to provide setting appropriate
     * to your application -- for example, setting the initial array to the values stored in database.
     *
     * @param boolean $overrideExisting If set to true, the method call initializes
     *                                        the collection even if it is not empty
     *
     * @return void
     */
    public function initEventsRelatedByexecPersonId($overrideExisting = true)
    {
        if (null !== $this->collEventsRelatedByexecPersonId && !$overrideExisting) {
            return;
        }
        $this->collEventsRelatedByexecPersonId = new PropelObjectCollection();
        $this->collEventsRelatedByexecPersonId->setModel('Event');
    }

    /**
     * Gets an array of Event objects which contain a foreign key that references this object.
     *
     * If the $criteria is not null, it is used to always fetch the results from the database.
     * Otherwise the results are fetched from the database the first time, then cached.
     * Next time the same method is called without $criteria, the cached collection is returned.
     * If this Person is new, it will return
     * an empty collection or the current collection; the criteria is ignored on a new object.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @return PropelObjectCollection|Event[] List of Event objects
     * @throws PropelException
     */
    public function getEventsRelatedByexecPersonId($criteria = null, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedByexecPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedByexecPersonId || null !== $criteria  || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedByexecPersonId) {
                // return empty collection
                $this->initEventsRelatedByexecPersonId();
            } else {
                $collEventsRelatedByexecPersonId = EventQuery::create(null, $criteria)
                    ->filterByDoctor($this)
                    ->find($con);
                if (null !== $criteria) {
                    if (false !== $this->collEventsRelatedByexecPersonIdPartial && count($collEventsRelatedByexecPersonId)) {
                      $this->initEventsRelatedByexecPersonId(false);

                      foreach($collEventsRelatedByexecPersonId as $obj) {
                        if (false == $this->collEventsRelatedByexecPersonId->contains($obj)) {
                          $this->collEventsRelatedByexecPersonId->append($obj);
                        }
                      }

                      $this->collEventsRelatedByexecPersonIdPartial = true;
                    }

                    $collEventsRelatedByexecPersonId->getInternalIterator()->rewind();
                    return $collEventsRelatedByexecPersonId;
                }

                if($partial && $this->collEventsRelatedByexecPersonId) {
                    foreach($this->collEventsRelatedByexecPersonId as $obj) {
                        if($obj->isNew()) {
                            $collEventsRelatedByexecPersonId[] = $obj;
                        }
                    }
                }

                $this->collEventsRelatedByexecPersonId = $collEventsRelatedByexecPersonId;
                $this->collEventsRelatedByexecPersonIdPartial = false;
            }
        }

        return $this->collEventsRelatedByexecPersonId;
    }

    /**
     * Sets a collection of EventRelatedByexecPersonId objects related by a one-to-many relationship
     * to the current object.
     * It will also schedule objects for deletion based on a diff between old objects (aka persisted)
     * and new objects from the given Propel collection.
     *
     * @param PropelCollection $eventsRelatedByexecPersonId A Propel collection.
     * @param PropelPDO $con Optional connection object
     * @return Person The current object (for fluent API support)
     */
    public function setEventsRelatedByexecPersonId(PropelCollection $eventsRelatedByexecPersonId, PropelPDO $con = null)
    {
        $eventsRelatedByexecPersonIdToDelete = $this->getEventsRelatedByexecPersonId(new Criteria(), $con)->diff($eventsRelatedByexecPersonId);

        $this->eventsRelatedByexecPersonIdScheduledForDeletion = unserialize(serialize($eventsRelatedByexecPersonIdToDelete));

        foreach ($eventsRelatedByexecPersonIdToDelete as $eventRelatedByexecPersonIdRemoved) {
            $eventRelatedByexecPersonIdRemoved->setDoctor(null);
        }

        $this->collEventsRelatedByexecPersonId = null;
        foreach ($eventsRelatedByexecPersonId as $eventRelatedByexecPersonId) {
            $this->addEventRelatedByexecPersonId($eventRelatedByexecPersonId);
        }

        $this->collEventsRelatedByexecPersonId = $eventsRelatedByexecPersonId;
        $this->collEventsRelatedByexecPersonIdPartial = false;

        return $this;
    }

    /**
     * Returns the number of related Event objects.
     *
     * @param Criteria $criteria
     * @param boolean $distinct
     * @param PropelPDO $con
     * @return int             Count of related Event objects.
     * @throws PropelException
     */
    public function countEventsRelatedByexecPersonId(Criteria $criteria = null, $distinct = false, PropelPDO $con = null)
    {
        $partial = $this->collEventsRelatedByexecPersonIdPartial && !$this->isNew();
        if (null === $this->collEventsRelatedByexecPersonId || null !== $criteria || $partial) {
            if ($this->isNew() && null === $this->collEventsRelatedByexecPersonId) {
                return 0;
            }

            if($partial && !$criteria) {
                return count($this->getEventsRelatedByexecPersonId());
            }
            $query = EventQuery::create(null, $criteria);
            if ($distinct) {
                $query->distinct();
            }

            return $query
                ->filterByDoctor($this)
                ->count($con);
        }

        return count($this->collEventsRelatedByexecPersonId);
    }

    /**
     * Method called to associate a Event object to this object
     * through the Event foreign key attribute.
     *
     * @param    Event $l Event
     * @return Person The current object (for fluent API support)
     */
    public function addEventRelatedByexecPersonId(Event $l)
    {
        if ($this->collEventsRelatedByexecPersonId === null) {
            $this->initEventsRelatedByexecPersonId();
            $this->collEventsRelatedByexecPersonIdPartial = true;
        }
        if (!in_array($l, $this->collEventsRelatedByexecPersonId->getArrayCopy(), true)) { // only add it if the **same** object is not already associated
            $this->doAddEventRelatedByexecPersonId($l);
        }

        return $this;
    }

    /**
     * @param	EventRelatedByexecPersonId $eventRelatedByexecPersonId The eventRelatedByexecPersonId object to add.
     */
    protected function doAddEventRelatedByexecPersonId($eventRelatedByexecPersonId)
    {
        $this->collEventsRelatedByexecPersonId[]= $eventRelatedByexecPersonId;
        $eventRelatedByexecPersonId->setDoctor($this);
    }

    /**
     * @param	EventRelatedByexecPersonId $eventRelatedByexecPersonId The eventRelatedByexecPersonId object to remove.
     * @return Person The current object (for fluent API support)
     */
    public function removeEventRelatedByexecPersonId($eventRelatedByexecPersonId)
    {
        if ($this->getEventsRelatedByexecPersonId()->contains($eventRelatedByexecPersonId)) {
            $this->collEventsRelatedByexecPersonId->remove($this->collEventsRelatedByexecPersonId->search($eventRelatedByexecPersonId));
            if (null === $this->eventsRelatedByexecPersonIdScheduledForDeletion) {
                $this->eventsRelatedByexecPersonIdScheduledForDeletion = clone $this->collEventsRelatedByexecPersonId;
                $this->eventsRelatedByexecPersonIdScheduledForDeletion->clear();
            }
            $this->eventsRelatedByexecPersonIdScheduledForDeletion[]= $eventRelatedByexecPersonId;
            $eventRelatedByexecPersonId->setDoctor(null);
        }

        return $this;
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedByexecPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedByexecPersonIdJoinEventType($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('EventType', $join_behavior);

        return $this->getEventsRelatedByexecPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedByexecPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedByexecPersonIdJoinClient($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('Client', $join_behavior);

        return $this->getEventsRelatedByexecPersonId($query, $con);
    }


    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Person is new, it will return
     * an empty collection; or if this Person has previously
     * been saved, it will retrieve related EventsRelatedByexecPersonId from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Person.
     *
     * @param Criteria $criteria optional Criteria object to narrow the query
     * @param PropelPDO $con optional connection object
     * @param string $join_behavior optional join type to use (defaults to Criteria::LEFT_JOIN)
     * @return PropelObjectCollection|Event[] List of Event objects
     */
    public function getEventsRelatedByexecPersonIdJoinOrgStructure($criteria = null, $con = null, $join_behavior = Criteria::LEFT_JOIN)
    {
        $query = EventQuery::create(null, $criteria);
        $query->joinWith('OrgStructure', $join_behavior);

        return $this->getEventsRelatedByexecPersonId($query, $con);
    }

    /**
     * Clears the current object and sets all attributes to their default values
     */
    public function clear()
    {
        $this->id = null;
        $this->createdatetime = null;
        $this->createperson_id = null;
        $this->modifydatetime = null;
        $this->modifyperson_id = null;
        $this->deleted = null;
        $this->code = null;
        $this->federalcode = null;
        $this->regionalcode = null;
        $this->lastname = null;
        $this->firstname = null;
        $this->patrname = null;
        $this->post_id = null;
        $this->speciality_id = null;
        $this->org_id = null;
        $this->orgstructure_id = null;
        $this->office = null;
        $this->office2 = null;
        $this->tariffcategory_id = null;
        $this->finance_id = null;
        $this->retiredate = null;
        $this->ambplan = null;
        $this->ambplan2 = null;
        $this->ambnorm = null;
        $this->homplan = null;
        $this->homplan2 = null;
        $this->homnorm = null;
        $this->expplan = null;
        $this->expnorm = null;
        $this->login = null;
        $this->password = null;
        $this->userprofile_id = null;
        $this->retired = null;
        $this->birthdate = null;
        $this->birthplace = null;
        $this->sex = null;
        $this->snils = null;
        $this->inn = null;
        $this->availableforexternal = null;
        $this->primaryquota = null;
        $this->ownquota = null;
        $this->consultancyquota = null;
        $this->externalquota = null;
        $this->lastaccessibletimelinedate = null;
        $this->timelineaccessibledays = null;
        $this->typetimelineperson = null;
        $this->maxoverqueue = null;
        $this->maxcito = null;
        $this->quotunit = null;
        $this->academicdegree_id = null;
        $this->academictitle_id = null;
        $this->uuid_id = null;
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
            if ($this->collActionsRelatedBycreatePersonId) {
                foreach ($this->collActionsRelatedBycreatePersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collActionsRelatedBymodifyPersonId) {
                foreach ($this->collActionsRelatedBymodifyPersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collActionsRelatedBysetPersonId) {
                foreach ($this->collActionsRelatedBysetPersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collEventsRelatedBycreatePersonId) {
                foreach ($this->collEventsRelatedBycreatePersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collEventsRelatedBymodifyPersonId) {
                foreach ($this->collEventsRelatedBymodifyPersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collEventsRelatedBysetPersonId) {
                foreach ($this->collEventsRelatedBysetPersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }
            if ($this->collEventsRelatedByexecPersonId) {
                foreach ($this->collEventsRelatedByexecPersonId as $o) {
                    $o->clearAllReferences($deep);
                }
            }

            $this->alreadyInClearAllReferencesDeep = false;
        } // if ($deep)

        if ($this->collActionsRelatedBycreatePersonId instanceof PropelCollection) {
            $this->collActionsRelatedBycreatePersonId->clearIterator();
        }
        $this->collActionsRelatedBycreatePersonId = null;
        if ($this->collActionsRelatedBymodifyPersonId instanceof PropelCollection) {
            $this->collActionsRelatedBymodifyPersonId->clearIterator();
        }
        $this->collActionsRelatedBymodifyPersonId = null;
        if ($this->collActionsRelatedBysetPersonId instanceof PropelCollection) {
            $this->collActionsRelatedBysetPersonId->clearIterator();
        }
        $this->collActionsRelatedBysetPersonId = null;
        if ($this->collEventsRelatedBycreatePersonId instanceof PropelCollection) {
            $this->collEventsRelatedBycreatePersonId->clearIterator();
        }
        $this->collEventsRelatedBycreatePersonId = null;
        if ($this->collEventsRelatedBymodifyPersonId instanceof PropelCollection) {
            $this->collEventsRelatedBymodifyPersonId->clearIterator();
        }
        $this->collEventsRelatedBymodifyPersonId = null;
        if ($this->collEventsRelatedBysetPersonId instanceof PropelCollection) {
            $this->collEventsRelatedBysetPersonId->clearIterator();
        }
        $this->collEventsRelatedBysetPersonId = null;
        if ($this->collEventsRelatedByexecPersonId instanceof PropelCollection) {
            $this->collEventsRelatedByexecPersonId->clearIterator();
        }
        $this->collEventsRelatedByexecPersonId = null;
    }

    /**
     * return the string representation of this object
     *
     * @return string
     */
    public function __toString()
    {
        return (string) $this->exportTo(PersonPeer::DEFAULT_STRING_FORMAT);
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

    // timestampable behavior

    /**
     * Mark the current object so that the update date doesn't get updated during next save
     *
     * @return     Person The current object (for fluent API support)
     */
    public function keepUpdateDateUnchanged()
    {
        $this->modifiedColumns[] = PersonPeer::MODIFYDATETIME;

        return $this;
    }

}
