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
use Webmis\Models\FDField;
use Webmis\Models\FDFieldPeer;
use Webmis\Models\FDFieldQuery;
use Webmis\Models\FDFieldValue;

/**
 * Base class that represents a query for the 'FDField' table.
 *
 *
 *
 * @method FDFieldQuery orderById($order = Criteria::ASC) Order by the id column
 * @method FDFieldQuery orderByFDFieldTypeId($order = Criteria::ASC) Order by the fdFieldType_id column
 * @method FDFieldQuery orderByFlatDirectoryId($order = Criteria::ASC) Order by the flatDirectory_id column
 * @method FDFieldQuery orderByFlatDirectoryCode($order = Criteria::ASC) Order by the flatDirectory_code column
 * @method FDFieldQuery orderByName($order = Criteria::ASC) Order by the name column
 * @method FDFieldQuery orderByDescription($order = Criteria::ASC) Order by the description column
 * @method FDFieldQuery orderByMask($order = Criteria::ASC) Order by the mask column
 * @method FDFieldQuery orderByMandatory($order = Criteria::ASC) Order by the mandatory column
 * @method FDFieldQuery orderByOrder($order = Criteria::ASC) Order by the order column
 *
 * @method FDFieldQuery groupById() Group by the id column
 * @method FDFieldQuery groupByFDFieldTypeId() Group by the fdFieldType_id column
 * @method FDFieldQuery groupByFlatDirectoryId() Group by the flatDirectory_id column
 * @method FDFieldQuery groupByFlatDirectoryCode() Group by the flatDirectory_code column
 * @method FDFieldQuery groupByName() Group by the name column
 * @method FDFieldQuery groupByDescription() Group by the description column
 * @method FDFieldQuery groupByMask() Group by the mask column
 * @method FDFieldQuery groupByMandatory() Group by the mandatory column
 * @method FDFieldQuery groupByOrder() Group by the order column
 *
 * @method FDFieldQuery leftJoin($relation) Adds a LEFT JOIN clause to the query
 * @method FDFieldQuery rightJoin($relation) Adds a RIGHT JOIN clause to the query
 * @method FDFieldQuery innerJoin($relation) Adds a INNER JOIN clause to the query
 *
 * @method FDFieldQuery leftJoinFDFieldValue($relationAlias = null) Adds a LEFT JOIN clause to the query using the FDFieldValue relation
 * @method FDFieldQuery rightJoinFDFieldValue($relationAlias = null) Adds a RIGHT JOIN clause to the query using the FDFieldValue relation
 * @method FDFieldQuery innerJoinFDFieldValue($relationAlias = null) Adds a INNER JOIN clause to the query using the FDFieldValue relation
 *
 * @method FDField findOne(PropelPDO $con = null) Return the first FDField matching the query
 * @method FDField findOneOrCreate(PropelPDO $con = null) Return the first FDField matching the query, or a new FDField object populated from the query conditions when no match is found
 *
 * @method FDField findOneByFDFieldTypeId(int $fdFieldType_id) Return the first FDField filtered by the fdFieldType_id column
 * @method FDField findOneByFlatDirectoryId(int $flatDirectory_id) Return the first FDField filtered by the flatDirectory_id column
 * @method FDField findOneByFlatDirectoryCode(string $flatDirectory_code) Return the first FDField filtered by the flatDirectory_code column
 * @method FDField findOneByName(string $name) Return the first FDField filtered by the name column
 * @method FDField findOneByDescription(string $description) Return the first FDField filtered by the description column
 * @method FDField findOneByMask(string $mask) Return the first FDField filtered by the mask column
 * @method FDField findOneByMandatory(boolean $mandatory) Return the first FDField filtered by the mandatory column
 * @method FDField findOneByOrder(int $order) Return the first FDField filtered by the order column
 *
 * @method array findById(int $id) Return FDField objects filtered by the id column
 * @method array findByFDFieldTypeId(int $fdFieldType_id) Return FDField objects filtered by the fdFieldType_id column
 * @method array findByFlatDirectoryId(int $flatDirectory_id) Return FDField objects filtered by the flatDirectory_id column
 * @method array findByFlatDirectoryCode(string $flatDirectory_code) Return FDField objects filtered by the flatDirectory_code column
 * @method array findByName(string $name) Return FDField objects filtered by the name column
 * @method array findByDescription(string $description) Return FDField objects filtered by the description column
 * @method array findByMask(string $mask) Return FDField objects filtered by the mask column
 * @method array findByMandatory(boolean $mandatory) Return FDField objects filtered by the mandatory column
 * @method array findByOrder(int $order) Return FDField objects filtered by the order column
 *
 * @package    propel.generator.Models.om
 */
