<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rbTreatment' table.
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
class RbTreatmentTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.RbTreatmentTableMap';

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
        $this->setName('rbTreatment');
        $this->setPhpName('RbTreatment');
        $this->setClassname('Webmis\\Models\\RbTreatment');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 32, null);
        $this->addColumn('name', 'name', 'LONGVARCHAR', true, null, null);
        $this->addForeignKey('pacientModel_id', 'PacientModelId', 'INTEGER', 'rbPacientModel', 'id', true, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('RbPacientModel', 'Webmis\\Models\\RbPacientModel', RelationMap::MANY_TO_ONE, array('pacientModel_id' => 'id', ), null, null);
        $this->addRelation('ClientQuoting', 'Webmis\\Models\\ClientQuoting', RelationMap::ONE_TO_MANY, array('id' => 'treatment_id', ), null, null, 'ClientQuotings');
    } // buildRelations()

} // RbTreatmentTableMap
