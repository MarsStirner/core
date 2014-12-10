<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\SettingQuery;

class SettingsController
{
    public function listAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $path = $request->query->get('path');


        $settings = SettingQuery::create()
            ->select(array('id', 'path','value'))
            ->_if($path)
                ->filterByPath($path)
            ->_endIf()
            ->find()
            ->toArray();

        return $app['jsonp']->jsonp(array('data'=>$settings));
    }
}
