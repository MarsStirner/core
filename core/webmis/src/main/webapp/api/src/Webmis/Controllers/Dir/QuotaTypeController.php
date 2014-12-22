<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\QuotaTypeQuery;

class QuotaTypeController
{
    public function listAction(Request $request, Application $app)
    {
            $teenOlder = (int) $request->query->get('teenOlder');
            $teenOlder = $teenOlder ? $teenOlder : 0;

            $mkbId = (int) $request->query->get('mkbId');

            $quotaType = QuotaTypeQuery::create()
            ->filterByTeenOlder($teenOlder)//фильтр по возрасту
                ->_if($mkbId)//фильтр по диагнозу
                    ->useMkbQuotaTypePacientModelQuery()
                        ->filterByMkbId($mkbId)
                    ->endUse()
                ->_endif()
            ->select(array('id', 'code','name'))
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$quotaType));
    }
}
