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
use Webmis\Models\DrugComponent;
use Webmis\Models\RlsBalanceOfGoods;
use Webmis\Models\rbUnit;
use Webmis\Models\rlsActMatters;
use Webmis\Models\rlsFilling;
use Webmis\Models\rlsForm;
use Webmis\Models\rlsNomen;
use Webmis\Models\rlsNomenPeer;
use Webmis\Models\rlsNomenQuery;
use Webmis\Models\rlsPacking;
use Webmis\Models\rlsTradeName;

/**
 * Base class that represents a query for the 'rlsNomen' table.
 *
 *
 *
 * @method rlsNomenQuery orderByid($order = Criteria::ASC) Order by the id column
 * @method rlsNomenQuery orderByactMattersId($order = Criteria::ASC) Order by the actMatters_id column
 * @method rlsNomenQuery orderBytradeNameId($order = Criteria::ASC) Order by the tradeName_id column
 * @method rlsNomenQuery orderByformId($order = Criteria::ASC) Order by the form_id column
 * @method rlsNomenQuery orderBypackingId($order = Criteria::ASC) Order by the packing_id column
 * @method rlsNomenQuery orderByfillingId($order = Criteria::ASC) Order by the filling_id column
 * @method rlsNomenQuery orderByunitId($order = Criteria::ASC) Order by the unit_id column
 * @method rlsNomenQuery orderBydosageValue($order = Criteria::ASC) Order by the dosageValue column
 * @method rlsNomenQuery orderBydosageUnitId($order = Criteria::ASC) Order by the dosageUnit_id column
 * @method rlsNomenQuery orderBydrugLifetime($order = Criteria::ASC) Order by the drugLifetime column
 * @method rlsNomenQuery orderByregDate($order = Criteria::ASC) Order by the regDate column
 * @method rlsNomenQuery orderByannDate($order = Criteria::ASC) Order by the annDate column
 *
 * @method rlsNomenQuery groupByid() Group by the id column
 * @method rlsNomenQuery groupByactMattersId() Group by the actMatters_id column
 * @method rlsNomenQuery groupBytradeNameId() Group by the tradeName_id column
 * @method rlsNomenQuery groupByformId() Group by the form_id column
 * @method rlsNomenQuery groupBypackingId() Group by the packing_id column
 * @method rlsNomenQuery groupByfillingId() Group by the filling_id column
 * @method rlsNomenQuery groupByunitId() Group by the unit_id column
 * @method rlsNomenQuery groupBydosageValue() Group by the dosageValue column
 * @method rlsNomenQuery groupBydosageUnitId() Group by the dosageUnit_id column
 * @method rlsNomenQuery groupBydrugLifetime() Group by the drugLifetime column
 * @method rlsNomenQuery groupByregDate() Group by the regDate column
 * @method rlsNomenQuery groupByannDate() Group by the annDate column
 *
 * @method rlsNomenQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method rlsNomenQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method rlsNomenQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method rlsNomenQuery leftJoinrbUnitRelatedByunitId($relationAlias = null) Adds a LEFT JOIN clause to the query using the rbUnitRelatedByunitId relation
 * @method rlsNomenQuery rightJoinrbUnitRelatedByunitId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rbUnitRelatedByunitId relation
 * @method rlsNomenQuery innerJoinrbUnitRelatedByunitId($relationAlias = null) Adds a INNER JOIN clause to the query using the rbUnitRelatedByunitId relation
 *
 * @method rlsNomenQuery leftJoinrbUnitRelatedBydosageUnitId($relationAlias = null) Adds a LEFT JOIN clause to the query using the rbUnitRelatedBydosageUnitId relation
 * @method rlsNomenQuery rightJoinrbUnitRelatedBydosageUnitId($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rbUnitRelatedBydosageUnitId relation
 * @method rlsNomenQuery innerJoinrbUnitRelatedBydosageUnitId($relationAlias = null) Adds a INNER JOIN clause to the query using the rbUnitRelatedBydosageUnitId relation
 *
 * @method rlsNomenQuery leftJoinrlsFilling($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsFilling relation
 * @method rlsNomenQuery rightJoinrlsFilling($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsFilling relation
 * @method rlsNomenQuery innerJoinrlsFilling($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsFilling relation
 *
 * @method rlsNomenQuery leftJoinrlsForm($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsForm relation
 * @method rlsNomenQuery rightJoinrlsForm($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsForm relation
 * @method rlsNomenQuery innerJoinrlsForm($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsForm relation
 *
 * @method rlsNomenQuery leftJoinrlsPacking($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsPacking relation
 * @method rlsNomenQuery rightJoinrlsPacking($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsPacking relation
 * @method rlsNomenQuery innerJoinrlsPacking($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsPacking relation
 *
 * @method rlsNomenQuery leftJoinrlsActMatters($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsActMatters relation
 * @method rlsNomenQuery rightJoinrlsActMatters($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsActMatters relation
 * @method rlsNomenQuery innerJoinrlsActMatters($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsActMatters relation
 *
 * @method rlsNomenQuery leftJoinrlsTradeName($relationAlias = null) Adds a LEFT JOIN clause to the query using the rlsTradeName relation
 * @method rlsNomenQuery rightJoinrlsTradeName($relationAlias = null) Adds a RIGHT JOIN clause to the query using the rlsTradeName relation
 * @method rlsNomenQuery innerJoinrlsTradeName($relationAlias = null) Adds a INNER JOIN clause to the query using the rlsTradeName relation
 *
 * @method rlsNomenQuery leftJoinDrugComponent($relationAlias = null) Adds a LEFT JOIN clause to the query using the DrugComponent relation
 * @method rlsNomenQuery rightJoinDrugComponent($relationAlias = null) Adds a RIGHT JOIN clause to the query using the DrugComponent relation
 * @method rlsNomenQuery innerJoinDrugComponent($relationAlias = null) Adds a INNER JOIN clause to the query using the DrugComponent relation
 *
 * @method rlsNomenQuery leftJoinRlsBalanceOfGoods($relationAlias = null) Adds a LEFT JOIN clause to the query using the RlsBalanceOfGoods relation
 * @method rlsNomenQuery rightJoinRlsBalanceOfGoods($relationAlias = null) Adds a RIGHT JOIN clause to the query using the RlsBalanceOfGoods relation
 * @method rlsNomenQuery innerJoinRlsBalanceOfGoods($relationAlias = null) Adds a INNER JOIN clause to the query using the RlsBalanceOfGoods relation
 *
 * @method rlsNomen findOne(PropelPDO $con = null) Return the first rlsNomen matching the query
 * @method rlsNomen findOneOrCreate(PropelPDO $con = null) Return the first rlsNomen matching the query, or a new rlsNomen object populated from the query conditions when no match is found
 *
 * @method rlsNomen findOneByactMattersId(int $actMatters_id) Return the first rlsNomen filtered by the actMatters_id column
 * @method rlsNomen findOneBytradeNameId(int $tradeName_id) Return the first rlsNomen filtered by the tradeName_id column
 * @method rlsNomen findOneByformId(int $form_id) Return the first rlsNomen filtered by the form_id column
 * @method rlsNomen findOneBypackingId(int $packing_id) Return the first rlsNomen filtered by the packing_id column
 * @method rlsNomen findOneByfillingId(int $filling_id) Return the first rlsNomen filtered by the filling_id column
 * @method rlsNomen findOneByunitId(int $unit_id) Return the first rlsNomen filtered by the unit_id column
 * @method rlsNomen findOneBydosageValue(string $dosageValue) Return the first rlsNomen filtered by the dosageValue column
 * @method rlsNomen findOneBydosageUnitId(int $dosageUnit_id) Return the first rlsNomen filtered by the dosageUnit_id column
 * @method rlsNomen findOneBydrugLifetime(int $drugLifetime) Return the first rlsNomen filtered by the drugLifetime column
 * @method rlsNomen findOneByregDate(string $regDate) Return the first rlsNomen filtered by the regDate column
 * @method rlsNomen findOneByannDate(string $annDate) Return the first rlsNomen filtered by the annDate column
 *
 * @method array findByid(int $id) Return rlsNomen objects filtered by the id column
 * @method array findByactMattersId(int $actMatters_id) Return rlsNomen objects filtered by the actMatters_id column
 * @method array findBytradeNameId(int $tradeName_id) Return rlsNomen objects filtered by the tradeName_id column
 * @method array findByformId(int $form_id) Return rlsNomen objects filtered by the form_id column
 * @method array findBypackingId(int $packing_id) Return rlsNomen objects filtered by the packing_id column
 * @method array findByfillingId(int $filling_id) Return rlsNomen objects filtered by the filling_id column
 * @method array findByunitId(int $unit_id) Return rlsNomen objects filtered by the unit_id column
 * @method array findBydosageValue(string $dosageValue) Return rlsNomen objects filtered by the dosageValue column
 * @method array findBydosageUnitId(int $dosageUnit_id) Return rlsNomen objects filtered by the dosageUnit_id column
 * @method array findBydrugLifetime(int $drugLifetime) Return rlsNomen objects filtered by the drugLifetime column
 * @method array findByregDate(string $regDate) Return rlsNomen objects filtered by the regDate column
 * @method array findByannDate(string $annDate) Return rlsNomen objects filtered by the annDate column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaserlsNomenQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaserlsNomenQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\rlsNomen', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new rlsNomenQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   rlsNomenQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return rlsNomenQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof rlsNomenQuery) {
            return $criteria;
        }
        $query = new rlsNomenQuery();
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
     * @return   rlsNomen|rlsNomen[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = rlsNomenPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(rlsNomenPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 rlsNomen A model object, or null if the key is not found
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
     * @return                 rlsNomen A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `actMatters_id`, `tradeName_id`, `form_id`, `packing_id`, `filling_id`, `unit_id`, `dosageValue`, `dosageUnit_id`, `drugLifetime`, `regDate`, `annDate` FROM `rlsNomen` WHERE `id` = :p0';
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
            $obj = new rlsNomen();
            $obj->hydrate($row);
            rlsNomenPeer::addInstanceToPool($obj, (string) $key);
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
     * @return rlsNomen|rlsNomen[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|rlsNomen[]|mixed the list of results, formatted by the current formatter
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
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(rlsNomenPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(rlsNomenPeer::ID, $keys, Criteria::IN);
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
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByid($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(rlsNomenPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(rlsNomenPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the actMatters_id column
     *
     * Example usage:
     * <code>
     * $query->filterByactMattersId(1234); // WHERE actMatters_id = 1234
     * $query->filterByactMattersId(array(12, 34)); // WHERE actMatters_id IN (12, 34)
     * $query->filterByactMattersId(array('min' => 12)); // WHERE actMatters_id >= 12
     * $query->filterByactMattersId(array('max' => 12)); // WHERE actMatters_id <= 12
     * </code>
     *
     * @see       filterByrlsActMatters()
     *
     * @param     mixed $actMattersId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByactMattersId($actMattersId = null, $comparison = null)
    {
        if (is_array($actMattersId)) {
            $useMinMax = false;
            if (isset($actMattersId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::ACTMATTERS_ID, $actMattersId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($actMattersId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::ACTMATTERS_ID, $actMattersId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::ACTMATTERS_ID, $actMattersId, $comparison);
    }

    /**
     * Filter the query on the tradeName_id column
     *
     * Example usage:
     * <code>
     * $query->filterBytradeNameId(1234); // WHERE tradeName_id = 1234
     * $query->filterBytradeNameId(array(12, 34)); // WHERE tradeName_id IN (12, 34)
     * $query->filterBytradeNameId(array('min' => 12)); // WHERE tradeName_id >= 12
     * $query->filterBytradeNameId(array('max' => 12)); // WHERE tradeName_id <= 12
     * </code>
     *
     * @see       filterByrlsTradeName()
     *
     * @param     mixed $tradeNameId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterBytradeNameId($tradeNameId = null, $comparison = null)
    {
        if (is_array($tradeNameId)) {
            $useMinMax = false;
            if (isset($tradeNameId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::TRADENAME_ID, $tradeNameId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($tradeNameId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::TRADENAME_ID, $tradeNameId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::TRADENAME_ID, $tradeNameId, $comparison);
    }

    /**
     * Filter the query on the form_id column
     *
     * Example usage:
     * <code>
     * $query->filterByformId(1234); // WHERE form_id = 1234
     * $query->filterByformId(array(12, 34)); // WHERE form_id IN (12, 34)
     * $query->filterByformId(array('min' => 12)); // WHERE form_id >= 12
     * $query->filterByformId(array('max' => 12)); // WHERE form_id <= 12
     * </code>
     *
     * @see       filterByrlsForm()
     *
     * @param     mixed $formId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByformId($formId = null, $comparison = null)
    {
        if (is_array($formId)) {
            $useMinMax = false;
            if (isset($formId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::FORM_ID, $formId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($formId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::FORM_ID, $formId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::FORM_ID, $formId, $comparison);
    }

    /**
     * Filter the query on the packing_id column
     *
     * Example usage:
     * <code>
     * $query->filterBypackingId(1234); // WHERE packing_id = 1234
     * $query->filterBypackingId(array(12, 34)); // WHERE packing_id IN (12, 34)
     * $query->filterBypackingId(array('min' => 12)); // WHERE packing_id >= 12
     * $query->filterBypackingId(array('max' => 12)); // WHERE packing_id <= 12
     * </code>
     *
     * @see       filterByrlsPacking()
     *
     * @param     mixed $packingId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterBypackingId($packingId = null, $comparison = null)
    {
        if (is_array($packingId)) {
            $useMinMax = false;
            if (isset($packingId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::PACKING_ID, $packingId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($packingId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::PACKING_ID, $packingId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::PACKING_ID, $packingId, $comparison);
    }

    /**
     * Filter the query on the filling_id column
     *
     * Example usage:
     * <code>
     * $query->filterByfillingId(1234); // WHERE filling_id = 1234
     * $query->filterByfillingId(array(12, 34)); // WHERE filling_id IN (12, 34)
     * $query->filterByfillingId(array('min' => 12)); // WHERE filling_id >= 12
     * $query->filterByfillingId(array('max' => 12)); // WHERE filling_id <= 12
     * </code>
     *
     * @see       filterByrlsFilling()
     *
     * @param     mixed $fillingId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByfillingId($fillingId = null, $comparison = null)
    {
        if (is_array($fillingId)) {
            $useMinMax = false;
            if (isset($fillingId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::FILLING_ID, $fillingId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($fillingId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::FILLING_ID, $fillingId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::FILLING_ID, $fillingId, $comparison);
    }

    /**
     * Filter the query on the unit_id column
     *
     * Example usage:
     * <code>
     * $query->filterByunitId(1234); // WHERE unit_id = 1234
     * $query->filterByunitId(array(12, 34)); // WHERE unit_id IN (12, 34)
     * $query->filterByunitId(array('min' => 12)); // WHERE unit_id >= 12
     * $query->filterByunitId(array('max' => 12)); // WHERE unit_id <= 12
     * </code>
     *
     * @see       filterByrbUnitRelatedByunitId()
     *
     * @param     mixed $unitId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByunitId($unitId = null, $comparison = null)
    {
        if (is_array($unitId)) {
            $useMinMax = false;
            if (isset($unitId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::UNIT_ID, $unitId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($unitId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::UNIT_ID, $unitId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::UNIT_ID, $unitId, $comparison);
    }

    /**
     * Filter the query on the dosageValue column
     *
     * Example usage:
     * <code>
     * $query->filterBydosageValue('fooValue');   // WHERE dosageValue = 'fooValue'
     * $query->filterBydosageValue('%fooValue%'); // WHERE dosageValue LIKE '%fooValue%'
     * </code>
     *
     * @param     string $dosageValue The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterBydosageValue($dosageValue = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($dosageValue)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $dosageValue)) {
                $dosageValue = str_replace('*', '%', $dosageValue);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::DOSAGEVALUE, $dosageValue, $comparison);
    }

    /**
     * Filter the query on the dosageUnit_id column
     *
     * Example usage:
     * <code>
     * $query->filterBydosageUnitId(1234); // WHERE dosageUnit_id = 1234
     * $query->filterBydosageUnitId(array(12, 34)); // WHERE dosageUnit_id IN (12, 34)
     * $query->filterBydosageUnitId(array('min' => 12)); // WHERE dosageUnit_id >= 12
     * $query->filterBydosageUnitId(array('max' => 12)); // WHERE dosageUnit_id <= 12
     * </code>
     *
     * @see       filterByrbUnitRelatedBydosageUnitId()
     *
     * @param     mixed $dosageUnitId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterBydosageUnitId($dosageUnitId = null, $comparison = null)
    {
        if (is_array($dosageUnitId)) {
            $useMinMax = false;
            if (isset($dosageUnitId['min'])) {
                $this->addUsingAlias(rlsNomenPeer::DOSAGEUNIT_ID, $dosageUnitId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dosageUnitId['max'])) {
                $this->addUsingAlias(rlsNomenPeer::DOSAGEUNIT_ID, $dosageUnitId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::DOSAGEUNIT_ID, $dosageUnitId, $comparison);
    }

    /**
     * Filter the query on the drugLifetime column
     *
     * Example usage:
     * <code>
     * $query->filterBydrugLifetime(1234); // WHERE drugLifetime = 1234
     * $query->filterBydrugLifetime(array(12, 34)); // WHERE drugLifetime IN (12, 34)
     * $query->filterBydrugLifetime(array('min' => 12)); // WHERE drugLifetime >= 12
     * $query->filterBydrugLifetime(array('max' => 12)); // WHERE drugLifetime <= 12
     * </code>
     *
     * @param     mixed $drugLifetime The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterBydrugLifetime($drugLifetime = null, $comparison = null)
    {
        if (is_array($drugLifetime)) {
            $useMinMax = false;
            if (isset($drugLifetime['min'])) {
                $this->addUsingAlias(rlsNomenPeer::DRUGLIFETIME, $drugLifetime['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($drugLifetime['max'])) {
                $this->addUsingAlias(rlsNomenPeer::DRUGLIFETIME, $drugLifetime['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::DRUGLIFETIME, $drugLifetime, $comparison);
    }

    /**
     * Filter the query on the regDate column
     *
     * Example usage:
     * <code>
     * $query->filterByregDate('2011-03-14'); // WHERE regDate = '2011-03-14'
     * $query->filterByregDate('now'); // WHERE regDate = '2011-03-14'
     * $query->filterByregDate(array('max' => 'yesterday')); // WHERE regDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $regDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByregDate($regDate = null, $comparison = null)
    {
        if (is_array($regDate)) {
            $useMinMax = false;
            if (isset($regDate['min'])) {
                $this->addUsingAlias(rlsNomenPeer::REGDATE, $regDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($regDate['max'])) {
                $this->addUsingAlias(rlsNomenPeer::REGDATE, $regDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::REGDATE, $regDate, $comparison);
    }

    /**
     * Filter the query on the annDate column
     *
     * Example usage:
     * <code>
     * $query->filterByannDate('2011-03-14'); // WHERE annDate = '2011-03-14'
     * $query->filterByannDate('now'); // WHERE annDate = '2011-03-14'
     * $query->filterByannDate(array('max' => 'yesterday')); // WHERE annDate > '2011-03-13'
     * </code>
     *
     * @param     mixed $annDate The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function filterByannDate($annDate = null, $comparison = null)
    {
        if (is_array($annDate)) {
            $useMinMax = false;
            if (isset($annDate['min'])) {
                $this->addUsingAlias(rlsNomenPeer::ANNDATE, $annDate['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($annDate['max'])) {
                $this->addUsingAlias(rlsNomenPeer::ANNDATE, $annDate['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(rlsNomenPeer::ANNDATE, $annDate, $comparison);
    }

    /**
     * Filter the query by a related rbUnit object
     *
     * @param   rbUnit|PropelObjectCollection $rbUnit The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrbUnitRelatedByunitId($rbUnit, $comparison = null)
    {
        if ($rbUnit instanceof rbUnit) {
            return $this
                ->addUsingAlias(rlsNomenPeer::UNIT_ID, $rbUnit->getid(), $comparison);
        } elseif ($rbUnit instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::UNIT_ID, $rbUnit->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrbUnitRelatedByunitId() only accepts arguments of type rbUnit or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rbUnitRelatedByunitId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrbUnitRelatedByunitId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rbUnitRelatedByunitId');

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
            $this->addJoinObject($join, 'rbUnitRelatedByunitId');
        }

        return $this;
    }

    /**
     * Use the rbUnitRelatedByunitId relation rbUnit object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rbUnitQuery A secondary query class using the current class as primary query
     */
    public function userbUnitRelatedByunitIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrbUnitRelatedByunitId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rbUnitRelatedByunitId', '\Webmis\Models\rbUnitQuery');
    }

    /**
     * Filter the query by a related rbUnit object
     *
     * @param   rbUnit|PropelObjectCollection $rbUnit The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrbUnitRelatedBydosageUnitId($rbUnit, $comparison = null)
    {
        if ($rbUnit instanceof rbUnit) {
            return $this
                ->addUsingAlias(rlsNomenPeer::DOSAGEUNIT_ID, $rbUnit->getid(), $comparison);
        } elseif ($rbUnit instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::DOSAGEUNIT_ID, $rbUnit->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrbUnitRelatedBydosageUnitId() only accepts arguments of type rbUnit or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rbUnitRelatedBydosageUnitId relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrbUnitRelatedBydosageUnitId($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rbUnitRelatedBydosageUnitId');

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
            $this->addJoinObject($join, 'rbUnitRelatedBydosageUnitId');
        }

        return $this;
    }

    /**
     * Use the rbUnitRelatedBydosageUnitId relation rbUnit object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rbUnitQuery A secondary query class using the current class as primary query
     */
    public function userbUnitRelatedBydosageUnitIdQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrbUnitRelatedBydosageUnitId($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rbUnitRelatedBydosageUnitId', '\Webmis\Models\rbUnitQuery');
    }

    /**
     * Filter the query by a related rlsFilling object
     *
     * @param   rlsFilling|PropelObjectCollection $rlsFilling The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsFilling($rlsFilling, $comparison = null)
    {
        if ($rlsFilling instanceof rlsFilling) {
            return $this
                ->addUsingAlias(rlsNomenPeer::FILLING_ID, $rlsFilling->getid(), $comparison);
        } elseif ($rlsFilling instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::FILLING_ID, $rlsFilling->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsFilling() only accepts arguments of type rlsFilling or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsFilling relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrlsFilling($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsFilling');

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
            $this->addJoinObject($join, 'rlsFilling');
        }

        return $this;
    }

    /**
     * Use the rlsFilling relation rlsFilling object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsFillingQuery A secondary query class using the current class as primary query
     */
    public function userlsFillingQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsFilling($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsFilling', '\Webmis\Models\rlsFillingQuery');
    }

    /**
     * Filter the query by a related rlsForm object
     *
     * @param   rlsForm|PropelObjectCollection $rlsForm The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsForm($rlsForm, $comparison = null)
    {
        if ($rlsForm instanceof rlsForm) {
            return $this
                ->addUsingAlias(rlsNomenPeer::FORM_ID, $rlsForm->getid(), $comparison);
        } elseif ($rlsForm instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::FORM_ID, $rlsForm->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsForm() only accepts arguments of type rlsForm or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsForm relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrlsForm($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsForm');

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
            $this->addJoinObject($join, 'rlsForm');
        }

        return $this;
    }

    /**
     * Use the rlsForm relation rlsForm object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsFormQuery A secondary query class using the current class as primary query
     */
    public function userlsFormQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsForm($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsForm', '\Webmis\Models\rlsFormQuery');
    }

    /**
     * Filter the query by a related rlsPacking object
     *
     * @param   rlsPacking|PropelObjectCollection $rlsPacking The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsPacking($rlsPacking, $comparison = null)
    {
        if ($rlsPacking instanceof rlsPacking) {
            return $this
                ->addUsingAlias(rlsNomenPeer::PACKING_ID, $rlsPacking->getid(), $comparison);
        } elseif ($rlsPacking instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::PACKING_ID, $rlsPacking->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsPacking() only accepts arguments of type rlsPacking or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsPacking relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrlsPacking($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsPacking');

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
            $this->addJoinObject($join, 'rlsPacking');
        }

        return $this;
    }

    /**
     * Use the rlsPacking relation rlsPacking object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsPackingQuery A secondary query class using the current class as primary query
     */
    public function userlsPackingQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsPacking($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsPacking', '\Webmis\Models\rlsPackingQuery');
    }

    /**
     * Filter the query by a related rlsActMatters object
     *
     * @param   rlsActMatters|PropelObjectCollection $rlsActMatters The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsActMatters($rlsActMatters, $comparison = null)
    {
        if ($rlsActMatters instanceof rlsActMatters) {
            return $this
                ->addUsingAlias(rlsNomenPeer::ACTMATTERS_ID, $rlsActMatters->getid(), $comparison);
        } elseif ($rlsActMatters instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::ACTMATTERS_ID, $rlsActMatters->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsActMatters() only accepts arguments of type rlsActMatters or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsActMatters relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrlsActMatters($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsActMatters');

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
            $this->addJoinObject($join, 'rlsActMatters');
        }

        return $this;
    }

    /**
     * Use the rlsActMatters relation rlsActMatters object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsActMattersQuery A secondary query class using the current class as primary query
     */
    public function userlsActMattersQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinrlsActMatters($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsActMatters', '\Webmis\Models\rlsActMattersQuery');
    }

    /**
     * Filter the query by a related rlsTradeName object
     *
     * @param   rlsTradeName|PropelObjectCollection $rlsTradeName The related object(s) to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByrlsTradeName($rlsTradeName, $comparison = null)
    {
        if ($rlsTradeName instanceof rlsTradeName) {
            return $this
                ->addUsingAlias(rlsNomenPeer::TRADENAME_ID, $rlsTradeName->getid(), $comparison);
        } elseif ($rlsTradeName instanceof PropelObjectCollection) {
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }

            return $this
                ->addUsingAlias(rlsNomenPeer::TRADENAME_ID, $rlsTradeName->toKeyValue('PrimaryKey', 'id'), $comparison);
        } else {
            throw new PropelException('filterByrlsTradeName() only accepts arguments of type rlsTradeName or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the rlsTradeName relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinrlsTradeName($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('rlsTradeName');

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
            $this->addJoinObject($join, 'rlsTradeName');
        }

        return $this;
    }

    /**
     * Use the rlsTradeName relation rlsTradeName object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\rlsTradeNameQuery A secondary query class using the current class as primary query
     */
    public function userlsTradeNameQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinrlsTradeName($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'rlsTradeName', '\Webmis\Models\rlsTradeNameQuery');
    }

    /**
     * Filter the query by a related DrugComponent object
     *
     * @param   DrugComponent|PropelObjectCollection $drugComponent  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByDrugComponent($drugComponent, $comparison = null)
    {
        if ($drugComponent instanceof DrugComponent) {
            return $this
                ->addUsingAlias(rlsNomenPeer::ID, $drugComponent->getnomen(), $comparison);
        } elseif ($drugComponent instanceof PropelObjectCollection) {
            return $this
                ->useDrugComponentQuery()
                ->filterByPrimaryKeys($drugComponent->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByDrugComponent() only accepts arguments of type DrugComponent or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the DrugComponent relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinDrugComponent($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('DrugComponent');

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
            $this->addJoinObject($join, 'DrugComponent');
        }

        return $this;
    }

    /**
     * Use the DrugComponent relation DrugComponent object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\DrugComponentQuery A secondary query class using the current class as primary query
     */
    public function useDrugComponentQuery($relationAlias = null, $joinType = Criteria::LEFT_JOIN)
    {
        return $this
            ->joinDrugComponent($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'DrugComponent', '\Webmis\Models\DrugComponentQuery');
    }

    /**
     * Filter the query by a related RlsBalanceOfGoods object
     *
     * @param   RlsBalanceOfGoods|PropelObjectCollection $rlsBalanceOfGoods  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 rlsNomenQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByRlsBalanceOfGoods($rlsBalanceOfGoods, $comparison = null)
    {
        if ($rlsBalanceOfGoods instanceof RlsBalanceOfGoods) {
            return $this
                ->addUsingAlias(rlsNomenPeer::ID, $rlsBalanceOfGoods->getrlsNomenId(), $comparison);
        } elseif ($rlsBalanceOfGoods instanceof PropelObjectCollection) {
            return $this
                ->useRlsBalanceOfGoodsQuery()
                ->filterByPrimaryKeys($rlsBalanceOfGoods->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByRlsBalanceOfGoods() only accepts arguments of type RlsBalanceOfGoods or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the RlsBalanceOfGoods relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function joinRlsBalanceOfGoods($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('RlsBalanceOfGoods');

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
            $this->addJoinObject($join, 'RlsBalanceOfGoods');
        }

        return $this;
    }

    /**
     * Use the RlsBalanceOfGoods relation RlsBalanceOfGoods object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\RlsBalanceOfGoodsQuery A secondary query class using the current class as primary query
     */
    public function useRlsBalanceOfGoodsQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinRlsBalanceOfGoods($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'RlsBalanceOfGoods', '\Webmis\Models\RlsBalanceOfGoodsQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   rlsNomen $rlsNomen Object to remove from the list of results
     *
     * @return rlsNomenQuery The current query, for fluid interface
     */
    public function prune($rlsNomen = null)
    {
        if ($rlsNomen) {
            $this->addUsingAlias(rlsNomenPeer::ID, $rlsNomen->getid(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
