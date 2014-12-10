<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'Client' table.
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
class ClientTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.ClientTableMap';

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
        $this->setName('Client');
        $this->setPhpName('Client');
        $this->setClassname('Webmis\\Models\\Client');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('lastName', 'lastName', 'VARCHAR', true, 30, null);
        $this->addColumn('firstName', 'firstName', 'VARCHAR', true, 30, null);
        $this->addColumn('patrName', 'patrName', 'VARCHAR', true, 30, null);
        $this->addColumn('birthDate', 'birthDate', 'DATE', true, null, null);
        $this->addColumn('sex', 'sex', 'TINYINT', true, null, null);
        $this->addColumn('SNILS', 'snils', 'CHAR', true, 11, null);
        $this->addColumn('bloodType_id', 'bloodTypeId', 'INTEGER', false, null, null);
        $this->addColumn('bloodDate', 'bloodDate', 'DATE', false, null, null);
        $this->addColumn('bloodNotes', 'bloodNotes', 'VARCHAR', true, 255, null);
        $this->addColumn('growth', 'growth', 'VARCHAR', true, 16, null);
        $this->addColumn('weight', 'weight', 'VARCHAR', true, 16, null);
        $this->addColumn('notes', 'notes', 'VARCHAR', true, 255, null);
        $this->addColumn('version', 'version', 'INTEGER', true, 10, null);
        $this->addColumn('birthPlace', 'birthPlace', 'VARCHAR', true, 128, '');
        $this->addColumn('uuid_id', 'uuidId', 'INTEGER', true, null, 0);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('Event', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'client_id', ), null, null, 'Events');
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

} // ClientTableMap
