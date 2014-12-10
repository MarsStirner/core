<?php
namespace Webmis\Controllers\Prescription;

// use \Propel;
// use \PDO;
// use \BasePeer;
// use \Criteria;
use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\Action;
use Webmis\Models\ActionProperty;
use Webmis\Models\ActionPropertyDouble;
use Webmis\Models\ActionPropertyString;
use Webmis\Models\ActionPropertyTypeQuery;
use Webmis\Models\ActionQuery;
use Webmis\Models\ActionTypeQuery;
use Webmis\Models\DrugChart;
use Webmis\Models\DrugChartQuery;
use Webmis\Models\DrugComponent;
use Webmis\Models\DrugComponentQuery;
use \PropelObjectCollection;

class PrescriptionController
{

    public function noAction(Request $request, Application $app)
    {
        return $app['jsonp']->jsonp(array('message' => 'тут ничего нет, иди домой' ));
    }

    public function typesListAction(Request $request, Application $app)
    {
        $types = ActionTypeQuery::create()
            ->filterByFlatCode(array('prescription', 'infusion', 'analgesia', 'chemotherapy'))
            ->select(array('id','title')) 
            ->find()->toArray();

        return $app['jsonp']->jsonp(array('data' => $types));
    }


    public function templateAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $actionTypeId = (int) $route_params['actionTypeId'];
        $data = array(
            'actionTypeId' => $actionTypeId,
            'eventId' => null,
            'note' => '',
            'properties' => array(),
            'drugs' => array(),
            'assigmentIntervals' => array()
        );

        if(!$actionTypeId){
            return $app['jsonp']->jsonp(array('message' => 'id for ActionPropertyType?' ));
        }


        $actionPropertyTypes = ActionPropertyTypeQuery::create()
            ->filterByActionTypeId($actionTypeId)
            ->filterByDeleted(false)
            ->find();

        foreach ($actionPropertyTypes as $actionPropertyType) {
            $data['properties'][] = array(
                'actionPropertyTypeId' => $actionPropertyType->getId(),
                'mandatory' => $actionPropertyType->getMandatory(),
                'name' => $actionPropertyType->getName(),
                'value' => '',
                'valueDomain' => $actionPropertyType->getValueDomain(),
                'type' => $actionPropertyType->getTypeName(),
                'code' => $actionPropertyType->getCode(),
            );

        }


