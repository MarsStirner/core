<?php
namespace Webmis\Controllers\Therapy;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\ActionQuery;
use Webmis\Models\EventQuery;
use Webmis\Models\FDFieldQuery;
use Webmis\Models\FDRecordQuery;
use Webmis\Models\FDFieldValueQuery;

class TherapyController
{


    public function forPatientAction(Request $request, Application $app)
    {
            $route_params = $request->get('_route_params') ;
            // $eventId = $route_params['eventId'];

            // $event = EventQuery::create()->findOneById($eventId);

            // if(!$event){
            //     $error = array('message' => 'Не найдена история болезни с идентификатором '.$eventId);
            //     return $app['jsonp']->jsonp($error);
            // }

            //$clientId = $event->getClientId();
            $patientId = $route_params['patientId'];

            if(!$patientId){
                $error = array('message' => 'Нет кода пациента.');
                return $app['jsonp']->jsonp($error);
            }
            
            //фильтруем экшены в которых есть поля "терапии"
            $filterActionsQuery = ActionQuery::create()
            ->filterByDeleted(false)
            ->useEventQuery()
                ->filterByClientId($patientId)
                ->where('Event.deleted != 1')
            ->endUse()
            ->onlyWithTherapyProperties()
            ->groupBy('id')
            ->select(array('id'))
            ->orderByCreateDatetime('desc');

            $actionsIds = $filterActionsQuery->find()->toArray();
            
            //получаем экшены и связанные таблицы 
            $actionsQuery = ActionQuery::create()
            ->filterById($actionsIds)
            ->useActionPropertyQuery('ap')
                ->leftJoinActionPropertyType('apt')
                ->leftJoinActionPropertyString('string')
                ->leftJoinActionPropertyDouble('double')
                ->leftJoinActionPropertyDate('date')
                ->useActionPropertyFDRecordQuery('fdr','left join')
                    ->useFDFieldValueQuery('fdfv','left join')
                        ->leftJoinFDField('fdf')
                    ->endUse()
                ->endUse()
            ->endUse()
            ->with('ap')
            ->with('apt')
            ->with('string')
            ->with('date')
            ->with('double')
            ->with('fdr')
            ->with('fdfv')
            ->with('fdf')
            ->orderByCreateDatetime('desc');

            $rawSql = $actionsQuery->toString();
            $actions = $actionsQuery->find();

            $data = $this->therapyMagic($actions);
            return $app['jsonp']->jsonp(array(
                /* 'sql'=>$rawSql, */
                'data'=>$data
            ));
    }


