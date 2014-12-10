<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'ActionPropertyType' table.
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
class ActionPropertyTypeTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ActionPropertyTypeTableMap';

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
        $this->setName('ActionPropertyType');
        $this->setPhpName('ActionPropertyType');
        $this->setClassname('Webmis\\Models\\ActionPropertyType');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addForeignKey('actionType_id', 'actionTypeId', 'INTEGER', 'ActionType', 'id', true, null, null);
        $this->addColumn('idx', 'idx', 'INTEGER', true, null, 0);
        $this->addColumn('template_id', 'templateId', 'INTEGER', false, null, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 128, null);
        $this->addColumn('descr', 'descr', 'VARCHAR', true, 128, null);
        $this->addColumn('unit_id', 'unitId', 'INTEGER', false, null, null);
        $this->addColumn('typeName', 'typeName', 'VARCHAR', true, 64, null);
        $this->addColumn('valueDomain', 'ValueDomain', 'LONGVARCHAR', true, null, null);
        $this->addColumn('defaultValue', 'defaultValue', 'VARCHAR', true, 5000, null);
        $this->addColumn('code', 'code', 'VARCHAR', false, 64, null);
        $this->addColumn('isVector', 'isVector', 'BOOLEAN', true, 1, false);
        $this->addColumn('norm', 'norm', 'VARCHAR', true, 64, null);
        $this->addColumn('sex', 'sex', 'TINYINT', true, null, null);
        $this->addColumn('age', 'age', 'VARCHAR', true, 9, null);
        $this->addColumn('age_bu', 'ageBu', 'TINYINT', false, null, null);
        $this->addColumn('age_bc', 'ageBc', 'SMALLINT', false, null, null);
        $this->addColumn('age_eu', 'ageEu', 'TINYINT', false, null, null);
        $this->addColumn('age_ec', 'ageEc', 'SMALLINT', false, null, null);
        $this->addColumn('penalty', 'penalty', 'INTEGER', true, 3, 0);
        $this->addColumn('visibleInJobTicket', 'visibleInJobTicket', 'BOOLEAN', true, 1, false);
        $this->addColumn('isAssignable', 'isAssignable', 'BOOLEAN', true, 1, false);
        $this->addColumn('test_id', 'testId', 'INTEGER', false, null, null);
        $this->addColumn('defaultEvaluation', 'defaultEvaluation', 'BOOLEAN', true, 1, false);
        $this->addColumn('toEpicrisis', 'toEpicrisis', 'BOOLEAN', true, 1, false);
        $this->addColumn('mandatory', 'mandatory', 'BOOLEAN', true, 1, false);
        $this->addColumn('readOnly', 'readonly', 'BOOLEAN', true, 1, false);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ActionTypeRelatedByactionTypeId', 'Webmis\\Models\\ActionType', RelationMap::MANY_TO_ONE, array('actionType_id' => 'id', ), null, null);
        $this->addRelation('ActionProperty', 'Webmis\\Models\\ActionProperty', RelationMap::ONE_TO_MANY, array('id' => 'type_id', ), null, null, 'ActionPropertys');
        $this->addRelation('ActionTypeRelatedByid', 'Webmis\\Models\\ActionType', RelationMap::ONE_TO_ONE, array('actionType_id' => 'id', ), null, null);
    } // buildRelations()

} // ActionPropertyTypeTableMap
