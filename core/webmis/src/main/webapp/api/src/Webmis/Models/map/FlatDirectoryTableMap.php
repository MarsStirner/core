<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'FlatDirectory' table.
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
class FlatDirectoryTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.FlatDirectoryTableMap';

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
        $this->setName('FlatDirectory');
        $this->setPhpName('FlatDirectory');
        $this->setClassname('Webmis\\Models\\FlatDirectory');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'Id', 'INTEGER', true, 10, null);
        $this->addColumn('name', 'Name', 'VARCHAR', true, 4096, null);
        $this->addColumn('code', 'Code', 'CHAR', false, 128, null);
        $this->addColumn('description', 'Description', 'VARCHAR', false, 4096, null);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
    } // buildRelations()

} // FlatDirectoryTableMap
