<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'DrugComponent' table.
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
class DrugComponentTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.DrugComponentTableMap';

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
        $this->setName('DrugComponent');
        $this->setPhpName('DrugComponent');
        $this->setClassname('Webmis\\Models\\DrugComponent');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addForeignKey('action_id', 'actionId', 'INTEGER', 'Action', 'id', true, null, null);
        $this->addForeignKey('nomen', 'nomen', 'INTEGER', 'rlsNomen', 'id', false, null, null);
        $this->addColumn('name', 'name', 'VARCHAR', false, 255, null);
        $this->addColumn('dose', 'dose', 'FLOAT', false, null, null);
        $this->addForeignKey('unit', 'unit', 'INTEGER', 'rbUnit', 'id', false, 10, null);
        $this->addColumn('createDateTime', 'createDateTime', 'TIMESTAMP', true, null, null);
        $this->addColumn('cancelDateTime', 'cancelDateTime', 'TIMESTAMP', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('Action', 'Webmis\\Models\\Action', RelationMap::MANY_TO_ONE, array('action_id' => 'id', ), null, null);
        $this->addRelation('rbUnit', 'Webmis\\Models\\rbUnit', RelationMap::MANY_TO_ONE, array('unit' => 'id', ), null, null);
        $this->addRelation('rlsNomen', 'Webmis\\Models\\rlsNomen', RelationMap::MANY_TO_ONE, array('nomen' => 'id', ), null, null);
    } // buildRelations()

    /**
     *
     * Gets the list of behaviors registered for this table
     *
     * @return array Associative array (name => parameters) of behaviors
     */
    public function getBehaviors()
    {
        return array(
            'timestampable' =>  array (
  'create_column' => 'createDateTime',
  'update_column' => 'updated_at',
  'disable_updated_at' => 'true',
),
        );
    } // getBehaviors()

} // DrugComponentTableMap
