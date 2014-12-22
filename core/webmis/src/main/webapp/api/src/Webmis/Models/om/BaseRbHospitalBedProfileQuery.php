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
use Webmis\Models\RbHospitalBedProfile;
use Webmis\Models\RbHospitalBedProfilePeer;
use Webmis\Models\RbHospitalBedProfileQuery;

/**
 * Base class that represents a query for the 'rbHospitalBedProfile' table.
 *
 *
 *
 * @method RbHospitalBedProfileQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method RbHospitalBedProfileQuery orderBycode($order = Criteria::ASC) Order by the code column
 * @method RbHospitalBedProfileQuery orderByname($order = Criteria::ASC) Order by the name column
 * @method RbHospitalBedProfileQuery orderByserviceId($order = Criteria::ASC) Order by the service_id column
 *
 * @method RbHospitalBedProfileQuery groupByid() Group by the id column
 * @method RbHospitalBedProfileQuery groupBycode() Group by the code column
 * @method RbHospitalBedProfileQuery groupByname() Group by the name column
 * @method RbHospitalBedProfileQuery groupByserviceId() Group by the service_id column
 *
 * @method RbHospitalBedProfileQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method RbHospitalBedProfileQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method RbHospitalBedProfileQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method RbHospitalBedProfile findOne(PropelPDO $con = null) Return the first RbHospitalBedProfile matching the query
 * @method RbHospitalBedProfile findOneOrCreate(PropelPDO $con = null) Return the first RbHospitalBedProfile matching the query, or a new RbHospitalBedProfile object populated from the query conditions when no match is found
 *
 * @method RbHospitalBedProfile findOneBycode(string $code) Return the first RbHospitalBedProfile filtered by the code column
 * @method RbHospitalBedProfile findOneByname(string $name) Return the first RbHospitalBedProfile filtered by the name column
 * @method RbHospitalBedProfile findOneByserviceId(int $service_id) Return the first RbHospitalBedProfile filtered by the service_id column
 *
 * @method array findByid(int $id) Return RbHospitalBedProfile objects filtered by the id column
 * @method array findBycode(string $code) Return RbHospitalBedProfile objects filtered by the code column
 * @method array findByname(string $name) Return RbHospitalBedProfile objects filtered by the name column
 * @method array findByserviceId(int $service_id) Return RbHospitalBedProfile objects filtered by the service_id column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseRbHospitalBedProfileQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseRbHospitalBedProfileQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\RbHospitalBedProfile', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new RbHospitalBedProfileQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   RbHospitalBedProfileQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return RbHospitalBedProfileQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof RbHospitalBedProfileQuery) {
            return $criteria;
        }
        $query = new RbHospitalBedProfileQuery();
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
     * @return   RbHospitalBedProfile|RbHospitalBedProfile[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = RbHospitalBedProfilePeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(RbHospitalBedProfilePeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 RbHospitalBedProfile A model object, or null if the key is not found
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
     * @return                 RbHospitalBedProfile A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `code`, `name`, `service_id` FROM `rbHospitalBedProfile` WHERE `id` = :p0';
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
            $obj = new RbHospitalBedProfile();
            $obj->hydrate($row);
            RbHospitalBedProfilePeer::addInstanceToPool($obj, (string) $key);
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
     * @return RbHospitalBedProfile|RbHospitalBedProfile[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|RbHospitalBedProfile[]|mixed the list of results, formatted by the current formatter
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
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $keys, Criteria::IN);
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
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the code column
     *
     * Example usage:
     * <code>
     * $query->filterBycode('fooValue');   // WHERE code = 'fooValue'
     * $query->filterBycode('%fooValue%'); // WHERE code LIKE '%fooValue%'
     * </code>
     *
     * @param     string $code The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterBycode($code = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($code)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $code)) {
                $code = str_replace('*', '%', $code);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(RbHospitalBedProfilePeer::CODE, $code, $comparison);
    }

    /**
     * Filter the query on the name column
     *
     * Example usage:
     * <code>
     * $query->filterByname('fooValue');   // WHERE name = 'fooValue'
     * $query->filterByname('%fooValue%'); // WHERE name LIKE '%fooValue%'
     * </code>
     *
     * @param     string $name The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterByname($name = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($name)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $name)) {
                $name = str_replace('*', '%', $name);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(RbHospitalBedProfilePeer::NAME, $name, $comparison);
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
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function filterByserviceId($serviceId = null, $comparison = null)
    {
        if (is_array($serviceId)) {
            $useMinMax = false;
            if (isset($serviceId['min'])) {
                $this->addUsingAlias(RbHospitalBedProfilePeer::SERVICE_ID, $serviceId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($serviceId['max'])) {
                $this->addUsingAlias(RbHospitalBedProfilePeer::SERVICE_ID, $serviceId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(RbHospitalBedProfilePeer::SERVICE_ID, $serviceId, $comparison);
    }

    /**
     * Exclude object from result
     *
     * @param   RbHospitalBedProfile $rbHospitalBedProfile Object to remove from the list of results
     *
     * @return RbHospitalBedProfileQuery The current query, for fluid interface
     */
    public function prune($rbHospitalBedProfile = null)
    {
        if ($rbHospitalBedProfile) {
            $this->addUsingAlias(RbHospitalBedProfilePeer::ID, $rbHospitalBedProfile->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
