<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rlsNomen' table.
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
class rlsNomenTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.rlsNomenTableMap';

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
        $this->setName('rlsNomen');
        $this->setPhpName('rlsNomen');
        $this->setClassname('Webmis\\Models\\rlsNomen');
        $this->setPackage('Models');
        $this->setUseIdGenerator(false);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addForeignKey('actMatters_id', 'actMattersId', 'INTEGER', 'rlsActMatters', 'id', false, null, null);
        $this->addForeignKey('tradeName_id', 'tradeNameId', 'INTEGER', 'rlsTradeName', 'id', true, null, null);
        $this->addForeignKey('form_id', 'formId', 'INTEGER', 'rlsForm', 'id', false, null, null);
        $this->addForeignKey('packing_id', 'packingId', 'INTEGER', 'rlsPacking', 'id', false, null, null);
        $this->addForeignKey('filling_id', 'fillingId', 'INTEGER', 'rlsFilling', 'id', false, null, null);
        $this->addForeignKey('unit_id', 'unitId', 'INTEGER', 'rbUnit', 'id', false, null, null);
        $this->addColumn('dosageValue', 'dosageValue', 'VARCHAR', false, 128, null);
        $this->addForeignKey('dosageUnit_id', 'dosageUnitId', 'INTEGER', 'rbUnit', 'id', false, null, null);
        $this->addColumn('drugLifetime', 'drugLifetime', 'INTEGER', false, null, null);
        $this->addColumn('regDate', 'regDate', 'DATE', false, null, null);
        $this->addColumn('annDate', 'annDate', 'DATE', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('rbUnitRelatedByunitId', 'Webmis\\Models\\rbUnit', RelationMap::MANY_TO_ONE, array('unit_id' => 'id', ), null, null);
        $this->addRelation('rbUnitRelatedBydosageUnitId', 'Webmis\\Models\\rbUnit', RelationMap::MANY_TO_ONE, array('dosageUnit_id' => 'id', ), null, null);
        $this->addRelation('rlsFilling', 'Webmis\\Models\\rlsFilling', RelationMap::MANY_TO_ONE, array('filling_id' => 'id', ), null, null);
        $this->addRelation('rlsForm', 'Webmis\\Models\\rlsForm', RelationMap::MANY_TO_ONE, array('form_id' => 'id', ), null, null);
        $this->addRelation('rlsPacking', 'Webmis\\Models\\rlsPacking', RelationMap::MANY_TO_ONE, array('packing_id' => 'id', ), null, null);
        $this->addRelation('rlsActMatters', 'Webmis\\Models\\rlsActMatters', RelationMap::MANY_TO_ONE, array('actMatters_id' => 'id', ), null, null);
        $this->addRelation('rlsTradeName', 'Webmis\\Models\\rlsTradeName', RelationMap::MANY_TO_ONE, array('tradeName_id' => 'id', ), null, null);
        $this->addRelation('DrugComponent', 'Webmis\\Models\\DrugComponent', RelationMap::ONE_TO_MANY, array('id' => 'nomen', ), null, null, 'DrugComponents');
        $this->addRelation('RlsBalanceOfGoods', 'Webmis\\Models\\RlsBalanceOfGoods', RelationMap::ONE_TO_MANY, array('id' => 'rlsNomen_id', ), null, null, 'RlsBalanceOfGoodss');
    } // buildRelations()

} // rlsNomenTableMap
