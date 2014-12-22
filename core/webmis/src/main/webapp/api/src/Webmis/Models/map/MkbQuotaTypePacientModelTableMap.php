<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'MKB_QuotaType_PacientModel' table.
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
class MkbQuotaTypePacientModelTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.MkbQuotaTypePacientModelTableMap';

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
        $this->setName('MKB_QuotaType_PacientModel');
        $this->setPhpName('MkbQuotaTypePacientModel');
        $this->setClassname('Webmis\\Models\\MkbQuotaTypePacientModel');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('MKB_id', 'mkbId', 'INTEGER', true, null, null);
        $this->addForeignKey('pacientModel_id', 'pacientModelId', 'INTEGER', 'rbPacientModel', 'id', true, null, null);
        $this->addForeignKey('quotaType_id', 'quotaTypeId', 'INTEGER', 'QuotaType', 'id', true, null, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('QuotaType', 'Webmis\\Models\\QuotaType', RelationMap::MANY_TO_ONE, array('quotaType_id' => 'id', ), null, null);
        $this->addRelation('RbPacientModel', 'Webmis\\Models\\RbPacientModel', RelationMap::MANY_TO_ONE, array('pacientModel_id' => 'id', ), null, null);
    } // buildRelations()

} // MkbQuotaTypePacientModelTableMap
