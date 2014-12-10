<?php
namespace Webmis\Controllers\Action;

// use \Propel;
// use \PDO;
use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\ActionQuery;

class ActionController
{

    public function noAction(Request $request, Application $app)
    {


        // $con = Propel::getConnection();
        // $sql = "SELECT * FROM Action WHERE id = :id";
        // $stmt = $con->prepare($sql);
        // $stmt->execute(array(':id' => 1));
        // $action = $stmt->fetch(PDO::FETCH_ASSOC);

        // return $app['jsonp']->jsonp(array('data' => $action));
        return $app['jsonp']->jsonp(array('message' => 'тут ничего нет' ));
    }


    public function readAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $actionId = (int) $route_params['actionId'];
        $data = array();

        if(!$actionId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора экшена.' ));
        }

        $action = ActionQuery::create()
        ->filterById($actionId)
        ->getProperties()

        ->find();//->toArray();

        $action = $action[0];

        $actionType = $action->getActionType();
        $actionProperties = $action->getActionPropertys();

        $data['id'] = $action->getId();
        $data['name'] = $actionType->getName();
        $data['properties'] = array();

        foreach ($actionProperties as $actionProperty){
                    $actionPropertyType = $actionProperty->getActionPropertyType();
                    $typeName = $actionPropertyType -> getTypeName();

                    $p = array();
                    $p['id'] = $actionProperty->getId();
                    $p['name'] = $actionPropertyType -> getName();
                    $p['code'] = $actionPropertyType -> getCode();
                    $p['scope'] = $actionPropertyType -> getValueDomain();
                    $p['p'] = $actionProperty->toArray();
                    $p['pt'] = $actionPropertyType->toArray();

                    switch ($typeName) {
                        case 'String':
                        case 'Html':
                        case 'Text':
                        //case 'Constructor':
                            // $p['value'] = $actionProperty->getActionPropertyString()->getValue();
                        break;
                        case 'Double':
                            // $p['value'] = $actionProperty->getActionPropertyDouble()->getValue();
                        break;
                        case 'FlatDirectory':
                            // $p['value'] = $actionProperty->getActionPropertyFDRecord()->getValue();
                        break;
                        case 'Date':
                            // $date = $actionProperty->getActionPropertyDate()->getValue();
                            // $dateArray = explode('-', $date);
                            // $year = $dateArray[0];
                            // if($year < 1000){
                            //     $p['value'] = null;
                            // }else{
                            //     $p['value'] = strtotime($date)*1000;
                            // }

                        break;
                        default:
                            $p['value'] = 'этот тип экшен проперти пока не поддерживается';
                        break;
                    }

                    $data['properties'][] = $p;

                }

        return $app['jsonp']->jsonp(array('data' => $data ));
    }


    // public function countActionsByTypeAction(Request $request, Application $app)
    // {
    //     $route_params = $request->get('_route_params') ;
    //     $appealId = $route_params['appealId'];
    //     $actyonTypeId = $request->get('actyonTypeId');

    //     $get_params = $request->query->all();
    //     $params = array_merge($get_params, $route_params);


    //     $actionsCount = ActionQuery::create()
    //         ->filterByEventId($appealId)
    //         ->filterByActionTypeId($actyonTypeId)
    //         ->count();

    //     return $app['jsonp']->jsonp(array('params' => $params,
    //         'data'=> array('actionsCount' => $actionsCount) ));
    // }


}
