<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'OrgStructure' table.
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
class OrgStructureTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.OrgStructureTableMap';

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
        $this->setName('OrgStructure');
        $this->setPhpName('OrgStructure');
        $this->setClassname('Webmis\\Models\\OrgStructure');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('organisation_id', 'organisationId', 'INTEGER', true, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 255, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 255, null);
        $this->addColumn('parent_id', 'parentId', 'INTEGER', false, null, null);
        $this->addColumn('type', 'type', 'INTEGER', true, null, 0);
        $this->addColumn('net_id', 'netId', 'INTEGER', false, null, null);
        $this->addColumn('isArea', 'isArea', 'TINYINT', true, null, 0);
        $this->addColumn('hasHospitalBeds', 'hasHospitalBeds', 'BOOLEAN', true, 1, false);
        $this->addColumn('hasStocks', 'hasStocks', 'BOOLEAN', true, 1, false);
        $this->addColumn('infisCode', 'infisCode', 'VARCHAR', true, 16, null);
        $this->addColumn('infisInternalCode', 'infisInternalCode', 'VARCHAR', true, 16, null);
        $this->addColumn('infisDepTypeCode', 'infisDepTypeCode', 'VARCHAR', true, 16, null);
        $this->addColumn('infisTariffCode', 'infisTariffCode', 'VARCHAR', true, 16, null);
        $this->addColumn('availableForExternal', 'availableForExternal', 'INTEGER', true, 1, 1);
        $this->addColumn('Address', 'address', 'VARCHAR', true, 255, null);
        $this->addColumn('inheritEventTypes', 'inheritEventTypes', 'BOOLEAN', true, 1, false);
        $this->addColumn('inheritActionTypes', 'inheritActionTypes', 'BOOLEAN', true, 1, false);
        $this->addColumn('inheritGaps', 'inheritGaps', 'BOOLEAN', true, 1, false);
        $this->addColumn('uuid_id', 'uuidId', 'INTEGER', true, null, 0);
        $this->addColumn('show', 'show', 'INTEGER', true, null, 1);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('Event', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'orgStructure_id', ), null, null, 'Events');
        $this->addRelation('RbStorage', 'Webmis\\Models\\RbStorage', RelationMap::ONE_TO_MANY, array('id' => 'orgStructure_id', ), null, null, 'RbStorages');
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

} // OrgStructureTableMap
