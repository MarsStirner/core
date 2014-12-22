<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\RbResultQuery;

class rbResultController
{
    public function listAction(Request $request, Application $app)
    {
            $appealId = $request->query->get('appealId');

            $rbResultQuery = RbResultQuery::create()
            ->_if($appealId)
                ->useEventTypeQuery()
                    ->useEventQuery()
                        ->filterById($appealId)
                    ->endUse()
                ->endUse()
            ->_endif()
            ->select(array('id', 'code','name'))
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$rbResultQuery));
    }
}
