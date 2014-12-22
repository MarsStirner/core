<?php

namespace Webmis\Controllers\Patients;

use Silex\Application;
use Silex\ControllerProviderInterface;



class PatientsRouter implements ControllerProviderInterface
{
	public function connect(Application $app)
	{
		function getController($shortName)
		{
			list($shortClass, $shortMethod) = explode('/', $shortName, 2);
			return sprintf('Webmis\Controllers\Therapy\%sController::%sAction', $shortClass, $shortMethod);
		}

		$dirRouter = $app['controllers_factory'];

		$dirRouter->get('/{patientId}/therapies', getController('Therapy/forPatient'));

		return $dirRouter;
	}
}
