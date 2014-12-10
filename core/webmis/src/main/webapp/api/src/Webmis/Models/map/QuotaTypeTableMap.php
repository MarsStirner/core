<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'QuotaType' table.
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
class QuotaTypeTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.QuotaTypeTableMap';

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
        $this->setName('QuotaType');
        $this->setPhpName('QuotaType');
        $this->setClassname('Webmis\\Models\\QuotaType');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('class', 'Class', 'BOOLEAN', true, 1, null);
        $this->addColumn('group_code', 'groupCode', 'VARCHAR', false, 16, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 16, null);
        $this->addColumn('name', 'name', 'VARCHAR', true, 1000, null);
        $this->addColumn('teenOlder', 'teenOlder', 'BOOLEAN', true, 1, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ClientQuoting', 'Webmis\\Models\\ClientQuoting', RelationMap::ONE_TO_MANY, array('id' => 'quotaType_id', ), null, null, 'ClientQuotings');
        $this->addRelation('MkbQuotaTypePacientModel', 'Webmis\\Models\\MkbQuotaTypePacientModel', RelationMap::ONE_TO_MANY, array('id' => 'quotaType_id', ), null, null, 'MkbQuotaTypePacientModels');
        $this->addRelation('RbPacientModel', 'Webmis\\Models\\RbPacientModel', RelationMap::ONE_TO_MANY, array('id' => 'quotaType_id', ), null, null, 'RbPacientModels');
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

} // QuotaTypeTableMap
