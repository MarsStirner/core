<?php

namespace Webmis\Models\om;

use \Criteria;
use \Exception;
use \ModelCriteria;
use \PDO;
use \Propel;
use \PropelException;
use \PropelObjectCollection;
use \PropelPDO;
use Webmis\Models\Mkb;
use Webmis\Models\MkbPeer;
use Webmis\Models\MkbQuery;

/**
 * Base class that represents a query for the 'MKB' table.
 *
 *
 *
 * @method MkbQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method MkbQuery orderByclassId($order = Criteria::ASC) Order by the ClassID column
 * @method MkbQuery orderByclassName($order = Criteria::ASC) Order by the ClassName column
 * @method MkbQuery orderByblockId($order = Criteria::ASC) Order by the BlockID column
 * @method MkbQuery orderByblockName($order = Criteria::ASC) Order by the BlockName column
 * @method MkbQuery orderBydiagId($order = Criteria::ASC) Order by the DiagID column
 * @method MkbQuery orderBydiagName($order = Criteria::ASC) Order by the DiagName column
 * @method MkbQuery orderByprim($order = Criteria::ASC) Order by the Prim column
 * @method MkbQuery orderBysex($order = Criteria::ASC) Order by the sex column
 * @method MkbQuery orderByage($order = Criteria::ASC) Order by the age column
 * @method MkbQuery orderByageBu($order = Criteria::ASC) Order by the age_bu column
 * @method MkbQuery orderByageBc($order = Criteria::ASC) Order by the age_bc column
 * @method MkbQuery orderByageEu($order = Criteria::ASC) Order by the age_eu column
 * @method MkbQuery orderByageEc($order = Criteria::ASC) Order by the age_ec column
 * @method MkbQuery orderBycharacters($order = Criteria::ASC) Order by the characters column
 * @method MkbQuery orderByduration($order = Criteria::ASC) Order by the duration column
 * @method MkbQuery orderByserviceId($order = Criteria::ASC) Order by the service_id column
 * @method MkbQuery orderByMkbSubclassId($order = Criteria::ASC) Order by the MKBSubclass_id column
 *
 * @method MkbQuery groupByid() Group by the id column
 * @method MkbQuery groupByclassId() Group by the ClassID column
 * @method MkbQuery groupByclassName() Group by the ClassName column
 * @method MkbQuery groupByblockId() Group by the BlockID column
 * @method MkbQuery groupByblockName() Group by the BlockName column
 * @method MkbQuery groupBydiagId() Group by the DiagID column
 * @method MkbQuery groupBydiagName() Group by the DiagName column
 * @method MkbQuery groupByprim() Group by the Prim column
 * @method MkbQuery groupBysex() Group by the sex column
 * @method MkbQuery groupByage() Group by the age column
 * @method MkbQuery groupByageBu() Group by the age_bu column
 * @method MkbQuery groupByageBc() Group by the age_bc column
 * @method MkbQuery groupByageEu() Group by the age_eu column
 * @method MkbQuery groupByageEc() Group by the age_ec column
 * @method MkbQuery groupBycharacters() Group by the characters column
 * @method MkbQuery groupByduration() Group by the duration column
 * @method MkbQuery groupByserviceId() Group by the service_id column
 * @method MkbQuery groupByMkbSubclassId() Group by the MKBSubclass_id column
 *
 * @method MkbQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method MkbQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method MkbQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method Mkb findOne(PropelPDO $con = null) Return the first Mkb matching the query
 * @method Mkb findOneOrCreate(PropelPDO $con = null) Return the first Mkb matching the query, or a new Mkb object populated from the query conditions when no match is found
 *
 * @method Mkb findOneByclassId(string $ClassID) Return the first Mkb filtered by the ClassID column
 * @method Mkb findOneByclassName(string $ClassName) Return the first Mkb filtered by the ClassName column
 * @method Mkb findOneByblockId(string $BlockID) Return the first Mkb filtered by the BlockID column
 * @method Mkb findOneByblockName(string $BlockName) Return the first Mkb filtered by the BlockName column
 * @method Mkb findOneBydiagId(string $DiagID) Return the first Mkb filtered by the DiagID column
 * @method Mkb findOneBydiagName(string $DiagName) Return the first Mkb filtered by the DiagName column
 * @method Mkb findOneByprim(string $Prim) Return the first Mkb filtered by the Prim column
 * @method Mkb findOneBysex(boolean $sex) Return the first Mkb filtered by the sex column
 * @method Mkb findOneByage(string $age) Return the first Mkb filtered by the age column
 * @method Mkb findOneByageBu(int $age_bu) Return the first Mkb filtered by the age_bu column
 * @method Mkb findOneByageBc(int $age_bc) Return the first Mkb filtered by the age_bc column
 * @method Mkb findOneByageEu(int $age_eu) Return the first Mkb filtered by the age_eu column
 * @method Mkb findOneByageEc(int $age_ec) Return the first Mkb filtered by the age_ec column
 * @method Mkb findOneBycharacters(int $characters) Return the first Mkb filtered by the characters column
 * @method Mkb findOneByduration(int $duration) Return the first Mkb filtered by the duration column
 * @method Mkb findOneByserviceId(int $service_id) Return the first Mkb filtered by the service_id column
 * @method Mkb findOneByMkbSubclassId(int $MKBSubclass_id) Return the first Mkb filtered by the MKBSubclass_id column
 *
 * @method array findByid(int $id) Return Mkb objects filtered by the id column
 * @method array findByclassId(string $ClassID) Return Mkb objects filtered by the ClassID column
 * @method array findByclassName(string $ClassName) Return Mkb objects filtered by the ClassName column
 * @method array findByblockId(string $BlockID) Return Mkb objects filtered by the BlockID column
 * @method array findByblockName(string $BlockName) Return Mkb objects filtered by the BlockName column
 * @method array findBydiagId(string $DiagID) Return Mkb objects filtered by the DiagID column
 * @method array findBydiagName(string $DiagName) Return Mkb objects filtered by the DiagName column
 * @method array findByprim(string $Prim) Return Mkb objects filtered by the Prim column
 * @method array findBysex(boolean $sex) Return Mkb objects filtered by the sex column
 * @method array findByage(string $age) Return Mkb objects filtered by the age column
 * @method array findByageBu(int $age_bu) Return Mkb objects filtered by the age_bu column
 * @method array findByageBc(int $age_bc) Return Mkb objects filtered by the age_bc column
 * @method array findByageEu(int $age_eu) Return Mkb objects filtered by the age_eu column
 * @method array findByageEc(int $age_ec) Return Mkb objects filtered by the age_ec column
 * @method array findBycharacters(int $characters) Return Mkb objects filtered by the characters column
 * @method array findByduration(int $duration) Return Mkb objects filtered by the duration column
 * @method array findByserviceId(int $service_id) Return Mkb objects filtered by the service_id column
 * @method array findByMkbSubclassId(int $MKBSubclass_id) Return Mkb objects filtered by the MKBSubclass_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseMkbQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseMkbQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\Mkb', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new MkbQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   MkbQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return MkbQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof MkbQuery) {
            return $criteria;
        }
        $query = new MkbQuery();
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
     * @return   Mkb|Mkb[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = MkbPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(MkbPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 Mkb A model object, or null if the key is not found
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
     * @return                 Mkb A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `ClassID`, `ClassName`, `BlockID`, `BlockName`, `DiagID`, `DiagName`, `Prim`, `sex`, `age`, `age_bu`, `age_bc`, `age_eu`, `age_ec`, `characters`, `duration`, `service_id`, `MKBSubclass_id` FROM `MKB` WHERE `id` = :p0';
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
            $obj = new Mkb();
            $obj->hydrate($row);
            MkbPeer::addInstanceToPool($obj, (string) $key);
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
     * @return Mkb|Mkb[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|Mkb[]|mixed the list of results, formatted by the current formatter
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
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(MkbPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(MkbPeer::ID, $keys, Criteria::IN);
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
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(MkbPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(MkbPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the ClassID column
     *
     * Example usage:
     * <code>
     * $query->filterByclassId('fooValue');   // WHERE ClassID = 'fooValue'
     * $query->filterByclassId('%fooValue%'); // WHERE ClassID LIKE '%fooValue%'
     * </code>
     *
     * @param     string $classId The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByclassId($classId = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($classId)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $classId)) {
                $classId = str_replace('*', '%', $classId);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::CLASSID, $classId, $comparison);
    }

    /**
     * Filter the query on the ClassName column
     *
     * Example usage:
     * <code>
     * $query->filterByclassName('fooValue');   // WHERE ClassName = 'fooValue'
     * $query->filterByclassName('%fooValue%'); // WHERE ClassName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $className The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByclassName($className = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($className)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $className)) {
                $className = str_replace('*', '%', $className);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::CLASSNAME, $className, $comparison);
    }

    /**
     * Filter the query on the BlockID column
     *
     * Example usage:
     * <code>
     * $query->filterByblockId('fooValue');   // WHERE BlockID = 'fooValue'
     * $query->filterByblockId('%fooValue%'); // WHERE BlockID LIKE '%fooValue%'
     * </code>
     *
     * @param     string $blockId The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByblockId($blockId = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($blockId)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $blockId)) {
                $blockId = str_replace('*', '%', $blockId);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::BLOCKID, $blockId, $comparison);
    }

    /**
     * Filter the query on the BlockName column
     *
     * Example usage:
     * <code>
     * $query->filterByblockName('fooValue');   // WHERE BlockName = 'fooValue'
     * $query->filterByblockName('%fooValue%'); // WHERE BlockName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $blockName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByblockName($blockName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($blockName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $blockName)) {
                $blockName = str_replace('*', '%', $blockName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::BLOCKNAME, $blockName, $comparison);
    }

    /**
     * Filter the query on the DiagID column
     *
     * Example usage:
     * <code>
     * $query->filterBydiagId('fooValue');   // WHERE DiagID = 'fooValue'
     * $query->filterBydiagId('%fooValue%'); // WHERE DiagID LIKE '%fooValue%'
     * </code>
     *
     * @param     string $diagId The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterBydiagId($diagId = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($diagId)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $diagId)) {
                $diagId = str_replace('*', '%', $diagId);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::DIAGID, $diagId, $comparison);
    }

    /**
     * Filter the query on the DiagName column
     *
     * Example usage:
     * <code>
     * $query->filterBydiagName('fooValue');   // WHERE DiagName = 'fooValue'
     * $query->filterBydiagName('%fooValue%'); // WHERE DiagName LIKE '%fooValue%'
     * </code>
     *
     * @param     string $diagName The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterBydiagName($diagName = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($diagName)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $diagName)) {
                $diagName = str_replace('*', '%', $diagName);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::DIAGNAME, $diagName, $comparison);
    }

    /**
     * Filter the query on the Prim column
     *
     * Example usage:
     * <code>
     * $query->filterByprim('fooValue');   // WHERE Prim = 'fooValue'
     * $query->filterByprim('%fooValue%'); // WHERE Prim LIKE '%fooValue%'
     * </code>
     *
     * @param     string $prim The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByprim($prim = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($prim)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $prim)) {
                $prim = str_replace('*', '%', $prim);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::PRIM, $prim, $comparison);
    }

    /**
     * Filter the query on the sex column
     *
     * Example usage:
     * <code>
     * $query->filterBysex(true); // WHERE sex = true
     * $query->filterBysex('yes'); // WHERE sex = true
     * </code>
     *
     * @param     boolean|string $sex The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterBysex($sex = null, $comparison = null)
    {
        if (is_string($sex)) {
            $sex = in_array(strtolower($sex), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(MkbPeer::SEX, $sex, $comparison);
    }

    /**
     * Filter the query on the age column
     *
     * Example usage:
     * <code>
     * $query->filterByage('fooValue');   // WHERE age = 'fooValue'
     * $query->filterByage('%fooValue%'); // WHERE age LIKE '%fooValue%'
     * </code>
     *
     * @param     string $age The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByage($age = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($age)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $age)) {
                $age = str_replace('*', '%', $age);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(MkbPeer::AGE, $age, $comparison);
    }

    /**
     * Filter the query on the age_bu column
     *
     * Example usage:
     * <code>
     * $query->filterByageBu(1234); // WHERE age_bu = 1234
     * $query->filterByageBu(array(12, 34)); // WHERE age_bu IN (12, 34)
     * $query->filterByageBu(array('min' => 12)); // WHERE age_bu >= 12
     * $query->filterByageBu(array('max' => 12)); // WHERE age_bu <= 12
     * </code>
     *
     * @param     mixed $ageBu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByageBu($ageBu = null, $comparison = null)
    {
        if (is_array($ageBu)) {
            $useMinMax = false;
            if (isset($ageBu['min'])) {
                $this->addUsingAlias(MkbPeer::AGE_BU, $ageBu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBu['max'])) {
                $this->addUsingAlias(MkbPeer::AGE_BU, $ageBu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::AGE_BU, $ageBu, $comparison);
    }

    /**
     * Filter the query on the age_bc column
     *
     * Example usage:
     * <code>
     * $query->filterByageBc(1234); // WHERE age_bc = 1234
     * $query->filterByageBc(array(12, 34)); // WHERE age_bc IN (12, 34)
     * $query->filterByageBc(array('min' => 12)); // WHERE age_bc >= 12
     * $query->filterByageBc(array('max' => 12)); // WHERE age_bc <= 12
     * </code>
     *
     * @param     mixed $ageBc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByageBc($ageBc = null, $comparison = null)
    {
        if (is_array($ageBc)) {
            $useMinMax = false;
            if (isset($ageBc['min'])) {
                $this->addUsingAlias(MkbPeer::AGE_BC, $ageBc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageBc['max'])) {
                $this->addUsingAlias(MkbPeer::AGE_BC, $ageBc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::AGE_BC, $ageBc, $comparison);
    }

    /**
     * Filter the query on the age_eu column
     *
     * Example usage:
     * <code>
     * $query->filterByageEu(1234); // WHERE age_eu = 1234
     * $query->filterByageEu(array(12, 34)); // WHERE age_eu IN (12, 34)
     * $query->filterByageEu(array('min' => 12)); // WHERE age_eu >= 12
     * $query->filterByageEu(array('max' => 12)); // WHERE age_eu <= 12
     * </code>
     *
     * @param     mixed $ageEu The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByageEu($ageEu = null, $comparison = null)
    {
        if (is_array($ageEu)) {
            $useMinMax = false;
            if (isset($ageEu['min'])) {
                $this->addUsingAlias(MkbPeer::AGE_EU, $ageEu['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEu['max'])) {
                $this->addUsingAlias(MkbPeer::AGE_EU, $ageEu['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::AGE_EU, $ageEu, $comparison);
    }

    /**
     * Filter the query on the age_ec column
     *
     * Example usage:
     * <code>
     * $query->filterByageEc(1234); // WHERE age_ec = 1234
     * $query->filterByageEc(array(12, 34)); // WHERE age_ec IN (12, 34)
     * $query->filterByageEc(array('min' => 12)); // WHERE age_ec >= 12
     * $query->filterByageEc(array('max' => 12)); // WHERE age_ec <= 12
     * </code>
     *
     * @param     mixed $ageEc The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByageEc($ageEc = null, $comparison = null)
    {
        if (is_array($ageEc)) {
            $useMinMax = false;
            if (isset($ageEc['min'])) {
                $this->addUsingAlias(MkbPeer::AGE_EC, $ageEc['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($ageEc['max'])) {
                $this->addUsingAlias(MkbPeer::AGE_EC, $ageEc['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::AGE_EC, $ageEc, $comparison);
    }

    /**
     * Filter the query on the characters column
     *
     * Example usage:
     * <code>
     * $query->filterBycharacters(1234); // WHERE characters = 1234
     * $query->filterBycharacters(array(12, 34)); // WHERE characters IN (12, 34)
     * $query->filterBycharacters(array('min' => 12)); // WHERE characters >= 12
     * $query->filterBycharacters(array('max' => 12)); // WHERE characters <= 12
     * </code>
     *
     * @param     mixed $characters The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterBycharacters($characters = null, $comparison = null)
    {
        if (is_array($characters)) {
            $useMinMax = false;
            if (isset($characters['min'])) {
                $this->addUsingAlias(MkbPeer::CHARACTERS, $characters['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($characters['max'])) {
                $this->addUsingAlias(MkbPeer::CHARACTERS, $characters['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::CHARACTERS, $characters, $comparison);
    }

    /**
     * Filter the query on the duration column
     *
     * Example usage:
     * <code>
     * $query->filterByduration(1234); // WHERE duration = 1234
     * $query->filterByduration(array(12, 34)); // WHERE duration IN (12, 34)
     * $query->filterByduration(array('min' => 12)); // WHERE duration >= 12
     * $query->filterByduration(array('max' => 12)); // WHERE duration <= 12
     * </code>
     *
     * @param     mixed $duration The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByduration($duration = null, $comparison = null)
    {
        if (is_array($duration)) {
            $useMinMax = false;
            if (isset($duration['min'])) {
                $this->addUsingAlias(MkbPeer::DURATION, $duration['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($duration['max'])) {
                $this->addUsingAlias(MkbPeer::DURATION, $duration['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::DURATION, $duration, $comparison);
    }

    /**
     * Filter the query on the service_id column
     *
     * Example usage:
     * <code>
     * $query->filterByserviceId(1234); // WHERE service_id = 1234
     * $query->filterByserviceId(array(12, 34)); // WHERE service_id IN (12, 34)
     * $query->filterByserviceId(array('min' => 12)); // WHERE service_id >= 12
     * $query->filterByserviceId(array('max' => 12)); // WHERE service_id <= 12
     * </code>
     *
     * @param     mixed $serviceId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByserviceId($serviceId = null, $comparison = null)
    {
        if (is_array($serviceId)) {
            $useMinMax = false;
            if (isset($serviceId['min'])) {
                $this->addUsingAlias(MkbPeer::SERVICE_ID, $serviceId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($serviceId['max'])) {
                $this->addUsingAlias(MkbPeer::SERVICE_ID, $serviceId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::SERVICE_ID, $serviceId, $comparison);
    }

    /**
     * Filter the query on the MKBSubclass_id column
     *
     * Example usage:
     * <code>
     * $query->filterByMkbSubclassId(1234); // WHERE MKBSubclass_id = 1234
     * $query->filterByMkbSubclassId(array(12, 34)); // WHERE MKBSubclass_id IN (12, 34)
     * $query->filterByMkbSubclassId(array('min' => 12)); // WHERE MKBSubclass_id >= 12
     * $query->filterByMkbSubclassId(array('max' => 12)); // WHERE MKBSubclass_id <= 12
     * </code>
     *
     * @param     mixed $mkbSubclassId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function filterByMkbSubclassId($mkbSubclassId = null, $comparison = null)
    {
        if (is_array($mkbSubclassId)) {
            $useMinMax = false;
            if (isset($mkbSubclassId['min'])) {
                $this->addUsingAlias(MkbPeer::MKBSUBCLASS_ID, $mkbSubclassId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($mkbSubclassId['max'])) {
                $this->addUsingAlias(MkbPeer::MKBSUBCLASS_ID, $mkbSubclassId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(MkbPeer::MKBSUBCLASS_ID, $mkbSubclassId, $comparison);
    }

    /**
     * Exclude object from result
     *
     * @param   Mkb $mkb Object to remove from the list of results
     *
     * @return MkbQuery The current query, for fluid interface
     */
    public function prune($mkb = null)
    {
        if ($mkb) {
            $this->addUsingAlias(MkbPeer::ID, $mkb->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
