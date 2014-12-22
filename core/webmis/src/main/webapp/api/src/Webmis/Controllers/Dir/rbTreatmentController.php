<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\RbTreatmentQuery;

class rbTreatmentController
{
    public function listAction(Request $request, Application $app)
    {
            $pacientModelId = $request->query->get('pacientModelId');

            $treatment = RbTreatmentQuery::create()
            ->_if($pacientModelId)
                ->useRbPacientModelQuery()
                    ->filterById($pacientModelId)
                ->endUse()
            ->_endif()
            ->select(array('id', 'code','name'))
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$treatment));
    }
}
