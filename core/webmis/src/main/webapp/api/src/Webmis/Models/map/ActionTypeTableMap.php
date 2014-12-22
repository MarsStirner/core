<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'ActionType' table.
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
class ActionTypeTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ActionTypeTableMap';

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
        $this->setName('ActionType');
        $this->setPhpName('ActionType');
        $this->setClassname('Webmis\\Models\\ActionType');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addForeignPrimaryKey('id', 'id', 'INTEGER' , 'ActionPropertyType', 'actionType_id', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('class', 'Class', 'BOOLEAN', true, 1, null);
        $this->addColumn('group_id', 'groupId', 'INTEGER', false, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 25, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 255, null);
        $this->addColumn('title', 'title', 'VARCHAR', true, 255, null);
        $this->addColumn('flatCode', 'flatCode', 'VARCHAR', true, 64, null);
        $this->addColumn('sex', 'sex', 'TINYINT', true, null, null);
        $this->addColumn('age', 'age', 'VARCHAR', true, 9, null);
        $this->addColumn('age_bu', 'ageBu', 'TINYINT', false, null, null);
        $this->addColumn('age_bc', 'ageBc', 'SMALLINT', false, null, null);
        $this->addColumn('age_eu', 'ageEu', 'TINYINT', false, null, null);
        $this->addColumn('age_ec', 'ageEc', 'SMALLINT', false, null, null);
        $this->addColumn('office', 'office', 'VARCHAR', true, 32, null);
        $this->addColumn('showInForm', 'showInForm', 'BOOLEAN', true, 1, null);
        $this->addColumn('genTimetable', 'genTimeTable', 'BOOLEAN', true, 1, null);
        $this->addColumn('service_id', 'serviceId', 'INTEGER', false, null, null);
        $this->addColumn('quotaType_id', 'quotaTypeId', 'INTEGER', false, null, null);
        $this->addColumn('context', 'context', 'VARCHAR', true, 64, null);
        $this->addColumn('amount', 'amount', 'DOUBLE', true, null, 1);
        $this->addColumn('amountEvaluation', 'amountEvaluation', 'INTEGER', true, 1, 0);
        $this->addColumn('defaultStatus', 'defaultStatus', 'TINYINT', true, null, 0);
        $this->addColumn('defaultDirectionDate', 'defaultDirectionDate', 'TINYINT', true, null, 0);
        $this->addColumn('defaultPlannedEndDate', 'defaultPlannedEndDate', 'BOOLEAN', true, 1, null);
        $this->addColumn('defaultEndDate', 'defaultEndDate', 'TINYINT', true, null, 0);
        $this->addColumn('defaultExecPerson_id', 'defaultExecPersonId', 'INTEGER', false, null, null);
        $this->addColumn('defaultPersonInEvent', 'defaultPersonInEvent', 'TINYINT', true, null, 0);
        $this->addColumn('defaultPersonInEditor', 'defaultPersonInEditor', 'TINYINT', true, null, 0);
        $this->addColumn('maxOccursInEvent', 'maxOccursInEvent', 'INTEGER', true, null, 0);
        $this->addColumn('showTime', 'showTime', 'BOOLEAN', true, 1, false);
        $this->addColumn('isMES', 'isMes', 'INTEGER', false, null, null);
        $this->addColumn('nomenclativeService_id', 'nomenclativeServiceId', 'INTEGER', false, null, null);
        $this->addColumn('isPreferable', 'isPreferable', 'BOOLEAN', true, 1, true);
        $this->addColumn('prescribedType_id', 'prescribedTypeId', 'INTEGER', false, null, null);
        $this->addColumn('shedule_id', 'sheduleId', 'INTEGER', false, null, null);
        $this->addColumn('isRequiredCoordination', 'isRequiredCoordination', 'BOOLEAN', true, 1, false);
        $this->addColumn('isRequiredTissue', 'isRequiredTissue', 'BOOLEAN', true, 1, false);
        $this->addColumn('testTubeType_id', 'testTubeTypeId', 'INTEGER', false, null, null);
        $this->addColumn('jobType_id', 'jobTypeId', 'INTEGER', false, null, null);
        $this->addColumn('mnem', 'mnem', 'VARCHAR', false, 32, '');
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ActionPropertyTypeRelatedByid', 'Webmis\\Models\\ActionPropertyType', RelationMap::MANY_TO_ONE, array('id' => 'actionType_id', ), null, null);
        $this->addRelation('Action', 'Webmis\\Models\\Action', RelationMap::ONE_TO_MANY, array('id' => 'actionType_id', ), null, null, 'Actions');
        $this->addRelation('ActionPropertyTypeRelatedByactionTypeId', 'Webmis\\Models\\ActionPropertyType', RelationMap::ONE_TO_MANY, array('id' => 'actionType_id', ), null, null, 'ActionPropertyTypesRelatedByactionTypeId');
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
  'create_column' => 'createDatetime',
  'update_column' => 'modifyDatetime',
  'disable_updated_at' => 'false',
),
        );
    } // getBehaviors()

} // ActionTypeTableMap
