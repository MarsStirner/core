<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rbUnit' table.
 *
 *
 *
 * This map class is used by Propel to do runtime db structure discovery.
 * For example, the createSelectSql() method checks the type of a given column used in an
 * ORDER BY clause to know whether it needs to apply SQL to make the ORDER BY case-insensitive
 * (i.e. if it's a text column type).
 *
 * @package    propel.generator.Models.map
 */
class rbUnitTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.rbUnitTableMap';

    /**
     * Initialize the table attributes, columns and validators
     * Relations are not initialized by this method since they are lazy loaded
     *
     * @return void
     * @throws PropelException
     */
    public function initialize()
    {
        // attributes
        $this->setName('rbUnit');
        $this->setPhpName('rbUnit');
        $this->setClassname('Webmis\\Models\\rbUnit');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', false, 256, null);
        $this->addColumn('name', 'name', 'VARCHAR', false, 256, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('DrugComponent', 'Webmis\\Models\\DrugComponent', RelationMap::ONE_TO_MANY, array('id' => 'unit', ), null, null, 'DrugComponents');
        $this->addRelation('rlsNomenRelatedByunitId', 'Webmis\\Models\\rlsNomen', RelationMap::ONE_TO_MANY, array('id' => 'unit_id', ), null, null, 'rlsNomensRelatedByunitId');
        $this->addRelation('rlsNomenRelatedBydosageUnitId', 'Webmis\\Models\\rlsNomen', RelationMap::ONE_TO_MANY, array('id' => 'dosageUnit_id', ), null, null, 'rlsNomensRelatedBydosageUnitId');
    } // buildRelations()

} // rbUnitTableMap