        return $app['jsonp']->jsonp(array('data' => $data,
            'message' => 'templateAction' ));
    }


    public function listAction(Request $request, Application $app)
    {

        $dateRangeMin = (int) $request->get('dateRangeMin');
        $dateRangeMax = (int) $request->get('dateRangeMax');


        $clientId = (int) $request->get('clientId');
        $pacientName = $request->get('pacientName');
        $drugName = $request->get('drugName');
        $departmentId = (int) $request->get('departmentId');
        $administrationId = (int) $request->get('administrationId');
        $doctorId = (int) $request->get('doctorId');
        $doctorName = $request->get('doctorName');
        $setPersonName = $request->get('setPersonName');
        $eventId = (int) $request->get('eventId');
        $page = (int) $request->get('page') ?: 1;
        $limit = (int) $request->get('limit') ?: 20;

        $data = array();

        $filter = array('eventId' => $eventId,
            'clientId' => $clientId,
            'pacientName' => $pacientName,
            'doctorName' => $doctorName,
            'setPersonName' => $setPersonName,
            'drugName' => $drugName,
            'departmentId' => $departmentId,
            'administrationId' => $administrationId,
            'dateRangeMax' => $dateRangeMax,
            'dateRangeMin' => $dateRangeMin);

        $hidrate = array(
            'actionType' => true,
            'properties' => true,
            'doctor' => true,
            'setPerson' => true,
            'createPerson' => true,
            'modifyPerson' => true,
            'drugs' => true,
            'intervals' => true,
            'client' => true
        );

        $filterQuery = ActionQuery::create()->filterPrescriptions($filter);
        $filteredPrescriptions = $filterQuery->find()->toArray();

        $filteredPrescriptionsIds = array_map(function($prescription) { return $prescription['id'];}, $filteredPrescriptions);

        // return $app['jsonp']->jsonp(array(
        //     'filter' => $filter,
        //     'filterSql' => $filterQuery->toString(),
        //     'data' => $filteredPrescriptionsIds
        //     ));

        if(count($filteredPrescriptionsIds)){
            $query = ActionQuery::create()->getPrescriptions($filteredPrescriptionsIds, $hidrate);

            $prescriptions = $query->find();

            if($prescriptions){
                foreach ($prescriptions as $prescription) {
                    $data[] = $prescription->serializePrescription($hidrate);
                }
            }
        }



        return $app['jsonp']->jsonp(array(
            /* 'sql' => $filterQuery->toString(), */
            'data' => $data
        ));

    }




    public function readAction(Request $request, Application $app){

        $route_params = $request->get('_route_params') ;
        $prescriptionId = (int) $route_params['prescriptionId'];
        $data = array();

        if(!$prescriptionId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора назначения.' ));
        }

        $prescription = ActionQuery::create()
            ->getPrescriptions(array('id' => $prescriptionId))
            ->find()->getFirst();

        if($prescription){
            $data = $prescription->serializePrescription();
        }

        return $app['jsonp']->jsonp(array('data' => $data ));
    }


    public function createAction(Request $request, Application $app){
        $createPersonId = (int) $request->cookies->get('userId');//неправильно....
        /* $execPersonId = (int) $request->cookies->get('userId');//неправильно.... */

        $data = $request->get('data');
        $eventId = @$data['eventId'];
        $note = @$data['note'];
        $isUrgent = @$data['isUrgent'];
        $actionTypeId = (int) @$data['actionTypeId'];
        $drugs = @$data['drugs'];
        $properties = $data['properties'];
        $assigmentIntervals = @$data['assigmentIntervals'];


        if(!$createPersonId){
            return $app['jsonp']->jsonp(array('message' => 'You are logged in?' ));
        }

        if(!$eventId){
            return $app['jsonp']->jsonp(array('message' => 'Id for event?' ));
        }

        if(!$actionTypeId){
            return $app['jsonp']->jsonp(array('message' => 'Where id for actionType?' ));
        }

        if(!$drugs){
            return $app['jsonp']->jsonp(array('message' => 'Well and where drugs?' ));
        }
        if(!$assigmentIntervals){
            return $app['jsonp']->jsonp(array('message' => 'Intervals?' ));
        }



        $prescription = new Action();

        $prescription->setEventId($eventId);
        $prescription->setNote($note);
        $prescription->setIsUrgent($isUrgent);
        $prescription->setActionTypeId($actionTypeId);
        $prescription->setDeleted(false);
        $prescription->setCreatePersonId($createPersonId);
        $prescription->setModifyPersonId($createPersonId);
        $prescription->setSetPersonId($createPersonId);
        /* $prescription->setPersonId($execPersonId); */
        $prescription->setStatus(0);


        $actionPropertyTypes = ActionPropertyTypeQuery::create()
            ->filterByActionTypeId($actionTypeId)
            ->filterByDeleted(false)
            ->find();

        foreach ($actionPropertyTypes as $actionPropertyType) {

            $actionProperty = new ActionProperty();
            $actionProperty->setTypeId($actionPropertyType->getId());
            $actionProperty->setCreatePersonId($createPersonId);
            $actionProperty->setModifyPersonId($createPersonId);

            $prescription->addActionProperty($actionProperty);

        }


        $drugAllowedFields = array_flip(array('nomen', 'name', 'dose', 'unit'));

        foreach ($drugs as $drug) {
            $drug = array_intersect_key($drug, $drugAllowedFields);

            $drugComponent = new DrugComponent();
            $drugComponent->fromArray($drug);
            $prescription->addDrugComponent($drugComponent);
        }

        foreach ($assigmentIntervals as $interval) {
            $beginDateTime = round((int) @$interval['beginDateTime']/1000);
            $endDateTime = round((int) @$interval['endDateTime']/1000);
            $note = @$interval['note'];

            $drugChart = new DrugChart();

            $drugChart->setBegDateTime($beginDateTime);
            if($endDateTime){
                $drugChart->setEndDateTime($endDateTime);
            }
            if($note){
                $drugChart->setNote($note); 
            }

            $prescription->addDrugChart($drugChart);
        }


        $prescription->save();

        //заполнение значений для экшенпропертей
        if(is_array($properties)){
            $pi = array();
            foreach ($properties as $property) {
                $pi[$property['actionPropertyTypeId']] = $property;
            }
            $properties = $pi;
        }

        $actionProperties = $prescription->getActionPropertys();

        if($actionProperties){
            foreach ($actionProperties as $actionProperty) {
                $actionPropertyId = $actionProperty->getId();
                $actionPropertyType = $actionProperty->getActionPropertyType();
                $actionPropertyTypeId = $actionPropertyType->getId();

                if(array_key_exists($actionPropertyTypeId,$properties)){
                    if($properties[$actionPropertyTypeId]['type'] == 'ReferenceRb'){
                        if(array_key_exists('valueId', $properties[$actionPropertyTypeId])){
                            $actionProperty->setValue($properties[$actionPropertyTypeId]['valueId']);
                        }
                    }else{
                        $actionProperty->setValue($properties[$actionPropertyTypeId]['value']);
                    }
                }

            }
        }

        $actionProperties->save();


        $drugCharts = $prescription->getDrugCharts();

        if($drugCharts){
            foreach ($drugCharts as $drugChart) {
                if(!$drugChart->getMasterId()){
                    $drugChartCopy = new DrugChart();
                    $drugChartCopy->setMasterId($drugChart->getId());
                    $drugChartCopy->setBegDateTime($drugChart->getBegDateTime());
                    if($drugChart->getEndDateTime()){
                        $drugChartCopy->setEndDateTime($drugChart->getEndDateTime());
                    }
                    if($drugChart->getNote()){
                        $drugChartCopy->setNote($drugChart->getNote());
                    }


                    $prescription->addDrugChart($drugChartCopy);
                }
            }
        }

        $prescription->save();


        return $app['jsonp']->jsonp(array(
            'data' => $prescription->serializePrescription(),
            'message' => 'create controller'
        ));
    }

    public function updateAction(Request $request, Application $app){
        $route_params = $request->get('_route_params') ;
        $prescriptionId = (int) $route_params['prescriptionId'];
        $modifyPersonId = (int) $request->cookies->get('userId');//неправильно....

        $data = $request->get('data');
        $eventId = @$data['eventId'];
        $note = @$data['note'];
        $isUrgent = @$data['isUrgent'];
        $actionTypeId = (int) @$data['actionTypeId'];
        $drugs = @$data['drugs'];
        $properties = $data['properties'];
        $assigmentIntervals = @$data['assigmentIntervals'];

        if(!$prescriptionId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора назначения.' ));
        }

        if(!$modifyPersonId){
            return $app['jsonp']->jsonp(array('message' => 'You are logged in?' ));
        }

        if(!$eventId){
            return $app['jsonp']->jsonp(array('message' => 'Id for event?' ));
        }

        if(!$actionTypeId){
            return $app['jsonp']->jsonp(array('message' => 'Where id for actionType?' ));
        }

        if(!$drugs){
            return $app['jsonp']->jsonp(array('message' => 'Well and where drugs?' ));
        }
        if(!$assigmentIntervals){
            return $app['jsonp']->jsonp(array('message' => 'Intervals?' ));
        }


        $prescription = ActionQuery::create()
            ->getPrescriptions(array('id' => $prescriptionId))
            ->find()->getFirst();

        if($prescription){
            $prescription->setNote($note);
            $prescription->setIsUrgent($isUrgent);
            $prescription->setModifyPersonId($modifyPersonId);

            $actionProperties = $prescription->getActionPropertys();
            foreach ($properties as $property) {
                $actionProperty = $this->getById($actionProperties, $property['id']);
                $value = array_key_exists('valueId', $property) ? $property['valueId'] : $property['value'];
                $actionProperty->updateValue($value);
            }
            $actionProperties->save();

            $drugAllowedFields = array_flip(array('nomen', 'name', 'dose', 'id', 'unit'));
            $drugComponents = $prescription->getDrugComponents();

            foreach ($drugs as $drug) {
                $drug = array_intersect_key($drug, $drugAllowedFields);

                if(array_key_exists('id', $drug)){
                    $drugComponent = $this->getById($drugComponents, $drug['id']);
                }else{
                    $drugComponent = new DrugComponent();
                }

                if($drugComponent){
                    $drugComponent->fromArray($drug);
                    $prescription->addDrugComponent($drugComponent);
                } 
            }

            //новые интервалы
            foreach ($assigmentIntervals as $interval) {
                if(array_key_exists('id', $interval)){
                    continue;
                }
                $beginDateTime = round((int) @$interval['beginDateTime']/1000);
                $endDateTime = round((int) @$interval['endDateTime']/1000);
                $note = @$interval['note'];

                $drugChart = new DrugChart();

                $drugChart->setBegDateTime($beginDateTime);
                if($endDateTime){
                    $drugChart->setEndDateTime($endDateTime);
                }
                if($note){
                    $drugChart->setNote($note); 
                }

                $prescription->addDrugChart($drugChart);
                $prescription->save();

                $drugChartCopy = new DrugChart();
                $drugChartCopy->setMasterId($drugChart->getId());
                $drugChartCopy->setBegDateTime($drugChart->getBegDateTime());
                if($drugChart->getEndDateTime()){
                    $drugChartCopy->setEndDateTime($drugChart->getEndDateTime());
                }
                if($drugChart->getNote()){
                    $drugChartCopy->setNote($drugChart->getNote());
                }


                $prescription->addDrugChart($drugChartCopy);

            }

            $drugCharts = $prescription->getDrugCharts();
            //редактирование интервалов
            foreach ($assigmentIntervals as $interval) {
                if(!array_key_exists('id', $interval)){
                    continue;
                }

                $beginDateTime = round((int) @$interval['beginDateTime']/1000);
                $endDateTime = round((int) @$interval['endDateTime']/1000);
                $note = @$interval['note'];

                $drugChart = $this->getById($drugCharts, $interval['id']);
                $drugChart->setBegDateTime($beginDateTime);
                if($endDateTime){
                    $drugChart->setEndDateTime($endDateTime);
                }
                if($note){
                    $drugChart->setNote($note);
                }

                if(array_key_exists('executionIntervals', $interval)){
                    $eis = $interval['executionIntervals'];
                    foreach($eis as $ei){
                        $beginDateTime = round((int) @$ei['beginDateTime']/1000);
                        $endDateTime = round((int) @$ei['endDateTime']/1000);
                        $note = @$ei['note'];

                        $drugChart = $this->getById($drugCharts, $ei['id']);
                        $drugChart->setBegDateTime($beginDateTime);
                        if($endDateTime){
                            $drugChart->setEndDateTime($endDateTime);
                        }
                        if($note){
                            $drugChart->setNote($note);
                        }


                    } 
                }

            }

            $prescription->save();


            $data = $prescription->serializePrescription();
        }

        return $app['jsonp']->jsonp(array('data' => $data ));
    }




    public function drugCancelAction(Request $request, Application $app){
        $route_params = $request->get('_route_params') ;
        $drugId = (int) $route_params['drugId'];

        if(!$drugId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора лекарства.' ));
        }

        $drugComponent = DrugComponentQuery::create()->findOneById($drugId);

        if($drugComponent){
            $drugComponent->cancel()->save();
        }

        return $app['jsonp']->jsonp(array('message' => 'blablabla' ));
    }


    public function readIntervalsAction(Request $request, Application $app){
        $route_params = $request->get('_route_params') ;
        $prescriptionId = (int) $route_params['prescriptionId'];
        $data = array();

        if(!$prescriptionId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора назначения.' ));
        }

        $intervals = DrugChartQuery::create()
            ->filterByActionId($prescriptionId)
            ->find();

        if($intervals){
            $prescription = new Action();
            $data = $prescription->serializeIntervals($intervals);
        }



        return $app['jsonp']->jsonp(array('data' => $data ));

    }




    public function updateIntervalsAction(Request $request, Application $app){
        /* $route_params = $request->get('_route_params') ; */
        $data = $request->get('data');

        if(is_array($data)){

            foreach($data as $executionInterval){
                if(array_key_exists('id', $executionInterval)){
                    $interval = DrugChartQuery::create()->filterById($executionInterval['id'])->findOne();
                }else{
                    $interval = new DrugChart();
                    $interval->setStatus(0);
                }

                if($interval){
                    if(array_key_exists('actionId', $executionInterval)){
                        $interval->setActionId($executionInterval['actionId']);
                    }

                    if(array_key_exists('masterId', $executionInterval)){
                        $interval->setMasterId($executionInterval['masterId']);
                    }

                    if(array_key_exists('status', $executionInterval)){
                        if($interval->getStatus() != $executionInterval['status']){
                            $interval->setStatus($executionInterval['status']);
                        }
                    }

                    if(array_key_exists('note', $executionInterval)){
                        $interval->setNote($executionInterval['note']);
                    }

                    if(array_key_exists('beginDateTime', $executionInterval)){
                        $interval->setBegDateTime(round((int) $executionInterval['beginDateTime']/1000));
                    }

                    if(array_key_exists('endDateTime', $executionInterval)  && !empty($executionInterval['endDateTime'])){
                        $interval->setEndDateTime(round((int) $executionInterval['endDateTime']/1000));
                    }

                    $interval->save();
                }
            }
        }

        return $app['jsonp']->jsonp(array('data' => '' ));
    }

    public function cancelIntervalsExecutionAction(Request $request, Application $app){
        $data = $request->get('data');

        $intervals = DrugChartQuery::create()->filterById($data)->find();

        //return $app['jsonp']->jsonp(array('data' => $data ));
        foreach($intervals as $interval){
            $interval->setStatus(0);
        }

        $intervals->save();

        return $app['jsonp']->jsonp(array('data' => $intervals->toArray() ));
    }
    public function cancelIntervalsAction(Request $request, Application $app){
        $data = $request->get('data');

        $intervals = DrugChartQuery::create()->filterById($data)->find();

        //return $app['jsonp']->jsonp(array('data' => $data ));
        foreach($intervals as $interval){
            $interval->setStatus(2);
        }

        $intervals->save();

        return $app['jsonp']->jsonp(array('data' => $intervals->toArray() ));
    }


    public function executeIntervalsAction(Request $request, Application $app){
        $data = $request->get('data');

        $intervals = DrugChartQuery::create()->filterById($data)->find();

        //return $app['jsonp']->jsonp(array('data' => $data ));
        foreach($intervals as $interval){
            $interval->setStatus(1);
        }

        $intervals->save();

        return $app['jsonp']->jsonp(array('data' => $intervals->toArray() ));
    }


    public function getById($collection, $id){

        $item = null;

        foreach ($collection as $i)
        {
            if ($id == $i->getId()){
                $item = $i;
                break;
            }
        }

        return $item;
    }

    public function getIntervalById($intervals, $id){

        // $filteredIntervals = array_filter($intervals, function ($interval) use ($id) {
        //     return $id === $interval->getId();
        // });

        // $interval = empty($filteredIntervals) ? null : $filteredIntervals[0];
        $interval = null;

        foreach ($intervals as $inter)
        {
            if ($id == $inter->getId()){
                $interval = $inter;
                break;
            }
        }

        return $interval;
    }
}
