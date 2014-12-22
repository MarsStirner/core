<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'rbPacientModel' table.
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
class RbPacientModelTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.RbPacientModelTableMap';

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
        $this->setName('rbPacientModel');
        $this->setPhpName('RbPacientModel');
        $this->setClassname('Webmis\\Models\\RbPacientModel');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('code', 'code', 'VARCHAR', true, 32, null);
        $this->addColumn('name', 'name', 'LONGVARCHAR', true, null, null);
        $this->addForeignKey('quotaType_id', 'quotaTypeId', 'INTEGER', 'QuotaType', 'id', true, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('QuotaType', 'Webmis\\Models\\QuotaType', RelationMap::MANY_TO_ONE, array('quotaType_id' => 'id', ), null, null);
        $this->addRelation('ClientQuoting', 'Webmis\\Models\\ClientQuoting', RelationMap::ONE_TO_MANY, array('id' => 'pacientModel_id', ), null, null, 'ClientQuotings');
        $this->addRelation('MkbQuotaTypePacientModel', 'Webmis\\Models\\MkbQuotaTypePacientModel', RelationMap::ONE_TO_MANY, array('id' => 'pacientModel_id', ), null, null, 'MkbQuotaTypePacientModels');
        $this->addRelation('RbTreatment', 'Webmis\\Models\\RbTreatment', RelationMap::ONE_TO_MANY, array('id' => 'pacientModel_id', ), null, null, 'RbTreatments');
    } // buildRelations()

} // RbPacientModelTableMap
