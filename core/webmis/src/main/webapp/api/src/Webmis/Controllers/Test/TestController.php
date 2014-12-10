<?php
namespace Webmis\Controllers\Test;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;






class TestController
{


    public function indexAction(Request $request, Application $app)
    {
            $route_params = $request->get('_route_params') ;



            $client = $app['prescriptionExchange']->getClient();

            $responce = $client->updateBalanceOfGoods(array(8168));

            $data = array('updateBalanceOfGoods' => $responce);
            return $app['jsonp']->jsonp($data);

    }





}
