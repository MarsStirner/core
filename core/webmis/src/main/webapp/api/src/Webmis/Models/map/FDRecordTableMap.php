<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'FDRecord' table.
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
class FDRecordTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.FDRecordTableMap';

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
        $this->setName('FDRecord');
        $this->setPhpName('FDRecord');
        $this->setClassname('Webmis\\Models\\FDRecord');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, 10, null);
        $this->addColumn('flatDirectory_id', 'FlatDirectoryId', 'INTEGER', true, 10, null);
        $this->addColumn('flatDirectory_code', 'FlatDirectoryCode', 'CHAR', false, 128, null);
        $this->addColumn('order', 'Order', 'INTEGER', false, null, null);
        $this->addColumn('name', 'Name', 'VARCHAR', false, 4096, null);
        $this->addColumn('description', 'Description', 'VARCHAR', false, 4096, null);
        $this->addColumn('dateStart', 'DateStart', 'TIMESTAMP', false, null, null);
        $this->addColumn('dateEnd', 'DateEnd', 'TIMESTAMP', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ActionPropertyFDRecord', 'Webmis\\Models\\ActionPropertyFDRecord', RelationMap::ONE_TO_MANY, array('id' => 'value', ), null, null, 'ActionPropertyFDRecords');
        $this->addRelation('FDFieldValue', 'Webmis\\Models\\FDFieldValue', RelationMap::ONE_TO_MANY, array('id' => 'fdRecord_id', ), null, null, 'FDFieldValues');
    } // buildRelations()

} // FDRecordTableMap
