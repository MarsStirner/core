<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\RbMethodOfAdministrationQuery;


class RbMethodOfAdministrationController
{
    public function listAction(Request $request, Application $app)
    {

            $code = $request->query->get('code');
            if($code){
                $code = preg_replace('/\s+/', '', $code);
                $code = explode(',', $code);
            }

            $administration = RbMethodOfAdministrationQuery::create()
            ->_if($code)
                ->filterByCode($code)
            ->_endIf()

            ->select(array('id', 'code','name'))
//            ->orderByName()
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$administration));
    }
}
