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
use Webmis\Models\ActionPropertyFDRecord;
use Webmis\Models\FDFieldValue;
use Webmis\Models\FDRecord;
use Webmis\Models\FDRecordPeer;
use Webmis\Models\FDRecordQuery;

/**
 * Base class that represents a query for the 'FDRecord' table.
 *
 *
 *
 * @method FDRecordQuery orderById($order = Criteria::ASC) Order by the id column
 * @method FDRecordQuery orderByFlatDirectoryId($order = Criteria::ASC) Order by the flatDirectory_id column
 * @method FDRecordQuery orderByFlatDirectoryCode($order = Criteria::ASC) Order by the flatDirectory_code column
 * @method FDRecordQuery orderByOrder($order = Criteria::ASC) Order by the order column
 * @method FDRecordQuery orderByName($order = Criteria::ASC) Order by the name column
 * @method FDRecordQuery orderByDescription($order = Criteria::ASC) Order by the description column
 * @method FDRecordQuery orderByDateStart($order = Criteria::ASC) Order by the dateStart column
 * @method FDRecordQuery orderByDateEnd($order = Criteria::ASC) Order by the dateEnd column
 *
 * @method FDRecordQuery groupById() Group by the id column
 * @method FDRecordQuery groupByFlatDirectoryId() Group by the flatDirectory_id column
 * @method FDRecordQuery groupByFlatDirectoryCode() Group by the flatDirectory_code column
 * @method FDRecordQuery groupByOrder() Group by the order column
 * @method FDRecordQuery groupByName() Group by the name column
 * @method FDRecordQuery groupByDescription() Group by the description column
 * @method FDRecordQuery groupByDateStart() Group by the dateStart column
 * @method FDRecordQuery groupByDateEnd() Group by the dateEnd column
 *
 * @method FDRecordQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method FDRecordQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method FDRecordQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method FDRecordQuery leftJoinActionPropertyFDRecord($relationAlias = null) Adds a LEFT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method FDRecordQuery rightJoinActionPropertyFDRecord($relationAlias = null) Adds a RIGHT JOIN clause to the query using the ActionPropertyFDRecord relation
 * @method FDRecordQuery innerJoinActionPropertyFDRecord($relationAlias = null) Adds a INNER JOIN clause to the query using the ActionPropertyFDRecord relation
 *
 * @method FDRecordQuery leftJoinFDFieldValue($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDFieldValue relation
 * @method FDRecordQuery rightJoinFDFieldValue($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDFieldValue relation
 * @method FDRecordQuery innerJoinFDFieldValue($relationAlias = null) Adds a INNER JOIN clause to the query using the FDFieldValue relation
 *
 * @method FDRecord findOne(PropelPDO $con = null) Return the first FDRecord matching the query
 * @method FDRecord findOneOrCreate(PropelPDO $con = null) Return the first FDRecord matching the query, or a new FDRecord object populated from the query conditions when no match is found
 *
 * @method FDRecord findOneByFlatDirectoryId(int $flatDirectory_id) Return the first FDRecord filtered by the flatDirectory_id column
 * @method FDRecord findOneByFlatDirectoryCode(string $flatDirectory_code) Return the first FDRecord filtered by the flatDirectory_code column
 * @method FDRecord findOneByOrder(int $order) Return the first FDRecord filtered by the order column
 * @method FDRecord findOneByName(string $name) Return the first FDRecord filtered by the name column
 * @method FDRecord findOneByDescription(string $description) Return the first FDRecord filtered by the description column
 * @method FDRecord findOneByDateStart(string $dateStart) Return the first FDRecord filtered by the dateStart column
 * @method FDRecord findOneByDateEnd(string $dateEnd) Return the first FDRecord filtered by the dateEnd column
 *
 * @method array findById(int $id) Return FDRecord objects filtered by the id column
 * @method array findByFlatDirectoryId(int $flatDirectory_id) Return FDRecord objects filtered by the flatDirectory_id column
 * @method array findByFlatDirectoryCode(string $flatDirectory_code) Return FDRecord objects filtered by the flatDirectory_code column
 * @method array findByOrder(int $order) Return FDRecord objects filtered by the order column
 * @method array findByName(string $name) Return FDRecord objects filtered by the name column
 * @method array findByDescription(string $description) Return FDRecord objects filtered by the description column
 * @method array findByDateStart(string $dateStart) Return FDRecord objects filtered by the dateStart column
 * @method array findByDateEnd(string $dateEnd) Return FDRecord objects filtered by the dateEnd column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseFDRecordQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseFDRecordQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\FDRecord', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new FDRecordQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   FDRecordQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return FDRecordQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof FDRecordQuery) {
            return $criteria;
        }
        $query = new FDRecordQuery();
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
     * @return   FDRecord|FDRecord[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = FDRecordPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(FDRecordPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 FDRecord A model object, or null if the key is not found
     * @throws PropelException
     */
     public function findOneById($key, $con = null)
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
     * @return                 FDRecord A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `flatDirectory_id`, `flatDirectory_code`, `order`, `name`, `description`, `dateStart`, `dateEnd` FROM `FDRecord` WHERE `id` = :p0';
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
            $obj = new FDRecord();
            $obj->hydrate($row);
            FDRecordPeer::addInstanceToPool($obj, (string) $key);
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
     * @return FDRecord|FDRecord[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|FDRecord[]|mixed the list of results, formatted by the current formatter
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
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(FDRecordPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(FDRecordPeer::ID, $keys, Criteria::IN);
    }

    /**
     * Filter the query on the id column
     *
     * Example usage:
     * <code>
     * $query->filterById(1234); // WHERE id = 1234
     * $query->filterById(array(12, 34)); // WHERE id IN (12, 34)
     * $query->filterById(array('min' => 12)); // WHERE id >= 12
     * $query->filterById(array('max' => 12)); // WHERE id <= 12
     * </code>
     *
     * @param     mixed $id The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(FDRecordPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(FDRecordPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the flatDirectory_id column
     *
     * Example usage:
     * <code>
     * $query->filterByFlatDirectoryId(1234); // WHERE flatDirectory_id = 1234
     * $query->filterByFlatDirectoryId(array(12, 34)); // WHERE flatDirectory_id IN (12, 34)
     * $query->filterByFlatDirectoryId(array('min' => 12)); // WHERE flatDirectory_id >= 12
     * $query->filterByFlatDirectoryId(array('max' => 12)); // WHERE flatDirectory_id <= 12
     * </code>
     *
     * @param     mixed $flatDirectoryId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByFlatDirectoryId($flatDirectoryId = null, $comparison = null)
    {
        if (is_array($flatDirectoryId)) {
            $useMinMax = false;
            if (isset($flatDirectoryId['min'])) {
                $this->addUsingAlias(FDRecordPeer::FLATDIRECTORY_ID, $flatDirectoryId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($flatDirectoryId['max'])) {
                $this->addUsingAlias(FDRecordPeer::FLATDIRECTORY_ID, $flatDirectoryId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::FLATDIRECTORY_ID, $flatDirectoryId, $comparison);
    }

    /**
     * Filter the query on the flatDirectory_code column
     *
     * Example usage:
     * <code>
     * $query->filterByFlatDirectoryCode('fooValue');   // WHERE flatDirectory_code = 'fooValue'
     * $query->filterByFlatDirectoryCode('%fooValue%'); // WHERE flatDirectory_code LIKE '%fooValue%'
     * </code>
     *
     * @param     string $flatDirectoryCode The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByFlatDirectoryCode($flatDirectoryCode = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($flatDirectoryCode)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $flatDirectoryCode)) {
                $flatDirectoryCode = str_replace('*', '%', $flatDirectoryCode);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::FLATDIRECTORY_CODE, $flatDirectoryCode, $comparison);
    }

    /**
     * Filter the query on the order column
     *
     * Example usage:
     * <code>
     * $query->filterByOrder(1234); // WHERE order = 1234
     * $query->filterByOrder(array(12, 34)); // WHERE order IN (12, 34)
     * $query->filterByOrder(array('min' => 12)); // WHERE order >= 12
     * $query->filterByOrder(array('max' => 12)); // WHERE order <= 12
     * </code>
     *
     * @param     mixed $order The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByOrder($order = null, $comparison = null)
    {
        if (is_array($order)) {
            $useMinMax = false;
            if (isset($order['min'])) {
                $this->addUsingAlias(FDRecordPeer::ORDER, $order['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($order['max'])) {
                $this->addUsingAlias(FDRecordPeer::ORDER, $order['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::ORDER, $order, $comparison);
    }

    /**
     * Filter the query on the name column
     *
     * Example usage:
     * <code>
     * $query->filterByName('fooValue');   // WHERE name = 'fooValue'
     * $query->filterByName('%fooValue%'); // WHERE name LIKE '%fooValue%'
     * </code>
     *
     * @param     string $name The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByName($name = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($name)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $name)) {
                $name = str_replace('*', '%', $name);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::NAME, $name, $comparison);
    }

    /**
     * Filter the query on the description column
     *
     * Example usage:
     * <code>
     * $query->filterByDescription('fooValue');   // WHERE description = 'fooValue'
     * $query->filterByDescription('%fooValue%'); // WHERE description LIKE '%fooValue%'
     * </code>
     *
     * @param     string $description The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByDescription($description = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($description)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $description)) {
                $description = str_replace('*', '%', $description);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::DESCRIPTION, $description, $comparison);
    }

    /**
     * Filter the query on the dateStart column
     *
     * Example usage:
     * <code>
     * $query->filterByDateStart('2011-03-14'); // WHERE dateStart = '2011-03-14'
     * $query->filterByDateStart('now'); // WHERE dateStart = '2011-03-14'
     * $query->filterByDateStart(array('max' => 'yesterday')); // WHERE dateStart > '2011-03-13'
     * </code>
     *
     * @param     mixed $dateStart The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByDateStart($dateStart = null, $comparison = null)
    {
        if (is_array($dateStart)) {
            $useMinMax = false;
            if (isset($dateStart['min'])) {
                $this->addUsingAlias(FDRecordPeer::DATESTART, $dateStart['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dateStart['max'])) {
                $this->addUsingAlias(FDRecordPeer::DATESTART, $dateStart['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::DATESTART, $dateStart, $comparison);
    }

    /**
     * Filter the query on the dateEnd column
     *
     * Example usage:
     * <code>
     * $query->filterByDateEnd('2011-03-14'); // WHERE dateEnd = '2011-03-14'
     * $query->filterByDateEnd('now'); // WHERE dateEnd = '2011-03-14'
     * $query->filterByDateEnd(array('max' => 'yesterday')); // WHERE dateEnd > '2011-03-13'
     * </code>
     *
     * @param     mixed $dateEnd The value to use as filter.
     *              Values can be integers (unix timestamps), DateTime objects, or strings.
     *              Empty strings are treated as NULL.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function filterByDateEnd($dateEnd = null, $comparison = null)
    {
        if (is_array($dateEnd)) {
            $useMinMax = false;
            if (isset($dateEnd['min'])) {
                $this->addUsingAlias(FDRecordPeer::DATEEND, $dateEnd['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($dateEnd['max'])) {
                $this->addUsingAlias(FDRecordPeer::DATEEND, $dateEnd['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDRecordPeer::DATEEND, $dateEnd, $comparison);
    }

    /**
     * Filter the query by a related ActionPropertyFDRecord object
     *
     * @param   ActionPropertyFDRecord|PropelObjectCollection $actionPropertyFDRecord  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDRecordQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByActionPropertyFDRecord($actionPropertyFDRecord, $comparison = null)
    {
        if ($actionPropertyFDRecord instanceof ActionPropertyFDRecord) {
            return $this
                ->addUsingAlias(FDRecordPeer::ID, $actionPropertyFDRecord->getValue(), $comparison);
        } elseif ($actionPropertyFDRecord instanceof PropelObjectCollection) {
            return $this
                ->useActionPropertyFDRecordQuery()
                ->filterByPrimaryKeys($actionPropertyFDRecord->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByActionPropertyFDRecord() only accepts arguments of type ActionPropertyFDRecord or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the ActionPropertyFDRecord relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function joinActionPropertyFDRecord($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('ActionPropertyFDRecord');

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
            $this->addJoinObject($join, 'ActionPropertyFDRecord');
        }

        return $this;
    }

    /**
     * Use the ActionPropertyFDRecord relation ActionPropertyFDRecord object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\ActionPropertyFDRecordQuery A secondary query class using the current class as primary query
     */
    public function useActionPropertyFDRecordQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinActionPropertyFDRecord($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'ActionPropertyFDRecord', '\Webmis\Models\ActionPropertyFDRecordQuery');
    }

    /**
     * Filter the query by a related FDFieldValue object
     *
     * @param   FDFieldValue|PropelObjectCollection $fDFieldValue  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDRecordQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDFieldValue($fDFieldValue, $comparison = null)
    {
        if ($fDFieldValue instanceof FDFieldValue) {
            return $this
                ->addUsingAlias(FDRecordPeer::ID, $fDFieldValue->getFDRecordId(), $comparison);
        } elseif ($fDFieldValue instanceof PropelObjectCollection) {
            return $this
                ->useFDFieldValueQuery()
                ->filterByPrimaryKeys($fDFieldValue->getPrimaryKeys())
                ->endUse();
        } else {
            throw new PropelException('filterByFDFieldValue() only accepts arguments of type FDFieldValue or PropelCollection');
        }
    }

    /**
     * Adds a JOIN clause to the query using the FDFieldValue relation
     *
     * @param     string $relationAlias optional alias for the relation
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function joinFDFieldValue($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        $tableMap = $this->getTableMap();
        $relationMap = $tableMap->getRelation('FDFieldValue');

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
            $this->addJoinObject($join, 'FDFieldValue');
        }

        return $this;
    }

    /**
     * Use the FDFieldValue relation FDFieldValue object
     *
     * @see       useQuery()
     *
     * @param     string $relationAlias optional alias for the relation,
     *                                   to be used as main alias in the secondary query
     * @param     string $joinType Accepted values are null, 'left join', 'right join', 'inner join'
     *
     * @return   \Webmis\Models\FDFieldValueQuery A secondary query class using the current class as primary query
     */
    public function useFDFieldValueQuery($relationAlias = null, $joinType = Criteria::INNER_JOIN)
    {
        return $this
            ->joinFDFieldValue($relationAlias, $joinType)
            ->useQuery($relationAlias ? $relationAlias : 'FDFieldValue', '\Webmis\Models\FDFieldValueQuery');
    }

    /**
     * Exclude object from result
     *
     * @param   FDRecord $fDRecord Object to remove from the list of results
     *
     * @return FDRecordQuery The current query, for fluid interface
     */
    public function prune($fDRecord = null)
    {
        if ($fDRecord) {
            $this->addUsingAlias(FDRecordPeer::ID, $fDRecord->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
