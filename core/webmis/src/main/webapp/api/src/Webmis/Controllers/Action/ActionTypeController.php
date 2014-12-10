<?php
namespace Webmis\Controllers\Action;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\ActionTypeQuery;

class ActionTypeController
{
    public function treeAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $rootCode =  $route_params['rootCode'];

        $actions = ActionTypeQuery::create()
        ->filterByDeleted(0)
        ->find()
        ->toArray();

        if(!$rootCode){
            return $app['jsonp']->jsonp(array('message' => 'Нет кода корневого экшентайпа.' ));
        }

        $tree = $this->makeTree($rootCode, $actions);

        return $app['jsonp']->jsonp(array('data' => $tree ));
    }

    private function makeTree($rootCode, $items){
        $rootId = $this->getRootId($rootCode, $items);

        if(!$rootId){
            return false;
        }

        $tree = $this->getBranch($rootId, $items);

        return $tree;
    }

    private function getBranch($rootId, $items){
        $branch = array();

        foreach ($items as $item) {

            if($item['groupId'] == $rootId){
                $leaf = array(
                    'id' => $item['id']
                    ,'code' => $item['code']
                    ,'groupId' => $item['groupId']
                    ,'name' => $item['name']
                    );

                $children = $this->getBranch($item['id'], $items);

                if($children){
                    $leaf['groups'] = $children;
                }

                $branch[] = $leaf;
            }
        }

        return $branch;
    }

    private function getRootId($rootCode, $items){
        $rootId = false;

        foreach ($items as $item) {
            if($item['code'] == $rootCode){
                $rootId = $item['id'];
                break;
            }
        }

        return $rootId;
    }


    public function listAction(Request $request, Application $app)
    {
        $page = (int) $request->get('page') ?: 1;
        $limit = (int) $request->get('limit') ?: 20;

        $data = array();

        $actionsPager = ActionTypeQuery::create()
        ->filterByDeleted(0)
        ->paginate($page, $limit);


        foreach ($actionsPager as $action) {

            $data[] = array(
                'id' => $action->getId()
                ,'name' => $action->getName()
                ,'code' => $action->getCode()
                ,'groupId' => $action->getGroupId()
                //,'flatCode' =>$action->getFlatCode()
                );
        }

        return $app['jsonp']->jsonp(array(
            'data' => $data,
            'request' => array(
                'page' => $page
                ,'limit' => $limit
                ,'count'=> $actionsPager->getNbResults()
                )
            ));

    }
        // $tree = (bool) $request->get('tree') ?: false;

    public function readAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $actionTypeId = (int) $route_params['actionTypeId'];
        $data = array();

        if(!$actionTypeId){
            return $app['jsonp']->jsonp(array('message' => 'Нет идентификатора экшентайпа.' ));
        }

        $actionType = ActionTypeQuery::create()
        ->joinWithActionPropertyTypeRelatedByactionTypeId()
        ->filterById($actionTypeId)
        ->orderBy('ActionPropertyTypeRelatedByactionTypeId.idx')
        ->where('ActionPropertyTypeRelatedByactionTypeId.deleted = ?', 0)
        ->find();

        $actionType = $actionType[0];

        $data['id'] = $actionType->getId();
        $data['code'] = $actionType->getCode();
        $data['name'] = $actionType->getName();
        $data['properties'] = array();

        $properties = $actionType->getActionPropertyTypesRelatedByactionTypeId();

        foreach($properties as $property){
            $data['properties'][] = array(
                'name'=>$property->getName()
                ,'idx'=> $property->getIdx()
                ,'mandatory' => $property->getMandatory()
                ,'readOnly' => $property->getReadOnly()
                ,'typeName' => $property->getTypeName()
                ,'scope' => $property->getValueDomain()
                ,'value' => ''

                );
        }


        return $app['jsonp']->jsonp(array('data' => $data));
    }


}
