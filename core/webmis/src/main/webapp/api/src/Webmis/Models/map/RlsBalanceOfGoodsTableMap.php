<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rlsBalanceOfGoods' table.
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
class RlsBalanceOfGoodsTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.RlsBalanceOfGoodsTableMap';

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
        $this->setName('rlsBalanceOfGoods');
        $this->setPhpName('RlsBalanceOfGoods');
        $this->setClassname('Webmis\\Models\\RlsBalanceOfGoods');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addForeignKey('rlsNomen_id', 'rlsNomenId', 'INTEGER', 'rlsNomen', 'id', true, null, null);
        $this->addColumn('value', 'value', 'DOUBLE', true, null, null);
        $this->addColumn('bestBefore', 'bestBefore', 'DATE', true, null, null);
        $this->addColumn('disabled', 'disabled', 'TINYINT', true, null, 0);
        $this->addColumn('updateDateTime', 'updateDateTime', 'TIMESTAMP', false, null, null);
        $this->addForeignKey('storage_id', 'storageId', 'INTEGER', 'rbStorage', 'id', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('RbStorage', 'Webmis\\Models\\RbStorage', RelationMap::MANY_TO_ONE, array('storage_id' => 'id', ), null, null);
        $this->addRelation('rlsNomen', 'Webmis\\Models\\rlsNomen', RelationMap::MANY_TO_ONE, array('rlsNomen_id' => 'id', ), null, null);
    } // buildRelations()

} // RlsBalanceOfGoodsTableMap
