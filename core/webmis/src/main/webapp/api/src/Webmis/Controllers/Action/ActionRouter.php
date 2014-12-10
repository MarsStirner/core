<?php

namespace Webmis\Controllers\Action;

use Silex\Application;
use Silex\ControllerProviderInterface;


class ActionRouter implements ControllerProviderInterface
{
    public function connect(Application $app)
    {
        function getController($shortName)
        {
            list($shortClass, $shortMethod) = explode('/', $shortName, 2);
            return sprintf('Webmis\Controllers\Action\%sController::%sAction', $shortClass, $shortMethod);
        }

        $dirRouter = $app['controllers_factory'];


        $dirRouter->get('/', getController('Action/no'));
        $dirRouter->get('/{actionId}', getController('Action/read'));


        $dirRouter->get('/types', getController('ActionType/list'));
        $dirRouter->get('/types/tree/{rootCode}', getController('ActionType/tree'));
        $dirRouter->get('/types/{actionTypeId}', getController('ActionType/read'));


        return $dirRouter;
    }
}
