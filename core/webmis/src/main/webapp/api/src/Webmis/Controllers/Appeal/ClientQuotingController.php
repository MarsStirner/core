<?php
namespace Webmis\Controllers\Appeal;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\ClientQuotingQuery;

class ClientQuotingController
{


    public function readAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $appealId = $route_params['appealId'];
        $select_sql = "SELECT cq.*, pm.name as 'patientModelName',mkb.DiagName,mkb.id as 'mkbId', qt.name AS 'quotaTypeName',qt.code AS 'quotaTypeCode',t.name AS 'treatmentName' "
                ."FROM Client_Quoting as cq "
                ."JOIN MKB AS mkb ON cq.MKB = mkb.DiagID "
                ."JOIN rbPacientModel AS pm on cq.pacientModel_id = pm.id "
                ."JOIN QuotaType AS qt on cq.quotaType_id = qt.id "
                ."JOIN rbTreatment AS t ON cq.treatment_id = t.id "
                ."WHERE cq.event_id = ? "
                ."ORDER BY cq.createDatetime DESC ";

                $vmpTalon = $app['db']->fetchAssoc($select_sql, array((int) $appealId));

                if(!$vmpTalon){
                    $vmpTalon = array();
                }

            return $app['jsonp']->jsonp(array("data" => $vmpTalon,'$route_params'=>$route_params));
    }


    public function readPrevAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $appealId = $route_params['appealId'];

            $select_sql = "SELECT * "
            ."FROM Event as e "
            ."WHERE e.id = ? "
            ."AND e.eventType_id IN (2,13,53,67,68,69,100,102,103,104) "
            ."ORDER BY e.createDatetime DESC "
            ."LIMIT 1 ";

            $event = $app['db']->fetchAssoc($select_sql, array((int) $appealId));
            $clientId = $event["client_id"];


            $select_sql2 = "SELECT cq.*, pm.name as 'patientModelName',mkb.id AS 'mkbId',mkb.DiagName, qt.name AS 'quotaTypeName',qt.code AS 'quotaTypeCode',t.name AS 'treatmentName' "
            ."FROM Client_Quoting as cq "
            ."JOIN MKB AS mkb ON cq.MKB = mkb.DiagID "
            ."JOIN rbPacientModel AS pm on cq.pacientModel_id = pm.id "
            ."JOIN QuotaType AS qt on cq.quotaType_id = qt.id "
            ."JOIN rbTreatment AS t ON cq.treatment_id = t.id "
            ."WHERE cq.master_id = ? "
            ."AND cq.event_id != ? "
            ."ORDER BY cq.createDatetime DESC "
            ."LIMIT 1";

            $previousVmpTalon = $app['db']->fetchAssoc($select_sql2, array((int) $clientId,(int) $appealId));
            if(!$previousVmpTalon){
                $previousVmpTalon = array();
            }

            return $app['jsonp']->jsonp(array("data" => $previousVmpTalon));
    }
}
