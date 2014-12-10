<?php

namespace Webmis\Controllers\Rls;

use Silex\Application;
use Silex\ControllerProviderInterface;
use Symfony\Component\HttpFoundation\Request;

class RlsRouter implements ControllerProviderInterface
{
    public function connect(Application $app)
    {
        function getController($shortName)
        {
            list($shortClass, $shortMethod) = explode('/', $shortName, 2);
            return sprintf('Webmis\Controllers\Rls\%sController::%sAction', $shortClass, $shortMethod);
        }

        $router = $app['controllers_factory'];

        $router->get('/', getController('Rls/list'));
        $router->get('/{id}', getController('Rls/read'));
        $router->get('/balance/{nomenId}', getController('Rls/balance'));

        return $router;
    }
}
