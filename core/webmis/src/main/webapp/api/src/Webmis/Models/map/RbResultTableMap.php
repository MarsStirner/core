<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rbResult' table.
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
class RbResultTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.RbResultTableMap';

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
        $this->setName('rbResult');
        $this->setPhpName('RbResult');
        $this->setClassname('Webmis\\Models\\RbResult');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('eventPurpose_id', 'eventPurposeId', 'INTEGER', true, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 8, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 64, null);
        $this->addColumn('continued', 'continued', 'BOOLEAN', true, 1, null);
        $this->addColumn('regionalCode', 'regionalCode', 'VARCHAR', true, 8, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('EventType', 'Webmis\\Models\\EventType', RelationMap::ONE_TO_MANY, array('eventPurpose_id' => 'purpose_id', ), null, null, 'EventTypes');
    } // buildRelations()

} // RbResultTableMap
