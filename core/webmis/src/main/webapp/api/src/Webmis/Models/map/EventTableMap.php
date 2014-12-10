<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'Event' table.
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
class EventTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.EventTableMap';

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
        $this->setName('Event');
        $this->setPhpName('Event');
        $this->setClassname('Webmis\\Models\\Event');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addForeignKey('createPerson_id', 'createPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addForeignKey('modifyPerson_id', 'modifyPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('externalId', 'externalId', 'VARCHAR', true, 30, null);
        $this->addForeignKey('eventType_id', 'eventTypeId', 'INTEGER', 'EventType', 'id', true, null, null);
        $this->addColumn('org_id', 'orgId', 'INTEGER', false, null, null);
        $this->addForeignKey('client_id', 'clientId', 'INTEGER', 'Client', 'id', false, null, null);
        $this->addColumn('contract_id', 'contractId', 'INTEGER', false, null, null);
        $this->addColumn('prevEventDate', 'prevEventDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('setDate', 'setDate', 'TIMESTAMP', true, null, null);
        $this->addForeignKey('setPerson_id', 'setPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('execDate', 'execDate', 'TIMESTAMP', false, null, null);
        $this->addForeignKey('execPerson_id', 'execPersonId', 'INTEGER', 'Person', 'id', false, null, null);
        $this->addColumn('isPrimary', 'isPrimary', 'BOOLEAN', true, 1, null);
        $this->addColumn('order', 'order', 'BOOLEAN', true, 1, null);
        $this->addColumn('result_id', 'resultId', 'INTEGER', false, null, null);
        $this->addColumn('nextEventDate', 'nextEventDate', 'TIMESTAMP', false, null, null);
        $this->addColumn('payStatus', 'payStatus', 'INTEGER', true, null, null);
        $this->addColumn('typeAsset_id', 'typeAssetId', 'INTEGER', false, null, null);
        $this->addColumn('note', 'note', 'LONGVARCHAR', true, null, null);
        $this->addColumn('curator_id', 'curatorId', 'INTEGER', false, null, null);
        $this->addColumn('assistant_id', 'assistantId', 'INTEGER', false, null, null);
        $this->addColumn('pregnancyWeek', 'pregnancyWeek', 'INTEGER', true, null, 0);
        $this->addColumn('MES_id', 'mesId', 'INTEGER', false, null, null);
        $this->addColumn('mesSpecification_id', 'mesSpecificationId', 'INTEGER', false, null, null);
        $this->addColumn('rbAcheResult_id', 'rbAcheResultId', 'INTEGER', false, null, null);
        $this->addColumn('version', 'version', 'INTEGER', true, null, 0);
        $this->addColumn('privilege', 'privilege', 'BOOLEAN', false, 1, false);
        $this->addColumn('urgent', 'urgent', 'BOOLEAN', false, 1, false);
        $this->addForeignKey('orgStructure_id', 'orgStructureId', 'INTEGER', 'OrgStructure', 'id', false, null, null);
        $this->addColumn('uuid_id', 'uuidId', 'INTEGER', true, null, 0);
        $this->addColumn('lpu_transfer', 'lpuTransfer', 'VARCHAR', false, 100, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('EventType', 'Webmis\\Models\\EventType', RelationMap::MANY_TO_ONE, array('eventType_id' => 'id', ), null, null);
        $this->addRelation('Client', 'Webmis\\Models\\Client', RelationMap::MANY_TO_ONE, array('client_id' => 'id', ), null, null);
        $this->addRelation('CreatePerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('createPerson_id' => 'id', ), null, null);
        $this->addRelation('ModifyPerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('modifyPerson_id' => 'id', ), null, null);
        $this->addRelation('SetPerson', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('setPerson_id' => 'id', ), null, null);
        $this->addRelation('Doctor', 'Webmis\\Models\\Person', RelationMap::MANY_TO_ONE, array('execPerson_id' => 'id', ), null, null);
        $this->addRelation('OrgStructure', 'Webmis\\Models\\OrgStructure', RelationMap::MANY_TO_ONE, array('orgStructure_id' => 'id', ), null, null);
        $this->addRelation('Action', 'Webmis\\Models\\Action', RelationMap::ONE_TO_MANY, array('id' => 'event_id', ), null, null, 'Actions');
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

} // EventTableMap
