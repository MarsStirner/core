<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'FDFieldValue' table.
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
class FDFieldValueTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.FDFieldValueTableMap';

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
        $this->setName('FDFieldValue');
        $this->setPhpName('FDFieldValue');
        $this->setClassname('Webmis\\Models\\FDFieldValue');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, 10, null);
        $this->addForeignKey('fdRecord_id', 'FDRecordId', 'INTEGER', 'FDRecord', 'id', true, 10, null);
        $this->addForeignKey('fdField_id', 'FDFieldId', 'INTEGER', 'FDField', 'id', true, 10, null);
        $this->addColumn('value', 'Value', 'CLOB', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('FDField', 'Webmis\\Models\\FDField', RelationMap::MANY_TO_ONE, array('fdField_id' => 'id', ), null, null);
        $this->addRelation('FDRecord', 'Webmis\\Models\\FDRecord', RelationMap::MANY_TO_ONE, array('fdRecord_id' => 'id', ), null, null);
        $this->addRelation('ActionPropertyFDRecord', 'Webmis\\Models\\ActionPropertyFDRecord', RelationMap::ONE_TO_MANY, array('fdRecord_id' => 'value', ), null, null, 'ActionPropertyFDRecords');
    } // buildRelations()

} // FDFieldValueTableMap
