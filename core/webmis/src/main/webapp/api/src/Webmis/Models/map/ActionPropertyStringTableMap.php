<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'ActionProperty_String' table.
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
class ActionPropertyStringTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ActionPropertyStringTableMap';

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
        $this->setName('ActionProperty_String');
        $this->setPhpName('ActionPropertyString');
        $this->setClassname('Webmis\\Models\\ActionPropertyString');
        $this->setPackage('Models');
        $this->setUseIdGenerator(false);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addPrimaryKey('index', 'index', 'INTEGER', true, null, 0);
        $this->addColumn('value', 'value', 'LONGVARCHAR', true, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ActionProperty', 'Webmis\\Models\\ActionProperty', RelationMap::ONE_TO_ONE, array('id' => 'id', ), null, null);
    } // buildRelations()

} // ActionPropertyStringTableMap
