<?php

namespace Webmis\Models;

use Webmis\Models\om\BaseActionQuery;
use \Criteria;

/**
 * Skeleton subclass for performing query and update operations on the 'Action' table.
 *
 *
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 *
 * @package    propel.generator.Models
 */
class ActionQuery extends BaseActionQuery
{
    public function getProperties()
    {
        return $this->useActionPropertyQuery('ActionProperty', 'left join')
                        ->useActionPropertyTypeQuery('apt', 'join')
                            //->orderByIdx()
                        ->endUse()
                        ->useActionPropertyStringQuery('string', 'left join')
                        ->endUse()
                        ->useActionPropertyDoubleQuery('double', 'left join')
                        ->endUse()
                        ->useActionPropertyDateQuery('date', 'left join')
                        ->endUse()
                        ->useActionPropertyFDRecordQuery('fdir', 'left join')
                            // ->useFDFieldValueQuery()
                            // ->endUse()
                        ->endUse()
                        ->endUse()
                        ->with('apt')
                        ->with('string')
                        ->with('double')
                        ->with('date')
                        ->with('fdir');
                    // ->groupBy('id');
    }

    public function filterByPatientId($patientId)
    {
            return $this->_if($patientId)
                            ->useEventQuery()
                                ->filterByClientId($patientId)
                            ->endUse()
                        ->_endif();
    }

    public function onlyTherapy()
    {
        return $this->useActionTypeQuery()
                        ->filterByFlatCode('therapy')
                    ->endUse();
    }


    public function onlyWithTherapyProperties()
    {
        return $this->useActionTypeQuery()->filterByMnem('JOUR')->endUse()
                    ->useActionPropertyQuery()
                        ->useActionPropertyTypeQuery()
                            ->filterByCode(array('therapyTitle'))
                        ->endUse()
                    ->endUse();
    }


        public function filterPrescriptions($filter = array())
    {

        $defaultFilterKeys = array( 'id' => null,
                                    'eventId' => null,
                                    'clientId' => null,
                                    'drugName' => null,
                                    'doctorId' => null,
                                    'pacientName' => null,
                                    'doctorName' => null,
                                    'setPersonName' => null,
                                    'departmentId' => null,
                                    'administrationId' => null,
                                    'dateRangeMin' => null,
                                    'dateRangeMax' => null
                                    );

        $filter = array_merge($defaultFilterKeys, $filter);
        $a = array_intersect_key($filter, array_flip(array_keys($defaultFilterKeys)));

        extract($a);

        return $this->filterByDeleted(false)
                    ->_if($id)
                        ->filterById($id)
                    ->_endIf()
                    ->_if($setPersonName)//назначивший врач
                        ->useSetPersonQuery()
                            ->filterByLastName($setPersonName.'%')
                        ->endUse()
                    ->_endIf()

                    ->useActionTypeQuery()
                        ->filterByFlatCode(array('chemotherapy','prescription','infusion','analgesia'))
                        ->filterByDeleted(false)
                    ->endUse()
                    ->_if($administrationId)
                        //фильтр по способу введения
                        ->useActionPropertyQuery('ap')
                            ->useActionPropertyTypeQuery('apt')
                                ->filterByCode('moa')
                            ->endUse()
                            ->useActionPropertyIntegerQuery('api_v')
                                ->filterByValue($administrationId)
                            ->endUse()
                        ->endUse()
                    ->_endIf()

                    ->_if($eventId || $clientId || $doctorId || $departmentId || $pacientName || $doctorName)
                         ->useEventQuery()
                            //фильтр по истории боле
                            ->_if($eventId)
                                ->filterById($eventId)
                            ->_endif()
                            //фильтр по пациенту
                            ->_if($clientId)
                                ->filterByClientId($clientId)
                            ->_endIf()
                            //
                            ->_if($pacientName)
                                ->useClientQuery()
                                    ->filterByLastName($pacientName.'%')
                                ->endUse()
                            ->_endIf()

                            ->_if($doctorName)
                                ->useDoctorQuery()
                                    ->filterByLastName($doctorName.'%')
                                ->endUse()
                            ->_endIf()

                            //фильтр по доктору
                            ->_if($doctorId)
                                ->filterByExecPersonId($doctorId)
                            ->_endIf()

                            //фильтр по отделению
                            ->_if($departmentId)
                                ->useActionQuery('movements')
                                    ->filterByDeleted(false)
                                    ->filterByEndDate()
                                    ->useActionTypeQuery('movements_at')
                                        ->filterByFlatCode('moving')
                                    ->endUse()
                                    ->useActionPropertyQuery('movements_ap')
                                        ->useActionPropertyTypeQuery('movements_apt')
                                            ->filterByCode('orgStructStay')
                                        ->endUse()
                                        ->useActionPropertyOrgStructureQuery('movements_aptv')
                                            ->filterByValue($departmentId)
                                        ->endUse()
                                    ->endUse()
                                ->endUse()
                            ->_endIf()
                        ->endUse()
                    ->_endIf()

                    ->_if($dateRangeMax || $dateRangeMin)
                        ->useDrugChartQuery()
                            //фильтр по интервалам назначения
                            ->_if($dateRangeMax && !$dateRangeMin)
                                ->filterByBegDateTime(array('max' => $dateRangeMax))
                            ->_endif()
                            ->_if($dateRangeMin && !$dateRangeMax)
                                ->filterByBegDateTime(array('min' => $dateRangeMin))
                            ->_endif()
                            ->_if($dateRangeMin && $dateRangeMax)
                                ->where('? BETWEEN DrugChart.begDateTime AND DrugChart.endDateTime', $dateRangeMin)
                                    ->_or()
                                ->where('? BETWEEN DrugChart.begDateTime AND DrugChart.endDateTime', $dateRangeMax)
                                    ->_or()
                                ->where('DrugChart.begDateTime BETWEEN ? AND ?',array($dateRangeMin, $dateRangeMax))
                                    ->_or()
                                ->where('DrugChart.endDateTime BETWEEN ? AND ?',array($dateRangeMin, $dateRangeMax))
                            ->_endif()
                        ->endUse()
                    ->_endif()

                    ->_if($drugName)
                        ->useDrugComponentQuery()
                            //фильтр по названию лекарства
                            ->_if($drugName)
                                ->filterByName($drugName.'%')
                            ->_endif()
                        ->endUse()
                    ->_endif();

    }




