<?php

namespace Webmis\Models\om;

use \Criteria;
use \Exception;
use \ModelCriteria;
use \ModelJoin;
use \PDO;
use \Propel;
use \PropelCollection;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\Client;
use Webmis\Models\ClientPeer;
use Webmis\Models\ClientQuery;
use Webmis\Models\Event;

/**
 * Base class that represents a query for the 'Client' table.
 *
 *
 *
 * @method ClientQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method ClientQuery orderBycreateDatetime($order = Criteria::ASC) Order by the createDatetime column
 * @method ClientQuery orderBycreatePersonId($order = Criteria::ASC) Order by the createPerson_id column
 * @method ClientQuery orderBymodifyDatetime($order = Criteria::ASC) Order by the modifyDatetime column
 * @method ClientQuery orderBymodifyPersonId($order = Criteria::ASC) Order by the modifyPerson_id column
 * @method ClientQuery orderBydeleted($order = Criteria::ASC) Order by the deleted column
 * @method ClientQuery orderBylastName($order = Criteria::ASC) Order by the lastName column
 * @method ClientQuery orderByfirstName($order = Criteria::ASC) Order by the firstName column
 * @method ClientQuery orderBypatrName($order = Criteria::ASC) Order by the patrName column
 * @method ClientQuery orderBybirthDate($order = Criteria::ASC) Order by the birthDate column
 * @method ClientQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method ClientQuery orderBysnils($order = Criteria::ASC) Order by the SNILS column
 * @method ClientQuery orderBybloodTypeId($order = Criteria::ASC) Order by the bloodType_id column
 * @method ClientQuery orderBybloodDate($order = Criteria::ASC) Order by the bloodDate column
 * @method ClientQuery orderBybloodNotes($order = Criteria::ASC) Order by the bloodNotes column
 * @method ClientQuery orderBygrowth($order = Criteria::ASC) Order by the growth column
 * @method ClientQuery orderByweight($order = Criteria::ASC) Order by the weight column
 * @method ClientQuery orderBynotes($order = Criteria::ASC) Order by the notes column
 * @method ClientQuery orderByversion($order = Criteria::ASC) Order by the version column
 * @method ClientQuery orderBybirthPlace($order = Criteria::ASC) Order by the birthPlace column
 * @method ClientQuery orderByuuidId($order = Criteria::ASC) Order by the uuid_id column
 *
 * @method ClientQuery groupByid() Group by the id column
 * @method ClientQuery groupBycreateDatetime() Group by the createDatetime column
 * @method ClientQuery groupBycreatePersonId() Group by the createPerson_id column
 * @method ClientQuery groupBymodifyDatetime() Group by the modifyDatetime column
 * @method ClientQuery groupBymodifyPersonId() Group by the modifyPerson_id column
 * @method ClientQuery groupBydeleted() Group by the deleted column
 * @method ClientQuery groupBylastName() Group by the lastName column
 * @method ClientQuery groupByfirstName() Group by the firstName column
 * @method ClientQuery groupBypatrName() Group by the patrName column
 * @method ClientQuery groupBybirthDate() Group by the birthDate column
 * @method ClientQuery groupBysex() Group by the sex column
 * @method ClientQuery groupBysnils() Group by the SNILS column
 * @method ClientQuery groupBybloodTypeId() Group by the bloodType_id column
 * @method ClientQuery groupBybloodDate() Group by the bloodDate column
 * @method ClientQuery groupBybloodNotes() Group by the bloodNotes column
 * @method ClientQuery groupBygrowth() Group by the growth column
 * @method ClientQuery groupByweight() Group by the weight column
 * @method ClientQuery groupBynotes() Group by the notes column
 * @method ClientQuery groupByversion() Group by the version column
 * @method ClientQuery groupBybirthPlace() Group by the birthPlace column
 * @method ClientQuery groupByuuidId() Group by the uuid_id column
 *
 * @method ClientQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method ClientQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method ClientQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method ClientQuery leftJoinEvent($relationAlias = null) Adds a LEFT JOIN clause to the query using the Event relation
 * @method ClientQuery rightJoinEvent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the Event relation
 * @method ClientQuery innerJoinEvent($relationAlias = null) Adds a INNER JOIN clause to the query using the Event relation
 *
 * @method Client findOne(PropelPDO $con = null) Return the first Client matching the query
 * @method Client findOneOrCreate(PropelPDO $con = null) Return the first Client matching the query, or a new Client object populated from the query conditions when no match is found
 *
 * @method Client findOneBycreateDatetime(string $createDatetime) Return the first Client filtered by the createDatetime column
 * @method Client findOneBycreatePersonId(int $createPerson_id) Return the first Client filtered by the createPerson_id column
 * @method Client findOneBymodifyDatetime(string $modifyDatetime) Return the first Client filtered by the modifyDatetime column
 * @method Client findOneBymodifyPersonId(int $modifyPerson_id) Return the first Client filtered by the modifyPerson_id column
 * @method Client findOneBydeleted(boolean $deleted) Return the first Client filtered by the deleted column
 * @method Client findOneBylastName(string $lastName) Return the first Client filtered by the lastName column
 * @method Client findOneByfirstName(string $firstName) Return the first Client filtered by the firstName column
 * @method Client findOneBypatrName(string $patrName) Return the first Client filtered by the patrName column
 * @method Client findOneBybirthDate(string $birthDate) Return the first Client filtered by the birthDate column
 * @method Client findOneBysex(int $sex) Return the first Client filtered by the sex column
 * @method Client findOneBysnils(string $SNILS) Return the first Client filtered by the SNILS column
 * @method Client findOneBybloodTypeId(int $bloodType_id) Return the first Client filtered by the bloodType_id column
 * @method Client findOneBybloodDate(string $bloodDate) Return the first Client filtered by the bloodDate column
 * @method Client findOneBybloodNotes(string $bloodNotes) Return the first Client filtered by the bloodNotes column
 * @method Client findOneBygrowth(string $growth) Return the first Client filtered by the growth column
 * @method Client findOneByweight(string $weight) Return the first Client filtered by the weight column
 * @method Client findOneBynotes(string $notes) Return the first Client filtered by the notes column
 * @method Client findOneByversion(int $version) Return the first Client filtered by the version column
 * @method Client findOneBybirthPlace(string $birthPlace) Return the first Client filtered by the birthPlace column
 * @method Client findOneByuuidId(int $uuid_id) Return the first Client filtered by the uuid_id column
 *
 * @method array findByid(int $id) Return Client objects filtered by the id column
 * @method array findBycreateDatetime(string $createDatetime) Return Client objects filtered by the createDatetime column
 * @method array findBycreatePersonId(int $createPerson_id) Return Client objects filtered by the createPerson_id column
 * @method array findBymodifyDatetime(string $modifyDatetime) Return Client objects filtered by the modifyDatetime column
 * @method array findBymodifyPersonId(int $modifyPerson_id) Return Client objects filtered by the modifyPerson_id column
 * @method array findBydeleted(boolean $deleted) Return Client objects filtered by the deleted column
 * @method array findBylastName(string $lastName) Return Client objects filtered by the lastName column
 * @method array findByfirstName(string $firstName) Return Client objects filtered by the firstName column
 * @method array findBypatrName(string $patrName) Return Client objects filtered by the patrName column
 * @method array findBybirthDate(string $birthDate) Return Client objects filtered by the birthDate column
 * @method array findBysex(int $sex) Return Client objects filtered by the sex column
 * @method array findBysnils(string $SNILS) Return Client objects filtered by the SNILS column
 * @method array findBybloodTypeId(int $bloodType_id) Return Client objects filtered by the bloodType_id column
 * @method array findBybloodDate(string $bloodDate) Return Client objects filtered by the bloodDate column
 * @method array findBybloodNotes(string $bloodNotes) Return Client objects filtered by the bloodNotes column
 * @method array findBygrowth(string $growth) Return Client objects filtered by the growth column
 * @method array findByweight(string $weight) Return Client objects filtered by the weight column
 * @method array findBynotes(string $notes) Return Client objects filtered by the notes column
 * @method array findByversion(int $version) Return Client objects filtered by the version column
 * @method array findBybirthPlace(string $birthPlace) Return Client objects filtered by the birthPlace column
 * @method array findByuuidId(int $uuid_id) Return Client objects filtered by the uuid_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseClientQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseClientQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\Client', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new ClientQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   ClientQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return ClientQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof ClientQuery) {
            return $criteria;
        }
        $query = new ClientQuery();
        if (null !== $modelAlias) {
            $query->setModelAlias($modelAlias);
        }
        if ($criteria instanceof Criteria) {
            $query->mergeWith($criteria);
        }

        return $query;
    }

    /**
     * Find object by primary key.
     * Propel uses the instance pool to skip the database if the object exists.
     * Go fast if the query is untouched.
     *
     * <code>
     * $obj  = $c->findPk(12, $con);
     * </code>
     *
     * @param mixed $key Primary key to use for the query
     * @param     PropelPDO $con an optional connection object
     *
     * @return   Client|Client[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = ClientPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(ClientPeer::DATABASE_NAME, Propel::CONNECTION_READ);
        }
        $this->basePreSelect($con);
        if ($this->formatter || $this->modelAlias || $this->with || $this->select
         || $this->selectColumns || $this->asColumns || $this->selectModifiers
         || $this->map || $this->having || $this->joins) {
            return $this->findPkComplex($key, $con);
        } else {
            return $this->findPkSimple($key, $con);
        }
    }

    /**
     * Alias of findPk to use instance pooling
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return                 Client A model object, or null if the key is not found
     * @throws PropelException
     */
     public function findOneByid($key, $con = null)
     {
        return $this->findPk($key, $con);
     }

    /**
     * Find object by primary key using raw SQL to go fast.
     * Bypass doSelect() and the object formatter by using generated code.
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return                 Client A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `lastName`, `firstName`, `patrName`, `birthDate`, `sex`, `SNILS`, `bloodType_id`, `bloodDate`, `bloodNotes`, `growth`, `weight`, `notes`, `version`, `birthPlace`, `uuid_id` FROM `Client` WHERE `id` = :p0';
        try {
            $stmt = $con->prepare($sql);
            $stmt->bindValue(':p0', $key, PDO::PARAM_INT);
            $stmt->execute();
        } catch (Exception $e) {
            Propel::log($e->getMessage(), Propel::LOG_ERR);
            throw new PropelException(sprintf('Unable to execute SELECT statement [%s]', $sql), $e);
        }
        $obj = null;
        if ($row = $stmt->fetch(PDO::FETCH_NUM)) {
            $obj = new Client();
            $obj->hydrate($row);
            ClientPeer::addInstanceToPool($obj, (string) $key);
        }
        $stmt->closeCursor();

        return $obj;
    }

    /**
     * Find object by primary key.
     *
     * @param     mixed $key Primary key to use for the query
     * @param     PropelPDO $con A connection object
     *
     * @return Client|Client[]|mixed the result, formatted by the current formatter
     */
    protected function findPkComplex($key, $con)
    {
        // As the query uses a PK condition, no limit(1) is necessary.
        $criteria = $this->isKeepQuery() ? clone $this : $this;
        $stmt = $criteria
            ->filterByPrimaryKey($key)
            ->doSelect($con);

        return $criteria->getFormatter()->init($criteria)->formatOne($stmt);
    }

    /**
     * Find objects by primary key
     * <code>
     * $objs = $c->findPks(array(12, 56, 832), $con);
     * </code>
     * @param     array $keys Primary keys to use for the query
     * @param     PropelPDO $con an optional connection object
     *
     * @return PropelObjectCollection|Client[]|mixed the list of results, formatted by the current formatter
     */
    public function findPks($keys, $con = null)
    {
        if ($con === null) {
            $con = Propel::getConnection($this->getDbName(), Propel::CONNECTION_READ);
        }
        $this->basePreSelect($con);
        $criteria = $this->isKeepQuery() ? clone $this : $this;
        $stmt = $criteria
            ->filterByPrimaryKeys($keys)
            ->doSelect($con);

        return $criteria->getFormatter()->init($criteria)->format($stmt);
    }

    /**
     * Filter the query by primary key
     *
     * @param     mixed $key Primary key to use for the query
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(ClientPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(ClientPeer::ID, $keys, Criteria::IN);
    }

    /**
     * Filter the query on the id column
     *
     * Example usage:
     * <code>
     * $query->filterByid(1234); // WHERE id = 1234
     * $query->filterByid(array(12, 34)); // WHERE id IN (12, 34)
     * $query->filterByid(array('min' => 12)); // WHERE id >= 12
     * $query->filterByid(array('max' => 12)); // WHERE id <= 12
     * </code>
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(ClientPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(ClientPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the createDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBycreateDatetime('2011-03-14'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime('now'); // WHERE createDatetime = '2011-03-14'
     * $query->filterBycreateDatetime(array('max' => 'yesterday')); // WHERE createDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $createDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBycreateDatetime($createDatetime = null, $comparison = null)
    {
        if (is_array($createDatetime)) {
            $useMinMax = false;
            if (isset($createDatetime['min'])) {
                $this->addUsingAlias(ClientPeer::CREATEDATETIME, $createDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createDatetime['max'])) {
                $this->addUsingAlias(ClientPeer::CREATEDATETIME, $createDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::CREATEDATETIME, $createDatetime, $comparison);
    }

    /**
     * Filter the query on the createPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBycreatePersonId(1234); // WHERE createPerson_id = 1234
     * $query->filterBycreatePersonId(array(12, 34)); // WHERE createPerson_id IN (12, 34)
     * $query->filterBycreatePersonId(array('min' => 12)); // WHERE createPerson_id >= 12
     * $query->filterBycreatePersonId(array('max' => 12)); // WHERE createPerson_id <= 12
     * </code>
     *
     * @param     mixed $createPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBycreatePersonId($createPersonId = null, $comparison = null)
    {
        if (is_array($createPersonId)) {
            $useMinMax = false;
            if (isset($createPersonId['min'])) {
                $this->addUsingAlias(ClientPeer::CREATEPERSON_ID, $createPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($createPersonId['max'])) {
                $this->addUsingAlias(ClientPeer::CREATEPERSON_ID, $createPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::CREATEPERSON_ID, $createPersonId, $comparison);
    }

    /**
     * Filter the query on the modifyDatetime column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyDatetime('2011-03-14'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime('now'); // WHERE modifyDatetime = '2011-03-14'
     * $query->filterBymodifyDatetime(array('max' => 'yesterday')); // WHERE modifyDatetime > '2011-03-13'
     * </code>
     *
     * @param     mixed $modifyDatetime The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBymodifyDatetime($modifyDatetime = null, $comparison = null)
    {
        if (is_array($modifyDatetime)) {
            $useMinMax = false;
            if (isset($modifyDatetime['min'])) {
                $this->addUsingAlias(ClientPeer::MODIFYDATETIME, $modifyDatetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyDatetime['max'])) {
                $this->addUsingAlias(ClientPeer::MODIFYDATETIME, $modifyDatetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::MODIFYDATETIME, $modifyDatetime, $comparison);
    }

    /**
     * Filter the query on the modifyPerson_id column
     *
     * Example usage:
     * <code>
     * $query->filterBymodifyPersonId(1234); // WHERE modifyPerson_id = 1234
     * $query->filterBymodifyPersonId(array(12, 34)); // WHERE modifyPerson_id IN (12, 34)
     * $query->filterBymodifyPersonId(array('min' => 12)); // WHERE modifyPerson_id >= 12
     * $query->filterBymodifyPersonId(array('max' => 12)); // WHERE modifyPerson_id <= 12
     * </code>
     *
     * @param     mixed $modifyPersonId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBymodifyPersonId($modifyPersonId = null, $comparison = null)
    {
        if (is_array($modifyPersonId)) {
            $useMinMax = false;
            if (isset($modifyPersonId['min'])) {
                $this->addUsingAlias(ClientPeer::MODIFYPERSON_ID, $modifyPersonId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($modifyPersonId['max'])) {
                $this->addUsingAlias(ClientPeer::MODIFYPERSON_ID, $modifyPersonId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::MODIFYPERSON_ID, $modifyPersonId, $comparison);
    }

    /**
     * Filter the query on the deleted column
     *
     * Example usage:
     * <code>
     * $query->filterBydeleted(true); // WHERE deleted = true
     * $query->filterBydeleted('yes'); // WHERE deleted = true
     * </code>
     *
     * @param     boolean|string $deleted The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBydeleted($deleted = null, $comparison = null)
    {
        if (is_string($deleted)) {
            $deleted = in_array(strtolower($deleted), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(ClientPeer::DELETED, $deleted, $comparison);
    }

    /**
     * Filter the query on the lastName column
     *
     * Example usage:
     * <code>
     * $query->filterBylastName('fooValue');   // WHERE lastName = 'fooValue'
     * $query->filterBylastName('%fooValue%'); // WHERE lastName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $lastName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBylastName($lastName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($lastName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $lastName)) {
                $lastName = str_replace('*', '%', $lastName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::LASTNAME, $lastName, $comparison);
    }

    /**
     * Filter the query on the firstName column
     *
     * Example usage:
     * <code>
     * $query->filterByfirstName('fooValue');   // WHERE firstName = 'fooValue'
     * $query->filterByfirstName('%fooValue%'); // WHERE firstName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $firstName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByfirstName($firstName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($firstName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $firstName)) {
                $firstName = str_replace('*', '%', $firstName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::FIRSTNAME, $firstName, $comparison);
    }

    /**
     * Filter the query on the patrName column
     *
     * Example usage:
     * <code>
     * $query->filterBypatrName('fooValue');   // WHERE patrName = 'fooValue'
     * $query->filterBypatrName('%fooValue%'); // WHERE patrName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $patrName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBypatrName($patrName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($patrName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $patrName)) {
                $patrName = str_replace('*', '%', $patrName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::PATRNAME, $patrName, $comparison);
    }

    /**
     * Filter the query on the birthDate column
     *
     * Example usage:
     * <code>
     * $query->filterBybirthDate('2011-03-14'); // WHERE birthDate = '2011-03-14'
     * $query->filterBybirthDate('now'); // WHERE birthDate = '2011-03-14'
     * $query->filterBybirthDate(array('max' => 'yesterday')); // WHERE birthDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $birthDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBybirthDate($birthDate = null, $comparison = null)
    {
        if (is_array($birthDate)) {
            $useMinMax = false;
            if (isset($birthDate['min'])) {
                $this->addUsingAlias(ClientPeer::BIRTHDATE, $birthDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($birthDate['max'])) {
                $this->addUsingAlias(ClientPeer::BIRTHDATE, $birthDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::BIRTHDATE, $birthDate, $comparison);
    }

    /**
     * Filter the query on the sex column
     *
     * Example usage:
     * <code>
     * $query->filterBysex(1234); // WHERE sex = 1234
     * $query->filterBysex(array(12, 34)); // WHERE sex IN (12, 34)
     * $query->filterBysex(array('min' => 12)); // WHERE sex >= 12
     * $query->filterBysex(array('max' => 12)); // WHERE sex <= 12
     * </code>
     *
     * @param     mixed $sex The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_array($sex)) {
            $useMinMax = false;
            if (isset($sex['min'])) {
                $this->addUsingAlias(ClientPeer::SEX, $sex['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($sex['max'])) {
                $this->addUsingAlias(ClientPeer::SEX, $sex['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::SEX, $sex, $comparison);
    }

    /**
     * Filter the query on the SNILS column
     *
     * Example usage:
     * <code>
     * $query->filterBysnils('fooValue');   // WHERE SNILS = 'fooValue'
     * $query->filterBysnils('%fooValue%'); // WHERE SNILS LIKE '%fooValue%'
     * </code>
     *
     * @param     string $snils The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBysnils($snils = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($snils)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $snils)) {
                $snils = str_replace('*', '%', $snils);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::SNILS, $snils, $comparison);
    }

    /**
     * Filter the query on the bloodType_id column
     *
     * Example usage:
     * <code>
     * $query->filterBybloodTypeId(1234); // WHERE bloodType_id = 1234
     * $query->filterBybloodTypeId(array(12, 34)); // WHERE bloodType_id IN (12, 34)
     * $query->filterBybloodTypeId(array('min' => 12)); // WHERE bloodType_id >= 12
     * $query->filterBybloodTypeId(array('max' => 12)); // WHERE bloodType_id <= 12
     * </code>
     *
     * @param     mixed $bloodTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBybloodTypeId($bloodTypeId = null, $comparison = null)
    {
        if (is_array($bloodTypeId)) {
            $useMinMax = false;
            if (isset($bloodTypeId['min'])) {
                $this->addUsingAlias(ClientPeer::BLOODTYPE_ID, $bloodTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($bloodTypeId['max'])) {
                $this->addUsingAlias(ClientPeer::BLOODTYPE_ID, $bloodTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::BLOODTYPE_ID, $bloodTypeId, $comparison);
    }

    /**
     * Filter the query on the bloodDate column
     *
     * Example usage:
     * <code>
     * $query->filterBybloodDate('2011-03-14'); // WHERE bloodDate = '2011-03-14'
     * $query->filterBybloodDate('now'); // WHERE bloodDate = '2011-03-14'
     * $query->filterBybloodDate(array('max' => 'yesterday')); // WHERE bloodDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $bloodDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBybloodDate($bloodDate = null, $comparison = null)
    {
        if (is_array($bloodDate)) {
            $useMinMax = false;
            if (isset($bloodDate['min'])) {
                $this->addUsingAlias(ClientPeer::BLOODDATE, $bloodDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($bloodDate['max'])) {
                $this->addUsingAlias(ClientPeer::BLOODDATE, $bloodDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::BLOODDATE, $bloodDate, $comparison);
    }

    /**
     * Filter the query on the bloodNotes column
     *
     * Example usage:
     * <code>
     * $query->filterBybloodNotes('fooValue');   // WHERE bloodNotes = 'fooValue'
     * $query->filterBybloodNotes('%fooValue%'); // WHERE bloodNotes LIKE '%fooValue%'
     * </code>
     *
     * @param     string $bloodNotes The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBybloodNotes($bloodNotes = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($bloodNotes)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $bloodNotes)) {
                $bloodNotes = str_replace('*', '%', $bloodNotes);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::BLOODNOTES, $bloodNotes, $comparison);
    }

    /**
     * Filter the query on the growth column
     *
     * Example usage:
     * <code>
     * $query->filterBygrowth('fooValue');   // WHERE growth = 'fooValue'
     * $query->filterBygrowth('%fooValue%'); // WHERE growth LIKE '%fooValue%'
     * </code>
     *
     * @param     string $growth The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBygrowth($growth = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($growth)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $growth)) {
                $growth = str_replace('*', '%', $growth);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::GROWTH, $growth, $comparison);
    }

    /**
     * Filter the query on the weight column
     *
     * Example usage:
     * <code>
     * $query->filterByweight('fooValue');   // WHERE weight = 'fooValue'
     * $query->filterByweight('%fooValue%'); // WHERE weight LIKE '%fooValue%'
     * </code>
     *
     * @param     string $weight The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByweight($weight = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($weight)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $weight)) {
                $weight = str_replace('*', '%', $weight);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::WEIGHT, $weight, $comparison);
    }

    /**
     * Filter the query on the notes column
     *
     * Example usage:
     * <code>
     * $query->filterBynotes('fooValue');   // WHERE notes = 'fooValue'
     * $query->filterBynotes('%fooValue%'); // WHERE notes LIKE '%fooValue%'
     * </code>
     *
     * @param     string $notes The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBynotes($notes = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($notes)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $notes)) {
                $notes = str_replace('*', '%', $notes);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::NOTES, $notes, $comparison);
    }

    /**
     * Filter the query on the version column
     *
     * Example usage:
     * <code>
     * $query->filterByversion(1234); // WHERE version = 1234
     * $query->filterByversion(array(12, 34)); // WHERE version IN (12, 34)
     * $query->filterByversion(array('min' => 12)); // WHERE version >= 12
     * $query->filterByversion(array('max' => 12)); // WHERE version <= 12
     * </code>
     *
     * @param     mixed $version The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByversion($version = null, $comparison = null)
    {
        if (is_array($version)) {
            $useMinMax = false;
            if (isset($version['min'])) {
                $this->addUsingAlias(ClientPeer::VERSION, $version['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($version['max'])) {
                $this->addUsingAlias(ClientPeer::VERSION, $version['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::VERSION, $version, $comparison);
    }

    /**
     * Filter the query on the birthPlace column
     *
     * Example usage:
     * <code>
     * $query->filterBybirthPlace('fooValue');   // WHERE birthPlace = 'fooValue'
     * $query->filterBybirthPlace('%fooValue%'); // WHERE birthPlace LIKE '%fooValue%'
     * </code>
     *
     * @param     string $birthPlace The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterBybirthPlace($birthPlace = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($birthPlace)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $birthPlace)) {
                $birthPlace = str_replace('*', '%', $birthPlace);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(ClientPeer::BIRTHPLACE, $birthPlace, $comparison);
    }

    /**
     * Filter the query on the uuid_id column
     *
     * Example usage:
     * <code>
     * $query->filterByuuidId(1234); // WHERE uuid_id = 1234
     * $query->filterByuuidId(array(12, 34)); // WHERE uuid_id IN (12, 34)
     * $query->filterByuuidId(array('min' => 12)); // WHERE uuid_id >= 12
     * $query->filterByuuidId(array('max' => 12)); // WHERE uuid_id <= 12
     * </code>
     *
     * @param     mixed $uuidId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function filterByuuidId($uuidId = null, $comparison = null)
    {
        if (is_array($uuidId)) {
            $useMinMax = false;
            if (isset($uuidId['min'])) {
                $this->addUsingAlias(ClientPeer::UUID_ID, $uuidId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($uuidId['max'])) {
                $this->addUsingAlias(ClientPeer::UUID_ID, $uuidId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(ClientPeer::UUID_ID, $uuidId, $comparison);
    }

    /**
     * Filter the query by a related Event object
     *
     * @param   Event|PropelObjectCollection $event  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 ClientQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByEvent($event, $comparison = null)
    {
        if ($event instanceof Event) {
            return $this
                ->addUsingAlias(ClientPeer::ID, $event->getclientId(), $comparison);
        } elseif ($event instanceof PropelObjectCollection) {
            return $this
                ->useEventQuery()
                ->filterByPrimaryKeys($event->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByEvent() only accepts arguments of type Event or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the Event relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function joinEvent($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('Event');

        // create a ModelJoin object for this join
        $join = new ModelJoin();
        $join->setJoinType($joinType);
        $join->setRelationMap($relationMap, $this->useAliasInSQL ? $this->getModelAlias() : null, $relationAlias);
        if ($previousJoin = $this->getPreviousJoin()) {
            $join->setPreviousJoin($previousJoin);
        }

        // add the ModelJoin to the current object
        if ($relationAlias) {
            $this->addAlias($relationAlias, $relationMap->getRightTable()->getName());
            $this->addJoinObject($join, $relationAlias);
        } else {
            $this->addJoinObject($join, 'Event');
        }

        return $this;
    }

    /**
     * Use the Event relation Event object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\EventQuery A secondary query class using the current class as primary query
     */
    public function useEventQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinEvent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'Event', '\Webmis\Models\EventQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   Client $client Object to remove from the list of results
     *
     * @return ClientQuery The current query, for fluid interface
     */
    public function prune($client = null)
    {
        if ($client) {
            $this->addUsingAlias(ClientPeer::ID, $client->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

    // timestampable behavior

    /**
     * Filter by the latest updated
     *
     * @param      int $nbDays Maximum age of the latest update in days
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function recentlyUpdated($nbDays = 7)
    {
        return $this->addUsingAlias(ClientPeer::MODIFYDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by update date desc
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function lastUpdatedFirst()
    {
        return $this->addDescendingOrderByColumn(ClientPeer::MODIFYDATETIME);
    }

    /**
     * Order by update date asc
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function firstUpdatedFirst()
    {
        return $this->addAscendingOrderByColumn(ClientPeer::MODIFYDATETIME);
    }

    /**
     * Filter by the latest created
     *
     * @param      int $nbDays Maximum age of in days
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function recentlyCreated($nbDays = 7)
    {
        return $this->addUsingAlias(ClientPeer::CREATEDATETIME, time() - $nbDays * 24 * 60 * 60, Criteria::GREATER_EQUAL);
    }

    /**
     * Order by create date desc
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function lastCreatedFirst()
    {
        return $this->addDescendingOrderByColumn(ClientPeer::CREATEDATETIME);
    }

    /**
     * Order by create date asc
     *
     * @return     ClientQuery The current query, for fluid interface
     */
    public function firstCreatedFirst()
    {
        return $this->addAscendingOrderByColumn(ClientPeer::CREATEDATETIME);
    }
}
