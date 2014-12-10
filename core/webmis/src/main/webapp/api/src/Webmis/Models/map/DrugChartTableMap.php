<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'DrugChart' table.
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
class DrugChartTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.DrugChartTableMap';

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
        $this->setName('DrugChart');
        $this->setPhpName('DrugChart');
        $this->setClassname('Webmis\\Models\\DrugChart');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addForeignKey('action_id', 'actionId', 'INTEGER', 'Action', 'id', true, null, null);
        $this->addForeignKey('master_id', 'masterId', 'INTEGER', 'DrugChart', 'id', false, null, null);
        $this->addColumn('begDateTime', 'begDateTime', 'TIMESTAMP', true, null, null);
        $this->addColumn('endDateTime', 'endDateTime', 'TIMESTAMP', false, null, null);
        $this->addColumn('status', 'status', 'TINYINT', true, 1, null);
        $this->addColumn('statusDateTime', 'statusDateTime', 'INTEGER', false, null, null);
        $this->addColumn('note', 'note', 'VARCHAR', false, 256, '');
        $this->addColumn('uuid', 'uuid', 'VARCHAR', false, 100, null);
        $this->addColumn('version', 'version', 'INTEGER', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('Action', 'Webmis\\Models\\Action', RelationMap::MANY_TO_ONE, array('action_id' => 'id', ), null, null);
        $this->addRelation('DrugChartRelatedBymasterId', 'Webmis\\Models\\DrugChart', RelationMap::MANY_TO_ONE, array('master_id' => 'id', ), 'CASCADE', 'CASCADE');
        $this->addRelation('DrugChartRelatedByid', 'Webmis\\Models\\DrugChart', RelationMap::ONE_TO_MANY, array('id' => 'master_id', ), 'CASCADE', 'CASCADE', 'DrugChartsRelatedByid');
    } // buildRelations()

} // DrugChartTableMap
