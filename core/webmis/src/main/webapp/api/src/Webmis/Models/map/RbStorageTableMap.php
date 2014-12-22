<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rbStorage' table.
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
class RbStorageTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.RbStorageTableMap';

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
        $this->setName('rbStorage');
        $this->setPhpName('RbStorage');
        $this->setClassname('Webmis\\Models\\RbStorage');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('uuid', 'uuid', 'VARCHAR', true, 50, null);
        $this->addColumn('name', 'name', 'VARCHAR', false, 256, null);
        $this->addForeignKey('orgStructure_id', 'orgStructureId', 'INTEGER', 'OrgStructure', 'id', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('OrgStructure', 'Webmis\\Models\\OrgStructure', RelationMap::MANY_TO_ONE, array('orgStructure_id' => 'id', ), null, null);
        $this->addRelation('RlsBalanceOfGoods', 'Webmis\\Models\\RlsBalanceOfGoods', RelationMap::ONE_TO_MANY, array('id' => 'storage_id', ), null, null, 'RlsBalanceOfGoodss');
    } // buildRelations()

} // RbStorageTableMap
