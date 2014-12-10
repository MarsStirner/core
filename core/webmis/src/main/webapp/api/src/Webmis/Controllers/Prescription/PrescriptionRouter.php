<?php

namespace Webmis\Controllers\Prescription;

use Silex\Application;
use Silex\ControllerProviderInterface;
use Symfony\Component\HttpFoundation\Request;

class PrescriptionRouter implements ControllerProviderInterface
{
    public function connect(Application $app)
    {
        function getController($shortName)
        {
            list($shortClass, $shortMethod) = explode('/', $shortName, 2);
            return sprintf('Webmis\Controllers\Prescription\%sController::%sAction', $shortClass, $shortMethod);
        }

        $router = $app['controllers_factory'];

        $router->get('/template/{actionTypeId}', getController('Prescription/template'));

        $router->put('/intervals', getController('Prescription/updateIntervals'));
        $router->put('/executeIntervals', getController('Prescription/executeIntervals'));
        $router->put('/cancelIntervalsExecution', getController('Prescription/cancelIntervalsExecution'));
        $router->put('/cancelIntervals', getController('Prescription/cancelIntervals'));
        $router->get('/types', getController('Prescription/typesList'));
        $router->get('/', getController('Prescription/list'));
        $router->post('/', getController('Prescription/create'));
        $router->get('/{prescriptionId}', getController('Prescription/read'));
        $router->put('/{prescriptionId}', getController('Prescription/update'));


        $router->delete('/drugs/{drugId}', getController('Prescription/drugCancel'));


        $router->get('/{prescriptionId}/intervals', getController('Prescription/readIntervals'));
        /* $router->put('/{prescriptionId}/intervals', getController('Prescription/updateIntervals')); */
        return $router;
    }
}
