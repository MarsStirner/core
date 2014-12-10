<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'MKB' table.
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
class MkbTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.MkbTableMap';

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
        $this->setName('MKB');
        $this->setPhpName('Mkb');
        $this->setClassname('Webmis\\Models\\Mkb');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('ClassID', 'classId', 'VARCHAR', true, 8, null);
        $this->addColumn('ClassName', 'className', 'VARCHAR', true, 150, null);
        $this->addColumn('BlockID', 'blockId', 'VARCHAR', true, 9, null);
        $this->addColumn('BlockName', 'blockName', 'VARCHAR', true, 160, null);
        $this->addColumn('DiagID', 'diagId', 'VARCHAR', true, 8, null);
        $this->addColumn('DiagName', 'diagName', 'VARCHAR', true, 160, null);
        $this->addColumn('Prim', 'prim', 'VARCHAR', true, 1, null);
        $this->addColumn('sex', 'sex', 'BOOLEAN', true, 1, null);
        $this->addColumn('age', 'age', 'VARCHAR', true, 12, null);
        $this->addColumn('age_bu', 'ageBu', 'TINYINT', false, null, null);
        $this->addColumn('age_bc', 'ageBc', 'SMALLINT', false, null, null);
        $this->addColumn('age_eu', 'ageEu', 'TINYINT', false, null, null);
        $this->addColumn('age_ec', 'ageEc', 'SMALLINT', false, null, null);
        $this->addColumn('characters', 'characters', 'TINYINT', true, null, null);
        $this->addColumn('duration', 'duration', 'INTEGER', true, 4, null);
        $this->addColumn('service_id', 'serviceId', 'INTEGER', false, null, null);
        $this->addColumn('MKBSubclass_id', 'MkbSubclassId', 'INTEGER', false, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
    } // buildRelations()

} // MkbTableMap
