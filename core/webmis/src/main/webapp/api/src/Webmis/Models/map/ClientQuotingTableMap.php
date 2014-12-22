<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'Client_Quoting' table.
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
class ClientQuotingTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ClientQuotingTableMap';

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
        $this->setName('Client_Quoting');
        $this->setPhpName('ClientQuoting');
        $this->setClassname('Webmis\\Models\\ClientQuoting');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('master_id', 'masterId', 'INTEGER', false, null, null);
        $this->addColumn('identifier', 'identifier', 'VARCHAR', false, 16, null);
        $this->addColumn('quotaTicket', 'quotaTicket', 'VARCHAR', false, 20, null);
        $this->addForeignKey('quotaType_id', 'quotaTypeId', 'INTEGER', 'QuotaType', 'id', false, null, null);
        $this->addColumn('stage', 'stage', 'INTEGER', false, 2, null);
        $this->addColumn('directionDate', 'directionDate', 'TIMESTAMP', true, null, null);
        $this->addColumn('freeInput', 'freeInput', 'VARCHAR', false, 128, null);
        $this->addColumn('org_id', 'orgId', 'INTEGER', false, null, null);
        $this->addColumn('amount', 'amount', 'INTEGER', true, 1, 0);
        $this->addColumn('MKB', 'mkb', 'VARCHAR', true, 8, null);
        $this->addColumn('status', 'status', 'INTEGER', true, 2, 0);
        $this->addColumn('request', 'request', 'INTEGER', true, 1, 0);
        $this->addColumn('statment', 'statment', 'VARCHAR', false, 255, null);
        $this->addColumn('dateRegistration', 'dateRegistration', 'TIMESTAMP', true, null, null);
        $this->addColumn('dateEnd', 'dateEnd', 'TIMESTAMP', true, null, null);
        $this->addColumn('orgStructure_id', 'orgStructureId', 'INTEGER', false, null, null);
        $this->addColumn('regionCode', 'regionCode', 'VARCHAR', false, 13, null);
        $this->addForeignKey('pacientModel_id', 'pacientModelId', 'INTEGER', 'rbPacientModel', 'id', true, null, null);
        $this->addForeignKey('treatment_id', 'treatmentId', 'INTEGER', 'rbTreatment', 'id', true, null, null);
        $this->addColumn('event_id', 'eventId', 'INTEGER', false, null, null);
        $this->addColumn('prevTalon_event_id', 'prevTalonEventId', 'INTEGER', false, null, null);
        $this->addColumn('version', 'version', 'INTEGER', true, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('RbTreatment', 'Webmis\\Models\\RbTreatment', RelationMap::MANY_TO_ONE, array('treatment_id' => 'id', ), null, null);
        $this->addRelation('RbPacientModel', 'Webmis\\Models\\RbPacientModel', RelationMap::MANY_TO_ONE, array('pacientModel_id' => 'id', ), null, null);
        $this->addRelation('QuotaType', 'Webmis\\Models\\QuotaType', RelationMap::MANY_TO_ONE, array('quotaType_id' => 'id', ), null, null);
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

} // ClientQuotingTableMap