abstract class BaseFDFieldQuery extends ModelCriteria
{
    /**
     * Initializes internal state of BaseFDFieldQuery object.
     *
     * @param     string $dbName The dabase name
     * @param     string $modelName The phpName of a model, e.g. 'Book'
     * @param     string $modelAlias The alias for the model in this query, e.g. 'b'
     */
    public function __construct($dbName = 'Webmis-API', $modelName = 'Webmis\\Models\\FDField', $modelAlias = null)
    {
        parent::__construct($dbName, $modelName, $modelAlias);
    }

    /**
     * Returns a new FDFieldQuery object.
     *
     * @param     string $modelAlias The alias of a model in the query
     * @param   FDFieldQuery|Criteria $criteria Optional Criteria to build the query from
     *
     * @return FDFieldQuery
     */
    public static function create($modelAlias = null, $criteria = null)
    {
        if ($criteria instanceof FDFieldQuery) {
            return $criteria;
        }
        $query = new FDFieldQuery();
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
     * @return   FDField|FDField[]|mixed the result, formatted by the current formatter
     */
    public function findPk($key, $con = null)
    {
        if ($key === null) {
            return null;
        }
        if ((null !== ($obj = FDFieldPeer::getInstanceFromPool((string) $key))) && !$this->formatter) {
            // the object is alredy in the instance pool
            return $obj;
        }
        if ($con === null) {
            $con = Propel::getConnection(FDFieldPeer::DATABASE_NAME, Propel::CONNECTION_READ);
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
     * @return                 FDField A model object, or null if the key is not found
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
     * @return                 FDField A model object, or null if the key is not found
     * @throws PropelException
     */
    protected function findPkSimple($key, $con)
    {
        $sql = 'SELECT `id`, `fdFieldType_id`, `flatDirectory_id`, `flatDirectory_code`, `name`, `description`, `mask`, `mandatory`, `order` FROM `FDField` WHERE `id` = :p0';
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
            $obj = new FDField();
            $obj->hydrate($row);
            FDFieldPeer::addInstanceToPool($obj, (string) $key);
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
     * @return FDField|FDField[]|mixed the result, formatted by the current formatter
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
     * @return PropelObjectCollection|FDField[]|mixed the list of results, formatted by the current formatter
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
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByPrimaryKey($key)
    {

        return $this->addUsingAlias(FDFieldPeer::ID, $key, Criteria::EQUAL);
    }

    /**
     * Filter the query by a list of primary keys
     *
     * @param     array $keys The list of primary key to use for the query
     *
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByPrimaryKeys($keys)
    {

        return $this->addUsingAlias(FDFieldPeer::ID, $keys, Criteria::IN);
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
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterById($id = null, $comparison = null)
    {
        if (is_array($id)) {
            $useMinMax = false;
            if (isset($id['min'])) {
                $this->addUsingAlias(FDFieldPeer::ID, $id['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($id['max'])) {
                $this->addUsingAlias(FDFieldPeer::ID, $id['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldPeer::ID, $id, $comparison);
    }

    /**
     * Filter the query on the fdFieldType_id column
     *
     * Example usage:
     * <code>
     * $query->filterByFDFieldTypeId(1234); // WHERE fdFieldType_id = 1234
     * $query->filterByFDFieldTypeId(array(12, 34)); // WHERE fdFieldType_id IN (12, 34)
     * $query->filterByFDFieldTypeId(array('min' => 12)); // WHERE fdFieldType_id >= 12
     * $query->filterByFDFieldTypeId(array('max' => 12)); // WHERE fdFieldType_id <= 12
     * </code>
     *
     * @param     mixed $fDFieldTypeId The value to use as filter.
     *              Use scalar values for equality.
     *              Use array values for in_array() equivalent.
     *              Use associative array('min' => $minValue, 'max' => $maxValue) for intervals.
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByFDFieldTypeId($fDFieldTypeId = null, $comparison = null)
    {
        if (is_array($fDFieldTypeId)) {
            $useMinMax = false;
            if (isset($fDFieldTypeId['min'])) {
                $this->addUsingAlias(FDFieldPeer::FDFIELDTYPE_ID, $fDFieldTypeId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($fDFieldTypeId['max'])) {
                $this->addUsingAlias(FDFieldPeer::FDFIELDTYPE_ID, $fDFieldTypeId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldPeer::FDFIELDTYPE_ID, $fDFieldTypeId, $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByFlatDirectoryId($flatDirectoryId = null, $comparison = null)
    {
        if (is_array($flatDirectoryId)) {
            $useMinMax = false;
            if (isset($flatDirectoryId['min'])) {
                $this->addUsingAlias(FDFieldPeer::FLATDIRECTORY_ID, $flatDirectoryId['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($flatDirectoryId['max'])) {
                $this->addUsingAlias(FDFieldPeer::FLATDIRECTORY_ID, $flatDirectoryId['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldPeer::FLATDIRECTORY_ID, $flatDirectoryId, $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
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

        return $this->addUsingAlias(FDFieldPeer::FLATDIRECTORY_CODE, $flatDirectoryCode, $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
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

        return $this->addUsingAlias(FDFieldPeer::NAME, $name, $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
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

        return $this->addUsingAlias(FDFieldPeer::DESCRIPTION, $description, $comparison);
    }

    /**
     * Filter the query on the mask column
     *
     * Example usage:
     * <code>
     * $query->filterByMask('fooValue');   // WHERE mask = 'fooValue'
     * $query->filterByMask('%fooValue%'); // WHERE mask LIKE '%fooValue%'
     * </code>
     *
     * @param     string $mask The value to use as filter.
     *              Accepts wildcards (* and % trigger a LIKE)
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByMask($mask = null, $comparison = null)
    {
        if (null === $comparison) {
            if (is_array($mask)) {
                $comparison = Criteria::IN;
            } elseif (preg_match('/[\%\*]/', $mask)) {
                $mask = str_replace('*', '%', $mask);
                $comparison = Criteria::LIKE;
            }
        }

        return $this->addUsingAlias(FDFieldPeer::MASK, $mask, $comparison);
    }

    /**
     * Filter the query on the mandatory column
     *
     * Example usage:
     * <code>
     * $query->filterByMandatory(true); // WHERE mandatory = true
     * $query->filterByMandatory('yes'); // WHERE mandatory = true
     * </code>
     *
     * @param     boolean|string $mandatory The value to use as filter.
     *              Non-boolean arguments are converted using the following rules:
     *                * 1, '1', 'true',  'on',  and 'yes' are converted to boolean true
     *                * 0, '0', 'false', 'off', and 'no'  are converted to boolean false
     *              Check on string values is case insensitive (so 'FaLsE' is seen as 'false').
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByMandatory($mandatory = null, $comparison = null)
    {
        if (is_string($mandatory)) {
            $mandatory = in_array(strtolower($mandatory), array('false', 'off', '-', 'no', 'n', '0', '')) ? false : true;
        }

        return $this->addUsingAlias(FDFieldPeer::MANDATORY, $mandatory, $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function filterByOrder($order = null, $comparison = null)
    {
        if (is_array($order)) {
            $useMinMax = false;
            if (isset($order['min'])) {
                $this->addUsingAlias(FDFieldPeer::ORDER, $order['min'], Criteria::GREATER_EQUAL);
                $useMinMax = true;
            }
            if (isset($order['max'])) {
                $this->addUsingAlias(FDFieldPeer::ORDER, $order['max'], Criteria::LESS_EQUAL);
                $useMinMax = true;
            }
            if ($useMinMax) {
                return $this;
            }
            if (null === $comparison) {
                $comparison = Criteria::IN;
            }
        }

        return $this->addUsingAlias(FDFieldPeer::ORDER, $order, $comparison);
    }

    /**
     * Filter the query by a related FDFieldValue object
     *
     * @param   FDFieldValue|PropelObjectCollection $fDFieldValue  the related object to use as filter
     * @param     string $comparison Operator to use for the column comparison, defaults to Criteria::EQUAL
     *
     * @return                 FDFieldQuery The current query, for fluid interface
     * @throws PropelException - if the provided filter is invalid.
     */
    public function filterByFDFieldValue($fDFieldValue, $comparison = null)
    {
        if ($fDFieldValue instanceof FDFieldValue) {
            return $this
                ->addUsingAlias(FDFieldPeer::ID, $fDFieldValue->getFDFieldId(), $comparison);
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
     * @return FDFieldQuery The current query, for fluid interface
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
     * @param   FDField $fDField Object to remove from the list of results
     *
     * @return FDFieldQuery The current query, for fluid interface
     */
    public function prune($fDField = null)
    {
        if ($fDField) {
            $this->addUsingAlias(FDFieldPeer::ID, $fDField->getId(), Criteria::NOT_EQUAL);
        }

        return $this;
    }

}
