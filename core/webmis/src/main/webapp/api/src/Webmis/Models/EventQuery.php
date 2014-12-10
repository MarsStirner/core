<?php

namespace Webmis\Models;

use Webmis\Models\om\BaseEventQuery;
use \Criteria;

/**
 * Skeleton subclass for performing query and update operations on the 'Event' table.
 *
 *
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 *
 * @package    propel.generator.Models
 */
class EventQuery extends BaseEventQuery
{
        private $serializeCLient = false;
        private $serializeCreatePerson = false;
        private $serializeDoctor = false;
        private $serializeModifyPerson = false;
        private $serializeSetPerson = false;


        public function withCLient(){
                $this->serializeClient = true;
                return $this->joinWithClient();
        }

	public function withCreatePerson(){
                $this->serializeCreatePerson = true;

		return $this->joinWith('CreatePerson cp', Criteria::LEFT_JOIN)
                        ->withColumn('cp.id','CreatePersonId')
                        ->withColumn('cp.lastName','CreatePersonLastName')
                        ->withColumn('cp.firstName','CreatePersonFirstName')
                        ->withColumn('cp.patrName','CreatePersonMiddleName');
	}

	public function withModifyPerson(){
                $this->serializeModifyPerson = true;

		return $this->joinWith('ModifyPerson mp', Criteria::LEFT_JOIN)
                        ->withColumn('mp.id','ModifyPersonId')
                        ->withColumn('mp.lastName','ModifyPersonLastName')
                        ->withColumn('mp.firstName','ModifyPersonFirstName')
                        ->withColumn('mp.patrName','ModifyPersonMiddleName');
	}

	public function withDoctor(){
                $this->serializeDoctor = true;

		return $this->joinWith('Doctor d', Criteria::LEFT_JOIN)
                        ->withColumn('d.id','DoctorId')
                        ->withColumn('d.lastName','DoctorLastName')
                        ->withColumn('d.firstName','DoctorFirstName')
                        ->withColumn('d.patrName','DoctorMiddleName');
	}

	public function withSetPerson(){
                $this->serializeSetPerson = true;

		return $this->joinWith('SetPerson sp', Criteria::LEFT_JOIN)
                        ->withColumn('sp.id','SetPersonId')
                        ->withColumn('sp.lastName','SetPersonLastName')
                        ->withColumn('sp.firstName','SetPersonFirstName')
                        ->withColumn('sp.patrName','SetPersonMiddleName');
	}

        public function serialize(){
                $data = array();
                $event = $this->findOne();

                if($event){
                    $data = $event->toArray();

                    if($this->serializeClient){
                            $data['client'] = $event->getClient()->toArray();
                    }

                    if($this->serializeDoctor){
                            $data['doctor'] = array(
                                'id' => (int) $event->getDoctorId(),
                                'firstName' => $event->getDoctorFirstName(),
                                'middleName' => $event->getDoctorMiddleName(),
                                'lastName' => $event->getDoctorLastName()
                                );
                    }

                    if($this->serializeCreatePerson){
                            $data['createPerson'] = array(
                                'id' => $event->getCreatePersonId(),
                                'firstName' => $event->getCreatePersonFirstName(),
                                'middleName' => $event->getCreatePersonMiddleName(),
                                'lastName' => $event->getCreatePersonLastName()
                                );
                    }

                     if($this->serializeModifyPerson){
                            $data['modifyPerson'] = array(
                                'id' => $event->getModifyPersonId(),
                                'firstName' => $event->getModifyPersonFirstName(),
                                'middleName' => $event->getModifyPersonMiddleName(),
                                'lastName' => $event->getModifyPersonLastName()
                                );
                     }

                      if($this->serializeSetPerson){
                            $data['setPerson'] = array(
                                'id' => $event->getSetPersonId(),
                                'firstName' => $event->getSetPersonFirstName(),
                                'middleName' => $event->getSetPersonMiddleName(),
                                'lastName' => $event->getSetPersonLastName()
                                );
                    }
                }


                return $data;
        }
}
