<?php

namespace Webmis\Models;

use Webmis\Models\om\BaseActionProperty;


/**
 * Skeleton subclass for representing a row from the 'ActionProperty' table.
 *
 *
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 *
 * @package    propel.generator.Models
 */
class ActionProperty extends BaseActionProperty
{
    public function getActionPropertyString(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyString === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyString = ActionPropertyStringQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            //$this->aActionPropertyString->setActionProperty($this);
        }

        return $this->aActionPropertyString;
    }

    public function getActionPropertyInteger(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyInteger === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyInteger = ActionPropertyIntegerQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            //$this->aActionPropertyInteger->setActionProperty($this);
        }

        return $this->aActionPropertyInteger;
    }

    public function getActionPropertyDouble(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyDouble === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyDouble = ActionPropertyDoubleQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            //$this->aActionPropertyDouble->setActionProperty($this);
        }

        return $this->aActionPropertyDouble;
    }

        public function getActionPropertyFDRecord(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyFDRecord === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyFDRecord = ActionPropertyFDRecordQuery::create()->findPk($this->id, $con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            //$this->aActionPropertyFDRecord->setActionProperty($this);
        }

        return $this->aActionPropertyFDRecord;
    }

    public function getActionPropertyDate(PropelPDO $con = null, $doQuery = true)
    {
        if ($this->aActionPropertyDate === null && ($this->id !== null) && $doQuery) {
            $this->aActionPropertyDate = ActionPropertyDateQuery::create()
                ->filterByActionProperty($this) // here
                ->findOne($con);
            // Because this foreign key represents a one-to-one relationship, we will create a bi-directional association.
            /* $this->aActionPropertyDate->setActionProperty($this); */
        }

        return $this->aActionPropertyDate;
    }

    public function updateValue($value)
    {
        if(!$value){
            return $this;
        }

        $data = array();
        $data['value'] = $value;
        $actionPropertyId = $this->getId();
        $actionPropertyType = $this->getActionPropertyType();
        $actionPropertyTypeId = $actionPropertyType->getId();
        $typeName = $actionPropertyType->getTypeName();

        switch ($typeName) {
            case 'String':
            case 'Html':
            case 'Text':
                $actionPropertyString = $this->getActionPropertyString() ? $this->getActionPropertyString() : new ActionPropertyString();

                if($actionPropertyString->isNew()){
                    $data['id'] = $actionPropertyId;
                    $data['index'] = 0;
                }

                $actionPropertyString->fromArray($data);
                $this->setActionPropertyString($actionPropertyString);

                break;
            case 'Double':
                $actionPropertyDouble = $this->getActionPropertyDouble() ? $this->getActionPropertyDouble() : new ActionPropertyDouble;

                if($actionPropertyDouble->isNew()){
                    $data['id'] = $actionPropertyId;
                    $data['index'] = 0;
                }

                $actionPropertyDouble->fromArray($data);
                $this->setActionPropertyDouble($actionPropertyDouble);

                break;
            case 'ReferenceRb':
                $actionPropertyInteger = $this->getActionPropertyInteger() ? $this->getActionPropertyInteger() : new ActionPropertyInteger();

                if($actionPropertyInteger->isNew()){
                    $data['id'] = $actionPropertyId;
                    $data['index'] = 0;
                }

                $actionPropertyInteger->fromArray($data);
                $this->setActionPropertyInteger($actionPropertyInteger);

                break;
            default:
                # code...
                break;
        }


        return $this;
    }


    public function setValue($value)
    {
        if(!$value){
            return $this;
        }

        $actionPropertyId = $this->getId();
        $actionPropertyType = $this->getActionPropertyType();
        $actionPropertyTypeId = $actionPropertyType->getId();
        $typeName = $actionPropertyType->getTypeName();

        switch ($typeName) {
            case 'String':
            case 'Html':
            case 'Text':
                $actionPropertyString = new ActionPropertyString();
                $actionPropertyString->fromArray(array(
                    'id' => $actionPropertyId,
                    'index' => 0,
                    'value' => $value
                    ));

                $this->setActionPropertyString($actionPropertyString);

                break;
            case 'Double':
                $actionPropertyDouble = new ActionPropertyDouble();
                $actionPropertyDouble->fromArray(array(
                    'id' => $actionPropertyId,
                    'index' => 0,
                    'value' => $value
                    ));

                $this->setActionPropertyDouble($actionPropertyDouble);

                break;

            case 'ReferenceRb':
                $actionPropertyInteger = new ActionPropertyInteger();
                $actionPropertyInteger->fromArray(array(
                    'id' => $actionPropertyId,
                    'index' => 0,
                    'value' => $value
                    ));

                $this->setActionPropertyInteger($actionPropertyInteger);

                break;
            default:
                # code...
                break;
        }


        return $this;
    }


}
