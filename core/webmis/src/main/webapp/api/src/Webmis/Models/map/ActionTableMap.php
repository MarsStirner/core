<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'Action' table.
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
class ActionTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ActionTableMap';

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
        $this->setName('Action');
        $this->setPhpName('Action');
        $this->setClassname('Webmis\\Models\\Action');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addForeignKey('createPerson_id', 'createPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addForeignKey('modifyPerson_id', 'modifyPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addForeignKey('actionType_id', 'actionTypeId', 'INTEGER', 'ActionType', 'id', true, null, null);
        $this->addForeignKey('event_id', 'eventId', 'INTEGER', 'Event', 'id', false, null, null);
        $this->addColumn('idx', 'idx', 'INTEGER', true, null, 0);
        $this->addColumn('directionDate', 'directionDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('status', 'status', 'TINYINT', true, null, null);
        $this->addForeignKey('setPerson_id', 'setPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('isUrgent', 'isUrgent', 'BOOLEAN', true, 1, false);
        $this->addColumn('begDate', 'begDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('plannedEndDate', 'plannedEndDate', 'TIMESTAMP', true, null, null);
        $this->addColumn('endDate', 'endDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('note', 'note', 'LONGVARCHAR', true, null, null);
        $this->addColumn('person_id', 'personId', 'INTEGER', false, null, null);
        $this->addColumn('office', 'office', 'VARCHAR', true, 16, null);
        $this->addColumn('amount', 'amount', 'DOUBLE', true, null, null);
        $this->addColumn('uet', 'uet', 'DOUBLE', false, null, 0);
        $this->addColumn('expose', 'expose', 'INTEGER', true, 1, 1);
        $this->addColumn('payStatus', 'payStatus', 'INTEGER', true, null, null);
        $this->addColumn('account', 'account', 'BOOLEAN', true, 1, null);
        $this->addColumn('finance_id', 'financeId', 'INTEGER', false, null, null);
        $this->addColumn('prescription_id', 'prescriptionId', 'INTEGER', false, null, null);
        $this->addColumn('takenTissueJournal_id', 'takenTissueJournalId', 'INTEGER', false, null, null);
        $this->addColumn('contract_id', 'contractId', 'INTEGER', false, null, null);
        $this->addColumn('coordDate', 'coordDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('coordAgent', 'coordAgent', 'VARCHAR', true, 128, '');
        $this->addColumn('coordInspector', 'coordInspector', 'VARCHAR', true, 128, '');
        $this->addColumn('coordText', 'coordText', 'VARCHAR', true, 255, null);
        $this->addColumn('hospitalUidFrom', 'hospitalUidFrom', 'VARCHAR', true, 128, '0');
        $this->addColumn('pacientInQueueType', 'pacientInQueueType', 'TINYINT', false, null, 0);
        $this->addColumn('AppointmentType', 'appointmentType', 'CHAR', true, null, null);
        $this->getColumn('AppointmentType', false)->setValueSet(array (
  0 => '0',
  1 => 'amb',
  2 => 'hospital',
  3 => 'polyclinic',
  4 => 'diagnostics',
  5 => 'portal',
  6 => 'otherLPU',
));
        $this->addColumn('version', 'version', 'INTEGER', true, null, 0);
        $this->addColumn('parentAction_id', 'parentActionId', 'INTEGER', false, null, null);
        $this->addColumn('uuid_id', 'uuidId', 'INTEGER', true, null, 0);
        $this->addColumn('dcm_study_uid', 'dcmStudyUid', 'VARCHAR', false, 50, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('Event', 'Webmis\\Models\\Event', RelationMap::MANY_TO_ONE, array('event_id' => 'id', ), null, null);
        $this->addRelation('CreatePerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('createPerson_id' => 'id', ), null, null);
        $this->addRelation('ModifyPerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('modifyPerson_id' => 'id', ), null, null);
        $this->addRelation('SetPerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('setPerson_id' => 'id', ), null, null);
        $this->addRelation('ActionType', 'Webmis\\Models\\ActionType', RelationMap::MANY_TO_ONE, array('actionType_id' => 'id', ), null, null);
        $this->addRelation('ActionProperty', 'Webmis\\Models\\ActionProperty', RelationMap::ONE_TO_MANY, array('id' => 'action_id', ), null, null, 'ActionPropertys');
        $this->addRelation('DrugChart', 'Webmis\\Models\\DrugChart', RelationMap::ONE_TO_MANY, array('id' => 'action_id', ), null, null, 'DrugCharts');
        $this->addRelation('DrugComponent', 'Webmis\\Models\\DrugComponent', RelationMap::ONE_TO_MANY, array('id' => 'action_id', ), null, null, 'DrugComponents');
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

} // ActionTableMap
