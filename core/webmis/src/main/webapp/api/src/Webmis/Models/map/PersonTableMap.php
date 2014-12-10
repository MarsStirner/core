<?php

namespace Webmis\Models\map;

use \RelationMap;
use \TableMap;


/**
 * This class defines the structure of the 'Person' table.
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
class PersonTableMap extends TableMap
{

    /**
     * The (dot-path) name of this class
     */
    const CLASS_NAME = 'Models.map.PersonTableMap';

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
        $this->setName('Person');
        $this->setPhpName('Person');
        $this->setClassname('Webmis\\Models\\Person');
        $this->setPackage('Models');
        $this->setUseIdGenerator(true);
        // columns
        $this->addPrimaryKey('id', 'id', 'INTEGER', true, null, null);
        $this->addColumn('createDatetime', 'createDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('createPerson_id', 'createPersonId', 'INTEGER', false, null, null);
        $this->addColumn('modifyDatetime', 'modifyDatetime', 'TIMESTAMP', true, null, null);
        $this->addColumn('modifyPerson_id', 'modifyPersonId', 'INTEGER', false, null, null);
        $this->addColumn('deleted', 'deleted', 'BOOLEAN', true, 1, false);
        $this->addColumn('code', 'code', 'VARCHAR', true, 12, null);
        $this->addColumn('federalCode', 'federalCode', 'VARCHAR', true, 255, null);
        $this->addColumn('regionalCode', 'regionalCode', 'VARCHAR', true, 16, null);
        $this->addColumn('lastName', 'lastName', 'VARCHAR', true, 30, null);
        $this->addColumn('firstName', 'firstName', 'VARCHAR', true, 30, null);
        $this->addColumn('patrName', 'patrName', 'VARCHAR', true, 30, null);
        $this->addColumn('post_id', 'postId', 'INTEGER', false, null, null);
        $this->addColumn('speciality_id', 'specialityId', 'INTEGER', false, null, null);
        $this->addColumn('org_id', 'orgId', 'INTEGER', false, null, null);
        $this->addColumn('orgStructure_id', 'orgStructureId', 'INTEGER', false, null, null);
        $this->addColumn('office', 'office', 'VARCHAR', true, 8, null);
        $this->addColumn('office2', 'office2', 'VARCHAR', true, 8, null);
        $this->addColumn('tariffCategory_id', 'tariffCategoryId', 'INTEGER', false, null, null);
        $this->addColumn('finance_id', 'financeId', 'INTEGER', false, null, null);
        $this->addColumn('retireDate', 'retireDate', 'DATE', false, null, null);
        $this->addColumn('ambPlan', 'ambPlan', 'SMALLINT', true, 4, null);
        $this->addColumn('ambPlan2', 'ambPlan2', 'SMALLINT', true, 4, null);
        $this->addColumn('ambNorm', 'ambNorm', 'SMALLINT', true, 4, null);
        $this->addColumn('homPlan', 'homPlan', 'SMALLINT', true, 4, null);
        $this->addColumn('homPlan2', 'homPlan2', 'SMALLINT', true, 4, null);
        $this->addColumn('homNorm', 'homNorm', 'SMALLINT', true, 4, null);
        $this->addColumn('expPlan', 'expPlan', 'SMALLINT', true, 4, null);
        $this->addColumn('expNorm', 'expNorm', 'SMALLINT', true, 4, null);
        $this->addColumn('login', 'login', 'VARCHAR', true, 32, null);
        $this->addColumn('password', 'password', 'VARCHAR', true, 32, null);
        $this->addColumn('userProfile_id', 'userProfileId', 'INTEGER', false, null, null);
        $this->addColumn('retired', 'retired', 'BOOLEAN', true, 1, null);
        $this->addColumn('birthDate', 'birthDate', 'DATE', true, null, null);
        $this->addColumn('birthPlace', 'birthPlace', 'VARCHAR', true, 64, null);
        $this->addColumn('sex', 'sex', 'TINYINT', true, null, null);
        $this->addColumn('SNILS', 'snils', 'CHAR', true, 11, null);
        $this->addColumn('INN', 'inn', 'CHAR', true, 15, null);
        $this->addColumn('availableForExternal', 'availableForExternal', 'INTEGER', true, 1, 1);
        $this->addColumn('primaryQuota', 'primaryQuota', 'SMALLINT', true, 4, 50);
        $this->addColumn('ownQuota', 'ownQuota', 'SMALLINT', true, 4, 25);
        $this->addColumn('consultancyQuota', 'consultancyQuota', 'SMALLINT', true, 4, 25);
        $this->addColumn('externalQuota', 'externalQuota', 'SMALLINT', true, 4, 10);
        $this->addColumn('lastAccessibleTimelineDate', 'lastAccessibleTimelineDate', 'DATE', false, null, null);
        $this->addColumn('timelineAccessibleDays', 'timelineAccessibleDays', 'INTEGER', true, null, 0);
        $this->addColumn('typeTimeLinePerson', 'typeTimeLinePerson', 'INTEGER', true, null, null);
        $this->addColumn('maxOverQueue', 'maxOverQueue', 'TINYINT', false, null, 0);
        $this->addColumn('maxCito', 'maxCito', 'TINYINT', false, null, 0);
        $this->addColumn('quotUnit', 'quotUnit', 'TINYINT', false, null, 0);
        $this->addColumn('academicdegree_id', 'academicDegreeId', 'INTEGER', false, null, null);
        $this->addColumn('academicTitle_id', 'academicTitleId', 'INTEGER', false, null, null);
        $this->addColumn('uuid_id', 'uuidId', 'INTEGER', true, null, 0);
        // validators
    } // initialize()

    /**
     * Build the RelationMap objects for this table relationships
     */
    public function buildRelations()
    {
        $this->addRelation('ActionRelatedBycreatePersonId', 'Webmis\\Models\\Action', RelationMap::ONE_TO_MANY, array('id' => 'createPerson_id', ), null, null, 'ActionsRelatedBycreatePersonId');
        $this->addRelation('ActionRelatedBymodifyPersonId', 'Webmis\\Models\\Action', RelationMap::ONE_TO_MANY, array('id' => 'modifyPerson_id', ), null, null, 'ActionsRelatedBymodifyPersonId');
        $this->addRelation('ActionRelatedBysetPersonId', 'Webmis\\Models\\Action', RelationMap::ONE_TO_MANY, array('id' => 'setPerson_id', ), null, null, 'ActionsRelatedBysetPersonId');
        $this->addRelation('EventRelatedBycreatePersonId', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'createPerson_id', ), null, null, 'EventsRelatedBycreatePersonId');
        $this->addRelation('EventRelatedBymodifyPersonId', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'modifyPerson_id', ), null, null, 'EventsRelatedBymodifyPersonId');
        $this->addRelation('EventRelatedBysetPersonId', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'setPerson_id', ), null, null, 'EventsRelatedBysetPersonId');
        $this->addRelation('EventRelatedByexecPersonId', 'Webmis\\Models\\Event', RelationMap::ONE_TO_MANY, array('id' => 'execPerson_id', ), null, null, 'EventsRelatedByexecPersonId');
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

} // PersonTableMap