        public function getPrescriptions($ids, $hidrate = array(
            'actionType' => true,
            'properties' => false,
            'doctor' => true,
            'createPerson' => true,
            'modifyPerson' => true,
            'setPerson' => true,
            'drugs' => false,
            'intervals' => true,
            'client' => false
            ))
    {

            return $this->filterByDeleted(false)
                        ->_if($ids)
                            ->filterById($ids)
                        ->_endIf()
                        ->_if($hidrate['actionType'])
                            ->leftJoinWithActionType()
                        ->_endIf()
                        ->_if($hidrate['setPerson'])
                            ->leftJoinSetPerson('setPerson')
                            ->with('setPerson')
                        ->_endIf()
                        ->_if($hidrate['createPerson'])
                            ->leftJoinCreatePerson('createPerson')
                            ->with('createPerson')
                        ->_endIf()
                        ->_if($hidrate['modifyPerson'])
                            ->leftJoinModifyPerson('modifyPerson')
                            ->with('modifyPerson')
                        ->_endIf()

                        ->_if($hidrate['properties'])
                            ->useActionPropertyQuery('ActionProperty', Criteria::LEFT_JOIN)
                                ->joinActionPropertyType('apType', Criteria::LEFT_JOIN)
                                ->joinActionPropertyString('apString', Criteria::LEFT_JOIN)
                                ->joinActionPropertyDouble('apDouble', Criteria::LEFT_JOIN)
                                ->joinActionPropertyDate('apDate', Criteria::LEFT_JOIN)
                                ->joinActionPropertyInteger('apInteger', Criteria::LEFT_JOIN)
                            ->endUse()
                            ->with('ActionProperty')
                            ->with('apType')
                            ->with('apString')
                            ->with('apDouble')
                            ->with('apDate')
                            ->with('apInteger')
                        ->_endIf()


                        ->_if($hidrate['client'] || $hidrate['doctor'])
                            ->useEventQuery()
                                ->_if($hidrate['client'])->leftJoinClient('client')->_endIf()
                                ->_if($hidrate['doctor'])->leftJoinDoctor('doctor')->_endIf()
                            ->endUse()
                            ->with('Event')
                            ->_if($hidrate['doctor'])->with('doctor')->_endIf()
                            ->_if($hidrate['client'])->with('client')->_endIf()
                        ->_endIf()


                        ->_if($hidrate['intervals'])
                            ->leftJoinWithDrugChart('intervals')
                        ->_endIf()


                        ->_if($hidrate['drugs'])
                            ->useDrugComponentQuery('drugs')
                                ->useRlsNomenQuery('nomen')
                                    ->leftJoinrbUnitRelatedByunitId('unit')
                                ->endUse()
                            ->endUse()
                            ->with('drugs')
                            ->with('nomen')
                            ->with('unit')
                        ->_endIf();

    }


}
