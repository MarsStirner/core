<?php
namespace Webmis\Controllers\Appeal;

use Silex\Application;
use Silex\ControllerProviderInterface;
use Symfony\Component\HttpFoundation\Request;
//use Symfony\Component\HttpFoundation\ParameterBag;

class AppealRouter implements ControllerProviderInterface
{
    public function connect(Application $app)
    {
        // creates a new controller based on the default route
        $controllers = $app['controllers_factory'];

        $controllers->get('/{appealId}/docs4closing', function($appealId, Request $request)  use ($app) {


            $json = array();

            $json['allDocs'] = true;//есть все документы для закрытия истории болезни

            //тип финансирования ВМП?
            $select_sql = "SELECT f.code,f.name FROM Event as e "
            ."JOIN EventType as et ON e.eventType_id = et.id "
            ."JOIN rbFinance as f ON et.finance_id = f.id "
            ."WHERE e.id = ? LIMIT 1";

            $financeType = $app['db']->fetchAssoc($select_sql, array((int) $appealId));

            if($financeType['name'] == 'ВМП'){
                $vmp = true;
            }else{
                $vmp = false;
            }

            $json['vmp'] = $vmp;


            //Если тип финансирования ВМП, то проверяем есть ли тикет для ВМП?
            if($vmp){

                $select_sql = "SELECT Client_Quoting.id FROM Event "
                ."JOIN Client_Quoting ON Event.externalId = Client_Quoting.Identifier "
                ."WHERE Event.id = ? "
                ."AND Client_Quoting.deleted = 0 ";

                $vmpTicket = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));

                $json['vmpTicket'] = $vmpTicket;
                $json['allDocs'] = $vmpTicket;

            }


            //есть ли эпикриз?
            $select_sql = "SELECT ActionType.id FROM Action "
            ."JOIN ActionType ON Action.actionType_id = ActionType.id "
            ."WHERE Action.event_id = ? "
            ."AND (ActionType.code = 4504 OR ActionType.code = 4507 OR ActionType.code = 4511) "
            ."AND ActionType.mnem = 'EPI' "
            ."AND Action.deleted = 0 ";

            $epicrisis = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));

            $json['epicrisis'] = $epicrisis;
            $json['allDocs'] = $json['allDocs'] && $json['epicrisis'];


            //есть ли выписка
            $select_sql = "SELECT Action.id FROM Action "
            ."JOIN ActionType ON Action.actionType_id = ActionType.id "
            ."WHERE Action.event_id = ? "
            ."AND ActionType.flatCode = 'leaved' "
            ."AND Action.deleted = 0 "
            ."AND ActionType.mnem = 'ORD' ";

            $discharge = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));
            $json['discharge'] = $discharge;
            $json['allDocs'] = $json['allDocs'] && $json['discharge'];

            //проверка на онко диагноз
            $select_sql = "SELECT Diagnostic.id "
            ."FROM Diagnostic "
            ."JOIN Diagnosis ON Diagnosis.id = Diagnostic.diagnosis_id "
            ."JOIN MKB ON Diagnosis.MKB = MKB.DiagID "
            ."WHERE Diagnostic.event_id = ? "
            ."AND Diagnostic.deleted = 0 "
            ."AND MKB.DiagId LIKE 'C%' ";

            $oncology = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));
            $json['oncology'] = $oncology;


            if($oncology){
                //Данные для онко диагнозов
                $select_sql = "SELECT rbDiagnosisType.name as 'diagnosisTypeName',"
                ." MKB.DiagId  AS 'diagnosisId',"
                ." MKB.DiagName AS 'diagnosisName',"
                ." rbDiseaseCharacter.code AS 'characterCode',"
                ." rbDiseaseCharacter.name AS 'characterName',"
                ." rbDiseaseStage.code AS 'stageCode',"
                ." rbDiseaseStage.name AS 'stageName'"
                ." FROM Diagnostic"
                ." JOIN Diagnosis ON Diagnostic.diagnosis_id = Diagnosis.id"
                ." LEFT JOIN rbDiagnosisType ON Diagnostic.diagnosisType_id = rbDiagnosisType.id"
                ." LEFT JOIN MKB ON Diagnosis.MKB = MKB.DiagID"
                ." LEFT JOIN rbDiseaseCharacter ON Diagnostic.character_id = rbDiseaseCharacter.id"
                ." LEFT JOIN rbDiseaseStage ON Diagnostic.stage_id = rbDiseaseStage.id"
                ." where Diagnostic.event_id = ? ";

                $onkoData = $app['db']->fetchAssoc($select_sql, array((int) $appealId));
                $json['onkoData'] = $onkoData;
            }

            if($oncology && ($onkoData['characterCode'] == 2)){
                //проверка наличия извещения 090/у
                /* $select_sql = "SELECT Action.id FROM Action " */
                /* ." JOIN ActionType ON Action.actionType_id = ActionType.id " */
                /* ." WHERE Action.event_id = ? " */
                /* ." AND ActionType.code= '1_7_2'" */
                /* ."AND Action.deleted = 0 " */
                /* ." AND ActionType.mnem = 'NOT' "; */

                /* $notice_090y = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId)); */

                /* $json['notice_090y'] = $notice_090y; */
                /* $json['allDocs'] = $json['allDocs'] && $json['notice_090y']; */
            }


            if($oncology && ($onkoData['characterCode'] == 2) && ($onkoData['stageCode'] == 4) ){
                /* проверка наличия извещения 027/у-2 */
                /* $select_sql = "SELECT Action.id FROM Action " */
                /* ." JOIN ActionType ON Action.actionType_id = ActionType.id " */
                /* ." WHERE Action.event_id = ? " */
                /* ." AND ActionType.code= '1_7_1'" */
                /* ." AND Action.deleted = 0 " */
                /* ." AND ActionType.mnem = 'NOT' "; */

                /* $notice_027y_2 = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId)); */

                /* $json['notice_027y_2'] = $notice_027y_2; */
                /* $json['allDocs'] = $json['allDocs'] && $json['notice_027y_2']; */
            }

            if($oncology){
                //проверка наличия извещения 027/у-1
                $select_sql = "SELECT Action.id FROM Action "
                ." JOIN ActionType ON Action.actionType_id = ActionType.id "
                ." WHERE Action.event_id = ? "
                ." AND Action.deleted = 0 "
                ." AND ActionType.code= '1_8_5'"
                ." AND ActionType.mnem = 'OTH' ";

                $notice_027y_1 = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));

                $json['notice_027y_1'] = $notice_027y_1;
                $json['allDocs'] = $json['allDocs'] && $json['notice_027y_1'];
            }

            //незакрытые документы
            $select_sql = "SELECT Action.id FROM Action "
            ." JOIN ActionType ON Action.actionType_id = ActionType.id "
            ." WHERE Action.event_id = ? "
            ." AND Action.deleted = 0 "
            ." AND Action.endDate IS NULL";

            $openDocs = (bool) $app['db']->fetchAssoc($select_sql, array((int) $appealId));

            $json['openDocs'] = $openDocs;


            return $app['jsonp']->jsonp(array('data' => $json));

        })->assert('appealId', '\d+');





        //закрыть незакрытые документы для госпитализации
        $controllers->get('/{appealId}/docs/close', function($appealId, Request $request)  use ($app){

            //Дата закрытия
            $execDate = $app['request']->get('execDate');
            if($execDate){
                $timestamp = $execDate/1000;
                $date = new \DateTime();
                $date->setTimestamp($timestamp);
            }else{
                $date = new \DateTime('NOW');
            }

            //текущая дата
            $modifyDatetime = new \DateTime('NOW');

            //идентификатор текущего пользователя
            $modifyPersonId = $request->cookies->get('userId');

            $update_sql = "UPDATE Action "
                ." JOIN ActionType ON Action.actionType_id = ActionType.id "
                ." SET Action.endDate = :endDate, Action.status = 2, Action.modifyDatetime = :modifyDatetime, Action.modifyPerson_id = :modifyPersonId "
                ." WHERE Action.event_id = :appealId "
                ." AND Action.endDate IS NULL";

            $statment = $app['db']->prepare($update_sql);
            $statment->bindValue('endDate', $date, "datetime");
            $statment->bindValue('modifyDatetime', $modifyDatetime, "datetime");
            $statment->bindValue('modifyPersonId',$modifyPersonId, "integer");
            $statment->bindValue('appealId', $appealId, "integer");
            $count = $statment->execute();

            return $app['jsonp']->jsonp(array('count'=>$count));

        })->assert('appealId', '\d+');




        //освободить койку
        $controllers->get('/{appealId}/bed/vacate', function($appealId, Request $request) use ($app){

            //Дата закрытия
            $execDate = $app['request']->get('execDate');
            if($execDate){
                $timestamp = $execDate/1000;
                $date = new \DateTime();
                $date->setTimestamp($timestamp);
            }else{
                $date = new \DateTime('NOW');
            }

            //текущая дата
            $modifyDatetime = new \DateTime('NOW');

            //идентификатор текущего пользователя
            $modifyPersonId = $request->cookies->get('userId');

            //есть ли выписка
            $discharge_select_sql = "SELECT Action.* FROM Action "
            ."JOIN ActionType ON Action.actionType_id = ActionType.id "
            ."WHERE Action.event_id = ? "
            ."AND ActionType.flatCode = 'leaved' "
            ."AND Action.deleted = 0 "
            ."AND ActionType.mnem = 'ORD' "
            ."ORDER BY Action.createDatetime DESC LIMIT 1";

            $discharge = $app['db']->fetchAssoc($discharge_select_sql, array((int) $appealId));

            if(!$discharge){
                $error = array('message' => 'Нет документа "Выписка". ');
                return $app['jsonp']->jsonp($error);
            }

            if(!$discharge['endDate']){//Документа "Выписка" не закрыт

                $update_discharge_sql = "UPDATE Action "
                ." JOIN ActionType ON Action.actionType_id = ActionType.id "
                ." SET Action.endDate = :endDate, Action.status = 2, Action.modifyDatetime = :modifyDatetime, Action.modifyPerson_id = :modifyPersonId "
                ." WHERE Action.id = :actionId "
                ." AND Action.endDate IS NULL";

                $statment = $app['db']->prepare($update_discharge_sql);
                $statment->bindValue('endDate', $date, "datetime");
                $statment->bindValue('modifyDatetime', $modifyDatetime, "datetime");
                $statment->bindValue('modifyPersonId',$modifyPersonId, "integer");
                $statment->bindValue('actionId', $discharge["id"], "integer");
                $statment->execute();

                $dischargeEndDate = $date->format('Y-m-d H:i:s');
            }else{
                $dischargeEndDate = $discharge['endDate'];
            }


            $select_last_move_sql = "SELECT Action.id, Action.begDate, Action.endDate FROM Action "
            ."JOIN ActionType ON Action.actionType_id = ActionType.id "
            ."WHERE Action.event_id = ? AND ActionType.flatCode = 'moving' "
            ."ORDER BY Action.directionDate DESC LIMIT 1";
            $lastMove = $app['db']->fetchAssoc($select_last_move_sql, array((int) $appealId));
            $lastMoveId = $lastMove['id'];

            if(!$lastMoveId){//если нет движений
                $error = array('message' => 'Нет движений для закрытия. ');
                return $app['jsonp']->jsonp($error);
            }

            $update_last_move_sql = "UPDATE Action "
            ."SET Action.endDate= :endDate, Action.modifyDatetime = :modifyDatetime, Action.modifyPerson_id = :modifyPersonId, Action.status = 2 "
            ."WHERE Action.id = :lastMoveId";

            $lastMoveEndDate = new \DateTime($dischargeEndDate);
            $lastMoveEndDate->modify('-1 second');

            $stmt = $app['db']->prepare($update_last_move_sql);
            $stmt->bindValue('endDate', $lastMoveEndDate, "datetime");
            $stmt->bindValue('modifyDatetime', $modifyDatetime, "datetime");
            $stmt->bindValue('modifyPersonId',$modifyPersonId, "integer");
            $stmt->bindValue('lastMoveId', $lastMoveId, "integer");
            $count = $stmt->execute();

            $timeleavead = $lastMoveEndDate->format('H:i:s');

            $select_sql = "SELECT ap.id from Action AS a "
                         ."JOIN ActionProperty AS ap ON ap.action_id = a.id "
                         ."WHERE a.id = ? AND ap.type_id = 1617 ";

            $timeleaveadProperty = $app['db']->fetchAssoc($select_sql, array((int) $lastMoveId));
            $timeleaveadPropertyId = $timeleaveadProperty['id'];

            $insert_sql = "INSERT INTO ActionProperty_Time (id, value) VALUES (:id,:time) "
                        ." ON DUPLICATE KEY UPDATE value= :time;";

            $stmt = $app['db']->prepare($insert_sql);
            $stmt->bindValue('id', $timeleaveadPropertyId, "integer");
            $stmt->bindValue('time', $timeleavead);
            $count = $stmt->execute();


            return $app['jsonp']->jsonp($count);

        })->assert('appealId', '\d+');




        //закрыть госпитализацию
        $controllers->get('/{appealId}/close', function($appealId, Request $request) use ($app){

            //Дата закрытия
            $execDate = $app['request']->get('execDate');
            if($execDate){
                $timestamp = $execDate/1000;
                $date = new \DateTime();
                $date->setTimestamp($timestamp);
            }else{
                $date = new \DateTime('NOW');
            }

            //текущая дата
            $modifyDatetime = new \DateTime('NOW');

            //идентификатор текущего пользователя
            $modifyPersonId = $request->cookies->get('userId');

            //результат госпитализации
            $resultId = $request->query->get('resultId');

            //выбираем историю болезни
            $appeal_sql = "SELECT * FROM Event WHERE id = ?";
            $appeal = $app['db']->fetchAssoc($appeal_sql, array((int) $appealId));

            $execPersonId = $appeal['execPerson_id'];

            if (!$execPersonId) {//должен быть назначен ответственный "лечащий врач"
                $error = array('message' => 'Ошибка: в истории болезни не назначен лечащий врач!');
                return $app['jsonp']->jsonp($error);
            }

            //выбираем последнего лечащего врача
            $select_exec_person_sql = "SELECT * FROM Event_Persons"
                                     ." WHERE event_id = :appealId AND person_id = :execPersonId"
                                     ." ORDER BY begDate DESC LIMIT 1";

            $stmt = $app['db']->prepare($select_exec_person_sql);
            $stmt->bindValue('appealId', $appealId, "integer");
            $stmt->bindValue('execPersonId', $execPersonId, "integer");
            $stmt->execute();
            $appealExecPerson = $stmt->fetch();

            if (!$appealExecPerson) {
                $error = array('message' => 'Ошибка: не нашли лечащего врача!');
                return $app['jsonp']->jsonp($error);
            }

            $appealExecPersonId = $appealExecPerson['id'];

            //обновляем историю болезни и последнего лечащего врача
            $update_sql = "UPDATE Event, Event_Persons SET Event.modifyDatetime = :modifyDatetime, Event.modifyPerson_id = :modifyPersonId ,Event.execDate = :execDate, Event.result_id = :resultId, Event_Persons.endDate = :execDate WHERE Event.id = :appealId AND Event_Persons.id = :appealExecPersonId";
            $stmt = $app['db']->prepare($update_sql);
            $stmt->bindValue('modifyDatetime', $modifyDatetime, "datetime");
            $stmt->bindValue('modifyPersonId',$modifyPersonId, "integer");
            $stmt->bindValue('execDate', $date, "datetime");
            $stmt->bindValue('resultId',$resultId, "integer");
            $stmt->bindValue('appealId', $appealId, "integer");
            $stmt->bindValue('appealExecPersonId', $appealExecPersonId, "integer");
            $stmt->execute();


            $appeal = $app['db']->fetchAssoc($appeal_sql, array((int) $appealId));

            return $app['jsonp']->jsonp($appeal);

        })->assert('appealId', '\d+');


        //вмп талон госпитализации
        $controllers->get('/{appealId}/client_quoting', 'Webmis\Controllers\Appeal\ClientQuotingController::readAction')->assert('appealId', '\d+');

        //предыдущий вмп талон госпитализации
        $controllers->get('/{appealId}/client_quoting/prev', 'Webmis\Controllers\Appeal\ClientQuotingController::readPrevAction')->assert('appealId', '\d+');

        //создание вмп талона госпитализации
        $controllers->post('/{appealId}/client_quoting', function($appealId, Request $request) use ($app){

            $userId = $request->cookies->get('userId');
            $date = new \DateTime('NOW');
            $now = $date->format('Y-m-d H:i:s');


            $select_event_sql = "SELECT * FROM Event WHERE id = ?";
            $event = $app['db']->fetchAssoc($select_event_sql, array((int) $appealId));
            $post = json_decode($request->getContent());

            $data = array(
                "createDatetime" => $now,
                "createPerson_id" => $userId,
                "modifyDatetime" => $now,
                "modifyPerson_id" => $userId,
                "master_id"=>$event['client_id'],
                "identifier"=>$event['externalId'],
                "status" => 2,
                "quotaType_id" => $post->data->quotaType_id,
                "MKB" => $post->data->MKB,
                "pacientModel_id" => $post->data->pacientModel_id,
                "treatment_id" => $post->data->treatment_id,
                "event_id" => $appealId
                );

            $app['db']->insert('Client_Quoting', $data);



            return $app['jsonp']->jsonp(array("data" => $data));

        })->assert('appealId', '\d+');

        //обновление вмп талона госпитализации
        $controllers->put('/{appealId}/client_quoting/{quotingId}', function($appealId, $quotingId, Request $request) use ($app){

            $userId = $request->cookies->get('userId');
            $date = new \DateTime('NOW');
            $now = $date->format('Y-m-d H:i:s');

            $put = json_decode($request->getContent());


            $data = array(
                "modifyDatetime" => $now,
                "modifyPerson_id" => $userId,
                "quotaType_id" => $put->data->quotaType_id,
                "MKB" => $put->data->MKB,
                "pacientModel_id" => $put->data->pacientModel_id,
                "treatment_id" => $put->data->treatment_id
                );

             $app['db']->update('Client_Quoting', $data, array('id' => (int) $quotingId));


            return $app['jsonp']->jsonp(array("data" => $data));

        })->assert('appealId', '\d+')->assert('quotingId', '\d+');

        $controllers->get('/{appealId}/countActionsByType', 'Webmis\Controllers\Actions\ActionsController::countActionsByTypeAction')->assert('appealId', '\d+');


        return $controllers;
    }
}