        private function therapyMagic($actions){
            $data = array();


            foreach ($actions as $action){//обходим все экшены
                $a = array();
                $a['docId'] = $action->getId();
                $a['eventId'] = $action->getEventId();
                $a['createDate'] = strtotime($action->getCreateDatetime())*1000;//$action->getCreateDatetime();

                $actionProperties = $action->getActionPropertys();
                foreach ($actionProperties as $actionProperty){//обходим экшенпроперти экшена
                    $actionPropertyType = $actionProperty->getActionPropertyType();
                    $typeName = $actionPropertyType -> getTypeName();

                    $p = array();
                    $p['name'] = $actionPropertyType -> getName();
                    $p['code'] = $actionPropertyType -> getCode();


                    switch ($typeName) {//получаем значение для экшенпроперти
                        case 'String':
                        case 'Html':
                        case 'Text':
                        case 'Constructor':
                            if($actionProperty->getActionPropertyString()){
                                $p['value'] = $actionProperty->getActionPropertyString()->getValue();
                            }
                        break;
                        case 'Double':
                            if($actionProperty->getActionPropertyDouble()){
                                $p['value'] = $actionProperty->getActionPropertyDouble()->getValue();
                            }
                        break;
                        case 'FlatDirectory':
                            if($actionProperty->getActionPropertyFDRecord()){
                                $p['valueId'] = $actionProperty->getActionPropertyFDRecord()->getValue();
                                $fdr = $actionProperty->getActionPropertyFDRecord()->getFDRecord();
                                $v = array();

                                if($fdr){
                                   $fdfvs = $fdr->getFDFieldValues();
                                   foreach($fdfvs as $fdfv){
                                       $fdf = $fdfv->getFDField();
                                       if($fdf){
                                           $v[$fdf->getName()] = $fdfv->getValue();
                                       }
                                   }
                                }

                                $p['value'] = $v;

                            }
                        break;
                        case 'Date':
                            if($actionProperty->getActionPropertyDate()){
                                $date = $actionProperty->getActionPropertyDate()->getValue();
                                $dateArray = explode('-', $date);
                                $year = $dateArray[0];
                                if($year < 1000){
                                    $p['value'] = null;
                                }else{
                                    $p['value'] = strtotime($date)*1000;
                                }

                            }

                        break;
                        default:
                            $p['value'] = 'этот тип экшен проперти пока не поддерживается';
                        break;
                    }

                    if($p['code'] == 'therapyTitle'){
                        if (array_key_exists('valueId', $p)){
                            $a['therapyTitleId'] = $p['valueId'];
                        }else{
                            $a['therapyTitleId'] = null;
                        }

                        if(array_key_exists('value', $p)){
                           // $a['therapyTitle'] =@$p['value'];
                            $a['therapyTitle'] =@$p['value']['Наименование'];
                            $a['therapyLink'] = @$p['value']['Ссылка'];
                        }else{
                            $a['therapyTitle'] = null;
                            $a['therapyLink'] = null;
                        }
                    }
                    if($p['code'] == 'therapyBegDate'){
                        if (array_key_exists('value', $p)){
                            $a['therapyBegDate'] = $p['value'];
                        }else{
                            $a['therapyBegDate'] = null;
                        }
                    }

                    if($p['code'] == 'therapyEndDate'){
                        if (array_key_exists('value', $p)){
                            $a['therapyEndDate'] = $p['value'];
                        }else{
                            $a['therapyEndDate'] = null;
                        }
                    }

                    if($p['code'] == 'therapyPhaseTitle'){
                        if (array_key_exists('valueId', $p)){
                            $a['therapyPhaseTitleId'] = $p['valueId'];
                        }else{
                            $a['therapyPhaseTitleId'] = null;
                        }
                        if(array_key_exists('value', $p)){
                            $a['therapyPhaseTitle'] = @$p['value']['Наименование'];
                            $a['therapyPhaseLink'] = @$p['value']['Ссылка'];
                        }else{
                            $a['therapyPhaseTitle'] = '';
                            $a['therapyPhaseLink'] = '';
                        }
                    }

                    if($p['code'] == 'therapyPhaseBegDate'){
                        if (array_key_exists('value', $p)){
                            $a['therapyPhaseBegDate'] = $p['value'];
                        }else{
                            $a['therapyPhaseBegDate'] = null;
                        }
                    }

                    if($p['code'] == 'therapyPhaseEndDate'){
                        if (array_key_exists('value', $p)){
                            $a['therapyPhaseEndDate'] = $p['value'];
                        }else{
                            $a['therapyPhaseEndDate'] = null;
                        }
                    }
                    if($p['code'] == 'therapyPhaseDay'){
                        if (array_key_exists('value', $p)){
                            $a['therapyPhaseDay'] = $p['value'];
                        }else{
                             $a['therapyPhaseDay'] = null;
                        }
                    }

                }


                //если есть название терапии и фазы
                if(array_key_exists('therapyTitle',$a) && $a['therapyTitle']!=null){
                     array_push($data, $a);
                }



            }

            $therapies = array();

            //терапии
            $first = end($data);
            reset($data);

            foreach ($data as $action){
                //обходим массив данных полученных из дневниковых осмотров и создаём терапии 
                $therapy = array(
                    /* 'id' => $first['docId'], */
                    'titleId' => $action['therapyTitleId'],
                    'title' => $action['therapyTitle'],
                    'link' => $action['therapyLink'],
                    'beginDate' => $action['therapyBegDate'],
                    'endDate' => $action['therapyEndDate'],
                    'phases' => array());


                //дата начала терапиии используется как индекс для массива терапий
                if(@$therapies[$action['therapyBegDate']]){//если  в массиве уже есть индекс с датой начала терапии
                    if(!@$therapies[$action['therapyBegDate']]['endDate']){//если нет даты окончания окончания терапии, то пытаемся её добавить из этого дн.осмотра
                        $therapies[$action['therapyBegDate']]['endDate'] = $action['therapyEndDate'];
                    }
                }else{//добавляем терапию в массив терапий
                    $therapies[$action['therapyBegDate']] = $therapy;
                }
            }

            //фазы терапий
            foreach($therapies as $key => $therapy){//для каждой терапии ищем её фазы

                foreach ($data as $action){
                    if(!$action['therapyPhaseTitle']){//пропускаем дневниковый осмотр  у которого нет названия фазы... webmis-346
                        continue; 
                    }

                    if($therapy['beginDate'] == $action['therapyBegDate']){
                       $phase = array(
                            'eventId' => $action['eventId'],
                            'title' => $action['therapyPhaseTitle'],
                            'link' => $action['therapyPhaseLink'],
                            'titleId' => $action['therapyPhaseTitleId'],
                            'beginDate' => $action['therapyPhaseBegDate'],
                            'endDate' => $action['therapyPhaseEndDate'],
                            'days' => array()
                        );

                       //также как для терапии используем дату начала фазы как индекс массива фаз
                        if(@$therapies[$key]['phases'][$action['therapyPhaseBegDate']]){
                            if(!@$therapies[$key]['phases'][$action['therapyPhaseBegDate']]['endDate']){
                                $therapies[$key]['phases'][$action['therapyPhaseBegDate']]['endDate'] = $action['therapyPhaseEndDate'];
                            }

                        }else{
                            $therapies[$key]['phases'][$action['therapyPhaseBegDate']] = $phase;
                        }

                    }
                }
            }

            //добавляем дни для фаз
            foreach ($data as $action){
                if(!$action['therapyPhaseTitle']){
                    continue; 
                }
                $therapies[$action['therapyBegDate']]['phases'][$action['therapyPhaseBegDate']]['days'][] = array(
                    'day'=> $action['therapyPhaseDay'],
                    'createDate' => $action['createDate'],
                    'eventId' => $action['eventId'],
                    'docId' => $action['docId']);

            }

            //убирание ключи массивов которые нам больше не нужны
            foreach ($therapies as $key => $therapy){
                $therapies[$key]['phases'] = array_values($therapies[$key]['phases']);
            }

            $therapies = array_values($therapies);



            return $therapies;
        }



}
