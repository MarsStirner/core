<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\MkbQuotaTypePacientModelQuery;
use Webmis\Models\RbPacientModelQuery;

class rbPacientModelController
{
    public function listAction(Request $request, Application $app)
    {
            $quotaTypeId = $request->query->get('quotaTypeId');

            $mkbId = $request->query->get('mkbId');

            $pacientModel = RbPacientModelQuery::create()
            ->useMkbQuotaTypePacientModelQuery()
                ->_if($mkbId)//фильтр по диагнозу
                    ->filterByMkbId($mkbId)
                ->_endif()
                ->_if($quotaTypeId)//фильтр по коду типа ВМП
                    ->filterByQuotaTypeId($quotaTypeId)
                ->_endif()
            ->endUse()
            ->select(array('id', 'code','name'))
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$pacientModel));
    }
}
