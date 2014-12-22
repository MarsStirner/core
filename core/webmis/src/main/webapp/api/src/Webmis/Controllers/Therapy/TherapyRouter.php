<?php

namespace Webmis\Controllers\Therapy;

use Silex\Application;
use Silex\ControllerProviderInterface;



class TherapyRouter implements ControllerProviderInterface
{
	public function connect(Application $app)
	{
		function getController($shortName)
		{
			list($shortClass, $shortMethod) = explode('/', $shortName, 2);
			return sprintf('Webmis\Controllers\Therapy\%sController::%sAction', $shortClass, $shortMethod);
		}

		$dirRouter = $app['controllers_factory'];

		//$dirRouter->get('/', getController('Therapy/list'));
		$dirRouter->get('/last4event/{eventId}', getController('Therapy/last'));
		//$dirRouter->get('/rest4event/{eventId}', getController('Therapy/rest'));
		//rest4patient

		return $dirRouter;
	}
}
