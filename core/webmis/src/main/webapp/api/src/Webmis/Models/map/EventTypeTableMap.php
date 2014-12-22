<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'EventType' table.
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
class EventTypeTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.EventTypeTableMap';

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
        $this->setName('EventType');
        $this->setPhpName('EventType');
        $this->setClassname('Webmis\\Models\\EventType');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('code', 'code', 'VARCHAR', true, 8, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 64, null);
        $this->addForeignKey('purpose_id', 'purposeId', 'INTEGER', 'rbEventTypePurpose', 'id', false, null, null);
        $this->addForeignKey('purpose_id', 'purposeId', 'INTEGER', 'rbResult', 'eventPurpose_id', false, null, null);
        $this->addColumn('finance_id', 'financeId', 'INTEGER', false, null, null);
        $this->addColumn('scene_id', 'sceneId', 'INTEGER', false, null, null);
        $this->addColumn('visitServiceModifier', 'visitServiceModifier', 'VARCHAR', true, 128, null);
        $this->addColumn('visitServiceFilter', 'visitServiceFilter', 'VARCHAR', true, 32, null);
        $this->addColumn('visitFinance', 'visitFinance', 'BOOLEAN', true, 1, false);
        $this->addColumn('actionFinance', 'actionFinance', 'BOOLEAN', true, 1, false);
        $this->addColumn('period', 'period', 'TINYINT', true, null, null);
        $this->addColumn('singleInPeriod', 'singleinPeriod', 'TINYINT', true, null, null);
        $this->addColumn('isLong', 'isLong', 'BOOLEAN', true, 1, false);
        $this->addColumn('dateInput', 'dateInput', 'BOOLEAN', true, 1, false);
        $this->addColumn('service_id', 'serviceId', 'INTEGER', false, null, null);
        $this->addColumn('context', 'context', 'VARCHAR', true, 64, null);
        $this->addColumn('form', 'form', 'VARCHAR', true, 64, null);
        $this->addColumn('minDuration', 'minDuration', 'INTEGER', true, null, 0);
        $this->addColumn('maxDuration', 'maxDuration', 'INTEGER', true, null, 0);
        $this->addColumn('showStatusActionsInPlanner', 'showStatusActionsInPlanner', 'BOOLEAN', true, 1, true);
        $this->addColumn('showDiagnosticActionsInPlanner', 'showDiagnosticActionsInPlanner', 'BOOLEAN', true, 1, true);
        $this->addColumn('showCureActionsInPlanner', 'showCureActionsInPlanner', 'BOOLEAN', true, 1, true);
        $this->addColumn('showMiscActionsInPlanner', 'showMiscActionsInPlanner', 'BOOLEAN', true, 1, true);
        $this->addColumn('limitStatusActionsInput', 'limitStatusActionsInput', 'BOOLEAN', true, 1, false);
        $this->addColumn('limitDiagnosticActionsInput', 'limitDiagnosticActionsInput', 'BOOLEAN', true, 1, false);
        $this->addColumn('limitCureActionsInput', 'limitCureActionsInput', 'BOOLEAN', true, 1, false);
        $this->addColumn('limitMiscActionsInput', 'limitMiscActionsInput', 'BOOLEAN', true, 1, false);
        $this->addColumn('showTime', 'showTime', 'BOOLEAN', true, 1, false);
        $this->addColumn('medicalAidType_id', 'medicalAidTypeId', 'INTEGER', false, null, null);
        $this->addColumn('eventProfile_id', 'eventProfileId', 'INTEGER', false, null, null);
        $this->addColumn('mesRequired', 'mesRequired', 'INTEGER', true, 1, 0);
        $this->addColumn('mesCodeMask', 'mesCodeMask', 'VARCHAR', false, 64, '');
        $this->addColumn('mesNameMask', 'mesNameMask', 'VARCHAR', false, 64, '');
        $this->addColumn('counter_id', 'counterId', 'INTEGER', false, null, null);
        $this->addColumn('isExternal', 'isExternal', 'BOOLEAN', true, 1, false);
        $this->addColumn('isAssistant', 'isAssistant', 'BOOLEAN', true, 1, false);
        $this->addColumn('isCurator', 'isCurator', 'BOOLEAN', true, 1, false);
        $this->addColumn('canHavePayableActions', 'canHavePayableActions', 'BOOLEAN', true, 1, false);
        $this->addColumn('isRequiredCoordination', 'isRequiredCoordination', 'BOOLEAN', true, 1, false);
        $this->addColumn('isOrgStructurePriority', 'isOrgStructurePriority', 'BOOLEAN', true, 1, false);
        $this->addColumn('isTakenTissue', 'isTakenTissue', 'BOOLEAN', true, 1, false);
        $this->addColumn('sex', 'sex', 'TINYINT', true, null, 0);
        $this->addColumn('age', 'age', 'VARCHAR', true, 9, null);
        $this->addColumn('rbMedicalKind_id', 'rbMedicalKindId', 'INTEGER', false, null, null);
        $this->addColumn('age_bu', 'ageBu', 'TINYINT', false, 3, null);
        $this->addColumn('age_bc', 'ageBc', 'SMALLINT', false, null, null);
        $this->addColumn('age_eu', 'ageEu', 'TINYINT', false, 3, null);
        $this->addColumn('age_ec', 'ageEc', 'SMALLINT', false, null, null);
        $this->addColumn('requestType_id', 'requestTypeId', 'INTEGER', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('RbEventTypePurpose', 'Webmis\\Models\\RbEventTypePurpose', RelationMap::MANY_TO_ONE, array('purpose_id' => 'id', ), null, null);
        $this->addRelation('RbResult', 'Webmis\\Models\\RbResult', RelationMap::MANY_TO_ONE, array('purpose_id' => 'eventPurpose_id', ), null, null);
        $this->addRelation('Event', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'eventType_id', ), null, null, 'Events');
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

} // EventTypeTableMap
