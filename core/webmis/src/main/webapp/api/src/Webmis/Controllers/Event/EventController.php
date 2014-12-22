<?php
namespace Webmis\Controllers\Event;

// use \Propel;
// use \PDO;
use \BasePeer;
use \Criteria;
use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\EventQuery;
use Webmis\Models\ActionQuery;

class EventController
{

    public function noAction(Request $request, Application $app)
    {
        return $app['jsonp']->jsonp(array('message' => 'тут ничего нет' ));
    }

    public function listAction(Request $request, Application $app)
    {
        $patientId = (int) $request->get('patientId');
        $data = array();

        if(!$patientId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора пациента.' ));
        }

        $events = EventQuery:: create()
        ->filterByDeleted(0)
        ->filterByclientId($patientId)
        ->useEventTypeQuery()
            ->filterByCode(array('01','14', '11', '12', '06', '15', '16', '03', '04', '17', '18'))
        ->endUse()
        ->select(array('id', 'externalId','execPerson_id'))
        ->orderByCreateDatetime('desc')
        ->find();

        $data = $events->toArray();


        return $app['jsonp']->jsonp(array('data' => $data ));
    }

    public function eventActionsAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $eventId = (int) $route_params['eventId'];
        $data = array();

        if(!$eventId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора евента.' ));
        }


        $actions = ActionQuery::create()
        ->filterByEventId($eventId)
        ->filterByDeleted(false)
        ->joinWithActionType()
        ->find();//->toArray();

        foreach ($actions as $action) {
            $data[] = array(
                'id' => $action->getId(),
                'name' => $action->getActionType()->getName() );
        }


        return $app['jsonp']->jsonp(array('data' => $data ));
    }

    public function readEventAction(Request $request, Application $app){
        return $app['jsonp']->jsonp(array('message' => 'тут ничего нет' ));
    }


    public function readAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $eventId = (int) $route_params['eventId'];


        if(!$eventId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора евента.' ));
        }

        $event = EventQuery::create()
            ->filterById($eventId)
            ->filterByDeleted(false)
            ->withClient()
            ->withCreatePerson()
            ->withModifyPerson()
            ->withDoctor()
            ->withSetPerson()
            ->serialize();

        //->toArray(BasePeer::TYPE_PHPNAME,true,array(),true);

        return $app['jsonp']->jsonp(array('data' => $event ));
    }


}
