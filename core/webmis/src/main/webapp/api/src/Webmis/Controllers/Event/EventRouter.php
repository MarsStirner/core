<?php

namespace Webmis\Controllers\Event;

use Silex\Application;
use Silex\ControllerProviderInterface;
use Symfony\Component\HttpFoundation\Request;

class EventRouter implements ControllerProviderInterface
{
    public function connect(Application $app)
    {
        function getController($shortName)
        {
            list($shortClass, $shortMethod) = explode('/', $shortName, 2);
            return sprintf('Webmis\Controllers\Event\%sController::%sAction', $shortClass, $shortMethod);
        }

        $dirRouter = $app['controllers_factory'];


        $dirRouter->get('/', getController('Event/list'));
        $dirRouter->get('/{eventId}', getController('Event/read'));
        $dirRouter->get('/{eventId}/actions', getController('Event/eventActions'));
        // $dirRouter->get('/{eventId}/actions/{actionId}', function(Request $request){
        //     $route_params = $request->get('_route_params') ;
        //     $eventId = (int) $route_params['eventId'];
        //     $actionId = (int) $route_params['actionId'];

        //     return "hello world".$eventId;//getController('Event/readEventAction')
        // });


        return $dirRouter;
    }
}
