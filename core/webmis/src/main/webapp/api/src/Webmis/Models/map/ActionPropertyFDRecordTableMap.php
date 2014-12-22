<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'ActionProperty_FDRecord' table.
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
class ActionPropertyFDRecordTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ActionPropertyFDRecordTableMap';

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
        $this->setName('ActionProperty_FDRecord');
        $this->setPhpName('ActionPropertyFDRecord');
        $this->setClassname('Webmis\\Models\\ActionPropertyFDRecord');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, 10, null);
        $this->addColumn('index', 'Index', 'INTEGER', true, 10, 0);
        $this->addForeignKey('value', 'Value', 'INTEGER', 'FDRecord', 'id', true, 10, null);
        $this->addForeignKey('value', 'Value', 'INTEGER', 'FDFieldValue', 'fdRecord_id', true, 10, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('FDRecord', 'Webmis\\Models\\FDRecord', RelationMap::MANY_TO_ONE, array('value' => 'id', ), null, null);
        $this->addRelation('FDFieldValue', 'Webmis\\Models\\FDFieldValue', RelationMap::MANY_TO_ONE, array('value' => 'fdRecord_id', ), null, null);
        $this->addRelation('ActionProperty', 'Webmis\\Models\\ActionProperty', RelationMap::ONE_TO_ONE, array('id' => 'id', ), null, null);
    } // buildRelations()

} // ActionPropertyFDRecordTableMap
